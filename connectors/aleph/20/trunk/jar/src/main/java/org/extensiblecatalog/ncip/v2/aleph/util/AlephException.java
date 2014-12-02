package org.extensiblecatalog.ncip.v2.aleph.util;

import java.io.Serializable;

/**
 * 
 * @author General exception for interacting with aleph
 *
 */
public class AlephException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5881855954246581333L;

	public AlephException(String message) {
		super(message);
	}
}
