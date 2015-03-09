package org.extensiblecatalog.ncip.v2.aleph.util;

import java.io.Serializable;

/**
 * @author General exception for interacting with aleph
 */
public class AlephException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5881855954246581333L;
	private static final String DEFAULT_SHORT_MESSAGE = "Internal connector error";
	public static final String INVALID_ITEM_IDENTIFIER_VALUE_FORMAT = "Invalid ItemIdentifierValue format";
	public static final String ITEM_NOT_FOUND = "Item not found";

	private String shortMessage;

	public AlephException(String message) {
		this(DEFAULT_SHORT_MESSAGE, message);
	}

	public AlephException(String shortMessage, String message) {
		super(message);
		this.shortMessage = shortMessage;
	}

	public String getShortMessage() {
		return shortMessage;
	}
}
