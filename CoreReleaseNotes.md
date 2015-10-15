# Release 1.1 #
### Fixes ###
  * Correct text in message written when no XML Schema validation will be performed from "SchemaURLs is empty; using default."" to "SchemaURLs is empty; no XML validation will be performed."
  * Add support for parsing NCIP messages that lack the required namespace
  * Added LoanedItem and RequestedItem elements to LookupUserResponseSample1.xml, showing how to set BibliographicRecordIdentifier.
  * Changed NCIP Schema default to ncip\_v2\_01\_ils-di\_extensions.xsd.

### Additional functionality ###
  * Configuration is now done, by default, through properties files. It can also or instead be done through Spring configuration. This allows a single application to use multiple sets of Toolkit objects (e.g. two different connectors) each configured differently, which is essential for some users (e.g. NCIP clients, NCIP gateways, etc.). Each connector war will have a "default" toolkit.properties file, and typically a library will have to override some of those defaults (e.g. with their local OPAC URL or JDBC connection URL); this will be done via a tomcat "context descriptor" file - see [here](GeneralConfiguration#Overriding_properties_in_the_servlet_context.md) for details.
  * Support for configuration of several details of the NCIP messages, e.g. namespace URIs and NCIP version (e.g. in the version attribute). See [here](GneralConfiguration.md) for the entire list.

### Documentation ###
  * Installation section re-written for changed process with 1.1 release (now each connector comes in a complete, self-contained war; no need to download the core plus a connector, etc.)
  * Configuration section re-written for use of properties.
  * FAQ section added.


---


# Release 1.0 #
### Fixes ###
  * Fixed "org.xml.sax.SAXParseException: cvc-complex-type.2.4.a: Invalid content was found starting with element 'ns1:Title'. One of '{"http://www.niso.org/2008/ncip":ItemId, "http://www.niso.org/2008/ncip":RequestId}' is expected.]" error when LookupUserResponse contains RequestedItem.
  * Fixed NCIP initiator class (NCIPImplProf1Client) to use connect & read timeouts supplied to constructor, or set via setters; also upped the readTimeout default to 3 minutes.
  * Set Scheme attribute on ProblemType element returned for parsing exceptions.
  * Added DatatypeConverter to handle NCIP dateTime parsing and formatting so incoming messages (e.g. from XC UI) must conform to the NCIP requirements or be close (see NotesOnFormatting.txt in sampleNCIPMessage folder for details).
  * Corrected NCIPServlet to catch **all** exceptions and return NCIP Problem responses, making the core more strictly conformant to the NCIP standard.
  * Added all Version1 Scheme/Value pair enumerations to configuration files so that they will be recognized during parsing of incoming messages.
  * General code clean-up.

### Additional functionality ###
  * Implemented Dozer-based copying to/from JAXB-generated classes; this simplifies maintenance and addition of new services.
  * All service classes representing enumerated values for use in NCIP's Scheme/Value pair elements are now type-specific, i.e. can only be used in an appropriate element.
  * Improved TestJAXBTranslator: added facility to do line-by-line diff on sample messages; added default filenames pattern and a fileNamesToOmitPattern to exclude files that match the filenames pattern; and it now deletes the temporary files it created.
  * Added some data to Dummy responder's response messages.
  * Added LoadTest class. See class's Javadoc for instructions on how to run it.
  * Added ToolkitStatisticsBean and StatisticsReportServlet to support viewing responder performance dynamically.

### Documentation ###
  * Added NotesOnFormatting.txt to sampleNCIPMessage folder explaining how to format XML files for testing.
  * Added comments about logging of confidential user information if logging level for org.dozer package is set to DEBUG. Also added recommendations about other packages that aren't worth setting to DEBUG logging.


---


# Release 0.1.2 #

  * Fixed [issue 26](https://code.google.com/p/xcncip2toolkit/issues/detail?id=26)
  * Fixed [issue 27](https://code.google.com/p/xcncip2toolkit/issues/detail?id=27): http://code.google.com/p/xcncip2toolkit/issues/detail?id=27
  * Switched to a static JAXBContext to improve throughput. This seems to have cut response time from 2-3 seconds to << 1 second.
  * Added tests of whether initiator wants the data before returning optional data (e.g. name, fiscal account, etc.)
  * Fix handling of ItemId and RequestId in RequestItem and correct element name in error messages from "StructuredAddress" to "RequestedItem".
  * Recall was added to XcRequestType.