package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;

public class KohaLookupItemTest extends TestCase {

	public void testPerformService() throws IOException, Exception {
		KohaLookupItemService service = new KohaLookupItemService();

		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();

		// Inputs:
		String adm_id = "5";

		// Outputs:
		String author = "Lamb, Richard,";
		String isbn = "80-7191-136-4 :";
		String title = "Válka v Itálii 1943-1945 /";
		String callNumber = "940.53\"1943/1945";
		String publisher = "Mustang,";
		String location = "DOSP";
		String medium = "KN";

		LookupItemInitiationData initData = new LookupItemInitiationData();
		initData.setBibliographicDescriptionDesired(true);
		initData.setCirculationStatusDesired(true);
		initData.setElectronicResourceDesired(true);
		initData.setHoldQueueLengthDesired(true);
		initData.setItemDescriptionDesired(true);
		initData.setLocationDesired(true);

		ItemId itemId = new ItemId();
		itemId.setItemIdentifierValue(adm_id);
		initData.setItemId(itemId);

		InitiationHeader initiationHeader = new InitiationHeader();

		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));

		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));

		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		initData.setInitiationHeader(initiationHeader);

		LookupItemResponseData responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertTrue("BibliographicDescription was not returned", responseData.getItemOptionalFields().getBibliographicDescription() != null);
		assertTrue("HoldQueueLength was not returned", responseData.getItemOptionalFields().getHoldQueueLength() != null);
		assertTrue("CirculationStatus was not returned", responseData.getItemOptionalFields().getCirculationStatus() != null);

		assertEquals("Item ID Incorrect", adm_id, responseData.getItemId().getItemIdentifierValue());
		assertEquals("Author incorrect", author, responseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
		assertEquals("ISBN incorrect", isbn, responseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId(0).getBibliographicItemIdentifier());
		assertEquals("Call number incorrect", callNumber, responseData.getItemOptionalFields().getItemDescription().getCallNumber());
		assertEquals("Location incorrect", location, responseData.getItemOptionalFields().getLocations().get(0).getLocationName().getLocationNameInstances().get(1)
				.getLocationNameValue());
		assertEquals("Medium incorrect", medium, responseData.getItemOptionalFields().getBibliographicDescription().getMediumType().getValue());
		assertEquals("Publisher incorrect", publisher, responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
		assertEquals("Title incorrect", title, responseData.getItemOptionalFields().getBibliographicDescription().getTitle());
		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());

	}
}
