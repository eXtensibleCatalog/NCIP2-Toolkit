/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding;

/**
 * BindingException represents errors that arise during binding, or marshalling data from Java objects
 * or unmarshalling data to Java objects.
 */
public class BindingException extends Exception {

    /**
     * The type of error
     */
    private final BindingError error;

    /**
     * Construct a new BindingException
     *
     * @param error       the type of error
     * @param explanation the text message
     * @param cause       the original exception
     */
    public BindingException(BindingError error, String explanation, Throwable cause) {
        super(explanation, cause);
        this.error = error;
    }

    /**
     * Construct a new BindingException
     *
     * @param error       the type of error
     * @param explanation the text message
     */
    public BindingException(BindingError error, String explanation) {
        super(explanation);
        this.error = error;
    }

    /**
     * Construct a new BindingException
     *
     * @param error the type of error
     * @param cause the original exception
     */
    public BindingException(BindingError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * Retrieves the type of error
     *
     * @return the BindingError
     */
    public BindingError getError() {
        return error;
    }

    /**
     * Represent the exception as a String (for logging, etc.).
     *
     * @return the String representation of this object
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Binding error: ").append(error).append(". ").append(super.toString());
        return buffer.toString();
    }
}
