/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupUserService;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class KohaLookupUserService implements LookupUserService {

	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final LookupUserResponseData responseData = new LookupUserResponseData();

		if (initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().trim().isEmpty()) {
			responseData.setProblems(Arrays.asList(new Problem(new ProblemType("UserId is undefined."), null, null, "Cannot lookup unkown user ..")));
		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				JSONObject kohaUser = kohaRemoteServiceManager.lookupUser(initData);

				updateResponseData(initData, responseData, kohaUser, kohaRemoteServiceManager);
			} catch (MalformedURLException mue) {
				Problem p = new Problem(new ProblemType("Processing MalformedURLException error."), null, mue.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (KohaException ke) {
				Problem p = new Problem(new ProblemType(ke.getShortMessage()), null, ke.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}

		}
		return responseData;
	}

	private void updateResponseData(LookupUserInitiationData initData, LookupUserResponseData responseData, JSONObject kohaUser, KohaRemoteServiceManager svcMgr) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		UserOptionalFields userOptionalFields = new UserOptionalFields();

		JSONObject userInfo = (JSONObject) kohaUser.get("userInfo");
		if (userInfo != null) {
			if (initData.getNameInformationDesired()) {
				String firstname = (String) userInfo.get("firstname");
				String surname = (String) userInfo.get("surname");
				String title = (String) userInfo.get("title");
				String othernames = (String) userInfo.get("othernames");

				StructuredPersonalUserName structuredPersonalUserName = new StructuredPersonalUserName();
				structuredPersonalUserName.setGivenName(firstname);
				structuredPersonalUserName.setPrefix(title);
				structuredPersonalUserName.setSurname(surname);
				structuredPersonalUserName.setSuffix(othernames);

				PersonalNameInformation personalNameInformation = new PersonalNameInformation();
				personalNameInformation.setStructuredPersonalUserName(structuredPersonalUserName);

				NameInformation nameInformation = new NameInformation();
				nameInformation.setPersonalNameInformation(personalNameInformation);
				userOptionalFields.setNameInformation(nameInformation);
			}

			if (initData.getUserAddressInformationDesired())
				userOptionalFields.setUserAddressInformations(KohaUtil.parseUserAddressInformations(userInfo));

			if (initData.getUserPrivilegeDesired()) {

			}
		}
		responseData.setUserId(initData.getUserId());

		JSONArray requestedItems = (JSONArray) kohaUser.get("requestedItems");
		if (requestedItems != null) {

		}

		JSONArray loanedItems = (JSONArray) kohaUser.get("loanedItems");
		if (loanedItems != null) {

		}

		JSONArray fiscalAccounts = (JSONArray) kohaUser.get("userFiscalAccount");
		if (fiscalAccounts != null) {

		}
		responseData.setUserOptionalFields(userOptionalFields);

	}
}
