/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Identifies a user.
 */
public final class UserId {

    protected AgencyId agencyId;

    protected UserIdentifierType userIdentifierType;

    protected String userIdentifierValue;

    public AgencyId getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    public UserIdentifierType getUserIdentifierType() {
        return userIdentifierType;
    }

    public void setUserIdentifierType(UserIdentifierType userIdentifierType) {
        this.userIdentifierType = userIdentifierType;
    }

    public String getUserIdentifierValue() {
        return userIdentifierValue;
    }

    public void setUserIdentifierValue(String userIdentifierValue) {
        this.userIdentifierValue = userIdentifierValue;
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
