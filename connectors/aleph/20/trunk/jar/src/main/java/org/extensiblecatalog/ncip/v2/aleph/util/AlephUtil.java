package org.extensiblecatalog.ncip.v2.aleph.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.service.CurrentBorrower;
import org.extensiblecatalog.ncip.v2.service.CurrentRequester;
import org.extensiblecatalog.ncip.v2.service.ElectronicResource;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemDescriptionLevel;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemTransaction;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LocationType;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicRecordIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1ItemDescriptionLevel;
import org.extensiblecatalog.ncip.v2.service.Version1MediumType;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;

public class AlephUtil {
	public static BibliographicDescription getBibliographicDescription(AlephItem alephItem, AgencyId agencyId) {

		BibliographicDescription bibliographicDescription = new BibliographicDescription();
		BibliographicItemId bibliographicItemId;
		BibliographicRecordId bibliographicRecordId;

		List<BibliographicItemId> bibIds = new ArrayList<BibliographicItemId>();
		List<BibliographicRecordId> bibRecIds = new ArrayList<BibliographicRecordId>();
		if (alephItem.getAuthor() != null) {
			bibliographicDescription.setAuthor(alephItem.getAuthor());
		}
		if (alephItem.getIsbn() != null) {
			bibliographicItemId = new BibliographicItemId();
			bibliographicItemId.setBibliographicItemIdentifier(alephItem.getIsbn());
			bibliographicItemId.setBibliographicItemIdentifierCode((Version1BibliographicItemIdentifierCode.ISBN));
			bibIds.add(bibliographicItemId);
		}
		if (alephItem.getBarcode() != null) {
			bibliographicItemId = new BibliographicItemId();
			bibliographicItemId.setBibliographicItemIdentifier(alephItem.getBarcode());
			bibliographicItemId.setBibliographicItemIdentifierCode(Version1BibliographicItemIdentifierCode.LEGAL_DEPOSIT_NUMBER);
			bibIds.add(bibliographicItemId);
		}

		if (alephItem.getMediumType() != null) {
			MediumType mediumType = AlephUtil.detectMediumType(alephItem.getMediumType());
			bibliographicDescription.setMediumType(mediumType);
		}
		if (alephItem.getPublisher() != null) {
			bibliographicDescription.setPublisher(alephItem.getPublisher());
		}
		if (alephItem.getSeries() != null) {
			bibliographicDescription.setSeriesTitleNumber(alephItem.getSeries());
		}
		if (alephItem.getTitle() != null) {
			bibliographicDescription.setTitle(alephItem.getTitle());
		}

		if (alephItem.getBibId() != null || alephItem.getDocNumber() != null) {
			if (alephItem.getDocNumber() != null) {
				bibliographicRecordId = new BibliographicRecordId();
				bibliographicRecordId.setAgencyId(agencyId);
				bibliographicRecordId.setBibliographicRecordIdentifier(alephItem.getDocNumber());
				bibliographicRecordId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER);
				bibRecIds.add(bibliographicRecordId);
			} else {
				bibliographicRecordId = new BibliographicRecordId();
				bibliographicRecordId.setAgencyId(agencyId);
				bibliographicRecordId.setBibliographicRecordIdentifier(alephItem.getBibId());
				bibliographicRecordId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER);
				bibRecIds.add(bibliographicRecordId);
			}
		}

		bibliographicDescription.setBibliographicItemIds(bibIds);
		bibliographicDescription.setBibliographicRecordIds(bibRecIds);

		// FIXME: NCIP cuts multiple bibliographic items/records although they are set properly - forwarded is only first bibItemId
		// all other item ids & record ids are cut off
		return bibliographicDescription;
	}

	public static Location getLocation(AlephItem alephItem) {
		Location location = new Location();
		alephItem.getLocation();
		LocationNameInstance locationNameInstance = new LocationNameInstance();

		locationNameInstance.setLocationNameValue(alephItem.getLocation());
		// TODO: more to come from requirement for level
		locationNameInstance.setLocationNameLevel(new BigDecimal("1"));// temporarily set to 1.

		List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
		locationNameInstances.add(locationNameInstance);

		LocationName locationName = new LocationName();
		locationName.setLocationNameInstances(locationNameInstances);

		location = new Location();
		location.setLocationName(locationName);
		location.setLocationType(new LocationType(AlephConstants.DEFAULT_SCHEME, AlephConstants.LOCATION_TYPE_PERMANENT));
		return location;
	}

	public static ItemOptionalFields getItemOptionalFields(AlephItem alephItem) {
		ItemOptionalFields iof = new ItemOptionalFields();
		if (alephItem.getCirculationStatus() != null) {
			if (alephItem.getCirculationStatus().getValue().equalsIgnoreCase(AlephConstants.ON_SHELF)) {
				iof.setCirculationStatus(alephItem.getCirculationStatus());
			}
		}
		if (alephItem.getElectronicResource() != null) {
			ElectronicResource resource = new ElectronicResource();
			resource.setReferenceToResource(alephItem.getElectronicResource());
			iof.setElectronicResource(resource);
		}

		if (alephItem.getHoldQueueLength() >= 0) {
			iof.setHoldQueueLength(new BigDecimal(alephItem.getHoldQueueLength()));
		} else if (alephItem.getHoldQueue() != null) {
			iof.setHoldQueue(alephItem.getHoldQueue());
		}

		ItemDescription description = null;

		if (alephItem.getCallNumber() != null) {
			description = new ItemDescription();
			description.setCallNumber(alephItem.getCallNumber());
			iof.setItemDescription(description);
		}

		if (alephItem.getCopyNumber() != null) {
			description = new ItemDescription();
			description.setCopyNumber(alephItem.getCopyNumber());
			iof.setItemDescription(description);
		}

		if (alephItem.getLocation() != null) {

			LocationNameInstance locationNameInstance = new LocationNameInstance();

			locationNameInstance.setLocationNameValue(alephItem.getLocation());
			// TODO: more to come from requirement for level
			locationNameInstance.setLocationNameLevel(new BigDecimal("1"));// temperarily set to 1.

			List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
			locationNameInstances.add(locationNameInstance);

			LocationName locationName = new LocationName();
			locationName.setLocationNameInstances(locationNameInstances);

			Location location = new Location();
			location.setLocationName(locationName);
			location.setLocationType(new LocationType(AlephConstants.DEFAULT_SCHEME, AlephConstants.LOCATION_TYPE_PERMANENT));
			List<Location> locations = new ArrayList<Location>();
			locations.add(location);
			iof.setLocations(locations);
		}

		if (alephItem.getDescription() != null) {
			if (description == null)
				description = new ItemDescription();
			description.setNumberOfPieces(alephItem.getNumberOfPieces());
			description.setItemDescriptionLevel(new ItemDescriptionLevel(AlephConstants.DEFAULT_SCHEME, alephItem.getDescription()));
			iof.setItemDescription(description);
		}
		return iof;
	}

	public static ItemTransaction getItemTransaction(AlephItem alephItem) {
		ItemTransaction itemTransaction = new ItemTransaction();
		if (alephItem.getBorrowingUsers() != null && alephItem.getBorrowingUsers().size() > 0) {
			CurrentBorrower borrower = new CurrentBorrower();
			UserId userId = new UserId();
			AlephUser alephUser = alephItem.getBorrowingUsers().get(0);
			userId.setUserIdentifierValue(alephUser.getAuthenticatedUsername());
			userId.setUserIdentifierType(new UserIdentifierType("Username"));
			userId.setAgencyId(new AgencyId(alephUser.getAgency().getAgencyId()));
			borrower.setUserId(userId);
			itemTransaction.setCurrentBorrower(borrower);
		}

		if (alephItem.getRequestingUsers() != null && alephItem.getRequestingUsers().size() > 0) {
			Iterator<AlephUser> i = alephItem.getRequestingUsers().iterator();
			while (i.hasNext()) {
				AlephUser alephUser = i.next();
				CurrentRequester requester = new CurrentRequester();
				UserId userId = new UserId();
				userId.setUserIdentifierValue(alephUser.getAuthenticatedUsername());
				userId.setUserIdentifierType(new UserIdentifierType("Username"));
				userId.setAgencyId(new AgencyId(alephUser.getAgency().getAgencyId()));
				requester.setUserId(userId);
				List<CurrentRequester> requesters = itemTransaction.getCurrentRequesters();
				if (requesters == null)
					requesters = new ArrayList<CurrentRequester>();
				requesters.add(requester);
				itemTransaction.setCurrentRequesters(requesters);
			}

		}
		return itemTransaction;
	}

	public static boolean inDaylightTime() {
		return TimeZone.getDefault().inDaylightTime(new java.util.Date());
	}

	public static String convertToAlephDate(GregorianCalendar gregorianCalendar) {
		// We need: 20141231

		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH) + 1);
		if (month.length() < 2)
			month = "0" + month;
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if (day.length() < 2)
			day = "0" + day;
		return Integer.toString(gregorianCalendar.get(Calendar.YEAR)) + month + day;
	}

	public static String parseRecordIdFromAlephItemId(String itemId) {
		return itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[0];
	}

	public static String parseItemIdFromAlephItemId(String itemId) {
		return itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[1];
	}

	public static MediumType detectMediumType(String mediumTypeParsed) {
		MediumType mediumType = null;
		// TODO: Create localized external settings
		// Kniha = Czech localized name of book
		// Vazan. ser. = of magazine
		if (mediumTypeParsed.equalsIgnoreCase("Kniha") || mediumTypeParsed.equalsIgnoreCase("Mapa") || mediumTypeParsed.equalsIgnoreCase("Grafika"))
			mediumType = Version1MediumType.BOOK;
		else if (mediumTypeParsed.equalsIgnoreCase("Vazan. ser.") || mediumTypeParsed.equalsIgnoreCase("Seriál"))
			mediumType = Version1MediumType.MAGAZINE;
		else if (mediumTypeParsed.equalsIgnoreCase("Kompaktní disk")) {
			mediumType = Version1MediumType.CD_ROM;
		} else {
			try {
				Version1MediumType.loadAll();
				mediumType = MediumType.find(Version1MediumType.VERSION_1_MEDIUM_TYPE, mediumTypeParsed);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return mediumType;
	}
}
