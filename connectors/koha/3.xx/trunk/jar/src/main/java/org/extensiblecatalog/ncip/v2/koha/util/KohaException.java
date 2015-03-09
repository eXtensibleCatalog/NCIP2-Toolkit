package org.extensiblecatalog.ncip.v2.koha.util;

import java.io.Serializable;

/**
 * 
 * @author General exception for interacting with koha
 *
 */
public class KohaException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5881855954246581333L;

	public KohaException(String message) {
		super(message);
	}
}
