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

public class UserOptionalFields {
    /**
     * Name Information
     */
    protected NameInformation nameInformation;
    /**
     * User Address Information
     */
    protected List<UserAddressInformation> userAddressInformations;
    /**
     * DateOfBirth
     */
    protected GregorianCalendar dateOfBirth;
    /**
     * User Language
     */
    protected List<UserLanguage> userLanguages;
    /**
     * User Privilege
     */
    protected List<UserPrivilege> userPrivileges;
    /**
     * Block Or Trap
     */
    protected List<BlockOrTrap> blockOrTraps;
    /**
     * User Id
     */
    protected List<UserId> userIds;
    /**
     * Previous User Id
     */
    protected List<PreviousUserId> previousUserIds;

    public NameInformation getNameInformation() {
        return nameInformation;
    }

    public void setNameInformation(NameInformation nameInformation) {
        this.nameInformation = nameInformation;
    }

    public List<UserAddressInformation> getUserAddressInformations() {
        return userAddressInformations;
    }

    public void setUserAddressInformations(List<UserAddressInformation> userAddressInformations) {
        this.userAddressInformations = userAddressInformations;
    }

    public GregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(GregorianCalendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<UserLanguage> getUserLanguages() {
        return userLanguages;
    }

    public void setUserLanguages(List<UserLanguage> userLanguages) {
        this.userLanguages = userLanguages;
    }

    public List<UserPrivilege> getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(List<UserPrivilege> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public List<BlockOrTrap> getBlockOrTraps() {
        return blockOrTraps;
    }

    public void setBlockOrTraps(List<BlockOrTrap> blockOrTraps) {
        this.blockOrTraps = blockOrTraps;
    }

    public List<UserId> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UserId> userIds) {
        this.userIds = userIds;
    }

    public List<PreviousUserId> getPreviousUserIds() {
        return previousUserIds;
    }

    public void setPreviousUserIds(List<PreviousUserId> previousUserIds) {
        this.previousUserIds = previousUserIds;
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
