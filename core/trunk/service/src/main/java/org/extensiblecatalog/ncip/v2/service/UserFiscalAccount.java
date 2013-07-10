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
 * Describes a UserFiscalAccount result from an NCIP response
 */
public class UserFiscalAccount {

    /**
     * account balance
     */
    protected AccountBalance accountBalance;
    /**
     * account detail
     */
    protected List<AccountDetails> accountDetails;

    /**
     * Construct a new UserFiscalAccount
     */
    public UserFiscalAccount() {
        // left blank
    }

    /**
     * Construct a new UserFiscalAccount
     *
     * @param accountBalance
     * @param accountDetails
     */
    public UserFiscalAccount(AccountBalance accountBalance, List<AccountDetails> accountDetails) {
        this.accountBalance = accountBalance;
        this.accountDetails = accountDetails;
    }

    /**
     * Set the account balance
     *
     * @param accountBalance the accountBalance to set
     */
    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Retrieve the account balance
     *
     * @return the accountBalance
     */
    public AccountBalance getAccountBalance() {
        return accountBalance;
    }


    public List<AccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public AccountDetails getAccountDetail(int index) {
        return accountDetails.get(index);
    }

    public void setAccountDetails(List<AccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }

    /*
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
