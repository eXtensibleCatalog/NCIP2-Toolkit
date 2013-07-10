/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class implements the Request Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. request id) and responds that the hold
 * was a success.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyUpdateRequestItemService implements UpdateRequestItemService {

    /**
     * Handles a NCIP UpdateRequestItem service by returning hard-coded data. This only looks for the first
     * item or bib id.
     *
     * @param initData       the UpdateRequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return UpdateRequestItemResponseData
     */
    @Override
    public UpdateRequestItemResponseData performService(UpdateRequestItemInitiationData initData,
                                                        ServiceContext serviceContext,
                                                        RemoteServiceManager serviceManager) {

        final UpdateRequestItemResponseData responseData = new UpdateRequestItemResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        responseData.setUserId(initData.getUserId());

        ItemId itemId;
        if (initData.getRequestId() != null ) {

            // Make up an item id
            String itemIdString = "1209381209";
            itemId = new ItemId();
            itemId.setItemIdentifierValue(itemIdString);

        } else {

            itemId = initData.getItemId();

        }

        if ( itemId != null) {

            responseData.setItemId(itemId);
            if ( initData.getRequestType() != null ) {

                responseData.setRequestType(initData.getRequestType());
                responseData.setRequestScopeType(Version1RequestScopeType.ITEM);
                // TODO: look at what's being sent in as delete fields and remove them
                // TODO: Look at what's being sent in as add fields and add them
                // TODO: Look at what's asked as Item or User Elements and return them

            } else {

                responseData.setProblems(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                    "//RequestType", null, "RequestType is required if RequestId is omitted."));

            }

        } else {

            // TODO: return problem - itemid or requestid is required
            responseData.setProblems(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                "//RequestId or //ItemId", null, "RequestId or ItemId is required."));

        }

        return responseData;
    }

}
