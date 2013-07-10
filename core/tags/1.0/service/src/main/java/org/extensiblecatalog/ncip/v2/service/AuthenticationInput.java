/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class AuthenticationInput {
    /**
     * Authentication Data Format Type
     */
    protected AuthenticationDataFormatType authenticationDataFormatType;
    /**
     * Authentication Input Data
     */
    protected String authenticationInputData;
    /**
     * Authentication Input Type
     */
    protected AuthenticationInputType authenticationInputType;

    public AuthenticationDataFormatType getAuthenticationDataFormatType() {
        return authenticationDataFormatType;
    }

    public void setAuthenticationDataFormatType(AuthenticationDataFormatType authenticationDataFormatType) {
        this.authenticationDataFormatType = authenticationDataFormatType;
    }

    public String getAuthenticationInputData() {
        return authenticationInputData;
    }

    public void setAuthenticationInputData(String authenticationInputData) {
        this.authenticationInputData = authenticationInputData;
    }

    public AuthenticationInputType getAuthenticationInputType() {
        return authenticationInputType;
    }

    public void setAuthenticationInputType(AuthenticationInputType authenticationInputType) {
        this.authenticationInputType = authenticationInputType;
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
