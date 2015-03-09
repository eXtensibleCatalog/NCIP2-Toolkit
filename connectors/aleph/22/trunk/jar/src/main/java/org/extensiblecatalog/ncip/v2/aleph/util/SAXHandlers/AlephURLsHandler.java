package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

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
	 * <br>
	 * Basically it is used with LookupItemSetService in which there could be set MaximumItemsCount declaring desired count of items to output.
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
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTRIBUTE);
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

	public boolean haveParsedMaxLinks() {
		return parsedMax;
	}

	public boolean haveParsedAll() {
		return parsedAll;
	}
}
