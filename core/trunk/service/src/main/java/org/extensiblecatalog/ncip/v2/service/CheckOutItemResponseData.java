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
import java.util.List;

/**
 * CheckOutItemResponseData contains the data that is in a NCIP CheckOut Item Response message.
 */
public class CheckOutItemResponseData implements NCIPResponseData {

//    public MessageType getMessageType() { return NCIPData.MessageType.RESPONSE; }
//    public boolean isInitiationMessage() { return false; }
//    public boolean isResponseMessage() { return true; }

    /**
     * Version attribute
     */
    protected String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
     * Required Item Use Restriction Type
     */
    protected List<ItemUseRestrictionType> requiredItemUseRestrictionTypes;
    /**
     * ItemID
     */
    protected ItemId itemId;
    /**
     * User Id
     */
    protected UserId userId;
    /**
     * Date Due
     */
    protected GregorianCalendar dateDue;
    /**
     * Indeterminate Loan Period Flag
     */
    protected Boolean indeterminateLoanPeriodFlag;
    /**
     * Non-Returnable Flag
     */
    protected Boolean nonReturnableFlag;
    /**
     * Renewal Count
     */
    protected BigDecimal renewalCount;
    /**
     * Electronic Resource
     */
    protected ElectronicResource electronicResource;
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

    public Problem getProblem(int index) {
        return problems.get(index);
    }

    /**
     * Set the list of {@link Problem}s.
     *
     * @param problems
     */
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    /**
     * Retrieve the item id
     *
     * @return the itemId
     */
    public ItemId getItemId() {
        return itemId;
    }

    /**
     * Set the item id
     *
     * @param itemId the itemId to set
     */
    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    /**
     * Retrieve the descriptive information about the item
     *
     * @return the itemOptionalFields
     */
    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    /**
     * Set the descriptive information about the item
     *
     * @param itemOptionalFields the itemDescription to set
     */
    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
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

    public ItemUseRestrictionType getRequiredItemUseRestrictionType(int index) {
        return requiredItemUseRestrictionTypes.get(index);
    }

    public void setRequiredItemUseRestrictionTypes(List<ItemUseRestrictionType> requiredItemUseRestrictionTypes) {
        this.requiredItemUseRestrictionTypes = requiredItemUseRestrictionTypes;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public GregorianCalendar getDateDue() {
        return dateDue;
    }

    public void setDateDue(GregorianCalendar dateDue) {
        this.dateDue = dateDue;
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

    public BigDecimal getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(BigDecimal renewalCount) {
        this.renewalCount = renewalCount;
    }

    public ElectronicResource getElectronicResource() {
        return electronicResource;
    }

    public void setElectronicResource(ElectronicResource electronicResource) {
        this.electronicResource = electronicResource;
    }

    public FiscalTransactionInformation getFiscalTransactionInformation() {
        return fiscalTransactionInformation;
    }

    public void setFiscalTransactionInformation(FiscalTransactionInformation fiscalTransactionInformation) {
        this.fiscalTransactionInformation = fiscalTransactionInformation;
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

