/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * Uses Spring's {@link PropertyPlaceholderConfigurer} to collect properties so
 * that ToolkitConfiguration classes can access properties loaded via Spring.
 *
 */
public class ToolkitPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger LOG = Logger.getLogger(ToolkitPropertyPlaceholderConfigurer.class);

    public Properties getProperties() throws IOException {

        Properties p = new Properties();

        super.loadProperties(p);

        return p;
    }

}
