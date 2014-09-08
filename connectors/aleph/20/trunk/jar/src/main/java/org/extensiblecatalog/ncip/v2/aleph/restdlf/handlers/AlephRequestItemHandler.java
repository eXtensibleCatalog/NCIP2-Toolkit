package org.extensiblecatalog.ncip.v2.aleph.restdlf.handlers;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephRequestItemHandler extends DefaultHandler {

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;
	private boolean noteReached = false;
	private boolean requestNumberReached = false;
	private String replyCode;
	private String replyText;
	private String noteValue;
	private String requestId;
	private boolean errorReturned;
	private boolean parsingSequenceNumber = false;
	private boolean deletable;
	private boolean holdRequestFound = false;
	private String itemIdToLookForSeqNumber;
	private String sequenceNumber;

	/**
	 * Sets the parser to parse sequence number of requested item matching supplied String. <br/>
	 * Once is appropriate sequence number found, this functionality is turned off.
	 * 
	 * @return
	 */
	public AlephRequestItemHandler parseSequenceNumber(String itemId) {
		itemIdToLookForSeqNumber = itemId;
		parsingSequenceNumber = true;
		return this;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!parsingSequenceNumber) {
			if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
				replyCodeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE)) {
				replyTextReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE)) {
				noteReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE)) {
				requestNumberReached = true;
			}
		} else { // parsing sequence number from e.g. http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds
			if (qName.equalsIgnoreCase(AlephConstants.HOLD_REQUEST_NODE)) {
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
				if (link.indexOf(itemIdToLookForSeqNumber) > -1) {
					holdRequestFound = true;
					// Substring last 4 characters from link - this should be sequence number
					// E.g. <hold-request delete="Y" href="http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds/MZK500013118150000200001"/>
					sequenceNumber = link.substring(link.length() - AlephConstants.SEQ_NUMBER_LENGTH);

					String deleteAttr = attributes.getValue(AlephConstants.DELETE_NODE_ATTR);
					if (deleteAttr.equalsIgnoreCase(AlephConstants.YES)) {
						deletable = true;
					} else
						deletable = false;
					parsingSequenceNumber = false;
				}
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!parsingSequenceNumber) {
			if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE) && replyCodeReached) {
				replyCodeReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE) && replyTextReached) {
				replyTextReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && noteReached) {
				noteReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z37_REQUEST_NUMBER_NODE) && requestNumberReached) {
				requestNumberReached = false;
			}
		} else { // nothing
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (!parsingSequenceNumber) {
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
				requestId = new String(ch, start, length);
				requestNumberReached = false;
			}
		} else { // nothing
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
		return requestId;
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
