package org.extensiblecatalog.ncip.v2.aleph;

import java.util.Arrays;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import junit.framework.TestCase;

public class AlephLookupRequestTest extends TestCase {
	public void testPerformService() throws ServiceException {
		AlephLookupRequestService service = new AlephLookupRequestService();
		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		LookupRequestInitiationData initData = new LookupRequestInitiationData();
		LookupRequestResponseData responseData = new LookupRequestResponseData();

		// Input:
		AgencyId agencyId = new AgencyId("MZK");
		String itemIdVal = "MZK01000065021-MZK50000065021000010";
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
		initData.setItemId(itemId);

		initData.setUserId(initData.getUserId());
		initData.setItemId(initData.getItemId());

		initData.setRequestType(Version1RequestType.HOLD);

		List<RequestElementType> requestElementTypes = Arrays.asList((RequestElementType) Version1RequestElementType.ACKNOWLEDGED_FEE_AMOUNT,
				(RequestElementType) Version1RequestElementType.DATE_AVAILABLE, (RequestElementType) Version1RequestElementType.DATE_OF_USER_REQUEST,
				(RequestElementType) Version1RequestElementType.EARLIEST_DATE_NEEDED, (RequestElementType) Version1RequestElementType.HOLD_QUEUE_POSITION,
				(RequestElementType) Version1RequestElementType.NEED_BEFORE_DATE, (RequestElementType) Version1RequestElementType.PAID_FEE_AMOUNT,
				(RequestElementType) Version1RequestElementType.PICKUP_DATE, (RequestElementType) Version1RequestElementType.PICKUP_EXPIRY_DATE,
				(RequestElementType) Version1RequestElementType.PICKUP_LOCATION, (RequestElementType) Version1RequestElementType.REQUEST_SCOPE_TYPE,
				(RequestElementType) Version1RequestElementType.REQUEST_STATUS_TYPE, (RequestElementType) Version1RequestElementType.REQUEST_TYPE,
				(RequestElementType) Version1RequestElementType.SHIPPING_INFORMATION, (RequestElementType) Version1RequestElementType.USER_ID);

		initData.setRequestElementTypes(requestElementTypes);

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

		int requestIdentifierLength = 9;

		// Output:
		String pickupLocation = "Loan Department - Ground floor";
		;
		String publisher = "Dům techniky ČSVTS,";
		String title = "Aktiv revizních techniků elektrických zařízení";
		String callNumber = "2-0805.673";
		String barcode = "2610034811";
		String medium = "Book";
		String location = "Stock / within 1 hour";
		
		String surName = "Pokus1 Přijmení";
		String street = "Trvalá ulice 123";
		String locality = "Trvalé";
		String postal = "12345 ";

		String electronicAddress = "test@mzk.cz";
		String privilegeDesc = "04 - S";

		//String blockOrTrapType = "Dlouhodobě nevrácená publikace";

		responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned.", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertEquals("Unexpected length of RequestIdentifierValue returned.", requestIdentifierLength, responseData.getRequestId().getRequestIdentifierValue().length());

		assertEquals("Unexpected RequestType returned.", Version1RequestType.ESTIMATE.getValue(), responseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), responseData.getRequestScopeType().getValue());
		assertEquals("Unexpected RequestStatusType returned.", Version1RequestStatusType.IN_PROCESS.getValue(), responseData.getRequestStatusType().getValue());
		assertEquals("Unexpected PickupLocation returned.", pickupLocation, responseData.getPickupLocation().getValue());
		
		assertEquals("Call number incorrect", callNumber, responseData.getItemOptionalFields().getItemDescription().getCallNumber());
		assertEquals("Location incorrect", location, responseData.getItemOptionalFields().getLocations().get(0).getLocationName().getLocationNameInstances().get(1)
				.getLocationNameValue());
		assertEquals("Medium incorrect", medium, responseData.getItemOptionalFields().getBibliographicDescription().getMediumType().getValue());
		assertEquals("Publisher incorrect", publisher, responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
		assertEquals("Title incorrect", title, responseData.getItemOptionalFields().getBibliographicDescription().getTitle());
		
		assertEquals("Unexpected Surname returned.", surName, responseData.getUserOptionalFields().getNameInformation().getPersonalNameInformation()
				.getStructuredPersonalUserName().getSurname());

		StructuredAddress structuredPhysicalAddress = responseData.getUserOptionalFields().getUserAddressInformation(0).getPhysicalAddress().getStructuredAddress();
		assertEquals("Unexpected Street returned.", street, structuredPhysicalAddress.getStreet());
		assertEquals("Unexpected Locality retuned.", locality, structuredPhysicalAddress.getLocality());
		assertEquals("Unexpected Postal returned.", postal, structuredPhysicalAddress.getPostalCode());

		assertEquals("Unexpected Electronic address returned (expecting " + electronicAddress + ")", electronicAddress, responseData.getUserOptionalFields()
				.getUserAddressInformation(1).getElectronicAddress().getElectronicAddressData());

		assertEquals("Unexpected privilege description returned", privilegeDesc, responseData.getUserOptionalFields().getUserPrivilege(0).getUserPrivilegeDescription());
		//assertEquals("Unexpected BlockOrTrapType returned.", blockOrTrapType, responseData.getUserOptionalFields().getBlockOrTrap(0).getBlockOrTrapType().getValue());
	}
}