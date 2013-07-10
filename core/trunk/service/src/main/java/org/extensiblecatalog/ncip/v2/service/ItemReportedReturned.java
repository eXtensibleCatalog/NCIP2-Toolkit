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
 * Carries data elements describing the ItemReportedReturned.
 */
public class ItemReportedReturned {

    /**
     * DateReportedReturned
     */
    protected GregorianCalendar dateReportedReturned;

    /**
     * Set DateReportedReturned.
     */
    public void setDateReportedReturned(GregorianCalendar dateReportedReturned) {

        this.dateReportedReturned = dateReportedReturned;

    }

    /**
     * Get DateReportedReturned.
     */
    public GregorianCalendar getDateReportedReturned() {

        return dateReportedReturned;

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

