/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;

public interface CoreConfiguration extends ToolkitConfiguration {

    final String CORE_PROPERTIES_FILE_TITLE_KEY = "CoreConfiguration.PropertiesFileTitle";
    final String CORE_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String CORE_LOCAL_PROPERTIES_FILE_TITLE_KEY = "CoreConfiguration.LocalPropertiesFileTitle";
    final String CORE_LOCAL_PROPERTIES_FILE_TITLE_DEFAULT = null;

    final String CORE_CONFIG_CLASS_NAME_KEY = "CoreConfiguration.ConfigClass";
    final String CORE_CONFIG_CLASS_NAME_DEFAULT = DefaultCoreConfiguration.class.getName();

    final String CORE_PROPERTIES_FILENAME_KEY = "CoreConfiguration.PropertiesFile";
    final String CORE_PROPERTIES_FILENAME_DEFAULT = "core.properties";

    final String CORE_LOCAL_PROPERTIES_FILENAME_KEY = "CoreConfiguration.LocalPropertiesFile";
    final String CORE_LOCAL_PROPERTIES_FILENAME_DEFAULT = "${ToolkitConfiguration.AppName}core.properties";

    final String CORE_CONFIG_PROPERTIES_FILE_OVERRIDE_KEY = "CoreConfiguration.PropertiesFileOverride";
    final String CORE_CONFIG_PROPERTIES_FILE_OVERRIDE_DEFAULT = null;

    final String CORE_CONFIG_FILE_NAME_KEY = "CoreConfiguration.SpringConfigFile";
    // There is no 'default' Spring configuration file - if you want the Toolkit to use Spring outside of the
    // webapp (where the ApplicationContext is loaded by the web server per web.xml) you must set the
    // {@link #CORE_CONFIG_FILE_NAME_KEY} property to point to the Spring configuration file, e.g.
    // "resources\coreconfig.xml".
    final String CORE_CONFIG_FILE_NAME_DEFAULT = null;

    final String CORE_CLASS_NAME_KEY = "CoreConfiguration.ClassName";
    final String CORE_CLASS_NAME_DEFAULT = null;


    final String CORE_LOGGING_CONFIG_FILE_NAME_KEY = "CoreConfiguration.LoggingConfigFile";
    final String CORE_LOGGING_CONFIG_FILE_NAME_DEFAULT = "/WEB-INF/classes/log4j.properties";
    final String CORE_LOGGING_CONFIG_FILE_NAME_IN_WAR_KEY = "CoreConfiguration.LoggingConfigFileInWar";
    final String CORE_LOGGING_CONFIG_FILE_NAME_IN_WAR_DEFAULT = "log4j.properties";

    /**
     * The directory to which log files should be written; the default value is "logs/".
     * If using Spring this property is overridden by the
     * {@link ToolkitServletContextListener} as follows:
     * <li>Under tomcat: "../logs/"  (as that usually starts from the CATALINA_BASE/bin directory)</li>
     * <li>Under jetty:  "logs/" (as that usually starts from jetty.home directory)</li>
     * <li>All others: "logs/" (as that seems unlikely to fail)</li>
     */
    final String CORE_LOGGING_DIR_KEY = "CoreConfiguration.LoggingDir";
    final String CORE_LOGGING_DIR_DEFAULT = "logs/";
    final String CORE_TOMCAT_LOGGING_DIR_DEFAULT = "../logs/";
    final String CORE_JETTY_LOGGING_DIR_DEFAULT = "logs/";

    final String CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_KEY = "CoreConfiguration.IncludeStackTracesInProblemResponses";
    final String CORE_INCLUDE_STACK_TRACES_IN_PROBLEM_RESPONSES_DEFAULT = "False";

