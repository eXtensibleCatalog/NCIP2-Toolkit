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

/**
 * LookupItemResponseData contains the data that is in a NCIP Lookup Item Response message.
 */
public class LookupItemResponseData implements NCIPResponseData {

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
     * ItemID
     */
    protected ItemId itemId;

    /**
     * RequestID
     */
    protected RequestId requestId;

    /**
     * Item Optional Fields
     */
    protected ItemOptionalFields itemOptionalFields;

    /**
     * Hold PickupDate
     */
    protected GregorianCalendar holdPickupDate;

    /**
     * Date Recalled
     */
    protected GregorianCalendar dateRecalled;

    /**
     * Item Transaction
     */
    protected ItemTransaction itemTransaction;

    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

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
     * Retrieves the hold pickup date
     *
     * @return the holdPickupDate
     */
    public GregorianCalendar getHoldPickupDate() {
        return holdPickupDate;
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
     * Retrieve the request id
     *
     * @return the requestId
     */
    public RequestId getRequestId() {
        return requestId;
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
     * Set the request id
     *
     * @param requestId the requestId to set
     */
    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    /**
     * Set the hold pickup date
     *
     * @param holdPickupDate the holdPickupDate to set
     */
    public void setHoldPickupDate(GregorianCalendar holdPickupDate) {
        this.holdPickupDate = holdPickupDate;
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

    /**
     * Retrieve the date recalled for the item
     *
     * @return the dateRecalled
     */
    public GregorianCalendar getDateRecalled() {
        return dateRecalled;
    }

    /**
     * Set the date recalled for the item
     *
     * @param dateRecalled the dateRecalled to set
     */
    public void setDateRecalled(GregorianCalendar dateRecalled) {
        this.dateRecalled = dateRecalled;
    }

    public void setItemTransaction(ItemTransaction itemTransaction) {
        this.itemTransaction = itemTransaction;
    }

    public ItemTransaction getItemTransaction() {
        return this.itemTransaction;
    }
}

