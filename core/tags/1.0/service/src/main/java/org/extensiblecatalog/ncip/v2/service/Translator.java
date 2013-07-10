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

    NCIPInitiationData createInitiationData(InputStream inputStream) throws ServiceException;

    NCIPResponseData createResponseData(InputStream inputStream) throws ServiceException;

    ByteArrayInputStream createInitiationMessageStream(NCIPInitiationData initiationData)
        throws ServiceException;

    ByteArrayInputStream createResponseMessageStream(NCIPResponseData responseData) throws ServiceException;

}
