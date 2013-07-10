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
 * This class supports service contexts for a single {@link NCIPVersion} (i.e. XML Schema) but with potentially
 * multiple {@link ApplicationProfileType}s.
 */
public class NCIPServiceContext implements ServiceContext {

    protected Properties properties;

    protected NCIPVersion version;
    protected List<ApplicationProfileType> applicationProfileTypes;
    protected boolean requireApplicationProfileType = false;
    protected String[] namespaceURIs;
    protected String defaultNamespace;
    protected boolean addDefaultNamespace = false;
    protected String packageNames;
    protected List<String> schemaURLs;

    public NCIPServiceContext(ServiceValidatorConfiguration config) throws ToolkitException {

        if ( config instanceof NCIPServiceValidatorConfiguration) {

            NCIPServiceValidatorConfiguration ncipConfig = (NCIPServiceValidatorConfiguration)config;
            this.version = ncipConfig.getVersion();
            this.requireApplicationProfileType = ncipConfig.requireApplicationProfileType();
            this.applicationProfileTypes = ncipConfig.getApplicationProfileTypes();
            this.namespaceURIs = ncipConfig.getNamespaceURIs();
            this.defaultNamespace = ncipConfig.getDefaultNamespaceURI();
            this.addDefaultNamespace = ncipConfig.addDefaultNamespaceURI();
            this.packageNames = ncipConfig.getPackageNames();
            this.schemaURLs = ncipConfig.getSchemaURLs();

        } else {

            throw new ToolkitException(NCIPv201ServiceContext.class.getName() + " constructor called with "
            + config.getClass().getName() + ", which is not an instance of " + NCIPServiceValidatorConfiguration.class.getName());
        }
    }

    protected NCIPServiceContext(NCIPVersion version, List<ApplicationProfileType> applicationProfileTypes,
                              boolean requireApplicationProfileType,
                              String jaxbPackageNames, String defaultNamespaceUri, boolean addDefaultNamespace,
                              String[] namespaceUris, List<String> schemaUrlsList) {

        this.version = version;
        this.requireApplicationProfileType = requireApplicationProfileType;
        this.applicationProfileTypes = applicationProfileTypes;
        this.namespaceURIs = namespaceUris;
        this.defaultNamespace = defaultNamespaceUri;
        this.addDefaultNamespace = addDefaultNamespace;
        this.packageNames = jaxbPackageNames;
        this.schemaURLs = schemaUrlsList;

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

    /**
     * Format a list of Strings
     * @param strings a iterator for a collection of String objects
     * @return
     */
    public static String formatList(Iterator<String> strings) {

        String result = "";
        if ( strings != null && strings.hasNext() ) {

            StringBuilder sb = new StringBuilder();
            while ( strings.hasNext() ) {

                sb.append(strings.next()).append(", ");
            }
            result = sb.toString();
            result = result.substring(0, result.length() - 2);

        }
        return result;

    }

    @Override
    public void validateBeforeMarshalling(NCIPMessage ncipMessage) throws ValidationException {

        if ( ncipMessage.getVersion() == null || ncipMessage.getVersion().isEmpty() ) {

            if ( version != null ) {

                ncipMessage.setVersion(version.getVersion());

            }
        }

    }

    @Override
    public void validateAfterUnmarshalling(NCIPMessage ncipMessage) throws ValidationException {

        if ( ncipMessage.getVersion() == null || ncipMessage.getVersion().isEmpty() ) {

            if ( version != null ) {

                ncipMessage.setVersion(version.getVersion());

            }
        }

    }

    /**
     * Get the java package names for the JAXB-generated classes to use for this service invocation.
     * @return a non-null comma-separated list of package names
     */
    public String getPackageNames() {

        return packageNames;

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

}
