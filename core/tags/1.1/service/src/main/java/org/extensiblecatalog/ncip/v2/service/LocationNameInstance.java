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
 * A entry in a hierarchically-ordered list of Location Name Instances
 * which together describe the location of an item.
 */
public class LocationNameInstance {
    public BigDecimal getLocationNameLevel() {
        return locationNameLevel;
    }

    public void setLocationNameLevel(BigDecimal locationNameLevel) {
        this.locationNameLevel = locationNameLevel;
    }

    public String getLocationNameValue() {
        return locationNameValue;
    }

    public void setLocationNameValue(String locationNameValue) {
        this.locationNameValue = locationNameValue;
    }

    /**
     * The level of the Location Name Instance in a sequence within Location Name
     */
    protected BigDecimal locationNameLevel;
    /**
     * The name of an Agency-specific location or sub-location where an Item is housed at a particular point in time
     */
    protected String locationNameValue;

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
