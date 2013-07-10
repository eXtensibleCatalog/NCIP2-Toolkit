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
 * The physical condition of an item.
 */
public class PhysicalCondition {
    /**
     * Details of the physical condition of an item, identified by Physical Condition Type
     */
    protected String physicalConditionDetails;
    /**
     * Specific details of any unusual physical conditions of an Item
     */
    protected PhysicalConditionType physicalConditionType;

    public String getPhysicalConditionDetails() {
        return physicalConditionDetails;
    }

    public void setPhysicalConditionDetails(String physicalConditionDetails) {
        this.physicalConditionDetails = physicalConditionDetails;
    }

    public PhysicalConditionType getPhysicalConditionType() {
        return physicalConditionType;
    }

    public void setPhysicalConditionType(PhysicalConditionType physicalConditionType) {
        this.physicalConditionType = physicalConditionType;
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
