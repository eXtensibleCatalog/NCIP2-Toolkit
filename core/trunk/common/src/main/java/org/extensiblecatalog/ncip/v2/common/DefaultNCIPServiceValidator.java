/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultNCIPServiceValidator implements ServiceValidator {

    protected NCIPServiceValidatorConfiguration config;

    protected Constructor serviceContextClassConstructor;

    public DefaultNCIPServiceValidator(ServiceValidatorConfiguration config) {

        this.config = (NCIPServiceValidatorConfiguration)config;

    }

    public ServiceContext getInitialServiceContext() throws ToolkitException {

        ServiceContext result;

        synchronized(this) {
            if ( serviceContextClassConstructor == null ) {

                try {

                    serviceContextClassConstructor = Class.forName(config.getServiceContextClassName())
                            .getConstructor(ServiceValidatorConfiguration.class);

                } catch (ClassNotFoundException e) {

                    throw new ToolkitException(e);

                } catch (NoSuchMethodException e) {

                    throw new ToolkitException(e);

                }
            }

        }

        try {

            result = (ServiceContext)serviceContextClassConstructor.newInstance(config);

        } catch (InstantiationException e) {

            throw new ToolkitException(e);

        } catch (IllegalAccessException e) {

            throw new ToolkitException(e);

        } catch (InvocationTargetException e) {

            throw new ToolkitException(e);

        }

        return result;

    }
}
