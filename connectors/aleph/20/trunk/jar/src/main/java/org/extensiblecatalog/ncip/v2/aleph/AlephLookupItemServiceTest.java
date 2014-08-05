package org.extensiblecatalog.ncip.v2.aleph;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.math.BigDecimal;

import junit.framework.TestCase;

import org.extensiblecatalog.ncip.v2.aleph.test.TestConfiguration;
import org.extensiblecatalog.ncip.v2.service.ItemId;
//import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;

public class AlephLookupItemServiceTest extends TestCase {
	
	public AlephLookupItemServiceTest() throws IOException {
	//	NCIPConfiguration config = new NCIPConfiguration();
	//	NCIPConfiguration.getInstance().getProperties().putAll(TestConfiguration.getProperties());
	}
	
	public void testPerformService() throws IOException,Exception {
		AlephLookupItemService service = new AlephLookupItemService();
		
		AlephRemoteServiceManager serviceManager = new AlephRemoteServiceManager();
		
		String bib_id = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String adm_id = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String hold_id = TestConfiguration.getProperty("LOAN_HOLD_ID4");
		String author = TestConfiguration.getProperty("LOAN_AUTHOR_4");
		
		String title = TestConfiguration.getProperty("LOAN_TITLE_4");
		String callNumber = TestConfiguration.getProperty("LOAN_CALLNUMBER_4");
		String publisher = TestConfiguration.getProperty("LOAN_PUBLISHER_4");
		String description = TestConfiguration.getProperty("LOAN_DESCRIPTION_4");
		String series = null;
		String location = TestConfiguration.getProperty("LOAN_LOCATION_4");
		String eresource = TestConfiguration.getProperty("LOAN_ERESOURCE_4");
		String medium = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_4");
		String circStatus = TestConfiguration.getProperty("LOAN_CIRC_STATUS_4");
		String holdingAgencyId = TestConfiguration.getProperty("EXPECTED_AGENCY");
		String holdingId = null;
		BigDecimal holdQueueLength = null;
		
		//extra checks for same bib id and different holding ids
		String item_id1 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_1");
		String item_id2 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		String item_id3 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_3");
		String hold_id1 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_1");
		String hold_id2 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_2");
		String hold_id3 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_3");
		String bib_id1 = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		LookupItemInitiationData initData = new LookupItemInitiationData();
		initData.setBibliographicDescriptionDesired(true);
		initData.setCirculationStatusDesired(true);
		initData.setCurrentBorrowerDesired(true);
		initData.setCurrentRequestersDesired(true);
		initData.setElectronicResourceDesired(true);
		initData.setHoldQueueLengthDesired(true);
		ItemId itemId = new ItemId();
		itemId.setAgencyId(new AgencyId(holdingAgencyId));
		itemId.setItemIdentifierValue(adm_id);
		initData.setItemId(itemId);
		
		LookupItemResponseData responseData = service.performService(initData, null, serviceManager);
		
		assertEquals("Item ID Incorrect",adm_id,responseData.getItemId().getItemIdentifierValue());
		assertEquals("Author incorrect",author,responseData.getItemOptionalFields().getBibliographicDescription().getAuthor());
		assertEquals("Bib Id incorrect",bib_id,responseData.getItemOptionalFields().getBibliographicDescription().getBibliographicRecordIds().get(0).getBibliographicRecordIdentifier());
		assertEquals("Borrowing Users incorrect",true,responseData.getItemTransaction()==null||responseData.getItemTransaction().getCurrentBorrower()==null);
		assertEquals("Call number incorrect",callNumber,responseData.getItemOptionalFields().getItemDescription().getCallNumber());
		//assertEquals("Circ status incorrect",circStatus,responseData.getItemOptionalFields().getCirculationStatus().getValue());
		assertEquals("E Resource incorrect",eresource,responseData.getItemOptionalFields().getElectronicResource().getReferenceToResource());
		//assertEquals("Holding agency incorrect",holdingAgencyId,responseData.getRequestId().getAgencyId().getValue());
		//assertEquals("Holding ID incorrect",holdingId,responseData.getRequestId().getRequestIdentifierType().getValue());
		assertEquals("Hold Queue Length incorrect",holdQueueLength,responseData.getItemOptionalFields().getHoldQueueLength());
		//assertEquals("ISBN incorrect",isbn,responseData.getItemOptionalFields().getBibliographicDescription().getBibliographicItemId().getBibliographicItemIdentifier());
		assertEquals("Description incorrect",description,responseData.getItemOptionalFields().getItemDescription().getItemDescriptionLevel().getValue());
		assertEquals("Location incorrect", location,responseData.getItemOptionalFields().getLocations().get(0).getLocationName().getLocationNameInstances().get(0).getLocationNameValue());
		assertEquals("Medium incorrect",medium,responseData.getItemOptionalFields().getBibliographicDescription().getMediumType().getValue());
		assertEquals("Publisher incorrect",publisher,responseData.getItemOptionalFields().getBibliographicDescription().getPublisher());
		assertEquals("Requesting Users incorrect",true,responseData.getItemTransaction()==null||responseData.getItemTransaction().getCurrentRequesters().size()==0);
		assertEquals("Series incorrect",series,responseData.getItemOptionalFields().getBibliographicDescription().getSeriesTitleNumber());
		assertEquals("Title incorrect",title,responseData.getItemOptionalFields().getBibliographicDescription().getTitle());
	}
}
