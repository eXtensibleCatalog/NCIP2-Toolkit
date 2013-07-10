/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;
import org.extensiblecatalog.ncip.v2.service.NCIPVersion;
import org.extensiblecatalog.ncip.v2.service.Protocol;

import java.util.*;

// TODO: Add setters for all attributes
public class DefaultNCIPServiceValidatorConfiguration implements NCIPServiceValidatorConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultNCIPServiceValidatorConfiguration.class);

    protected Properties properties;

    /** The name of the class to use as the ServiceValidator. */
    protected String serviceValidatorClassName = ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_DEFAULT;
    protected Protocol protocol = Protocol.NCIP;
    protected NCIPVersion version = NCIPVersion.VERSION_2_0;
    protected List<ApplicationProfileType> applicationProfileTypes = new ArrayList<ApplicationProfileType>(1);
    protected boolean requireApplicationProfileType = false;
    protected String[] namespaceURIs;
    protected String defaultNamespace;
    protected boolean addDefaultNamespaceURI = false;
    protected String packageNames;
    protected List<String> schemaURLs;

    public DefaultNCIPServiceValidatorConfiguration() {

        this(System.getProperties());

    }

    /**
     * Construct a {@link DefaultNCIPServiceValidatorConfiguration} from the {@link Properties} object. To set more than
     * a single protocol name, version and profile requires using an XML Configuration file.
     * @param properties the {@link Properties} object containing configuration values
     */
    public DefaultNCIPServiceValidatorConfiguration(Properties properties) {

        this.properties = properties;

        String classNameString = null;
        String nameString = null;
        String versionString = null;
        String profileString = null;
        String requireApplicationProfileTypeString = null;
        String namespaceURIsString = null;
        String defaultNamespaceString = null;
        String addDefaultNamespaceString = null;
        String packageNamesString = null;
        String schemaURLsString = null;

        if ( this.properties != null ) {

            classNameString = this.properties.getProperty(
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_DEFAULT);
            nameString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PROTOCOL_NAME_KEY,
                NCIPServiceValidatorConfiguration.PROTOCOL_NAME_DEFAULT);
            versionString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PROTOCOL_VERSION_KEY,
                NCIPServiceValidatorConfiguration.PROTOCOL_VERSION_DEFAULT);
            profileString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PROTOCOL_PROFILE_KEY,
                NCIPServiceValidatorConfiguration.PROTOCOL_PROFILE_DEFAULT);
            requireApplicationProfileTypeString = this.properties.getProperty(NCIPServiceValidatorConfiguration.REQUIRE_APPLICATION_PROFILE_TYPE_KEY,
                NCIPServiceValidatorConfiguration.REQUIRE_APPLICATION_PROFILE_TYPE_DEFAULT);
            namespaceURIsString = this.properties.getProperty(NCIPServiceValidatorConfiguration.NAMESPACE_URIS_KEY,
                NCIPServiceValidatorConfiguration.NAMESPACE_URIS_DEFAULT);
            defaultNamespaceString = this.properties.getProperty(
                NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_KEY,
                NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_DEFAULT);
            addDefaultNamespaceString = this.properties.getProperty(NCIPServiceValidatorConfiguration.ADD_DEFAULT_NAMESPACE_URI_KEY,
                NCIPServiceValidatorConfiguration.ADD_DEFAULT_NAMESPACE_URI_DEFAULT);
            packageNamesString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PACKAGE_NAMES_KEY,
                NCIPServiceValidatorConfiguration.PACKAGE_NAMES_DEFAULT);
            schemaURLsString = this.properties.getProperty(NCIPServiceValidatorConfiguration.SCHEMA_URLS_KEY,
                NCIPServiceValidatorConfiguration.SCHEMA_URLS_DEFAULT);

        }

        if ( classNameString != null ) {

            this.serviceValidatorClassName = classNameString;

        }

        if ( nameString != null ) {

            Protocol tempProtocol = Protocol.valueOf(nameString);
            if ( tempProtocol != null ) {

                this.protocol = tempProtocol;

            } else {

                LOG.warn("Protocol '" + nameString + "' is not valid; using default of '" + Protocol.NCIP.getName()
                    + "'.");
                this.protocol = Protocol.NCIP;

            }

        }

        if ( versionString != null ) {

            NCIPVersion tempVersion = (NCIPVersion)this.protocol.getVersion(versionString);
            if ( tempVersion != null ) {

                this.version = tempVersion;

            } else {

                LOG.warn("Version '" + versionString + "' is not valid; using default of '"
                    + NCIPVersion.VERSION_2_0.getVersion() + "'.");
                this.version = NCIPVersion.VERSION_2_0;

            }

        }

        if ( profileString != null ) {

            ApplicationProfileType tempProfile = protocol.getProfile(profileString);
            if ( tempProfile != null ) {

                this.applicationProfileTypes.add(tempProfile);

            } else {

                LOG.warn("ApplicationProfileType '" + profileString + "' is not recognized; ignoring this value.");

            }

        }

        if ( requireApplicationProfileTypeString != null ) {

            requireApplicationProfileType = Boolean.parseBoolean(requireApplicationProfileTypeString);

        }

        if ( namespaceURIsString != null ) {

            String[] namespaceURIsArray = namespaceURIsString.split(",");
            if ( namespaceURIsArray != null && namespaceURIsArray.length > 0 ) {

                this.namespaceURIs = namespaceURIsArray;

            } else {

                LOG.warn("NamespaceURIs is empty; using default.");

            }

        }

        if ( defaultNamespaceString != null ) {

            this.defaultNamespace = defaultNamespaceString;

        }

        if ( addDefaultNamespaceString != null ) {

            addDefaultNamespaceURI = Boolean.parseBoolean(addDefaultNamespaceString);

        }


        if ( packageNamesString != null ) {

            this.packageNames = packageNamesString;

        }

        if ( schemaURLsString != null ) {

            List<String> schemaURLsList = ToolkitHelper.createStringList(schemaURLsString);
            if ( schemaURLsList != null && ! schemaURLsList.isEmpty() ) {

                this.schemaURLs = schemaURLsList;

            } else {

                LOG.warn("SchemaURLs is empty; no XML validation will be performed.");

            }

        }

    }

    @Override
    public String getServiceValidatorClassName() {

        return serviceValidatorClassName;

    }

    @Override
    public void setServiceValidatorClassName(String className) {

        this.serviceValidatorClassName = className;

    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public NCIPVersion getVersion() {
        return version;
    }

    @Override
    public List<ApplicationProfileType> getApplicationProfileTypes() {
        return applicationProfileTypes;
    }

    @Override
    public boolean requireApplicationProfileType() {
        return requireApplicationProfileType;
    }

    @Override
    public String[] getNamespaceURIs() {
        return namespaceURIs;
    }

    @Override
    public String getDefaultNamespaceURI() {
        return defaultNamespace;
    }

    @Override
    public boolean addDefaultNamespaceURI() {
        return addDefaultNamespaceURI;
    }

    @Override
    public String getPackageNames() {
        return packageNames;
    }

    @Override
    public List<String> getSchemaURLs() {
        return schemaURLs;
    }

    @Override
    public Properties getProperties() {

        return properties;

    }

    @Override
    public Object getProperty(Object key) {

        return properties.get(key);

    }

}
