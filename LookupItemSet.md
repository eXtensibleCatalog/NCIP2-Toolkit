## Specifications for XC NCIP Toolkit Lookup Item Set (LUIS) ##

### Request Specifications ###

**General information**

Look Up Item Set (LUIS) is a new, and as of yet, non-standard NCIP service that has been designed by members of the ILS-DI discussion group and the eXtensible Catalog Organization’s NCIP Toolkit developers.

  * Many libraries today are implementing new discovery interfaces (e.g. the eXtensible Catalog, VuFind, WorldCat Local, Blacklight, Summon, Primo) that integrate the library’s collection with other resources and offer features not available in their ILS’s OPAC such as faceted browsing.
  * Often these discovery interfaces use indexes built from an extract of their catalog’s bibliographic information. Often, item level identifiers are not included in this index
  * With this architecture there is a requirement to retrieve real-time circulation status, location and other item data from the ILS, as defined by the “GetAvailability” service in the ILS-DI specifications (http://www.diglib.org/architectures/ilsdi/DLF_ILS_Discovery_1.1.pdf#page=26).

NCIP does have a Lookup Item services but it is limited to one item per service and requires the item identifier be used.   Using this would be inefficient, and in some cases impossible to use. The Lookup Item Set service is intended to overcome that limitation.

  * [Lookup Item Set (LUIS)](https://spreadsheets.google.com/spreadsheet/ccc?key=0AuEy9q5SF5GFdE9RcGhHR2QyNzBpTGo5NkhLaTBuUEE&hl=en_US) - More detailed information can be found here.
  * [Lookup Item Set (LUIS)Sample Request XML](http://code.google.com/p/xcncip2toolkit/wiki/lookupItemSet)

**Fields Connector Developers must support**

  * Bibliographic ID
  * Holdings Set ID - some systems do not have Holding records or Holding sets
  * Item ID

Client developers must send one of these fields in their requests

_Note_ - most of the ILS connectors only currently support use of Bibliographic IDs.  If you are referencing the bibliographic record of the local system, then be sure to the BibliographicRecordIdentifier (as opposed to the BibliographicItemIdentifier.  See sample below.
```
                <ns1:BibliographicRecordId>
                        <ns1:BibliographicRecordIdentifier>9229</ns1:BibliographicRecordIdentifier>
                        <ns1:AgencyId>NIU</ns1:AgencyId>
                </ns1:BibliographicRecordId>
```


**Recommended fields**

  * Maximum Items Count - the initiator can set the upper limit of what it wants to receive, and the connector developer needs to code to honor this
  * Next Item Token - this should never be included on an initial request.  If a response includes one, that means there are more records that meet the initial request and the initiator should put the token in a subsequent request and continue to do so until no token is returned in a response.

_Note on Next Item Token_ - With Lookup Item Set, it is possible for the response message size to become so large as to be unparseable or otherwise unuseable by the initiator.  The initiation message provides a Maximum Items Count element that limits the maximum number of items that the responder may return in the response message. There is still a potential for the response message size to vary greatly due to the amount of data associated with each item, and initiators are expected to account for this possibility when they set its value.

If a responder determines that the number of items matching the identifiers in the initiation message would exceed the Maximum Items Count, it should limit the number that are actually returned to less than or equal to the Maximum Items Count and also return a Next Item Token in the response message. When there are no further items to be returned, the Next Item Token must be omitted from the response.

The exact nature of the string value of a Next Item Token is not defined by this standard, but the following guidelines to connector developers are suggested:
  1. The token’s size should be small relative to the rest of the response message so as not to defeat its purpose.
  1. Some examples of what the token might be are: a key to temporary storage such as a web server’s session object, a row number in a result set, or the address of an object in memory.
  1. The token is intended to be opaque to the initiator, i.e. the initiator should not expect its value to be meaningful.
  1. As the examples in #2 suggest, the intent is that the responder could, if it gets this token back in a subsequent message, identify the remaining items records to return in that response.

### Response Specifications ###

**General Information**

The response should include all the records related for the given identifier.  For example, if a Bib record has 3 holdings, and those holdings each have 5 items, then the response will include data for all 15 items.

**Fields Connector Developers must support**

  * Problem - this field must appear if the request fails
  * Bib information - A wrapper for bibliographic and holdings information useful when describing a set of Items sharing common bibliographic or holdings data.

**Recommended fields**

  * Next Item Token - see above


Note:
  * there are data elements such as "location" that exist both within Holding Set and within Item Information.   The lower level supercedes.  To keep things simple, the ILS Interop group decided that a connector developer would only use the higher level element, if it could be safely assumed to apply to all the lower level items.  This was to save the the initiator from having to parse over the data.
  * HoldingSet - A wrapper for holdings and Item information useful when describing a set of Items sharing common holdings data.  For systems that do not have a “holdings” entity (i.e., items are “attached” directly with bibs), some of these elements would be omitted (e.g. the Holdings Set Id), and the contained Item Information element would contain all of the Items associated with the bib described by the parent Bib Information element.