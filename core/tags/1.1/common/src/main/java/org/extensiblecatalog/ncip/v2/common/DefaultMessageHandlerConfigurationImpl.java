/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.SchemeLoader;

import java.util.Properties;

public class DefaultMessageHandlerConfigurationImpl implements MessageHandlerConfiguration {

	  private static final Logger LOG = Logger.getLogger(DefaultMessageHandlerConfigurationImpl.class);

    protected Properties properties;

    protected String messageHandlerClassName = MessageHandlerConfiguration.MESSAGE_HANDLER_CLASS_NAME_DEFAULT;

    public DefaultMessageHandlerConfigurationImpl() {

        // Do nothing

    }

    public DefaultMessageHandlerConfigurationImpl(Properties properties) {

        this.properties = properties;

        String messageHandlerClassNameString = null;

        if ( properties != null ) {

            messageHandlerClassNameString = this.properties.getProperty(MessageHandlerConfiguration.MESSAGE_HANDLER_CLASS_NAME_KEY);

        }

        if ( messageHandlerClassNameString != null ) {

            this.messageHandlerClassName = messageHandlerClassNameString;

        }

    }

    public String getMessageHandlerClassName() {

        return messageHandlerClassName;

    }

    public void setMessageHandlerClassName(String className) {

        this.messageHandlerClassName = className;

    }

    @Override
    public Properties getProperties() {

        return properties;

    }

    @Override
    public Object getProperty(Object key) {

        return properties.get(key);

    }

}
