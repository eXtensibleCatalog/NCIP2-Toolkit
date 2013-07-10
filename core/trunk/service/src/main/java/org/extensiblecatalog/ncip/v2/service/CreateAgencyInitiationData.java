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
 * Data to initiate the CreateAgency service.
 */
public class CreateAgencyInitiationData implements NCIPInitiationData {


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
     * MandatedAction
     */
    protected org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction;

    /**
     * Set MandatedAction.
     */
    public void setMandatedAction(org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction) {

        this.mandatedAction = mandatedAction;

    }

    /**
     * Get MandatedAction.
     */
    public MandatedAction getMandatedAction() {

        return mandatedAction;

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
     * OrganizationNameInformations
     */
    protected List<org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation> organizationNameInformations;

    /**
     * Set OrganizationNameInformations.
     */
    public void setOrganizationNameInformations(
            List<org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation> organizationNameInformations) {

        this.organizationNameInformations = organizationNameInformations;

    }

    /**
     * Get OrganizationNameInformations.
     */
    public List<OrganizationNameInformation> getOrganizationNameInformations() {

        return organizationNameInformations;

    }

    /**
     * AgencyAddressInformations
     */
    protected List<org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation> agencyAddressInformations;

    /**
     * Set AgencyAddressInformations.
     */
    public void setAgencyAddressInformations(
            List<org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation> agencyAddressInformations) {

        this.agencyAddressInformations = agencyAddressInformations;

    }

    /**
     * Get AgencyAddressInformations.
     */
    public List<AgencyAddressInformation> getAgencyAddressInformations() {

        return agencyAddressInformations;

    }

    /**
     * AuthenticationPrompts
     */
    protected List<org.extensiblecatalog.ncip.v2.service.AuthenticationPrompt> authenticationPrompts;

    /**
     * Set AuthenticationPrompts.
     */
    public void setAuthenticationPrompts(
            List<org.extensiblecatalog.ncip.v2.service.AuthenticationPrompt> authenticationPrompts) {

        this.authenticationPrompts = authenticationPrompts;

    }

    /**
     * Get AuthenticationPrompts.
     */
    public List<AuthenticationPrompt> getAuthenticationPrompts() {

        return authenticationPrompts;

    }

    /**
     * ApplicationProfileSupportedTypes
     */
    protected List<org.extensiblecatalog.ncip.v2.service.ApplicationProfileSupportedType>
            applicationProfileSupportedTypes;

    /**
     * Set ApplicationProfileSupportedTypes.
     */
    public void setApplicationProfileSupportedTypes(
            List<org.extensiblecatalog.ncip.v2.service.ApplicationProfileSupportedType> applicationProfileSupportedTypes) {

        this.applicationProfileSupportedTypes = applicationProfileSupportedTypes;

    }

    /**
     * Get ApplicationProfileSupportedTypes.
     */
    public List<ApplicationProfileSupportedType>
    getApplicationProfileSupportedTypes() {

        return applicationProfileSupportedTypes;

    }

    /**
     * ConsortiumAgreements
     */
    protected List<org.extensiblecatalog.ncip.v2.service.ConsortiumAgreement> consortiumAgreements;

    /**
     * Set ConsortiumAgreements.
     */
    public void setConsortiumAgreements(
            List<org.extensiblecatalog.ncip.v2.service.ConsortiumAgreement> consortiumAgreements) {

        this.consortiumAgreements = consortiumAgreements;

    }

    /**
     * Get ConsortiumAgreements.
     */
    public List<ConsortiumAgreement> getConsortiumAgreements() {

        return consortiumAgreements;

    }

    /**
     * AgencyUserPrivilegeTypes
     */
    protected List<org.extensiblecatalog.ncip.v2.service.AgencyUserPrivilegeType> agencyUserPrivilegeTypes;

    /**
     * Set AgencyUserPrivilegeTypes.
     */
    public void setAgencyUserPrivilegeTypes(
            List<org.extensiblecatalog.ncip.v2.service.AgencyUserPrivilegeType> agencyUserPrivilegeTypes) {

        this.agencyUserPrivilegeTypes = agencyUserPrivilegeTypes;

    }

    /**
     * Get AgencyUserPrivilegeTypes.
     */
    public List<AgencyUserPrivilegeType> getAgencyUserPrivilegeTypes() {

        return agencyUserPrivilegeTypes;

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

