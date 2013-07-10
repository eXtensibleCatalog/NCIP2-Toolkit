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

public class PaidFeeAmount {
    /**
     * Currency Code
     */
    protected CurrencyCode currencyCode;
    /**
     * Monetary value, the Amount in terms of the value of Currency Code.
     * <b>Note:</b> This is represented as an <i>Integer</i> expressed as positive, negative, or zero, multiplied
     * by ten to the power M, where M is the value of the Minor unit for that currency as
     * defined in ISO 4217, Section 6. For example: USD 25.17 is represented with the currency code for USD
     * and the monetaryValue of 2517 (<i>not</i> a floating point value such as 25.17).
     */
    protected BigDecimal monetaryValue;

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
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
