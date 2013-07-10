/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.*;



/**
 * This class implements the Check In Item service for the connector. Basically this just
 * calls the RemoteServiceManager to get data (e.g. due date).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class MillenniumCheckInItemService implements CheckInItemService {

	private static final Logger LOG = Logger.getLogger(MillenniumRemoteServiceManager.class); 
	

	String authenticatedUserId = "";

	UserId user = new UserId();
    
    /**
     * Handles a NCIP CheckInItem service by returning hard-coded data.
     *
     * @param initData       the CheckInItemInitiationData
     * @param serviceManager provides access to remote services
     * @return CheckInItemResponseData
     */
    @Override
    public CheckInItemResponseData performService(CheckInItemInitiationData initData, ServiceContext serviceContext,
                                                   RemoteServiceManager serviceManager) {

    	
        final CheckInItemResponseData responseData = new CheckInItemResponseData();

 
        MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;

        // Echo back the same item id that came in
        responseData.setItemId(initData.getItemId());

        return responseData;
	}
}
