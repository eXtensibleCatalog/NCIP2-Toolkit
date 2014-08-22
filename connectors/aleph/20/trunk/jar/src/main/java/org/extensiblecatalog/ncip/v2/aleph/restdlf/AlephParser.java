package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.w3c.dom.Document;

public class AlephParser {
	Document xmlToParse;
	public AlephParser(Document xmlResponse) {
		xmlToParse = xmlResponse; 
	}
	public AlephItem toAlephItem() {
		AlephItem item = new AlephItem();
		
		
		return item;
	}
	public AlephUser toAlephUser() {
		AlephUser user = new AlephUser();
		
		return user;
	}
}
