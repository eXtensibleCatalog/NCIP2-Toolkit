package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.extensiblecatalog.ncip.v2.ilsdiv1_1.ILSDIv1_1_LookupAgencyService;
import org.extensiblecatalog.ncip.v2.ilsdiv1_1.ILSDIvOneOneLookupAgencyResponseData;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation;
import org.extensiblecatalog.ncip.v2.service.AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.ConsortiumAgreement;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyInitiationData;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.xml.sax.SAXException;

public class KohaLookupAgencyService implements ILSDIv1_1_LookupAgencyService {

	private boolean getAgencyAddressInformation = false;
	private boolean getConsortiumAgreement = false;
	private boolean getOrganizationNameInformation = false;

	@Override
	public ILSDIvOneOneLookupAgencyResponseData performService(LookupAgencyInitiationData initData,
			ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final ILSDIvOneOneLookupAgencyResponseData responseData = new ILSDIvOneOneLookupAgencyResponseData();

		boolean agencyIdIsEmpty = initData.getAgencyId().getValue().isEmpty();

		if (agencyIdIsEmpty) {

			Problem p = new Problem(new ProblemType("You have not supplied your agency id."), null, null);
			responseData.setProblems(Arrays.asList(p));

		} else {

			for (AgencyElementType aet : initData.getAgencyElementTypes()) {
				if (aet.getScheme().equals(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE)) {
					if (aet.getValue().equals(Version1AgencyElementType.AGENCY_ADDRESS_INFORMATION.getValue()))
						getAgencyAddressInformation = true;
					else if (aet.getValue().equals(Version1AgencyElementType.CONSORTIUM_AGREEMENT.getValue()))
						getConsortiumAgreement = true;
					else if (aet.getValue().equals(Version1AgencyElementType.ORGANIZATION_NAME_INFORMATION.getValue()))
						getOrganizationNameInformation = true;
				} else {
					Problem p = new Problem(new ProblemType("AgencyElementType has not supported scheme value."), null,
							null);
					responseData.setProblems(Arrays.asList(p));
				}
			}

			if (responseData.getProblems() == null) {
				KohaRemoteServiceManager kohaSvcMgr = (KohaRemoteServiceManager) serviceManager;

				try {

					List<AgencyAddressInformation> addresses = new ArrayList<AgencyAddressInformation>();

					if (LocalConfig.useRestApiInsteadOfSvc()) {

						if (getAgencyAddressInformation)
							kohaSvcMgr.lookupAgency(initData, addresses);

					} else {

						if (getAgencyAddressInformation)
							addresses = Arrays.asList(kohaSvcMgr.getOfficialAgencyPhysicalAddressInformation());
					}

					if (getOrganizationNameInformation)
						responseData.setOrganizationNameInformations(kohaSvcMgr.getOrganizationNameInformations());

					updateResponseData(initData, responseData, addresses);

				} catch (IOException ie) {
					Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(),
							"Are you connected to the Internet/Intranet?");
					responseData.setProblems(Arrays.asList(p));
				} catch (SAXException se) {
					Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (KohaException ke) {
					Problem p = new Problem(new ProblemType(ke.getShortMessage()), null, ke.getMessage());
					responseData.setProblems(Arrays.asList(p));
				} catch (Exception e) {
					Problem p = new Problem(new ProblemType("Unknown processing exception error."), null,
							StringUtils.join(e.getStackTrace(), "\n"));
					responseData.setProblems(Arrays.asList(p));
				}
			}
		}
		return responseData;
	}

	private void updateResponseData(LookupAgencyInitiationData initData,
			ILSDIvOneOneLookupAgencyResponseData responseData, List<AgencyAddressInformation> addresses) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		String localAgencyId = LocalConfig.getDefaultAgency();
		responseData.setAgencyId(KohaUtil.createAgencyId(localAgencyId));

		if (getAgencyAddressInformation)
			responseData.setAgencyAddressInformations(addresses);

		if (getConsortiumAgreement) {
			String registrationLink = LocalConfig.getUserRegistrationLink();

			ConsortiumAgreement consortiumAgreement = new ConsortiumAgreement(
					KohaConstants.REGISTRATION_LINK_CONS_AGR_TYPE, registrationLink);
			responseData.setConsortiumAgreements(Arrays.asList(consortiumAgreement));
		}
	}

}
