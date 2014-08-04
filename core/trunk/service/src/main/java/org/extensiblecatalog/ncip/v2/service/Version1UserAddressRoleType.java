/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1UserAddressRoleType extends UserAddressRoleType {

    private static final Logger LOG = Logger.getLogger(Version1UserAddressRoleType.class);

    public static final String VERSION_1_USER_ADDRESS_ROLE_TYPE
        = "http://www.niso.org/ncip/v2_0/imp1/schemes/useraddressroletype/useraddressroletype.scm";

    // Address to which bills for the User are to be sent
    public static final Version1UserAddressRoleType BILL_TO = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Bill To");

    // Home address of the User
    public static final Version1UserAddressRoleType HOME = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Home");

    // Address used for most purposes when communicating with the User
    public static final Version1UserAddressRoleType MULTI_PURPOSE = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Multi-Purpose");

    // Address to which notices to the User are to be sent
    public static final Version1UserAddressRoleType NOTICE = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Notice");

    // Address to which material destined for the User is to be shipped
    public static final Version1UserAddressRoleType SHIP_TO = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Ship To");

    // Work address of the User
    public static final Version1UserAddressRoleType WORK = new Version1UserAddressRoleType(VERSION_1_USER_ADDRESS_ROLE_TYPE, "Work");

    public static void loadAll() {
        LOG.debug("Loading Version1UserAddressRoleType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1UserAddressRoleType(String scheme, String value) {
        super(scheme, value);
    }
}
