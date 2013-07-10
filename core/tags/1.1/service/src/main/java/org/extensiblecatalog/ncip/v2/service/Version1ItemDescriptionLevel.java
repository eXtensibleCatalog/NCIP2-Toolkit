/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1ItemDescriptionLevel extends ItemDescriptionLevel {

    private static final Logger LOG = Logger.getLogger(Version1ItemDescriptionLevel.class);

    public static final String VERSION_1_ITEM_DESCRIPTION_LEVEL
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/itemdescriptionlevel/itemdescriptionlevel.scm";

    /**
     * Description of Item is at the level of the Bibliographic Item and contains no
     * identifying information about individual copies or pieces.
     */
    public static final Version1ItemDescriptionLevel BIBLIOGRAPHIC_ITEM = new Version1ItemDescriptionLevel(VERSION_1_ITEM_DESCRIPTION_LEVEL, "Bibliographic Item");
    /**
     * Description of Item is at the level of the individual Item and contains
     * identifying information for the Item, including, as appropriate, volume and
     * issue details and other holdings enumeration and chronology information
     * and/or copy identifiers.
     */
    public static final Version1ItemDescriptionLevel ITEM = new Version1ItemDescriptionLevel(VERSION_1_ITEM_DESCRIPTION_LEVEL, "Item");

    public static void loadAll() {
        LOG.debug("Loading Version1ItemDescriptionLevel.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ItemDescriptionLevel(String scheme, String value) {
        super(scheme, value);
    }
}
