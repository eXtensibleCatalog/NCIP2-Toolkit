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

import java.util.Properties;

public class TranslatorFactory extends BaseComponentFactory<Translator, TranslatorConfiguration> {

    private static final Logger LOG = Logger.getLogger(TranslatorFactory.class);

    /**
     * The instance of {@link Translator} that this factory returns on every call to
     * {@link #getComponent()}.
     */
    protected Translator translator;

    /**
     * Create an instance of the factory without initializing the {@link #translator} property; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public TranslatorFactory() throws ToolkitException {

        // Do nothing

    }

    /**
     * Create an instance of the factory which will return the generic (i.e. no appName) {@link Translator}, with the
     * provided override properties.
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public TranslatorFactory(Properties properties) throws ToolkitException {

        this.translator = buildTranslator(properties);

    }

    /**
     * Create an instance of the factory which will return the {@link Translator} for the provided appName.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @throws ToolkitException
     */
    public TranslatorFactory(String appName) throws ToolkitException {

        this.translator = buildTranslator(appName);

    }

    /**
     * Create an instance of the factory which will return the {@link Translator} for the provided appName, with the
     * provided override properties.
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the override Properties
     * @throws ToolkitException
     */
    public TranslatorFactory(String appName, Properties properties) throws ToolkitException {

        this.translator = buildTranslator(appName, properties);

    }

    /**
     * Create an instance of the factory which will return a {@link Translator} built with the supplied
     * {@link TranslatorConfiguration}.
     * @param configuration an instance of {@link TranslatorConfiguration}
     */
    public TranslatorFactory(TranslatorConfiguration configuration) throws ToolkitException {

        this.translator = (Translator) buildComponent(configuration);

    }

    /**
     * Returns the factory's {@link #translator} object.
     * @return the {@link #translator}
     */
    @Override
    public Translator getComponent() {

        return translator;

    }

    /**
     * Convenience method which calls {@link #buildTranslator(String, java.util.Properties)} passing <code>null</code>
     * for the appName and properties parameters.
     *
     * @return the {@link Translator}
     * @throws ToolkitException
     */
    public static Translator buildTranslator() throws ToolkitException {

        return buildTranslator(null, null);

    }

    /**
     * Convenience method which calls {@link #buildTranslator(String, java.util.Properties)} passing <code>null</code>
     * for the appName parameter.
     *
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this configuration
     * @return the {@link Translator}
     * @throws ToolkitException
     */
    public static Translator buildTranslator(Properties properties) throws ToolkitException {

        return buildTranslator(null, properties);

    }

    /**
     * Convenience method which calls {@link #buildTranslator(String, java.util.Properties)} passing <code>null</code>
     * for the properties parameter.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @return the {@link Translator}
     * @throws ToolkitException
     */
    public static Translator buildTranslator(String appName) throws ToolkitException {

        return buildTranslator(appName, null);

    }

    /**
     * Convenience method which calls {@link #buildComponent(ToolkitConfiguration)}.
     *
     * @param configuration an instance of {@link TranslatorConfiguration}
     * @return the {@link Translator}
     * @throws ToolkitException
     */
    public static Translator buildTranslator(TranslatorConfiguration configuration) throws ToolkitException {

        return (Translator) buildComponent(configuration);

    }

    /**
     * Construct a Translator object for the given appName using the provided property overrides.
     *
     * @param appName the name of the application; used to identify different configuration sets (e.g. different
     * Spring or property files); see {@link ConfigurationHelper} for a fuller explanation
     * @param properties the {@link Properties} object containing overrides to values taken from the environment (e.g.
     * Spring or properties files) for properties used to create this translator
     * @return the new Translator object
     * @throws ToolkitException on any error
     */
    public static Translator buildTranslator(String appName, Properties properties) throws ToolkitException {

        Translator trans;

        trans = (Translator)buildComponent(TranslatorFactory.class, appName, Translator.COMPONENT_NAME,
                TranslatorConfiguration.TRANSLATOR_CONFIG_FILE_NAME_KEY,
                TranslatorConfiguration.TRANSLATOR_CONFIG_FILE_NAME_DEFAULT,
                properties,
                TranslatorConfiguration.TRANSLATOR_PROPERTIES_FILENAME_KEY,
                TranslatorConfiguration.TRANSLATOR_PROPERTIES_FILENAME_DEFAULT,
                TranslatorConfiguration.TRANSLATOR_LOCAL_PROPERTIES_FILENAME_KEY,
                TranslatorConfiguration.TRANSLATOR_LOCAL_PROPERTIES_FILENAME_DEFAULT,
                TranslatorConfiguration.TRANSLATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY,
                TranslatorConfiguration.TRANSLATOR_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT);

        return trans;

    }
}
