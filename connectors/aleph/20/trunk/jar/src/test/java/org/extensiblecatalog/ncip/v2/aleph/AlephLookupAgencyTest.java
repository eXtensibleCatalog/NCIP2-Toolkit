package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import junit.framework.TestCase;

public class AlephLookupAgencyTest extends TestCase {
	public void testPerformService() throws ServiceException {
		AlephLookupAgencyService service = new AlephLookupAgencyService();

		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();
		LookupAgencyInitiationData initData = new LookupAgencyInitiationData();

		// Input:
		AgencyId agencyId = new AgencyId("MZK");
		List<AgencyElementType> agencyElementTypes = new ArrayList<AgencyElementType>();

		agencyElementTypes.add(Version1AgencyElementType.AGENCY_ADDRESS_INFORMATION);
		agencyElementTypes.add(Version1AgencyElementType.AGENCY_USER_PRIVILEGE_TYPE);
		agencyElementTypes.add(Version1AgencyElementType.APPLICATION_PROFILE_SUPPORTED_TYPE);
		agencyElementTypes.add(Version1AgencyElementType.AUTHENTICATION_PROMPT);
		agencyElementTypes.add(Version1AgencyElementType.CONSORTIUM_AGREEMENT);
		agencyElementTypes.add(Version1AgencyElementType.ORGANIZATION_NAME_INFORMATION);

		initData.setAgencyElementTypes(agencyElementTypes);
		initData.setAgencyId(agencyId);

		// Output:

		String organizationName = serviceManager.getAgencyName();
		String organizationAddress = serviceManager.getAgencyAddress();
		String registrationLink = serviceManager.getRegistrationLink();

		LookupAgencyResponseData responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected Agency Id returned.", agencyId.getValue(), responseData.getAgencyId().getValue());
		assertEquals("Unexpected Organization Name returned.", organizationName, responseData.getOrganizationNameInformations().get(0).getOrganizationName());
		assertEquals("Unexpected Organization Address returned.", organizationAddress, responseData.getAgencyAddressInformations().get(0).getPhysicalAddress()
				.getUnstructuredAddress().getUnstructuredAddressData());
		assertEquals("Unexpected Registration Link via ns1:PromtOutput->ns1:AuthenticationPromptData returned.", registrationLink, responseData.getAuthenticationPrompts().get(0)
				.getPromptOutput().getAuthenticationPromptData());
	}
}