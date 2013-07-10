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
 * A sequence of Chronology Level Instances, which together express a time period
 * covered by the Item as a specific part of a bibliographic item and shows the relationship
 * of that Item to the entire bibliographic item.
 */
public class HoldingsChronology {
    protected List<ChronologyLevelInstance> chronologyLevelInstances;

    public List<ChronologyLevelInstance> getChronologyLevelInstances() {
        return chronologyLevelInstances;
    }

    public ChronologyLevelInstance getChronologyLevelInstance(int index) {
        return chronologyLevelInstances.get(index);
    }

    public void setChronologyLevelInstances(List<ChronologyLevelInstance> chronologyLevelInstances) {
        this.chronologyLevelInstances = chronologyLevelInstances;
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
