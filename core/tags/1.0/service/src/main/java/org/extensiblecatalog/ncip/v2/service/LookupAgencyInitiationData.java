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
 * Carries data elements describing the LookupAgency.
 */
public class LookupAgencyInitiationData implements NCIPInitiationData {

    /**
     * InitiationHeader
     */
    protected org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader;

    /**
     * Set InitiationHeader.
     */
    public void setInitiationHeader(org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader) {

        this.initiationHeader = initiationHeader;

    }

    /**
     * Get InitiationHeader.
     */
    public org.extensiblecatalog.ncip.v2.service.InitiationHeader getInitiationHeader() {

        return initiationHeader;

    }

    /**
     * AgencyId
     */
    protected org.extensiblecatalog.ncip.v2.service.AgencyId agencyId;

    /**
     * Set AgencyId.
     */
    public void setAgencyId(org.extensiblecatalog.ncip.v2.service.AgencyId agencyId) {

        this.agencyId = agencyId;

    }

    /**
     * Get AgencyId.
     */
    public org.extensiblecatalog.ncip.v2.service.AgencyId getAgencyId() {

        return agencyId;

    }

    /**
     * AgencyElementTypes
     */
    protected List<org.extensiblecatalog.ncip.v2.service.AgencyElementType> agencyElementTypes;

    /**
     * Set AgencyElementTypes.
     */
    public void setAgencyElementTypes(
        List<org.extensiblecatalog.ncip.v2.service.AgencyElementType> agencyElementTypes) {

        this.agencyElementTypes = agencyElementTypes;

    }

    /**
     * Get AgencyElementTypes.
     */
    public List<org.extensiblecatalog.ncip.v2.service.AgencyElementType> getAgencyElementTypes() {

        return agencyElementTypes;

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

