package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.extensiblecatalog.ncip.v2.aleph.util.*;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyElementType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RestDlfConnector extends AlephMediator {

	private static final long serialVersionUID = -4425639616999642735L;
	protected AlephConfiguration alephConfig = null;
	public boolean echoParticularProblemsToLUIS = false;
	private boolean requiredAtLeastOneService = true;
	public String defaultAgency = "";
	private String serverName;
	private String serverPort;
	private String serverSuffix;
	private String bibLibrary;
	private String admLibrary;
	private String itemPathElement;
	private String userPathElement;
	private String itemsElement;
	private String patronElement;

	private int bibIdLength;
	private int sequenceNumberLength;
	private SAXParser parser;
	private String patronInfoElement;
	private String addressElement;
	private String circActionsElement;
	private String loansElement;
	private String requestsElement;
	private String holdsElement;
	private String patronStatusElement;
	private String registrationElement;
	private String recordPathElement;

	public RestDlfConnector() throws ServiceException {

		try {
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			alephConfig = new AlephConfiguration(config);
			serverName = alephConfig.getProperty(AlephConstants.REST_DLF_SERVER);
			serverPort = alephConfig.getProperty(AlephConstants.REST_DLF_PORT);
			if (serverName == null || serverPort == null) {
				throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Aleph server or port not set");
			}
			// Get configuration from toolkit.properties file

			serverSuffix = alephConfig.getProperty(AlephConstants.REST_DLF_SUFFIX);
			bibLibrary = alephConfig.getProperty(AlephConstants.BIBLIOGRAPHIC_LIBRARY);
			admLibrary = alephConfig.getProperty(AlephConstants.ALEPH_ADMINISTRATIVE_LIBRARY);
			defaultAgency = alephConfig.getProperty(AlephConstants.DEFAULT_AGENCY);

			echoParticularProblemsToLUIS = Boolean.parseBoolean(alephConfig.getProperty(AlephConstants.INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS));
			requiredAtLeastOneService = Boolean.parseBoolean(alephConfig.getProperty(AlephConstants.REQUIRE_AT_LEAST_ONE_SERVICE));

			parser = SAXParserFactory.newInstance().newSAXParser();

		} catch (ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		bibIdLength = AlephConstants.BIB_ID_LENGTH;
		sequenceNumberLength = AlephConstants.ITEM_ID_UNIQUE_PART_LENGTH;
		itemPathElement = AlephConstants.ITEM_PATH_ELEMENT;
		userPathElement = AlephConstants.USER_PATH_ELEMENT;
		itemsElement = AlephConstants.PARAM_ITEMS;
		patronInfoElement = AlephConstants.PARAM_PATRON_INFO;
		addressElement = AlephConstants.PARAM_ADDRESS;
		circActionsElement = AlephConstants.PARAM_CIRC_ACTIONS;
		loansElement = AlephConstants.PARAM_LOANS;
		holdsElement = AlephConstants.PARAM_HOLDS;
		requestsElement = AlephConstants.PARAM_REQUESTS;
		patronStatusElement = AlephConstants.PARAM_PATRON_STATUS;
		registrationElement = AlephConstants.PARAM_REGISTRATION;
		recordPathElement = AlephConstants.PARAM_RECORD;

	}

	public AgencyId getDefaultAgencyId() {
		return new AgencyId(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE, defaultAgency);
	}

	private boolean validateRecordId(String recordId) {
		if (recordId.length() == bibIdLength + bibLibrary.length()) {
			return true;
		}
		return false;
	}

	private boolean validateSequenceNumber(String sequenceNumber) {
		if (sequenceNumber.length() == sequenceNumberLength) {
			return true;
		}
		return false;
	}

	private String parseRecordIdFromItemId(String itemId) {
		return itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[0];
	}

	private String parseSequenceNumberFromItemId(String itemId) {
		return itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[1];
	}

	/**
	 * Looks up item with desired services in following order:
	 * 
	 * @param recordId
	 * @param bibliographicDescription
	 * @param circulationStatus
	 * @param holdQueueLength
	 * @param itemDesrciption
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws AlephException
	 */
	public List<AlephItem> lookupItems(String recordId, boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLength, boolean itemDesrciption)
			throws ParserConfigurationException, IOException, SAXException, AlephException {

		if (!validateRecordId(recordId)) {
			throw new AlephException("Record Id is accepted only in strict format with strict length. e.g. MZK01000000421");
		}

		URL url = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, itemPathElement, recordId, itemsElement).addRequest("view", "full").toURL();

		InputSource streamSource = new InputSource(url.openStream());

		AlephItemHandler itemHandler = new AlephItemHandler(requiredAtLeastOneService, bibliographicDescription, circulationStatus, holdQueueLength, itemDesrciption);

		parser.parse(streamSource, itemHandler);

		return itemHandler.getListOfItems();

	}

	/**
	 * @param itemId
	 * @param bibliographicDescription
	 * @param circulationStatus
	 * @param holdQueueLength
	 * @param itemDesrciption
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws AlephException
	 */
	public AlephItem lookupItem(String itemId, boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLength, boolean itemDesrciption)
			throws ParserConfigurationException, IOException, SAXException, AlephException {

		/*
		 * Input could be something like this: MZK01000000421-000010 What we need is: MZK01000000421/items/MZK50000000421000010
		 */

		String recordId = parseRecordIdFromItemId(itemId);
		String sequenceNumber = parseSequenceNumberFromItemId(itemId);

		if (!validateRecordId(recordId) || !validateSequenceNumber(sequenceNumber)) {
			throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01000000421-000010");
		}

		URL url = new URLBuilder().setBase(serverName, serverPort)
				.setPath(serverSuffix, itemPathElement, recordId, itemsElement, admLibrary + recordId.substring(AlephConstants.LIBRARY_PARAM_LENGTH) + sequenceNumber).toURL();

		InputSource streamSource = new InputSource(url.openStream());

		AlephItemHandler itemHandler = new AlephItemHandler(requiredAtLeastOneService, bibliographicDescription, circulationStatus, holdQueueLength, itemDesrciption);

		parser.parse(streamSource, itemHandler);

		return itemHandler.getAlephItem();
	}

	/**
	 * @param patronId
	 * @param initData
	 * @return
	 * @throws AlephException
	 * @throws IOException
	 * @throws SAXException
	 */
	public AlephUser lookupUser(String patronId, LookupUserInitiationData initData) throws AlephException, IOException, SAXException {

		boolean loanedItemsDesired = initData.getLoanedItemsDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/loans?view=full
		boolean nameInformationDesired = initData.getNameInformationDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address
		boolean requestedItemsDesired = initData.getRequestedItemsDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/requests/ ??? FIXME
		boolean userAddressInformationDesired = initData.getUserAddressInformationDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions -> Cash
		boolean userIdDesired = initData.getUserIdDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronInformation/address - > Mandatory address 1
		boolean userPrivilegeDesired = initData.getUserPrivilegeDesired(); // http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/patronStatus/registration

		boolean atLeastOneDesired = false;

		// Create URL request only if specified service was desired
		URL addressUrl = null;
		if (nameInformationDesired || userIdDesired || userAddressInformationDesired) {
			atLeastOneDesired = true;
			addressUrl = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, userPathElement, patronId, patronInfoElement, addressElement).toURL();
		}

		URL circulationsUrl = null;
		if (userFiscalAccountDesired) {
			atLeastOneDesired = true;
			// TODO: Ask librarian if amount is enough, or would be better detailed transactions overview
			// If not, use this sample URL: http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/cash?view=full
			circulationsUrl = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, userPathElement, patronId, circActionsElement).toURL();
		}

		URL loansUrl = null;
		if (loanedItemsDesired) {
			atLeastOneDesired = true;
			loansUrl = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, userPathElement, patronId, circActionsElement, loansElement)
					.addRequest("view", "full").toURL();
		}

		URL requestsUrl = null;
		if (requestedItemsDesired) {
			atLeastOneDesired = true;
			// We suppose desired requests are at http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/requests/bookings?view=full
			requestsUrl = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, userPathElement, patronId, circActionsElement, requestsElement, holdsElement)
					.addRequest("view", "full").toURL();
		}

		URL registrationUrl = null;
		if (userPrivilegeDesired) {
			atLeastOneDesired = true;
			registrationUrl = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, userPathElement, patronId, patronStatusElement, registrationElement).toURL();
		}

		if (atLeastOneDesired) {
			AlephUser alephUser = new AlephUser();

			AlephUserHandler userHandler = new AlephUserHandler(nameInformationDesired, userIdDesired, userAddressInformationDesired, userFiscalAccountDesired,
					userPrivilegeDesired);

			InputSource streamSource;

			if (loansUrl != null || requestsUrl != null) {

				AlephItemHandler itemHandler = new AlephItemHandler(false, false, false, false, false).parseLoansOrRequests();

				if (loansUrl != null) {
					streamSource = new InputSource(loansUrl.openStream());
					itemHandler.setLoansHandlingNow();
					parser.parse(streamSource, itemHandler);
					alephUser.setLoanedItems(itemHandler.getListOfLoanedItems());
				}
				if (requestsUrl != null) {
					streamSource = new InputSource(requestsUrl.openStream());
					itemHandler.setRequestsHandlingNow();
					parser.parse(streamSource, itemHandler);// FIXME: implement parsing requests
					alephUser.setRequestedItems(itemHandler.getListOfRequestedItems());
				}
			}

			userHandler.setAlephUser(alephUser);

			if (addressUrl != null) {
				streamSource = new InputSource(addressUrl.openStream());
				userHandler.setAddressHandlingNow();
				parser.parse(streamSource, userHandler);
			}
			if (circulationsUrl != null) {
				streamSource = new InputSource(circulationsUrl.openStream());
				userHandler.setCirculationsHandlingNow();
				parser.parse(streamSource, userHandler);
			}
			if (registrationUrl != null) {
				streamSource = new InputSource(registrationUrl.openStream());
				userHandler.setRegistrationHandlingNow();
				parser.parse(streamSource, userHandler);
			}

			return userHandler.getAlephUser();
		} else
			return null;
	}

	public AlephRequestItem requestItem(RequestItemInitiationData initData) throws MalformedURLException, AlephException {
		AlephRequestItem requestItem = new AlephRequestItem();

		// TODO: Is any of these request item capable services needed?
		boolean getItemUseRestrictionType = initData.getItemUseRestrictionTypeDesired();
		boolean getPhysicalCondition = initData.getPhysicalConditionDesired();
		boolean getSecurityMarker = initData.getSecurityMarkerDesired();
		boolean getSensitizationFlag = initData.getSensitizationFlagDesired();
		boolean getElectronicResource = initData.getElectronicResourceDesired();
		boolean getLocation = initData.getLocationDesired();

		boolean getBibDescription = initData.getBibliographicDescriptionDesired();
		boolean getCircStatus = initData.getCirculationStatusDesired();
		boolean getHoldQueueLength = initData.getHoldQueueLengthDesired();
		boolean getItemDescription = initData.getItemDescriptionDesired();

		boolean nameInformationDesired = initData.getNameInformationDesired();
		boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();
		boolean userIdDesired = initData.getUserIdDesired();
		boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();
		// How about calling lookupUser or lookupItem to satisfy these?
		// EOF TODO

		String patronId = initData.getUserId().getUserIdentifierValue();

		// FIXME: Allow request more items with one XML request
		String itemId = initData.getItemId(0).getItemIdentifierValue();

		String recordId = parseRecordIdFromItemId(itemId);
		String sequenceNumber = parseSequenceNumberFromItemId(itemId);

		if (!validateRecordId(recordId) || !validateSequenceNumber(sequenceNumber)) {
			throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01000000421-000010");
		}

		// PUT html to e.g. http://aleph.mzk.cz:1892/rest-dlf/patron/700/record/MZK01001330134/holds/MZK50001365071000010
		URL holdUrl = new URLBuilder()
				.setBase(serverName, serverPort)
				.setPath(serverSuffix, userPathElement, patronId, recordPathElement, recordId, holdsElement,
						admLibrary + recordId.substring(AlephConstants.LIBRARY_PARAM_LENGTH) + sequenceNumber).toURL();

		String pickupLocation = initData.getPickupLocation().getValue();

		String needBeforeDate = convertToAlephDate(initData.getNeedBeforeDate());
		String earliestDateNeeded = convertToAlephDate(initData.getEarliestDateNeeded());

		String firstNote = null;
		// Our own testing identifier system (Moravian Library's)
		if (patronId.equalsIgnoreCase("700") || true) {
			firstNote = "dg";
		}

		String XMLRequest = new XMLBuilder().setParent("hold-request-parameters").setPickupLocation(pickupLocation).setLastInterestDate(needBeforeDate)
				.setStartInterestDate(earliestDateNeeded).setFirstNote(firstNote).setRush("N").toString();
		

		return requestItem;
	}

	private String convertToAlephDate(GregorianCalendar gregorianCalendar) {
		// We need: 20141231

		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH) + 1);
		if (month.length() < 2)
			month = "0" + month;
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if (day.length() < 2)
			day = "0" + day;
		return Integer.toString(gregorianCalendar.get(Calendar.YEAR)) + month + day;
	}
}
