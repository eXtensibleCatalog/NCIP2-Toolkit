package org.extensiblecatalog.ncip.v2.koha.agency;

/**
 * Class that holds Agency settings for current user
 * 
 * @author Rick Johnson (NDU)
 *
 */
public class KohaAgency {
	private String agencyId;
	private String admLibrary;
	private String bibLibrary;
	private String holdingsLibrary;

	/**
	 * @param agencyId
	 *            the agencyId to set
	 */
	public void setAgencyId(final String agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the agencyId
	 */
	public String getAgencyId() {
		return agencyId;
	}

	/**
	 * @param admLibrary
	 *            the admLibrary to set
	 */
	public void setAdmLibrary(final String admLibrary) {
		this.admLibrary = admLibrary;
	}

	/**
	 * @return the admLibrary
	 */
	public String getAdmLibrary() {
		return admLibrary;
	}

	/**
	 * @param bibLibrary
	 *            the bibLibrary to set
	 */
	public void setBibLibrary(final String bibLibrary) {
		this.bibLibrary = bibLibrary;
	}

	/**
	 * @return the bibLibrary
	 */
	public String getBibLibrary() {
		return bibLibrary;
	}

	/**
	 * @param holdingsLibrary
	 *            the holdLibrary to set
	 */
	public void setHoldingsLibrary(final String holdingsLibrary) {
		this.holdingsLibrary = holdingsLibrary;
	}

	/**
	 * @return the holdLibrary
	 */
	public String getHoldingsLibrary() {
		return holdingsLibrary;
	}
}
