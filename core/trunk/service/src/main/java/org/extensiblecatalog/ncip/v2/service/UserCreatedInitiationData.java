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
 * Data to initiate the UserCreated service.
 */
public class UserCreatedInitiationData implements NCIPInitiationData {


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
     * UserId
     */
    protected org.extensiblecatalog.ncip.v2.service.UserId userId;

    /**
     * Set UserId.
     */
    public void setUserId(org.extensiblecatalog.ncip.v2.service.UserId userId) {

        this.userId = userId;

    }

    /**
     * Get UserId.
     */
    public UserId getUserId() {

        return userId;

    }

    /**
     * NameInformation
     */
    protected org.extensiblecatalog.ncip.v2.service.NameInformation nameInformation;

    /**
     * Set NameInformation.
     */
    public void setNameInformation(org.extensiblecatalog.ncip.v2.service.NameInformation nameInformation) {

        this.nameInformation = nameInformation;

    }

    /**
     * Get NameInformation.
     */
    public NameInformation getNameInformation() {

        return nameInformation;

    }

    /**
     * UserAddressInformations
     */
    protected List<org.extensiblecatalog.ncip.v2.service.UserAddressInformation> userAddressInformations;

    /**
     * Set UserAddressInformations.
     */
    public void setUserAddressInformations(
            List<org.extensiblecatalog.ncip.v2.service.UserAddressInformation> userAddressInformations) {

        this.userAddressInformations = userAddressInformations;

    }

    /**
     * Get UserAddressInformations.
     */
    public List<UserAddressInformation> getUserAddressInformations() {

        return userAddressInformations;

    }

    /**
     * DateOfBirth
     */
    protected GregorianCalendar dateOfBirth;

    /**
     * Set DateOfBirth.
     */
    public void setDateOfBirth(GregorianCalendar dateOfBirth) {

        this.dateOfBirth = dateOfBirth;

    }

    /**
     * Get DateOfBirth.
     */
    public GregorianCalendar getDateOfBirth() {

        return dateOfBirth;

    }

    /**
     * UserLanguages
     */
    protected List<org.extensiblecatalog.ncip.v2.service.UserLanguage> userLanguages;

    /**
     * Set UserLanguages.
     */
    public void setUserLanguages(List<org.extensiblecatalog.ncip.v2.service.UserLanguage> userLanguages) {

        this.userLanguages = userLanguages;

    }

    /**
     * Get UserLanguages.
     */
    public List<UserLanguage> getUserLanguages() {

        return userLanguages;

    }

    /**
     * UserPrivileges
     */
    protected List<org.extensiblecatalog.ncip.v2.service.UserPrivilege> userPrivileges;

    /**
     * Set UserPrivileges.
     */
    public void setUserPrivileges(List<org.extensiblecatalog.ncip.v2.service.UserPrivilege> userPrivileges) {

        this.userPrivileges = userPrivileges;

    }

    /**
     * Get UserPrivileges.
     */
    public List<UserPrivilege> getUserPrivileges() {

        return userPrivileges;

    }

    /**
     * BlockOrTraps
     */
    protected List<org.extensiblecatalog.ncip.v2.service.BlockOrTrap> blockOrTraps;

    /**
     * Set BlockOrTraps.
     */
    public void setBlockOrTraps(List<org.extensiblecatalog.ncip.v2.service.BlockOrTrap> blockOrTraps) {

        this.blockOrTraps = blockOrTraps;

    }

    /**
     * Get BlockOrTraps.
     */
    public List<BlockOrTrap> getBlockOrTraps() {

        return blockOrTraps;

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

