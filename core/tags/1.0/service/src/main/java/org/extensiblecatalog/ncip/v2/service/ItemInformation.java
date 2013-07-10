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

public class ItemInformation {

    /**
     * Item Id
     */
    protected ItemId itemId;
    /**
     * Request Ids
     */
    protected List<RequestId> requestIds;
    /**
     * Current Borrower
     */
    protected CurrentBorrower currentBorrower;
    /**
     * Current Requesters
     */
    protected List<CurrentRequester> currentRequesters;
    /**
     * Date Due
     */
    protected GregorianCalendar dateDue;
    /**
     * Hold Pickup Date
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
     * Item Optional Fields
     */
    protected ItemOptionalFields itemOptionalFields;

    /**
     * Item Note
     */
    protected String itemNote;
    /**
     * Problems
     */
    protected List<Problem> problems;

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    public List<RequestId> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<RequestId> requestIds) {
        this.requestIds = requestIds;
    }

    public CurrentBorrower getCurrentBorrower() {
        return currentBorrower;
    }

    public void setCurrentBorrower(CurrentBorrower currentBorrower) {
        this.currentBorrower = currentBorrower;
    }

    public List<CurrentRequester> getCurrentRequesters() {
        return currentRequesters;
    }

    public void setCurrentRequesters(List<CurrentRequester> currentRequesters) {
        this.currentRequesters = currentRequesters;
    }

    public GregorianCalendar getDateDue() {
        return dateDue;
    }

    public void setDateDue(GregorianCalendar dateDue) {
        this.dateDue = dateDue;
    }

    public GregorianCalendar getHoldPickupDate() {
        return holdPickupDate;
    }

    public void setHoldPickupDate(GregorianCalendar holdPickupDate) {
        this.holdPickupDate = holdPickupDate;
    }

    public GregorianCalendar getDateRecalled() {
        return dateRecalled;
    }

    public void setDateRecalled(GregorianCalendar dateRecalled) {
        this.dateRecalled = dateRecalled;
    }

    public ItemTransaction getItemTransaction() {
        return itemTransaction;
    }

    public void setItemTransaction(ItemTransaction itemTransaction) {
        this.itemTransaction = itemTransaction;
    }

    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
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
