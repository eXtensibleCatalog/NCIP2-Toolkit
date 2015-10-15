## Voyager Connector ##

The Voyager connector relies on both the VXWS web service and direct connections to the Voyager Oracle database to function.  The connector requires at least Voyager 7.2 and has not been tested on 8.x.  The instructions on the [Installation](http://code.google.com/p/xcncip2toolkit/wiki/VoyagerInstallation) and [Configuration](http://code.google.com/) pages assume the use of the Tomcat web server.

Currently, the connector only returns a subset of the information specified in the NCIP spec for certain services.  Below is documented what is missing in the response messages. Italicized fields are not returned.

### Lookup User Message ###

Fiscal account info
  * Title
  * Fine fee balance
  * Create date
  * Fine fee description
  * _Item id_
  * _Charge date_
  * _Discharge date_
  * _Renew date_
  * _Due date_

Loaned items
  * Due date
  * Item ID
  * Bib ID
  * Title
  * _Fine fee balance_
  * _Overdue notice count_

Callslip requests
  * Item ID
  * Queue position
  * Status description
  * Title
  * _Date requested_
  * _Available notice date_

Holds
  * Item ID
  * Queue position
  * Status description
  * Title
  * _Create date_
  * _Available notice date_

---


### Lookup Item Set Message ###

  * Bib Level Bibliographic Description
    * Author
    * ISBN
    * ISSN
    * Title
  * Holding Set ID
  * Item ID
  * Electronic Resource
  * Call Number
  * Circulation Status
  * Item Count
  * Item Description
  * Item Location
  * Copy Numbers
  * Due Date
  * Circulation Status
  * _If not mentioned, it is omitted_
<a href='Hidden comment: 
* _Current Requester_
* _Current Borrower_
* _Date Recalled_
* _Holding Level Hold Queue Length_
* _Hold Pickup Date_
* _Item Transaction_
* _Item Note_
* _Location Info @ Holding level_
* _Physical Marker_
* _Security Marker_
* _Sensitization Flag_
* _Title Level Hold Queue Length_
'></a>