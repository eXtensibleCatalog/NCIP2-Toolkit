/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class CitationSource {

    /**
     * Citation Source Value
     */
    protected String citationSourceValue;
    /**
     * Citation Source Type
     */
    protected CitationSourceType citationSourceType;

    public String getCitationSourceValue() {
        return citationSourceValue;
    }

    public void setCitationSourceValue(String citationSourceValue) {
        this.citationSourceValue = citationSourceValue;
    }

    public CitationSourceType getCitationSourceType() {
        return citationSourceType;
    }

    public void setCitationSourceType(CitationSourceType citationSourceType) {
        this.citationSourceType = citationSourceType;
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
