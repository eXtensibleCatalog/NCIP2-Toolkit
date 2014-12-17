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
	private static String z304address1formatting;
	private static String z304address2formatting;
	private static String z304address3formatting;
	private static String z304address4formatting;

	private static String z304telephone1formatting;
	private static String z304telephone2formatting;
	private static String z304telephone3formatting;
	private static String z304telephone4formatting;

	private static String userNameStoredIn;
	private static String userNameFormatting;

	private static String userStreetStoredIn;
	private static String userPostalStoredIn;
	private static String userCityStoredIn;

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
	 * @return the z304address1formatting
	 */
	public static String getZ304address1formatting() {
		return z304address1formatting;
	}

	/**
	 * @param z304address1formatting
	 *            the z304address1formatting to set
	 */
	public static void setZ304address1formatting(String z304address1formatting) {
		LocalConfig.z304address1formatting = z304address1formatting;
	}

	/**
	 * @return the z304address2formatting
	 */
	public static String getZ304address2formatting() {
		return z304address2formatting;
	}

	/**
	 * @param z304address2formatting
	 *            the z304address2formatting to set
	 */
	public static void setZ304address2formatting(String z304address2formatting) {
		LocalConfig.z304address2formatting = z304address2formatting;
	}

	/**
	 * @return the z304address3formatting
	 */
	public static String getZ304address3formatting() {
		return z304address3formatting;
	}

	/**
	 * @param z304address3formatting
	 *            the z304address3formatting to set
	 */
	public static void setZ304address3formatting(String z304address3formatting) {
		LocalConfig.z304address3formatting = z304address3formatting;
	}

	/**
	 * @return the z304address4formatting
	 */
	public static String getZ304address4formatting() {
		return z304address4formatting;
	}

	/**
	 * @param z304address4formatting
	 *            the z304address4formatting to set
	 */
	public static void setZ304address4formatting(String z304address4formatting) {
		LocalConfig.z304address4formatting = z304address4formatting;
	}

	/**
	 * @return the z304telephone1formatting
	 */
	public static String getZ304telephone1formatting() {
		return z304telephone1formatting;
	}

	/**
	 * @param z304telephone1formatting
	 *            the z304telephone1formatting to set
	 */
	public static void setZ304telephone1formatting(String z304telephone1formatting) {
		LocalConfig.z304telephone1formatting = z304telephone1formatting;
	}

	/**
	 * @return the z304telephone2formatting
	 */
	public static String getZ304telephone2formatting() {
		return z304telephone2formatting;
	}

	/**
	 * @param z304telephone2formatting
	 *            the z304telephone2formatting to set
	 */
	public static void setZ304telephone2formatting(String z304telephone2formatting) {
		LocalConfig.z304telephone2formatting = z304telephone2formatting;
	}

	/**
	 * @return the z304telephone3formatting
	 */
	public static String getZ304telephone3formatting() {
		return z304telephone3formatting;
	}

	/**
	 * @param z304telephone3formatting
	 *            the z304telephone3formatting to set
	 */
	public static void setZ304telephone3formatting(String z304telephone3formatting) {
		LocalConfig.z304telephone3formatting = z304telephone3formatting;
	}

	/**
	 * @return the z304telephone4formatting
	 */
	public static String getZ304telephone4formatting() {
		return z304telephone4formatting;
	}

	/**
	 * @param z304telephone4formatting
	 *            the z304telephone4formatting to set
	 */
	public static void setZ304telephone4formatting(String z304telephone4formatting) {
		LocalConfig.z304telephone4formatting = z304telephone4formatting;
	}

	/**
	 * @return the userNameStoredIn
	 */
	public static String getUserNameStoredIn() {
		return userNameStoredIn;
	}

	/**
	 * @param userNameStoredIn
	 *            the userNameStoredIn to set
	 */
	public static void setUserNameStoredIn(String userNameStoredIn) {
		LocalConfig.userNameStoredIn = userNameStoredIn;
	}

	/**
	 * @return the userNameFormatting
	 */
	public static String getUserNameFormatting() {
		return userNameFormatting;
	}

	/**
	 * @param userNameFormatting
	 *            the userNameFormatting to set
	 */
	public static void setUserNameFormatting(String userNameFormatting) {
		LocalConfig.userNameFormatting = userNameFormatting;
	}

	/**
	 * @return the userStreetStoredIn
	 */
	public static String getUserStreetStoredIn() {
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
	public static String getUserPostalStoredIn() {
		return userPostalStoredIn;
	}

	/**
	 * @param userPostalStoredIn
	 *            the userPostalStoredIn to set
	 */
	public static void setUserPostalStoredIn(String userPostalStoredIn) {
		LocalConfig.userPostalStoredIn = userPostalStoredIn;
	}

	/**
	 * @return the userCityStoredIn
	 */
	public static String getUserCityStoredIn() {
		return userCityStoredIn;
	}

	/**
	 * @param userCityStoredIn
	 *            the userCityStoredIn to set
	 */
	public static void setUserCityStoredIn(String userCityStoredIn) {
		LocalConfig.userCityStoredIn = userCityStoredIn;
	}

	/**
	 * @return the userPhoneStoredIn
	 */
	public static String getUserPhoneStoredIn() {
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
