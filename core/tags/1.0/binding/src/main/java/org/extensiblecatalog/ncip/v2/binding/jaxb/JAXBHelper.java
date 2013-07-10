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
import org.extensiblecatalog.ncip.v2.common.ReflectionHelper;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.NCIPResponseData;

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
    public static String getMessageName(NCIPMessage msg) throws InvocationTargetException, IllegalAccessException {

        LOG.debug("getMessageName(" + msg.getClass().getSimpleName() + ")");
        Object innerObj = ReflectionHelper.unwrapFirstNonNullFieldViaGetter(msg);
        String objectName;
        if ( innerObj != null ) {

            if ( ReflectionHelper.isCollection(innerObj.getClass()) ) {

                Collection coll = (Collection)innerObj;
                if ( ! coll.isEmpty() ) {

                    Object extensionObj = coll.iterator().next();
                    objectName = extensionObj.getClass().getSimpleName();

                } else {

                    // The collection is empty - this is the best we can do.
                    objectName = innerObj.getClass().getSimpleName();

                }

            } else {

                // If this is the Ext object, look inside it to get the first object in it and treat that as the
                // message, getting its simple name. This relies on the assumption that there can only be
                // one immediate child of NCIPMessage/Ext, just like there can only be one immediate child of
                // NCIPMessage.
                if ( innerObj instanceof Ext) {

                    Ext extObj = (Ext)innerObj;
                    List<Object> extensionsList = extObj.getAny();
                    if ( extensionsList != null && ! extensionsList.isEmpty() ) {

                        objectName = extensionsList.get(0).getClass().getSimpleName();

                    } else {

                        // The extension list is null or empty - this is the best we can do.
                        objectName = innerObj.getClass().getSimpleName();

                    }

                } else {

                    objectName = innerObj.getClass().getSimpleName();

                }

            }

        } else {

            objectName = msg.getClass().getSimpleName();
        }

        return objectName;

    }

    public static String getMessageName(NCIPInitiationData initiationData) {

        String initDataClassName = initiationData.getClass().getSimpleName();
        String msgName = initDataClassName.substring(0, initDataClassName.length() - LENGTH_OF_INITIATION_DATA_LITERAL);
        return msgName;

    }

    public static String getMessageName(NCIPResponseData responseData) {

        String initDataClassName = responseData.getClass().getSimpleName();
        String msgName = initDataClassName.substring(0, initDataClassName.length() - LENGTH_OF_RESPONSE_DATA_LITERAL);
        return msgName;

    }
}
