/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RequestElementType extends RequestElementType {

    private static final Logger LOG = Logger.getLogger(Version1RequestElementType.class);

    public static final String VERSION_1_REQUEST_ELEMENT_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/requestelementtype/requestelementtype.scm";

    public static final Version1RequestElementType USER_ID
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "User Id");
    public static final Version1RequestElementType REQUEST_TYPE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Request Type");
    public static final Version1RequestElementType REQUEST_SCOPE_TYPE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Request Scope Type");
    public static final Version1RequestElementType REQUEST_STATUS_TYPE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Request Status Type");
    public static final Version1RequestElementType HOLD_QUEUE_POSITION
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Hold Queue Position");
    public static final Version1RequestElementType SHIPPING_INFORMATION
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Shipping Information");
    public static final Version1RequestElementType EARLIEST_DATE_NEEDED
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Earliest Date Needed");
    public static final Version1RequestElementType NEED_BEFORE_DATE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Need Before Date");
    public static final Version1RequestElementType PICKUP_DATE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Pickup Date");
    public static final Version1RequestElementType PICKUP_LOCATION
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Pickup Location");
    public static final Version1RequestElementType PICKUP_EXPIRY_DATE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Pickup Expiry Date");
    public static final Version1RequestElementType DATE_OF_USER_REQUEST
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Date Of User Request");
    public static final Version1RequestElementType DATE_AVAILABLE
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Date Available");
    public static final Version1RequestElementType ACKNOWLEDGED_FEE_AMOUNT
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Acknowledged Fee Amount");
    public static final Version1RequestElementType PAID_FEE_AMOUNT
        = new Version1RequestElementType(VERSION_1_REQUEST_ELEMENT_TYPE, "Paid Fee Amount");

    public static void loadAll() {
        LOG.debug("Loading Version1RequestElementType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestElementType(String scheme, String value) {
        super(scheme, value);
    }
}
