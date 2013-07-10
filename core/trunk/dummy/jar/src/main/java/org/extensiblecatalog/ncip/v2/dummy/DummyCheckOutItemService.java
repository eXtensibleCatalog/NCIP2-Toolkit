/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * This class implements the Check Out Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. due date).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyCheckOutItemService implements CheckOutItemService {

    protected static final int LOAN_PERIOD = 28;

    /**
     * Handles a NCIP CheckOutItem service by returning hard-coded data.
     *
     * @param initData       the CheckOutItemInitiationData
     * @param serviceManager provides access to remote services
     * @return CheckOutItemResponseData
     */
    @Override
    public CheckOutItemResponseData performService(CheckOutItemInitiationData initData,
                                                   ServiceContext serviceContext,
                                                   RemoteServiceManager serviceManager) {

        final CheckOutItemResponseData responseData = new CheckOutItemResponseData();

        String itemId = initData.getItemId().getItemIdentifierValue();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        // Echo back the same item id that came in
        responseData.setItemId(initData.getItemId());

        responseData.setUserId(initData.getUserId());

        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_YEAR, LOAN_PERIOD);

        responseData.setDateDue(calendar);

        return responseData;
    }

}
