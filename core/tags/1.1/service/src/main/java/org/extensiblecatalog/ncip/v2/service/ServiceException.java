/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * ServiceException represents errors during the performance of a service.
 */
public class ServiceException extends Exception {

    /**
     * The type of error
     */
    private final ServiceError error;

    /**
     * Construct a new ServiceException
     *
     * @param error       the Exception error
     * @param explanation the text message
     * @param cause       the original exception
     */
    public ServiceException(ServiceError error, String explanation, Throwable cause) {
        super(explanation, cause);
        this.error = error;
    }

    /**
     * Construct a new ServiceException
     *
     * @param error       the type of error
     * @param explanation the text message
     */
    public ServiceException(ServiceError error, String explanation) {
        super(explanation);
        this.error = error;
    }

    /**
     * Construct a new ServiceException
     *
     * @param error the type of error
     * @param cause the original exception
     */
    public ServiceException(ServiceError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * Retrieves the type of error
     *
     * @return the error
     */
    public ServiceError getError() {
        return error;
    }

    /**
     * Represent the exception as a String (for logging, etc.).
     *
     * @return the String representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Service error: ").append(error).append(". ").append(super.toString());
        return buffer.toString();
    }
}
