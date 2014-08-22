package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class AlephResponder {

	public AlephResponder(URL httpRequest) {
		url = httpRequest;
	}

	public static URL url;

	protected Document getXMLResponse() throws ParserConfigurationException,
			IOException, SAXException {
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		if (url != null) {

			// Send data
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			// Get the response
			doc = docBuilder.parse(conn.getInputStream());
		}
		return doc;
	}
}
