/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.*;
import org.extensiblecatalog.ncip.v2.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class implements the Check Out Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. due date).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class MillenniumCheckOutItemService implements CheckOutItemService {

	private static final Logger LOG = Logger.getLogger(MillenniumRemoteServiceManager.class); 

	// User / Password
    ArrayList<String> authenticatedUserName = new ArrayList<String>();
	ArrayList<String> authenticatedUserPassword = new ArrayList<String>();
	
	ArrayList<PairGroup> testAuthUser = new ArrayList<PairGroup>();
	ArrayList<PairGroup> testAuthPass = new ArrayList<PairGroup>();
	
	ArrayList<PairGroup> authUserName = new ArrayList<PairGroup>();
	ArrayList<PairGroup> authPassword = new ArrayList<PairGroup>();
	
	StringFunction strFunction = new StringFunction();

	//final String getMillenniumServices = MillenniumConfig.getServices();
	//String[] millenniumServicesList = getMillenniumServices.split(",");
	
	//final String getMillenniumFunctions = MillenniumConfig.getFunctions();
	//String[] millenniumFunctionsList = getMillenniumFunctions.split(",");
	
	String authenticatedUserId = "";

	UserId user = new UserId();
    
    /**
     * Handles a NCIP CheckOutItem service by returning hard-coded data.
     *
     * @param initData       the CheckOutItemInitiationData
     * @param serviceManager provides access to remote services
     * @return CheckOutItemResponseData
     */
    @Override
    public CheckOutItemResponseData performService(CheckOutItemInitiationData initData, ServiceContext serviceContext,
                                                   RemoteServiceManager serviceManager) {

    	
        final CheckOutItemResponseData responseData = new CheckOutItemResponseData();

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
		MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.getConfiguration();
        String IIIClassicBaseUrl = MillenniumConfig.getURL();
        String SNo = MillenniumConfig.getSearchScope();
		String baseUrl = "https://" + IIIClassicBaseUrl + "/patroninfo"; //.html~S0";
		//LOG.debug("CheckOut - baseURL: " + baseUrl);
		
		boolean foundUserId = false;
		boolean foundUserPass = false;
		List<Problem> problems = null;

		String userIdentifierType = null;
	
		final String getLDAPUserVarString = MillenniumConfig.getLdapUserVariable();
		String[] getLDAPUserVarList = getLDAPUserVarString.split(",");
		final String getLDAPPasswordVarString = MillenniumConfig.getLdapPasswordVariable();
		String[] getLDAPPasswordVarList = getLDAPPasswordVarString.split(",");
		
		final String getPatronUserVarString = MillenniumConfig.getPatronUserVariable();
		String[] getPatronUserVarList = getPatronUserVarString.split(",");
		final String getPatronPasswordVarString = MillenniumConfig.getPatronPasswordVariable();
		String[] getPatronPasswordVarList = getPatronPasswordVarString.split(",");
		
		boolean foundLDAPUser = false;
		boolean foundPatronUser = false;
		
		//boolean foundService = false;
		boolean foundCheckOut = false;
		
		//for (int x = 0; x < millenniumServicesList.length; x++) {
		//	if (millenniumServicesList[x].trim().equals("CheckOut")) {
		//		foundService = true;
		//	}
		//}
		
		//if (foundService) {
			//for (int x = 0; x < millenniumFunctionsList.length; x++) {
			//	if (millenniumFunctionsList[x].trim().equals("CheckOut")) {
			//		foundCheckOut = true;
			//	} 
			//}
			//if (foundCheckOut) {
				authenticatedUserName = new ArrayList<String>();
				authenticatedUserPassword = new ArrayList<String>();
				for (AuthenticationInput authenticationInput : initData.getAuthenticationInputs()) {
					//LOG.debug("authenticationInput: " + authenticationInput.getAuthenticationInputData());
					//LOG.debug("Type: " + authenticationInput.getAuthenticationInputType().getValue());
					if ((authenticationInput.getAuthenticationInputType().getValue().toUpperCase().equals("LDAPUSERNAME")) ||
							(authenticationInput.getAuthenticationInputType().getValue().toUpperCase().equals("USERNAME"))) {
						userIdentifierType = authenticationInput.getAuthenticationInputType().getValue();
						LOG.debug("RequestItem - userIdentifierType: " + userIdentifierType);
						if (authenticationInput.getAuthenticationInputData().trim().length() > 0) {
							authenticatedUserName.add(authenticationInput.getAuthenticationInputData());
							foundUserId = true;	
						}
					}
					if ((authenticationInput.getAuthenticationInputType().getValue().toUpperCase().equals("LDAPPASSWORD")) ||
							(authenticationInput.getAuthenticationInputType().getValue().toUpperCase().equals("PASSWORD")) || 
							(authenticationInput.getAuthenticationInputType().getValue().toUpperCase().equals("PIN"))) {
						if (authenticationInput.getAuthenticationInputData().trim().length() > 0) {
							authenticatedUserPassword.add(authenticationInput.getAuthenticationInputData());
							foundUserPass = true;					
						}
					}
				}
				//LOG.debug("foundUser and foundPass: " + foundUserId + ", " + foundUserPass);
				if (foundUserId && foundUserPass) { 
					//LOG.debug("1 - User: " + authenticatedUserName.size() + "=" + getLDAPUserVarList.length + "Pass: " + authenticatedUserPassword.size() + "=" + getLDAPPasswordVarList.length);
					testAuthUser = new ArrayList<PairGroup>();
					testAuthPass = new ArrayList<PairGroup>();
					authUserName = new ArrayList<PairGroup>();
					authPassword = new ArrayList<PairGroup>();

					htmlProperty authenticateStatus = null;
					if ((authenticatedUserName.size() == getLDAPUserVarList.length) && (authenticatedUserPassword.size() == getLDAPPasswordVarList.length)) {
						for (int x = 0; x < authenticatedUserName.size(); x++) {
							//LOG.debug("User pair: " + getLDAPUserVarList[x] + ", " + authenticatedUserName.get(x));
							PairGroup userPair = millenniumSvcMgr.setPairGroup(getLDAPUserVarList[x], authenticatedUserName.get(x));	
							testAuthUser.add(userPair);
						}

						for (int y = 0; y < authenticatedUserPassword.size(); y++) {
							//LOG.debug("pass pair: " + getLDAPPasswordVarList[y] + ", " + authenticatedUserPassword.get(y));
							PairGroup passPair = millenniumSvcMgr.setPairGroup(getLDAPPasswordVarList[y], authenticatedUserPassword.get(y));
							testAuthPass.add(passPair);
						}
						authenticateStatus = millenniumSvcMgr.Authenticate(testAuthUser, testAuthPass, baseUrl);
						if (authenticateStatus.recordStatus.returnStatus) {
							authUserName = testAuthUser;
							authPassword = testAuthPass;
							foundLDAPUser = true;
							LOG.debug("LDAP authUserName.size() =" + authUserName.size());
						}
					}	
					//LOG.debug("2 - FoundLDAPUser: " + foundLDAPUser);
					if ((foundLDAPUser == false) && ((authenticatedUserName.size() == getPatronUserVarList.length) && (authenticatedUserPassword.size() == getPatronPasswordVarList.length))) {
						testAuthUser = new ArrayList<PairGroup>();
						testAuthPass = new ArrayList<PairGroup>();
						for (int x = 0; x < authenticatedUserName.size(); x++) {
							PairGroup userPair = millenniumSvcMgr.setPairGroup(getPatronUserVarList[x], authenticatedUserName.get(x));
							testAuthUser.add(userPair);
						}
						
						for (int y = 0; y < authenticatedUserPassword.size(); y++) {
							PairGroup passPair = millenniumSvcMgr.setPairGroup(getPatronPasswordVarList[y], authenticatedUserPassword.get(y));
							testAuthPass.add(passPair);
						}
						authenticateStatus = millenniumSvcMgr.Authenticate(testAuthUser, testAuthPass, baseUrl);
						if (authenticateStatus.recordStatus.returnStatus) {
							authUserName = testAuthUser;
							authPassword = testAuthPass;
							foundPatronUser = true;
							LOG.debug("Patron authUserName.size() =" + authUserName.size());
						}
					}
					
					String itemBarCode = initData.getItemId().getItemIdentifierValue();
					LOG.debug("CheckOut - itemBarCode: " + itemBarCode);			
				
					int barCodeIndex = 0;
						//htmlProperty authenticateStatus = millenniumSvcMgr.Authenticate(authenticatedUserName, authenticatedUserPassword, baseUrl);
				
					if (foundLDAPUser || foundPatronUser) {
						LOG.debug("CheckOut - Login Success!");
						boolean getItems = false;
						String strSessionId = authenticateStatus.sessionId;
						String redirectedUrl = authenticateStatus.url;
						LOG.debug("CheckOut - redirectedUrl: " + redirectedUrl);
						authenticatedUserId = authenticateStatus.userid;
						LOG.debug("CheckOut - authenticatedUserId: " + authenticatedUserId);
						String html = authenticateStatus.html; 
						String pageItem = authenticateStatus.pageItem.toLowerCase().trim();
						//LOG.debug("CheckOut - itempage: " + authenticateStatus.pageItem);
						//LOG.debug("CheckOut - SNo: " + SNo);
						if (pageItem.equals("items") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/items")) {
							getItems = true;
						}
						LOG.debug("CheckOut - Found - Items: " + getItems);
						if (getItems) {
							LOG.debug("CheckOut - Found Items Currently Checked out");
							StatusString getItemsStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "items");
							if (getItemsStatus.recordStatus.returnStatus) {
								LOG.debug("CheckOut - Success received items page. HTML char: " + getItemsStatus.statusValue.length());
								ArrayList<AuthenticationItemsInfo> itemsCheckedOutList = null;//millenniumSvcMgr.getItemsCheckedOut(authenticatedUserId, strSessionId, "items");
								UserItemInfo itemsCheckOutStatus = millenniumSvcMgr.getItemsCheckedOut(getItemsStatus.statusValue, foundCheckOut); 
								if (itemsCheckOutStatus.recordStatus.returnStatus) {
									LOG.debug("CheckOut - Success received items page array information");
									itemsCheckedOutList = itemsCheckOutStatus.itemsList;
									
									boolean foundBarCode = false;
									
							    	for (int x=0; x < itemsCheckedOutList.size(); x++) {
							    		if (itemsCheckedOutList.get(x).iBarcode.contains(itemBarCode)) {
							    			foundBarCode = true;
							    			barCodeIndex = x;
							    		} 	
							    	}
							    	if (foundBarCode) {
							    		LOG.debug("CheckOut - Found item barcode in items page at index: " + barCodeIndex);
							    		//LOG.debug("CheckOut - Mark id: " + itemsCheckedOutList.get(barCodeIndex).iMark);
							    		//LOG.debug("CheckOut - Mark Value: " + itemsCheckedOutList.get(barCodeIndex).iMarkValue);
							    		String url = null;
							    		if (strFunction.Rightstr(redirectedUrl, "/").equals("items")) {
							    			url = redirectedUrl;
							    		} else {
							    			url = redirectedUrl.replace(pageItem, "items");
							    		}
							    		LOG.debug("CheckOut - url: " + url);	
								    	PostMethod postMethod = new PostMethod(url);
							    		postMethod.addParameter(itemsCheckedOutList.get(barCodeIndex).iMark, itemsCheckedOutList.get(barCodeIndex).iMarkValue);
							    		postMethod.addParameter("renewsome", "YES");
						    			StatusString renewStatus = millenniumSvcMgr.LogInWebActionForm(postMethod, strSessionId);
						    			if (renewStatus.recordStatus.returnStatus) {
						    				//LOG.debug("html lenght: " + renewStatus.statusValue.length());
						    				UserItemInfo itemRenewStatus = millenniumSvcMgr.getItemsCheckedOut(renewStatus.statusValue, foundCheckOut);
						    				if (itemRenewStatus.recordStatus.returnStatus) {
						    					ArrayList<AuthenticationItemsInfo> renewItemList = itemRenewStatus.itemsList;
						    					if (itemsCheckedOutList.size() == renewItemList.size()) {
						    						LOG.debug("Checkout Status is: " + itemsCheckedOutList.get(barCodeIndex).iStatus);
						    						LOG.debug("Checkout Status is: " + renewItemList.get(barCodeIndex).iStatus);
						 
						    						if (itemsCheckedOutList.get(barCodeIndex).iStatus.equals(renewItemList.get(barCodeIndex).iStatus) == false) {
							    						StatusString newDueDateString = millenniumSvcMgr.setRenewSimpleDateFormat(renewItemList.get(barCodeIndex).iStatus);
							    						if (newDueDateString.recordStatus.returnStatus) {
							    							//Date dueDate = null; // set to null
							    							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
							    							try {
							    								Date dueDate = dateFormat.parse(newDueDateString.statusValue.trim().replace("-", "/"));
							    								LOG.debug("CheckOut - NewDueDate: " + dueDate);
																//Date newDueDate = millenniumSvcMgr.setRenewSimpleDateFormat(renewItemList.get(barCodeIndex).iStatus);
																GregorianCalendar DueDateCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
																DueDateCalendar.setTime(dueDate);
									    						responseData.setDateDue(DueDateCalendar);
									    						
													    		ItemId itemId = new ItemId();
													    		itemId.setItemIdentifierValue(itemBarCode);
													    		responseData.setItemId(itemId);
													    		
							    							} catch (ParseException pe) {
							    								LOG.error("setRenewSimpleDateFormat ERROR: Cannot parse \""	+ newDueDateString.statusValue + "\"");
									    						problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
															 			"CheckOut Error", null, "Date format error for: " + itemBarCode);
							    							}
							    						} else {
							    							LOG.error("CheckOut - " + newDueDateString.statusValue.trim() + " for Item Barcode: " + itemBarCode + ".");
							    							String errorMsg = newDueDateString.statusValue.trim() + " for Item Barcode '" + itemBarCode + "'.";
								    						problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
														 			"CheckOut Error", null, errorMsg);
							    						}						    							
						    						} else {
						    							LOG.error("CheckOut - Couldn't Renew Item for Item Barcode: " + itemBarCode);
							    						problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
													 			"CheckOut Error", null, "Couldn't Renew Item for Item Barcode: " + itemBarCode);
						    						}
							    					} else {
						    						LOG.error("CheckOut - Renew Item array list not match");
						    						problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
												 			"Item Array not match", null, "Renew Item array list not match");
						    					} // if (itemsCheckedOutList.size() == renewItemList.size())
						    					
						    				} else {
						    					LOG.error("CheckOut - False received renew items page array information");
						    					problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
											 			"Failed to get Items Array", null, itemRenewStatus.recordStatus.returnMsg);
						    				} // if (itemRenewStatus.recordStatus.returnStatus)
							    				
						    			} else {
						    				LOG.error("CheckOut - Couldn't receive Renewitem  page!");
						    				//LOG.debug ("Error: " + renewStatus.recordStatus.returnMsg);
						    				problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
										 			"Failed to get Renew Confirmation", null, renewStatus.recordStatus.returnMsg);
						    			} // if (renewStatus.recordStatus.returnStatus)
			
							    	} else {
							    		LOG.error("CheckOut - Couldn't find the Barcode: " + itemBarCode + " in Items Page of user: " + authenticatedUserName);
							    		problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
								 			"BarCode not Found", null, "Couldn't find the Barcode: " + itemBarCode + " in Items Page of user: " + authenticatedUserName);
							    	}

								} else {
									LOG.error("CheckOut - False received checkout items page array information");
								 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
								 			"Failed to get Items Array", null, itemsCheckOutStatus.recordStatus.returnMsg);
								} // if (foundLDAPUser || foundPatronUser)
							} else {
								LOG.error("CheckOut - False received items page");
								problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
							 			"False received items page", null, getItemsStatus.recordStatus.returnMsg);						
							} // if (getItemsStatus.recordStatus.returnStatus)
						} else { // if (getItems)
							LOG.error("CheckOut - Couldn't found Item page");
							problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
					 			"Items page not found", null, "Couldn't found Item page!");
						}
					} else {
						LOG.error("CheckOut - Incorrect User Id or Password!");
						problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"User Authentication Failed", null, "False to login - Incorrect User Id or Password!");
					}

				} else {
					LOG.error("User Id or Password is missing!");
					problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"Missing User Id or Password", null, "User Id or Password is missing!");
				}		
				
	//		} else {
	//		 	LOG.error("CheckOut - Function Renew is not support!");
	//		 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
	//		 			"Function is not support", null, "Function is not support!");
	//		} // if (foundRenew)
			
	//	} else {
	//	 	LOG.error("CheckOut - Services is not support!");
	//	 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
	//	 			"Services is not support", null, "Services is not support!");
	//	} // if (foundService)
		
        if ( problems != null ) {
        	//LOG.debug("main 3 - " + problems.size());
         	responseData.setProblems(problems);
         	
            if (foundLDAPUser || foundPatronUser) {
            	baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
            	millenniumSvcMgr.LogOut(baseUrl); 	
            }
         	
        	return responseData;
        }
        else {
        	LOG.debug("CheckOut - Success received all information");
        	user.setUserIdentifierValue(authUserName.get(0).secondValue);
			try {
				user.setUserIdentifierType(UserIdentifierType.find(null, userIdentifierType));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			responseData.setUserId(user);
        	
			baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
        	millenniumSvcMgr.LogOut(baseUrl);
        	
        	return responseData;
        }
	}
}
