/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface StatisticsBeanConfiguration extends ToolkitConfiguration {

    final String STATISTICS_BEAN_LOCAL_PROPERTIES_DEFAULT_FILENAME = "localstatisticsbean.properties";
    final String STATISTICS_BEAN_PROPERTIES_DEFAULT_FILENAME = "statisticsbean.properties";


    final String STATISTICS_BEAN_CONFIG_CLASS_NAME_KEY = "StatisticsBeanConfiguration.ConfigClass";
    final String STATISTICS_BEAN_CONFIG_CLASS_NAME_DEFAULT = DefaultStatisticsBeanConfiguration.class.getName();

    final String STATISTICS_BEAN_PROPERTIES_FILENAME_KEY = "StatisticsBeanConfiguration.PropertiesFile";
    final String STATISTICS_BEAN_PROPERTIES_FILENAME_DEFAULT = "statisticsbean.properties";

    final String STATISTICS_BEAN_LOCAL_PROPERTIES_FILENAME_KEY = "StatisticsBeanConfiguration.LocalPropertiesFile";
    final String STATISTICS_BEAN_LOCAL_PROPERTIES_FILENAME_DEFAULT = "localstatisticsbean.properties";

    final String STATISTICS_BEAN_CONFIG_FILE_NAME_KEY = "StatisticsBeanConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #STATISTICS_BEAN_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\toolkitconfig.xml".
    final String STATISTICS_BEAN_CONFIG_FILE_NAME_DEFAULT = null;

    final String STATISTICS_BEAN_CLASS_NAME_KEY = "StatisticsBeanConfiguration.ClassName";
    final String STATISTICS_BEAN_CLASS_NAME_DEFAULT = StatisticsBean.class.getName();

    String getStatisticsBeanClassName();
    void setStatisticsBeanClassName(String className);

}
