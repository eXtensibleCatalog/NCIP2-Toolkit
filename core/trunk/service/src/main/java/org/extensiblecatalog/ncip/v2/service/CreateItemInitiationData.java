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
 * Data to initiate the CreateItem service.
 */
public class CreateItemInitiationData implements NCIPInitiationData {


    /**
     * Version attribute
     */
    protected String version;

    /**
     * Get the version.
     */
    @Deprecated
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     */
    @Deprecated
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
    public InitiationHeader getInitiationHeader() {

        return initiationHeader;

    }

    /**
     * Relying Party Id
     */
    protected AgencyId relyingPartyId;
    /**
     * Get the RelyingPartyId.
     *
     * @return the RelyingPartyId
     */
    public AgencyId getRelyingPartyId() {
        return relyingPartyId;
    }

    /**
     * Set the RelyingPartyId.
     *
     * @param relyingPartyId the RelyingPartyId
     */
    public void setRelyingPartyId(AgencyId relyingPartyId) {
        this.relyingPartyId = relyingPartyId;
    }

    /**
     * MandatedAction
     */
    protected org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction;

    /**
     * Set MandatedAction.
     */
    public void setMandatedAction(org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction) {

        this.mandatedAction = mandatedAction;

    }

    /**
     * Get MandatedAction.
     */
    public MandatedAction getMandatedAction() {

        return mandatedAction;

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
    public ItemId getItemId() {

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
    public RequestId getRequestId() {

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
    public BibliographicDescription getBibliographicDescription() {

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
    public List<ItemUseRestrictionType> getItemUseRestrictionTypes() {

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
    public CirculationStatus getCirculationStatus() {

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
    public ItemDescription getItemDescription() {

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
    public List<Location> getLocations() {

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
    public PhysicalCondition getPhysicalCondition() {

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
    public SecurityMarker getSecurityMarker() {

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

