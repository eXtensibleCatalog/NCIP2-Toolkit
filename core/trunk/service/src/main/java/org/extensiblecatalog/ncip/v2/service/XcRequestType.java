/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class XcRequestType extends RequestType {

    private static final Logger LOG = Logger.getLogger(XcRequestType.class);

    public static final String XC_REQUEST_TYPE
            = "http://www.extensiblecatalog.ncip.v2.org/schemes/requesttype/requesttype.scm";

    public static final XcRequestType HOLD = new XcRequestType(XC_REQUEST_TYPE, "Hold");

    public static final XcRequestType CALL_SLIP = new XcRequestType(XC_REQUEST_TYPE, "Stack Retrieval");

    public static final XcRequestType RECALL = new XcRequestType(XC_REQUEST_TYPE, "Recall");

    public static void loadAll() {
        LOG.debug("Loading XcRequestType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public XcRequestType(String scheme, String value) {
        super(scheme, value);
    }
}
