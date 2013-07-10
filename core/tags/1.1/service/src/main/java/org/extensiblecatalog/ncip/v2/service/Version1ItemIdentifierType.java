/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

/**
 * Note: This is in fact inherited from the NCIP version 1 VisibleItemIdentifierType, but
 * as the VisibleItemIdentifier element was renamed ItemIdentifier with version 2,
 * that is the name used for this scheme.
 */
public class Version1ItemIdentifierType extends ItemIdentifierType {

    private static final Logger LOG = Logger.getLogger(Version1ItemIdentifierType.class);

    public static final String VERSION_1_ITEM_IDENTIFIER_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/visibleitemidentifiertype/visibleitemidentifiertype.scm";

    public static final Version1ItemIdentifierType ACCESSION_NUMBER
        = new Version1ItemIdentifierType(VERSION_1_ITEM_IDENTIFIER_TYPE, "Accession Number");
    public static final Version1ItemIdentifierType BARCODE
        = new Version1ItemIdentifierType(VERSION_1_ITEM_IDENTIFIER_TYPE, "Barcode");

    public static void loadAll() {
        LOG.debug("Loading Version1ItemIdentifierType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ItemIdentifierType(String scheme, String value) {
        super(scheme, value);
    }

}
