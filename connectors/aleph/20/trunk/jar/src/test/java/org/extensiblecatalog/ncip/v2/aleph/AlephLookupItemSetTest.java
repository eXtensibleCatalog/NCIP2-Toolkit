package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import junit.framework.TestCase;

public class AlephLookupItemSetTest extends TestCase {

	//FIXME: Add nextitemtoken test!
	public void testPerformService() throws ServiceException {
		AlephLookupItemSetService service = new AlephLookupItemSetService();

		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		// Inputs:
		String agency = "MZK";
		String bibRecIds[] = { "MZK01000000421", "MZK01000062021", "MZK01000000425", "MZK01000385610" };

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

		String[][] itemIds = { { "MZK01000000421-MZK50000000421000010" }, { "MZK01000062021-MZK50000062021000010" },
				{ "MZK01000000425-MZK50000000425000020", "MZK01000000425-MZK50000000425000030" }, { "MZK01000385610-MZK50000385610000010" } };

		String[][] barcodes = { { "2610002885" }, { "3119972468" }, { "2610007443", "2610008987" }, { "261H001467" } };

		String[][] itemRestrictions = { { "In Library Use Only" }, { "Limited Circulation, Normal Loan Period" },
				{ "In Library Use Only", "Limited Circulation, Normal Loan Period" }, { "Supervision Required" } };

		String[][] numberOfPieces = { { "1" }, { "1" }, { "2", "2" }, { "1" } };

		LookupItemSetInitiationData initData = new LookupItemSetInitiationData();

		List<BibliographicId> bibliographicIds = createBibRecordIdList(agency, bibRecIds);

		int bibIdsCount = bibliographicIds.size();

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

		LookupItemSetResponseData responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		String fName = "";
		assertEquals(fName, fName, fName);
		assertEquals("Responder did not return all items", true, responseData.getBibInformations().size() == bibIdsCount ? true : responseData.getNextItemToken() != null);

		for (int i = 0; i < bibIdsCount; i++) {
			HoldingsSet holdSet = responseData.getBibInformation(i).getHoldingsSet(0);

			assertEquals("Returned bibliographicRecordIdentifies doesn't equal to input.", bibRecIds[i], responseData.getBibInformation(i).getBibliographicId()
					.getBibliographicRecordId().getBibliographicRecordIdentifier());
			assertEquals("Unexpected Author returned.", authors[i], holdSet.getBibliographicDescription().getAuthor());
			assertEquals("Unexpected ISBN returned.", isbns[i], holdSet.getBibliographicDescription().getBibliographicItemId(0).getBibliographicItemIdentifier());
			assertEquals("Unexpected Publisher returned.", publishers[i], holdSet.getBibliographicDescription().getPublisher());
			assertEquals("Unexpected Title returned.", titles[i], holdSet.getBibliographicDescription().getTitle());
			assertEquals("Unexpected MediumType returned.", mediumTypes[i], holdSet.getBibliographicDescription().getMediumType().getValue());

			for (int j = 0; j < holdSet.getItemInformations().size(); j++) {
				ItemInformation itemInfo = holdSet.getItemInformation(j);

				assertEquals("Unexpected Accession Number returned. (Aleph Item Id)", itemIds[i][j], itemInfo.getItemId().getItemIdentifierValue());
				assertEquals("Unexpected Barcode returned. (Legal Deposit Number)", barcodes[i][j], itemInfo.getItemOptionalFields().getBibliographicDescription()
						.getBibliographicItemId(0).getBibliographicItemIdentifier());
				assertEquals("Unexpected Item Use Restriction Type returned.", itemRestrictions[i][j], itemInfo.getItemOptionalFields().getItemUseRestrictionType(0).getValue());
				assertEquals("Unexpected Call number returned.", callN0s[i][j], itemInfo.getItemOptionalFields().getItemDescription().getCallNumber());
				assertEquals("Unexpected Number of pieces returned.", numberOfPieces[i][j], itemInfo.getItemOptionalFields().getItemDescription().getNumberOfPieces().toString());
			}

		}

	}

	List<BibliographicId> createBibRecordIdList(String agency, String... bibIdValue) {
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