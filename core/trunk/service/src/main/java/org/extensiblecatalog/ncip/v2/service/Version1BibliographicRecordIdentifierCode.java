/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

/**
 * Note: This scheme is defined in the NCIP version 1 Implementation Profile 1.
 */
public class Version1BibliographicRecordIdentifierCode extends BibliographicRecordIdentifierCode {

    private static final Logger LOG = Logger.getLogger(Version1BibliographicRecordIdentifierCode.class);

    public static final String VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm";

    public static final Version1BibliographicRecordIdentifierCode ACCESSION_NUMBER
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "Accession Number");
    public static final Version1BibliographicRecordIdentifierCode ANBN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "ANBN");
    public static final Version1BibliographicRecordIdentifierCode BNBN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "BNBN");
    public static final Version1BibliographicRecordIdentifierCode CN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "CN");
    public static final Version1BibliographicRecordIdentifierCode LCCN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "LCCN");
    public static final Version1BibliographicRecordIdentifierCode NLM_TCN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "NLM TCN");
    public static final Version1BibliographicRecordIdentifierCode OCLC
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "OCLC");
    public static final Version1BibliographicRecordIdentifierCode RLIN
            = new Version1BibliographicRecordIdentifierCode(VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, "RLIN");

    public static void loadAll() {
        LOG.debug("Loading Version1BibliographicRecordIdentifierCode.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1BibliographicRecordIdentifierCode(String scheme, String value) {
        super(scheme, value);
    }

}
