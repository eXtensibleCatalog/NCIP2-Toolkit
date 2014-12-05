package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestScopeType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.xml.sax.SAXException;

public class AlephRequestAndCancelRequestItemTest extends TestCase {
	public void testRequestItems() throws ServiceException, ParserConfigurationException, SAXException {
		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		//
		// Test RequestItemService first
		//

		AlephRequestItemService requestItemService = new AlephRequestItemService();
		RequestItemInitiationData requestItemInitData = new RequestItemInitiationData();
		RequestItemResponseData requestItemResponseData = new RequestItemResponseData();

		// Input:

		AgencyId agencyId = new AgencyId("MZK");

		// Test multiple Requests
		String[] itemIdVals = { "MZK01001333770-MZK50001370317000010", "MZK01001333770-MZK50001370317000040", "MZK01001333770-MZK50001370317000050",
				"MZK01000000000-MZK50000000000000000", "MZK01001423230-MZK50001458432000020", "" };
		int itemIdsCountAboutToFailRequest = 3;

		String userIdVal = "701";

		UserId userId = new UserId();
		userId.setAgencyId(agencyId);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		userId.setUserIdentifierValue(userIdVal);
		requestItemInitData.setUserId(userId);

		List<ItemId> itemIds = new ArrayList<ItemId>();

		for (String itemIdVal : itemIdVals) {
			ItemId itemId = new ItemId();
			itemId.setAgencyId(agencyId);
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			itemId.setItemIdentifierValue(itemIdVal);
			itemIds.add(itemId);
		}
		requestItemInitData.setItemIds(itemIds);

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

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("MZK-Aleph"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("MZK-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		requestItemInitData.setInitiationHeader(initiationHeader);

		int itemIdsCount = requestItemInitData.getItemIds().size();
		// Ouput:

		requestItemResponseData = requestItemService.performService(requestItemInitData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, requestItemResponseData.getProblems() == null || requestItemResponseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, requestItemResponseData.getUserId().getUserIdentifierValue());

		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), requestItemResponseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), requestItemResponseData.getResponseHeader().getFromAgencyId().getAgencyId()
				.getValue());

		// This is what we are expecting on output ... merged itemIds because responseData of request item doesn't support multiple item ids
		String joinedItemIds = "";
		int itemIdsSize = requestItemInitData.getItemIds().size();
		for (int i = 0; i < itemIdsSize; i++) {

			String itemIdVal = requestItemInitData.getItemId(i).getItemIdentifierValue();

			if (itemIdVal.isEmpty())
				itemIdVal = "null";

			joinedItemIds += itemIdVal;

			if (i != itemIdsSize - 1)
				joinedItemIds += AlephConstants.REQUEST_ID_DELIMITER;

		}
		assertEquals("Unexpected ItemIds returned.", joinedItemIds, requestItemResponseData.getItemId().getItemIdentifierValue());

		String[] requestIds = requestItemResponseData.getRequestId().getRequestIdentifierValue().split(String.valueOf(AlephConstants.REQUEST_ID_DELIMITER));

		for (String requestId : requestIds) {
			// We have two options here:
			// 1) requestId is a nine-digit number, which means request was a success
			// 2) requestId specifies occurred error - than its length is at least 20 chars
			// 		- shortest error returned by Aleph ILS is "Group does not exist"

			boolean isCorrect = requestId.length() == AlephConstants.REQUEST_ID_LENGTH || requestId.length() > 19;

			assertEquals("Unexpected RequestIdentifierValue length returned.", true, isCorrect);
		}
		assertEquals("Unexpected RequestType returned.", Version1RequestType.HOLD.getValue(), requestItemResponseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), requestItemResponseData.getRequestScopeType().getValue());

		//
		// Now test CancelRequestItemService
		// We will cancel all recently requested items one by one :-(
		//

		for (int i = 0; i < itemIdVals.length - itemIdsCountAboutToFailRequest; i++) {

			AlephCancelRequestItemService cancelRequestItemService = new AlephCancelRequestItemService();
			CancelRequestItemInitiationData cancelRequestItemInitData = new CancelRequestItemInitiationData();
			CancelRequestItemResponseData cancelRequestItemResponseData = new CancelRequestItemResponseData();

			cancelRequestItemInitData.setUserId(userId);
			ItemId itemId = new ItemId();
			itemId.setAgencyId(agencyId);
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			itemId.setItemIdentifierValue(itemIdVals[i]);
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

			cancelRequestItemInitData.setInitiationHeader(initiationHeader);

			cancelRequestItemResponseData = cancelRequestItemService.performService(cancelRequestItemInitData, null, serviceManager);

			assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), cancelRequestItemResponseData.getResponseHeader().getToAgencyId().getAgencyId()
					.getValue());
			assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), cancelRequestItemResponseData.getResponseHeader().getFromAgencyId()
					.getAgencyId().getValue());
			assertEquals("Unexpected presence of ns1:Problem element in CancelRequestItemTest. Was testing on " + itemIdVals[i] + " itemId.", true,
					cancelRequestItemResponseData.getProblems() == null || cancelRequestItemResponseData.getProblems().get(0) == null);

			assertEquals("Unexpected UserId returned.", userIdVal, cancelRequestItemResponseData.getUserId().getUserIdentifierValue());
			assertEquals("Unexpected ItemId returned.", itemIdVals[i], cancelRequestItemResponseData.getItemId().getItemIdentifierValue());
		}
	}
}