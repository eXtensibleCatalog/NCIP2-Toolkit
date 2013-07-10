/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface NCIPProtocolVersionConfiguration extends ProtocolVersionConfiguration {

    final String PROTOCOL_VERSION_VERSION_ATTRIBUTE_KEY = "NCIPProtocolVersionConfiguration.VersionAttribute";
    final String PROTOCOL_VERSION_VERSION_ATTRIBUTE_DEFAULT = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd";
    final String PROTOCOL_VERSION_CANONICAL_VERSION_ATTRIBUTE_KEY = "NCIPProtocolVersionConfiguration.CanonicalVersionAttribute";
    final String PROTOCOL_VERSION_CANONICAL_VERSION_ATTRIBUTE_DEFAULT = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd";

    // TODO: Review whether these should be moved from ServiceValidator to here
/*
    final String NAMESPACE_URIS_KEY = "NCIPProtocolVersionConfiguration.NamespaceURIs";
    final String NAMESPACE_URIS_DEFAULT = "http://www.niso.org/2008/ncip";

    final String DEFAULT_NAMESPACE_URI_KEY = "NCIPProtocolVersionConfiguration.DefaultNamespaceURI";
    final String DEFAULT_NAMESPACE_URI_DEFAULT = "http://www.niso.org/2008/ncip";

    final String SUPPORTED_SCHEMA_URLS_KEY = "NCIPProtocolVersionConfiguration.SupportedSchemaURLs";
    // To restrict the supported data elements and services to those defined in the NCIP standard, version 2.0,
    // use "ncip_v2_0.xsd".
    // To use version 2.0 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_0_ils-di_extensions.xsd".
    // To use version 2.01 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_01_ils-di_extensions.xsd"
    // TODO: Test whether, in fact, order does matter in the supported schemas
    // Note: Order matters - if a schema B references elements in schema A, then the order must be "A,B".
    final String SUPPORTED_SCHEMA_URLS_DEFAULT = "ncip_v2_01.xsd,ncip_v2_01_ils-di_extensions.xsd";
*/

    String getVersionAttribute();
    DefaultNCIPVersion getCanonicalVersion();

}
