/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

public class CheckInItemResponseData implements NCIPResponseData {
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public RoutingInformation getRoutingInformation() {
        return routingInformation;
    }

    public void setRoutingInformation(RoutingInformation routingInformation) {
        this.routingInformation = routingInformation;
    }

    public FiscalTransactionInformation getFiscalTransactionInformation() {
        return fiscalTransactionInformation;
    }

    public void setFiscalTransactionInformation(FiscalTransactionInformation fiscalTransactionInformation) {
        this.fiscalTransactionInformation = fiscalTransactionInformation;
    }

    public ItemOptionalFields getItemOptionalFields() {
        return itemOptionalFields;
    }

    public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
        this.itemOptionalFields = itemOptionalFields;
    }

    public UserOptionalFields getUserOptionalFields() {
        return userOptionalFields;
    }

    public void setUserOptionalFields(UserOptionalFields userOptionalFields) {
        this.userOptionalFields = userOptionalFields;
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
     * User Id
     */
    protected UserId userId;
    /**
     * Routing Information
     */
    protected RoutingInformation routingInformation;
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
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
