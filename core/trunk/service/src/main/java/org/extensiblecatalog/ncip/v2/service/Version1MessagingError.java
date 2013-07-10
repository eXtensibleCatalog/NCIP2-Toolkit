/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1MessagingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1MessagingError.class);

    public static final String VERSION_1_MESSAGING_ERROR
            = "http://www.niso.org/ncip/v1_0/schemes/messagingerrortype/messagingerrortype.scm";

    public static final Version1LookupItemProcessingError INVALID_MESSAGE_SYNTAX_ERROR
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Invalid Message Syntax Error");

    public static final Version1LookupItemProcessingError PROTOCOL_ERROR
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Protocol Error");

    public static final Version1LookupItemProcessingError UNKNOWN_ELEMENT
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Unknown Element");

    public static final Version1LookupItemProcessingError UNKNOWN_SCHEME
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Unknown Scheme");

    public static final Version1LookupItemProcessingError UNKNOWN_SERVICE
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Unknown Service");

    public static final Version1LookupItemProcessingError UNKNOWN_VALUE_FROM_KNOWN_SCHEME
            = new Version1LookupItemProcessingError(VERSION_1_MESSAGING_ERROR, "Unknown Value From Known Scheme");

    public static void loadAll() {
        LOG.debug("Loading Version1MessagingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1MessagingError(String scheme, String value) {
        super(scheme, value);
    }
}
