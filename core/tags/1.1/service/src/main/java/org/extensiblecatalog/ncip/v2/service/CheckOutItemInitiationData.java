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

/**
 * CheckOutItemInitiationData contains the data required to build an NCIP CheckOutItem message.
 */
public class CheckOutItemInitiationData implements NCIPInitiationData {

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
     * Mandated Action
     */
    protected MandatedAction mandatedAction;
    /**
     * User Id
     */
    protected UserId userId;
    /**
     * Authentication Input
     */
    protected List<AuthenticationInput> authenticationInputs;
    /**
     * ItemID
     */
    protected ItemId itemId;
    /**
     * RequestID
     */
    protected RequestId requestId;
    protected AcknowledgedFeeAmount acknowledgedFeeAmount;
    protected PaidFeeAmount paidFeeAmount;
    protected List<ItemUseRestrictionType> acknowledgedItemUseRestrictionTypes;
    protected ShippingInformation shippingInformation;
    protected Boolean resourceDesired;
    protected GregorianCalendar desiredDateDue;
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

    public MandatedAction getMandatedAction() {
        return mandatedAction;
    }

    public void setMandatedAction(MandatedAction mandatedAction) {
        this.mandatedAction = mandatedAction;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
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

    public AcknowledgedFeeAmount getAcknowledgedFeeAmount() {
        return acknowledgedFeeAmount;
    }

    public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount acknowledgedFeeAmount) {
        this.acknowledgedFeeAmount = acknowledgedFeeAmount;
    }

    public PaidFeeAmount getPaidFeeAmount() {
        return paidFeeAmount;
    }

    public void setPaidFeeAmount(PaidFeeAmount paidFeeAmount) {
        this.paidFeeAmount = paidFeeAmount;
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

    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(ShippingInformation shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public Boolean getResourceDesired() {
        return resourceDesired;
    }

    public void setResourceDesired(Boolean resourceDesired) {
        this.resourceDesired = resourceDesired;
    }

    public GregorianCalendar getDesiredDateDue() {
        return desiredDateDue;
    }

    public void setDesiredDateDue(GregorianCalendar desiredDateDue) {
        this.desiredDateDue = desiredDateDue;
    }

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
     * Retrieve the item id
     *
     * @return the itemId
     */
    public ItemId getItemId() {
        return itemId;
    }

    /**
     * Set the item id
     *
     * @param itemId the itemId to set
     */
    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    /**
     * Retrieve the request id
     *
     * @return the requestId
     */
    public RequestId getRequestId() {
        return requestId;
    }

    /**
     * Set the request id
     *
     * @param requestId the requestId to set
     */
    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

}
