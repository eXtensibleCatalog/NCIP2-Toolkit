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

            System.out.println(CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_DEFAULT
                + " property overridden with '" + loggingConfigFilename + "'.");

        } else {

            String realPath = servletContextEvent.getServletContext().getRealPath("/");
            loggingConfigFilename = realPath + CoreConfiguration.CORE_LOGGING_CONFIG_FILE_NAME_DEFAULT;
            System.out.println("Setting logging config filename to " + loggingConfigFilename
                + ", derived from real path.");

        }

        Properties loggingProps = new Properties();
        try {

            FileInputStream istream = new FileInputStream(loggingConfigFilename);
            loggingProps.load(istream);
            istream.close();

            setAppName(loggingProps, servletContextEvent.getServletContext());
            setLoggingDir(loggingProps, servletContextEvent.getServletContext());

            PropertyConfigurator.configure(loggingProps);

        } catch (IOException e) {

            System.err.println("ToolkitServletContextListener failed to modify Log4j properties due to this exception:");
            e.printStackTrace(System.err);
            System.err.println("Default Log4j initialization will be performed.");

        }

        Properties configProperties = getContextParameters(servletContextEvent.getServletContext());
        setAppName(configProperties, servletContextEvent.getServletContext());
        CoreConfigurationFactory.setServerContextProperties(configProperties);
        ConnectorConfigurationFactory.setServerContextProperties(configProperties);
        MessageHandlerConfigurationFactory.setServerContextProperties(configProperties);
        ServiceValidatorConfigurationFactory.setServerContextProperties(configProperties);
        StatisticsBeanConfigurationFactory.setServerContextProperties(configProperties);
        TranslatorConfigurationFactory.setServerContextProperties(configProperties);

    }

    protected void setAppName(Properties props, ServletContext servletContext) {

        // Don't use the default here - we need to know whether the property was overridden.
        String appName = System.getProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY);
        if ( appName != null ) {

            System.out.println(CoreConfiguration.TOOLKIT_APP_NAME_KEY + " property overridden with '"
                + appName + "'.");

        } else {

            String contextPath = servletContext.getContextPath();
            if ( contextPath.startsWith("/") ) {

                contextPath = contextPath.substring(1); // Strip off the leading "/".

            }
            props.setProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY, contextPath);
            System.out.println("Setting " + CoreConfiguration.TOOLKIT_APP_NAME_KEY + " property to "
                + props.getProperty(CoreConfiguration.TOOLKIT_APP_NAME_KEY) + ", derived from context path.");

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

                loggingDirName = CoreConfiguration.CORE_TOMCAT_LOGGING_DIR;

            } else if ( serverInfo.matches("(?s)(?i).*Jetty.*") ) {

                loggingDirName = CoreConfiguration.CORE_JETTY_LOGGING_DIR;

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
