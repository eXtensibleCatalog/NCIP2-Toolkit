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

public class UserPrivilege {
    /**
     * Agency Id
     */
    protected AgencyId agencyId;
    /**
     * Agency User Privilege Type
     */
    protected AgencyUserPrivilegeType agencyUserPrivilegeType;
    /**
     * Valid From Date
     */
    protected GregorianCalendar validFromDate;
    /**
     * Valid To Date
     */
    protected GregorianCalendar validToDate;
    /**
     * User Privilege Fee
     */
    protected UserPrivilegeFee userPrivilegeFee;
    /**
     * UserPrivilegeStatus
     */
    protected UserPrivilegeStatus userPrivilegeStatus;
    /**
     * UserPrivilegeDescription
     */
    protected String userPrivilegeDescription;

    public AgencyId getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    public AgencyUserPrivilegeType getAgencyUserPrivilegeType() {
        return agencyUserPrivilegeType;
    }

    public void setAgencyUserPrivilegeType(AgencyUserPrivilegeType agencyUserPrivilegeType) {
        this.agencyUserPrivilegeType = agencyUserPrivilegeType;
    }

    public GregorianCalendar getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(GregorianCalendar validFromDate) {
        this.validFromDate = validFromDate;
    }

    public GregorianCalendar getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(GregorianCalendar validToDate) {
        this.validToDate = validToDate;
    }

    public UserPrivilegeFee getUserPrivilegeFee() {
        return userPrivilegeFee;
    }

    public void setUserPrivilegeFee(UserPrivilegeFee userPrivilegeFee) {
        this.userPrivilegeFee = userPrivilegeFee;
    }

    public UserPrivilegeStatus getUserPrivilegeStatus() {
        return userPrivilegeStatus;
    }

    public void setUserPrivilegeStatus(UserPrivilegeStatus userPrivilegeStatus) {
        this.userPrivilegeStatus = userPrivilegeStatus;
    }

    public String getUserPrivilegeDescription() {
        return userPrivilegeDescription;
    }

    public void setUserPrivilegeDescription(String userPrivilegeDescription) {
        this.userPrivilegeDescription = userPrivilegeDescription;
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
