/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import java.util.List;

public class FromSystemId extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(FromSystemId.class);
    private static final List<FromSystemId> VALUES_LIST = new CopyOnWriteArrayList<FromSystemId>();

    public FromSystemId(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    public FromSystemId(String value) {
        super(value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the FromSystemId that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a FromSystemId that matches, or null if none is found to match.
     */
    public static FromSystemId find(String scheme, String value) throws ServiceException {
        return (FromSystemId) find(scheme, value, VALUES_LIST, FromSystemId.class);
    }

}
