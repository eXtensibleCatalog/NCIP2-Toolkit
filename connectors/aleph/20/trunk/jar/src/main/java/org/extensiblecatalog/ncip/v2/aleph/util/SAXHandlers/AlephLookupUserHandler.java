package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.user.AlephRestDlfUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.LocalConfig;
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
public class AlephLookupUserHandler extends DefaultHandler {

	private LocalConfig localConfig;

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

	private boolean z304address1Reached = false;
	private boolean z304address2Reached = false;
	private boolean z304address3Reached = false;
	private boolean z304address4Reached = false;
	private boolean z304emailReached = false;
	private boolean z304telephone1Reached = false;

	private boolean z305borStatusReached = false;
	private boolean z305expiryDateReached = false;

	private boolean z31netSumReached = false;
	private boolean z31DateReached = false;
	private boolean z31descriptionReached = false;

	private boolean patronOrConsortialblockReached = false;
	private boolean translateChangeActiveLibraryReached = false;
	private boolean noteWithCashParamReached = false;
	private boolean openSumReached = false;

	private boolean agenciesSetToBlockOrTraps = false;

	private boolean parsingAddress = false;
	private boolean parsingCirculations = false;
	private boolean parsingCash = false;
	private boolean parsingBlockOrTraps = false;
	private boolean parsingRegistration = false;

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
	public AlephLookupUserHandler(LookupUserInitiationData initData, LocalConfig localConfig) throws AlephException {

		this.localConfig = localConfig;

		userAddressInformationDesired = initData.getUserAddressInformationDesired();
		nameInformationDesired = initData.getNameInformationDesired();
		userIdDesired = initData.getUserIdDesired();

		user = new AlephRestDlfUser();

		blockOrTraps = new ArrayList<BlockOrTrap>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (parsingAddress) {
			if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_1_NODE) && userIdDesired) {
				z304address1Reached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_2_NODE) && nameInformationDesired) {
				z304address2Reached = true;
			} else if (userAddressInformationDesired) {
				if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_3_NODE)) {// Adress
					z304address3Reached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z304_ADDRESS_4_NODE)) {// City
					z304address4Reached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z304_EMAIL_NODE)) {// E-mails
					z304emailReached = true;
				} else if (qName.equalsIgnoreCase(AlephConstants.Z304_TELEPHONE_1_NODE)) {// Phone No.
					z304telephone1Reached = true;
				}
			}
		} else if (parsingCirculations) {
			if (qName.equalsIgnoreCase(AlephConstants.NOTE_NODE)) {
				String type = attributes.getValue(AlephConstants.TYPE_NODE_ATTR);

				if (type != null && type.equalsIgnoreCase(AlephConstants.PARAM_CASH))
					noteWithCashParamReached = true;
			}
		} else if (parsingCash) {
			if (qName.equalsIgnoreCase(AlephConstants.OPEN_SUM_NODE)) {
				openSumReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NET_SUM_NODE)) {
				z31netSumReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DATE_NODE)) {
				z31DateReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z31_DESCRIPTION_NODE)) {
				z31descriptionReached = true;
			}
		} else if (parsingBlockOrTraps) {
			if (qName.matches(AlephConstants.PATRON_BLOCK_NODE + "|" + AlephConstants.CONSORTIAL_BLOCK_NODE)) {
				patronOrConsortialblockReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE)) {
				translateChangeActiveLibraryReached = true;
			}
		} else if (parsingRegistration) {
			if (qName.equalsIgnoreCase(AlephConstants.Z305_BOR_STATUS_NODE)) {
				z305borStatusReached = true;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z305_EXPIRY_DATE_NODE)) {
				z305expiryDateReached = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// If was node empty, we now need to set it's boolean to false
		if (parsingAddress) {
			z304address1Reached = false;
			z304address2Reached = false;

			if (userAddressInformationDesired) {
				z304address3Reached = false;
				z304address4Reached = false;
				z304emailReached = false;
				z304telephone1Reached = false;
			}

			if (qName.equalsIgnoreCase(AlephConstants.GET_PAT_ADRS_NODE))
				parsingAddress = false;

		} else if (parsingCirculations) {

			noteWithCashParamReached = false;

			if (qName.equalsIgnoreCase(AlephConstants.GET_CIRC_ACTS_NODE))
				parsingCirculations = false;

		} else if (parsingCash) {

			openSumReached = false;
			z31netSumReached = false;
			z31DateReached = false;

			if (z31descriptionReached) {
				fineDescription = "";
				z31descriptionReached = false;
			} else if (qName.equalsIgnoreCase(AlephConstants.Z31_NODE)) {

				if (fineDate != null && fineSum != null)
					user.addAccountDetails(fineDate, fineSum, fineDescription);

			} else if (qName.equalsIgnoreCase(AlephConstants.PAT_CASH_LIST_NODE))
				parsingCash = false;

		} else if (parsingBlockOrTraps) {

			patronOrConsortialblockReached = false;
			translateChangeActiveLibraryReached = false;

			if (qName.equalsIgnoreCase(AlephConstants.PAT_BLOCKS_NODE))
				parsingBlockOrTraps = false;

		} else if (parsingRegistration) {

			z305borStatusReached = false;
			z305expiryDateReached = false;

			if (qName.equalsIgnoreCase(AlephConstants.PAT_REGISTER_NODE))
				parsingRegistration = false;
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
		} else if (z31DateReached) {
			fineDate = new String(ch, start, length);
			z31DateReached = false;
		} else if (z31descriptionReached) {
			fineDescription = new String(ch, start, length);
			z31descriptionReached = false;
		} else if (noteWithCashParamReached) {
			user.setCashTypeNote(new String(ch, start, length));
			noteWithCashParamReached = false;
		} else if (z305borStatusReached) {
			user.setBorStatus(new String(ch, start, length), localConfig);
			z305borStatusReached = false;
		} else if (patronOrConsortialblockReached) {
			BlockOrTrap bot = AlephUtil.parseBlockOrTrap(new String(ch, start, length));
			blockOrTraps.add(bot);
			patronOrConsortialblockReached = false;
		} else if (translateChangeActiveLibraryReached) {
			agencyId = AlephUtil.createAgencyId(new String(ch, start, length));
			translateChangeActiveLibraryReached = false;
		} else if (z305expiryDateReached) {
			user.setValidToDate(new String(ch, start, length));
			z305expiryDateReached = false;
		}

	}

	public AlephRestDlfUser getAlephUser() {
		// Iterate over blockOrTraps list & set parsed agencyId
		if (!agenciesSetToBlockOrTraps && blockOrTraps.size() > 0) {
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

	public AlephLookupUserHandler parseAddress() {
		parsingAddress = true;
		return this;
	}

	public AlephLookupUserHandler parseCirculations() {
		parsingCirculations = true;
		return this;
	}

	public AlephLookupUserHandler parseCash() {
		parsingCash = true;
		return this;
	}

	public AlephLookupUserHandler parseBlockOrTraps() {
		parsingBlockOrTraps = true;
		return this;
	}

	public AlephLookupUserHandler parseRegistration() {
		parsingRegistration = true;
		return this;
	}
}
