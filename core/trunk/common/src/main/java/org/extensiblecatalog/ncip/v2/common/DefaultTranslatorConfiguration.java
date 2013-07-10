/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;

public class DefaultTranslatorConfiguration extends BaseToolkitConfiguration implements TranslatorConfiguration {

    private static final Logger LOG = Logger.getLogger(DefaultTranslatorConfiguration.class);

    /** The name of the class to use as the translator. */
    protected String translatorClassName = TranslatorConfiguration.TRANSLATOR_CLASS_NAME_DEFAULT;

    /** Whether or not to log initiation and response messages. */
    protected boolean logMessages = true;

    /** If logging the messages, use this logging level. */
    protected Level messagesLoggingLevel = Level.DEBUG;

    /**
     * Create an instance of the configuration without initializing properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws org.extensiblecatalog.ncip.v2.service.ToolkitException
     */
    public DefaultTranslatorConfiguration() throws ToolkitException {

        // Do nothing

    }

    public DefaultTranslatorConfiguration(Properties properties) throws ToolkitException {
        this(null, properties);
    }

    public DefaultTranslatorConfiguration(String appName) throws ToolkitException {
        this(appName, null);
    }

    public DefaultTranslatorConfiguration(String appName, Properties properties) throws ToolkitException {

        super(appName, properties);

        String translatorClassNameOverride = null;
        String logMessagesString = null;
        String messageLoggingLevelString = null;

        if ( properties != null ) {

            translatorClassNameOverride = properties.getProperty(TranslatorConfiguration.TRANSLATOR_CLASS_NAME_KEY,
                TranslatorConfiguration.TRANSLATOR_CLASS_NAME_DEFAULT);
            logMessagesString = properties.getProperty(TranslatorConfiguration.TRANSLATOR_LOG_MESSAGES_KEY,
                TranslatorConfiguration.TRANSLATOR_LOG_MESSAGES_DEFAULT);
            messageLoggingLevelString = properties.getProperty(
                TranslatorConfiguration.TRANSLATOR_MESSAGES_LOGGING_LEVEL_KEY,
                TranslatorConfiguration.TRANSLATOR_MESSAGES_LOGGING_LEVEL_DEFAULT);

        }

        if ( translatorClassNameOverride != null ) {

            this.translatorClassName = translatorClassNameOverride;

        }

        if ( logMessagesString != null ) {

            this.logMessages = Boolean.parseBoolean(logMessagesString);

        }

        if ( messageLoggingLevelString != null ) {

            Level tempMessagesLoggingLevel = Level.toLevel(messageLoggingLevelString);
            if ( tempMessagesLoggingLevel != null ) {

                this.messagesLoggingLevel = tempMessagesLoggingLevel;

            } else {

                LOG.warn(TranslatorConfiguration.TRANSLATOR_MESSAGES_LOGGING_LEVEL_KEY + " of '"
                    + messageLoggingLevelString + "' is invalid; using default of '"
                    + TranslatorConfiguration.TRANSLATOR_MESSAGES_LOGGING_LEVEL_DEFAULT + "' instead.");
                this.messagesLoggingLevel
                    = Level.toLevel(TranslatorConfiguration.TRANSLATOR_MESSAGES_LOGGING_LEVEL_DEFAULT);

            }

        } else {

            this.messagesLoggingLevel = Level.DEBUG;

        }

    }

    @Override
    public String getComponentClassName() {
        return translatorClassName;
    }

    @Override
    public void setComponentClassName(String componentClassName) {
        this.translatorClassName = componentClassName;
    }

    @Override
    public boolean getLogMessages() {
        return logMessages;
    }

    @Override
    public Level getMessagesLoggingLevel() {
        return messagesLoggingLevel;
    }

    @Override
    public void setLogMessages(boolean logMessages) {

        this.logMessages = logMessages;

    }

    @Override
    public void setMessagesLoggingLevel(Level messagesLoggingLevel) {

        this.messagesLoggingLevel = messagesLoggingLevel;

    }

}
