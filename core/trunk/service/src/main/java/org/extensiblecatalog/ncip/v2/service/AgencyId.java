/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class AgencyId extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(AgencyId.class);

    private static final List<AgencyId> VALUES_LIST = new CopyOnWriteArrayList<AgencyId>();

    public AgencyId(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    public AgencyId(String value) {
        super(value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the AgencyId that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a AgencyId that matches, or null if none is found to match.
     */
    public static AgencyId find(String scheme, String value) throws ServiceException {
        return (AgencyId) find(scheme, value, VALUES_LIST, AgencyId.class);
    }

}
