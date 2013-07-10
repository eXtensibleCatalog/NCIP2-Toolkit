/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;

public class ProtocolVersionFactory extends BaseComponentFactory<ProtocolVersion, ProtocolVersionConfiguration> {

    private static final Logger LOG = Logger.getLogger(ProtocolVersionFactory.class);

    /**
     * The instance of {@link ProtocolVersion} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected ProtocolVersion protocolVersion;

    /**
     * Create an instance of the factory without initializing the {@link #protocolVersion} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public ProtocolVersionFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link ProtocolVersion}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ProtocolVersionFactory(Properties properties) throws ToolkitException {

        this.protocolVersion = buildProtocolVersion(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link ProtocolVersion} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public ProtocolVersionFactory(String appName) throws ToolkitException {

        this.protocolVersion = buildProtocolVersion(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link ProtocolVersion} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ProtocolVersionFactory(String appName, Properties properties) throws ToolkitException {

        this.protocolVersion = buildProtocolVersion(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link ProtocolVersion} built with the supplied
     * {@link ProtocolVersionConfiguration}.
     * @param configuration an instance of {@link ProtocolVersionConfiguration}
     */
    public ProtocolVersionFactory(ProtocolVersionConfiguration configuration) throws ToolkitException {

        this.protocolVersion = (ProtocolVersion) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #protocolVersion} object.
     * @return the {@link #protocolVersion}
     */
    @Override
    public ProtocolVersion getComponent() {

        return protocolVersion;

    }

    /**
     * Convenience method which calls {@link #buildProtocolVersion(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link ProtocolVersion}
     * @throws ToolkitException
     */
    public static ProtocolVersion buildProtocolVersion() throws ToolkitException {

        return buildProtocolVersion(null, null);

    }

    /**
     * Convenience method which calls {@link #buildProtocolVersion(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link ProtocolVersion}
     * @throws ToolkitException
     */
    public static ProtocolVersion buildProtocolVersion(Properties properties) throws ToolkitException {

        return buildProtocolVersion(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildProtocolVersion(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link ProtocolVersion}
     * @throws ToolkitException
     */
    public static ProtocolVersion buildProtocolVersion(String appName) throws ToolkitException {

        return buildProtocolVersion(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link ProtocolVersionConfiguration}
     * @return the {@link ProtocolVersion}
     * @throws ToolkitException
     */
    public static ProtocolVersion buildProtocolVersion(ProtocolVersionConfiguration configuration) throws ToolkitException {

        return (ProtocolVersion) buildComponent(configuration);

    }

    /**
     * Construct a ProtocolVersion object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static ProtocolVersion buildProtocolVersion(String appName, Properties properties) throws ToolkitException {

        ProtocolVersion pv;

        pv = (ProtocolVersion)buildComponent(ProtocolVersionFactory.class, appName, ProtocolVersion.COMPONENT_NAME,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_CONFIG_FILE_NAME_KEY,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_CONFIG_FILE_NAME_DEFAULT,
                properties,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_PROPERTIES_FILENAME_KEY,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_PROPERTIES_FILENAME_DEFAULT,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_LOCAL_PROPERTIES_FILENAME_KEY,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                ProtocolVersionConfiguration.PROTOCOL_VERSION_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return pv;

    }
}
