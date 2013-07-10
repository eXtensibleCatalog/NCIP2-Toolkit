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

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// TODO: In an appropriate place explain the effect of appName, incl. if it's null, and properties.
/**
 * Note: If the Toolkit is running in a Servlet context, the static attribute {@link #globalAppContext} must be
 * initialized before any other methods on this class are used otherwise it will look for a configuration file in the
 * filesystem, which is not likely to be the desired behavior (e.g. you will have two different objects that are meant
 * to be singletons, one from the Servlet context's Spring configuration and one from the file system's Spring
 * configuration). The recommended way to do this when using Spring is via the {@link ToolkitAppContext} class,
 * e.g. put this before all other Toolkit-related beans in the Spring configuration file:
 * <br/><code>
 * &lt;bean name="ToolkitAppContext" class="org.extensiblecatalog.ncip.v2.common.ToolkitAppContext"
 * scope="singleton"/&gt;
 * </code>
 */
public class ConfigurationHelper {

    private static final Logger LOG = Logger.getLogger(ConfigurationHelper.class);

    protected static final String FACTORY_BEAN_SUFFIX = "Factory";
    protected static final String CONFIGURATION_BEAN_SUFFIX = "Configuration";
    protected static final String CONFIGURATION_FACTORY_BEAN_SUFFIX = "ConfigurationFactory";
    protected static final String CORE_COMPONENT_NAME = "Core";

    /**
     * The application-wide Spring application context.
     * Note: Using type of {@link Object} so as to allow this class to be called when Spring libraries aren't on the
     * classpath.
     */
    protected static Object globalAppContext;

    /**
     * This holds the app contexts keyed by AppName + ":" + ComponentName.
     * If the AppName is  null it represents the 'global' application context for that component.
     */
    protected static Map<String, Object> applicationContextMap = new HashMap<String, Object>();

    protected static Map<String, Properties> applicationPropertiesMap = new HashMap<String, Properties>();

    /**
     * Set the global {@link org.springframework.context.ApplicationContext}, i.e. that context shared by <i>all</i>
     * components in this container
     * This is set by {@link ToolkitAppContext} when running in a Spring container; in other environments this will
     * need to be called by the application.
     *
     * @param globalApplicationContext the global {@link org.springframework.context.ApplicationContext}
     */
    public static void setApplicationContext(Object globalApplicationContext) {

        globalAppContext = globalApplicationContext;

    }

    private synchronized static Object getApplicationContext(
            String appName, String componentName, String componentConfigFilename, String coreConfigFilename) {

        Object appContext;
        appContext = doApplicationContextLoad(appName, componentName, componentConfigFilename);

        if (appContext == null) {

            appContext = doApplicationContextLoad(appName, CORE_COMPONENT_NAME, coreConfigFilename);

            if (appContext == null && appName != null) { // Try the 'global', i.e. non-app-specific, context

                appContext = doApplicationContextLoad(null, componentName, componentConfigFilename);

                if (appContext == null) {

                    appContext = doApplicationContextLoad(null, CORE_COMPONENT_NAME, coreConfigFilename);

                }
            }
        }

        return appContext;

    }

    private synchronized static Object doApplicationContextLoad(String appName, String componentName,
                                                                String configFilename) {

        Object appContext;

        String key = appName + ":" + componentName;
        if (!applicationContextMap.containsKey(key)) {

            LOG.debug("AppContext for '" + key + "' not loaded; loading now...");
            appContext = getApplicationContext(configFilename);

            applicationContextMap.put(key, appContext);
            LOG.debug("AppContext '" + appContext + "' for '" + key + "' now loaded.");

        } else {

            // TO DO: test that a null value for a key brings us here; then remove this else-branch
            LOG.debug("AppContext for '" + key + "' already loaded.");

        }

        appContext = applicationContextMap.get(key);

        return appContext;

    }

    private synchronized static Object getApplicationContext(String componentConfigFilename) {

        Object appContext = null;
        if (componentConfigFilename != null && !componentConfigFilename.isEmpty()) {

            try {

                appContext = new org.springframework.context.support.ClassPathXmlApplicationContext(
                        componentConfigFilename);
                LOG.debug("Loaded application context from '" + componentConfigFilename + "'.");

            } catch (RuntimeException e) {

                LOG.debug("No application context at '" + componentConfigFilename + "'. Runtime exception: ", e);

            }

        } else {

            LOG.debug("No default Spring configuration filename.");

        }

        return appContext;

    }

    /**
     * Returns the ApplicationContext if available, otherwise null.
     *
     * @return the {@link org.springframework.context.ApplicationContext}
     */
    private synchronized static Object getGlobalApplicationContext() {

        // Note: The globalAppContext might be initialized through some external means (e.g.
        // if we're running in a Servlet context).
        if (globalAppContext == null) {

            globalAppContext = getApplicationContext(System.getProperty(CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY,
                    CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT));

        }

        return globalAppContext;

    }

