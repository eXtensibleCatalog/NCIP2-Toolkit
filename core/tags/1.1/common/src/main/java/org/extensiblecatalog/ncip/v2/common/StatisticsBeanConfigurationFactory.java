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
 * Instantiates StatisticsBeanConfiguration objects.
 */
public class StatisticsBeanConfigurationFactory extends BaseConfigurationFactory {

    private static final Logger LOG = Logger.getLogger(StatisticsBeanConfigurationFactory.class);

    protected static Properties serverContextProperties = new Properties();

    /**
     * This is the instance returned to all callers of getConfiguration(). If you want multiple, differing
     * configurations you will need to call {@link #getUniqueConfiguration()} or {@link #getConfiguration(java.util.Properties)}.
     */
    protected static StatisticsBeanConfiguration sharedConfigInstance;

    /**
     * Create the singleton instance of the factory using the supplied properties.
     * @param properties
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public StatisticsBeanConfigurationFactory(Properties properties) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getConfiguration(properties);

        } else {

            throw new ToolkitException("Attempt to re-initialize shared singleton statistics bean configuration from " +
                "Properties.");

        }

    }

    /**
     * Create the singleton instance of the factory using the supplied configuration.
     * @param configInstance
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public StatisticsBeanConfigurationFactory(StatisticsBeanConfiguration configInstance) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            this.sharedConfigInstance = configInstance;

        } else {

            throw new ToolkitException("Attempt to re-initialize shared singleton statistics bean configuration with " +
                "a new ConnectorConfiguration.");

        }

    }

    /**
     * Get the static StatisticsBeanConfiguration object. If this is not already set (e.g. by Spring) or a prior
     * call to this method, this calls {@link #getUniqueConfiguration()} to construct it.
     * @return the {@link org.extensiblecatalog.ncip.v2.common.StatisticsBeanConfiguration} object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static synchronized StatisticsBeanConfiguration getConfiguration() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the configInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getUniqueConfiguration();

        }

        return sharedConfigInstance;

    }

    /**
     * Calls populateProperties and then getConfiguration(Properties) to construct a new StatisticsBeanConfiguration object.
     *
     * @return
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static StatisticsBeanConfiguration getUniqueConfiguration() throws ToolkitException {

        Properties properties = populateProperties(serverContextProperties);

        StatisticsBeanConfiguration config = getConfiguration(properties);

        return config;

    }

    /**
     * Construct a StatisticsBeanConfiguration object using the provided properties. The name of the class to use
     * is the value of the key "StatisticsBeanConfigurationClass" in the properties; if that is not specified the
     * default StatisticsBeanConfiguration is used.
     *
     * @param properties the properties containing any overrides to default values
     * @return the new StatisticsBeanConfiguration object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static StatisticsBeanConfiguration getConfiguration(Properties properties) throws ToolkitException {

        StatisticsBeanConfiguration config;

        String configClassName = properties.getProperty(StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_CLASS_NAME_KEY,
            StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_CLASS_NAME_DEFAULT);

        if ( configClassName != null ) {

            config = (StatisticsBeanConfiguration)constructConfiguration(configClassName, properties);

        } else {

            throw new ToolkitException(StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_CLASS_NAME_KEY
                + " property was set to null");

        }

        return config;

    }

    /**
     * Called by StatisticsBeanConfigurationFactoryInitializingContextListener to pass web server context properties
     * into the factory.
     * @param properties
     */
    public static synchronized void setServerContextProperties(Properties properties) {

        LOG.debug("Setting serverContextProperties to " + properties);
        serverContextProperties = properties;

    }

    public static synchronized void setSharedConfigInstance(StatisticsBeanConfiguration configInstance)
        throws ToolkitException {

        if ( sharedConfigInstance == null) {

            sharedConfigInstance = configInstance;

        } else {

            throw new ToolkitException("Shared config instance already initialized.");

        }
    }

    @Override
    public Properties getDefaultProperties() {

        return populateProperties(serverContextProperties);

    }
}
