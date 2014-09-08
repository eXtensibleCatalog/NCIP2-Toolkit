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
	private boolean parsingLoan = false;
	private boolean loanFound = false;
	private boolean requestNumberReached = false;
	private boolean hourPlacedReached = false;
	private boolean earliestDateNeededReached = false;
	private boolean needBeforeDateReached = false;
	private boolean datePlacedReached = false;
	private boolean pickupLocationReached = false;
	private boolean pickupExpiryDateReached = false;
	private boolean requestIdReached = false;
	private boolean requestTypeReached = false;
	private boolean pickupDateReached = false;
	private boolean statusReached = false;
	private boolean holdQueueLengthReached = false;
	private boolean renewable;

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

	/*		responseData.setRequestScopeType(renewItem.getRequestScopeType());
	responseData.setRequestType(renewItem.getRequestType());
	responseData.setRequestId(renewItem.getRequestId());
	responseData.setItemOptionalFields(renewItem.getItemOptionalFields());
	responseData.setUserOptionalFields(renewItem.getUserOptionalFields()); 

	// Not implemented services, most of them probably even not implementable
	responseData.setDateAvailable(renewItem.getDateAvailable());
	responseData.setHoldQueuePosition(renewItem.getHoldQueuePosition());
	responseData.setShippingInformation(renewItem.getShippingInformation());
	responseData.setAcknowledgedFeeAmount(renewItem.getAcknowledgedFeeAmout());
	responseData.setDateOfUserRequest(renewItem.getDateOfUserRequest());
	responseData.setEarliestDateNeeded(renewItem.getEarliestDateNeeded());
	responseData.setHoldQueuePosition(renewItem.getHoldQueuePosition());
	responseData.setNeedBeforeDate(renewItem.getNeedBeforeDate());
	responseData.setPaidFeeAmount(renewItem.getPaidFeeAmount());
	responseData.setPickupDate(renewItem.getHoldPickupDate());
	responseData.setPickupExpiryDate(renewItem.getPickupExpiryDate());
	responseData.setPickupLocation(renewItem.getPickupLocation());
	responseData.setRequestStatusType(renewItem.getRequestStatusType());*/
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (parsingLoan) {

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

		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (parsingLoan) {

		}
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
