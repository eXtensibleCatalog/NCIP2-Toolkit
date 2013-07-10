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

public class UserAddressInformation {
    /**
     * User Address Role Type
     */
    protected UserAddressRoleType userAddressRoleType;
    /**
     * Valid From Date
     */
    protected GregorianCalendar validFromDate;
    /**
     * Valid To Date
     */
    protected GregorianCalendar validToDate;
    /**
     * Physical Address
     */
    protected PhysicalAddress physicalAddress;
    /**
     * Electronic Address
     */
    protected ElectronicAddress electronicAddress;

    public UserAddressRoleType getUserAddressRoleType() {
        return userAddressRoleType;
    }

    public void setUserAddressRoleType(UserAddressRoleType userAddressRoleType) {
        this.userAddressRoleType = userAddressRoleType;
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

    public PhysicalAddress getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(PhysicalAddress physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }

    public void setElectronicAddress(ElectronicAddress electronicAddress) {
        this.electronicAddress = electronicAddress;
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
