/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.voyager.util;

import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

public class VoyagerConfiguration extends DefaultConnectorConfiguration {

    private HashMap<String, String> agencyToUbid = new HashMap<String, String>();
    private HashMap<String, String> ubidToAgency = new HashMap<String, String>();

    public VoyagerConfiguration() {

        // Do nothing
    }

    public VoyagerConfiguration(Properties properties) {

        super(properties);

        Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            int inx = key.lastIndexOf("vxwsUrl");
            if (inx > 0) { 
                String agency = key.substring(0, inx);
                agencyToUbid.put(agency, properties.getProperty(agency));
                ubidToAgency.put(properties.getProperty(agency), agency);
            }
        }
    }

    public String getAgencyFromUbid(String agency) { return agencyToUbid.get(agency); }
    public String getUbidFromAgency(String ubid) { return ubidToAgency.get(ubid); }
	
}
