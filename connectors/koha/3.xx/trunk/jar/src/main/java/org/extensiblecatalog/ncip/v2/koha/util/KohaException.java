package org.extensiblecatalog.ncip.v2.koha.util;

public class KohaException extends Exception {

	private static final String DEFAULT_SHORT_MESSAGE = "Internal Error";
	public static final String INVALID_ITEMID_IDENTIFIER_VALUE_FORMAT = "Invalid ItemidIdentifierValue format";

	private String shortMessage;

	public KohaException(String message) {
		this(DEFAULT_SHORT_MESSAGE, message);
	}

	public KohaException(String shortMessage, String message) {
		super(message);
		this.shortMessage = shortMessage;
	}

	public String getShortMessage() {
		return shortMessage;
	}
}
