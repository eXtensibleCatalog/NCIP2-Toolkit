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
	
	private boolean z304address1Reached = false;
	private boolean z304address2Reached = false;
	private boolean z304address3Reached = false;
	private boolean z304address4Reached = false;
	private boolean z305borStatusReached = false;
	private boolean z304emailReached = false;
	private boolean z304telephone1Reached = false;
	private boolean z304dateToReached = false;
	private boolean z304dateFromReached = false;
	
	private boolean z31netSumReached = false;
	private boolean z31fineDateReached = false;
	private boolean z31descriptionReached = false;
	
	private boolean patronOrConsortialblockReached = false;
	private boolean translateChangeActiveLibraryReached = false;
	private boolean noteWithCashParamReached = false;
	private boolean openSumReached = false;

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
			z304address1Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE) && nameInformationDesired) {
			z304address2Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE) && userAddressInformationDesired) {// Adress
			z304address3Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE) && userAddressInformationDesired) {// City
			z304address4Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE) && userAddressInformationDesired) {// E-mails
			z304emailReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE) && userAddressInformationDesired) {// Phone No.
			z304telephone1Reached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NODE) && userFiscalAccountDesired) {
			fineDate = null;
			fineSum = null;
			fineDescription = null;
		} else if (qName.equalsIgnoreCase(AlephConstants.OPEN_SUM_NODE) && userFiscalAccountDesired) {
			openSumReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NET_SUM_NODE) && userFiscalAccountDesired) {
			z31netSumReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_FINE_DATE_NODE) && userFiscalAccountDesired) {
			z31fineDateReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DESCRIPTION_NODE) && userFiscalAccountDesired) {
			z31descriptionReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && userFiscalAccountDesired) {
			String type = attributes.getValue(AlephConstants.TYPE_NODE_ATTR);
			if (type.equalsIgnoreCase(AlephConstants.PARAM_CASH))
				noteWithCashParamReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && userPrivilegeDesired) {
			z305borStatusReached = true;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && blockOrTrapDesired) {
			patronOrConsortialblockReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
			translateChangeActiveLibraryReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_TO_NODE) && userPrivilegeDesired) {
			z304dateToReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_FROM_NODE) && userPrivilegeDesired) {
			z304dateFromReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Element can be empty, therefore it is needed to falsificate bools set to true

		if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_1_NODE) && z304address1Reached) {
			z304address1Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE) && z304address2Reached) {
			z304address2Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE) && z304address3Reached) {// Adress
			z304address3Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE) && z304address4Reached) {// City
			z304address4Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE) && z304emailReached) {// Mail
			z304emailReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE) && z304telephone1Reached) {// Phone No.
			z304telephone1Reached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.OPEN_SUM_NODE) && openSumReached) {
			openSumReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NET_SUM_NODE) && z31netSumReached) {
			z31netSumReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_FINE_DATE_NODE) && z31fineDateReached) {
			z31fineDateReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DESCRIPTION_NODE) && z31descriptionReached) {
			fineDescription = "";
			z31descriptionReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE) && noteWithCashParamReached) {
			noteWithCashParamReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE) && z305borStatusReached) {
			z305borStatusReached = false;
		} else if ((qName.equalsIgnoreCase(AlephConstants.PATRON_BLOCK_NODE) || qName.equalsIgnoreCase(AlephConstants.CONSORTIAL_BLOCK_NODE)) && patronOrConsortialblockReached) {
			patronOrConsortialblockReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE) && translateChangeActiveLibraryReached) {
			translateChangeActiveLibraryReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_TO_NODE) && z304dateToReached) {
			z304dateToReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z304_DATE_FROM_NODE) && z304dateFromReached) {
			z304dateFromReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NODE) && fineDate != null && fineSum != null) {
			user.addAccountDetails(fineDate, fineSum, fineDescription);
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (z304address1Reached) {
			user.setUserId(new String(ch, start, length));
			z304address1Reached = false;
		} else if (z304address2Reached) {
			user.setFullName(new String(ch, start, length));
			z304address2Reached = false;
		} else if (z304address3Reached) {// Adress
			address = new String(ch, start, length);
			if (city != null && !city.isEmpty())
				user.setPhysicalAddress(address + ", " + city);
			z304address3Reached = false;
		} else if (z304address4Reached) {// City
			city = new String(ch, start, length);
			if (address != null && !address.isEmpty())
				user.setPhysicalAddress(address + ", " + city);
			z304address4Reached = false;
		} else if (z304emailReached) {// mail
			user.setEmailAddress(new String(ch, start, length));
			z304emailReached = false;
		} else if (z304telephone1Reached) {// Phone No.
			user.setPhoneNumber(new String(ch, start, length));
			z304telephone1Reached = false;
		} else if (openSumReached) {
			user.setAccountBalance(new String(ch, start, length));
			openSumReached = false;
		} else if (z31netSumReached) {
			fineSum = new String(ch, start, length);
			z31netSumReached = false;
		} else if (z31fineDateReached) {
			fineDate = new String(ch, start, length);
			z31fineDateReached = false;
		} else if (z31descriptionReached) {
			fineDescription = new String(ch, start, length);
			z31descriptionReached = false;
		} else if (noteWithCashParamReached) {
			user.setCashTypeNote(new String(ch, start, length));
			noteWithCashParamReached = false;
		} else if (z305borStatusReached) {
			user.setBorStatus(new String(ch, start, length));
			z305borStatusReached = false;
		} else if (patronOrConsortialblockReached) {
			String parsedBlock = new String(ch, start, length);
			BlockOrTrap bot = AlephUtil.parseBlockOrTrap(parsedBlock);
			blockOrTraps.add(bot);
			patronOrConsortialblockReached = false;
		} else if (translateChangeActiveLibraryReached) {
			String parsedAgencyId = new String(ch, start, length);
			agencyId = AlephUtil.createAgencyId(parsedAgencyId);
			translateChangeActiveLibraryReached = false;
		} else if (z304dateToReached) {
			user.setValidToDate(new String(ch, start, length));
			z304dateToReached = false;
		} else if (z304dateFromReached) {
			user.setValidFromDate(new String(ch, start, length));
			z304dateFromReached = false;
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
