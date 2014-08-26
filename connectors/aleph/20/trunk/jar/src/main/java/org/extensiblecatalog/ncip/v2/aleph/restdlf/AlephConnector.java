package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.*;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AlephConnector extends AlephMediator {
	// TODO: Generate URL to be built based on type of action to be performed. Then use that URL to get XML response. Then parse the response & send back ncip formatted response.
	// FIXME: Lookup User service can't be handled by RESTful APIs. Need to provide XServices.

	private static final long serialVersionUID = -4425639616999642735L;
	protected AlephConfiguration alephConfig = null;
	private String serverName;
	private String serverPort;
	private String serverSuffix;
	private String bibLibrary;
	private String itemPathElement;
	private String userPathElement;
	private String itemsElement;
	private int bibIdLength;
	private SAXParser parser;
	public boolean echoParticularProblemsToLUIS = false;

	public AlephConnector() throws ServiceException {
		try {
			// Get configuration from toolkit.properties file
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			alephConfig = new AlephConfiguration(config);
			serverName = alephConfig.getProperty(AlephConstants.REST_DLF_SERVER);
			serverPort = alephConfig.getProperty(AlephConstants.REST_DLF_PORT);
			serverSuffix = alephConfig.getProperty(AlephConstants.REST_DLF_SUFFIX);
			bibLibrary = alephConfig.getProperty(AlephConstants.BIBLIOGRAPHIC_LIBRARY);
			
			echoParticularProblemsToLUIS = Boolean.parseBoolean(alephConfig.getProperty(AlephConstants.INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS));

			if (serverName == null || serverPort == null) {
				throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Aleph server or port not set");
			}

			bibIdLength = AlephConstants.BIB_ID_LENGTH;
			itemPathElement = AlephConstants.ITEM_PATH_ELEMENT;
			userPathElement = AlephConstants.USER_PATH_ELEMENT;
			itemsElement = AlephConstants.ITEMS_ELEMENT;

			parser = SAXParserFactory.newInstance().newSAXParser();

		} catch (ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	private String normalizeItem(String item) {
		while (item.length() < bibIdLength) {
			item = "0" + item;
		}
		return item;
	}

	/**
	 * Looks up item with desired services in following order:
	 * 
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
	public List<AlephItem> lookupItem(String itemId, boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLength, boolean itemDesrciption) throws ParserConfigurationException, IOException, SAXException,
			AlephException {
		
		itemId = normalizeItem(itemId);
		
		URL url = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, itemPathElement, bibLibrary + itemId, itemsElement).addRequest("view", "full").toURL();
		
		InputSource streamSource = new InputSource(url.openStream());
		
		AlephItemHandler itemHandler = new AlephItemHandler(bibliographicDescription, circulationStatus, holdQueueLength, itemDesrciption);

		parser.parse(streamSource, itemHandler);

		return itemHandler.getListOfItems();

	}

}