/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1RequestScopeType extends RequestScopeType {

    private static final Logger LOG = Logger.getLogger(Version1RequestScopeType.class);

    public static final String VERSION_1_REQUEST_SCOPE_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm";

    // Request includes any physical pieces and copies described by the specific Bibliographic Item.
    public static final Version1RequestScopeType BIBLIOGRAPHIC_ITEM
            = new Version1RequestScopeType(VERSION_1_REQUEST_SCOPE_TYPE, "Bibliographic Item");
    // Request is restricted to a specific instance or copy of the bibliographic Item.
    public static final Version1RequestScopeType ITEM
            = new Version1RequestScopeType(VERSION_1_REQUEST_SCOPE_TYPE, "Item");


    public static void loadAll() {
        LOG.debug("Loading Version1RequestScopeType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestScopeType(String scheme, String value) {
        super(scheme, value);
    }
}
