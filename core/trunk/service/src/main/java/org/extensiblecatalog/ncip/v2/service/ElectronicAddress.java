/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ElectronicAddress {
    /**
     * Electronic Address Data
     */
    protected String electronicAddressData;
    /**
     * Electronic Address Type
     */
    protected ElectronicAddressType electronicAddressType;

    public String getElectronicAddressData() {
        return electronicAddressData;
    }

    public void setElectronicAddressData(String electronicAddressData) {
        this.electronicAddressData = electronicAddressData;
    }

    public ElectronicAddressType getElectronicAddressType() {
        return electronicAddressType;
    }

    public void setElectronicAddressType(ElectronicAddressType electronicAddressType) {
        this.electronicAddressType = electronicAddressType;
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
