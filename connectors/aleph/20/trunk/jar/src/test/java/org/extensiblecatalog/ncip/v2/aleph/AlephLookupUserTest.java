package org.extensiblecatalog.ncip.v2.aleph;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class AlephLookupUserTest extends TestCase {
	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {

		AlephLookupUserService service = new AlephLookupUserService();
		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();
		LookupUserInitiationData initData = new LookupUserInitiationData();
		LookupUserResponseData responseData = new LookupUserResponseData();

		// Input:
		AgencyId agencyId = new AgencyId("MZK");

		String user = "700";

		UserId userId = new UserId();
		userId.setAgencyId(agencyId);
		userId.setUserIdentifierValue(user);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		initData.setUserId(userId);

		initData.setAuthenticationInputDesired(true);
		initData.setBlockOrTrapDesired(true);
		initData.setDateOfBirthDesired(true);
		initData.setLoanedItemsDesired(true);
		initData.setNameInformationDesired(true);
		initData.setPreviousUserIdDesired(true);
		initData.setRequestedItemsDesired(true);
		initData.setUserAddressInformationDesired(true);
		initData.setUserFiscalAccountDesired(true);
		initData.setUserLanguageDesired(true);
		initData.setUserPrivilegeDesired(true);

		// Output:
		String surName = "Pokus1 Přijmení";
		String street = "Trvalá ulice 123";
		String locality = "Trvalé";
		String postal = "12345 ";

		String electronicAddress = "test@mzk.cz";
		String privilegeDesc = "04 - S";

		InitiationHeader initiationHeader = new InitiationHeader();
		
		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("MZK-Aleph"));
				
		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("MZK-VuFind"));
		
		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		initData.setInitiationHeader(initiationHeader);
		
		responseData = service.performService(initData, null, serviceManager);

		//FIXME: Add tests for LookupUser with all features desired
		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected Surname returned.", surName, responseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
				.getStructuredPersonalUserName().getSurname());

		StructuredAddress structuredPhysicalAddress = responseData.getUserOptionalFields().getUserAddressInformation(0).getPhysicalAddress().getStructuredAddress();
		assertEquals("Unexpected Street returned.", street, structuredPhysicalAddress.getStreet());
		assertEquals("Unexpected Locality retuned.", locality, structuredPhysicalAddress.getLocality());
		assertEquals("Unexpected Postal returned.", postal, structuredPhysicalAddress.getPostalCode());

		assertEquals("Unexpected Electronic address returned (expecting " + electronicAddress + ")", electronicAddress, responseData.getUserOptionalFields()
				.getUserAddressInformation(1).getElectronicAddress().getElectronicAddressData());

		assertEquals("Unexpected privilege description returned", privilegeDesc, responseData.getUserOptionalFields().getUserPrivilege(0).getUserPrivilegeDescription());
		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());
	}
}