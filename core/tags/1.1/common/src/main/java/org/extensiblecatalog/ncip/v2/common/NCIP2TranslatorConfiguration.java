/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.List;

public interface NCIP2TranslatorConfiguration extends TranslatorConfiguration {

    final String SCHEMA_URLS_KEY = "NCIP2TranslatorConfiguration.SchemaURLs";

    // To restrict the supported data elements and services to those defined in the NCIP standard, version 2.0,
    // use "ncip_v2_0.xsd".
    // To use version 2.0 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_0_ils-di_extensions.xsd".
    // To use version 2.01 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_01_ils-di_extensions.xsd"
    
    final String SCHEMA_URLS_DEFAULT = "ncip_v2_01_ils-di_extensions.xsd";

    List<String> getSchemaURLs();
    void setSchemaURLs(List<String> urls);

}
