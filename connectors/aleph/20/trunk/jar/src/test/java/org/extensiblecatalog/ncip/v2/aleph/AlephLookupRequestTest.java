package org.extensiblecatalog.ncip.v2.aleph;

import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData;
import org.extensiblecatalog.ncip.v2.service.RequestElementType;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.StructuredAddress;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Version1LocationType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestElementType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestScopeType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.xml.sax.SAXException;

public class AlephLookupRequestTest extends TestCase {
	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
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
		initData.setItemUseRestrictionTypeDesired(true);

		initData.setBibliographicDescriptionDesired(true);
		initData.setCirculationStatusDesired(true);
		initData.setElectronicResourceDesired(true);
		initData.setHoldQueueLengthDesired(true);
		initData.setItemDescriptionDesired(true);
		initData.setLocationDesired(true);

		int requestIdentifierLength = 9;

		// Output:
		String pickupLocation = "Loan Department - Ground floor";
		;
		String publisher = "Dům techniky ČSVTS,";
		String title = "Aktiv revizních techniků elektrických zařízení";
		String callNumber = "2-0805.673";
		String holdingsInfo = "1996";
		String barcode = "2610034811";
		String medium = "Book";
		String locationLevelOne = "MZK?";
		String locationLevelTwo = "Stock / within 1 hour";
		String circulationStatus = "In process";

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

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned.", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertEquals("Unexpected length of RequestIdentifierValue returned.", requestIdentifierLength, responseData.getRequestId().getRequestIdentifierValue().length());

		assertEquals("Unexpected RequestType retuned.", Version1RequestType.HOLD.getValue(), responseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), responseData.getRequestScopeType().getValue());
		assertEquals("Unexpected RequestStatusType returned.", Version1RequestStatusType.IN_PROCESS.getValue(), responseData.getRequestStatusType().getValue());
		assertEquals("Unexpected PickupLocation returned.", pickupLocation, responseData.getPickupLocation().getValue());

		assertEquals("Unexpected ComponentId returned", barcode, responseData.getItemOptionalFields().getBibliographicDescription().getComponentId().getComponentIdentifier());

		assertEquals("Call number incorrect", callNumber, responseData.getItemOptionalFields().getItemDescription().getCallNumber());
		assertEquals("HoldingsInformation incorrect", holdingsInfo, responseData.getItemOptionalFields().getItemDescription().getHoldingsInformation()
				.getUnstructuredHoldingsData());

		assertEquals("LocationType incorrect", Version1LocationType.PERMANENT_LOCATION.getValue(), responseData.getItemOptionalFields().getLocation(0).getLocationType().getValue());
		assertEquals("Location of first NameLevel incorrect", locationLevelOne, responseData.getItemOptionalFields().getLocations().get(0).getLocationName()
				.getLocationNameInstance(0).getLocationNameValue());
		assertEquals("Location of second NameLevel incorrect", locationLevelTwo, responseData.getItemOptionalFields().getLocations().get(0).getLocationName()
				.getLocationNameInstance(1).getLocationNameValue());

		assertEquals("MediumType incorrect", medium, responseData.getItemOptionalFields().getBibliographicDescription().getMediumType().getValue());
		assertEquals("Publisher incorrect", publisher, responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
		assertEquals("Title incorrect", title, responseData.getItemOptionalFields().getBibliographicDescription().getTitle());

		assertEquals("Unexpected ItemUseRestrictionType returned.", Version1ItemUseRestrictionType.IN_LIBRARY_USE_ONLY.getValue(), responseData.getItemOptionalFields()
				.getItemUseRestrictionType(0).getValue());
		assertEquals("Unexpected CirculationStatus returned.", circulationStatus, responseData.getItemOptionalFields().getCirculationStatus().getValue());

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