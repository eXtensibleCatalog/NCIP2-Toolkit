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
import java.math.BigDecimal;

/**
 * Carries data elements describing the PromptInput.
 */
public class PromptInput {

    /**
     * AuthenticationInputType
     */
    protected org.extensiblecatalog.ncip.v2.service.AuthenticationInputType authenticationInputType;

    /**
     * Set AuthenticationInputType.
     */
    public void setAuthenticationInputType(
            org.extensiblecatalog.ncip.v2.service.AuthenticationInputType authenticationInputType) {

        this.authenticationInputType = authenticationInputType;

    }

    /**
     * Get AuthenticationInputType.
     */
    public org.extensiblecatalog.ncip.v2.service.AuthenticationInputType getAuthenticationInputType() {

        return authenticationInputType;

    }

    /**
     * AuthenticationDataFormatType
     */
    protected org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType authenticationDataFormatType;

    /**
     * Set AuthenticationDataFormatType.
     */
    public void setAuthenticationDataFormatType(
            org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType authenticationDataFormatType) {

        this.authenticationDataFormatType = authenticationDataFormatType;

    }

    /**
     * Get AuthenticationDataFormatType.
     */
    public org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType getAuthenticationDataFormatType() {

        return authenticationDataFormatType;

    }

    /**
     * SensitiveDataFlag
     */
    protected Boolean sensitiveDataFlag;

    /**
     * Set SensitiveDataFlag.
     */
    public void setSensitiveDataFlag(Boolean sensitiveDataFlag) {

        this.sensitiveDataFlag = sensitiveDataFlag;

    }

    /**
     * Get SensitiveDataFlag.
     */
    public Boolean getSensitiveDataFlag() {

        return sensitiveDataFlag;

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

