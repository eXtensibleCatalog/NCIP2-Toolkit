package org.extensiblecatalog.ncip.v2.koha;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicRecordIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.Version1MediumType;
import org.xml.sax.SAXException;

public class KohaLookupItemSet_RecordIdsTest extends TestCase {

	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
		KohaLookupItemSetService service = new KohaLookupItemSetService();

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();

		// Inputs:
		String agency = "KOH";
		String bibRecIds[] = { "KOH01000000421", "KOH01000062021", "KOH01000000425", "KOH01000385610" };
		int bibIdsCount = bibRecIds.length;

		int maximumItemsCount = 0;

		// Outputs:
		String[] isbns = { "80-900870-1-9", "0-13-914622-9", "80-85605-77-5", "261H001467" };

		String[] authors = { "Malinová, Libuše", "Brammer, Lawrence M.", "Elman, Jiří", "Náhoda (hudební skupina)" };

		String[] publishers = { "EPA,", "Prentice-Hall,", "Victoria Publishing,", "Studio ASD," };

		String[] titles = { "Anglicko-český a česko-anglický elektrotechnický a elektronický slovník / Sest. aut. kol. pod",
				"Therapeutic psychology : fundamentals of counseling and psychotherapy / Lawrence M. Brammer, Everett",
				"Anglicko-český ekonomický slovník : Ekonomie, právo, výpočetní technika. [2.] M-Z / ..., Kam", "Náhoda [zvukový záznam] / Náhoda " };

		String[] mediumTypes = { Version1MediumType.BOOK.getValue(), Version1MediumType.BOOK.getValue(), Version1MediumType.BOOK.getValue(),
				Version1MediumType.AUDIO_TAPE.getValue() };

		String[][] callN0s = { { "621.3 ANG" }, { "PK-0083.568" }, { "2-0997.767,2", "2-0997.767,2" }, { "KZ-0001.436" } };

		String[][] itemIds = { { "KOH01000000421-KOH50000000421000010" }, { "KOH01000062021-KOH50000062021000010" },
				{ "KOH01000000425-KOH50000000425000020", "KOH01000000425-KOH50000000425000030" }, { "KOH01000385610-KOH50000385610000010" } };

		String[][] barcodes = { { "2610002885" }, { "3119972468" }, { "2610007443", "2610008987" }, { "261H001467" } };

		String[][] itemRestrictions = { { "In Library Use Only" }, { "Limited Circulation, Normal Loan Period" },
				{ "In Library Use Only", "Limited Circulation, Normal Loan Period" }, { "Supervision Required" } };

		String[][] numberOfPieces = { { "1" }, { "1" }, { "2", "2" }, { "1" } };

		String[][] circStatuses = { { Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue() }, { Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue() },
				{ Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue(), Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue() },
				{ Version1CirculationStatus.AVAILABLE_ON_SHELF.getValue() } };

		LookupItemSetResponseData responseData;
		LookupItemSetInitiationData initData;

		List<BibliographicId> bibliographicIds;

