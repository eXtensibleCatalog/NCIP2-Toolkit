/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PhysicalAddress {

    /**
     * Structured Address
     */
    protected StructuredAddress structuredAddress;
    /**
     * Unstructured Address
     */
    protected UnstructuredAddress unstructuredAddress;
    /**
     * PhysicalAddressType
     */
    protected PhysicalAddressType physicalAddressType;

    public StructuredAddress getStructuredAddress() {
        return structuredAddress;
    }

    public void setStructuredAddress(StructuredAddress structuredAddress) {
        this.structuredAddress = structuredAddress;
    }

    public UnstructuredAddress getUnstructuredAddress() {
        return unstructuredAddress;
    }

    public void setUnstructuredAddress(UnstructuredAddress unstructuredAddress) {
        this.unstructuredAddress = unstructuredAddress;
    }

    public PhysicalAddressType getPhysicalAddressType() {
        return physicalAddressType;
    }

    public void setPhysicalAddressType(PhysicalAddressType physicalAddressType) {
        this.physicalAddressType = physicalAddressType;
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
