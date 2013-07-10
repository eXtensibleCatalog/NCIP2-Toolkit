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

public class DefaultConnectorConfiguration extends BaseToolkitConfiguration implements ConnectorConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultConnectorConfiguration.class);

    protected String connectorClassName = ConnectorConfiguration.CONNECTOR_CLASS_NAME_DEFAULT;

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public DefaultConnectorConfiguration() {

        // Do nothing

    }

    public DefaultConnectorConfiguration(Properties properties) {

        super(null, properties);

    }

    public DefaultConnectorConfiguration(String appName) {

        super(appName, null);

    }

    public DefaultConnectorConfiguration(String appName, Properties properties) {

        super(appName, properties);

    }

    @Override
    public String getComponentClassName() {
        return connectorClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.connectorClassName = componentClassName;
    }
}
