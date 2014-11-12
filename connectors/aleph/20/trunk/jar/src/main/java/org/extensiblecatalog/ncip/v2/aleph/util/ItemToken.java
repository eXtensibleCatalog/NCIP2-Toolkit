package org.extensiblecatalog.ncip.v2.aleph.util;

/**
 * Represents last returned item
 * 
 * @author SharmilaR
 *
 */
public class ItemToken {

	private String bibliographicId;

	private String holdingsId;

	private String itemId;

	private String nextToken;

	private boolean isRecordId = false;

	private boolean doneWithRecordId = false;

	private int numberOfDoneItemIds = 0;

	public String getBibliographicId() {
		return bibliographicId;
	}

	public void setBibliographicId(String bibliographicId) {
		this.bibliographicId = bibliographicId;
	}

	public String getHoldingsId() {
		return holdingsId;
	}

	public void setHoldingsId(String holdingsId) {
		this.holdingsId = holdingsId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getNextToken() {
		return nextToken;
	}

	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}

	public boolean isRecordId() {
		return isRecordId;
	}

	public void setIsRecordId(boolean isRecordId) {
		this.isRecordId = isRecordId;
	}

	public int getNoOfDoneItemIds() {
		return numberOfDoneItemIds;
	}

	public void setNoOfDoneItemIds(int numberOfDoneItemIds) {
		this.numberOfDoneItemIds = numberOfDoneItemIds;
	}

	/**
	 * In case there have not been completely listed all items within record ID, we have to continue listing those.
	 * <br /><br />
	 *  If <b>all of those item IDs were already listed to output</b>, this method returns "true" thus appropriate ItemToken can be deleted.
	 *  <br /><br />
	 *  Otherwise it means there is more items to be returned additionally.
	 */
	public boolean doneWithRecordId() {
		return doneWithRecordId;
	}

	public void setDoneWithRecordId(boolean doneWithRecordId) {
		this.doneWithRecordId = doneWithRecordId;
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("[bibliographicId=" + bibliographicId);
		b.append(", holdingsId=" + holdingsId);
		b.append(", itemId=" + itemId);
		b.append(", nextToken=" + nextToken + "]");
		return b.toString();
	}

}
