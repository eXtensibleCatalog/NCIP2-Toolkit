/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;

import java.util.List;
import java.util.Map;

public interface NCIPServiceValidatorConfiguration extends ServiceValidatorConfiguration {

    final String PROTOCOL_NAME_KEY = "NCIPServiceValidatorConfiguration.Protocol";
    final String PROTOCOL_NAME_DEFAULT = "NCIP";

    final String PROTOCOL_PROFILE_KEY = "NCIPServiceValidatorConfiguration.ProtocolProfile";
    final String PROTOCOL_PROFILE_DEFAULT = null;

    final String NAMESPACE_URIS_KEY = "NCIPServiceValidatorConfiguration.NamespaceURIs";
    final String NAMESPACE_URIS_DEFAULT = "http://www.niso.org/2008/ncip";

    final String DEFAULT_NAMESPACE_URI_KEY = "NCIPServiceValidatorConfiguration.DefaultNamespaceURI";
    final String DEFAULT_NAMESPACE_URI_DEFAULT = "http://www.niso.org/2008/ncip";

    // The XML Schema URLs are the locations of the XSD files as resources in the classpath or on the network.
    // These should be the 'canonical' URLs, and NCIP2TranslatorConfiguration.CANONICAL_SCHEMA_URL_MAP_DEFAULT
    // should map non-canonical URLs to these. See there for more detail.
    final String SUPPORTED_SCHEMA_URLS_KEY = "NCIPServiceValidatorConfiguration.SupportedSchemaURLs";
    // To restrict the supported data elements and services to those defined in the NCIP standard, version 2.0,
    // use "ncip_v2_0.xsd".
    // To use version 2.0 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_0_ils-di_extensions.xsd".
    // To use version 2.01 with the eXtensible Catalog Organization's extensions, e.g. the Lookup Item Set service,
    // use "ncip_v2_01_ils-di_extensions.xsd"
    // TODO: Test whether, in fact, order does matter in the supported schemas
    // Note: Order matters - if a schema B references elements in schema A, then the order must be "A,B".
    final String SUPPORTED_SCHEMA_URLS_DEFAULT = "ncip_v2_02.xsd";

    final String VALIDATE_MESSAGES_AGAINST_SCHEMA_KEY = "NCIPServiceValidatorConfiguration.ValidateMessagesAgainstSchema";
    final String VALIDATE_MESSAGES_AGAINST_SCHEMA_DEFAULT = "true";

    final String ADD_DEFAULT_NAMESPACE_URI_KEY = "NCIPServiceValidatorConfiguration.AddDefaultNamespaceURI";
    final String ADD_DEFAULT_NAMESPACE_URI_DEFAULT = "false";

    final String REQUIRE_APPLICATION_PROFILE_TYPE_KEY = "NCIPServiceValidatorConfiguration.RequireApplicationProfileType";
    final String REQUIRE_APPLICATION_PROFILE_TYPE_DEFAULT = "False";

    final String APPLICATION_PROFILE_TYPES_KEY = "NCIPServiceValidatorConfiguration.ApplicationProfileTypes";
    final String APPLICATION_PROFILE_TYPES_DEFAULT = null;

    final String PARSER_FEATURES_KEY = "NCIPServiceValidatorConfiguration.ParserFeatures";
    final String PARSER_FEATURES_DEFAULT = null;

    DefaultNCIPVersion getVersion();

    List<ApplicationProfileType> getApplicationProfileTypes();

    boolean requireApplicationProfileType();

    String[] getNamespaceURIs();

    String getDefaultNamespaceURI();

    boolean addDefaultNamespaceURI();

    List<String> getSupportedSchemaURLs();

    boolean validateMessagesAgainstSchema();

    Map<String, Boolean> getParserFeatures();

    public String getServiceContextClassName();

    public void setServiceContextClassName(String serviceContextClassName);

}
