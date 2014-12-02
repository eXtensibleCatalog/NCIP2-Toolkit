package org.extensiblecatalog.ncip.v2.aleph.util;

import java.util.Properties;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;

public class AlephConfiguration implements ConnectorConfiguration {

	private DefaultConnectorConfiguration delegate = null;

	public AlephConfiguration(DefaultConnectorConfiguration delegate) {
		this.delegate = delegate;
	}

	public String[] getArray(String key) {
		String value = delegate.getProperty(key);
		if (value != null) {
			return value.split(",");
		} else {
			return null;
		}
	}

	@Override
	public Properties getProperties() {
		return delegate.getProperties();
	}

	@Override
	public String getProperty(String key) {
		return delegate.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return delegate.getProperty(key, defaultValue);
	}

	@Override
	public String getAppName() {
		return delegate.getAppName();
	}

	@Override
	public void setAppName(String appName) {
		delegate.setAppName(appName);
	}

	@Override
	public String getComponentClassName() {
		return delegate.getComponentClassName();
	}

	@Override
	public void setComponentClassName(String componentClassName) {
		delegate.setComponentClassName(componentClassName);
	}

}
