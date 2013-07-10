/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import java.math.BigDecimal;


public class CheckoutTransactionReults {
	
	private String dueDateString;
	private BigDecimal renewalCount;
	private String statusCode;
	
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getDueDateString() {
		return dueDateString;
	}
	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}
	public BigDecimal getRenewalCount() {
		return renewalCount;
	}
	public void setRenewalCount(BigDecimal renewalCountString) {
		this.renewalCount = renewalCountString;
	}
	
	
	
}
