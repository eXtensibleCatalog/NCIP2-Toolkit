/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class initializes the ConfigurationHelper class and should be used whenever the Toolkit is run in a Spring context,
 * i.e. this class must appear before all other Toolkit-related beans in the Spring configuration file, like this:
 * <br/><code>
 *     &lt;bean name="ToolkitAppContext" class="org.extensiblecatalog.ncip.v2.common.ToolkitAppContext"
 *             scope="singleton"/&gt;
 * </code>
 */
public class ToolkitAppContext implements ApplicationContextAware {

    protected static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        ConfigurationHelper.setApplicationContext(applicationContext);

    }

}
