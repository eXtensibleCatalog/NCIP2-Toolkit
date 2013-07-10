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

public class AcceptItemInitiationData implements NCIPInitiationData {

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

    public InitiationHeader getInitiationHeader() {
        return initiationHeader;
    }

    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
    }

    public MandatedAction getMandatedAction() {
        return mandatedAction;
    }

    public void setMandatedAction(MandatedAction mandatedAction) {
        this.mandatedAction = mandatedAction;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    public RequestedActionType getRequestedActionType() {
        return requestedActionType;
    }

    public void setRequestedActionType(RequestedActionType requestedActionType) {
        this.requestedActionType = requestedActionType;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public GregorianCalendar getDateForReturn() {
        return dateForReturn;
    }

    public void setDateForReturn(GregorianCalendar dateForReturn) {
        this.dateForReturn = dateForReturn;
    }

    public Boolean getIndeterminateLoanPeriodFlag() {
        return indeterminateLoanPeriodFlag;
    }

    public void setIndeterminateLoanPeriodFlag(Boolean indeterminateLoanPeriodFlag) {
        this.indeterminateLoanPeriodFlag = indeterminateLoanPeriodFlag;
    }

    public Boolean getNonReturnableFlag() {
        return nonReturnableFlag;
    }

    public void setNonReturnableFlag(Boolean nonReturnableFlag) {
        this.nonReturnableFlag = nonReturnableFlag;
    }

    public Boolean getRenewalNotPermitted() {
        return renewalNotPermitted;
    }

    public void setRenewalNotPermitted(Boolean renewalNotPermitted) {
        this.renewalNotPermitted = renewalNotPermitted;
    }

    public FiscalTransactionInformation getFiscalTransactionInformation() {
        return fiscalTransactionInformation;
    }

    public void setFiscalTransactionInformation(FiscalTransactionInformation fiscalTransactionInformation) {
        this.fiscalTransactionInformation = fiscalTransactionInformation;
    }

    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
    }

    public UserOptionalFields getUserOptionalFields() {
        return userOptionalFields;
    }

    public void setUserOptionalFields(UserOptionalFields userOptionalFields) {
        this.userOptionalFields = userOptionalFields;
    }

    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public GregorianCalendar getPickupExpiryDate() {
        return pickupExpiryDate;
    }

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
