/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * An object representing the response from SirsiDynix Symphony JSON service.
 * @author ajconsta@ncsu.edu, $LastChangedBy$
 * @since FIXME
 * @version $LastChangedRevision$
 */
public class SymphonyResponse<T> {

	@JsonProperty("outcome")
	private String outcome;
	
	@JsonProperty("message")
	private String message;

    @JsonProperty("userMessage")
    private String userMessage;
	
	private String responseCode;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	private boolean error;

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}


	/**
     * Get the message reported by the backend; this <em>may</em> be suitable for
     * display to users if <code>userMessage</code> has not been set.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


    /**
     * Gets an error message suitable for displaying to the user.  The backend will typically supply a
     * value for this property when the error message reported by Sirsi is unsuitable for display to users.
     * @return
     */
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }


    /**
     * Gets a string suitable for reporting a problem to the user.
     * @return
     */
    public String getErrorMessage() {
        return userMessage == null ? message : userMessage;
    }
}
