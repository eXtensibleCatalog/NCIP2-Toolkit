package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is constructed to parse XML outputs of Aleph RESTful APIs items
 * 
 * 
 * @author Jiří Kozlovský (MZK)
 * 
 */
public class AlephItemHandler extends DefaultHandler {

	private List<AlephItem> listOfItems;
	private AlephItem currentAlephItem;

	// Required to decide whether is set second call number the one we need
	private String secondCallNoType;
	private boolean z30callNumberReached = false;
	private boolean z30callNumber2TypeReached = false;
	private boolean z30callNumber2Reached = false;

	// Desired services
	private boolean bibDescriptionDesired;
	private boolean circulationStatusDesired;
	private boolean holdQueueLengthDesired;
	private boolean itemDesrciptionDesired;
	private boolean locationDesired;
	private boolean itemRestrictionDesired;

	// Location
	private boolean z30subLibraryReached = false;
	private boolean z30collectionReached = false;

	// Regular item achievements
	private boolean statusReached = false;
	private boolean queueReached = false;
	private boolean z30desrciptionReached = false;
	private boolean z13authorReached = false;
	private boolean z13isbnReached = false;
	private boolean z13titleReached = false;
	private boolean z13publisherReached = false;
	private boolean z30copyIdReached = false;
	private boolean z30materialReached = false;
	private boolean z30barcodeReached = false;
	private boolean z30itemStatusReached = false;

	private boolean localizationDesired = false;

	public List<AlephItem> getListOfItems() {
		return listOfItems;
	}

	public AlephItem getCurrentAlephItem() {
		return currentAlephItem;
	}