    final String CORE_SCHEME_VALUE_PAIR_CLASSES_LIST_KEY = "CoreConfiguration.SVPClasses";
    final String CORE_SCHEME_VALUE_PAIR_CLASSES_LIST_DEFAULT = Version1AcceptItemProcessingError.class.getName() + ","
            + Version1AgencyAddressRoleType.class.getName() + ","
            + Version1AgencyElementType.class.getName() + ","
            + Version1AuthenticationDataFormatType.class.getName() + ","
            + Version1AuthenticationInputType.class.getName() + ","
            + Version1BibliographicItemIdentifierCode.class.getName() + ","
            + Version1BibliographicLevel.class.getName() + ","
            + Version1BibliographicRecordIdentifierCode.class.getName() + ","
            + Version1CancelRequestItemProcessingError.class.getName() + ","
            + Version1CheckInItemProcessingError.class.getName() + ","
            + Version1CheckOutItemProcessingError.class.getName() + ","
            + Version1CirculationStatus.class.getName() + ","
            + Version1ComponentIdentifierType.class.getName() + ","
            + Version1CurrencyCode.class.getName() + ","
            + Version1ElectronicAddressType.class.getName() + ","
            + Version1ElectronicDataFormatType.class.getName() + ","
            + Version1FiscalActionType.class.getName() + ","
            + Version1FiscalTransactionType.class.getName() + ","
            + Version1GeneralProcessingError.class.getName() + ","
            + Version1ItemDescriptionLevel.class.getName() + ","
            + Version1ItemElementType.class.getName() + ","
            + Version1ItemIdentifierType.class.getName() + ","
            + Version1ItemUseRestrictionType.class.getName() + ","
            + Version1Language.class.getName() + ","
            + Version1LocationType.class.getName() + ","
            + Version1LookupItemProcessingError.class.getName() + ","
            + Version1LookupRequestProcessingError.class.getName() + ","
            + Version1LookupUserProcessingError.class.getName() + ","
            + Version1MediumType.class.getName() + ","
            + Version1MessagingError.class.getName() + ","
            + Version1OrganizationNameType.class.getName() + ","
            + Version1PaymentMethodType.class.getName() + ","
            + Version1PhysicalAddressType.class.getName() + ","
            + Version1PhysicalConditionType.class.getName() + ","
            + Version1RenewItemProcessingError.class.getName() + ","
            + Version1RequestedActionType.class.getName() + ","
            + Version1RequestElementType.class.getName() + ","
            + Version1RequestItemProcessingError.class.getName() + ","
            + Version1RequestScopeType.class.getName() + ","
            + Version1RequestStatusType.class.getName() + ","
            + Version1RequestType.class.getName() + ","
            + Version1SecurityMarker.class.getName() + ","
            + Version1UnstructuredAddressType.class.getName() + ","
            + Version1UserAddressRoleType.class.getName() + ","
            + Version1UpdateRequestItemProcessingError.class.getName() + ","
            + Version1UserElementType.class.getName() + ","
            + Version1UserIdentifierType.class.getName();

    final String CORE_SCHEME_VALUE_PAIR_ADDED_CLASSES_LIST_KEY = "CoreConfiguration.AddedSVPClasses";
    final String CORE_SCHEME_VALUE_PAIR_ADDED_CLASSES_LIST_DEFAULT = null;

    final String CORE_SCHEME_VALUE_PAIR_ALLOW_ANY_CLASSES_LIST_KEY = "CoreConfiguration.SVPClassesAllowAny";
    final String CORE_SCHEME_VALUE_PAIR_ALLOW_ANY_CLASSES_LIST_DEFAULT = AgencyId.class.getName() + ","
            + AuthenticationDataFormatType.class.getName() + ","
            + AuthenticationInputType.class.getName() + ","
            + ApplicationProfileType.class.getName() + ","
            + FromSystemId.class.getName() + ","
            + PickupLocation.class.getName() + ","
            + RequestIdentifierType.class.getName() + ","
            + ToSystemId.class.getName();

    final String CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_ANY_CLASSES_LIST_KEY = "CoreConfiguration.AddedSVPClassesAllowAny";
    final String CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_ANY_CLASSES_LIST_DEFAULT = null;

    final String CORE_SCHEME_VALUE_PAIR_ALLOW_NULL_SCHEME_CLASSES_LIST_KEY = "CoreConfiguration.SVPClassesAllowNullScheme";
    final String CORE_SCHEME_VALUE_PAIR_ALLOW_NULL_SCHEME_CLASSES_LIST_DEFAULT = AgencyElementType.class.getName() + ","
            + ItemElementType.class.getName() + ","
            + RequestElementType.class.getName() + ","
            + UserElementType.class.getName();

    final String CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_NULL_SCHEME_CLASSES_LIST_KEY = "CoreConfiguration.AddedSVPClassesAllowNullScheme";
    final String CORE_SCHEME_VALUE_PAIR_ADDED_ALLOW_NULL_SCHEME_CLASSES_LIST_DEFAULT = null;

    /**
     * Returns the ConnectorConfiguration.
     */
    ConnectorConfiguration getConnectorConfiguration() throws ToolkitException;

    /**
     * Returns the MessageHandlerConfiguration.
     */
    MessageHandlerConfiguration getMessageHandlerConfiguration() throws ToolkitException;

    /**
     * Returns the ServiceValidatorConfiguration.
     */
    ServiceValidatorConfiguration getServiceValidatorConfiguration() throws ToolkitException;

    /**
     * Returns the TranslatorConfiguration.
     */
    TranslatorConfiguration getTranslatorConfiguration() throws ToolkitException;

    /**
     * Returns the StatisticsBeanConfiguration.
     */
    StatisticsBeanConfiguration getStatisticsBeanConfiguration() throws ToolkitException;

    /**
     * Returns the ProtocolVersionConfiguration.
     */
    ProtocolVersionConfiguration getProtocolVersionConfiguration() throws ToolkitException;

    /**
     * Tests whether this component's configuration property has been set (so that BaseConfigurationFactory
     * can avoid calling it and creating an infinite loop if the {@link #getConfiguration(String)} method performs
     * lazy initialization of it).
     */
    boolean isConfigurationSet(String componentName) throws ToolkitException;

    ToolkitConfiguration getConfiguration(String componentName) throws ToolkitException;

    /**
     * Whether or not to include Java stacktraces in the NCIP Problem response elements. Doing so exposes
     * implementation details about the application code "behind" the responder, which an organization may not
     * want to do.
     */
    boolean getIncludeStackTracesInProblemResponses();

    void setIncludeStackTracesInProblemResponses(boolean setting);


}
