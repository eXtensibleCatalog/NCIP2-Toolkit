package org.extensiblecatalog.ncip.v2.koha.item;

import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;

import junit.framework.TestCase;

public class KohaItemTest extends TestCase {

	public void testSetBibId() {
		KohaItem item = new KohaItem();
		String inBibId = "335678";
		String expectedBibId = "000335678";
		item.setBibId(inBibId);
		assertEquals("Unexpected bibId, Expected: "+expectedBibId+", Actual: "+item.getBibId(),expectedBibId,item.getBibId());
	}

	public void testSetItemId() {
		KohaItem item = new KohaItem();
		item.setItemId("itemId");
		assertEquals("Unexpected itemId, Expected: itemId, Actual: "+item.getItemId(),item.getItemId(),"itemId");
	}

	public void testSetDescription() {
		KohaItem item = new KohaItem();
		item.setDescription("this is a book");
		assertEquals("Unexpected description, Expected: 'this is a book', Actual: '"+item.getDescription()+"'",item.getDescription(),"this is a book");
	}

	public void testSetLocation() {
		KohaItem item = new KohaItem();
		item.setLocation("on the shelf");
		assertEquals("Unexpected location, Expected: 'on the shelf', Actual: '"+item.getLocation()+"'",item.getLocation(),"on the shelf");
	}

	public void testSetCallNumber() {
		KohaItem item = new KohaItem();
		item.setCallNumber("11111");
		assertEquals("Unexpected call number, Expected: '11111', Actual: '"+item.getCallNumber()+"'",item.getCallNumber(),"11111");
	}

	public void testSetAuthor() {
		KohaItem item = new KohaItem();
		item.setAuthor("Johnson, Richard");
		assertEquals("Unexpected author, Expected: 'Johnson, Richard', Actual: '"+item.getAuthor()+"'",item.getAuthor(),"Johnson, Richard");
	}

	public void testSetIsbn() {
		KohaItem item = new KohaItem();
		item.setIsbn("isbn");
		assertEquals("Unexpected isbn, Expected: 'isbn', Actual: '"+item.getIsbn()+"'",item.getIsbn(),"isbn");
	}


	public void testSetMediumType() {
		KohaItem item = new KohaItem();
		item.setMediumType(KohaUtil.detectMediumType("dvd"));
		assertEquals("Unexpected medium type, Expected: 'dvd', Actual: '"+item.getMediumType()+"'",item.getMediumType(),KohaUtil.detectMediumType("dvd"));
	}

	public void testSetPublisher() {
		KohaItem item = new KohaItem();
		item.setPublisher("random house");
		assertEquals("Unexpected publisher, Expected: 'random house', Actual: '"+item.getPublisher()+"'",item.getPublisher(),"random house");
	}

	public void testSetSeries() {
		KohaItem item = new KohaItem();
		item.setSeries("series");
		assertEquals("Unexpected series, Expected: 'series', Actual: '"+item.getSeries()+"'",item.getSeries(),"series");
	}

	public void testSetTitle() {
		KohaItem item = new KohaItem();
		item.setTitle("My Book");
		assertEquals("Unexpected title, Expected: 'My Book', Actual: '"+item.getTitle()+"'",item.getTitle(),"My Book");
	}

	public void testSetCirculationStatus() {
		KohaItem item = new KohaItem();
		item.setCirculationStatus(KohaUtil.parseCirculationStatus("Checked Out"));
		assertEquals("Unexpected circulation status, Expected: 'Checked Out', Actual: '"+item.getCirculationStatus().getValue()+"'",item.getCirculationStatus().getValue(),"Checked Out");
	}

	public void testSetElectronicResource() {
		KohaItem item = new KohaItem();
		item.setElectronicResource("e-resource");
		assertEquals("Unexpected Electronic Resource, Expected: 'e-resource', Actual: '"+item.getElectronicResource()+"'",item.getElectronicResource(),"e-resource");
	}
	
	public void testSetSessionId(){
		KohaItem item = new KohaItem();
		item.setSessionId("22222222");
		assertEquals("Unexpected Session Id, Expected: '22222222', Actual: '"+item.getSessionId()+"'",item.getSessionId(),"22222222");
	}
	
	public void testSetBarcode(){
		KohaItem item = new KohaItem();
		item.setBarcode("00000002");
		assertEquals("Unexpected Barcode, Expected: '00000002', Actual: '"+item.getBarcode()+"'",item.getBarcode(),"00000002");
	}

}
