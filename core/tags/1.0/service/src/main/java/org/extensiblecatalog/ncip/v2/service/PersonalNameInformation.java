/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PersonalNameInformation {
    /**
     * Unstructured Personal User Name
     */
    protected String unstructuredPersonalUserName;
    /**
     * Structured Personal User Name
     */
    protected StructuredPersonalUserName structuredPersonalUserName;

    public String getUnstructuredPersonalUserName() {
        return unstructuredPersonalUserName;
    }

    public void setUnstructuredPersonalUserName(String unstructuredPersonalUserName) {
        this.unstructuredPersonalUserName = unstructuredPersonalUserName;
    }

    public StructuredPersonalUserName getStructuredPersonalUserName() {
        return structuredPersonalUserName;
    }

    public void setStructuredPersonalUserName(StructuredPersonalUserName structuredPersonalUserName) {
        this.structuredPersonalUserName = structuredPersonalUserName;
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
