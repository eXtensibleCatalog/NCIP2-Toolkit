/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1CheckInItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1CheckInItemProcessingError.class);

    public static final String VERSION_1_CHECK_IN_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/checkinitemprocessingerror.scm";

    public static final Version1CheckInItemProcessingError ITEM_NOT_CHECKED_OUT
        = new Version1CheckInItemProcessingError(VERSION_1_CHECK_IN_ITEM_PROCESSING_ERROR, "Item Not Checked Out");

    public static final Version1CheckInItemProcessingError UNKNOWN_ITEM
        = new Version1CheckInItemProcessingError(VERSION_1_CHECK_IN_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static void loadAll() {
        LOG.debug("Loading Version1CheckInItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1CheckInItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
