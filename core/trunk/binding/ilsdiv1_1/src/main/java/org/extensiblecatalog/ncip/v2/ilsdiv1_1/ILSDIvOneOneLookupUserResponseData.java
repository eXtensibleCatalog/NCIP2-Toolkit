/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.ilsdiv1_1;

import org.extensiblecatalog.ncip.v2.service.LookupUserResponseData;

/**
 * Data to create response of the LookupItemSet service with respect to ILS-di v1.1 binding
 */
public class ILSDIvOneOneLookupUserResponseData extends LookupUserResponseData {

	protected LoanedItemsHistory loanedItemsHistory;

	public LoanedItemsHistory getLoanedItemsHistory() {
		return loanedItemsHistory;
	}

	public void setLoanedItemsHistory(LoanedItemsHistory loanedItemsHistory) {
		this.loanedItemsHistory = loanedItemsHistory;
	}
    
}
