/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import java.util.List;

/**
 * This class represents a location's name. In NCIP a location name is a hierarchy of {@link LocationNameInstance}s.
 */
public class LocationName {
    /**
     * A sequence of Location Name Instances, which together express the location
     * where an Item is housed at a particular point in time
     */
    protected List<LocationNameInstance> locationNameInstances;

    /**
     * Set the list of {@link LocationNameInstance}s for this location.
     *
     * @param locationNameInstances the list of LocationNameInstances
     */
    public void setLocationNameInstances(List<LocationNameInstance> locationNameInstances) {
        this.locationNameInstances = locationNameInstances;
    }

    /**
     * Get the list of {@link LocationNameInstance}s for this location.
     *
     * @return the list of LocationNameInstances
     */
    public List<LocationNameInstance> getLocationNameInstances() {
        return locationNameInstances;
    }
}
