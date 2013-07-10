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
 * Data to initiate the LookupItemSet service.
 */
public class LookupItemSetInitiationData implements NCIPInitiationData {


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
     * Initiation Header
     */
    protected InitiationHeader initiationHeader;
    /**
     * Bibliographic Id
     */
    protected List<BibliographicId> bibliographicIds;
    /**
     * Item Id
     */
    protected List<ItemId> itemIds;
    /**
     * Holdings Set Ids
     */
    protected List<String> holdingsSetIds;
    /**
     * Flag indicating whether the current borrower's information is desired.
     */
    protected boolean currentBorrowerDesired;
    /**
     * Flag indicating whether the current requesters' information is desired.
     */
    protected boolean currentRequestersDesired;
    /**
     * Maximum items count
     */
    protected BigDecimal maximumItemsCount;
    /**
     * Next Item Token
     */
    protected String nextItemToken;


    /**
     * Retrieve the initiation header.
     *
     * @return the initiation header
     */
    public InitiationHeader getInitiationHeader() {
        return initiationHeader;
    }

    /**
     * Set the initiation header
     *
     * @param initiationHeader the InitiationHeader
     */
    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
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
     * Retrieve the list of item ids
     *
     * @return the itemIds
     */
    public List<ItemId> getItemIds() {
        return itemIds;
    }

    public ItemId getItemId(int index) {
        return itemIds.get(index);
    }

    /**
     * Set the item ids
     *
     * @param itemIds the list of itemIds
     */
    public void setItemIds(List<ItemId> itemIds) {
        this.itemIds = itemIds;
    }

    /**
     * Retrieve the list of holdings set ids
     *
     * @return the holdingsSetIds
     */
    public List<String> getHoldingsSetIds() {
        return holdingsSetIds;
    }

    public String getHoldingsSetId(int index) {
        return holdingsSetIds.get(index);
    }

    /**
     * Set the list of holdings set ids
     *
     * @param holdingsSetIds the list of holdings set ids
     */
    public void setHoldingsSetIds(List<String> holdingsSetIds) {
        this.holdingsSetIds = holdingsSetIds;
    }

    /**
     * Retrieve the list of bibliographic ids
     *
     * @return the bibliographicIds
     */
    public List<BibliographicId> getBibliographicIds() {
        return bibliographicIds;
    }

    public BibliographicId getBibliographicId(int index) {
        return bibliographicIds.get(index);
    }

    /**
     * Set the bibliographic ids
     *
     * @param bibliographicIds the list of bibliographicIds
     */
    public void setBibliographicIds(List<BibliographicId> bibliographicIds) {
        this.bibliographicIds = bibliographicIds;
    }

    public boolean getCurrentBorrowerDesired() {
        return currentBorrowerDesired;
    }

    public void setCurrentBorrowerDesired(boolean currentBorrowerDesired) {
        this.currentBorrowerDesired = currentBorrowerDesired;
    }

    public boolean getCurrentRequestersDesired() {
        return currentRequestersDesired;
    }

    public void setCurrentRequestersDesired(boolean currentRequestersDesired) {
        this.currentRequestersDesired = currentRequestersDesired;
    }

    public BigDecimal getMaximumItemsCount() {
        return maximumItemsCount;
    }

    public void setMaximumItemsCount(BigDecimal maximumItemsCount) {
        this.maximumItemsCount = maximumItemsCount;
    }

    public String getNextItemToken() {
        return nextItemToken;
    }

    public void setNextItemToken(String nextItemToken) {
        this.nextItemToken = nextItemToken;
    }

    /**
     * Whether the bibliographicDescription is desired in the response.
     */
    protected boolean bibliographicDescriptionDesired;
    /**
     * Whether the circulationStatus is desired in the response.
     */
    protected boolean circulationStatusDesired;
    /**
     * Whether the holdQueueLength is desired in the response.
     */
    protected boolean holdQueueLengthDesired;
    /**
     * Whether the itemDescription is desired in the response.
     */
    protected boolean itemDescriptionDesired;
    /**
     * Whether the itemUseRestrictionType is desired in the response.
     */
    protected boolean itemUseRestrictionTypeDesired;
    /**
     * Whether the location is desired in the response.
     */
    protected boolean locationDesired;
    /**
     * Whether the physicalCondition is desired in the response.
     */
    protected boolean physicalConditionDesired;
    /**
     * Whether the securityMarker is desired in the response.
     */
    protected boolean securityMarkerDesired;
    /**
     * Whether the sensitizationFlag is desired in the response.
     */
    protected boolean sensitizationFlagDesired;

