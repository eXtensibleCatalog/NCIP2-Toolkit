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
 * Identifies the data format of an electronic item.
 */
public class ElectronicDataFormatType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(ElectronicDataFormatType.class);

    private static final List<ElectronicDataFormatType> VALUES_LIST = new CopyOnWriteArrayList<ElectronicDataFormatType>();

    public ElectronicDataFormatType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the ItemIdentifierType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a ItemIdentifierType that matches, or null if none is found to match.
     */
    public static ElectronicDataFormatType find(String scheme, String value) throws ServiceException {
        return (ElectronicDataFormatType) find(scheme, value, VALUES_LIST, ElectronicDataFormatType.class);
    }

}
