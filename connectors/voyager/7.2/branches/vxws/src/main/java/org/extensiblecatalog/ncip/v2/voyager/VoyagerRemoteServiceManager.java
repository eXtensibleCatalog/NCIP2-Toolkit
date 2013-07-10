package org.extensiblecatalog.ncip.v2.voyager;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.AccountDetails;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddress;
import org.extensiblecatalog.ncip.v2.service.ElectronicResource;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation;
import org.extensiblecatalog.ncip.v2.service.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemDetails;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.LoanedItemsCount;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;
import org.extensiblecatalog.ncip.v2.service.XcRequestType;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.LDAPUtils;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


/**
 * VoyagerRemoteServiceManager is the implementation of the back-end service
 * 
 * @author SharmilaR
 *
 */
public class VoyagerRemoteServiceManager implements  RemoteServiceManager { 
	
	/** Logger */
    static Logger log = Logger.getLogger(VoyagerRemoteServiceManager.class);
	
	/** Database read connection */
	private static Connection conn;
    

	HttpClient client = new HttpClient(
    	    new MultiThreadedHttpConnectionManager());

    /**
     * Constructor
     */
    public VoyagerRemoteServiceManager() throws ILSException {
	}
	
	/**
	 * Authenticate user
	 * 
	 * @param inputs
	 * @return
	 * @throws ILSException
	 * @throws ILSException
	 */
	public String authenticateUser(List<AuthenticationInput> inputs, String ubPrefix) throws ILSException {
		 
		 String username = null;
		 String password = null;
		 String ldapUsername = null;
		 String ldapPassword = null;
		 String authenticatedUserId = null;
		 
		 for (AuthenticationInput a : inputs) {
			if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Username")) {
				username = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Password")) {
				password = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPUsername")) {
				ldapUsername = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPPassword")) {
				ldapPassword = a.getAuthenticationInputData();
			}
		 }
		 
