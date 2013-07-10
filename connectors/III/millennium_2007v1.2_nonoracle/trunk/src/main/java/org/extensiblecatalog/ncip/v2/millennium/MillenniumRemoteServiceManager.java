/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.text.StrLookup;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extensiblecatalog.ncip.v2.millennium.Config;

/**
 * ServiceManager is responsible for locating the correct back-end service; for
 * the Dummy back-end there are no services; this class always returns the same
 * hard-coded values. Note: If you're looking for a model of how to code your
 * own ILS's RemoteServiceManager, do not use this class's methods as an
 * example. See the NCIP toolkit Connector developer's documentation for
 * guidance.
 */
public class MillenniumRemoteServiceManager implements RemoteServiceManager {

	Config strConfig = new org.extensiblecatalog.ncip.v2.millennium.Config();

	String IIIClassicBaseUrl = strConfig.getProperty("millenniumUrl");

	int intPort = Integer.parseInt(strConfig.getProperty("millenniumPort"));

	/**
	 * HttpClient used for screen scraping the web opac
	 */
	private HttpClient client = new HttpClient(
			new MultiThreadedHttpConnectionManager());

	/**
	 * Construct a DummyRemoteServiceManager; this class is not configurable so
	 * there are no parameters.
	 */
	public MillenniumRemoteServiceManager() {
	}

	/**
	 * This enumeration represents the Circulation Statuses in this ILS.
	 */
	public enum CircStatus {
		/**
		 * The item is on order, i.e. in acquisitions and not available for
		 * circulation.
		 */
		ON_ORDER,
		/**
		 * The item is on the shelf, i.e. available for circulation.
		 */
		ON_SHELF,
		/**
		 * The item is checked-out, i.e.e not available for circulation,
		 * possibly overdue.
		 */
		CHECKED_OUT,
		/**
		 * The item is in-transit between library locations and not available
		 * for circulation.
		 */
		IN_TRANSIT,
		/**
		 * On the web, electronic resource
		 */
		ON_THE_WEB,
		/**
		 * Non Circulating
		 */
		NONCIRCULATING
	}

	/**
	 * Get the Bib Record Id associated with an item id (barcode).
	 * 
	 * @param itemId
	 *            the item barcode
	 * @return the bibliographic record # in this ILS's database.
	 */
	// TODO Finish this
	public String getBibRecordId(String itemId) {
		return itemId;
	}

	/**
	 * Get the library's name.
	 * 
	 * @return the library name
	 */
	public String getLibraryName() {
		// TODO Get this from configuration
		return strConfig.getProperty("libraryName");
	}

	/**
	 * Get the language code for the bib record (e.g. "eng").
	 * 
	 * @param bibRecordId
	 *            the bib record # in this ILS's database.
	 * @return the language code
	 */
	// TODO Finish this
	public String getBibLanguage(String bibRecordId) {
		return "eng";
	}

	/**
	 * Get the holdings statement for a given item id.
	 * 
	 * @param itemId
	 *            the item id (barcode)
	 * @return the holdings statement
	 */
	// TODO Finish this
	public String getHoldings(String itemId) {
		return "vols. 1-3";
	}

	// TODO Finish this
	public String getRequestId() {
		return "19730184";
	}

	// TODO Finish this
	public String getItemId() {
		return "35556991919116";
	}

	// TODO Finish this
	public String getUserId() {
		return "p1921918933";
	}

