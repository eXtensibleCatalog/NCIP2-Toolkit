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

		if (patronId == null) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined. Note that Aleph supports only User Id lookup.");
		}

		responseData.setUserId(initData.getUserId());

		// ResponseElementControl can regulate output
		List<ResponseElementControl> responseElementControls = initData.getResponseElementControls();

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				responseHeader.setFromAgencyId(initiationHeader.getFromAgencyId());
				responseHeader.setToAgencyId(initiationHeader.getToAgencyId());
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
			responseData.setResponseHeader(responseHeader);
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

		if (alephUser != null) {
			boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions -> Cash
			boolean requestedItemsDesired = initData.getRequestedItemsDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/requests/ ??? FIXME
			boolean loanedItemsDesired = initData.getLoanedItemsDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/loans?view=full

			if (userFiscalAccountDesired) {
				// Note that summary is enough for our purposes
				UserFiscalAccountSummary ufas = alephUser.getUserFiscalAccountSummary();
				responseData.setUserFiscalAccountSummary(ufas);

				// Aleph is capable of returning detailed transactions
			}

			if (requestedItemsDesired) {
				List<RequestedItem> requestedItems = alephUser.getRequestedItems();
				responseData.setRequestedItems(requestedItems);
			}

			if (loanedItemsDesired) {
				List<LoanedItem> loanedItems = alephUser.getLoanedItems();
				responseData.setLoanedItems(loanedItems);
			}
			// User optional fields:
			boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();
			boolean nameInformationDesired = initData.getNameInformationDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address
			boolean userAddressInformationDesired = initData.getUserAddressInformationDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address
			boolean userIdDesired = initData.getUserIdDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address - > Mandatory address 1
			boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();

			UserOptionalFields uof = new UserOptionalFields();

			if (blockOrTrapDesired) {
				List<BlockOrTrap> blockOrTraps = alephUser.getBlockOrTraps();
				uof.setBlockOrTraps(blockOrTraps);
			}

			if (nameInformationDesired) {
				NameInformation nameInfo = alephUser.getNameInformation();
				uof.setNameInformation(nameInfo);
			}

			if (userAddressInformationDesired) {
				List<UserAddressInformation> userAddrInfos = alephUser.getUserAddressInformations();
				uof.setUserAddressInformations(userAddrInfos);
			}

			if (userIdDesired) {
				List<UserId> userIds = alephUser.getUserIds();
				uof.setUserIds(userIds);
			}

			if (userPrivilegeDesired) {
				List<UserPrivilege> userPrivileges = alephUser.getUserPrivileges();
				uof.setUserPrivileges(userPrivileges);
			}

			responseData.setUserOptionalFields(uof);
		}
	}
}
