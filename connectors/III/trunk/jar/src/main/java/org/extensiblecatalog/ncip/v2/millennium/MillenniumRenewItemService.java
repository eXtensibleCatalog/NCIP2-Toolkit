/**
 * Copyright (c) 2012 eXtensible Catalog Organization
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class implements the RenewItem service connector. Basically this just calls the MillenniumRemoteServiceManager
 * to get data (e.g. title, call #, etc.) for Checkout item, Fine, or Request Hold item and return collected data back 
 * to response XML 
 */
public class MillenniumRenewItemService implements RenewItemService {
	
	private static final Logger LOG = Logger.getLogger(MillenniumRenewItemService.class);

	// User / Password
    ArrayList<String> authenticatedUserName = new ArrayList<String>();
	ArrayList<String> authenticatedUserPassword = new ArrayList<String>();
	
	ArrayList<PairGroup> testAuthUser = new ArrayList<PairGroup>();
	ArrayList<PairGroup> testAuthPass = new ArrayList<PairGroup>();
	
	ArrayList<PairGroup> authUserName = new ArrayList<PairGroup>();
	ArrayList<PairGroup> authPassword = new ArrayList<PairGroup>();
	
	StringFunction strFunction = new StringFunction();

	String authenticatedUserId = "";

	UserId user = new UserId();
	
