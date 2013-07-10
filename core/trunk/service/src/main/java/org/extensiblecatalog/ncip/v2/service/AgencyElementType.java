/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Identifies the type of AgencyElementType.
 */
public class AgencyElementType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(AgencyElementType.class);

    private static final List<AgencyElementType> VALUES_LIST = new CopyOnWriteArrayList<AgencyElementType>();

    public AgencyElementType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the AgencyElementType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an AgencyElementType that matches, or null if none is found to match.
     */
    public static AgencyElementType find(String scheme, String value) throws ServiceException {
        return (AgencyElementType) find(scheme, value, VALUES_LIST, AgencyElementType.class);
    }

    public static Iterator<AgencyElementType> iterator() {

        return VALUES_LIST.iterator();

    }

}
