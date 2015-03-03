package org.extensiblecatalog.ncip.v2.koha.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.agency.KohaAgency;
import org.extensiblecatalog.ncip.v2.koha.item.KohaItem;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.AccountBalance;
import org.extensiblecatalog.ncip.v2.service.AccountDetails;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.AgencyUserPrivilegeType;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.BlockOrTrap;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddress;
import org.extensiblecatalog.ncip.v2.service.FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionType;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccountSummary;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.UserPrivilege;
import org.extensiblecatalog.ncip.v2.service.Version1ElectronicAddressType;
import org.extensiblecatalog.ncip.v2.service.Version1FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.Version1FiscalTransactionType;
import org.extensiblecatalog.ncip.v2.service.Version1UserAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.Version1UserIdentifierType;
import org.xml.sax.SAXException;

/**
 * A user that exists within Koha. This is most likely returned by the authenticate or lookup methods within the KohaAPI class
 * 
 * @author rjohns14
 */

public class KohaUser implements Serializable {

	private static final long serialVersionUID = 65005L;
	private String username;
	private String fullName;
	private String authenticatedUserName;

	private List<RequestedItem> requestedItems;
	private List<LoanedItem> loanedItems;
	private List<KohaItem> fineItems;
	private List<String> blocks;
	private List<String> notes;

	// the current session id for this authenticated koha user
	private String sessionId;

	private KohaAgency agency;
	private NameInformation nameInfo;
	private List<BlockOrTrap> blockOrTraps;
	private List<UserAddressInformation> userAddrInfos;
	private List<UserId> userIds;
	private List<UserPrivilege> userPrivileges;
	private UserFiscalAccountSummary userFiscalAccountSummary;
	private UserPrivilege userPrivilege;
	private int balanceMinorUnit;
	private UserFiscalAccount userFiscalAccount;
	private List<AccountDetails> accountDetails;

	public KohaUser() {
		requestedItems = new ArrayList<RequestedItem>();
		loanedItems = new ArrayList<LoanedItem>();
		blockOrTraps = new ArrayList<BlockOrTrap>();
		userAddrInfos = new ArrayList<UserAddressInformation>();
		userIds = new ArrayList<UserId>();
		userPrivileges = new ArrayList<UserPrivilege>();

		userFiscalAccountSummary = new UserFiscalAccountSummary();
		userFiscalAccount = new UserFiscalAccount();
		accountDetails = new ArrayList<AccountDetails>();

		nameInfo = new NameInformation();
	}

	/**
	 * Set the username for this koha user
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the username for this koha user
	 * 
	 * @return the user name
	 */
	public String getUsername() {
		return username;
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


		uai.setUserAddressRoleType(Version1UserAddressRoleType.SHIP_TO);

		userAddrInfos.add(uai);

	}


	/**
	 * Set the session id for the current session of this authenticated Koha User
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Get the session id for the current session of this authenticated Koha User\
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
	public void addRequestedItem(KohaItem item) {
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
		AccountBalance accountBalance = new AccountBalance();

		balanceMinorUnit = balance.split("\\.")[1].length();

		String balanceValue = balance.split("\\.")[0];

		balanceValue = balanceValue.replace(" ", "");

		accountBalance.setMonetaryValue(new BigDecimal(balanceValue).multiply(new BigDecimal(100)));

		userFiscalAccountSummary.setAccountBalance(accountBalance);
		userFiscalAccount.setAccountBalance(accountBalance);
	}

	public void setCashTypeNote(String noteValue) {
		// Parsed text example:
		// Please note that there is an additional accrued overdue items fine of: 35.00.

		// AccrualDate will be today
		GregorianCalendar accrualDate = new GregorianCalendar();

		FiscalTransactionInformation fiscalTransactionInformation = new FiscalTransactionInformation();

		String[] values = noteValue.split(" ");

		String value = values[values.length - 1].split("\\.")[0];
		values = null;

		Amount amount = new Amount();
		amount.setMonetaryValue(new BigDecimal(value).multiply(new BigDecimal(100)));

		fiscalTransactionInformation.setAmount(amount);

		FiscalTransactionType fiscalTransactionType = Version1FiscalTransactionType.SERVICE_CHARGE;
		fiscalTransactionInformation.setFiscalTransactionType(fiscalTransactionType);

		FiscalActionType fiscalActionType = Version1FiscalActionType.PENALTY;
		fiscalTransactionInformation.setFiscalActionType(fiscalActionType);

		fiscalTransactionInformation.setFiscalTransactionDescription(noteValue);

		AccountDetails details = new AccountDetails();
		details.setAccrualDate(accrualDate);
		details.setFiscalTransactionInformation(fiscalTransactionInformation);

		accountDetails.add(details);
	}

	public void addAccountDetails(String accrualDateParsed, String value, String description) throws SAXException {
		AccountDetails details = new AccountDetails();

		GregorianCalendar accrualDate = KohaUtil.parseGregorianCalendarFromKohaDate(accrualDateParsed);
		details.setAccrualDate(accrualDate);

		FiscalTransactionInformation fiscalTransactionInformation = new FiscalTransactionInformation();

		value = value.replaceAll("\\(|\\)", "");
		value = value.split("\\.")[0];

		Amount amount = new Amount();
		amount.setMonetaryValue(new BigDecimal(value).multiply(new BigDecimal(100)));

		fiscalTransactionInformation.setAmount(amount);

		FiscalTransactionType fiscalTransactionType = Version1FiscalTransactionType.FINE;
		fiscalTransactionInformation.setFiscalTransactionType(fiscalTransactionType);

		FiscalActionType fiscalActionType = Version1FiscalActionType.PAYMENT;
		fiscalTransactionInformation.setFiscalActionType(fiscalActionType);

		fiscalTransactionInformation.setFiscalTransactionDescription(description);

		details.setFiscalTransactionInformation(fiscalTransactionInformation);

		accountDetails.add(details);
	}

	public List<UserFiscalAccount> getUserFiscalAccounts() {
		List<UserFiscalAccount> userFiscalAccounts = new ArrayList<UserFiscalAccount>();

		userFiscalAccount.setAccountDetails(accountDetails);
		userFiscalAccounts.add(userFiscalAccount);

		return userFiscalAccounts;
	}

	public int getBalanceMinorUnit() {
		return balanceMinorUnit;
	}

	/**
	 * @param fineItems
	 *            the fineItems to set
	 */
	public void setFineItems(List<KohaItem> fineItems) {
		this.fineItems = fineItems;
	}

