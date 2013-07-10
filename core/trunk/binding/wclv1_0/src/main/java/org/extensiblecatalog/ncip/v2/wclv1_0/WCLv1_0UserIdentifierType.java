/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UserIdentifierType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLv1_0UserIdentifierType extends UserIdentifierType {

    private static final Logger LOG = Logger.getLogger(WCLv1_0UserIdentifierType.class);

    private static final List<WCLv1_0UserIdentifierType> VALUES_LIST = new CopyOnWriteArrayList<WCLv1_0UserIdentifierType>();

    public WCLv1_0UserIdentifierType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLv1_0UserIdentifierType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLv1_0UserIdentifierType that matches, or null if none is found to match.
     */
    public static WCLv1_0UserIdentifierType find(String scheme, String value) throws ServiceException {
        return (WCLv1_0UserIdentifierType)find(scheme, value, VALUES_LIST, WCLv1_0UserIdentifierType.class);
    }

    public static final String VERSION_1_WCL_USER_IDENTIFIER_TYPE
        = "http://worldcat.org/ncip/schemes/v2/extensions/useridentifiertype.scm";

    public static final WCLv1_0UserIdentifierType EIDM = new WCLv1_0UserIdentifierType(VERSION_1_WCL_USER_IDENTIFIER_TYPE, "EIDM");

    public static void loadAll() {
        LOG.debug("Loading WCLv1_0UserIdentifierType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
