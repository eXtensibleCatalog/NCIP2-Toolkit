/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * A sequence of Enumeration Level Instances, which together express a numeric or
 * alphabetic designation of a specific part of a bibliographic
 * item and show the relationship of that Item to the whole bibliographic item.
 */
public class HoldingsEnumeration {
    protected List<EnumerationLevelInstance> enumerationLevelInstances;

    public List<EnumerationLevelInstance> getEnumerationLevelInstances() {
        return enumerationLevelInstances;
    }

    public EnumerationLevelInstance getEnumerationLevelInstance(int index) {
        return enumerationLevelInstances.get(index);
    }

    public void setEnumerationLevelInstances(List<EnumerationLevelInstance> enumerationLevelInstances) {
        this.enumerationLevelInstances = enumerationLevelInstances;
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
