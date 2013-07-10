/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.Properties;

public class DefaultStatisticsBeanConfiguration implements StatisticsBeanConfiguration {

    protected Properties properties;

    protected String statisticsBeanClassName = StatisticsBeanConfiguration.STATISTICS_BEAN_CLASS_NAME_DEFAULT;


    public DefaultStatisticsBeanConfiguration() {

        // Do nothing

    }

    public DefaultStatisticsBeanConfiguration(Properties properties) {

        this.properties = properties;

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
    public Properties getProperties() {

        return properties;

    }

    @Override
    public Object getProperty(Object key) {

        return properties.get(key);

    }

    @Override
    public String getStatisticsBeanClassName() {
        return statisticsBeanClassName;
    }

    @Override
    public void setStatisticsBeanClassName(String className) {
        this.statisticsBeanClassName = className;
    }
}
