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
 * Note: This scheme is defined in the NCIP version 1 Implementation Profile 1.
 */
public class Version1PhysicalConditionType extends PhysicalConditionType {

    private static final Logger LOG = Logger.getLogger(Version1PhysicalConditionType.class);

    public static final String VERSION_1_PHYSICAL_CONDITION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/physicalconditiontype/physicalconditiontype.scm";

    public static final Version1PhysicalConditionType BAD_URL
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Bad URL");
    public static final Version1PhysicalConditionType BINDING_WEAK
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Binding Weak");
    public static final Version1PhysicalConditionType COLOR_PLATES_MISSING
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Color Plates Missing");
    public static final Version1PhysicalConditionType CORRUPT_OR_UNREADABLE_FILE
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Corrupt Or Unreadable File");
    public static final Version1PhysicalConditionType DISCOLORED
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Discolored");
    public static final Version1PhysicalConditionType FADED
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Faded");
    public static final Version1PhysicalConditionType MARKINGS
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Markings");
    public static final Version1PhysicalConditionType PAGES_MISSING
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Pages Missing");
    public static final Version1PhysicalConditionType PHOTOCOPY_ILLEGIBLE
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Photocopy Illegible");
    public static final Version1PhysicalConditionType SPECIAL_BINDING
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Special Binding");
    public static final Version1PhysicalConditionType WATER_DAMAGE
        = new Version1PhysicalConditionType(VERSION_1_PHYSICAL_CONDITION_TYPE, "Water Damage");

    public static void loadAll() {
        LOG.debug("Loading Version1PhysicalConditionType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1PhysicalConditionType(String scheme, String value) {
        super(scheme, value);
    }

}
