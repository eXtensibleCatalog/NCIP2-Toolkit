/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.Properties;

public interface MessageHandlerConfiguration extends ToolkitConfiguration {

    final String MESSAGE_HANDLER_CONFIG_CLASS_NAME_KEY = "MessageHandlerConfiguration.ConfigClass";
    final String MESSAGE_HANDLER_CONFIG_CLASS_NAME_DEFAULT = DefaultMessageHandlerConfigurationImpl.class.getName();

    final String MESSAGE_HANDLER_CLASS_NAME_KEY = "MessageHandlerConfiguration.ClassName";
    final String MESSAGE_HANDLER_CLASS_NAME_DEFAULT = MappedMessageHandler.class.getName();

    final String MESSAGE_HANDLER_CONFIG_FILE_NAME_KEY = "TranslatorConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #MESSAGE_HANDLER_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\toolkitconfig.xml".
    final String MESSAGE_HANDLER_CONFIG_FILE_NAME_DEFAULT = null;

    public String getMessageHandlerClassName();

}
