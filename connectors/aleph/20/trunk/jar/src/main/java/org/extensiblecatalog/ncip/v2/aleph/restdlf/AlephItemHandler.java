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
	private String docNumber;
	private String itemSequence;
	private boolean itemIdNotFound = false;
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
	private boolean docNoReached = false;
	private boolean locationReached = false;
	private boolean openDateReached = false;
	private boolean callNoReached = false;
	private boolean copyNoReached = false;
	private boolean materialReached = false;
	private boolean barcodeReached = false;
	private boolean itemSequenceReached = false;
	private boolean agencyReached = false;
	private boolean collectionReached = false;
	private boolean parsingLoansOrRequests = false;

	public AlephItemHandler parseLoansOrRequests() {
		parsingLoansOrRequests = true;
		return this;
	}

	/**
	 * @return the listOfItems
	 */
	public List<AlephItem> getListOfItems() {
		return listOfItems;
	}

	public AlephItem getAlephItem() {
		return item;
	}

	/**
	 * Constructor sets desired services.
	 * 
	 * @param requireAtLeastOneService
	 *            - usually parsed from toolkit.properties
	 * @param bibDescriptionDesired
	 *            - parsed from initData
	 * @param circulationStatusDesired
	 *            - parsed from initData
	 * @param holdQueueLnegthDesired
	 *            - parsed from initData
	 * @param itemDesrciptionDesired
	 *            - parsed from initData
	 * @throws AlephException
	 */
	public AlephItemHandler(boolean requireAtLeastOneService, boolean bibDescriptionDesired, boolean circulationStatusDesired, boolean holdQueueLnegthDesired,
			boolean itemDesrciptionDesired) throws AlephException {
		if (bibDescriptionDesired || circulationStatusDesired || holdQueueLnegthDesired || itemDesrciptionDesired) {
			this.bibDescriptionDesired = bibDescriptionDesired;
			this.circulationStatusDesired = circulationStatusDesired;
			this.holdQueueLnegthDesired = holdQueueLnegthDesired;
			this.itemDesrciptionDesired = itemDesrciptionDesired;
		} else if (requireAtLeastOneService) {
			throw new AlephException("No service desired. Please supply at least one service you wish to use.");
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!parsingLoansOrRequests) {
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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
				docNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_SEQUENCE_NODE)) {
				itemSequenceReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE)) {
				locationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_OPEN_DATE_NODE)) {
				openDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE)) {
				callNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COPY_ID_NODE)) {
				copyNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE)) {
				barcodeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE)) {
				materialReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
				agencyReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE)) {
				collectionReached = true;
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
		} else {
			// TODO: implement parsing loans & requests here ...
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!parsingLoansOrRequests) {
			if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
				if (!itemIdNotFound)
					item.setItemId(docNumber.trim() + "-" + itemSequence.trim());
				listOfItems.add(item);
			} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && circulationStatusDesired && circulationStatusReached) {
				item.setCirculationStatus(AlephConstants.ERROR_CIRCULATION_STATUS_NOT_FOUND);
				circulationStatusReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_HOLD_DOC_NUMBER_NODE) && holdQueueLnegthDesired && holdQueueLnegthReached) {
				item.setHoldQueueLength(-1);
				item.setholdQueue(AlephConstants.ERROR_HOLD_QUEUE_NOT_FOUND);
				holdQueueLnegthReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && itemDesrciptionDesired && itemDesrciptionReached) {
				item.setDescription(AlephConstants.ERROR_ITEM_DESCRIPTION_NOT_FOUND);
				itemDesrciptionReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && docNoReached) {
				// If is item sequence set, but doc number is not, then I can't parse unique Aleph proprietary item id (e.g. 421-1.0)
				if (itemSequence != null)
					item.setItemId(AlephConstants.ERROR_ITEM_ID_NOT_FOUND);
				item.setBibId(AlephConstants.ERROR_BIBLIOGRAPHIC_ID_NOT_FOUND);
				item.setDocNumber(AlephConstants.ERROR_DOCUMENT_NUMBER_NOT_FOUND);
				itemIdNotFound = true;
				docNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_SEQUENCE_NODE) && itemSequenceReached) {
				// If is item sequence set, but doc number is not, then I can't parse unique Aleph proprietary item id (e.g. 421-1.0)
				if (docNumber != null)
					item.setItemId(AlephConstants.ERROR_ITEM_ID_NOT_FOUND);
				item.setSeqNumber(AlephConstants.ERROR_SEQUENCE_NUMBER_NOT_FOUND);
				itemIdNotFound = true;
				itemSequenceReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && locationReached) {
				item.setLocation(AlephConstants.ERROR_LOCATION_NOT_FOUND);
				locationReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_OPEN_DATE_NODE) && openDateReached) {
				item.setPublicationDate(AlephConstants.ERROR_OPENDATE_NOT_FOUND);
				openDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && callNoReached) {
				item.setCallNumber(AlephConstants.ERROR_CALL_NO_NOT_FOUND);
				callNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COPY_ID_NODE) && copyNoReached) {
				item.setCopyNumber(AlephConstants.ERROR_COPY_NO_NOT_FOUND);
				copyNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && barcodeReached) {
				item.setBarcode(AlephConstants.ERROR_BARCODE_NOT_FOUND);
				barcodeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && materialReached) {
				item.setMediumType(AlephConstants.ERROR_MATERIAL_NOT_FOUND);
				materialReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE) && agencyReached) {
				item.setAgency(AlephConstants.ERROR_AGENCY_NOT_FOUND);
				agencyReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && collectionReached) {
				item.setCollection(AlephConstants.ERROR_COLLECTION_NOT_FOUND);
				collectionReached = false;
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
			}
		} else {
			// TODO: implement parsing loans & requests here ...
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (!parsingLoansOrRequests) {
			if (circulationStatusReached) {
				item.setCirculationStatus(new String(ch, start, length));
				circulationStatusReached = false;
			} else if (holdQueueLnegthReached) {
				item.setHoldQueueLength(Integer.parseInt(new String(ch, start, length)));
				holdQueueLnegthReached = false;
			} else if (itemDesrciptionReached) {
				item.setDescription(new String(ch, start, length));
				itemDesrciptionReached = false;
			} else if (docNoReached) {
				docNumber = new String(ch, start, length);
				item.setDocNumber(docNumber);
				item.setBibId(docNumber);
				docNoReached = false;
			} else if (itemSequenceReached) {
				itemSequence = new String(ch, start, length);
				item.setSeqNumber(itemSequence);
				itemSequenceReached = false;
			} else if (locationReached) {
				item.setLocation(new String(ch, start, length));
				locationReached = false;
			} else if (openDateReached) {
				item.setPublicationDate(new String(ch, start, length));
				openDateReached = false;
			} else if (callNoReached) {
				item.setCallNumber(new String(ch, start, length));
				callNoReached = false;
			} else if (copyNoReached) {
				item.setCopyNumber(new String(ch, start, length));
				copyNoReached = false;
			} else if (barcodeReached) {
				item.setBarcode(new String(ch, start, length));
				barcodeReached = false;
			} else if (materialReached) {
				item.setMediumType(new String(ch, start, length));
				materialReached = false;
			} else if (agencyReached) {
				item.setAgency(new String(ch, start, length));
				agencyReached = false;
			} else if (collectionReached) {
				item.setCollection(new String(ch, start, length));
				collectionReached = false;
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
			}
		} else {
			// TODO: implement parsing loans & requests here ...
		}
	}
}
