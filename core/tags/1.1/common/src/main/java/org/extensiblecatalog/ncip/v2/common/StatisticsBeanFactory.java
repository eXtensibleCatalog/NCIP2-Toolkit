/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class StatisticsBeanFactory {

    /**
     * This is the instance returned to all callers of getStatisticsBean(). If you want multiple, differing
     * statisticsBeans you will need to call {@link #getStatisticsBean()} or
     * {@link #getStatisticsBean(StatisticsBeanConfiguration)}.
     */
    protected static StatisticsBean sharedInstance;

    protected final StatisticsBeanConfiguration configuration;

    protected final Properties properties;

    /**
     * Construct a StatisticsBeanFactory that can be used to construct distinct {@link StatisticsBean} objects using the
     * same construction sequence used by {@link #getSharedStatisticsBean()}.
     * @throws ToolkitException
     */
    public StatisticsBeanFactory() throws ToolkitException {

        configuration = null;
        properties = null;

    }

    /**
     * Construct a StatisticsBeanFactory that will return {@link StatisticsBean} objects constructed with the passed-in
     * {@link StatisticsBeanConfiguration}.
     *
     * @param configuration the {@link StatisticsBeanConfiguration} to use to construct {@link StatisticsBean} instances.
     *
     */
    public StatisticsBeanFactory(StatisticsBeanConfiguration configuration) throws ToolkitException {

        this.configuration = configuration;
        this.properties = null;

    }

    /**
     * Construct a StatisticsBeanFactory that will return {@link StatisticsBean} objects constructed with the passed-in
     * {@link Properties}.
     *
     * @param properties the {@link Properties} to use to construct {@link StatisticsBean} instances.
     *
     */
    public StatisticsBeanFactory(Properties properties) throws ToolkitException {

        this.configuration = null;
        this.properties = properties;

    }

    /**
     * Get the shared StatisticsBean object. In order try the following:
     * 1) If the {@link #sharedInstance} is already set (e.g. by Spring) or a prior call to this method,
     * return that.
     * 2) Look for a "statisticsBean" bean in the AppContext and return that.
     * 3) Look for a "statisticsBeanFactory" bean in the AppContext and (to avoid infinite recursion) if it is an instance
     * of this class, call the {@link #getStatisticsBean()} method on that object; otherwise call
     * {@link #getSharedStatisticsBean()} method on that object.
     * 4) Look for a "statisticsBeanConfigurationFactory" bean in the AppContext and get the {@link StatisticsBeanConfiguration}
     * object from that and pass that to {@link #getStatisticsBean(StatisticsBeanConfiguration)} method and return the result.
     * 5) Call {@link ConfigurationHelper#getCoreConfiguration()} method to get a {@link CoreConfiguration},
     * call its {@link CoreConfiguration#getStatisticsBeanConfiguration()} method, pass the result to
     * {@link #getStatisticsBean(StatisticsBeanConfiguration)} and return that result.
     *
     * this calls {@link #getStatisticsBean()} to construct it.
     * @return the {@link StatisticsBean} object
     * @throws ToolkitException
     */
    public static synchronized StatisticsBean getSharedStatisticsBean() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the sharedInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedInstance == null ) {

            ApplicationContext appContext = ConfigurationHelper.getApplicationContext();

            if ( appContext != null ) {

                if ( appContext.containsBean("statisticsBean") ) {

                  sharedInstance = (StatisticsBean)appContext.getBean("statisticsBean");

                } else if ( appContext.containsBean("statisticsBeanFactory") ) {

                    StatisticsBeanFactory statisticsBeanFactory
                        = (StatisticsBeanFactory)appContext.getBean("statisticsBeanFactory");

                    // Protect against a recursive call, which will produce an infinite loop
                    if ( statisticsBeanFactory.getClass() == StatisticsBeanFactory.class ) {

                        // If the AppContext has this class as the factory, call getStatisticsBean,
                        // and *if* that class was constructed with a configuration or properties, it won't call back
                        // to this method. If it was, this will result in an infinite loop.
                        sharedInstance = statisticsBeanFactory.getNonSharedStatisticsBean();


                    } else {

                        // Otherwise, call the StatisticsBeanFactory in the AppContext; it's their responsibility not
                        // to call this method
                        sharedInstance = statisticsBeanFactory.getSharedStatisticsBean();

                    }

                } else if ( appContext.containsBean("statisticsBeanConfigurationFactory") ) {

                    StatisticsBeanConfigurationFactory statisticsBeanConfigFactory
                        = (StatisticsBeanConfigurationFactory)appContext.getBean("statisticsBeanConfigurationFactory");

                    sharedInstance = getStatisticsBean(statisticsBeanConfigFactory.getConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration();
                if ( coreConfig != null ) {

                    sharedInstance = getStatisticsBean(coreConfig.getStatisticsBeanConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                throw new ToolkitException("Unable to initialize statistics bean.");

            }

        }

        return sharedInstance;

    }

    /**
     *
     * @return
     * @throws ToolkitException
     */
    public StatisticsBean getStatisticsBean() throws ToolkitException {

        StatisticsBean statisticsBean = getNonSharedStatisticsBean();

        if ( statisticsBean == null ) {

            statisticsBean = getSharedStatisticsBean();

        }

        return statisticsBean;

    }

    /**
     * Used by {@link #getStatisticsBean()} to get a StatisticsBean from either the {@link #configuration} or the
     * {@link #properties}, and by {@link #getSharedStatisticsBean()} when it's retrieved an instance of itself from the
     * AppContext and so needs to avoid calling back to {@link #getSharedStatisticsBean()} because that would cause an
     * infinite loop.
     *
     * @return
     * @throws ToolkitException
     */
    private StatisticsBean getNonSharedStatisticsBean() throws ToolkitException {

        StatisticsBean statisticsBean = null;
        if ( configuration != null ) {

            statisticsBean = getStatisticsBean(configuration);

        } else if ( properties != null ) {

            statisticsBean = getStatisticsBean(properties);

        }

        return statisticsBean;

    }

    /**
     * Construct a StatisticsBean using the provided configuration.
     *
     * @param configuration the {@link StatisticsBeanConfiguration} containing any overrides to default values
     * @return the new StatisticsBean object
     * @throws ToolkitException
     */
    public static StatisticsBean getStatisticsBean(StatisticsBeanConfiguration configuration) throws ToolkitException {

        StatisticsBean statisticsBean;

        // See if we have a StatisticsBean class name in the configuration, if so instantiate that with the configuration
        String statisticsBeanClassName = configuration.getStatisticsBeanClassName();
        if ( statisticsBeanClassName != null ) {

            try {

                Class<?> configClass = Class.forName(statisticsBeanClassName);

                Constructor ctor = configClass.getConstructor(StatisticsBeanConfiguration.class);

                statisticsBean = (StatisticsBean)ctor.newInstance(configuration);

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("Exception loading statistics bean class.", e);

            } catch (InstantiationException e) {

                throw new ToolkitException("Exception constructing statistics bean class.", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("Exception constructing statistics bean class.", e);

            } catch (NoSuchMethodException e) {

                throw new ToolkitException("Exception constructing statistics bean class.", e);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("Exception constructing statistics bean class.", e);

            }

        } else {

            throw new ToolkitException("StatisticsBean class name not set in statistics bean configuration.");

        }

        return statisticsBean;

    }

    /**
     * Construct a StatisticsBean using the provided properties to obtain a StatisticsBeanConfiguration object from the
     * StatisticsBeanConfigurationFactory and then calling {@link #getStatisticsBean(StatisticsBeanConfiguration)} with it.
     *
     * @param properties the {@link Properties} containing any overrides to default values
     * @return the new StatisticsBean object
     * @throws ToolkitException
     */
    public static StatisticsBean getStatisticsBean(Properties properties) throws ToolkitException {

        StatisticsBeanConfiguration configuration = StatisticsBeanConfigurationFactory.getConfiguration(properties);

        return getStatisticsBean(configuration);

    }

}
