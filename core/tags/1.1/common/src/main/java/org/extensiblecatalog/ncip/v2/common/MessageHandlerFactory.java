/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.MessageHandler;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class MessageHandlerFactory {

    /**
     * This is the instance returned to all callers of getMessageHandler(). If you want multiple, differing
     * messageHandlers you will need to call {@link #getMessageHandler()} or
     * {@link #getMessageHandler(MessageHandlerConfiguration)}.
     */
    protected static MessageHandler sharedInstance;

    protected final MessageHandlerConfiguration configuration;

    protected final Properties properties;

    /**
     * Construct a MessageHandlerFactory that can be used to construct distinct {@link MessageHandler} objects using the
     * same construction sequence used by {@link #getSharedMessageHandler()}.
     * @throws ToolkitException
     */
    public MessageHandlerFactory() throws ToolkitException {

        configuration = null;
        properties = null;

    }

    /**
     * Construct a MessageHandlerFactory that will return {@link MessageHandler} objects constructed with the passed-in
     * {@link MessageHandlerConfiguration}.
     *
     * @param configuration the {@link MessageHandlerConfiguration} to use to construct {@link MessageHandler} instances.
     *
     */
    public MessageHandlerFactory(MessageHandlerConfiguration configuration) throws ToolkitException {

        this.configuration = configuration;
        this.properties = null;

    }

    /**
     * Construct a MessageHandlerFactory that will return {@link MessageHandler} objects constructed with the passed-in
     * {@link Properties}.
     *
     * @param properties the {@link Properties} to use to construct {@link MessageHandler} instances.
     *
     */
    public MessageHandlerFactory(Properties properties) throws ToolkitException {

        this.configuration = null;
        this.properties = properties;

    }

    /**
     * Get the shared MessageHandler object. In order try the following:
     * 1) If the {@link #sharedInstance} is already set (e.g. by Spring) or a prior call to this method,
     * return that.
     * 2) Look for a "messageHandler" bean in the AppContext and return that.
     * 3) Look for a "messageHandlerFactory" bean in the AppContext and (to avoid infinite recursion) if it is an instance
     * of this class, call the {@link #getMessageHandler()} method on that object; otherwise call
     * {@link #getSharedMessageHandler()} method on that object.
     * 4) Look for a "messageHandlerConfigurationFactory" bean in the AppContext and get the {@link MessageHandlerConfiguration}
     * object from that and pass that to {@link #getMessageHandler(MessageHandlerConfiguration)} method and return the result.
     * 5) Call {@link ConfigurationHelper#getCoreConfiguration()} method to get a {@link CoreConfiguration},
     * call its {@link CoreConfiguration#getMessageHandlerConfiguration()} method, pass the result to
     * {@link #getMessageHandler(MessageHandlerConfiguration)} and return that result.
     * this calls {@link #getMessageHandler()} to construct it.
     * @return the {@link MessageHandler} object
     * @throws ToolkitException
     */
    public static synchronized MessageHandler getSharedMessageHandler() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the sharedInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedInstance == null ) {

            ApplicationContext appContext = ConfigurationHelper.getApplicationContext();

            if ( appContext != null ) {

                if ( appContext.containsBean("messageHandler") ) {

                  sharedInstance = (MessageHandler)appContext.getBean("messageHandler");

                } else if ( appContext.containsBean("messageHandlerFactory") ) {

                    MessageHandlerFactory messageHandlerFactory
                        = (MessageHandlerFactory)appContext.getBean("messageHandlerFactory");

                    // Protect against a recursive call, which will produce an infinite loop
                    if ( messageHandlerFactory.getClass() == MessageHandlerFactory.class ) {

                        // If the AppContext has this class as the factory, call getMessageHandler,
                        // and *if* that class was constructed with a configuration or properties, it won't call back
                        // to this method. If it was, this will result in an infinite loop.
                        sharedInstance = messageHandlerFactory.getNonSharedMessageHandler();


                    } else {

                        // Otherwise, call the MessageHandlerFactory in the AppContext; it's their responsibility not
                        // to call this method
                        sharedInstance = messageHandlerFactory.getSharedMessageHandler();

                    }

                } else if ( appContext.containsBean("messageHandlerConfigurationFactory") ) {

                    MessageHandlerConfigurationFactory messageHandlerConfigFactory
                        = (MessageHandlerConfigurationFactory)appContext.getBean("messageHandlerConfigurationFactory");

                    sharedInstance = getMessageHandler(messageHandlerConfigFactory.getConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration();
                if ( coreConfig != null ) {

                    sharedInstance = getMessageHandler(coreConfig.getMessageHandlerConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                throw new ToolkitException("Unable to initialize message handler.");

            }

        }

        return sharedInstance;

    }

    /**
     *
     * @return
     * @throws ToolkitException
     */
    public MessageHandler getMessageHandler() throws ToolkitException {

        MessageHandler messageHandler = getNonSharedMessageHandler();

        if ( messageHandler == null ) {

            messageHandler = getSharedMessageHandler();

        }

        return messageHandler;

    }

    /**
     * Used by {@link #getMessageHandler()} to get a MessageHandler from either the {@link #configuration} or the
     * {@link #properties}, and by {@link #getSharedMessageHandler()} when it's retrieved an instance of itself from the
     * AppContext and so needs to avoid calling back to {@link #getSharedMessageHandler()} because that would cause an
     * infinite loop.
     *
     * @return
     * @throws ToolkitException
     */
    private MessageHandler getNonSharedMessageHandler() throws ToolkitException {

        MessageHandler messageHandler = null;
        if ( configuration != null ) {

            messageHandler = getMessageHandler(configuration);

        } else if ( properties != null ) {

            messageHandler = getMessageHandler(properties);

        }

        return messageHandler;

    }

    /**
     * Construct a MessageHandler using the provided configuration.
     *
     * @param configuration the {@link MessageHandlerConfiguration} containing any overrides to default values
     * @return the new MessageHandler object
     * @throws ToolkitException
     */
    public static MessageHandler getMessageHandler(MessageHandlerConfiguration configuration) throws ToolkitException {

        MessageHandler messageHandler;

        // See if we have a MessageHandler class name in the configuration, if so instantiate that with the configuration
        String messageHandlerClassName = configuration.getMessageHandlerClassName();
        if ( messageHandlerClassName != null ) {

            try {

                Class<?> configClass = Class.forName(messageHandlerClassName);

                Constructor ctor = configClass.getConstructor(MessageHandlerConfiguration.class);

                messageHandler = (MessageHandler)ctor.newInstance(configuration);

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("Exception loading message handler class.", e);

            } catch (InstantiationException e) {

                throw new ToolkitException("Exception constructing message handler class.", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("Exception constructing message handler class.", e);

            } catch (NoSuchMethodException e) {

                throw new ToolkitException("Exception constructing message handler class.", e);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("Exception constructing message handler class.", e);

            }

        } else {

            throw new ToolkitException("MessageHandler class name not set in message handler configuration.");

        }

        return messageHandler;

    }

    /**
     * Construct a MessageHandler using the provided properties to obtain a MessageHandlerConfiguration object from the
     * MessageHandlerConfigurationFactory and then calling {@link #getMessageHandler(MessageHandlerConfiguration)} with it.
     *
     * @param properties the {@link Properties} containing any overrides to default values
     * @return the new MessageHandler object
     * @throws ToolkitException
     */
    public static MessageHandler getMessageHandler(Properties properties) throws ToolkitException {

        MessageHandlerConfiguration configuration = MessageHandlerConfigurationFactory.getConfiguration(properties);

        return getMessageHandler(configuration);

    }

}
