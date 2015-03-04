package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import org.extensiblecatalog.ncip.v2.koha.item.KohaItem;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KohaLookupItemHandler extends DefaultHandler {

	private KohaItem kohaItem;

	private boolean barcodeReached = false;

	public KohaLookupItemHandler() {
		kohaItem = new KohaItem();
	}

	/*<controlfield tag="005">20150303134635.0</controlfield>*/

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals(KohaConstants.XML_CONTROLFIELD)) {

			String tagVal = attributes.getValue(KohaConstants.XML_ATTR_TAG);

			if (tagVal.equals("005")) {
				barcodeReached = true;
			}

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		barcodeReached = false;
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (barcodeReached) {
			kohaItem.setBarcode(new String(ch, start, length));
		}
	}

	public KohaItem getKohaItem() {
		return kohaItem;
	}

}
