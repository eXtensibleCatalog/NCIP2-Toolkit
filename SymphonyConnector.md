## Symphony Connector ##

The Symphony connector relies on Perl scripts which call the Symphony API. As described on the [Symphony Install Page](http://code.google.com/p/xcncip2toolkit/wiki/SymphonyInstallation) page, the Perl scripts are available on the [SirsiDynix Developers Community Website.](http://www.sirsidynix.com/sirsidynix-developer-community)

The connector has successfully been implemented with version 3.4 of SirsiDynix Symphony.

The instructions on the [Symphony Install Page](http://code.google.com/p/xcncip2toolkit/wiki/SymphonyInstallation)  page assume the use of a Jetty web server but it should work properly when deployed to a Tomcat server also.

Currently, the connector only returns a subset of the information specified in the NCIP spec for certain services.  The list below documents which services (and elements within those services) are supported.

### Lookup User Service ###

Request
  * User Id or Authentication Input
  * User Element Type
    * Name Information
    * User Address Information
    * User Language
    * User Privilege
Response
  * User Id
  * User Optional Fields
    * Name Information
    * User Address Information
    * User Language
    * User Privilege

### Accept Item Service ###

Request
  * Request Id
  * Requested Action Type
  * User Id
  * Item Id
  * Item Optional Fields
    * Bibliographic Description, - Author & Title
    * Item  Description - Call Number
  * Pickup Location

Response
  * Item Id
  * User Id
  * User Optional Fields
    * Bibliographic Description
    * Item Description

### Check Out Item Service ###

Request
  * User Id or Authentication Input
  * Item Id

Response
  * Item Id
  * User Id
  * Date Due or Indeterminate Loan Period Flag or Non Returnable Flag
  * Renewal Count


### Check In Item Service ###

Request
  * Item Id
  * Item Element Type
    * Bibliographic Description
    * Item Description

Response
  * Item Id
  * User Id
  * User Optional Fields
    * Bibliographic Description
    * Item Description

