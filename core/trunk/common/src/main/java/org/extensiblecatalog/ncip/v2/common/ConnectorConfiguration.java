/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface ConnectorConfiguration extends ToolkitConfiguration {

    final String COMPONENT_NAME = "Connector";

    final String CONNECTOR_PROPERTIES_FILE_TITLE_KEY = "ConnectorConfiguration.PropertiesFileTitle";
    final String CONNECTOR_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String CONNECTOR_LOCAL_PROPERTIES_FILE_TITLE_KEY = "ConnectorConfiguration.LocalPropertiesFileTitle";
    final String CONNECTOR_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String CONNECTOR_CONFIG_CLASS_NAME_KEY = "ConnectorConfiguration.ConfigClass";
    final String CONNECTOR_CONFIG_CLASS_NAME_DEFAULT = DefaultConnectorConfiguration.class.getName();

    final String CONNECTOR_PROPERTIES_FILENAME_KEY = "ConnectorConfiguration.PropertiesFile";
    final String CONNECTOR_PROPERTIES_FILENAME_DEFAULT = "connector.properties";

    final String CONNECTOR_LOCAL_PROPERTIES_FILENAME_KEY = "ConnectorConfiguration.LocalPropertiesFile";
    final String CONNECTOR_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}connector.properties";

    final String CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "ConnectorConfiguration.PropertiesFileOverride";
    final String CONNECTOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String CONNECTOR_CONFIG_FILE_NAME_KEY = "ConnectorConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@value #CONNECTOR_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\connectorconfig.xml".
    final String CONNECTOR_CONFIG_FILE_NAME_DEFAULT = null;

    final String CONNECTOR_CLASS_NAME_KEY = "ConnectorConfiguration.ClassName";
    // The default connector class is in the dummy package, which isn't a dependency of the common package
    // that this class is in, so we have to hard-code the class name here (despite the risks of breakage).
    final String CONNECTOR_CLASS_NAME_DEFAULT = "org.extensiblecatalog.ncip.v2.dummy.DummyRemoteServiceManager";

}
