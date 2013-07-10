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
 * This class represents the contents of an NCIP response message that indicates one or more {@link Problem}s.
 */
public class ProblemResponseData implements NCIPResponseData {

//    public MessageType getMessageType() { return NCIPData.MessageType.RESPONSE; }
//    public boolean isInitiationMessage() { return false; }
//    public boolean isResponseMessage() { return true; }

    /**
     * Version attribute
     */
    protected String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * The list of {@link Problem} elements.
     */
    protected List<Problem> problems;

    /**
     * Set the list of {@link Problem}s for this response message.
     *
     * @param problems the list of Problems
     */
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    /**
     * Get the list of {@link Problem}s in this response message.
     *
     * @return the list of Problems.
     */
    public List<Problem> getProblems() {
        return problems;
    }

    public Problem getProblem(int index) {
        return problems.get(index);
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
