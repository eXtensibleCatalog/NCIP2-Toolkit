package org.extensiblecatalog.ncip.v2.aleph.restdlf.handlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.RequestType;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephRequestItemHandler extends DefaultHandler {

	// Required to build unique item id
	private String agencyId;
	private String bibDocNumber;
	private String itemSequence;
	private String itemDocNumber;
	private String bibLibrary;
	private boolean itemIdNotFound;

	private BibliographicDescription bibliographicDescription = new BibliographicDescription();
	private TimeZone localTimeZone = TimeZone.getTimeZone("ECT");;

	private String replyCode;
	private String replyText;
	private String noteValue;
	private String requestId;
	private String sequenceNumber;
	private String itemIdToLookForSeqNumber;

	private boolean errorReturned;
	private boolean deletable;

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;
	private boolean noteReached = false;
	private boolean requestNumberReached = false;
	private boolean parsingSequenceNumber = false;
	private boolean holdRequestFound = false;
	private boolean hourPlacedReached = false;
	private boolean earliestDateNeededReached = false;
	private boolean needBeforeDateReached = false;
	private boolean datePlacedReached = false;
	private boolean pickupLocationReached = false;
	private boolean pickupExpiryDateReached = false;
	private boolean reminderLevelReached = false;
	private boolean requestIdReached = false;
	private boolean requestTypeReached = false;
	private boolean pickupDateReached = false;
	private boolean statusReached = false;
	private boolean holdQueueLengthReached = false;
	private boolean itemSequenceReached = false;
	private boolean authorReached = false;
	private boolean isbnReached = false;
	private boolean titleReached = false;
	private boolean publisherReached = false;
	private boolean bibDocNoReached = false;
	private boolean itemDocNoReached = false;
	private boolean materialReached = false;
	private boolean agencyReached = false;

	private List<RequestedItem> requestedItems;
	private RequestedItem currentRequestedItem;

	/**
	 * This class was developed to detect deletability of an request and to parse output of successful deletion. <br />
	 * <br />
	 * There has been later added an functionality to parse more requests at once using "view=full" GET HTTP request.<br />
	 * Example URL: http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds?view=full
	 * 
	 */
	public AlephRequestItemHandler(String bibLibrary) {
		this.bibLibrary = bibLibrary;
	}

	/**
	 * Sets the parser to parse sequence number of requested item matching supplied String. <br/>
	 * Once is appropriate sequence number found, this functionality is turned off.
	 * 
	 * @return AlephRequestItemHandler
	 */
	public AlephRequestItemHandler parseSequenceNumber(String itemId) {
		itemIdToLookForSeqNumber = itemId;
		parsingSequenceNumber = true;
		return this;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!parsingSequenceNumber) {
			if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
				replyCodeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE)) {
				replyTextReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE)) {
				noteReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
				requestNumberReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
				currentRequestedItem = new RequestedItem();
				if (requestedItems == null)
					requestedItems = new ArrayList<RequestedItem>();
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE)) {
				datePlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE)) {
				hourPlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE)) {
				earliestDateNeededReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE)) {
				needBeforeDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_ITEM_SEQUENCE_NODE)) {
				itemSequenceReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.QUEUE_NODE)) {
				holdQueueLengthReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE)) {
				pickupLocationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
				pickupExpiryDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_RECALL_TYPE_NODE)) {
				reminderLevelReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
				requestIdReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PRIORITY_NODE)) {
				requestTypeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
				pickupDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
				statusReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE)) {
				authorReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE)) {
				isbnReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE)) {
				titleReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE)) {
				publisherReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
				itemDocNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE)) {
				bibDocNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
				agencyReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE)) {
				materialReached = true;
			}
		} else { // parsing sequence number from e.g. http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds
			if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
				if (link.indexOf(itemIdToLookForSeqNumber) > -1) {
					holdRequestFound = true;
					// Substring last 4 characters from link - this should be sequence number
					// E.g. <hold-request delete="Y" href="http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds/MZK500013118150000200001"/>
					sequenceNumber = link.substring(link.length() - AlephConstants.SEQ_NUMBER_LENGTH);

					String deleteAttr = attributes.getValue(AlephConstants.DELETE_NODE_ATTR);
					if (deleteAttr.equalsIgnoreCase(AlephConstants.YES)) {
						deletable = true;
					} else
						deletable = false;
				}
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!parsingSequenceNumber) {
			if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE) && replyCodeReached) {
				replyCodeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE) && replyTextReached) {
				replyTextReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && noteReached) {
				noteReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && requestNumberReached) {
				requestNumberReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
				if (!itemIdNotFound) {
					ItemId itemId = new ItemId();
					itemId.setItemIdentifierValue(bibLibrary + bibDocNumber.trim() + "-" + agencyId.trim() + itemDocNumber.trim() + itemSequence);
					itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
					currentRequestedItem.setItemId(itemId);
				}
				currentRequestedItem.setBibliographicDescription(bibliographicDescription);
				requestedItems.add(currentRequestedItem);
				bibliographicDescription = new BibliographicDescription();
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE) && datePlacedReached) {
				datePlacedReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE) && hourPlacedReached) {
				hourPlacedReached = false;
				itemIdNotFound = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE) && earliestDateNeededReached) {
				earliestDateNeededReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE) && needBeforeDateReached) {
				needBeforeDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_ITEM_SEQUENCE_NODE) && itemSequenceReached) {
				itemSequenceReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.QUEUE_NODE) && holdQueueLengthReached) {
				holdQueueLengthReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE) && pickupLocationReached) {
				pickupLocationReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE) && pickupExpiryDateReached) {
				pickupExpiryDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_RECALL_TYPE_NODE) && reminderLevelReached) {
				reminderLevelReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && requestIdReached) {
				requestIdReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PRIORITY_NODE) && requestTypeReached) {
				requestTypeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && pickupDateReached) {
				pickupDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && statusReached) {
				statusReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && authorReached) {
				authorReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && isbnReached) {
				isbnReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && titleReached) {
				titleReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && publisherReached) {
				publisherReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && itemDocNoReached) {
				itemIdNotFound = true;
				itemDocNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE) && bibDocNoReached) {
				itemIdNotFound = true;
				bibDocNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE) && agencyReached) {
				itemIdNotFound = true;
				agencyReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && materialReached) {
				materialReached = false;
			}
		} else {
			if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUESTS_NODE)) {
				parsingSequenceNumber = false;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (!parsingSequenceNumber) {
			if (replyCodeReached) {
				replyCode = new String(ch, start, length);
				if (!replyCode.equalsIgnoreCase("0000"))
					errorReturned = true;
				else
					errorReturned = false;
				replyCodeReached = false;
			} else if (replyTextReached) {
				replyText = new String(ch, start, length);
				replyTextReached = false;
			} else if (noteReached) {
				noteValue = new String(ch, start, length);
				noteReached = false;
			} else if (requestNumberReached) {
				requestId = new String(ch, start, length);
				requestNumberReached = false;
			} else if (datePlacedReached) {
				String datePlacedParsed = new String(ch, start, length);
				GregorianCalendar datePlaced = AlephUtil.parseGregorianCalendarFromAlephDate(datePlacedParsed);

				currentRequestedItem.setDatePlaced(datePlaced);

				datePlacedReached = false;
			} else if (hourPlacedReached) {
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
				hourPlacedReached = false;
			} else if (earliestDateNeededReached) {
				String earliestDateNeededParsed = new String(ch, start, length);
				GregorianCalendar earliestDateNeeded = AlephUtil.parseGregorianCalendarFromAlephDate(earliestDateNeededParsed);

				currentRequestedItem.setEarliestDateNeeded(earliestDateNeeded);

				earliestDateNeededReached = false;
			} else if (needBeforeDateReached) {
				String needBeforeDateParsed = new String(ch, start, length);
				GregorianCalendar needBeforeDate = AlephUtil.parseGregorianCalendarFromAlephDate(needBeforeDateParsed);

				currentRequestedItem.setNeedBeforeDate(needBeforeDate);

				needBeforeDateReached = false;
			} else if (holdQueueLengthReached) {
				// Parse this: <queue>1 request(s) of 4 items</queue>
				String parsedHoldQueueLength = (new String(ch, start, length)).split(" ")[0];
				currentRequestedItem.setHoldQueueLength(new BigDecimal(parsedHoldQueueLength));
				holdQueueLengthReached = false;
			} else if (pickupLocationReached) {
				PickupLocation pickupLocation = new PickupLocation(new String(ch, start, length));
				currentRequestedItem.setPickupLocation(pickupLocation);
				pickupLocationReached = false;
			} else if (pickupExpiryDateReached) {
				String pickupExpiryDateParsed = new String(ch, start, length);
				GregorianCalendar pickupExpiryDate = AlephUtil.parseGregorianCalendarFromAlephDate(pickupExpiryDateParsed);

				currentRequestedItem.setPickupExpiryDate(pickupExpiryDate);

				pickupExpiryDateReached = false;
			} else if (reminderLevelReached) {
				currentRequestedItem.setReminderLevel(new BigDecimal(new String(ch, start, length)));
				reminderLevelReached = false;
			} else if (requestIdReached) {
				RequestId requestId = new RequestId();
				requestId.setRequestIdentifierValue(new String(ch, start, length));
				currentRequestedItem.setRequestId(requestId);
				requestIdReached = false;
			} else if (requestTypeReached) {
				RequestType requestType = null;
				String parsedValue = new String(ch, start, length);
				if (parsedValue == "30") // TODO: Add remaining request types - better FIXME move to AlephUtil
					requestType = Version1RequestType.LOAN;
				else
					requestType = Version1RequestType.ESTIMATE; // FIXME: Put here better default value
				currentRequestedItem.setRequestType(requestType);
				requestTypeReached = false;
			} else if (pickupDateReached) {
				String pickupDateParsed = new String(ch, start, length);
				GregorianCalendar pickupDate = AlephUtil.parseGregorianCalendarFromAlephDate(pickupDateParsed);

				currentRequestedItem.setPickupDate(pickupDate);

				pickupDateReached = false;
			} else if (statusReached) {
				String parsedStatus = new String(ch, start, length);
				RequestStatusType requestStatusType;
				if (parsedStatus == "S")
					requestStatusType = Version1RequestStatusType.AVAILABLE_FOR_PICKUP;
				else
					requestStatusType = Version1RequestStatusType.IN_PROCESS;
				currentRequestedItem.setRequestStatusType(requestStatusType);
				statusReached = false;
			} else if (materialReached) {
				String mediumTypeParsed = new String(ch, start, length);
				MediumType mediumType = AlephUtil.detectMediumType(mediumTypeParsed);
				bibliographicDescription.setMediumType(mediumType);
				materialReached = false;
			} else if (authorReached) {
				bibliographicDescription.setAuthor(new String(ch, start, length));
				authorReached = false;
			} else if (isbnReached) {
				bibliographicDescription.setBibliographicLevel(null);
				isbnReached = false;
			} else if (titleReached) {
				bibliographicDescription.setTitle(new String(ch, start, length));
				titleReached = false;
			} else if (publisherReached) {
				bibliographicDescription.setPublisher(new String(ch, start, length));
				publisherReached = false;
			} else if (itemDocNoReached) {
				itemDocNumber = new String(ch, start, length);
				itemDocNoReached = false;
			} else if (bibDocNoReached) {
				String parsedBibId = new String(ch, start, length);
				bibDocNumber = parsedBibId;
				bibDocNoReached = false;
			} else if (itemSequenceReached) {
				itemSequence = new String(ch, start, length);
				itemSequenceReached = false;
			} else if (agencyReached) {
				agencyId = new String(ch, start, length);
				agencyReached = false;
			}
		} else { // nothing
		}
	}

	/**
	 * Returns true if there was an error.
	 * 
	 * @return
	 */
	public boolean returnedError() {
		return errorReturned;
	}

	/**
	 * Returns "note" element value.
	 * 
	 * @return
	 */
	public String getNoteValue() {
		return noteValue;
	}

	/**
	 * Returns "reply-text" element value.
	 * 
	 * @return
	 */
	public String getReplyText() {
		return replyText;
	}

	/**
	 * Returns "reply-code" element value in integer format.
	 * 
	 * @return
	 */
	public int getReplyCode() {
		return Integer.parseInt(replyCode);
	}

	/**
	 * Returns "z37-request-number" element value.
	 * 
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Returns sequence number from "hold-request" element's "href" attribute value.
	 * 
	 * @return
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * Returns true, if an hold request is deletable.
	 * 
	 * @return
	 */
	public boolean isDeletable() {
		return deletable;
	}

	public boolean requestWasFound() {
		return holdRequestFound;
	}

	public List<RequestedItem> getRequestedItems() {
		return requestedItems;
	}
}
