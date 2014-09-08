package org.extensiblecatalog.ncip.v2.aleph.restdlf.user;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.service.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A user that exists within Aleph. This is most likely returned by the authenticate or lookup methods within the AlephAPI class
 * 
 * @author rjohns14
 *
 */

public class AlephUser implements Serializable {

	private static final long serialVersionUID = 65005L;
	private String username;
	private String fullName;
	private String authenticatedUserName;

	private List<RequestedItem> requestedItems;
	private List<LoanedItem> loanedItems;
	private List<AlephItem> fineItems;
	private List<String> blocks;
	private List<String> notes;

	// the current session id for this authenticated aleph user
	private String sessionId;

	private AlephAgency agency;
	private NameInformation nameInfo;
	private List<BlockOrTrap> blockOrTraps;
	private List<UserAddressInformation> userAddrInfos;
	private List<UserId> userIds;
	private List<UserPrivilege> userPrivileges;
	private UserFiscalAccountSummary userFiscalAccountSummary;

	public AlephUser() {
		requestedItems = new ArrayList<RequestedItem>();
		loanedItems = new ArrayList<LoanedItem>();
		blockOrTraps = new ArrayList<BlockOrTrap>();
		userAddrInfos = new ArrayList<UserAddressInformation>();
		userIds = new ArrayList<UserId>();
		userPrivileges = new ArrayList<UserPrivilege>();
		userFiscalAccountSummary = new UserFiscalAccountSummary();
		nameInfo = new NameInformation();
	}

	/**
	 * Set the username for this aleph user
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the username for this aleph user
	 * 
	 * @return the user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the full name
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
		setNameInformation(fullName);
	}

	/**
	 * Get the full name
	 * 
	 * @return full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Set the user's permanent address
	 * 
	 * @param address
	 */
	public void setPhysicalAddress(String address) {

		UserAddressInformation uai = new UserAddressInformation();
		PhysicalAddress physicalAddress = new PhysicalAddress();
		UnstructuredAddress unstructuredAddress = new UnstructuredAddress();

		unstructuredAddress.setUnstructuredAddressData(address);
		unstructuredAddress.setUnstructuredAddressType(Version1UnstructuredAddressType.NEWLINE_DELIMITED_TEXT);

		physicalAddress.setUnstructuredAddress(unstructuredAddress);
		physicalAddress.setPhysicalAddressType(Version1PhysicalAddressType.POSTAL_ADDRESS);

		uai.setPhysicalAddress(physicalAddress);

		uai.setUserAddressRoleType(Version1UserAddressRoleType.SHIP_TO);

		userAddrInfos.add(uai);

	}

	/**
	 * Set the email address
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {

		UserAddressInformation uai = new UserAddressInformation();
		ElectronicAddress electronicAddress = new ElectronicAddress();

		electronicAddress.setElectronicAddressData(emailAddress);
		electronicAddress.setElectronicAddressType(Version1ElectronicAddressType.MAILSERVER);

		uai.setElectronicAddress(electronicAddress);

		uai.setUserAddressRoleType(Version1UserAddressRoleType.NOTICE);

		userAddrInfos.add(uai);

	}

	/**
	 * Set the session id for the current session of this authenticated Aleph User
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Get the session id for the current session of this authenticated Aleph User\
	 * 
	 * @return session id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Add on item on loan to this user
	 * 
	 * @param item
	 */
	public void addLoanItem(LoanedItem item) {
		if (!loanedItems.contains(item)) {
			loanedItems.add(item);
		}
	}

	/**
	 * @return the items on loan for this user
	 */
	public List<LoanedItem> getLoanedItems() {
		return loanedItems;
	}

	/**
	 * Set the loan items to a new list
	 * 
	 * @param loanedItems
	 */
	public void setLoanedItems(List<LoanedItem> loanedItems) {
		this.loanedItems = loanedItems;
	}

	/**
	 * Add a requested item to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addRequestedItem(AlephItem item) {
		RequestedItem reqItem = new RequestedItem();

		if (!requestedItems.contains(reqItem)) {
			requestedItems.add(reqItem);
		}
	}

	/**
	 * @return the requestedItems
	 */
	public List<RequestedItem> getRequestedItems() {
		return requestedItems;
	}

	/**
	 * Set the requested items list to the list passed in
	 * 
	 * @param reqItems
	 */
	public void setRequestedItems(List<RequestedItem> reqItems) {
		this.requestedItems = reqItems;
	}

	/**
	 * @param blocks
	 *            the blocks to set
	 */
	public void setBlocks(List<String> blocks) {
		this.blocks = blocks;
	}

	/**
	 * @return the blocks
	 */
	public List<String> getBlocks() {
		return blocks;
	}

	/**
	 * Add a block to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addBlock(String block) {
		if (blocks == null)
			blocks = new ArrayList<String>();
		if (!blocks.contains(block)) {
			blocks.add(block);
		}
	}

	/**
	 * @param notes
	 *            the messages to set
	 */
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	/**
	 * @return the notes
	 */
	public List<String> getNotes() {
		return notes;
	}

