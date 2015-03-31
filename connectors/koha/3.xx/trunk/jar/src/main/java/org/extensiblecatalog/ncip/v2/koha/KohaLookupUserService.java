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
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class KohaLookupUserService implements LookupUserService {

	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final LookupUserResponseData responseData = new LookupUserResponseData();

		String patronId = null;
		String password = null;
		boolean authenticationIncluded = false;

		if (initData.getUserId() != null)
			patronId = initData.getUserId().getUserIdentifierValue();
		else {
			for (AuthenticationInput authInput : initData.getAuthenticationInputs()) {
				if (authInput.getAuthenticationInputType().getValue().equals(Version1AuthenticationInputType.USER_ID.getValue())) {
					patronId = authInput.getAuthenticationInputData();
				} else if (authInput.getAuthenticationInputType().getValue().equals(Version1AuthenticationInputType.PASSWORD.getValue())) {
					password = authInput.getAuthenticationInputData();
				}
			}
			authenticationIncluded = true;
		}

		boolean userIdIsEmpty = patronId.isEmpty();
		boolean authIsSetAndPwIsEmpty = initData.getAuthenticationInputs() != null && initData.getAuthenticationInputs().size() > 0 && (password == null || password.isEmpty());

		if (userIdIsEmpty || authIsSetAndPwIsEmpty) {
			List<Problem> problems = new ArrayList<Problem>();

			if (!authenticationIncluded) {

				Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Element UserIdentifierValue is empty.");
				problems.add(p);

			} else {
				if (userIdIsEmpty) {

					Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Set AuthenticationInputType to \""
							+ Version1AuthenticationInputType.USER_ID.getValue() + "\" to specify user id input.");
					problems.add(p);

				}
				if (authIsSetAndPwIsEmpty) {

					Problem p = new Problem(new ProblemType("Password is undefined."), null, "Can't authenticate without password specified.", "Set AuthenticationInputType to \""
							+ Version1AuthenticationInputType.PASSWORD.getValue() + "\" to specify password input.");
					problems.add(p);

				}
			}

			responseData.setProblems(problems);
		} else {

			if (authenticationIncluded) {
				// FIXME
			} else {
				// Regular lookup User ..

				KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

				JSONObject kohaUser = null;
				try {
					kohaUser = kohaRemoteServiceManager.lookupUser(patronId, initData);

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
		}
		return responseData;
	}

	private void updateResponseData(LookupUserInitiationData initData, LookupUserResponseData responseData, JSONObject kohaUser, KohaRemoteServiceManager svcMgr) {

		if (kohaUser != null) {

			ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

			if (responseHeader != null)
				responseData.setResponseHeader(responseHeader);

			responseData.setUserId(initData.getUserId());

			boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
			boolean requestedItemsDesired = initData.getRequestedItemsDesired();
			boolean loanedItemsDesired = initData.getLoanedItemsDesired();

			if (userFiscalAccountDesired) {
				List<UserFiscalAccount> userFiscalAccounts = null;

				responseData.setUserFiscalAccounts(userFiscalAccounts);
			}

			/*
						if (requestedItemsDesired)
							responseData.setRequestedItems(KohaUtil.parseRequestedItems(kohaUser));

						if (loanedItemsDesired)
							responseData.setLoanedItems(KohaUtil.parseLoanedItems(kohaUser));

			*/

			responseData.setUserOptionalFields(null);
		}
	}
}
