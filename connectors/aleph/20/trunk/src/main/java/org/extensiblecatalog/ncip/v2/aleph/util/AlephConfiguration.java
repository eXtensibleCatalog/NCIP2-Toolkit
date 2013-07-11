package org.extensiblecatalog.ncip.v2.aleph.util;

import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;

public class AlephConfiguration extends DefaultConnectorConfiguration {

	public String[] getArray(String key) {
		String value = super.getProperty(key);
		return value.split(",");
	}
	
}
