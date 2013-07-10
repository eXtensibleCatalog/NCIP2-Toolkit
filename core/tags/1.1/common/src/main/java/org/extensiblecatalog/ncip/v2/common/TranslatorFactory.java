/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.Translator;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class TranslatorFactory {

    /**
     * This is the instance returned to all callers of getTranslator(). If you want multiple, differing
     * translators you will need to call {@link #getTranslator()} or
     * {@link #getTranslator(TranslatorConfiguration)}.
     */
    protected static Translator sharedInstance;

    protected final TranslatorConfiguration configuration;

    protected final Properties properties;

    /**
     * Construct a TranslatorFactory that can be used to construct distinct {@link Translator} objects using the
     * same construction sequence used by {@link #getSharedTranslator()}.
     * @throws ToolkitException
     */
    public TranslatorFactory() throws ToolkitException {

        configuration = null;
        properties = null;

    }

    /**
     * Construct a TranslatorFactory that will return {@link Translator} objects constructed with the passed-in
     * {@link TranslatorConfiguration}.
     *
     * @param configuration the {@link TranslatorConfiguration} to use to construct {@link Translator} instances.
     *
     */
    public TranslatorFactory(TranslatorConfiguration configuration) throws ToolkitException {

        this.configuration = configuration;
        this.properties = null;

    }

    /**
     * Construct a TranslatorFactory that will return {@link Translator} objects constructed with the passed-in
     * {@link Properties}.
     *
     * @param properties the {@link Properties} to use to construct {@link Translator} instances.
     *
     */
    public TranslatorFactory(Properties properties) throws ToolkitException {

        this.configuration = null;
        this.properties = properties;

    }

    /**
     * Get the shared Translator object. In order try the following:
     * 1) If the {@link #sharedInstance} is already set (e.g. by Spring) or a prior call to this method,
     * return that.
     * 2) Look for a "translator" bean in the AppContext and return that.
     * 3) Look for a "translatorFactory" bean in the AppContext and (to avoid infinite recursion) if it is an instance
     * of this class, call the {@link #getTranslator()} method on that object; otherwise call
     * {@link #getSharedTranslator()} method on that object.
     * 4) Look for a "translatorConfigurationFactory" bean in the AppContext and get the {@link TranslatorConfiguration}
     * object from that and pass that to {@link #getTranslator(TranslatorConfiguration)} method and return the result.
     * 5) Call {@link ConfigurationHelper#getCoreConfiguration()} method to get a {@link CoreConfiguration},
     * call its {@link CoreConfiguration#getTranslatorConfiguration()} method, pass the result to
     * {@link #getTranslator(TranslatorConfiguration)} and return that result.
     *
     * this calls {@link #getTranslator()} to construct it.
     * @return the {@link Translator} object
     * @throws ToolkitException
     */
    public static synchronized Translator getSharedTranslator() throws ToolkitException {

        // Note: even the first time through this method, it's possible that something (e.g. Spring)
        // has already set the sharedInstance. The logic here covers the use cases where that
        // has not happened, e.g. we're not running in a Spring-enabled web server.
        if ( sharedInstance == null ) {

            ApplicationContext appContext = ConfigurationHelper.getApplicationContext();

            if ( appContext != null ) {

                if ( appContext.containsBean("translator") ) {

                  sharedInstance = (Translator)appContext.getBean("translator");

                } else if ( appContext.containsBean("translatorFactory") ) {

                    TranslatorFactory translatorFactory
                        = (TranslatorFactory)appContext.getBean("translatorFactory");

                    // Protect against a recursive call, which will produce an infinite loop
                    if ( translatorFactory.getClass() == TranslatorFactory.class ) {

                        // If the AppContext has this class as the factory, call getTranslator,
                        // and *if* that class was constructed with a configuration or properties, it won't call back
                        // to this method. If it was, this will result in an infinite loop.
                        sharedInstance = translatorFactory.getNonSharedTranslator();


                    } else {

                        // Otherwise, call the TranslatorFactory in the AppContext; it's their responsibility not
                        // to call this method
                        sharedInstance = translatorFactory.getSharedTranslator();

                    }

                } else if ( appContext.containsBean("translatorConfigurationFactory") ) {

                    TranslatorConfigurationFactory translatorConfigFactory
                        = (TranslatorConfigurationFactory)appContext.getBean("translatorConfigurationFactory");

                    sharedInstance = getTranslator(translatorConfigFactory.getConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                CoreConfiguration coreConfig = ConfigurationHelper.getCoreConfiguration();
                if ( coreConfig != null ) {

                    sharedInstance = getTranslator(coreConfig.getTranslatorConfiguration());

                }

            }

            if ( sharedInstance == null ) {

                throw new ToolkitException("Unable to initialize translator.");

            }

        }

        return sharedInstance;

    }

    /**
     *
     * @return
     * @throws ToolkitException
     */
    public Translator getTranslator() throws ToolkitException {

        Translator translator = getNonSharedTranslator();

        if ( translator == null ) {

            translator = getSharedTranslator();

        }

        return translator;

    }

    /**
     * Used by {@link #getTranslator()} to get a Translator from either the {@link #configuration} or the
     * {@link #properties}, and by {@link #getSharedTranslator()} when it's retrieved an instance of itself from the
     * AppContext and so needs to avoid calling back to {@link #getSharedTranslator()} because that would cause an
     * infinite loop.
     *
     * @return
     * @throws ToolkitException
     */
    private Translator getNonSharedTranslator() throws ToolkitException {

        Translator translator = null;
        if ( configuration != null ) {

            translator = getTranslator(configuration);

        } else if ( properties != null ) {

            translator = getTranslator(properties);

        }

        return translator;

    }

    /**
     * Construct a Translator using the provided configuration.
     *
     * @param configuration the {@link TranslatorConfiguration} containing any overrides to default values
     * @return the new Translator object
     * @throws ToolkitException
     */
    public static Translator getTranslator(TranslatorConfiguration configuration) throws ToolkitException {

        Translator translator;

        // See if we have a Translator class name in the configuration, if so instantiate that with the configuration
        String translatorClassName = configuration.getTranslatorClassName();
        if ( translatorClassName != null ) {

            try {

                Class<?> configClass = Class.forName(translatorClassName);

                Constructor ctor = configClass.getConstructor(TranslatorConfiguration.class);

                translator = (Translator)ctor.newInstance(configuration);

            } catch (ClassNotFoundException e) {

                throw new ToolkitException("Exception loading translator class.", e);

            } catch (InstantiationException e) {

                throw new ToolkitException("Exception constructing translator class.", e);

            } catch (IllegalAccessException e) {

                throw new ToolkitException("Exception constructing translator class.", e);

            } catch (NoSuchMethodException e) {

                throw new ToolkitException("Exception constructing translator class.", e);

            } catch (InvocationTargetException e) {

                throw new ToolkitException("Exception constructing translator class.", e);

            }

        } else {

            throw new ToolkitException("Translator class name not set in translator configuration.");

        }

        return translator;

    }

    /**
     * Construct a Translator using the provided properties to obtain a TranslatorConfiguration object from the
     * TranslatorConfigurationFactory and then calling {@link #getTranslator(TranslatorConfiguration)} with it.
     *
     * @param properties the {@link Properties} containing any overrides to default values
     * @return the new Translator object
     * @throws ToolkitException
     */
    public static Translator getTranslator(Properties properties) throws ToolkitException {

        TranslatorConfiguration configuration = TranslatorConfigurationFactory.getConfiguration(properties);

        return getTranslator(configuration);

    }

}
