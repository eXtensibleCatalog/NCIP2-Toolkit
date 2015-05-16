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

import org.extensiblecatalog.ncip.v2.aleph.user.AlephRestDlfUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AccountBalance;
import org.extensiblecatalog.ncip.v2.service.AccountDetails;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.CurrencyCode;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation;
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
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.xml.sax.SAXException;

public class AlephLookupUserService implements LookupUserService {

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
				// Just authenticate patronId with password input through X-Services ..

				AgencyId suppliedAgencyId;

				AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

				if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null)
					suppliedAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId();
				else
					suppliedAgencyId = new AgencyId(alephRemoteServiceManager.getDefaultAgencyId());

				try {

					String username = alephRemoteServiceManager.authenticateUser(suppliedAgencyId, patronId, password);

					// Later is UserId from initData copied to responseData - we need to overwrite AuthInputs
					initData.setUserId(createUserId(suppliedAgencyId, username));					

					AlephRestDlfUser alephUser = alephRemoteServiceManager.lookupUser(username, initData);

					updateResponseData(initData, responseData, alephUser, alephRemoteServiceManager);

				} catch (IOException ie) {
					Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
					responseData.setProblems(Arrays.asList(p));
				} catch (SAXException se) {
					Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (AlephException ae) {
					Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (ParserConfigurationException pce) {
					Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (Exception e) {
					Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
					responseData.setProblems(Arrays.asList(p));
				}
			} else {
				// Regular lookup User ..

				AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

				AlephRestDlfUser alephUser = null;
				try {
					alephUser = alephRemoteServiceManager.lookupUser(patronId, initData);

					updateResponseData(initData, responseData, alephUser, alephRemoteServiceManager);
				} catch (MalformedURLException mue) {
					Problem p = new Problem(new ProblemType("Processing MalformedURLException error."), null, mue.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (IOException ie) {
					Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
					responseData.setProblems(Arrays.asList(p));
				} catch (SAXException se) {
					Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (AlephException ae) {
					Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (Exception e) {
					Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
					responseData.setProblems(Arrays.asList(p));
				}
			}
		}
		return responseData;
	}

	private void updateResponseData(LookupUserInitiationData initData, LookupUserResponseData responseData, AlephRestDlfUser alephUser, AlephRemoteServiceManager svcMgr) {

		if (alephUser != null) {

			ResponseHeader responseHeader = AlephUtil.reverseInitiationHeader(initData);

			if (responseHeader != null)
				responseData.setResponseHeader(responseHeader);

			UserId userId = new UserId();			
			userId.setUserIdentifierValue(initData.getUserId().getUserIdentifierValue());
			userId.setAgencyId(AlephUtil.createAgencyId(LocalConfig.getDefaultAgency())); // Default agency from config is important here ..
			responseData.setUserId(userId);

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
	
	private UserId createUserId(AgencyId suppliedAgencyId, String userName) {

		UserId userId = new UserId();
		userId.setAgencyId(suppliedAgencyId);
		userId.setUserIdentifierValue(userName);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		
		return userId;
	}
}
