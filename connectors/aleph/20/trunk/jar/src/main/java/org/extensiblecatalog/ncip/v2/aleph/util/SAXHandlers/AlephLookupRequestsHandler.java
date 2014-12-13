package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephLookupRequestsHandler extends DefaultHandler {

	private TimeZone localTimeZone = TimeZone.getTimeZone("ECT");

	private BibliographicDescription bibliographicDescription;

	private boolean localizationDesired = false;

	private List<RequestedItem> requestedItems;
	private RequestedItem currentRequestedItem;

	// Required to build unique item id
	private String bibDocNumber;
	private String itemSequence;
	private String itemDocNumber;
	private LocalConfig localConfig;
	private boolean itemFullIdFound;

	// Dates
	private boolean z37requestDateReached = false;
	private boolean z37endHoldDateReached = false;
	private boolean z37openDateReached = false;
	private boolean z37endRequestDateReached = false;
	private boolean z37openHourReached = false;
	private boolean z37holdDateReached = false;

	// Request specifics
	private boolean z37recallTypeReached = false;
	private boolean z37statusReached = false;

	// Item identifiers
	private boolean z13docNumberReached = false;
	private boolean z30docNumberReached = false;
	private boolean z37itemSequenceReached = false;

	// Bibliographic description
	private boolean z30materialReached = false;
	private boolean statusReached = false;
	private boolean z13authorReached = false;
	private boolean z13isbnReached = false;
	private boolean z13titleReached = false;
	private boolean z13publisherReached = false;

	// Item optional fields etc.
	private boolean z37pickupLocationReached = false;

	private boolean z37requestNumberReached = false;

	public AlephLookupRequestsHandler(LocalConfig localConfig) {
		this.localConfig = localConfig;
		requestedItems = new ArrayList<RequestedItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
			currentRequestedItem = new RequestedItem();

			bibliographicDescription = new BibliographicDescription();
			itemFullIdFound = true;

		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
			z37requestNumberReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE)) {
			z37openDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE)) {
			z37openHourReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE)) {
			z37requestDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE)) {
			z37endRequestDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_ITEM_SEQUENCE_NODE)) {
			z37itemSequenceReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE)) {
			z37pickupLocationReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
			z37endHoldDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_RECALL_TYPE_NODE)) {
			z37recallTypeReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
			z37holdDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
			z37statusReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE)) {
			statusReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE)) {
			z13authorReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE)) {
			z13isbnReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE)) {
			z13titleReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE)) {
			z13publisherReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
			z30docNumberReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE)) {
			z13docNumberReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE)) {
			z30materialReached = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
			if (itemFullIdFound) {
				String itemIdVal = AlephUtil.buildAlephItemId(localConfig, bibDocNumber, itemDocNumber, itemSequence);

				ItemId itemId = new ItemId();
				itemId.setItemIdentifierValue(itemIdVal);
				itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);

				currentRequestedItem.setItemId(itemId);
			}
			currentRequestedItem.setBibliographicDescription(bibliographicDescription);
			requestedItems.add(currentRequestedItem);
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && z37requestNumberReached) {
			z37requestNumberReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE) && z37openDateReached) {
			z37openDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE) && z37openHourReached) {
			z37openHourReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE) && z37requestDateReached) {
			z37requestDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE) && z37endRequestDateReached) {
			z37endRequestDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE) && z37pickupLocationReached) {
			z37pickupLocationReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE) && z37endHoldDateReached) {
			z37endHoldDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_RECALL_TYPE_NODE) && z37recallTypeReached) {
			z37recallTypeReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && z37holdDateReached) {
			z37holdDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && z37statusReached) {
			z37statusReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && statusReached) {
			statusReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && z13authorReached) {
			z13authorReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && z13isbnReached) {
			z13isbnReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && z13titleReached) {
			z13titleReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && z13publisherReached) {
			z13publisherReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && z30docNumberReached) {
			itemFullIdFound = false;
			z30docNumberReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE) && z13docNumberReached) {
			itemFullIdFound = false;
			z13docNumberReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_ITEM_SEQUENCE_NODE) && z37itemSequenceReached) {
			itemFullIdFound = false;
			z37itemSequenceReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && z30materialReached) {
			z30materialReached = false;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (z37requestNumberReached) {
			RequestId requestId = new RequestId();
			requestId.setRequestIdentifierValue(new String(ch, start, length));
			currentRequestedItem.setRequestId(requestId);
			z37requestNumberReached = false;
		} else if (z37openDateReached) {
			GregorianCalendar datePlaced = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentRequestedItem.setDatePlaced(datePlaced);
			z37openDateReached = false;
		} else if (z37openHourReached) {
			String hourPlacedParsed = new String(ch, start, length);
			if (!hourPlacedParsed.equalsIgnoreCase("00000000")) {
				GregorianCalendar datePlaced = currentRequestedItem.getDatePlaced();
				GregorianCalendar hourPlaced = new GregorianCalendar(localTimeZone);

				try {
					hourPlaced.setTime(AlephConstants.ALEPH_HOUR_FORMATTER.parse(hourPlacedParsed));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				datePlaced.add(Calendar.HOUR_OF_DAY, hourPlaced.get(Calendar.HOUR_OF_DAY) - 1);
				datePlaced.add(Calendar.MINUTE, hourPlaced.get(Calendar.MINUTE));

				currentRequestedItem.setDatePlaced(datePlaced);
			}
			z37openHourReached = false;
		} else if (z37requestDateReached) {
			GregorianCalendar earliestDateNeeded = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentRequestedItem.setEarliestDateNeeded(earliestDateNeeded);
			z37requestDateReached = false;
		} else if (z37endHoldDateReached) {
			GregorianCalendar needBeforeDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentRequestedItem.setNeedBeforeDate(needBeforeDate);
			z37endHoldDateReached = false;
		} else if (z37pickupLocationReached) {
			currentRequestedItem.setPickupLocation(new PickupLocation(new String(ch, start, length)));
			z37pickupLocationReached = false;
		} else if (z37endRequestDateReached) {
			GregorianCalendar pickupExpiryDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentRequestedItem.setPickupExpiryDate(pickupExpiryDate);
			z37endRequestDateReached = false;
		} else if (z37recallTypeReached) {
			currentRequestedItem.setReminderLevel(new BigDecimal(new String(ch, start, length)));
			z37recallTypeReached = false;
		} else if (z37holdDateReached) {
			GregorianCalendar pickupDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentRequestedItem.setPickupDate(pickupDate);
			z37holdDateReached = false;
		} else if (z37statusReached) {
			RequestStatusType requestStatusType = AlephUtil.parseRequestStatusTypeFromZ37StatusNode(new String(ch, start, length));
			currentRequestedItem.setRequestStatusType(requestStatusType);
			currentRequestedItem.setRequestType(Version1RequestType.HOLD);
			z37statusReached = false;
		} else if (statusReached) {
			String parsedStatus = new String(ch, start, length);
			bibliographicDescription.setSponsoringBody(parsedStatus);

			int parsedHoldQueue = AlephUtil.parseHoldQueueLengthFromStatusNode(parsedStatus);
			if (parsedHoldQueue != -1)
				currentRequestedItem.setHoldQueueLength(new BigDecimal(parsedHoldQueue));

			statusReached = false;
		} else if (z30materialReached) {
			MediumType mediumType = AlephUtil.detectMediumType(new String(ch, start, length), localizationDesired);
			bibliographicDescription.setMediumType(mediumType);
			z30materialReached = false;
		} else if (z13authorReached) {
			bibliographicDescription.setAuthor(new String(ch, start, length));
			z13authorReached = false;
		} else if (z13isbnReached) {
			BibliographicItemId bibId = AlephUtil.createBibliographicItemIdAsISBN(new String(ch, start, length));
			bibliographicDescription.setBibliographicItemIds(Arrays.asList(bibId));
			z13isbnReached = false;
		} else if (z13titleReached) {
			bibliographicDescription.setTitle(new String(ch, start, length));
			z13titleReached = false;
		} else if (z13publisherReached) {
			bibliographicDescription.setPublisher(new String(ch, start, length));
			z13publisherReached = false;
		} else if (z30docNumberReached) {
			itemDocNumber = new String(ch, start, length);
			z30docNumberReached = false;
		} else if (z13docNumberReached) {
			bibDocNumber = new String(ch, start, length);
			z13docNumberReached = false;
		} else if (z37itemSequenceReached) {
			itemSequence = new String(ch, start, length);
			z37itemSequenceReached = false;
		}
	}

	public List<RequestedItem> getRequestedItems() {
		return requestedItems;
	}

	public void setLocalizationDesired(boolean localizationDesired) {
		this.localizationDesired = localizationDesired;
	}
}
