/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.NCIPVersion;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

public class NCIPv201ServiceContext extends NCIPServiceContext {

    public NCIPv201ServiceContext() {

        super(NCIPVersion.VERSION_2_01, NCIPServiceValidatorConfiguration.APPLICATION_PROFILE_TYPES_DEFAULT,
            Boolean.parseBoolean(NCIPServiceValidatorConfiguration.REQUIRE_APPLICATION_PROFILE_TYPE_DEFAULT),
            NCIPServiceValidatorConfiguration.PACKAGE_NAMES_DEFAULT,
            NCIPServiceValidatorConfiguration.DEFAULT_NAMESPACE_URI_DEFAULT,
            Boolean.parseBoolean(NCIPServiceValidatorConfiguration.ADD_DEFAULT_NAMESPACE_URI_DEFAULT),
            NCIPServiceValidatorConfiguration.NAMESPACE_URIS_DEFAULT.split(","), 
            ToolkitHelper.createStringList(NCIPServiceValidatorConfiguration.SCHEMA_URLS_DEFAULT));

    }

    public NCIPv201ServiceContext(ServiceValidatorConfiguration config) throws ToolkitException {

        super(config);

    }
}
