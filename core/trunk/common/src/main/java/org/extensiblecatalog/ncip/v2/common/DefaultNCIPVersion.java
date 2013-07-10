/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultNCIPVersion implements ProtocolVersion {

    private static final Logger LOG = Logger.getLogger(DefaultNCIPVersion.class);

    protected static Map<String, DefaultNCIPVersion> versionsByAttribute = new HashMap<String, DefaultNCIPVersion>();

    protected final String versionAttribute;
    protected final DefaultNCIPVersion canonicalVersion;

    public DefaultNCIPVersion(String versionAttribute, DefaultNCIPVersion canonicalVersion) {

        this.versionAttribute = versionAttribute;
        this.canonicalVersion = canonicalVersion;
        synchronized (DefaultNCIPVersion.class) {
            versionsByAttribute.put(this.versionAttribute, this);
        }

    }

    public DefaultNCIPVersion(String versionAttribute) {

        this(versionAttribute, null);
    }

    public DefaultNCIPVersion(Properties properties) throws ToolkitException {

        this(ProtocolVersionConfigurationFactory.buildConfiguration(properties));

    }

    public DefaultNCIPVersion(ProtocolVersionConfiguration config) {

        this(((NCIPProtocolVersionConfiguration)config).getVersionAttribute(),
                ((NCIPProtocolVersionConfiguration)config).getCanonicalVersion());

    }

    public static final DefaultNCIPVersion VERSION_1_0 =  new DefaultNCIPVersion("http://www.niso.org/ncip/v1_0/imp1/dtd/ncip_v1_0.dtd");
    public static final DefaultNCIPVersion VERSION_1_01 = new DefaultNCIPVersion("http://www.niso.org/ncip/v1_01/imp1/dtd/ncip_v1_01.dtd");
    public static final DefaultNCIPVersion VERSION_2_0 =  new DefaultNCIPVersion("http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd");
    public static final DefaultNCIPVersion VERSION_2_01 = new DefaultNCIPVersion("http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_01.xsd");

    public String getVersionAttribute() {

        return this.versionAttribute;

    }

    public DefaultNCIPVersion getCanonicalVersion() {

        return this.canonicalVersion;

    }

    public static DefaultNCIPVersion findByVersionAttribute(String versionAttribute) {

        DefaultNCIPVersion result = null;

        if ( versionAttribute != null ) {
            result = versionsByAttribute.get(versionAttribute);
        }

        return result;

    }

}
