/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.dozer.MappingException;
import org.extensiblecatalog.ncip.v2.service.*;

import javax.xml.bind.JAXBElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class JAXBHelper {

    private static final Logger LOG = Logger.getLogger(JAXBHelper.class);

    // TODO: Write JUnit tests that include a test for an Extension message like LookupItemSet.
    /**
     *
     * @param msg
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String getMessageName(Object msg) throws InvocationTargetException, IllegalAccessException, ToolkitException {

        String msgName = null;

        Class objClass = msg.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for ( Field f : fields ) {

            Method getterMethod = ReflectionHelper.findMethod(objClass, "get" + f.getName());
            if ( getterMethod != null ) {

                Object innerObj = getterMethod.invoke(msg);
                if ( innerObj != null ) {

                    if ( ReflectionHelper.isCollection(innerObj.getClass() ) ) {

                        // We've found the initiation or response object, which is a collection
                        Collection coll = (Collection)innerObj;
                        if ( ! coll.isEmpty() ) {

                            Object collectionObj = coll.iterator().next();
                            msgName = collectionObj.getClass().getSimpleName();
                            break;

                        } else {

                            // An empty collection is effectively a null field
                            continue;

                        }

                    } else {

                        // If this is an Ext object, look inside it to get the first object in it and treat that as the
                        // message, getting its simple name. This relies on the assumption that there can only be
                        // one immediate child of NCIPMessage/Ext, just like there can only be one immediate child of
                        // NCIPMessage.
                        Method getAnyMethod = ReflectionHelper.findMethod(innerObj.getClass(), "getAny");
                        if ( getAnyMethod != null ) {

                            List<Object> anyObjList = (List<Object>)getAnyMethod.invoke(innerObj);
                            if ( anyObjList != null && ! anyObjList.isEmpty() ) {

                                msgName = anyObjList.get(0).getClass().getSimpleName();

                            } else {

                                // The extension list is null or empty - this is the best we can do.
                                msgName = innerObj.getClass().getSimpleName();

                            }

                        } else {

                            msgName = innerObj.getClass().getSimpleName();

                        }
                        break;

                    }

                }

            }

        }

        return msgName;

    }

    public static String getScheme(Object jaxbObject) {

        String result;
        try {

            result = (String)ReflectionHelper.findMethod(jaxbObject.getClass(), "getScheme").invoke(jaxbObject);

        } catch (IllegalAccessException e) {

            throw new MappingException(e);

        } catch (InvocationTargetException e) {

            throw new MappingException(e);

        }

        return result;

    }

    public static String getValue(Object jaxbObject) {

        String result;
        try {

            result = (String)ReflectionHelper.findMethod(jaxbObject.getClass(), "getValue").invoke(jaxbObject);

        } catch (IllegalAccessException e) {

            throw new MappingException(e);

        } catch (InvocationTargetException e) {

            throw new MappingException(e);

        }

        return result;

    }

    public static void setScheme(JAXBElement element, String scheme) {

        setScheme(element.getValue(), scheme);

    }

    public static void setScheme(Object jaxbObject, String scheme) {

        try {

            ReflectionHelper.findMethod(jaxbObject.getClass(), "setScheme", String.class).invoke(jaxbObject, scheme);

        } catch (IllegalAccessException e) {

            throw new MappingException(e);

        } catch (InvocationTargetException e) {

            throw new MappingException(e);

        }

    }

    public static void setValue(JAXBElement element, String value) {

        setValue(element.getValue(), value);

    }

    public static void setValue(Object jaxbObject, String value) {

        try {

            ReflectionHelper.findMethod(jaxbObject.getClass(), "setValue", String.class).invoke(jaxbObject, value);

        } catch (IllegalAccessException e) {

            throw new MappingException(e);

        } catch (InvocationTargetException e) {

            throw new MappingException(e);

        }

    }

    public static List<Object> getAnyList(Object extension) {

        List<Object> result;
        Method getAnyMethod = ReflectionHelper.findMethod(extension.getClass(), "getAny");
        if ( getAnyMethod != null ) {

            try {

                result = (List<Object>)getAnyMethod.invoke(extension);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (InvocationTargetException e) {

                throw new MappingException(e);

            }

        } else {

            throw new MappingException("Object " + extension.getClass() + " does not appear to be an Ext element"
                + " (no 'getAny' method).");

        }

        return result;

    }

    public static void addToExtension(Object extension, Object obj) {

        List<Object> list = JAXBHelper.getAnyList(extension);
        list.add(obj);

    }

    public static void addAllToExtension(Object extension, List<Object> listToAdd) {

        List<Object> list = JAXBHelper.getAnyList(extension);
        list.addAll(listToAdd);
        
    }

    public static <JAXBSVP> JAXBSVP createJAXBSchemeValuePair(Class<JAXBSVP> jaxbSVPClass, String scheme, String value) throws IllegalAccessException, InstantiationException {

        JAXBSVP jaxbSVP = jaxbSVPClass.newInstance();
        setScheme(jaxbSVP, scheme);
        setValue(jaxbSVP, value);
        return jaxbSVP;

    }

}
