/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import java.util.Properties;

public class DefaultConnectorConfiguration implements ConnectorConfiguration {

    protected Properties properties;

    public DefaultConnectorConfiguration() {

        // Do nothing

    }

    public DefaultConnectorConfiguration(Properties properties) {

        this.properties = properties;

    }

    @Override
    public Properties getProperties() {

        return properties;

    }

    @Override
    public Object getProperty(Object key) {

        return properties.get(key);

    }

    public String getProperty(String key, String defaultValue) {

        return properties.getProperty(key, defaultValue);

    }
}
