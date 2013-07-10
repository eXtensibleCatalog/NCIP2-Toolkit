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

    final String TOOLKIT_CONFIG_PROPERTY = "toolkitConfig";
    
    /**
     * The Toolkit application name. See {@ToolkitServletContextListener} for how this is used in a web server
     * environment. The default in a command-line environment is "local".
     * If using Spring this property is overridden by the
     * {@link org.extensiblecatalog.ncip.v2.common.ToolkitServletContextListener} to be the web app's
     * context path (which by default is the name of the war file) so that if you rename the war file (e.g. to
     * ncipv2beta.war) the Toolkit.AppName property will *default* to ncipv2beta.
     * This default value can be overridden by setting the Toolkit.AppName property explicitly, e.g. on the JVM
     * command-line.
     * If not using Spring (or if you remove the ToolkitServletContextListener from the Spring configuration file)
     * then this property will determine the application name.
     */
    final String TOOLKIT_APP_NAME_KEY = "Toolkit.AppName";
    final String TOOLKIT_APP_NAME_DEFAULT = "local";

    final String TOOLKIT_PROPERTIES_FILENAME_KEY = "ToolkitConfiguration.PropertiesFile";
    final String TOOLKIT_PROPERTIES_FILENAME_DEFAULT = "toolkit.properties";

    final String TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY = "ToolkitConfiguration.LocalPropertiesFile";
    final String TOOLKIT_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${Toolkit.AppName}toolkit.properties";

    final String TOOLKIT_PROPERTIES_FILE_TITLE_KEY = "ToolkitConfiguration.PropertiesFileTitle";
    final String TOOLKIT_PROPERTIES_FILE_TITLE_DEFAULT = "(Untitled)";

    final String TOOLKIT_LOCAL_PROPERTIES_FILE_TITLE_KEY = "ToolkitConfiguration.LocalPropertiesFileTitle";
    final String TOOLKIT_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = "(Untitled)";

    Properties getProperties();
    public Object getProperty(Object key);

}
