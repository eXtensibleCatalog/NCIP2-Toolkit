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
 * Describes a RequestedItemsCount result from an NCIP response
 */
public class RequestedItemsCount {

    protected RequestType requestType;
    protected CirculationStatus circulationStatus;
    protected ItemUseRestrictionType itemUseRestrictionType;
    protected BigDecimal requestedItemCountValue;

    public RequestType getRequestType() {
        return requestType;
    }
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
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
    public BigDecimal getRequestedItemCountValue() {
        return requestedItemCountValue;
    }
    public void setRequestedItemCountValue(BigDecimal requestedItemCountValue) {
        this.requestedItemCountValue = requestedItemCountValue;
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
