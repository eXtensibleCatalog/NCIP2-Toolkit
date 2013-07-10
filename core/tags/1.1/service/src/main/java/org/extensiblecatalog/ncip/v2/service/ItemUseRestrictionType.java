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

public class ItemUseRestrictionType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(ItemUseRestrictionType.class);

    private static final List<ItemUseRestrictionType> VALUES_LIST = new CopyOnWriteArrayList<ItemUseRestrictionType>();

    public ItemUseRestrictionType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the ItemUseRestrictionType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an ItemUseRestrictionType that matches, or null if none is found to match.
     */
    public static ItemUseRestrictionType find(String scheme, String value) throws ServiceException {
        return (ItemUseRestrictionType)find(scheme, value, VALUES_LIST, ItemUseRestrictionType.class);
    }

}

