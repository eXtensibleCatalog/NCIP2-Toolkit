/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1LookupUserProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1LookupUserProcessingError.class);

    public static final String VERSION_1_LOOKUP_USER_PROCESSING_ERROR
            = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/lookupuserprocessingerror.scm";

    public static final Version1LookupUserProcessingError ELEMENT_RULE_VIOLATED
            = new Version1LookupUserProcessingError(VERSION_1_LOOKUP_USER_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1LookupUserProcessingError NON_UNIQUE_USER
            = new Version1LookupUserProcessingError(VERSION_1_LOOKUP_USER_PROCESSING_ERROR, "Non-Unique User");

    public static final Version1LookupUserProcessingError UNKNOWN_USER
            = new Version1LookupUserProcessingError(VERSION_1_LOOKUP_USER_PROCESSING_ERROR, "Unknown User");

    public static final Version1LookupUserProcessingError USER_ACCESS_DENIED
            = new Version1LookupUserProcessingError(VERSION_1_LOOKUP_USER_PROCESSING_ERROR, "User Access Denied");

    public static final Version1LookupUserProcessingError USER_AUTHENTICATION_FAILED
            = new Version1LookupUserProcessingError(VERSION_1_LOOKUP_USER_PROCESSING_ERROR, "User Authentication Failed");

    public static void loadAll() {
        LOG.debug("Loading Version1LookupUserProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1LookupUserProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
