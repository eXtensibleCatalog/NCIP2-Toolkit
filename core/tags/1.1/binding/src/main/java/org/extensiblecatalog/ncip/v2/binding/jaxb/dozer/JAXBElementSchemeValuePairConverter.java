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

import javax.xml.bind.JAXBElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JAXBElementSchemeValuePairConverter< SVP extends SchemeValuePair >
    extends DozerConverter<JAXBElement, SchemeValuePair> {

    protected static final Map<Class, Method> findMethodsByClass = new HashMap<Class, Method>();

    protected final Class SVPClass;

    public JAXBElementSchemeValuePairConverter(Class SVPClass) {
        super(JAXBElement.class, SVPClass);
        this.SVPClass = SVPClass;
    }

    @Override
    public SchemeValuePair convertTo(JAXBElement source, SchemeValuePair destination) {
        // Convert to JAXB svp from service SVP
        // If the parent JAXB object uses the get-Content catch-all, then SchemeValuePair objects are contained
        //  w/i JAXBElement objects, so we have to unwrap them here.

        SchemeValuePair result = null;
        if ( source != null ) {

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP =
                (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair)source.getValue();
            Method findMethod = getFindMethod(SVPClass);
            try {
                result = (SchemeValuePair)findMethod.invoke(null, jaxbSVP.getScheme(), jaxbSVP.getValue());
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
    public JAXBElement convertFrom(SchemeValuePair source, JAXBElement destination) {

        // Convert from service SVP to JAXBElement
        throw new MappingException("Unsupported mapping from service.SchemeValuePair to JAXBElement.");

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
