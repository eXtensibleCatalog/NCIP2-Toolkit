/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Request Id
 */
public final class RequestId {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 4389080391314069789L;

    protected String requestIdentifierValue;
    protected AgencyId agencyId;
    protected RequestIdentifierType requestIdentifierType;

    /**
     * Construct a new Request Id.
     */
    public RequestId() {
    }

    /**
     * Set the request id's value
     *
     * @param requestIdentifierValue the identifier of the request
     */
    public void setRequestIdentifierValue(String requestIdentifierValue) {
        this.requestIdentifierValue = requestIdentifierValue;
    }

    /**
     * Returns the request identifier.
     *
     * @return the identifier of the request
     */
    public String getRequestIdentifierValue() {
        return requestIdentifierValue;
    }

    public void setRequestIdentifierType(RequestIdentifierType requestIdentifierType) {
        this.requestIdentifierType = requestIdentifierType;
    }

    /**
     * Returns the identifier's type (e.g. hold #, external request identifier, etc.)
     *
     * @return the identifier's type
     */
    public RequestIdentifierType getRequestIdentifierType() {
        return requestIdentifierType;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * Returns the id of the agency associated with this request.
     *
     * @return the agency id for the agency associated with this request
     */
    public AgencyId getAgencyId() {
        return agencyId;
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
