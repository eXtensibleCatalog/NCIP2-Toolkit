package org.extensiblecatalog.ncip.v2.aleph.util;

public class LocalConfig {

	private static boolean echoParticularProblemsToLUIS;

	private static String defaultAgency;
	private static String agencyAddress;
	private static String agencyName;
	private static String NCIPVersion;
	private static String userRegistrationLink;
	private static String authDataFormatType;

	private static String serverName;
	private static String serverPort;
	private static String serverSuffix;
	private static String bibLibrary;
	private static String admLibrary;

	// Aleph API's patron -> address mapping
	private static String userStreetStoredIn;
	private static String userPostalAndCityStoredIn;

	private static String userPhoneStoredIn;
	private static String userIdCardStoredIn;

	private static int maxItemPreparationTimeDelay = 0;

	private static int tokenExpirationTime;

	private static int bibLibraryLength;

	/**
	 * @return the echoParticularProblemsToLUIS
	 */
	public static boolean isEchoParticularProblemsToLUIS() {
		return echoParticularProblemsToLUIS;
	}

	/**
	 * @param echoParticularProblemsToLUIS
	 *            the echoParticularProblemsToLUIS to set
	 */
	public static void setEchoParticularProblemsToLUIS(boolean echoParticularProblemsToLUIS) {
		LocalConfig.echoParticularProblemsToLUIS = echoParticularProblemsToLUIS;
	}

	/**
	 * @return the defaultAgency
	 */
	public static String getDefaultAgency() {
		return defaultAgency;
	}

	/**
	 * @param defaultAgency
	 *            the defaultAgency to set
	 */
	public static void setDefaultAgency(String defaultAgency) {
		LocalConfig.defaultAgency = defaultAgency;
	}

	/**
	 * @return the agencyAddress
	 */
	public static String getAgencyAddress() {
		return agencyAddress;
	}

	/**
	 * @param agencyAddress
	 *            the agencyAddress to set
	 */
	public static void setAgencyAddress(String agencyAddress) {
		LocalConfig.agencyAddress = agencyAddress;
	}

	/**
	 * @return the agencyName
	 */
	public static String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName
	 *            the agencyName to set
	 */
	public static void setAgencyName(String agencyName) {
		LocalConfig.agencyName = agencyName;
	}

	/**
	 * @return the nCIPVersion
	 */
	public static String getNCIPVersion() {
		return NCIPVersion;
	}

	/**
	 * @param nCIPVersion
	 *            the nCIPVersion to set
	 */
	public static void setNCIPVersion(String nCIPVersion) {
		NCIPVersion = nCIPVersion;
	}

	/**
	 * @return the userRegistrationLink
	 */
	public static String getUserRegistrationLink() {
		return userRegistrationLink;
	}

	/**
	 * @param userRegistrationLink
	 *            the userRegistrationLink to set
	 */
	public static void setUserRegistrationLink(String userRegistrationLink) {
		LocalConfig.userRegistrationLink = userRegistrationLink;
	}

	/**
	 * @return the authDataFormatType
	 */
	public static String getAuthDataFormatType() {
		return authDataFormatType;
	}

	/**
	 * @param authDataFormatType
	 *            the authDataFormatType to set
	 */
	public static void setAuthDataFormatType(String authDataFormatType) {
		LocalConfig.authDataFormatType = authDataFormatType;
	}

	/**
	 * @return the serverName
	 */
	public static String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public static void setServerName(String serverName) {
		LocalConfig.serverName = serverName;
	}

	/**
	 * @return the serverPort
	 */
	public static String getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the serverPort to set
	 */
	public static void setServerPort(String serverPort) {
		LocalConfig.serverPort = serverPort;
	}

	/**
	 * @return the serverSuffix
	 */
	public static String getServerSuffix() {
		return serverSuffix;
	}

	/**
	 * @param serverSuffix
	 *            the serverSuffix to set
	 */
	public static void setServerSuffix(String serverSuffix) {
		LocalConfig.serverSuffix = serverSuffix;
	}

	/**
	 * @return the bibLibrary
	 */
	public static String getBibLibrary() {
		return bibLibrary;
	}

	/**
	 * @param bibLibrary
	 *            the bibLibrary to set
	 */
	public static void setBibLibrary(String bibLibrary) {
		LocalConfig.bibLibraryLength = bibLibrary.length();
		LocalConfig.bibLibrary = bibLibrary;
	}

	/**
	 * @return the admLibrary
	 */
	public static String getAdmLibrary() {
		return admLibrary;
	}

	/**
	 * @param admLibrary
	 *            the admLibrary to set
	 */
	public static void setAdmLibrary(String admLibrary) {
		LocalConfig.admLibrary = admLibrary;
	}

	/**
	 * @return the userStreetStoredIn
	 */
	public static String getNodeUserStreetStoredIn() {
		return userStreetStoredIn;
	}

	/**
	 * @param userStreetStoredIn
	 *            the userStreetStoredIn to set
	 */
	public static void setUserStreetStoredIn(String userStreetStoredIn) {
		LocalConfig.userStreetStoredIn = userStreetStoredIn;
	}

	/**
	 * @return the userPostalStoredIn
	 */
	public static String getNodeUserPostalAndCityStoredIn() {
		return userPostalAndCityStoredIn;
	}

	/**
	 * @param userPostalStoredIn
	 *            the userPostalStoredIn to set
	 */
	public static void setUserPostalAndCityStoredIn(String userPostalStoredIn) {
		LocalConfig.userPostalAndCityStoredIn = userPostalStoredIn;
	}

	/**
	 * @return the userPhoneStoredIn
	 */
	public static String getNodeUserPhoneStoredIn() {
		return userPhoneStoredIn;
	}

	/**
	 * @param userPhoneStoredIn
	 *            the userPhoneStoredIn to set
	 */
	public static void setUserPhoneStoredIn(String userPhoneStoredIn) {
		LocalConfig.userPhoneStoredIn = userPhoneStoredIn;
	}

	/**
	 * @return the userIdCardStoredIn
	 */
	public static String getUserIdCardStoredIn() {
		return userIdCardStoredIn;
	}

	/**
	 * @param userIdCardStoredIn
	 *            the userIdCardStoredIn to set
	 */
	public static void setUserIdCardStoredIn(String userIdCardStoredIn) {
		LocalConfig.userIdCardStoredIn = userIdCardStoredIn;
	}

	/**
	 * @return the maxItemPreparationTimeDelay
	 */
	public static int getMaxItemPreparationTimeDelay() {
		return maxItemPreparationTimeDelay;
	}

	/**
	 * @param maxItemPreparationTimeDelay
	 *            the maxItemPreparationTimeDelay to set
	 */
	public static void setMaxItemPreparationTimeDelay(int maxItemPreparationTimeDelay) {
		LocalConfig.maxItemPreparationTimeDelay = maxItemPreparationTimeDelay;
	}

	/**
	 * @return the tokenExpirationTime
	 */
	public static int getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	/**
	 * @param tokenExpirationTime
	 *            the tokenExpirationTime to set
	 */
	public static void setTokenExpirationTime(int tokenExpirationTime) {
		LocalConfig.tokenExpirationTime = tokenExpirationTime;
	}

	/**
	 * @return the bibLibraryLength
	 */
	public static int getBibLibraryLength() {
		return bibLibraryLength;
	}

}
