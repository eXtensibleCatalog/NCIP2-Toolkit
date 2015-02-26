package org.extensiblecatalog.ncip.v2.koha;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.CreateUserFiscalTransactionInitiationData;
import org.extensiblecatalog.ncip.v2.service.CreateUserFiscalTransactionResponseData;
import org.extensiblecatalog.ncip.v2.service.FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionType;
import org.extensiblecatalog.ncip.v2.service.NCIPService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ValidationException;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;

public class KohaCreateUserFiscalTransactionService implements NCIPService<CreateUserFiscalTransactionInitiationData, CreateUserFiscalTransactionResponseData> {

	@Override
	public CreateUserFiscalTransactionResponseData performService(CreateUserFiscalTransactionInitiationData initData, ServiceContext serviceContext,
			RemoteServiceManager serviceManager) throws ServiceException, ValidationException {

		final CreateUserFiscalTransactionResponseData responseData = new CreateUserFiscalTransactionResponseData();

		String patronId = null;
		String password = null;

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
		}

		boolean userIdIsEmpty = patronId.isEmpty();
		boolean authIsSetAndPwIsEmpty = initData.getAuthenticationInputs() != null && initData.getAuthenticationInputs().size() > 0 && password.isEmpty();

		if (userIdIsEmpty || authIsSetAndPwIsEmpty) {
			List<Problem> problems = new ArrayList<Problem>();

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

			responseData.setProblems(problems);
		}

		if (responseData.getProblems() == null) {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

			if (responseHeader != null)
				responseData.setResponseHeader(responseHeader);

			responseData.setUserId(initData.getUserId());

			FiscalActionType fiscalActionType = initData.getFiscalTransactionInformation().getFiscalActionType();
			FiscalTransactionType fiscalTransactionType = initData.getFiscalTransactionInformation().getFiscalTransactionType();
			Amount amount = initData.getFiscalTransactionInformation().getAmount();

			// TODO: Implement storing this information into database.
			// This service basically says user has paid mentioned amount.
			// For now we will just send a message back we understood the payment was a success.

			FiscalTransactionReferenceId fiscalTransactionReferenceId = new FiscalTransactionReferenceId();

			AgencyId agencyId = new AgencyId(KohaConstants.DEFAULT_AGENCY);
			fiscalTransactionReferenceId.setAgencyId(agencyId);

			String uniquePaymentIdentifier = createUniquePaymentIdentifier(initData, patronId);

			fiscalTransactionReferenceId.setFiscalTransactionIdentifierValue(uniquePaymentIdentifier);

			responseData.setFiscalTransactionReferenceId(fiscalTransactionReferenceId);
		}

		return responseData;
	}

	/**
	 * 
	 * Creates unique payment identifier based on current time, patronId & requestId if available.
	 * <p>
	 * Returned value should be something like:
	 * <p>
	 * 1412521144340-005033321-700<br>
	 * <br>
	 * 
	 * Else if there is no requestId, value should be like this:
	 * <p>
	 * 1412521144340-700
	 * 
	 * @return uniquePaymentIdentifier
	 */
	private String createUniquePaymentIdentifier(CreateUserFiscalTransactionInitiationData initData, String patronId) {
		long milisecs = new Date().getTime();

		String uniquePaymentIdentifier = String.valueOf(milisecs);

		// If requestId is not null, include it
		if (initData.getFiscalTransactionInformation().getRequestId() != null)
			if (initData.getFiscalTransactionInformation().getRequestId().getRequestIdentifierValue() != null)
				uniquePaymentIdentifier += "-" + initData.getFiscalTransactionInformation().getRequestId().getRequestIdentifierValue();

		uniquePaymentIdentifier += "-" + patronId;
		return uniquePaymentIdentifier;
	}

}
