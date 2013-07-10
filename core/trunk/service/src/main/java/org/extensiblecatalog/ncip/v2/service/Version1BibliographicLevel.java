/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1BibliographicLevel extends BibliographicLevel {

    private static final Logger LOG = Logger.getLogger(Version1BibliographicLevel.class);

    public static final String VERSION_1_BIBLIOGRAPHIC_LEVEL
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographiclevel/bibliographiclevel.scm";

    // Bibliographic Item describes a collection, i.e., a group of Items treated as a unit.
    public static final Version1BibliographicLevel COLLECTION
            = new Version1BibliographicLevel(VERSION_1_BIBLIOGRAPHIC_LEVEL, "Collection");
    // Bibliographic Item describes a monograph, i.e., a non-serial bibliographic Item, which is either complete in one part or is complete, or intended to be complete, in a finite number of separate parts.
    public static final Version1BibliographicLevel MONOGRAPH
            = new Version1BibliographicLevel(VERSION_1_BIBLIOGRAPHIC_LEVEL, "Monograph");
    // Bibliographic Item describes a unit of a monograph, such as a volume of a multi-part monograph or a chapter within a monograph.
    public static final Version1BibliographicLevel MONOGRAPHIC_COMPONENT_PART
            = new Version1BibliographicLevel(VERSION_1_BIBLIOGRAPHIC_LEVEL, "Monographic Component Part");
    // Bibliographic Item describes a publication issued in successive parts, usually having numerical and/or chronological designation, and intended to be continued indefinitely.
    public static final Version1BibliographicLevel SERIAL
            = new Version1BibliographicLevel(VERSION_1_BIBLIOGRAPHIC_LEVEL, "Serial");
    // Bibliographic Item describes a unit of a serial, such as an issue of a serial, or an article within an issue.
    public static final Version1BibliographicLevel SERIAL_COMPONENT_PART
            = new Version1BibliographicLevel(VERSION_1_BIBLIOGRAPHIC_LEVEL, "Serial Component Part");

    public static void loadAll() {
        LOG.debug("Loading Version1BibliographicLevel.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1BibliographicLevel(String scheme, String value) {
        super(scheme, value);
    }
}
