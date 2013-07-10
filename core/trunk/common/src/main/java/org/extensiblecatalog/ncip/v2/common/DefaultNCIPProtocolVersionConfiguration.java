/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;

import java.util.Properties;

public class DefaultNCIPProtocolVersionConfiguration extends BaseToolkitConfiguration
    implements NCIPProtocolVersionConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultNCIPProtocolVersionConfiguration.class);

    protected String protocolVersionClassName = ProtocolVersionConfiguration.PROTOCOL_VERSION_CLASS_NAME_DEFAULT;
    protected String versionAttribute = NCIPProtocolVersionConfiguration.PROTOCOL_VERSION_VERSION_ATTRIBUTE_DEFAULT;
    protected DefaultNCIPVersion canonicalVersion =
            DefaultNCIPVersion.findByVersionAttribute(NCIPProtocolVersionConfiguration.PROTOCOL_VERSION_CANONICAL_VERSION_ATTRIBUTE_DEFAULT);

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public DefaultNCIPProtocolVersionConfiguration() {

        // Do nothing

    }

    public DefaultNCIPProtocolVersionConfiguration(String appName) {

        this(appName, null);

    }

    public DefaultNCIPProtocolVersionConfiguration(Properties properties) {

        this(null, properties);

    }

    public DefaultNCIPProtocolVersionConfiguration(String appName, Properties properties) {

        super(appName, properties);

        if ( properties != null ) {

            String classNameString = this.properties.getProperty(
                ProtocolVersionConfiguration.PROTOCOL_VERSION_CLASS_NAME_KEY);

            if ( classNameString != null ) {

                this.protocolVersionClassName = classNameString;

            }

            String versionAttributeString = this.properties.getProperty(
                NCIPProtocolVersionConfiguration.PROTOCOL_VERSION_VERSION_ATTRIBUTE_KEY);

            if ( versionAttributeString != null ) {

                this.versionAttribute = versionAttributeString;

            }

            String canonicalVersionAttributeString = this.properties.getProperty(
                NCIPProtocolVersionConfiguration.PROTOCOL_VERSION_CANONICAL_VERSION_ATTRIBUTE_KEY);

            if ( canonicalVersionAttributeString != null ) {

                this.canonicalVersion = DefaultNCIPVersion.findByVersionAttribute(canonicalVersionAttributeString);

            }

        }


    }

    @Override
    public String getComponentClassName() {
        return protocolVersionClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.protocolVersionClassName = componentClassName;
    }

    @Override
    public String getVersionAttribute() {
        return versionAttribute;
    }

    @Override
    public DefaultNCIPVersion getCanonicalVersion() {
        return canonicalVersion;
    }
}
