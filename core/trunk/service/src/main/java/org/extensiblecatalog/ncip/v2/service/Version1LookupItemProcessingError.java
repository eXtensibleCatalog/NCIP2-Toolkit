/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1LookupItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1LookupItemProcessingError.class);

    public static final String VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR
            = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/lookupitemprocessingerror.scm";

    public static final Version1LookupItemProcessingError ELEMENT_RULE_VIOLATED
            = new Version1LookupItemProcessingError(VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1LookupItemProcessingError ITEM_ACCESS_DENIED
            = new Version1LookupItemProcessingError(VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR, "Item Access Denied");

    public static final Version1LookupItemProcessingError NON_UNIQUE_ITEM
            = new Version1LookupItemProcessingError(VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR, "Non-Unique Item");

    public static final Version1LookupItemProcessingError UNKNOWN_ITEM
            = new Version1LookupItemProcessingError(VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static void loadAll() {
        LOG.debug("Loading Version1LookupItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1LookupItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
