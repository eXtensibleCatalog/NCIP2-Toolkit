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
 * Carries data elements describing the CancelRequestItem.
 */
public class CancelRequestItemInitiationData implements NCIPInitiationData {

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
    public org.extensiblecatalog.ncip.v2.service.MandatedAction getMandatedAction() {

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
    public org.extensiblecatalog.ncip.v2.service.UserId getUserId() {

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
    public List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> getAuthenticationInputs() {

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
    public org.extensiblecatalog.ncip.v2.service.RequestType getRequestType() {

        return requestType;

    }

    /**
     * RequestScopeType
     */
    protected org.extensiblecatalog.ncip.v2.service.RequestScopeType requestScopeType;

    /**
     * Set RequestScopeType.
     */
    public void setRequestScopeType(org.extensiblecatalog.ncip.v2.service.RequestScopeType requestScopeType) {

        this.requestScopeType = requestScopeType;

    }

    /**
     * Get RequestScopeType.
     */
    public org.extensiblecatalog.ncip.v2.service.RequestScopeType getRequestScopeType() {

        return requestScopeType;

    }

    /**
     * AcknowledgedFeeAmount
     */
    protected org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount acknowledgedFeeAmount;

    /**
     * Set AcknowledgedFeeAmount.
     */
    public void setAcknowledgedFeeAmount(
        org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount acknowledgedFeeAmount) {

        this.acknowledgedFeeAmount = acknowledgedFeeAmount;

    }

    /**
     * Get AcknowledgedFeeAmount.
     */
    public org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount getAcknowledgedFeeAmount() {

        return acknowledgedFeeAmount;

    }

    /**
     * PaidFeeAmount
     */
    protected org.extensiblecatalog.ncip.v2.service.PaidFeeAmount paidFeeAmount;

    /**
     * Set PaidFeeAmount.
     */
    public void setPaidFeeAmount(org.extensiblecatalog.ncip.v2.service.PaidFeeAmount paidFeeAmount) {

        this.paidFeeAmount = paidFeeAmount;

    }

    /**
     * Get PaidFeeAmount.
     */
    public org.extensiblecatalog.ncip.v2.service.PaidFeeAmount getPaidFeeAmount() {

        return paidFeeAmount;

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

