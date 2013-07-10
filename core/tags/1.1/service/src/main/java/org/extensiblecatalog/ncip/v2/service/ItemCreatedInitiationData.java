/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import java.util.GregorianCalendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * Carries data elements describing the ItemCreated.
 */
public class ItemCreatedInitiationData implements NCIPInitiationData {

//    public MessageType getMessageType() { return NCIPData.MessageType.INITIATION; }
//    public boolean isInitiationMessage() { return true; }
//    public boolean isResponseMessage() { return false; }

    /** Version attribute */
    protected String version;

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * InitiationHeader
     */
    protected org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader;

    /**
     * Set InitiationHeader.
     */
    public void setInitiationHeader(org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader) {

        this.initiationHeader = initiationHeader;

    }

    /**
     * Get InitiationHeader.
     */
    public org.extensiblecatalog.ncip.v2.service.InitiationHeader getInitiationHeader() {

        return initiationHeader;

    }

    /**
     * ItemId
     */
    protected org.extensiblecatalog.ncip.v2.service.ItemId itemId;

    /**
     * Set ItemId.
     */
    public void setItemId(org.extensiblecatalog.ncip.v2.service.ItemId itemId) {

        this.itemId = itemId;

    }

    /**
     * Get ItemId.
     */
    public org.extensiblecatalog.ncip.v2.service.ItemId getItemId() {

        return itemId;

    }

    /**
     * RequestId
     */
    protected org.extensiblecatalog.ncip.v2.service.RequestId requestId;

    /**
     * Set RequestId.
     */
    public void setRequestId(org.extensiblecatalog.ncip.v2.service.RequestId requestId) {

        this.requestId = requestId;

    }

    /**
     * Get RequestId.
     */
    public org.extensiblecatalog.ncip.v2.service.RequestId getRequestId() {

        return requestId;

    }

    /**
     * BibliographicDescription
     */
    protected org.extensiblecatalog.ncip.v2.service.BibliographicDescription bibliographicDescription;

    /**
     * Set BibliographicDescription.
     */
    public void setBibliographicDescription(
        org.extensiblecatalog.ncip.v2.service.BibliographicDescription bibliographicDescription) {

        this.bibliographicDescription = bibliographicDescription;

    }

    /**
     * Get BibliographicDescription.
     */
    public org.extensiblecatalog.ncip.v2.service.BibliographicDescription getBibliographicDescription() {

        return bibliographicDescription;

    }

    /**
     * ItemUseRestrictionTypes
     */
    protected List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> itemUseRestrictionTypes;

    /**
     * Set ItemUseRestrictionTypes.
     */
    public void setItemUseRestrictionTypes(
        List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> itemUseRestrictionTypes) {

        this.itemUseRestrictionTypes = itemUseRestrictionTypes;

    }

    /**
     * Get ItemUseRestrictionTypes.
     */
    public List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> getItemUseRestrictionTypes() {

        return itemUseRestrictionTypes;

    }

    /**
     * CirculationStatus
     */
    protected org.extensiblecatalog.ncip.v2.service.CirculationStatus circulationStatus;

    /**
     * Set CirculationStatus.
     */
    public void setCirculationStatus(org.extensiblecatalog.ncip.v2.service.CirculationStatus circulationStatus) {

        this.circulationStatus = circulationStatus;

    }

    /**
     * Get CirculationStatus.
     */
    public org.extensiblecatalog.ncip.v2.service.CirculationStatus getCirculationStatus() {

        return circulationStatus;

    }

    /**
     * ItemDescription
     */
    protected org.extensiblecatalog.ncip.v2.service.ItemDescription itemDescription;

    /**
     * Set ItemDescription.
     */
    public void setItemDescription(org.extensiblecatalog.ncip.v2.service.ItemDescription itemDescription) {

        this.itemDescription = itemDescription;

    }

    /**
     * Get ItemDescription.
     */
    public org.extensiblecatalog.ncip.v2.service.ItemDescription getItemDescription() {

        return itemDescription;

    }

    /**
     * Locations
     */
    protected List<org.extensiblecatalog.ncip.v2.service.Location> locations;

    /**
     * Set Locations.
     */
    public void setLocations(List<org.extensiblecatalog.ncip.v2.service.Location> locations) {

        this.locations = locations;

    }

    /**
     * Get Locations.
     */
    public List<org.extensiblecatalog.ncip.v2.service.Location> getLocations() {

        return locations;

    }

    /**
     * PhysicalCondition
     */
    protected org.extensiblecatalog.ncip.v2.service.PhysicalCondition physicalCondition;

    /**
     * Set PhysicalCondition.
     */
    public void setPhysicalCondition(org.extensiblecatalog.ncip.v2.service.PhysicalCondition physicalCondition) {

        this.physicalCondition = physicalCondition;

    }

    /**
     * Get PhysicalCondition.
     */
    public org.extensiblecatalog.ncip.v2.service.PhysicalCondition getPhysicalCondition() {

        return physicalCondition;

    }

    /**
     * SecurityMarker
     */
    protected org.extensiblecatalog.ncip.v2.service.SecurityMarker securityMarker;

    /**
     * Set SecurityMarker.
     */
    public void setSecurityMarker(org.extensiblecatalog.ncip.v2.service.SecurityMarker securityMarker) {

        this.securityMarker = securityMarker;

    }

    /**
     * Get SecurityMarker.
     */
    public org.extensiblecatalog.ncip.v2.service.SecurityMarker getSecurityMarker() {

        return securityMarker;

    }

    /**
     * SensitizationFlag
     */
    protected Boolean sensitizationFlag;

    /**
     * Set SensitizationFlag.
     */
    public void setSensitizationFlag(Boolean sensitizationFlag) {

        this.sensitizationFlag = sensitizationFlag;

    }

    /**
     * Get SensitizationFlag.
     */
    public Boolean getSensitizationFlag() {

        return sensitizationFlag;

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

