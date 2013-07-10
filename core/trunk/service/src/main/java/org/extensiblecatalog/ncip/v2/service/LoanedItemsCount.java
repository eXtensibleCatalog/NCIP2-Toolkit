/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

/**
 * Describes a LoanedItemsCount result from an NCIP response
 */
public class LoanedItemsCount {


    protected CirculationStatus circulationStatus;
    protected ItemUseRestrictionType itemUseRestrictionType;
    protected BigDecimal loanedItemCountValue;

    public CirculationStatus getCirculationStatus() {
        return circulationStatus;
    }

    public void setCirculationStatus(CirculationStatus circulationStatus) {
        this.circulationStatus = circulationStatus;
    }

    public ItemUseRestrictionType getItemUseRestrictionType() {
        return itemUseRestrictionType;
    }

    public void setItemUseRestrictionType(
            ItemUseRestrictionType itemUseRestrictionType) {
        this.itemUseRestrictionType = itemUseRestrictionType;
    }

    public BigDecimal getLoanedItemCountValue() {
        return loanedItemCountValue;
    }

    public void setLoanedItemCountValue(BigDecimal loanedItemCountValue) {
        this.loanedItemCountValue = loanedItemCountValue;
    }

    /*
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
