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
 * Electronic resource entity, such as a computer file or the full text of the Item.
 */
public class ElectronicResource {

    /**
     * The electronic resource entity, such as a computer file or the full text of the Item
     */
    protected String actualResource;
    /**
     * the data type of the electronic resource. Values are specified in the IANA Registry of Media Types
     */
    protected ElectronicDataFormatType electronicDataFormatType;
    /**
     * Provides a pointer to the resource rather than providing the resource itself
     */
    protected String referenceToResource;

    public String getActualResource() {
        return actualResource;
    }

    public void setActualResource(String actualResource) {
        this.actualResource = actualResource;
    }

    public ElectronicDataFormatType getElectronicDataFormatType() {
        return electronicDataFormatType;
    }

    public void setElectronicDataFormatType(ElectronicDataFormatType electronicDataFormatType) {
        this.electronicDataFormatType = electronicDataFormatType;
    }

    public String getReferenceToResource() {
        return referenceToResource;
    }

    public void setReferenceToResource(String referenceToResource) {
        this.referenceToResource = referenceToResource;
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
