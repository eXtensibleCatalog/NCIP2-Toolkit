/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * ExtraData contains a value to add to the NCIP message that is not defined as
 * part of the protocol.
 */
public class ExtraData {

    /**
     * Element Name
     */
    private final String elementName;
    /**
     * Element Value
     */
    private final String elementValue;

    /**
     * Construct a new ExtraData
     *
     * @param elementName  The name of the element in the ncip request
     * @param elementValue The element value
     */
    public ExtraData(String elementName, String elementValue) {
        this.elementName = elementName;
        this.elementValue = elementValue;
    }

    /**
     * Retrieve the element name
     *
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Retrieve the element value
     *
     * @return the elementValue
     */
    public String getElementValue() {
        return elementValue;
    }
}