	/**
	 * Add a note to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addNote(String note) {
		if (notes == null)
			notes = new ArrayList<String>();
		if (!notes.contains(note)) {
			notes.add(note);
		}
	}

	/**
	 * Try to set the balance from the string passed in
	 * 
	 * @param balance
	 */
	public void setAccountBalance(String balance) {
		AccountBalance accountBallance = new AccountBalance();
		accountBallance.setMonetaryValue(new BigDecimal(balance));
		userFiscalAccountSummary.setAccountBalance(accountBallance);
	}

	/**
	 * @param fineItems
	 *            the fineItems to set
	 */
	public void setFineItems(List<AlephItem> fineItems) {
		this.fineItems = fineItems;
	}

	/**
	 * @return the fineItems
	 */
	public List<AlephItem> getFineItems() {
		if (fineItems == null)
			fineItems = new ArrayList<AlephItem>();
		return fineItems;
	}

	/**
	 * Add a fine item to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addFineItem(AlephItem item) {
		if (fineItems == null)
			fineItems = new ArrayList<AlephItem>();
		if (!fineItems.contains(item)) {
			fineItems.add(item);
		}
	}

	/**
	 * @param authenticatedUsername
	 *            the username used to authenticate (may be different from username if used external authentication etc.)
	 */
	public void setAuthenticatedUsername(String authenticatedUsername) {
		this.authenticatedUserName = authenticatedUsername;
	}

	/**
	 * @return the username used to authenticate (may be different from username if used external authentication etc.)
	 */
	public String getAuthenticatedUsername() {
		return authenticatedUserName;
	}

	/**
	 * @param agency
	 *            the agency to set
	 */
	public void setAgency(AlephAgency agency) {
		this.agency = agency;
	}

	/**
	 * @return the agency
	 */
	public AlephAgency getAgency() {
		return agency;
	}

	public NameInformation getNameInformation() {
		return nameInfo;
	}

	public void setNameInformation(String nameInfo) {
		PersonalNameInformation pni = new PersonalNameInformation();
		StructuredPersonalUserName spun = new StructuredPersonalUserName();
		String[] nameInfos = nameInfo.split(AlephConstants.UNSTRUCTURED_NAME_SEPERATOR);
		if (nameInfos.length > 0) {
			if (AlephConstants.FIRST_SURNAME) {
				spun.setSurname(nameInfos[0].trim());
				if (nameInfos.length > 1)
					spun.setGivenName(nameInfos[1].trim());
			} else {
				spun.setGivenName(nameInfos[0].trim());
				if (nameInfos.length > 1)
					spun.setSurname(nameInfos[1].trim());
			}
		}
		pni.setStructuredPersonalUserName(spun);
		this.nameInfo.setPersonalNameInformation(pni);
	}

	public List<BlockOrTrap> getBlockOrTraps() {
		return blockOrTraps;
	}

	public void setBlockOrTraps(List<BlockOrTrap> bot) {
		blockOrTraps = bot;
	}

	public List<UserAddressInformation> getUserAddressInformations() {
		return userAddrInfos;
	}

	public void setUserAddressInformations(List<UserAddressInformation> userAddrInfos) {
		this.userAddrInfos = userAddrInfos;
	}

	public List<UserId> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<UserId> userIds) {
		this.userIds = userIds;
	}

	public void setUserId(String userId) {
		UserId uid = new UserId();
		uid.setUserIdentifierValue(userId);
		uid.setUserIdentifierType(Version1UserIdentifierType.BARCODE);
		// uid.setAgencyId();
		userIds.add(uid);
	}

	public List<UserPrivilege> getUserPrivileges() {
		return userPrivileges;
	}

	public void setUserPrivileges(List<UserPrivilege> userPrivileges) {
		this.userPrivileges = userPrivileges;
	}

	public void setBorStatus(String string) {

		UserPrivilege userPrivilege = new UserPrivilege();

		// http://www.niso.org/apps/group_public/download.php/8966/z39-83-1-2012_NCIP.pdf#page=88
		userPrivilege.setUserPrivilegeDescription(string);
		userPrivilege.setAgencyUserPrivilegeType(new AgencyUserPrivilegeType("http://www.niso.org/ncip/v1_0/imp1/schemes/agencyuserprivilegetype/agencyuserprivilegetype.scm",
				"MZK type"));
		userPrivilege.setAgencyId(new AgencyId("MZK")); // FIXME: this is default - shouldn't be
		userPrivileges.add(userPrivilege);
	}

	public UserFiscalAccountSummary getUserFiscalAccountSummary() {
		return userFiscalAccountSummary;
	}

	public void setUserFiscalAccountSummary(UserFiscalAccountSummary ufas) {
		userFiscalAccountSummary = ufas;
	}

	public UserOptionalFields getUserOptionalFields() {
		UserOptionalFields uof = new UserOptionalFields();
		if (nameInfo != null)
			uof.setNameInformation(nameInfo);
		if (blockOrTraps != null)
			uof.setBlockOrTraps(blockOrTraps);
		if (userAddrInfos != null)
			uof.setUserAddressInformations(userAddrInfos);
		if (userIds != null)
			uof.setUserIds(userIds);
		if (userPrivileges != null)
			uof.setUserPrivileges(userPrivileges);
		return uof;
	}

}