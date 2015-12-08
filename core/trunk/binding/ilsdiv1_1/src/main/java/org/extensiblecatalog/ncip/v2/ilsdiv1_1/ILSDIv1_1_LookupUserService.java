/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.ilsdiv1_1;

import org.extensiblecatalog.ncip.v2.service.*;

/**
 * Defines the interface for the NCIP Lookup User service.
 */
public interface ILSDIv1_1_LookupUserService extends NCIPService<ILSDIvOneOneLookupUserInitiationData, LookupUserResponseData> {

    @Override
    LookupUserResponseData performService(ILSDIvOneOneLookupUserInitiationData initData,
                                             ServiceContext serviceContext,
                                             RemoteServiceManager serviceManager)
            throws ServiceException;

}
