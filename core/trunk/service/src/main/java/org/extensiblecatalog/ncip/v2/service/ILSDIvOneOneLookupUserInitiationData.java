/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import java.math.BigDecimal;

/**
 * Data to initiate the LookupItemSet service.
 */
public class ILSDIvOneOneLookupUserInitiationData extends LookupUserInitiationData {

    /**
     * Maximum items count
     */
    protected BigDecimal maximumItemsCount;
    /**
     * Next Item Token
     */
    protected String nextItemToken;
    
    public BigDecimal getMaximumItemsCount() {
        return maximumItemsCount;
    }

    public void setMaximumItemsCount(BigDecimal maximumItemsCount) {
        this.maximumItemsCount = maximumItemsCount;
    }

    public String getNextItemToken() {
        return nextItemToken;
    }

    public void setNextItemToken(String nextItemToken) {
        this.nextItemToken = nextItemToken;
    }

}
