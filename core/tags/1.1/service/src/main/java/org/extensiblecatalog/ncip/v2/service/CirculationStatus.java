/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.log4j.Logger;
import java.util.List;

/**
 * Indicates the current availability status of an item.
 */
public class CirculationStatus extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(CirculationStatus.class);

    private static final List<CirculationStatus> VALUES_LIST = new CopyOnWriteArrayList<CirculationStatus>();

    public CirculationStatus(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the CirculationStatus that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a CirculationStatus that matches, or null if none is found to match.
     */
    public static CirculationStatus find(String scheme, String value) throws ServiceException {
        return (CirculationStatus)find(scheme, value, VALUES_LIST, CirculationStatus.class);
    }

}
