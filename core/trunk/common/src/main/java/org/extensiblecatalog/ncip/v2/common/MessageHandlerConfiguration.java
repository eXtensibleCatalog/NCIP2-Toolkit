/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface MessageHandlerConfiguration extends ToolkitConfiguration {

    final String MESSAGE_HANDLER_PROPERTIES_FILE_TITLE_KEY = "MessageHandlerConfiguration.PropertiesFileTitle";
    final String MESSAGE_HANDLER_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String MESSAGE_HANDLER_LOCAL_PROPERTIES_FILE_TITLE_KEY = "MessageHandlerConfiguration.LocalPropertiesFileTitle";
    final String MESSAGE_HANDLER_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String MESSAGE_HANDLER_CONFIG_CLASS_NAME_KEY = "MessageHandlerConfiguration.ConfigClass";
    final String MESSAGE_HANDLER_CONFIG_CLASS_NAME_DEFAULT = DefaultMessageHandlerConfiguration.class.getName();

    final String MESSAGE_HANDLER_PROPERTIES_FILENAME_KEY = "MessageHandlerConfiguration.PropertiesFile";
    final String MESSAGE_HANDLER_PROPERTIES_FILENAME_DEFAULT = "messagehandler.properties";

    final String MESSAGE_HANDLER_LOCAL_PROPERTIES_FILENAME_KEY = "MessageHandlerConfiguration.LocalPropertiesFile";
    final String MESSAGE_HANDLER_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}messagehandler.properties";

    final String MESSAGE_HANDLER_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "MessageHandlerConfiguration.PropertiesFileOverride";
    final String MESSAGE_HANDLER_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String MESSAGE_HANDLER_CONFIG_FILE_NAME_KEY = "MessageHandlerConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #MESSAGE_HANDLER_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\messagehandlerconfig.xml".
    final String MESSAGE_HANDLER_CONFIG_FILE_NAME_DEFAULT = null;

    final String MESSAGE_HANDLER_CLASS_NAME_KEY = "MessageHandlerConfiguration.ClassName";
    final String MESSAGE_HANDLER_CLASS_NAME_DEFAULT = MappedMessageHandler.class.getName();

}
