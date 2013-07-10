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

import java.util.Properties;

public class CoreFactory extends BaseComponentFactory<Core, CoreConfiguration> {

    private static final Logger LOG = Logger.getLogger(CoreFactory.class);

    /**
     * The instance of {@link Core} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected Core core;

    /**
     * Create an instance of the factory without initializing the {@link #core} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public CoreFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link Core}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public CoreFactory(Properties properties) throws ToolkitException {

        this.core = buildCore(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link Core} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public CoreFactory(String appName) throws ToolkitException {

        this.core = buildCore(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link Core} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public CoreFactory(String appName, Properties properties) throws ToolkitException {

        this.core = buildCore(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link Core} built with the supplied
     * {@link CoreConfiguration}.
     * @param configuration an instance of {@link CoreConfiguration}
     */
    public CoreFactory(CoreConfiguration configuration) throws ToolkitException {

        this.core = (Core) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #core} object.
     * @return the {@link #core}
     */
    @Override
    public Core getComponent() {

        return core;

    }

    /**
     * Convenience method which calls {@link #buildCore(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link Core}
     * @throws ToolkitException
     */
    public static Core buildCore() throws ToolkitException {

        return buildCore(null, null);

    }

    /**
     * Convenience method which calls {@link #buildCore(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link Core}
     * @throws ToolkitException
     */
    public static Core buildCore(Properties properties) throws ToolkitException {

        return buildCore(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildCore(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link Core}
     * @throws ToolkitException
     */
    public static Core buildCore(String appName) throws ToolkitException {

        return buildCore(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link CoreConfiguration}
     * @return the {@link Core}
     * @throws ToolkitException
     */
    public static Core buildCore(CoreConfiguration configuration) throws ToolkitException {

        return (Core) buildComponent(configuration);

    }

    /**
     * Construct a Core object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static Core buildCore(String appName, Properties properties) throws ToolkitException {

        Core c;

        c = (Core)buildComponent(CoreFactory.class, appName, Core.COMPONENT_NAME,
                CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY,
                CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT,
                properties,
                CoreConfiguration.CORE_PROPERTIES_FILENAME_KEY,
                CoreConfiguration.CORE_PROPERTIES_FILENAME_DEFAULT,
                CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_KEY,
                CoreConfiguration.CORE_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                CoreConfiguration.CORE_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                CoreConfiguration.CORE_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return c;

    }
}
