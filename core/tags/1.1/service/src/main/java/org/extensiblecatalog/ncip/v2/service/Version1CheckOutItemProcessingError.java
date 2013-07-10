/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1CheckOutItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1CheckOutItemProcessingError.class);

    public static final String VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/checkoutitemprocessingerror.scm";

    public static final Version1CheckOutItemProcessingError ITEM_DOES_NOT_CIRCULATE
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Item Does Not Circulate");

    public static final Version1CheckOutItemProcessingError MAXIMUM_CHECK_OUTS_EXCEEDED
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Maximum Check Outs Exceeded");

    public static final Version1CheckOutItemProcessingError RESOURCE_CANNOT_BE_PROVIDED
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Resource Cannot Be Provided");

    public static final Version1CheckOutItemProcessingError UNKNOWN_ITEM
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown Item");

    public static final Version1CheckOutItemProcessingError UNKNOWN_REQUEST
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown Request");

    public static final Version1CheckOutItemProcessingError UNKNOWN_USER
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown User");

    public static final Version1CheckOutItemProcessingError UNSUPPORTED_SHIPPING_ADDRESS_TYPE
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unsupported Shipping Address Type");

    public static final Version1CheckOutItemProcessingError USER_AUTHENTICATION_FAILED
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Authentication Failed");

    public static final Version1CheckOutItemProcessingError USER_BLOCKED
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Blocked");

    public static final Version1CheckOutItemProcessingError USER_INELIGIBLE_TO_CHECK_OUT_THIS_ITEM
        = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Ineligible To Check Out This Item");

    public static void loadAll() {
        LOG.debug("Loading Version1CheckOutProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1CheckOutItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
