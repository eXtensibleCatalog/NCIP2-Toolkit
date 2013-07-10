/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

public class NameInformation {

    /**
     * Personal Name Information
     */
    protected PersonalNameInformation personalNameInformation;
    /**
     * Organization Name Informations
     */
    protected List<OrganizationNameInformation> organizationNameInformations;

    public PersonalNameInformation getPersonalNameInformation() {
        return personalNameInformation;
    }

    public void setPersonalNameInformation(PersonalNameInformation personalNameInformation) {
        this.personalNameInformation = personalNameInformation;
    }

    public List<OrganizationNameInformation> getOrganizationNameInformations() {
        return organizationNameInformations;
    }

    public void setOrganizationNameInformations(List<OrganizationNameInformation> organizationNameInformations) {
        this.organizationNameInformations = organizationNameInformations;
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
