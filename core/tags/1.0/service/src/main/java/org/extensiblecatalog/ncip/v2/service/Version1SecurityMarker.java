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
 * Security Marker (from NCIP version 1's Implementation Profile 1.
 */
public class Version1SecurityMarker extends SecurityMarker {

    private static final Logger LOG = Logger.getLogger(Version1SecurityMarker.class);

    public static final String VERSION_1_SECURITY_MARKER
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/securitymarker/securitymarker.scm";

    public static final Version1SecurityMarker CHECKPOINT_EMAG
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Checkpoint emag");
    public static final Version1SecurityMarker CHECKPOINT_RFID
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Checkpoint RFID");
    public static final Version1SecurityMarker GEMPLUS_RFID
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Gemplus RFID");
    public static final Version1SecurityMarker GUARDIAN_EMAG
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Guardian emag");
    public static final Version1SecurityMarker KETEC_RFID
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Ketec RFID");
    public static final Version1SecurityMarker KNOGO_EMAG
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Knogo emag");
    public static final Version1SecurityMarker LIB_CHIP
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Lib-Chip");
    public static final Version1SecurityMarker NONE
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "None");
    public static final Version1SecurityMarker PGP
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "PGP");
    public static final Version1SecurityMarker PROTEXIT_EMAG
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Protexit emag");
    public static final Version1SecurityMarker SENSORMATIC_EMAG
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Sensormatic emag");
    public static final Version1SecurityMarker TAG_IT
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Tag-It");
    public static final Version1SecurityMarker TATTLE_TAPE_SECURITY_STRIP
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Tattle-Tape Security Strip");
    public static final Version1SecurityMarker ULTRA_MAX
        = new Version1SecurityMarker(VERSION_1_SECURITY_MARKER, "Ultra-Max");

    public static void loadAll() {
        LOG.debug("Loading Version1SecurityMarker.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1SecurityMarker(String scheme, String value) {
        super(scheme, value);
    }

}
