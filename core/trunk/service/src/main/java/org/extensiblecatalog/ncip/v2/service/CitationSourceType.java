/**
 * Copyright (c) 2012 eXtensible Catalog Organization
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
 * Identifies the type of CitationSourceType.
 */
public class CitationSourceType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(CitationSourceType.class);

    private static final List<CitationSourceType> VALUES_LIST = new CopyOnWriteArrayList<CitationSourceType>();

    public CitationSourceType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the CitationSourceType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an CitationSourceType that matches, or null if none is found to match.
     */
    public static CitationSourceType find(String scheme, String value) throws ServiceException {
        return (CitationSourceType) find(scheme, value, VALUES_LIST, CitationSourceType.class);
    }

}
