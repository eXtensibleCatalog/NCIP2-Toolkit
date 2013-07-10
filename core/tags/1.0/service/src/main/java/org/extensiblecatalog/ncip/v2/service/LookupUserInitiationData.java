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


/**
 * LookupUserInitiationData contains the data required to build an NCIP LookupUser message.
 */
public class LookupUserInitiationData implements NCIPInitiationData {
    /**
     * Initiation Header
     */
    protected InitiationHeader initiationHeader;
    /**
     * Authentication input
     */
    protected List<AuthenticationInput> authenticationInputs;
    /**
     * UserID
     */
    protected UserId userId;
    /**
     * Flag indicating whether the loaned item's information is desired.
     */
    protected boolean loanedItemsDesired;
    /**
     * Flag indicating whether the Requested Items' information is desired.
     */
    protected boolean requestedItemsDesired;
    /**
     * Flag indicating whether the User Fiscal Account' information is desired.
     */
    protected boolean userFiscalAccountDesired;

    /*
     All of the User Element Types
     */
    protected boolean authenticationInputDesired;
    protected boolean blockOrTrapDesired;
    protected boolean dateOfBirthDesired;
    protected boolean nameInformationDesired;
    protected boolean userAddressInformationDesired;
    protected boolean userLanguageDesired;
    protected boolean userPrivilegeDesired;
    protected boolean userIdDesired;
    protected boolean previousUserIdDesired;
    /**
     * Retrieve the initiation header.
     *
     * @return the initiation header
     */
    public InitiationHeader getInitiationHeader() {
        return initiationHeader;
    }

    /**
     * Set the initiation header
     *
     * @param initiationHeader
     */
    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
    }

    public List<AuthenticationInput> getAuthenticationInputs() {
        return authenticationInputs;
    }

    public void setAuthenticationInputs(List<AuthenticationInput> authenticationInputs) {
        this.authenticationInputs = authenticationInputs;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public boolean getLoanedItemsDesired() {
        return loanedItemsDesired;
    }

    public void setLoanedItemsDesired(boolean loanedItemsDesired) {
        this.loanedItemsDesired = loanedItemsDesired;
    }

    public boolean getRequestedItemsDesired() {
        return requestedItemsDesired;
    }

    public void setRequestedItemsDesired(boolean requestedItemsDesired) {
        this.requestedItemsDesired = requestedItemsDesired;
    }

    public boolean getUserFiscalAccountDesired() {
        return userFiscalAccountDesired;
    }

    public void setUserFiscalAccountDesired(boolean userFiscalAccountDesired) {
        this.userFiscalAccountDesired = userFiscalAccountDesired;
    }

    //the user element types setter and getter
    public boolean getAuthenticationInputDesired() {
        return authenticationInputDesired;
    }
    public void setAuthenticationInputDesired(boolean authenticationInputDesired) {
        this.authenticationInputDesired = authenticationInputDesired;
    }
    public boolean getBlockOrTrapDesired() {
        return blockOrTrapDesired;
    }
    public void setBlockOrTrapDesired(boolean blockOrTrapDesired) {
        this.blockOrTrapDesired = blockOrTrapDesired;
    }
    public boolean getDateOfBirthDesired() {
        return dateOfBirthDesired;
    }
    public void setDateOfBirthDesired(boolean dateOfBirthDesired) {
        this.dateOfBirthDesired = dateOfBirthDesired;
    }
    public boolean getNameInformationDesired() {
        return nameInformationDesired;
    }
    public void setNameInformationDesired(boolean nameInformationDesired) {
        this.nameInformationDesired = nameInformationDesired;
    }
    public boolean getUserAddressInformationDesired() {
        return userAddressInformationDesired;
    }
    public void setUserAddressInformationDesired(
            boolean userAddressInformationDesired) {
        this.userAddressInformationDesired = userAddressInformationDesired;
    }
    public boolean getUserLanguageDesired() {
        return userLanguageDesired;
    }
    public void setUserLanguageDesired(boolean userLanguageDesired) {
        this.userLanguageDesired = userLanguageDesired;
    }
    public boolean getUserPrivilegeDesired() {
        return userPrivilegeDesired;
    }
    public void setUserPrivilegeDesired(boolean userPrivilegeDesired) {
        this.userPrivilegeDesired = userPrivilegeDesired;
    }
    public boolean getUserIdDesired() {
        return userIdDesired;
    }
    public void setUserIdDesired(boolean userIdDesired) {
        this.userIdDesired = userIdDesired;
    }
    public boolean getPreviousUserIdDesired() {
        return previousUserIdDesired;
    }
    public void setPreviousUserIdDesired(boolean previousUserIdDesired) {
        this.previousUserIdDesired = previousUserIdDesired;
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
