package org.extensiblecatalog.ncip.v2.aleph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

public class AlephLookupItemSet_ItemIdsTest extends TestCase {

	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
		AlephLookupItemSetService service = new AlephLookupItemSetService();

		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		// Inputs:
		String agency = "MZK";
		String itemIdValues[] = { "MZK01000000421-MZK50000000421000010", "MZK01000062021-MZK50000062021000010", "MZK01000000425-MZK50000000425000020",
				"MZK01000000425-MZK50000000425000030", "MZK01000385610-MZK50000385610000010" };

		int itemIdsCount = itemIdValues.length;
		int maximumItemsCount = 0;

		// Outputs:
		String[] isbns = { "80-900870-1-9", "0-13-914622-9", "80-85605-77-5", "80-85605-77-5", "261H001467" };

		String[] authors = { "Malinová, Libuše", "Brammer, Lawrence M.", "Elman, Jiří", "Elman, Jiří", "Náhoda (hudební skupina)" };

		String[] publishers = { "EPA,", "Prentice-Hall,", "Victoria Publishing,", "Victoria Publishing,", "Studio ASD," };

		String[] titles = { "Anglicko-český a česko-anglický elektrotechnický a elektronický slovník / Sest. aut. kol. pod",
				"Therapeutic psychology : fundamentals of counseling and psychotherapy / Lawrence M. Brammer, Everett",
				"Anglicko-český ekonomický slovník : Ekonomie, právo, výpočetní technika. [2.] M-Z / ..., Kam",
				"Anglicko-český ekonomický slovník : Ekonomie, právo, výpočetní technika. [2.] M-Z / ..., Kam", "Náhoda [zvukový záznam] / Náhoda " };

		String[] mediumTypes = { Version1MediumType.BOOK.getValue(), Version1MediumType.BOOK.getValue(), Version1MediumType.BOOK.getValue(), Version1MediumType.BOOK.getValue(),
				Version1MediumType.AUDIO_TAPE.getValue() };

		String[] callN0s = { "621.3 ANG", "PK-0083.568", "2-0997.767,2", "2-0997.767,2", "KZ-0001.436" };

		String[] barcodes = { "2610002885", "3119972468", "2610007443", "2610008987", "261H001467" };

		String[] itemRestrictions = { "In Library Use Only", "Limited Circulation, Normal Loan Period", "In Library Use Only", "Limited Circulation, Normal Loan Period",
				"Supervision Required" };

		String[] circStatuses = { Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue(), Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue(),
				Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue(), Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue(),
				Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue() };

		LookupItemSetInitiationData initData;
		List<ItemId> itemIds;
		String parsedNextItemToken;

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("MZK-Aleph"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("MZK-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);

		int processedItems, itemsParsedInInnerWhileCycle;
		boolean nextItemTokenIsNull;

		HoldingsSet holdSet;
		ItemInformation itemInfo;

		LookupItemSetResponseData responseData;

		while (++maximumItemsCount <= itemIdsCount) {

			processedItems = 0;

			nextItemTokenIsNull = true;
			parsedNextItemToken = null;

			initData = new LookupItemSetInitiationData();

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

					assertEquals("Returned bibliographicRecordIdentifies doesn't equal to input.", itemIdValues[processedItems], responseData.getBibInformation(processedItemIds)
							.getBibliographicId().getBibliographicItemId().getBibliographicItemIdentifier());
					assertEquals("Unexpected Author returned.", authors[processedItems], holdSet.getBibliographicDescription().getAuthor());
					if (processedItems < 4)
						assertEquals("Unexpected ISBN returned.", isbns[processedItems], holdSet.getBibliographicDescription().getBibliographicItemId(0)
								.getBibliographicItemIdentifier());
					assertEquals("Unexpected Publisher returned.", publishers[processedItems], holdSet.getBibliographicDescription().getPublisher());
					assertEquals("Unexpected Title returned.", titles[processedItems], holdSet.getBibliographicDescription().getTitle());
					assertEquals("Unexpected MediumType returned.", mediumTypes[processedItems], holdSet.getBibliographicDescription().getMediumType().getValue());

					itemInfo = holdSet.getItemInformation(0);

					assertEquals("Unexpected Circulation Status returned", circStatuses[processedItems], itemInfo.getItemOptionalFields().getCirculationStatus().getValue());
					assertEquals("Unexpected Accession Number returned. (Aleph Item Id)", itemIdValues[processedItems], itemInfo.getItemId().getItemIdentifierValue());
					assertEquals("Unexpected Barcode returned. (Legal Deposit Number)", barcodes[processedItems], itemInfo.getItemOptionalFields().getBibliographicDescription()
							.getBibliographicItemId(0).getBibliographicItemIdentifier());
					assertEquals("Unexpected Item Use Restriction Type returned.", itemRestrictions[processedItems], itemInfo.getItemOptionalFields().getItemUseRestrictionType(0)
							.getValue());
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