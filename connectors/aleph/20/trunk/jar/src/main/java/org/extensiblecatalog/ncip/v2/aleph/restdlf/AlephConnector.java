package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.*;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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

	public AlephConnector() throws ServiceException {
		try {
			// Get configuration from toolkit.properties file
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			alephConfig = new AlephConfiguration(config);
			serverName = alephConfig.getProperty(AlephConstants.REST_DLF_SERVER);
			serverPort = alephConfig.getProperty(AlephConstants.REST_DLF_PORT);
			serverSuffix = alephConfig.getProperty(AlephConstants.REST_DLF_SUFFIX);
			bibLibrary = alephConfig.getProperty(AlephConstants.BIBLIOGRAPHIC_LIBRARY);

			if (serverName == null || serverPort == null) {
				throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Aleph server or port not set");
			}

			bibIdLength = AlephConstants.BIB_ID_LENGTH;
			itemPathElement = AlephConstants.ITEM_PATH_ELEMENT;
			userPathElement = AlephConstants.USER_PATH_ELEMENT;
			itemsElement = AlephConstants.ITEMS_ELEMENT;

		} catch (ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}

	}

	private String normalizeItem(String item) {
		while (item.length() < bibIdLength) {
			item = "0" + item;
		}
		return item;
	}

	/**
	 * @param itemId
	 * @param bibliographicDescription
	 * @param circulationStatus
	 * @param holdQueueLnegth
	 * @param itemDesrciption
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws AlephException
	 */
	public AlephItem lookupItem(String itemId, boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLnegth, boolean itemDesrciption) throws ParserConfigurationException, IOException, SAXException, AlephException {
		itemId = normalizeItem(itemId);
		URL url = new URLBuilder().setBase(serverName, serverPort).setPath(serverSuffix, itemPathElement, bibLibrary + itemId, itemsElement).addRequest("view", "full").toURL();
		Document xml = new AlephResponder(url).getXMLResponse();
		if (xml.hasChildNodes()) {
			AlephItem item = new AlephParser(xml).toAlephItem(bibliographicDescription, circulationStatus, holdQueueLnegth, itemDesrciption);
			return item;
		} else {
			throw new AlephException("Server XML response has no child nodes.");
		}
	}

}