package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.RequestDetails;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephLookupRequestHandler extends DefaultHandler {

	private boolean parsingRequest = false;

	private AlephRequestItem alephRequestItem;
	private RequestDetails requestDetails;

	private ItemOptionalFields itemOptionalFields;

	private String itemIdToLookFor;
	private String requestLink;

	private TimeZone localTimeZone;

	private boolean z37statusReached = false;
	private boolean holdRequestFound = false;
	private boolean pickupDateReached = false;
	private boolean hourPlacedReached = false;
	private boolean datePlacedReached = false;
	private boolean requestNumberReached = false;
	private boolean needBeforeDateReached = false;
	private boolean pickupLocationReached = false;
	private boolean holdQueueLengthReached = false;
	private boolean pickupExpiryDateReached = false;
	private boolean earliestDateNeededReached = false;

	// Bibliographic description
	private boolean materialReached = false;
	private boolean statusReached = false;
	private boolean authorReached = false;
	private boolean isbnReached = false;
	private boolean titleReached = false;
	private boolean publisherReached = false;

	private boolean getBibDescription;
	private boolean getHoldQueueLength;
	private boolean getCircStatus;
	private boolean getItemUseRestrictionType;
	private boolean getLocation;
	private boolean getItemDescription;

	private BibliographicDescription bibliographicDescription;

	private boolean localizationDesired = false;

	private boolean barcodeReached;

	/**
	 * This class is used to work with single request. To parse more requests use {@link AlephLookupRequestsHandler}.
	 * 
	 * @param initData
	 * @param itemIdToLookFor
	 */
	public AlephLookupRequestHandler(LookupRequestInitiationData initData, String itemIdToLookFor) {

		alephRequestItem = new AlephRequestItem();

		setDesiredServices(initData);

		this.itemIdToLookFor = itemIdToLookFor;

		localTimeZone = TimeZone.getTimeZone("ECT");

		requestDetails = new RequestDetails();
		alephRequestItem.setRequestDetails(requestDetails);
	}

	private void setDesiredServices(LookupRequestInitiationData initData) {
		getBibDescription = initData.getBibliographicDescriptionDesired();
		getHoldQueueLength = initData.getHoldQueueLengthDesired();
		getCircStatus = initData.getCirculationStatusDesired();
		getItemUseRestrictionType = initData.getItemUseRestrictionTypeDesired();
		getLocation = initData.getLocationDesired();
		getItemDescription = initData.getItemDescriptionDesired();

		if (getBibDescription || getCircStatus || getHoldQueueLength || getItemDescription || getLocation) {
			itemOptionalFields = new ItemOptionalFields();

			bibliographicDescription = new BibliographicDescription();
			itemOptionalFields.setBibliographicDescription(bibliographicDescription);

			alephRequestItem.setItemOptionalFields(itemOptionalFields);
			
			if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null)
				localizationDesired = !initData.getInitiationHeader().getApplicationProfileType().getValue().isEmpty();
		}

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (parsingRequest) {

			// Request details
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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_SEQUENCE_NODE) && getHoldQueueLength) {
				holdQueueLengthReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE) && getLocation) {
				pickupLocationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
				pickupExpiryDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
				pickupDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
				z37statusReached = true;
			} else if (itemOptionalFields != null) {
				if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && getBibDescription) {
					statusReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && getBibDescription) {
					authorReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && getBibDescription) {
					isbnReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && getBibDescription) {
					titleReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && getBibDescription) {
					publisherReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && getBibDescription) {
					materialReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && getBibDescription) {
					barcodeReached = true;
				}
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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && pickupDateReached) {
				pickupDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && z37statusReached) {
				z37statusReached = false;
			} else if (itemOptionalFields != null) {
				if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && statusReached) {
					statusReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && authorReached) {
					authorReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && isbnReached) {
					isbnReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && titleReached) {
					titleReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && publisherReached) {
					publisherReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && materialReached) {
					materialReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && barcodeReached) {
					barcodeReached = false;
				}
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
				alephRequestItem.setRequestId(requestId);
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
						hourPlaced.setTime(AlephConstants.ALEPH_HOUR_FORMATTER.parse(hourPlacedParsed));
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
				alephRequestItem.setHoldQueueLength(new BigDecimal(new String(ch, start, length)));
				holdQueueLengthReached = false;
			} else if (pickupLocationReached) {
				PickupLocation pickupLocation = new PickupLocation(new String(ch, start, length));
				requestDetails.setPickupLocation(pickupLocation);
				pickupLocationReached = false;
			} else if (pickupExpiryDateReached) {
				GregorianCalendar pickupExpiryDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setPickupExpiryDate(pickupExpiryDate);
				pickupExpiryDateReached = false;
			} else if (pickupDateReached) {
				GregorianCalendar pickupDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				alephRequestItem.setHoldPickupDate(pickupDate);
				pickupDateReached = false;
			} else if (z37statusReached) {
				RequestStatusType requestStatusType = AlephUtil.parseRequestStatusTypeFromZ37StatusNode(new String(ch, start, length));
				requestDetails.setRequestStatusType(requestStatusType);
				z37statusReached = false;
			} else if (itemOptionalFields != null) {

				// Bibliographic Description here:
				if (statusReached) {
					String parsedStatus = new String(ch, start, length);
					bibliographicDescription.setSponsoringBody(parsedStatus);
					statusReached = false;
				} else if (materialReached) {
					MediumType mediumType = AlephUtil.detectMediumType(new String(ch, start, length), localizationDesired);
					bibliographicDescription.setMediumType(mediumType);
					materialReached = false;
				} else if (authorReached) {
					bibliographicDescription.setAuthor(new String(ch, start, length));
					authorReached = false;
				} else if (isbnReached) {
					BibliographicItemId bibId = AlephUtil.createBibliographicItemIdAsISBN(new String(ch, start, length));
					bibliographicDescription.setBibliographicItemIds(Arrays.asList(bibId));
					isbnReached = false;
				} else if (titleReached) {
					bibliographicDescription.setTitle(new String(ch, start, length));
					titleReached = false;
				} else if (publisherReached) {
					bibliographicDescription.setPublisher(new String(ch, start, length));
					publisherReached = false;
				} else if (barcodeReached) {
					bibliographicDescription.setComponentId(AlephUtil.createComponentIdAsAccessionNumber(new String(ch, start, length)));
					barcodeReached = false;
				}
			}

		}
	}

	public String getRequestLink() {
		// Call to this method means it will follow parsing detailed data about the request
		parsingRequest = true;
		return requestLink;
	}

	public boolean requestWasFound() {
		return holdRequestFound;
	}

	public AlephRequestItem getAlephRequestItem() {
		return alephRequestItem;
	}
}