	/**
	 * Handles a NCIP RenewItem service by returning XML data.
	 * 
	 * @param initData
	 *            the LookupUserInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupUserResponseData
	 */
	@Override
	public RenewItemResponseData performService(RenewItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) {

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
        MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.buildConfiguration();

        String IIIClassicBaseUrl = MillenniumConfig.getURL();
		String baseUrl = "https://" + IIIClassicBaseUrl + "/patroninfo"; //.html~S0";
		// LOG.debug("RenewItem - baseURL: " + baseUrl);
		
		boolean foundUserId = false;
		boolean foundUserPass = false;
		List<Problem> problems = null;
		final RenewItemResponseData responseData = new RenewItemResponseData();
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
		boolean foundRenew = false;
		
		//for (int x = 0; x < millenniumServicesList.length; x++) {
		//	if (millenniumServicesList[x].trim().equals("RenewItem")) {
		//		foundService = true;
		//	}
		//}
		
		//if (foundService) {
        final String[] millenniumFunctionsList = MillenniumConfig.getFunctions().split(",");
			for (int x = 0; x < millenniumFunctionsList.length; x++) {
				if (millenniumFunctionsList[x].trim().equals("Renew")) {
					foundRenew = true;
				} 
			}
			if (foundRenew) {
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
					LOG.debug("RenewItem - itemBarCode: " + itemBarCode);			
				
					int barCodeIndex = 0;
						//htmlProperty authenticateStatus = millenniumSvcMgr.Authenticate(authenticatedUserName, authenticatedUserPassword, baseUrl);
				
					if (foundLDAPUser || foundPatronUser) {
						LOG.debug("RenewItem - Login Success!");
						boolean getItems = false;
						String strSessionId = authenticateStatus.sessionId;
						String redirectedUrl = authenticateStatus.url;
						LOG.debug("RenewItem - redirectedUrl: " + redirectedUrl);
						authenticatedUserId = authenticateStatus.userid;
						LOG.debug("RenewItem - authenticatedUserId: " + authenticatedUserId);
						String html = authenticateStatus.html; 
						String pageItem = authenticateStatus.pageItem.toLowerCase().trim();
						//LOG.debug("RenewItem - itempage: " + authenticateStatus.pageItem);
						//LOG.debug("RenewItem - SNo: " + SNo);
                        String SNo = MillenniumConfig.getSearchScope();
						if (pageItem.equals("items") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/items")) {
							getItems = true;
						}
						LOG.debug("RenewItem - Found - Items: " + getItems);
						if (getItems) {
							LOG.debug("RenewItem - Found Items Currently Checked out");
							StatusString getItemsStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "items");
							if (getItemsStatus.recordStatus.returnStatus) {
								LOG.debug("RenewItem - Success received items page. HTML char: " + getItemsStatus.statusValue.length());
								ArrayList<AuthenticationItemsInfo> itemsCheckedOutList = null;//millenniumSvcMgr.getItemsCheckedOut(authenticatedUserId, strSessionId, "items");
								UserItemInfo itemsCheckOutStatus = millenniumSvcMgr.getItemsCheckedOut(getItemsStatus.statusValue, foundRenew); 
								if (itemsCheckOutStatus.recordStatus.returnStatus) {
									LOG.debug("RenewItem - Success received items page array information");
									itemsCheckedOutList = itemsCheckOutStatus.itemsList;
									
									boolean foundBarCode = false;
									
							    	for (int x=0; x < itemsCheckedOutList.size(); x++) {
							    		if (itemsCheckedOutList.get(x).iBarcode.contains(itemBarCode)) {
							    			foundBarCode = true;
							    			barCodeIndex = x;
							    		} 	
							    	}
							    	if (foundBarCode) {
							    		LOG.debug("RenewItem - Found item barcode in items page at index: " + barCodeIndex);
							    		//LOG.debug("RenewItem - Mark id: " + itemsCheckedOutList.get(barCodeIndex).iMark);
							    		//LOG.debug("RenewItem - Mark Value: " + itemsCheckedOutList.get(barCodeIndex).iMarkValue);
							    		String url = null;
							    		if (strFunction.Rightstr(redirectedUrl, "/").equals("items")) {
							    			url = redirectedUrl;
							    		} else {
							    			url = redirectedUrl.replace(pageItem, "items");
							    		}
							    		LOG.debug("RenewItem - url: " + url);	
								    	PostMethod postMethod = new PostMethod(url);
							    		postMethod.addParameter(itemsCheckedOutList.get(barCodeIndex).iMark, itemsCheckedOutList.get(barCodeIndex).iMarkValue);
							    		postMethod.addParameter("renewsome", "YES");
						    			StatusString renewStatus = millenniumSvcMgr.LogInWebActionForm(postMethod, strSessionId);
						    			if (renewStatus.recordStatus.returnStatus) {
						    				//LOG.debug("html lenght: " + renewStatus.statusValue.length());
						    				UserItemInfo itemRenewStatus = millenniumSvcMgr.getItemsCheckedOut(renewStatus.statusValue, foundRenew);
						    				if (itemRenewStatus.recordStatus.returnStatus) {
						    					ArrayList<AuthenticationItemsInfo> renewItemList = itemRenewStatus.itemsList;
						    					if (itemsCheckedOutList.size() == renewItemList.size()) {
						    						LOG.debug("Checkout Status is: " + itemsCheckedOutList.get(barCodeIndex).iStatus);
						    						LOG.debug("Renew Status is: " + renewItemList.get(barCodeIndex).iStatus);
						 
						    						if (itemsCheckedOutList.get(barCodeIndex).iStatus.equals(renewItemList.get(barCodeIndex).iStatus) == false) {
							    						StatusString newDueDateString = millenniumSvcMgr.setRenewSimpleDateFormat(renewItemList.get(barCodeIndex).iStatus);
							    						if (newDueDateString.recordStatus.returnStatus) {
							    							//Date dueDate = null; // set to null
							    							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
							    							try {
							    								Date dueDate = dateFormat.parse(newDueDateString.statusValue.trim().replace("-", "/"));
							    								LOG.debug("RenewItem - NewDueDate: " + dueDate);
																//Date newDueDate = millenniumSvcMgr.setRenewSimpleDateFormat(renewItemList.get(barCodeIndex).iStatus);
																GregorianCalendar DueDateCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
																DueDateCalendar.setTime(dueDate);
									    						responseData.setDateDue(DueDateCalendar);
									    						
													    		ItemId itemId = new ItemId();
													    		itemId.setItemIdentifierValue(itemBarCode);
													    		responseData.setItemId(itemId);
													    		
									    						String renewalCount = millenniumSvcMgr.getRenewalCount(renewItemList.get(barCodeIndex).iStatus);
									    						responseData.setRenewalCount(new BigDecimal(renewalCount));
													    		
							    							} catch (ParseException pe) {
							    								LOG.error("setRenewSimpleDateFormat ERROR: Cannot parse \""	+ newDueDateString.statusValue + "\"");
									    						problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE,
															 			"RenewItem Error", null, "Date format error for: " + itemBarCode);
							    							}
							    						} else {
							    							LOG.error("RenewItem - " + newDueDateString.statusValue.trim() + " for Item Barcode: " + itemBarCode + ".");
							    							String errorMsg = newDueDateString.statusValue.trim() + " for Item Barcode '" + itemBarCode + "'.";
								    						problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE,
														 			"Item maybe renew with error!", null, errorMsg);
							    						}						    							
						    						} else {
						    							LOG.error("RenewItem - Couldn't Renew Item for Item Barcode: " + itemBarCode);
							    						problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE,
													 			"RenewItem Error", null, "Couldn't Renew Item for Item Barcode: " + itemBarCode);
						    						}
							    				} else {
						    						LOG.error("RenewItem - Renew Item array list not match");
						    						problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.UNKNOWN_ITEM,
												 			"Item Array not match", null, "Renew Item array list not match");
						    					} // if (itemsCheckedOutList.size() == renewItemList.size())
						    					
						    				} else {
						    					LOG.error("RenewItem - False received renew items page array information");
						    					problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
											 			"Failed to get Items Array", null, itemRenewStatus.recordStatus.returnMsg);
						    				} // if (itemRenewStatus.recordStatus.returnStatus)
							    				
						    			} else {
						    				LOG.error("RenewItem - Couldn't receive Renewitem  page!");
						    				//LOG.debug ("Error: " + renewStatus.recordStatus.returnMsg);
						    				problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
										 			"Failed to get Renew Confirmation", null, renewStatus.recordStatus.returnMsg);
						    			} // if (renewStatus.recordStatus.returnStatus)
			
							    	} else {
							    		LOG.error("RenewItem - Couldn't find the Barcode: " + itemBarCode + " in Items Page of user: " + authenticatedUserName);
							    		problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_CHECKED_OUT,
								 			"BarCode not Found", null, "Couldn't find the Barcode: " + itemBarCode + " in Items Page of user: " + authenticatedUserName);
							    	}

								} else {
									LOG.error("RenewItem - False received checkout items page array information");
								 	problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
								 			"Failed to get Items Array", null, itemsCheckOutStatus.recordStatus.returnMsg);
								} // if (foundLDAPUser || foundPatronUser)
							} else {
								LOG.error("RenewItem - False received items page");
								problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
							 			"False received items page", null, getItemsStatus.recordStatus.returnMsg);						
							} // if (getItemsStatus.recordStatus.returnStatus)
						} else { // if (getItems)
							LOG.error("RenewItem - Couldn't found Item page");
							problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
					 			"Items page not found", null, "Couldn't found Item page!");
						}
					} else {
						LOG.error("RenewItem - Incorrect User Id or Password!");
						problems = ServiceHelper.generateProblems(Version1RenewItemProcessingError.USER_AUTHENTICATION_FAILED,
				 			"User Authentication Failed", null, "False to login - Incorrect Username or Password!");
					}

				} else {
					LOG.error("User Id or Password is missing!");
					problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"Missing User Id or Password", null, "User Id or Password is missing!");
				}		
				
			} else {
			 	LOG.error("RenewItem - Function Renew is not support!");
			 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
			 			"Function is not support", null, "Function is not support!");
			} // if (foundRenew)
			
		//} else {
		// 	LOG.error("RenewItem - Services is not support!");
		// 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
		// 			"Services is not support", null, "Services is not support!");
		//} // if (foundService)
		
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
        	LOG.debug("RenewItem - Success received all information");
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
