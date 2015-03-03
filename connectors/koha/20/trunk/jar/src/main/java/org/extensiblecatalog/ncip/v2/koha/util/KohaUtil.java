package org.extensiblecatalog.ncip.v2.koha.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.koha.item.KohaItem;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BlockOrTrap;
import org.extensiblecatalog.ncip.v2.service.BlockOrTrapType;
import org.extensiblecatalog.ncip.v2.service.CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.ComponentId;
import org.extensiblecatalog.ncip.v2.service.ComponentIdentifierType;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.StructuredAddress;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicRecordIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.Version1ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Version1LocationType;
import org.extensiblecatalog.ncip.v2.service.Version1MediumType;
import org.extensiblecatalog.ncip.v2.service.Version1PhysicalAddressType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestStatusType;
import org.xml.sax.SAXException;

public class KohaUtil {

	public static AgencyId createAgencyId(String agencyId) {
		return new AgencyId(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE, agencyId);
	}

	public static BibliographicDescription parseBibliographicDescription(KohaItem kohaItem) {
		return parseBibliographicDescription(kohaItem, false);
	}

	public static BibliographicDescription parseBibliographicDescription(KohaItem kohaItem, boolean includeComponentIdWithBarcode) {

		BibliographicDescription bibliographicDescription = new BibliographicDescription();

		if (kohaItem.getAuthor() != null)
			bibliographicDescription.setAuthor(kohaItem.getAuthor());

		if (kohaItem.getMediumType() != null)
			bibliographicDescription.setMediumType(kohaItem.getMediumType());

		if (kohaItem.getPublisher() != null)
			bibliographicDescription.setPublisher(kohaItem.getPublisher());

		if (kohaItem.getSeries() != null)
			bibliographicDescription.setSeriesTitleNumber(kohaItem.getSeries());

		if (kohaItem.getTitle() != null)
			bibliographicDescription.setTitle(kohaItem.getTitle());

		if (includeComponentIdWithBarcode && kohaItem.getBarcodeValue() != null) {
			ComponentId componentId = createComponentIdAsAccessionNumber(kohaItem.getBarcodeValue());
			bibliographicDescription.setComponentId(componentId);
		}

		if (kohaItem.getIsbn() != null)
			bibliographicDescription.setBibliographicItemIds(Arrays.asList(kohaItem.getIsbn()));

		// FIXME: NCIP JAXB translator cuts off all BibliographicRecordIds - that's why we're using componentId to transfer barcode
		return bibliographicDescription;
	}

	public static ComponentId createComponentIdAsAccessionNumber(String barcodeValue) {
		ComponentId componentId = new ComponentId();
		componentId.setComponentIdentifierType(new ComponentIdentifierType(Version1BibliographicRecordIdentifierCode.VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE,
				Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER.getValue()));
		componentId.setComponentIdentifier(barcodeValue);
		return componentId;
	}

	public static Location parseLocation(KohaItem kohaItem) {
		Location location = new Location();
		kohaItem.getLocation();
		LocationNameInstance locationNameInstance = new LocationNameInstance();

		locationNameInstance.setLocationNameValue(kohaItem.getLocation());
		// TODO: more to come from requirement for level
		locationNameInstance.setLocationNameLevel(new BigDecimal("1"));// temporarily set to 1.

		List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
		locationNameInstances.add(locationNameInstance);

		LocationName locationName = new LocationName();
		locationName.setLocationNameInstances(locationNameInstances);

		location = new Location();
		location.setLocationName(locationName);
		location.setLocationType(Version1LocationType.PERMANENT_LOCATION);
		return location;
	}

	public static ItemOptionalFields parseItemOptionalFields(KohaItem kohaItem) {
		ItemOptionalFields iof = new ItemOptionalFields();

		if (kohaItem.getCirculationStatus() != null)
			iof.setCirculationStatus(kohaItem.getCirculationStatus());

		if (kohaItem.getHoldQueueLength() >= 0)
			iof.setHoldQueueLength(new BigDecimal(kohaItem.getHoldQueueLength()));

		if (kohaItem.getElectronicResource() != null)
			iof.setElectronicResource(kohaItem.getElectronicResource());

		if (kohaItem.getCallNumber() != null || kohaItem.getCopyNumber() != null || kohaItem.getDescription() != null) {
			ItemDescription description = new ItemDescription();

			if (kohaItem.getCallNumber() != null)
				description.setCallNumber(kohaItem.getCallNumber());

			if (kohaItem.getCopyNumber() != null)
				description.setCopyNumber(kohaItem.getCopyNumber());

			if (kohaItem.getDescription() != null)
				description.setHoldingsInformation(kohaItem.getDescription());

			if (kohaItem.getNumberOfPieces() != null)
				description.setNumberOfPieces(kohaItem.getNumberOfPieces());

			iof.setItemDescription(description);
		}

		if (kohaItem.getItemUseRestrictionTypes().size() > 0)
			iof.setItemUseRestrictionTypes(kohaItem.getItemUseRestrictionTypes());

		if (kohaItem.getLocation() != null || kohaItem.getCollection() != null) {
			Location location = new Location();

			LocationName locationName = new LocationName();

			List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();

			if (kohaItem.getLocation() != null)
				locationNameInstances.add(createLocationNameInstance(kohaItem.getLocation(), new BigDecimal(1)));

			if (kohaItem.getCollection() != null)
				locationNameInstances.add(createLocationNameInstance(kohaItem.getCollection(), new BigDecimal(2)));

			locationName.setLocationNameInstances(locationNameInstances);

			location.setLocationName(locationName);
			location.setLocationType(Version1LocationType.PERMANENT_LOCATION);

			iof.setLocations(Arrays.asList(location));
		}

		if (kohaItem.getBarcode() != null)
			iof.setBibliographicDescription(kohaItem.getBarcode());

		return iof;
	}

