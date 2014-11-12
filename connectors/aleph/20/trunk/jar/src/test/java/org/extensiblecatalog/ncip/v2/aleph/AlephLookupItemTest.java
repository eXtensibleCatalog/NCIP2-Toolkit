package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;

public class AlephLookupItemTest extends TestCase {

	public void testPerformService() throws IOException, Exception {
		AlephLookupItemService service = new AlephLookupItemService();

		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();

		// Inputs:
		String adm_id = "MZK01000000421-MZK50000000421000010";
		String holdingAgencyId = "MZK";

		// Outputs:
		String author = "Malinová, Libuše";
		String isbn = "80-900870-1-9";
		String title = "Anglicko-český a česko-anglický elektrotechnický a elektronický slovník / Sest. aut. kol. pod";
		String callNumber = "621.3 ANG";
		String publisher = "EPA,";
		String location = "Science, Technology and Medicine / 6th Floor";
		String medium = "Book";

		LookupItemInitiationData initData = new LookupItemInitiationData();
		initData.setBibliographicDescriptionDesired(true);
		initData.setCirculationStatusDesired(true);
		initData.setCurrentBorrowerDesired(true);
		initData.setCurrentRequestersDesired(true);
		initData.setElectronicResourceDesired(true);
		initData.setHoldQueueLengthDesired(true);
		initData.setLocationDesired(true);

		ItemId itemId = new ItemId();
		itemId.setAgencyId(new AgencyId(holdingAgencyId));
		itemId.setItemIdentifierValue(adm_id);
		initData.setItemId(itemId);

		LookupItemResponseData responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Item ID Incorrect", adm_id, responseData.getItemId().getItemIdentifierValue());
		assertEquals("Author incorrect", author, responseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
		assertEquals("ISBN incorrect", isbn, responseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId(0).getBibliographicItemIdentifier());
		assertEquals("Call number incorrect", callNumber, responseData.getItemOptionalFields().getItemDescription().getCallNumber());
		assertEquals("Location incorrect", location, responseData.getItemOptionalFields().getLocations().get(0).getLocationName().getLocationNameInstances().get(1)
				.getLocationNameValue());
		assertEquals("Medium incorrect", medium, responseData.getItemOptionalFields().getBibliographicDescription().getMediumType().getValue());
		assertEquals("Publisher incorrect", publisher, responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
		assertEquals("Title incorrect", title, responseData.getItemOptionalFields().getBibliographicDescription().getTitle());
	}
}
