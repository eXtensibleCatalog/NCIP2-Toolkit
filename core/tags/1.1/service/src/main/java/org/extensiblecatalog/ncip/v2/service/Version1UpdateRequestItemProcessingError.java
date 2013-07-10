/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1UpdateRequestItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1UpdateRequestItemProcessingError.class);

    public static final String VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/updaterequestitemprocessingerror.scm";

    public static final Version1UpdateRequestItemProcessingError ELEMENT_RULE_VIOLATED
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1UpdateRequestItemProcessingError ITEM_ACCESS_DENIED
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Item Access Denied");

    public static final Version1UpdateRequestItemProcessingError UNABLE_TO_ADD_ELEMENT
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Unable To Add Element");

    public static final Version1UpdateRequestItemProcessingError UNABLE_TO_DELETE_ELEMENT
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Unable To Delete Element");

    public static final Version1UpdateRequestItemProcessingError UNKNOWN_ITEM
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static final Version1UpdateRequestItemProcessingError UNKNOWN_REQUEST
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Request");

    public static final Version1UpdateRequestItemProcessingError UNKNOWN_USER
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "Unknown User");

    public static final Version1UpdateRequestItemProcessingError USER_AUTHENTICATION_FAILED
        = new Version1UpdateRequestItemProcessingError(VERSION_1_UPDATE_REQUEST_ITEM_PROCESSING_ERROR, "User Authentication Failed");

    public static void loadAll() {
        LOG.debug("Loading Version1UpdateRequestItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1UpdateRequestItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
