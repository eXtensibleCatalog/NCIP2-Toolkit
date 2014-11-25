package org.extensiblecatalog.ncip.v2.aleph;

import java.util.Date;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.CreateUserFiscalTransactionInitiationData;
import org.extensiblecatalog.ncip.v2.service.CreateUserFiscalTransactionResponseData;
import org.extensiblecatalog.ncip.v2.service.FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionType;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.NCIPService;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.ValidationException;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;

public class AlephCreateUserFiscalTransactionService implements NCIPService<CreateUserFiscalTransactionInitiationData, CreateUserFiscalTransactionResponseData> {

	@Override
	public CreateUserFiscalTransactionResponseData performService(CreateUserFiscalTransactionInitiationData initData, ServiceContext serviceContext,
			RemoteServiceManager serviceManager) throws ServiceException, ValidationException {

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

		if (patronId.isEmpty()) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined.");
		}

		if (initData.getAuthenticationInputs() != null && initData.getAuthenticationInputs().size() > 0 && password.isEmpty()) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Password is undefined.");
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final CreateUserFiscalTransactionResponseData responseData = new CreateUserFiscalTransactionResponseData();

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				// Reverse From/To AgencyId because of the request was processed (return to initiator)
				ToAgencyId toAgencyId = new ToAgencyId();
				toAgencyId.setAgencyIds(initiationHeader.getFromAgencyId().getAgencyIds());
				
				FromAgencyId fromAgencyId = new FromAgencyId();
				fromAgencyId.setAgencyIds(initiationHeader.getToAgencyId().getAgencyIds());
				
				responseHeader.setFromAgencyId(fromAgencyId);
				responseHeader.setToAgencyId(toAgencyId);
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

		FiscalActionType fiscalActionType = initData.getFiscalTransactionInformation().getFiscalActionType();
		FiscalTransactionType fiscalTransactionType = initData.getFiscalTransactionInformation().getFiscalTransactionType();
		Amount amount = initData.getFiscalTransactionInformation().getAmount();

		// TODO: Implement storing this information into database.
		// This service basically says user has paid mentioned amount.
		// For now we will just send a message back we understood the payment was a success.

		FiscalTransactionReferenceId fiscalTransactionReferenceId = new FiscalTransactionReferenceId();

		AgencyId agencyId = new AgencyId(alephRemoteServiceManager.getDefaultAgencyId());
		fiscalTransactionReferenceId.setAgencyId(agencyId);

		String uniquePaymentIdentifier = createUniquePaymentIdentifier(initData, patronId);

		fiscalTransactionReferenceId.setFiscalTransactionIdentifierValue(uniquePaymentIdentifier);

		responseData.setFiscalTransactionReferenceId(fiscalTransactionReferenceId);

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
