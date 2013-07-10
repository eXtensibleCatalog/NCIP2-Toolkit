/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.*;
import org.extensiblecatalog.ncip.v2.service.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class implements the Lookup User service for the MillenniumLookupUserService back-end connector. Basically this 
 * just calls the MillenniumRemoteServiceManager to get data (e.g. title, call #, etc.) for Checkout item, Fine, or 
 * Request Hold item and return collected data back to response XML 
 */
public class MillenniumLookupUserService implements LookupUserService {
	
    private static final Logger LOG = Logger.getLogger(MillenniumLookupUserService.class);
    
	// User/Password
    ArrayList<String> authenticatedUserName = new ArrayList<String>();
	ArrayList<String> authenticatedUserPassword = new ArrayList<String>();
	
	ArrayList<PairGroup> testAuthUser = new ArrayList<PairGroup>();
	ArrayList<PairGroup> testAuthPass = new ArrayList<PairGroup>();
	
	ArrayList<PairGroup> authUserName = new ArrayList<PairGroup>();
	ArrayList<PairGroup> authPassword = new ArrayList<PairGroup>();
	
	String authenticatedUserId = "";
	UserId user = new UserId();

	/**
	 * LookupUserResponseData
	 *       Handles a NCIP LookupUser service by calls the MillenniumRemoteServiceManager to get data (e.g. title,
	 *       call #, etc.) for Checkout item, Fine, or Request Hold item and return collected data back to response XML
	 * 
	 * @param initData
	 *            the LookupUserInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupUserResponseData
	 */
	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) {

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
        MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.getConfiguration();

        String IIIClassicBaseUrl = MillenniumConfig.getURL();
		String baseUrl = "https://" + IIIClassicBaseUrl + "/patroninfo"; //.html~S0";
		//LOG.debug("LookupUser - baseUrl: " + baseUrl);
		
		List<Problem> problems = null;
		final LookupUserResponseData responseData = new LookupUserResponseData();
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
		boolean foundCancel = false;

		//for (int x = 0; x < millenniumServicesList.length; x++) {
		//	if (millenniumServicesList[x].trim().equals("LookupUser")) {
		//		foundService = true;
		//	}
		//}
		
		//if (foundService) {
        final String[] millenniumFunctionsList = MillenniumConfig.getFunctions().split(",");

			for (int x = 0; x < millenniumFunctionsList.length; x++) {
				if (millenniumFunctionsList[x].trim().equals("Renew")) {
					foundRenew = true;
				} else if (millenniumFunctionsList[x].trim().equals("Cancel")) {
					foundCancel = true;
				} 
			}
						
			boolean foundUserId = false;
			boolean foundUserPass = false;

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
			LOG.debug("LookupUser - (foundUser, foundPass) = (" + foundUserId + ", " + foundUserPass + ")");
			if (foundUserId && foundUserPass) {
				//LOG.debug("1 - User: " + authenticatedUserName.size() + "=" + getLDAPUserVarList.length + "Pass: " + authenticatedUserPassword.size() + "=" + getLDAPPasswordVarList.length);
				testAuthUser = new ArrayList<PairGroup>();
				testAuthPass = new ArrayList<PairGroup>();
				authUserName = new ArrayList<PairGroup>();
				authPassword = new ArrayList<PairGroup>();
				//String requestPage = "";
				
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
					
				if (foundLDAPUser || foundPatronUser) {
					//UserOptionalFields userOptionalFields = new UserOptionalFields();

					LOG.debug("LookupUser - Login Success!");
					boolean getBlockOrTrap = false;
					boolean getRecalls = false;
					boolean getMessages = false;
					boolean getItems = false;
					boolean getHolds = false;
					boolean getOverdues = false;
				    String strSessionId = authenticateStatus.sessionId;
				    String redirectedUrl = authenticateStatus.url;
					LOG.debug("LookupUser - redirectedUrl: " + redirectedUrl);
					authenticatedUserId = authenticateStatus.userid;
					LOG.debug("LookupUser - authenticatedUserId: " + authenticatedUserId);
					String html = authenticateStatus.html; //New at 12/09/2011
					String pageItem = authenticateStatus.pageItem.toLowerCase().trim();
					//LOG.debug("LookupUser - itempage: " + authenticateStatus.pageItem);
					//LOG.debug("LookupUser - SNo: " + SNo);
                    String SNo = MillenniumConfig.getSearchScope();
					if (pageItem.equals("items") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/items")) {
						getItems = true;
					}
					if (pageItem.equals("holds") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/holds")) {
						getHolds = true;
					}
					if (pageItem.equals("overdues") || html.contains("<a href=\"/patroninfo~" + SNo + "/" + authenticatedUserId + "/overdues")) {
						getOverdues = true;
					}
					LOG.debug("LookupUser - Found - Items: " + getItems + ". Holds: " + getHolds + ". Overdues: " + getOverdues);
					if (getItems) {
						LOG.debug("LookupUser - Found Items Currently Checked out");
						StatusString getItemsStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "items");
						if (getItemsStatus.recordStatus.returnStatus) {
							LOG.debug("LookupUser - Success received items page. HTML char: " + getItemsStatus.statusValue.length());
							ArrayList<AuthenticationItemsInfo> itemsCheckedOutList = null;//millenniumSvcMgr.getItemsCheckedOut(authenticatedUserId, strSessionId, "items");
							UserItemInfo itemsCheckOutStatus = millenniumSvcMgr.getItemsCheckedOut(getItemsStatus.statusValue, foundRenew);
							if (itemsCheckOutStatus.recordStatus.returnStatus) {
								LOG.debug("LookupUser - Success received items page array information");
								itemsCheckedOutList = itemsCheckOutStatus.itemsList;
								List<LoanedItem> loanedItemsList = new ArrayList<LoanedItem>();
							    for (int x = 0; x < itemsCheckedOutList.size(); x++) {
							    	LoanedItem loanedItem = new LoanedItem();
							    	ItemId itemId = new ItemId();
							    	itemId.setItemIdentifierValue(itemsCheckedOutList.get(x).bRecord);
							    	loanedItem.setTitle(itemsCheckedOutList.get(x).iTitle);
							    	loanedItem.setItemId(itemId);
							    	GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
							    	Date dueDate = null; // set to null
							    	dueDate = millenniumSvcMgr.setSimpleDateFormat(itemsCheckedOutList.get(x).iStatus);
							    	calendar.setTime(dueDate);
							    	loanedItem.setDateDue(calendar);
									String ReminderLevel = millenniumSvcMgr.getReminderLevel(itemsCheckedOutList.get(x).iStatus);
									//LOG.debug("LookupUser - ReminderLevel: " + ReminderLevel);
									if (ReminderLevel != null) {
										loanedItem.setReminderLevel(new BigDecimal(ReminderLevel));
									} else {
										loanedItem.setReminderLevel(new BigDecimal(1));	
									}
									Amount amount = new Amount();
								   	amount.setCurrencyCode(Version1CurrencyCode.USD); //Version1CurrencyCode.USD.getMinorUnit()
								   	String itemFineAmount = "0";
								   	if (itemsCheckedOutList.get(x).iStatus.contains("FINE")) {
								   		itemFineAmount = millenniumSvcMgr.getItemFineAmount(itemsCheckedOutList.get(x).iStatus);
								   	}
								   	amount.setMonetaryValue(new BigDecimal(itemFineAmount));
								   	loanedItem.setAmount(amount);
								   	loanedItemsList.add(loanedItem);
							    }
							    responseData.setLoanedItems(loanedItemsList);	
							} else {
								LOG.error("LookupUser - False received items page array information");
							 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
							 			"Failed to get Items Array", null, itemsCheckOutStatus.recordStatus.returnMsg);
							} // if (itemsCheckOutStatus.recordStatus.returnStatus)
						} else {
							LOG.error("LookupUser - False received items page");
							problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
						 			"False received items page", null, getItemsStatus.recordStatus.returnMsg);						
						} // if (getItemsStatus.recordStatus.returnStatus)
					} // if (getItems)
										
					if (getOverdues) {
						LOG.debug("LookupUser - Found Items Overdues");
						StatusString getOverduesStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "overdues");
						if (getOverduesStatus.recordStatus.returnStatus) {
							LOG.debug("LookupUser - Success received overdues page. HTML char: " + getOverduesStatus.statusValue.length());
							ArrayList<AuthenticationItemsInfo> itemsOverduesList = null; //millenniumSvcMgr.getItemsOverdues (authenticatedUserId, strSessionId, "overdues");
							UserItemInfo itemsOverduesStatus = millenniumSvcMgr.getItemsOverdues(getOverduesStatus.statusValue);
							if (itemsOverduesStatus.recordStatus.returnStatus) {
								LOG.debug("LookupUser - Success received items overdues page array information");
								itemsOverduesList = itemsOverduesStatus.itemsList;
								BigDecimal userTotalAmount = new BigDecimal(0);
								
								List<AccountDetails> userAccountDetailsList = new ArrayList<AccountDetails>();
								List<UserFiscalAccount> userFiscalAccountsList = new ArrayList<UserFiscalAccount>();
										
								UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
										
								//ArrayList<BibliographicItemId> bibliographicItemIds = new ArrayList<BibliographicItemId>();
								for (int x = 0; x < itemsOverduesList.size(); x++) {
									BigDecimal iAmount = new BigDecimal (itemsOverduesList.get(x).iFinesDetailAmt.replace("$", "").replace(".", "").trim());
									//LOG.debug("LookupUser - iAmount: " + iAmount);
									userTotalAmount = userTotalAmount.add(iAmount);
									//LOG.debug("LookupUser - Total Amount: " + iTotalAmount);
									//LOG.debug("Fine title: " + itemsOverduesList.get(x).iFinesEntryTitle);
								
							    	BibliographicDescription bibliographicDescription = new BibliographicDescription();
							    	bibliographicDescription.setTitle(itemsOverduesList.get(x).iFinesEntryTitle);

									AccountDetails userAccountDetails = new AccountDetails();
							    	ItemDetails userItemDetails = new ItemDetails();
							    	ItemId userItemId = new ItemId();
							    	userItemId.setItemIdentifierValue("");
							    	userItemDetails.setItemId(userItemId);
							    	userItemDetails.setBibliographicDescription(bibliographicDescription);
								    
							    	FiscalTransactionInformation userFiscalTransactionInformation = new FiscalTransactionInformation();
							    	try {
										userFiscalTransactionInformation.setFiscalActionType(FiscalActionType.find(null, "Payment"));
									} catch (ServiceException e1) {
										LOG.debug("Error! Couldn't set FiscalActionType");
										//e1.printStackTrace();
									}
							    	try {
										userFiscalTransactionInformation.setFiscalTransactionType(FiscalTransactionType.find(null, itemsOverduesList.get(x).iFinesDetailType));
									} catch (ServiceException e) {
										LOG.debug("Error! Couldn't set FiscalTransactionType");
										//e.printStackTrace();
									}
									    	
							    	GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
									calendar.getTime();
									userAccountDetails.setAccrualDate(calendar);
									//userFiscalTransactionInformation.setValidFromDate(calendar);
							    	//userFiscalTransactionInformation.setValidToDate(calendar);
							    	Amount userAmount = new Amount();
							    	userAmount.setCurrencyCode(Version1CurrencyCode.USD);
							    	userAmount.setMonetaryValue(new BigDecimal(iAmount.intValue()));
									    	
							    	userFiscalTransactionInformation.setAmount(userAmount);
							    	userFiscalTransactionInformation.setItemDetails(userItemDetails);
															    	
							    	userAccountDetails.setFiscalTransactionInformation(userFiscalTransactionInformation);
							    	userAccountDetailsList.add(userAccountDetails);
							    	userFiscalAccount.setAccountDetails(userAccountDetailsList);
							    	//userFiscalAccountsList.add(userFiscalAccount); // if added here - there will be repeated
								}
						        userFiscalAccountsList.add(userFiscalAccount);
								AccountBalance accountBalance = new AccountBalance();
								accountBalance.setCurrencyCode(Version1CurrencyCode.USD);
								accountBalance.setMonetaryValue(new BigDecimal(userTotalAmount.intValue()));
								userFiscalAccount.setAccountBalance(accountBalance);
								responseData.setUserFiscalAccounts(userFiscalAccountsList);
							} else {
								LOG.error("LookupUser - False received items overdues page array information");
								problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
							 			"Failed to get overdues Array", null, itemsOverduesStatus.recordStatus.returnMsg);
							} // if (itemsOverduesStatus.recordStatus.returnStatus)
								
						} else {
							LOG.error("LookupUser - False received overdues page.");
							problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
					 			"False received overdues page", null, getOverduesStatus.recordStatus.returnMsg);						
						} // if (getOverduesStatus.recordStatus.returnStatus)
					}
									
					if (getHolds) {
						LOG.debug("LookupUser - Found Items Holds");
						StatusString getHoldsStatus = millenniumSvcMgr.getAuthenticationItemsPage(authenticatedUserId, strSessionId, "holds");
						if (getHoldsStatus.recordStatus.returnStatus) {
							LOG.debug("LookupUser - Success received holds page. HTML char: " + getHoldsStatus.statusValue.length());
							ArrayList<AuthenticationItemsInfo> itemsHoldsList = null; //millenniumSvcMgr.getItemsHolds (authenticatedUserId, strSessionId, "holds");
							UserItemInfo itemsHoldsStatus = millenniumSvcMgr.getItemsHolds(getHoldsStatus.statusValue, foundCancel);
							if (itemsHoldsStatus.recordStatus.returnStatus) {
								LOG.debug("LookupUser - Success received items holds page array information");
								List<RequestedItem> requestedItemsList = new ArrayList<RequestedItem>();
								itemsHoldsList = itemsHoldsStatus.itemsList;
								for (int x = 0; x < itemsHoldsList.size(); x++) {
									//LOG.debug("Process: " + x + " record!");
											
							    	RequestedItem requestedItem = new RequestedItem();
								    					    	
							    	ItemId requestedItemId = new ItemId();
							    	requestedItemId.setItemIdentifierValue(itemsHoldsList.get(x).bRecord);
							    	requestedItem.setItemId(requestedItemId);
									requestedItem.setTitle(itemsHoldsList.get(x).iTitle); // Got Error if turn on
									requestedItem.setRequestType(Version1RequestType.HOLD);
							    	if (itemsHoldsList.get(x).iStatus.contains("Ready")) {
							    		//LOG.debug("Ready");
								    	requestedItem.setRequestStatusType(Version1RequestStatusType.AVAILABLE_FOR_PICKUP);
								    	//String patFuncStatus = millenniumSvcMgr.getpatFuncStatus(itemsHoldsList.get(x).iStatus);
										//LOG.debug("Ready - patFuncStatus: " + patFuncStatus);
										Date pickupDate = null; // set to null
										pickupDate = millenniumSvcMgr.setSimpleDateFormat(itemsHoldsList.get(x).iStatus);
										GregorianCalendar pickupCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
										pickupCalendar.setTime(pickupDate);
										//LOG.debug("Pickup Date: " + pickupDate);
										requestedItem.setPickupDate(pickupCalendar); 
								    } else if (itemsHoldsList.get(x).iStatus.toUpperCase().contains("AVAILABLE") ||
								    		itemsHoldsList.get(x).iStatus.toUpperCase().contains("NOT CHK'D OUT")) {
								    	//LOG.debug("AVAILABLE");
								    	requestedItem.setRequestStatusType(Version1RequestStatusType.AVAILABLE_FOR_PICKUP);	
								    } else if (itemsHoldsList.get(x).iStatus.toUpperCase().contains("DUE")) {
								    	//LOG.debug("DUE");
								    	requestedItem.setRequestStatusType(Version1RequestStatusType.IN_PROCESS);
								    } else if (itemsHoldsList.get(x).iStatus.toLowerCase().contains("holds")) {
								    	//LOG.debug("holds");
								    	requestedItem.setRequestStatusType(Version1RequestStatusType.IN_PROCESS);
								    	String patFuncStatus = millenniumSvcMgr.getpatFuncStatus(itemsHoldsList.get(x).iStatus);
										//LOG.debug("holds - patFuncStatus: " + patFuncStatus);
										String getHoldQueuePosition = millenniumSvcMgr.getHoldQueuePosition(patFuncStatus);
										//LOG.debug("holds - getHoldQueuePosition: " + getHoldQueuePosition);
										requestedItem.setHoldQueuePosition(new BigDecimal (getHoldQueuePosition));
								    } else {
								    	requestedItem.setRequestStatusType(Version1RequestStatusType.CANNOT_FULFILL_REQUEST);	
								    }
								    GregorianCalendar requestCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
								    requestCalendar.getTime();
								    //requestCalendar.add(Calendar.DAY_OF_YEAR, REQUEST_BACKDATE_INTERVAL);
								    requestedItem.setDatePlaced(requestCalendar);
								    //requestedItem.setPickupExpiryDate(itemsHoldsList.get(x).iCancelIfNotPickup);
								    Date cancelDate = null; // set to null
								    if (itemsHoldsList.get(x).iCancelIfNotPickup.contains("Missing")) {
								    	LOG.debug("LookupUser - Couldn't find 'Cancel if Not Filled By Date'!");
								    } else {
								    	cancelDate = millenniumSvcMgr.setSimpleDateFormat(itemsHoldsList.get(x).iCancelIfNotPickup);	
								    	GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
										calendar.setTime(cancelDate);
								    	requestedItem.setPickupExpiryDate(calendar);
								    }
									    	
								    try {
										requestedItem.setPickupLocation(PickupLocation.find(null, itemsHoldsList.get(x).iPickupLocation));
									} catch (ServiceException e) {
											//e.printStackTrace();
								    	LOG.debug("LookupUser - Error! Couldn't set PickupLocation");
									}
									    	
								    requestedItemsList.add(requestedItem);								
								}
								//LOG.debug("Response Object: " + responseData.toString());
								responseData.setRequestedItems(requestedItemsList);
							} else {
								LOG.error("LookupUser - False received items holds page array information");
								problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
							 			"Failed to get holds Array", null, itemsHoldsStatus.recordStatus.returnMsg);
							} // if (itemsHoldsStatus.recordStatus.returnStatus)
						} else {
							LOG.error("LookupUser - False received holds page.");
							problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
					 			"False received holds page", null, getHoldsStatus.recordStatus.returnMsg);
						} // if (getOverduesStatus.recordStatus.returnStatus)
					}
					    
					// TODO xcLookupUser: Finish getBlockOrTrap
					// If getBlockOrTrap is true, the returned user list of blocks
					// must contain all blocks currently placed on the user represented
					// by the userId field.
					if (getBlockOrTrap) {
						// log.debug("Get Block or Trap");
						/*
						 * 11/22/2011 @ 10:45 AM - Bach Nguyen added comment below after talking to Michael
						 * UNCC Millennium Library Datatbase does not have any function to display for this information
						 */
						LOG.debug("LookupUser - Todo: Get Block or Trap");
					}
					// If getRecalls is true, the returned user’s list of recalls must
					// contain XCUserRecalledItem Objects for all recalls the user
					// represented by the userId field has placed.
					if (getRecalls) {
						/*
						 * 11/22/2011 @ 11:33 AM - Bach Nguyen added comment below
						 * Right now we don't do anything with recall item yet for LookupUser
						 */
						LOG.debug("LookupUser - Todo: get Recalls");
					}
					// If getMessages is true, the returned user’s list of messages must
					// contain all messages applying the user represented by the userId.
					if (getMessages) {
						/*
						 * 11/22/2011 @ 11:28 AM - Bach Nguyen added comment below
						 * There are no message in UNCC Millennium Library
						 */
						LOG.debug("LookupUser - Todo: get Messages");
					}			    
				} else {
					LOG.error("LookupUser - Incorrect User Id or Password");
				 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
				 			"User Authentication Failed", null, authenticateStatus.recordStatus.returnMsg);
				} // (foundLDAPUser || foundPatronUser)

			} else {
			 	LOG.error("LookupUser - Username or Password is missing!");
			 	problems = ServiceHelper.generateProblems(Version1LookupUserProcessingError.USER_AUTHENTICATION_FAILED,
			 			"Missing Username or Password", null, "Username or Password is missing!");				
			} // if (foundRenew)
			
		//} else {
		// 	LOG.error("LookupUser - Services is not support!");
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
        	LOG.debug("LookupUser - Success received all information");
        	user.setUserIdentifierValue(authUserName.get(0).secondValue);
			try {
				user.setUserIdentifierType(UserIdentifierType.find(null, userIdentifierType));
			} catch (ServiceException e) {
				LOG.debug("LookupUser - Error - setUserIdentifierType");
				// e.printStackTrace();
			}
			responseData.setUserId(user);
			
			baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
        	millenniumSvcMgr.LogOut(baseUrl);
			
        	return responseData;
        }
	}
}
