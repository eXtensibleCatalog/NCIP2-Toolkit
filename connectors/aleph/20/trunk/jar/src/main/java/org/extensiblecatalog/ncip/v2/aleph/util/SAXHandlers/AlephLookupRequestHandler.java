package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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

	private ItemOptionalFields itemOptionalFields;
	private ItemDescription itemDescription;
	private List<LocationNameInstance> locationNameInstances;

	private String itemIdToLookFor;
	private String requestLink;

	// Required to decide whether is set second call number the one we need
	private String secondCallNoType;

	private TimeZone localTimeZone;

	private boolean holdRequestFound = false;

	// Parsing one of two set call numbers
	private boolean callNoReached = false;
	private boolean secondCallNoTypeReached = false;
	private boolean secondCallNoReached = false;

	// Item description
	private boolean barcodeReached = false;
	private boolean z30descriptionReached = false;

	// Request element types
	private boolean z37statusReached = false;
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

	// Location
	private boolean subLibraryReached = false;
	private boolean collectionReached = false;

	// Item Use restriction type
	private boolean z30itemStatusReached = false;

	private boolean getBibDescription;
	private boolean getHoldQueueLength;
	private boolean getCircStatus;
	private boolean getItemUseRestrictionType;
	private boolean getLocation;
	private boolean getItemDescription;

	private BibliographicDescription bibliographicDescription;

	private boolean localizationDesired = false;

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
				requestNumberReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_DATE_NODE)) {
				datePlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_OPEN_HOUR_NODE)) {
				hourPlacedReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_DATE_NODE)) {
				earliestDateNeededReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_REQUEST_DATE_NODE)) {
				needBeforeDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE)) {
				pickupLocationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE)) {
				pickupExpiryDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE)) {
				pickupDateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE)) {
				z37statusReached = true;
			} else if (itemOptionalFields != null) {
				// Bibliographic description
				if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && (getBibDescription || getCircStatus)) {
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
				} else
				// Item description parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && getItemDescription) {
					callNoReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && getItemDescription) {
					secondCallNoReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && getItemDescription) {
					secondCallNoTypeReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && getItemDescription) {
					z30descriptionReached = true;
				} else
				// Location
				if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && getLocation) {
					collectionReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && getLocation) {
					subLibraryReached = true;
				} else
				// Hold Queue Length
				if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_SEQUENCE_NODE) && getHoldQueueLength) {
					holdQueueLengthReached = true;
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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_PICKUP_LOCATION_NODE) && pickupLocationReached) {
				pickupLocationReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_END_HOLD_DATE_NODE) && pickupExpiryDateReached) {
				pickupExpiryDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_DATE_NODE) && pickupDateReached) {
				pickupDateReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_STATUS_NODE) && z37statusReached) {
				z37statusReached = false;
			} else if (itemOptionalFields != null) {
				// Bibliographic description
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
				} else
				// Item description parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_NODE) && callNoReached) {
					callNoReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_NODE) && secondCallNoReached) {
					secondCallNoReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_CALL_NUMBER_2_TYPE_NODE) && secondCallNoTypeReached) {
					secondCallNoTypeReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DESCRIPTION_NODE) && z30descriptionReached) {
					z30descriptionReached = false;
				} else
				// Location parsing
				if (qName.equalsIgnoreCase(AlephConstants.Z30_COLLECTION_NODE) && collectionReached) {
					collectionReached = false;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z30_SUB_LIBRARY_NODE) && subLibraryReached) {
					subLibraryReached = false;
				} else
				// Hold Queue Length
				if (qName.equalsIgnoreCase(AlephConstants.Z37_HOLD_SEQUENCE_NODE) && holdQueueLengthReached) {
					holdQueueLengthReached = false;
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

					if (getBibDescription)
						bibliographicDescription.setSponsoringBody(parsedStatus);

					if (getCircStatus)
						itemOptionalFields.setCirculationStatus(AlephUtil.parseCirculationStatus(parsedStatus));

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
				} else

				// Item description here:
				if (callNoReached) {
					itemDescription.setCallNumber(new String(ch, start, length));
					callNoReached = false;
				} else if (secondCallNoTypeReached) {
					secondCallNoType = new String(ch, start, length);
					secondCallNoTypeReached = false;
				} else if (secondCallNoReached) {

					// MZK's Aleph ILS has specific settings - when 9 is set as call no. type, then parse it instead of the first.
					// Note that NCIP doesn't allow transfer of two call numbers.

					if (secondCallNoType != null && !secondCallNoType.equalsIgnoreCase("9"))
						itemDescription.setCallNumber(new String(ch, start, length));
					else if (secondCallNoType == null)
						itemDescription.setCallNumber(new String(ch, start, length));

					secondCallNoReached = false;
				} else if (z30descriptionReached) {
					itemDescription.setHoldingsInformation(AlephUtil.createHoldingsInformationUnscructured(new String(ch, start, length)));
					z30descriptionReached = false;
				} else

				// Location
				if (subLibraryReached) {
					// This will be LocationLevel 1
					locationNameInstances.add(AlephUtil.createLocationNameInstance(new String(ch, start, length), new BigDecimal(1)));
					subLibraryReached = false;
				} else if (collectionReached) {
					// This will be LocationLevel 2
					locationNameInstances.add(AlephUtil.createLocationNameInstance(new String(ch, start, length), new BigDecimal(2)));
					collectionReached = false;
				} else

				// Hold queue here:
				if (holdQueueLengthReached) {
					itemOptionalFields.setHoldQueueLength(new BigDecimal(new String(ch, start, length)));
					holdQueueLengthReached = false;
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
