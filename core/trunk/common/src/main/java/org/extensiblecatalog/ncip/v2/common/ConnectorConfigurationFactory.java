/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;

/**
 * Builds ConnectorConfiguration objects. The primary intended use is to call one of the static
 * <code>buildConfiguration</code> methods, passing application name and/or properties as desired, and obtain a
 * {@link ConnectorConfiguration} object. It is also possible to construct a factory instance with those same
 * parameters; the factory object will construct the {@link ConnectorConfiguration} object by calling the
 * appropriate <code>buildConfiguration</code> method, and return that same object on every call to the instance
 * method {@link #getConfiguration()}.
 */
public class ConnectorConfigurationFactory extends BaseConfigurationFactory<ConnectorConfiguration> {

    private static final Logger LOG = Logger.getLogger(ConnectorConfigurationFactory.class);

    /**
     * The instance of {@link ConnectorConfiguration} that this factory returns on every call to
     * {@link #getConfiguration()}.
     */
    protected ConnectorConfiguration configuration;

    /**
     * Create an instance of the factory without initializing the {@link #configuration} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public ConnectorConfigurationFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory using a configuration obtained by calling {@link #buildConfiguration(String)}.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     */
    public ConnectorConfigurationFactory(String appName) throws ToolkitException {

        configuration = buildConfiguration(appName);

    }

    /**
     * Create an instance of the factory using a configuration obtained by calling
     * {@link #buildConfiguration(String, Properties)}.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @throws ToolkitException
     */
    public ConnectorConfigurationFactory(String appName, Properties properties) throws ToolkitException {

        configuration = buildConfiguration(appName, properties);

    }

    /**
     * Create an instance of the factory using a configuration obtained by calling
     * {@link #buildConfiguration(Properties)}.
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @throws ToolkitException
     */
    public ConnectorConfigurationFactory(Properties properties) throws ToolkitException {

        configuration = buildConfiguration(properties);

    }

    /**
     * Create a factory which returns the supplied configuration.
     * @param configInstance an instance of {@link ConnectorConfiguration}
     * @throws ToolkitException
     */
    public ConnectorConfigurationFactory(ConnectorConfiguration configInstance) throws ToolkitException {

        this.configuration = configInstance;

    }
    
    /**
     * Return the factory's configuration object.
     * @return the {@link ConnectorConfiguration} object
     */
    public ConnectorConfiguration getConfiguration() {

        return configuration;

    }

    /**
     * Convenience method which calls {@link #buildConfiguration(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return
     * @throws ToolkitException
     */
    public static ConnectorConfiguration buildConfiguration() throws ToolkitException {

        return buildConfiguration(null, null);

    }

    /**
     * Convenience method which calls {@link #buildConfiguration(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return
     * @throws ToolkitException
     */
    public static ConnectorConfiguration buildConfiguration(Properties properties) throws ToolkitException {

        return buildConfiguration(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildConfiguration(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return
     * @throws ToolkitException
     */
    public static ConnectorConfiguration buildConfiguration(String appName) throws ToolkitException {

        return buildConfiguration(appName, null);

    }

    /**
     * Construct a ConnectorConfiguration object for the given appName using the provided property overrides.
     * The name of the class to use is the value of the key
     * {@link ConnectorConfiguration#CONNECTOR_CONFIG_CLASS_NAME_KEY} in the properties; if that is not specified the
     * default {@link ConnectorConfiguration#CONNECTOR_CONFIG_CLASS_NAME_DEFAULT} is used.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new ConnectorConfiguration object
     * @throws ToolkitException
     */
    public static ConnectorConfiguration buildConfiguration(String appName, Properties properties) throws ToolkitException {

        ConnectorConfiguration config;

        config = (ConnectorConfiguration) buildConfiguration(appName, Connector.COMPONENT_NAME,
                ConnectorConfiguration.CONNECTOR_CONFIG_FILE_NAME_KEY,
                ConnectorConfiguration.CONNECTOR_CONFIG_FILE_NAME_DEFAULT,
                properties,
                ConnectorConfiguration.CONNECTOR_CONFIG_CLASS_NAME_KEY,
                ConnectorConfiguration.CONNECTOR_CONFIG_CLASS_NAME_DEFAULT,
                ConnectorConfiguration.CONNECTOR_PROPERTIES_FILENAME_KEY,
                ConnectorConfiguration.CONNECTOR_PROPERTIES_FILENAME_DEFAULT,
                ConnectorConfiguration.CONNECTOR_LOCAL_PROPERTIES_FILENAME_KEY,
                ConnectorConfiguration.CONNECTOR_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                ConnectorConfiguration.CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                ConnectorConfiguration.CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return config;

    }

}
