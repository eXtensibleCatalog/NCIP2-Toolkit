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
 * Carries data elements describing the UserNoticeSent.
 */
public class UserNoticeSentInitiationData implements NCIPInitiationData {

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
    public org.extensiblecatalog.ncip.v2.service.UserId getUserId() {

        return userId;

    }

    /**
     * DateSent
     */
    protected GregorianCalendar dateSent;

    /**
     * Set DateSent.
     */
    public void setDateSent(GregorianCalendar dateSent) {

        this.dateSent = dateSent;

    }

    /**
     * Get DateSent.
     */
    public GregorianCalendar getDateSent() {

        return dateSent;

    }

    /**
     * UserNoticeDetails
     */
    protected org.extensiblecatalog.ncip.v2.service.UserNoticeDetails userNoticeDetails;

    /**
     * Set UserNoticeDetails.
     */
    public void setUserNoticeDetails(org.extensiblecatalog.ncip.v2.service.UserNoticeDetails userNoticeDetails) {

        this.userNoticeDetails = userNoticeDetails;

    }

    /**
     * Get UserNoticeDetails.
     */
    public org.extensiblecatalog.ncip.v2.service.UserNoticeDetails getUserNoticeDetails() {

        return userNoticeDetails;

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

