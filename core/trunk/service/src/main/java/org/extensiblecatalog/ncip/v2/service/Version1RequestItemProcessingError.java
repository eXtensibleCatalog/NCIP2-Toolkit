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
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Duplicate Request"); // Request for the Item already exists; acting on this update would create a duplicate request for the Item for the User.
    public static final Version1RequestItemProcessingError ELEMENT_RULE_VIOLATED
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Element Rule Violated"); // Known and valid element contains a locally unacceptable value. For example, this error would be appropriate when a "date" type includes a value outside a range deemed valid by the responding application.
    public static final Version1RequestItemProcessingError ITEM_DOES_NOT_CIRCULATE
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Item Does Not Circulate"); // Request or check out of Item cannot proceed because the Item is non-circulating.
    public static final Version1RequestItemProcessingError ITEM_NOT_AVAILABLE_BY_NEED_BEFORE_DATE
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Item Not Available By Need Before Date"); // Item requested will not be available by the date/time the User needs the Item.
    public static final Version1RequestItemProcessingError UNKNOWN_ITEM
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Unknown Item"); // Item is not known.
    public static final Version1RequestItemProcessingError UNKNOWN_USER
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "Unknown User"); // User is not known.
    public static final Version1RequestItemProcessingError USER_AUTHENTICATION_FAILED
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Authentication Failed"); // Attempt to authenticate User was unsuccessful; the reason for the failure is unspecified.
    public static final Version1RequestItemProcessingError USER_BLOCKED
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Blocked"); // Circulation service, such as check out, renew, or request, cannot proceed because User information indicates that the User is blocked from access to the specified service.
    public static final Version1RequestItemProcessingError USER_INELIGIBLE_TO_REQUEST_THIS_ITEM
            = new Version1RequestItemProcessingError(VERSION_1_REQUEST_ITEM_PROCESSING_ERROR, "User Ineligible To Request This Item"); // Item Use Restriction Type, in conjunction with the User Privilege, prevents the Item from being requested by the User.

    public static void loadAll() {
        LOG.debug("Loading Version1RequestItemProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
