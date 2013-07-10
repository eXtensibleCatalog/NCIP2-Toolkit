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
 * Indicates the level of the Item description being requested. Examples: bibliographic item (i.e., any item with
 * the same bibliographic id), item (i.e., any item with the same bibliographic id and volume)
 */
public class RequestScopeType extends SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(RequestScopeType.class);

    private static final List<RequestScopeType> VALUES_LIST = new CopyOnWriteArrayList<RequestScopeType>();

    public RequestScopeType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the RequestScopeType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return a RequestScopeType that matches, or null if none is found to match.
     */
    public static RequestScopeType find(String scheme, String value) throws ServiceException {
        return (RequestScopeType) find(scheme, value, VALUES_LIST, RequestScopeType.class);
    }

}
