/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

// TODO: Get actual values defined for this Scheme - it's not in any of the documents
public class Version1LookupRequestProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1LookupRequestProcessingError.class);

    public static final String VERSION_1_LOOKUP_REQUEST_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/lookuprequestprocessingerror.scm";

    public static final Version1LookupRequestProcessingError ELEMENT_RULE_VIOLATED
        = new Version1LookupRequestProcessingError(VERSION_1_LOOKUP_REQUEST_PROCESSING_ERROR, "Element Rule Violated");

    public static final Version1LookupRequestProcessingError UNKNOWN_REQUEST
        = new Version1LookupRequestProcessingError(VERSION_1_LOOKUP_REQUEST_PROCESSING_ERROR, "Unknown Request");

    public static void loadAll() {
        LOG.debug("Loading Version1LookupRequestProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1LookupRequestProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
