package org.extensiblecatalog.ncip.v2.aleph.util;

public class ILSException extends Exception {

	/** Serial Id */
	private static final long serialVersionUID = -5767614856650456744L;

	/**
	 * Construct a new AuthenticationException
	 *
	 * @param explanation
	 *            the text message
	 * @param cause
	 *            the original exception
	 */
	public ILSException(String explanation, Throwable cause) {
		super(explanation, cause);
	}

	/**
	 * Construct a new AuthenticationException
	 *
	 * @param explanation
	 *            the text message
	 */
	public ILSException(String explanation) {
		super(explanation);
	}

	/**
	 * Construct a new AuthenticationException
	 *
	 * @param cause
	 *            the original exception
	 */
	public ILSException(Throwable cause) {
		super(cause);
	}

	/**
	 * Represent the exception as a String (for logging, etc.).
	 *
	 * @return the String representation
	 */
	public String toString() {
		return super.toString();
	}

}
