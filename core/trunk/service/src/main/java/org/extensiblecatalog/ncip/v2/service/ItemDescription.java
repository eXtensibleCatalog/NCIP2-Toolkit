/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Description of a specific physical or electronic Item belonging to an Agency's collection(s).
 */
public class ItemDescription {

    /**
     * Permanent relative physical location assigned to a bibliographic item
     */
    protected String callNumber;
    /**
     * The copy number of an Item held by an Agency
     */
    protected String copyNumber;
    /**
     * Holdings Information
     */
    protected HoldingsInformation holdingsInformation;
    /**
     * The level at which the Item is described
     */
    protected ItemDescriptionLevel itemDescriptionLevel;
    /**
     * the number of pieces that comprise this item
     */
    protected BigDecimal numberOfPieces;

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(String copyNumber) {
        this.copyNumber = copyNumber;
    }

    public HoldingsInformation getHoldingsInformation() {
        return holdingsInformation;
    }

    public void setHoldingsInformation(HoldingsInformation holdingsInformation) {
        this.holdingsInformation = holdingsInformation;
    }

    public ItemDescriptionLevel getItemDescriptionLevel() {
        return itemDescriptionLevel;
    }

    public void setItemDescriptionLevel(ItemDescriptionLevel itemDescriptionLevel) {
        this.itemDescriptionLevel = itemDescriptionLevel;
    }

    public BigDecimal getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(BigDecimal numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
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
