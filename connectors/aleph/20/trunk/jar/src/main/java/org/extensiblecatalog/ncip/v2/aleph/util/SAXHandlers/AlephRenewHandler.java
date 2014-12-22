package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.GregorianCalendar;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephRenewItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephRenewHandler extends DefaultHandler {

	private AlephRenewItem renewItem;

	private String itemIdToLookFor;
	private String loanLink;
	private String status;
	private String replyText;

	private String bibDocNumber;
	private String itemDocNumber;
	private String itemSequenceNumber;

	private boolean loanFound = false;
	private boolean renewable;
	private boolean returnedOkResponseCode;

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;
	private boolean statusReached = false;
	private boolean newDueDateReached = false;

	private boolean bibDocNoReached = false;
	private boolean itemDocNoReached = false;
	private boolean itemSequenceReached = false;

	private boolean itemFullIdFound;

	/**
	 * This constructor is used to initialize parser for outputs of successful renewals.
	 * 
	 * @param itemIdToLookFor
	 * @param renewItem
	 */
	public AlephRenewHandler(String itemIdToLookFor, AlephRenewItem renewItem) {
		this.itemIdToLookFor = itemIdToLookFor;
		this.renewItem = renewItem;

		// Expect there will be data needed to construct Item Id (item doc no., item bib doc no. & agency id)
		// If one of these is not found, then this boolean is set to false
		itemFullIdFound = true;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.LOAN_ITEM_NODE)) {

			String link = attributes.getValue(AlephConstants.HREF_NODE_ATTRIBUTE);

			if (link != null && link.contains(itemIdToLookFor)) {
				loanLink = link;
				loanFound = true;

				String renewAttr = attributes.getValue(AlephConstants.RENEW_NODE_ATTRIBUTE);

				if (renewAttr != null && renewAttr.equalsIgnoreCase(AlephConstants.YES)) {
					renewable = true;
				} else
					renewable = false;
			}
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
			replyCodeReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE)) {
			replyTextReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE)) {
			statusReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.NEW_DUE_DATE_NODE)) {
			newDueDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
			itemDocNoReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE)) {
			bibDocNoReached = true;
		} else if (qName.matches(AlephConstants.Z36_ITEM_SEQUENCE_NODE)) {
			itemSequenceReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE) && replyCodeReached) {
			replyCodeReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE) && replyTextReached) {
			replyTextReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.STATUS_NODE) && statusReached) {
			statusReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.NEW_DUE_DATE_NODE) && newDueDateReached) {
			newDueDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && itemDocNoReached) {
			itemFullIdFound = false;
			itemDocNoReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE) && bibDocNoReached) {
			itemFullIdFound = false;
			bibDocNoReached = false;
		} else if (qName.matches(AlephConstants.Z36_ITEM_SEQUENCE_NODE) && itemSequenceReached) {
			itemFullIdFound = false;
			itemSequenceReached = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (replyCodeReached) {
			String replyCodeParsed = new String(ch, start, length);
			if (replyCodeParsed.equalsIgnoreCase(AlephConstants.SUCCESS_REPLY_CODE)) {
				returnedOkResponseCode = true;
			} else
				returnedOkResponseCode = false;
		} else if (replyTextReached) {
			replyText = new String(ch, start, length);
			replyTextReached = false;
		} else if (statusReached) {
			status = new String(ch, start, length);
			statusReached = false;
		} else if (newDueDateReached) {
			GregorianCalendar newDueDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			renewItem.setDateDue(newDueDate);
			newDueDateReached = false;
		} else if (itemDocNoReached) {
			itemDocNumber = new String(ch, start, length);
			itemDocNoReached = false;
		} else if (bibDocNoReached) {
			bibDocNumber = new String(ch, start, length);
			bibDocNoReached = false;
		} else if (itemSequenceReached) {
			itemSequenceNumber = new String(ch, start, length);
			itemSequenceReached = false;
		}
	}

	public boolean loanWasFound() {
		return loanFound;
	}

	public boolean isRenewable() {
		return renewable;
	}

	public String getLoanLink() {
		return loanLink;
	}

	public boolean actionSucceeded() {
		return returnedOkResponseCode;
	}

	public String getReplyText() {
		return replyText;
	}

	public String getStatusText() {
		return status;
	}

	/**
	 * Returns false if itemDocNo or bibDocNo or itemSeqNo was not found. 
	 * 
	 * @return itemFullIdFound
	 */
	public boolean isFullIdFound() {
		return itemFullIdFound;
	}

	public String getBibDocNumber() {
		return bibDocNumber;
	}

	public String getItemDocNumber() {
		return itemDocNumber;
	}

	public String getItemSequenceNumber() {
		return itemSequenceNumber;
	}

}