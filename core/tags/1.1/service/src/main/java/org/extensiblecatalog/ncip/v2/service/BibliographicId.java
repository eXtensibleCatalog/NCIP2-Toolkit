/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BibliographicId {

    /**
     * Bibliographic Item Id
     */
    protected BibliographicItemId bibliographicItemId;
    /**
     * Bibliographic Record Id
     */
    protected BibliographicRecordId bibliographicRecordId;

    public BibliographicItemId getBibliographicItemId() {
        return bibliographicItemId;
    }

    public void setBibliographicItemId(BibliographicItemId bibliographicItemId) {
        this.bibliographicItemId = bibliographicItemId;
    }

    public BibliographicRecordId getBibliographicRecordId() {
        return bibliographicRecordId;
    }

    public void setBibliographicRecordId(BibliographicRecordId bibliographicRecordId) {
        this.bibliographicRecordId = bibliographicRecordId;
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
