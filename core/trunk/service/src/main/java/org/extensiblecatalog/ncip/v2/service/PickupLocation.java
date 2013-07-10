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
 * Identifies the type of pickup location. Note there are no instances of this class, meaning there are
 * no defined values in the standard and if this element is to be used, values must be specified in
 * the configuration.
 */
public class PickupLocation extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(PickupLocation.class);

    private static final List<PickupLocation> VALUES_LIST = new CopyOnWriteArrayList<PickupLocation>();

    public PickupLocation(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    public PickupLocation(String value) {
        super(value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the PickupLocation that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an PickupLocation that matches, or null if none is found to match.
     */
    public static PickupLocation find(String scheme, String value) throws ServiceException {
        return (PickupLocation) find(scheme, value, VALUES_LIST, PickupLocation.class);
    }

}
