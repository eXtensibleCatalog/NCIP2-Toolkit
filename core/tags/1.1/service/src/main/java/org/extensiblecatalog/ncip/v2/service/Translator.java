/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class converts between Streams and NCIPInitiationData and NCIPResponseData objects.
 */
public interface Translator {

    /**
     * @param inputStream
     * @return
     * @throws ServiceException
     */
    NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException;

    /**
     * @param inputStream
     * @return
     * @throws ServiceException
     */
    NCIPResponseData createResponseData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException;

    ByteArrayInputStream createInitiationMessageStream(ServiceContext serviceContext, NCIPInitiationData initiationData)
        throws ServiceException, ValidationException;

    ByteArrayInputStream createResponseMessageStream(ServiceContext serviceContext, NCIPResponseData responseData)
        throws ServiceException, ValidationException;

}
