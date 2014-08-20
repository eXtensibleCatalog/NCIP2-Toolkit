package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.Serializable;

public class AlephException extends Exception implements Serializable {
	/**
	 * 
	 * @author Jiří Kozlovský - Moravian Library in Brno (Moravská zemská knihovna v Brně)
	 * 
	 *
	 */
	private static final long serialVersionUID = 65000L;
	
	public AlephException(String message) {
		super(message);
	}
}