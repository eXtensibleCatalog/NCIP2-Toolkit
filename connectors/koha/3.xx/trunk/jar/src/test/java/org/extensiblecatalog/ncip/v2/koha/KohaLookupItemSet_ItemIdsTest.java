package org.extensiblecatalog.ncip.v2.koha;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.ILSDIvOneOneLookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.xml.sax.SAXException;

public class KohaLookupItemSet_ItemIdsTest extends TestCase {

	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
		KohaLookupItemSetService service = new KohaLookupItemSetService();

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();

		// Inputs:
		String agency = "KOH";

		String[] itemIdValues = { "118", "142", "150", "1426", };

		int itemIdsCount = itemIdValues.length;
		int maximumItemsCount = 0;

		// Outputs:
		String[] isbns = { "80-85941-28-7 :", "80-87197-000-X :", "80-7176-163-X :", "80-7186-097-2 :" };

		String[] authors = { "Grey, Zane,", "L'Amour, Louis,", "Pearson, Ridley,", "Blyton, Enid," };

		String[] publishers = { "Oddych,", "Tallpress,", "Knižní klub,", "Egmont ČR," };

		String[] titles = { "Ztracené Pueblo /", "Hora pokladů /", "Dobrodinec /", "Správná pětka." };

		String[] mediumTypes = { "KN", "KN", "KN", "KN" };

		String[] callN0s = { null, null, null, "M/II" };

		ILSDIvOneOneLookupItemSetInitiationData initData;
		List<ItemId> itemIds;
		String parsedNextItemToken;

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);

		int processedItems;
		boolean nextItemTokenIsNull;

		HoldingsSet holdSet;
		ItemInformation itemInfo;

		LookupItemSetResponseData responseData;

		while (++maximumItemsCount <= itemIdsCount) {

			processedItems = 0;

			nextItemTokenIsNull = true;
			parsedNextItemToken = null;

			initData = new ILSDIvOneOneLookupItemSetInitiationData();

			itemIds = createItemIdList(agency, itemIdValues);
			initData.setItemIds(itemIds);

			initData.setBibliographicDescriptionDesired(true);
			initData.setCirculationStatusDesired(true);
			initData.setCurrentBorrowerDesired(true);
			initData.setCurrentRequestersDesired(true);
			initData.setElectronicResourceDesired(true);
			initData.setHoldQueueLengthDesired(true);
			initData.setItemDescriptionDesired(true);
			initData.setItemUseRestrictionTypeDesired(true);
			initData.setLocationDesired(true);
			initData.setSensitizationFlagDesired(true);

			initData.setInitiationHeader(initiationHeader);

			initData.setMaximumItemsCount(new BigDecimal(maximumItemsCount));
			parsedNextItemToken = null;
			initData.setNextItemToken(parsedNextItemToken);
			do {

				if (parsedNextItemToken != null)
					initData.setNextItemToken(parsedNextItemToken);

				responseData = service.performService(initData, null, serviceManager);

				nextItemTokenIsNull = responseData.getNextItemToken() == null;

				if (!nextItemTokenIsNull)
					parsedNextItemToken = responseData.getNextItemToken();

				assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

				String fName = "";
				assertEquals(fName, fName, fName);
				assertEquals("Responder did not return all items", true, responseData.getBibInformations().size() == itemIds.size() ? true : !nextItemTokenIsNull);
				assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
				assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());

				for (int processedItemIds = 0; processedItemIds < itemIds.size();) {
					holdSet = responseData.getBibInformation(processedItemIds).getHoldingsSet(0);

					assertEquals("Unexpected BibliographicItemId returned. (Koha Item Id)", itemIdValues[processedItems], responseData.getBibInformation(processedItemIds)
							.getBibliographicId().getBibliographicItemId().getBibliographicItemIdentifier());

					assertEquals("Returned bibliographicRecordIdentifies doesn't equal to input.", itemIdValues[processedItems], responseData.getBibInformation(processedItemIds)
							.getBibliographicId().getBibliographicItemId().getBibliographicItemIdentifier());
					assertEquals("Unexpected Author returned.", authors[processedItems], holdSet.getItemInformation(0).getItemOptionalFields().getBibliographicDescription()
							.getAuthor());
					if (processedItems < 4)
						assertEquals("Unexpected ISBN returned.", isbns[processedItems], holdSet.getItemInformation(0).getItemOptionalFields().getBibliographicDescription()
								.getBibliographicItemId(0).getBibliographicItemIdentifier());
					assertEquals("Unexpected Publisher returned.", publishers[processedItems], holdSet.getItemInformation(0).getItemOptionalFields().getBibliographicDescription()
							.getPublisher());
					assertEquals("Unexpected Title returned.", titles[processedItems], holdSet.getItemInformation(0).getItemOptionalFields().getBibliographicDescription()
							.getTitle());
					assertEquals("Unexpected MediumType returned.", mediumTypes[processedItems], holdSet.getItemInformation(0).getItemOptionalFields()
							.getBibliographicDescription().getMediumType().getValue());

					itemInfo = holdSet.getItemInformation(0);

					assertEquals("Unexpected Call number returned.", callN0s[processedItems], itemInfo.getItemOptionalFields().getItemDescription().getCallNumber());

					++processedItems;
					if (++processedItemIds == maximumItemsCount)
						break;
				}

			} while (processedItems != itemIdsCount);
		}
	}

	private List<ItemId> createItemIdList(String agency, String... itemIdValues) {
		List<ItemId> itemIds = new ArrayList<ItemId>();
		AgencyId agencyId = new AgencyId(agency);
		ItemId itemId;

		for (String itemIdVal : itemIdValues) {
			itemId = new ItemId();
			itemId.setAgencyId(agencyId);
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			itemId.setItemIdentifierValue(itemIdVal);
			itemIds.add(itemId);
		}
		return itemIds;
	}
}