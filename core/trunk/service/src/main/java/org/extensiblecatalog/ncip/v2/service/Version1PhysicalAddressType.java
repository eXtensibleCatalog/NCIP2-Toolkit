/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1PhysicalAddressType extends PhysicalAddressType {

    private static final Logger LOG = Logger.getLogger(Version1PhysicalAddressType.class);

    public static final String VERSION_1_PHYSICAL_ADDRESS_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/physicaladdresstype/physicaladdresstype.scm";

    public static final Version1PhysicalAddressType POSTAL_ADDRESS
            = new Version1PhysicalAddressType(VERSION_1_PHYSICAL_ADDRESS_TYPE, "Postal Address");

    public static final Version1PhysicalAddressType STREET_ADDRESS
            = new Version1PhysicalAddressType(VERSION_1_PHYSICAL_ADDRESS_TYPE, "Street Address");

    public static void loadAll() {
        LOG.debug("Loading Version1PhysicalAddressType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1PhysicalAddressType(String scheme, String value) {
        super(scheme, value);
    }

}
