package org.extensiblecatalog.ncip.v2.aleph.util.sax_handlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephURLsHandler extends DefaultHandler {

	private List<String> links;

	private int totalItems;

	private int atLinkIndex;

	private int fromIndex;
	private int maxLinks;

	private boolean parsedAll;

	private boolean parsedMax;

	/**
	 * AlephURLsHandler is a SAX parser used to parse links from href attributes within &lt;item&gt; node in defined range.<br>
	 *
	 * @param {@link Integer} fromIndex
	 * @param {@link Integer} maxLinks
	 */
	public AlephURLsHandler(int fromIndex, int maxLinks) {
		this.fromIndex = fromIndex;
		this.maxLinks = maxLinks;
		atLinkIndex = -1;
		parsedAll = false;
		parsedMax = false;
		links = new ArrayList<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEM_NODE)) {
			++atLinkIndex;
			if (!parsedMax && atLinkIndex >= fromIndex) {
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
				if (link != null && !link.isEmpty()) {
					links.add(link);
					parsedMax = maxLinks == links.size();
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.ITEMS_NODE)) {

			totalItems = ++atLinkIndex;

			parsedAll = ((totalItems - fromIndex) == links.size());
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

	}

	public int getTotalItemsCount() {
		return totalItems;
	}

	public List<String> getLinks() {
		return links;
	}

	public int getNextLinkIndex() {
		return fromIndex + links.size();
	}

	public boolean haveParsedMax() {
		return parsedMax;
	}

	public boolean haveParsedAll() {
		return parsedAll;
	}
}
