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

/**
 * Data to initiate the AcceptItem service.
 */
public class AcceptItemInitiationData implements NCIPInitiationData {


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
     * Mandated Action
     */
    protected MandatedAction mandatedAction;
    /**
     * ItemID
     */
    protected ItemId itemId;
    /**
     * Request Id
     */
    protected RequestId requestId;
    /**
     * Requested Action Type
     */
    protected RequestedActionType requestedActionType;

    /**
     * Get the InitiationHeader.
     *
     * @return the InitiationHeader
     */
    public InitiationHeader getInitiationHeader() {
        return initiationHeader;
    }

    /**
     * Set the InitiationHeader.
     *
     * @param initiationHeader the InitiationHeader
     */
    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
    }

    /**
     * Get the MandatedAction.
     *
     * @return the MandatedAction
     */
    public MandatedAction getMandatedAction() {
        return mandatedAction;
    }

    /**
     * Set the MandatedAction.
     *
     * @param mandatedAction the MandatedAction
     */
    public void setMandatedAction(MandatedAction mandatedAction) {
        this.mandatedAction = mandatedAction;
    }

    /**
     * Get the ItemId.
     *
     * @return the ItemId
     */
    public ItemId getItemId() {
        return itemId;
    }

    /**
     * Set the ItemId.
     *
     * @param itemId the ItemId
     */
    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    /**
     * Get the RequestId.
     *
     * @return the RequestId
     */
    public RequestId getRequestId() {
        return requestId;
    }

    /**
     * Set the RequestId.
     *
     * @param requestId the RequestId
     */
    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    /**
     * Get the RequestedActionType.
     *
     * @return the RequestedActionType
     */
    public RequestedActionType getRequestedActionType() {
        return requestedActionType;
    }

    /**
     * Set the RequestedActionType.
     *
     * @param requestedActionType the RequestedActionType
     */
    public void setRequestedActionType(RequestedActionType requestedActionType) {
        this.requestedActionType = requestedActionType;
    }

    /**
     * Get the UserId.
     *
     * @return the UserId
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * Set the UserId.
     *
     * @param userId the UserId
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * Get the GregorianCalendar.
     *
     * @return the GregorianCalendar
     */
    public GregorianCalendar getDateForReturn() {
        return dateForReturn;
    }

    /**
     * Set the DateForReturn.
     *
     * @param dateForReturn the DateForReturn
     */
    public void setDateForReturn(GregorianCalendar dateForReturn) {
        this.dateForReturn = dateForReturn;
    }

    /**
     * Get the Boolean.
     *
     * @return the Boolean
     */
    public Boolean getIndeterminateLoanPeriodFlag() {
        return indeterminateLoanPeriodFlag;
    }

    /**
     * Set the IndeterminateLoanPeriodFlag.
     *
     * @param indeterminateLoanPeriodFlag the IndeterminateLoanPeriodFlag
     */
    public void setIndeterminateLoanPeriodFlag(Boolean indeterminateLoanPeriodFlag) {
        this.indeterminateLoanPeriodFlag = indeterminateLoanPeriodFlag;
    }

    /**
     * Get the Boolean.
     *
     * @return the Boolean
     */
    public Boolean getNonReturnableFlag() {
        return nonReturnableFlag;
    }

    /**
     * Set the NonReturnableFlag.
     *
     * @param nonReturnableFlag the NonReturnableFlag
     */
    public void setNonReturnableFlag(Boolean nonReturnableFlag) {
        this.nonReturnableFlag = nonReturnableFlag;
    }

    /**
     * Get the Boolean.
     *
     * @return the Boolean
     */
    public Boolean getRenewalNotPermitted() {
        return renewalNotPermitted;
    }

    /**
     * Set the RenewalNotPermitted.
     *
     * @param renewalNotPermitted the RenewalNotPermitted
     */
    public void setRenewalNotPermitted(Boolean renewalNotPermitted) {
        this.renewalNotPermitted = renewalNotPermitted;
    }

    /**
     * Get the FiscalTransactionInformation.
     *
     * @return the FiscalTransactionInformation
     */
    public FiscalTransactionInformation getFiscalTransactionInformation() {
        return fiscalTransactionInformation;
    }

    /**
     * Set the FiscalTransactionInformation.
     *
     * @param fiscalTransactionInformation the FiscalTransactionInformation
     */
    public void setFiscalTransactionInformation(FiscalTransactionInformation fiscalTransactionInformation) {
        this.fiscalTransactionInformation = fiscalTransactionInformation;
    }

    /**
     * Get the ItemOptionalFields.
     *
     * @return the ItemOptionalFields
     */
    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    /**
     * Set the ItemOptionalFields.
     *
     * @param itemOptionalFields the ItemOptionalFields
     */
    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
    }

    /**
     * Get the UserOptionalFields.
     *
     * @return the UserOptionalFields
     */
    public UserOptionalFields getUserOptionalFields() {
        return userOptionalFields;
    }

    /**
     * Set the UserOptionalFields.
     *
     * @param userOptionalFields the UserOptionalFields
     */
    public void setUserOptionalFields(UserOptionalFields userOptionalFields) {
        this.userOptionalFields = userOptionalFields;
    }

    /**
     * Get the PickupLocation.
     *
     * @return the PickupLocation
     */
    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    /**
     * Set the PickupLocation.
     *
     * @param pickupLocation the PickupLocation
     */
    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    /**
     * Get the GregorianCalendar.
     *
     * @return the GregorianCalendar
     */
    public GregorianCalendar getPickupExpiryDate() {
        return pickupExpiryDate;
    }

    /**
     * Set the PickupExpiryDate.
     *
     * @param pickupExpiryDate the PickupExpiryDate
     */
    public void setPickupExpiryDate(GregorianCalendar pickupExpiryDate) {
        this.pickupExpiryDate = pickupExpiryDate;
    }

    /**
     * User Id
     */
    protected UserId userId;
    /**
     * Date For Return
     */
    protected GregorianCalendar dateForReturn;
    /**
     * Indeterminate Loan Period Flag
     */
    protected Boolean indeterminateLoanPeriodFlag;
    /**
     * Non-returnable Flag
     */
    protected Boolean nonReturnableFlag;
    /**
     * Renewal Not Permitted
     */
    protected Boolean renewalNotPermitted;
    /**
     * Fiscal Transaction Information
     */
    protected FiscalTransactionInformation fiscalTransactionInformation;
    /**
     * Item Optional Fields
     */
    protected ItemOptionalFields itemOptionalFields;
    /**
     * User Optional Fields
     */
    protected UserOptionalFields userOptionalFields;
    /**
     * Pickup Location
     */
    protected PickupLocation pickupLocation;
    /**
     * Pickup Expiry Date
     */
    protected GregorianCalendar pickupExpiryDate;

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
