package org.extensiblecatalog.ncip.v2.aleph.item;

import junit.framework.TestCase;

public class AlephItemTest extends TestCase {

	public void testSetBibId() {
		AlephItem item = new AlephItem();
		String inBibId = "335678";
		String expectedBibId = "000335678";
		item.setBibId(inBibId);
		assertEquals("Unexpected bibId, Expected: "+expectedBibId+", Actual: "+item.getBibId(),expectedBibId,item.getBibId());
	}

	public void testSetItemId() {
		AlephItem item = new AlephItem();
		item.setItemId("itemId");
		assertEquals("Unexpected itemId, Expected: itemId, Actual: "+item.getItemId(),item.getItemId(),"itemId");
	}

	public void testSetDescription() {
		AlephItem item = new AlephItem();
		item.setDescription("this is a book");
		assertEquals("Unexpected description, Expected: 'this is a book', Actual: '"+item.getDescription()+"'",item.getDescription(),"this is a book");
	}

	public void testSetLocation() {
		AlephItem item = new AlephItem();
		item.setLocation("on the shelf");
		assertEquals("Unexpected location, Expected: 'on the shelf', Actual: '"+item.getLocation()+"'",item.getLocation(),"on the shelf");
	}

	public void testSetCallNumber() {
		AlephItem item = new AlephItem();
		item.setCallNumber("11111");
		assertEquals("Unexpected call number, Expected: '11111', Actual: '"+item.getCallNumber()+"'",item.getCallNumber(),"11111");
	}

	public void testSetAuthor() {
		AlephItem item = new AlephItem();
		item.setAuthor("Johnson, Richard");
		assertEquals("Unexpected author, Expected: 'Johnson, Richard', Actual: '"+item.getAuthor()+"'",item.getAuthor(),"Johnson, Richard");
	}

	public void testSetIsbn() {
		AlephItem item = new AlephItem();
		item.setIsbn("isbn");
		assertEquals("Unexpected isbn, Expected: 'isbn', Actual: '"+item.getIsbn()+"'",item.getIsbn(),"isbn");
	}


	public void testSetMediumType() {
		AlephItem item = new AlephItem();
		item.setMediumType("dvd");
		assertEquals("Unexpected medium type, Expected: 'dvd', Actual: '"+item.getMediumType()+"'",item.getMediumType(),"dvd");
	}

	public void testSetPublisher() {
		AlephItem item = new AlephItem();
		item.setPublisher("random house");
		assertEquals("Unexpected publisher, Expected: 'random house', Actual: '"+item.getPublisher()+"'",item.getPublisher(),"random house");
	}

	public void testSetSeries() {
		AlephItem item = new AlephItem();
		item.setSeries("series");
		assertEquals("Unexpected series, Expected: 'series', Actual: '"+item.getSeries()+"'",item.getSeries(),"series");
	}

	public void testSetTitle() {
		AlephItem item = new AlephItem();
		item.setTitle("My Book");
		assertEquals("Unexpected title, Expected: 'My Book', Actual: '"+item.getTitle()+"'",item.getTitle(),"My Book");
	}

	public void testSetCirculationStatus() {
		AlephItem item = new AlephItem();
		item.setCirculationStatus("Checked Out");
		assertEquals("Unexpected circulation status, Expected: 'Checked Out', Actual: '"+item.getCirculationStatus()+"'",item.getCirculationStatus(),"Checked Out");
	}

	public void testSetElectronicResource() {
		AlephItem item = new AlephItem();
		item.setElectronicResource("e-resource");
		assertEquals("Unexpected Electronic Resource, Expected: 'e-resource', Actual: '"+item.getElectronicResource()+"'",item.getElectronicResource(),"e-resource");
	}
	
	public void testSetSessionId(){
		AlephItem item = new AlephItem();
		item.setSessionId("22222222");
		assertEquals("Unexpected Session Id, Expected: '22222222', Actual: '"+item.getSessionId()+"'",item.getSessionId(),"22222222");
	}
	
	public void testSetBarcode(){
		AlephItem item = new AlephItem();
		item.setBarcode("00000002");
		assertEquals("Unexpected Barcode, Expected: '00000002', Actual: '"+item.getBarcode()+"'",item.getBarcode(),"00000002");
	}

}
