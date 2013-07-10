/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1UnstructuredAddressType extends UnstructuredAddressType {

    private static final Logger LOG = Logger.getLogger(Version1UnstructuredAddressType.class);

    public static final String VERSION_1_UNSTRUCTURED_ADDRESS_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/unstructuredaddresstype/unstructuredaddresstype.scm";

    public static final Version1UnstructuredAddressType CARRIAGE_RETURN_NEWLINE_DELIMITED_TEXT
            = new Version1UnstructuredAddressType(VERSION_1_UNSTRUCTURED_ADDRESS_TYPE, "Carriage-Return, Newline-Delimited Text");

    public static final Version1UnstructuredAddressType HTML
            = new Version1UnstructuredAddressType(VERSION_1_UNSTRUCTURED_ADDRESS_TYPE, "HTML");

    public static final Version1UnstructuredAddressType NEWLINE_DELIMITED_TEXT
            = new Version1UnstructuredAddressType(VERSION_1_UNSTRUCTURED_ADDRESS_TYPE, "Newline-Delimited Text");

    public static final Version1UnstructuredAddressType XML
            = new Version1UnstructuredAddressType(VERSION_1_UNSTRUCTURED_ADDRESS_TYPE, "XML");

    public static void loadAll() {
        LOG.debug("Loading Version1UnstructuredAddressType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1UnstructuredAddressType(String scheme, String value) {
        super(scheme, value);
    }

}
