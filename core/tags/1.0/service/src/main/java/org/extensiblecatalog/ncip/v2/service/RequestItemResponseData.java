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

public class RequestItemResponseData implements NCIPResponseData {
    /**
     * Response Header
     */
    protected ResponseHeader responseHeader;
    /**
     * Problems
     */
    protected List<Problem> problems;
    /**
     * Required Fee Amount
     */
    protected RequiredFeeAmount requiredFeeAmount;
    /**
     * Required Item Use Restriction Types
     */
    protected List<ItemUseRestrictionType> requiredItemUseRestrictionTypes;
    /**
     * Item Id
     */
    protected ItemId itemId;
    /**
     * Request Id
     */
    protected RequestId requestId;
    /**
     * User Id
     */
    protected UserId userId;
    /**
     * Request Type
     */
    protected RequestType requestType;
    /**
     * Request Scope Type
     */
    protected RequestScopeType requestScopeType;
    /**
     * Shipping Information
     */
    protected ShippingInformation shippingInformation;
    /**
     * Date Available
     */
    protected GregorianCalendar dateAvailable;
    /**
     * Hold Pickup Date
     */
    protected GregorianCalendar holdPickupDate;
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
     * Retrieve the response header.
     *
     * @return the response header
     */
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     * Set the response header
     *
     * @param responseHeader
     */
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     * Retrieve the list of {@Problem}s.
     *
     * @return the list of problems
     */
    public List<Problem> getProblems() {
        return problems;
    }

    /**
     * Set the list of {@link Problem}s.
     *
     * @param problems
     */
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public RequiredFeeAmount getRequiredFeeAmount() {
        return requiredFeeAmount;
    }

    public void setRequiredFeeAmount(RequiredFeeAmount requiredFeeAmount) {
        this.requiredFeeAmount = requiredFeeAmount;
    }

    public List<ItemUseRestrictionType> getRequiredItemUseRestrictionTypes() {
        return requiredItemUseRestrictionTypes;
    }

    public void setRequiredItemUseRestrictionTypes(List<ItemUseRestrictionType> requiredItemUseRestrictionTypes) {
        this.requiredItemUseRestrictionTypes = requiredItemUseRestrictionTypes;
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

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestScopeType getRequestScopeType() {
        return requestScopeType;
    }

    public void setRequestScopeType(RequestScopeType requestScopeType) {
        this.requestScopeType = requestScopeType;
    }

    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(ShippingInformation shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public GregorianCalendar getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(GregorianCalendar dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public GregorianCalendar getHoldPickupDate() {
        return holdPickupDate;
    }

    public void setHoldPickupDate(GregorianCalendar holdPickupDate) {
        this.holdPickupDate = holdPickupDate;
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
