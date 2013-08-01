/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.dummy.DummyDatabase.UserInfo;
import org.extensiblecatalog.ncip.v2.service.*;


import java.math.BigDecimal;
import java.util.*;

/**
 * This class implements the Lookup User service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyLookupUserService implements LookupUserService {

    /**
     * Handles a NCIP LookupUser service by returning hard-coded data.
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService(LookupUserInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final LookupUserResponseData responseData = new LookupUserResponseData();
        boolean passwordChecked = false;
        boolean passwordConfirmed = false;
        List<Problem> listProblems = new ArrayList<Problem>();
        
        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;
        AgencyId agencyId = new AgencyId(dummySvcMgr.getLibraryName());
        
        
        if (initData.getAuthenticationInputDesired() || initData.getAuthenticationInputs().size() == 2) {
        	
			String userName = getPlaintextValue(initData.getAuthenticationInputs(), "username");
			String password = getPlaintextValue(initData.getAuthenticationInputs(), "password");
			
			if (userName == null || password == null) {
				listProblems.add(this.generateAuthenticationProblem());
				responseData.setProblems(listProblems);
				return responseData;
			}
			
			UserInfo userInfo = DummyDatabase.UserInfo.getUserInfo(userName);
			passwordChecked = true;
			if (userInfo != null) {
				passwordConfirmed = userInfo.confirmPassword(password);
			}
			if (userInfo != null && passwordConfirmed) {
			   //auth user
			   UserId userId = new UserId();
			   userId.setAgencyId(agencyId);
			   userId.setUserIdentifierValue(userName);
			   userId.setUserIdentifierType(new UserIdentifierType("userType"));
			   responseData.setUserId(userId);
			   initData.setUserId(userId);
			}
        } else if (initData.getUserId() != null  && !passwordChecked ) {
        	//requested via test page
        	responseData.setUserId(initData.getUserId());
        }
        
        if(responseData.getUserId() == null) {
        	listProblems.add(generateAuthenticationProblem());
        	responseData.setProblems(listProblems);
        	return responseData;
        	
        }
        
//default user
//        if (initData.getUserId() == null && !passwordChecked) {
//        	UserId id = new UserId();
//        	id.setAgencyId(agencyId);
//        	id.setUserIdentifierValue(initData.getAuthenticationInput(0).getAuthenticationInputData());
//        	id.setUserIdentifierType(new UserIdentifierType("userType"));
//        	responseData.setUserId(id);
//        }
//----
        UserOptionalFields userOptionalFields = new UserOptionalFields();
        
        String userNo = responseData.getUserId().getUserIdentifierValue();;

        if ( initData.getLoanedItemsDesired() ) {

            List<DummyDatabase.ItemInfo> itemInfos = DummyDatabase.ItemInfo.getChargedItemsByUserNo(userNo);
            if ( itemInfos != null && itemInfos.size() > 0 ) {

                List<LoanedItem> loanedItemsList = new ArrayList<LoanedItem>(itemInfos.size());

                for ( DummyDatabase.ItemInfo itemInfo : itemInfos ) {

                    LoanedItem loanedItem = new LoanedItem();
                    ItemId itemId = new ItemId();
                    itemId.setItemIdentifierValue(itemInfo.barcode);
                    loanedItem.setItemId(itemId);
                    loanedItem.setDateDue(itemInfo.dateDue);
                    // TODO: Once ReminderLevel is optional, change this
                    if ( itemInfo.overdueReminderCount == 0 ) {

                        loanedItem.setReminderLevel(new BigDecimal(1));

                    } else {

                        loanedItem.setReminderLevel(new BigDecimal(itemInfo.overdueReminderCount));

                    }
                    // Note: NCIP v2 schema requires an Amount here; by convention of the ILS-DI implementers
                    // we set it to zero when there is no amount. In the circ system we're simulating, there is
                    // no amount for charged items.
                    Amount amount = new Amount();
                    amount.setCurrencyCode(Version1CurrencyCode.USD);
                    amount.setMonetaryValue(new BigDecimal(000));
                    loanedItem.setAmount(amount);

                    loanedItem.setRenewalCount(new BigDecimal(itemInfo.renewalCount));

                    loanedItem.setDateCheckedOut(itemInfo.checkoutDate);

                    DummyDatabase.HoldingInfo holdingInfo = DummyDatabase.HoldingInfo.getByItemBarcode(itemInfo.barcode);
                    BibliographicDescription bibDescription = dummySvcMgr.getBibliographicDescription(holdingInfo.bibInfo);
                    loanedItem.setBibliographicDescription(bibDescription);
                    
                    loanedItemsList.add(loanedItem);

                }
                responseData.setLoanedItems(loanedItemsList);
                List<LoanedItemsCount> loanedItemsCountsList = new ArrayList<LoanedItemsCount>(1);
                LoanedItemsCount checkedOutLoanedItemsCount = new LoanedItemsCount();
                checkedOutLoanedItemsCount.setCirculationStatus(
                    DummyRemoteServiceManager.translateCircStatus(DummyDatabase.CircStatus.CHECKED_OUT));
                checkedOutLoanedItemsCount.setLoanedItemCountValue(new BigDecimal(loanedItemsList.size()));
                loanedItemsCountsList.add(checkedOutLoanedItemsCount);
                responseData.setLoanedItemsCounts(loanedItemsCountsList);

            } else {

                // No charged items - omit the LoanedItem element.

            }

        }

        if ( initData.getUserFiscalAccountDesired() ) {

            // Add a user fiscal account
            UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
            List<AccountDetails> accountDetailsList = new ArrayList<AccountDetails>();
            List<UserFiscalAccount> userFiscalAccountsList = new ArrayList<UserFiscalAccount>();
            userFiscalAccount.setAccountDetails(accountDetailsList);
            userFiscalAccountsList.add(userFiscalAccount);
            responseData.setUserFiscalAccounts(userFiscalAccountsList);
            long accountBalanceValue = 0;

            // Create an account detail for a late fee
            AccountDetails lateFeeAccountDetails = new AccountDetails();
            GregorianCalendar lateFeeAccrualDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeAccrualDate.add(Calendar.DAY_OF_YEAR, -45);
            lateFeeAccountDetails.setAccrualDate(lateFeeAccrualDate);
            accountDetailsList.add(lateFeeAccountDetails);

            FiscalTransactionInformation lateFeeFiscalTransactionInformation = new FiscalTransactionInformation();
            lateFeeAccountDetails.setFiscalTransactionInformation(lateFeeFiscalTransactionInformation);

            Amount lateFeeAmount = new Amount();
            lateFeeAmount.setCurrencyCode(Version1CurrencyCode.USD);
            lateFeeAmount.setMonetaryValue(new BigDecimal(750));
            accountBalanceValue+= 750;
            lateFeeFiscalTransactionInformation.setAmount(lateFeeAmount);
            lateFeeFiscalTransactionInformation.setFiscalActionType(Version1FiscalActionType.ASSESS);
            lateFeeFiscalTransactionInformation.setFiscalTransactionDescription("Late fee.");
            FiscalTransactionReferenceId lateFeeFiscalTransactionReferenceId = new FiscalTransactionReferenceId();
            lateFeeFiscalTransactionReferenceId.setFiscalTransactionIdentifierValue("19xkq2701hshjq0183nkjxs17_bcrq");
            lateFeeFiscalTransactionReferenceId.setAgencyId(agencyId);
            lateFeeFiscalTransactionInformation.setFiscalTransactionReferenceId(lateFeeFiscalTransactionReferenceId);
            lateFeeFiscalTransactionInformation.setFiscalTransactionType(Version1FiscalTransactionType.FINE);
            ItemDetails lateFeeItemDetails = new ItemDetails();
            BibliographicDescription lateFeeBibDescription = dummySvcMgr.getBibliographicDescription(
                DummyDatabase.BibInfo.getByBibNo("mzk.001168631"));
            lateFeeItemDetails.setBibliographicDescription(lateFeeBibDescription);
            GregorianCalendar lateFeeCheckoutDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeCheckoutDate.add(Calendar.DAY_OF_YEAR, -75);
            lateFeeItemDetails.setDateCheckedOut(lateFeeCheckoutDate);
            GregorianCalendar lateFeeDateDue = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeDateDue.add(Calendar.DAY_OF_YEAR, -50);
            lateFeeItemDetails.setDateDue(lateFeeDateDue);
            GregorianCalendar lateFeeDateReturned = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeDateReturned.add(Calendar.DAY_OF_YEAR, -45);
            lateFeeItemDetails.setDateReturned(lateFeeDateReturned);
            ItemId lateFeeItemId = new ItemId();
            lateFeeItemId.setItemIdentifierValue("25556192919132");
            lateFeeItemDetails.setItemId(lateFeeItemId);
            lateFeeFiscalTransactionInformation.setItemDetails(lateFeeItemDetails);
            // Omitting the following fields as not relevant to the example being dummied up here
            // (i.e. no paymentmethod for a fiscal transaction that's unpaid as yet).
            //fiscalTransactionInformation.setPaymentMethodType();
            //fiscalTransactionInformation.setRequestId();
            GregorianCalendar lateFeeValidFromDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeValidFromDate.add(Calendar.DAY_OF_YEAR, -45);
            lateFeeFiscalTransactionInformation.setValidFromDate(lateFeeValidFromDate);
            GregorianCalendar lateFeeValidToDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            lateFeeValidToDate.add(Calendar.YEAR, 1);
            lateFeeFiscalTransactionInformation.setValidToDate(lateFeeValidToDate);

            // Create an account detail for an ILL request
            AccountDetails illFineAccountDetails = new AccountDetails();
            GregorianCalendar illFineAccrualDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            illFineAccrualDate.add(Calendar.DAY_OF_YEAR, -5);
            illFineAccountDetails.setAccrualDate(illFineAccrualDate);
            accountDetailsList.add(illFineAccountDetails);

            FiscalTransactionInformation illFineFiscalTransactionInformation = new FiscalTransactionInformation();
            illFineAccountDetails.setFiscalTransactionInformation(illFineFiscalTransactionInformation);

            Amount illFineAmount = new Amount();
            illFineAmount.setCurrencyCode(Version1CurrencyCode.USD);
            illFineAmount.setMonetaryValue(new BigDecimal(450));
            accountBalanceValue+= 450;
            illFineFiscalTransactionInformation.setAmount(illFineAmount);
            illFineFiscalTransactionInformation.setFiscalActionType(Version1FiscalActionType.ASSESS);
            illFineFiscalTransactionInformation.setFiscalTransactionDescription("ILL fee.");
            FiscalTransactionReferenceId illFineFiscalTransactionReferenceId = new FiscalTransactionReferenceId();
            illFineFiscalTransactionReferenceId.setFiscalTransactionIdentifierValue("VDX-1291831");
            illFineFiscalTransactionReferenceId.setAgencyId(agencyId);
            illFineFiscalTransactionInformation.setFiscalTransactionReferenceId(illFineFiscalTransactionReferenceId);
            illFineFiscalTransactionInformation.setFiscalTransactionType(
                Version1FiscalTransactionType.INTERLIBRARY_LOAN_FEE);
            RequestId illFineRequestId = new RequestId();
            illFineRequestId.setRequestIdentifierValue("VDX-1291831");
            illFineFiscalTransactionInformation.setRequestId(illFineRequestId);

            // Now set the Account Balance
            AccountBalance accountBalance = new AccountBalance();
            accountBalance.setCurrencyCode(Version1CurrencyCode.USD);
            accountBalance.setMonetaryValue(new BigDecimal(accountBalanceValue));
            userFiscalAccount.setAccountBalance(accountBalance);

            // Now create summary
            UserFiscalAccountSummary userFiscalAcctSummary = new UserFiscalAccountSummary();
            userFiscalAcctSummary.setAccountBalance(accountBalance);
            userFiscalAcctSummary.setChargesCount(new BigDecimal(accountDetailsList.size()));
            responseData.setUserFiscalAccountSummary(userFiscalAcctSummary);
        }

        if ( initData.getRequestedItemsDesired() ) {

            List<DummyDatabase.RequestInfo> requestInfos = DummyDatabase.RequestInfo.getByUserNo(userNo);
            if ( requestInfos != null && requestInfos.size() > 0 ) {

                List<RequestedItem> requestedItemsList = new ArrayList<RequestedItem>(requestInfos.size());
                Map<CirculationStatus, BigDecimal> countByCircStatus
                    = new HashMap<CirculationStatus, BigDecimal>();

                for ( DummyDatabase.RequestInfo requestInfo : requestInfos ) {
                	
                    RequestedItem requestedItem = new RequestedItem();
                    ItemId itemId = new ItemId();
                    itemId.setItemIdentifierValue(requestInfo.itemBarcode);
                    requestedItem.setItemId(itemId);
                    RequestId requestId = new RequestId();
                    requestId.setRequestIdentifierValue(requestInfo.requestNo);
                    requestedItem.setDatePlaced(requestInfo.createDate);
                    requestedItem.setRequestId(requestId);
                    DummyDatabase.HoldingInfo holdingInfo = DummyDatabase.HoldingInfo.getByItemBarcode(requestInfo.itemBarcode);
                    BibliographicDescription bibDescription = dummySvcMgr.getBibliographicDescription(holdingInfo.bibInfo);
                    requestedItem.setBibliographicDescription(bibDescription);

                    // TODO: These next two are hard-coded; instead get from Database.
                    requestedItem.setRequestType(Version1RequestType.HOLD);
                    requestedItem.setRequestStatusType(Version1RequestStatusType.AVAILABLE_FOR_PICKUP);
                    requestedItem.setPickupLocation(new PickupLocation(null, requestInfo.pickupLoc));
                    int queuePosition = requestInfo.getQueuePosition();

                    // TODO: Once HoldQueuePosition is optional, change this
                    if ( queuePosition == 0 ) {

                        requestedItem.setHoldQueuePosition(new BigDecimal(1));

                    } else {

                        requestedItem.setHoldQueuePosition(new BigDecimal(queuePosition));

                    }

                    if ( queuePosition == 0 ) {

                        requestedItem.setPickupDate(requestInfo.pickupStart);
                        requestedItem.setPickupExpiryDate(requestInfo.pickupEnd);
                        // TODO: Once ReminderLevel is optional, change this
                        if ( requestInfo.itemAvailableCount == 0 ) {

                            requestedItem.setReminderLevel(new BigDecimal(1));

                        } else {

                            requestedItem.setReminderLevel(new BigDecimal(requestInfo.itemAvailableCount));

                        }

                    }

                    requestedItemsList.add(requestedItem);
                    DummyDatabase.ItemInfo itemInfo = DummyDatabase.ItemInfo.getByBarcode(requestInfo.itemBarcode);
                    assert ( itemInfo != null );
                    CirculationStatus ncipCircStatus = DummyRemoteServiceManager.translateCircStatus(itemInfo.circStatus);
                    BigDecimal count = countByCircStatus.get(ncipCircStatus);
                    if ( count != null ) {

                        count = count.add(new BigDecimal(1));

                    } else {

                        count = new BigDecimal(1);

                    }
                    countByCircStatus.put(ncipCircStatus, count);

                }
                responseData.setRequestedItems(requestedItemsList);

                if ( countByCircStatus.size() > 0 ) {

                    List<RequestedItemsCount> requestedItemsCountsList
                        = new ArrayList<RequestedItemsCount>(countByCircStatus.size());

                    for ( Map.Entry<CirculationStatus, BigDecimal> entry : countByCircStatus.entrySet() ) {

                        RequestedItemsCount requestedItemsCount = new RequestedItemsCount();
                        requestedItemsCount.setCirculationStatus(entry.getKey());
                        requestedItemsCount.setRequestedItemCountValue(entry.getValue());
                        requestedItemsCountsList.add(requestedItemsCount);

                    }

                    responseData.setRequestedItemsCounts(requestedItemsCountsList);

                }

            } else {

                // No charged items - omit the LoanedItem element.

            }

        }

        UserOptionalFields loadedOptionalFields = null;

        if ( initData.getBlockOrTrapDesired() ) {

            // TODO: Block or trap

        }

        if ( initData.getDateOfBirthDesired() ) {

        	if ( loadedOptionalFields == null ) {
        		DummyDatabase.UserInfo userInfo = DummyDatabase.UserInfo.getUserInfo(initData.getUserId().getUserIdentifierValue());
        		if ( userInfo != null) {
        			loadedOptionalFields = userInfo.getOptionalFields();
        		}
        	}
           
        	if ( loadedOptionalFields != null ) {
        		userOptionalFields.setDateOfBirth(loadedOptionalFields.getDateOfBirth());
        	}

        }

        if ( initData.getNameInformationDesired() ) {

        	if ( loadedOptionalFields == null ) {
        		DummyDatabase.UserInfo userInfo = DummyDatabase.UserInfo.getUserInfo(initData.getUserId().getUserIdentifierValue());
        		if ( userInfo != null) {
        			loadedOptionalFields = userInfo.getOptionalFields();
        		}
        	}
           
        	if ( loadedOptionalFields != null ) {
        		userOptionalFields.setNameInformation(loadedOptionalFields.getNameInformation());
        	}
            
        }
        
        if ( initData.getUserAddressInformationDesired() ) {
        	if ( loadedOptionalFields == null ) {
        		DummyDatabase.UserInfo userInfo = DummyDatabase.UserInfo.getUserInfo(initData.getUserId().getUserIdentifierValue());
        		if ( userInfo != null) {
        			loadedOptionalFields = userInfo.getOptionalFields();
        		}
        	}
        	
        	if ( loadedOptionalFields != null ) {
        		userOptionalFields.setUserAddressInformations(loadedOptionalFields.getUserAddressInformations());
        	}
        }

        responseData.setUserOptionalFields(userOptionalFields);
        
        return responseData;
    }
    
    static Problem generateAuthenticationProblem() {
    	Problem authProblem = new Problem();
    	authProblem.setProblemDetail("invalid name or password");
    	authProblem.setProblemElement("problem");
    	authProblem.setProblemValue("value");
    	authProblem.setProblemType(new ProblemType("authProblem"));
    	return authProblem;
    }
    
    static String getPlaintextValue (List<AuthenticationInput> inputs, String value) {
    	for (AuthenticationInput current : inputs) {
    		if (current.getAuthenticationInputType().getValue().equalsIgnoreCase(value) 
    				&& current.getAuthenticationDataFormatType().getValue().equalsIgnoreCase("text")) {
    			return current.getAuthenticationInputData();
    		}
    	}
    	return null;
    }
    

}
