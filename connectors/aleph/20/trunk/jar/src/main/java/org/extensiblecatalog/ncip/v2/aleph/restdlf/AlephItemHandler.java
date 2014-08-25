package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.agency.AlephAgency;
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
	private String responseCode;
	private boolean bibDescriptionDesired;
	private boolean circulationStatusDesired;
	private boolean holdQueueLnegthDesired;
	private boolean itemDesrciptionDesired;
	private boolean circulationStatusReached = false;
	private boolean holdQueueLnegthReached = false;
	private boolean itemDesrciptionReached = false;
	private boolean authorReached = false;
	private boolean isbnReached = false;
	private boolean titleReached = false;
	private boolean publisherReached = false;
	private boolean bibIdReached = false;
	private boolean itemIdReached = false;
	private boolean responseReached = false;
	private boolean agencyReached = false;
	private boolean itemFound = false;
	private boolean openDateReached = false;

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
		if (qName.equalsIgnoreCase(AlephConstants.GET_ITEM_LIST_NODE) && itemFound) {
			itemFound = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			itemFound = true;
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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
			itemIdReached = true;
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
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
			responseReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE)) {
			agencyReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_OPEN_DATE_NODE)) {
			openDateReached  = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			listOfItems.add(item);
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && circulationStatusDesired && circulationStatusReached) {
			item.setCirculationStatus(AlephConstants.ERROR_CIRCULATION_STATUS_NOT_FOUND);
			circulationStatusReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_HOLD_DOC_NUMBER_NODE) && holdQueueLnegthDesired && holdQueueLnegthReached) {
			item.setholdQueue(AlephConstants.ERROR_HOLD_QUEUE_NOT_FOUND);
			holdQueueLnegthReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && itemDesrciptionDesired && itemDesrciptionReached) {
			item.setDescription(AlephConstants.ERROR_ITEM_DESCRIPTION_NOT_FOUND);
			itemDesrciptionReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && itemIdReached) {
			item.setItemId(AlephConstants.ERROR_ITEM_ID_NOT_FOUND);
			itemIdReached = false;
		} else if (bibDescriptionDesired) {
			if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && authorReached) {
				item.setAuthor(AlephConstants.ERROR_AUTHOR_NOT_FOUND);
				authorReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && isbnReached) {
				item.setIsbn(AlephConstants.ERROR_ISBN_NOT_FOUND);
				isbnReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && titleReached) {
				item.setTitle(AlephConstants.ERROR_TITLE_NOT_FOUND);
				titleReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && publisherReached) {
				item.setPublisher(AlephConstants.ERROR_PUBLISHER_NOT_FOUND);
				publisherReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_BIB_ID_NODE) && bibIdReached) {
				item.setBibId(AlephConstants.ERROR_BIBLIOGRAPHIC_ID_NOT_FOUND);
				bibIdReached = false;
			}
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
			if (responseCode == "0019") {
				listOfItems.add(new AlephItem(responseCode));
			}
		} else if (qName.equalsIgnoreCase(AlephConstants.GET_ITEM_LIST_NODE) && !itemFound) {
			listOfItems.add(new AlephItem(responseCode));
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && agencyReached) {
			AlephAgency agency = new AlephAgency(); //FIXME: sublibrary is not agency! see {@link AlephItem.setSubLibrary(String subLibrary)}
			agency.setAgencyId(AlephConstants.ERROR_AGENCY_NOT_FOUND);
			item.setAgency(agency);
			agencyReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_OPEN_DATE_NODE) && openDateReached) {
			//TODO: Set opendate not set for this item
			openDateReached = false;
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
		} else if (itemIdReached) {
			item.setItemId(new String(ch, start, length));
			itemIdReached = false;
		} else if (bibDescriptionDesired && (authorReached || isbnReached || titleReached || publisherReached || bibIdReached)) {
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
		} else if (responseReached) {
			responseCode = new String(ch, start, length);
			responseReached = false;
		} else if (agencyReached) {
			AlephAgency agency = new AlephAgency();
			agency.setAgencyId(new String(ch,start,length));
			item.setAgency(agency);
			agencyReached = false;
		} else if (openDateReached) {
			//TODO: set open date (publicationDate {@link BibliographicDescription}
			//TODO: you have to first implement this service to {@link AlephItem} with respect to bibDescription
			
			openDateReached = false;
		}
	}
}
