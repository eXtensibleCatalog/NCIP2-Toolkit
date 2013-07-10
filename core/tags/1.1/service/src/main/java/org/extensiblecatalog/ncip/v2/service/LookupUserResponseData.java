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
 * LookupItemResponseData contains the data that is in a NCIP Lookup Item Response message.
 */
public class LookupUserResponseData implements NCIPResponseData {

//    public MessageType getMessageType() { return NCIPData.MessageType.RESPONSE; }
//    public boolean isInitiationMessage() { return false; }
//    public boolean isResponseMessage() { return true; }

    /** Version attribute */
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
     * UserID
     */
    protected UserId userId;
    /**
     * UserFiscalAccounts
     */
    protected List<UserFiscalAccount> userFiscalAccounts;
    /**
     * LoanedItemsCounts
     */
    protected List<LoanedItemsCount> loanedItemsCounts;
    /**
     * LoanedItems
     */
    protected List<LoanedItem> loanedItems;
    /**
     * RequestedItemsCounts
     */
    protected List<RequestedItemsCount> requestedItemsCounts;
    /**
     * RequestedItems
     */
    protected List<RequestedItem> requestedItems;
    /**
     * User Optional Fields
     */
    protected UserOptionalFields userOptionalFields;

    protected UserFiscalAccountSummary userFiscalAccountSummary;

    protected List<SubsequentElementControl> subsequentElementControls;

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
     * Retrieve the user id
     *
     * @return the userId
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * Set the user id
     *
     * @param userId the userId to set
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * Retrieve the list of {@UserFiscalAccount}s.
     *
     * @return the list of userFiscalAccounts
     */
    public List<UserFiscalAccount> getUserFiscalAccounts() {
        return userFiscalAccounts;
    }

    public UserFiscalAccount getUserFiscalAccount(int index) {
        return userFiscalAccounts.get(index);
    }

    /**
     * Set the list of {@link UserFiscalAccount}s.
     *
     * @param userFiscalAccounts
     */
    public void setUserFiscalAccounts(List<UserFiscalAccount> userFiscalAccounts) {
        this.userFiscalAccounts = userFiscalAccounts;
    }

    /**
     * Retrieve the list of {@LoanedItemsCount}s.
     *
     * @return the list of loanedItemsCounts
     */
    public List<LoanedItemsCount> getLoanedItemsCounts() {
        return loanedItemsCounts;
    }

    public LoanedItemsCount getLoanedItemsCount(int index) {
        return loanedItemsCounts.get(index);
    }

    /**
     * Set the list of {@link LoanedItemsCount}s.
     *
     * @param loanedItemsCounts
     */
    public void setLoanedItemsCounts(List<LoanedItemsCount> loanedItemsCounts) {
        this.loanedItemsCounts = loanedItemsCounts;
    }

    /**
     * Retrieve the list of {@LoanedItem}s.
     *
     * @return the list of loanedItems
     */
    public List<LoanedItem> getLoanedItems() {
        return loanedItems;
    }

    public LoanedItem getLoanedItem(int index) {
        return loanedItems.get(index);
    }

    /**
     * Set the list of {@link LoanedItem}s.
     *
     * @param loanedItems
     */
    public void setLoanedItems(List<LoanedItem> loanedItems) {
        this.loanedItems = loanedItems;
    }

    /**
     * Retrieve the list of {@RequestedItemsCount}s.
     *
     * @return the list of requestedItemsCounts
     */
    public List<RequestedItemsCount> getRequestedItemsCounts() {
        return requestedItemsCounts;
    }

    public RequestedItemsCount getRequestedItemsCount(int index) {
        return requestedItemsCounts.get(index);
    }

    /**
     * Set the list of {@link RequestedItemsCount}s.
     *
     * @param requestedItemsCounts
     */
    public void setRequestedItemsCounts(List<RequestedItemsCount> requestedItemsCounts) {
        this.requestedItemsCounts = requestedItemsCounts;
    }

    /**
     * Retrieve the list of {@RequestedItem}s.
     *
     * @return the list of requestedItems
     */
    public List<RequestedItem> getRequestedItems() {
        return requestedItems;
    }

    public RequestedItem getRequestedItem(int index) {
        return requestedItems.get(index);
    }

    /**
     * Set the list of {@link RequestedItem}s.
     *
     * @param requestedItems
     */
    public void setRequestedItems(List<RequestedItem> requestedItems) {
        this.requestedItems = requestedItems;
    }

    /**
     * Retrieve the descriptive information about the user
     *
     * @return the userOptionalFields
     */
    public UserOptionalFields getUserOptionalFields() {
        return userOptionalFields;
    }

    /**
     * Set the descriptive information about the user
     *
     * @param userOptionalFields the userDescription to set
     */
    public void setUserOptionalFields(UserOptionalFields userOptionalFields) {
        this.userOptionalFields = userOptionalFields;
    }

    public UserFiscalAccountSummary getUserFiscalAccountSummary() {
        return userFiscalAccountSummary;
    }

    public void setUserFiscalAccountSummary(UserFiscalAccountSummary userFiscalAccountSummary) {
        this.userFiscalAccountSummary = userFiscalAccountSummary;
    }

    public List<SubsequentElementControl> getSubsequentElementControls() {
        return subsequentElementControls;
    }

    public SubsequentElementControl getSubsequentElementControl(int index) {
        return subsequentElementControls.get(index);
    }

    public void setSubsequentElementControls(List<SubsequentElementControl> subsequentElementControls) {
        this.subsequentElementControls = subsequentElementControls;
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

