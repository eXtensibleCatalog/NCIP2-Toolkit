package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import java.util.HashMap;
import java.util.Map;

import org.extensiblecatalog.ncip.v2.koha.item.MarcItem;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KohaLookupItemHandler extends DefaultHandler {

	private MarcItem marcItem;

	private String currentCodeVal;
	private String currentTagVal;

	private Map<String, String> currentSubFieldMap;

	private boolean marcSubFieldReached;
	private boolean marcControlFieldReached;
	private boolean marcLeaderReached;

	public KohaLookupItemHandler() {
		marcItem = new MarcItem();

		marcSubFieldReached = false;
		marcControlFieldReached = false;
		marcLeaderReached = false;

		currentSubFieldMap = new HashMap<String, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals(KohaConstants.XML_SUBFIELD)) {

			currentCodeVal = attributes.getValue(KohaConstants.XML_ATTR_CODE);
			marcSubFieldReached = true;

		} else if (qName.equals(KohaConstants.XML_DATAFIELD)) {

			currentTagVal = attributes.getValue(KohaConstants.XML_ATTR_TAG);

		} else if (qName.equals(KohaConstants.XML_CONTROLFIELD)) {

			currentTagVal = attributes.getValue(KohaConstants.XML_ATTR_TAG);
			marcControlFieldReached = true;

		} else if (qName.equals(KohaConstants.XML_LEADER)) {

			marcLeaderReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals(KohaConstants.XML_SUBFIELD)) {

			marcSubFieldReached = false;

		} else if (qName.equals(KohaConstants.XML_DATAFIELD)) {

			marcItem.addDataField(currentTagVal, currentSubFieldMap);
			currentSubFieldMap = new HashMap<String, String>();

		} else if (qName.equals(KohaConstants.XML_CONTROLFIELD)) {

			marcControlFieldReached = false;

		} else if (qName.equals(KohaConstants.XML_LEADER)) {

			marcLeaderReached = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (marcSubFieldReached) {
			String subFieldVal = new String(ch, start, length);
			currentSubFieldMap.put(currentCodeVal, subFieldVal);
		} else if (marcControlFieldReached) {
			String controlFieldVal = new String(ch, start, length);
			marcItem.addControlField(currentTagVal, controlFieldVal);
			currentTagVal = null;
		} else if (marcLeaderReached) {
			marcItem.setLeader(new String(ch, start, length));
		}

	}

	public MarcItem getMarcItem() {
		return marcItem;
	}
}
