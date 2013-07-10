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
import java.util.GregorianCalendar;

/**
 * This class implements the Request Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. request id) and responds that the hold
 * was a success.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyCancelRequestItemService implements CancelRequestItemService {

    /**
     * Handles a NCIP CancelRequestItem service by returning hard-coded data. This only looks for the first
     * item or bib id.
     *
     * @param initData       the CancelRequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return CancelRequestItemResponseData
     */
    @Override
    public CancelRequestItemResponseData performService(CancelRequestItemInitiationData initData,
                                                        ServiceContext serviceContext,
                                                        RemoteServiceManager serviceManager) {

        final CancelRequestItemResponseData responseData = new CancelRequestItemResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        responseData.setUserId(initData.getUserId());

        if (initData.getItemId() != null) {
            // Echo back the same item id that came in
            String itemIdString = initData.getItemId().getItemIdentifierValue();
            ItemId itemId = new ItemId();
            itemId.setItemIdentifierValue(itemIdString);
            responseData.setItemId(itemId);
        }

        if (initData.getRequestId() != null) {
            // Echo back the same request id that came in
            String requestIdString = initData.getRequestId().getRequestIdentifierValue();
            RequestId requestId = new RequestId();
            requestId.setRequestIdentifierValue(requestIdString);
            responseData.setRequestId(requestId);
        }

        if (initData.getUserId() != null) {
            // Echo back the same user id that came in
            String userIdString = initData.getUserId().getUserIdentifierValue();
            UserId userId = new UserId();
            userId.setUserIdentifierValue(userIdString);
            responseData.setUserId(userId);
        }

        // If the request id is even, return fiscal transaction info
        String id = initData.getRequestId() != null ? initData.getRequestId().getRequestIdentifierValue() : initData.getItemId() != null ? initData.getItemId().getItemIdentifierValue() : null;
        if ( id != null && id.length() > 0 ) {

            FiscalTransactionInformation fti = new FiscalTransactionInformation();
            Amount fee = new Amount();
            fee.setMonetaryValue(new BigDecimal(2));
            fee.setCurrencyCode(Version1CurrencyCode.USD);
            fti.setAmount(fee);
            fti.setFiscalActionType(Version1FiscalActionType.ASSESS);
            fti.setFiscalTransactionType(Version1FiscalTransactionType.RESERVATION_CHARGE);
            fti.setFiscalTransactionDescription("Charge for canceling partially-processed hold.");
            FiscalTransactionReferenceId ftri = new FiscalTransactionReferenceId();
            ftri.setFiscalTransactionIdentifierValue(id + "-001");
            fti.setFiscalTransactionReferenceId(ftri);
            fti.setRequestId(initData.getRequestId());
            fti.setValidFromDate(new GregorianCalendar());
        }

        return responseData;
    }

}
