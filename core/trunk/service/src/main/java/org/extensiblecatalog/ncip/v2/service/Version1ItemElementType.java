/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1ItemElementType extends ItemElementType {

    private static final Logger LOG = Logger.getLogger(Version1ItemElementType.class);

    public static final String VERSION_1_ITEM_ELEMENT_TYPE
            = "http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm";

    public static final Version1ItemElementType BIBLIOGRAPHIC_DESCRIPTION = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Bibliographic Description");
    public static final Version1ItemElementType CIRCULATION_STATUS = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Circulation Status");
    public static final Version1ItemElementType ELECTRONIC_RESOURCE = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Electronic Resource");
    public static final Version1ItemElementType HOLD_QUEUE_LENGTH = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Hold Queue Length");
    public static final Version1ItemElementType ITEM_DESCRIPTION = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Item Description");
    public static final Version1ItemElementType ITEM_USE_RESTRICTION_TYPE = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Item Use Restriction Type");
    public static final Version1ItemElementType LOCATION = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Location");
    public static final Version1ItemElementType PHYSICAL_CONDITION = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Physical Condition");
    public static final Version1ItemElementType SECURITY_MARKER = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Security Marker");
    public static final Version1ItemElementType SENSITIZATION_FLAG = new Version1ItemElementType(VERSION_1_ITEM_ELEMENT_TYPE, "Sensitization Flag");

    public static void loadAll() {
        LOG.debug("Loading Version1ItemElementType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ItemElementType(String scheme, String value) {
        super(scheme, value);
    }
}
