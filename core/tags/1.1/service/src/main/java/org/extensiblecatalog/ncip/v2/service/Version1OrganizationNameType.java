/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1OrganizationNameType extends OrganizationNameType {

    private static final Logger LOG = Logger.getLogger(Version1OrganizationNameType.class);

    public static final String VERSION_1_ORGANIZATION_NAME_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/organizationnametype/organizationnametype.scm";

    public static final Version1OrganizationNameType ABBREVIATION_OR_ACRONYM = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Abbreviation Or Acronym");
    public static final Version1OrganizationNameType ALTERNATIVE_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Alternative Name");
    public static final Version1OrganizationNameType CONVERTED_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Converted Name");
    public static final Version1OrganizationNameType DISTINGUISHED_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Distinguished Name");
    public static final Version1OrganizationNameType OFFICIAL_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Official Name");
    public static final Version1OrganizationNameType TRANSLATED_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Translated Name");
    public static final Version1OrganizationNameType TRANSLITERATED_NAME = new Version1OrganizationNameType(VERSION_1_ORGANIZATION_NAME_TYPE, "Transliterated Name");

    public static void loadAll() {
        LOG.debug("Loading Version1OrganizationNameType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1OrganizationNameType(String scheme, String value) {
        super(scheme, value);
    }
}
