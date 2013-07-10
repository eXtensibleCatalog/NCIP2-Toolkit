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
 * A bibliographic item idenifier. Examples are an ISBN, Government Document number, Legal Deposit Number, etc.
 */
public class BibliographicItemId {
    protected String bibliographicItemIdentifier;

    protected BibliographicItemIdentifierCode bibliographicItemIdentifierCode;

    public String getBibliographicItemIdentifier() {
        return bibliographicItemIdentifier;
    }

    public void setBibliographicItemIdentifier(String bibliographicItemIdentifier) {
        this.bibliographicItemIdentifier = bibliographicItemIdentifier;
    }

    public BibliographicItemIdentifierCode getBibliographicItemIdentifierCode() {
        return bibliographicItemIdentifierCode;
    }

    public void setBibliographicItemIdentifierCode(BibliographicItemIdentifierCode bibliographicItemIdentifierCode) {
        this.bibliographicItemIdentifierCode = bibliographicItemIdentifierCode;
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
