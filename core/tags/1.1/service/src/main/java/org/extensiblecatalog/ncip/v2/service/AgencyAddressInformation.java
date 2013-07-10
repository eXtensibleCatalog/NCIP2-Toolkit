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
 * Carries data elements describing the AgencyAddressInformation.
 */
public class AgencyAddressInformation {

    /**
     * AgencyAddressRoleType
     */
    protected org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType agencyAddressRoleType;

    /**
     * Set AgencyAddressRoleType.
     */
    public void setAgencyAddressRoleType(
        org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType agencyAddressRoleType) {

        this.agencyAddressRoleType = agencyAddressRoleType;

    }

    /**
     * Get AgencyAddressRoleType.
     */
    public org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType getAgencyAddressRoleType() {

        return agencyAddressRoleType;

    }

    /**
     * ValidFromDate
     */
    protected GregorianCalendar validFromDate;

    /**
     * Set ValidFromDate.
     */
    public void setValidFromDate(GregorianCalendar validFromDate) {

        this.validFromDate = validFromDate;

    }

    /**
     * Get ValidFromDate.
     */
    public GregorianCalendar getValidFromDate() {

        return validFromDate;

    }

    /**
     * ValidToDate
     */
    protected GregorianCalendar validToDate;

    /**
     * Set ValidToDate.
     */
    public void setValidToDate(GregorianCalendar validToDate) {

        this.validToDate = validToDate;

    }

    /**
     * Get ValidToDate.
     */
    public GregorianCalendar getValidToDate() {

        return validToDate;

    }

    /**
     * PhysicalAddress
     */
    protected org.extensiblecatalog.ncip.v2.service.PhysicalAddress physicalAddress;

    /**
     * Set PhysicalAddress.
     */
    public void setPhysicalAddress(org.extensiblecatalog.ncip.v2.service.PhysicalAddress physicalAddress) {

        this.physicalAddress = physicalAddress;

    }

    /**
     * Get PhysicalAddress.
     */
    public org.extensiblecatalog.ncip.v2.service.PhysicalAddress getPhysicalAddress() {

        return physicalAddress;

    }

    /**
     * ElectronicAddress
     */
    protected org.extensiblecatalog.ncip.v2.service.ElectronicAddress electronicAddress;

    /**
     * Set ElectronicAddress.
     */
    public void setElectronicAddress(org.extensiblecatalog.ncip.v2.service.ElectronicAddress electronicAddress) {

        this.electronicAddress = electronicAddress;

    }

    /**
     * Get ElectronicAddress.
     */
    public org.extensiblecatalog.ncip.v2.service.ElectronicAddress getElectronicAddress() {

        return electronicAddress;

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