    /**
     * Convenience method to call {@link #getCoreConfiguration(String, Properties, String)} with default values.
     *
     * @return the {@link CoreConfiguration}
     * @throws ToolkitException if there is an error
     */
    public static CoreConfiguration getCoreConfiguration()
            throws ToolkitException {

        return getCoreConfiguration(null, System.getProperties(), System.getProperty(
                CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY, CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT));

    }

    public static CoreConfiguration getCoreConfiguration(String appName, Properties properties,
                                                         String coreConfigFilename)
            throws ToolkitException {

        CoreConfiguration coreConfiguration = (CoreConfiguration) getComponentConfiguration(appName,
                CORE_COMPONENT_NAME, null, coreConfigFilename);

        if (coreConfiguration == null) {

            CoreConfigurationFactory coreConfigFactory
                    = (CoreConfigurationFactory) getComponentConfigurationFactory(appName, CORE_COMPONENT_NAME,
                    null, coreConfigFilename);

            if (coreConfigFactory != null) {

                coreConfiguration = coreConfigFactory.buildConfiguration(appName);

            }

        }

        if (coreConfiguration == null) {

            coreConfiguration = CoreConfigurationFactory.buildConfiguration(appName, properties);

        }

        return coreConfiguration;

    }

    /**
     * Lower-cases the first letter of the componentName to make the bean name.
     *
     * @param componentName the name of the component (e.g. {@link MessageHandler#COMPONENT_NAME}.
     * @return the name of the component's bean, e.g. "messageHandler".
     */
    protected static String makeBeanName(String componentName) {

        return componentName.substring(0, 1).toLowerCase() + componentName.substring(1);

    }


    @SuppressWarnings(value={"unchecked"}) // Because BeanFactory.getBean returns Object.
    public static <COMPONENT extends ToolkitComponent> COMPONENT getComponent(
            String appName, String componentName, String componentConfigFilename, String coreConfigFilename) {

        COMPONENT component = null;

        String beanName = makeBeanName(componentName);

        LOG.debug("Looking for bean with name '" + beanName + "' in Spring Application context for appName '" + appName
                + "' and componentName '" + componentName + "', using componentConfigFilename of '"
                + componentConfigFilename + "' and coreConfigFilename of '" + coreConfigFilename + "'.");

        org.springframework.context.ApplicationContext theAppContext
                = (org.springframework.context.ApplicationContext) getApplicationContext(appName, componentName,
                componentConfigFilename, coreConfigFilename);

        if (theAppContext != null) {

            if (theAppContext.containsBean(beanName)) {

                component = (COMPONENT)theAppContext.getBean(beanName);
                LOG.debug("Found " + component);

            } else {

                LOG.debug("Bean '" + beanName + "' not found.");

            }

        } else {

            LOG.debug("Application Context for appName '" + appName
                    + "' and componentName '" + componentName + "', using componentConfigFilename of '"
                    + componentConfigFilename + "' and coreConfigFilename of '" + coreConfigFilename + "' not found.");

        }

        if ( component == null ) {

            LOG.debug("Looking for bean with name '" + beanName + "' in global Spring Application context.");

            org.springframework.context.ApplicationContext globalAppContext =
                    (org.springframework.context.ApplicationContext) getGlobalApplicationContext();

            if (globalAppContext != null) {

                if ( globalAppContext.containsBean(beanName) ) {

                    component = (COMPONENT)globalAppContext.getBean(beanName);
                    LOG.debug("Found " + component);

                } else {

                    LOG.debug("Bean '" + beanName + "' not found.");

                }

            } else {

                LOG.debug("No global Application Context.");

            }

        }

        return component;

    }

    @SuppressWarnings(value={"unchecked"}) // Because BeanFactory.getBean returns Object.
    public static <COMPONENT_FACTORY extends ToolkitComponentFactory> COMPONENT_FACTORY getComponentFactory(
            String appName, String componentName, String componentConfigFilename, String coreConfigFilename) {

        COMPONENT_FACTORY component = null;

        String beanName = makeBeanName(componentName) + FACTORY_BEAN_SUFFIX;

        org.springframework.context.ApplicationContext theAppContext
                = (org.springframework.context.ApplicationContext) getApplicationContext(appName, componentName,
                componentConfigFilename, coreConfigFilename);
        if (theAppContext != null && theAppContext.containsBean(beanName)) {

            component = (COMPONENT_FACTORY)theAppContext.getBean(beanName);

        }

        return component;

    }

    @SuppressWarnings(value={"unchecked"}) // Because BeanFactory.getBean returns Object.
    public static <CONFIG extends ToolkitConfiguration> CONFIG getComponentConfiguration(
            String appName, String componentName, String componentConfigFilename, String coreConfigFilename) {

        CONFIG componentConfiguration = null;

        String beanName = makeBeanName(componentName) + CONFIGURATION_BEAN_SUFFIX;

        org.springframework.context.ApplicationContext theAppContext
                = (org.springframework.context.ApplicationContext) getApplicationContext(appName, componentName,
                componentConfigFilename, coreConfigFilename);
        if (theAppContext != null && theAppContext.containsBean(beanName)) {

            componentConfiguration = (CONFIG)theAppContext.getBean(beanName);

        }

        return componentConfiguration;

    }

