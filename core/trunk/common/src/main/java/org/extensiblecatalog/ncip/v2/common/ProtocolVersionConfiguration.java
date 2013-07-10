/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface ProtocolVersionConfiguration extends ToolkitConfiguration {

    final String PROTOCOL_VERSION_PROPERTIES_FILE_TITLE_KEY = "ProtocolVersionConfiguration.PropertiesFileTitle";
    final String PROTOCOL_VERSION_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String PROTOCOL_VERSION_LOCAL_PROPERTIES_FILE_TITLE_KEY = "ProtocolVersionConfiguration.LocalPropertiesFileTitle";
    final String PROTOCOL_VERSION_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String PROTOCOL_VERSION_CONFIG_CLASS_NAME_KEY = "ProtocolVersionConfiguration.ConfigClass";
    final String PROTOCOL_VERSION_CONFIG_CLASS_NAME_DEFAULT = DefaultNCIPProtocolVersionConfiguration.class.getName();

    final String PROTOCOL_VERSION_PROPERTIES_FILENAME_KEY = "ProtocolVersionConfiguration.PropertiesFile";
    final String PROTOCOL_VERSION_PROPERTIES_FILENAME_DEFAULT = "protocolversion.properties";

    final String PROTOCOL_VERSION_LOCAL_PROPERTIES_FILENAME_KEY = "ProtocolVersionConfiguration.LocalPropertiesFile";
    final String PROTOCOL_VERSION_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}protocolversion.properties";

    final String PROTOCOL_VERSION_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "ProtocolVersionConfiguration.PropertiesFileOverride";
    final String PROTOCOL_VERSION_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String PROTOCOL_VERSION_CONFIG_FILE_NAME_KEY = "ProtocolVersionConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #PROTOCOL_VERSION_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\protocolversionconfig.xml".
    final String PROTOCOL_VERSION_CONFIG_FILE_NAME_DEFAULT = null;

    final String PROTOCOL_VERSION_CLASS_NAME_KEY = "ProtocolVersionConfiguration.ClassName";
    final String PROTOCOL_VERSION_CLASS_NAME_DEFAULT = DefaultNCIPVersion.class.getName();

}
