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
import java.util.GregorianCalendar;

/**
 * Contains field defining how a responder should organize the response data.
 */
public class ResponseElementControl {

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public BigDecimal getStartElement() {
        return startElement;
    }

    public void setStartElement(BigDecimal startElement) {
        this.startElement = startElement;
    }

    public BigDecimal getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(BigDecimal maximumCount) {
        this.maximumCount = maximumCount;
    }

    public SortField getSortField() {
        return sortField;
    }

    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }

    public SortOrderType getSortOrderType() {
        return sortOrderType;
    }

    public void setSortOrderType(SortOrderType sortOrderType) {
        this.sortOrderType = sortOrderType;
    }

    protected ElementType elementType;

    protected BigDecimal startElement;

    protected BigDecimal maximumCount;

    protected SortField sortField;

    protected SortOrderType sortOrderType;

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
