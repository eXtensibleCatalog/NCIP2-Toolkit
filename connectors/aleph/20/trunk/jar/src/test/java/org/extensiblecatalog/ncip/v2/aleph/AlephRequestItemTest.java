package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import junit.framework.TestCase;

public class AlephRequestItemTest extends TestCase {
	public void testPerformService() throws ServiceException {
		AlephRequestItemService service = new AlephRequestItemService();

		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		RequestItemInitiationData initData = new RequestItemInitiationData();
		RequestItemResponseData responseData = new RequestItemResponseData();

		// Input:
		AgencyId agencyId = new AgencyId("MZK");
		String itemIdVal = "MZK01001424423-MZK50001464126000010";
		String userIdVal = "700";

		UserId userId = new UserId();
		userId.setAgencyId(agencyId);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		userId.setUserIdentifierValue(userIdVal);
		initData.setUserId(userId);

		ItemId itemId = new ItemId();
		itemId.setAgencyId(agencyId);
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		itemId.setItemIdentifierValue(itemIdVal);
		initData.setItemIds(Arrays.asList(itemId));

		initData.setRequestType(Version1RequestType.HOLD);
		initData.setRequestScopeType(Version1RequestScopeType.ITEM);

		PickupLocation pickupLocation = new PickupLocation("MZK ");
		initData.setPickupLocation(pickupLocation);

		initData.setAuthenticationInputDesired(true);
		initData.setBlockOrTrapDesired(true);
		initData.setDateOfBirthDesired(true);
		initData.setNameInformationDesired(true);
		initData.setPreviousUserIdDesired(true);
		initData.setUserAddressInformationDesired(true);
		initData.setUserLanguageDesired(true);
		initData.setUserPrivilegeDesired(true);

		initData.setBibliographicDescriptionDesired(true);
		initData.setCirculationStatusDesired(true);
		initData.setElectronicResourceDesired(true);
		initData.setHoldQueueLengthDesired(true);

		GregorianCalendar now = new GregorianCalendar();
		initData.setEarliestDateNeeded(now);
		initData.setNeedBeforeDate(now);
		initData.setPickupExpiryDate(now);

		// Ouput:

		int requestIdentifierLength = 9;

		String author = "Antonín, Robert,";
		String isbn = "978-80-7289-609-7 (brož.)";
		String publisher = "Nová škola,";
		String title = "Dějepis : středověk, počátky novověku : pracovní sešit vytvořený v souladu s RVP ZV / [nap";
		String callNumber = "4-1333.328";

		String surName = "Pokus1 Přijmení";
		String street = "Trvalá ulice 123";
		String locality = "Trvalé";
		String postal = "12345 ";

		String electronicAddress = "test@mzk.cz";
		String privilegeDesc = "04 - S";

		String blockOrTrapType = "Dlouhodobě nevrácená publikace";

		responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element in RequestItemTest.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned.", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertEquals("Unexpected length of RequestIdentifierValue returned.", requestIdentifierLength, responseData.getRequestId().getRequestIdentifierValue().length());
		assertEquals("Unexpected RequestType returned.", Version1RequestType.HOLD.getValue(), responseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), responseData.getRequestScopeType().getValue());

		if (serviceManager.iofDesiredForReqItem()) {
			assertEquals("Item ID Incorrect", itemIdVal, responseData.getItemId().getItemIdentifierValue());
			assertEquals("Author incorrect", author, responseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
			assertEquals("ISBN incorrect", isbn, responseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId(0).getBibliographicItemIdentifier());
			assertEquals("Call number incorrect", callNumber, responseData.getItemOptionalFields().getItemDescription().getCallNumber());
			assertEquals("Publisher incorrect", publisher, responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
			assertEquals("Title incorrect", title, responseData.getItemOptionalFields().getBibliographicDescription().getTitle());
		}

		if (serviceManager.uofDesiredForReqItem()) {
			assertEquals("Unexpected Surname returned.", surName, responseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
					.getStructuredPersonalUserName().getSurname());

			StructuredAddress structuredPhysicalAddress = responseData.getUserOptionalFields().getUserAddressInformation(0).getPhysicalAddress().getStructuredAddress();
			assertEquals("Unexpected Street returned.", street, structuredPhysicalAddress.getStreet());
			assertEquals("Unexpected Locality retuned.", locality, structuredPhysicalAddress.getLocality());
			assertEquals("Unexpected Postal returned.", postal, structuredPhysicalAddress.getPostalCode());

			assertEquals("Unexpected Electronic address returned (expecting " + electronicAddress + ")", electronicAddress, responseData.getUserOptionalFields()
					.getUserAddressInformation(1).getElectronicAddress().getElectronicAddressData());

			assertEquals("Unexpected privilege description returned", privilegeDesc, responseData.getUserOptionalFields().getUserPrivilege(0).getUserPrivilegeDescription());
			assertEquals("Unexpected BlockOrTrapType returned.", blockOrTrapType, responseData.getUserOptionalFields().getBlockOrTrap(0).getBlockOrTrapType().getValue());
		}
	}
}