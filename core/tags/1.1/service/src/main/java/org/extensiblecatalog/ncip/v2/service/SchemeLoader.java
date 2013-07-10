/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class SchemeLoader {

    private SchemeLoader() {
        // Do nothing - prevent construction of instances of this utility class.
    }

    public static void init(String svpClassNamesCSV, String addedSVPClassNamesCSV,
                            String allowAnyClassNamesCSV, String addedAllowAnyClassNamesCSV,
                            String allowNullSchemeClassNamesCSV, String addedAllowNullSchemeClassNamesCSV)
        throws InvocationTargetException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {

        loadAll(svpClassNamesCSV, addedSVPClassNamesCSV);
        allowAny(allowAnyClassNamesCSV, addedAllowAnyClassNamesCSV);
        allowNullScheme(allowNullSchemeClassNamesCSV, addedAllowNullSchemeClassNamesCSV);

    }

    public static void loadAll(String svpClassNamesCSV, String addedSVPClassNamesCSV)
        throws ClassNotFoundException, NoSuchMethodException,
        InvocationTargetException, IllegalAccessException {

        if ( svpClassNamesCSV != null && ! svpClassNamesCSV.isEmpty() ) {
            String[] classNames = svpClassNamesCSV.split(",");
            for ( String className : classNames ) {

                Class<?> clazz = Class.forName(className);
                Class<? extends SchemeValuePair> svpClass = clazz.asSubclass(SchemeValuePair.class);
                Method loadAllMethod = svpClass.getMethod("loadAll");
                loadAllMethod.invoke(null);

            }
        }

        if ( addedSVPClassNamesCSV != null && ! addedSVPClassNamesCSV.isEmpty() ) {
            String[] classNames = addedSVPClassNamesCSV.split(",");
            for ( String className : classNames ) {

                Class<?> clazz = Class.forName(className);
                Class<? extends SchemeValuePair> svpClass = clazz.asSubclass(SchemeValuePair.class);
                Method loadAllMethod = svpClass.getMethod("loadAll");
                loadAllMethod.invoke(null);

            }
        }

    }

    public static void allowAny(String allowAnyClassNamesCSV, String addedAllowAnyClassNamesCSV)
        throws ClassNotFoundException, NoSuchMethodException,
        InvocationTargetException, IllegalAccessException {

        if ( allowAnyClassNamesCSV != null && ! allowAnyClassNamesCSV.isEmpty() ) {
            String[] classNames = allowAnyClassNamesCSV.split(",");
            for ( String className : classNames ) {

                SchemeValuePair.mapBehavior(className, SchemeValuePair.Behavior.ALLOW_ANY);

            }
        }

        if ( addedAllowAnyClassNamesCSV != null && ! addedAllowAnyClassNamesCSV.isEmpty() ) {
            String[] classNames = addedAllowAnyClassNamesCSV.split(",");
            for ( String className : classNames ) {

                SchemeValuePair.mapBehavior(className, SchemeValuePair.Behavior.ALLOW_ANY);

            }
        }

    }

    public static void allowNullScheme(String allowNullSchemeClassNamesCSV, String addedAllowNullSchemeClassNamesCSV)
        throws ClassNotFoundException, NoSuchMethodException,
        InvocationTargetException, IllegalAccessException {

        if ( allowNullSchemeClassNamesCSV != null && ! allowNullSchemeClassNamesCSV.isEmpty() ) {
            String[] classNames = allowNullSchemeClassNamesCSV.split(",");
            for ( String className : classNames ) {

                SchemeValuePair.allowNullScheme(className);

            }
        }

        if ( addedAllowNullSchemeClassNamesCSV != null && ! addedAllowNullSchemeClassNamesCSV.isEmpty() ) {
            String[] classNames = addedAllowNullSchemeClassNamesCSV.split(",");
            for ( String className : classNames ) {

                SchemeValuePair.allowNullScheme(className);

            }
        }

    }

}
