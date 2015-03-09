package org.extensiblecatalog.ncip.v2.aleph.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephItem;

/**
 * A user that exists within Aleph. This is most likely returned by the authenticate or lookup methods within the XService class
 * 
 * @author rjohns14
 *
 */
public class AlephXServicesUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3666744273906126407L;
	private String username;
	private String fullName;
	private String address;
	private String emailAddress;
	private String authenticatedUserName;

	// store internally as double
	private double balance;

	private List<AlephItem> requestedItems;
	private List<AlephItem> loanItems;
	private List<AlephItem> fineItems;
	private List<String> blocks;
	private List<String> notes;

	// the current session id for this authenticated aleph user
	private String sessionId;

	private AlephAgency agency;

	public AlephXServicesUser() {
		requestedItems = new ArrayList<AlephItem>();
		loanItems = new ArrayList<AlephItem>();
	}

	/**
	 * Set the username for this aleph user
	 * 
	 * @param username
	 */
	protected void setUsername(String username) {
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
	protected void setFullName(String fullName) {
		this.fullName = fullName;
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
	protected void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the user's permanent address
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the email address
	 * 
	 * @param emailAddress
	 */
	protected void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Get the email address
	 * 
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Set the session id for the current session of this authenticated Aleph User
	 * 
	 * @param sessionId
	 */
	protected void setSessionId(String sessionId) {
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
	public void addLoanItem(AlephItem item) {
		if (loanItems == null)
			loanItems = new ArrayList<AlephItem>();
		if (!loanItems.contains(item)) {
			loanItems.add(item);
		}
	}

	/**
	 * @return the items on loan for this user
	 */
	public List<AlephItem> getLoanItems() {
		if (loanItems == null)
			loanItems = new ArrayList<AlephItem>();
		return loanItems;
	}

	/**
	 * Set the loan items to a new list
	 * 
	 * @param items
	 */
	public void setLoanItems(List<AlephItem> items) {
		this.loanItems = items;
	}

	/**
	 * Add a requested item to this user It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addRequestedItem(AlephItem item) {
		if (requestedItems == null)
			requestedItems = new ArrayList<AlephItem>();
		if (!requestedItems.contains(item)) {
			requestedItems.add(item);
		}
	}

	/**
	 * @return the requestedItems
	 */
	public List<AlephItem> getRequestedItems() {
		if (requestedItems == null)
			requestedItems = new ArrayList<AlephItem>();
		return requestedItems;
	}

	/**
	 * Set the requested items list to the list passed in
	 * 
	 * @param items
	 */
	public void setRequestedItems(List<AlephItem> items) {
		this.requestedItems = items;
	}

	/**
	 * Check current requested items to see if the item passed in already exists. Try to match on either bibid or itemid.
	 * 
	 * @param item
	 * @return true if exists
	 */
	public boolean hasRequestedItem(AlephItem item) {
		if (item == null || getRequestedItems() == null || getRequestedItems().size() <= 0 || (item.getBibId() == null && item.getItemId() == null)) {
			return false;
		}
		for (AlephItem eachItem : getRequestedItems()) {
			if (item.getItemId() != null && item.getItemId().equals(eachItem.getItemId())) {
				return true;
			} else if (item.getBibId() != null && item.getBibId().equals(eachItem.getBibId())) {
				return true;
			}
		}
		return false;
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
	public void setBalance(String balance) {
		try {
			setBalance(Double.parseDouble(balance));
		} catch (NumberFormatException nfe) {
			// do nothing...make
		}
	}

	/**
	 * Set the balance to the double value, constrain to two decimal places
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	/**
	 * This returns the balance with the decimal places shifted two places and the dollar balance amount in an integer. For example $37.12 would return the int value 3712
	 * 
	 * @return balance with decimal shifted two places
	 */
	public int getBalanceInt() {
		Double val = new Double(getBalance() * 100);
		// check if there is a trailing fraction, if so roundup if >= 5
		boolean roundup = false;
		String valStr = val.toString();
		if (valStr.indexOf('.') != -1) {
			String right = "";
			if (valStr.length() > valStr.indexOf('.') + 1) {
				right += valStr.substring(valStr.indexOf('.') + 1);
				if (Integer.parseInt(right.substring(0, 1)) > 5) {
					roundup = true;
				}
			}
		}
		int valInt = val.intValue();
		if (roundup)
			valInt++;
		return valInt;
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
}
