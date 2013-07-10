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

public abstract class BaseToolkitConfiguration implements ToolkitConfiguration {

    private static final Logger LOG = Logger.getLogger(BaseToolkitConfiguration.class);

    protected String appName;
    protected Properties properties;

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public BaseToolkitConfiguration() {

        // Do nothing

    }

    public BaseToolkitConfiguration(Properties properties) {

        this.properties = properties;

    }

    public BaseToolkitConfiguration(String appName) {

        this.appName = appName;

    }

    public BaseToolkitConfiguration(String appName, Properties properties) {

        this.appName = appName;
        this.properties = properties;

    }

    @Override
    public Properties getProperties() {

        return properties;

    }

    @Override
    public String getProperty(String key) {

        return properties.getProperty(key);

    }

    @Override
    public String getProperty(String key, String defaultValue) {

        return properties.getProperty(key, defaultValue);

    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }


}
