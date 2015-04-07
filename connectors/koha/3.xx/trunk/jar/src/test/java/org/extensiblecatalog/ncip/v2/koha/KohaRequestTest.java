package org.extensiblecatalog.ncip.v2.koha;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.dozer.classmap.CopyByReference;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData;
import org.extensiblecatalog.ncip.v2.service.RequestElementType;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemResponseData;
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

public class KohaRequestTest extends TestCase {

	private String itemIdVal = "1";
	private String userIdVal = "3";
	private String pickupLocation = "DOSP";

	private String fromAgency = "CPK-VuFind";
	private String toAgency = "DOSP-CeskaTrebova";

	private InitiationHeader initiationHeader;
	private ItemId itemId;
	private UserId userId;
	private String requestIdVal;

	private ToAgencyId toAgencyId;
	private FromAgencyId fromAgencyId;

	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {

		// Test schedule: LookupRequest ->
		// if (RequestExists) then -> {CancelRequestItem -> LookupRequest -> RequestItem -> LookupRequest}
		// else -> {RequestItem -> LookupRequest -> CancelRequestItem -> LookupRequest}

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();

		KohaRequestItemService requestItemService = new KohaRequestItemService();
		RequestItemResponseData requestItemResponseData;

		KohaLookupRequestService lookupRequestService = new KohaLookupRequestService();
		LookupRequestResponseData lookupRequestResponseData;

		KohaCancelRequestItemService cancelRequestItemService = new KohaCancelRequestItemService();
		CancelRequestItemResponseData cancelRequestItemResponseData;

		initiationHeader = new InitiationHeader();

		toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId(toAgency));

		fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId(fromAgency));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);

		userId = new UserId();
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		userId.setUserIdentifierValue(userIdVal);

		itemId = new ItemId();
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		itemId.setItemIdentifierValue(itemIdVal);

		// ******
		// First step: Look it up ..
		// ******

		lookupRequestResponseData = lookupRequestService.performService(createLookupRequestInitiationData(), null, serviceManager);

		if (!requestWasNotFound(lookupRequestResponseData)) {
			// First scenario ..
			// Request probably exists thus do CancelRequestItem -> LookupReqeust -> RequestItem -> LookupRequest
			// Now test LookupRequest service

			assertLookupRequest(lookupRequestResponseData);

			// Now CancelRequestItem to test the service

			requestIdVal = lookupRequestResponseData.getRequestId().getRequestIdentifierValue();

			cancelRequestItemResponseData = cancelRequestItemService.performService(createCancelRequestItemInitiationData(), null, serviceManager);

			assertCancelRequestItem(cancelRequestItemResponseData);

			// Now LookupRequest to find out if it is really cancelled

			lookupRequestResponseData = lookupRequestService.performService(createLookupRequestInitiationData(), null, serviceManager);

			assertTrue("Request cancellation did not succeed.", requestWasNotFound(lookupRequestResponseData));

			// Now RequestItem to test the service

			requestItemResponseData = requestItemService.performService(createRequestItemInitiationData(), null, serviceManager);

			assertRequestItem(requestItemResponseData);

			// Now LookupRequest to test RequestItem completely

			lookupRequestResponseData = lookupRequestService.performService(createLookupRequestInitiationData(), null, serviceManager);

			assertLookupRequest(lookupRequestResponseData);

		} else {
			// Second scenario .. Request not found, thus do some request, then look it up, cancel it & check cancellation looking it up again

			requestItemResponseData = requestItemService.performService(createRequestItemInitiationData(), null, serviceManager);

			assertRequestItem(requestItemResponseData);

			// Now LookupRequest to test RequestItem & LookupRequest service

			lookupRequestResponseData = lookupRequestService.performService(createLookupRequestInitiationData(), null, serviceManager);

			assertLookupRequest(lookupRequestResponseData);

			// Now CancelRequestItem to test the service

			requestIdVal = lookupRequestResponseData.getRequestId().getRequestIdentifierValue();

			cancelRequestItemResponseData = cancelRequestItemService.performService(createCancelRequestItemInitiationData(), null, serviceManager);

			assertCancelRequestItem(cancelRequestItemResponseData);

			// Now LookupRequest to find out if it is really cancelled

			lookupRequestResponseData = lookupRequestService.performService(createLookupRequestInitiationData(), null, serviceManager);

			assertTrue("Request cancellation did not succeed.", requestWasNotFound(lookupRequestResponseData));

		}
	}

	private void assertLookupRequest(LookupRequestResponseData responseData) {
		assertTrue("Unexpected presence of ns1:Problem element while Looking up request..", responseData.getProblems() == null);

		assertEquals("Unexpected UserId returned while Looking up request..", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned while Looking up request..", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertTrue("RequestIdentifier not returned while Looking up request..", responseData.getRequestId() != null);

		assertTrue("RequestType not retuned while Looking up request..", responseData.getRequestType() != null);
		assertTrue("RequestScopeType not retuned while Looking up request..", responseData.getRequestScopeType() != null);
		assertTrue("RequestStatusType not returned while Looking up request..", responseData.getRequestStatusType() != null);
		assertTrue("DateAvailable not returned while Looking up request..", responseData.getDateAvailable() != null);
		assertTrue("DateOfUserRequest not returned while Looking up request..", responseData.getDateOfUserRequest() != null);
		assertTrue("EarliestDateNeeded not returned while Looking up request..", responseData.getEarliestDateNeeded() != null);
		assertTrue("HoldQueuePosition not returned while Looking up request..", responseData.getHoldQueuePosition() != null);

		assertEquals("Unexpected ToAgencyId returned while Looking up request..", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId()
				.getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned while Looking up request..", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId()
				.getAgencyId().getValue());
		assertEquals("Unexpected PickupLocation returned while Looking up request..", pickupLocation, responseData.getPickupLocation().getValue());
	}

	private void assertCancelRequestItem(CancelRequestItemResponseData responseData) {
		assertTrue("Unexpected presence of ns1:Problem element while Cancelling request..", responseData.getProblems() == null);

		assertEquals("Unexpected ToAgencyId returned while Cancelling request..", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId()
				.getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned while Cancelling request..", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId()
				.getAgencyId().getValue());

		assertEquals("Unexpected RequestId returned while Cancelling request..", requestIdVal, responseData.getRequestId().getRequestIdentifierValue());
		assertEquals("Unexpected ItemId returned while Cancelling request..", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertTrue("RequestIdentifier not returned while Cancelling request..", responseData.getRequestId() != null);
		assertTrue("RequestIdentifier not returned while Cancelling request..", responseData.getUserId() != null);

		assertEquals("Unexpected ToAgencyId returned while Looking up request..", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId()
				.getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned while Looking up request..", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId()
				.getAgencyId().getValue());
	}

	private void assertRequestItem(RequestItemResponseData responseData) {
		assertTrue("Unexpected presence of ns1:Problem element while Requesting an item ..", responseData.getProblems() == null);

		assertTrue("RequestIdentifier not returned while Requesting an item ..", responseData.getRequestId() != null);

		assertEquals("Unexpected UserId returned while Requesting an item ..", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected ItemId returned while Requesting an item ..", itemIdVal, responseData.getItemId().getItemIdentifierValue());

		assertTrue("RequestType not retuned while Requesting an item ..", responseData.getRequestType() != null);
		assertTrue("RequestScopeType not retuned while Requesting an item ..", responseData.getRequestScopeType() != null);
	}

	private LookupRequestInitiationData createLookupRequestInitiationData() {
		LookupRequestInitiationData initData = new LookupRequestInitiationData();
		initData.setInitiationHeader(initiationHeader);
		initData.setUserId(userId);
		initData.setItemId(itemId);

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

		return initData;
	}

	private RequestItemInitiationData createRequestItemInitiationData() {
		RequestItemInitiationData initData = new RequestItemInitiationData();
		initData.setInitiationHeader(initiationHeader);

		initData.setUserId(userId);
		initData.setItemIds(Arrays.asList(itemId));

		initData.setRequestType(Version1RequestType.HOLD);
		initData.setRequestScopeType(Version1RequestScopeType.ITEM);

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

		return initData;
	}

	private CancelRequestItemInitiationData createCancelRequestItemInitiationData() {
		CancelRequestItemInitiationData initData = new CancelRequestItemInitiationData();
		initData.setInitiationHeader(initiationHeader);
		initData.setUserId(userId);

		initData.setRequestType(Version1RequestType.HOLD);
		initData.setRequestId(KohaUtil.createRequestId(requestIdVal));
		initData.setRequestScopeType(Version1RequestScopeType.ITEM);

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
		return initData;
	}

	private boolean requestWasNotFound(LookupRequestResponseData responseData) {
		return responseData.getProblems() != null && responseData.getProblems().get(0).getProblemType().getValue().equals(KohaException.NOT_FOUND_404);
	}
}