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

public class ServiceValidatorFactory extends BaseComponentFactory<ServiceValidator, ServiceValidatorConfiguration> {

    private static final Logger LOG = Logger.getLogger(ServiceValidatorFactory.class);

    /**
     * The instance of {@link ServiceValidator} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected ServiceValidator serviceValidator;

    /**
     * Create an instance of the factory without initializing the {@link #serviceValidator} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public ServiceValidatorFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link ServiceValidator}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ServiceValidatorFactory(Properties properties) throws ToolkitException {

        this.serviceValidator = buildServiceValidator(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link ServiceValidator} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public ServiceValidatorFactory(String appName) throws ToolkitException {

        this.serviceValidator = buildServiceValidator(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link ServiceValidator} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public ServiceValidatorFactory(String appName, Properties properties) throws ToolkitException {

        this.serviceValidator = buildServiceValidator(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link ServiceValidator} built with the supplied
     * {@link ServiceValidatorConfiguration}.
     * @param configuration an instance of {@link ServiceValidatorConfiguration}
     */
    public ServiceValidatorFactory(ServiceValidatorConfiguration configuration) throws ToolkitException {

        this.serviceValidator = (ServiceValidator) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #serviceValidator} object.
     * @return the {@link #serviceValidator}
     */
    @Override
    public ServiceValidator getComponent() {

        return serviceValidator;

    }

    /**
     * Convenience method which calls {@link #buildServiceValidator(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link ServiceValidator}
     * @throws ToolkitException
     */
    public static ServiceValidator buildServiceValidator() throws ToolkitException {

        return buildServiceValidator(null, null);

    }

    /**
     * Convenience method which calls {@link #buildServiceValidator(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link ServiceValidator}
     * @throws ToolkitException
     */
    public static ServiceValidator buildServiceValidator(Properties properties) throws ToolkitException {

        return buildServiceValidator(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildServiceValidator(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link ServiceValidator}
     * @throws ToolkitException
     */
    public static ServiceValidator buildServiceValidator(String appName) throws ToolkitException {

        return buildServiceValidator(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link ServiceValidatorConfiguration}
     * @return the {@link ServiceValidator}
     * @throws ToolkitException
     */
    public static ServiceValidator buildServiceValidator(ServiceValidatorConfiguration configuration) throws ToolkitException {

        return (ServiceValidator) buildComponent(configuration);

    }

    /**
     * Construct a ServiceValidator object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static ServiceValidator buildServiceValidator(String appName, Properties properties) throws ToolkitException {

        ServiceValidator sv;

        sv = (ServiceValidator)buildComponent(ServiceValidatorFactory.class, appName, ServiceValidator.COMPONENT_NAME,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CONFIG_FILE_NAME_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CONFIG_FILE_NAME_DEFAULT,
                properties,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_PROPERTIES_FILENAME_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_PROPERTIES_FILENAME_DEFAULT,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILENAME_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                ServiceValidatorConfiguration.SERVICE_VALIDATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return sv;

    }
}
