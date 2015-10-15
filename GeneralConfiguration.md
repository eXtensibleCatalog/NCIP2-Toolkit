# Introduction #

The NCIP 2 Toolkit supports configuration via Java properties files, Spring beans, or both.

## Using properties files ##
The default behavior is to look for properties files in the classpath. The default properties file name is `toolkit.properties`. Typically an ILS connector comes with a `toolkit.properties` file within it. You will either have to modify that file or override some of its values to configure the connector for your instance of the ILS (e.g. with the username and password for the ILS database). We recommend that you override the values in the connector's `toolkit.properties` file setting them in the servlet context.

## Overriding properties in the servlet context -- Tomcat ##
In tomcat you can define a servlet `Context` element outside the war file, as described in the tomcat documentation [here](http://tomcat.apache.org/tomcat-7.0-doc/config/context.html). While there are several ways to do this, the recommended approach is to create individual files (with a ".xml" extension) in the `$CATALINA_BASE/conf/[enginename]/[hostname]/` directory. Any property set in the servlet context will take precedence over those in the `toolkit.properties` file included in the war. You can even direct the toolkit to read a _different_ `toolkit.properties` file entirely.

For example, to set the URL of your Millennium system for the Millennium connector, create a `$CATALINA_BASE/conf/[enginename]/[hostname]/[appname].xml` file containing this:

```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <!-- Use this to override the connector's value for this property. -->
  <Parameter name="MillenniumConfiguration.URL" value="http://jasmine.uncc.edu" override="true"/>

</Context>
```

`enginename` defaults to `Catalina`; `hostname` defaults to `localhost`; `appname` defaults to the name of the war file, e.g. for `millennium.war` it would be `millennium`.

You can use this approach to disable a service that is supported by the connector but which you do not use. Simply override the property for that service and set its value to 'null':
```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <!-- Use this to disable a service that's defined in the connector's default toolkit.properties file. -->
  <Parameter name="LookupUserService.Class" value="null" override="true"/>

</Context>
```

Or to turn on the inclusion of stack traces in the response messages:
```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <!-- Turn on inclusion of stack traces in ProblemDetail of response messages when an exception occurs. -->
  <Parameter name="CoreConfiguration.IncludeStackTracesInProblemResponses" value="true" override="true"/>

</Context>
```

To avoid having to change the `$CATALINA_BASE/conf/[enginename]/[hostname]/[appname].xml` every time you want to change a property, you can set the `ToolkitConfiguration.LocalPropertiesFile` property to direct the Toolkit to load a second properties file containing overrides to the properties in the connector's toolkit.properties file:

```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <!-- Use this to override what file the Toolkit loads its properties from. -->
  <Parameter name="ToolkitConfiguration.LocalPropertiesFile" value="../localtoolkit.properties" override="true"/>

</Context>
```

Then create a file named `localtoolkit.properties` in the $CATALINA\_BASE directory (or wherever else you set the property to  point) and enter the property overrides there.


Finally, you can direct the Toolkit to load a _different_ properties file than the one that comes with the connector:

```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <!-- Use this to replace the toolkit.properties file entirely. -->
  <Parameter name="ToolkitConfiguration.PropertiesFile" value="../replacementtoolkit.properties" override="true"/>

</Context>
```

Then create a file named `replacementtoolkit.properties` in the $CATALINA\_BASE directory (or wherever else you set the property to  point) and enter the property overrides there.


## Overriding properties in the servlet context -- Jetty ##
The same principle can be applied to Jetty by customizing the override-web.xml file.


(Tested with Jetty 7.5.1 and 7.4.4 & the Symphony connector)

Place the override-web.xml file within a ../contexts/symphony.d/ folder (if using the symphony.war for example...or use dummy.d folder if using the dummy.war...)
```
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app 
   xmlns="http://java.sun.com/xml/ns/javaee" 
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" 
   version="2.5"> 


<!-- This web.xml format file is an override file that is applied to the test webapp AFTER
     it has been configured by the default descriptor and the WEB-INF/web.xml descriptor -->

  <!-- Add or override context init parameter -->
  <context-param>
    <param-name>SirsiScriptURL</param-name>
    <param-value>http://urlforyour.sirsiscripts.yourdomain.edu/sirsi/</param-value>
  </context-param>
  <context-param>
    <param-name>HOME_LIBRARY</param-name>
    <param-value>LEHIGH</param-value>
  </context-param>
  <context-param>
    <param-name>AGENCY_ID</param-name>
    <param-value>LEHI</param-value>
  </context-param>
  <context-param>
    <param-name>AUTH_BARCODE</param-name>
    <param-value>Barcode Id</param-value>
  </context-param>
  <context-param>
    <param-name>AUTH_UID</param-name>
    <param-value>Username</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_STAT_DESCRIPTION</param-name>
    <param-value>User Status</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_STATUS</param-name>
    <param-value>STATUS</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_USER_PROFILE_DESCRIPTION</param-name>
    <param-value>User Profile</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_PROFILE</param-name>
    <param-value>PROFILE</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_LIB_DESCRIPTION</param-name>
    <param-value>User Library</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_LIBRARY</param-name>
    <param-value>LIBRARY</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_CAT_DESCRIPTION</param-name>
    <param-value>User Category One</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_CAT</param-name>
    <param-value>CAT1</param-value>
  </context-param>
  <context-param>
    <param-name>daysUntilHoldExpires</param-name>
    <param-value>7</param-value>
  </context-param>

</web-app>

```

You will also need a symphony.xml file placed within the ../contexts/ folder.  (The symphony.xml file name matches the name of the war file -- symphony.war.  If you are using another connector.war -- the file name should match the name of the war file you are using) The symphony.xml file should look like this:

```


<?xml version="1.0"  encoding="ISO-8859-1"?>
<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure.dtd">

<!-- ==================================================================
Configure and deploy the test web application in $(jetty.home)/webapps/test

Note. If this file did not exist or used a context path other that /test
then the default configuration of jetty.xml would discover the test
webapplication with a WebAppDeployer.  By specifying a context in this
directory, additional configuration may be specified and hot deployments 
detected.
===================================================================== -->

<Configure class="org.eclipse.jetty.webapp.WebAppContext">


  <!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
  <!-- Required minimal context configuration :                        -->
  <!--  + contextPath                                                  -->
  <!--  + war OR resourceBase                                          -->
  <!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
  <Set name="contextPath">/symphony</Set>
  <Set name="war"><SystemProperty name="jetty.home" default="."/>/webapps/symphony.war</Set>
  <Set name="overrideDescriptor"><SystemProperty name="jetty.home" default="."/>/contexts/symphony.d/override-web.xml</Set>


</Configure>


```





NOTE: the above technique (defining a Context) is only useable within a webapp. Thus if your Toolkit-based app is an NCIP initiator, not a responder, see [GeneralConfiguration#Configuring\_the\_Toolkit\_in\_non-webapp\_environments](GeneralConfiguration#Configuring_the_Toolkit_in_non-webapp_environments.md).


## General Configuration Options ##
|Parameter|Default value|Notes|
|:--------|:------------|:----|
|CoreConfiguration.LoggingConfigFile|/WEB-INF/classes/log4j.properties|Set this to a log4j properties file if you want to replace the default logging configuration.|
|CoreConfiguration.LoggingDir|For tomcat: ../logs/<p />For jetty and other web servers: logs/|Set this to change the directory into which the log file will be written.|
|CoreConfiguration.IncludeStackTracesInProblemResponses|false        |Set this to true if you want Java stacktraces to be included in the ProblemDetail element returned when the Toolkit encounters an exception.|
|CoreConfiguration.SVPClasses|(The package name is ommitted from the classes in the following list; do _not_ omit the package name in setting the property.)<p />Version1AcceptItemProcessingError, Version1AuthenticationDataFormatType, Version1AuthenticationInputType, Version1BibliographicItemIdentifierCode, Version1BibliographicLevel, Version1BibliographicRecordIdentifierCode, Version1CheckInItemProcessingError, Version1CheckOutItemProcessingError, Version1CirculationStatus, Version1ComponentIdentifierType, Version1CurrencyCode, Version1ElectronicDataFormatType, Version1FiscalActionType, Version1GeneralProcessingError, Version1ItemDescriptionLevel, Version1ItemElementType, Version1ItemIdentifierType, Version1ItemUseRestrictionType, Version1Language, Version1LocationType, Version1LookupItemProcessingError, Version1LookupUserProcessingError, Version1MediumType, Version1OrganizationNameType, Version1PhysicalAddressType, Version1PhysicalConditionType, Version1RenewItemProcessingError, Version1RequestedActionType, Version1RequestElementType, Version1RequestItemProcessingError, Version1RequestScopeType, Version1RequestStatusType, Version1RequestType, Version1SecurityMarker, Version1UnstructuredAddressType, Version1UserElementType|This specifies the SchemeValuePair sub-classes that define valid values for use in NCIP messages.|
|CoreConfiguration.AddedSVPClasses|null         |Set this to a comma-delimited list of the names of any _additional_ SchemeValuePair sub-classes that define valid values for use in NCIP messages. These will be used in _addition to_ classes in the CoreConfiguration.SVPClasses list. E.g.<p />`CoreConfiguration.AddedSVPClasses=org.extensiblecatalog.ncip.v2.voyager.VoyagerCircStatus,org.extensiblecatalog.ncip.v2.voyager.VoyagerCurrencyCodes`|
|CoreConfiguration.SVPClassesAllowAny|(The package name is ommitted from the classes in the following list; do _not_ omit the package name in setting the property.)<p />AgencyId, AuthenticationDataFormatType, AuthenticationInputType, ApplicationProfileType, FromSystemId, PickupLocation, RequestIdentifierType, ToSystemId|This specifies the SchemeValuePair sub-classes that may have any combination of Scheme and Value (i.e., are not validated).|
|CoreConfiguration.AddedSVPClassesAllowAny|null         |Set this to a comma-delimited list of the names of any _additional_ SchemeValuePair sub-classes that may have _any_ combination of Scheme and Value (i.e. are not validated). These will be used in _addition_ to classes in the CoreConfiguration.SVPClassesAllowAny list.|
|CoreConfiguration.SVPClassesAllowNullScheme|(The package name is ommitted from the classes in the following list; do _not_ omit the package name in setting the property.)<p />AgencyElementType, ItemElementType, RequestElementType, UserElementType|This specifies the SchemeValuePair sub-classes that are not _required_ to have a Scheme attribute.|
|CoreConfiguration.AddedSVPClassesAllowNullScheme|null         |Set this to a comma-delimited list of the names of any _additional_ SchemeValuePair sub-classes that are not _required_ to have a Scheme attribute. These will be used in addition to classes in the CoreConfiguration.SVPClassesAllowNullScheme list.|
|ConnectorConfiguration.ConfigClass|DefaultConnectorConfiguration|Set this to the name of the class that specifies the configuration information for the connector, e.g.:<p />`ConnecvtorConfiguration.ConfigClass=org.extensiblecatalog.ncip.v2.millennium.MillenniumConfiguration`|
|

&lt;NCIP-service-name&gt;

Service.Class|(varies)     |For each supported service, this identifies the class that implements that service. If you want to disable a service that is, by default, enabled for your connector, you should change this property for that service to the value 'org.extensiblecatalog.ncip.v2.common.DefaultService'.|
|NCIPServiceValidatorConfiguration.AddDefaultNamespaceURI|false        |Set this to `true` if the NCIP messages coming into the Toolkit do not have a namespace; this will cause the Toolkit to add the XML namespace for NCIP to the document and allow the Toolkit to process the messages.|
|NCIPServiceValidatorConfiguration.DefaultNamespaceURI|http://www.niso.org/2008/ncip|This supplies the default namespace URI included in every NCIP message produced by the Toolkit.|
|NCIPServiceValidatorConfiguration.NamespaceURIs|http://www.niso.org/2008/ncip|This comma-separated list supplies the namespace URIs that will be recognized by the Toolkit.|
|NCIPServiceValidatorConfiguration.ProtocolVersion|http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd|This supplies the NCIP version added to all messages produced by the Toolkit.|
|NCIPServiceValidatorConfiguration.SchemaURLs|ncip\_v2\_01\_ils-di\_extensions.xsd|This supplies the location of the NCIP XML Schema used by the Toolkit. This can be an HTTP URL or (as in the default case) a file resource.|
|ToolkitConfiguration.PropertiesFile|toolkit.properties|To override the name and location of the toolkit.properties file, set this on the Java command line (`-DToolkitConfiguration.PropertiesFile=mytoolkit.properties`).|
|ToolkitConfiguration.PropertiesFileTitle|null         |(For developers.)<p />If set, this will appear in the properties object used by the Toolkit to perform configuration, and can be inspected while debugging or written to a log file.|
|ToolkitConfiguration.LocalPropertiesFile|localtoolkit.properties|Set this to the name of a properties file that will override any matching properties in the toolkit.properties file.<p />Note: You _must_ set this outside of the toolkit.properties file itself, e.g. on the command-line (`-D ToolkitConfiguration.LocalPropertiesFile=mylocaltoolkit.properties`) or via Spring (if using that for configuration).|
|ToolkitConfiguration.LocalPropertiesFileTitle|null         |(For developers.)<p />If set, this will appear in the properties object used by the Toolkit to perform configuration, and can be inspected while debugging or written to a log file.|
|TranslatorConfiguration.LogMessages|false        |(For developers.)<p />Set this to `true` if you want the Toolkit to write incoming and outgoing NCIP messages to the log. This can be helpful during debugging, but you should be _not_ use this in production as it will cause user credentials and other personal information to be written the the log file.|
|TranslatorConfiguration.MessagesLoggingLevel|INFO         |(For developers.)<p />Set this to the log4j logging level (`ERROR, WARN, INFO, or DEBUG`) at which you want to log NCIP messages (see TranslatorConfiguration.LogMessages).|
|DozerTranslatorConfiguration.MappingFiles|ncipv2\_mappings.xml|(For developers.)<p />Set this to the Dozer mapping file you want to use to control copying data from the JAXB-generated classes to the Toolkit's service package classes.|