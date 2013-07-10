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
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Item Does Not Circulate"); // Request or check out of Item cannot proceed because the Item is non-circulating.

    public static final Version1CheckOutItemProcessingError MAXIMUM_CHECK_OUTS_EXCEEDED
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Maximum Check Outs Exceeded"); // Check out cannot proceed because the User already has the maximum number of items checked out.

    public static final Version1CheckOutItemProcessingError RESOURCE_CANNOT_BE_PROVIDED
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Resource Cannot Be Provided"); // Check out cannot proceed because the desired resource cannot be provided.

    public static final Version1CheckOutItemProcessingError UNKNOWN_ITEM
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown Item"); // Item is not known.

    public static final Version1CheckOutItemProcessingError UNKNOWN_REQUEST
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown Request"); // Request is not known.

    public static final Version1CheckOutItemProcessingError UNKNOWN_USER
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unknown User"); // User is not known.

    public static final Version1CheckOutItemProcessingError UNSUPPORTED_SHIPPING_ADDRESS_TYPE
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "Unsupported Shipping Address Type"); // The address type supplied in Shipping Information is not supported. The problem value may be either a Physical Address Type or an Electronic Address Type.

    public static final Version1CheckOutItemProcessingError USER_AUTHENTICATION_FAILED
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Authentication Failed"); // Attempt to authenticate User was unsuccessful; the reason for the failure is unspecified.

    public static final Version1CheckOutItemProcessingError USER_BLOCKED
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Blocked"); // Circulation service, such as check out, renew, or request, cannot proceed because User information indicates that the User is blocked from access to the specified service.

    public static final Version1CheckOutItemProcessingError USER_INELIGIBLE_TO_CHECK_OUT_THIS_ITEM
            = new Version1CheckOutItemProcessingError(VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR, "User Ineligible To Check Out This Item"); // Item Use Restriction Type, in conjunction with the User Privilege, prevents the Item from being checked out to the User.

    public static void loadAll() {
        LOG.debug("Loading Version1CheckOutProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1CheckOutItemProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
