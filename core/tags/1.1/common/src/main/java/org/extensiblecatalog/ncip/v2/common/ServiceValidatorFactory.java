/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ServiceValidator;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ServiceValidatorFactory {

    /**
     * This is the instance returned to all callers of getServiceValidator(). If you want multiple, differing
     * serviceValidators you will need to call {@link #getServiceValidator()} or
     * {@link #getServiceValidator(ServiceValidatorConfiguration)}.
     */
    protected static ServiceValidator sharedInstance;

    protected final ServiceValidatorConfiguration configuration;

    protected final Properties properties;

    /**
     * Construct a ServiceValidatorFactory that can be used to construct distinct
     * {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} objects using the
     * same construction sequence used by {@link #getSharedServiceValidator()}.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public ServiceValidatorFactory() throws ToolkitException {

        configuration = null;
        properties = null;

    }

    /**
     * Construct a ServiceValidatorFactory that will return
     * {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} objects constructed with the passed-in
     * {@link ServiceValidatorConfiguration}.
     *
     * @param configuration the {@link ServiceValidatorConfiguration} to use to construct
     * {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} instances.
     *
     */
    public ServiceValidatorFactory(ServiceValidatorConfiguration configuration) throws ToolkitException {

        this.configuration = configuration;
        this.properties = null;

    }

    /**
     * Construct a ServiceValidatorFactory that will return
     * {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} objects constructed with the passed-in
     * {@link Properties}.
     *
     * @param properties the {@link Properties} to use to construct
     * {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} instances.
     *
     */
    public ServiceValidatorFactory(Properties properties) throws ToolkitException {

        this.configuration = null;
        this.properties = properties;

    }

    /**
     * Get the shared ServiceValidator object. In order try the following:
     * 1) If the {@link #sharedInstance} is already set (e.g. by Spring) or a prior call to this method,
     * return that.
     * 2) Look for a "serviceValidator" bean in the AppContext and return that.
     * 3) Look for a "serviceValidatorFactory" bean in the AppContext and (to avoid infinite recursion) if it is an instance
     * of this class, call the {@link #getServiceValidator()} method on that object; otherwise call
     * {@link #getSharedServiceValidator()} method on that object.
     * 4) Look for a "serviceValidatorConfigurationFactory" bean in the AppContext and get the {@link ServiceValidatorConfiguration}
     * object from that and pass that to {@link #getServiceValidator(ServiceValidatorConfiguration)} method and return the result.
     * 5) Call {@link ConfigurationHelper#getCoreConfiguration()} method to get a {@link CoreConfiguration},
     * call its {@link CoreConfiguration#getServiceContextConfiguration()} method, pass the result to
     * {@link #getServiceValidator(ServiceValidatorConfiguration)} and return that result.
     *
     * this calls {@link #getServiceValidator()} to construct it.
     * @return the {@link org.extensiblecatalog.ncip.v2.service.ServiceValidator} object
     * @throws ToolkitException
     */
    public static synchronized ServiceValidator getSharedServiceValidator() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the sharedInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedInstance == null ) {

            ApplicationContext appContext = ConfigurationHelper.getApplicationContext();

            if ( appContext != null ) {

                if ( appContext.containsBean("serviceValidator") ) {

                  sharedInstance = (ServiceValidator)appContext.getBean("serviceValidator");

                } else if ( appContext.containsBean("serviceValidatorFactory") ) {

                    ServiceValidatorFactory serviceValidatorFactory
                        = (ServiceValidatorFactory)appContext.getBean("serviceValidatorFactory");

                    // Protect against a recursive call, which will produce an infinite loop
                    if ( serviceValidatorFactory.getClass() == ServiceValidatorFactory.class ) {

                        // If the AppContext has this class as the factory, call getServiceValidator,
                        // and *if* that class was constructed with a configuration or properties, it won't call back
                        // to this method. If it was, this will result in an infinite loop.
                        sharedInstance = serviceValidatorFactory.getNonSharedServiceValidator();


                    } else {

                        // Otherwise, call the ServiceValidatorFactory in the AppContext; it's their responsibility not
                        // to call this method
                        sharedInstance = serviceValidatorFactory.getSharedServiceValidator();

                    }

                } else if ( appContext.containsBean("serviceValidatorConfigurationFactory") ) {

                    ServiceValidatorConfigurationFactory serviceContextConfigFactory
                        = (ServiceValidatorConfigurationFactory)appContext.getBean("serviceValidatorConfigurationFactory");

                    sharedInstance = getServiceValidator(serviceContextConfigFactory.getConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration();
                if ( coreConfig != null ) {

                    sharedInstance = getServiceValidator(coreConfig.getServiceContextConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                throw new ToolkitException("Unable to initialize service validator.");

            }

        }

        return sharedInstance;

    }

    /**
     *
     * @return
     * @throws ToolkitException
     */
    public ServiceValidator getServiceValidator() throws ToolkitException {

        ServiceValidator serviceValidator = getNonSharedServiceValidator();

        if ( serviceValidator == null ) {

            serviceValidator = getSharedServiceValidator();

        }

        return serviceValidator;

    }

    /**
     * Used by {@link #getServiceValidator()} to get a ServiceValidator from either the {@link #configuration} or the
     * {@link #properties}, and by {@link #getSharedServiceValidator()} when it's retrieved an instance of itself from the
     * AppContext and so needs to avoid calling back to {@link #getSharedServiceValidator()} because that would cause an
     * infinite loop.
     *
     * @return
     * @throws ToolkitException
     */
    private ServiceValidator getNonSharedServiceValidator() throws ToolkitException {

        ServiceValidator serviceValidator = null;
        if ( configuration != null ) {

            serviceValidator = getServiceValidator(configuration);

        } else if ( properties != null ) {

            serviceValidator = getServiceValidator(properties);

        }

        return serviceValidator;

    }

    /**
     * Construct a ServiceValidator using the provided configuration.
     *
     * @param configuration the {@link ServiceValidatorConfiguration} containing any overrides to default values
     * @return the new ServiceValidator object
     * @throws ToolkitException
     */
    public static ServiceValidator getServiceValidator(ServiceValidatorConfiguration configuration) throws ToolkitException {

        ServiceValidator serviceValidator;

        // See if we have a ServiceValidator class name in the configuration, if so instantiate that with the configuration
        String serviceValidatorClassName = configuration.getServiceValidatorClassName();
        if ( serviceValidatorClassName != null ) {

            try {

                Class<?> configClass = Class.forName(serviceValidatorClassName);

                Constructor ctor = configClass.getConstructor(ServiceValidatorConfiguration.class);

                serviceValidator = (ServiceValidator)ctor.newInstance(configuration);

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("Exception loading service validator class.", e);

            } catch (InstantiationException e) {

                throw new ToolkitException("Exception constructing service validator class.", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("Exception constructing service validator class.", e);

            } catch (NoSuchMethodException e) {

                throw new ToolkitException("Exception constructing service validator class.", e);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("Exception constructing service validator class.", e);

            }

        } else {

            throw new ToolkitException("ServiceValidator class name not set in service validator configuration.");

        }

        return serviceValidator;

    }

    /**
     * Construct a ServiceValidator using the provided properties to obtain a ServiceValidatorConfiguration object from the
     * ServiceValidatorConfigurationFactory and then calling {@link #getServiceValidator(ServiceValidatorConfiguration)} with it.
     *
     * @param properties the {@link Properties} containing any overrides to default values
     * @return the new ServiceValidator object
     * @throws ToolkitException
     */
    public static ServiceValidator getServiceValidator(Properties properties) throws ToolkitException {

        ServiceValidatorConfiguration configuration = ServiceValidatorConfigurationFactory.getConfiguration(properties);

        return getServiceValidator(configuration);

    }

}
