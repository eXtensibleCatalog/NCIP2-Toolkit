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
 * held by an Agency to a whole Item, expressed in chronological terms.
 */
public class ChronologyLevelInstance {
    protected String chronologyCaption;
    protected BigDecimal chronologyLevel;
    protected String chronologyValue;

    public String getChronologyCaption() {
        return chronologyCaption;
    }

    public void setChronologyCaption(String chronologyCaption) {
        this.chronologyCaption = chronologyCaption;
    }

    public BigDecimal getChronologyLevel() {
        return chronologyLevel;
    }

    public void setChronologyLevel(BigDecimal chronologyLevel) {
        this.chronologyLevel = chronologyLevel;
    }

    public String getChronologyValue() {
        return chronologyValue;
    }

    public void setChronologyValue(String chronologyValue) {
        this.chronologyValue = chronologyValue;
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
