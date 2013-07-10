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

public class FiscalTransactionInformation {
    /**
     * FiscalActionType
     */
    protected FiscalActionType fiscalActionType;
    /**
     * FiscalTransactionReferenceId
     */
    protected FiscalTransactionReferenceId fiscalTransactionReferenceId;
    /**
     * RelatedFiscalTransactionReferenceId
     */
    protected List<RelatedFiscalTransactionReferenceId> relatedFiscalTransactionReferenceIds;
    /**
     * FiscalTransactionType
     */
    protected FiscalTransactionType fiscalTransactionType;
    /**
     * ValidFromDate
     */
    protected GregorianCalendar validFromDate;
    /**
     * ValidToDate
     */
    protected GregorianCalendar validToDate;
    /**
     * Amount
     */
    protected Amount amount;
    /**
     * PaymentMethodType
     */
    protected PaymentMethodType paymentMethodType;
    /**
     * FiscalTransactionDescription
     */
    protected String fiscalTransactionDescription;
    /**
     * Request Id
     */
    protected RequestId requestId;
    /**
     * Item Details
     */
    protected ItemDetails itemDetails;

    public FiscalActionType getFiscalActionType() {
        return fiscalActionType;
    }

    public void setFiscalActionType(FiscalActionType fiscalActionType) {
        this.fiscalActionType = fiscalActionType;
    }

    public FiscalTransactionReferenceId getFiscalTransactionReferenceId() {
        return fiscalTransactionReferenceId;
    }

    public void setFiscalTransactionReferenceId(FiscalTransactionReferenceId fiscalTransactionReferenceId) {
        this.fiscalTransactionReferenceId = fiscalTransactionReferenceId;
    }

    public List<RelatedFiscalTransactionReferenceId> getRelatedFiscalTransactionReferenceIds() {
        return relatedFiscalTransactionReferenceIds;
    }

    public void setRelatedFiscalTransactionReferenceIds(
        List<RelatedFiscalTransactionReferenceId> relatedFiscalTransactionReferenceIds) {
        this.relatedFiscalTransactionReferenceIds = relatedFiscalTransactionReferenceIds;
    }

    public FiscalTransactionType getFiscalTransactionType() {
        return fiscalTransactionType;
    }

    public void setFiscalTransactionType(FiscalTransactionType fiscalTransactionType) {
        this.fiscalTransactionType = fiscalTransactionType;
    }

    public GregorianCalendar getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(GregorianCalendar validFromDate) {
        this.validFromDate = validFromDate;
    }

    public GregorianCalendar getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(GregorianCalendar validToDate) {
        this.validToDate = validToDate;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public String getFiscalTransactionDescription() {
        return fiscalTransactionDescription;
    }

    public void setFiscalTransactionDescription(String fiscalTransactionDescription) {
        this.fiscalTransactionDescription = fiscalTransactionDescription;
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDetails itemDetails) {
        this.itemDetails = itemDetails;
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
