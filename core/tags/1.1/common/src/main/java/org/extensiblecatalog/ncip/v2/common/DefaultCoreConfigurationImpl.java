/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.SchemeLoader;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

// TODO: the SchemeValuePair attributes should be exposed via getters & setters
// TODO: The call SchemeLoader.init(...) should be handled outside the configuration class so using the default ctor works
public class DefaultCoreConfigurationImpl implements CoreConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultCoreConfigurationImpl.class);

    protected Properties properties;

    protected MessageHandlerConfiguration messageHandlerConfig;
    protected ServiceValidatorConfiguration serviceValidatorConfig;
    protected TranslatorConfiguration translatorConfig;
    protected StatisticsBeanConfiguration statisticsBeanConfig;

    /** Whether or not to include stack traces in response messages. */
    protected boolean includeStackTracesInProblemResponses
        = Boolean.parseBoolean(CoreConfiguration.CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_DEFAULT);

    public DefaultCoreConfigurationImpl() {

        // Do nothing

    }

    public DefaultCoreConfigurationImpl(Properties properties) throws ToolkitException {

        this.properties = properties;

        if ( this.properties != null ) {

            messageHandlerConfig = MessageHandlerConfigurationFactory.getConfiguration(properties);
            serviceValidatorConfig = ServiceValidatorConfigurationFactory.getConfiguration(properties);
            translatorConfig = TranslatorConfigurationFactory.getConfiguration(properties);
            statisticsBeanConfig = StatisticsBeanConfigurationFactory.getConfiguration(properties);

            String stackTracesPropertyString = null;

            stackTracesPropertyString = this.properties.getProperty(
                CoreConfiguration.CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_KEY,
                CoreConfiguration.CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_DEFAULT);

            if ( stackTracesPropertyString != null ) {

                this.includeStackTracesInProblemResponses = Boolean.parseBoolean(stackTracesPropertyString);

            }

            String svpClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_CLASSES_LIST_KEY,
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_CLASSES_LIST_DEFAULT);

            String addedSVPClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ADDED_CLASSES_LIST_KEY);

            String allowAnyClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ALLOW_ANY_CLASSES_LIST_KEY,
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ALLOW_ANY_CLASSES_LIST_DEFAULT);

            String addedAllowAnyClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_ANY_CLASSES_LIST_KEY);

            String allowNullSchemeClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ALLOW_NULL_SCHEME_CLASSES_LIST_KEY,
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ALLOW_NULL_SCHEME_CLASSES_LIST_DEFAULT);

            String addedAllowNullSchemeClassNamesCSV = this.properties.getProperty(
                CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_NULL_SCHEME_CLASSES_LIST_KEY);

            try {

                SchemeLoader.init(svpClassNamesCSV, addedSVPClassNamesCSV, 
                    allowAnyClassNamesCSV, addedAllowAnyClassNamesCSV,
                    allowNullSchemeClassNamesCSV, addedAllowNullSchemeClassNamesCSV);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("InvocationTargetException calling SchemeLoader.init", e);

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("ClassNotFoundException calling SchemeLoader.init", e);

            } catch (NoSuchMethodException e) {

                throw new ToolkitException("NoSuchMethodException calling SchemeLoader.init", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("IllegalAccessException calling SchemeLoader.init", e);

            }

        } else {

            throw new ToolkitException("Properties parameter must not be null.");

        }

    }

    @Override
    public MessageHandlerConfiguration getMessageHandlerConfiguration() throws ToolkitException {
        return messageHandlerConfig;
    }

    @Override
    public ServiceValidatorConfiguration getServiceContextConfiguration() throws ToolkitException {
        return serviceValidatorConfig;
    }

    @Override
    public TranslatorConfiguration getTranslatorConfiguration() throws ToolkitException {
        return translatorConfig;
    }

    @Override
    public StatisticsBeanConfiguration getStatisticsBeanConfiguration() throws ToolkitException {
        return statisticsBeanConfig;
    }

    public boolean getIncludeStackTracesInProblemResponses() {

        return includeStackTracesInProblemResponses;

    }

    public void setIncludeStackTracesInProblemResponses(boolean setting) {

        this.includeStackTracesInProblemResponses = setting;

    }

    @Override
    public Properties getProperties() {

        return properties;

    }

    @Override
    public Object getProperty(Object key) {

        return properties.get(key);

    }

}
