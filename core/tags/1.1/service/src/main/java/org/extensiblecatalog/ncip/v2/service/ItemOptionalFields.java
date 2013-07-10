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
import java.util.List;

/**
 * Carries data elements describing the Item Object.
 */
public class ItemOptionalFields {
    protected ItemDescription itemDescription;
    protected BibliographicDescription bibliographicDescription;
    protected CirculationStatus circulationStatus;
    protected ElectronicResource electronicResource;
    protected BigDecimal holdQueueLength;
    protected List<ItemUseRestrictionType> itemUseRestrictionTypes;
    protected List<Location> locations;
    protected PhysicalCondition physicalCondition;
    protected SecurityMarker securityMarker;
    protected Boolean sensitizationFlag = false;

    public ItemDescription getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(ItemDescription itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BibliographicDescription getBibliographicDescription() {
        return bibliographicDescription;
    }

    public void setBibliographicDescription(BibliographicDescription bibliographicDescription) {
        this.bibliographicDescription = bibliographicDescription;
    }

    public CirculationStatus getCirculationStatus() {
        return circulationStatus;
    }

    public void setCirculationStatus(CirculationStatus circulationStatus) {
        this.circulationStatus = circulationStatus;
    }

    public ElectronicResource getElectronicResource() {
        return electronicResource;
    }

    public void setElectronicResource(ElectronicResource electronicResource) {
        this.electronicResource = electronicResource;
    }

    public BigDecimal getHoldQueueLength() {
        return holdQueueLength;
    }

    public void setHoldQueueLength(BigDecimal holdQueueLength) {
        this.holdQueueLength = holdQueueLength;
    }

    public List<ItemUseRestrictionType> getItemUseRestrictionTypes() {
        return itemUseRestrictionTypes;
    }

    public ItemUseRestrictionType getItemUseRestrictionType(int index) {
        return itemUseRestrictionTypes.get(index);
    }

    public void setItemUseRestrictionTypes(List<ItemUseRestrictionType> itemUseRestrictionTypes) {
        this.itemUseRestrictionTypes = itemUseRestrictionTypes;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Location getLocation(int index) {
        return locations.get(index);
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(PhysicalCondition physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public SecurityMarker getSecurityMarker() {
        return securityMarker;
    }

    public void setSecurityMarker(SecurityMarker securityMarker) {
        this.securityMarker = securityMarker;
    }

    public boolean getSensitizationFlag() {
        return sensitizationFlag;
    }

    public void setSensitizationFlag(boolean sensitizationFlag) {
        this.sensitizationFlag = sensitizationFlag;
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
