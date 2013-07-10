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

public class RequestItemInitiationData implements NCIPInitiationData {

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
     * Initiation Header
     */
    protected InitiationHeader initiationHeader;
    /**
     * Authentication input
     */
    protected List<AuthenticationInput> authenticationInputs;
    /**
     * UserID
     */
    protected UserId userId;
    /**
     * Request scope type
     */
    protected RequestScopeType requestScopeType;
    /**
     * Request type
     */
    protected RequestType requestType;
    /**
     * Bibliographic id
     */
    protected List<BibliographicId> bibliographicIds;
    /**
     * Item id
     */
    protected List<ItemId> itemIds;
    /**
     * Acknowledged Fee Amount
     */
    protected AcknowledgedFeeAmount acknowledgedFeeAmount;
    /**
     * Acknowledged Item Use Restriction Type
     */
    protected List<ItemUseRestrictionType> acknowledgedItemUseRestrictionTypes;
    /**
     * Earliest Date Needed
     */
    protected GregorianCalendar earliestDateNeeded;
    /**
     * Item Optional Fields
     */
    protected ItemOptionalFields itemOptionalFields;
    /**
     * Mandated Action
     */
    protected MandatedAction mandatedAction;
    /**
     * Need Before Date
     */
    protected GregorianCalendar needBeforeDate;
    /**
     * Pickup Expiry Date
     */
    protected GregorianCalendar pickupExpiryDate;
    /**
     * Pickup Location
     */
    protected PickupLocation pickupLocation;
    /**
     * Paid Fee Amount
     */
    protected PaidFeeAmount paidFeeAmount;
    /**
     * Shipping Information
     */
    protected ShippingInformation shippingInformation;
    /**
     * Request Id
     */
    protected RequestId requestId;

    protected GregorianCalendar pickupDate;

    protected GregorianCalendar suspensionStartDate;

    protected GregorianCalendar suspensionEndDate;

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

    public List<AuthenticationInput> getAuthenticationInputs() {
        return authenticationInputs;
    }

    public AuthenticationInput getAuthenticationInput(int index) {
        return authenticationInputs.get(index);
    }

    public void setAuthenticationInputs(List<AuthenticationInput> authenticationInputs) {
        this.authenticationInputs = authenticationInputs;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public RequestScopeType getRequestScopeType() {
        return requestScopeType;
    }

    public void setRequestScopeType(RequestScopeType requestScopeType) {
        this.requestScopeType = requestScopeType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public List<BibliographicId> getBibliographicIds() {
        return bibliographicIds;
    }

    public BibliographicId getBibliographicId(int index) {
        return bibliographicIds.get(index);
    }

    public BibliographicId getBibliographicIds(int index) {
        return bibliographicIds.get(index);
    }

    public void setBibliographicIds(List<BibliographicId> bibliographicIds) {
        this.bibliographicIds = bibliographicIds;
    }

    public List<ItemId> getItemIds() {
        return itemIds;
    }

    public ItemId getItemId(int index) {
        return itemIds.get(index);
    }

    public void setItemIds(List<ItemId> itemIds) {
        this.itemIds = itemIds;
    }

    public AcknowledgedFeeAmount getAcknowledgedFeeAmount() {
        return acknowledgedFeeAmount;
    }

    public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount acknowledgedFeeAmount) {
        this.acknowledgedFeeAmount = acknowledgedFeeAmount;
    }

    public List<ItemUseRestrictionType> getAcknowledgedItemUseRestrictionTypes() {
        return acknowledgedItemUseRestrictionTypes;
    }

    public ItemUseRestrictionType getAcknowledgedItemUseRestrictionType(int index) {
        return acknowledgedItemUseRestrictionTypes.get(index);
    }

    public void setAcknowledgedItemUseRestrictionTypes(
        List<ItemUseRestrictionType> acknowledgedItemUseRestrictionTypes) {
        this.acknowledgedItemUseRestrictionTypes = acknowledgedItemUseRestrictionTypes;
    }

    public GregorianCalendar getEarliestDateNeeded() {
        return earliestDateNeeded;
    }

    public void setEarliestDateNeeded(GregorianCalendar earliestDateNeeded) {
        this.earliestDateNeeded = earliestDateNeeded;
    }

    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
    }

