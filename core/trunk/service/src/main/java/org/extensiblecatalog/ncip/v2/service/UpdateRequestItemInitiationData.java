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
 * Data to initiate the UpdateRequestItem service.
 */
public class UpdateRequestItemInitiationData implements NCIPInitiationData {


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
     * UserId
     */
    protected org.extensiblecatalog.ncip.v2.service.UserId userId;

    /**
     * Set UserId.
     */
    public void setUserId(org.extensiblecatalog.ncip.v2.service.UserId userId) {

        this.userId = userId;

    }

    /**
     * Get UserId.
     */
    public UserId getUserId() {

        return userId;

    }

    /**
     * AuthenticationInputs
     */
    protected List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> authenticationInputs;

    /**
     * Set AuthenticationInputs.
     */
    public void setAuthenticationInputs(
            List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> authenticationInputs) {

        this.authenticationInputs = authenticationInputs;

    }

    /**
     * Get AuthenticationInputs.
     */
    public List<AuthenticationInput> getAuthenticationInputs() {

        return authenticationInputs;

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
     * RequestType
     */
    protected org.extensiblecatalog.ncip.v2.service.RequestType requestType;

    /**
     * Set RequestType.
     */
    public void setRequestType(org.extensiblecatalog.ncip.v2.service.RequestType requestType) {

        this.requestType = requestType;

    }

    /**
     * Get RequestType.
     */
    public RequestType getRequestType() {

        return requestType;

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
     * DeleteRequestFields
     */
    protected org.extensiblecatalog.ncip.v2.service.DeleteRequestFields deleteRequestFields;

    /**
     * Set DeleteRequestFields.
     */
    public void setDeleteRequestFields(org.extensiblecatalog.ncip.v2.service.DeleteRequestFields deleteRequestFields) {

        this.deleteRequestFields = deleteRequestFields;

    }

    /**
     * Get DeleteRequestFields.
     */
    public DeleteRequestFields getDeleteRequestFields() {

        return deleteRequestFields;

    }

    /**
     * AddRequestFields
     */
    protected org.extensiblecatalog.ncip.v2.service.AddRequestFields addRequestFields;

    /**
     * Set AddRequestFields.
     */
    public void setAddRequestFields(org.extensiblecatalog.ncip.v2.service.AddRequestFields addRequestFields) {

        this.addRequestFields = addRequestFields;

    }

    /**
     * Get AddRequestFields.
     */
    public AddRequestFields getAddRequestFields() {

        return addRequestFields;

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

