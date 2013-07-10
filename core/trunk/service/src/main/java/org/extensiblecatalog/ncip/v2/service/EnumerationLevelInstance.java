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
 * Provides structured information that describes the hierarchical relationship of the specific part
 * held by an Agency to an entire bibliographic item, expressed in numerative terms.
 */
public class EnumerationLevelInstance {
    protected String enumerationCaption;
    protected BigDecimal enumerationLevel;
    protected String enumerationValue;

    public String getEnumerationCaption() {
        return enumerationCaption;
    }

    public void setEnumerationCaption(String enumerationCaption) {
        this.enumerationCaption = enumerationCaption;
    }

    public BigDecimal getEnumerationLevel() {
        return enumerationLevel;
    }

    public void setEnumerationLevel(BigDecimal enumerationLevel) {
        this.enumerationLevel = enumerationLevel;
    }

    public String getEnumerationValue() {
        return enumerationValue;
    }

    public void setEnumerationValue(String enumerationValue) {
        this.enumerationValue = enumerationValue;
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
