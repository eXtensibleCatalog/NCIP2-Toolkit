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
 * Data to initiate the ItemShipped service.
 */
public class ItemShippedInitiationData implements NCIPInitiationData {


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
     * DateShipped
     */
    protected GregorianCalendar dateShipped;

    /**
     * Set DateShipped.
     */
    public void setDateShipped(GregorianCalendar dateShipped) {

        this.dateShipped = dateShipped;

    }

    /**
     * Get DateShipped.
     */
    public GregorianCalendar getDateShipped() {

        return dateShipped;

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
    public ShippingInformation getShippingInformation() {

        return shippingInformation;

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
    public ItemOptionalFields getItemOptionalFields() {

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
    public UserOptionalFields getUserOptionalFields() {

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

