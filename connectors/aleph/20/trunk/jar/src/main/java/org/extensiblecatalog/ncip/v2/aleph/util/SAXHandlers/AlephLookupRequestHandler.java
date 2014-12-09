package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.RequestDetails;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.RequestType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephLookupRequestHandler extends DefaultHandler {

	private SimpleDateFormat alephHourFormatter;
	private TimeZone localTimeZone;
	private AlephRequestItem requestItem;
	private RequestDetails requestDetails;
	private String itemIdToLookFor;
	private String requestLink;
	private boolean parsingRequest = false;
	private boolean z37statusReached = false;
	private boolean holdRequestFound = false;
	private boolean pickupDateReached = false;
	private boolean hourPlacedReached = false;
	private boolean datePlacedReached = false;
	private boolean requestTypeReached = false;
	private boolean requestNumberReached = false;
	private boolean needBeforeDateReached = false;
	private boolean pickupLocationReached = false;
	private boolean holdQueueLengthReached = false;
	private boolean pickupExpiryDateReached = false;
	private boolean earliestDateNeededReached = false;

	/**
	 * This class is used to work with single request. To parse more requests use {@link AlephLookupRequestsHandler}.
	 * 
	 * @param itemIdToLookFor
	 * @param requestItem
	 */
	public AlephLookupRequestHandler(String itemIdToLookFor, AlephRequestItem requestItem) {
		this.itemIdToLookFor = itemIdToLookFor;
		this.requestItem = requestItem;
		alephHourFormatter = new SimpleDateFormat("HHmm");
		localTimeZone = TimeZone.getTimeZone("ECT");

		requestDetails = new RequestDetails();
		requestItem.setRequestDetails(requestDetails);
	}

	public AlephLookupRequestHandler setParsingRequest() {
		parsingRequest = true;
		return this;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (parsingRequest) {
			if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
				requestNumberReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE)) {
				datePlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE)) {
				hourPlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE)) {
				earliestDateNeededReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE)) {
				needBeforeDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_SEQUENCE_NODE)) {
				holdQueueLengthReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE)) {
				pickupLocationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
				pickupExpiryDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PRIORITY_NODE)) {
				requestTypeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
				pickupDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
				z37statusReached = true;
			}
		} else {
			if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
				if (link.contains(itemIdToLookFor)) {
					requestLink = link;
					holdRequestFound = true;
				}
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (parsingRequest) {
			if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && requestNumberReached) {
				requestNumberReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE) && datePlacedReached) {
				datePlacedReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE) && hourPlacedReached) {
				hourPlacedReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE) && earliestDateNeededReached) {
				earliestDateNeededReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE) && needBeforeDateReached) {
				needBeforeDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_SEQUENCE_NODE) && holdQueueLengthReached) {
				holdQueueLengthReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE) && pickupLocationReached) {
				pickupLocationReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE) && pickupExpiryDateReached) {
				pickupExpiryDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PRIORITY_NODE) && requestTypeReached) {
				requestTypeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && pickupDateReached) {
				pickupDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && z37statusReached) {
				z37statusReached = false;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingRequest) {
			if (requestNumberReached) {
				String requestIdParsed = new String(ch, start, length);
				RequestId requestId = new RequestId();
				requestId.setRequestIdentifierValue(requestIdParsed);
				requestItem.setRequestId(requestId);
				requestNumberReached = false;
			} else if (datePlacedReached) {
				GregorianCalendar datePlaced = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setDatePlaced(datePlaced);
				datePlacedReached = false;
			} else if (hourPlacedReached) {
				String hourPlacedParsed = new String(ch, start, length);
				if (!hourPlacedParsed.equalsIgnoreCase("00000000")) {
					GregorianCalendar datePlaced = requestDetails.getDatePlaced();
					GregorianCalendar hourPlaced = new GregorianCalendar(localTimeZone);

					try {
						hourPlaced.setTime(alephHourFormatter.parse(hourPlacedParsed));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					datePlaced.add(Calendar.HOUR_OF_DAY, hourPlaced.get(Calendar.HOUR_OF_DAY));
					datePlaced.add(Calendar.MINUTE, hourPlaced.get(Calendar.MINUTE));

					// Note that this is not duplicate of the above "setDatePlaced", it just adds hours if any
					requestDetails.setDatePlaced(datePlaced);
				}
				hourPlacedReached = false;
			} else if (earliestDateNeededReached) {
				GregorianCalendar earliestDateNeeded = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setEarliestDateNeeded(earliestDateNeeded);
				earliestDateNeededReached = false;
			} else if (needBeforeDateReached) {
				GregorianCalendar needBeforeDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setNeedBeforeDate(needBeforeDate);
				needBeforeDateReached = false;
			} else if (holdQueueLengthReached) {
				requestItem.setHoldQueueLength(new BigDecimal(new String(ch, start, length)));
				holdQueueLengthReached = false;
			} else if (pickupLocationReached) {
				PickupLocation pickupLocation = new PickupLocation(new String(ch, start, length));
				requestDetails.setPickupLocation(pickupLocation);
				pickupLocationReached = false;
			} else if (pickupExpiryDateReached) {
				GregorianCalendar pickupExpiryDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setPickupExpiryDate(pickupExpiryDate);
				pickupExpiryDateReached = false;
			} else if (requestTypeReached) {
				RequestType requestType = AlephUtil.parseRequestTypeFromZ37PriorityNode(new String(ch, start, length));
				requestItem.setRequestType(requestType);
				requestTypeReached = false;
			} else if (pickupDateReached) {
				GregorianCalendar pickupDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestItem.setHoldPickupDate(pickupDate);
				pickupDateReached = false;
			} else if (z37statusReached) {
				RequestStatusType requestStatusType = AlephUtil.parseRequestStatusTypeFromZ37StatusNode(new String(ch, start, length));
				requestDetails.setRequestStatusType(requestStatusType);
				z37statusReached = false;
			}

		}
	}

	public String getRequestLink() {
		return requestLink;
	}

	public boolean requestWasFound() {
		return holdRequestFound;
	}
}
