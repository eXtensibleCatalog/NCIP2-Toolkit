/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RequestType extends RequestType {

    private static final Logger LOG = Logger.getLogger(Version1RequestType.class);

    public static final String VERSION_1_REQUEST_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm";

    // Request is for an estimate of the charge to provide the Item or service requested.
    public static final Version1RequestType ESTIMATE = new Version1RequestType(VERSION_1_REQUEST_TYPE, "Estimate");
    // Request is to reserve the Item for future use. If the Item is not currently available, the request is placed
    // in an ordered list or queue so that the request is satisfied when the Item becomes available. Alternatively
    // the request can specify a specific date/time when the Item is required.
    public static final Version1RequestType HOLD = new Version1RequestType(VERSION_1_REQUEST_TYPE, "Hold");
    // Request is for the loan of the Item for a specified period of time.
    public static final Version1RequestType LOAN = new Version1RequestType(VERSION_1_REQUEST_TYPE, "Loan");
    // Request is for the supply of the Item with no requirement that the Item be returned.
    public static final Version1RequestType NON_RETURNABLE_COPY = new Version1RequestType(VERSION_1_REQUEST_TYPE, "Non-returnable Copy");
    // Request is for the retrieval of the Item from a location that may not be accessible to a User.
    public static final Version1RequestType STACK_RETRIEVAL = new Version1RequestType(VERSION_1_REQUEST_TYPE, "Stack Retrieval");

    public static void loadAll() {
        LOG.debug("Loading Version1RequestType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestType(String scheme, String value) {
        super(scheme, value);
    }
}
