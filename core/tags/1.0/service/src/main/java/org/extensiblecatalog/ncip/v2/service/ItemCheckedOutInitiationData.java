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
 * Carries data elements describing the ItemCheckedOut.
 */
public class ItemCheckedOutInitiationData implements NCIPInitiationData {

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
     * DateDue
     */
    protected GregorianCalendar dateDue;

    /**
     * Set DateDue.
     */
    public void setDateDue(GregorianCalendar dateDue) {

        this.dateDue = dateDue;

    }

    /**
     * Get DateDue.
     */
    public GregorianCalendar getDateDue() {

        return dateDue;

    }

    /**
     * IndeterminateLoanPeriodFlag
     */
    protected Boolean indeterminateLoanPeriodFlag;

    /**
     * Set IndeterminateLoanPeriodFlag.
     */
    public void setIndeterminateLoanPeriodFlag(Boolean indeterminateLoanPeriodFlag) {

        this.indeterminateLoanPeriodFlag = indeterminateLoanPeriodFlag;

    }

    /**
     * Get IndeterminateLoanPeriodFlag.
     */
    public Boolean getIndeterminateLoanPeriodFlag() {

        return indeterminateLoanPeriodFlag;

    }

    /**
     * NonReturnableFlag
     */
    protected Boolean nonReturnableFlag;

    /**
     * Set NonReturnableFlag.
     */
    public void setNonReturnableFlag(Boolean nonReturnableFlag) {

        this.nonReturnableFlag = nonReturnableFlag;

    }

    /**
     * Get NonReturnableFlag.
     */
    public Boolean getNonReturnableFlag() {

        return nonReturnableFlag;

    }

    /**
     * ElectronicResourceProvidedFlag
     */
    protected Boolean electronicResourceProvidedFlag;

    /**
     * Set ElectronicResourceProvidedFlag.
     */
    public void setElectronicResourceProvidedFlag(Boolean electronicResourceProvidedFlag) {

        this.electronicResourceProvidedFlag = electronicResourceProvidedFlag;

    }

    /**
     * Get ElectronicResourceProvidedFlag.
     */
    public Boolean getElectronicResourceProvidedFlag() {

        return electronicResourceProvidedFlag;

    }

    /**
     * RenewalCount
     */
    protected BigDecimal renewalCount;

    /**
     * Set RenewalCount.
     */
    public void setRenewalCount(BigDecimal renewalCount) {

        this.renewalCount = renewalCount;

    }

    /**
     * Get RenewalCount.
     */
    public BigDecimal getRenewalCount() {

        return renewalCount;

    }

    /**
     * FiscalTransactionInformation
     */
    protected org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation fiscalTransactionInformation;

    /**
     * Set FiscalTransactionInformation.
     */
    public void setFiscalTransactionInformation(
        org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation fiscalTransactionInformation) {

        this.fiscalTransactionInformation = fiscalTransactionInformation;

    }

    /**
     * Get FiscalTransactionInformation.
     */
    public org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation getFiscalTransactionInformation() {

        return fiscalTransactionInformation;

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

