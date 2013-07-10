/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.MappingException;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SchemeValuePairConverter< SVP extends SchemeValuePair >
    extends DozerConverter<org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair,
    org.extensiblecatalog.ncip.v2.service.SchemeValuePair> {

    protected static final Map<Class, Method> findMethodsByClass = new HashMap<Class, Method>();

    protected final Class SVPClass;

    public SchemeValuePairConverter(Class SVPClass) {
        super(org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair.class,
            SVPClass);
        this.SVPClass = SVPClass;
    }

    @Override
    public SchemeValuePair convertTo(org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair source,
                                     SchemeValuePair destination) {
        // Convert to JAXB svp from service SVP
        SchemeValuePair result = null;
        if ( source != null ) {

            try {
                Method findMethod = getFindMethod(SVPClass);
                result = (SchemeValuePair)findMethod.invoke(null, source.getScheme(), source.getValue());
            } catch (IllegalAccessException e) {
                throw new MappingException(e);
            } catch (InvocationTargetException e) {
                throw new MappingException(e);
            }

        } else {

            if ( destination != null ) {

                throw new MappingException("Source is null but destination is not null.");

            } else {

                // This is ok - return null

            }

        }
        return result;

    }

    @Override
    public org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair convertFrom(
        SchemeValuePair source, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair destination) {

        // Convert from service SVP to JAXB SVP
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair result = null;
        if ( source != null ) {

            if ( destination == null ) {

                result = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair();

            }

            result.setScheme(source.getScheme());
            result.setValue(source.getValue());


        } else {

            if ( destination != null ) {

                throw new MappingException("Source is null but destination is not null.");

            } else {

                // This is ok - return null

            }

        }

        return result;
    }

    protected Method getFindMethod(Class SVPClass) {
        Method findMethod = findMethodsByClass.get(SVPClass);
        if ( findMethod == null ) {
            try {
                findMethod = SVPClass.getMethod("find", String.class, String.class);
                findMethodsByClass.put(SVPClass, findMethod);
            } catch (NoSuchMethodException e) {
                throw new MappingException(e);
            }
        }
        return findMethod;
    }

}
