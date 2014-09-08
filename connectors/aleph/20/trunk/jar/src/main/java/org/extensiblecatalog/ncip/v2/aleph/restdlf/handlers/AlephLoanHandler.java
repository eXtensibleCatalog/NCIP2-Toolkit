package org.extensiblecatalog.ncip.v2.aleph.restdlf.handlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRenewItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.RequestType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestStatusType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlephLoanHandler extends DefaultHandler {
	private SimpleDateFormat alephDateFormatter;
	private SimpleDateFormat alephHourFormatter;
	private TimeZone localTimeZone;
	private AlephRenewItem renewItem;
	private String itemIdToLookFor;
	private String loanLink;
	private String docNumber;
	private String itemSequenceNumber;
	private boolean parsingLoan = false;
	private boolean loanFound = false;
	private boolean renewable;
	private boolean docNoReached;
	private boolean itemSeqReached;

	public AlephLoanHandler(String itemIdToLookFor, AlephRenewItem renewItem) {
		this.itemIdToLookFor = itemIdToLookFor;
		this.renewItem = renewItem;
		alephDateFormatter = new SimpleDateFormat("yyyyMMdd");
		alephHourFormatter = new SimpleDateFormat("HHmm");
		localTimeZone = TimeZone.getTimeZone("ECT");
	}

	public AlephLoanHandler setParsingLoans() {
		parsingLoan = true;
		return this;
	}

	/*
	responseData.setFiscalTransactionInformation(renewItem.getFiscalTransactionInfo()); //TODO: Ask librarian when this service costs something & where to find those values
	responseData.setDateDue(renewItem.getDateDue());
	responseData.setDateForReturn(renewItem.getDateForReturn());
	responseData.setPending(renewItem.getPending());
	responseData.setRenewalCount(renewItem.getRenewalCount());
	responseData.setRequiredFeeAmount(renewItem.getRequiredFeeAmount());
	responseData.setRequiredItemUseRestrictionTypes(renewItem.getRequiredItemUseRestrictionTypes());
	*/
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (parsingLoan) {
			if (qName.equalsIgnoreCase(AlephConstants.Z36_DOC_NUMBER_NODE)) {
				docNoReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z36_ITEM_SEQUENCE_NODE)) {
				itemSeqReached = true;
			}
		} else {
			if (qName.equalsIgnoreCase(AlephConstants.LOAN_ITEM_NODE)) {
				String link = attributes.getValue(AlephConstants.HREF_NODE_ATTR);
				if (link.indexOf(itemIdToLookFor) > -1) {
					loanLink = link;
					loanFound = true;
					String renewAttr = attributes.getValue(AlephConstants.RENEW_NODE_ATTR);
					if (renewAttr.equalsIgnoreCase(AlephConstants.YES)) {
						renewable = true;
					} else
						renewable = false;
				}
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (parsingLoan) {
			if (qName.equalsIgnoreCase(AlephConstants.Z36_DOC_NUMBER_NODE) && docNoReached) {
				docNoReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z36_ITEM_SEQUENCE_NODE) && itemSeqReached) {
				itemSeqReached = false;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingLoan) {
			if (docNoReached) {
				docNumber = new String(ch, start, length);
				docNoReached = false;
			} else if (itemSeqReached) {
				itemSequenceNumber = new String(ch, start, length);
				itemSeqReached = false;
			}
		}
	}
	
	public String getDocNumber() {
		return docNumber;
	}
	
	public String getItemSequenceNumber() {
		return itemSequenceNumber;
	}

	public String getLoanLink() {
		return loanLink;
	}

	public boolean loanWasFound() {
		return loanFound;
	}
	
	public boolean isRenewable() {
		return renewable;
	}
}
