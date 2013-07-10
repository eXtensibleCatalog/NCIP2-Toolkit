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

public class DefaultMessageHandlerConfiguration extends BaseToolkitConfiguration implements MessageHandlerConfiguration {

	  private static final Logger LOG = Logger.getLogger(DefaultMessageHandlerConfiguration.class);

    protected String messageHandlerClassName = MessageHandlerConfiguration.MESSAGE_HANDLER_CLASS_NAME_DEFAULT;

    /**
     * Create an instance of the configuration without initializing the properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public DefaultMessageHandlerConfiguration() throws ToolkitException {

        // Do nothing

    }

    public DefaultMessageHandlerConfiguration(String appName) throws ToolkitException {

        this(appName, null);

    }

    public DefaultMessageHandlerConfiguration(Properties properties) throws ToolkitException {

        this(null, properties);

    }

    public DefaultMessageHandlerConfiguration(String appName, Properties properties) {

        super(appName, properties);

        String messageHandlerClassNameString = null;

        if ( properties != null ) {

            messageHandlerClassNameString = this.properties.getProperty(MessageHandlerConfiguration.MESSAGE_HANDLER_CLASS_NAME_KEY);

        }

        if ( messageHandlerClassNameString != null ) {

            this.messageHandlerClassName = messageHandlerClassNameString;

        }

    }

    @Override
    public String getComponentClassName() {
        return messageHandlerClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.messageHandlerClassName = componentClassName;
    }
}