	public static LocationNameInstance createLocationNameInstance(String locationNameValue, BigDecimal locationNameLevel) {
		LocationNameInstance locationNameInstance = new LocationNameInstance();
		locationNameInstance.setLocationNameValue(locationNameValue);
		locationNameInstance.setLocationNameLevel(locationNameLevel);
		return locationNameInstance;
	}

	public static boolean inDaylightTime() {
		return TimeZone.getDefault().inDaylightTime(new Date());
	}

	public static String convertToKohaDate(GregorianCalendar gregorianCalendar) {
		if (gregorianCalendar == null)
			return null;
		// We need: 20141231

		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH) + 1);
		if (month.length() < 2)
			month = "0" + month;
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if (day.length() < 2)
			day = "0" + day;
		return Integer.toString(gregorianCalendar.get(Calendar.YEAR)) + month + day;
	}

	public static MediumType detectMediumType(String mediumTypeParsed) {
		return detectMediumType(mediumTypeParsed, false);
	}

	public static MediumType detectMediumType(String mediumTypeParsed, boolean localizationDesired) {
		MediumType mediumType = null;

		// TODO: Find out Koha non-localized values & change these KohaLocalization.SOMETHING to KohaConstants.SOMETHING ...

		if (localizationDesired) {
			mediumType = new MediumType("localized", mediumTypeParsed);
		} else {
			if (mediumTypeParsed.matches(KohaLocalization.BOOK + "|" + KohaLocalization.GRAPHICS + "|" + KohaLocalization.MAP))
				mediumType = Version1MediumType.BOOK;
			else if (mediumTypeParsed.matches(KohaLocalization.MAGAZINE1 + "|" + KohaLocalization.MAGAZINE2))
				mediumType = Version1MediumType.MAGAZINE;
			else if (mediumTypeParsed.equalsIgnoreCase(KohaLocalization.COMPACT_DISC))
				mediumType = Version1MediumType.CD_ROM;
			else if (mediumTypeParsed.equalsIgnoreCase(KohaLocalization.AUDIO_TAPE))
				mediumType = Version1MediumType.AUDIO_TAPE;
			else {
				mediumType = new MediumType("localized", mediumTypeParsed);
			}
		}
		return mediumType;
	}

	public static BlockOrTrap parseBlockOrTrap(String parsedBlock) {
		BlockOrTrap blockOrTrap = new BlockOrTrap();

		blockOrTrap.setBlockOrTrapType(new BlockOrTrapType("http://www.niso.org/ncip/v1_0/imp1/schemes/blockortraptype/blockortraptype.scm", parsedBlock));

		return blockOrTrap;
	}

	/**
	 * Tries to convert date string parsed from koha response to GregorianCalendar format.<br />
	 * Throws SAXException if not successful.
	 * 
	 * @param kohaDateParsed
	 * @return gregorianCalendarDate
	 * @throws SAXException
	 */
	public static GregorianCalendar parseGregorianCalendarFromKohaDate(String kohaDateParsed) throws SAXException {
		if (!kohaDateParsed.equalsIgnoreCase("0000-00-00")) {
			GregorianCalendar gregorianCalendarDate = new GregorianCalendar(TimeZone.getDefault());

			try {
				gregorianCalendarDate.setTime(KohaConstants.KOHA_DATE_FORMATTER.parse(kohaDateParsed));
				if (inDaylightTime())
					gregorianCalendarDate.add(Calendar.HOUR_OF_DAY, 3);
				else
					gregorianCalendarDate.add(Calendar.HOUR_OF_DAY, 1);
			} catch (ParseException e) {
				throw new SAXException(e);
			}

			return gregorianCalendarDate;
		} else
			return null;
	}

	public static ResponseHeader reverseInitiationHeader(NCIPInitiationData initData) {

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		ResponseHeader responseHeader = null;

		if (initiationHeader != null) {
			responseHeader = new ResponseHeader();

			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				// Reverse From/To AgencyId because of the request was processed (return to initiator)
				ToAgencyId toAgencyId = new ToAgencyId();
				toAgencyId.setAgencyIds(initiationHeader.getFromAgencyId().getAgencyIds());

				FromAgencyId fromAgencyId = new FromAgencyId();
				fromAgencyId.setAgencyIds(initiationHeader.getToAgencyId().getAgencyIds());

				responseHeader.setFromAgencyId(fromAgencyId);
				responseHeader.setToAgencyId(toAgencyId);
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
		}
		return responseHeader;
	}

	/**
	 * Marks expired tokens as expired.<br>
	 * Note that these expired tokens are removed immediately after new token is created.<br>
	 * The purpose of not removing those earlier is to let user know of the token expiration.
	 * 
	 * @param tokens
	 * @param tokenExpirationTimeInSeconds
	 */
	public static void markExpiredTokens(HashMap<String, ItemToken> tokens, int tokenExpirationTimeInSeconds) {
		long theTime = new Date().getTime();

		for (ItemToken token : tokens.values()) {
			if (token.getTimeCreated() + tokenExpirationTimeInSeconds * 1000 < theTime) {
				token.setExpired();
			}
		}
	}

	/**
	 * Removes expired tokens from memory hashmap.
	 * 
	 * @param tokens
	 */
	public static void purgeExpiredTokens(HashMap<String, ItemToken> tokens) {
		for (Map.Entry<String, ItemToken> tokenEntry : tokens.entrySet()) {
			if (tokenEntry.getValue().isExpired())
				tokens.remove(tokenEntry.getKey());
		}
	}

	public static BibliographicItemId createBibliographicItemIdAsISBN(String bibliographicItemIdentifier) {
		BibliographicItemId bibliographicItemId = new BibliographicItemId();
		bibliographicItemId.setBibliographicItemIdentifier(bibliographicItemIdentifier);
		bibliographicItemId.setBibliographicItemIdentifierCode((Version1BibliographicItemIdentifierCode.ISBN));
		return bibliographicItemId;
	}

	public static BibliographicItemId createBibliographicItemIdAsLegalDepositNumber(String bibliographicItemIdentifier) {
		BibliographicItemId bibliographicItemId = new BibliographicItemId();
		bibliographicItemId.setBibliographicItemIdentifier(bibliographicItemIdentifier);
		bibliographicItemId.setBibliographicItemIdentifierCode(Version1BibliographicItemIdentifierCode.LEGAL_DEPOSIT_NUMBER);
		return bibliographicItemId;
	}

	public static BibliographicItemId createBibliographicItemIdAsURI(String bibliographicItemIdentifier) {
		BibliographicItemId bibliographicItemId = new BibliographicItemId();
		bibliographicItemId.setBibliographicItemIdentifier(bibliographicItemIdentifier);
		bibliographicItemId.setBibliographicItemIdentifierCode(Version1BibliographicItemIdentifierCode.URI);
		return bibliographicItemId;
	}

	/**
	 * Builds an XML POST in order to send it as a Http request to renew an item.
	 * You can set desired due date - but your Koha settings may not be compatible with
	 * setting custom due date & will add by default let's say 5 days to old due date.<br>
	 * <br>
	 * That's why you can set it's argument to null & nothing extra will happen
	 * 
	 * @param desiredDueDate
	 * @return renewXMLtoPOST
	 */
	public static String buildRenewPOSTXml(GregorianCalendar desiredDueDate) {
		if (desiredDueDate != null)
			return buildRenewPOSTXml(KohaUtil.convertToKohaDate(desiredDueDate));
		else
			return buildRenewPOSTXml("20991231");
	}

	private static String buildRenewPOSTXml(String desiredDueDate) {
		return "<?xml version = \"1.0\" encoding = \"UTF-8\"?>" + "<get-pat-loan><loan renew=\"Y\"><z36><z36-due-date>" + desiredDueDate
				+ "</z36-due-date></z36></loan></get-pat-loan>";
	}

	public static RequestStatusType parseRequestStatusTypeFromZ37StatusNode(String value) {
		RequestStatusType requestStatusType;
		if (value == "S")
			requestStatusType = Version1RequestStatusType.AVAILABLE_FOR_PICKUP;
		else
			requestStatusType = Version1RequestStatusType.IN_PROCESS;
		return requestStatusType;
	}

	public static HoldingsInformation createHoldingsInformationUnscructured(String description) {
		HoldingsInformation holdingsInformation = new HoldingsInformation();
		holdingsInformation.setUnstructuredHoldingsData(description);
		return holdingsInformation;
	}

	public static int parseHoldQueueLengthFromStatusNode(String parsedStatus) {
		// Parsing this : Waiting in position 1 in queue; current due date 05/Jan/2015
		// Or this: In process - in this case return -1
		String[] words = parsedStatus.split("\\s+");
		if (words.length < 6)
			return -1;
		else
			return Integer.parseInt(words[3]);
	}
}
