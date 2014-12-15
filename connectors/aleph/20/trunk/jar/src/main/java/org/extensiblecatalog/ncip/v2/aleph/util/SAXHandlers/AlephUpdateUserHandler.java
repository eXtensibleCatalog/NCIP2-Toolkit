package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephUpdateUserHandler extends DefaultHandler {

	private boolean isSuccess = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
	}

	public boolean isSuccess() {
		return isSuccess;
	}
}
