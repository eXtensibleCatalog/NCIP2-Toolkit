/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Identifies a primitive element by which the containing element will be sorted in a response message.
 */
public class SortField extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(SortField.class);

    private static final List<SortField> VALUES_LIST = new CopyOnWriteArrayList<SortField>();

    public SortField(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the SortField that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a SortField that matches, or null if none is found to match.
     */
    public static SortField find(String scheme, String value) throws ServiceException {
        return (SortField)find(scheme, value, VALUES_LIST, SortField.class);
    }

}
