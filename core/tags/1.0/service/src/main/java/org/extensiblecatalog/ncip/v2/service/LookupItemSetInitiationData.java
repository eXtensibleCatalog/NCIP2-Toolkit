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
 * LookupItemInitiationData contains the data required to build an NCIP LookupItem message.
 */
public class LookupItemSetInitiationData implements NCIPInitiationData {
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
     * Flag indicating whether the bibliographic description is desired.
     */
    protected boolean bibliographicDescriptionDesired;
    /**
     * Flag indicating whether the circulation status is desired.
     */
    protected boolean circulationStatusDesired;
    /**
     * Flag indicating whether the electronic resource is desired.
     */
    protected boolean electronicResourceDesired;
    /**
     * Flag indicating whether the hold queue length is desired.
     */
    protected boolean holdQueueLengthDesired;
    /**
     * Flag indicating whether the item description is desired.
     */
    protected boolean itemDescriptionDesired;
    /**
     * Flag indicating whether the item use restriction types are desired.
     */
    protected boolean itemUseRestrictionTypeDesired;
    /**
     * Flag indicating whether the location is desired.
     */
    protected boolean locationDesired;
    /**
     * Flag indicating whether the physical condition is desired.
     */
    protected boolean physicalConditionDesired;
    /**
     * Flag indicating whether the security marker is desired.
     */
    protected boolean securityMarkerDesired;
    /**
     * Flag indicating whether the sensitization flag is desired.
     */
    protected boolean sensitizationFlagDesired;
    /**
     * Maximum items count
     */
    protected BigDecimal maximumItemsCount;
    /**
     * Next Item Token
     */
    protected String nextItemToken;


    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

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
     * @param initiationHeader
     */
    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
    }

    /**
     * Retrieve the list of item ids
     *
     * @return the itemIds
     */
    public List<ItemId> getItemIds() {
        return itemIds;
    }

    /**
     * Set the item ids
     *
     * @param itemIds the list of itemIds to set
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

    /**
     * Set the list of holdings set ids
     *
     * @param holdingsSetIds the list of holdings set ids to set
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

    /**
     * Set the bibliographic ids
     *
     * @param bibliographicIds the list of bibliographicIds to set
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

    public boolean getBibliographicDescriptionDesired() {
        return bibliographicDescriptionDesired;
    }

    public void setBibliographicDescriptionDesired(boolean bibliographicDescriptionDesired) {
        this.bibliographicDescriptionDesired = bibliographicDescriptionDesired;
    }

    public boolean getCirculationStatusDesired() {
        return circulationStatusDesired;
    }

    public void setCirculationStatusDesired(boolean circulationStatusDesired) {
        this.circulationStatusDesired = circulationStatusDesired;
    }

    public boolean getElectronicResourceDesired() {
        return electronicResourceDesired;
    }

    public void setElectronicResourceDesired(boolean electronicResourceDesired) {
        this.electronicResourceDesired = electronicResourceDesired;
    }

    public boolean getHoldQueueLengthDesired() {
        return holdQueueLengthDesired;
    }

    public void setHoldQueueLengthDesired(boolean holdQueueLengthDesired) {
        this.holdQueueLengthDesired = holdQueueLengthDesired;
    }

    public boolean getItemDescriptionDesired() {
        return itemDescriptionDesired;
    }

    public void setItemDescriptionDesired(boolean itemDescriptionDesired) {
        this.itemDescriptionDesired = itemDescriptionDesired;
    }

    public boolean getItemUseRestrictionTypeDesired() {
        return itemUseRestrictionTypeDesired;
    }

    public void setItemUseRestrictionTypeDesired(boolean itemUseRestrictionTypeDesired) {
        this.itemUseRestrictionTypeDesired = itemUseRestrictionTypeDesired;
    }

    public boolean getLocationDesired() {
        return locationDesired;
    }

    public void setLocationDesired(boolean locationDesired) {
        this.locationDesired = locationDesired;
    }

    public boolean getPhysicalConditionDesired() {
        return physicalConditionDesired;
    }

    public void setPhysicalConditionDesired(boolean physicalConditionDesired) {
        this.physicalConditionDesired = physicalConditionDesired;
    }

    public boolean getSecurityMarkerDesired() {
        return securityMarkerDesired;
    }

    public void setSecurityMarkerDesired(boolean securityMarkerDesired) {
        this.securityMarkerDesired = securityMarkerDesired;
    }

    public boolean getSensitizationFlagDesired() {
        return sensitizationFlagDesired;
    }

    public void setSensitizationFlagDesired(boolean sensitizationFlagDesired) {
        this.sensitizationFlagDesired = sensitizationFlagDesired;
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

}
