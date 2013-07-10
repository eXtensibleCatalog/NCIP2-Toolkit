/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;

import java.util.Properties;

public class DefaultStatisticsBeanConfiguration extends BaseToolkitConfiguration
    implements StatisticsBeanConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultStatisticsBeanConfiguration.class);

    protected String statisticsBeanClassName = StatisticsBeanConfiguration.STATISTICS_BEAN_CLASS_NAME_DEFAULT;

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public DefaultStatisticsBeanConfiguration() {

        // Do nothing

    }

    public DefaultStatisticsBeanConfiguration(String appName) {

        this(appName, null);

    }

    public DefaultStatisticsBeanConfiguration(Properties properties) {

        this(null, properties);

    }

    public DefaultStatisticsBeanConfiguration(String appName, Properties properties) {

        super(appName, properties);

        String statisticsBeanClassNameString = null;

        if ( properties != null ) {

            statisticsBeanClassNameString = this.properties.getProperty(
                StatisticsBeanConfiguration.STATISTICS_BEAN_CLASS_NAME_KEY);

        }

        if ( statisticsBeanClassNameString != null ) {

            this.statisticsBeanClassName = statisticsBeanClassNameString;

        }


    }

    @Override
    public String getComponentClassName() {
        return statisticsBeanClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.statisticsBeanClassName = componentClassName;
    }

}
