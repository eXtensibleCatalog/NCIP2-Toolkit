package org.extensiblecatalog.ncip.v2.voyager.util;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;

/**
 * A Thread whose run method looks up information on the user who is
 * authenticated every minute. This will prevent Voyager from timing out the
 * user.
 * 
 * @author Eric Osisek
 */

public class VoyagerAuthentication {


	/** Logger */
    static Logger log = Logger.getLogger(VoyagerAuthentication.class);

    private String pid = "";

	/**
     * Used to keep track of a connection to Voyager
     */
    private String seq = "";

	/**
     * Used to run regexes
     */
    private RegexUtils regexUtils = new RegexUtils();
//
//    /**
//     * Used to connect to the LDAP server defined in the configuration file
//     */
//    private LDAPUtils ldapUtils = new LDAPUtils();

    /**
     * HttpClient used for screen scraping Voyager
     */
    private HttpClient client = new HttpClient(
	    new MultiThreadedHttpConnectionManager());

	public String authenticatedUserId = null;

    /**
     * A Thread which sends a heartbeat to Voyager every minute to prevent an
     * authenticated user from being timed out by Voyager
     */
    VoyagerHeartbeatThread heartbeat = null;

	private class VoyagerHeartbeatThread extends Thread {
	
		/** Logger */
	    Logger log = Logger.getLogger(VoyagerHeartbeatThread.class);
	
		/**
		 * The ID of the user who we're maintaining authentication for
		 */
		private String userId;
	
		/**
		 * True while the Thread is running, false when it is done. This is set
		 * to true when the run method is invoked, and is set to false when the
		 * stopRunning method is invoked. After setting this to true, the run
		 * method will not terminate until it is set to false
		 */
		private boolean isRunning = false;
	
		/**
		 * The time when the last request was made.
		 */
		private Date lastRequestTime = new Date();
	
		/**
		 * Constructs a VoyagerHeartbeatThread for a given user
		 * 
		 * @param userId
		 *            The ID of the user we're maintaining authentication for
		 */
		public VoyagerHeartbeatThread(String userId) {
		    if (log.isDebugEnabled())
			log.debug("Creating a heartbeat Thread for user with ID "
				+ userId);
	
		    this.userId = userId;
		}
	
		/**
		 * The run method for the heartbeat Thread. This looks up information on
		 * the user every minute
		 */
		public void run() {
		    if (log.isDebugEnabled())
			log.debug("Telling the heartbeat Thread for use with ID "
				+ userId + " to start running.");
	
		    isRunning = true; // Tells the Thread to continue running until told
		    // to stop
	
		    // While we haven't been told to stop running, repeatadly get the
		    // user's
		    // information page and then sleep for 1 minute
		    while (isRunning) {
			// The URL of the information page for the authenticated user
			String informationPageUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL)
				+ "?PAGE=pbLogon&PID=" + pid;
	
			// Will hold the HTTP GET request to get the Voyager "first"
			// page
			GetMethod getInformationPage = null;
	
			// The status code returned with the HTTP response
			int statusCode = 0;
	
			try {
			    if (log.isDebugEnabled())
				log
					.debug("In the heartbeat Thread, getting the user's Information Page at "
						+ informationPageUrl);
	
			    synchronized (client) {
				// Instantiate a GET HTTP method to get the Voyager
				// "first" page
				getInformationPage = new GetMethod(informationPageUrl);
	
				// Execute the get method to get the Voyager "first"
				// page
				statusCode = client.executeMethod(getInformationPage);
			    }
	
			    // If the get was successful (200 is the status code for
			    // success)
			    if (statusCode == 200) {
				if (log.isDebugEnabled())
				    log
					    .debug("Successfully accessed the information page, returning the HTML");
	
				// Get the response from the HTTP GET request
				String result = getInformationPage
					.getResponseBodyAsString();
	
				// Either Voyager returns a status code of 200 when it
				// means to return a code of 400
				// in cases where the bib ID was invalid, or the HTTP
				// client is not correctly detecting
				// the status code. To compensate for this, we need to
				// manually check the returned HTML
				// to see if it's a 400.
				if (result
					.contains("HTTP Status Code 400 - Bad Request"))
				    log
					    .warn("Cound not find the user's Information Page in the heartbeat Thread for user with ID "
						    + userId + ", status code was 400");
			    } else
				// We could not get the page
				log
					.warn("Cound not find the user's Information Page in the heartbeat Thread for user with ID "
						+ userId
						+ ", status code was "
						+ statusCode);
			} catch (IOException e) {
			    log
				    .error(
					    "IOException caught while getting the user's information page in the heartbeat Thread for user with ID "
						    + userId, e);
			} catch (IllegalArgumentException e) {
			    log
				    .warn(
					    "Cound not find the user's information Page in the heartbeat Thread for user with ID "
						    + userId, e);
			} finally {
			    if (log.isDebugEnabled())
				log
					.debug("In the heartbeat Thread, releasing the HTTP connection to "
						+ informationPageUrl);
	
			    // Release the connection
			    if (getInformationPage != null)
				getInformationPage.releaseConnection();
			}
	
			// If the number of seconds since the last request is greater
			// than the timeout specified in
			// the configuration file, we can terminate the heartbeat Thread
			// by returning.
	
			// Get the number of seconds since the last request. Since
			// getTime returns milliseconds,
			// we'll need to divide the result by 1000. We then use
			// Math.round to avoid truncated data.
			long numSecondsPassed = Math
				.round((System.currentTimeMillis() - lastRequestTime
					.getTime()) / 1000.0);
	
			if (log.isDebugEnabled())
			    log
				    .debug(numSecondsPassed
					    + " seconds have passed since the last request occurred.");
	
			// Get the timeout from the configuration file
			Long timeout = Long
				.parseLong(NCIPConfiguration
					.getProperty(VoyagerConstants.CONFIG_ILS_AUTHENTICATION_TIMEOUT));
	
			// Delete the authentication information and stop running if
			// we're passed the timeout
			if (numSecondsPassed > timeout) {
			    if (log.isInfoEnabled())
				log
					.info("Stopping the heartbeat Thread because the timeout of "
						+ timeout
						+ " seconds has been exceeded.");
	
			    // Reset the authentication data
			    authenticatedUserId = null;
			    setupPidAndSeq(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL) + "?DB=local&PAGE=First");
	
			    return;
			}
	
