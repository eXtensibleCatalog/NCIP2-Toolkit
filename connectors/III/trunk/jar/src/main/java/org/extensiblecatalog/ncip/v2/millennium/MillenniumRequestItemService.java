/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.PairGroup;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.StatusString;
import org.extensiblecatalog.ncip.v2.service.*;

import java.util.*;

/**
 * This class implements the Request Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. request id) and responds that the hold
 * was a success.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class MillenniumRequestItemService implements RequestItemService {
	
	private static final Logger LOG = Logger.getLogger(MillenniumRequestItemService.class);
    
    /**
     * Handles a NCIP RequestItem service by returning hard-coded data.
     *
     * @param initData the RequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RequestItemResponseData
     */
    @Override
    public RequestItemResponseData performService(RequestItemInitiationData initData, ServiceContext serviceContext,
                                                  RemoteServiceManager serviceManager) {

        MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
        MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.buildConfiguration();

        final RequestItemResponseData responseData = new RequestItemResponseData();

		final String getAgencyId = MillenniumConfig.getDefaultAgency();
		String[] getAgencyIdList = getAgencyId.split(",");
		
		final String getLDAPUserVarString = MillenniumConfig.getLdapUserVariable();
		String[] getLDAPUserVarList = getLDAPUserVarString.split(",");
		final String getLDAPPasswordVarString = MillenniumConfig.getLdapPasswordVariable();
		String[] getLDAPPasswordVarList = getLDAPPasswordVarString.split(",");
		
		final String getPatronUserVarString = MillenniumConfig.getPatronUserVariable();
		String[] getPatronUserVarList = getPatronUserVarString.split(",");
		final String getPatronPasswordVarString = MillenniumConfig.getPatronPasswordVariable();
		String[] getPatronPasswordVarList = getPatronPasswordVarString.split(",");

        String IIIClassicBaseUrl = MillenniumConfig.getURL();
		String baseUrl = "https://" + IIIClassicBaseUrl;
		//LOG.debug("baseUrl: " + baseUrl);
		
		UserId user = new UserId();
		
		ArrayList<String> authenticatedUserName = new ArrayList<String>();
		ArrayList<String> authenticatedUserPassword = new ArrayList<String>();
			
		ArrayList<PairGroup> testAuthUser = new ArrayList<PairGroup>();
		ArrayList<PairGroup> testAuthPass = new ArrayList<PairGroup>();
			
		ArrayList<PairGroup> authUserName = new ArrayList<PairGroup>();
		ArrayList<PairGroup> authPassword = new ArrayList<PairGroup>();
				
		String fromAgencyId = null;
		boolean existFromAgencyId = false; // Check fromAgencyId on Request HTML
		boolean foundFromAgencyId = false; // check fromAgencyId with value list in Config file
		
		String toAgencyId = null;
		boolean existToAgencyId = false; // Check toAgencyId on Request HTML
		boolean foundToAgencyId = false; // check toAgencyId with value list in Config file

		boolean foundPickupLocation = false;
		
		boolean foundUserId = false;
		boolean foundUserPass = false;
		boolean foundLDAPUser = false;
		boolean foundPatronUser = false;
		String userIdentifierType = null;
		
		List<Problem> problems = null; 

		if (initData.getInitiationHeader().getFromAgencyId().getAgencyId() != null) {
			fromAgencyId = initData.getInitiationHeader().getFromAgencyId().getAgencyId().getValue();
			LOG.debug("RequestItem - fromAgencyId = " + fromAgencyId);
			existFromAgencyId = true;
		} else {
			LOG.debug("RequestItem - agencyId = " + existFromAgencyId);
		}
		
		if (initData.getInitiationHeader().getToAgencyId().getAgencyId() != null) {
			toAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
			LOG.debug("RequestItem - toAgencyId = " + toAgencyId);
			existToAgencyId = true;
		} else {
			LOG.debug("RequestItem - toagencyId = " + existToAgencyId);
		}
		
		//String itemId = initData.getBibliographicId().getBibliographicRecordId().getBibliographicRecordIdentifier();
		String itemId = initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier();
		LOG.debug("RequestItem - itemId = " + itemId);
		
		String pickupLocation = initData.getPickupLocation().getValue();
		LOG.debug("RequestItem - pickupLocation = " + pickupLocation);
		
		GregorianCalendar expiredDateCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		expiredDateCalendar.getTime();
		int getYear = expiredDateCalendar.get(Calendar.YEAR);
		int getMonth = expiredDateCalendar.get(Calendar.MONTH) + 2;
		int getDate = expiredDateCalendar.get(Calendar.DATE);

		if (initData.getPickupExpiryDate() != null) {
			Date expiredDate = initData.getPickupExpiryDate().getTime();
			//LOG.debug("RequestItem - ExpiredDate = " + expiredDate);
			expiredDateCalendar.setTime(expiredDate);
			//LOG.debug("RequestItem - expiredDateCalendar = " + expiredDateCalendar.getTime());
			getYear = expiredDateCalendar.get(Calendar.YEAR);
			getMonth = expiredDateCalendar.get(Calendar.MONTH) + 1;
			getDate = expiredDateCalendar.get(Calendar.DATE);
		}
		
		//LOG.debug("Year/Month/Date - " + getYear + "/" + getMonth + "/" + getDate);
				
		String requestIdString = initData.getRequestId().getRequestIdentifierValue();
		String requestTypeString = initData.getRequestType().getValue();
		String requestScopeTypeString = initData.getRequestScopeType().getValue();
		
		for (int x = 0; x < getAgencyIdList.length; x++) {
			//LOG.debug("LookupItem - AgencyIdList[" + x + "]: " + getAgencyIdList[x].trim());
			if (fromAgencyId != null && fromAgencyId.equals(getAgencyIdList[x].trim())) {
				foundFromAgencyId = true;
			}
			if (toAgencyId != null && toAgencyId.equals(getAgencyIdList[x].trim())) {
				foundToAgencyId = true;
			}
			if (pickupLocation != null && pickupLocation.equals(getAgencyIdList[x].trim())) {
				foundPickupLocation = true;
			}
		}
			
		if ((existFromAgencyId == false && existToAgencyId == false) || 
				((fromAgencyId != null && foundFromAgencyId) && (toAgencyId != null && foundToAgencyId))) {
			LOG.debug("RequestItem - Without AgencyId or AgencyId is validated");
			
			if (foundPickupLocation) {
				if (itemId.length() > 0) {
					//MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
					StatusString lookupPage = millenniumSvcMgr.getItemPage(itemId, "Bib Record");
					if (lookupPage.recordStatus.returnStatus) {
						LOG.debug("RequestItem - Success received lookup item page for bib record: " + itemId);
						
						if (lookupPage.statusValue.contains("<a href=\"/logout")) {
							LOG.debug("RequestItem - User not LOGOUT YET! - Check Logout code");
						}
						
						if (lookupPage.statusValue.contains("No Such Record") == false) {
							if (lookupPage.statusValue.contains("request~" + itemId)) {
								LOG.debug("RequestItem - Item: " + itemId + " can requested.");
                                String SNo = MillenniumConfig.getSearchScope();
								baseUrl = "https://" + IIIClassicBaseUrl + ":443/search~" + SNo + "?/." + itemId + "/." + itemId + "/1%2C1%2C1%2CB/request~" + itemId;
								LOG.debug("RequestItem = baseUrl: " + baseUrl);
								
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
									String requestPage = "";
									// Don't need else statement - simplify else statement. LDAPUsername will take care
									if ((authenticatedUserName.size() == getLDAPUserVarList.length) && (authenticatedUserPassword.size() == getLDAPPasswordVarList.length)) {
										LOG.debug("Requestitem - Try to Login with first Pair of username and password");

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
										/*
										for (int x = 0; x < authUserName.size(); x++) {
											LOG.debug("User Name: " + authUserName.get(x).firstValue + " - " + authUserName.get(x).secondValue);
											LOG.debug("User Password: " + authPassword.get(x).firstValue + " - " + authPassword.get(x).secondValue);
										}
										*/
										//LOG.debug("RequestItem - PickupLocation: " + pickupLocation);
										//LOG.debug("Requestitem - baseUrl: " + baseUrl);
										StatusString getRequestLoginStatus = millenniumSvcMgr.RequestItemLogIn(testAuthUser, testAuthPass, pickupLocation, baseUrl);
										if (getRequestLoginStatus.recordStatus.returnStatus) {
											//LOG.debug("status: " + getRequestLoginStatus.recordStatus.returnMsg);
											if (getRequestLoginStatus.recordStatus.returnMsg.contains("(Handshake-301)")) {
												LOG.debug("RequestItem - LDAP Try Login again with new redirect Url");
												String redirectUrl = "https://" + IIIClassicBaseUrl + getRequestLoginStatus.statusValue;
												//LOG.debug("RequestItem - redirectUrl:" + redirectUrl);
												StatusString getRetrieveRequestLoginStatus = millenniumSvcMgr.RequestItemLogIn(testAuthUser, testAuthPass, pickupLocation, redirectUrl);
												if (getRetrieveRequestLoginStatus.recordStatus.returnStatus) {
													LOG.debug("RequestItem - LDAP redirectUrl - Handshake(301) - OK");
													requestPage = getRetrieveRequestLoginStatus.statusValue;
												}
											} else {
												LOG.debug("RequestItem - LDAP Login Success");
												requestPage = getRequestLoginStatus.statusValue;
											}
											
											LOG.debug("RequestItem - First Pair Username Handshake - OK! - Body.length: " + requestPage.length());
											if (requestPage.contains("<form method=\"post\">Choose one item from the list below")) {
												LOG.debug("RequestItem - LDAP Login Received Page Success!");
												authUserName = testAuthUser;
												authPassword = testAuthPass;
												foundLDAPUser = true;
											} else if (requestPage.contains("name=locx00 id=locx00")){
												LOG.debug("RequestItem - LDAP request page found Location HTML Page");
												StatusString getSecondRequestLoginStatus = millenniumSvcMgr.RequestItemPickLocation (pickupLocation, getYear, getMonth, getDate, baseUrl);
												if (getSecondRequestLoginStatus.recordStatus.returnStatus) {
													requestPage = getSecondRequestLoginStatus.statusValue;
													
													//LOG.debug(requestPage);
													
													if (requestPage.contains("<form method=\"post\">Choose one item from the list below")) {
														LOG.debug("RequestItem - LDAP Received Location Page Success!");
														authUserName = testAuthUser;
														authPassword = testAuthPass;
														foundLDAPUser = true;
													}
												}
											}
										} // Test with LDAP first so don't need to return error here with else statement
									}

									LOG.debug("Requestitem - FoundLDAPUser: " + foundLDAPUser);
									if ((foundLDAPUser == false) && ((authenticatedUserName.size() == getPatronUserVarList.length) && 
											(authenticatedUserPassword.size() == getPatronPasswordVarList.length))) {
										LOG.debug("Requestitem - Try to Login with second Pair of username and password");
										testAuthUser = new ArrayList<PairGroup>();
										testAuthPass = new ArrayList<PairGroup>();
										//authUserName = new ArrayList<PairGroup>();
										//authPassword = new ArrayList<PairGroup>();
										
										for (int x = 0; x < authenticatedUserName.size(); x++) {
											PairGroup userPair = millenniumSvcMgr.setPairGroup(getPatronUserVarList[x], authenticatedUserName.get(x));
											testAuthUser.add(userPair);
										}
			
										for (int y = 0; y < authenticatedUserPassword.size(); y++) {
											PairGroup passPair = millenniumSvcMgr.setPairGroup(getPatronPasswordVarList[y], authenticatedUserPassword.get(y));
											testAuthPass.add(passPair);
										}
										
										//LOG.debug("BaseUrl: " + baseUrl);
										StatusString getRequestLoginStatus = millenniumSvcMgr.RequestItemLogIn (testAuthUser, testAuthPass, pickupLocation, baseUrl);
										
										if (getRequestLoginStatus.recordStatus.returnStatus) {
											//LOG.debug("Patron status: " + getRequestLoginStatus.recordStatus.returnMsg);
											if (getRequestLoginStatus.recordStatus.returnMsg.contains("(Handshake-301)")) {
												LOG.debug("RequestItem - Patron Try Login again with new redirect Url");
												String redirectUrl = "https://" + IIIClassicBaseUrl + getRequestLoginStatus.statusValue;
												//LOG.debug("Patron redirectUrl: " + redirectUrl);
												StatusString getRetrieveRequestLoginStatus = millenniumSvcMgr.RequestItemLogIn(testAuthUser, testAuthPass, pickupLocation, redirectUrl);
												if (getRetrieveRequestLoginStatus.recordStatus.returnStatus) {
													LOG.debug("RequestItem - Patron redirectUrl - Handshake(301) - OK");
													requestPage = getRetrieveRequestLoginStatus.statusValue;
												} 
											} else {
												LOG.debug("RequestItem - Patron Login Success");
												requestPage = getRequestLoginStatus.statusValue;
											}
											
											LOG.debug("RequestItem - Patron Login Success! Body.length: " + requestPage.length());
											if (requestPage.contains("<form method=\"post\">Choose one item from the list below")) {
												LOG.debug("RequestItem - Patron Login Received Page Success!");
												authUserName = testAuthUser;
												authPassword = testAuthPass;
												foundPatronUser = true;
											} else if (requestPage.contains("name=locx00 id=locx00")){
												LOG.debug("RequestItem - Patron request page found Location HTML Page");
												StatusString getSecondRequestLoginStatus = millenniumSvcMgr.RequestItemPickLocation (pickupLocation, getYear, getMonth, getDate, baseUrl);
												if (getSecondRequestLoginStatus.recordStatus.returnStatus) {
													requestPage = getSecondRequestLoginStatus.statusValue;
													
													//LOG.debug(requestPage);
													
													if (requestPage.contains("<form method=\"post\">Choose one item from the list below")) {
														LOG.debug("RequestItem - Patron Received Location Page Success!");
														authUserName = testAuthUser;
														authPassword = testAuthPass;
														foundPatronUser = true;
													}
												} 
											}
										} // if (getRequestLoginStatus.recordStatus.returnStatus) - Don't need report error here
									} 
										
									LOG.debug("foundLDAPUser and foundPatronUser: " + foundLDAPUser + ", " + foundPatronUser);
									if (foundLDAPUser || foundPatronUser) {
										StatusString itemValue = millenniumSvcMgr.getRequestItemValue(requestPage);
										StatusString itemTitle = millenniumSvcMgr.getRequestItemTitle(requestPage);

										if (itemValue.recordStatus.returnStatus) {
											LOG.debug("RequestItem - itemValue: " + itemValue.statusValue);
													
											if (itemTitle.recordStatus.returnStatus) {
															
												StatusString getRequestConfirmLoginStatus = millenniumSvcMgr.RequestItemConfirmLogIn (authUserName, authPassword,
													pickupLocation, getYear, getMonth, getDate, itemValue.statusValue, baseUrl);
												if (getRequestConfirmLoginStatus.recordStatus.returnStatus) {
													LOG.debug("RequestItem - Confirm Login Success! Body.length: " + getRequestConfirmLoginStatus.statusValue.length());
													if (getRequestConfirmLoginStatus.statusValue.contains("Your request for <strong>" + itemTitle.statusValue + "</strong> was successful.")) {
														LOG.debug("RequestItem - itemId: " + itemId + " was successful request!");
														ItemId bibId = new ItemId();
														bibId.setItemIdentifierValue(itemId);
														responseData.setItemId(bibId);

														user.setUserIdentifierValue(authUserName.get(0).secondValue);
														try {
															user.setUserIdentifierType(UserIdentifierType.find(null, userIdentifierType));
														} catch (ServiceException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														responseData.setUserId(user);
																	
											            RequestId requestId = new RequestId();
											            requestId.setRequestIdentifierValue(requestIdString);
											            responseData.setRequestId(requestId);
														            
											            try {
															responseData.setRequestType(RequestType.find(null, requestTypeString));
														} catch (ServiceException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														            
											            try {
											            	responseData.setRequestScopeType(RequestScopeType.find(null, requestScopeTypeString));
											            } catch (ServiceException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														//HoldPickupDate holdDate = new HoldPickupDate();
											            
													} else if (getRequestConfirmLoginStatus.statusValue.contains("Request denied - already on hold for or checked out to you")) {
														LOG.error("RequestItem - Item: " + itemId + " already on hold for or checked out to you.");
														problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.DUPLICATE_REQUEST,
												 			"Request denied - Item already on hold to you", null, "RequestItem - Item: " + itemId + " already on hold for or checked out to you.");
													} else if (getRequestConfirmLoginStatus.statusValue.contains("Need-By Date has already passed")) {
														LOG.error("RequestItem - Item: " + itemId + " has Need-By Date has already passed");
														problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.ITEM_NOT_AVAILABLE_BY_NEED_BEFORE_DATE,
												 			"Need-By Date has already passed", null, "RequestItem - Item: " + itemId + " has Need-By Date has already passed.");
													} else {
														LOG.error("RequestItem - Confirm Login Error! False to login");
														problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
															"User Authentication Failed", null, "Confirm Login Error! False to login");
													}
												} else {
													LOG.error("RequestItem - Confirm Login Error: " + getRequestConfirmLoginStatus.recordStatus.returnMsg);
													problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
											 			"User Authentication Failed", null, getRequestConfirmLoginStatus.recordStatus.returnMsg);
												}	
											} else {
												LOG.error("RequestItem - Error! - " + itemTitle.recordStatus.returnMsg);
												problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
										 			"User Authentication Failed", null, "RequestItem - Error! - " + itemTitle.recordStatus.returnMsg);
											}
									
										} else {
											LOG.error("RequestItem - Error! - " + itemValue.recordStatus.returnMsg);
											problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
									 			"User Authentication Failed", null, "RequestItem - Error! - " + itemValue.recordStatus.returnMsg);
										}
								
									} else {
										LOG.error("RequestItem - Login Error! False to login");
										problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
											"User Authentication Failed", null, "Login Error! False to login");
									} 

								} else {
								 	LOG.error("RequestItem - Username or Password is missing");
								 	problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_AUTHENTICATION_FAILED,
								 			"Error! Username or Password", null, "Username or Password is missing");
								} // if (foundUserId && foundUserPass)

							} else {
								LOG.error("RequestItem - BibliographicRecordIdentifier: " + itemId + " couldn't Requested!"); 
				                problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM, "RequestItem",
					                    null, "RequestItem - BibliographicRecordIdentifier: " + itemId + " couldn't Requested!"); 
							} // if (lookupPage.statusValue.contains("request~" + itemId))
				
						} else {
							LOG.error("RequestItem - BibliographicRecordIdentifier: " + itemId + " not found!"); 
			                problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM, "RequestItem",
				                    null, "BibliographicRecordIdentifier: " + itemId + " not found!"); 
						} // if (lookupPage.statusValue.contains("No Such Record") == false)
				
					} else {
						LOG.error("RequestItem - Error: " + lookupPage.recordStatus.returnMsg);
						problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "RequestItem",
				                null, lookupPage.recordStatus.returnMsg); 
					} // if (lookupPage.recordStatus.returnStatus)
			
				} else {
					LOG.error("BibliographicRecordIdentifier is required."); 
		            problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "RequestItem",
			                null, "BibliographicRecordIdentifier is required."); 
				} // if (itemId.length() > 0)
		
			} else {
				LOG.error("RequestItem - PickupLocation: " + pickupLocation + " not found!");
				problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM, "RequestItem",
	                    null, "Pickup Location: " + pickupLocation + " not found!");
			} // if (foundPickupLocation)
			
		} else {
			String agencyId = null;
			if (foundFromAgencyId == false) {
				agencyId = fromAgencyId;
			} 
			if (foundToAgencyId == false) {
				if (agencyId != null) {
					agencyId = agencyId + ", " + toAgencyId;
				} else {
					agencyId = toAgencyId;
				}
			}
			LOG.error("RequestItem - AgencyId: " + agencyId + " not found!");
			problems = ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM, "RequestItem",
                    null, "AgencyId: " + agencyId + " not found!");
		} // if ((existFromAgencyId == false && existToAgencyId == false)

        if ( problems != null ) {
            responseData.setProblems(problems);
            if (foundLDAPUser || foundPatronUser) {
            	baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
            	millenniumSvcMgr.LogOut(baseUrl); 	
            }
            return responseData;
        }
        else {
        	baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
        	millenniumSvcMgr.LogOut(baseUrl);
           	return responseData;
        }
    }
}
