/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1CancelRequestItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1CancelRequestItemProcessingError.class);

    public static final String VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/cancelrequestitemprocessingerror.scm";

    public static final Version1CancelRequestItemProcessingError ELEMENT_RULE_VIOLATED
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1CancelRequestItemProcessingError REQUEST_ALREADY_PROCESSED
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "Request Already Processed");

    public static final Version1CancelRequestItemProcessingError UNKNOWN_ITEM
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static final Version1CancelRequestItemProcessingError UNKNOWN_REQUEST
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Request");

    public static final Version1CancelRequestItemProcessingError UNKNOWN_USER
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "Unknown User");

    public static final Version1CancelRequestItemProcessingError USER_AUTHENTICATION_FAILED
        = new Version1CancelRequestItemProcessingError(VERSION_1_CANCEL_REQUEST_ITEM_PROCESSING_ERROR, "User Authentication Failed");

    public static void loadAll() {
        LOG.debug("Loading Version1CancelRequestItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1CancelRequestItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
