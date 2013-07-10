/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceValidator;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

public class DefaultNCIPServiceValidator implements ServiceValidator {

    protected ServiceValidatorConfiguration config;

    public DefaultNCIPServiceValidator(ServiceValidatorConfiguration config) {

        this.config = config;

    }

    public ServiceContext getInitialServiceContext() throws ToolkitException {

        return new NCIPv201ServiceContext(config);

    }
}
