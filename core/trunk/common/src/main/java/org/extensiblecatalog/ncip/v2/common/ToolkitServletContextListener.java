/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ToolkitServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        // Configure log4j from the Toolkit's logging config file (if set).
        // (Don't use the default here - we need to know whether the property was overridden.)
        String loggingConfigFilename = System.getProperty(CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_KEY);
        if ( loggingConfigFilename != null ) {

            System.out.println("Setting logging config filename to " + loggingConfigFilename
                + " (from override of " + CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_KEY + " property).");

        } else {

            String realPath = servletContextEvent.getServletContext().getRealPath("/");
            if ( realPath != null ) {

                loggingConfigFilename = realPath + CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_DEFAULT;
                System.out.println("Setting logging config filename to " + loggingConfigFilename
                    + ", derived from real path.");

            } else {

                // realPath can be null if, for example, the webapp is run from an un-exploded war.
                System.out.println("Real path could not be determined - looking for in-war logging filename property.");
                loggingConfigFilename = System.getProperty(CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_IN_WAR_KEY);
                if ( loggingConfigFilename != null ) {

                    System.out.println("Setting logging config filename to " + loggingConfigFilename
                        + " (from override of " + CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_IN_WAR_KEY
                        + " property).");

                } else {

                    loggingConfigFilename = CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_IN_WAR_DEFAULT;
                    System.out.println("Setting logging config filename to " + loggingConfigFilename
                        + ", the default (as real path could not be determined).");

                }

            }

        }

        String appName = ConfigurationHelper.getAppName(servletContextEvent.getServletContext());

        // Initialize logging
        Properties loggingProps = new Properties();
        ToolkitHelper.setPropertiesFromClasspathOrFilesystem(loggingProps, loggingConfigFilename);
        setAppName(loggingProps, appName);
        setLoggingDir(loggingProps, servletContextEvent.getServletContext());
        PropertyConfigurator.configure(loggingProps);

        // Initialize Toolkit components
        Properties configProperties = getContextParameters(servletContextEvent.getServletContext());
        setAppName(configProperties, appName);
        ConfigurationHelper.setServerContextProperties(appName, configProperties);

    }

    /**
     * If the the appName property ({@link CoreConfiguration#TOOLKIT_APP_NAME_KEY}) is not already set,
     * set it to appName in the supplied {@link Properties} object.
     * @param props the {@link Properties} object to set the appName property in
     * @param appName the app name
     */
    protected void setAppName(Properties props, String appName) {

        String overrideAppName = System.getProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY);
        if ( overrideAppName != null ) {

            System.out.println(CoreConfiguration.TOOLKIT_APP_NAME_KEY + " property overridden with '"
                + overrideAppName + "'.");

        } else {

            props.setProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY, appName);
            System.out.println("Setting " + CoreConfiguration.TOOLKIT_APP_NAME_KEY + " property to "
                + props.getProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY) + ".");

        }

    }

    protected void setLoggingDir(Properties props, ServletContext servletContext) {

        // Don't use the default here - we need to know whether the property was overridden.
        String loggingDirName = System.getProperty(CoreConfiguration.CORE_LOGGING_DIR_KEY);
        if ( loggingDirName != null ) {

            System.out.println(CoreConfiguration.CORE_LOGGING_DIR_KEY + " property overridden with '"
                + loggingDirName + "'.");

        } else {

            loggingDirName = CoreConfiguration.CORE_LOGGING_DIR_DEFAULT;

            String serverInfo = servletContext.getServerInfo();

            if ( serverInfo.matches("(?s)(?i).*Tomcat.*") ) {

                loggingDirName = CoreConfiguration.CORE_TOMCAT_LOGGING_DIR_DEFAULT;

            } else if ( serverInfo.matches("(?s)(?i).*Jetty.*") ) {

                loggingDirName = CoreConfiguration.CORE_JETTY_LOGGING_DIR_DEFAULT;

            }

            props.setProperty(CoreConfiguration.CORE_LOGGING_DIR_KEY, loggingDirName);
            System.out.println("Setting " + CoreConfiguration.CORE_LOGGING_DIR_KEY + " property to "
                + props.getProperty(CoreConfiguration.CORE_LOGGING_DIR_KEY)
                + ", based on servlet container type.");

        }

    }

    protected Properties getContextParameters(ServletContext servletContext) {

        Properties properties = new Properties();
        Enumeration parameterNames = servletContext.getInitParameterNames();
        while ( parameterNames.hasMoreElements() ) {

            String paramName = (String)parameterNames.nextElement();
            String parameter = servletContext.getInitParameter(paramName);
            if ( parameter != null && ! parameter.isEmpty() ) {
                System.out.println("Setting " + paramName + " to " + parameter);
                properties.put(paramName, parameter);
            }

        }

        return properties;

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Do nothing
    }
}
