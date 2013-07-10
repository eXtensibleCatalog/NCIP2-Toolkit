/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

public class ToolkitException extends Exception {

    /**
     * Construct a new ToolkitException
     *
     * @param explanation the text message
     * @param cause       the original exception
     */
    public ToolkitException(String explanation, Throwable cause) {
        super(explanation, cause);
    }

    /**
     * Construct a new ToolkitException
     *
     * @param explanation the text message
     */
    public ToolkitException(String explanation) {
        super(explanation);
    }

    /**
     * Construct a new ToolkitException
     *
     * @param cause the original exception
     */
    public ToolkitException(Throwable cause) {
        super(cause);
    }


}
