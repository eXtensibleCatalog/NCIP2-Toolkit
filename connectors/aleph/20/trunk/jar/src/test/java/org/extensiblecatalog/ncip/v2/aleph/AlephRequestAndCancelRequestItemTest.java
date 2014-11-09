package org.extensiblecatalog.ncip.v2.aleph;

import java.util.Arrays;
import java.util.GregorianCalendar;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import junit.framework.TestCase;

public class AlephRequestAndCancelRequestItemTest extends TestCase {
	public void testPerformService() throws ServiceException {
		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		//
		// Test RequestItemService first
		//

		AlephRequestItemService requestItemService = new AlephRequestItemService();
		RequestItemInitiationData requestItemInitData = new RequestItemInitiationData();
		RequestItemResponseData requestItemResponseData = new RequestItemResponseData();

		// Input:

		AgencyId agencyId = new AgencyId("MZK");
		String itemIdVal = "MZK01001424423-MZK50001464126000010";
		String userIdVal = "701";

		UserId userId = new UserId();
		userId.setAgencyId(agencyId);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		userId.setUserIdentifierValue(userIdVal);
		requestItemInitData.setUserId(userId);

		ItemId itemId = new ItemId();
		itemId.setAgencyId(agencyId);
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		itemId.setItemIdentifierValue(itemIdVal);
		requestItemInitData.setItemIds(Arrays.asList(itemId));

		PickupLocation pickupLocation = new PickupLocation("MZK ");
		requestItemInitData.setPickupLocation(pickupLocation);

		requestItemInitData.setAuthenticationInputDesired(true);
		requestItemInitData.setBlockOrTrapDesired(true);
		requestItemInitData.setDateOfBirthDesired(true);
		requestItemInitData.setNameInformationDesired(true);
		requestItemInitData.setPreviousUserIdDesired(true);
		requestItemInitData.setUserAddressInformationDesired(true);
		requestItemInitData.setUserLanguageDesired(true);
		requestItemInitData.setUserPrivilegeDesired(true);

		requestItemInitData.setBibliographicDescriptionDesired(true);
		requestItemInitData.setCirculationStatusDesired(true);
		requestItemInitData.setElectronicResourceDesired(true);
		requestItemInitData.setHoldQueueLengthDesired(true);

		GregorianCalendar now = new GregorianCalendar();
		requestItemInitData.setEarliestDateNeeded(now);
		requestItemInitData.setNeedBeforeDate(now);
		requestItemInitData.setPickupExpiryDate(now);

		requestItemInitData.setRequestType(Version1RequestType.HOLD);
		requestItemInitData.setRequestScopeType(Version1RequestScopeType.ITEM);

		// Ouput:

		int requestIdentifierLength = 9;

		String author = "Antonín, Robert,";
		String isbn = "978-80-7289-609-7 (brož.)";
		String publisher = "Nová škola,";
		String title = "Dějepis : středověk, počátky novověku : pracovní sešit vytvořený v souladu s RVP ZV / [nap";
		String callNumber = "4-1333.328";

		String surName = "Test Vufind";
		String street = "Ulice 123";
		String locality = "Brno";

		String postal = "602 00";

		String electronicAddress = "test@mzk.cz";
		String privilegeDesc = "05 - P";

		String firstBlockOrTrapType = "Pokuta";

		requestItemResponseData = requestItemService.performService(requestItemInitData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element in RequestItemTest.", true, requestItemResponseData.getProblems() == null
				|| requestItemResponseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, requestItemResponseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned.", itemIdVal, requestItemResponseData.getItemId().getItemIdentifierValue());

		assertEquals("Unexpected length of RequestIdentifierValue returned.", requestIdentifierLength, requestItemResponseData.getRequestId().getRequestIdentifierValue().length());

		assertEquals("Unexpected RequestType returned.", Version1RequestType.HOLD.getValue(), requestItemResponseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), requestItemResponseData.getRequestScopeType().getValue());

		if (serviceManager.iofDesiredForReqItem()) {
			assertEquals("Item ID Incorrect", itemIdVal, requestItemResponseData.getItemId().getItemIdentifierValue());
			assertEquals("Author incorrect", author, requestItemResponseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
			assertEquals("ISBN incorrect", isbn, requestItemResponseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId(0)
					.getBibliographicItemIdentifier());
			assertEquals("Call number incorrect", callNumber, requestItemResponseData.getItemOptionalFields().getItemDescription().getCallNumber());
			assertEquals("Publisher incorrect", publisher, requestItemResponseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
			assertEquals("Title incorrect", title, requestItemResponseData.getItemOptionalFields().getBibliographicDescription().getTitle());
		}

		if (serviceManager.uofDesiredForReqItem()) {
			assertEquals("Unexpected Surname returned.", surName, requestItemResponseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
					.getStructuredPersonalUserName().getSurname());

			StructuredAddress structuredPhysicalAddress = requestItemResponseData.getUserOptionalFields().getUserAddressInformation(0).getPhysicalAddress().getStructuredAddress();
			assertEquals("Unexpected Street returned.", street, structuredPhysicalAddress.getStreet());
			assertEquals("Unexpected Locality retuned.", locality, structuredPhysicalAddress.getLocality());
			assertEquals("Unexpected Postal returned.", postal, structuredPhysicalAddress.getPostalCode());

			assertEquals("Unexpected Electronic address returned (expecting " + electronicAddress + ")", electronicAddress, requestItemResponseData.getUserOptionalFields()
					.getUserAddressInformation(1).getElectronicAddress().getElectronicAddressData());

			assertEquals("Unexpected privilege description returned", privilegeDesc, requestItemResponseData.getUserOptionalFields().getUserPrivilege(0)
					.getUserPrivilegeDescription());
			assertEquals("Unexpected BlockOrTrapType returned.", firstBlockOrTrapType, requestItemResponseData.getUserOptionalFields().getBlockOrTrap(0).getBlockOrTrapType()
					.getValue());
		}

		//
		// Now test CancelRequestItemService
		//

		AlephCancelRequestItemService cancelRequestItemService = new AlephCancelRequestItemService();
		CancelRequestItemInitiationData cancelRequestItemInitData = new CancelRequestItemInitiationData();
		CancelRequestItemResponseData cancelRequestItemResponseData = new CancelRequestItemResponseData();

		cancelRequestItemInitData.setUserId(userId);
		cancelRequestItemInitData.setItemId(itemId);

		cancelRequestItemInitData.setRequestType(Version1RequestType.HOLD);
		cancelRequestItemInitData.setRequestScopeType(Version1RequestScopeType.ITEM);

		cancelRequestItemInitData.setAuthenticationInputDesired(true);
		cancelRequestItemInitData.setBlockOrTrapDesired(true);
		cancelRequestItemInitData.setDateOfBirthDesired(true);
		cancelRequestItemInitData.setNameInformationDesired(true);
		cancelRequestItemInitData.setPreviousUserIdDesired(true);
		cancelRequestItemInitData.setUserAddressInformationDesired(true);
		cancelRequestItemInitData.setUserLanguageDesired(true);
		cancelRequestItemInitData.setUserPrivilegeDesired(true);

		cancelRequestItemInitData.setBibliographicDescriptionDesired(true);
		cancelRequestItemInitData.setCirculationStatusDesired(true);
		cancelRequestItemInitData.setElectronicResourceDesired(true);
		cancelRequestItemInitData.setHoldQueueLengthDesired(true);

		cancelRequestItemResponseData = cancelRequestItemService.performService(cancelRequestItemInitData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element in CancelRequestItemTest.", true, cancelRequestItemResponseData.getProblems() == null
				|| cancelRequestItemResponseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, cancelRequestItemResponseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned.", itemIdVal, cancelRequestItemResponseData.getItemId().getItemIdentifierValue());

		if (serviceManager.iofDesiredForCanReqItem()) {
			assertEquals("Item ID Incorrect", itemIdVal, cancelRequestItemResponseData.getItemId().getItemIdentifierValue());
			assertEquals("Author incorrect", author, cancelRequestItemResponseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
			assertEquals("ISBN incorrect", isbn, cancelRequestItemResponseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId(0)
					.getBibliographicItemIdentifier());
			assertEquals("Call number incorrect", callNumber, cancelRequestItemResponseData.getItemOptionalFields().getItemDescription().getCallNumber());
			assertEquals("Publisher incorrect", publisher, cancelRequestItemResponseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
			assertEquals("Title incorrect", title, cancelRequestItemResponseData.getItemOptionalFields().getBibliographicDescription().getTitle());
		}

		if (serviceManager.uofDesiredForCanReqItem()) {
			assertEquals("Unexpected Surname returned.", surName, cancelRequestItemResponseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
					.getStructuredPersonalUserName().getSurname());

			StructuredAddress structuredPhysicalAddress = cancelRequestItemResponseData.getUserOptionalFields().getUserAddressInformation(0).getPhysicalAddress()
					.getStructuredAddress();
			assertEquals("Unexpected Street returned.", street, structuredPhysicalAddress.getStreet());
			assertEquals("Unexpected Locality retuned.", locality, structuredPhysicalAddress.getLocality());
			assertEquals("Unexpected Postal returned.", postal, structuredPhysicalAddress.getPostalCode());

			assertEquals("Unexpected Electronic address returned (expecting " + electronicAddress + ")", electronicAddress, cancelRequestItemResponseData.getUserOptionalFields()
					.getUserAddressInformation(1).getElectronicAddress().getElectronicAddressData());

			assertEquals("Unexpected privilege description returned", privilegeDesc, cancelRequestItemResponseData.getUserOptionalFields().getUserPrivilege(0)
					.getUserPrivilegeDescription());
			assertEquals("Unexpected BlockOrTrapType returned.", firstBlockOrTrapType, cancelRequestItemResponseData.getUserOptionalFields().getBlockOrTrap(0).getBlockOrTrapType()
					.getValue());
		}

	}
}