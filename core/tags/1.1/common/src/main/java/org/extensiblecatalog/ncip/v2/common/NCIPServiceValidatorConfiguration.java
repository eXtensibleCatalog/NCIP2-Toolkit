/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;
import org.extensiblecatalog.ncip.v2.service.NCIPVersion;
import org.extensiblecatalog.ncip.v2.service.Protocol;

import java.util.ArrayList;
import java.util.List;

public interface NCIPServiceValidatorConfiguration extends ServiceValidatorConfiguration {

    final String PROTOCOL_NAME_KEY = "NCIPServiceValidatorConfiguration.Protocol";
    final String PROTOCOL_NAME_DEFAULT = "NCIP";

    final String PROTOCOL_VERSION_KEY = "NCIPServiceValidatorConfiguration.ProtocolVersion";
    final String PROTOCOL_VERSION_DEFAULT = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd";

    final String PROTOCOL_PROFILE_KEY = "NCIPServiceValidatorConfiguration.ProtocolProfile";
    final String PROTOCOL_PROFILE_DEFAULT = null;

    final String NAMESPACE_URIS_KEY = "NCIPServiceValidatorConfiguration.NamespaceURIs";
    final String NAMESPACE_URIS_DEFAULT = "http://www.niso.org/2008/ncip";

    final String DEFAULT_NAMESPACE_URI_KEY = "NCIPServiceValidatorConfiguration.DefaultNamespaceURI";
    final String DEFAULT_NAMESPACE_URI_DEFAULT = "http://www.niso.org/2008/ncip";

    final String ADD_DEFAULT_NAMESPACE_URI_KEY = "NCIPServiceValidatorConfiguration.AddDefaultNamespaceURI";
    final String ADD_DEFAULT_NAMESPACE_URI_DEFAULT = "false";

    final String PACKAGE_NAMES_KEY = "NCIPServiceValidatorConfiguration.PackageNames";
    final String PACKAGE_NAMES_DEFAULT = "org.extensiblecatalog.ncip.v2.binding.jaxb.elements";

    final String SCHEMA_URLS_KEY = "NCIPServiceValidatorConfiguration.SchemaURLs";
    final String SCHEMA_URLS_DEFAULT = "ncip_v2_01_ils-di_extensions.xsd";

    final String REQUIRE_APPLICATION_PROFILE_TYPE_KEY = "NCIPServiceValidatorConfiguration.RequireApplicationProfileType";
    final String REQUIRE_APPLICATION_PROFILE_TYPE_DEFAULT = "False";

    final String APPLICATION_PROFILE_TYPES_KEY = "NCIPServiceValidatorConfiguration.ApplicationProfileTypes";
    final List<ApplicationProfileType> APPLICATION_PROFILE_TYPES_DEFAULT
        = new ArrayList<ApplicationProfileType>(0);

    String getServiceValidatorClassName();
    void setServiceValidatorClassName(String className);

    Protocol getProtocol();
    NCIPVersion getVersion();

    List<ApplicationProfileType> getApplicationProfileTypes();

    boolean requireApplicationProfileType();

    String[] getNamespaceURIs();

    String getDefaultNamespaceURI();

    boolean addDefaultNamespaceURI();

    String getPackageNames();

    List<String> getSchemaURLs();
}
