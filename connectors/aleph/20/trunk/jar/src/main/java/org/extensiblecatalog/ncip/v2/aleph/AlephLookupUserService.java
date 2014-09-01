/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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
	 * @throws ServiceException
	 */
	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {
		// TODO: Think about forwarding password in encrypted format ({@link Version1AuthenticationDataFormatType.APPLICATION_AUTH_POLICY_XML})

		final LookupUserResponseData responseData = new LookupUserResponseData();
		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		String patronId = initData.getUserId().getUserIdentifierValue();

		// TODO: What are these data good for?
		InitiationHeader initiationHeader = initData.getInitiationHeader();
		AgencyId relyingPartyId = initData.getRelyingPartyId();
		List<ResponseElementControl> responseElementControls = initData.getResponseElementControls();
		// EOF TODO

		if (patronId == null) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined. Note that Aleph supports only User Id lookup.");
		}

		AlephUser alephUser = null;

		try {
			alephUser = alephRemoteServiceManager.lookupUser(patronId, initData);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlephException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		updateResponseData(initData, responseData, alephUser);

		return responseData;
	}

	private void updateResponseData(LookupUserInitiationData initData, LookupUserResponseData responseData, AlephUser alephUser) {

		UserOptionalFields userOptionalFields = new UserOptionalFields();

		if (alephUser != null && initData.getNameInformationDesired()) {
			PersonalNameInformation pni = new PersonalNameInformation();
			pni.setUnstructuredPersonalUserName(alephUser.getFullName());
			NameInformation ni = new NameInformation();
			ni.setPersonalNameInformation(pni);
			userOptionalFields.setNameInformation(ni);
		}

		responseData.setUserOptionalFields(userOptionalFields);

	}

}
