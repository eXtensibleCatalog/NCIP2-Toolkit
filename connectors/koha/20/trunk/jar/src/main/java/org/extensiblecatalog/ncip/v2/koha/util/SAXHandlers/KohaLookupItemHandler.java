package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import org.extensiblecatalog.ncip.v2.koha.item.KohaItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KohaLookupItemHandler extends DefaultHandler {

	private KohaItem kohaItem;

	public KohaLookupItemHandler() {
		kohaItem = new KohaItem();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

	}

	public KohaItem getKohaItem() {
		return kohaItem;
	}

}
