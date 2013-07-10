/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class converts between Streams and NCIPInitiationData and NCIPResponseData objects.
 */
public interface Translator extends ToolkitComponent {

    public static final String COMPONENT_NAME = Translator.class.getSimpleName();

    /**
     * This method must call {@link org.extensiblecatalog.ncip.v2.service.ServiceContext#validateAfterUnmarshalling}.
     * Always pass the same instance of ServiceContext to the {@link Translator} for a given invocation of a service.
     * @param inputStream
     * @return
     * @throws org.extensiblecatalog.ncip.v2.service.ServiceException
     */
    NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException;

    /**
     * This method must call {@link ServiceContext#validateAfterUnmarshalling}.
     * Always pass the same instance of ServiceContext to the {@link Translator} for a given invocation of a service.
     * @param inputStream
     * @return
     * @throws ServiceException
     */
    NCIPResponseData createResponseData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException;

    /**
     * This method must call {@link ServiceContext#validateBeforeMarshalling}.
     * Always pass the same instance of ServiceContext to the {@link Translator} for a given invocation of a service.
     * @param serviceContext
     * @param initiationData
     * @return
     * @throws ServiceException
     * @throws ValidationException
     */
    ByteArrayInputStream createInitiationMessageStream(ServiceContext serviceContext, NCIPInitiationData initiationData)
        throws ServiceException, ValidationException;

    /**
     * This method must call {@link ServiceContext#validateBeforeMarshalling}.
     * Always pass the same instance of ServiceContext to the {@link Translator} for a given invocation of a service.
     * @param serviceContext
     * @param responseData
     * @return
     * @throws ServiceException
     * @throws ValidationException
     */
    ByteArrayInputStream createResponseMessageStream(ServiceContext serviceContext, NCIPResponseData responseData)
        throws ServiceException, ValidationException;

}
