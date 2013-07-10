/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

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
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public DummyLookupUserService() {
    }

    /**
     * Handles a NCIP LookupUser service by returning hard-coded data.
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService(LookupUserInitiationData initData,
                                                 RemoteServiceManager serviceManager) {

        final LookupUserResponseData responseData = new LookupUserResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        // Echo back the same item id that came in
        responseData.setUserId(initData.getUserId());

        UserOptionalFields userOptionalFields = new UserOptionalFields();

        String userNo = initData.getUserId().getUserIdentifierValue();

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
                    loanedItem.setReminderLevel(new BigDecimal(itemInfo.overdueReminderCount));
                    // Note: NCIP v2 schema requires an Amount here; by convention of the ILS-DI implementers
                    // we set it to zero when there is no amount. In the circ system we're simulating, there is
                    // no amount for charged items.
                    Amount amount = new Amount();
                    amount.setCurrencyCode(Version1CurrencyCode.USD);
                    amount.setMonetaryValue(new BigDecimal(000));
                    loanedItem.setAmount(amount);
                    loanedItemsList.add(loanedItem);

                }
                responseData.setLoanedItems(loanedItemsList);
                List<LoanedItemsCount> loanedItemsCountsList = new ArrayList<LoanedItemsCount>(1);
                LoanedItemsCount checkedOutLoanedItemsCount = new LoanedItemsCount();
                checkedOutLoanedItemsCount.setCirculationStatus(Version1CirculationStatus.ON_LOAN);
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
            AccountBalance accountBalance = new AccountBalance();
            accountBalance.setCurrencyCode(Version1CurrencyCode.USD);
            accountBalance.setMonetaryValue(new BigDecimal(200));
            userFiscalAccount.setAccountBalance(accountBalance);
            List<UserFiscalAccount> userFiscalAccountsList = new ArrayList<UserFiscalAccount>();
            userFiscalAccountsList.add(userFiscalAccount);
            responseData.setUserFiscalAccounts(userFiscalAccountsList);

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
                    requestedItem.setPickupLocation(new PickupLocation(null, requestInfo.pickupLoc));
                    int queuePosition = requestInfo.getQueuePosition();  
                    requestedItem.setHoldQueuePosition(new BigDecimal(queuePosition));
                    if ( queuePosition == 0 ) {

                        requestedItem.setPickupDate(requestInfo.pickupStart);
                        requestedItem.setPickupExpiryDate(requestInfo.pickupEnd);
                        requestedItem.setReminderLevel(new BigDecimal(requestInfo.itemAvailableCount));

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

        if ( initData.getAuthenticationInputDesired() ) {

            // TODO: Auth inputs

        }

        if ( initData.getBlockOrTrapDesired() ) {

            // TODO: Block or trap

        }

        if ( initData.getDateOfBirthDesired() ) {

            // TODO: Date of birth

        }

        if ( initData.getNameInformationDesired() ) {

            PersonalNameInformation pni = new PersonalNameInformation();
            pni.setUnstructuredPersonalUserName("Jane Doer");

            NameInformation ni = new NameInformation();
            ni.setPersonalNameInformation(pni);
            userOptionalFields.setNameInformation(ni);
            
        }

        responseData.setUserOptionalFields(userOptionalFields);

        return responseData;
    }
}
