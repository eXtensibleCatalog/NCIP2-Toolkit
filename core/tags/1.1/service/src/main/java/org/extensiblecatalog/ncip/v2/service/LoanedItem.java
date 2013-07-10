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
 * Describes a LoanedItem result from an NCIP response
 */
public class LoanedItem {

    protected ItemId itemId;
    protected BigDecimal reminderLevel;
    protected GregorianCalendar dateDue;
    protected Boolean indeterminateLoanPeriodFlag;
    protected Amount amount;
    protected String title;
    protected MediumType mediumType;
    protected BigDecimal renewalCount;
    protected GregorianCalendar dateCheckedOut;
    protected BibliographicDescription bibliographicDescription;

    public ItemId getItemId() {
        return itemId;
    }
    public void setItemId(ItemId itemId) {
        this.itemId = itemId;
    }
    public BigDecimal getReminderLevel() {
        return reminderLevel;
    }
    public void setReminderLevel(BigDecimal reminderLevel) {
        this.reminderLevel = reminderLevel;
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
    public void setIndeterminateLoanPeriodFlag(
            Boolean indeterminateLoanPeriodFlag) {
        this.indeterminateLoanPeriodFlag = indeterminateLoanPeriodFlag;
    }
    public Amount getAmount() {
        return amount;
    }
    public void setAmount(Amount amount) {
        this.amount = amount;
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

    public BigDecimal getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(BigDecimal renewalCount) {
        this.renewalCount = renewalCount;
    }

    public GregorianCalendar getDateCheckedOut() {
        return dateCheckedOut;
    }

    public void setDateCheckedOut(GregorianCalendar dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
    }

    public BibliographicDescription getBibliographicDescription() {
        return bibliographicDescription;
    }

    public void setBibliographicDescription(BibliographicDescription bibliographicDescription) {
        this.bibliographicDescription = bibliographicDescription;
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
