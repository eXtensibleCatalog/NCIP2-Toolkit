/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class FiscalTransactionReferenceId {
    /**
     * Agency Id
     */
    protected AgencyId agencyId;
    /**
     * Fiscal Transaction Identifier Value
     */
    protected String fiscalTransactionIdentifierValue;

    public AgencyId getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    public String getFiscalTransactionIdentifierValue() {
        return fiscalTransactionIdentifierValue;
    }

    public void setFiscalTransactionIdentifierValue(String fiscalTransactionIdentifierValue) {
        this.fiscalTransactionIdentifierValue = fiscalTransactionIdentifierValue;
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
