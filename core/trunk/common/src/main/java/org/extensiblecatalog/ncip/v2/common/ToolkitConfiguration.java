/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.Properties;

public interface ToolkitConfiguration {

    final String TOOLKIT_CONFIG_PROPERTY_OVERRIDE_KEY = "ToolkitConfiguration.PropertiesFileOverride";
    final String TOOLKIT_CONFIG_PROPERTY_OVERRIDE_DEFAULT = null;
    
    final String TOOLKIT_PROPERTIES_FILENAME_KEY = "ToolkitConfiguration.PropertiesFile";
    final String TOOLKIT_PROPERTIES_FILENAME_DEFAULT = "toolkit.properties";

    final String TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY = "ToolkitConfiguration.LocalPropertiesFile";
    final String TOOLKIT_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}toolkit.properties";

    final String TOOLKIT_PROPERTIES_FILE_TITLE_KEY = "ToolkitConfiguration.PropertiesFileTitle";
    final String TOOLKIT_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String TOOLKIT_LOCAL_PROPERTIES_FILE_TITLE_KEY = "ToolkitConfiguration.LocalPropertiesFileTitle";
    final String TOOLKIT_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String TOOLKIT_CONFIG_FILE_NAME_KEY = "ToolkitConfiguration.SpringConfigFile";
    /** There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
     * webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
     * {@value #TOOLKIT_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
     * "resources\toolkitconfig.xml".
     */
    final String TOOLKIT_CONFIG_FILE_NAME_DEFAULT = null;

    /**
     * The Toolkit application name. See {@ToolkitServletContextListener} for how this is used in a web server
     * environment. The default in a command-line environment is "local".
     * If using Spring this property is overridden by the
     * {@link ToolkitServletContextListener} to be the web app's
     * context path (which by default is the name of the war file) so that if you rename the war file (e.g. to
     * ncipv2beta.war) the ToolkitConfiguration.AppName property will *default* to ncipv2beta.
     * This default value can be overridden by setting the ToolkitConfiguration.AppName property explicitly, e.g. on the JVM
     * command-line.
     * If not using Spring (or if you remove the ToolkitServletContextListener from the Spring configuration file)
     * then this property will determine the application name.
     */
    final String TOOLKIT_APP_NAME_KEY = "ToolkitConfiguration.AppName";
    final String TOOLKIT_APP_NAME_DEFAULT = "local";

    Properties getProperties();
    public String getProperty(String key);
    public String getProperty(String key, String defaultValue);

    public String getAppName();
    public void setAppName(String appName);

    public String getComponentClassName();
    public void setComponentClassName(String componentClassName);

}
