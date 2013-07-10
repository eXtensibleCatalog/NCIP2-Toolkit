/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class InitiationHeader {
    /**
     * Application Profile Type
     */
    protected ApplicationProfileType applicationProfileType;
    /**
     * To System Id
     */
    protected ToSystemId toSystemId;

    public ApplicationProfileType getApplicationProfileType() {
        return applicationProfileType;
    }

    public void setApplicationProfileType(ApplicationProfileType applicationProfileType) {
        this.applicationProfileType = applicationProfileType;
    }

    public ToSystemId getToSystemId() {
        return toSystemId;
    }

    public void setToSystemId(ToSystemId toSystemId) {
        this.toSystemId = toSystemId;
    }

    public String getFromSystemAuthentication() {
        return fromSystemAuthentication;
    }

    public void setFromSystemAuthentication(String fromSystemAuthentication) {
        this.fromSystemAuthentication = fromSystemAuthentication;
    }

    public FromSystemId getFromSystemId() {
        return fromSystemId;
    }

    public void setFromSystemId(FromSystemId fromSystemId) {
        this.fromSystemId = fromSystemId;
    }

    public String getFromAgencyAuthentication() {
        return fromAgencyAuthentication;
    }

    public void setFromAgencyAuthentication(String fromAgencyAuthentication) {
        this.fromAgencyAuthentication = fromAgencyAuthentication;
    }

    public OnBehalfOfAgency getOnBehalfOfAgency() {
        return onBehalfOfAgency;
    }

    public void setOnBehalfOfAgency(OnBehalfOfAgency onBehalfOfAgency) {
        this.onBehalfOfAgency = onBehalfOfAgency;
    }

    /**
     * To Agency Id
     */

    protected ToAgencyId toAgencyId;
    /**
     * From System Authentication
     */
    protected String fromSystemAuthentication;
    /**
     * From System Id
     */
    protected FromSystemId fromSystemId;
    /**
     * From Agency Authentication
     */
    protected String fromAgencyAuthentication;
    /**
     * From Agency Id
     */
    protected FromAgencyId fromAgencyId;
    /**
     * On-behalf-of Agency Id
     */
    protected OnBehalfOfAgency onBehalfOfAgency;

    /**
     * Retrieve the to agency id
     *
     * @return the to agency id
     */
    public ToAgencyId getToAgencyId() {
        return toAgencyId;
    }

    /**
     * Set the id of the agency that this data should be sent to
     *
     * @param toAgencyId the "to" agency id
     */
    public void setToAgencyId(ToAgencyId toAgencyId) {
        this.toAgencyId = toAgencyId;
    }

    /**
     * Retrieve the from agency id
     *
     * @return the "from" agency id
     */
    public FromAgencyId getFromAgencyId() {
        return fromAgencyId;
    }

    /**
     * Set the id of the agency that this data is coming from
     *
     * @param fromAgencyId the "from" agency id
     */
    public void setFromAgencyId(FromAgencyId fromAgencyId) {
        this.fromAgencyId = fromAgencyId;
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
