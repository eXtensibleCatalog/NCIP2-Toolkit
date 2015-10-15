# Configuration #
  1. Where do I find the log file?
> > Look in the logs directory of your webserver (e.g. for tomcat, in `$CATALINA_HOME/logs`) a file named `<warname>toolkit.log` (where `<warname>` is the name of the war file containing the toolkit, e.g. `millennium.war`). If there is no such file, then look for a file named `toolkit.log` in the same directory.
  1. How can I change what gets logged?
> > You can override the log4j.properties file used by the Toolkit by setting the [CoreConfiguration.LoggingConfigFile](GeneralConfiguration.md) parameter. This approach ensures the setting will continue to be used even if you replace the war file with a newer version. Alternatively you could edit the log4j.properties file in the exploded webapp (typically $CATALINA\_HOME/webapp/`<warname>`/WEB-INF/classes/log4j.properties), but that change will be lost if you deploy a new copy of the war file.
  1. How do I configure my connector (i.e. set the properties to something other than the defaults)?
> > Simply edit the values (or add properties and set their values) in the context descriptor you created when you installed the connector.
  1. How do I turn off XML Schema validation?
> > Put the following in your toolkit.properties file:
> > > `NCIPServiceValidatorConfiguration.SchemaURLs=`

> > Note that there’s nothing after the equals sign; the effect is to suppress the default behavior where the Toolkit loads the ILS DI schema (which incorporates the NCIP 2.01 schema). With that set you should see a DEBUG-level message in the log file like this:
> > > `SchemaURLs is empty; no XML validation will be performed.`
  1. How can I get more information when there's an error?
    * Turn the logging level up to DEBUG (see FAQ #2 above).
    * Set the [CoreConfiguration.IncludeStackTracesInProblemResponses](GeneralConfiguration.md) property to `true`.
    * If the error is related to parsing or unmarshaling an NCIP message, set the [TranslatorConfiguration.LogMessages](GeneralConfiguration.md) property to `true`, and make sure the [TranslatorConfiguration.MessagesLoggingLevel](GeneralConfiguration.md) property is set to a level that will appear in the log file (see FAQ #2 above).
  1. Some NCIP initiators omit the NCIP namespace from their messages; how do I configure the Toolkit to accept those messages?

> > Set the [NCIPServiceValidatorConfiguration.AddDefaultNamespaceURI](GeneralConfiguration.md) property to `true`.
  1. How do I disable a service (e.g. LookupUser) that is supported by my connector?
> > Set the [LookupUserService.Class](GeneralConfiguration.md) property in the context descriptor (changing 'LookupUser' to the name of the service you want to disable) to `org.extensiblecatalog.ncip.v2.common.DefaultService`.
  1. How can I run multiple instances of a Toolkit connector (e.g. a test and a production) each with their own settings for the properties?
> > Your initial deployment consisted of two files in your tomcat directories: the war file and the context descriptor. Here are the steps to create another instance of the same web application, but with different properties:
      1. Create a second copy of the [context descriptor](CoreInstallation.md) (step #2), setting the properties as appropriate, and name it differently from your existing context descriptor, e.g prefix it with `test`.
      1. Copy this new context descriptor to the same directory as your original context descriptor (by default, `$CATALINA_BASE/conf/Catalina/localhost/`).
      1. Create a second copy of the war file, naming it to match the new context descriptor, e.g. prefix it with `test`.
      1. Copy this new war file to the same `$CATALINA_BASE/webapps` directory as your original war file.
      1. Re-start tomcat; your existing instance should still be available at `http://localhost:8080/<warname>` (where `<warname>` is the name of your original war file) and the new instance should be available at `http://localhost:8080/test<warname>`.
  1. How well does the Toolkit perform?
> > Average response time, measured from entry to exit of the HttpServlet::!doPost method, was well under 10 milliseconds for 4,000 service invocations. The test was conducted on a Dell Latitude laptop running Windows 7 Enterprise with a Intel(R) Core(TM) i5-2520M CPU @ 2.50GHz. It used the LookupItemSet service, which returned some minimal bibliographic data for one bib with 1-3 items. The test used the Dummy connector, so it _does **not** include time spent interacting with the ILS_, which is obviously going to be the greatest portion of processing for any NCIP responder.