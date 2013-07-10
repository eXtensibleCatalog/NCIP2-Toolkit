/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RequestStatusType extends RequestStatusType {

    private static final Logger LOG = Logger.getLogger(Version1RequestStatusType.class);

    public static final String VERSION_1_REQUEST_STATUS_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/requeststatustype/requeststatustype.scm";

    public static final Version1RequestStatusType AVAILABLE_FOR_PICKUP = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "Available For Pickup");

    public static final Version1RequestStatusType CANNOT_FULFILL_REQUEST = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "Cannot Fulfill Request");

    public static final Version1RequestStatusType EXPIRED = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "Expired");

    public static final Version1RequestStatusType IN_PROCESS = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "In Process");

    public static final Version1RequestStatusType NEED_TO_ACCEPT_CONDITIONS = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "Need to Accept Conditions");

    public static final Version1RequestStatusType REQUESTED_VIA_ILL = new Version1RequestStatusType(VERSION_1_REQUEST_STATUS_TYPE, "Requested Via ILL");

    public static void loadAll() {
        LOG.debug("Loading Version1RequestStatusType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestStatusType(String scheme, String value) {
        super(scheme, value);
    }
}
