/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.util.List;

public class ValidationException extends Exception {

    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    protected List<Problem> problemsList;

    /**
     * Construct a new ValidationException with the provided list of {@link Problem}s.
     * @param problemsList
     */
    public ValidationException(List<Problem> problemsList) {
        super("Invalid message.");
        this.problemsList = problemsList;
    }

    public List<Problem> getProblems() {

        return problemsList;

    }
    
    /**
     * Format the {@link Problem} elements as an exception message.
     * @return the exception message
     */
    public String getMessage() {

        String detailMessage = "";

        if ( problemsList != null ) {

            StringBuilder sb = new StringBuilder();
            for ( Problem p : problemsList ) {

                // TODO: Do a better job of formatting the Problem elements here
                sb.append(p.toString()).append(LINE_SEPARATOR);
            }

            detailMessage = sb.toString();

        }

        return detailMessage;

    }

}
