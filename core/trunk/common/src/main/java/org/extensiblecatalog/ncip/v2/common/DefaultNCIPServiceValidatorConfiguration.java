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
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.*;

public class DefaultNCIPServiceValidatorConfiguration extends BaseToolkitConfiguration
    implements NCIPServiceValidatorConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultNCIPServiceValidatorConfiguration.class);

    /** The name of the class to use as the ServiceValidator. */
    protected String serviceValidatorClassName = ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_DEFAULT;
    protected Protocol protocol = Protocol.NCIP;
    protected DefaultNCIPVersion version = DefaultNCIPVersion.VERSION_2_0;
    protected List<ApplicationProfileType> applicationProfileTypes = new ArrayList<ApplicationProfileType>(1);
    protected boolean requireApplicationProfileType = false;
    protected String[] namespaceURIs = NCIPServiceValidatorConfiguration.NAMESPACE_URIS_DEFAULT.split(",");
    protected String defaultNamespace = NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_DEFAULT;
    protected boolean addDefaultNamespaceURI = false;
    protected List<String> supportedSchemaURLs =
            ToolkitHelper.createStringList(NCIPServiceValidatorConfiguration.SUPPORTED_SCHEMA_URLS_DEFAULT);
    protected boolean validateMessagesAgainstSchema = true;
    protected Map<String, Boolean> parserFeatures = new HashMap<String, Boolean>();
    protected String serviceContextClassName = ServiceValidatorConfiguration.SERVICE_VALIDATOR_SERVICE_CONTEXT_CLASS_NAME_DEFAULT;

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public DefaultNCIPServiceValidatorConfiguration() throws ToolkitException {

        // Do nothing

    }

    public DefaultNCIPServiceValidatorConfiguration(String appName) throws ToolkitException {

        this(appName, null);

    }

    public DefaultNCIPServiceValidatorConfiguration(Properties properties) throws ToolkitException {

        this(null, properties);

    }

    /**
     * Construct an instance for the named application with the overrides given by the {@link Properties} object.
     * @param properties overrides for configuration properties
     */
    // TODO: To set more than a single protocol name, version and profile requires using an XML Configuration file.
    public DefaultNCIPServiceValidatorConfiguration(String appName, Properties properties) throws ToolkitException {

        super(appName, properties);

        String classNameString = null;
        String nameString = null;
        String profileString = null;
        String namespaceURIsString = null;
        String defaultNamespaceString = null;
        String addDefaultNamespaceString = null;
        String supportedSchemaURLsString = null;
        String validateMessagesAgainstSchemaString = null;
        String parserFeaturesString = null;
        String serviceContextClassNameString = null;

        if ( this.properties != null ) {

            classNameString = this.properties.getProperty(
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CLASS_NAME_DEFAULT);
            nameString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PROTOCOL_NAME_KEY,
                NCIPServiceValidatorConfiguration.PROTOCOL_NAME_DEFAULT);
            profileString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PROTOCOL_PROFILE_KEY,
                NCIPServiceValidatorConfiguration.PROTOCOL_PROFILE_DEFAULT);
            namespaceURIsString = this.properties.getProperty(NCIPServiceValidatorConfiguration.NAMESPACE_URIS_KEY,
                NCIPServiceValidatorConfiguration.NAMESPACE_URIS_DEFAULT);
            defaultNamespaceString = this.properties.getProperty(
                NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_KEY,
                NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_DEFAULT);
            addDefaultNamespaceString = this.properties.getProperty(NCIPServiceValidatorConfiguration.ADD_DEFAULT_NAMESPACE_URI_KEY,
                NCIPServiceValidatorConfiguration.ADD_DEFAULT_NAMESPACE_URI_DEFAULT);
            supportedSchemaURLsString = this.properties.getProperty(NCIPServiceValidatorConfiguration.SUPPORTED_SCHEMA_URLS_KEY,
                NCIPServiceValidatorConfiguration.SUPPORTED_SCHEMA_URLS_DEFAULT);
            validateMessagesAgainstSchemaString = this.properties.getProperty(NCIPServiceValidatorConfiguration.VALIDATE_MESSAGES_AGAINST_SCHEMA_KEY,
                NCIPServiceValidatorConfiguration.VALIDATE_MESSAGES_AGAINST_SCHEMA_DEFAULT);
            parserFeaturesString = this.properties.getProperty(NCIPServiceValidatorConfiguration.PARSER_FEATURES_KEY,
                NCIPServiceValidatorConfiguration.PARSER_FEATURES_DEFAULT);
            serviceContextClassNameString = this.properties.getProperty(NCIPServiceValidatorConfiguration.SERVICE_VALIDATOR_SERVICE_CONTEXT_CLASS_NAME_KEY,
                NCIPServiceValidatorConfiguration.SERVICE_VALIDATOR_SERVICE_CONTEXT_CLASS_NAME_DEFAULT);

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

        this.version = (DefaultNCIPVersion)ProtocolVersionFactory.buildProtocolVersion(properties);

        if ( profileString != null ) {

            ApplicationProfileType tempProfile = protocol.getProfile(profileString);
            if ( tempProfile != null ) {

                this.applicationProfileTypes.add(tempProfile);

            } else {

                LOG.warn("ApplicationProfileType '" + profileString + "' is not recognized; ignoring this value.");

            }

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

        if ( supportedSchemaURLsString != null ) {

            List<String> schemaURLsList = ToolkitHelper.createStringList(supportedSchemaURLsString);
            if ( schemaURLsList != null && ! schemaURLsList.isEmpty() ) {

                this.supportedSchemaURLs = schemaURLsList;

            } else {

                LOG.warn("SchemaURLs is empty; no XML validation will be performed.");

            }

        }

        if ( validateMessagesAgainstSchemaString != null ) {

            validateMessagesAgainstSchema = Boolean.parseBoolean(validateMessagesAgainstSchemaString);

        }

        if ( parserFeaturesString != null ) {

            String[] parserFeaturesArray = parserFeaturesString.split(",");
            if ( parserFeaturesArray.length > 0 ) {

                for ( String parserFeature : parserFeaturesArray ) {

                    String[] featureHalves = parserFeature.split("=");
                    if ( featureHalves.length == 2 ) {

                        parserFeatures.put(featureHalves[0], Boolean.parseBoolean(featureHalves[1]));


                    } else {

                        LOG.warn("ParserFeatures element '" + parserFeature
                                + "' lacks an equals sign or has more than one.");
                    }
                }

            } else {

                LOG.warn("ParserFeatures is empty.");

            }

        }

        if ( serviceContextClassNameString != null ) {

            serviceContextClassName = serviceContextClassNameString;

        }

    }

    public void setServiceValidatorClassName(String serviceValidatorClassName) {
        this.serviceValidatorClassName = serviceValidatorClassName;
    }

    @Override
    public String getComponentClassName() {
        return serviceValidatorClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.serviceValidatorClassName = componentClassName;
    }

    public void setVersion(DefaultNCIPVersion version) {
        this.version = version;
    }

    @Override
    public DefaultNCIPVersion getVersion() {
        return version;
    }

    public void setApplicationProfileTypes(List<ApplicationProfileType> applicationProfileTypes) {
        this.applicationProfileTypes = applicationProfileTypes;
    }

    @Override
    public List<ApplicationProfileType> getApplicationProfileTypes() {
        return applicationProfileTypes;
    }

    @Override
    public boolean requireApplicationProfileType() {
        return requireApplicationProfileType;
    }

    public void setNamespaceURIs(String[] namespaceURIs) {
        this.namespaceURIs = namespaceURIs;
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

    public void setSupportedSchemaURLs(List<String> supportedSchemaURLs) {
        this.supportedSchemaURLs = supportedSchemaURLs;
    }

    @Override
    public List<String> getSupportedSchemaURLs() {
        return supportedSchemaURLs;
    }

    @Override
    public boolean validateMessagesAgainstSchema() {
        return validateMessagesAgainstSchema;
    }

    public void setParserFeatures(Map<String, Boolean> parserFeatures) {
        this.parserFeatures = parserFeatures;
    }

    @Override
    public Map<String, Boolean> getParserFeatures() {

        return parserFeatures;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean isRequireApplicationProfileType() {
        return requireApplicationProfileType;
    }

    public void setRequireApplicationProfileType(boolean requireApplicationProfileType) {
        this.requireApplicationProfileType = requireApplicationProfileType;
    }

    public String getDefaultNamespace() {
        return defaultNamespace;
    }

    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }

    public boolean isAddDefaultNamespaceURI() {
        return addDefaultNamespaceURI;
    }

    public void setAddDefaultNamespaceURI(boolean addDefaultNamespaceURI) {
        this.addDefaultNamespaceURI = addDefaultNamespaceURI;
    }

    public boolean isValidateMessagesAgainstSchema() {
        return validateMessagesAgainstSchema;
    }

    public void setValidateMessagesAgainstSchema(boolean validateMessagesAgainstSchema) {
        this.validateMessagesAgainstSchema = validateMessagesAgainstSchema;
    }

    public String getServiceContextClassName() {
        return serviceContextClassName;
    }

    public void setServiceContextClassName(String serviceContextClassName) {
        this.serviceContextClassName = serviceContextClassName;
    }

}
