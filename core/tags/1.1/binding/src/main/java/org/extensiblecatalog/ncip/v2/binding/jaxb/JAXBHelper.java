/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.ReflectionHelper;
import org.extensiblecatalog.ncip.v2.service.NCIPData;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class JAXBHelper {

    private static final Logger LOG = Logger.getLogger(JAXBHelper.class);

    private static final int LENGTH_OF_INITIATION_DATA_LITERAL = "InitiationData".length();
    private static final int LENGTH_OF_RESPONSE_DATA_LITERAL = "Data".length();

    // TODO: Write JUnit tests that include a test for an Extension message like LookupItemSet.
    /**
     *
     * @param msg
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String getMessageName(NCIPMessage msg) throws InvocationTargetException, IllegalAccessException, ToolkitException {

        String msgName = null;

        Class objClass = msg.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for ( Field f : fields ) {

            Method getterMethod = ReflectionHelper.findMethod(objClass, "get" + f.getName());
            if ( getterMethod != null ) {

                Object innerObj = getterMethod.invoke(msg);
                if ( innerObj != null ) {

                    if ( ReflectionHelper.isCollection(innerObj.getClass() ) ) {

                        // We've found the initiation or response object, which is a
                        // collection and should only be a List<Problem>.
                        Collection coll = (Collection)innerObj;
                        if ( ! coll.isEmpty() ) {

                            Object problemObj = coll.iterator().next();
                            if ( problemObj instanceof Problem) {

                                msgName = problemObj.getClass().getSimpleName();
                                break;

                            } else {

                                throw new ToolkitException("Object in first non-null collection in JAXB NCIPMessage "
                                    + "object is '" + problemObj.getClass().getSimpleName() + "', not '"
                                    + Problem.class.getName() + "', as expected.");

                            }

                        } else {

                            // An empty collection is effectively a null field
                            continue;

                        }

                    } else {

                        // We've found the initiation or response object
                        // If this is the Ext object, look inside it to get the first object in it and treat that as the
                        // message, getting its simple name. This relies on the assumption that there can only be
                        // one immediate child of NCIPMessage/Ext, just like there can only be one immediate child of
                        // NCIPMessage.
                        if ( innerObj instanceof Ext) {

                            Ext extObj = (Ext)innerObj;
                            List<Object> extensionsList = extObj.getAny();
                            if ( extensionsList != null && ! extensionsList.isEmpty() ) {

                                msgName = extensionsList.get(0).getClass().getSimpleName();

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

    public static String getMessageName(NCIPData ncipData) {

        String dataClassName = ncipData.getClass().getSimpleName();
        int lengthOfSuffix
            = ncipData instanceof NCIPInitiationData ? LENGTH_OF_INITIATION_DATA_LITERAL : LENGTH_OF_RESPONSE_DATA_LITERAL;
        String msgName = dataClassName.substring(0, dataClassName.length() - lengthOfSuffix);
        return msgName;

    }

}
