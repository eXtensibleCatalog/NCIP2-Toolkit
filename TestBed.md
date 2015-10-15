### Overview ###

We have setup a test NCIP Toolkit that will connect to a Voyager ILS and are calling this a "test bed."

The test page for the test bed can be accessed via Firefox at http://xco-ncip.carli.illinois.edu:8080/ncipv2/ (Internet Explorer is currently not supported).

The test bed currently supports the following services.
  * Lookup User
  * Lookup Item Set
  * Renew Item - any of the item ids listed under the "Patron" section can be renewed.

The test bed will be restored daily.

The data below can be used in conjunction with the supported [SampleXML](SampleXML.md) and the above test page to manually submit NCIP requests and receive responses.  These data can also be used by NCIP Initiators/Clients when writing their initiation code.  Note that not all sample xml pages are supported by this test bed.  For example, the test bed is configured for a single institution, so UB transactions are not supported.

NCIP Initiators/Clients wishing to test the service should POST their XML requests to http://xco-ncip.carli.illinois.edu:8080/ncipv2/NCIPResponder with Content-type: application/xml and charset: utf-8.

When you include an Agency Id (sometimes it is required) in your request, use "XC"

### Item Lookups ###

To test these, you can insert any of the the bib numbers into the Lookup Item Set sample XML snippet shown below.
```
<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
```


One Bib, 3 copies, multi volume set, all available
  * Bib 21236
  * One holding 21755
  * Three items of 23161, 23162, 23163 (vols 1,2 3)

Missing item, no copy available
  * Bib 34940
  * Holding 35562
  * Item 39363 is missing

Record does not exist
  * Bib 123456

Charged Item, No copy available
  * Bib 5866
  * Holding 6049
  * Item 6633 is charged

2 volumes, one charged and one not charged
  * Bib 20071
  * Holding 20532
  * Item 21746 is not charged and Item 21747 is charged

Serial called "Science in Progress"
  * Bib 388
  * Holding 402
  * 7 items, 612-618, item 615 is charged, the rest are available

2 volumes, different temp location
  * Bib 45829
  * Holding 46482
  * Item 51809 has temp location at "Graduate Library"
  * Item 51810 is at "Main"

Multi holdings in multi location
  * Bib 41258
  * Holding #1 - 41880 with item 46271 at "Main"
  * Holding #2 - 62156 with 2 items 67934 and 67935 at "Reference Department"

### Patrons ###

To test these, you can insert any of the the Barcodes and Lastname combination's below into the Lookup User sample XML as below.
```
<ns1:AuthenticationInputData>98765432</ns1:AuthenticationInputData>
and
<ns1:AuthenticationInputData>Cook</ns1:AuthenticationInputData>

```

Patron:  Randall Cook
  * Barcode 98765432, Patron ID 381
  * Has two items check out:  American Impressionism in 1898 and History of Science, both due 10/30/2011
  * Has a hold request for "War and peace in the Global Village" that expires 8/16/2011
  * Has a Recall request for "Traditional crafts of Persia" that expires 8/16/2011
  * Has a Call slip request for "Brief History of Mathematics" for pick up location request of " Other Branch Circ. Desk"

Patron: John Bodfish
  * Barcode 87654321, Patron ID 382
  * Charged Items - "Civil War and the Constitution" and a serial called "Science in progress"

Donna Smith

  * Barcode 2639, Patron ID 7
  * Has three items check out:  "War and peace in the Global Village" (Item ID 6633), "Wonders of the Modern World" (4874), and "Traditional crafts of Persia" (5126)
  * Has two over due charges, totaling $7.00 USD.   The item "Wonders of the Modern World" has a charge of $2 and "War and Peace in a Global Village" has a charge of $5.
  * Has a Call slip request for "She who is : the mystery of God in feminist theological discourse"


Jack Scott

  * Barcode 2659, Patron ID 227
  * Nothing charged
  * Two hold requests.  He has queue position 1 for "5 spices, 50 dishes" and has queue position 2 for "Why Spy"

Sean Walker
  * Barcode 2963, Patron ID 205
  * Has three items charged and over due, due on 8/6/2008:
  * Item 63091 - "All those moments"
  * Item 63724 - "Racquetball:  steps to success"
  * Item 64209 - "They burn the thistles"
  * Has three hold requests for "If it ain't baroque...," "Politics of culture," and "They burn the thistles."

Kim Callahan
  * Barcode 4821, Patron ID 368
  * 4 charges items, all can be renewed
  * Item 62924 - "Aging, Society..."
  * Item 63110 - "Clockwork Orange"
  * Item 63230 - "Goodnight Moon..."
  * Item 63231 - "If it Ain't baroque..."