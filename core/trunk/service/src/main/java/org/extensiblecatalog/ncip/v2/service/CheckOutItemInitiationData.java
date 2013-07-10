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
 * Data to initiate the CheckOutItem service.
 */
public class CheckOutItemInitiationData implements NCIPInitiationData {


    /**
     * Version attribute
     */
    protected String version;

    /**
     * Get the version.
     *
     * @return the version
     */
    @Deprecated
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     *
     * @param version the version
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
    /**
     * Acknowledged Fee Amount
     */
    protected AcknowledgedFeeAmount acknowledgedFeeAmount;
    /**
     * Paid Fee Amount
     */
    protected PaidFeeAmount paidFeeAmount;
    /**
     * Item Use Restriction Types
     */
    protected List<ItemUseRestrictionType> acknowledgedItemUseRestrictionTypes;
    /**
     * Shipping Information
     */
    protected ShippingInformation shippingInformation;
    /**
     * Whether the (electronic) resource should be returned in the response message.
     */
    protected Boolean resourceDesired;
    /**
     * Date Due
     */
    protected GregorianCalendar desiredDateDue;

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
     * @param itemId the itemId
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
     * @param requestId the requestId
     */
    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    /**
     * Get the mandated action
     *
     * @return the mandated action
     */
    public MandatedAction getMandatedAction() {
        return mandatedAction;
    }

    /**
     * Set the MandatedAction.
     *
     * @param mandatedAction the MandatedAction.
     */
    public void setMandatedAction(MandatedAction mandatedAction) {
        this.mandatedAction = mandatedAction;
    }

    /**
     * Get the user id
     *
     * @return the user id
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * Set the UserId.
     *
     * @param userId the UserId.
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * Get the  authentication inputs
     *
     * @return the  authentication inputs
     */
    public List<AuthenticationInput> getAuthenticationInputs() {
        return authenticationInputs;
    }

    /**
     * Get the authentication input at the provided index
     *
     * @param index the index
     * @return the authentication input at the provided index
     */
    public AuthenticationInput getAuthenticationInput(int index) {
        return authenticationInputs.get(index);
    }

    /**
     * Set the AuthenticationInputs.
     *
     * @param authenticationInputs the list of authentication input
     */
    public void setAuthenticationInputs(List<AuthenticationInput> authenticationInputs) {
        this.authenticationInputs = authenticationInputs;
    }

    /**
     * Get the acknowledged fee amount
     *
     * @return the acknowledged fee amount
     */
    public AcknowledgedFeeAmount getAcknowledgedFeeAmount() {
        return acknowledgedFeeAmount;
    }

