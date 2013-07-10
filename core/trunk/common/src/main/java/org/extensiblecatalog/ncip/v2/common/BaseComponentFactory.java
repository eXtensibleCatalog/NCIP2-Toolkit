/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.commons.lang.reflect.ConstructorUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public abstract class BaseComponentFactory<COMPONENT extends ToolkitComponent, CONFIG extends ToolkitConfiguration>
        implements ToolkitComponentFactory {

    private static final Logger LOG = Logger.getLogger(BaseComponentFactory.class);

    // TODO: Revise Javadoc to match the behavior
    /**
     * A template method to get a <COMPONENT> object. All sub-classes should call this so that all components are
     * created similarly, thus simplifying documentation and usage.
     * <p/>
     * Note: Overriding implementations of this method should <i>not</i> call back to the
     * {@link #getComponent} method or this could result in an infinite loop.
     * <p/>
     * Get the shared <COMPONENT> object. In order try the following:
     * 1) Call {@link ConfigurationHelper#getComponent(String, String, String, String)} and if non-null return that.
     * 2) Call {@link ConfigurationHelper#getComponentFactory(String, String, String, String)} and if non-null, check
     * (to avoid infinite recursion) whether it is an instance of the same class as <code>componentFactory</code> and
     * if so call {@link #buildComponent(ToolkitConfiguration)} on it; otherwise call {@link #getComponent()} on it.
     * 3) Call {@link ConfigurationHelper#getComponentConfiguration(String, String, String, String)} and if non-null
     * pass that to {@link #buildComponent(ToolkitConfiguration)} and return the result.
     * 4) Call {@link ConfigurationHelper#getComponentConfigurationFactory(String, String, String, String)} and if
     * non-null get the {@link ToolkitConfiguration} object from that and pass that to
     * {@link #buildComponent(ToolkitConfiguration)} and return the result.
     * 5) Call {@link ConfigurationHelper#getCoreConfiguration(String, Properties, String)} to get a {@link CoreConfiguration},
     * get the component's configuration from that {@link CoreConfiguration} object, pass that to
     * {@link #buildComponent(ToolkitConfiguration)} and return its result.
     *
     * @return the <COMPONENT> object
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     *
     */
    public static <F extends ToolkitComponentFactory> ToolkitComponent buildComponent(
            Class<F> factoryClass, String appName, String componentName,
            String componentSpringConfigFilenameKey, String componentSpringConfigFilenameDefault,
            Properties properties,
            String propertiesFilenameKey, String propertiesFilenameDefault,
            String localPropertiesFilenameKey, String localPropertiesFilenameDefault,
            String configPropertyOverrideKey, String configPropertyOverrideDefault) throws ToolkitException {

        Properties mergedProperties = ConfigurationHelper.populateProperties(appName, properties,
                propertiesFilenameKey, propertiesFilenameDefault,
                localPropertiesFilenameKey, localPropertiesFilenameDefault,
                configPropertyOverrideKey, configPropertyOverrideDefault);

        String componentSpringConfigFilename = mergedProperties.getProperty(componentSpringConfigFilenameKey,
                componentSpringConfigFilenameDefault);
        String coreSpringConfigFilename = mergedProperties.getProperty(CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY,
                CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT);

        ToolkitComponent component = ConfigurationHelper.getComponent(appName, componentName,
                    componentSpringConfigFilename, coreSpringConfigFilename);

            if (component == null) {

                ToolkitComponentFactory componentFactory
                        = ConfigurationHelper.getComponentFactory(appName, componentName,
                        componentSpringConfigFilename, coreSpringConfigFilename);

                if (componentFactory != null) {

                    // Protect against a recursive call, which will produce an infinite loop
                    if (componentFactory.getClass().isInstance(factoryClass) ) {

                        throw new ToolkitException("Recursive call to component factory '"
                                + componentFactory.getClass().getName() + "'.");

                    } else {

                        // Otherwise, call the componentFactory from the AppContext; it's their responsibility not
                        // to call back to this implementation of the method.
                        component = componentFactory.getComponent();

                    }

                }
            }

            if (component == null) {

                ToolkitConfiguration toolkitConfig = ConfigurationHelper.getComponentConfiguration(appName,
                        componentName, componentSpringConfigFilename, coreSpringConfigFilename);

                if (toolkitConfig != null) {

                    component = buildComponent(toolkitConfig);

                }

            }

            if (component == null) {

                ToolkitComponentConfigurationFactory configFactory =
                        ConfigurationHelper.getComponentConfigurationFactory(appName, componentName,
                                componentSpringConfigFilename, coreSpringConfigFilename);

                if (configFactory != null) {

                    component = buildComponent(configFactory.getConfiguration());

                }

            }

            if (component == null) {

                CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration(appName, mergedProperties,
                        coreSpringConfigFilename);
                if (coreConfig != null) {

                    component = buildComponent(coreConfig.getConfiguration(componentName));

                }

            }

            if (component == null) {

                throw new ToolkitException("Unable to initialize " + componentName + ".");

            }

        return component;

    }

    /**
     * Construct a <COMPONENT> using the provided configuration.
     *
     * @param configuration the <CONFIG>
     * @return the new <COMPONENT> object
     * @throws ToolkitException
     */
    @SuppressWarnings(value={"unchecked"}) // Because Constructor.newInstance returns Object.
    protected static ToolkitComponent buildComponent(ToolkitConfiguration configuration) throws ToolkitException {

        ToolkitComponent component;

        String componentClassName = configuration.getComponentClassName();
        if (componentClassName != null) {

            try {

                Class<? extends ToolkitComponent> componentClass
                        = Class.forName(componentClassName).asSubclass(ToolkitComponent.class);

                Constructor<? extends ToolkitComponent> ctor
                        = ConstructorUtils.getMatchingAccessibleConstructor(componentClass,
                        new Class[] { configuration.getClass() });

                if ( ctor != null ) {

                    component = ctor.newInstance(configuration);

                } else {

                    throw new ToolkitException("Exception constructing " + componentClassName +
                            " class: No matching constructor found for '" + configuration.getClass().getName() + "'.");

                }

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("Exception loading " + componentClassName + " class.", e);

            } catch (InstantiationException e) {

                throw new ToolkitException("Exception constructing " + componentClassName + " class.", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("Exception constructing " + componentClassName + " class.", e);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("Exception constructing " + componentClassName + " class.", e);

            }

        } else {

            throw new ToolkitException("Component class name not set in component configuration.");

        }

        return component;

    }

}
