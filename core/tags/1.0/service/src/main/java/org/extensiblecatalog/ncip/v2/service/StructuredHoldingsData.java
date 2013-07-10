/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Contains Holdings Chronology AND/OR Holdings Enumeration.
 */
public class StructuredHoldingsData {
    protected HoldingsChronology holdingsChronology;
    protected HoldingsEnumeration holdingsEnumeration;

    public HoldingsChronology getHoldingsChronology() {
        return holdingsChronology;
    }

    public void setHoldingsChronology(HoldingsChronology holdingsChronology) {
        this.holdingsChronology = holdingsChronology;
    }

    public HoldingsEnumeration getHoldingsEnumeration() {
        return holdingsEnumeration;
    }

    public void setHoldingsEnumeration(HoldingsEnumeration holdingsEnumeration) {
        this.holdingsEnumeration = holdingsEnumeration;
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
