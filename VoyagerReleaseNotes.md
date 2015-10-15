## Release 1.1 ##

  * Changes made to be compatible with the 1.1 release of the NCIP v2 Toolkit. See the [core release notes](http://code.google.com/p/xcncip2toolkit/wiki/CoreReleaseNotes) for more information.
  * Fixed bug in LUIS where if a single record was invalid the entire response was flagged invalid.
  * Fixed bug in LUIS where bib records without holding records were returning an invalid record message.
  * Improved error responses in LookupUser and LUIS to be better inline with the NCIP protocol.
  * Reintegrated ODBC support.  LookupUser now returns the bib Id of an item in its response.

## Release 1.0 ##

Connector has been made compliant with the NCIP 2 Toolkit v. 1.0 core release and is not backward compatible.

## Release 0.2.0 ##

### Enhancements ###

  * The following services are implemented in version 0.2.0 using the Voyager 7.2 RESTful web services and are now fully UB enabled: Renew Item, LUIS and Lookup User.
  * To lookup a UB user, include the agency ID of the patron in the Initiation header like so:
```
<ns1:InitiationHeader>
  <ns1:ToAgencyId>
    <ns1:AgencyId>UIU</ns1:AgencyId>
  </ns1:ToAgencyId>
</ns1:InitiationHeader>
```
> See the sample XML for a complete example.

  * To renew an item charged to a UB patron, include the agency ID of the patron in the initiation header just like you would for LU and include the agency ID of the item in the Item Id.  Again, see the sample XML page for an example.

  * Lookup User now supports using the UserID field if it's available to the initiator (The Voyager Patron ID).  See the sample XML page for an example.

### Changes from the previous version ###

  * LUIS now uses the BibliographicRecordId instead of BibliographicItemId in the request.  BibliographicRecordId requires both a  BibliographicRecordIdentifier and an AgencyId to be submitted in the request.
  * Location was removed from the Holding set level in the LUIS response.
  * The configuration option MultipleTomcats was added to the driver config.  In almost all cases this should be set to false unless you run NCIP in a consortium that uses a separate Tomcat instance for each database instance.
  * The configuration option MaxLuisItems was added and determines the number of items that LUIS will return in a response.  Access subsequent items using the resumption token.
  * Removed the deprecated configuration options for Voyager database name, database URL, OPAC URL, and database read and write user credentials.

### Major bug fixes ###

  * LUIS returns the correct location of an item, whether temporary or permanent and only once.
  * Recall requests were being classified as Holds. Recall was added to XcRequestType in the core.
  * Fine amounts now show the correct amount as defined by the NCIP schema.
  * Patrons with callslip requests sometimes resulted in LU returning a null pointer exception.

## Release 0.1.1 ##

  * Fixed [issue 21](https://code.google.com/p/xcncip2toolkit/issues/detail?id=21): http://code.google.com/p/xcncip2toolkit/issues/detail?id=21
  * Fixed an issue where holdings with e-resources (and no item ids) was causing the toolkit to fail.  Bibs with electronic resources now return the link in the holding set response
  * Submitting a non-existent bib id to the toolkit was causing it to fail instead of returning a problem response.  This has been fixed.
  * There was a problem with the resumption token not correctly resuming at the correct item record.  This has been fixed.
  * Documented the driver\_config file.