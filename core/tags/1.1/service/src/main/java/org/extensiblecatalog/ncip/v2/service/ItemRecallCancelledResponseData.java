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
 * Carries data elements describing the ItemRecallCancelledResponse.
 */
public class ItemRecallCancelledResponseData implements NCIPResponseData {

//    public MessageType getMessageType() { return NCIPData.MessageType.RESPONSE; }
//    public boolean isInitiationMessage() { return false; }
//    public boolean isResponseMessage() { return true; }

    /** Version attribute */
    protected String version;

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * ResponseHeader
     */
    protected org.extensiblecatalog.ncip.v2.service.ResponseHeader responseHeader;

    /**
     * Set ResponseHeader.
     */
    public void setResponseHeader(org.extensiblecatalog.ncip.v2.service.ResponseHeader responseHeader) {

        this.responseHeader = responseHeader;

    }

    /**
     * Get ResponseHeader.
     */
    public org.extensiblecatalog.ncip.v2.service.ResponseHeader getResponseHeader() {

        return responseHeader;

    }

    /**
     * Problems
     */
    protected List<org.extensiblecatalog.ncip.v2.service.Problem> problems;

    /**
     * Set Problems.
     */
    public void setProblems(List<org.extensiblecatalog.ncip.v2.service.Problem> problems) {

        this.problems = problems;

    }

    /**
     * Get Problems.
     */
    public List<org.extensiblecatalog.ncip.v2.service.Problem> getProblems() {

        return problems;

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

