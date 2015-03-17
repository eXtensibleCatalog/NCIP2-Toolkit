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

import org.extensiblecatalog.ncip.v2.koha.item.MarcItem;
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
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Language;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicRecordIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Version1Language;
import org.extensiblecatalog.ncip.v2.service.Version1LocationType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestStatusType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class KohaUtil {

	public static AgencyId createAgencyId(String agencyId) {
		return new AgencyId(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE, agencyId);
	}

	public static ComponentId createComponentIdAsAccessionNumber(String barcodeValue) {
		ComponentId componentId = new ComponentId();
		componentId.setComponentIdentifierType(new ComponentIdentifierType(Version1BibliographicRecordIdentifierCode.VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE,
				Version1BibliographicRecordIdentifierCode.ACCESSION_NUMBER.getValue()));
		componentId.setComponentIdentifier(barcodeValue);
		return componentId;
	}

	public static Location parseLocation(MarcItem kohaItem) {
		Location location = new Location();
		LocationNameInstance locationNameInstance = new LocationNameInstance();

		String locationVal = "";
		locationNameInstance.setLocationNameValue(locationVal);
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

	public static BlockOrTrap parseBlockOrTrap(String parsedBlock) {
		BlockOrTrap blockOrTrap = new BlockOrTrap();

		// TODO: Revise this
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

	/**
	 * Converts LookupItemInitiationData to LookupItemSetInitiationData with respect only to desired services.
	 * 
	 * @param initData
	 * @return luisInitData
	 */
	public static LookupItemSetInitiationData lookupItemInitDataToLUISInitData(LookupItemInitiationData initData) {
		LookupItemSetInitiationData luisInitData = new LookupItemSetInitiationData();

		luisInitData.setBibliographicDescriptionDesired(initData.getBibliographicDescriptionDesired());
		luisInitData.setCirculationStatusDesired(initData.getCirculationStatusDesired());
		luisInitData.setCurrentBorrowerDesired(initData.getCurrentBorrowerDesired());
		luisInitData.setCurrentRequestersDesired(initData.getCurrentRequestersDesired());
		luisInitData.setElectronicResourceDesired(initData.getElectronicResourceDesired());
		luisInitData.setHoldQueueLengthDesired(initData.getHoldQueueLengthDesired());
		luisInitData.setItemDescriptionDesired(initData.getItemDescriptionDesired());
		luisInitData.setItemUseRestrictionTypeDesired(initData.getItemUseRestrictionTypeDesired());
		luisInitData.setLocationDesired(initData.getLocationDesired());
		luisInitData.setPhysicalConditionDesired(initData.getPhysicalConditionDesired());
		luisInitData.setSecurityMarkerDesired(initData.getSecurityMarkerDesired());
		luisInitData.setSensitizationFlagDesired(initData.getSensitizationFlagDesired());

		return luisInitData;
	}

	public static ItemOptionalFields parseItemOptionalFields(LookupItemInitiationData initData, MarcItem marcItem) {
		return parseItemOptionalFields(lookupItemInitDataToLUISInitData(initData), marcItem);
	}

	public static ItemOptionalFields parseItemOptionalFields(LookupItemSetInitiationData initData, MarcItem marcItem) {
		ItemOptionalFields iof = new ItemOptionalFields();

		if (initData.getCirculationStatusDesired()) {
			iof.setCirculationStatus(parseCirculationStatus(marcItem));
		}

		if (initData.getHoldQueueLengthDesired()) {
			iof.setHoldQueueLength(parseHoldQueueLength(marcItem));
		}

		if (initData.getItemDescriptionDesired()) {
			ItemDescription description = new ItemDescription();

			String callNumber = parseCallNumber(marcItem);
			BigDecimal noOfPieces = parseNoOfPieces(marcItem);
			if (callNumber != null || noOfPieces != null) {

				description.setCallNumber(callNumber);
				description.setNumberOfPieces(noOfPieces);

				iof.setItemDescription(description);
			}
		}

		if (initData.getItemUseRestrictionTypeDesired()) {
			iof.setItemUseRestrictionTypes(parseItemUseRestrictionTypes(marcItem));
		}

		if (initData.getLocationDesired()) {
			iof.setLocations(parseLocations(marcItem));

		}
		if (initData.getBibliographicDescriptionDesired()) {
			BibliographicDescription bibDesc = parseBibliographicDescription(marcItem);

			iof.setBibliographicDescription(bibDesc);
		}
		return iof;
	}

	public static BibliographicDescription parseBibliographicDescription(MarcItem marcItem) {
		BibliographicDescription bibliographicDescription = new BibliographicDescription();

		Map<String, String> authorDataField = marcItem.getDataField(KohaConstants.DATAFIELD_AUTHOR_RELATED_TAG);
		if (authorDataField != null) {
			String author = authorDataField.get(KohaConstants.SUBFIELD_AUTHOR_NAME_CODE);
			bibliographicDescription.setAuthor(author);
		}

		Map<String, String> authorOfComponentDataField = marcItem.getDataField(KohaConstants.DATAFIELD_AUTHOR_OF_COMPONENT_RELATED_TAG);
		if (authorOfComponentDataField != null) {
			String authorOfComponent = authorOfComponentDataField.get(KohaConstants.SUBFIELD_AUTHOR_NAME_CODE);
			bibliographicDescription.setAuthorOfComponent(authorOfComponent);
		}

		bibliographicDescription.setMediumType(parseMediumType(marcItem));

		String flde = marcItem.getControlField(KohaConstants.CONTROLFIELD_FLDE_TAG);
		if (flde != null && flde.length() > 37) {
			// Language is at position 35-37
			String langVal = (String) flde.subSequence(35, 38);
			Language lang = new Language(Version1Language.VERSION_1_LANGUAGE, langVal);
			bibliographicDescription.setLanguage(lang);
		}

		Map<String, String> titleDataField = marcItem.getDataField(KohaConstants.DATAFIELD_TITLE_RELATED_TAG);
		if (titleDataField != null) {
			String title = titleDataField.get(KohaConstants.SUBFIELD_TITLE_CODE);
			bibliographicDescription.setTitle(title);

			String titleOfComponent = titleDataField.get(KohaConstants.SUBFIELD_TITLE_OF_COMPONENT_CODE);

			if (titleOfComponent == null || titleOfComponent.trim().isEmpty())
				titleOfComponent = titleDataField.get(KohaConstants.SUBFIELD_TITLE_OF_COMPONENT_SECOND_CODE);

			bibliographicDescription.setTitleOfComponent(titleOfComponent);
		}

		Map<String, String> publicationDataField = marcItem.getDataField(KohaConstants.DATAFIELD_PUBLICATION_RELATED_TAG);
		if (publicationDataField != null) {
			String publisher = publicationDataField.get(KohaConstants.SUBFIELD_PUBLISHER_NAME_CODE);
			bibliographicDescription.setPublisher(publisher);

			String publicationPlace = publicationDataField.get(KohaConstants.SUBFIELD_PUBLICATION_PLACE_CODE);
			bibliographicDescription.setPlaceOfPublication(publicationPlace);

			String publicationDate = publicationDataField.get(KohaConstants.SUBFIELD_PUBLICATION_DATE_CODE);
			bibliographicDescription.setPublicationDate(publicationDate);
		}

		Map<String, String> editionDataField = marcItem.getDataField(KohaConstants.DATAFIELD_EDITION_TAG);
		if (editionDataField != null) {
			String edition = editionDataField.get(KohaConstants.SUBFIELD_EDITION_STATEMENT_CODE);
			bibliographicDescription.setEdition(edition);
		}

		Map<String, String> paginationDataField = marcItem.getDataField(KohaConstants.DATAFIELD_PHYSICAL_DESCRIPTION_TAG);
		if (paginationDataField != null) {
			String pagination = paginationDataField.get(KohaConstants.SUBFIELD_PAGINATION_CODE);
			bibliographicDescription.setPagination(pagination);
		}

		List<BibliographicItemId> bibIds = new ArrayList<BibliographicItemId>();

		Map<String, String> isbnDataField = marcItem.getDataField(KohaConstants.DATAFIELD_ISBN_RELATED_TAG);
		if (isbnDataField != null) {

			String isbnVal = isbnDataField.get(KohaConstants.SUBFIELD_ISBN_CODE);

			if (isbnVal != null) {
				bibIds.add(createBibliographicItemIdAsISBN(isbnVal));
			}
		}

		if (marcItem.getHoldingsItemSubfields() != null) {
			String barcode = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_BARCODE_CODE);

			if (barcode == null)
				barcode = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_BARCODE_SECOND_CODE);

			if (barcode != null) {
				bibIds.add(createBibliographicItemIdAsLegalDepositNumber(barcode));
			}
		}

		if (bibIds.size() > 0)
			bibliographicDescription.setBibliographicItemIds(bibIds);

		return bibliographicDescription;
	}

	public static ItemId parseItemId(MarcItem marcItem) throws KohaException {

		String itemIdVal = marcItem.getControlField(KohaConstants.CONTROLFIELD_ITEM_ID_TAG);
		if (itemIdVal == null || itemIdVal.isEmpty())
			throw new KohaException("Koha svc have returned empty itemId (tag 001)");

		ItemId itemId = new ItemId();
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		itemId.setItemIdentifierValue(itemIdVal);

		itemId.setAgencyId(parseAgencyId(marcItem));

		return itemId;
	}

	public static AgencyId parseAgencyId(MarcItem marcItem) {
		Map<String, String> agencyDataField = marcItem.getDataField(KohaConstants.DATAFIELD_SIGLA_PARENT_TAG);
		if (agencyDataField != null) {
			String agencyIdVal = agencyDataField.get(KohaConstants.SUBFIELD_SIGLA_CODE);

			if (agencyIdVal != null && !agencyIdVal.trim().isEmpty())
				return createAgencyId(agencyIdVal);
		}
		return createAgencyId(LocalConfig.getDefaultAgency());
	}

	private static CirculationStatus parseCirculationStatus(MarcItem marcItem) {
		if (marcItem.getHoldingsItemSubfields() != null) {
			String statusVal = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_STATUS_CODE);
			if (statusVal != null) {
				if (statusVal.matches(KohaConstants.CIRC_STATUS_NOT_AVAILABLE + "|" + KohaConstants.CIRC_STATUS_LOST + "|" + KohaConstants.CIRC_STATUS_DAMAGED)) {
					return Version1CirculationStatus.NOT_AVAILABLE;
				} else if (statusVal.matches(KohaConstants.CIRC_STATUS_ON_SHELF + "|" + KohaConstants.CIRC_STATUS_PRESENT_ONLY)) {
					return Version1CirculationStatus.AVAILABLE_ON_SHELF;
				} else if (statusVal.matches(KohaConstants.CIRC_STATUS_PROCESSING)) {
					return Version1CirculationStatus.IN_PROCESS;
				}
			}
		}
		return Version1CirculationStatus.CIRCULATION_STATUS_UNDEFINED;
	}

	public static RequestDetails parseRequestDetails(MarcItem requestItem) {
		// FIXME
		return null;
	}

	private static BigDecimal parseHoldQueueLength(MarcItem marcItem) {
		// FIXME
		return null;
	}

	private static BigDecimal parseNoOfPieces(MarcItem marcItem) {
		// FIXME
		return null;
	}

	private static MediumType parseMediumType(MarcItem marcItem) {
		// FIXME: datafield 996 -> code "??" -> val KN = Book & mapping?
		return null;
	}

	private static String parseCallNumber(MarcItem marcItem) {
		if (marcItem.getHoldingsItemSubfields() != null) {
			return marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_CALL_NUMBER_CODE);
		}
		return null;
	}

	private static List<ItemUseRestrictionType> parseItemUseRestrictionTypes(MarcItem marcItem) {
		List<ItemUseRestrictionType> iurts = new ArrayList<ItemUseRestrictionType>();

		if (marcItem.getHoldingsItemSubfields() != null) {
			String statusVal = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_STATUS_CODE);

			if (statusVal.equals(KohaConstants.CIRC_STATUS_PRESENT_ONLY)) {
				iurts.add(Version1ItemUseRestrictionType.IN_LIBRARY_USE_ONLY);
			}
		}

		// TODO: datafield 996 -> code "5" -> mapping? ..

		if (iurts.size() > 0)
			return iurts;

		return null;
	}

	private static List<Location> parseLocations(MarcItem marcItem) {

		if (marcItem.getHoldingsItemSubfields() != null) {
			String locationOnAgencyLevel = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_LOCATION_ON_AGENCY_LEVEL_CODE);
			String locationOnBuildingLevel = marcItem.getHoldingsItemSubfields().get(KohaConstants.SUBFIELD_HOLDINGS_ITEM_LOCATION_ON_BUILDING_LEVEL_CODE);

			if (locationOnAgencyLevel != null || locationOnBuildingLevel != null) {
				Location location = new Location();
				LocationName locationName = new LocationName();
				List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();

				if (locationOnAgencyLevel != null)
					locationNameInstances.add(createLocationNameInstance(locationOnAgencyLevel, new BigDecimal(1)));

				if (locationOnBuildingLevel != null)
					locationNameInstances.add(createLocationNameInstance(locationOnBuildingLevel, new BigDecimal(2)));

				locationName.setLocationNameInstances(locationNameInstances);
				location.setLocationName(locationName);
				location.setLocationType(Version1LocationType.PERMANENT_LOCATION);

				return Arrays.asList(location);
			}
		}
		return null;
	}

	public static List<UserFiscalAccount> parseUserFiscalAccounts(JSONObject kohaUser) {
		// TODO

		/*
				CurrencyCode currencyCode = new CurrencyCode(svcMgr.getCurrencyCode(), kohaUser.getBalanceMinorUnit());

				// Update Currency Code
				for (UserFiscalAccount userFiscalAccount : userFiscalAccounts) {
					AccountBalance accountBalance = userFiscalAccount.getAccountBalance();

					if (accountBalance == null) {
						accountBalance = new AccountBalance();
						accountBalance.setMonetaryValue(new BigDecimal("0"));
					}
					accountBalance.setCurrencyCode(currencyCode);
					userFiscalAccount.setAccountBalance(accountBalance);

					List<AccountDetails> accountDetails = userFiscalAccount.getAccountDetails();

					for (AccountDetails details : accountDetails) {
						FiscalTransactionInformation fiscalTransactionInformation = details.getFiscalTransactionInformation();

						Amount amount = fiscalTransactionInformation.getAmount();
						amount.setCurrencyCode(currencyCode);

						fiscalTransactionInformation.setAmount(amount);

						details.setFiscalTransactionInformation(fiscalTransactionInformation);
					}
				}
				*/
		return null;
	}

	public static UserOptionalFields parseUserOptionalFields(LookupUserInitiationData initData, JSONObject kohaUser) {
		UserOptionalFields uof = new UserOptionalFields();

		boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();
		boolean loanedItemsDesired = initData.getLoanedItemsDesired();
		boolean requestedItemsDesired = initData.getRequestedItemsDesired();
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();
		/*
		 * 
						if (blockOrTrapDesired) {
							uof.setBlockOrTraps(kohaUser.getBlockOrTraps());
							includeUserOptionalFields = true;
						}

						if (nameInformationDesired) {
							uof.setNameInformation(kohaUser.getNameInformation());
							includeUserOptionalFields = true;
						}

						if (userAddressInformationDesired) {
							uof.setUserAddressInformations(kohaUser.getUserAddressInformations());
							includeUserOptionalFields = true;
						}

						if (userIdDesired) {
							uof.setUserIds(kohaUser.getUserIds());
							includeUserOptionalFields = true;
						}

						if (userPrivilegeDesired) {
							uof.setUserPrivileges(kohaUser.getUserPrivileges());
							includeUserOptionalFields = true;
						}
		 */

		boolean nameInformationDesired = initData.getNameInformationDesired();
		boolean userIdDesired = initData.getUserIdDesired();
		boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();

		return null; // TODO: Return null if none optional field desired ..
	}
}
