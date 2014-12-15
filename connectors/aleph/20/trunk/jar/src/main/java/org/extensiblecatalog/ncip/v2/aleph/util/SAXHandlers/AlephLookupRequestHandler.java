package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.RequestDetails;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.Version1LocationType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephLookupRequestHandler extends DefaultHandler {

	private boolean parsingRequest = false;

	private AlephRequestItem alephRequestItem;
	private RequestDetails requestDetails;

	private BibliographicDescription bibliographicDescription;

	private ItemOptionalFields itemOptionalFields;
	private ItemDescription itemDescription;
	private List<LocationNameInstance> locationNameInstances;

	private String itemIdToLookFor;
	private String requestLink;

	// Required to decide whether is set second call number the one we need
	private String secondCallNoType;

	private boolean localizationDesired = false;

	private boolean holdRequestFound = false;

	// Parsing one of two set call numbers
	private boolean z30callNoReached = false;
	private boolean z30callNo2TypeReached = false;
	private boolean z30callNo2Reached = false;

	// Item description
	private boolean z30barcodeReached = false;
	private boolean z30descriptionReached = false;

	// Request element types
	private boolean z37statusReached = false;
	private boolean z37holdDateReached = false;
	private boolean z37openHourReached = false;
	private boolean z37openDateReached = false;
	private boolean z37requestNumberReached = false;
	private boolean z37endRequestDateReached = false;
	private boolean z37pickupLocationReached = false;
	private boolean z37endHoldDateReached = false;
	private boolean z37requestDateReached = false;

	// Bibliographic description
	private boolean z30materialReached = false;
	private boolean statusReached = false;
	private boolean z13authorReached = false;
	private boolean z13isbnReached = false;
	private boolean z13titleReached = false;
	private boolean z13publisherReached = false;

	// Location
	private boolean z30subLibraryReached = false;
	private boolean z30collectionReached = false;

	// Item Use restriction type
	private boolean z30itemStatusReached = false;

	private boolean getBibDescription;
	private boolean getHoldQueueLength;
	private boolean getCircStatus;
	private boolean getItemUseRestrictionType;
	private boolean getLocation;
	private boolean getItemDescription;

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

		if (getBibDescription || getCircStatus || getHoldQueueLength || getItemDescription || getLocation || getItemUseRestrictionType) {
			itemOptionalFields = new ItemOptionalFields();

			if (getBibDescription) {
				bibliographicDescription = new BibliographicDescription();
				itemOptionalFields.setBibliographicDescription(bibliographicDescription);
			}

			if (getItemDescription) {
				itemDescription = new ItemDescription();
				itemOptionalFields.setItemDescription(itemDescription);
			}

			if (getLocation) {
				Location location = new Location();
				LocationName locationName = new LocationName();

				locationNameInstances = new ArrayList<LocationNameInstance>();
				locationName.setLocationNameInstances(locationNameInstances);

				location.setLocationType(Version1LocationType.PERMANENT_LOCATION);
				location.setLocationName(locationName);
				itemOptionalFields.setLocations(Arrays.asList(location));
			}
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
				z37requestNumberReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE)) {
				z37openDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE)) {
				z37openHourReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE)) {
				z37requestDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE)) {
				z37endRequestDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE)) {
				z37pickupLocationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
				z37endHoldDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
				z37holdDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
				z37statusReached = true;
			} else if (itemOptionalFields != null) {
				// Bibliographic description
				if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && (getBibDescription || getCircStatus)) {
					statusReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && getBibDescription) {
					z13authorReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && getBibDescription) {
					z13isbnReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && getBibDescription) {
					z13titleReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && getBibDescription) {
					z13publisherReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && getBibDescription) {
					z30materialReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && getBibDescription) {
					z30barcodeReached = true;
				} else
				// Item description parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && getItemDescription) {
					z30callNoReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && getItemDescription) {
					z30callNo2Reached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && getItemDescription) {
					z30callNo2TypeReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && getItemDescription) {
					z30descriptionReached = true;
				} else
				// Location
				if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && getLocation) {
					z30collectionReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && getLocation) {
					z30subLibraryReached = true;
				} else
				// Item Use Restriction Type
				if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_STATUS_NODE) && getItemUseRestrictionType) {
					z30itemStatusReached = true;
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
			if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && z37requestNumberReached) {
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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && z37holdDateReached) {
				z37holdDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && z37statusReached) {
				z37statusReached = false;
			} else if (itemOptionalFields != null) {
				// Bibliographic description
				if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && statusReached) {
					statusReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && z13authorReached) {
					z13authorReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_ISBN_NODE) && z13isbnReached) {
					z13isbnReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && z13titleReached) {
					z13titleReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && z13publisherReached) {
					z13publisherReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && z30materialReached) {
					z30materialReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_BARCODE) && z30barcodeReached) {
					z30barcodeReached = false;
				} else
				// Item description parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && z30callNoReached) {
					z30callNoReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && z30callNo2Reached) {
					z30callNo2Reached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && z30callNo2TypeReached) {
					z30callNo2TypeReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && z30descriptionReached) {
					z30descriptionReached = false;
				} else
				// Location parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && z30collectionReached) {
					z30collectionReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && z30subLibraryReached) {
					z30subLibraryReached = false;
				} else
				// Item Use Restriction Type
				if (qName.equalsIgnoreCase(AlephConstants.Z30_ITEM_STATUS_NODE) && z30itemStatusReached) {
					z30itemStatusReached = false;
				}
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingRequest) {
			if (z37requestNumberReached) {
				String requestIdParsed = new String(ch, start, length);
				RequestId requestId = new RequestId();
				requestId.setRequestIdentifierValue(requestIdParsed);
				alephRequestItem.setRequestId(requestId);
				z37requestNumberReached = false;
			} else if (z37openDateReached) {
				GregorianCalendar datePlaced = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setDatePlaced(datePlaced);
				z37openDateReached = false;
			} else if (z37openHourReached) {
				String hourPlacedParsed = new String(ch, start, length);
				if (!hourPlacedParsed.equalsIgnoreCase("00000000")) {
					GregorianCalendar datePlaced = requestDetails.getDatePlaced();

					int hours = Integer.parseInt(hourPlacedParsed.substring(0, 2));
					int minutes = Integer.parseInt(hourPlacedParsed.substring(2));

					datePlaced.add(Calendar.HOUR_OF_DAY, hours);
					datePlaced.add(Calendar.MINUTE, minutes);

					// Note that this is not duplicate of the above "setDatePlaced", it just adds hours if any
					requestDetails.setDatePlaced(datePlaced);
				}
				z37openHourReached = false;
			} else if (z37requestDateReached) {
				GregorianCalendar earliestDateNeeded = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setEarliestDateNeeded(earliestDateNeeded);
				z37requestDateReached = false;
			} else if (z37endRequestDateReached) {
				GregorianCalendar needBeforeDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setNeedBeforeDate(needBeforeDate);
				z37endRequestDateReached = false;
			} else if (z37pickupLocationReached) {
				PickupLocation pickupLocation = new PickupLocation(new String(ch, start, length));
				requestDetails.setPickupLocation(pickupLocation);
				z37pickupLocationReached = false;
			} else if (z37endHoldDateReached) {
				GregorianCalendar pickupExpiryDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				requestDetails.setPickupExpiryDate(pickupExpiryDate);
				z37endHoldDateReached = false;
			} else if (z37holdDateReached) {
				GregorianCalendar pickupDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
				alephRequestItem.setHoldPickupDate(pickupDate);
				z37holdDateReached = false;
			} else if (z37statusReached) {
				RequestStatusType requestStatusType = AlephUtil.parseRequestStatusTypeFromZ37StatusNode(new String(ch, start, length));
				requestDetails.setRequestStatusType(requestStatusType);
				z37statusReached = false;
			} else if (itemOptionalFields != null) {

				// Bibliographic Description here:
				if (statusReached) {
					// Here is also possibly specified position in queue
					String parsedStatus = new String(ch, start, length);

					if (getHoldQueueLength) {
						int parsedHoldQueue = AlephUtil.parseHoldQueueLengthFromStatusNode(parsedStatus);
						if (parsedHoldQueue != -1)
							itemOptionalFields.setHoldQueueLength(new BigDecimal(parsedHoldQueue));
					}
					if (getCircStatus)
						itemOptionalFields.setCirculationStatus(AlephUtil.parseCirculationStatus(parsedStatus));

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
				} else if (z30barcodeReached) {
					bibliographicDescription.setComponentId(AlephUtil.createComponentIdAsAccessionNumber(new String(ch, start, length)));
					z30barcodeReached = false;
				} else

				// Item description here:
				if (z30callNoReached) {
					itemDescription.setCallNumber(new String(ch, start, length));
					z30callNoReached = false;
				} else if (z30callNo2TypeReached) {
					secondCallNoType = new String(ch, start, length);
					z30callNo2TypeReached = false;
				} else if (z30callNo2Reached) {

					// MZK's Aleph ILS has specific settings - when 9 is set as call no. type, then parse it instead of the first.
					// Note that NCIP doesn't allow transfer of two call numbers.

					if (secondCallNoType != null && !secondCallNoType.equalsIgnoreCase("9"))
						itemDescription.setCallNumber(new String(ch, start, length));
					else if (secondCallNoType == null)
						itemDescription.setCallNumber(new String(ch, start, length));

					z30callNo2Reached = false;
				} else if (z30descriptionReached) {
					itemDescription.setHoldingsInformation(AlephUtil.createHoldingsInformationUnscructured(new String(ch, start, length)));
					z30descriptionReached = false;
				} else

				// Location
				if (z30subLibraryReached) {
					// This will be LocationLevel 1
					locationNameInstances.add(AlephUtil.createLocationNameInstance(new String(ch, start, length), new BigDecimal(1)));
					z30subLibraryReached = false;
				} else if (z30collectionReached) {
					// This will be LocationLevel 2
					locationNameInstances.add(AlephUtil.createLocationNameInstance(new String(ch, start, length), new BigDecimal(2)));
					z30collectionReached = false;
				} else

				// Item Use Restriction Type
				if (z30itemStatusReached) {
					itemOptionalFields.setItemUseRestrictionTypes(Arrays.asList(AlephUtil.parseItemUseRestrictionType(new String(ch, start, length))));
					z30itemStatusReached = false;
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
