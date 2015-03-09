package org.extensiblecatalog.ncip.v2.aleph.util;

import java.util.HashMap;
import java.util.Map;

public class AlephPatronAddress {

	private Map<String, String> keyValuePairs;

	public AlephPatronAddress() {
		keyValuePairs = new HashMap<String, String>();
	}

	public String get(String key) {
		return keyValuePairs.get(key);
	}

	/**
	 * @return the z304address1
	 */
	public String getZ304address1() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_1_NODE);
	}

	/**
	 * @param z304address1
	 *            the z304address1 to set
	 */
	public void setZ304address1(String z304address1) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_1_NODE, z304address1);
	}

	/**
	 * @return the z304address2
	 */
	public String getZ304address2() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_2_NODE);
	}

	/**
	 * @param z304address2
	 *            the z304address2 to set
	 */
	public void setZ304address2(String z304address2) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_2_NODE, z304address2);
	}

	/**
	 * @return the z304address3
	 */
	public String getZ304address3() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_3_NODE);
	}

	/**
	 * @param z304address3
	 *            the z304address3 to set
	 */
	public void setZ304address3(String z304address3) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_3_NODE, z304address3);
	}

	/**
	 * @return the z304address4
	 */
	public String getZ304address4() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_4_NODE);
	}

	/**
	 * @param z304address4
	 *            the z304address4 to set
	 */
	public void setZ304address4(String z304address4) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_4_NODE, z304address4);
	}

	/**
	 * @return the z304address5
	 */
	public String getZ304address5() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_5_NODE);
	}

	/**
	 * @param z304address5
	 *            the z304address5 to set
	 */
	public void setZ304address5(String z304address5) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_5_NODE, z304address5);
	}

	/**
	 * @return the z304dateFrom
	 */
	public String getZ304dateFrom() {
		return keyValuePairs.get(AlephConstants.Z304_DATE_FROM_NODE);
	}

	/**
	 * @param z304dateFrom
	 *            the z304dateFrom to set
	 */
	public void setZ304dateFrom(String z304dateFrom) {
		keyValuePairs.put(AlephConstants.Z304_DATE_FROM_NODE, z304dateFrom);
	}

	/**
	 * @return the z304dateTo
	 */
	public String getZ304dateTo() {
		return keyValuePairs.get(AlephConstants.Z304_DATE_TO_NODE);
	}

	/**
	 * @param z304dateTo
	 *            the z304dateTo to set
	 */
	public void setZ304dateTo(String z304dateTo) {
		keyValuePairs.put(AlephConstants.Z304_DATE_TO_NODE, z304dateTo);
	}

	/**
	 * @return the z304zip
	 */
	public String getZ304zip() {
		return keyValuePairs.get(AlephConstants.Z304_ZIP_NODE);
	}

	/**
	 * @param z304zip
	 *            the z304zip to set
	 */
	public void setZ304zip(String z304zip) {
		keyValuePairs.put(AlephConstants.Z304_ZIP_NODE, z304zip);
	}

	/**
	 * @return the z304telephone1
	 */
	public String getZ304telephone1() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_1_NODE);
	}

	/**
	 * @param z304telephone1
	 *            the z304telephone1 to set
	 */
	public void setZ304telephone1(String z304telephone1) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_1_NODE, z304telephone1);
	}

	/**
	 * @return the z304telephone2
	 */
	public String getZ304telephone2() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_2_NODE);
	}

	/**
	 * @param z304telephone2
	 *            the z304telephone2 to set
	 */
	public void setZ304telephone2(String z304telephone2) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_2_NODE, z304telephone2);
	}

	/**
	 * @return the z304telephone3
	 */
	public String getZ304telephone3() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_3_NODE);
	}

	/**
	 * @param z304telephone3
	 *            the z304telephone3 to set
	 */
	public void setZ304telephone3(String z304telephone3) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_3_NODE, z304telephone3);
	}

	/**
	 * @return the z304telephone4
	 */
	public String getZ304telephone4() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_4_NODE);
	}

	/**
	 * @param z304telephone4
	 *            the z304telephone4 to set
	 */
	public void setZ304telephone4(String z304telephone4) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_4_NODE, z304telephone4);
	}

	/**
	 * @return the z304smsNumber
	 */
	public String getZ304smsNumber() {
		return keyValuePairs.get(AlephConstants.Z304_SMS_NUMBER_NODE);
	}

	/**
	 * @param z304smsNumber
	 *            the z304smsNumber to set
	 */
	public void setZ304smsNumber(String z304smsNumber) {
		keyValuePairs.put(AlephConstants.Z304_SMS_NUMBER_NODE, z304smsNumber);
	}

	/**
	 * @return the z303wantSms
	 */
	public String getZ303wantSms() {
		return keyValuePairs.get(AlephConstants.Z303_WANT_SMS_NODE);
	}

	/**
	 * @param z303wantSms
	 *            the z303wantSms to set
	 */
	public void setZ303wantSms(String z303wantSms) {
		keyValuePairs.put(AlephConstants.Z303_WANT_SMS_NODE, z303wantSms);
	}

	/**
	 * @return the z303plainHtml
	 */
	public String getZ303plainHtml() {
		return keyValuePairs.get(AlephConstants.Z303_PLAIN_HTML_NODE);
	}

	/**
	 * @param z303plainHtml
	 *            the z303plainHtml to set
	 */
	public void setZ303plainHtml(String z303plainHtml) {
		keyValuePairs.put(AlephConstants.Z303_PLAIN_HTML_NODE, z303plainHtml);
	}

	/**
	 * @return the z304emailAddress
	 */
	public String getZ304emailAddress() {
		return keyValuePairs.get(AlephConstants.Z304_EMAIL_ADDRESS_NODE);
	}

	/**
	 * @param z304emailAddress
	 *            the z304emailAddress to set
	 */
	public void setZ304emailAddress(String z304emailAddress) {
		keyValuePairs.put(AlephConstants.Z304_EMAIL_ADDRESS_NODE, z304emailAddress);
	}

	/**
	 * @return the z304address1
	 */
	public String getZ304address1MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_1_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304address1MaxLength
	 *            the z304address1 to set
	 */
	public void setZ304address1MaxLength(String z304address1MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_1_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304address1MaxLength);
	}

	/**
	 * @return the z304address2
	 */
	public String getZ304address2MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_2_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304address2MaxLength
	 *            the z304address2 to set
	 */
	public void setZ304address2MaxLength(String z304address2MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_2_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304address2MaxLength);
	}

	/**
	 * @return the z304address3
	 */
	public String getZ304address3MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_3_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304address3MaxLength
	 *            the z304address3 to set
	 */
	public void setZ304address3MaxLength(String z304address3MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_3_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304address3MaxLength);
	}

	/**
	 * @return the z304address4
	 */
	public String getZ304address4MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_4_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304address4MaxLength
	 *            the z304address4 to set
	 */
	public void setZ304address4MaxLength(String z304address4MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_4_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304address4MaxLength);
	}

	/**
	 * @return the z304address5
	 */
	public String getZ304address5MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ADDRESS_5_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304address5MaxLength
	 *            the z304address5 to set
	 */
	public void setZ304address5MaxLength(String z304address5MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ADDRESS_5_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304address5MaxLength);
	}

	/**
	 * @return the z304zip
	 */
	public String getZ304zipMaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_ZIP_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304zipMaxLength
	 *            the z304zip to set
	 */
	public void setZ304zipMaxLength(String z304zipMaxLength) {
		keyValuePairs.put(AlephConstants.Z304_ZIP_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304zipMaxLength);
	}

	/**
	 * @return the z304telephone1
	 */
	public String getZ304telephone1MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_1_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304telephone1MaxLength
	 *            the z304telephone1 to set
	 */
	public void setZ304telephone1MaxLength(String z304telephone1MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_1_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304telephone1MaxLength);
	}

	/**
	 * @return the z304telephone2
	 */
	public String getZ304telephone2MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_2_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304telephone2MaxLength
	 *            the z304telephone2 to set
	 */
	public void setZ304telephone2MaxLength(String z304telephone2MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_2_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304telephone2MaxLength);
	}

	/**
	 * @return the z304telephone3
	 */
	public String getZ304telephone3MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_3_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304telephone3MaxLength
	 *            the z304telephone3 to set
	 */
	public void setZ304telephone3MaxLength(String z304telephone3MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_3_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304telephone3MaxLength);
	}

	/**
	 * @return the z304telephone4
	 */
	public String getZ304telephone4MaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_TELEPHONE_4_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304telephone4MaxLength
	 *            the z304telephone4 to set
	 */
	public void setZ304telephone4MaxLength(String z304telephone4MaxLength) {
		keyValuePairs.put(AlephConstants.Z304_TELEPHONE_4_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304telephone4MaxLength);
	}

	/**
	 * @return the z304smsNumber
	 */
	public String getZ304smsNumberMaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_SMS_NUMBER_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304smsNumberMaxLength
	 *            the z304smsNumber to set
	 */
	public void setZ304smsNumberMaxLength(String z304smsNumberMaxLength) {
		keyValuePairs.put(AlephConstants.Z304_SMS_NUMBER_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304smsNumberMaxLength);
	}

	/**
	 * @return the z304emailAddress max_len attribute value
	 */
	public String getZ304emailAddressMaxLength() {
		return keyValuePairs.get(AlephConstants.Z304_EMAIL_ADDRESS_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE);
	}

	/**
	 * @param z304emailAddressMaxLength
	 *            the z304emailAddress max_len to set
	 */
	public void setZ304emailAddressMaxLength(String z304emailAddressMaxLength) {
		keyValuePairs.put(AlephConstants.Z304_EMAIL_ADDRESS_NODE + AlephConstants.MAX_LEN_NODE_ATTRIBUTE, z304emailAddressMaxLength);
	}

}
