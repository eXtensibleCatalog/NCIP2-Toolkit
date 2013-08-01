/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;

import java.util.*;

// TODO: Write a Multi-servicecontext class

/**
 * This class supports service contexts for a single {@link DefaultNCIPVersion} (i.e. XML Schema) but with potentially
 * multiple {@link ApplicationProfileType}s.
 */
public class NCIPServiceContext implements ServiceContext {

    protected Properties properties;

    protected DefaultNCIPVersion version;
    protected List<ApplicationProfileType> applicationProfileTypes;
    protected boolean requireApplicationProfileType = false;
    protected String[] namespaceURIs;
    protected String defaultNamespace;
    protected boolean addDefaultNamespace = false;
    protected List<String> schemaURLs;
    protected boolean validateMessagesAgainstSchema = true;
    protected Map<String, Boolean> parserFeatures;

    public NCIPServiceContext() {

        // Do nothing

    }

    public NCIPServiceContext(ServiceValidatorConfiguration config) throws ToolkitException {

        if ( config instanceof NCIPServiceValidatorConfiguration) {

            NCIPServiceValidatorConfiguration ncipConfig = (NCIPServiceValidatorConfiguration)config;
            this.version = ncipConfig.getVersion();
            this.requireApplicationProfileType = ncipConfig.requireApplicationProfileType();
            this.applicationProfileTypes = ncipConfig.getApplicationProfileTypes();
            this.namespaceURIs = ncipConfig.getNamespaceURIs();            
            this.defaultNamespace = ncipConfig.getDefaultNamespaceURI();
            this.addDefaultNamespace = ncipConfig.addDefaultNamespaceURI();
            this.schemaURLs = ncipConfig.getSupportedSchemaURLs();
            this.validateMessagesAgainstSchema = ncipConfig.validateMessagesAgainstSchema();
            this.parserFeatures = ncipConfig.getParserFeatures();

        } else {

            throw new ToolkitException(NCIPServiceContext.class.getName() + " constructor called with "
            + config.getClass().getName() + ", which is not an instance of " + NCIPServiceValidatorConfiguration.class.getName());
        }
    }

    public boolean requiresApplicationProfileType() {

        return requireApplicationProfileType;

    }

    public boolean addDefaultNamespace() {

       return addDefaultNamespace;

    }

    public List<ApplicationProfileType> getApplicationProfileTypes() {

        return applicationProfileTypes;

    }

    public Map<String, Boolean> getParserFeatures() {

        return parserFeatures;

    }

    public DefaultNCIPVersion getVersion() {
        return version;
    }

    public void setVersion(DefaultNCIPVersion version) {
        this.version = version;
    }

    @Override
    public void validateBeforeMarshalling(NCIPMessage ncipMessage) throws ValidationException {

        if ( ncipMessage.getVersion() == null || ncipMessage.getVersion().isEmpty() ) {

            if ( version != null ) {

                ncipMessage.setVersion(version.getVersionAttribute());

            }
        }

    }

    @Override
    public void validateAfterUnmarshalling(NCIPMessage ncipMessage) throws ValidationException {

        if ( ncipMessage.getVersion() == null || ncipMessage.getVersion().isEmpty() ) {

            if ( version != null ) {

                ncipMessage.setVersion(version.getVersionAttribute());

            }
        }

    }

    /** Get the array of XML namespace URIs for this service invocation. */
    public String[] getNamespaceURIs() {

        return namespaceURIs;

    }

    /**
     * The default namespace, if any, for this service invocation.
     * @return the default namespace or null
     */
    public String getDefaultNamespace() {

        return defaultNamespace;

    }


    public List<String> getSchemaURLs() {

        return schemaURLs;

    }

    public boolean validateMessagesAgainstSchema() {

        return validateMessagesAgainstSchema;

    }
}
