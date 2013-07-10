/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLApplicationProfileType extends ApplicationProfileType {

    private static final Logger LOG = Logger.getLogger(WCLApplicationProfileType.class);

    private static final List<WCLApplicationProfileType> VALUES_LIST = new CopyOnWriteArrayList<WCLApplicationProfileType>();

    public WCLApplicationProfileType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLApplicationProfileType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLApplicationProfileType that matches, or null if none is found to match.
     */
    public static WCLApplicationProfileType find(String scheme, String value) throws ServiceException {
        return (WCLApplicationProfileType)find(scheme, value, VALUES_LIST, WCLApplicationProfileType.class);
    }

    public static final String VERSION_1_WCL_APPLICATION_PROFILE_TYPE
        = "http://worldcat.org/ncip/schemes/v2/extensions/applicationprofiletype.scm";

    public static final WCLApplicationProfileType VERSION_2011 = new WCLApplicationProfileType(VERSION_1_WCL_APPLICATION_PROFILE_TYPE, "Version 2011");

    public static void loadAll() {
        LOG.debug("Loading WCLApplicationProfileType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
