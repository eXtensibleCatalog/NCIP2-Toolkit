/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData;
import org.extensiblecatalog.ncip.v2.service.CheckInItemService;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;

/**
 * This class implements the Check In Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. due date).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyCheckInItemService implements CheckInItemService {

    protected static final int LOAN_PERIOD = 28;

    /**
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public DummyCheckInItemService() {
    }

    /**
     * Handles a NCIP CheckInItem service by returning hard-coded data.
     *
     * @param initData       the CheckInItemInitiationData
     * @param serviceManager provides access to remote services
     * @return CheckInItemResponseData
     */
    @Override
    public CheckInItemResponseData performService(CheckInItemInitiationData initData,
                                                  RemoteServiceManager serviceManager) {

        final CheckInItemResponseData responseData = new CheckInItemResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        // Echo back the same item id that came in
        responseData.setItemId(initData.getItemId());

        return responseData;
    }

}