    public MandatedAction getMandatedAction() {
        return mandatedAction;
    }

    public void setMandatedAction(MandatedAction mandatedAction) {
        this.mandatedAction = mandatedAction;
    }

    public GregorianCalendar getNeedBeforeDate() {
        return needBeforeDate;
    }

    public void setNeedBeforeDate(GregorianCalendar needBeforeDate) {
        this.needBeforeDate = needBeforeDate;
    }

    public GregorianCalendar getPickupExpiryDate() {
        return pickupExpiryDate;
    }

    public void setPickupExpiryDate(GregorianCalendar pickupExpiryDate) {
        this.pickupExpiryDate = pickupExpiryDate;
    }

    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public PaidFeeAmount getPaidFeeAmount() {
        return paidFeeAmount;
    }

    public void setPaidFeeAmount(PaidFeeAmount paidFeeAmount) {
        this.paidFeeAmount = paidFeeAmount;
    }

    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(ShippingInformation shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    /**
     * Boolean fields representing UserElementTypes
     */
    protected boolean authenticationInputDesired;
    protected boolean blockOrTrapDesired;
    protected boolean dateOfBirthDesired;
    protected boolean nameInformationDesired;
    protected boolean userAddressInformationDesired;
    protected boolean userLanguageDesired;
    protected boolean userPrivilegeDesired;
    protected boolean userIdDesired;
    protected boolean previousUserIdDesired;

    //the user element types setter and getter
    public boolean getAuthenticationInputDesired() {
        return authenticationInputDesired;
    }
    public void setAuthenticationInputDesired(boolean authenticationInputDesired) {
        this.authenticationInputDesired = authenticationInputDesired;
    }
    public boolean getBlockOrTrapDesired() {
        return blockOrTrapDesired;
    }
    public void setBlockOrTrapDesired(boolean blockOrTrapDesired) {
        this.blockOrTrapDesired = blockOrTrapDesired;
    }
    public boolean getDateOfBirthDesired() {
        return dateOfBirthDesired;
    }
    public void setDateOfBirthDesired(boolean dateOfBirthDesired) {
        this.dateOfBirthDesired = dateOfBirthDesired;
    }
    public boolean getNameInformationDesired() {
        return nameInformationDesired;
    }
    public void setNameInformationDesired(boolean nameInformationDesired) {
        this.nameInformationDesired = nameInformationDesired;
    }
    public boolean getUserAddressInformationDesired() {
        return userAddressInformationDesired;
    }
    public void setUserAddressInformationDesired(
            boolean userAddressInformationDesired) {
        this.userAddressInformationDesired = userAddressInformationDesired;
    }
    public boolean getUserLanguageDesired() {
        return userLanguageDesired;
    }
    public void setUserLanguageDesired(boolean userLanguageDesired) {
        this.userLanguageDesired = userLanguageDesired;
    }
    public boolean getUserPrivilegeDesired() {
        return userPrivilegeDesired;
    }
    public void setUserPrivilegeDesired(boolean userPrivilegeDesired) {
        this.userPrivilegeDesired = userPrivilegeDesired;
    }
    public boolean getUserIdDesired() {
        return userIdDesired;
    }
    public void setUserIdDesired(boolean userIdDesired) {
        this.userIdDesired = userIdDesired;
    }
    public boolean getPreviousUserIdDesired() {
        return previousUserIdDesired;
    }
    public void setPreviousUserIdDesired(boolean previousUserIdDesired) {
        this.previousUserIdDesired = previousUserIdDesired;
    }

    public GregorianCalendar getSuspensionStartDate() {
        return suspensionStartDate;
    }

    public void setSuspensionStartDate(GregorianCalendar suspensionStartDate) {
        this.suspensionStartDate = suspensionStartDate;
    }

    public GregorianCalendar getSuspensionEndDate() {
        return suspensionEndDate;
    }

    public void setSuspensionEndDate(GregorianCalendar suspensionEndDate) {
        this.suspensionEndDate = suspensionEndDate;
    }

    public GregorianCalendar getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(GregorianCalendar pickupDate) {
        this.pickupDate = pickupDate;
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
