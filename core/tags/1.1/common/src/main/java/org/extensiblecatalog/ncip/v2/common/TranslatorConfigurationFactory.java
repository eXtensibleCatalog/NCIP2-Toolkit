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
 * Instantiates TranslatorConfiguration objects.
 */
public class TranslatorConfigurationFactory extends BaseConfigurationFactory {

    private static final Logger LOG = Logger.getLogger(TranslatorConfigurationFactory.class);

    protected static Properties serverContextProperties = new Properties();

    /**
     * This is the instance returned to all callers of getConfiguration(). If you want multiple, differing
     * configurations you will need to call {@link #getUniqueConfiguration()} or {@link #getConfiguration(Properties)}.
     */
    protected static TranslatorConfiguration sharedConfigInstance;

    /**
     * Create the singleton instance of the factory using the supplied properties.
     * @param properties
     * @throws ToolkitException
     */
    public TranslatorConfigurationFactory(Properties properties) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getConfiguration(properties);

        } else {

            throw new ToolkitException(
                "Attempt to re-initialize shared singleton translator configuration from Properties.");

        }

    }

    /**
     * Create the singleton instance of the factory using the supplied configuration.
     * @param configInstance
     * @throws ToolkitException
     */
    public TranslatorConfigurationFactory(TranslatorConfiguration configInstance) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            this.sharedConfigInstance = configInstance;

        } else {

            throw new ToolkitException("Attempt to re-initialize shared singleton translator configuration with " +
                "a new TranslatorConfiguration.");

        }

    }

    /**
     * Get the static TranslatorConfiguration object. If this is not already set (e.g. by Spring) or a prior
     * call to this method, this calls {@link #getUniqueConfiguration()} to construct it.
     * @return the {@link TranslatorConfiguration} object
     * @throws ToolkitException
     */
    public static synchronized TranslatorConfiguration getConfiguration() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the configInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getUniqueConfiguration();

        }

        return sharedConfigInstance;

    }

    /**
     * Calls populateProperties and then getConfiguration(Properties) to construct a new TranslatorConfiguration object.
     *
     * @return
     * @throws ToolkitException
     */
    public static TranslatorConfiguration getUniqueConfiguration() throws ToolkitException {

        Properties properties = populateProperties(serverContextProperties);

        TranslatorConfiguration config = getConfiguration(properties);

        return config;

    }

    /**
     * Construct a TranslatorConfiguration object using the provided properties. The name of the class to use
     * is the value of the key "TranslatorConfigurationClass" in the properties; if that is not specified the
     * default TranslatorConfiguration is used.
     *
     * @param properties the properties containing any overrides to default values
     * @return the new TranslatorConfiguration object
     * @throws ToolkitException
     */
    public static TranslatorConfiguration getConfiguration(Properties properties) throws ToolkitException {

        TranslatorConfiguration config;

        String configClassName = properties.getProperty(TranslatorConfiguration.TRANSLATOR_CONFIG_CLASS_NAME_KEY,
            TranslatorConfiguration.TRANSLATOR_CONFIG_CLASS_NAME_DEFAULT);
        if ( configClassName != null ) {

            config = (TranslatorConfiguration)constructConfiguration(configClassName, properties);

        } else {

            throw new ToolkitException(TranslatorConfiguration.TRANSLATOR_CONFIG_CLASS_NAME_KEY
                + " property was set to null");

        }

        return config;

    }

    /**
     * Called by TranslatorConfigurationFactoryInitializingContextListener to pass web server context properties
     * into the factory.
     * @param properties
     */
    public static synchronized void setServerContextProperties(Properties properties) {

        LOG.debug("Setting serverContextProperties to " + properties);
        serverContextProperties = properties;

    }

    public static synchronized void setSharedConfigInstance(TranslatorConfiguration configInstance)
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
