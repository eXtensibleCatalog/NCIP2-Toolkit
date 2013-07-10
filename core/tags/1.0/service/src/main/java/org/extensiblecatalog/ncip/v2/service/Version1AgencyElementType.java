/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1AgencyElementType extends AgencyElementType {

    private static final Logger LOG = Logger.getLogger(Version1AgencyElementType.class);

    public static final String VERSION_1_AGENCY_ELEMENT_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/agencyelementtype/agencyelementtype.scm";

    public static final Version1AgencyElementType AGENCY_ADDRESS_INFORMATION
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Agency Address Information");
    public static final Version1AgencyElementType AGENCY_USER_PRIVILEGE_TYPE
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Agency User Privilege Type");
    public static final Version1AgencyElementType APPLICATION_PROFILE_SUPPORTED_TYPE
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Application Profile Supported Type");
    public static final Version1AgencyElementType AUTHENTICATION_PROMPT
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Authentication Prompt");
    public static final Version1AgencyElementType CONSORTIUM_AGREEMENT
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Consortium Agreement");
    public static final Version1AgencyElementType ORGANIZATION_NAME_INFORMATION
        = new Version1AgencyElementType(VERSION_1_AGENCY_ELEMENT_TYPE, "Organization Name Information");

    public static void loadAll() {
        LOG.debug("Loading Version1AgencyElementType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1AgencyElementType(String scheme, String value) {
        super(scheme, value);
    }
}
