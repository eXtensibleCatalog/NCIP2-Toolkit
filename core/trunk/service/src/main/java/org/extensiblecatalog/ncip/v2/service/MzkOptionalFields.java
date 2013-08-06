package org.extensiblecatalog.ncip.v2.service;

public class MzkOptionalFields {

    protected Integer volume = null;
    protected Integer yearTurn = null;
    protected Integer publicationYear = null;

    public Integer getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume > -1 ? new Integer(volume) : null;
	}
	
	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Integer getYearTurn() {
		return yearTurn;
	}

	public void setYearTurn(int yearTurn) {
		this.yearTurn = yearTurn > -1 ? new Integer(yearTurn) : null;
	}
	
	public void setYearTurn(Integer yearTurn) {
		this.yearTurn = yearTurn;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear > -1 ? new Integer(publicationYear) : null;
	}
	
	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

}
