## Specifications for XC NCIP Toolkit Lookup User ##

**General information**

  * [Lookup User](https://spreadsheets.google.com/ccc?key=0Ah4r3w4XYLVddGNkUEdQWUdRblJua2ZBRUt6Y3cyTFE&hl=en&authkey=CLeiw9wM) - More detailed information can be found here
  * [Lookup User Sample Request XML](http://code.google.com/p/xcncip2toolkit/wiki/lookupUser)
  * [Lookup User in protocol](http://www.niso.org/apps/group_public/download.php/5814/z39-83-1-2008_2_01.pdf#page=22)

**Of interest to developers writing a connector for a consortial environment**

It's recommended that the Agency ID of the user to be looked up be placed inside the ToAgencyId element in the Initiation Header message

### Request Specifications ###

**Fields Connector Developers must support**

One of the following:
  * Authentication Input
  * User ID

Client developers must send one of these fields in their requests

The presence of any of the following fields in the request indicates that the client would like to retrieve these fields in the response

  * Loaned Items Desired
  * Requested Items Desired
  * User Element Type
    * Name Information
    * User Address Information
  * User Fiscal Account Desired



**Fields Connector Developers must support if supported by ILS**

  * none

**Fields Connector Developer should consider supporting**

  * none

### Response Specifications ###

**General Information**

Every response will contain either a Problem or User ID field. All the other fields are optionally returned depending on the request sent. For example, User Fiscal Account fields only appear if User Fiscal Account Desired is sent in the request


**Fields Connector Developers must support**

  * Problem - this field must appear if the request fails
  * User ID - this field must appear if the request is successful
  * Loaned Items Count
  * Loaned Item
  * Requested Items Count
  * Requested Item
  * User Fiscal Account
    * Account Balance
    * Account Details
  * User Optional Fields
    * Name Information
    * User Address Information