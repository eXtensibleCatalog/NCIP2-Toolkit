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

public class MessageHandlerFactory extends BaseComponentFactory<MessageHandler, MessageHandlerConfiguration> {

    private static final Logger LOG = Logger.getLogger(MessageHandlerFactory.class);

    /**
     * The instance of {@link MessageHandler} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected MessageHandler messageHandler;

    /**
     * Create an instance of the factory without initializing the {@link #messageHandler} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public MessageHandlerFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link MessageHandler}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public MessageHandlerFactory(Properties properties) throws ToolkitException {

        this.messageHandler = buildMessageHandler(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link MessageHandler} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public MessageHandlerFactory(String appName) throws ToolkitException {

        this.messageHandler = buildMessageHandler(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link MessageHandler} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public MessageHandlerFactory(String appName, Properties properties) throws ToolkitException {

        this.messageHandler = buildMessageHandler(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link MessageHandler} built with the supplied
     * {@link MessageHandlerConfiguration}.
     * @param configuration an instance of {@link MessageHandlerConfiguration}
     */
    public MessageHandlerFactory(MessageHandlerConfiguration configuration) throws ToolkitException {

        this.messageHandler = (MessageHandler) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #messageHandler} object.
     * @return the {@link #messageHandler}
     */
    @Override
    public MessageHandler getComponent() {

        return messageHandler;

    }

    /**
     * Convenience method which calls {@link #buildMessageHandler(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link MessageHandler}
     * @throws ToolkitException
     */
    public static MessageHandler buildMessageHandler() throws ToolkitException {

        return buildMessageHandler(null, null);

    }

    /**
     * Convenience method which calls {@link #buildMessageHandler(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link MessageHandler}
     * @throws ToolkitException
     */
    public static MessageHandler buildMessageHandler(Properties properties) throws ToolkitException {

        return buildMessageHandler(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildMessageHandler(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link MessageHandler}
     * @throws ToolkitException
     */
    public static MessageHandler buildMessageHandler(String appName) throws ToolkitException {

        return buildMessageHandler(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link MessageHandlerConfiguration}
     * @return the {@link MessageHandler}
     * @throws ToolkitException
     */
    public static MessageHandler buildMessageHandler(MessageHandlerConfiguration configuration) throws ToolkitException {

        return (MessageHandler) buildComponent(configuration);

    }

    /**
     * Construct a MessageHandler object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static MessageHandler buildMessageHandler(String appName, Properties properties) throws ToolkitException {

        MessageHandler mh;

        mh = (MessageHandler)buildComponent(MessageHandlerFactory.class, appName, MessageHandler.COMPONENT_NAME,
                MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_FILE_NAME_KEY,
                MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_FILE_NAME_DEFAULT,
                properties,
                MessageHandlerConfiguration.MESSAGE_HANDLER_PROPERTIES_FILENAME_KEY,
                MessageHandlerConfiguration.MESSAGE_HANDLER_PROPERTIES_FILENAME_DEFAULT,
                MessageHandlerConfiguration.MESSAGE_HANDLER_LOCAL_PROPERTIES_FILENAME_KEY,
                MessageHandlerConfiguration.MESSAGE_HANDLER_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                MessageHandlerConfiguration.MESSAGE_HANDLER_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return mh;

    }
}
