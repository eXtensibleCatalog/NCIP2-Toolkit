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

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the CancelItem service connector. Basically this just calls the MillenniumRemoteServiceManager
 * to get data (e.g. title, call #, etc.) for Checkout item, Fine, or Request Hold item and return collected data back 
 * to response XML 
 */
public class MillenniumCancelRequestItemService implements CancelRequestItemService {

    private static final Logger LOG = Logger.getLogger(MillenniumCancelRequestItemService.class);
    
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
	 * Handles a NCIP CancelItem service by returning new date.
	 * 
	 * @param initData
	 *            the CancelRequestItemInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return CancelItemResponseData
	 */
	@Override
	public CancelRequestItemResponseData performService(CancelRequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) {

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
        MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.buildConfiguration();
		
        String IIIClassicBaseUrl = MillenniumConfig.getURL();

		String baseUrl = "https://" + IIIClassicBaseUrl + "/patroninfo"; //.html~S0";
		//LOG.debug("LookupUser - baseURL: " + baseUrl);
		boolean foundUserId = false;
		boolean foundUserPass = false;
		List<Problem> problems = null;
		final CancelRequestItemResponseData responseData = new CancelRequestItemResponseData();
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
		boolean foundCancel = false;
		
		//for (int x = 0; x < millenniumServicesList.length; x++) {
		//	if (millenniumServicesList[x].trim().equals("CancelItem")) {
		//		foundService = true;
		//	}
		//}
		//if (foundService) {
        final String[] millenniumFunctionsList = MillenniumConfig.getFunctions().split(",");

			for (int x = 0; x < millenniumFunctionsList.length; x++) {
				if (millenniumFunctionsList[x].trim().equals("Cancel")) {
					foundCancel = true;
				} 
			}
			if (foundCancel) {
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
						}
					}						
						
					String itemBarCode = initData.getRequestId().getRequestIdentifierValue();
					LOG.debug("CancelRequestItem - itemBarCode: " + itemBarCode);			
				
					int barCodeIndex = 0;
						if (foundLDAPUser || foundPatronUser) {
						LOG.debug("CancelRequestItem - Login Success!");
						boolean getHoldItems = false;
						String strSessionId = authenticateStatus.sessionId;
						String redirectedUrl = authenticateStatus.url;
						LOG.debug("CancelRequestItem - redirectedUrl: " + redirectedUrl);
						authenticatedUserId = authenticateStatus.userid;
						LOG.debug("CancelRequestItem - authenticatedUserId: " + authenticatedUserId);
						String html = authenticateStatus.html; 
						String pageItem = authenticateStatus.pageItem.toLowerCase().trim();
						//LOG.debug("CancelRequestItem - itempage: " + authenticateStatus.pageItem);
						//LOG.debug("CancelRequestItem - SNo: " + SNo);
                        String SNo = MillenniumConfig.getSearchScope();
						if (pageItem.equals("holds") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/holds")) {
							getHoldItems = true;
						}
						LOG.debug("CancelRequestItem - Found - Hold Items: " + getHoldItems);
							if (getHoldItems) {
							LOG.debug("CancelRequestItem - Found Items Currently Hold");
							StatusString getItemsStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "holds");
							if (getItemsStatus.recordStatus.returnStatus) {
								LOG.debug("CancelRequestItem - Success received hold items page. HTML char: " + getItemsStatus.statusValue.length());
								ArrayList<AuthenticationItemsInfo> itemsHoldList = null;//millenniumSvcMgr.getItemsCheckedOut(authenticatedUserId, strSessionId, "holds");
								UserItemInfo itemsHoldStatus = millenniumSvcMgr.getItemsHolds(getItemsStatus.statusValue, foundCancel);
								if (itemsHoldStatus.recordStatus.returnStatus) {
									LOG.debug("CancelRequestItem - Success received items hold page array information");
									itemsHoldList = itemsHoldStatus.itemsList;
									boolean foundBarCode = false;
									
							    	for (int x=0; x < itemsHoldList.size(); x++) {
							    		if (itemsHoldList.get(x).bRecord.contains(itemBarCode)) {
							    			foundBarCode = true;
							    			barCodeIndex = x;
							    		} 	
							    	}
							    	if (foundBarCode) {
							    		LOG.debug("CancelRequestItem - Found item barcode in items hold page at index: " + barCodeIndex);
							    		LOG.debug("CancelRequestItem - Mark id: " + itemsHoldList.get(barCodeIndex).iMark);
							    		//LOG.debug("CancelRequestItem - Mark Value: " + itemsHoldList.get(barCodeIndex).iMarkValue);
							    		//LOG.debug("CancelRequestItem - right of redirectedUrl: " + strFunction.Rightstr(redirectedUrl, "/"));
							    		String url = null;
							    		if (strFunction.Rightstr(redirectedUrl, "/").equals("holds")) {
							    			url = redirectedUrl;
							    		} else {
							    			url = redirectedUrl.replace(pageItem, "holds");
							    		}
							    		LOG.debug("CancelRequestItem - url: " + url);
							    		PostMethod postMethod = new PostMethod(url);
							    		postMethod.addParameter(itemsHoldList.get(barCodeIndex).iMark.replace("cancel", "loc"), "");
							    		postMethod.addParameter(itemsHoldList.get(barCodeIndex).iMark, "on");
							    		postMethod.addParameter("updateholdssome", "YES");
							    			
							    		StatusString holdStatus = millenniumSvcMgr.LogInWebActionForm(postMethod, strSessionId);
							    		if (holdStatus.recordStatus.returnStatus) {
							    			//LOG.debug("html " + holdStatus.statusValue);
							    			LOG.debug("html lenght: " + holdStatus.statusValue.length());
							    			UserItemInfo cancelItemsHoldStatus = millenniumSvcMgr.getItemsHolds(holdStatus.statusValue, foundCancel);
							    			if (cancelItemsHoldStatus.recordStatus.returnStatus) {
							    				ArrayList<AuthenticationItemsInfo> cancelItemsHoldList = cancelItemsHoldStatus.itemsList;
							    				if (cancelItemsHoldList.size() < itemsHoldList.size()) {
							    					LOG.debug("CancelRequestItem - Success cancel item: " + itemBarCode);
										    		ItemId itemId = new ItemId();
										    		itemId.setItemIdentifierValue(itemBarCode);
										    		responseData.setItemId(itemId);
							    					
							    				} else {
							    					LOG.error("CancelRequestItem - False cancel item: " + itemBarCode);
								    				problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
												 			"Couldn't cancel item", null, "Couldn't cancel item: " + itemBarCode);
							    				}
							    			} else {
							    				LOG.error("CancelRequestItem - Couldn't receive cancel hold item  page!");
							    				problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
											 			"Failed to get Cancel Item page", null, cancelItemsHoldStatus.recordStatus.returnMsg);
							    			}
							    		} else {
						    				LOG.error("CancelRequestItem - Couldn't receive hold item  page!");
						    				//LOG.debug("Error: " + renewStatus.recordStatus.returnMsg);
						    				problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
										 			"Failed to get Cancel Item page", null, holdStatus.recordStatus.returnMsg);
						    			}
							    	} else {
							    		LOG.error("CancelRequestItem - Couldn't find the bRecord: " + itemBarCode + " in Hold Items Page of user: " + authenticatedUserName);
							    		problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
												"BarCode not Found", null, "Couldn't find the bRecord: " + itemBarCode + " in Hold Items Page of user: " + authenticatedUserName);
							    	}

								} else {
									LOG.error("CancelRequestItem - False received hold items page array information");
								 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
								 			"Failed to get Items Array", null, itemsHoldStatus.recordStatus.returnMsg);
								} // if (itemsCheckOutStatus.recordStatus.returnStatus)
							} else {
								LOG.error("CancelRequestItem - False received items page");
								problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
						 			"False received items page", null, getItemsStatus.recordStatus.returnMsg);						
							} // if (getItemsStatus.recordStatus.returnStatus)
								
						} else { 
							LOG.error("CancelRequestItem - Couldn't found Hold Item page");
							problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
					 			"Items page not found", null, "Couldn't found Item page!");
						} // if (getHoldItems)
						
					} else {
						LOG.error("CancelRequestItem - Incorrect User Id or Password!");
						problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"User Authentication Failed", null, "False to login - Incorrect User Id or Password!");
					} // if (foundLDAPUser || foundPatronUser)

				} else {
					LOG.error("User Id or Password is missing!");
				 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"Missing User Id or Password", null, "User Id or Password is missing!");
				}

			} else {
			 	LOG.error("CancelRequestItem - Function CancelItem is not support!");
			 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
			 			"Function is not support", null, "Function is not support!");
			} // if (foundRenew)
			
		//} else {
		// 	LOG.error("CancelRequestItem - Services is not support!");
		// 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
		// 			"Services is not support", null, "Services is not support!");
		//} // if (foundService)
				
        if ( problems != null ) {
        	responseData.setProblems(problems);
        	
            if (foundLDAPUser || foundPatronUser) {
            	baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
            	millenniumSvcMgr.LogOut(baseUrl); 	
            }
        	
        	return responseData;
        }
        else {
        	LOG.debug("CancelItem - Success received all information");
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
