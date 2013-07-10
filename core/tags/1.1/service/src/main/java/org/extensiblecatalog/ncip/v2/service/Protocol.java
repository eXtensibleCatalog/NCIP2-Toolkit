/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Protocol {

    private static final Logger LOG = Logger.getLogger(Protocol.class);

    protected static Map<Protocol, Map<String /*Version String*/, ProtocolVersion>> versionsByNameByProtocolMap
        = new HashMap<Protocol, Map<String, ProtocolVersion>>();

    protected static Map<Protocol, Map<String /*Profile String*/, ApplicationProfileType>> profilesByNameByProtocolMap
        = new HashMap<Protocol, Map<String, ApplicationProfileType>>();

    public static final Protocol NCIP = new Protocol("NCIP");

    static {

        Map<String, ProtocolVersion> ncipVersionsMap = new HashMap<String, ProtocolVersion>();
        ncipVersionsMap.put(NCIPVersion.VERSION_2_0.getVersion(), NCIPVersion.VERSION_2_0);
        ncipVersionsMap.put(NCIPVersion.VERSION_2_01.getVersion(), NCIPVersion.VERSION_2_01);
        versionsByNameByProtocolMap.put(NCIP, ncipVersionsMap);

        Map<String, ApplicationProfileType> ncipProfilesMap = new HashMap<String, ApplicationProfileType>();
        // None yet
        profilesByNameByProtocolMap.put(NCIP, ncipProfilesMap);

    }

    public static void addProtocolVersion(Protocol protocol, ProtocolVersion version) {

        Map<String, ProtocolVersion> versionsByNameMap = versionsByNameByProtocolMap.get(protocol);
        if ( versionsByNameMap == null ) {

            versionsByNameMap = new HashMap<String, ProtocolVersion>();

        }

        if ( ! versionsByNameMap.containsKey(version.getVersion()) ) {

            versionsByNameMap.put(version.getVersion(), version);

        } else {

            LOG.debug("Not adding version " + version + " because it's already in the map as "
                + versionsByNameMap.get(version));
        }

    }

    public static Protocol valueOf(String name) {

        Protocol result;
        if ( NCIP.getName().compareToIgnoreCase(name) == 0 ) {

            result = NCIP;

        } else {

            result = null;

        }

        return result;
    }

    protected final String name;

    protected Protocol(String name) {

        this.name = name;

    }

    public String getName() {

        return this.name;

    }


    public ProtocolVersion getVersion(String version) {

        ProtocolVersion result;
        Map<String, ProtocolVersion> versionMap = versionsByNameByProtocolMap.get(this);
        if ( versionMap != null ) {

            result = versionMap.get(version);

        } else {

            result = null;

        }

        return result;

    }

    public ApplicationProfileType getProfile(String profile) {

        ApplicationProfileType result;
        Map<String, ApplicationProfileType> profileMap = profilesByNameByProtocolMap.get(this);
        if ( profileMap != null ) {

            result = profileMap.get(profile);

        } else {

            result = null;

        }

        // TODO: Need a way to indicate that this can support the "null" profile, i.e. "Standard" NCIP.

        return result;

    }

    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
    
}
