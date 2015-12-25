/**
 * Copyright (c) 2015 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.ilsdiv1_1;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;

import java.math.BigDecimal;
import java.util.List;

public class LoanedItemsHistory {

    /**
     * Current page returned
     */
    protected BigDecimal page;

    /**
     * Last page that can be requested
     */
    protected BigDecimal lastPage;
    
    /**
     * {@LoanedItem}s as the User's history
     */
    protected List<LoanedItem> loanedItems;
    
	public BigDecimal getPage() {
		return page;
	}

	public void setPage(BigDecimal page) {
		this.page = page;
	}

    public BigDecimal getLastPage() {
		return lastPage;
	}

	public void setLastPage(BigDecimal lastPage) {
		this.lastPage = lastPage;
	}

    /**
     * Retrieve the list of {@LoanedItem}s.
     *
     * @return the list of loanedItems
     */
    public List<LoanedItem> getLoanedItems() {
        return loanedItems;
    }

    public LoanedItem getLoanedItem(int index) {
        return loanedItems.get(index);
    }

    /**
     * Set the list of {@link LoanedItem}s.
     *
     * @param loanedItems
     */
    public void setLoanedItems(List<LoanedItem> loanedItems) {
        this.loanedItems = loanedItems;
    }

	/**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
