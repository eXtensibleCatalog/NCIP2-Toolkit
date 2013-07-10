/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1AcceptItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1AcceptItemProcessingError.class);

    public static final String VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/acceptitemprocessingerror.scm";

    public static final Version1AcceptItemProcessingError CANNOT_ACCEPT_ITEM
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Cannot Accept Item");

    public static final Version1AcceptItemProcessingError CANNOT_GUARANTEE_RESTRICTIONS_ON_USE
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Cannot Guarantee Restrictions On Use");

    public static final Version1AcceptItemProcessingError ELEMENT_RULE_VIOLATED
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1AcceptItemProcessingError UNKNOWN_ITEM
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static final Version1AcceptItemProcessingError UNKNOWN_REQUEST
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Unknown Request");

    public static final Version1AcceptItemProcessingError UNKNOWN_USER
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "Unknown User");

    public static final Version1AcceptItemProcessingError USER_INELIGIBLE_TO_CHECK_OUT_THIS_ITEM
        = new Version1AcceptItemProcessingError(VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR, "User Ineligible To Check Out This Item");

    public static void loadAll() {
        LOG.debug("Loading Version1AcceptItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1AcceptItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
