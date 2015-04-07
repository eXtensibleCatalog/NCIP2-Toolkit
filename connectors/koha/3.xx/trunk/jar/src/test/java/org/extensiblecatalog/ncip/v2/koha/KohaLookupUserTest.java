package org.extensiblecatalog.ncip.v2.koha;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class KohaLookupUserTest extends TestCase {
	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {

		KohaLookupUserService service = new KohaLookupUserService();
		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();
		LookupUserInitiationData initData = new LookupUserInitiationData();
		LookupUserResponseData responseData = new LookupUserResponseData();

		// Input:

		String user = "3";

		UserId userId = new UserId();
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
		String surname = "Kozlovský";
		String givenName = "Jiří";
		String prefix = "Mr";

		String categoryCode = "KN";

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		initData.setInitiationHeader(initiationHeader);

		responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null);

		assertEquals("Unexpected Surname returned.", surname, responseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
				.getStructuredPersonalUserName().getSurname());

		assertEquals("Unexpected GivenName returned.", givenName, responseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
				.getStructuredPersonalUserName().getGivenName());

		List<UserAddressInformation> userAddressInformations = responseData.getUserOptionalFields().getUserAddressInformations();
		assertTrue("No UserAddressInformation returned ..", userAddressInformations != null && userAddressInformations.size() > 0);

		assertEquals("Unexpected privilege description returned", categoryCode, responseData.getUserOptionalFields().getUserPrivilege(0).getAgencyUserPrivilegeType().getValue());
		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());
	}
}