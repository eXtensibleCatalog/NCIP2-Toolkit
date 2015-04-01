package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (KOH)
 */
public class KohaLoginHandler extends DefaultHandler {

	private boolean isLogged = false;

	private boolean statusReached = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(KohaConstants.XML_STATUS)) {
			statusReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		statusReached = false;
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (statusReached) {
			String str = new String(ch, start, length);
			if (str.equals(KohaConstants.XML_VAL_STATUS_OK))
				isLogged = true;
		}
	}

	public boolean isLogged() {
		return isLogged;
	}
}
