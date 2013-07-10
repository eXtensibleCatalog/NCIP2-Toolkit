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

public class StatisticsBeanFactory extends BaseComponentFactory<StatisticsBean, StatisticsBeanConfiguration> {

    private static final Logger LOG = Logger.getLogger(StatisticsBeanFactory.class);

    /**
     * The instance of {@link StatisticsBean} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected StatisticsBean statisticsBean;

    /**
     * Create an instance of the factory without initializing the {@link #statisticsBean} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public StatisticsBeanFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link StatisticsBean}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public StatisticsBeanFactory(Properties properties) throws ToolkitException {

        this.statisticsBean = buildStatisticsBean(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link StatisticsBean} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public StatisticsBeanFactory(String appName) throws ToolkitException {

        this.statisticsBean = buildStatisticsBean(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link StatisticsBean} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public StatisticsBeanFactory(String appName, Properties properties) throws ToolkitException {

        this.statisticsBean = buildStatisticsBean(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link StatisticsBean} built with the supplied
     * {@link StatisticsBeanConfiguration}.
     * @param configuration an instance of {@link StatisticsBeanConfiguration}
     */
    public StatisticsBeanFactory(StatisticsBeanConfiguration configuration) throws ToolkitException {

        this.statisticsBean = (StatisticsBean) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #statisticsBean} object.
     * @return the {@link #statisticsBean}
     */
    @Override
    public StatisticsBean getComponent() {

        return statisticsBean;

    }

    /**
     * Convenience method which calls {@link #buildStatisticsBean(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link StatisticsBean}
     * @throws ToolkitException
     */
    public static StatisticsBean buildStatisticsBean() throws ToolkitException {

        return buildStatisticsBean(null, null);

    }

    /**
     * Convenience method which calls {@link #buildStatisticsBean(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link StatisticsBean}
     * @throws ToolkitException
     */
    public static StatisticsBean buildStatisticsBean(Properties properties) throws ToolkitException {

        return buildStatisticsBean(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildStatisticsBean(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link StatisticsBean}
     * @throws ToolkitException
     */
    public static StatisticsBean buildStatisticsBean(String appName) throws ToolkitException {

        return buildStatisticsBean(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link StatisticsBeanConfiguration}
     * @return the {@link StatisticsBean}
     * @throws ToolkitException
     */
    public static StatisticsBean buildStatisticsBean(StatisticsBeanConfiguration configuration) throws ToolkitException {

        return (StatisticsBean) buildComponent(configuration);

    }

    /**
     * Construct a StatisticsBean object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the new CoreConfiguration object
     * @throws ToolkitException on any error
     */
    public static StatisticsBean buildStatisticsBean(String appName, Properties properties) throws ToolkitException {

        StatisticsBean sb;

        sb = (StatisticsBean)buildComponent(StatisticsBeanFactory.class, appName, StatisticsBean.COMPONENT_NAME,
                StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_FILE_NAME_KEY,
                StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_FILE_NAME_DEFAULT,
                properties,
                StatisticsBeanConfiguration.STATISTICS_BEAN_PROPERTIES_FILENAME_KEY,
                StatisticsBeanConfiguration.STATISTICS_BEAN_PROPERTIES_FILENAME_DEFAULT,
                StatisticsBeanConfiguration.STATISTICS_BEAN_LOCAL_PROPERTIES_FILENAME_KEY,
                StatisticsBeanConfiguration.STATISTICS_BEAN_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                StatisticsBeanConfiguration.STATISTICS_BEAN_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return sb;

    }
}
