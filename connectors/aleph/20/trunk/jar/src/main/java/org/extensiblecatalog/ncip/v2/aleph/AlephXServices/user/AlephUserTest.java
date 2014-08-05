package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.user;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.item.AlephItem;

import java.util.ArrayList;


import junit.framework.TestCase;

public class AlephUserTest extends TestCase {

	public void testSetUsername() {
		AlephUser user = new AlephUser();
		user.setUsername("rick");
		assertEquals("Username incorrect, Expected: rick Actual: "+user.getUsername(),"rick",user.getUsername());
	}

	public void testSetFullName() {
		AlephUser user = new AlephUser();
		String fullName = "Johnson, Richard Patrick";
		user.setFullName(fullName);
		assertEquals("Full name incorrect, Expected: "+fullName+" Actual: "+user.getFullName(),fullName,user.getFullName());
	}

	public void testSetAddress() {
		AlephUser user = new AlephUser();
		String address = "111 Mockingbird Lane";
		user.setAddress(address);
		assertEquals("Address incorrect, Expected: "+address+" Actual: "+user.getAddress(),address,user.getAddress());
	}

	public void testSetEmailAddress() {
		AlephUser user = new AlephUser();
		String email = "rick@user.com";
		user.setEmailAddress(email);
		assertEquals("Email incorrect, Expected: "+email+" Actual: "+user.getEmailAddress(),email,user.getEmailAddress());
	}

	public void testSetSessionId() {
		AlephUser user = new AlephUser();
		String sessionId = "fdafraere5555";
		user.setSessionId(sessionId);
		assertEquals("Session id incorrect, Expected: "+sessionId+" Actual: "+user.getSessionId(),sessionId,user.getSessionId());
	}
	
	public void testHasRequestedItem(){
		AlephUser user = new AlephUser();
		AlephItem item1 = new AlephItem();
		item1.setItemId("item1");
		item1.setBibId("bib1");
		AlephItem item2 = new AlephItem();
		item2.setItemId("item2");
		AlephItem item3 = new AlephItem();
		item3.setBibId("bib3");
		AlephItem item4 = new AlephItem();
		AlephItem item5 = new AlephItem();
		item5.setBibId("bib1");
		AlephItem itemNull = null;
		
		//check no items in user
		user.setRequestedItems(null);
		assertFalse("Item Found when none exist",user.hasRequestedItem(item1));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item2));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item3));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item4));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item5));
		assertFalse("Item Found when none exist",user.hasRequestedItem(itemNull));
		
		//check no items in user
		user.setRequestedItems(new ArrayList<AlephItem>());
		assertFalse("Item Found when none exist",user.hasRequestedItem(item1));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item2));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item3));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item4));
		assertFalse("Item Found when none exist",user.hasRequestedItem(item5));
		assertFalse("Item Found when none exist",user.hasRequestedItem(itemNull));
		
		//add some items
		user.addRequestedItem(item2);
		user.addRequestedItem(item5);
		//item 5 exists with same bib id and item 1 so should be true
		assertTrue("Did not find item with same bib id as expected",user.hasRequestedItem(item1));
		assertTrue("Did not find item 2",user.hasRequestedItem(item2));
		assertFalse("Item Found when does not exist",user.hasRequestedItem(item3));
		//item 4 should always be false
		assertFalse("Item Found when none exist",user.hasRequestedItem(item4));
		
		assertTrue("Did not find item 5",user.hasRequestedItem(item5));
		assertFalse("Item Found when none exist",user.hasRequestedItem(itemNull));
	}
}
