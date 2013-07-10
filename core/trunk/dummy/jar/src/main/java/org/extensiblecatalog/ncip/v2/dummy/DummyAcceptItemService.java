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
 * This class implements the Accept Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. due date).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyAcceptItemService implements AcceptItemService {

    /**
     * Handles a NCIP AcceptItem service by returning hard-coded data.
     *
     * @param initData       the AcceptItemInitiationData
     * @param serviceManager provides access to remote services
     * @return AcceptItemResponseData
     */
    @Override
    public AcceptItemResponseData performService(AcceptItemInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final AcceptItemResponseData responseData = new AcceptItemResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        // Echo back the same request id that came in
        responseData.setRequestId(initData.getRequestId());

        return responseData;
    }

}
