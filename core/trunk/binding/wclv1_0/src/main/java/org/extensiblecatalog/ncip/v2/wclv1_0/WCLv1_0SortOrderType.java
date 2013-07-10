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
import org.extensiblecatalog.ncip.v2.service.SortOrderType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLv1_0SortOrderType extends SortOrderType {

    private static final Logger LOG = Logger.getLogger(WCLv1_0SortOrderType.class);

    private static final List<WCLv1_0SortOrderType> VALUES_LIST = new CopyOnWriteArrayList<WCLv1_0SortOrderType>();

    public WCLv1_0SortOrderType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLv1_0SortOrderType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLv1_0SortOrderType that matches, or null if none is found to match.
     */
    public static WCLv1_0SortOrderType find(String scheme, String value) throws ServiceException {
        return (WCLv1_0SortOrderType)find(scheme, value, VALUES_LIST, WCLv1_0SortOrderType.class);
    }

    public static final String VERSION_1_WCL_SORT_ORDER_TYPE
        = "http://worldcat.org/ncip/schemes/v2/extensions/sortordertype.scm";

    public static final WCLv1_0SortOrderType ASCENDING = new WCLv1_0SortOrderType(VERSION_1_WCL_SORT_ORDER_TYPE, "Ascending");
    public static final WCLv1_0SortOrderType DESCENDING = new WCLv1_0SortOrderType(VERSION_1_WCL_SORT_ORDER_TYPE, "Descending");

    public static void loadAll() {
        LOG.debug("Loading WCLv1_0SortOrderType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
