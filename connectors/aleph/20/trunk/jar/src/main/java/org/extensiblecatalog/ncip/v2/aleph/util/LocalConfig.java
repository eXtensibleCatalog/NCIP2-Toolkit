package org.extensiblecatalog.ncip.v2.aleph.util;

import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

public class LocalConfig {

	private boolean echoParticularProblemsToLUIS;

	private String defaultAgency;
	private String agencyAddress;
	private String agencyName;
	private String NCIPVersion;
	private String userRegistrationLink;
	private String authDataFormatType;

	private String serverName;
	private String serverPort;
	private String serverSuffix;
	private String bibLibrary;
	private String admLibrary;

	private int maxItemPreparationTimeDelay;

	private int tokenExpirationTime;

	private int bibLibraryLength;

	public LocalConfig() {
		maxItemPreparationTimeDelay = 0;
	}

	/**
	 * @return the echoParticularProblemsToLUIS
	 */
	public boolean isEchoParticularProblemsToLUIS() {
		return echoParticularProblemsToLUIS;
	}

	/**
	 * @param echoParticularProblemsToLUIS
	 *            the echoParticularProblemsToLUIS to set
	 */
	public void setEchoParticularProblemsToLUIS(boolean echoParticularProblemsToLUIS) {
		this.echoParticularProblemsToLUIS = echoParticularProblemsToLUIS;
	}

	/**
	 * @return the defaultAgency
	 */
	public String getDefaultAgency() {
		return defaultAgency;
	}

	/**
	 * @param defaultAgency
	 *            the defaultAgency to set
	 */
	public void setDefaultAgency(String defaultAgency) {
		this.defaultAgency = defaultAgency;
	}

	/**
	 * @return the agencyAddress
	 */
	public String getAgencyAddress() {
		return agencyAddress;
	}

	/**
	 * @param agencyAddress
	 *            the agencyAddress to set
	 */
	public void setAgencyAddress(String agencyAddress) {
		this.agencyAddress = agencyAddress;
	}

	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName
	 *            the agencyName to set
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * @return the nCIPVersion
	 */
	public String getNCIPVersion() {
		return NCIPVersion;
	}

	/**
	 * @param nCIPVersion
	 *            the nCIPVersion to set
	 */
	public void setNCIPVersion(String nCIPVersion) {
		NCIPVersion = nCIPVersion;
	}

	/**
	 * @return the userRegistrationLink
	 */
	public String getUserRegistrationLink() {
		return userRegistrationLink;
	}

	/**
	 * @param userRegistrationLink
	 *            the userRegistrationLink to set
	 */
	public void setUserRegistrationLink(String userRegistrationLink) {
		this.userRegistrationLink = userRegistrationLink;
	}

	/**
	 * @return the authDataFormatType
	 */
	public String getAuthDataFormatType() {
		return authDataFormatType;
	}

	/**
	 * @param authDataFormatType
	 *            the authDataFormatType to set
	 */
	public void setAuthDataFormatType(String authDataFormatType) {
		this.authDataFormatType = authDataFormatType;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 * @throws ServiceException
	 */
	public void setServerName(String serverName) throws ServiceException {
		if (serverName == null || serverName.isEmpty())
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Server name is not set. Can be set up in toolkit.properties");
		this.serverName = serverName;
	}

	/**
	 * @return the serverPort
	 */
	public String getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(String serverPort) throws ServiceException {
		if (serverPort == null || serverPort.isEmpty())
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Server port is not set. Can be set up in toolkit.properties");
		this.serverPort = serverPort;
	}

	/**
	 * @return the serverSuffix
	 */
	public String getServerSuffix() {
		return serverSuffix;
	}

	/**
	 * @param serverSuffix
	 *            the serverSuffix to set
	 */
	public void setServerSuffix(String serverSuffix) {
		this.serverSuffix = serverSuffix;
	}

	/**
	 * @return the bibLibrary
	 */
	public String getBibLibrary() {
		return bibLibrary;
	}

	/**
	 * @param bibLibrary
	 *            the bibLibrary to set
	 */
	public void setBibLibrary(String bibLibrary) {
		bibLibraryLength = bibLibrary.length();
		this.bibLibrary = bibLibrary;
	}

	/**
	 * @return the admLibrary
	 */
	public String getAdmLibrary() {
		return admLibrary;
	}

	public int getBibLibraryLength() {
		return bibLibraryLength;
	}

	/**
	 * @param admLibrary
	 *            the admLibrary to set
	 */
	public void setAdmLibrary(String admLibrary) {
		this.admLibrary = admLibrary;
	}

	/**
	 * @return the maxItemPreparationTimeDelay
	 */
	public int getMaxItemPreparationTimeDelay() {
		return maxItemPreparationTimeDelay;
	}

	/**
	 * @param maxItemPreparationTimeDelay
	 *            the maxItemPreparationTimeDelay to set
	 */
	public void setMaxItemPreparationTimeDelay(int maxItemPreparationTimeDelay) {
		this.maxItemPreparationTimeDelay = maxItemPreparationTimeDelay;
	}

	public int getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(int tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

}
