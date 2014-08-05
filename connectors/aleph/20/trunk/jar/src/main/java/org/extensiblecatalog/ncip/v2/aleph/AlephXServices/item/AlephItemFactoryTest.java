package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.item;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.XMLParserUtil;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test.TestConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice.XService;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice.XServiceFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import junit.framework.TestCase;

public class AlephItemFactoryTest extends TestCase {
	
	public void testUpdateAlephItem() throws Exception {
		String bib_base = TestConfiguration.getProperty("BIB_LIBRARY");
		String adm_base = TestConfiguration.getProperty("ADM_LIBRARY");
		String hold_base = TestConfiguration.getProperty("HOL_LIBRARY");
		
		String bib_id1 = TestConfiguration.getProperty("LOAN_BIB_ID1");
		String item_id1 = TestConfiguration.getProperty("LOAN_ADM_ID1");
		String author1 = TestConfiguration.getProperty("LOAN_AUTHOR_1");
		String isbn1 = TestConfiguration.getProperty("LOAN_ISBN_1");
		String title1 = TestConfiguration.getProperty("LOAN_TITLE_1");
		String callNumber1 = TestConfiguration.getProperty("LOAN_CALLNUMBER_1");
		String publisher1 = TestConfiguration.getProperty("LOAN_PUBLISHER_1");
		String description1 = TestConfiguration.getProperty("LOAN_DESCRIPTION_1");
		String series1 = TestConfiguration.getProperty("LOAN_SERIES_1");
		String location1 = TestConfiguration.getProperty("LOAN_LOCATION_1");
		String eresource1 = TestConfiguration.getProperty("LOAN_ERESOURCE_1");
		String medium1 = TestConfiguration.getProperty("LOAN_MEDIUM_1");
		String circStatus1 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_1");
		String barcode1 = TestConfiguration.getProperty("LOAN_BARCODE_1");
		
		String bib_id2 = TestConfiguration.getProperty("LOAN_BIB_ID2");
		String item_id2 = TestConfiguration.getProperty("LOAN_ADM_ID2");
		String author2 = TestConfiguration.getProperty("LOAN_AUTHOR_2");
		String isbn2 = TestConfiguration.getProperty("LOAN_ISBN_2");
		//String title2 = Configuration.getProperty("LOAN_TITLE_2");
		String callNumber2 = TestConfiguration.getProperty("LOAN_CALLNUMBER_2");
		String publisher2 = TestConfiguration.getProperty("LOAN_PUBLISHER_2");
		String description2 = TestConfiguration.getProperty("LOAN_DESCRIPTION_2");
		String series2 = TestConfiguration.getProperty("LOAN_SERIES_2");
		String location2 = TestConfiguration.getProperty("LOAN_LOCATION_2");
		String eresource2 = TestConfiguration.getProperty("LOAN_ERESOURCE_2");
		String medium2 = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_2");
		String circStatus2 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_2");
		String barcode2 = TestConfiguration.getProperty("LOAN_BARCODE_2");
		
		String bib_id3 = TestConfiguration.getProperty("LOAN_BIB_ID3");
		String item_id3 = TestConfiguration.getProperty("LOAN_ADM_ID3");
		String author3 = TestConfiguration.getProperty("LOAN_AUTHOR_3");
		//String isbn3 = Configuration.getProperty("LOAN_ISBN_3");
		String title3 = TestConfiguration.getProperty("LOAN_TITLE_3");
		String callNumber3 = TestConfiguration.getProperty("LOAN_CALLNUMBER_3");
		//String publisher3 = Configuration.getProperty("LOAN_PUBLISHER_3");
		String description3 = TestConfiguration.getProperty("LOAN_DESCRIPTION_3");
		String series3 = TestConfiguration.getProperty("LOAN_SERIES_3");
		String location3 = TestConfiguration.getProperty("LOAN_LOCATION_3");
		String eresource3 = TestConfiguration.getProperty("LOAN_ERESOURCE_3");
		String medium3 = TestConfiguration.getProperty("LOAN_MEDIUMTYPE_3");
		String circStatus3 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_3");
		String barcode3 = TestConfiguration.getProperty("LOAN_BARCODE_3");
		
		String bib_id4 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String item_id4 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String holdId4 = TestConfiguration.getProperty("LOAN_HOLD_ID4");
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
		String barcode4 = TestConfiguration.getProperty("LOAN_BARCODE_4");
		
		AlephItem item;
		
		//do negative testing, test bad values
		XService xService = XServiceFactory.createFindDocXService(null, null);
		Document doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		XMLParserUtil.outputNode(doc);
		
		boolean threwException = false;
		try {
			item = new AlephItem();
			item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_DOC_NUM_MISSING)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when doc number missing");
		}
		