		 if (username != null && password != null) {
			 authenticatedUserId = authenticateUser(username, password, ubPrefix);
		 }		 
		 if (ldapUsername != null && ldapPassword != null) {
			 authenticatedUserId = authenticateLDAPUser(ldapUsername, ldapPassword, ubPrefix);
		 }
		 return authenticatedUserId;
	 }
	
	/**
	 *  Authenticates User before processing the request
	 *  
	 * @param username
	 * @param password
	 * @param ubPrefix
	 * @return
	 * @throws ILSException
	 */
    public String authenticateUser(String username, String password, String ubPrefix) throws ILSException {

    	if (log.isDebugEnabled())
    	    log.debug("Entering authenticateUser for user: " + username + ".");
 
    	// The institution ID returned by the web service assuming username was an institution ID
	    String institutionId = getPatronIdFromInstitutionIdAuthData(username, password.toLowerCase(), ubPrefix);

	    if (institutionId != null) {
    		log.info("Found institution ID " + institutionId + " from vxws with institution id");
    		return institutionId;
	    } 
	    // The web service did not authenticate the user, try again assuming barcode data
	    else { 
	    	institutionId = getPatronIdFromBarcodeAuthData(username, password.toLowerCase(), ubPrefix);
    		// If any results were returned, return the id
    		if (institutionId != null) {
    			log.debug("Found institution ID " + institutionId + " from vxws using barcode");
    		    return institutionId;
    		} else {
    		    log.info("The username or password provided by the user was incorrect.");
    		    throw new ILSException(
    			    "The username or password was incorrect.");
    		}
	    }
	}
    
    /**
     * Authenticates user against LDAP server
     * 
     * @param username User name of user
     * @param password Password
     * @return
     * @throws ILSException
     */
    public String authenticateLDAPUser(String username, String password, String ubPrefix) throws ILSException {
    	if (log.isDebugEnabled())
    	    log.debug("Entering authenticateUser for LDAP user: " + username + ".");

    	// The connection to the LDAP server
    	DirContext ldapConnection = null;
    	LDAPUtils ldapUtils = new LDAPUtils();

    	try {
    	    // Get a connection to the LDAP server
    	    ldapConnection = ldapUtils.getLDAPConnection(username, password);

    	    // Get the username attribute and start location on the LDAP server
    	    // from the configuration file
    	    String usernameAttribute = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_USERNAME_ATTRIBUTE);
    	    String startLocation = NCIPConfiguration
    		    .getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_START);

    	    // Get the attributes associated with the user
    	    Attributes attributes = ldapConnection
    		    .getAttributes(usernameAttribute + "=" + username + ", "
    			    + startLocation);

    	    // Get the attribute containing the patron key which Voyager needs
    	    String urid = (String) attributes
    		    .get(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_UR_ID))
    		    .get();
    	    
    	    // Get the attribute containing the last name which Voyager needs
    	    String lastName = (String)  attributes.get(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_LAST_NAME)).get();
    	   
    	    log.info("Retrieved urid: " + urid + " and last name: " + lastName);
    	    
    	    // Authenticate against voyager db
    	    return getPatronIdFromInstitutionIdAuthData(urid, lastName, ubPrefix);
    	} catch (NamingException e) {
    	    log.error("An error occurred while getting the users patron key.",
    		    e);

    	    throw new ILSException(
    		    "An error occurred while getting the users patron key.  This may have been caused "
    			    + "if the ExternalLDAPVoyagerPatronKey was set incorrectly in the NCIP Toolkit configuration file.");
    	} finally {
    	    // If we set up a connection to the LDAP server, close it
    	    if (ldapConnection != null) {
	    		try {
	    		    if (log.isDebugEnabled())
	    			log.debug("Closing the LDAP connection.");
	
	    		    ldapConnection.close();
	    		} catch (NamingException e) {
	    		    log
	    			    .warn(
	    				    "An error occurred while closing the connection to the LDAP server defined in the "
	    					    + "configuration file.", e);
	    		}
    	    }
    	}
    }

	/**
	 * Given a user's authentication data, return their patron ID.  If their
	 * authentication information was not valid, return null instead
	 * 
	 * @param username The user's barcode
	 * @param password The user's password
	 * @param ubPrefix The ubPrefix to the web service URL
	 * @return The user's patron ID if the username and password were correct, or null otherwise
	 * @throws ILSException If an error occurred while connecting to the web service
	 */
	public String getPatronIdFromBarcodeAuthData(String username, String password, String ubPrefix) throws ILSException
	{   
	    // Construct the xml to send to the web service
        // Using B for barcode id
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Document authUserXML = new Document();
        Element root = new Element("serviceParameters", serNs);
        authUserXML.addContent(root);
        Element parametersElement = new Element("parameters", serNs);
        root.addContent(parametersElement);
        Element patronElement = new Element("patronIdentifier", serNs);
        patronElement.setAttribute("lastName", password);
        root.addContent(patronElement);
        Element authElement = new Element("authFactor", serNs);
        authElement.setAttribute("type", "B");
        authElement.setText(username);
        patronElement.addContent(authElement);
        
        XMLOutputter xmlOutP = new XMLOutputter();
        
        int statusCode = 0;
        PostMethod postMethod = null;
        // Retrieve the URL of vxws
        String postUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        // And attach the particular web service we are using
		postUrl += ubPrefix + "/AuthenticatePatronService";
		
    	try {
	        synchronized(client) {
	        	postMethod = new PostMethod(postUrl);
	        	//postRenewItem.setRequestEntity(new StringRequestEntity(authenticateUserXml));
	        	postMethod.setRequestEntity(new StringRequestEntity(xmlOutP.outputString(authUserXML)));
	        	statusCode = client.executeMethod(postMethod);	
				log.info("Sent xml request to server.  Received status code " + Integer.toString(statusCode));
	        }
	        
	        if (statusCode == 200) {
	        	InputStream response = postMethod.getResponseBodyAsStream();	        	
	        	SAXBuilder builder = new SAXBuilder();
	        	try {
	        		// Build a JDOM Document from the response and extract the institution ID
		        	Document doc = builder.build(response);
		        	Namespace patNs = Namespace.getNamespace("pat", "http://www.endinfosys.com/Voyager/patronAuthentication");
		        	// If there is a serviceData element then the user was authenticated.  Otherwise an error response was returned
		        	if (doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs) != null) {
		        		String patronId = doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs).getAttributeValue("patronId"); 
		        		return patronId;
		        	}
	        		else
		        		return null;
	        	} catch (JDOMException e) { 
	        		log.debug("Error parsing xml response");
	        	} catch (NullPointerException e) {
	        		log.debug("User not found with barcode date");
	        		return null;
	        	}
	        	
	        } else 
				log.error("Cound not contact the vxws service");
	        
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
		}	
    	    	
    	return null;
	}

	/**
	 * Given a user's authentication data, return their patron ID.  If their
	 * authentication information was not valid, return null instead
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @param ubPrefix The ubPrefix to the web service URL
	 * @return The user's patron ID if the username and password were correct, or null otherwise
	 * @throws ILSException If an error occurred while connecting to the database
	 */
	public String getPatronIdFromInstitutionIdAuthData(String username, String password, String ubPrefix) throws ILSException
	{   
	    // Construct the xml to send to the web service
        // Using I for institution id
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Document authUserXML = new Document();
        Element root = new Element("serviceParameters", serNs);
        authUserXML.addContent(root);
        Element parametersElement = new Element("parameters", serNs);
        root.addContent(parametersElement);
        Element patronElement = new Element("patronIdentifier", serNs);
        patronElement.setAttribute("lastName", password.toLowerCase());
        root.addContent(patronElement);
        Element authElement = new Element("authFactor", serNs);
        authElement.setAttribute("type", "I");
        authElement.setText(username);
        patronElement.addContent(authElement);
        
        XMLOutputter xmlOutP = new XMLOutputter();
        
        int statusCode = 0;
        PostMethod postRenewItem = null;
        // Retrieve the URL of vxws
        String postUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        // And attach the particular web service we are using
		postUrl += ubPrefix + "/AuthenticatePatronService";
		
		log.debug("Using URL " + postUrl);
		
    	try {
	        synchronized(client) {
	        	postRenewItem = new PostMethod(postUrl);
	        	postRenewItem.setRequestEntity(new StringRequestEntity(xmlOutP.outputString(authUserXML)));
	        	statusCode = client.executeMethod(postRenewItem);	
				log.info("Sent xml request to server.  Received status code " + Integer.toString(statusCode));
	        }
	        
	        if (statusCode == 200) {
	        	InputStream response = postRenewItem.getResponseBodyAsStream();	        	
	        	SAXBuilder builder = new SAXBuilder();
	        	try {
	        		// Build a JDOM Document from the response and extract the institution ID
		        	Document doc = builder.build(response);
		        	Namespace patNs = Namespace.getNamespace("pat", "http://www.endinfosys.com/Voyager/patronAuthentication");
		        	// If there is a serviceData element then the user was authenticated.  Otherwise an error response was returned
		        	if (doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs) != null) {
			        	String patronId = doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs).getAttributeValue("patronId"); 
			        	return patronId;
			        }
	        		else
		        		return null;
	        	} catch (JDOMException e) { 
	        		log.debug("Error parsing xml response");
	        	} catch (NullPointerException npe) {
	        		log.debug("Did not find patron with I data");
	        		return null;
	        	}
	        	
	        } else 
				log.error("Cound not contact the vxws service");
	        
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
		}	
    	    	
    	return null;
	}

	/**
	 * Given a partial URL to a vxws service, constructs the entire URL and sends
	 * the given XML to the service, returning the response Document
	 * 
	 * @param serviceUrl partial URL to the particular vxws service
	 * @param inputXML XML to PUT in the HTTP request
	 * @return XML Document from the vxws response
	 */
	public Document putWebServicesDoc(String serviceUrl, String inputXml) {
		
    	int statusCode;
    	PutMethod putMethod = null;
        // The URL of the web services server + service name
        String baseUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        
        String fullUrl = baseUrl + serviceUrl; 
        
        InputStream response = null;
        
    	try {
	        synchronized(client) {
	        	putMethod = new PutMethod(fullUrl);
	        	putMethod.setRequestEntity(new StringRequestEntity(inputXml));
	        	statusCode = client.executeMethod(putMethod);
	        }
	        
	        if (statusCode == 200) {
	        	response = putMethod.getResponseBodyAsStream();
	        } else {
				log.error("Cound not contact the vxws service.  Recieved HTTP status code: " + statusCode);
	        }
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
		}

    	SAXBuilder builder = new SAXBuilder();
    	Document doc = null;
       	try {
    		// Build a JDOM Document from the response 
        	doc = builder.build(response);
       	} catch (JDOMException e) { 
    		log.debug("JDOMException parsing xml response");
    	} catch (IOException e) {
    		log.debug("IOException parsing xml response");
    	}   
    	
    	return doc;
		
	}
	
	/**
	 * Given a partial URL to a vxws service, constructs the entire URL
	 * and issues an HTTP GET request to that URL
	 * @param serviceUrl partial URL to the particular vxws service
	 * @return XML Document from the vxws response
	 */
	public Document getWebServicesDoc(String serviceUrl) {
		
    	int statusCode;
    	GetMethod getMethod = null;
        // The URL of the web services server + service name
        String baseUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        
        String fullUrl = baseUrl + serviceUrl; 
        
        InputStream response = null;
        
    	try {
	        synchronized(client) {
	        	getMethod = new GetMethod(fullUrl);
	        	statusCode = client.executeMethod(getMethod);
	        }
	        
	        if (statusCode == 200) {
	        	response = getMethod.getResponseBodyAsStream();
	        } else {
				log.error("Cound not contact the vxws service.  Recieved HTTP status code: " + statusCode);
	        }
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
		}

    	SAXBuilder builder = new SAXBuilder();
    	Document doc = null;
       	try {
    		// Build a JDOM Document from the response 
        	doc = builder.build(response);
       	} catch (JDOMException e) { 
    		log.debug("JDOMException parsing xml response");
    	} catch (IOException e) {
    		log.debug("IOException parsing xml response");
    	}   
    	
    	return doc;
	}
	

	/**
	 * Given a partial URL to a vxws service, constructs the entire URL and POSTS
	 * the given XML to the service, returning the response Document
	 * 
	 * @param serviceUrl partial URL to the particular vxws service
	 * @return XML Document from the vxws response
	 */
	public Document postWebServicesDoc(String serviceUrl) {		
    
		int statusCode;
    	PostMethod postMethod = null;
        // The URL of the web services server + service name
        String baseUrl = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        
        String fullUrl = baseUrl + serviceUrl; 
        
        InputStream response = null;
        
    	try {
	        synchronized(client) {
	        	postMethod = new PostMethod(fullUrl);
	        	statusCode = client.executeMethod(postMethod);
	        }
	        
	        if (statusCode == 200) {
	        	response = postMethod.getResponseBodyAsStream();
	        } else {
				log.error("Cound not contact the vxws service with URL: " + serviceUrl + ".  Recieved HTTP status code: " + statusCode);
	        }
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
		}

    	SAXBuilder builder = new SAXBuilder();
    	Document doc = null;
       	try {
    		// Build a JDOM Document from the response 
        	doc = builder.build(response);
       	} catch (JDOMException e) { 
    		log.debug("JDOMException parsing xml response");
    	} catch (IOException e) {
    		log.debug("IOException parsing xml response");
    	}   
    	
    	return doc;
	}
	
	public GregorianCalendar stringDateToGC (String date) {
		String[] dateComponents = date.substring(0, 10).split("-");
		String[] timeComponents = date.substring(11).split(":");
		
		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
				Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0 
				Integer.parseInt(dateComponents[2]),
				Integer.parseInt(timeComponents[0]),
				Integer.parseInt(timeComponents[1]));    	
		
		return gc;
	}
}

