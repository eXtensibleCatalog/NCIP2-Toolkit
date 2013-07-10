/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class implements the Lookup User service for the Dummy back-end
 * connector. Basically this just calls the DummyRemoteServiceManager to get
 * hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService
 * classes, do not use this class as an example. See the NCIP toolkit Connector
 * developer's documentation for guidance.
 */
public class MillenniumLookupUserService implements LookupUserService {

	Config strConfig = new org.extensiblecatalog.ncip.v2.millennium.Config();

	String IIIClassicBaseUrl = strConfig.getProperty("millenniumUrl");

	String authenticatedUserId = "";

	UserId user = new UserId();

	String finesTable = "";
	
	/**
	 * Construct a DummyRemoteServiceManager; this class is not configurable so
	 * there are no parameters.
	 */
	public MillenniumLookupUserService() {
	}

	/**
	 * User / Password
	 */
	String authenticatedUserName = strConfig.getProperty("tempUser");
	String authenticatedUserPassword = strConfig.getProperty("tempPass");

	/**
	 * HttpClient used for screen scraping the web opac
	 */
	private HttpClient client = new HttpClient(
			new MultiThreadedHttpConnectionManager());

	/**
	 * Handles a NCIP LookupUser service by returning hard-coded data.
	 * 
	 * @param initData
	 *            the LookupUserInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupUserResponseData
	 */
	@Override
	public LookupUserResponseData performService(
			LookupUserInitiationData initData,
			RemoteServiceManager serviceManager) {

		String baseUrl = "https://" + IIIClassicBaseUrl + "/patroninfo%7ES0/";

		final LookupUserResponseData responseData = new LookupUserResponseData();

		String userId = initData.getUserId().getUserIdentifierValue();

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;

		String strSessionId = millenniumSvcMgr.getIIISessionId(baseUrl);

		String redirectedUrl = millenniumSvcMgr.authenticate(
				authenticatedUserName, authenticatedUserPassword, baseUrl,
				strSessionId);

		// if the returned logIntoWebForm isn't null
		if (redirectedUrl != null) {
			// extract the iii id from the URL
			int start = redirectedUrl.indexOf("~S0/");

			// Check to see if we're not redirected to the "top"
			int end = redirectedUrl.indexOf("/top");
			if (end == -1) {
				end = redirectedUrl.indexOf("/items");
			}

			String urid = redirectedUrl.substring(start, end).replace("~S0/",
					"");

			authenticatedUserId = urid;
		}

		boolean getBlockOrTrap = false;
		boolean getAddress = true;
		boolean getVisibleUserID = true;
		boolean getFines = true;
		boolean getHolds = false;
		boolean getLoans = false;
		boolean getRecalls = false;
		boolean getMessages = false;

		// System.out.println("xcLookupUser authID: " + authenticatedUserId);
		// System.out.println("xcLookupUser redirUrl: " + redirectedUrl);

		// setup lookup method
		GetMethod lookupMethod = new GetMethod(redirectedUrl);
		lookupMethod.addRequestHeader("Cookie", "III_SESSION_ID="
				+ strSessionId);

		try {
			try {
				client.executeMethod(lookupMethod);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String html = lookupMethod.getResponseBodyAsString();
			// log.error(html);

			/**
			 * Boolean Values to Return as objects
			 */

			// TODO xcLookupUser: Finish getBlockOrTrap
			// If getBlockOrTrap is true, the returned user’s list of blocks
			// must contain all blocks currently placed on the user represented
			// by the userId field.
			if (getBlockOrTrap) {
				// log.debug("Get Block or Trap");
				System.out.println("Todo: Get Block or Trap");
			}

			// TODO xcLookupUser: Finish getAddres (Temp Address) if possible
			// If getMessages is true, the returned user’s list of messages must
			// contain all messages applying the user represented by the userId

			if (getAddress) {
				// log.debug("Get Address");
				Pattern address = Pattern
						.compile(
								"^<span(.*?)class=\"large\">(?s)(.*?)</form>(?s)(.*?)<p>(?s)(.*?)<br />(?s)(.*?)<p>(?s)(.*?)</span>$",
								Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
				Matcher addressMatch = address.matcher(html);

				addressMatch.find();
				// System.out.println(addressMatch.group(5));

				String strAddress = addressMatch.group(5)
						.replaceAll("<br />", "|").replaceAll("\\n", "")
						.replaceAll("||", "");
				strAddress = strAddress.replace("||", "");

				// returnUser.setAddress(strAddress);
				System.out.println("Found the home address " + strAddress);
				// + " for the patron with patron ID " + patronId)
				// log.debug("Found the home address " + returnUser.getAddress()
				// + " for the patron with patron ID " + patronId);

			}

			// if true, the user's fullName must be set to the correct value for
			// the user represented by the userId field if this value is known.
			if (getVisibleUserID) {
				// log.debug("get Visible User Id");

				// If there were any results, build the full name string
				Pattern visibleId = Pattern
						.compile(
								"^<span(.*?)class=\"large\">(?s)(.*?)</form>(?s)(.*?)<p>(?s)(.*?)<br />(?s)(.*?)<p>(?s)(.*?)</span>$",
								Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
				Matcher visibleIdMatch = visibleId.matcher(html);

				visibleIdMatch.find();
				// System.out.println(visibleIdMatch.group(4));

				// Clean up strName, removing tags
				String strName = visibleIdMatch.group(4)
						.replace("<strong>", "").replace("</strong>", "");

				// set full name to strName, removing any unneded whitespaces
				String fullName = strName.trim();

				// Check to make sure it exists
				if (fullName.length() > 1) {

					// setFullName(fullName);
					System.out.println(fullName);

					// log.debug("Found the user's full name to be "
					// + returnUser.getFullName());
				} else {
					// log
					// .info("Could not find the name information for the patron with patron ID "
					// + patronId);
				}

			}

			// TODO xcLookupUser: Finish getFines
			// if true the user's totalFinesCurrencyCode and totalFines fields
			// must be set to the correct values for the user represented by the
			// userId field if these values are known. In addition, the list of
			// fines must contain NCIPUserFine Objects for all fines the user
			// owes.
			if (getFines) {
				// log.debug("get Fines");
				// https://jasmine.uncc.edu/patroninfo~S0/1205433/overdues
				System.out.println("Get Fines");
				// System.out.println("Auth: " + authenticatedUserId);

				html = millenniumSvcMgr.getFines(authenticatedUserId,
						strSessionId);

				Pattern fines = Pattern
						.compile(
								"^(?s)(.*?)<table(.*?)class=\"patFunc\">(?s)(.*?)</table>(?s)(.*?)$",
								Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);

				Matcher finesMatch = fines.matcher(html);

				if (finesMatch.matches()) {

						finesTable = finesMatch.group(3).toString();				
					
				}
				
				System.out.println(finesTable);
				
				System.out.println("==================");
				
				fines = Pattern.compile("^(?s)(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)</tr>(?s)(.*?)$", Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
				
				finesMatch = fines.matcher(finesTable);
				
				Pattern title = Pattern.compile("^(?s)(.*?)<em>(?s)(.*?)</em></td>(?s)(.*?)$", Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
				
				while (finesMatch.find()) { 
					
					//for (int i=0; i < finesMatch.groupCount(); i++) {
						
						System.out.println(finesMatch.group(2));
						
						Matcher titleMatch = title.matcher(finesMatch.group(2));
						// Grab the title out of the finesMatch string
						System.out.println("=====");
				
						if (titleMatch.matches()) {
							System.out.println(titleMatch.group(2));
						}
						
						System.out.println("=====");
						
					//}
				
				}
				
				System.out.println("==================");

				/*
				 * <table border="1" width="100%" class="patFunc"> <tbody>
				 * 
				 * </tbody> </table>
				 */

				// TODO Parse out fines, and add them to objects
				

			}

			// TODO xcLookupUser: Finish getHolds
			// If getHolds is true, the returned user’s list of holds must
			// contain NCIPUserRequestedItem Objects for all holds and callslips
			// the user represented by the userId field has placed.
			if (getHolds) {
				System.out.println("Todo: get holds");

				/*
				 * NCIPUserRequestedItem item = new
				 * NCIPUserRequestedItem(results .getDate("date_hold_placed"),
				 * // The date the request // was placed null, // The date the
				 * request is expected to become // available newItem); // The
				 * bibliographic ID of the item the // request is for
				 * 
				 * // Add the item to the list of requested items
				 * returnUser.addRequestedItem(item);
				 */
			}
			// If getLoans is true, the returned user’s list of checkedOutItems
			// must contain NCIPUserLoanedItem Objects for all items the user
			// represented by the userId field has checked out.
			if (getLoans) {
				System.out.println("get Loans");

				// Setup Request URL
				String requestUrl = redirectedUrl.substring(0,
						redirectedUrl.lastIndexOf("/"))
						+ "/items";

				// setup getLoans method
				GetMethod getLoansMethod = new GetMethod(requestUrl);
				getLoansMethod.addRequestHeader("Cookie", "III_SESSION_ID="
						+ strSessionId);

				// Grab items page
				try {
					client.executeMethod(getLoansMethod);

					String loansHtml = getLoansMethod.getResponseBodyAsString();
					// Parse out the second page

					// System.out.println(loansHtml);

					// If there were any results, build the full name string
					Pattern loans = Pattern
							.compile(
									"^(?s)(.*?)<table(.*?)class=\"patFunc\">(?s)(.*?)<th(.*?)>(?s)(.*?)</th>(?s)(.*?)</tr>(?s)(.*?)</table>(?s)(.*?)$",
									Pattern.MULTILINE
											+ Pattern.CASE_INSENSITIVE);

					Matcher loansMatch = loans.matcher(loansHtml);

					if (loansMatch.matches()) {
						// System.out.println("Matches");
						// System.out.println(loansMatch.groupCount());

						// for (int i=0; i < loansMatch.groupCount(); i++) {
						// Add to String Buffer
						// System.out.println(loansMatch.group(7));
						// }
						String loansTable = loansMatch.group(7).toString();
						// loansTable = loansTable.replaceAll("\n\n","");

						Pattern loansTwo = Pattern
								.compile(
										"^(.*?)<tr(.*?)class=\"patFuncEntry\">(?s)(.*?)</tr>(?s)(.*?)$",
										Pattern.MULTILINE
												+ Pattern.CASE_INSENSITIVE);

						Matcher loansTwoMatch = loansTwo.matcher(loansTable);

						// initialize variables
						StringBuffer sb = new StringBuffer();
						String lineMatch;
						String strCleanup;

						/*
						 * Pattern loansThree = Pattern.compile(
						 * "^(.*?)value=\"(.*?)\"(.*?)$",
						 * Pattern.CASE_INSENSITIVE);
						 */

						// loop over each iteration
						while (loansTwoMatch.find()) {

							// Replacements to get pipe delmited output
							lineMatch = loansTwoMatch.group(3).replaceAll("\n",
									"");

							lineMatch = lineMatch
									.replaceAll(
											"<td align=\"left\" class=\"patFuncMark\"><input type=\"checkbox\" name=\"renew(.*?)\" value=\"",
											"");
							lineMatch = lineMatch.replaceAll(
									"\"(.*?)href=\"(.*?)&", "'|'");
							lineMatch = lineMatch.replaceAll(
									"</a>(.*?)Barcode\">", "'|'");
							lineMatch = lineMatch.replaceAll(
									"</td>(.*?)patFuncStatus\">", "'|'");
							lineMatch = lineMatch.replaceAll(
									"</td>(.*?)patFuncCallNo\">", "'|'");
							lineMatch = lineMatch.replaceAll("\"> ", "'|'");
							lineMatch = lineMatch.replaceAll(" </td>", "");

							// Append Pipe delimited to string buffer
							sb.append(lineMatch + "\n");

						}
						// Convert to string for output
						strCleanup = sb.toString();

						String aryTest[] = stringToArray(strCleanup, "\n");

						// System.out.println(aryTest[1]);

						// Loop Over Loans
						for (int i = 0; i < aryTest.length; i++) {

							// Split up, and turn into array / assign to object
							String aryVal[] = aryTest[i].split("'|'");

							/*
							 * This is all we can get, itemid, bib, title,
							 * barcode duedate and call number
							 * System.out.println("--" + i);
							 * System.out.print("| item: " + aryVal[0] +
							 * "| bib: b" + aryVal[2] + "| title " + aryVal[4] +
							 * "| barcode: " + aryVal[6] + "| due: " + aryVal[8]
							 * + "| CN: " + aryVal[10]);
							 * 
							 * System.out.println("--");
							 */

							// set variables for loaned object
							String strAgencyId = strConfig
									.getProperty("defaultAgency");/*
																 * configuration
																 * .getProperty(
																 * Constants.
																 * CONFIG_ILS_DEFAULT_AGENCY
																 * );
																 */

							// Start Date Format
							Date dateDue = null; // set to null
							DateFormat dateFormatter = DateFormat
									.getDateInstance(DateFormat.SHORT); // create
							// formatter

							String strDate = aryVal[8].replaceAll("DUE ", "")
									.replace("-", "/"); // format string

							// System.out.println(strDate); // for testing

							// parse the date
							try {
								dateDue = dateFormatter.parse(strDate);
							} catch (ParseException pe) {
								System.out
										.println("LookupUser ERROR: Cannot parse \""
												+ strDate + "\"");
							}

							// add b to the value for the bib
							String bibId = "b" + aryVal[2];

							// Create a new item for each loaned item
							/*
							 * NCIPUserLoanedItem item = new NCIPUserLoanedItem(
							 * dateDue, new NCIPItem(new NCIPAgency(
							 * strAgencyId), bibId));
							 */
							// add object to returnUser
							// returnUser.addLoanedItem(item);

							System.out
									.println("Added the user's loaned item with item ID "
											+ bibId + " Due: " + dateDue);

						}

					} // end matches

					// Error handling
				} catch (HttpException e) {
					e.printStackTrace();
					/*
					 * throw new ILSException("HttpException thrown, exiting.",
					 * Constants.ERROR_INTERNAL_ERROR, new StrictNcipError(true,
					 * "NCIP General Processing Error",
					 * "Temporary Processing Failure", "NCIPMessage", null));
					 */
				} catch (IOException e) {
					e.printStackTrace();
					/*
					 * throw new ILSException("IOException thrown, exiting.",
					 * Constants.ERROR_INTERNAL_ERROR, new StrictNcipError(true,
					 * "NCIP General Processing Error",
					 * "Temporary Processing Failure", "NCIPMessage", null));
					 */
				}

			}

			// TODO xcLookupUser: Finish Recalls
			// If getRecalls is true, the returned user’s list of recalls must
			// contain XCUserRecalledItem Objects for all recalls the user
			// represented by the userId field has placed.
			if (getRecalls) {
				System.out.println("Todo: get Recalls");
			}

			// TODO xcLookupUser: Finish getMessages
			// If getMessages is true, the returned user’s list of messages must
			// contain all messages applying the user represented by the userId.
			if (getMessages) {
				System.out.println("Todo: get Messages");
			}

			// return returnUser;

		} catch (HttpException e) {
			e.printStackTrace();
			/*
			 * throw new ILSException( "HttpException thrown, exiting.",
			 * Constants.ERROR_INTERNAL_ERROR, new StrictNcipError(true,
			 * "NCIP General Processing Error", "Temporary Processing Failure",
			 * "NCIPMessage", null));
			 */
		} catch (IOException e) {
			e.printStackTrace();
			/*
			 * throw new ILSException( "IOException thrown, exiting.",
			 * Constants.ERROR_INTERNAL_ERROR, new StrictNcipError(true,
			 * "NCIP General Processing Error", "Temporary Processing Failure",
			 * "NCIPMessage", null));
			 */
		}

		user.setUserIdentifierValue(userId);
		responseData.setUserId(user);
		// Echo back the same item id that came in
		// responseData.setUserId(initData.getUserId());
		return responseData;
	}

	/***
	 * Converts a delimited string into an array of string tokens.
	 * 
	 * @param String
	 *            [] The 'separator' separated string.
	 * @param String
	 *            The string separator.
	 * @return String A string array of the original tokens.
	 */
	public static final String[] stringToArray(String str, String separators) {
		StringTokenizer tokenizer;
		String[] array = null;
		int count = 0;

		if (str == null)
			return array;

		if (separators == null)
			separators = ",";

		tokenizer = new StringTokenizer(str, separators);
		if ((count = tokenizer.countTokens()) <= 0) {
			return array;
		}

		array = new String[count];

		int ix = 0;
		while (tokenizer.hasMoreTokens()) {
			array[ix] = tokenizer.nextToken();
			ix++;
		}

		return array;
	}
}
