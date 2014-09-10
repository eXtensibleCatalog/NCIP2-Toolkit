package org.extensiblecatalog.ncip.v2.aleph.restdlf.handlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BlockOrTrap;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephUserHandler extends DefaultHandler {
	private AlephUser user;
	private String address;
	private AgencyId agencyId;
	private String city;
	private List<BlockOrTrap> blockOrTraps;
	private boolean nameInformationDesired;
	private boolean userIdDesired;
	private boolean userAddressInformationDesired;
	private boolean blockOrTrapDesired;
	private boolean userFiscalAccountDesired;
	private boolean userPrivilegeDesired;
	private boolean userIdReached = false;
	private boolean nameInformationReached = false;
	private boolean userAddressReached = false;
	private boolean userCityReached = false;
	private boolean cashReached = false;
	private boolean borStatusReached = false;
	private boolean userMailReached = false;
	private boolean phoneNoReached = false;
	private boolean blockOrTrapReached = false;
	private boolean agencyReached;

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
	public AlephUserHandler(LookupUserInitiationData initData) throws AlephException {
		this.blockOrTrapDesired = initData.getBlockOrTrapDesired();
		this.nameInformationDesired = initData.getNameInformationDesired();
		this.userIdDesired = initData.getUserIdDesired();
		this.userAddressInformationDesired = initData.getUserAddressInformationDesired();
		this.userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		this.userPrivilegeDesired = initData.getUserPrivilegeDesired();
		this.blockOrTraps = new ArrayList<BlockOrTrap>();
	}

	public void setAlephUser(AlephUser alephUser) {
		user = alephUser;
	}

	public AlephUser getAlephUser() {
		// Iterate over blockOrTraps list & set parsed agencyId
		if (agencyId != null) {
			for (BlockOrTrap blockOrTrap : blockOrTraps) {
				blockOrTrap.setAgencyId(agencyId);
			}
			user.setBlockOrTraps(blockOrTraps);
		}
		return user;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE) && userAddressInformationDesired) {// Phone No.
			phoneNoReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.TOTAL_NODE) && attributes.getValue(AlephConstants.TYPE_NODE_ATTR).equalsIgnoreCase(AlephConstants.PARAM_CASH)
				&& userFiscalAccountDesired) {
			cashReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && userPrivilegeDesired) {
			borStatusReached = true;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && blockOrTrapDesired) {
			blockOrTrapReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
			agencyReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Element can be empty, therefore it is needed to falsificate bools set to true

		if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_1_NODE) && userIdReached) {
			userIdReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE) && nameInformationReached) {
			nameInformationReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE) && userAddressReached) {// Adress
			userAddressReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE) && userCityReached) {// City
			userCityReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE) && userMailReached) {// Mail
			userMailReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE) && phoneNoReached) {// Phone No.
			phoneNoReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.TOTAL_NODE) && cashReached) {
			cashReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && borStatusReached) {
			borStatusReached = false;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && blockOrTrapReached) {
			blockOrTrapReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE) && agencyReached) {
			agencyReached = false;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

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
		} else if (phoneNoReached) {// Phone No.
			user.setPhoneNumber(new String(ch, start, length));
			phoneNoReached = false;
		} else if (cashReached) {
			user.setAccountBalance(new String(ch, start, length));
			cashReached = false;
		} else if (borStatusReached) {
			user.setBorStatus(new String(ch, start, length));
			borStatusReached = false;
		} else if (blockOrTrapReached) {
			String parsedBlock = new String(ch, start, length);
			BlockOrTrap bot = AlephUtil.parseBlockOrTrap(parsedBlock);
			blockOrTraps.add(bot);
			blockOrTrapReached = false;
		} else if (agencyReached) {
			String parsedAgencyId = new String(ch, start, length);
			agencyId = new AgencyId("http://www.niso.org/ncip/v1_0/schemes/agencyidtype/agencyidtype.scm", parsedAgencyId);
			agencyReached = false;
		}

	}
}
