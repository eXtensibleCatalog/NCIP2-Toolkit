package org.extensiblecatalog.ncip.v2.koha;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInputType;
import org.extensiblecatalog.ncip.v2.service.AuthenticationPrompt;
import org.extensiblecatalog.ncip.v2.service.AuthenticationPromptType;
import org.extensiblecatalog.ncip.v2.service.ConsortiumAgreement;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.PromptInput;
import org.extensiblecatalog.ncip.v2.service.PromptOutput;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationDataFormatType;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;

public class KohaLookupAgencyService implements LookupAgencyService {

	@Override
	public LookupAgencyResponseData performService(LookupAgencyInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final LookupAgencyResponseData responseData = new LookupAgencyResponseData();

		boolean agencyIdIsEmpty = initData.getAgencyId().getValue().isEmpty();

		if (agencyIdIsEmpty) {

			Problem p = new Problem(new ProblemType("You have not supplied your agency id."), null, null);
			responseData.setProblems(Arrays.asList(p));

		} else {

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
					Problem p = new Problem(new ProblemType("AgencyElementType has not supported scheme value."), null, null);
					responseData.setProblems(Arrays.asList(p));
				}
			}

			if (responseData.getProblems() == null) {
				KohaRemoteServiceManager kohaSvcMgr = (KohaRemoteServiceManager) serviceManager;

				ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

				if (responseHeader != null)
					responseData.setResponseHeader(responseHeader);

				String localAgencyId = LocalConfig.getDefaultAgency();
				String registrationLink = LocalConfig.getUserRegistrationLink();

				if (getAgencyAddressInformation)
					responseData.setAgencyAddressInformations(Arrays.asList(kohaSvcMgr.getAgencyPhysicalAddressInformation()));
				
				// TODO: How about returning optional phone numbers in AgencyAddressInfromations ? 

				if (getOrganizationNameInformation)
					responseData.setOrganizationNameInformations(kohaSvcMgr.getOrganizationNameInformations());

				if (getConsortiumAgreement) {
					ConsortiumAgreement consortiumAgreement = new ConsortiumAgreement("RegistrationLink", registrationLink);
					responseData.setConsortiumAgreements(Arrays.asList(consortiumAgreement));
				}
				
				responseData.setAgencyId(KohaUtil.createAgencyId(localAgencyId));
			}
		}
		return responseData;
	}

}
