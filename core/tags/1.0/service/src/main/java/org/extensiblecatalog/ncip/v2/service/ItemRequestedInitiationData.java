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
 * Carries data elements describing the ItemRequested.
 */
public class ItemRequestedInitiationData implements NCIPInitiationData {

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
     * BibliographicId
     */
    protected org.extensiblecatalog.ncip.v2.service.BibliographicId bibliographicId;

    /**
     * Set BibliographicId.
     */
    public void setBibliographicId(org.extensiblecatalog.ncip.v2.service.BibliographicId bibliographicId) {

        this.bibliographicId = bibliographicId;

    }

    /**
     * Get BibliographicId.
     */
    public org.extensiblecatalog.ncip.v2.service.BibliographicId getBibliographicId() {

        return bibliographicId;

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
     * ItemOptionalFields
     */
    protected org.extensiblecatalog.ncip.v2.service.ItemOptionalFields itemOptionalFields;

    /**
     * Set ItemOptionalFields.
     */
    public void setItemOptionalFields(org.extensiblecatalog.ncip.v2.service.ItemOptionalFields itemOptionalFields) {

        this.itemOptionalFields = itemOptionalFields;

    }

    /**
     * Get ItemOptionalFields.
     */
    public org.extensiblecatalog.ncip.v2.service.ItemOptionalFields getItemOptionalFields() {

        return itemOptionalFields;

    }

    /**
     * UserOptionalFields
     */
    protected org.extensiblecatalog.ncip.v2.service.UserOptionalFields userOptionalFields;

    /**
     * Set UserOptionalFields.
     */
    public void setUserOptionalFields(org.extensiblecatalog.ncip.v2.service.UserOptionalFields userOptionalFields) {

        this.userOptionalFields = userOptionalFields;

    }

    /**
     * Get UserOptionalFields.
     */
    public org.extensiblecatalog.ncip.v2.service.UserOptionalFields getUserOptionalFields() {

        return userOptionalFields;

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

