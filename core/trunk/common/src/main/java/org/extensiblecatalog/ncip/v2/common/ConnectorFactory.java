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

public class ConnectorFactory extends BaseComponentFactory<Connector, ConnectorConfiguration> {

    private static final Logger LOG = Logger.getLogger(ConnectorFactory.class);

    /**
     * The instance of {@link Connector} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected Connector connector;

    /**
     * Create an instance of the factory without initializing the {@link #connector} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public ConnectorFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link Connector}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ConnectorFactory(Properties properties) throws ToolkitException {

        this.connector = buildConnector(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link Connector} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public ConnectorFactory(String appName) throws ToolkitException {

        this.connector = buildConnector(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link Connector} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ConnectorFactory(String appName, Properties properties) throws ToolkitException {

        this.connector = buildConnector(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link Connector} built with the supplied
     * {@link ConnectorConfiguration}.
     * @param configuration an instance of {@link ConnectorConfiguration}
     */
    public ConnectorFactory(ConnectorConfiguration configuration) throws ToolkitException {

        this.connector = (Connector) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #connector} object.
     * @return the {@link #connector}
     */
    @Override
    public Connector getComponent() {

        return connector;

    }

    /**
     * Convenience method which calls {@link #buildConnector(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link Connector}
     * @throws ToolkitException
     */
    public static Connector buildConnector() throws ToolkitException {

        return buildConnector(null, null);

    }

    /**
     * Convenience method which calls {@link #buildConnector(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link Connector}
     * @throws ToolkitException
     */
    public static Connector buildConnector(Properties properties) throws ToolkitException {

        return buildConnector(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildConnector(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link Connector}
     * @throws ToolkitException
     */
    public static Connector buildConnector(String appName) throws ToolkitException {

        return buildConnector(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link ConnectorConfiguration}
     * @return the {@link Connector}
     * @throws ToolkitException
     */
    public static Connector buildConnector(ConnectorConfiguration configuration) throws ToolkitException {

        return (Connector) buildComponent(configuration);

    }

    /**
     * Construct a Connector object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static Connector buildConnector(String appName, Properties properties) throws ToolkitException {

        Connector conn;

        conn = (Connector)buildComponent(ConnectorFactory.class, appName, Connector.COMPONENT_NAME,
                ConnectorConfiguration.CONNECTOR_CONFIG_FILE_NAME_KEY,
                ConnectorConfiguration.CONNECTOR_CONFIG_FILE_NAME_DEFAULT,
                properties,
                ConnectorConfiguration.CONNECTOR_PROPERTIES_FILENAME_KEY,
                ConnectorConfiguration.CONNECTOR_PROPERTIES_FILENAME_DEFAULT,
                ConnectorConfiguration.CONNECTOR_LOCAL_PROPERTIES_FILENAME_KEY,
                ConnectorConfiguration.CONNECTOR_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                ConnectorConfiguration.CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                ConnectorConfiguration.CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return conn;

    }
}
