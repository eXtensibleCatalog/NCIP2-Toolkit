package org.extensiblecatalog.ncip.v2.aleph.AlephXServices;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.agency.AlephAgencyFactory;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test.TestConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.user.AlephUser;

import java.util.ArrayList;
import java.util.List;


import junit.framework.TestCase;

public class AlephMediatorTest extends TestCase {
	
	private AlephMediator mediator;
	
	private AlephMediator getAlephMediator(){
		return mediator;
	}

	public AlephMediatorTest(String name) throws Exception{
		super(name);
		mediator = new AlephMediator();
		mediator.setXServerName(TestConfiguration.getProperty("XSERVER_NAME"));
		mediator.setXServerPort(TestConfiguration.getProperty("XSERVER_PORT"));
		
		AlephAgency agency = AlephAgencyFactory.createAlephAgency(TestConfiguration.getProperty("AGENCY_1"), TestConfiguration.getProperty("AGENCY_ADM_1"), TestConfiguration.getProperty("AGENCY_BIB_1"), TestConfiguration.getProperty("AGENCY_HOL_1"));
		mediator.addAlephAgency(agency);
		agency = AlephAgencyFactory.createAlephAgency(TestConfiguration.getProperty("AGENCY_3"), TestConfiguration.getProperty("AGENCY_ADM_3"), TestConfiguration.getProperty("AGENCY_BIB_3"), TestConfiguration.getProperty("AGENCY_HOL_3"));
		mediator.addAlephAgency(agency);
		agency = AlephAgencyFactory.createAlephAgency(TestConfiguration.getProperty("AGENCY_2"), TestConfiguration.getProperty("AGENCY_ADM_2"), TestConfiguration.getProperty("AGENCY_BIB_2"), TestConfiguration.getProperty("AGENCY_HOL_2"));
		mediator.addAlephAgency(agency);
		agency = AlephAgencyFactory.createAlephAgency(TestConfiguration.getProperty("AGENCY_4"), TestConfiguration.getProperty("AGENCY_ADM_4"), TestConfiguration.getProperty("AGENCY_BIB_4"), TestConfiguration.getProperty("AGENCY_HOL_4"));
		mediator.addAlephAgency(agency);
	}
	
	public void testSetXServerName() throws Exception{
		AlephMediator aleph = new AlephMediator();
		aleph.setXServerName(TestConfiguration.getProperty("XSERVER_NAME"));
		assertEquals("X Server Name not set correctly, Expected: "+TestConfiguration.getProperty("XSERVER_NAME")+" Actual: "+aleph.getXServerName(),
				TestConfiguration.getProperty("XSERVER_NAME"),aleph.getXServerName());
	}
	
	public void testSetXServerPort() throws Exception{
		AlephMediator aleph = new AlephMediator();
		aleph.setXServerPort(TestConfiguration.getProperty("XSERVER_PORT"));
		assertEquals("X Server Port not set correctly, Expected: "+TestConfiguration.getProperty("XSERVER_PORT")+" Actual: "+aleph.getXServerPort(),
				TestConfiguration.getProperty("XSERVER_PORT"),aleph.getXServerPort());
	}

	public void testAuthenticateUser() throws Exception{
		
		String patron_id = TestConfiguration.getProperty("USERNAME");
		String password = TestConfiguration.getProperty("PASSWORD");
		String sub_library = TestConfiguration.getProperty("AGENCY_1");
		String expectedUserId = TestConfiguration.getProperty("PATRON_ID");
		
		AlephUser user = getAlephMediator().authenticateUser(sub_library, patron_id, password);
		
		assertEquals("Aleph User name returned is incorrect, Expected: "+expectedUserId+" Actual: "+user.getUsername(),expectedUserId,user.getUsername());
		assertTrue("User session id not Returned",user.getSessionId()!=null&&user.getSessionId().length()>0);
		
		//negative testing, check for exceptions
		boolean threwException = false;
		try {
			getAlephMediator().authenticateUser(sub_library, null, null);
		} catch (AlephException ae){
			threwException = true;
			if (!ae.getMessage().equals(AlephConstants.ERROR_BOR_ID_VER)){
				fail("Unknown exception: "+ae);
				throw ae;
			}
		}
		if (!threwException){
			fail("Did not throw exception with user id and password missing");
		}
		
		//check for bad password
		threwException = false;
		try {
			getAlephMediator().authenticateUser(sub_library, patron_id, "bogus");
		} catch (AlephException ae){
			threwException = true;
			if (!ae.getMessage().equals(AlephConstants.ERROR_AUTH_VERIFICATION)){
				fail("Unknown exception: "+ae);
				throw ae;
			}
		}
		if (!threwException){
			fail("Did not throw exception with bad password");
		}
		
	}
	
