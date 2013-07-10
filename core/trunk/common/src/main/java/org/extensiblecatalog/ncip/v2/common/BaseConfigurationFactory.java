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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

public abstract class BaseConfigurationFactory<CONFIG extends ToolkitConfiguration>
        implements ToolkitComponentConfigurationFactory {

    private static final Logger LOG = Logger.getLogger(BaseConfigurationFactory.class);

    // TODO: Revise Javadoc to match the behavior
    /**
     * Construct a sub-class of {@link ToolkitConfiguration} from the provided {@link Properties}.
     * @param properties the properties to use to set attributes of the {@link ToolkitConfiguration} object
     * @return the sub-class of {@link ToolkitConfiguration}
     * @throws ToolkitException if an Exception is thrown during construction
     */
    // Kind of a sub-set of what BaseComponentFactory does, i.e. only the config object part.
    protected static ToolkitConfiguration buildConfiguration(
            String appName, String componentName,
            String componentSpringConfigFilenameKey, String componentSpringConfigFilenameDefault,
            Properties properties, String configClassNameKey, String configClassNameDefault,
            String propertiesFilenameKey, String propertiesFilenameDefault,
            String localPropertiesFilenameKey, String localPropertiesFilenameDefault,
            String configPropertyOverrideKey, String configPropertyOverrideDefault)
        throws ToolkitException {

        Properties mergedProperties = ConfigurationHelper.populateProperties(appName, properties,
                propertiesFilenameKey, propertiesFilenameDefault,
                localPropertiesFilenameKey, localPropertiesFilenameDefault,
                configPropertyOverrideKey, configPropertyOverrideDefault);

        String componentSpringConfigFilename = mergedProperties.getProperty(componentSpringConfigFilenameKey,
                componentSpringConfigFilenameDefault);
        String coreSpringConfigFilename = mergedProperties.getProperty(CoreConfiguration.CORE_CONFIG_FILE_NAME_KEY,
                CoreConfiguration.CORE_CONFIG_FILE_NAME_DEFAULT);

        ToolkitConfiguration toolkitConfig = ConfigurationHelper.getComponentConfiguration(appName,
                componentName, componentSpringConfigFilename, coreSpringConfigFilename);

        if ( toolkitConfig == null ) {

            ToolkitComponentConfigurationFactory configFactory =
                    ConfigurationHelper.getComponentConfigurationFactory(appName, componentName,
                            componentSpringConfigFilename, coreSpringConfigFilename);

            if (configFactory != null) {

                toolkitConfig = configFactory.getConfiguration();

            }
        }

        if ( toolkitConfig == null && componentName.compareTo(Core.COMPONENT_NAME) != 0 ) {

            CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration(appName, properties,
                    coreSpringConfigFilename);

            if (coreConfig != null && coreConfig.isConfigurationSet(componentName)) {

                toolkitConfig = coreConfig.getConfiguration(componentName);

            }

        }

        if ( toolkitConfig == null ) {

            String configClassName = mergedProperties.getProperty(configClassNameKey, configClassNameDefault);

            if ( configClassName != null ) {

                try {

                    Class<?> configClass = Class.forName(configClassName);

                    Constructor ctor = configClass.getConstructor(Properties.class);

                    toolkitConfig = (ToolkitConfiguration)ctor.newInstance(mergedProperties);

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

            } else {

                throw new ToolkitException(configClassNameKey + " property was set to null");

            }

        }

        return toolkitConfig;

    }

}
