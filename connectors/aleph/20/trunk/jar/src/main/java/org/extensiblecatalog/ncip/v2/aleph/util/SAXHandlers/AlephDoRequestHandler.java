package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephDoRequestHandler extends DefaultHandler {

	// Required to build unique item id
	private String bibDocNumber;
	private String itemSequence;
	private String itemDocNumber;
	private LocalConfig localConfig;
	private boolean itemFullIdFound;

	private String replyCode;
	private String replyText;
	private String noteValue;
	private String requestIdVal;
	private String sequenceNumber;
	private String itemIdToLookForSeqNumber;

	private boolean errorReturned;
	private boolean deletable;

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;
	private boolean noteReached = false;
	private boolean requestNumberReached = false;
	private boolean holdRequestFound = false;

	public AlephDoRequestHandler(String itemIdVal) {
		itemIdToLookForSeqNumber = itemIdVal;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
			itemFullIdFound = true;
			
			String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
			if (link != null && link.contains(itemIdToLookForSeqNumber)) {
				holdRequestFound = true;
				// Substring last 4 characters from link - this should be sequence number
				// E.g. <hold-request delete="Y" href="http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds/MZK500013118150000200001"/>
				sequenceNumber = link.substring(link.length() - AlephConstants.SEQ_NUMBER_LENGTH);

				String deleteAttr = attributes.getValue(AlephConstants.DELETE_NODE_ATTR);
				if (deleteAttr.equalsIgnoreCase(AlephConstants.YES)) {
					deletable = true;
				} else
					deletable = false;
			}
			
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
			replyCodeReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE)) {
			replyTextReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE)) {
			noteReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
			requestNumberReached = true;
		} 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE) && replyCodeReached) {
			replyCodeReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE) && replyTextReached) {
			replyTextReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && noteReached) {
			noteReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && requestNumberReached) {
			requestNumberReached = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (replyCodeReached) {
			replyCode = new String(ch, start, length);
			if (!replyCode.equalsIgnoreCase("0000"))
				errorReturned = true;
			else
				errorReturned = false;
			replyCodeReached = false;
		} else if (replyTextReached) {
			replyText = new String(ch, start, length);
			replyTextReached = false;
		} else if (noteReached) {
			noteValue = new String(ch, start, length);
			noteReached = false;
		} else if (requestNumberReached) {
			requestIdVal = new String(ch, start, length);
			requestNumberReached = false;
		} 
	}

	/**
	 * Returns true if there was an error.
	 * 
	 * @return
	 */
	public boolean returnedError() {
		return errorReturned;
	}

	/**
	 * Returns "note" element value.
	 * 
	 * @return
	 */
	public String getNoteValue() {
		return noteValue;
	}

	/**
	 * Returns "reply-text" element value.
	 * 
	 * @return
	 */
	public String getReplyText() {
		return replyText;
	}

	/**
	 * Returns "reply-code" element value in integer format.
	 * 
	 * @return
	 */
	public int getReplyCode() {
		return Integer.parseInt(replyCode);
	}

	/**
	 * Returns "z37-request-number" element value.
	 * 
	 * @return
	 */
	public String getRequestId() {
		return requestIdVal;
	}

	/**
	 * Returns sequence number from "hold-request" element's "href" attribute value.
	 * 
	 * @return
	 */
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	
	/**
	 * Returns true, if an hold request is deletable.
	 * 
	 * @return
	 */
	public boolean isDeletable() {
		return deletable;
	}

	public boolean requestWasFound() {
		return holdRequestFound;
	}
}
