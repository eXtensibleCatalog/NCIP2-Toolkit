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
 * Contains a Current Borrower, and/or one or more Current Requesters.
 */
public class ItemTransaction {
    protected CurrentBorrower currentBorrower;

    protected List<CurrentRequester> currentRequesters;

    public CurrentBorrower getCurrentBorrower() {
        return currentBorrower;
    }

    public void setCurrentBorrower(CurrentBorrower currentBorrower) {
        this.currentBorrower = currentBorrower;
    }

    public List<CurrentRequester> getCurrentRequesters() {
        return currentRequesters;
    }

    public CurrentRequester getCurrentRequester(int index) {
        return currentRequesters.get(index);
    }

    public void setCurrentRequesters(List<CurrentRequester> currentRequesters) {
        this.currentRequesters = currentRequesters;
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
