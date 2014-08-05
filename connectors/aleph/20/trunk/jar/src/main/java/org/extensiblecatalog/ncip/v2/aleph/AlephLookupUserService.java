/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class implements the Lookup User service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class AlephLookupUserService implements LookupUserService {

    /**
     * Handles a NCIP LookupUser service by returning hard-coded data.
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService(LookupUserInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final LookupUserResponseData responseData = new LookupUserResponseData();

        //AgencyId agencyId = new AgencyId(dummySvcMgr.getLibraryName());

        // Echo back the same item id that came in
        responseData.setUserId(initData.getUserId());

        UserOptionalFields userOptionalFields = new UserOptionalFields();

        if ( initData.getNameInformationDesired() ) {

            PersonalNameInformation pni = new PersonalNameInformation();
            pni.setUnstructuredPersonalUserName("Jane Doer");

            NameInformation ni = new NameInformation();
            ni.setPersonalNameInformation(pni);
            userOptionalFields.setNameInformation(ni);
            
        }

        responseData.setUserOptionalFields(userOptionalFields);

        return responseData;
    }

}
