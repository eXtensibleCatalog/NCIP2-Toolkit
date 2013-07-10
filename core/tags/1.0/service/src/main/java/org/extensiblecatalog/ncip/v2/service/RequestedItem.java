/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
/**
 * Describes a RequestedItem result from an NCIP response
 */
public class RequestedItem {

    protected ItemId itemId;
    protected RequestId requestId;
    protected RequestType requestType;
    protected RequestStatusType requestStatusType;
    protected GregorianCalendar datePlaced;
    protected GregorianCalendar pickupDate;
    protected PickupLocation pickupLocation;
    protected GregorianCalendar pickupExpiryDate;
    protected BigDecimal reminderLevel;
    protected BigDecimal holdQueuePosition;
    protected String title;
    protected MediumType mediumType;

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
    public RequestType getRequestType() {
        return requestType;
    }
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    public RequestStatusType getRequestStatusType() {
        return requestStatusType;
    }
    public void setRequestStatusType(RequestStatusType requestStatusType) {
        this.requestStatusType = requestStatusType;
    }
    public GregorianCalendar getDatePlaced() {
        return datePlaced;
    }
    public void setDatePlaced(GregorianCalendar datePlaced) {
        this.datePlaced = datePlaced;
    }
    public GregorianCalendar getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(GregorianCalendar pickupDate) {
        this.pickupDate = pickupDate;
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
    public BigDecimal getReminderLevel() {
        return reminderLevel;
    }
    public void setReminderLevel(BigDecimal reminderLevel) {
        this.reminderLevel = reminderLevel;
    }
    public BigDecimal getHoldQueuePosition() {
        return holdQueuePosition;
    }
    public void setHoldQueuePosition(BigDecimal holdQueuePosition) {
        this.holdQueuePosition = holdQueuePosition;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public MediumType getMediumType() {
        return mediumType;
    }
    public void setMediumType(MediumType mediumType) {
        this.mediumType = mediumType;
    }

    /*
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
