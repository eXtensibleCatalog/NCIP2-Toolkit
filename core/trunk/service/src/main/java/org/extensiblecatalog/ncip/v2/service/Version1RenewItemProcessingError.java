/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RenewItemProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1RenewItemProcessingError.class);

    public static final String VERSION_1_RENEW_ITEM_PROCESSING_ERROR
            = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/renewitemprocessingerror.scm";

    public static final Version1RenewItemProcessingError ITEM_NOT_CHECKED_OUT
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Item Not Checked Out");
    public static final Version1RenewItemProcessingError ITEM_NOT_RENEWABLE
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Item Not Renewable");
    public static final Version1RenewItemProcessingError MAXIMUM_RENEWALS_EXCEEDED
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Maximum Renewals Exceeded");
    public static final Version1RenewItemProcessingError RENEWAL_NOT_ALLOWED_ITEM_HAS_OUTSTANDING_REQUESTS
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Renewal Not Allowed - Item Has Outstanding Requests");
    public static final Version1RenewItemProcessingError UNKNOWN_ITEM
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Unknown Item");
    public static final Version1RenewItemProcessingError UNKNOWN_USER
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "Unknown User");
    public static final Version1RenewItemProcessingError USER_AUTHENTICATION_FAILED
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "User Authentication Failed");
    public static final Version1RenewItemProcessingError USER_BLOCKED
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "User Blocked");
    public static final Version1RenewItemProcessingError USER_INELIGIBLE_TO_RENEW_THIS_ITEM
            = new Version1RenewItemProcessingError(VERSION_1_RENEW_ITEM_PROCESSING_ERROR, "User Ineligible To Renew This Item");

    public static void loadAll() {
        LOG.debug("Loading Version1RenewItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RenewItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
