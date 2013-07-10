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
import org.dozer.util.MappingUtils;
import org.extensiblecatalog.ncip.v2.service.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This class assumes that all service package SchemeValuePair sub-classes have been populated.
 */
public class SchemeValuePairToBooleanConverter extends DozerConverter<List, Boolean> {

    protected static Class<org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair> jaxbSVPClass 
        = org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair.class;
    protected Method jaxbSVPGetSchemeMethod = jaxbSVPClass.getMethod("getScheme");
    protected Method jaxbSVPGetValueMethod = jaxbSVPClass.getMethod("getValue");
    protected Method jaxbSVPSetSchemeMethod = jaxbSVPClass.getMethod("setScheme", String.class);
    protected Method jaxbSVPSetValueMethod = jaxbSVPClass.getMethod("setValue", String.class);
    protected static Map<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>
        svpAgencyElementTypeFields
        = new HashMap<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>();
    static {

        Iterator<AgencyElementType> iterator = AgencyElementType.iterator();
        while ( iterator.hasNext() ) {

            AgencyElementType svcSVP = iterator.next();
            String scheme = svcSVP.getScheme();
            String value = svcSVP.getValue();
            String fieldName = convertToFieldName(value);
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP
                = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair();
            jaxbSVP.setScheme(scheme);
            jaxbSVP.setValue(value);
            svpAgencyElementTypeFields.put(fieldName, jaxbSVP);

        }


    }

    protected static Map<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>
        svpItemElementTypeFields
        = new HashMap<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>();
    static {

        Iterator<ItemElementType> iterator = ItemElementType.iterator();
        while ( iterator.hasNext() ) {

            ItemElementType svcSVP = iterator.next();
            String scheme = svcSVP.getScheme();
            String value = svcSVP.getValue();
            String fieldName = convertToFieldName(value);
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP
                = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair();
            jaxbSVP.setScheme(scheme);
            jaxbSVP.setValue(value);
            svpItemElementTypeFields.put(fieldName, jaxbSVP);

        }

    }

    protected static Map<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>
        svpRequestElementTypeFields
        = new HashMap<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>();
    static {

        Iterator<RequestElementType> iterator = RequestElementType.iterator();
        while ( iterator.hasNext() ) {

            RequestElementType svcSVP = iterator.next();
            String scheme = svcSVP.getScheme();
            String value = svcSVP.getValue();
            String fieldName = convertToFieldName(value);
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP
                = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair();
            jaxbSVP.setScheme(scheme);
            jaxbSVP.setValue(value);
            svpRequestElementTypeFields.put(fieldName, jaxbSVP);

        }

    }

    protected static Map<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>
        svpUserElementTypeFields
        = new HashMap<String, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair>();

    static {

        Iterator<UserElementType> iterator = UserElementType.iterator();
        while ( iterator.hasNext() ) {

            UserElementType svcSVP = iterator.next();
            String scheme = svcSVP.getScheme();
            String value = svcSVP.getValue();
            String fieldName = convertToFieldName(value);
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP
                = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair();
            jaxbSVP.setScheme(scheme);
            jaxbSVP.setValue(value);
            svpUserElementTypeFields.put(fieldName, jaxbSVP);

        }

    }


    public SchemeValuePairToBooleanConverter() throws NoSuchMethodException {
        super(List.class, Boolean.class);
    }

    @Override
    public Boolean convertTo(List srcObj, Boolean targetBoolean) {

        // TODO: This approach means that SVP elements that don't match a boolean flag attribute of the target class are silently ignored. It would be ideal to throw an exception
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

                throw new MappingException(e);

            } catch (NoSuchFieldException e) {

                throw new MappingException(e);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (InvocationTargetException e) {

                throw new MappingException(e);

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

                throw new MappingException(e);

            } catch (ClassNotFoundException e) {

                throw new MappingException(e);

            } catch (NoSuchFieldException e) {

                throw new MappingException(e);

            } catch (InstantiationException e) {

                throw new MappingException(e);

            } catch (InvocationTargetException e) {

                throw new MappingException(e);

            }

        } else {

            // Do nothing - leave the list alone

        }

        return result;

    }

    /**
     * Return the instance of the service package sub-class of SchemeValuePair identified by the
     * custom-converter-param on the dozer mapping's field element for this conversion.
     * @return
     * @throws ClassNotFoundException if there is no such sub-class as that in the custom-converter-param.
     * @throws NoSuchFieldException if there is no such field on the sub-class
     * @throws IllegalAccessException
     */
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

    public org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair getAgencyElementType(String fieldName) {

        return svpAgencyElementTypeFields.get(fieldName);

    }

    public org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair getItemElementType(String fieldName) {

        return svpItemElementTypeFields.get(fieldName);

    }

    public org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair getRequestElementType(String fieldName) {

        return svpRequestElementTypeFields.get(fieldName);

    }

    public org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair getUserElementType(String fieldName) {

        return svpUserElementTypeFields.get(fieldName);

    }

    protected static String convertToFieldName(String value) {

        return value.replaceAll(" ","");

    }
}
