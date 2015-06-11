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
import org.extensiblecatalog.ncip.v2.service.ILSDIvOneOneLookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicRecordIdentifierCode;
import org.xml.sax.SAXException;

public class KohaLookupItemSet_RecordIdsTest extends TestCase {

	public void testPerformService() throws ServiceException, ParserConfigurationException, SAXException {
		KohaLookupItemSetService service = new KohaLookupItemSetService();

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();

		// Inputs:
		String bibRecIds[] = { "96", "110", "115", "1200" };
		int bibIdsCount = bibRecIds.length;

		int maximumItemsCount = 0;

		// Outputs:
		String[] isbns = { "80-85941-28-7 :", "80-87197-000-X :", "80-7176-163-X :", "80-7186-097-2 :" };

		String[] authors = { "Grey, Zane,", "L'Amour, Louis,", "Pearson, Ridley,", "Blyton, Enid," };

		String[] publishers = { "Oddych,", "Tallpress,", "Knižní klub,", "Egmont ČR," };

		String[] titles = { "Ztracené Pueblo /", "Hora pokladů /", "Dobrodinec /", "Správná pětka." };

		String[][] callN0s = { { null, "PP" }, { null, "PP" }, { null, "PP" }, { "M/II", "PP M/II" } };

		String[][] itemIds = { { "118", "119" }, { "142", "143" }, { "150", "151" }, { "1426", "1427" } };

		String[][] numberOfPieces = { { "2", "2" }, { "2", "2" }, { "2", "2" }, { "2", "2" } };

		LookupItemSetResponseData responseData;
		ILSDIvOneOneLookupItemSetInitiationData initData;

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

			initData = new ILSDIvOneOneLookupItemSetInitiationData();

			bibliographicIds = createBibRecordIdList(bibRecIds);

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

					for (int j = 0; j < holdSet.getItemInformations().size(); j++) {

						itemInfo = holdSet.getItemInformation(j);

						assertEquals("Unexpected Accession Number returned. (Koha Item Id)", itemIds[processedBibInfos][processedItemInfos], itemInfo.getItemId()
								.getItemIdentifierValue());
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

	private List<BibliographicId> createBibRecordIdList(String... bibIdValue) {
		List<BibliographicId> bibliographicIds = new ArrayList<BibliographicId>();

		BibliographicId bibliographicId;
		BibliographicRecordId bibliographicRecordId;

		for (String bibId : bibIdValue) {
			bibliographicId = new BibliographicId();
			bibliographicRecordId = new BibliographicRecordId();
			bibliographicRecordId.setBibliographicRecordIdentifier(bibId);
			bibliographicRecordId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER);
			bibliographicId.setBibliographicRecordId(bibliographicRecordId);
			bibliographicIds.add(bibliographicId);
		}
		return bibliographicIds;
	}
}