/**
 * Copyright (c) 2013 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Identifies the kind of substitution of other editions than the one specified in the request
 * that the user will allow.
 */
public class EditionSubstitutionType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(EditionSubstitutionType.class);

    private static final List<EditionSubstitutionType> VALUES_LIST = new CopyOnWriteArrayList<EditionSubstitutionType>();

    public EditionSubstitutionType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the EditionSubstitutionType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an EditionSubstitutionType that matches, or null if none is found to match.
     */
    public static EditionSubstitutionType find(String scheme, String value) throws ServiceException {
        return (EditionSubstitutionType) find(scheme, value, VALUES_LIST, EditionSubstitutionType.class);
    }

    public static Iterator<EditionSubstitutionType> iterator() {

        return VALUES_LIST.iterator();

    }

}
