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
 * Carries data elements describing the Pending.
 */
public class Pending {

    /**
     * DateOfExpectedReply
     */
    protected GregorianCalendar dateOfExpectedReply;

    /**
     * Set DateOfExpectedReply.
     */
    public void setDateOfExpectedReply(GregorianCalendar dateOfExpectedReply) {

        this.dateOfExpectedReply = dateOfExpectedReply;

    }

    /**
     * Get DateOfExpectedReply.
     */
    public GregorianCalendar getDateOfExpectedReply() {

        return dateOfExpectedReply;

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

