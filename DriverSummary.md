# What is NCIP? #

NCIP (NISO Circulation Interchange Protocol, also known as Z39.83) is a North American standard with implementations in the US, Canada, and many other countries around the world.  NCIP services facilitate the automation of tasks, the exchange of data, the ability to provide information to library staff, and the empowerment of patrons.  Each service is comprised of a request from an initiating application and a reply from a responding application.  It is possible for a single software application to play both the initiation and responding roles, but typically there are at least two applications involved.

More information about the standard and how it is maintained can be found at http://ncip.info/ and http://ncip.info/documentation.

  * Version 2.01 - http://www.niso.org/apps/group_public/download.php/5814/z39-83-1-2008_2_01.pdf

There a couple of common application areas for NCIP.  These include
  * Resource Sharing (e.g. inter library loan, consortial borrowing)
  * Self Service (e.g. check outs)
  * Web Scale Discovery (e.g. XC Drupal Toolkit, VUFind)

# What is the XC NCIP Toolkit? #

The NCIP Toolkit started out to fulfill the needs for web scale Discovery applications to obtain circulation status and patron account information but has grown to include resource sharing related services.

The NCIP Toolkit includes a core application that all implementations require and an ILS connector component that allows communication with the specific ILS.

Currently, developers from several different institutions are working collaboratively to implement and expand a subset of  supported NCIP services within the XC NCIP Toolkit.

We would like the NCIP Toolkit to be used as broadly as possible and welcome institutions interested in expanding either "end point" (either initiators or connector developers) to contact us.


## Planned Services and ILS Connectors ##

The NCIP services listed below are planned for implementation in the core toolkit.  Selecting the links will take you to a spreadsheet that details the minimal definition for the NCIP service.

A complete list of planned NCIP services and ILS connectors can be found at [Planned Services and Connectors](https://spreadsheets.google.com/ccc?key=0Ah4r3w4XYLVddDA1djJWSURJX1MxMlhMQkRwa25tVUE&hl=en&authkey=CMP9hPoM).

**Hyperlinks below will take you to a Google Doc spreadsheet that shows the minimum "goal" for an ILS connector.**

  * [Lookup Item](https://spreadsheets.google.com/ccc?key=0Ah4r3w4XYLVddDg2NnlhVGQtemRLbzVaaDdsM0ZsdFE&hl=en&authkey=CJbNr5gE) - Complete, note that many Discovery platforms do not have the required "item level" identifier that is needed
  * [Lookup Item Set (LUIS)](https://spreadsheets.google.com/spreadsheet/ccc?key=0AuEy9q5SF5GFdE9RcGhHR2QyNzBpTGo5NkhLaTBuUEE&hl=en_US) - This is a non standard NCIP service (it is in review to be a standard service), that allows requests using bibliographic level identifers and a response that includes all related items
  * [Lookup User](https://spreadsheets.google.com/ccc?key=0Ah4r3w4XYLVddGNkUEdQWUdRblJua2ZBRUt6Y3cyTFE&hl=en&authkey=CLeiw9wM) - Complete
  * [Renew Item](https://spreadsheets.google.com/spreadsheet/ccc?key=0Aitqc3TBbtGmdEtHeENMak00VW1XX3JxOERFbWdta3c&hl=en_US&authkey=CIDzt7IK) - Complete
  * [Request Item](https://docs.google.com/document/pub?id=11Qm4UoEdL6wu49fOhvPj4siliu-hOt4RJGMKNbt9H-U) - Is supported
  * Lookup Request - Is supported
  * Update Request Item - Is supported
  * Cancel Request Item - Is supported
  * Check Out Item - Is supported
  * Check In Item - Is supported
  * Accept Item - Is supported