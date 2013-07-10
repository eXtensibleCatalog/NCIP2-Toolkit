/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class implements the Request Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. request id) and responds that the hold
 * was a success.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class MillenniumRequestItemService implements RequestItemService {

    /**
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public MillenniumRequestItemService() {
    }

    /**
     * Handles a NCIP RequestItem service by returning hard-coded data.
     *
     * @param initData       the RequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RequestItemResponseData
     */
    @Override
    public RequestItemResponseData performService(RequestItemInitiationData initData,
                                                  RemoteServiceManager serviceManager) {

        final RequestItemResponseData responseData = new RequestItemResponseData();

        MillenniumRemoteServiceManager dummySvcMgr = (MillenniumRemoteServiceManager)serviceManager;

        String userIdString = dummySvcMgr.getUserId();
        UserId userId = new UserId();
        userId.setUserIdentifierValue(userIdString);
        responseData.setUserId(userId);

        responseData.setRequestScopeType(Version1RequestScopeType.ITEM);

        responseData.setRequestType(initData.getRequestType());

        String requestIdString = dummySvcMgr.getRequestId();
        if (initData.getRequestId() != null) {
            // Echo back the same request id that came in
            requestIdString = initData.getRequestId().getRequestIdentifierValue();
        }
        RequestId requestId = new RequestId();
        requestId.setRequestIdentifierValue(requestIdString);
        responseData.setRequestId(requestId);

        // The Dummy Service always pretends that it resolved the request to a particular item, and returns
        // that item id. If the initiation data had a specific item id, we return that one. Otherwise
        // we return a made-up one.
        String itemIdString = dummySvcMgr.getItemId();
        if (initData.getItemId() != null) {
            itemIdString = initData.getItemId().getItemIdentifierValue();
        }
        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(itemIdString);
        responseData.setItemId(itemId);

        return responseData;
    }

}
