/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.List;
import java.util.Map;

public interface NCIP2TranslatorConfiguration extends TranslatorConfiguration {

    final String SCHEMA_URLS_TO_PACKAGE_MAP_KEY = "NCIP2TranslatorConfiguration.SchemaURLsToPackageMap";
    final String SCHEMA_URLS_TO_PACKAGE_MAP_DEFAULT
        =                   "ncip_v2_01.xsd=org.extensiblecatalog.ncip.v2.binding.ncipv2_01.jaxb.elements,"
        +                   "ncip_v2_02.xsd=org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.elements,"
        + "ncip_v2_01_ils-di_extensions.xsd=org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0.jaxb.elements";

    // TODO: Implement the mapping of other URLs for the Schemas to the ones internal to this package.
    // A 'canonical' Schema URL is the URL used within the Toolkit, *not* the 'correct' one per the NCIP
    // standard. The purpose of this map is to accept a variety of URLs from external partners
    // and map them to the *resource URL* used by the Toolkit to load them at run-time. Normally the
    // canonical URLs will be resources within the classpath, but that is not *required*.
    final String CANONICAL_SCHEMA_URL_MAP_KEY = "NCIP2TranslatorConfiguration.CanonicalSchemaURLMap";
    final String CANONICAL_SCHEMA_URL_MAP_DEFAULT = null;

    Map<String, String> getSchemaURLsToPackageMap();
    void setSchemaURLsToPackageMap(Map<String, String> schemaURLsToPackageMap);
    Map<String, String> getCanonicalSchemaURLMap();
    void setCanonicalSchemaURLMap(Map<String, String> aliasToCanonicalSchemaURLMap);

}
