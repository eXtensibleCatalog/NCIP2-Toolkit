package org.extensiblecatalog.ncip.v2.aleph;

import java.util.Arrays;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInputType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationPrompt;
import org.extensiblecatalog.ncip.v2.service.AuthenticationPromptType;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyService;
import org.extensiblecatalog.ncip.v2.service.PromptInput;
import org.extensiblecatalog.ncip.v2.service.PromptOutput;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationDataFormatType;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;

public class AlephLookupAgencyService implements LookupAgencyService {

	@Override
	public LookupAgencyResponseData performService(LookupAgencyInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		boolean agencyIdIsEmpty = initData.getAgencyId().getValue().isEmpty();

		if (agencyIdIsEmpty) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "You have not supplied your agency id.");
		}

		boolean getAgencyAddressInformation = false;
		boolean getAgencyUserPrivilegeType = false;
		boolean getApplicationProfileSupportedType = false;
		boolean getAuthenticationPrompt = false;
		boolean getConsortiumAgreement = false;
		boolean getOrganizationNameInformation = false;

		for (AgencyElementType aet : initData.getAgencyElementTypes()) {
			if (aet.getScheme().equals(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE)) {
				if (aet.getValue().equals(Version1AgencyElementType.AGENCY_ADDRESS_INFORMATION.getValue()))
					getAgencyAddressInformation = true;
				else if (aet.getValue().equals(Version1AgencyElementType.AGENCY_USER_PRIVILEGE_TYPE.getValue()))
					getAgencyUserPrivilegeType = true;
				else if (aet.getValue().equals(Version1AgencyElementType.APPLICATION_PROFILE_SUPPORTED_TYPE.getValue()))
					getApplicationProfileSupportedType = true;
				else if (aet.getValue().equals(Version1AgencyElementType.AUTHENTICATION_PROMPT.getValue()))
					getAuthenticationPrompt = true;
				else if (aet.getValue().equals(Version1AgencyElementType.CONSORTIUM_AGREEMENT.getValue()))
					getConsortiumAgreement = true;
				else if (aet.getValue().equals(Version1AgencyElementType.ORGANIZATION_NAME_INFORMATION.getValue()))
					getOrganizationNameInformation = true;
			} else {
				throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "AgencyElementType has not supported scheme value.");
			}
		}

		AlephRemoteServiceManager alephSvcMgr = (AlephRemoteServiceManager) serviceManager;

		final LookupAgencyResponseData responseData = new LookupAgencyResponseData();

		ResponseHeader responseHeader = AlephUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		String localAgencyId = alephSvcMgr.getLocalConfig().getDefaultAgency();
		String registrationLink = alephSvcMgr.getLocalConfig().getUserRegistrationLink();

		if (getAgencyAddressInformation)
			responseData.setAgencyAddressInformations(Arrays.asList(alephSvcMgr.getAgencyPhysicalAddressInformation()));

		if (getOrganizationNameInformation)
			responseData.setOrganizationNameInformations(alephSvcMgr.getOrganizationNameInformations());

		if (getAuthenticationPrompt) {
			AuthenticationPrompt authenticationPrompt = new AuthenticationPrompt();

			PromptOutput promptOutput = new PromptOutput();

			AuthenticationPromptType authenticationPromptType = new AuthenticationPromptType("", "User Registration Link");
			promptOutput.setAuthenticationPromptType(authenticationPromptType);

			promptOutput.setAuthenticationPromptData(registrationLink);

			authenticationPrompt.setPromptOutput(promptOutput);

			PromptInput promptInput = new PromptInput();

			AuthenticationDataFormatType authenticationDataFormatType;

			String authenticationDataFormatTypeScheme = Version1AuthenticationDataFormatType.VERSION_1_AUTHENTICATION_DATA_FORMAT_TYPE;
			String authenticationDataFormatTypeValue = alephSvcMgr.getLocalConfig().getAuthDataFormatType();

			authenticationDataFormatType = new Version1AuthenticationDataFormatType(authenticationDataFormatTypeScheme, authenticationDataFormatTypeValue);
			promptInput.setAuthenticationDataFormatType(authenticationDataFormatType);

			String authenticationInputTypeScheme = Version1AuthenticationInputType.VERSION_1_AUTHENTICATION_INPUT_TYPE;
			String authenticationInputTypeValue = ""; // This AuthenticationInput is used to forward user registration link.

			promptInput.setAuthenticationInputType(new AuthenticationInputType(authenticationInputTypeScheme, authenticationInputTypeValue));

			authenticationPrompt.setPromptInput(promptInput);
			responseData.setAuthenticationPrompts(Arrays.asList(authenticationPrompt));
		}
		responseData.setAgencyId(AlephUtil.toAgencyId(localAgencyId));

		return responseData;
	}

}