    /**
     * Set the AcknowledgedFeeAmount.
     *
     * @param acknowledgedFeeAmount the AcknowledgedFeeAmount.
     */
    public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount acknowledgedFeeAmount) {
        this.acknowledgedFeeAmount = acknowledgedFeeAmount;
    }

    /**
     * Get the paid fee amount
     *
     * @return the paid fee amount
     */
    public PaidFeeAmount getPaidFeeAmount() {
        return paidFeeAmount;
    }

    /**
     * Set the PaidFeeAmount.
     *
     * @param paidFeeAmount the PaidFeeAmount.
     */
    public void setPaidFeeAmount(PaidFeeAmount paidFeeAmount) {
        this.paidFeeAmount = paidFeeAmount;
    }

    /**
     * Get the acknowledged item use restriction types
     *
     * @return the acknowledged item use restriction types
     */
    public List<ItemUseRestrictionType> getAcknowledgedItemUseRestrictionTypes() {
        return acknowledgedItemUseRestrictionTypes;
    }

    /**
     * Get the acknowledged item use restriction type at the provided index
     *
     * @param index the index
     * @return the acknowledged item use restriction type at the provided index
     */
    public ItemUseRestrictionType getAcknowledgedItemUseRestrictionType(int index) {
        return acknowledgedItemUseRestrictionTypes.get(index);
    }

    /**
     * Set the AcknowledgedItemUseRestrictionTypes.
     *
     * @param acknowledgedItemUseRestrictionTypes
     *         list of acknowledged item use restriction types
     */
    public void setAcknowledgedItemUseRestrictionTypes(
            List<ItemUseRestrictionType> acknowledgedItemUseRestrictionTypes) {
        this.acknowledgedItemUseRestrictionTypes = acknowledgedItemUseRestrictionTypes;
    }

    /**
     * Get the shipping information
     *
     * @return the shipping information
     */
    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    /**
     * Set the ShippingInformation.
     *
     * @param shippingInformation the ShippingInformation.
     */
    public void setShippingInformation(ShippingInformation shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    /**
     * Get whether the (electronic) resource should be returned in the response.
     *
     * @return whether the (electronic) resource should be returned in the response
     */
    public Boolean getResourceDesired() {
        return resourceDesired;
    }

    /**
     * Set the ResourceDesired.
     *
     * @param resourceDesired the ResourceDesired.
     */
    public void setResourceDesired(Boolean resourceDesired) {
        this.resourceDesired = resourceDesired;
    }

    /**
     * Get the desired due date
     *
     * @return the desired due date
     */
    public GregorianCalendar getDesiredDateDue() {
        return desiredDateDue;
    }

    /**
     * Set the DesiredDateDue.
     *
     * @param desiredDateDue the DesiredDateDue.
     */
    public void setDesiredDateDue(GregorianCalendar desiredDateDue) {
        this.desiredDateDue = desiredDateDue;
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
     * Whether the electronicResource is desired in the response.
     */
    protected boolean electronicResourceDesired;
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
     * Whether the ElectronicResource is desired in the response.
     *
     * @return the ElectronicResourceDesired
     */
    public boolean getElectronicResourceDesired() {
        return electronicResourceDesired;
    }

    /**
     * Set whether the ElectronicResource is desired in the response.
     *
     * @param electronicResourceDesired the ElectronicResourceDesired
     */
    public void setElectronicResourceDesired(boolean electronicResourceDesired) {
        this.electronicResourceDesired = electronicResourceDesired;
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
     * Whether the authenticationInput is desired in the response.
     */
    protected boolean authenticationInputDesired;
    /**
     * Whether the blockOrTrap is desired in the response.
     */
    protected boolean blockOrTrapDesired;
    /**
     * Whether the dateOfBirth is desired in the response.
     */
    protected boolean dateOfBirthDesired;
    /**
     * Whether the nameInformation is desired in the response.
     */
    protected boolean nameInformationDesired;
    /**
     * Whether the userAddressInformation is desired in the response.
     */
    protected boolean userAddressInformationDesired;
    /**
     * Whether the userLanguage is desired in the response.
     */
    protected boolean userLanguageDesired;
    /**
     * Whether the userPrivilege is desired in the response.
     */
    protected boolean userPrivilegeDesired;
    /**
     * Whether the userId is desired in the response.
     */
    protected boolean userIdDesired;
    /**
     * Whether the previousUserId is desired in the response.
     */
    protected boolean previousUserIdDesired;

    /**
     * Get the AuthenticationInput is desired in the response.
     *
     * @return the AuthenticationInputDesired
     */
    public boolean getAuthenticationInputDesired() {
        return authenticationInputDesired;
    }

    /**
     * Set whether the AuthenticationInput is desired in the response.
     *
     * @param authenticationInputDesired the AuthenticationInputDesired
     */
    public void setAuthenticationInputDesired(boolean authenticationInputDesired) {
        this.authenticationInputDesired = authenticationInputDesired;
    }

    /**
     * Get the BlockOrTrap is desired in the response.
     *
     * @return the BlockOrTrapDesired
     */
    public boolean getBlockOrTrapDesired() {
        return blockOrTrapDesired;
    }

    /**
     * Set whether the BlockOrTrap is desired in the response.
     *
     * @param blockOrTrapDesired the BlockOrTrapDesired
     */
    public void setBlockOrTrapDesired(boolean blockOrTrapDesired) {
        this.blockOrTrapDesired = blockOrTrapDesired;
    }

    /**
     * Get the DateOfBirth is desired in the response.
     *
     * @return the DateOfBirthDesired
     */
    public boolean getDateOfBirthDesired() {
        return dateOfBirthDesired;
    }

    /**
     * Set whether the DateOfBirth is desired in the response.
     *
     * @param dateOfBirthDesired the DateOfBirthDesired
     */
    public void setDateOfBirthDesired(boolean dateOfBirthDesired) {
        this.dateOfBirthDesired = dateOfBirthDesired;
    }

    /**
     * Get the NameInformation is desired in the response.
     *
     * @return the NameInformationDesired
     */
    public boolean getNameInformationDesired() {
        return nameInformationDesired;
    }

    /**
     * Set whether the NameInformation is desired in the response.
     *
     * @param nameInformationDesired the NameInformationDesired
     */
    public void setNameInformationDesired(boolean nameInformationDesired) {
        this.nameInformationDesired = nameInformationDesired;
    }

    /**
     * Get the UserAddressInformation is desired in the response.
     *
     * @return the UserAddressInformationDesired
     */
    public boolean getUserAddressInformationDesired() {
        return userAddressInformationDesired;
    }

    /**
     * Set whether the UserAddressInformation is desired in the response.
     *
     * @param userAddressInformationDesired the UserAddressInformationDesired
     */
    public void setUserAddressInformationDesired(
            boolean userAddressInformationDesired) {
        this.userAddressInformationDesired = userAddressInformationDesired;
    }

    /**
     * Get the UserLanguage is desired in the response.
     *
     * @return the UserLanguageDesired
     */
    public boolean getUserLanguageDesired() {
        return userLanguageDesired;
    }

    /**
     * Set whether the UserLanguage is desired in the response.
     *
     * @param userLanguageDesired the UserLanguageDesired
     */
    public void setUserLanguageDesired(boolean userLanguageDesired) {
        this.userLanguageDesired = userLanguageDesired;
    }

    /**
     * Get the UserPrivilege is desired in the response.
     *
     * @return the UserPrivilegeDesired
     */
    public boolean getUserPrivilegeDesired() {
        return userPrivilegeDesired;
    }

    /**
     * Set whether the UserPrivilege is desired in the response.
     *
     * @param userPrivilegeDesired the UserPrivilegeDesired
     */
    public void setUserPrivilegeDesired(boolean userPrivilegeDesired) {
        this.userPrivilegeDesired = userPrivilegeDesired;
    }

    /**
     * Get the UserId is desired in the response.
     *
     * @return the UserIdDesired
     */
    public boolean getUserIdDesired() {
        return userIdDesired;
    }

    /**
     * Set whether the UserId is desired in the response.
     *
     * @param userIdDesired the UserIdDesired
     */
    public void setUserIdDesired(boolean userIdDesired) {
        this.userIdDesired = userIdDesired;
    }

    /**
     * Get the PreviousUserId is desired in the response.
     *
     * @return the PreviousUserIdDesired
     */
    public boolean getPreviousUserIdDesired() {
        return previousUserIdDesired;
    }

    /**
     * Set whether the PreviousUserId is desired in the response.
     *
     * @param previousUserIdDesired the PreviousUserIdDesired
     */
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
