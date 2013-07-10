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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * This class implements the Renew Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyRenewItemService implements RenewItemService {

    protected static int RENEWAL_PERIOD = 10;

    /**
     * Handles a NCIP RenewItem service by returning hard-coded data.
     *
     * @param initData       the RenewItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RenewItemResponseData
     */
    @Override
    public RenewItemResponseData performService(RenewItemInitiationData initData,
                                                ServiceContext serviceContext,
                                                RemoteServiceManager serviceManager) {

        final RenewItemResponseData responseData = new RenewItemResponseData();

        DummyRemoteServiceManager svcMgr = (DummyRemoteServiceManager)serviceManager;

        String itemBarcode = initData.getItemId() != null ? initData.getItemId().getItemIdentifierValue() : null;

        if ( itemBarcode != null ) {

            DummyDatabase.ItemInfo itemInfo = DummyDatabase.ItemInfo.getByBarcode(itemBarcode);
            if ( itemInfo != null ) {

                DummyDatabase.RenewErrorCode errorCode = itemInfo.renew();
                if ( errorCode == null ) {

                    responseData.setItemId(initData.getItemId());

                    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
                    calendar.add(Calendar.DAY_OF_YEAR, RENEWAL_PERIOD);
                    responseData.setDateDue(calendar);

                    responseData.setRenewalCount(new BigDecimal(itemInfo.renewalCount));
                    responseData.setDateDue(itemInfo.dateDue);

                } else {

                    ProblemType problemType;
                    switch ( errorCode ) {

                        case GENERAL_POLICY_PROBLEM :

                            problemType = Version1RenewItemProcessingError.USER_INELIGIBLE_TO_RENEW_THIS_ITEM;
                            break;

                        case ITEM_ON_HOLD :

                            problemType = Version1RenewItemProcessingError.RENEWAL_NOT_ALLOWED_ITEM_HAS_OUTSTANDING_REQUESTS;
                            break;

                        case MAX_RENEWALS_EXCEEDED :

                            problemType = Version1RenewItemProcessingError.MAXIMUM_RENEWALS_EXCEEDED;
                            break;

                        case NOT_RENEWABLE :

                            problemType = Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE;
                            break;

                        case USER_BLOCKED :

                            problemType = Version1RenewItemProcessingError.USER_BLOCKED;
                            break;

                        default :

                            problemType = Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE;
                            break;

                    }
                    responseData.setProblems(ServiceHelper.generateProblems(problemType, null, null, null));

                }

            } else {

                responseData.setProblems(ServiceHelper.generateProblems(Version1RenewItemProcessingError.UNKNOWN_ITEM,
                    "//ItemId/ItemIdentifierValue", itemBarcode, "Item barcode was not found."));

            }


        } else {

                responseData.setProblems(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                    "//ItemId/ItemIdentifierValue", null, "Item barcode is missing."));

        }

        return responseData;
    }

}
