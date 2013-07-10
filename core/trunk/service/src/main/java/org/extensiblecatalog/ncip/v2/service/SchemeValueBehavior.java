/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class SchemeValueBehavior {

    private static final Logger LOG = Logger.getLogger(SchemeValueBehavior.class);

    public static final SchemeValueBehavior UNSET = new SchemeValueBehavior() {

        <SVP extends SchemeValuePair> SVP applyBehavior(final String scheme, final String value,
                                                        final List<SVP> values, final Class<SVP> svpClass)
                throws ServiceException {

            throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "No match found for scheme '" + scheme
                    + "', value '" + value + "' in " + svpClass.getName()
                    + "; configuration option for defining values for this class is unset.");

        }
    };

    public static final SchemeValueBehavior DEFINED_ONLY = new SchemeValueBehavior() {

        <SVP extends SchemeValuePair> SVP applyBehavior(final String scheme, final String value,
                                                        final List<SVP> values, final Class<SVP> svpClass)
                throws ServiceException {

            throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "No match found for scheme '" + scheme
                    + "', value '" + value + "' in " + svpClass.getName()
                    + "; configuration option for defining values for this class is 'DEFINED_ONLY'.");

        }
    };

    public static final SchemeValueBehavior ALLOW_ANY = new SchemeValueBehavior() {

        <SVP extends SchemeValuePair> SVP applyBehavior(final String scheme, final String value,
                                                        final List<SVP> values,
                                                        final Class<SVP> svpClass)
                throws ServiceException {

            return SchemeValuePair.addIfAbsent(scheme, value, values, svpClass);

        }

    };

    abstract <SVP extends SchemeValuePair> SVP applyBehavior(final String scheme, final String value,
                                                             final List<SVP> values, final Class<SVP> svpClass)
            throws ServiceException;

}
