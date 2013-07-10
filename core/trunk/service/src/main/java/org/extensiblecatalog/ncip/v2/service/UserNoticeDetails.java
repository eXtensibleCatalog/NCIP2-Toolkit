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
 * Carries data elements describing the UserNoticeDetails.
 */
public class UserNoticeDetails {

    /**
     * NoticeType
     */
    protected org.extensiblecatalog.ncip.v2.service.NoticeType noticeType;

    /**
     * Set NoticeType.
     */
    public void setNoticeType(org.extensiblecatalog.ncip.v2.service.NoticeType noticeType) {

        this.noticeType = noticeType;

    }

    /**
     * Get NoticeType.
     */
    public org.extensiblecatalog.ncip.v2.service.NoticeType getNoticeType() {

        return noticeType;

    }

    /**
     * NoticeContent
     */
    protected String noticeContent;

    /**
     * Set NoticeContent.
     */
    public void setNoticeContent(String noticeContent) {

        this.noticeContent = noticeContent;

    }

    /**
     * Get NoticeContent.
     */
    public String getNoticeContent() {

        return noticeContent;

    }

    /**
     * NoticeItems
     */
    protected List<org.extensiblecatalog.ncip.v2.service.NoticeItem> noticeItems;

    /**
     * Set NoticeItems.
     */
    public void setNoticeItems(List<org.extensiblecatalog.ncip.v2.service.NoticeItem> noticeItems) {

        this.noticeItems = noticeItems;

    }

    /**
     * Get NoticeItems.
     */
    public List<org.extensiblecatalog.ncip.v2.service.NoticeItem> getNoticeItems() {

        return noticeItems;

    }

    /**
     * UserFiscalAccount
     */
    protected org.extensiblecatalog.ncip.v2.service.UserFiscalAccount userFiscalAccount;

    /**
     * Set UserFiscalAccount.
     */
    public void setUserFiscalAccount(org.extensiblecatalog.ncip.v2.service.UserFiscalAccount userFiscalAccount) {

        this.userFiscalAccount = userFiscalAccount;

    }

    /**
     * Get UserFiscalAccount.
     */
    public org.extensiblecatalog.ncip.v2.service.UserFiscalAccount getUserFiscalAccount() {

        return userFiscalAccount;

    }

    /**
     * UserPrivilege
     */
    protected org.extensiblecatalog.ncip.v2.service.UserPrivilege userPrivilege;

    /**
     * Set UserPrivilege.
     */
    public void setUserPrivilege(org.extensiblecatalog.ncip.v2.service.UserPrivilege userPrivilege) {

        this.userPrivilege = userPrivilege;

    }

    /**
     * Get UserPrivilege.
     */
    public org.extensiblecatalog.ncip.v2.service.UserPrivilege getUserPrivilege() {

        return userPrivilege;

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

