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
 * LookupItemResponseData contains the data that is in a NCIP Lookup Item Response message.
 */
public class LookupItemSetResponseData implements NCIPResponseData {

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
     * Response Header
     */
    protected ResponseHeader responseHeader;
    /**
     * Problems
     */
    protected List<Problem> problems;
    /**
     * Bib Information
     */
    protected List<BibInformation> bibInformations;

    /**
     * Next Item Token
     */
    protected String nextItemToken;

    /**
     * Retrieve the response header.
     *
     * @return the response header
     */
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    /**
     * Set the response header
     *
     * @param responseHeader
     */
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     * Retrieve the list of {@Problem}s.
     *
     * @return the list of problems
     */
    public List<Problem> getProblems() {
        return problems;
    }

    public Problem getProblem(int index) {
        return problems.get(index);
    }

    /**
     * Set the list of {@link org.extensiblecatalog.ncip.v2.service.Problem}s.
     *
     * @param problems
     */
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public List<BibInformation> getBibInformations() {
        return bibInformations;
    }

    public BibInformation getBibInformation(int index) {
        return bibInformations.get(index);
    }

    public void setBibInformations(List<BibInformation> bibInformations) {
        this.bibInformations = bibInformations;
    }

    public String getNextItemToken() {
        return nextItemToken;
    }

    public void setNextItemToken(String nextItemToken) {
        this.nextItemToken = nextItemToken;
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