		//get good data
		xService = XServiceFactory.createFindDocXService(bib_base, bib_id1);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		//do negative testing, remove session id node
		NodeList nodes = doc.getElementsByTagName(AlephConstants.SESSION_ID_NODE);
		for (int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.getParentNode().removeChild(n);
		}
		
		threwException = false;
		try {
			item = new AlephItem();
			item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_FIND_DOC_FAILED_SESSION_ID_MISSING)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = new AlephItem();
		item.setBibId(bib_id1);
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//get circ status from item data call
		xService = XServiceFactory.createItemDataXService(bib_base, bib_id1);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseItemDataResponse(item,doc);
		
		XMLParserUtil.outputNode(doc);
		
		assertTrue("Item session id not Returned",item.getSessionId()!=null&&item.getSessionId().length()>0);
		assertEquals("Aleph item author returned is incorrect, Expected: "+author1+" Actual: "+item.getAuthor(),author1,item.getAuthor());
		assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn1+" Actual: "+item.getIsbn(),isbn1,item.getIsbn());
		assertEquals("Aleph item title returned is incorrect, Expected: "+title1+" Actual: "+item.getTitle(),title1,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber1+" Actual: "+item.getCallNumber(),callNumber1,item.getCallNumber());
		assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher1+" Actual: "+item.getPublisher(),publisher1,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description1+" Actual: "+item.getDescription(),description1,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series1+" Actual: "+item.getSeries(),series1,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location1+" Actual: "+item.getLocation(),location1,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource1+" Actual: "+item.getElectronicResource(),eresource1,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium1+" Actual: "+item.getMediumType(),medium1,item.getMediumType());
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus1+" Actual :"+item.getCirculationStatus(),circStatus1,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id1+" Actual :"+item.getItemId(),item_id1,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Excepted: "+bib_id1+" Actual: "+item.getBibId(),bib_id1,item.getBibId());
		assertEquals("Aleph item barcode returned is incorrect, Excepted: "+barcode1+" Actual: "+item.getBarcode(),barcode1,item.getBarcode());
		
		//get good data for second item
		xService = XServiceFactory.createFindDocXService(bib_base, bib_id2);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		item = new AlephItem();
		item.setBibId(bib_id2);
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//get circ status from item data call
		xService = XServiceFactory.createItemDataXService(bib_base, bib_id2);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseItemDataResponse(item,doc);
	
		XMLParserUtil.outputNode(doc);
		
		assertTrue("Item session id not Returned",item.getSessionId()!=null&&item.getSessionId().length()>0);
		assertEquals("Aleph item author returned is incorrect, Expected: "+author2+" Actual: "+item.getAuthor(),author2,item.getAuthor());
		assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn2+" Actual: "+item.getIsbn(),isbn2,item.getIsbn());
		//assertEquals("Aleph item title returned is incorrect, Expected: "+title2+" Actual: "+item.getTitle(),title2,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber2+" Actual: "+item.getCallNumber(),callNumber2,item.getCallNumber());
		assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher2+" Actual: "+item.getPublisher(),publisher2,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description2+" Actual: "+item.getDescription(),description2,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series2+" Actual: "+item.getSeries(),series2,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location2+" Actual: "+item.getLocation(),location2,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource2+" Actual: "+item.getElectronicResource(),eresource2,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium2+" Actual: "+item.getMediumType(),medium2,item.getMediumType());
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus2+" Actual :"+item.getCirculationStatus(),circStatus2,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id2+" Actual :"+item.getItemId(),item_id2,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Excepted: "+bib_id2+" Actual: "+item.getBibId(),bib_id2,item.getBibId());
		assertEquals("Aleph item barcode returned is incorrect, Excepted: "+barcode2+" Actual: "+item.getBarcode(),barcode2,item.getBarcode());
		
		//get good data for third item starting with adm id
		xService = XServiceFactory.createFindDocXService(adm_base, item_id3);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		item = new AlephItem();
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//now get bib info from bib id
		xService = XServiceFactory.createFindDocXService(bib_base, item.getBibId());
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		XMLParserUtil.outputNode(doc);
		
		//get circ status from item data call
		xService = XServiceFactory.createItemDataXService(bib_base, bib_id3);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseItemDataResponse(item,doc);
	
		XMLParserUtil.outputNode(doc);
	
		assertTrue("Item session id not Returned",item.getSessionId()!=null&&item.getSessionId().length()>0);
		assertEquals("Aleph item author returned is incorrect, Expected: "+author3+" Actual: "+item.getAuthor(),author3,item.getAuthor());
		//assertEquals("Aleph item isbn returned is incorrect, Expected: "+isbn3+" Actual: "+item.getIsbn(),isbn3,item.getIsbn());
		assertEquals("Aleph item title returned is incorrect, Expected: "+title3+" Actual: "+item.getTitle(),title3,item.getTitle());
		assertEquals("Aleph item call number returned is incorrect, Expected: "+callNumber3+" Actual: "+item.getCallNumber(),callNumber3,item.getCallNumber());
		//assertEquals("Aleph item publisher returned is incorrect, Expected: "+publisher3+" Actual: "+item.getPublisher(),publisher3,item.getPublisher());
		assertEquals("Aleph item description returned is incorrect, Expected: "+description3+" Actual: "+item.getDescription(),description3,item.getDescription());
		assertEquals("Aleph item series returned is incorrect, Expected: "+series3+" Actual: "+item.getSeries(),series3,item.getSeries());
		assertEquals("Aleph item location returned is incorrect, Expected: "+location3+" Actual: "+item.getLocation(),location3,item.getLocation());
		assertEquals("Aleph item eresource returned is incorrect, Expected: "+eresource3+" Actual: "+item.getElectronicResource(),eresource3,item.getElectronicResource());
		assertEquals("Aleph item medium returned is incorrect, Expected: "+medium3+" Actual: "+item.getMediumType(),medium3,item.getMediumType());
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus3+" Actual :"+item.getCirculationStatus(),circStatus3,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id3+" Actual :"+item.getItemId(),item_id3,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Excepted: "+bib_id3+" Actual: "+item.getBibId(),bib_id3,item.getBibId());
		assertEquals("Aleph item barcode returned is incorrect, Excepted: "+barcode3+" Actual: "+item.getBarcode(),barcode3,item.getBarcode());
		
		//get good data for fourth item, start with adm id
		xService = XServiceFactory.createFindDocXService(adm_base, item_id4);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		item = new AlephItem();
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		xService = XServiceFactory.createFindDocXService(bib_base, item.getBibId());
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//get circ status from item data call, use adm_id this time instead
		xService = XServiceFactory.createItemDataXService(bib_base, bib_id4);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseItemDataResponse(item,doc);
	
		XMLParserUtil.outputNode(doc);
	
		assertTrue("Item session id not Returned",item.getSessionId()!=null&&item.getSessionId().length()>0);
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
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus4+" Actual :"+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id4+" Actual: "+item.getItemId(),item_id4,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Excepted: "+bib_id4+" Actual: "+item.getBibId(),bib_id4,item.getBibId());
		assertEquals("Aleph item barcode returned is incorrect, Excepted: "+barcode4+" Actual: "+item.getBarcode(),barcode4,item.getBarcode());
		
		//test getting data with hold id
		xService = XServiceFactory.createFindDocXService(hold_base, holdId4);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
	
		item = new AlephItem();
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//get bib info
		xService = XServiceFactory.createFindDocXService(bib_base, item.getBibId());
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		item = AlephItemFactory.updateAlephItemParseFindDocResponse(item,doc);
		
		//get circ status from item data call, use adm_id this time instead
		xService = XServiceFactory.createItemDataXService(bib_base, item.getBibId());
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		item = AlephItemFactory.updateAlephItemParseItemDataResponse(item,doc);
	
		XMLParserUtil.outputNode(doc);
		
		assertTrue("Item session id not Returned",item.getSessionId()!=null&&item.getSessionId().length()>0);
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
		assertEquals("Aleph item circulation status returned is incorrect, Expected: "+circStatus4+" Actual :"+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
		assertEquals("Aleph item adm id returned is incorrect, Expected: "+item_id4+" Actual: "+item.getItemId(),item_id4,item.getItemId());
		assertEquals("Aleph item bib id returned is incorrect, Excepted: "+bib_id4+" Actual: "+item.getBibId(),bib_id4,item.getBibId());
		assertEquals("Aleph item barcode returned is incorrect, Excepted: "+barcode4+" Actual: "+item.getBarcode(),barcode4,item.getBarcode());
		
	}
	
	public void testGetHoldItems() throws Exception{
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String patron_id = TestConfiguration.getProperty("PATRON_ID");
		
		String cashDataType = AlephConstants.USER_CASH_ALL_DATA;
		String holdDataType = AlephConstants.USER_HOLDS_ALL_DATA;
		String loanDataType = AlephConstants.USER_LOANS_ALL_DATA;
		
		XService xService = XServiceFactory.createBorInfoXService(library, patron_id, null, cashDataType, null, holdDataType, loanDataType);
		Document doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		XMLParserUtil.outputNode(doc);
	}
}