	public AlephItemHandler(LookupItemInitiationData initData) throws AlephException {

		bibDescriptionDesired = initData.getBibliographicDescriptionDesired();
		circulationStatusDesired = initData.getCirculationStatusDesired();
		holdQueueLengthDesired = initData.getHoldQueueLengthDesired();
		itemDesrciptionDesired = initData.getItemDescriptionDesired();
		locationDesired = initData.getLocationDesired();
		itemRestrictionDesired = initData.getItemUseRestrictionTypeDesired();

		if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null) {
			String localization = initData.getInitiationHeader().getApplicationProfileType().getValue();

			if (localization != null && !localization.isEmpty())
				localizationDesired = true;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			secondCallNoType = null;
			currentAlephItem = new AlephItem();

			String itemLink = attributes.getValue(AlephConstants.HREF_NODE_ATTRIBUTE);

			if (itemLink != null) {
				// This occurs if it is parsing link with view=full parameter (e.g. using LookupItemSet with BibRecId)
				String[] itemLinkParts = itemLink.split("/");

				currentAlephItem.setItemId(itemLinkParts[5] + AlephConstants.UNIQUE_ITEM_ID_SEPARATOR + itemLinkParts[7]);
			}

			if (listOfItems == null)
				listOfItems = new ArrayList<AlephItem>();

		} else if (qName.equalsIgnoreCase(AlephConstants.QUEUE_NODE) && holdQueueLengthDesired) {
			queueReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && locationDesired) {
			z30collectionReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && locationDesired) {
			z30subLibraryReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && circulationStatusDesired) {
			statusReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COPY_ID_NODE) && itemDesrciptionDesired) {
			z30copyIdReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && itemDesrciptionDesired) {
			z30materialReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && itemDesrciptionDesired) {
			z30desrciptionReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && itemDesrciptionDesired) {
			z30callNumberReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && itemDesrciptionDesired) {
			z30callNumber2Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && itemDesrciptionDesired) {
			z30callNumber2TypeReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_STATUS_NODE) && itemRestrictionDesired) {
			z30itemStatusReached = true;
		} else if (bibDescriptionDesired) {
			if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE)) {
				z13authorReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE)) {
				z30barcodeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE)) {
				z13isbnReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE)) {
				z13titleReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE)) {
				z13publisherReached = true;
			}
		}

	}

	// END OF PARSING START ELEMENT

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			listOfItems.add(currentAlephItem);
		} else if (qName.equalsIgnoreCase(AlephConstants.QUEUE_NODE) && queueReached) {
			currentAlephItem.setHoldQueueLength(0);
			queueReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && z30collectionReached) {
			z30collectionReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && z30subLibraryReached) {
			z30subLibraryReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && statusReached) {
			statusReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_COPY_ID_NODE) && z30copyIdReached) {
			z30copyIdReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && z30materialReached) {
			z30materialReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && z30desrciptionReached) {
			z30desrciptionReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && z30callNumberReached) {
			z30callNumberReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && z30callNumber2Reached) {
			z30callNumber2Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && z30callNumber2TypeReached) {
			z30callNumber2TypeReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_STATUS_NODE) && z30itemStatusReached) {
			z30itemStatusReached = false;
		} else if (bibDescriptionDesired) {
			if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && z13authorReached) {
				z13authorReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && z30barcodeReached) {
				z30barcodeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && z13isbnReached) {
				z13isbnReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && z13titleReached) {
				z13titleReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && z13publisherReached) {
				z13publisherReached = false;
			}
		}
	}

	// END OF PARSING END ELEMENT

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (statusReached) {
			currentAlephItem.setCirculationStatus(AlephUtil.parseCirculationStatus(new String(ch, start, length)));
			statusReached = false;
		} else if (queueReached) {
			// Parse this: <queue>1 request(s) of 4 items</queue>
			String parsedHoldQueueLength = (new String(ch, start, length)).split(" ")[0];
			currentAlephItem.setHoldQueueLength(Integer.parseInt(parsedHoldQueueLength));
			queueReached = false;
		} else if (z30desrciptionReached) {
			currentAlephItem.setDescription(new String(ch, start, length));
			z30desrciptionReached = false;
		} else if (z30callNumberReached) {
			currentAlephItem.setCallNumber(new String(ch, start, length));
			z30callNumberReached = false;
		} else if (z30callNumber2TypeReached) {
			secondCallNoType = new String(ch, start, length);
			z30callNumber2TypeReached = false;
		} else if (z30callNumber2Reached) {

			// MZK's Aleph ILS has specific settings - when 9 is set as call no. type, then parse it instead of the first.
			// Note that NCIP doesn't allow transfer of two call numbers.

			if (secondCallNoType != null && !secondCallNoType.equalsIgnoreCase("9"))
				currentAlephItem.setCallNumber(new String(ch, start, length));
			else if (secondCallNoType == null)
				currentAlephItem.setCallNumber(new String(ch, start, length));

			z30callNumber2Reached = false;
		} else if (z30copyIdReached) {
			currentAlephItem.setCopyNumber(new String(ch, start, length));
			z30copyIdReached = false;
		} else if (z30materialReached) {
			MediumType mediumType = AlephUtil.detectMediumType(new String(ch, start, length), localizationDesired);
			currentAlephItem.setMediumType(mediumType);
			z30materialReached = false;
		} else if (z30subLibraryReached) {
			currentAlephItem.setLocation(new String(ch, start, length));
			z30subLibraryReached = false;
		} else if (z30collectionReached) {
			currentAlephItem.setCollection(new String(ch, start, length));
			z30collectionReached = false;
		} else if (z30itemStatusReached) {
			currentAlephItem.addItemRestriction(new String(ch, start, length));
			z30itemStatusReached = false;
		} else if (bibDescriptionDesired) {
			if (z13authorReached) {
				currentAlephItem.setAuthor(new String(ch, start, length));
				z13authorReached = false;
			} else if (z30barcodeReached) {
				currentAlephItem.setBarcode(new String(ch, start, length));
				z30barcodeReached = false;
			} else if (z13isbnReached) {
				currentAlephItem.setIsbn(new String(ch, start, length));
				z13isbnReached = false;
			} else if (z13titleReached) {
				currentAlephItem.setTitle(new String(ch, start, length));
				z13titleReached = false;
			} else if (z13publisherReached) {
				currentAlephItem.setPublisher(new String(ch, start, length));
				z13publisherReached = false;
			}
		}
	}

	// END OF PARSING CHARACTERS INSIDE ELEMENTS
}