	/**
	 * Get the title for the bibliographic entity associated iwth this item id.
	 * 
	 * @param html
	 *            the pages html
	 * @return the title
	 */
	public String getTitle(String html) {
		String strReturn = "";

		// Set Title
		Pattern itemDesc = Pattern
				.compile(
						"^(.*?)<td(.*?)bibInfoLabel(.*?)Title</td>(?s)(.*?)bibInfoData(?s)(.*?)<strong>(.*?)</strong>(?s)(.*?)</td>$",
						Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher itemDescMatch = itemDesc.matcher(html);

		if (itemDescMatch.find()) {
			strReturn = itemDescMatch.group(6);
		}
		// end set title

		return strReturn;
	}

	/**
	 * Get the circulation status of this item.
	 * 
	 * @param itemId
	 *            the item id (barcode)
	 * @return the {@link CircStatus circulation status}
	 */
	public String getCirculationStatus(String html) {
		String strCircStatus = "";

		// Set the circulation Status

		// Search Again
		Pattern circTwo = Pattern.compile("^(.*?)-->&nbsp;(.*?)</td>(.*?)$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);

		Matcher circTwoMatch = circTwo.matcher(html);
		String[] test;
		test = new String[2];
		int i = 0;

		while (circTwoMatch.find()) {

			test[i] = circTwoMatch.group(2);
			i++;
		}

		i = 0;
		while (i < test.length) {
			System.out
					.println("Millennium Availability[" + i + "]: " + test[i]);
			i++;
		}

		strCircStatus = test[1];
		// End getCircStatus
		CircStatus Status = null;

		if (strCircStatus.matches("(.*?)AVAILABLE(.*?)")) {
			Status = CircStatus.ON_SHELF;
		} else if (strCircStatus.matches("DUE(.*?)")) {
			Status = CircStatus.CHECKED_OUT;
		} else if (strCircStatus.matches("ON THE WEB")) {
			Status = CircStatus.ON_THE_WEB;
		} else if (strCircStatus.matches("NONCIRCULATING")) {
			Status = CircStatus.NONCIRCULATING;
		} else {
			Status = CircStatus.CHECKED_OUT;
		}

		return strCircStatus;
	}

	/**
	 * Get the Call number for this item.
	 * 
	 * @param html
	 *            HTML for the page to fetch the CN from
	 * @return the call #
	 */
	public String getCallNo(String html) {
		String strReturn = "";

		// Set call number
		Pattern itemCN = Pattern.compile(
				"^(.*?)field C -->(.*?)>(.*?)</a>(.*?)</td>$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);

		Matcher itemCNMatch = itemCN.matcher(html);

		if (itemCNMatch.find()) {
			strReturn = itemCNMatch.group(3);
		}
		// end set call number
		return strReturn;
	}

	/**
	 * fetches the author from html
	 * 
	 * @param html
	 * @return string
	 */
	public String getAuthor(String html) {
		String strReturn = "";

		Pattern first = Pattern.compile(
				"^(.*?)Author(?s)(.*?)infoData(.*?)<a(.*?)>(.*?)</a>(.*?)$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher m = first.matcher(html);

		// Check all occurrences
		if (m.find()) {
			// Add to String Buffer
			strReturn = m.group(5);
			System.out.println(m.group(5));
		}

		return strReturn;
	}

	/**
	 * Fetches the imprint from html
	 * 
	 * @param html
	 * @return string
	 */
	public String getImprint(String html) {
		String strReturn = "";

		Pattern first = Pattern.compile(
				"^(.*?)Imprint(?s)(.*?)infoData(.*?)>(.*?)</td></tr>$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher m = first.matcher(html);

		// Check all occurrences
		if (m.find()) {
			// Add to String Buffer
			strReturn = m.group(4);
			System.out.println("Imprint: " + m.group(4));
		}

		return strReturn;
	}

	/**
	 * Returns the series out of html
	 * 
	 * @param html
	 * @return string
	 */
	public String getSeries(String html) {

		String strValue = "";

		Pattern first = Pattern.compile(
				"^(.*?)Series(?s)(.*?)infoData(.*?)<a(.*?)>(.*?)</a>(.*?)$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher m = first.matcher(html);

		// Check all occurrences
		if (m.find()) {
			// Add to String Buffer
			strValue = m.group(5);
			System.out.println("Series: " + m.group(5));
		}

		// Escape the publisher from the result set
		strValue = escapeCharacters(strValue);

		// Set Publisher Value

		return strValue;

	}

	/**
	 * Gets the ISBN from the page
	 * 
	 * @param lookupPage
	 *            HTML code of the item
	 * @return
	 */
	public String getIsbn(String lookupPage) {

		return getBibDescription(lookupPage, "ISBN");
	}

	/**
	 * 
	 */
	public String getBibDescription(String lookupPage, String lookupField) {
		// TODO lookupItem: finish getBibDescription

		// X Medium Type
		// log.debug("entering getBibDescription");

		String strReturn = null;
		
		StringBuffer sb = new StringBuffer();

		Pattern first = Pattern.compile(
				"(.*?)bibInfoLabel(.*?)\n(.*?)bibInfoData(.*?)\n(.*?)$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher m = first.matcher(lookupPage);

		// Check all occurrences
		while (m.find()) {

			// Add to String Buffer
			sb.append(m.group() + "\n");
		}

		// Deep Search
		Pattern bibInfoTwo = Pattern
				.compile(
						"^(.*?)bibInfoLabel\">(.*?)</td>\n<td class=\"bibInfoData\">\n(.*?)$",
						Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher bibInfoTwoMatch = bibInfoTwo.matcher(sb);

		// Set to Array key value pairs
		while (bibInfoTwoMatch.find()) {
			// System.out.println(bibInfoTwoMatch.group(2)
			// + " : "
			// + bibInfoTwoMatch.group(3)
			/* .replace("</td></tr>", "") */// );

			// Shorten the bibInfo Match Field and Value
			String strField = bibInfoTwoMatch.group(2);
			String strValue = bibInfoTwoMatch.group(3)
					.replace("</td></tr>", "");

			// System.out.println("Key:" + strField + " : " + strValue);

			// Get the author from the result set item.setAuthor();
			if (strField.equals("Author") && lookupField.equals("Author")) {

				// remove link from Author Field
				// item.setAuthor(strValue.replaceAll("<(.*?)a(.*?)>", ""));

				// if (log.isDebugEnabled())
				// log.debug("The Author was found to be "
				// + item.getAuthor());
				strReturn = "Author";
			}

			// Set the ISBN
			if (strField.equals("ISBN") && lookupField.equals("ISBN")) {
				// Get the ISBN from the result set
				// item.setIsbn(strValue);

				// if (log.isDebugEnabled())
				// log.debug("The ISBN was found to be "
				// + item.getIsbn());
				strReturn = strValue;
			}

			/*
			 * // Get the medium type from the result set item.setMediumType();
			 * 
			 * if(log.isDebugEnabled())
			 * log.debug("The Medium Type was found to be " +
			 * item.getMediumType());
			 */

			// Set the publisher
			if (strField.equals("Imprint") && lookupField.equals("Imprint")) {

				// Escape the publisher from the result set
				// strValue =
				// xc.nciptoolkit.utils.StringUtils.escapeCharacters(strValue);

				// Set Publisher Value
				// item.setPublisher(strValue);

				// if (log.isDebugEnabled())
				// log.debug("The Publisher was found to be: \""
				// + item.getPublisher() + "\"");
				strReturn = "Imprint";
			}

			// Set the series
			if (strField.equals("Series") && lookupField.equals("Series")) {

				strValue = strValue.replaceAll("<(.*?)>", "");
				
				// item.setSeries(xc.nciptoolkit.utils.StringUtils.escapeCharacters(strValue));
				strReturn = org.apache.commons.lang.StringEscapeUtils.escapeXml(strValue);
				// if(log.isDebugEnabled())
				// log.debug("The Series was found to be " +
				// item.getSeries());

			}
			
			// Set the series
			if (strField.equals("Description") && lookupField.equals("mediumType")) {

				strReturn = strValue;
				
			}
			
			// set the Title
			if (strField.equals("Title") && lookupField.equals("Title")) {
				// Get the series from the result set
				// item.setTitle(strValue.replaceAll("<(.*?)>", ""));

				// System.out.println(strValue.replaceAll("<(.*?)>", ""));

				// if (log.isDebugEnabled())
				// log.debug("The Title was found to be: \""
				// + item.getTitle() + "\"");
				// }
				strReturn = "TITLE";
			}

		}
		System.out.println("bibDescription returning: "+strReturn);
		return strReturn;

	} // End getBibDescription

	/**
	 * return the html for an ID
	 * 
	 * @param itemId
	 *            the item id
	 * @return the html for the page
	 * @throws IOException
	 */
	public String getItemPage(String itemId) throws IOException {

		String html = "";

		// Setup Get Method
		String baseUrl = "http://" + IIIClassicBaseUrl;
		String recordUrl = "/record=" + itemId;

		html = doGetPage(baseUrl, recordUrl, intPort);

		// For Debugging
		// System.out.print("===================");
		// System.out.print("Record URL: " +recordUrl);
		// System.out.print("HTML: "+html);
		// System.out.print("===================");

		return html;
	}

	/**
	 * 
	 * @param strHost
	 *            Host Name (including protocol)
	 * @param strTarget
	 *            Page to fetch
	 * @param intPort
	 *            Port Number
	 * @return html html of page (if successful 200)
	 * @throws HttpException
	 * @throws IOException
	 */
	private String doGetPage(String strHost, String strTarget, int intPort)
			throws HttpException, IOException {

		// Setup Variables
		String url = strHost + strTarget;
		String html = "";
		GetMethod getMethod = new GetMethod(url);

		// Execute & return status code
		int statusCode = client.executeMethod(getMethod);

		// If Successful
		if (statusCode == 200) {
			html = getMethod.getResponseBodyAsString();
		}

		return html;
	}

	/**
	 * A helper method to replace special characters for XML
	 * 
	 * @param inputString
	 *            The string to replace the characters of
	 * @return String a string of replaced characters
	 */
	public static String escapeCharacters(String inputString) {
		String returnString = StringEscapeUtils.escapeXml(inputString);

		return returnString;
	}

	/**
	 * Pulls the session id out of the Set-Cookie response header from a GET
	 * request on the given url.
	 * 
	 * @param url
	 *            - the url that the GET request will hit.
	 * @return the session id.
	 */
	public String getIIISessionId(String url) {

		String sessionId = "over 9000!!!";

		HttpClient client = new HttpClient();

		GetMethod getMethod = new GetMethod(url);
		// set cookie policy
		getMethod.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		try {
			client.executeMethod(getMethod);

			Header setCookieHeader = getMethod.getResponseHeader("Set-Cookie");

			String cookieValue = setCookieHeader.getValue();

			if (cookieValue.contains("III_SESSION_ID")) {

				int start = cookieValue.indexOf("III_SESSION_ID") + 15;
				int end = cookieValue.indexOf(";", start);

				sessionId = cookieValue.substring(start, end);
			}

		} catch (HttpException e) {
			// TODO Auto-generated getIIISessionId catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated getIIISessionId catch block
			e.printStackTrace();
		} finally {

			// log.debug("Releasing the HTTP connection to " + url);

			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}

		return sessionId;
	}

	/**
	 * Makes a GET request using the given URL to resolve any HTTP redirects.
	 * 
	 * @param url
	 *            - the base url which may result in a redirect.
	 * @return the corrected url.
	 */
	public String getRedirectedUrl(String url, String sessionId)
	/* throws ILSException */{

		GetMethod getMethod = null;

		String returnedUrl = "";

		// log.error("Original Url: " + url);

		try {
			// log.debug("Loading the page at " + url);

			int statusCode = 0;

			synchronized (client) {
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID="
						+ sessionId);

				statusCode = client.executeMethod(getMethod);

			}

			if (statusCode == 200) {
				/*
				 * log.error("Request headers:"); Header[] ao_headers =
				 * o_get_method.getRequestHeaders(); for( int i = 0; i <
				 * ao_headers.length; i++ ) { log.error( "Header " + i + " is "
				 * + ao_headers[i] ); }
				 * 
				 * log.error("Response headers:"); ao_headers =
				 * o_get_method.getResponseHeaders(); for( int i = 0; i <
				 * ao_headers.length; i++ ) { log.error( "Header " + i + " is "
				 * + ao_headers[i] ); }
				 */

				returnedUrl = getMethod.getURI().toString();

				// log.debug("Successfully loaded " + url);

				// log.error("New URL " + s_returned_url);

			} else // We could not get the page
			{
				// log.error("Could not load " + url + ", status code was "
				// + statusCode);
				System.err.println("Invalid status code " + statusCode
						+ " recieved.");

				/*
				 * throw new ILSException(
				 * "Failed to load the page on III Classic.",
				 * Constants.ERROR_ILS_INTERFACE_ERROR, new
				 * StrictNcipError(true, "NCIP General Processing Error Scheme",
				 * "Temporary Processing Failure", "NCIPMessage", null));
				 */
			}

		}

		catch (IOException e) {
			// log.error("IOException caught while getting " + url, e);

			/*
			 * throw new ILSException(
			 * "An internal error occurred in the NCIP Toolkit.",
			 * Constants.ERROR_INTERNAL_ERROR, new StrictNcipError(true,
			 * "NCIP General Processing Error Scheme",
			 * "Temporary Processing Failure", "NCIPMessage", null));
			 */
		}

		finally {
			// log.debug("Releasing the HTTP connection to " + url);

			if (getMethod != null) {
				getMethod.releaseConnection();
			}

		}

		return returnedUrl;

	}

	public String getFines(String userId, String sessionId) {

		GetMethod getMethod = null;

		String baseUrl = strConfig.getProperty("millenniumUrl");

		String html = null;

		String url = "https://" + baseUrl + "/patroninfo~S0/" + userId
				+ "/overdues";

		try {
			// log.debug("Loading the page at " + url);

			int statusCode = 0;

			synchronized (client) {
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID="
						+ sessionId);

				statusCode = client.executeMethod(getMethod);

				if (statusCode == 200) {
					/*
					 * log.error("Request headers:"); Header[] ao_headers =
					 * o_get_method.getRequestHeaders(); for( int i = 0; i <
					 * ao_headers.length; i++ ) { log.error( "Header " + i +
					 * " is " + ao_headers[i] ); }
					 * 
					 * log.error("Response headers:"); ao_headers =
					 * o_get_method.getResponseHeaders(); for( int i = 0; i <
					 * ao_headers.length; i++ ) { log.error( "Header " + i +
					 * " is " + ao_headers[i] ); }
					 */

					// returnedUrl = getMethod.getURI().toString();

					// log.debug("Successfully loaded " + url);
					System.out.println("Got 200");
					html = getMethod.getResponseBodyAsString();
					// log.error("New URL " + s_returned_url);

				} else // We could not get the page
				{
					// log.error("Could not load " + url + ", status code was "
					// + statusCode);
					System.err.println("Invalid status code " + statusCode
							+ " recieved.");

					/*
					 * throw new ILSException(
					 * "Failed to load the page on III Classic.",
					 * Constants.ERROR_ILS_INTERFACE_ERROR, new
					 * StrictNcipError(true,
					 * "NCIP General Processing Error Scheme",
					 * "Temporary Processing Failure", "NCIPMessage", null));
					 */
				}

			}
		} catch (HttpException e) {
			// TODO Auto-generated getIIISessionId catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated getIIISessionId catch block
			e.printStackTrace();
		} finally {
			// log.debug("Releasing the HTTP connection to " + url);
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}

		return html;
	}

	public String authenticate(String authenticatedUserName,
			String authenticatedUserPassword, String baseUrl,
			String strSessionId) {

		// Print the sessionid
		System.out.println("Session ID: " + strSessionId);

		// setup base URL & variables
		// log.debug("xcLookupUser Session: " + strSessionId);

		// Gets the proper login url
		String redirectUrl = getRedirectedUrl(baseUrl, strSessionId);
		System.out.println("Redirected Url: " + redirectUrl);

		// Set up redirected Url
		String redirectedUrl = null;
		// create post method
		PostMethod loginForm = new PostMethod(redirectUrl);

		// Set up authentication types
		boolean authLDAP = false;
		boolean authPatron = false;

		// Get Authentication Type
		String authType = strConfig.getProperty("authType");

		if (authType.equals("both")) {
			authLDAP = true;
			authPatron = true;
		} else if (authType.equals("ldap")) {
			authLDAP = true;
		} else if (authType.equals("patron")) {
			authPatron = true;
		}

		// ldap authentication
		if (authLDAP) {

			System.out.println("ldap authentication");

			// add username and password from auth data
			loginForm.addParameter("extpatid", authenticatedUserName);
			loginForm.addParameter("extpatpw", authenticatedUserPassword);

			// Gets the patron info url
			redirectedUrl = logIntoWebForm(loginForm, strSessionId);

			// System.out.println("baseUrl: " + baseUrl);
			// System.out.println("Redirected Url 2: " + redirectedUrl);
		}
		// end ldap

		// patron authentication
		if (authPatron) {

			if (redirectedUrl.equals(baseUrl)) {
				System.out.println("Patron Authentication");

				loginForm = null;
				// create post method
				loginForm = new PostMethod(redirectUrl);

				// remove ldap parameters
				loginForm.removeParameter("expatid");
				loginForm.removeParameter("expatpw");

				// add patron
				loginForm.addParameter("name", authenticatedUserName);
				loginForm.addParameter("code", authenticatedUserPassword);

				// Gets the patron info url
				redirectedUrl = logIntoWebForm(loginForm, strSessionId);

				// System.out.println("Redirected Url 3: " + redirectedUrl);
			}
		}
		// End patron authentication

		return redirectedUrl;
	}

	/**
	 * Attempts a login using the given PostMethod by adding the needed form
	 * parameters.
	 * 
	 * @param postMethod
	 *            - the PostMethod with all other needed params already
	 *            inserted.
	 * @return the response as a String.
	 */
	public String logIntoWebForm(PostMethod postMethod, String sessionId) {
		String responseString = "Login incomplete.";
		String url = "not set.";
		String redirectLocation = "unknown.";

		// set the login params

		// postMethod.setParameter("extpatid", authenticatedUserName);
		// postMethod.setParameter("extpatpw", authenticatedUserPassword);
		postMethod.addRequestHeader("Cookie", "III_SESSION_ID=" + sessionId);

		// set other needed params since iii is picky.
		// postMethod.addParameter("name", "");
		// postMethod.addParameter("code", "");

		// postMethod.addParameter("name", authenticatedUserName);
		// postMethod.addParameter("pin", authenticatedUserPassword);

		// postMethod.addParameter("pin", "");
		postMethod.addParameter("submit.x", "0");
		postMethod.addParameter("submit.y", "0");

		// Since login returns a 302, we have to make another GET request after
		// the POST.
		GetMethod getMethod = null;

		try {
			int statusCode = client.executeMethod(postMethod);

			URI uri = postMethod.getURI();
			url = uri.toString();

			// log.error("Login URL is " + url);
			// log.debug("Initial status code is " + statusCode);

			// We're redirecting on the first request
			if (statusCode == 302) {

				System.out.println("in 302");
				redirectLocation = postMethod.getResponseHeader("Location")
						.getValue();
				// log.error("redir loc: " + redirectLocation);

				url = url.substring(0, url.indexOf("/", 10)) + redirectLocation;

				// log.error("Getting from " + url);
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID="
						+ sessionId);

				// statusCode = client.executeMethod( getMethod );
				// log.error("Secondary status code is " + n_status_code);
				responseString = getMethod.getResponseBodyAsString();
				// log.error("302 Response: " + responseString);
			}
			if (statusCode == 200) {
				System.out.println("in 200");
				// s_redirect_location =
				// vo_post_method.getResponseHeader("Location").getValue();
				responseString = postMethod.getResponseBodyAsString();
				// log.error("200 res string: " + responseString);
				// System.out.println("Header Loc: " +
				// postMethod.getResponseHeader("Location").getValue());
				System.out.println("200 res string: " + responseString);
				// url = null;

			} else {

				System.err.println("Something weird happened.");
				responseString = postMethod.getResponseBodyAsString();
				// / log.error("Response: " + responseString);
				// s_redirect_location =
				// vo_post_method.getResponseHeader("Location").getValue();
			}

		} catch (HttpException e1) {
			System.err.println("HttpException recieved.");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.err.println("IOException recieved.");
			e1.printStackTrace();

		} finally {
			// log.debug("Releasing the HTTP connection to " + url);

			if (postMethod != null) {
				postMethod.releaseConnection();
			}

			if (getMethod != null) {
				getMethod.releaseConnection();
			}

		}

		// System.out.println("Returning loginToWebForm");

		// return s_response;
		return url;
	}

}