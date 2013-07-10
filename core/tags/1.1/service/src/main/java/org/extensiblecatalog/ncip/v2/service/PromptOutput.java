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
 * Carries data elements describing the PromptOutput.
 */
public class PromptOutput {

    /**
     * AuthenticationPromptData
     */
    protected String authenticationPromptData;

    /**
     * Set AuthenticationPromptData.
     */
    public void setAuthenticationPromptData(String authenticationPromptData) {

        this.authenticationPromptData = authenticationPromptData;

    }

    /**
     * Get AuthenticationPromptData.
     */
    public String getAuthenticationPromptData() {

        return authenticationPromptData;

    }

    /**
     * AuthenticationPromptType
     */
    protected org.extensiblecatalog.ncip.v2.service.AuthenticationPromptType authenticationPromptType;

    /**
     * Set AuthenticationPromptType.
     */
    public void setAuthenticationPromptType(
        org.extensiblecatalog.ncip.v2.service.AuthenticationPromptType authenticationPromptType) {

        this.authenticationPromptType = authenticationPromptType;

    }

    /**
     * Get AuthenticationPromptType.
     */
    public org.extensiblecatalog.ncip.v2.service.AuthenticationPromptType getAuthenticationPromptType() {

        return authenticationPromptType;

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

