package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.*;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
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
	// TODO: Generate URL to be built based on type of action to be performed. Then use that URL to get XML response. Then parse the response & send back ncip formatted response.
	// FIXME: Lookup User service can't be handled by RESTful APIs. Need to provide XServices.

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
	private int bibIdLength;
	private int itemIdUniquePart;
	private SAXParser parser;

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
		itemIdUniquePart = AlephConstants.ITEM_ID_UNIQUE_PART_LENGTH;
		itemPathElement = AlephConstants.ITEM_PATH_ELEMENT;
		userPathElement = AlephConstants.USER_PATH_ELEMENT;
		itemsElement = AlephConstants.ITEMS_ELEMENT;

	}

	public AgencyId getDefaultAgencyId() {
		return new AgencyId(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE, defaultAgency);
	}

	private String normalizeRecordId(String recordId) {
		while (recordId.length() < bibIdLength) {
			recordId = "0" + recordId;
		}
		return recordId;
	}

	private String normalizeItemIdPart(String itemId) {
		while (itemId.length() < itemIdUniquePart) {
			itemId = "0" + itemId;
		}
		return itemId;
	}

	private String normalizeItemId(String itemId) {
		// All this is needed to build unique ItemId URL
		// e.g. http://aleph.mzk.cz:1892/rest-dlf/record/MZK01000000421/items/MZK50000000421000010
		String recordId = itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[0];
		String itemIdPart = itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[1];
		String[] itemIdParts = itemIdPart.split("\\.");

		if (itemIdParts != null) {
			/*
			 * Input is something like this: 421-1.0 What we need is: 000000421000010; Sure only if BIB_ID_LENGTH = 9 & ITEM_ID_UNIQUE_PART_LENGTH = 6
			 */
			itemId = itemIdParts[0] + itemIdParts[1];
		} else
			itemId = itemIdPart;

		return normalizeRecordId(recordId) + normalizeItemIdPart(itemId);
	}

	private String normalizeRecordIdFromItemId(String itemId) {
		// Take the part before UNIQUE_ITEM_ID_SEPERATOR and normalize it
		return normalizeRecordId(itemId.split(AlephConstants.UNIQUE_ITEM_ID_SEPERATOR)[0]);
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

		recordId = normalizeRecordId(recordId);

		URL url = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, itemPathElement, bibLibrary + recordId, itemsElement).addRequest("view", "full").toURL();

		InputSource streamSource = new InputSource(url.openStream());

		AlephItemHandler itemHandler = new AlephItemHandler(bibliographicDescription, circulationStatus, holdQueueLength, itemDesrciption);

		itemHandler.requireAtLeastOneService(requiredAtLeastOneService);

		parser.parse(streamSource, itemHandler);

		return itemHandler.getListOfItems();

	}

	public AlephItem lookupItem(String itemId, boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLength, boolean itemDesrciption)
			throws ParserConfigurationException, IOException, SAXException, AlephException {

		String recordId = normalizeRecordIdFromItemId(itemId);
		itemId = normalizeItemId(itemId);

		URL url = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, itemPathElement, bibLibrary + recordId, itemsElement, admLibrary + itemId).toURL();

		InputSource streamSource = new InputSource(url.openStream());

		AlephItemHandler itemHandler = new AlephItemHandler(bibliographicDescription, circulationStatus, holdQueueLength, itemDesrciption);

		itemHandler.requireAtLeastOneService(requiredAtLeastOneService);

		parser.parse(streamSource, itemHandler);

		return itemHandler.item;
	}

}