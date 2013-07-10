/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1UserElementType extends UserElementType {

    private static final Logger LOG = Logger.getLogger(Version1UserElementType.class);

    public static final String VERSION_1_USER_ELEMENT_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/userelementtype/userelementtype.scm";

    public static final Version1UserElementType AUTHENTICATION_INPUT
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "Authentication Input");
    public static final Version1UserElementType BLOCK_OR_TRAP
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "Block Or Trap");
    public static final Version1UserElementType DATE_OF_BIRTH
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "Date Of Birth");
    public static final Version1UserElementType NAME_INFORMATION
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "Name Information");
    public static final Version1UserElementType USER_ADDRESS_INFORMATION
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "User Address Information");
    public static final Version1UserElementType USER_LANGUAGE
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "User Language");
    public static final Version1UserElementType USER_PRIVILEGE
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "User Privilege");
    public static final Version1UserElementType USER_ID
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "User Id");
    public static final Version1UserElementType PREVIOUS_USER_ID
        = new Version1UserElementType(VERSION_1_USER_ELEMENT_TYPE, "Previous User Id");

    public static void loadAll() {
        LOG.debug("Loading Version1UserElementType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1UserElementType(String scheme, String value) {
        super(scheme, value);
    }
}
