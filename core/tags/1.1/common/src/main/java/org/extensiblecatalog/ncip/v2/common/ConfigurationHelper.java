/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Note: If the Toolkit is running in a Servlet context, the static attribute {@link #appContext} must be initialized
 * before any other methods on this class are used otherwise it will look for a configuration file in the filesystem,
 * which is not likely to be the desired behavior (e.g. you will have two different objects that are meant to
 * be singletons, one from the Servlet context's Spring configuration and one from the file system's Spring
 * configuration). The recommended way to do this when using Spring is via the {@link ToolkitAppContext} class,
 * e.g. put this before all other Toolkit-related beans in the Spring configuration file:
 * <br/><code>
 *     &lt;bean name="ToolkitAppContext" class="org.extensiblecatalog.ncip.v2.common.ToolkitAppContext"
 *             scope="singleton"/&gt;
 * </code>
 */
public class ConfigurationHelper {

    private static final Logger LOG = Logger.getLogger(ConfigurationHelper.class);

    protected static ApplicationContext appContext;

    /**
     * Set by ToolkitAppContext when running in a Spring container.
     * @param applicationContext
     * @throws BeansException
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        appContext = applicationContext;

    }

    /**
     * Returns the ApplicationContext if running in a Spring container, otherwise null.
     * @return
     */
    // TODO: Test what happens if run w/o spring jars on the classpath - will the new FileSystemXmlApplicationContext() call fail?
    protected synchronized static ApplicationContext getApplicationContext() {

        // Check to see if the appContext was already initialized through some external means (e.g.
        // if we're running in a Servlet context).
        if ( appContext == null ) {

            String appConfigFilename = System.getProperty(CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY,
                CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT);

            if ( appConfigFilename != null && ! appConfigFilename.isEmpty() ) {

                try {

                    appContext = new FileSystemXmlApplicationContext(appConfigFilename);
                    LOG.debug("Loaded application context from '" + appConfigFilename + "'.");

                } catch (BeansException e) {

                    LOG.debug("No application context at '" + appConfigFilename + "'.");

                }

            } else {

                LOG.debug("No default Spring configuration filename.");

            }

        }
        return appContext;

    }

    public static CoreConfiguration getCoreConfiguration()
        throws ToolkitException {

        CoreConfiguration coreConfiguration = null;

        ApplicationContext appContext = getApplicationContext();

        if ( appContext != null ) {

            if ( appContext.containsBean("coreConfiguration") ) {

                coreConfiguration = (CoreConfiguration)appContext.getBean("coreConfiguration");

            } else if ( appContext.containsBean("coreConfigurationFactory") ) {

                CoreConfigurationFactory coreConfigFactory
                    = (CoreConfigurationFactory)appContext.getBean("coreConfigurationFactory");

                coreConfiguration = coreConfigFactory.getConfiguration();

            }


        }

        if ( coreConfiguration == null ) {

            coreConfiguration = CoreConfigurationFactory.getConfiguration();
            
        }

        return coreConfiguration;

    }

}
