/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class NCIPVersion implements ProtocolVersion {

    private static final Logger LOG = Logger.getLogger(NCIPVersion.class);

    protected final String version;
    protected final int ordinalPosition;
    protected final String[] aliases;

    protected NCIPVersion(String version, int ordinalPosition, String[] aliases) {

        this.version = version;
        this.ordinalPosition = ordinalPosition;
        this.aliases = aliases;

    }

    public static final NCIPVersion VERSION_2_0 = new NCIPVersion("http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd", 1, new String[0]);
    public static final NCIPVersion VERSION_2_01 = new NCIPVersion("http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_01.xsd", 2, new String[0]);

    @Override
    public String getVersion() {

        return this.version;

    }

    public static ProtocolVersion valueOf(String version) {

        ProtocolVersion result;
        if ( VERSION_2_0.getVersion().compareToIgnoreCase(version) == 0 ) {

            result = VERSION_2_0;

        } else if ( VERSION_2_01.getVersion().compareToIgnoreCase(version) == 0 ) {

            result = VERSION_2_01;

        } else {

            result = null;

        }

        return result;
    }

    public int getOrdinalPosition() {

        return ordinalPosition;

    }

    @Override
    public String[] getAliases() {
        // So far, no aliases
        return new String[0];
    }

}
