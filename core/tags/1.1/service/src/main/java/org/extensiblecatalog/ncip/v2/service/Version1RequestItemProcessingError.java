/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RequestItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1RequestItemProcessingError.class);

    public static final String VERSION_1_REQUEST_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/requestitemprocessingerror.scm";

    public static final Version1RequestItemProcessingError DUPLICATE_REQUEST
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Duplicate Request");
    public static final Version1RequestItemProcessingError ELEMENT_RULE_VIOLATED
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Element Rule Violated");
    public static final Version1RequestItemProcessingError ITEM_DOES_NOT_CIRCULATE
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Item Does Not Circulate");
    public static final Version1RequestItemProcessingError ITEM_NOT_AVAILABLE_BY_NEED_BEFORE_DATE
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Item Not Available By Need Before Date");
    public static final Version1RequestItemProcessingError UNKNOWN_ITEM
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Item");
    public static final Version1RequestItemProcessingError UNKNOWN_USER
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Unknown User");
    public static final Version1RequestItemProcessingError USER_AUTHENTICATION_FAILED
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Authentication Failed");
    public static final Version1RequestItemProcessingError USER_BLOCKED
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Blocked");
    public static final Version1RequestItemProcessingError USER_INELIGIBLE_TO_REQUEST_THIS_ITEM
        = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Ineligible To Request This Item");

    public static void loadAll() {
        LOG.debug("Loading Version1RequestItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
