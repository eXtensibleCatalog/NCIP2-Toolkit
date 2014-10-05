package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation;
import org.extensiblecatalog.ncip.v2.service.AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.ApplicationProfileSupportedType;
import org.extensiblecatalog.ncip.v2.service.ConsortiumAgreement;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyService;
import org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;

public class AlephLookupAgencyService implements LookupAgencyService {

	@Override
	public LookupAgencyResponseData performService(LookupAgencyInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {
		LookupAgencyResponseData responseData = new LookupAgencyResponseData();

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

		String agencyId = initData.getAgencyId().getValue();

		if (agencyId == null) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Cannot look for empty agency Id. Please supply one.");
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
				throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "AgencyElementType has not valid scheme value.");
			}
		}

		// Note that this class is not going to work properly until there aren't records of agencies connected with (could be solved with database connection)
		// Another possible resource of data: http://www.mkcr.cz/assets/literatura-a-knihovny/Evidovane-knihovny-web-16-07-12.xls
		String hardCodedAgency = "MZK";
		String ncipVersion = "1.0.0";

		if (!agencyId.equals(hardCodedAgency)) {
			List<Problem> problems = new ArrayList<Problem>();
			Problem problem = new Problem();
			problem.setProblemType(new ProblemType("Unknown agency"));
			problem.setProblemDetail("Missing database of agencies to look into.");
			problem.setProblemValue("Passed agencyId (" + initData.getAgencyId().getValue() + ") was not found.");
			problems.add(problem);
			responseData.setProblems(problems);
		} else {
			responseData.setAgencyId(new AgencyId(agencyId));

			if (getAgencyAddressInformation) {
				List<AgencyAddressInformation> agencyAddressInformations = AlephUtil.getAgencyAddressInformations(agencyId);
				responseData.setAgencyAddressInformations(agencyAddressInformations);
			}
			/*
			 * if (getAgencyUserPrivilegeType) { throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Agency user privilege type has not been implemented yet."); }
			 */
			if (getApplicationProfileSupportedType) {
				List<ApplicationProfileSupportedType> applicationProfileSupportedTypes = AlephUtil.getApplicationProfileSupportedTypes(agencyId);
				responseData.setApplicationProfileSupportedTypes(applicationProfileSupportedTypes);
			}
			/*
			 * if (getAuthenticationPrompt) { throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Authentication prompt has not been implemented yet."); }
			 */
			if (getConsortiumAgreement) {
				List<ConsortiumAgreement> consortiumAgreements = AlephUtil.getConsortiumAgreements(agencyId);
				responseData.setConsortiumAgreements(consortiumAgreements);
			}

			if (getOrganizationNameInformation) {
				List<OrganizationNameInformation> organizationNameInformations = AlephUtil.getOrganizationNameInformations(agencyId);
				responseData.setOrganizationNameInformations(organizationNameInformations);
			}
			responseData.setVersion(ncipVersion);
		}
		return responseData;
	}

}
