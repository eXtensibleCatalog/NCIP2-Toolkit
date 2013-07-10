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
 * Data to initiate the LookupUser service.
 */
public class LookupUserInitiationData implements NCIPInitiationData {


    /**
     * Version attribute
     */
    protected String version;

    /**
     * Get the version.
     * @return the version
     */
    @Deprecated
    public String getVersion() {
        return version;
    }

    /**
     * Set the version.
     * @param version the version
     */
    @Deprecated
    public void setVersion(String version) {
        this.version = version;
    }

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


    /**
     * The list of {@link ResponseElementControl}s
     */
    protected List<ResponseElementControl> responseElementControls;

    /**
     * Get the list of {@link ResponseElementControl}s
     * @return the list of {@link ResponseElementControl}s
     */
    public List<ResponseElementControl> getResponseElementControls() {
        return responseElementControls;
    }

    /**
     * Get the {@link ResponseElementControl} at the provided index
     * @param index the index
     * @return the {@link ResponseElementControl}
     */
    public ResponseElementControl getResponseElementControl(int index) {
        return responseElementControls.get(index);
    }

    /**
     * Set the response element controls
     * @param responseElementControls the list of {@link ResponseElementControl}s
     */
    public void setResponseElementControls(List<ResponseElementControl> responseElementControls) {
        this.responseElementControls = responseElementControls;
    }

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
     * @param initiationHeader the {@link InitiationHeader}
     */
    public void setInitiationHeader(InitiationHeader initiationHeader) {
        this.initiationHeader = initiationHeader;
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
     * Get the authentication inputs
     * @return list of {@link AuthenticationInput}s
     */
    public List<AuthenticationInput> getAuthenticationInputs() {
        return authenticationInputs;
    }

    /**
     * Get the authentication input at the provided index
     * @param index the index
     * @return the {@link AuthenticationInput}
     */
    public AuthenticationInput getAuthenticationInput(int index) {
        return authenticationInputs.get(index);
    }

    /**
     * Set the list of {@link AuthenticationInput}s
     * @param authenticationInputs the list of {@link AuthenticationInput}s
     */
    public void setAuthenticationInputs(List<AuthenticationInput> authenticationInputs) {
        this.authenticationInputs = authenticationInputs;
    }

    /**
     * Get the user id
     * @return the {@link UserId}
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * Set the user id
     * @param userId the {@link UserId}
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * Get whether loaned items are desired in the response
     * @return whether the {@link LoanedItem}s are desired
     */
    public boolean getLoanedItemsDesired() {
        return loanedItemsDesired;
    }

    /**
     * Set whether loaned items are desired in the response
     * @param loanedItemsDesired whether {@link LoanedItem}s are desired in the response
     */
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

    /**
     * Whether the authenticationInput is desired in the response.
     */
    protected boolean authenticationInputDesired;
    /**
     * Whether the blockOrTrap is desired in the response.
     */
    protected boolean blockOrTrapDesired;
    /**
     * Whether the dateOfBirth is desired in the response.
     */
    protected boolean dateOfBirthDesired;
    /**
     * Whether the nameInformation is desired in the response.
     */
    protected boolean nameInformationDesired;
    /**
     * Whether the userAddressInformation is desired in the response.
     */
    protected boolean userAddressInformationDesired;
    /**
     * Whether the userLanguage is desired in the response.
     */
    protected boolean userLanguageDesired;
    /**
     * Whether the userPrivilege is desired in the response.
     */
    protected boolean userPrivilegeDesired;
    /**
     * Whether the userId is desired in the response.
     */
    protected boolean userIdDesired;
    /**
     * Whether the previousUserId is desired in the response.
     */
    protected boolean previousUserIdDesired;

    /**
     * Get the AuthenticationInput is desired in the response.
     *
     * @return the AuthenticationInputDesired
     */
    public boolean getAuthenticationInputDesired() {
        return authenticationInputDesired;
    }

    /**
     * Set whether the AuthenticationInput is desired in the response.
     *
     * @param authenticationInputDesired the AuthenticationInputDesired
     */
    public void setAuthenticationInputDesired(boolean authenticationInputDesired) {
        this.authenticationInputDesired = authenticationInputDesired;
    }

    /**
     * Get the BlockOrTrap is desired in the response.
     *
     * @return the BlockOrTrapDesired
     */
    public boolean getBlockOrTrapDesired() {
        return blockOrTrapDesired;
    }

    /**
     * Set whether the BlockOrTrap is desired in the response.
     *
     * @param blockOrTrapDesired the BlockOrTrapDesired
     */
    public void setBlockOrTrapDesired(boolean blockOrTrapDesired) {
        this.blockOrTrapDesired = blockOrTrapDesired;
    }

    /**
     * Get the DateOfBirth is desired in the response.
     *
     * @return the DateOfBirthDesired
     */
    public boolean getDateOfBirthDesired() {
        return dateOfBirthDesired;
    }

    /**
     * Set whether the DateOfBirth is desired in the response.
     *
     * @param dateOfBirthDesired the DateOfBirthDesired
     */
    public void setDateOfBirthDesired(boolean dateOfBirthDesired) {
        this.dateOfBirthDesired = dateOfBirthDesired;
    }

    /**
     * Get the NameInformation is desired in the response.
     *
     * @return the NameInformationDesired
     */
    public boolean getNameInformationDesired() {
        return nameInformationDesired;
    }

    /**
     * Set whether the NameInformation is desired in the response.
     *
     * @param nameInformationDesired the NameInformationDesired
     */
    public void setNameInformationDesired(boolean nameInformationDesired) {
        this.nameInformationDesired = nameInformationDesired;
    }

    /**
     * Get the UserAddressInformation is desired in the response.
     *
     * @return the UserAddressInformationDesired
     */
    public boolean getUserAddressInformationDesired() {
        return userAddressInformationDesired;
    }

    /**
     * Set whether the UserAddressInformation is desired in the response.
     *
     * @param userAddressInformationDesired the UserAddressInformationDesired
     */
    public void setUserAddressInformationDesired(
            boolean userAddressInformationDesired) {
        this.userAddressInformationDesired = userAddressInformationDesired;
    }

    /**
     * Get the UserLanguage is desired in the response.
     *
     * @return the UserLanguageDesired
     */
    public boolean getUserLanguageDesired() {
        return userLanguageDesired;
    }

    /**
     * Set whether the UserLanguage is desired in the response.
     *
     * @param userLanguageDesired the UserLanguageDesired
     */
    public void setUserLanguageDesired(boolean userLanguageDesired) {
        this.userLanguageDesired = userLanguageDesired;
    }

    /**
     * Get the UserPrivilege is desired in the response.
     *
     * @return the UserPrivilegeDesired
     */
    public boolean getUserPrivilegeDesired() {
        return userPrivilegeDesired;
    }

    /**
     * Set whether the UserPrivilege is desired in the response.
     *
     * @param userPrivilegeDesired the UserPrivilegeDesired
     */
    public void setUserPrivilegeDesired(boolean userPrivilegeDesired) {
        this.userPrivilegeDesired = userPrivilegeDesired;
    }

    /**
     * Get the UserId is desired in the response.
     *
     * @return the UserIdDesired
     */
    public boolean getUserIdDesired() {
        return userIdDesired;
    }

    /**
     * Set whether the UserId is desired in the response.
     *
     * @param userIdDesired the UserIdDesired
     */
    public void setUserIdDesired(boolean userIdDesired) {
        this.userIdDesired = userIdDesired;
    }

    /**
     * Get the PreviousUserId is desired in the response.
     *
     * @return the PreviousUserIdDesired
     */
    public boolean getPreviousUserIdDesired() {
        return previousUserIdDesired;
    }

    /**
     * Set whether the PreviousUserId is desired in the response.
     *
     * @param previousUserIdDesired the PreviousUserIdDesired
     */
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
