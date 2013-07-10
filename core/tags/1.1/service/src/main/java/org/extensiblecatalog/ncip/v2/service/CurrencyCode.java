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
 * Identifies a currency code.
 */
public class CurrencyCode extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(CurrencyCode.class);

    private static final List<CurrencyCode> VALUES_LIST = new CopyOnWriteArrayList<CurrencyCode>();

    protected int minorUnit = 0;

    public CurrencyCode(String scheme, String value, int minorUnit) {
        super(scheme, value);
        this.minorUnit = minorUnit;
        VALUES_LIST.add(this);
    }

    public CurrencyCode(String value, int minorUnit) {
        super(null, value);
        this.minorUnit = minorUnit;
        VALUES_LIST.add(this);
    }

    
    /**
     * Find the CurrencyCode that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an CurrencyCode that matches, or null if none is found to match.
     */
    public static CurrencyCode find(String scheme, String value) throws ServiceException {
        return (CurrencyCode)find(scheme, value, VALUES_LIST, CurrencyCode.class);
    }

    public int getMinorUnit() {
        return minorUnit;
    }
}
