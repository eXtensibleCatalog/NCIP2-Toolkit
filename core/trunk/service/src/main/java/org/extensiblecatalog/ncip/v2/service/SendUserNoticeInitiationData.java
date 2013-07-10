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
 * Data to initiate the SendUserNotice service.
 */
public class SendUserNoticeInitiationData implements NCIPInitiationData {


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
     * DateToSend
     */
    protected GregorianCalendar dateToSend;

    /**
     * Set DateToSend.
     */
    public void setDateToSend(GregorianCalendar dateToSend) {

        this.dateToSend = dateToSend;

    }

    /**
     * Get DateToSend.
     */
    public GregorianCalendar getDateToSend() {

        return dateToSend;

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
    public UserNoticeDetails getUserNoticeDetails() {

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

