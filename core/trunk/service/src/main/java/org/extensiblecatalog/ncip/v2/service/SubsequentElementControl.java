/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

public class SubsequentElementControl {

    protected ElementType elementType;

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public BigDecimal getNextElement() {
        return nextElement;
    }

    public void setNextElement(BigDecimal nextElement) {
        this.nextElement = nextElement;
    }

    protected BigDecimal nextElement;

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
