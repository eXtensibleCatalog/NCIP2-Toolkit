/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public abstract class BaseConfigurationFactory implements ToolkitConfigurationFactory {

    private static final Logger LOG = Logger.getLogger(BaseConfigurationFactory.class);

    /**
     * Populate a {@link java.util.Properties} object from various configuration files and system properties.
     * The approach used is derived from that described for a Spring context here:
     * http://springtips.blogspot.com/2008/09/configuring-applications-with-spring.html. Specifically the following
     * are used as sources of configuration properties, in the order given (such that subsequent sources
     * replace values in earlier sources);
     * <li>
     * Properties file 'toolkit.properties' loaded from the classpath first, or the filesystem if not found on the
     * classpath.
     * </li>
     * <li>
     * Properties file '${Toolkit.AppName}toolkit.properties' loaded from the classpath first, or the filesystem if not found
     * on the classpath.
     * </li>
     * <li>
     * Properties file located by the system (i.e., JVM) environment variable toolkitConfig.
     * </li>
     * <li>
     * Context parameters from web.xml or context attributes specified in application server meta-data (e.g. a Tomcat
     * context.xml). If the application is not running in a web server that supports ServletContextListener, this
     * step will have no effect.
     * <li>
     * Property values specified as a system or environment property; what keys are used depends on the particular
     * MessageHandlerConfiguration class that is used.
     * </li>
     * @param serverContextProperties the properties from the server context
     * @return the Properties object
     */
    protected static Properties populateProperties(Properties serverContextProperties) {

        Properties properties = new Properties();
        String appName = serverContextProperties.getProperty(ToolkitConfiguration.TOOLKIT_APP_NAME_KEY,
                ToolkitConfiguration.TOOLKIT_APP_NAME_DEFAULT);

        // Load from toolkit.properties
        String toolkitPropertiesFileName
                = serverContextProperties.containsKey(ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_KEY)
                ? serverContextProperties.getProperty(ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_KEY)
                : System.getProperty(ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_KEY,
                    ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_DEFAULT);

        setPropertiesFromClasspathOrFilesystem(properties, toolkitPropertiesFileName);

        // Load from ${Toolkit.AppName}toolkit.properties
        String localToolkitPropertiesFileName
                = serverContextProperties.containsKey(ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY)
                ? serverContextProperties.getProperty(ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY)
                : System.getProperty(ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY,
                    ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_DEFAULT);

        localToolkitPropertiesFileName = localToolkitPropertiesFileName.replaceAll("\\$\\{Toolkit.AppName\\}", appName);

        setPropertiesFromClasspathOrFilesystem(properties, localToolkitPropertiesFileName);

        // Load from file/resource named by system/environment property toolkitConfig.
        String toolkitConfigFileName
                = serverContextProperties.containsKey(ToolkitConfiguration.TOOLKIT_CONFIG_PROPERTY)
                ? serverContextProperties.getProperty(ToolkitConfiguration.TOOLKIT_CONFIG_PROPERTY)
                : System.getProperty(ToolkitConfiguration.TOOLKIT_CONFIG_PROPERTY);
        if ( toolkitConfigFileName != null ) {

            setPropertiesFromClasspathOrFilesystem(properties, toolkitConfigFileName);

        }

        // Load from core.properties
        String corePropertiesFileName
                = serverContextProperties.containsKey(CoreConfiguration.CORE_PROPERTIES_FILENAME_KEY)
                ? serverContextProperties.getProperty(CoreConfiguration.CORE_PROPERTIES_FILENAME_KEY)
                : System.getProperty(CoreConfiguration.CORE_PROPERTIES_FILENAME_KEY,
                    CoreConfiguration.CORE_PROPERTIES_FILENAME_DEFAULT);

        setPropertiesFromClasspathOrFilesystem(properties, corePropertiesFileName);

        // Load from ${Toolkit.AppName}core.properties
        String localCorePropertiesFileName
                = serverContextProperties.containsKey(CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_KEY)
                ? serverContextProperties.getProperty(CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_KEY)
                : System.getProperty(CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_KEY,
                    CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_DEFAULT);

        localCorePropertiesFileName = localCorePropertiesFileName.replaceAll("\\$\\{Toolkit.AppName\\}", appName);

        setPropertiesFromClasspathOrFilesystem(properties, localCorePropertiesFileName);

        // Load from file/resource named by system/environment property coreConfig.
        String coreConfigFileName
                = serverContextProperties.containsKey(CoreConfiguration.CORE_CONFIG_PROPERTY)
                ? serverContextProperties.getProperty(CoreConfiguration.CORE_CONFIG_PROPERTY)
                : System.getProperty(CoreConfiguration.CORE_CONFIG_PROPERTY);
        if ( coreConfigFileName != null ) {

            setPropertiesFromClasspathOrFilesystem(properties, coreConfigFileName);

        }

        /**
         * Add all properties from web.xml or context attributes.
         * This approach follows that suggested here:
         * http://stackoverflow.com/questions/41659/is-there-a-way-to-access-web-xml-properties-from-a-java-bean
         */
        if ( serverContextProperties != null ) {

            properties.putAll(serverContextProperties);

        }

        // Load from system/environment properties
        setPropertiesFromSystemProperties(properties);

        return properties;

    }

    /**
     * Add all properties from System properties.
     * @param properties the Properties into which to load the System properties
     */
    public static void setPropertiesFromSystemProperties(Properties properties) {

        properties.putAll(System.getProperties());

    }

    /**
     * Add all properties from a properties file named by propertiesFileName and read from the classpath or,
     * if not found on the classpath, from the filesystem. If the file is not found a message will be written
     * to the log at debug level; if there is an IOException loading the properties a message will be written
     * to the log at warn level.
     * @param properties the {@link Properties} into which the properties from the file will be loaded.
     * @param propertiesFileName the filename for the properties file
     */
    public static void setPropertiesFromClasspathOrFilesystem(Properties properties, String propertiesFileName) {

        LOG.debug("Trying to load " + propertiesFileName);

        InputStream inputStream
            = ConfigurationHelper.class.getClassLoader().getResourceAsStream(propertiesFileName);

        if (inputStream == null) {

            try {

                inputStream = new FileInputStream(propertiesFileName);

            } catch (FileNotFoundException e) {

                LOG.debug("FileNotFoundException loading properties from file '" + propertiesFileName + "'.", e);

            }

        }

        if ( inputStream != null ) {

            try {

                properties.load(inputStream);
                if ( LOG.isDebugEnabled() ) {

                    ToolkitHelper.dumpProperties(LOG, "Properties after loading " + propertiesFileName, properties);
                }

            } catch (IOException e) {

                LOG.warn("IOException loading properties from file '" + propertiesFileName + "'.", e);

            }

        }

    }

    protected static ToolkitConfiguration constructConfiguration(String configClassName, Properties properties)
        throws ToolkitException {

        ToolkitConfiguration config;

        try {

            Class<?> configClass = Class.forName(configClassName);

            Constructor ctor = configClass.getConstructor(Properties.class);

            config = (ToolkitConfiguration)ctor.newInstance(properties);

        } catch (ClassNotFoundException e) {

            throw new ToolkitException("Exception loading configuration class.", e);

        } catch (InstantiationException e) {

            throw new ToolkitException("Exception constructing configuration class.", e);

        } catch (IllegalAccessException e) {

            throw new ToolkitException("Exception constructing configuration class.", e);

        } catch (NoSuchMethodException e) {

            throw new ToolkitException("Exception constructing configuration class.", e);

        } catch (InvocationTargetException e) {

            throw new ToolkitException("Exception constructing configuration class.", e);

        }

        return config;

    }
}