	/**
	 * @return the fineItems
	 */
	public List<KohaItem> getFineItems() {
		if (fineItems == null)
			fineItems = new ArrayList<KohaItem>();
		return fineItems;
	}

	/**
	 * Add a fine item to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addFineItem(KohaItem item) {
		if (fineItems == null)
			fineItems = new ArrayList<KohaItem>();
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
	public void setAgency(KohaAgency agency) {
		this.agency = agency;
	}

	/**
	 * @return the agency
	 */
	public KohaAgency getAgency() {
		return agency;
	}

	public NameInformation getNameInformation() {
		return nameInfo;
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

	public void setBorStatus(String string) throws SAXException {

		if (userPrivilege == null)
			userPrivilege = new UserPrivilege();

		// http://www.niso.org/apps/group_public/download.php/8966/z39-83-1-2012_NCIP.pdf#page=88
		userPrivilege.setUserPrivilegeDescription(string);
		userPrivilege.setAgencyUserPrivilegeType(new AgencyUserPrivilegeType("http://www.niso.org/ncip/v1_0/imp1/schemes/agencyuserprivilegetype/agencyuserprivilegetype.scm",
				"KOH type"));
		userPrivilege.setAgencyId(new AgencyId(LocalConfig.getDefaultAgency()));

		if (userPrivilege.getValidFromDate() != null)
			userPrivileges.add(userPrivilege);
	}

	public void setValidToDate(String validToDateParsed) throws SAXException {
		if (userPrivilege == null)
			userPrivilege = new UserPrivilege();

		userPrivilege.setValidToDate(KohaUtil.parseGregorianCalendarFromKohaDate(validToDateParsed));

		if (userPrivilege.getUserPrivilegeDescription() != null)
			userPrivileges.add(userPrivilege);
	}

	public void setValidFromDate(String validFromDateParsed) throws SAXException {
		if (userPrivilege == null)
			userPrivilege = new UserPrivilege();

		userPrivilege.setValidFromDate(KohaUtil.parseGregorianCalendarFromKohaDate(validFromDateParsed));

		if (userPrivilege.getUserPrivilegeDescription() != null && userPrivilege.getValidToDate() != null)
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

	public void setPhoneNumber(String phoneNumber) {
		UserAddressInformation uai = new UserAddressInformation();
		ElectronicAddress electronicAddress = new ElectronicAddress();
		electronicAddress.setElectronicAddressType(Version1ElectronicAddressType.TEL);
		electronicAddress.setElectronicAddressData(phoneNumber);
		uai.setElectronicAddress(electronicAddress);
		uai.setUserAddressRoleType(Version1UserAddressRoleType.MULTI_PURPOSE);
		userAddrInfos.add(uai);
	}

	public void addLoanedItems(List<LoanedItem> loanedItems) {
		if (this.loanedItems != null)
			this.loanedItems.addAll(loanedItems);
		else
			this.loanedItems = loanedItems;
	}

	public void setUserFiscalAccount(UserFiscalAccount userFiscalAccount) {
		this.userFiscalAccount = userFiscalAccount;
	}
}