package org.extensiblecatalog.ncip.v2.koha;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupAgencyResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.xml.sax.SAXException;

public class KohaLookupAgencyTest extends TestCase {
	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
		KohaLookupAgencyService service = new KohaLookupAgencyService();

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();
		LookupAgencyInitiationData initData = new LookupAgencyInitiationData();

		// Input:

		List<AgencyElementType> agencyElementTypes = new ArrayList<AgencyElementType>();

		agencyElementTypes.add(Version1AgencyElementType.AGENCY_ADDRESS_INFORMATION);
		agencyElementTypes.add(Version1AgencyElementType.AGENCY_USER_PRIVILEGE_TYPE);
		agencyElementTypes.add(Version1AgencyElementType.APPLICATION_PROFILE_SUPPORTED_TYPE);
		agencyElementTypes.add(Version1AgencyElementType.AUTHENTICATION_PROMPT);
		agencyElementTypes.add(Version1AgencyElementType.CONSORTIUM_AGREEMENT);
		agencyElementTypes.add(Version1AgencyElementType.ORGANIZATION_NAME_INFORMATION);

		initData.setAgencyElementTypes(agencyElementTypes);

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		initData.setInitiationHeader(initiationHeader);
		initData.setAgencyId(fromAgencyId.getAgencyId());

		// Output:

		String organizationName = LocalConfig.getAgencyName();
		String organizationAddress = LocalConfig.getAgencyAddress();
		String registrationLink = LocalConfig.getUserRegistrationLink();

		LookupAgencyResponseData responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertTrue("Agency Id not returned.", responseData.getAgencyId() != null);
		assertEquals("Unexpected Organization Name returned.", organizationName, responseData.getOrganizationNameInformations().get(0).getOrganizationName());
		assertEquals("Unexpected Organization Address returned.", organizationAddress, responseData.getAgencyAddressInformations().get(0).getPhysicalAddress()
				.getUnstructuredAddress().getUnstructuredAddressData());
		assertEquals("Unexpected Registration Link via ns1:PromtOutput->ns1:AuthenticationPromptData returned.", registrationLink, responseData.getAuthenticationPrompts().get(0)
				.getPromptOutput().getAuthenticationPromptData());
		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());
	}
}