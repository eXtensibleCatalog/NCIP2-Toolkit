/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

public class AlephLookupUserService implements LookupUserService {

	@Override
	public LookupUserResponseData performService(LookupUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		String patronId = null;
		String password = null;
		boolean authenticateOnly = false;

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
			authenticateOnly = true;
		}

		if (patronId.isEmpty()) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined.");
		}

		if (initData.getAuthenticationInputs() != null && initData.getAuthenticationInputs().size() > 0 && password.isEmpty()) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Password is undefined.");
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final LookupUserResponseData responseData = new LookupUserResponseData();

		if (!authenticateOnly) {
			AlephUser alephUser = null;
			try {
				alephUser = alephRemoteServiceManager.lookupUser(patronId, initData);
			} catch (MalformedURLException mue) {
				Problem p = new Problem(new ProblemType("Processing MalformedURLException error."), null, mue.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (AlephException ae) {
				Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}

			updateResponseData(initData, responseData, alephUser, alephRemoteServiceManager);
		} else {
			AgencyId suppliedAgencyId;
			if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null)
				suppliedAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId();
			else
				suppliedAgencyId = new AgencyId(alephRemoteServiceManager.getDefaultAgencyId());

			try {

				String username = alephRemoteServiceManager.authenticateUser(suppliedAgencyId, patronId, password);

				UserId userId = new UserId();
				userId.setAgencyId(suppliedAgencyId);
				userId.setUserIdentifierValue(username);
				userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);

				responseData.setUserId(userId);

			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephException ae) {
				Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}
		}
		return responseData;
	}

	private void updateResponseData(LookupUserInitiationData initData, LookupUserResponseData responseData, AlephUser alephUser, AlephRemoteServiceManager svcMgr) {

		if (alephUser != null) {

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

			responseData.setUserId(initData.getUserId());

			boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
			boolean requestedItemsDesired = initData.getRequestedItemsDesired();
			boolean loanedItemsDesired = initData.getLoanedItemsDesired();

			if (userFiscalAccountDesired) {
				List<UserFiscalAccount> userFiscalAccounts = alephUser.getUserFiscalAccounts();

				CurrencyCode currencyCode = new CurrencyCode(svcMgr.getCurrencyCode(), alephUser.getBalanceMinorUnit());

				// Update Currency Code
				for (UserFiscalAccount userFiscalAccount : userFiscalAccounts) {
					AccountBalance accountBalance = userFiscalAccount.getAccountBalance();

					if (accountBalance == null) {
						accountBalance = new AccountBalance();
						accountBalance.setMonetaryValue(new BigDecimal("0"));
					}
					accountBalance.setCurrencyCode(currencyCode);
					userFiscalAccount.setAccountBalance(accountBalance);

					List<AccountDetails> accountDetails = userFiscalAccount.getAccountDetails();

					for (AccountDetails details : accountDetails) {
						FiscalTransactionInformation fiscalTransactionInformation = details.getFiscalTransactionInformation();

						Amount amount = fiscalTransactionInformation.getAmount();
						amount.setCurrencyCode(currencyCode);

						fiscalTransactionInformation.setAmount(amount);

						details.setFiscalTransactionInformation(fiscalTransactionInformation);
					}
				}

				responseData.setUserFiscalAccounts(userFiscalAccounts);
			}

			if (requestedItemsDesired)
				responseData.setRequestedItems(alephUser.getRequestedItems());

			if (loanedItemsDesired)
				responseData.setLoanedItems(alephUser.getLoanedItems());

			// User optional fields:
			boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();
			boolean nameInformationDesired = initData.getNameInformationDesired();
			boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();
			boolean userIdDesired = initData.getUserIdDesired();
			boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();

			UserOptionalFields uof = new UserOptionalFields();
			boolean includeUserOptionalFields = false;

			if (blockOrTrapDesired) {
				uof.setBlockOrTraps(alephUser.getBlockOrTraps());
				includeUserOptionalFields = true;
			}

			if (nameInformationDesired) {
				uof.setNameInformation(alephUser.getNameInformation());
				includeUserOptionalFields = true;
			}

			if (userAddressInformationDesired) {
				uof.setUserAddressInformations(alephUser.getUserAddressInformations());
				includeUserOptionalFields = true;
			}

			if (userIdDesired) {
				uof.setUserIds(alephUser.getUserIds());
				includeUserOptionalFields = true;
			}

			if (userPrivilegeDesired) {
				uof.setUserPrivileges(alephUser.getUserPrivileges());
				includeUserOptionalFields = true;
			}

			if (includeUserOptionalFields)
				responseData.setUserOptionalFields(uof);
		}
	}
}
