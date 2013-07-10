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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Object representing a "payment" on a "bill" in the Sirsi API output.

 */
public class Payment {
	
	@JsonProperty("amountPaid")
	private Amount amount;
	
	@JsonProperty("datePaid")
	private Date datePaid;
	
	@JsonProperty("paymentType")
	private String paymentType;

	/**
	 * @return the amount
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * @return the datePaid
	 */
	public Date getDatePaid() {
		return datePaid;
	}

	/**
	 * @param datePaid the datePaid to set
	 */
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	

}
