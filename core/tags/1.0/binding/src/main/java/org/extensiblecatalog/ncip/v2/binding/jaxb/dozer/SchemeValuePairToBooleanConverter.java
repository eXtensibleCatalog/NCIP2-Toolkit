/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.util.MappingUtils;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SchemeValuePairToBooleanConverter extends DozerConverter<List, Boolean> {

    protected static Class jaxbSVPClass = org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair.class;
    protected Method jaxbSVPGetSchemeMethod = jaxbSVPClass.getMethod("getScheme");
    protected Method jaxbSVPGetValueMethod = jaxbSVPClass.getMethod("getValue");
    protected Method jaxbSVPSetSchemeMethod = jaxbSVPClass.getMethod("setScheme", String.class);
    protected Method jaxbSVPSetValueMethod = jaxbSVPClass.getMethod("setValue", String.class);

    public SchemeValuePairToBooleanConverter() throws NoSuchMethodException {
        super(List.class, Boolean.class);
    }

    @Override
    public Boolean convertTo(List srcObj, Boolean targetBoolean) {

        Boolean result = Boolean.FALSE;

        if ( srcObj != null ) {

            try {

                SchemeValuePair svcSVP = getServiceSVP();
                // Search the list to see if it has a matching entry
                List<Object> jaxbSVPList = (List<Object>)srcObj;
                for ( Object jaxbSVPObj : jaxbSVPList ) {

                    String scheme = (String)jaxbSVPGetSchemeMethod.invoke(jaxbSVPObj);
                    String value = (String)jaxbSVPGetValueMethod.invoke(jaxbSVPObj);
                    if ( svcSVP.matches(scheme, value) )
                    {

                        result = Boolean.TRUE;
                        break;

                    }

                }

            } catch (ClassNotFoundException e) {

                MappingUtils.throwMappingException(e);

            } catch (NoSuchFieldException e) {

                MappingUtils.throwMappingException(e);

            } catch (IllegalAccessException e) {

                MappingUtils.throwMappingException(e);

            } catch (InvocationTargetException e) {

                MappingUtils.throwMappingException(e);

            }

        } else {

            result = Boolean.FALSE;

        }

        return result;

    }

    @Override
    public List<Object> convertFrom(Boolean srcBoolean, List targetObj) {

        List<Object> result;

        if ( targetObj != null ) {

            result = (List<Object>)targetObj;

        } else {

            result = new ArrayList<Object>();

        }

        if ( srcBoolean ) {

            try {

                SchemeValuePair svcSVP = getServiceSVP();

                Object jaxbSVPObject = jaxbSVPClass.newInstance();
                jaxbSVPSetSchemeMethod.invoke(jaxbSVPObject, svcSVP.getScheme());
                jaxbSVPSetValueMethod.invoke(jaxbSVPObject, svcSVP.getValue());

                result.add(jaxbSVPObject);

            } catch (IllegalAccessException e) {

                MappingUtils.throwMappingException(e);

            } catch (ClassNotFoundException e) {

                MappingUtils.throwMappingException(e);

            } catch (NoSuchFieldException e) {

                MappingUtils.throwMappingException(e);

            } catch (InstantiationException e) {

                MappingUtils.throwMappingException(e);

            } catch (InvocationTargetException e) {

                MappingUtils.throwMappingException(e);

            }

        } else {

            // Do nothing - leave the list alone

        }

        return result;

    }

    protected SchemeValuePair getServiceSVP()
        throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        String valueClassAndField = getParameter();
        String className = valueClassAndField.substring(0, valueClassAndField.lastIndexOf("."));
        String fieldName = valueClassAndField.substring(valueClassAndField.lastIndexOf(".") + 1);
        Class svcSVPClass = Class.forName(className);
        Field svpField = svcSVPClass.getField(fieldName);
        SchemeValuePair svcSVP = (SchemeValuePair)svpField.get(null);
        return svcSVP;

    }

}
