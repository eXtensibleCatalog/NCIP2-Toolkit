package org.extensiblecatalog.ncip.v2.service;

public class MzkNewspaperItemInformation extends ItemInformation {
	
	protected int pubYear = -1;
	protected int volume = -1;
	protected int yearTurn = -1;
	
	
	public MzkNewspaperItemInformation ( ItemInformation info ) {
		this.setCurrentBorrower(info.getCurrentBorrower());
		this.setCurrentRequesters(info.getCurrentRequesters());
		this.setDateDue(info.getDateDue());
		this.setDateRecalled(info.getDateRecalled());
		this.setHoldPickupDate(info.getHoldPickupDate());
		this.setItemId(info.getItemId());
		this.setItemNote(info.getItemNote());
		this.setItemOptionalFields(info.getItemOptionalFields());
		this.setItemTransaction(info.getItemTransaction());
		this.setProblems(info.getProblems());
		this.setRequestIds(info.getRequestIds());
	}
	
	public int getPubYear() {
		return pubYear;
	}
	public void setPubYear(int pubYear) {
		this.pubYear = pubYear;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getYearTurn() {
		return yearTurn;
	}
	public void setYearTurn(int yearTurn) {
		this.yearTurn = yearTurn;
	}

}
