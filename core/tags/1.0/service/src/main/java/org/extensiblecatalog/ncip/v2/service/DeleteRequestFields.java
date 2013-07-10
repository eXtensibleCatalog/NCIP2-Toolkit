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
 * Carries data elements describing the DeleteRequestFields.
 */
public class DeleteRequestFields {

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
     * RequestStatusType
     */
    protected org.extensiblecatalog.ncip.v2.service.RequestStatusType requestStatusType;

    /**
     * Set RequestStatusType.
     */
    public void setRequestStatusType(org.extensiblecatalog.ncip.v2.service.RequestStatusType requestStatusType) {

        this.requestStatusType = requestStatusType;

    }

    /**
     * Get RequestStatusType.
     */
    public org.extensiblecatalog.ncip.v2.service.RequestStatusType getRequestStatusType() {

        return requestStatusType;

    }

    /**
     * ShippingInformation
     */
    protected org.extensiblecatalog.ncip.v2.service.ShippingInformation shippingInformation;

    /**
     * Set ShippingInformation.
     */
    public void setShippingInformation(org.extensiblecatalog.ncip.v2.service.ShippingInformation shippingInformation) {

        this.shippingInformation = shippingInformation;

    }

    /**
     * Get ShippingInformation.
     */
    public org.extensiblecatalog.ncip.v2.service.ShippingInformation getShippingInformation() {

        return shippingInformation;

    }

    /**
     * EarliestDateNeeded
     */
    protected GregorianCalendar earliestDateNeeded;

    /**
     * Set EarliestDateNeeded.
     */
    public void setEarliestDateNeeded(GregorianCalendar earliestDateNeeded) {

        this.earliestDateNeeded = earliestDateNeeded;

    }

    /**
     * Get EarliestDateNeeded.
     */
    public GregorianCalendar getEarliestDateNeeded() {

        return earliestDateNeeded;

    }

    /**
     * NeedBeforeDate
     */
    protected GregorianCalendar needBeforeDate;

    /**
     * Set NeedBeforeDate.
     */
    public void setNeedBeforeDate(GregorianCalendar needBeforeDate) {

        this.needBeforeDate = needBeforeDate;

    }

    /**
     * Get NeedBeforeDate.
     */
    public GregorianCalendar getNeedBeforeDate() {

        return needBeforeDate;

    }

    /**
     * PickupLocation
     */
    protected org.extensiblecatalog.ncip.v2.service.PickupLocation pickupLocation;

    /**
     * Set PickupLocation.
     */
    public void setPickupLocation(org.extensiblecatalog.ncip.v2.service.PickupLocation pickupLocation) {

        this.pickupLocation = pickupLocation;

    }

    /**
     * Get PickupLocation.
     */
    public org.extensiblecatalog.ncip.v2.service.PickupLocation getPickupLocation() {

        return pickupLocation;

    }

    /**
     * PickupExpiryDate
     */
    protected GregorianCalendar pickupExpiryDate;

    /**
     * Set PickupExpiryDate.
     */
    public void setPickupExpiryDate(GregorianCalendar pickupExpiryDate) {

        this.pickupExpiryDate = pickupExpiryDate;

    }

    /**
     * Get PickupExpiryDate.
     */
    public GregorianCalendar getPickupExpiryDate() {

        return pickupExpiryDate;

    }

    /**
     * DateOfUserRequest
     */
    protected GregorianCalendar dateOfUserRequest;

    /**
     * Set DateOfUserRequest.
     */
    public void setDateOfUserRequest(GregorianCalendar dateOfUserRequest) {

        this.dateOfUserRequest = dateOfUserRequest;

    }

    /**
     * Get DateOfUserRequest.
     */
    public GregorianCalendar getDateOfUserRequest() {

        return dateOfUserRequest;

    }

    /**
     * DateAvailable
     */
    protected GregorianCalendar dateAvailable;

    /**
     * Set DateAvailable.
     */
    public void setDateAvailable(GregorianCalendar dateAvailable) {

        this.dateAvailable = dateAvailable;

    }

    /**
     * Get DateAvailable.
     */
    public GregorianCalendar getDateAvailable() {

        return dateAvailable;

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
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {

        return ReflectionToStringBuilder.reflectionToString(this);

    }

}