    @SuppressWarnings(value={"unchecked"}) // Because BeanFactory.getBean returns Object.
    public static <COMPONENT_CONFIG_FACTORY extends ToolkitComponentConfigurationFactory> COMPONENT_CONFIG_FACTORY
    getComponentConfigurationFactory(String appName, String componentName,
                                     String componentConfigFilename, String coreConfigFilename) {

        COMPONENT_CONFIG_FACTORY componentConfigurationFactory = null;

        String beanName = makeBeanName(componentName) + CONFIGURATION_FACTORY_BEAN_SUFFIX;

        org.springframework.context.ApplicationContext theAppContext
                = (org.springframework.context.ApplicationContext) getApplicationContext(appName, componentName,
                componentConfigFilename, coreConfigFilename);
        if (theAppContext != null && theAppContext.containsBean(beanName)) {

            componentConfigurationFactory = (COMPONENT_CONFIG_FACTORY)theAppContext.getBean(beanName);

        }

        return componentConfigurationFactory;

    }

    /**
     * Set the server context properties for the provided appName.
     * @param appName the name of the application (e.g. webapp) to be associated with these properties
     * @param props the {@link Properties}
     */
    public static void setServerContextProperties(String appName, Properties props) {

        applicationPropertiesMap.put(appName, props);

    }

    public static Properties getServerContextProperties(String appName) {
        return applicationPropertiesMap.get(appName);
    }

    // TODO: Review Javadoc for accuracy
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
     * Properties file '${ToolkitConfiguration.AppName}toolkit.properties' loaded from the classpath first, or the
     * filesystem if not found on the classpath.
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
     * @param overrideProperties properties that should override those loaded from the environment (e.g. Spring
     * Application Context, web.xml, and properties files)
     * @return the Properties object
     */
    protected static Properties populateProperties(
            String appName, Properties overrideProperties,
            String propertiesFilenameKey, String propertiesFilenameDefault,
            String localPropertiesFilenameKey, String localPropertiesFilenameDefault,
            String configPropertyOverrideKey, String configPropertyOverrideDefault) {

        Properties properties = System.getProperties();

        // Load from toolkit.properties
        ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, System.getProperty(
            ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_KEY,
            ToolkitConfiguration.TOOLKIT_PROPERTIES_FILENAME_DEFAULT));

        // Load from ${ToolkitConfiguration.AppName}toolkit.properties
        ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, System.getProperty(
            ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_KEY,
            ToolkitConfiguration.TOOLKIT_LOCAL_PROPERTIES_FILENAME_DEFAULT));

        // Load from file/resource named by system/environment property toolkitConfig.
        String propertiesFileName = System.getProperty(ToolkitConfiguration.TOOLKIT_CONFIG_PROPERTY_OVERRIDE_KEY,
            ToolkitConfiguration.TOOLKIT_CONFIG_PROPERTY_OVERRIDE_DEFAULT);
        if ( propertiesFileName != null ) {

            ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, propertiesFileName);

        }

        // Load from <component-name>.properties
        ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, System.getProperty(
            propertiesFilenameKey, propertiesFilenameDefault));

        // Load from ${ToolkitConfiguration.AppName}<component-name>.properties
        ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, System.getProperty(
            localPropertiesFilenameKey, localPropertiesFilenameDefault));

        // Load from file/resource named by system/environment property
        // <component-name>Configuration.PropertiesFileOverride.
        propertiesFileName = System.getProperty(configPropertyOverrideKey, configPropertyOverrideDefault);
        if ( propertiesFileName != null ) {

            ToolkitHelper.setPropertiesFromClasspathOrFilesystem(properties, propertiesFileName);

        }

        /**
         * Add all properties from web.xml or context attributes.
         * This approach follows that suggested here:
         * http://stackoverflow.com/questions/41659/is-there-a-way-to-access-web-xml-properties-from-a-java-bean
         */
        Properties serverContextProperties = ConfigurationHelper.getServerContextProperties(appName);
        if ( serverContextProperties != null ) {

            properties.putAll(serverContextProperties);

        }

        // Load override properties last
        if ( overrideProperties != null ) {

            properties.putAll(overrideProperties);

        }

        if ( LOG.isDebugEnabled() ) {

            LOG.debug("Properties files loaded:");
            for ( Map.Entry<Object, Object> entry : properties.entrySet() ) {

                if ( ((String)entry.getKey()).contains("FileTitle") ) {

                    LOG.debug(entry.getKey() + "=" + entry.getValue());

                }
            }
        }

        return properties;

    }

    public static String getAppName(ServletContext servletContext) {

        String appName;

        if ( servletContext.getContextPath().startsWith("/") ) {

            appName = servletContext.getContextPath().substring(1); // Strip off the leading "/".

        } else {

            appName = servletContext.getContextPath();
        }

        return appName;

    }

}
