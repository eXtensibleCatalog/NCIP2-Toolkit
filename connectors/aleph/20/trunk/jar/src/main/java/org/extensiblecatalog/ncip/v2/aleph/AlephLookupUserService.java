/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

/**
 * This class implements the Lookup User service for the Dummy back-end connector. Basically this just calls the DummyRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class AlephLookupUserService implements LookupUserService {

	static Logger log = Logger.getLogger(AlephLookupUserService.class);

	/**
	 * Handles a NCIP LookupUser service by returning hard-coded data.
	 *
	 * @param initData
	 *            the LookupUserInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupUserResponseData
	 */
	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) {
		//TODO: Think about forwarding password in encrypted format ({@link Version1AuthenticationDataFormatType.APPLICATION_AUTH_POLICY_XML})

		final LookupUserResponseData responseData = new LookupUserResponseData();
		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		String patronId = null;
		String password = null;

		for (AuthenticationInput authInput : initData.getAuthenticationInputs()) {
			if (authInput.getAuthenticationInputType().getValue().equals("Username")) {
				patronId = authInput.getAuthenticationInputData();
			}
			if (authInput.getAuthenticationInputType().getValue().equals("Password")) {
				password = authInput.getAuthenticationInputData();
			}
		}

		AlephUser user = null;

		try {
			//Can restful apis authenticatE?
			//user = alephRemoteServiceManager.authenticateUser(agencyId, patronId, password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// AgencyId agencyId = new AgencyId(dummySvcMgr.getLibraryName());

		// Echo back the same item id that came in
		responseData.setUserId(initData.getUserId());

		UserOptionalFields userOptionalFields = new UserOptionalFields();

		if (user != null && initData.getNameInformationDesired()) {
			PersonalNameInformation pni = new PersonalNameInformation();
			pni.setUnstructuredPersonalUserName(user.getFullName());
			NameInformation ni = new NameInformation();
			ni.setPersonalNameInformation(pni);
			userOptionalFields.setNameInformation(ni);
		}

		responseData.setUserOptionalFields(userOptionalFields);

		return responseData;
	}

}
