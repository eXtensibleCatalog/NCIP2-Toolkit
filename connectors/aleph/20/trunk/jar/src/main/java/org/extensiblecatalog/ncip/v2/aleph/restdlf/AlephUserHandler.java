package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.util.ArrayList;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephUserHandler extends DefaultHandler {
	private AlephUser user;
	private boolean nameInformationDesired;
	private boolean userIdDesired;
	private boolean userAddressInformationDesired;
	private boolean userFiscalAccountDesired;
	private boolean userPrivilegeDesired;
	private boolean addressHandling = false;
	private boolean circulationsHandling = false;
	private boolean registrationHandling = false;
	private boolean userIdReached = false;
	private boolean nameInformationReached = false;
	private boolean userAddressReached = false;
	private boolean userCityReached = false;
	private boolean cashReached = false;
	private boolean borStatusReached = false;
	private String address;
	private String city;
	private boolean userMailReached;

	public void setAddressHandlingNow() {
		addressHandling = true;
		circulationsHandling = false;
		registrationHandling = false;
	}

	public void setCirculationsHandlingNow() {
		addressHandling = false;
		circulationsHandling = true;
		registrationHandling = false;
	}

	public void setRegistrationHandlingNow() {
		addressHandling = false;
		circulationsHandling = false;
		registrationHandling = true;
	}

	/**
	 * Initializes new AlephUserHandler instance with desired services in the following order:
	 * 
	 * @param nameInformationDesired
	 * @param userIdDesired
	 * @param userAddressInformationDesired
	 * @param userFiscalAccountDesired
	 * @param userPrivilegeDesired
	 * @throws AlephException
	 */
	public AlephUserHandler(boolean nameInformationDesired, boolean userIdDesired, boolean userAddressInformationDesired, boolean userFiscalAccountDesired,
			boolean userPrivilegeDesired) throws AlephException {
		this.nameInformationDesired = nameInformationDesired;
		this.userIdDesired = userIdDesired;
		this.userAddressInformationDesired = userAddressInformationDesired;
		this.userFiscalAccountDesired = userFiscalAccountDesired;
		this.userPrivilegeDesired = userPrivilegeDesired;
	}

	public void setAlephUser(AlephUser alephUser) {
		user = alephUser;
	}

	public AlephUser getAlephUser() {
		return user;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (addressHandling) {
			if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_1_NODE) && userIdDesired) {
				userIdReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE) && nameInformationDesired) {
				nameInformationReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE) && userAddressInformationDesired) {// Adress
				userAddressReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE) && userAddressInformationDesired) {// City
				userCityReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE) && userAddressInformationDesired) {// E-mails
				userMailReached = true;
			}
		} else if (circulationsHandling) { // Note that if there was a need to parse transaction overview, circulationsHandling boolean would be handy - please do not delete it
			if (qName.equalsIgnoreCase(AlephConstants.TOTAL_NODE) && attributes.getValue(AlephConstants.TYPE_NODE_ATTR).equalsIgnoreCase(AlephConstants.PARAM_CASH)
					&& userFiscalAccountDesired) {
				cashReached = true;
			}
		} else if (registrationHandling) { // Note that user registration expiration can be parsed on this site under <z305-expiry-date> node
			if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && userPrivilegeDesired) {
				borStatusReached = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Element can be empty, therefore it is needed to falsificate bools set to true
		// TODO: Add error messages ... see {@link AlephItemHandler}
		if (addressHandling) {
			if (userIdReached) {
				userIdReached = false;
			} else if (nameInformationReached) {
				nameInformationReached = false;
			} else if (userAddressReached) {// Adress
				userAddressReached = false;
			} else if (userCityReached) {// City
				userCityReached = false;
			} else if (userMailReached) {// Mail
				userMailReached = false;
			}
		} else if (circulationsHandling) {
			if (cashReached) {
				cashReached = false;
			}
		} else if (registrationHandling) {
			if (borStatusReached) {
				borStatusReached = false;
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (addressHandling) {
			if (userIdReached) {
				user.setUserId(new String(ch, start, length));
				userIdReached = false;
			} else if (nameInformationReached) {
				user.setFullName(new String(ch, start, length));
				nameInformationReached = false;
			} else if (userAddressReached) {// Adress
				address = new String(ch, start, length);
				if (city != null && !city.isEmpty())
					user.setPhysicalAddress(address + ", " + city);
				userAddressReached = false;
			} else if (userCityReached) {// City
				city = new String(ch, start, length);
				if (address != null && !address.isEmpty())
					user.setPhysicalAddress(address + ", " + city);
				userCityReached = false;
			} else if (userMailReached) {// mail
				user.setEmailAddress(new String(ch, start, length));
				userMailReached = false;
			}
		} else if (circulationsHandling) {
			if (cashReached) {
				user.setAccountBalance(new String(ch, start, length));
				cashReached = false;
			}
		} else if (registrationHandling) {
			if (borStatusReached) {
				user.setBorStatus(new String(ch, start, length));
				borStatusReached = false;
			}
		}
	}
}
