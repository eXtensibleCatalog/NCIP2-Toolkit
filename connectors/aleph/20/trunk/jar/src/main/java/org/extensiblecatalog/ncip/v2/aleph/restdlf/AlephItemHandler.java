package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephItemHandler extends DefaultHandler {
	private List<AlephItem> listOfItems;
	private AlephItem item;
	private boolean bibDescriptionDesired;
	private boolean circulationStatusDesired;
	private boolean holdQueueLnegthDesired;
	private boolean itemDesrciptionDesired;
	private boolean bibDescriptionReached = false;
	private boolean circulationStatusReached = false;
	private boolean holdQueueLnegthReached = false;
	private boolean itemDesrciptionReached = false;
	private boolean authorReached = false;
	private boolean isbnReached = false;
	private boolean titleReached = false;
	private boolean publisherReached = false;
	private boolean bibIdReached = false;

	/**
	 * @return the listOfItems
	 */
	public List<AlephItem> getListOfItems() {
		return listOfItems;
	}

	/**
	 * Constructor sets desired services - this prevents to forget.
	 * 
	 * @param bibDescriptionDesired
	 * @param circulationStatusDesired
	 * @param holdQueueLnegthDesired
	 * @param itemDesrciptionDesired
	 * @throws AlephException
	 */
	public AlephItemHandler(boolean bibDescriptionDesired, boolean circulationStatusDesired, boolean holdQueueLnegthDesired, boolean itemDesrciptionDesired) throws AlephException {
		if (bibDescriptionDesired || circulationStatusDesired || holdQueueLnegthDesired || itemDesrciptionDesired) {
			this.bibDescriptionDesired = bibDescriptionDesired;
			this.circulationStatusDesired = circulationStatusDesired;
			this.holdQueueLnegthDesired = holdQueueLnegthDesired;
			this.itemDesrciptionDesired = itemDesrciptionDesired;
		} else {
			throw new AlephException("No service desired. Please supply at least one service you wish to use.");
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			item = new AlephItem();
			item.setLink(attributes.getValue(AlephConstants.HREF_NODE_ATTR));
			if (listOfItems == null)
				listOfItems = new ArrayList<AlephItem>();
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && circulationStatusDesired) {
			circulationStatusReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_HOLD_DOC_NUMBER_NODE) && holdQueueLnegthDesired) {
			holdQueueLnegthReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && itemDesrciptionDesired) {
			itemDesrciptionReached = true;
		} else if (bibDescriptionDesired) {
			if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE)) {
				authorReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE)) {
				isbnReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE)) {
				titleReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE)) {
				publisherReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_BIB_ID_NODE)) {
				bibIdReached = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			listOfItems.add(item);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (circulationStatusReached) {
			item.setCirculationStatus(new String(ch, start, length));
			circulationStatusReached = false;
		} else if (holdQueueLnegthReached) {
			item.setHoldQueueLength(Integer.parseInt(new String(ch, start, length)));
			holdQueueLnegthReached = false;
		} else if (itemDesrciptionReached) {
			item.setDescription(new String(ch, start, length));
			itemDesrciptionReached = false;
		} else if (bibDescriptionReached && (authorReached || isbnReached || titleReached || publisherReached || bibIdReached)) {
			if (authorReached) {
				item.setAuthor(new String(ch, start, length));
				authorReached = false;
			} else if (isbnReached) {
				item.setIsbn(new String(ch, start, length));
				isbnReached = false;
			} else if (titleReached) {
				item.setTitle(new String(ch, start, length));
				titleReached = false;
			} else if (publisherReached) {
				item.setPublisher(new String(ch, start, length));
				publisherReached = false;
			} else if (bibIdReached) {
				item.setBibId(new String(ch, start, length));
				bibIdReached = false;
			}
		}
	}
}
