/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * Describes a problem result from an NCIP response
 */
public class Problem {


    /**
     * Text describing the type of error
     */
    protected ProblemType problemType;
    /**
     * Detailed error info
     */
    protected String problemDetail;
    /**
     * Processing element where problem occurred
     */
    protected String problemElement;
    /**
     * Error description
     */
    protected String problemValue;

    /**
     * Construct a new Problem
     */
    public Problem() {
        // left blank
    }

    /**
     * Construct a new Problem
     *
     * @param problemType    the text message defining the type error
     * @param problemElement Data/Processing element in which error occurred
     * @param problemValue   the error description
     * @param problemDetail  detailed error information
     */
    public Problem(ProblemType problemType, String problemElement, String problemValue, String problemDetail) {
        this.problemType = problemType;
        this.problemDetail = problemDetail;
        this.problemElement = problemElement;
        this.problemValue = problemValue;
    }

    /**
     * Construct a new Problem
     *
     * @param problemType    the text message defining the type error
     * @param problemElement Data/Processing element in which error occurred
     * @param problemValue   the error description
     */
    public Problem(ProblemType problemType, String problemElement, String problemValue) {
        this(problemType, problemElement, problemValue, null);
    }

    /**
     * Set the type of problem
     *
     * @param problemType the problemType to set
     */
    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    /**
     * Retrieve the type of problem
     *
     * @return the problemType
     */
    public ProblemType getProblemType() {
        return problemType;
    }

    /**
     * Set detail describing the problem
     *
     * @param problemDetail the problemDetail to set
     */
    public void setProblemDetail(String problemDetail) {
        this.problemDetail = problemDetail;
    }

    /**
     * Retrieve detail describing the problem
     *
     * @return the problemDetail
     */
    public String getProblemDetail() {
        return problemDetail;
    }

    /**
     * Set the element where the problem occurred
     *
     * @param problemElement the problemElement to set
     */
    public void setProblemElement(String problemElement) {
        this.problemElement = problemElement;
    }

    /**
     * Retrieve the element where the problem occurred
     *
     * @return the problemElement
     */
    public String getProblemElement() {
        return problemElement;
    }

    /**
     * Save the text of the problem
     *
     * @param problemValue the problemValue to set
     */
    public void setProblemValue(String problemValue) {
        this.problemValue = problemValue;
    }

    /**
     * Retrieve the text of the problem
     *
     * @return the problemValue
     */
    public String getProblemValue() {
        return problemValue;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (problemType != null && problemType.getScheme() != null) {
            buffer.append(problemType.getScheme()).append(":");
        }
        if (problemType != null ) {
            buffer.append(problemType.getValue()).append(".");
        }
        if (problemType != null && ( problemType.getScheme() != null || problemType.getValue() != null)) {
            buffer.append(" ");
        }

        if (problemElement != null) {
            buffer.append("In ").append(problemElement).append(".");
        }

        if (problemValue != null) {
            buffer.append("Contents '").append(problemValue).append("'.");
        }

        if (problemDetail != null) {
            buffer.append("(Details: ").append(problemDetail).append(")");
        }
        return buffer.toString();
    }
}
