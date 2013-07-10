/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1ComponentIdentifierType extends ComponentIdentifierType {

    private static final Logger LOG = Logger.getLogger(Version1ComponentIdentifierType.class);

    public static final String VERSION_1_COMPONENT_IDENTIFIER_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/componentidentifiertype/componentidentifiertype.scm";

    public static final Version1ComponentIdentifierType BICI
        = new Version1ComponentIdentifierType(VERSION_1_COMPONENT_IDENTIFIER_TYPE, "BICI");
    public static final Version1ComponentIdentifierType SICI
        = new Version1ComponentIdentifierType(VERSION_1_COMPONENT_IDENTIFIER_TYPE, "SICI");

    public static void loadAll() {
        LOG.debug("Loading Version1ComponentIdentifierType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ComponentIdentifierType(String scheme, String value) {
        super(scheme, value);
    }
}
