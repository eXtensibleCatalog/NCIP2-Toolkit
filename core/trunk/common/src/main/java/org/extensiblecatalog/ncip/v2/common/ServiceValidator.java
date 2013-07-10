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

public interface ServiceValidator extends ToolkitComponent {

    public static final String COMPONENT_NAME = ServiceValidator.class.getSimpleName();

    ServiceContext getInitialServiceContext() throws ToolkitException;

}