			// Sleep for one minute, then send another heartbeat
			try {
			    if (log.isDebugEnabled())
				log.debug("Heartbeat Thread sleeping for one minute.");
	
			    // Sleep for one minute
			    Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
		    }
		}
	
		/**
		 * Sets the userId the heartbeat Thread is authenticating for
		 */
		public void setUserId(String userId) {
		    this.userId = userId;
		}
	
		/**
		 * Tells the heartbeat Thread that we just handled a request
		 */
		public void signalRequestHandled() {
		    lastRequestTime.setTime(System.currentTimeMillis());
		}
	
		/**
		 * Tells the heartbeat Thread to stop running
		 */
		public void stopRunning() {
		    if (log.isDebugEnabled())
			log.debug("Telling the heartbeat Thread for use with ID "
				+ userId + " to stop running.");
	
		    isRunning = false;
		}
	}

    /**
     * Does an HTTP GET request on Voyager which confirms that the connection
     * has been authenticated
     * 
     * @throws IOException
     *             When an error occurred accessing the URL to confirm
     *             authentication
     * @throws ILSException
     *             If the authentication URL was not loaded correctly
     */
    public void sendAuthenticationUrl() throws IOException, ILSException {
		// The HTTP GET request which will confirm authentication
		GetMethod confirmAuthentication = null;
	
		// The URL of the confirmAuthentication GetMethod
		String confirmAuthenticationUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL)
			+ "?PAGE=pbPatronRequestBibLogon&PID=" + pid + "&SEQ=" + seq
			+ "&authenticate=Y";
	
		try {
		    if (log.isDebugEnabled())
			log.debug("Sending the authentication confirmation at "
				+ confirmAuthenticationUrl);
	
		    int statusCode = 0; // The status code in the HTTP response
	
		    synchronized (client) {
				// Instantiate a GET HTTP method to get the Voyager "first" page
				confirmAuthentication = new GetMethod(confirmAuthenticationUrl);
		
				// Execute the get method to get the Voyager "first" page
				statusCode = client.executeMethod(confirmAuthentication);
		    }
	
		    // If the get was successful (200 is the status code for success)
		    if (statusCode == 200) {
			if (log.isDebugEnabled())
			    log
				    .debug("Successfully sent the authentication confirmation, returning");
		    } else // We could not get the page
		    {
			log.error("Cound not send the confirm authentication URL");
	
			throw new ILSException("Failed to confirm authentication with Voyager.");
		    }
		} finally {
		    if (log.isDebugEnabled())
			log.debug("Releasing the HTTP connection to "
				+ confirmAuthenticationUrl);
	
		    // Release the connection
		    if (confirmAuthentication != null)
			confirmAuthentication.releaseConnection();
		}
    }
    
    /**
     * If there is currently no heartbeat Thread, starts a new heartbeat Thread
     * to maintain authentication for the specified user. Otherwise reconfigures
     * the existing heartbeat Thread to work for the specified user.
     * 
     * @param userId
     *            The user who the heartbeat is for
     */
    public void setupHeartbeatThread(String userId) {
		if (log.isDebugEnabled())
		    log.debug("Entering setupHeartbeatThread");
	
		// If there's not currently a heartbeat Thread, create a new one
		// for the specified user
		if (heartbeat == null) {
		    if (log.isDebugEnabled())
			log.debug("Creating a new heartbeat Thread for user " + userId);
	
		    heartbeat = new VoyagerHeartbeatThread(userId);
		    try {
			// Create a unique name for the heartbeat Thread based on the
			// current Thread's name
			heartbeat.setName(Thread.currentThread().getName()
				+ " Heartbeat");
		    } catch (SecurityException e) {
		    } // This happens if we couldn't change the Thread's name
	
		    heartbeat.start();
		}
		// If there's already a heartbeat Thread running, set it to use the
		// specified user instead of the one it's already configured to use
		else {
		    if (log.isDebugEnabled())
			log.debug("Setting the heartbeat Thread to use user " + userId);
	
		    heartbeat.setUserId(userId);
		}
    }
    
    /**
     * Returns true iff the PID and SEQ values were successfully set. This
     * method is used to check if the VoyagerInterface was correctly initialized
     * before trying to perform any operations
     * 
     * @return true if the PID and SEQ are non-empty strings, false otherwise
     */
    public boolean validPidAndSeq() {
    	return (pid != null && seq != null && pid.length() > 0 && seq.length() > 0);
    }
    
    /**
     * Starts a session with Voyager and pulls the PID and SEQ values from that
     * session to track it as we preform our operations on Voyager.
     * 
     * @param url
     *            The URL from which to pull the PID and SEQ
     */
    public void setupPidAndSeq(String url) {
		if (log.isDebugEnabled())
		    log.debug("Entering setupPidAndSeq");
	
		// Will hold the HTTP GET request to get the Voyager "first" page
		GetMethod getFirstPage = null;
	
		try {
		    if (log.isDebugEnabled())
			log.debug("Getting the Voyager First Page at " + url);
	
		    int statusCode = 0; // The status code in the HTTP response
	
		    synchronized (client) {
			// Instantiate a GET HTTP method to get the Voyager "first" page
			getFirstPage = new GetMethod(url);
	
			// Execute the get method to get the Voyager "first" page
			statusCode = client.executeMethod(getFirstPage);
		    }
	
		    // If the get was successful (200 is the status code for success
		    if (statusCode == 200) {
				if (log.isDebugEnabled())
				    log
					    .debug("Parsing the PID and SEQ from the Voyager First Page using regexes.");
		
				// Get the response from the HTTP GET request
				String response = getFirstPage.getResponseBodyAsString();
		
				// Run a regex to get the PID from the response
				pid = regexUtils.runRegex(response, "PID=([^\"|^&]*)", 1);
		
				if (log.isDebugEnabled())
				    log.debug("The PID was found to be " + pid);
		
				// Run a regex to get the SEQ from the response
				seq = regexUtils.runRegex(response, "SEQ=(\\w*)", 1);
		
				if (log.isDebugEnabled())
				    log.debug("The SEQ was found to be " + seq);
		    } else {// We could not get the page
			    
				log.error("Cound not find the Voyager First Page.  This most likely happened because the VoyagerUrl "
					+ "property in the configuration file was incorrect.  The VoyagerUrl was "
					+ NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL));
			 }
		} catch (IOException e) {
		    log.error("IOException caught while getting the Voyager first page.An internal error occurred in the NCIP Toolkit.", e);
		} catch (IllegalStateException e) {
	
		    log.error("IllegalStateException caught while getting the PID and SEQ from the Voyager First Page. An internal error occurred in the NCIP Toolkit.", e);
		} catch (IllegalArgumentException e) {
	
		    log.error("Cound not find the Voyager First Page.  This most likely happened because the VoyagerUrl "
			    + "property in the configuration file was incorrect.  The VoyagerUrl was "
			    + NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL), e);
		} finally {
		    if (log.isDebugEnabled())
			log.debug("Releasing the HTTP connection to " + url);
	
		    // Release the connection
		    if (getFirstPage != null)
			getFirstPage.releaseConnection();
		}
    }


    public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


    public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
