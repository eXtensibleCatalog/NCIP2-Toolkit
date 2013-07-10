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
 * Data to initiate the LookupAgency service.
 */
public class LookupAgencyInitiationData implements NCIPInitiationData {


    /**
     * Version attribute
     */
    protected String version;

    /**
     * Get the version.
     */
    @Deprecated
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     */
    @Deprecated
    public void setVersion(String version) {
        this.version = version;
    }


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
    public InitiationHeader getInitiationHeader() {

        return initiationHeader;

    }

    /**
     * Relying Party Id
     */
    protected AgencyId relyingPartyId;
    /**
     * Get the RelyingPartyId.
     *
     * @return the RelyingPartyId
     */
    public AgencyId getRelyingPartyId() {
        return relyingPartyId;
    }

    /**
     * Set the RelyingPartyId.
     *
     * @param relyingPartyId the RelyingPartyId
     */
    public void setRelyingPartyId(AgencyId relyingPartyId) {
        this.relyingPartyId = relyingPartyId;
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
    public AgencyId getAgencyId() {

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
    public List<AgencyElementType> getAgencyElementTypes() {

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