    /**
     * Whether the BibliographicDescription is desired in the response.
     *
     * @return the BibliographicDescriptionDesired
     */
    public boolean getBibliographicDescriptionDesired() {
        return bibliographicDescriptionDesired;
    }

    /**
     * Set whether the BibliographicDescription is desired in the response.
     *
     * @param bibliographicDescriptionDesired
     *         the BibliographicDescriptionDesired
     */
    public void setBibliographicDescriptionDesired(boolean bibliographicDescriptionDesired) {
        this.bibliographicDescriptionDesired = bibliographicDescriptionDesired;
    }

    /**
     * Whether the CirculationStatus is desired in the response.
     *
     * @return the CirculationStatusDesired
     */
    public boolean getCirculationStatusDesired() {
        return circulationStatusDesired;
    }

    /**
     * Set whether the CirculationStatus is desired in the response.
     *
     * @param circulationStatusDesired the CirculationStatusDesired
     */
    public void setCirculationStatusDesired(boolean circulationStatusDesired) {
        this.circulationStatusDesired = circulationStatusDesired;
    }

    /**
     * Whether the HoldQueueLength is desired in the response.
     *
     * @return the HoldQueueLengthDesired
     */
    public boolean getHoldQueueLengthDesired() {
        return holdQueueLengthDesired;
    }

    /**
     * Set whether the HoldQueueLength is desired in the response.
     *
     * @param holdQueueLengthDesired the HoldQueueLengthDesired
     */
    public void setHoldQueueLengthDesired(boolean holdQueueLengthDesired) {
        this.holdQueueLengthDesired = holdQueueLengthDesired;
    }

    /**
     * Whether the ItemDescription is desired in the response.
     *
     * @return the ItemDescriptionDesired
     */
    public boolean getItemDescriptionDesired() {
        return itemDescriptionDesired;
    }

    /**
     * Set whether the ItemDescription is desired in the response.
     *
     * @param itemDescriptionDesired the ItemDescriptionDesired
     */
    public void setItemDescriptionDesired(boolean itemDescriptionDesired) {
        this.itemDescriptionDesired = itemDescriptionDesired;
    }

    /**
     * Whether the ItemUseRestrictionType is desired in the response.
     *
     * @return the ItemUseRestrictionTypeDesired
     */
    public boolean getItemUseRestrictionTypeDesired() {
        return itemUseRestrictionTypeDesired;
    }

    /**
     * Set whether the ItemUseRestrictionType is desired in the response.
     *
     * @param itemUseRestrictionTypeDesired the ItemUseRestrictionTypeDesired
     */
    public void setItemUseRestrictionTypeDesired(boolean itemUseRestrictionTypeDesired) {
        this.itemUseRestrictionTypeDesired = itemUseRestrictionTypeDesired;
    }

    /**
     * Whether the Location is desired in the response.
     *
     * @return the LocationDesired
     */
    public boolean getLocationDesired() {
        return locationDesired;
    }

    /**
     * Set whether the Location is desired in the response.
     *
     * @param locationDesired the LocationDesired
     */
    public void setLocationDesired(boolean locationDesired) {
        this.locationDesired = locationDesired;
    }

    /**
     * Whether the PhysicalCondition is desired in the response.
     *
     * @return the PhysicalConditionDesired
     */
    public boolean getPhysicalConditionDesired() {
        return physicalConditionDesired;
    }

    /**
     * Set whether the PhysicalCondition is desired in the response.
     *
     * @param physicalConditionDesired the PhysicalConditionDesired
     */
    public void setPhysicalConditionDesired(boolean physicalConditionDesired) {
        this.physicalConditionDesired = physicalConditionDesired;
    }

    /**
     * Whether the SecurityMarker is desired in the response.
     *
     * @return the SecurityMarkerDesired
     */
    public boolean getSecurityMarkerDesired() {
        return securityMarkerDesired;
    }

    /**
     * Set whether the SecurityMarker is desired in the response.
     *
     * @param securityMarkerDesired the SecurityMarkerDesired
     */
    public void setSecurityMarkerDesired(boolean securityMarkerDesired) {
        this.securityMarkerDesired = securityMarkerDesired;
    }

    /**
     * Whether the SensitizationFlag is desired in the response.
     *
     * @return the SensitizationFlagDesired
     */
    public boolean getSensitizationFlagDesired() {
        return sensitizationFlagDesired;
    }

    /**
     * Set whether the SensitizationFlag is desired in the response.
     *
     * @param sensitizationFlagDesired the SensitizationFlagDesired
     */
    public void setSensitizationFlagDesired(boolean sensitizationFlagDesired) {
        this.sensitizationFlagDesired = sensitizationFlagDesired;
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
