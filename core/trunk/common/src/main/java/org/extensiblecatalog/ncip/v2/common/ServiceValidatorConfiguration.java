/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

public interface ServiceValidatorConfiguration extends ToolkitConfiguration {

    final String SERVICE_VALIDATOR_PROPERTIES_FILE_TITLE_KEY = "ServiceValidatorConfiguration.PropertiesFileTitle";
    final String SERVICE_VALIDATOR_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILE_TITLE_KEY = "ServiceValidatorConfiguration.LocalPropertiesFileTitle";
    final String SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String SERVICE_VALIDATOR_PROPERTIES_FILENAME_KEY = "ServiceValidatorConfiguration.PropertiesFile";
    final String SERVICE_VALIDATOR_PROPERTIES_FILENAME_DEFAULT = "servicevalidator.properties";

    final String SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILENAME_KEY = "ServiceValidatorConfiguration.LocalPropertiesFile";
    final String SERVICE_VALIDATOR_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}servicevalidator.properties";

    final String SERVICE_VALIDATOR_CONFIG_CLASS_NAME_KEY = "ServiceValidatorConfiguration.ConfigClass";
    final String SERVICE_VALIDATOR_CONFIG_CLASS_NAME_DEFAULT = DefaultNCIPServiceValidatorConfiguration.class.getName();

    final String SERVICE_VALIDATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "ServiceValidatorConfiguration.PropertiesFileOverride";
    final String SERVICE_VALIDATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String SERVICE_VALIDATOR_CONFIG_FILE_NAME_KEY = "ServiceValidatorConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #SERVICE_VALIDATOR_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\servicevalidatorconfig.xml".
    final String SERVICE_VALIDATOR_CONFIG_FILE_NAME_DEFAULT = null;

    final String SERVICE_VALIDATOR_CLASS_NAME_KEY = "ServiceValidatorConfiguration.ClassName";
    final String SERVICE_VALIDATOR_CLASS_NAME_DEFAULT = DefaultNCIPServiceValidator.class.getName();

    final String SERVICE_VALIDATOR_SERVICE_CONTEXT_CLASS_NAME_KEY = "ServiceValidatorConfiguration.ServiceContextClassName";
    final String SERVICE_VALIDATOR_SERVICE_CONTEXT_CLASS_NAME_DEFAULT = NCIPServiceContext.class.getName();
}
