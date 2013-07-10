/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1MediumType extends MediumType {

    private static final Logger LOG = Logger.getLogger(Version1MediumType.class);

    public static final String VERSION_1_MEDIUM_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/mediumtype/mediumtype.scm";

    public static final Version1MediumType AUDIO_TAPE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Audio Tape");
    public static final Version1MediumType BOOK = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Book");
    public static final Version1MediumType BOOK_WITH_AUDIO_TAPE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Book With Audio Tape");
    public static final Version1MediumType BOOK_WITH_COMPACT_DISC = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Book With Compact Disc");
    public static final Version1MediumType BOOK_WITH_DISKETTE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Book With Diskette");
    public static final Version1MediumType BOUND_JOURNAL = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Bound Journal");
    public static final Version1MediumType CD_ROM = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "CD-ROM");
    public static final Version1MediumType COMPACT_DISC = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Compact Disc (CD)");
    public static final Version1MediumType DISKETTE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Diskette");
    public static final Version1MediumType MAGAZINE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Magazine");
    public static final Version1MediumType MICROFORM = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Microform");
    public static final Version1MediumType VIDEO_TAPE = new Version1MediumType(VERSION_1_MEDIUM_TYPE, "Video Tape");


    public static void loadAll() {
        LOG.debug("Loading Version1MediumType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1MediumType(String scheme, String value) {
        super(scheme, value);
    }
}
