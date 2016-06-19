package org.extensiblecatalog.ncip.v2.koha.util;

import java.util.HashMap;
import java.util.Map;

import org.extensiblecatalog.ncip.v2.service.CurrencyCode;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

public class LocalConfig {

	private static boolean echoParticularProblemsToLUIS;
	
	private static boolean useRestApiInsteadOfSvc;

	private static String stringFormatForExpired;
	private static String stringFormatForTotalfines;
	private static String stringFormatForDebarred;

	private static String defaultAgency;
	private static String agencyAddress;
	private static String agencyName;
	private static String NCIPVersion;
	private static String userRegistrationLink;
	private static String authDataFormatType;

	private static String serverName;
	private static String serverPort;
	private static String svcSuffix;
	private static String bibLibrary;
	private static String admLibrary;

	private static String currencyCode;

	private static String adminName;
	private static String adminPass;

	private static int maxItemPreparationTimeDelay = 0;

	private static int tokenExpirationTime;

	private static int bibLibraryLength;

	private static Map<String, Integer> transferBranchTime;

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
	 * @return useRestApiInsteadOfSvc
	 */
	public static boolean useRestApiInsteadOfSvc() {
		return useRestApiInsteadOfSvc;
	}
	
	/**
	 * @param useRestApiInsteadOfSvc
	 */
	public static void setUseRestApiInsteadOfSvc(boolean useRestApiInsteadOfSvc) {
		LocalConfig.useRestApiInsteadOfSvc = useRestApiInsteadOfSvc;
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
	public static void setOpacServerName(String serverName) {
		LocalConfig.serverName = serverName;
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

	/**
	 * @return the svcSuffix
	 */
	public static String getSvcSuffix() {
		return svcSuffix;
	}

	/**
	 * @param svcSuffix
	 *            the svcSuffix to set
	 */
	public static void setSvcSuffix(String svcSuffix) {
		LocalConfig.svcSuffix = svcSuffix;
	}

	/**
	 * @return the svcPort
	 */
	public static String getIntranetServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort
	 *            the svcPort to set
	 */
	public static void setIntranetServerPort(String serverPort) {
		LocalConfig.serverPort = serverPort;
	}

	/**
	 * @return the adminName
	 */
	public static String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName
	 *            the adminName to set
	 */
	public static void setAdminName(String adminName) {
		LocalConfig.adminName = adminName;
	}

	/**
	 * @return the adminPass
	 */
	public static String getAdminPass() {
		return adminPass;
	}

	/**
	 * @param adminPass
	 *            the adminPass to set
	 */
	public static void setAdminPass(String adminPass) {
		LocalConfig.adminPass = adminPass;
	}

	public static Map<String, Integer> getTransferBranchTime() {
		return transferBranchTime;
	}

	public static void setTransferBranchesTime(String transferBranchTime) throws ServiceException {
		Map<String, Integer> keyValuePairs = new HashMap<String, Integer>();
		try {
			for (String keyValuePair : transferBranchTime.split(",")) {
				String[] splitted = keyValuePair.split(":");

				String key = splitted[0].trim();
				Integer value = Integer.parseInt(splitted[1]);

				keyValuePairs.put(key, value);
			}
		} catch (Exception e) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Please check transferBranchTime syntax (should be KeyValuePairs like this: \"DOSP:1, SPBE:48, P:24\")");
		}
		LocalConfig.transferBranchTime = keyValuePairs;
	}

	public static String getCurrencyCode() {
		return currencyCode;
	}

	public static void setCurrencyCode(String currencyCode) {
		LocalConfig.currencyCode = currencyCode;
	}

	public static String getBlockOrTrapStringFormattedOfTotalfines(String valueToFormat) {
		return stringFormatForTotalfines == null ? null : String.format(stringFormatForTotalfines, valueToFormat);
	}

	public static void setBlockOrTrapStringFormatForTotalfines(String stringFormat) {
		stringFormatForTotalfines = stringFormat;
	}

	public static String getBlockOrTrapStringFormattedOfExpired(String valueToFormat) {
		return stringFormatForExpired == null ? null : String.format(stringFormatForExpired, valueToFormat);
	}

	public static void setBlockOrTrapStringFormatForExpired(String stringFormat) {
		stringFormatForExpired = stringFormat;
	}

	public static String getBlockOrTrapStringFormattedOfDebarred(String valueToFormat) {
		return stringFormatForDebarred == null ? null : String.format(stringFormatForDebarred, valueToFormat);
	}

	public static void setBlockOrTrapStringFormatForDebarred(String stringFormat) {
		stringFormatForDebarred = stringFormat;
	}
}