	public void testLookupUser() throws Exception {
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		String patron_id = TestConfiguration.getProperty("USERNAME");
		String expectedUserId = TestConfiguration.getProperty("PATRON_ID");
		
		AlephUser user = getAlephMediator().lookupUser(agencyId, patron_id, null, true, true, true);
		
		assertEquals("Aleph User name returned is incorrect, Expected: "+expectedUserId+" Actual: "+user.getUsername(),expectedUserId,user.getUsername());
		assertTrue("User session id not Returned",user.getSessionId()!=null&&user.getSessionId().length()>0);
		
		//try with user id as well
		user = getAlephMediator().lookupUser(agencyId, expectedUserId, null, true, true, true);
		
		assertEquals("Aleph User name returned is incorrect, Expected: "+expectedUserId+" Actual: "+user.getUsername(),expectedUserId,user.getUsername());
		assertTrue("User session id not Returned",user.getSessionId()!=null&&user.getSessionId().length()>0);
	}
	
	public void testLookupItemByAdmId() throws Exception {
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_4");
		String bib_id4 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String item_id4 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String author4 = TestConfiguration.getProperty("LOAN_AUTHOR_4");
		//String isbn4 = Configuration.getProperty("LOAN_ISBN_4");
		String title4 = TestConfiguration.getProperty("LOAN_TITLE_4");
		String callNumber4 = TestConfiguration.getProperty("LOAN_CALLNUMBER_4");
		String publisher4 = TestConfiguration.getProperty("LOAN_PUBLISHER_4");
		String description4 = TestConfiguration.getProperty("LOAN_DESCRIPTION_4");
		String series4 = TestConfiguration.getProperty("LOAN_SERIES_4");
		String location4 = TestConfiguration.getProperty("LOAN_LOCATION_4");
		String eresource4 = TestConfiguration.getProperty("LOAN_ERESOURCE_4");
		String medium4 = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_4");
		String circStatus4 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_4");
		
		AlephItem item = getAlephMediator().lookupItemByItemId(item_id4, agencyId, true, false, false, false, true);
		assertEquals("Aleph item author returned is incorrect, Expected: "+author4+" Actual: "+item.getAuthor(),author4,item.getAuthor());
		//assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn4+" Actual: "+item.getIsbn(),isbn4,item.getIsbn());
		assertEquals("Aleph item title returned is incorrect, Expected: "+title4+" Actual: "+item.getTitle(),title4,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber4+" Actual: "+item.getCallNumber(),callNumber4,item.getCallNumber());
		assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher4+" Actual: "+item.getPublisher(),publisher4,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description4+" Actual: "+item.getDescription(),description4,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series4+" Actual: "+item.getSeries(),series4,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location4+" Actual: "+item.getLocation(),location4,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource4+" Actual: "+item.getElectronicResource(),eresource4,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium4+" Actual: "+item.getMediumType(),medium4,item.getMediumType());
		//assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus4+" Actual :"+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id4+" Actual :"+item.getItemId(),item_id4,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Expected: "+bib_id4+" Actual :"+item.getBibId(),bib_id4,item.getBibId());
	}
	
	public void testLookupItemByBibId() throws Exception {
		
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_4");
		String bib_id4 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String item_id4 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String author4 = TestConfiguration.getProperty("LOAN_AUTHOR_4");
		//String isbn4 = Configuration.getProperty("LOAN_ISBN_4");
		String title4 = TestConfiguration.getProperty("LOAN_TITLE_4");
		String callNumber4 = TestConfiguration.getProperty("LOAN_CALLNUMBER_4");
		String publisher4 = TestConfiguration.getProperty("LOAN_PUBLISHER_4");
		String description4 = TestConfiguration.getProperty("LOAN_DESCRIPTION_4");
		String series4 = TestConfiguration.getProperty("LOAN_SERIES_4");
		String location4 = TestConfiguration.getProperty("LOAN_LOCATION_4");
		String eresource4 = TestConfiguration.getProperty("LOAN_ERESOURCE_4");
		String medium4 = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_4");
		String circStatus4 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_4");
		
		AlephItem item = getAlephMediator().lookupItemByBibId(bib_id4, agencyId, true, false, false, false);
		assertEquals("Aleph item author returned is incorrect, Expected: "+author4+" Actual: "+item.getAuthor(),author4,item.getAuthor());
		//assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn4+" Actual: "+item.getIsbn(),isbn4,item.getIsbn());
		assertEquals("Aleph item title returned is incorrect, Expected: "+title4+" Actual: "+item.getTitle(),title4,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber4+" Actual: "+item.getCallNumber(),callNumber4,item.getCallNumber());
		assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher4+" Actual: "+item.getPublisher(),publisher4,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description4+" Actual: "+item.getDescription(),description4,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series4+" Actual: "+item.getSeries(),series4,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location4+" Actual: "+item.getLocation(),location4,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource4+" Actual: "+item.getElectronicResource(),eresource4,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium4+" Actual: "+item.getMediumType(),medium4,item.getMediumType());
		//assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus4+" Actual :"+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id4+" Actual :"+item.getItemId(),item_id4,item.getItemId());
		
		List<AlephItem> items = getAlephMediator().lookupItemsByBibId(bib_id4, agencyId, true, true, true, true);
		
	}
	
	public void testLookupHoldingsItemsByBibId() throws Exception {
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_4");
		String item_id1 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_1");
		String item_id2 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		String item_id3 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_3");
		String hold_id1 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_1");
		String hold_id2 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_2");
		String hold_id3 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_3");
		String bib_id = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		List<AlephItem> items = getAlephMediator().lookupHoldingsItemsByBibId(bib_id, agencyId, true, false, false, false);
		for (AlephItem item : items){
			System.out.println("Found Item: "+item.toString());
		}
	}
	
	public void testLookupItemByHoldId() throws Exception {
		
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_4");
		String bib_id4 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String item_id4 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String author4 = TestConfiguration.getProperty("LOAN_AUTHOR_4");
		//String isbn4 = Configuration.getProperty("LOAN_ISBN_4");
		String title4 = TestConfiguration.getProperty("LOAN_TITLE_4");
		String callNumber4 = TestConfiguration.getProperty("LOAN_CALLNUMBER_4");
		String publisher4 = TestConfiguration.getProperty("LOAN_PUBLISHER_4");
		String description4 = TestConfiguration.getProperty("LOAN_DESCRIPTION_4");
		String series4 = TestConfiguration.getProperty("LOAN_SERIES_4");
		String location4 = TestConfiguration.getProperty("LOAN_LOCATION_4");
		String eresource4 = TestConfiguration.getProperty("LOAN_ERESOURCE_4");
		String medium4 = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_4");
		String circStatus4 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_4");
		String hold_id4 = TestConfiguration.getProperty("LOAN_HOLD_ID4");
		System.out.println("HOLD_ID4: "+hold_id4);
		AlephItem item = getAlephMediator().lookupItemByHoldingsIdItemId(hold_id4, null, agencyId, true, false, false, false);
		
		assertEquals("Aleph item author returned is incorrect, Expected: "+author4+" Actual: "+item.getAuthor(),author4,item.getAuthor());
		//assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn4+" Actual: "+item.getIsbn(),isbn4,item.getIsbn());
		assertEquals("Aleph item title returned is incorrect, Expected: "+title4+" Actual: "+item.getTitle(),title4,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber4+" Actual: "+item.getCallNumber(),callNumber4,item.getCallNumber());
		assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher4+" Actual: "+item.getPublisher(),publisher4,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description4+" Actual: "+item.getDescription(),description4,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series4+" Actual: "+item.getSeries(),series4,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location4+" Actual: "+item.getLocation(),location4,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource4+" Actual: "+item.getElectronicResource(),eresource4,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium4+" Actual: "+item.getMediumType().toUpperCase(),medium4.toUpperCase(),item.getMediumType().toUpperCase());
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus4+" Actual :"+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id4+" Actual :"+item.getItemId(),item_id4,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Expected: "+bib_id4+" Actual :"+item.getBibId(),bib_id4,item.getBibId());
		assertEquals("Aleph item holdings id returned is incorrect, Expected: "+hold_id4+" Actual :"+item.getHoldingsId(),hold_id4,item.getHoldingsId());
		
		String item_id1 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_1");
		String item_id2 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		String item_id3 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_3");
		String hold_id1 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_1");
		String hold_id2 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_2");
		String hold_id3 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_3");
		String bib_id = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		List<AlephItem> items = getAlephMediator().lookupItemsByHoldingsId(hold_id1, agencyId, true, false, false, false);
		assertEquals("Wrong number of items returned for Holdings Id: "+hold_id1,1,items.size());
		AlephItem iterItem = items.get(0);
		assertEquals("Aleph Item Hold Id does not match, expected: "+hold_id1+" actual: "+iterItem.getHoldingsId(),hold_id1,iterItem.getHoldingsId());
		assertEquals("Aleph Item Bib Id does not match, expected: "+bib_id+" actual: "+iterItem.getBibId(),bib_id,iterItem.getBibId());
		assertEquals("Aleph Item Item Id does not match, expected: "+item_id1+" actual: "+iterItem.getItemId(),item_id1,iterItem.getItemId());
		
		items = getAlephMediator().lookupItemsByHoldingsId(hold_id2, agencyId, true, false, false, false);
		assertEquals("Wrong number of items returned for Holdings Id: "+hold_id2,1,items.size());
		iterItem = items.get(0);
		assertEquals("Aleph Item Hold Id does not match, expected: "+hold_id2+" actual: "+iterItem.getHoldingsId(),hold_id2,iterItem.getHoldingsId());
		assertEquals("Aleph Item Bib Id does not match, expected: "+bib_id+" actual: "+iterItem.getBibId(),bib_id,iterItem.getBibId());
		assertEquals("Aleph Item Item Id does not match, expected: "+item_id2+" actual: "+iterItem.getItemId(),item_id2,iterItem.getItemId());
		items = getAlephMediator().lookupItemsByHoldingsId(hold_id3, agencyId, true, false, false, false);
		assertEquals("Wrong number of items returned for Holdings Id: "+hold_id3,1,items.size());
		iterItem = items.get(0);
		assertEquals("Aleph Item Hold Id does not match, expected: "+hold_id3+" actual: "+iterItem.getHoldingsId(),hold_id3,iterItem.getHoldingsId());
		assertEquals("Aleph Item Bib Id does not match, expected: "+bib_id+" actual: "+iterItem.getBibId(),bib_id,iterItem.getBibId());
		assertEquals("Aleph Item Item Id does not match, expected: "+item_id3+" actual: "+iterItem.getItemId(),item_id3,iterItem.getItemId());
	}
	
	public void testRequestItemByItemId() throws Exception {
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		String itemId_checkedout = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId_available = TestConfiguration.getProperty("AVAIL_ADM_ID2");
		
		//test bad user id
		boolean caughtException = false;
		try {
			getAlephMediator().requestItemByItemId("testUser", agencyId, itemId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for bad user");
		}
		
		//test item is null
		caughtException = false;
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, null);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test throw exception bad item data
		caughtException = false;
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, "b64");
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test request checked out item throws error
		caughtException = false;
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, itemId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for checked out item");
		}
		
		//test ok
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, itemId_available);
		} catch (Exception e){
			throw e;
		}
		
		//test item already exists
		caughtException = false;
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, itemId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test canceling item
		
		//test bad params
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByItemId(agencyId, bor_id, null);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test cancel bad item
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByItemId(agencyId, bor_id, "b64");
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test cancel
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByItemId(agencyId, bor_id, itemId_available);
		} catch (Exception e){
			throw e;
		}
		
		//test cancel, already cancelled
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByItemId(agencyId, bor_id, itemId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}	
	}
	
	public void testRequestItemByBibId() throws Exception{
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		String bibId_checkedout = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId_available = TestConfiguration.getProperty("AVAIL_BIB_ID1");
		
		String bibId2 = TestConfiguration.getProperty("RESTRICTED_BIB_ID");
		
		String item_id_same_bib_avail_1 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		String item_id_same_bib_avail_2 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_3");
		String bib_id_avail_2 = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		//test checked out item throws error
		boolean caughtException = false;
		try {
			getAlephMediator().requestItemByBibId(bor_id, agencyId, bibId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for checked out item");
		}
		
		//other negative testing done in get by item id so not as much necessary here
		
		//test ok
		try {
			getAlephMediator().requestItemByBibId(bor_id, agencyId,bibId_available);
		} catch (Exception e){
			fail("Exception caught when should be requested: "+e);
		}
		
		//test item already exists
		caughtException = false;
		try {
			getAlephMediator().requestItemByBibId(bor_id, agencyId,bibId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test canceling item
		
		//test cancel
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByBibId(agencyId, bor_id, bibId_available);
		} catch (Exception e){
			throw e;
		}
		
		//test cancel, already canceled
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByBibId(agencyId, bor_id, bibId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test special collections item
		try {
			getAlephMediator().requestItemByBibId(bor_id, agencyId,bibId2);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught when requesting special collections item");
		}
		
		//request items by item id to make sure cancel works for same hold id but multiple items
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, item_id_same_bib_avail_1);
		} catch (Exception e){
			fail("Exception caught when should be requested: "+e);
		}
		
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, item_id_same_bib_avail_2);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for item already requested with same bib id");
		}
		
		//now test cancel using hold id that is same for both items
		//test cancel
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByBibId(agencyId, bor_id, bib_id_avail_2);
		} catch (Exception e){
			throw e;
		}
	}
	
	public void testRequestItemByHoldId() throws Exception{
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		
		String holdId_checkedout = TestConfiguration.getProperty("LOAN_HOLD_ID_1");
		String holdId_available = TestConfiguration.getProperty("AVAIL_HOLD_ID1");
		
		String item_id_same_bib_avail_1 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		String item_id_same_bib_avail_2 = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_3");
		String hold_id_avail_2 = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_2");
		
		//test checked out item throws error
		boolean caughtException = false;
		try {
			getAlephMediator().requestItemByHoldingsId(bor_id, agencyId, holdId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for checked out item");
		}
		
		//other negative testing done in get by item id so not as much necessary here
		
		//test ok
		try {
			getAlephMediator().requestItemByHoldingsId(bor_id, agencyId, holdId_available);
		} catch (Exception e){
			fail("Exception caught when should be requested: "+e);
		}
		
		//test item already exists
		caughtException = false;
		try {
			getAlephMediator().requestItemByHoldingsId(bor_id, agencyId, holdId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test canceling item
		
		//test cancel
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByHoldingsId(agencyId, bor_id, holdId_available);
		} catch (Exception e){
			throw e;
		}
		
		//test cancel, already canceled
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByHoldingsId(agencyId, bor_id, holdId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//request items by item id to make sure cancel works for same hold id but multiple items
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, item_id_same_bib_avail_1);
		} catch (Exception e){
			fail("Exception caught when should be requested: "+e);
		}
		
		try {
			getAlephMediator().requestItemByItemId(bor_id, agencyId, item_id_same_bib_avail_2);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for item already requested with same bib id");
		}
		
		//now test cancel using hold id that is same for both items
		//test cancel
		caughtException = false;
		try {
			getAlephMediator().cancelRequestItemByHoldingsId(agencyId, bor_id, hold_id_avail_2);
		} catch (Exception e){
			throw e;
		}
	}
	
	public void testRenewItemByItemId() throws Exception {
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_1");
		String itemId_checkedout = TestConfiguration.getProperty("LOAN_ADM_ID1");
		String itemId_available = TestConfiguration.getProperty("AVAIL_ADM_ID1");
		
		//test bad user id
		boolean caughtException = false;
		try {
			getAlephMediator().renewItemByItemId("testUser", agencyId, itemId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for bad user");
		}
		
		//test item is null
		caughtException = false;
		try {
			getAlephMediator().renewItemByItemId(bor_id, agencyId, null);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test throw exception bad item data
		caughtException = false;
		try {
			getAlephMediator().renewItemByItemId(bor_id, agencyId, "b64");
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for null item");
		}
		
		//test renew unloaned item throws error
		caughtException = false;
		try {
			getAlephMediator().renewItemByItemId(bor_id, agencyId, itemId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for renewing item available");
		}
		
		//test ok
		try {
			getAlephMediator().renewItemByItemId(bor_id, agencyId, itemId_checkedout);
		} catch (Exception e){
			throw e;
		}
		
		//test item already renewed
		caughtException = false;
		try {
			getAlephMediator().renewItemByItemId(bor_id, agencyId, itemId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for renewing item already extended");
		}
		
	}
	
	public void testRenewItemByBibId() throws Exception{
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_2");
		String bibId_checkedout = TestConfiguration.getProperty("LOAN_BIB_ID2");
		String bibId_available = TestConfiguration.getProperty("AVAIL_BIB_ID1");
		
		//test available item throws error
		boolean caughtException = false;
		try {
			getAlephMediator().renewItemByBibId(bor_id, agencyId, bibId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for available item");
		}
		
		//other negative testing done in get by item id so not as much necessary here
		
		//test ok
		try {
			getAlephMediator().renewItemByBibId(bor_id, agencyId, bibId_checkedout);
		} catch (Exception e){
			fail("Exception caught when should be renewed: "+e);
		}
		
		//test item already renewed
		caughtException = false;
		try {
			getAlephMediator().renewItemByBibId(bor_id, agencyId, bibId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for item already renewed");
		}
		
	}
	
	public void testRenewItemByHoldingsId() throws Exception{
		
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		String agencyId = TestConfiguration.getProperty("LOAN_AGENCY_3");
		
		String holdingsId_checkedout = TestConfiguration.getProperty("LOAN_HOLD_ID_3");
		String holdingsId_available = TestConfiguration.getProperty("AVAIL_HOLD_ID1");
		
		
		//test available item throws error
		boolean caughtException = false;
		try {
			getAlephMediator().renewItemByHoldingsId(bor_id, agencyId, holdingsId_available);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not thrown for available item");
		}
		
		//other negative testing done in get by item id so not as much necessary here
		
		//test ok
		try {
			getAlephMediator().renewItemByHoldingsId(bor_id, agencyId, holdingsId_checkedout);
		} catch (Exception e){
			fail("Exception caught when should be renewed: "+e);
		}
		
		//test item already renewed
		caughtException = false;
		try {
			getAlephMediator().renewItemByHoldingsId(bor_id, agencyId, holdingsId_checkedout);
		} catch (Exception e){
			System.out.println("Caught exception (Pass): "+e);
			caughtException = true;
		}
		
		if (!caughtException){
			fail("Exception not caught for item already renewed");
		}
		
	}
	
	
	public void testGetAvailabilityHoldingsId() throws Exception {
		
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		List<String> itemIds = new ArrayList<String>();
		String itemId1 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId2 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		//String itemId3 = TestConfiguration.getProperty("RESTRICTED_ADM_ID");
		String itemId4 = TestConfiguration.getProperty("AVAIL_ADM_ID3");
		String itemId5 = TestConfiguration.getProperty("AVAIL_ADM_ID1");
		//bogus number
		//String itemId6 = null;
		//from bibid with multiple items
		String itemId7a = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_1");
		String itemId7b = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		
		itemIds.add(itemId1);
		itemIds.add(itemId2);
		//itemIds.add(itemId3);
		itemIds.add(itemId4);
		itemIds.add(itemId5);
		itemIds.add(itemId7a);
		itemIds.add(itemId7b);
		
		List<String> bibIds = new ArrayList<String>();
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId2 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		//String bibId3 = TestConfiguration.getProperty("RESTRICTED_BIB_ID");
		String bibId4 = TestConfiguration.getProperty("AVAIL_BIB_ID3"); 
		String bibId5 = TestConfiguration.getProperty("AVAIL_BIB_ID1");
		String bibId6 = null;
		//need bib id that returns more than one item as well
		String bibId7 = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		bibIds.add(bibId1);
		bibIds.add(bibId2);
		//bibIds.add(bibId3);
		bibIds.add(bibId4);
		bibIds.add(bibId5);
		bibIds.add(bibId7);
		
		List<String> holdingsIds = new ArrayList<String>();
		String holdingsId1 = TestConfiguration.getProperty("LOAN_HOLD_ID4");
		String holdingsId2 = TestConfiguration.getProperty("LOAN_HOLD_ID4");
		String holdingsId3 = TestConfiguration.getProperty("RESTRICTED_HOLD_ID");
		String holdingsId4 = TestConfiguration.getProperty("AVAIL_HOLD_ID3");
		String holdingsId5 = TestConfiguration.getProperty("AVAIL_HOLD_ID1");
		String holdingsId6 = "b64";
		String holdingsId7a = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_1");
		String holdingsId7b = TestConfiguration.getProperty("AVAILABILITY_HOLD_ID_2");
		
		holdingsIds.add(holdingsId1);
		holdingsIds.add(holdingsId2);
		holdingsIds.add(holdingsId3);
		holdingsIds.add(holdingsId4);
		holdingsIds.add(holdingsId5);
		holdingsIds.add(holdingsId6);
		holdingsIds.add(holdingsId7a);
		holdingsIds.add(holdingsId7b);
		
		AlephConstants.Availability item1Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item2Avail = AlephConstants.Availability.NOT_AVAILABLE;
		//AlephConstants.Availability item3Avail = AlephConstants.Availability.POSSIBLY_AVAILABLE;
		AlephConstants.Availability item4Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item5Avail = AlephConstants.Availability.AVAILABLE;
		AlephConstants.Availability item6Avail = AlephConstants.Availability.DOESNT_EXIST;
		String item7aAvail = TestConfiguration.getProperty("AVAILABILITY_EXPECTED_1");
		String item7bAvail = TestConfiguration.getProperty("AVAILABILITY_EXPECTED_2");
		
		List<AlephItem> items = getAlephMediator().getAvailabilityByHoldingsId(agencyId, holdingsIds);
		
		//check return values
		for (AlephItem item : items){
			System.out.println(item);
			if (item.getHoldingsId().equals(holdingsId1)){
				assertEquals("Bib id incorrect for item: "+holdingsId1+" expected: "+bibId1+" actual: "+item.getBibId(),bibId1,item.getBibId());
				assertEquals("Available incorrect for item: "+holdingsId1+" expected: "+item1Avail+" actual: "+item.getAvailability(),item1Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId2)){
				assertEquals("Bib id incorrect for item: "+holdingsId2+" expected: "+bibId2+" actual: "+item.getBibId(),bibId2,item.getBibId());
				assertEquals("Available incorrect for item: "+holdingsId2+" expected: "+item2Avail+" actual: "+item.getAvailability(),item2Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			//} else if (item.getHoldingsId().equals(holdingsId3)){
			//	assertEquals("Bib id incorrect for item: "+holdingsId3+" expected: "+bibId3+" actual: "+item.getBibId(),bibId3,item.getBibId());
			//	assertEquals("Available incorrect for item: "+holdingsId3+" expected: "+item3Avail+" actual: "+item.getAvailability(),item3Avail,item.getAvailability());
			//	itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId4)){
				assertEquals("Bib id incorrect for item: "+holdingsId4+" expected: "+bibId4+" actual: "+item.getBibId(),bibId4,item.getBibId());
				assertEquals("Available incorrect for item: "+holdingsId4+" expected: "+item4Avail+" actual: "+item.getAvailability(),item4Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId5)){
				assertEquals("Bib id incorrect for item: "+holdingsId5+" expected: "+bibId5+" actual: "+item.getBibId(),bibId5,item.getBibId());
				assertEquals("Available incorrect for item: "+holdingsId5+" expected: "+item5Avail+" actual: "+item.getAvailability(),item5Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId6)){
				assertEquals("Bib id incorrect for item: "+holdingsId6+" expected: "+bibId6+" actual: "+item.getBibId(),bibId6,item.getBibId());
				assertEquals("Available incorrect for item: "+holdingsId6+" expected: "+item6Avail+" actual: "+item.getAvailability(),item6Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId7a)&&item.getItemId().equals(itemId7a)){
				assertEquals("Bib id incorrect for item: "+itemId7a+" expected: "+bibId7+" actual: "+item.getBibId(),bibId7,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId7a+" expected: "+item7aAvail+" actual: "+item.getAvailability().toString(),item7aAvail,item.getAvailability().toString());
				itemIds.remove(item.getItemId());
			} else if (item.getHoldingsId().equals(holdingsId7b)&&item.getItemId().equals(itemId7b)){
				assertEquals("Bib id incorrect for item: "+itemId7b+" expected: "+bibId7+" actual: "+item.getBibId(),bibId7,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId7b+" expected: "+item7bAvail+" actual: "+item.getAvailability().toString(),item7bAvail,item.getAvailability().toString());
				itemIds.remove(item.getItemId());
			}
		}
		if (itemIds.size()>0){
			fail("Some item ids missed: "+itemIds);
		}
	}

	public void testGetAvailabilityItemId() throws Exception {
		
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		List<String> itemIds = new ArrayList<String>();
		String itemId1 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId2 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId3 = TestConfiguration.getProperty("RESTRICTED_ADM_ID");
		String itemId4 = TestConfiguration.getProperty("AVAIL_ADM_ID3");
		String itemId5 = TestConfiguration.getProperty("AVAIL_ADM_ID1");
		//bogus number
		String itemId6 = "b64";
		
		itemIds.add(itemId1);
		itemIds.add(itemId2);
		itemIds.add(itemId3);
		itemIds.add(itemId4);
		itemIds.add(itemId5);
		itemIds.add(itemId6);
		
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId2 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId3 = TestConfiguration.getProperty("RESTRICTED_BIB_ID");
		String bibId4 = TestConfiguration.getProperty("AVAIL_BIB_ID3"); 
		String bibId5 = TestConfiguration.getProperty("AVAIL_BIB_ID1");
		String bibId6 = null;
		
		AlephConstants.Availability item1Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item2Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item3Avail = AlephConstants.Availability.POSSIBLY_AVAILABLE;
		AlephConstants.Availability item4Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item5Avail = AlephConstants.Availability.AVAILABLE;
		AlephConstants.Availability item6Avail = AlephConstants.Availability.DOESNT_EXIST;
		
		List<AlephItem> items = getAlephMediator().getAvailabilityByItemId(agencyId, itemIds);
		
		//check return values
		for (AlephItem item : items){
			System.out.println(item);
			if (item.getItemId().equals(itemId1)){
				assertEquals("Bib id incorrect for item: "+itemId1+" expected: "+bibId1+" actual: "+item.getBibId(),bibId1,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId1+" expected: "+item1Avail+" actual: "+item.getAvailability(),item1Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getItemId().equals(itemId2)){
				assertEquals("Bib id incorrect for item: "+itemId2+" expected: "+bibId2+" actual: "+item.getBibId(),bibId2,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId2+" expected: "+item2Avail+" actual: "+item.getAvailability(),item2Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getItemId().equals(itemId3)){
				assertEquals("Bib id incorrect for item: "+itemId3+" expected: "+bibId3+" actual: "+item.getBibId(),bibId3,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId3+" expected: "+item3Avail+" actual: "+item.getAvailability(),item3Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getItemId().equals(itemId4)){
				assertEquals("Bib id incorrect for item: "+itemId4+" expected: "+bibId4+" actual: "+item.getBibId(),bibId4,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId4+" expected: "+item4Avail+" actual: "+item.getAvailability(),item4Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getItemId().equals(itemId5)){
				assertEquals("Bib id incorrect for item: "+itemId5+" expected: "+bibId5+" actual: "+item.getBibId(),bibId5,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId5+" expected: "+item5Avail+" actual: "+item.getAvailability(),item5Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getItemId().equals(itemId6)){
				assertEquals("Bib id incorrect for item: "+itemId6+" expected: "+bibId6+" actual: "+item.getBibId(),bibId6,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId6+" expected: "+item6Avail+" actual: "+item.getAvailability(),item6Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			}
		}
		if (itemIds.size()>0){
			fail("Some item ids missed: "+itemIds);
		}
	}
	
	public void testGetAvailabilityBibId() throws Exception {
		
		String agencyId = TestConfiguration.getProperty("AGENCY_1");
		List<String> bibIds = new ArrayList<String>();
		List<String> itemIds = new ArrayList<String>();
		String itemId1 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId2 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String itemId3 = TestConfiguration.getProperty("RESTRICTED_ADM_ID");
		String itemId4 = TestConfiguration.getProperty("AVAIL_ADM_ID3");
		String itemId5 = TestConfiguration.getProperty("AVAIL_ADM_ID1");
		//bogus number
		String itemId6 = null;
		//from bibid with multiple items
		String itemId7a = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_1");
		String itemId7b = TestConfiguration.getProperty("AVAILABILITY_ITEM_ID_SAME_BIB_ID_2");
		
		itemIds.add(itemId1);
		itemIds.add(itemId2);
		itemIds.add(itemId3);
		itemIds.add(itemId4);
		itemIds.add(itemId5);
		itemIds.add(itemId7a);
		itemIds.add(itemId7b);
		
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId2 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId3 = TestConfiguration.getProperty("RESTRICTED_BIB_ID");
		String bibId4 = TestConfiguration.getProperty("AVAIL_BIB_ID3"); 
		String bibId5 = TestConfiguration.getProperty("AVAIL_BIB_ID1");
		String bibId6 = "b64";
		//need bib id that returns more than one item as well
		String bibId7 = TestConfiguration.getProperty("AVAILABILITY_BIB_ID");
		
		bibIds.add(bibId1);
		bibIds.add(bibId2);
		bibIds.add(bibId3);
		bibIds.add(bibId4);
		bibIds.add(bibId5);
		bibIds.add(bibId6);
		bibIds.add(bibId7);
		
		AlephConstants.Availability item1Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item2Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item3Avail = AlephConstants.Availability.POSSIBLY_AVAILABLE;
		AlephConstants.Availability item4Avail = AlephConstants.Availability.NOT_AVAILABLE;
		AlephConstants.Availability item5Avail = AlephConstants.Availability.AVAILABLE;
		AlephConstants.Availability item6Avail = AlephConstants.Availability.DOESNT_EXIST;
		String item7aAvail = TestConfiguration.getProperty("AVAILABILITY_EXPECTED_1");
		String item7bAvail = TestConfiguration.getProperty("AVAILABILITY_EXPECTED_2");
		
		List<AlephItem> items = getAlephMediator().getAvailabilityByBibId(agencyId, bibIds);
		
		//check return values
		for (AlephItem item : items){
			System.out.println(item);
			if (item.getBibId().equals(bibId1)){
				assertEquals("Bib id incorrect for item: "+itemId1+" expected: "+bibId1+" actual: "+item.getBibId(),bibId1,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId1+" expected: "+item1Avail+" actual: "+item.getAvailability(),item1Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId2)){
				assertEquals("Bib id incorrect for item: "+itemId2+" expected: "+bibId2+" actual: "+item.getBibId(),bibId2,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId2+" expected: "+item2Avail+" actual: "+item.getAvailability(),item2Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId3)){
				assertEquals("Bib id incorrect for item: "+itemId3+" expected: "+bibId3+" actual: "+item.getBibId(),bibId3,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId3+" expected: "+item3Avail+" actual: "+item.getAvailability(),item3Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId4)){
				assertEquals("Bib id incorrect for item: "+itemId4+" expected: "+bibId4+" actual: "+item.getBibId(),bibId4,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId4+" expected: "+item4Avail+" actual: "+item.getAvailability(),item4Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId5)){
				assertEquals("Bib id incorrect for item: "+itemId5+" expected: "+bibId5+" actual: "+item.getBibId(),bibId5,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId5+" expected: "+item5Avail+" actual: "+item.getAvailability(),item5Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId6)){
				assertEquals("Bib id incorrect for item: "+itemId6+" expected: "+bibId6+" actual: "+item.getBibId(),bibId6,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId6+" expected: "+item6Avail+" actual: "+item.getAvailability(),item6Avail,item.getAvailability());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId7)&&item.getItemId().equals(itemId7a)){
				assertEquals("Bib id incorrect for item: "+itemId7a+" expected: "+bibId7+" actual: "+item.getBibId(),bibId7,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId7a+" expected: "+item7aAvail+" actual: "+item.getAvailability().toString(),item7aAvail,item.getAvailability().toString());
				itemIds.remove(item.getItemId());
			} else if (item.getBibId().equals(bibId7)&&item.getItemId().equals(itemId7b)){
				assertEquals("Bib id incorrect for item: "+itemId7b+" expected: "+bibId7+" actual: "+item.getBibId(),bibId7,item.getBibId());
				assertEquals("Available incorrect for item: "+itemId7b+" expected: "+item7bAvail+" actual: "+item.getAvailability().toString(),item7bAvail,item.getAvailability().toString());
				itemIds.remove(item.getItemId());
			}
		}
		if (itemIds.size()>0){
			fail("Some item ids missed: "+itemIds);
		}
	}
	
	public void testInitializeAgencyMap() throws Exception{
		AlephMediator aleph = getAlephMediator();
		
		AlephAgency agency = aleph.getAlephAgency(TestConfiguration.getProperty("AGENCY_1"));
		assertEquals("Agency Mismatch",TestConfiguration.getProperty("AGENCY_1"),agency.getAgencyId());
		assertEquals("Adm Mismatch",TestConfiguration.getProperty("AGENCY_ADM_1"),agency.getAdmLibrary());
		assertEquals("Bib Mismatch",TestConfiguration.getProperty("AGENCY_BIB_1"),agency.getBibLibrary());
		assertEquals("Hol Mismatch",TestConfiguration.getProperty("AGENCY_HOL_1"),agency.getHoldingsLibrary());
		
		agency = aleph.getAlephAgency(TestConfiguration.getProperty("AGENCY_2"));
		assertEquals("Agency Mismatch",TestConfiguration.getProperty("AGENCY_2"),agency.getAgencyId());
		assertEquals("Adm Mismatch",TestConfiguration.getProperty("AGENCY_ADM_2"),agency.getAdmLibrary());
		assertEquals("Bib Mismatch",TestConfiguration.getProperty("AGENCY_BIB_2"),agency.getBibLibrary());
		assertEquals("Hol Mismatch",TestConfiguration.getProperty("AGENCY_HOL_2"),agency.getHoldingsLibrary());
		
		agency = aleph.getAlephAgency(TestConfiguration.getProperty("AGENCY_3"));
		assertEquals("Agency Mismatch",TestConfiguration.getProperty("AGENCY_3"),agency.getAgencyId());
		assertEquals("Adm Mismatch",TestConfiguration.getProperty("AGENCY_ADM_3"),agency.getAdmLibrary());
		assertEquals("Bib Mismatch",TestConfiguration.getProperty("AGENCY_BIB_3"),agency.getBibLibrary());
		assertEquals("Hol Mismatch",TestConfiguration.getProperty("AGENCY_HOL_3"),agency.getHoldingsLibrary());
		
		agency = aleph.getAlephAgency(TestConfiguration.getProperty("AGENCY_4"));
		assertEquals("Agency Mismatch",TestConfiguration.getProperty("AGENCY_4"),agency.getAgencyId());
		assertEquals("Adm Mismatch",TestConfiguration.getProperty("AGENCY_ADM_4"),agency.getAdmLibrary());
		assertEquals("Bib Mismatch",TestConfiguration.getProperty("AGENCY_BIB_4"),agency.getBibLibrary());
		assertEquals("Hol Mismatch",TestConfiguration.getProperty("AGENCY_HOL_4"),agency.getHoldingsLibrary());
	}
}
