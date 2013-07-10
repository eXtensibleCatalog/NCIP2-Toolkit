/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import java.util.GregorianCalendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * Carries data elements describing the NoticeItem.
 */
public class NoticeItem {

    /**
     * ItemDetails
     */
    protected org.extensiblecatalog.ncip.v2.service.ItemDetails itemDetails;

    /**
     * Set ItemDetails.
     */
    public void setItemDetails(org.extensiblecatalog.ncip.v2.service.ItemDetails itemDetails) {

        this.itemDetails = itemDetails;

    }

    /**
     * Get ItemDetails.
     */
    public org.extensiblecatalog.ncip.v2.service.ItemDetails getItemDetails() {

        return itemDetails;

    }

    /**
     * Amount
     */
    protected org.extensiblecatalog.ncip.v2.service.Amount amount;

    /**
     * Set Amount.
     */
    public void setAmount(org.extensiblecatalog.ncip.v2.service.Amount amount) {

        this.amount = amount;

    }

    /**
     * Get Amount.
     */
    public org.extensiblecatalog.ncip.v2.service.Amount getAmount() {

        return amount;

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

