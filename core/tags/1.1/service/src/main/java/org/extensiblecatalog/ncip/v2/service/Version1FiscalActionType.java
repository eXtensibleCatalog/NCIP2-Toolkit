/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1FiscalActionType extends FiscalActionType {

    private static final Logger LOG = Logger.getLogger(Version1FiscalActionType.class);

    public static final String VERSION_1_FISCAL_ACTION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/fiscalactiontype/fiscalactiontype.scm";

    public static final Version1FiscalActionType ASSESS
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Assess");

    public static final Version1FiscalActionType CANCEL
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Cancel");

    public static final Version1FiscalActionType FORGIVE
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Forgive");

    public static final Version1FiscalActionType PAYMENT
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Payment");
   
    public static final Version1FiscalActionType PENALTY
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Penalty");
    
    public static final Version1FiscalActionType WAIVE
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Waive");

    public static final Version1FiscalActionType WRITE_OFF
        = new Version1FiscalActionType(VERSION_1_FISCAL_ACTION_TYPE, "Write Off");


    public static void loadAll() {
        LOG.debug("Loading Version1FiscalActionType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1FiscalActionType(String scheme, String value) {
        super(scheme, value);
    }
}
