## Specifications for XC NCIP Toolkit Request Item ##

### Request Specifications ###

**General information**

We decided to use Request Item for three types of request including: holds when item is checked out, holds when item is available on shelf, direct requests for an item by faculty where they wish the item to be delivered to their office

  * [Request Item in protocol](http://www.niso.org/apps/group_public/download.php/5814/z39-83-1-2008_2_01.pdf#page=35)

**Fields Connector Developers must support**

  * UserID - Client must send this field
  * Bibliographic ID - Client must send this field
  * Request Type - Client must send this field
    * Hold - item is held for patron at location
    * Loan - item is checked out to patron and sent to them
  * Request Scope Type - Client must send this field
    * Must support bibliographic level holdings
    * Item level holds are not required to be supported at this time
  * Pickup Location - Client must send this field
    * List of Pickup locations can be defined in several ways
      * Discovery Clients can have a configuration file with this information
      * Lookup Agency can be used to get this information
        * Connector developers can get this dynamically from the ILS or from a configuration file
        * Connector developers should output this as human readable strings but are responsible for writing code to translate these strings back into the appropriate ILS codes

**Fields Connector Developers must support if supported by ILS**

  * NeedBeforeDate - This should be used if system for which connector is being written supports this type of field (for example hold expiration date)

**Fields Connector Developer should consider supporting**

  * None

### Response Specifications ###

**General Information**

We are not explicitly planning for or handling requests for items that require fees in this version of this specification

**Fields Connector Developers must support**

  * UserID - Response must include this field
  * RequestType - Response must include this field
  * RequestScopeType - Response must include this field <br>this can be a different scope than the one sent in the request<br>
<ul><li>ItemID - Response must include this field if hold is resolved to a specific item<br>
</li><li>RequestID - Response must include this field <br />Connector developers should build this if their ILS doesn’t provide one. Suggested syntax bibliographic id + date time stamp of request