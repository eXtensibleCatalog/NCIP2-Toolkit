/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * A bibliographic record identifier. Examples are Australian National Bibliography Number,
 * OCLC number, or (if the Agency Id attribute is provided instead of the Bibliographic
 * Record Identifier Code), the local system control number.
 */
public class BibliographicRecordId {
    protected String bibliographicRecordIdentifier;

    protected AgencyId agencyId;
    protected BibliographicRecordIdentifierCode bibliographicRecordIdentifierCode;

    public String getBibliographicRecordIdentifier() {
        return bibliographicRecordIdentifier;
    }

    public void setBibliographicRecordIdentifier(String bibliographicRecordIdentifier) {
        this.bibliographicRecordIdentifier = bibliographicRecordIdentifier;
    }

    public AgencyId getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    public BibliographicRecordIdentifierCode getBibliographicRecordIdentifierCode() {
        return bibliographicRecordIdentifierCode;
    }

    public void setBibliographicRecordIdentifierCode(
        BibliographicRecordIdentifierCode bibliographicRecordIdentifierCode) {
        this.bibliographicRecordIdentifierCode = bibliographicRecordIdentifierCode;
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
