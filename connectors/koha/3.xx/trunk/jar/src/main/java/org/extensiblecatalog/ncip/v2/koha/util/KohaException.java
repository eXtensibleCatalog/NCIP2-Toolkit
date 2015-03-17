package org.extensiblecatalog.ncip.v2.koha.util;

public class KohaException extends Exception {

	private static final String DEFAULT_SHORT_MESSAGE = "Internal connector error";
	public static final String INVALID_ITEM_IDENTIFIER_VALUE_FORMAT = "Invalid ItemIdentifierValue format";
	public static final String ITEM_NOT_FOUND = "Item not found";
	public static final String BAD_REQUEST_400 = "400 Bad Request";

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

	public static KohaException create400BadRequestException() {
		return new KohaException(KohaException.BAD_REQUEST_400,
				"Invalid syntax provided. This is usually caused by invalid toolkit's logic or by ILS' updates. Either way needs to solve this problem by toolkit's code renewal.");
	}
}
