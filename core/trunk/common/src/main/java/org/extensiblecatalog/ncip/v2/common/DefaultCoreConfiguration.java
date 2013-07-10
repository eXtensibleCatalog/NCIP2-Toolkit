/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

// TODO: The call SchemeLoader.init(...) should be handled outside the configuration class so using the default ctor works
public class DefaultCoreConfiguration extends BaseToolkitConfiguration implements CoreConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultCoreConfiguration.class);

    protected String coreClassName = CoreConfiguration.CORE_CLASS_NAME_DEFAULT;
    protected ConnectorConfiguration connectorConfig;
    protected MessageHandlerConfiguration messageHandlerConfig;
    protected ServiceValidatorConfiguration serviceValidatorConfig;
    protected TranslatorConfiguration translatorConfig;
    protected StatisticsBeanConfiguration statisticsBeanConfig;
    protected ProtocolVersionConfiguration protocolVersionConfig;

    /** Whether or not to include stack traces in response messages. */
    protected boolean includeStackTracesInProblemResponses
        = Boolean.parseBoolean(CoreConfiguration.CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_DEFAULT);

    /**
     * Create an instance of the configuration without initializing the properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public DefaultCoreConfiguration() throws ToolkitException {

        // Do nothing

    }

    public DefaultCoreConfiguration(String appName) throws ToolkitException {

        this(appName, null);

    }

    public DefaultCoreConfiguration(Properties properties) throws ToolkitException {

        this(null, properties);

    }

    public DefaultCoreConfiguration(String appName, Properties properties) throws ToolkitException {

        super(appName, properties);

        if ( this.properties != null ) {

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
    public boolean isConfigurationSet(String componentName) throws ToolkitException {

        if ( componentName.compareTo(Core.COMPONENT_NAME) == 0 ) {
            return true;
        } else if ( componentName.compareTo(Connector.COMPONENT_NAME) == 0 ) {
            return connectorConfig != null;
        } else if ( componentName.compareTo(MessageHandler.COMPONENT_NAME) == 0 ) {
            return messageHandlerConfig != null;
        } else if ( componentName.compareTo(ProtocolVersion.COMPONENT_NAME) == 0 ) {
            return protocolVersionConfig != null;
        } else if ( componentName.compareTo(ServiceValidator.COMPONENT_NAME) == 0 ) {
            return serviceValidatorConfig != null;
        } else if ( componentName.compareTo(StatisticsBean.COMPONENT_NAME) == 0 ) {
            return statisticsBeanConfig != null;
        } else if ( componentName.compareTo(Translator.COMPONENT_NAME) == 0 ) {
            return translatorConfig != null;
        } else {
            throw new ToolkitException("No matching component for '" + componentName + "'.");
        }
    }

    @Override
    public ToolkitConfiguration getConfiguration(String componentName) throws ToolkitException {

        if ( componentName.compareTo(Core.COMPONENT_NAME) == 0 ) {
            return this;
        } else if ( componentName.compareTo(Connector.COMPONENT_NAME) == 0 ) {
            return getConnectorConfiguration();
        } else if ( componentName.compareTo(MessageHandler.COMPONENT_NAME) == 0 ) {
            return getMessageHandlerConfiguration();
        } else if ( componentName.compareTo(ProtocolVersion.COMPONENT_NAME) == 0 ) {
            return getProtocolVersionConfiguration();
        } else if ( componentName.compareTo(ProtocolVersion.COMPONENT_NAME) == 0 ) {
            return getProtocolVersionConfiguration();
        } else if ( componentName.compareTo(ServiceValidator.COMPONENT_NAME) == 0 ) {
            return getServiceValidatorConfiguration();
        } else if ( componentName.compareTo(StatisticsBean.COMPONENT_NAME) == 0 ) {
            return getStatisticsBeanConfiguration();
        } else if ( componentName.compareTo(Translator.COMPONENT_NAME) == 0 ) {
            return getTranslatorConfiguration();
        } else {
            throw new ToolkitException("No matching component for '" + componentName + "'.");
        }
    }

    @Override
    public String getComponentClassName() {
        return coreClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.coreClassName = componentClassName;
    }

    @Override
    public ConnectorConfiguration getConnectorConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( connectorConfig == null ) {
                connectorConfig = ConnectorConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return connectorConfig;
    }

    @Override
    public MessageHandlerConfiguration getMessageHandlerConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( messageHandlerConfig == null ) {
                messageHandlerConfig = MessageHandlerConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return messageHandlerConfig;
    }

    @Override
    public ServiceValidatorConfiguration getServiceValidatorConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( serviceValidatorConfig == null ) {
                serviceValidatorConfig = ServiceValidatorConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return serviceValidatorConfig;
    }

    @Override
    public TranslatorConfiguration getTranslatorConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( translatorConfig == null ) {
                translatorConfig = TranslatorConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return translatorConfig;
    }

    @Override
    public StatisticsBeanConfiguration getStatisticsBeanConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( statisticsBeanConfig == null ) {
                statisticsBeanConfig = StatisticsBeanConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return statisticsBeanConfig;
    }

    @Override
    public ProtocolVersionConfiguration getProtocolVersionConfiguration() throws ToolkitException {
        synchronized (this) {
            if ( protocolVersionConfig == null ) {
                protocolVersionConfig = ProtocolVersionConfigurationFactory.buildConfiguration(appName, properties);
            }
        }
        return protocolVersionConfig;
    }

    public boolean getIncludeStackTracesInProblemResponses() {

        return includeStackTracesInProblemResponses;

    }

    public void setIncludeStackTracesInProblemResponses(boolean setting) {

        this.includeStackTracesInProblemResponses = setting;

    }

}
