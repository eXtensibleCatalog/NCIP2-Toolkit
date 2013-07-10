/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * Defines the interface of any service which performs an NCIP service.
 * Through this interface applications can create instances of NCIPInitiationData and NCIPResponseData
 * appropriate to the service, and perform the service.
 *
 * @param <NCIPInitiationData> the type of NCIP Request
 * @param <NCIPResponseData> the type of NCIP Response
 */
public interface NCIPService<NCIPInitiationData, NCIPResponseData> {


    /**
     * Process an NCIP Request to formulate an NCIP Response
     *
     * @param initiationData the NCIPInitiationData
     * @param serviceManager provides access to remote services
     * @return the NCIPResponseData
     * @throws ServiceException if the service fails
     */
    NCIPResponseData performService(NCIPInitiationData initiationData, RemoteServiceManager serviceManager)
        throws ServiceException;

}
