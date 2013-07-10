/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ShippingInformation {
    /**
     * Shipping Instructions
     */
    protected String shippingInstructions;
    /**
     * Shipping Notes
     */
    protected String shippingNote;
    /**
     * Physical Address
     */
    protected PhysicalAddress physicalAddress;
    /**
     * Electronic Address
     */
    protected ElectronicAddress electronicAddress;

    public String getShippingInstructions() {
        return shippingInstructions;
    }

    public void setShippingInstructions(String shippingInstructions) {
        this.shippingInstructions = shippingInstructions;
    }

    public String getShippingNote() {
        return shippingNote;
    }

    public void setShippingNote(String shippingNote) {
        this.shippingNote = shippingNote;
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
