package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephPatronAddress;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 */
public class AlephUpdateUserHandler extends DefaultHandler {

	private AlephPatronAddress patronAddress;

	private boolean isUpdateable = false;

	private String replyCode;
	private String replyText;

	// Mandatory fields
	private boolean z304address1Reached = false;
	private boolean z304dateFromReached = false;
	private boolean z304dateToReached = false;

	// Optional Fields
	private boolean z304address2Reached = false;
	private boolean z304address3Reached = false;
	private boolean z304address4Reached = false;
	private boolean z304address5Reached = false;

	private boolean z304telephone1Reached = false;
	private boolean z304telephone2Reached = false;
	private boolean z304telephone3Reached = false;
	private boolean z304telephone4Reached = false;

	private boolean z304emailAddressReached = false;

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;

	private boolean parsingMandatoryFields;

	public AlephUpdateUserHandler() {
		patronAddress = new AlephPatronAddress();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (parsingMandatoryFields) {
			if (qName.equalsIgnoreCase(AlephConstants.ADDRESS_INFORMATION_NODE)) {
				isUpdateable = attributes.getValue(AlephConstants.UPDATEABLE_NODE_ATTR).equalsIgnoreCase(AlephConstants.YES);
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_1_NODE)) {
				z304address1Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_FROM_NODE)) {
				z304dateFromReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_TO_NODE))
				z304dateToReached = true;
		} else {
			if (qName.equalsIgnoreCase(AlephConstants.REPLY_CODE_NODE)) {
				replyCodeReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.REPLY_TEXT_NODE)) {
				replyTextReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE)) {
				z304address2Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE)) {
				z304address3Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE)) {
				z304address4Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_5_NODE)) {
				z304address5Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE)) {
				z304telephone1Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_2_NODE)) {
				z304telephone2Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_3_NODE)) {
				z304telephone3Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_4_NODE)) {
				z304telephone4Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE)) {
				z304emailAddressReached = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Set all booleans of type "Reached something" to false in order to prevent bugs caused by empty nodes
		if (parsingMandatoryFields) {
			z304address1Reached = false;
			z304dateFromReached = false;
			z304dateToReached = false;

			if (qName.equalsIgnoreCase(AlephConstants.GET_PAT_ADRS_NODE))
				parsingMandatoryFields = false;

		} else {
			replyCodeReached = false;
			replyTextReached = false;
			
			z304address2Reached = false;
			z304address3Reached = false;
			z304address4Reached = false;
			z304address5Reached = false;
			
			z304telephone1Reached = false;
			z304telephone2Reached = false;
			z304telephone3Reached = false;
			z304telephone4Reached = false;
			
			z304emailAddressReached = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingMandatoryFields) {
			if (z304address1Reached) {
				patronAddress.setZ304address1(new String(ch, start, length));
				z304address1Reached = false;
			} else if (z304dateFromReached) {
				patronAddress.setZ304dateFrom(new String(ch, start, length));
				z304dateFromReached = false;
			} else if (z304dateToReached) {
				patronAddress.setZ304dateTo(new String(ch, start, length));
				z304dateToReached = false;
			}
		} else {

			if (replyCodeReached) {
				replyCode = new String(ch, start, length);
				replyCodeReached = false;

			} else if (replyTextReached) {
				replyText = new String(ch, start, length);
				replyTextReached = false;

			} else if (z304address2Reached) {
				patronAddress.setZ304address2(new String(ch, start, length));
				z304address2Reached = false;

			} else if (z304address3Reached) {
				patronAddress.setZ304address3(new String(ch, start, length));
				z304address3Reached = false;

			} else if (z304address4Reached) {
				patronAddress.setZ304address4(new String(ch, start, length));
				z304address4Reached = false;

			} else if (z304address5Reached) {
				patronAddress.setZ304address5(new String(ch, start, length));
				z304address5Reached = false;

			} else if (z304telephone1Reached) {
				patronAddress.setZ304telephone1(new String(ch, start, length));
				z304telephone1Reached = false;

			} else if (z304telephone2Reached) {
				patronAddress.setZ304telephone2(new String(ch, start, length));
				z304telephone2Reached = false;

			} else if (z304telephone3Reached) {
				patronAddress.setZ304telephone3(new String(ch, start, length));
				z304telephone3Reached = false;

			} else if (z304telephone4Reached) {
				patronAddress.setZ304telephone4(new String(ch, start, length));
				z304telephone4Reached = false;

			} else if (z304emailAddressReached) {
				patronAddress.setZ304emailAddress(new String(ch, start, length));
				z304emailAddressReached = false;
			}
		}
	}

	public AlephUpdateUserHandler setParsingMandatoryFields() {
		parsingMandatoryFields = true;
		return this;
	}

	public String getReplyCode() {
		return replyCode;
	}

	public String getReplyText() {
		return replyText;
	}

	public AlephPatronAddress getPatronAddress() {
		return patronAddress;
	}

	public boolean isUpdateable() {
		return isUpdateable;
	}

	public boolean parsedAllMandatoryFields() {
		return !isNullOrEmpty(patronAddress.getZ304address1()) && !isNullOrEmpty(patronAddress.getZ304dateFrom()) && !isNullOrEmpty(patronAddress.getZ304dateTo());
	}

	private boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

}
