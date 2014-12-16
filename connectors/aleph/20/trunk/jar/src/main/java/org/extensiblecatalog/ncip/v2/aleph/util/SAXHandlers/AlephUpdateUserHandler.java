package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephUpdateUserHandler extends DefaultHandler {

	private boolean isUpdateable = false;

	private String replyCode;
	private String replyText;

	// Mandatory fields
	private boolean z304address1Reached = false;
	private boolean z304dateFromReached = false;
	private boolean z304dateToReached = false;

	// Values of mandatory fields
	private String z304address1;
	private String z304dateFrom;
	private String z304dateTo;

	// Optional Fields
	private boolean z304address2Reached = false;
	private boolean z304address3Reached = false;
	private boolean z304address4Reached = false;
	private boolean z304emailReached = false;
	private boolean z304telephone1Reached = false;

	private boolean replyCodeReached = false;
	private boolean replyTextReached = false;

	private boolean parsingMandatoryFields;

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
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE)) {// Adress
				z304address3Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE)) {// City
				z304address4Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE)) {// E-mails
				z304emailReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE)) {// Phone No.
				z304telephone1Reached = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
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
			z304telephone1Reached = false;
			z304emailReached = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingMandatoryFields) {
			if (z304address1Reached) {
				z304address1 = new String(ch, start, length);
				z304address1Reached = false;
			} else if (z304dateFromReached) {
				z304dateFrom = new String(ch, start, length);
				z304dateFromReached = false;
			} else if (z304dateToReached) {
				z304dateTo = new String(ch, start, length);
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

				z304address2Reached = false;
			} else if (z304address3Reached) {// Adress

				z304address3Reached = false;
			} else if (z304address4Reached) {// City

				z304address4Reached = false;
			} else if (z304emailReached) {// mail

				z304emailReached = false;
			} else if (z304telephone1Reached) {// Phone No.

				z304telephone1Reached = false;
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

	public String getZ304address1() {
		return z304address1;
	}

	public String getZ304dateFrom() {
		return z304dateFrom;
	}

	public String getZ304dateTo() {
		return z304dateTo;
	}

	public boolean isUpdateable() {
		return isUpdateable;
	}

	public boolean parsedAllMandatoryFields() {
		return !isNullOrEmpty(z304address1) && !isNullOrEmpty(z304dateFrom) && !isNullOrEmpty(z304dateTo);
	}

	private boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}

}
