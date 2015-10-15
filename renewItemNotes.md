## Specifications for XC NCIP Toolkit Renew Item ##

### Request Specifications ###

**General information**

  * [Renew Item](https://spreadsheets.google.com/spreadsheet/ccc?key=0Aitqc3TBbtGmdEtHeENMak00VW1XX3JxOERFbWdta3c&hl=en_US&authkey=CIDzt7IK) - More detailed information can be found here.
  * [Renew Item Sample Request XML](http://code.google.com/p/xcncip2toolkit/wiki/renewItem)
  * [Renew Item in protocol](http://www.niso.org/apps/group_public/download.php/5814/z39-83-1-2008_2_01.pdf#page=33)


**Fields Connector Developers must support**

  * Authentication Input - This field must be sent by the client
  * Item ID - This field must be sent by the client


**Fields Connector Developers must support if supported by ILS**

  * none

**Fields Connector Developer should consider supporting**

  * none

### Response Specifications ###

**General Information**

Because the main purpose of this message is to renew an item, we have chosen to limit the fields the response returns

**Fields Connector Developers must support**

  * Problem - This field must be returned in the response if request fails
  * Item ID - This field MUST be returned in the response if request succeeds
  * User ID - This field MUST be returned in the response if request succeeds

**Fields Connector Developers must support if supported by ILS**

  * none

**Fields Connector Developer should consider supporting**

  * none