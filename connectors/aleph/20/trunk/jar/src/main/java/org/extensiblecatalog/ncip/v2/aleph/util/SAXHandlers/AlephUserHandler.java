package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.user.AlephRestDlfUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
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
	private AlephRestDlfUser user;
	private String address;
	private AgencyId agencyId;
	private String city;
	private String fineDate;
	private String fineSum;
	private String fineDescription;
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
	private boolean agencyReached = false;
	private boolean validUntilDateReached = false;
	private boolean validFromDateReached = false;
	private boolean netSumReached = false;
	private boolean fineDateReached = false;
	private boolean descriptionReached = false;
	private boolean cashTypeNoteReached = false;

	private boolean agenciesSetToBlockOrTraps = false;

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
		user = new AlephRestDlfUser();

		blockOrTrapDesired = initData.getBlockOrTrapDesired();
		nameInformationDesired = initData.getNameInformationDesired();
		userIdDesired = initData.getUserIdDesired();
		userAddressInformationDesired = initData.getUserAddressInformationDesired();
		userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		userPrivilegeDesired = initData.getUserPrivilegeDesired();
		blockOrTraps = new ArrayList<BlockOrTrap>();
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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NODE) && userFiscalAccountDesired) {
			fineDate = null;
			fineSum = null;
			fineDescription = null;
		} else if (qName.equalsIgnoreCase(AlephConstants.OPEN_SUM_NODE) && userFiscalAccountDesired) {
			cashReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NET_SUM_NODE) && userFiscalAccountDesired) {
			netSumReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_FINE_DATE_NODE) && userFiscalAccountDesired) {
			fineDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DESCRIPTION_NODE) && userFiscalAccountDesired) {
			descriptionReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && userFiscalAccountDesired) {
			String type = attributes.getValue(AlephConstants.TYPE_NODE_ATTR);
			if (type.equalsIgnoreCase(AlephConstants.PARAM_CASH))
				cashTypeNoteReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && userPrivilegeDesired) {
			borStatusReached = true;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && blockOrTrapDesired) {
			blockOrTrapReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
			agencyReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_TO_NODE) && userPrivilegeDesired) {
			validUntilDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_FROM_NODE) && userPrivilegeDesired) {
			validFromDateReached = true;
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
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NODE) && fineDate != null && fineSum != null) {
			user.addAccountDetails(fineDate, fineSum, fineDescription);
		} else if (qName.equalsIgnoreCase(AlephConstants.OPEN_SUM_NODE) && cashReached) {
			cashReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NET_SUM_NODE) && netSumReached) {
			netSumReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_FINE_DATE_NODE) && fineDateReached) {
			fineDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DESCRIPTION_NODE) && descriptionReached) {
			fineDescription = "";
			descriptionReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE)) {
			cashTypeNoteReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && borStatusReached) {
			borStatusReached = false;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && blockOrTrapReached) {
			blockOrTrapReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE) && agencyReached) {
			agencyReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_TO_NODE) && validUntilDateReached) {
			validUntilDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_FROM_NODE) && validFromDateReached) {
			validFromDateReached = false;
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
		} else if (netSumReached) {
			fineSum = new String(ch, start, length);
			netSumReached = false;
		} else if (fineDateReached) {
			fineDate = new String(ch, start, length);
			fineDateReached = false;
		} else if (descriptionReached) {
			fineDescription = new String(ch, start, length);
			descriptionReached = false;
		} else if (cashTypeNoteReached) {
			user.setCashTypeNote(new String(ch, start, length));
			cashTypeNoteReached = false;
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
		} else if (validUntilDateReached) {
			user.setValidToDate(new String(ch, start, length));
			validUntilDateReached = false;
		} else if (validFromDateReached) {
			user.setValidFromDate(new String(ch, start, length));
			validFromDateReached = false;
		}

	}
	
	public AlephRestDlfUser getAlephUser() {
		// Iterate over blockOrTraps list & set parsed agencyId
		if (!agenciesSetToBlockOrTraps) {
			if (agencyId != null) {
				for (BlockOrTrap blockOrTrap : blockOrTraps) {
					blockOrTrap.setAgencyId(agencyId);
				}
				user.setBlockOrTraps(blockOrTraps);
			}
			agenciesSetToBlockOrTraps = true;
		}
		return user;
	}
}