		int processedItemInfos, processedBibInfos, itemsParsedInInnerWhileCycle;
		boolean maxReachedWithinItemInfos, maxReachedWithinBibInfos, nextItemTokenIsNull;
		String parsedNextItemToken;
		HoldingsSet holdSet;
		ItemInformation itemInfo;

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);

		while (++maximumItemsCount <= bibIdsCount) {
			processedItemInfos = 0;
			processedBibInfos = 0;
			maxReachedWithinItemInfos = false;
			maxReachedWithinBibInfos = false;

			nextItemTokenIsNull = true;
			parsedNextItemToken = null;

			initData = new LookupItemSetInitiationData();

			bibliographicIds = createBibRecordIdList(agency, bibRecIds);

			initData.setBibliographicIds(bibliographicIds);

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

				itemsParsedInInnerWhileCycle = 0;

				if (parsedNextItemToken != null)
					initData.setNextItemToken(parsedNextItemToken);

				responseData = service.performService(initData, null, serviceManager);

				nextItemTokenIsNull = responseData.getNextItemToken() == null;

				if (!nextItemTokenIsNull)
					parsedNextItemToken = responseData.getNextItemToken();

				assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

				String fName = "";
				assertEquals(fName, fName, fName);
				assertEquals("Responder did not return all items", true, responseData.getBibInformations().size() == bibliographicIds.size() ? true : !nextItemTokenIsNull);
				assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
				assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());

				for (int i = 0; i < bibliographicIds.size(); i++) {

					holdSet = responseData.getBibInformation(i).getHoldingsSet(0);

					assertEquals("Returned bibliographicRecordIdentifies doesn't equal to input.", bibRecIds[processedBibInfos], responseData.getBibInformation(i)
							.getBibliographicId().getBibliographicRecordId().getBibliographicRecordIdentifier());
					assertEquals("Unexpected Author returned.", authors[processedBibInfos], holdSet.getBibliographicDescription().getAuthor());
					if (processedBibInfos < 3)
						assertEquals("Unexpected ISBN returned.", isbns[processedBibInfos], holdSet.getBibliographicDescription().getBibliographicItemId(0)
								.getBibliographicItemIdentifier());
					assertEquals("Unexpected Publisher returned.", publishers[processedBibInfos], holdSet.getBibliographicDescription().getPublisher());
					assertEquals("Unexpected Title returned.", titles[processedBibInfos], holdSet.getBibliographicDescription().getTitle());
					assertEquals("Unexpected MediumType returned.", mediumTypes[processedBibInfos], holdSet.getBibliographicDescription().getMediumType().getValue());

					for (int j = 0; j < holdSet.getItemInformations().size(); j++) {

						itemInfo = holdSet.getItemInformation(j);

						assertEquals("Unexpected Circulation Status returned", circStatuses[processedBibInfos][processedItemInfos], itemInfo.getItemOptionalFields()
								.getCirculationStatus().getValue());
						assertEquals("Unexpected Accession Number returned. (Koha Item Id)", itemIds[processedBibInfos][processedItemInfos], itemInfo.getItemId()
								.getItemIdentifierValue());
						assertEquals("Unexpected Barcode returned. (Legal Deposit Number)", barcodes[processedBibInfos][processedItemInfos], itemInfo.getItemOptionalFields()
								.getBibliographicDescription().getBibliographicItemId(0).getBibliographicItemIdentifier());
						assertEquals("Unexpected Item Use Restriction Type returned.", itemRestrictions[processedBibInfos][processedItemInfos], itemInfo.getItemOptionalFields()
								.getItemUseRestrictionType(0).getValue());
						assertEquals("Unexpected Call number returned.", callN0s[processedBibInfos][processedItemInfos], itemInfo.getItemOptionalFields().getItemDescription()
								.getCallNumber());
						assertEquals("Unexpected Number of pieces returned.", numberOfPieces[processedBibInfos][processedItemInfos], itemInfo.getItemOptionalFields()
								.getItemDescription().getNumberOfPieces().toString());

						++processedItemInfos;
						++itemsParsedInInnerWhileCycle;

						if (processedItemInfos == itemIds[processedBibInfos].length)
							processedItemInfos = 0;
						else {
							maxReachedWithinItemInfos = processedItemInfos == maximumItemsCount;

							if (maxReachedWithinItemInfos) {
								maxReachedWithinItemInfos = false;
								break;
							}
						}
					}
					if (!maxReachedWithinItemInfos) {

						if (processedItemInfos == 0)
							++processedBibInfos;

						maxReachedWithinBibInfos = itemsParsedInInnerWhileCycle == maximumItemsCount;

						if (maxReachedWithinBibInfos) {
							maxReachedWithinBibInfos = false;
							break;
						}

					} else
						break;
				}
			} while (processedBibInfos != bibIdsCount);
		}
	}

	private List<BibliographicId> createBibRecordIdList(String agency, String... bibIdValue) {
		List<BibliographicId> bibliographicIds = new ArrayList<BibliographicId>();
		AgencyId agencyId = new AgencyId(agency);

		BibliographicId bibliographicId;
		BibliographicRecordId bibliographicRecordId;

		for (String bibId : bibIdValue) {
			bibliographicId = new BibliographicId();
			bibliographicRecordId = new BibliographicRecordId();
			bibliographicRecordId.setAgencyId(agencyId);
			bibliographicRecordId.setBibliographicRecordIdentifier(bibId);
			bibliographicRecordId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER);
			bibliographicId.setBibliographicRecordId(bibliographicRecordId);
			bibliographicIds.add(bibliographicId);
		}
		return bibliographicIds;
	}
}