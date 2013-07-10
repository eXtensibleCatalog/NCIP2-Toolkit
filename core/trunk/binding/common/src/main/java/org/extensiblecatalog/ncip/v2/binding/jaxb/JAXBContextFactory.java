/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.Map;

public class JAXBContextFactory {

    /**
     * This holds the instance of JAXBContext returned to all callers of getJAXBContext(packageName) for a given
     * package name. If you want multiple JAXBContexts for the same package name you will need to call
     * {@link #getUniqueJAXBContext(String)}.
     */
    protected static Map<String /* package name */, JAXBContext> sharedJAXBContextInstances
        = new HashMap<String, JAXBContext>();

    public JAXBContextFactory() throws ToolkitException {
        // Do nothing - nothing to initialize
    }

    /**
     * Get the shared JAXBContext object for this colon-separated list of package names. 
     * If this is not already set (e.g. by Spring) or a prior
     * call to this method, this calls {@link #getUniqueJAXBContext(String)} to construct it.
     * @return the {@link JAXBContext} object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static synchronized JAXBContext getJAXBContext(String packageNames) throws ToolkitException {

        JAXBContext jaxbContext;

        jaxbContext = sharedJAXBContextInstances.get(packageNames);

        if ( jaxbContext == null ) {

            jaxbContext = getUniqueJAXBContext(packageNames);
            sharedJAXBContextInstances.put(packageNames, jaxbContext);

        }

        return jaxbContext;

    }

    /**
     * Construct a JAXBContext for the provided packageName.
     *
     * @param packageNames the colon-separated list of package names of the Java package(s) for the JAXBContext
     * @return the new JAXBContext object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static JAXBContext getUniqueJAXBContext(String packageNames) throws ToolkitException {

        JAXBContext jaxbContext;

        try {

            jaxbContext = JAXBContext.newInstance(packageNames);

        } catch (JAXBException e) {

            throw new ToolkitException("Exception constructing a JAXBContext for package name(s) "
                + packageNames + ".", e);
        }

        return jaxbContext;

    }

}
