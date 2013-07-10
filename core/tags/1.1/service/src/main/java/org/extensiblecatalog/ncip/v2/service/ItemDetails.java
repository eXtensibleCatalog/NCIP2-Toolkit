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

public class ItemDetails {
    /**
     * Item Id
     */
    protected ItemId itemId;
    /**
     * Bibliographic Description
     */
    protected BibliographicDescription bibliographicDescription;
    /**
     * Date Checked Out
     */
    protected GregorianCalendar dateCheckedOut;
    /**
     * Date Renewed
     */
    protected List<GregorianCalendar> dateReneweds;
    /**
     * Date Due
     */
    protected GregorianCalendar dateDue;
    /**
     * Indeterminate Loan Period Flag
     */
    protected Boolean indeterminateLoanPeriodFlag;
    /**
     * NonReturnable Flag
     */
    protected Boolean nonReturnableFlag;
    /**
     * Date Returned
     */
    protected GregorianCalendar dateReturned;

    public ItemId getItemId() {
        return itemId;
    }

    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }

    public BibliographicDescription getBibliographicDescription() {
        return bibliographicDescription;
    }

    public void setBibliographicDescription(BibliographicDescription bibliographicDescription) {
        this.bibliographicDescription = bibliographicDescription;
    }

    public GregorianCalendar getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(GregorianCalendar dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
    }

    public List<GregorianCalendar> getDateReneweds() {
        return dateReneweds;
    }

    public GregorianCalendar getDateRenewed(int index) {
        return dateReneweds.get(index);
    }

    public void setDateReneweds(List<GregorianCalendar> dateReneweds) {
        this.dateReneweds = dateReneweds;
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

    public GregorianCalendar getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(GregorianCalendar dateReturned) {
        this.dateReturned = dateReturned;
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
