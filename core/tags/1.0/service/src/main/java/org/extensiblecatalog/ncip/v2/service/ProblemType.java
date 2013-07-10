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
 * Identifies the type of problem which occurred during processing of an NCIP service.
 * All enumerated types that can be carried in a Problem Type element (e.g. Version1GeneralProcessingError)
 * extend this, which allows the following:
 * if ( ProblemType.ITEM_NOT_RENEWABLE.equals(RenewItemProcessingError.ITEM_NOT_RENEWABLE) ) // Always true
 */
public class ProblemType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(ProblemType.class);

    private static final List<ProblemType> VALUES_LIST = new CopyOnWriteArrayList<ProblemType>();

    public ProblemType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    public ProblemType(String value) {
        super(null, value);
        VALUES_LIST.add(this);
    }
    
    /**
     * Find the ProblemType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an ProblemType that matches, or null if none is found to match.
     */
    public static ProblemType find(String scheme, String value) throws ServiceException {
        return (ProblemType)find(scheme, value, VALUES_LIST, ProblemType.class);
    }

}
