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
    protected BibliographicId bibliographicId;
    /**
     * Item id
     */
    protected ItemId itemId;
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

    public BibliographicId getBibliographicId() {
        return bibliographicId;
    }

    public void setBibliographicId(BibliographicId bibliographicId) {
        this.bibliographicId = bibliographicId;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
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
