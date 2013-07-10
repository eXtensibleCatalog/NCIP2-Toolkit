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
public class Version1RequestedActionType extends RequestedActionType {

    private static final Logger LOG = Logger.getLogger(Version1RequestedActionType.class);

    public static final String VERSION_1_REQUESTED_ACTION_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/requestedactiontype/requestedactiontype.scm";

    public static final Version1RequestedActionType CIRCULATE
            = new Version1RequestedActionType(VERSION_1_REQUESTED_ACTION_TYPE, "Circulate");
    public static final Version1RequestedActionType CIRCULATE_AND_NOTIFY
            = new Version1RequestedActionType(VERSION_1_REQUESTED_ACTION_TYPE, "Circulate And Notify");
    public static final Version1RequestedActionType HOLD_FOR_PICKUP
            = new Version1RequestedActionType(VERSION_1_REQUESTED_ACTION_TYPE, "Hold For Pickup");
    public static final Version1RequestedActionType HOLD_FOR_PICKUP_AND_NOTIFY
            = new Version1RequestedActionType(VERSION_1_REQUESTED_ACTION_TYPE, "Hold For Pickup And Notify");

    public static void loadAll() {
        LOG.debug("Loading Version1RequestedActionType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1RequestedActionType(String scheme, String value) {
        super(scheme, value);
    }

}
