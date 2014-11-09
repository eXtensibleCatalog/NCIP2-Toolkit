package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
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
		
		// Test multiple Requests
		String[] itemIdVals = { "MZK01001333770-MZK50001370317000010", "MZK01001333770-MZK50001370317000040", "MZK01001333770-MZK50001370317000050" };
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

		int itemIdsCount = requestItemInitData.getItemIds().size();
		// Ouput:

		int requestIdentifierLength = 9 * itemIdsCount + itemIdsCount - 1;

		requestItemResponseData = requestItemService.performService(requestItemInitData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, requestItemResponseData.getProblems() == null || requestItemResponseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, requestItemResponseData.getUserId().getUserIdentifierValue());

		// This is what we are expecting on output ... merged itemIds because responseData of request item doesn't support multiple item ids
		String joinedItemIds = "";
		int itemIdsSize = requestItemInitData.getItemIds().size();
		for (int i = 0; i < itemIdsSize; i++) {
			if (i != itemIdsSize - 1) {
				joinedItemIds += requestItemInitData.getItemId(i).getItemIdentifierValue() + AlephConstants.REQUEST_ID_DELIMITER;
			} else
				joinedItemIds += requestItemInitData.getItemId(i).getItemIdentifierValue();
		}
		assertEquals("Unexpected ItemId returned.", joinedItemIds, requestItemResponseData.getItemId().getItemIdentifierValue());

		assertEquals("Unexpected length of RequestIdentifierValue returned.", requestIdentifierLength, requestItemResponseData.getRequestId().getRequestIdentifierValue().length());

		assertEquals("Unexpected RequestType returned.", Version1RequestType.HOLD.getValue(), requestItemResponseData.getRequestType().getValue());
		assertEquals("Unexpected RequestScopeType retuned.", Version1RequestScopeType.ITEM.getValue(), requestItemResponseData.getRequestScopeType().getValue());

		//
		// Now test CancelRequestItemService
		// We will cancel all recently requested items one by one :-(
		//

		for (String itemIdVal : itemIdVals) {
			AlephCancelRequestItemService cancelRequestItemService = new AlephCancelRequestItemService();
			CancelRequestItemInitiationData cancelRequestItemInitData = new CancelRequestItemInitiationData();
			CancelRequestItemResponseData cancelRequestItemResponseData = new CancelRequestItemResponseData();

			cancelRequestItemInitData.setUserId(userId);
			ItemId itemId = new ItemId();
			itemId.setAgencyId(agencyId);
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			itemId.setItemIdentifierValue(itemIdVal);
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
		}
	}
}