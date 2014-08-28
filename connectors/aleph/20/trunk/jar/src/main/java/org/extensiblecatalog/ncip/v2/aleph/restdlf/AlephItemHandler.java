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
	public AlephItem item;
	private String docNumber;
	private String itemSequence;
	private boolean requireAtLeastOneService;
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
	private boolean agencyReached = false;
	private boolean openDateReached = false;
	private boolean callNoReached = false;
	private boolean copyNoReached = false;
	private boolean materialReached = false;
	private boolean barcodeReached = false;
	private boolean itemSequenceReached;

	/**
	 * Sets if has responder return error if there is no service desired.
	 * 
	 * @param requireAtLeastOneService
	 */
	public void requireAtLeastOneService(boolean requireAtLeastOneService) {
		this.requireAtLeastOneService = requireAtLeastOneService;
	}

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
		} else if (requireAtLeastOneService) {
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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
			docNoReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_SEQUENCE_NODE)) {
			itemSequenceReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE)) {
			agencyReached = true;
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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && docNoReached && itemSequence != null) {
			item.setItemId(AlephConstants.ERROR_ITEM_ID_NOT_FOUND);
			itemIdNotFound = true;
			docNoReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_SEQUENCE_NODE) && itemSequenceReached && docNumber != null) {
			item.setItemId(AlephConstants.ERROR_ITEM_ID_NOT_FOUND);
			itemIdNotFound = true;
			itemSequenceReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && agencyReached) {
			AlephAgency agency = new AlephAgency(); // FIXME: sublibrary is not agency! see {@link AlephItem.setSubLibrary(String subLibrary)}
			agency.setAgencyId(AlephConstants.ERROR_AGENCY_NOT_FOUND);
			item.setAgency(agency);
			agencyReached = false;
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
		} else if (docNoReached) {
			docNumber = new String(ch, start, length);
			docNoReached = false;
		} else if (itemSequenceReached) {
			itemSequence = new String(ch, start, length);
			itemSequenceReached = false;
		} else if (agencyReached) {
			AlephAgency agency = new AlephAgency();
			agency.setAgencyId(new String(ch, start, length));
			item.setAgency(agency);
			agencyReached = false;
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
	}
}
