/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class OrganizationNameInformation {
    /**
     * Organization Name Type
     */
    protected OrganizationNameType organizationNameType;
    /**
     * Organization Name
     */
    protected String organizationName;

    public OrganizationNameType getOrganizationNameType() {
        return organizationNameType;
    }

    public void setOrganizationNameType(OrganizationNameType organizationNameType) {
        this.organizationNameType = organizationNameType;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
