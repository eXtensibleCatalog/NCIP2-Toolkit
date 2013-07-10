/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RoutingInformation {

    /**
     * Routing Instructions
     */
    protected String routingInstructions;
    /**
     * Destination
     */
    protected Destination destination;

    public String getRoutingInstructions() {
        return routingInstructions;
    }

    public void setRoutingInstructions(String routingInstructions) {
        this.routingInstructions = routingInstructions;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public NameInformation getNameInformation() {
        return nameInformation;
    }

    public void setNameInformation(NameInformation nameInformation) {
        this.nameInformation = nameInformation;
    }

    /**
     * Request Type
     */
    protected RequestType requestType;
    /**
     * User Id
     */
    protected UserId userId;
    /**
     * Name Information
     */
    protected NameInformation nameInformation;

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
