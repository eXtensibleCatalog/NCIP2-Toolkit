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
 * Location Type (from NCIP version 1's Implementation Profile 1.
 */
public class Version1LocationType extends LocationType {

    private static final Logger LOG = Logger.getLogger(Version1LocationType.class);

    public static final String VERSION_1_LOCATION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/locationtype/locationtype.scm";

    public static final Version1LocationType CURRENT_LOCATION
        = new Version1LocationType(VERSION_1_LOCATION_TYPE, "Current Location");
    public static final Version1LocationType PERMANENT_LOCATION
        = new Version1LocationType(VERSION_1_LOCATION_TYPE, "Permanent Location");
    public static final Version1LocationType TEMPORARY_LOCATION
        = new Version1LocationType(VERSION_1_LOCATION_TYPE, "Temporary Location");

    public static void loadAll() {
        LOG.debug("Loading Version1LocationType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1LocationType(String scheme, String value) {
        super(scheme, value);
    }

}
