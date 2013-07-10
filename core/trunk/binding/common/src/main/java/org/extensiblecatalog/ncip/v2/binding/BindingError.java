/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding;

/**
 * Enumerates the reasons for failure within the binding  API.
 */
public enum BindingError {

    /**
     * The message was invalid
     */
    INVALID_MESSAGE_FORMAT,

    /**
     * Runtime error
     */
    RUNTIME_ERROR,
    /**
     * Invalid Scheme/Value combination 
     */
    INVALID_SCHEME_VALUE
}
