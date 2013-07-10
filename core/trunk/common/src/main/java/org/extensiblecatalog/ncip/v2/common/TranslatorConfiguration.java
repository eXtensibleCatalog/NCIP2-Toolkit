/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Level;

public interface TranslatorConfiguration extends ToolkitConfiguration {

    final String TRANSLATOR_PROPERTIES_FILE_TITLE_KEY = "TranslatorConfiguration.PropertiesFileTitle";
    final String TRANSLATOR_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String TRANSLATOR_LOCAL_PROPERTIES_FILE_TITLE_KEY = "TranslatorConfiguration.LocalPropertiesFileTitle";
    final String TRANSLATOR_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String TRANSLATOR_CONFIG_CLASS_NAME_KEY = "TranslatorConfiguration.ConfigClass";
    // The default translator config class is in the binding package, which isn't a dependency of the common package
    // that this class is in, so we have to hard-code the class name here (despite the risks of breakage).
    final String TRANSLATOR_CONFIG_CLASS_NAME_DEFAULT = "org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.JAXBDozerNCIP2TranslatorConfiguration";

    final String TRANSLATOR_PROPERTIES_FILENAME_KEY = "TranslatorConfiguration.PropertiesFile";
    final String TRANSLATOR_PROPERTIES_FILENAME_DEFAULT = "translator.properties";

    final String TRANSLATOR_LOCAL_PROPERTIES_FILENAME_KEY = "TranslatorConfiguration.LocalPropertiesFile";
    final String TRANSLATOR_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}translator.properties";

    final String TRANSLATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "TranslatorConfiguration.PropertiesFileOverride";
    final String TRANSLATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String TRANSLATOR_CONFIG_FILE_NAME_KEY = "TranslatorConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #TRANSLATOR_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\translatorconfig.xml".
    final String TRANSLATOR_CONFIG_FILE_NAME_DEFAULT = null;

    final String TRANSLATOR_CLASS_NAME_KEY = "TranslatorConfiguration.ClassName";
    // The translator class is in a binding package, which isn't a dependency of the common package
    // that this class is in, so we have to hard-code the class name here (despite the risks of breakage).
    final String TRANSLATOR_CLASS_NAME_DEFAULT = "org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.dozer.NCIPv2_02JAXBDozerTranslator";

    final String TRANSLATOR_LOG_MESSAGES_KEY = "TranslatorConfiguration.LogMessages";
    final String TRANSLATOR_LOG_MESSAGES_DEFAULT = "false";

    final String TRANSLATOR_MESSAGES_LOGGING_LEVEL_KEY = "TranslatorConfiguration.MessagesLoggingLevel";
    final String TRANSLATOR_MESSAGES_LOGGING_LEVEL_DEFAULT = "INFO";

    boolean getLogMessages();

    /**
     * Set whether or not to log NCIP messages sent & received.
     * @param logMessages
     */
    void setLogMessages(boolean logMessages);

    Level getMessagesLoggingLevel();
    /**
     * Set the level to log NCIP messages at.
     * @param messagesLoggingLevel
     */
    void setMessagesLoggingLevel(Level messagesLoggingLevel);

}
