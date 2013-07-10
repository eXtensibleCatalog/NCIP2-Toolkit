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

/**
 * Describes a AccountDetails result from an NCIP response
 */
public class AccountDetails {

    protected GregorianCalendar accrualDate;

    protected FiscalTransactionInformation fiscalTransactionInformation;

    /**
     * Retrieves the accrual date
     *
     * @return the accrualDate
     */
    public GregorianCalendar getAccrualDate() {
        return accrualDate;
    }

    /**
     * Set the accrual date
     *
     * @param accrualDate the accrual date to set
     */
    public void setAccrualDate(GregorianCalendar accrualDate) {
        this.accrualDate = accrualDate;
    }

    /**
     * Retrieves the FiscalTransactionInformation
     *
     * @return FiscalTransactionInformation
     */
    public FiscalTransactionInformation getFiscalTransactionInformation() {
        return fiscalTransactionInformation;
    }

    /**
     * Set the Fiscal Transaction Information
     *
     * @param fiscalTransactionInformation the information to set
     */
    public void setFiscalTransactionInformation(FiscalTransactionInformation fiscalTransactionInformation) {
        this.fiscalTransactionInformation = fiscalTransactionInformation;
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
