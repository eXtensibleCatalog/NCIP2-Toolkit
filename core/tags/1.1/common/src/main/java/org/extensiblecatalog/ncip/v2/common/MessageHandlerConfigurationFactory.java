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
 * Instantiates MessageHandlerConfiguration objects.
 */
public class MessageHandlerConfigurationFactory extends BaseConfigurationFactory {

    private static final Logger LOG = Logger.getLogger(MessageHandlerConfigurationFactory.class);

    protected static Properties serverContextProperties = new Properties();

    /**
     * This is the instance returned to all callers of getConfiguration(). If you want multiple, differing
     * configurations you will need to call {@link #getUniqueConfiguration()} or {@link #getConfiguration(java.util.Properties)}.
     */
    protected static MessageHandlerConfiguration sharedConfigInstance;

    /**
     * Create the singleton instance of the factory using the supplied properties.
     * @param properties
     * @throws ToolkitException
     */
    public MessageHandlerConfigurationFactory(Properties properties) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getConfiguration(properties);

        } else {

            throw new ToolkitException("Attempt to re-initialize shared singleton core configuration from Properties.");

        }

    }

    /**
     * Create the singleton instance of the factory using the supplied configuration.
     * @param configInstance
     * @throws ToolkitException
     */
    public MessageHandlerConfigurationFactory(MessageHandlerConfiguration configInstance) throws ToolkitException {

        if ( sharedConfigInstance == null ) {

            this.sharedConfigInstance = configInstance;

        } else {

            throw new ToolkitException("Attempt to re-initialize shared singleton core configuration with a new " +
                "ConnectorConfiguration.");

        }


    }

    /**
     * Get the static MessageHandlerConfiguration object. If this is not already set (e.g. by Spring) or a prior
     * call to this method, this calls {@link #getUniqueConfiguration()} to construct it.
     * @return the {@link org.extensiblecatalog.ncip.v2.common.MessageHandlerConfiguration} object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static synchronized MessageHandlerConfiguration getConfiguration() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the configInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedConfigInstance == null ) {

            sharedConfigInstance = getUniqueConfiguration();

        }

        return sharedConfigInstance;

    }

    /**
     * Calls populateProperties and then getConfiguration(Properties) to construct a new MessageHandlerConfiguration object.
     *
     * @return
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static MessageHandlerConfiguration getUniqueConfiguration() throws ToolkitException {

        Properties properties = populateProperties(serverContextProperties);

        MessageHandlerConfiguration config = getConfiguration(properties);

        return config;

    }

    /**
     * Construct a MessageHandlerConfiguration object using the provided properties. The name of the class to use
     * is the value of the key "MessageHandlerConfigurationClass" in the properties; if that is not specified the
     * default MessageHandlerConfigurationImpl is used.
     *
     * @param properties the properties containing any overrides to default values
     * @return the new MessageHandlerConfiguration object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public static MessageHandlerConfiguration getConfiguration(Properties properties) throws ToolkitException {

        MessageHandlerConfiguration config;

        String configClassName = properties.getProperty(
            MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_CLASS_NAME_KEY,
            MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_CLASS_NAME_DEFAULT);
        if ( configClassName != null ) {

            config = (MessageHandlerConfiguration)constructConfiguration(configClassName, properties);

        } else {

            throw new ToolkitException(MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_CLASS_NAME_KEY
                + " property was set to null");

        }

        return config;

    }

    /**
     * Called by MessageHandlerConfigurationFactoryInitializingContextListener to pass web server context properties
     * into the factory.
     * @param properties
     */
    public static synchronized void setServerContextProperties(Properties properties) {

        LOG.debug("Setting serverContextProperties to " + properties);
        serverContextProperties = properties;

    }

    public static synchronized void setSharedConfigInstance(MessageHandlerConfiguration configInstance) throws ToolkitException {

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
