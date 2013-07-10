/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1AgencyAddressRoleType extends AgencyAddressRoleType {

    private static final Logger LOG = Logger.getLogger(Version1AgencyAddressRoleType.class);

    public static final String VERSION_1_AGENCY_ADDRESS_ROLE_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/agencyaddressroletype/agencyaddressroletype.scm";

    // Address to which bills for the Agency are to be sent.
    public static final Version1AgencyAddressRoleType BILL_TO
            = new Version1AgencyAddressRoleType(VERSION_1_AGENCY_ADDRESS_ROLE_TYPE, "Bill To");
    // Address used for most purposes when communicating with the Agency.
    public static final Version1AgencyAddressRoleType MULTI_PURPOSE
            = new Version1AgencyAddressRoleType(VERSION_1_AGENCY_ADDRESS_ROLE_TYPE, "Multi-Purpose");
    // Official address of the Agency.
    public static final Version1AgencyAddressRoleType OFFICIAL
            = new Version1AgencyAddressRoleType(VERSION_1_AGENCY_ADDRESS_ROLE_TYPE, "Official");
    // Address from which the Agency ships material.
    public static final Version1AgencyAddressRoleType SHIP_FROM
            = new Version1AgencyAddressRoleType(VERSION_1_AGENCY_ADDRESS_ROLE_TYPE, "Ship From");
    // Address to which material destined for the Agency is to be shipped.
    public static final Version1AgencyAddressRoleType SHIP_TO
            = new Version1AgencyAddressRoleType(VERSION_1_AGENCY_ADDRESS_ROLE_TYPE, "Ship To");

    public static void loadAll() {
        LOG.debug("Loading Version1AgencyAddressRoleType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1AgencyAddressRoleType(String scheme, String value) {
        super(scheme, value);
    }
}
