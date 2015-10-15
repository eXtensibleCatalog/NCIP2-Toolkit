The <a href='http://www.extensiblecatalog.org'>eXtensible Catalog (XC)</a> project is working to design and develop a set of open-source applications that will provide libraries with an alternative way to reveal their collections to library users.

Please visit our project website at http://www.extensiblecatalog.org for a more complete overview of the eXtensible Catalog project and the software we are creating.

## NCIP Toolkit (version 2.0 of NCIP protocol) ##

#### Note:  This project is continuing from <a href='http://code.google.com/p/xcnciptoolkit/'>XC NCIP Toolkit ver. 1 of standard</a> ####

The NCIP Toolkit will allow Discovery Interfaces (DI) to interact with an ILS for authentication requests, live circulation status lookups, and circulation requests.  The XC NCIP Toolkit uses the NCIP standard protocol to accomplish this.  Once the NCIP Toolkit has been successfully installed alongside a compatible ILS, that ILS will be able to inter-operate both with XC and non-XC NCIP clients.

The NCIP Toolkit is intended to be installed alongside a compatible ILS and act as an intermediary between an NCIP initiator (e.g. a Discovery Interface such as the XC Drupal Toolkit)  and the ILS. When a client sends an NCIP request to the toolkit, the request is parsed and sent to the ILS using its proprietary interface. The response is then translated back into the NCIP protocol and returned to the client. Discovery Interfaces can use NCIP Toolkit to provide user interface functionality that requires real-time interaction with the ILS database.

The XC team along with partner institutions and open source collaborators plan to include [NCIP toolkit connectors](http://code.google.com/p/xcncip2toolkit/wiki/DriverSummary) for a wide range of popular commercial and open-source integrated library systems.

The NCIP Protocol consists of a large number of services.  The NCIP Toolkit uses a subset of these services.  Through code contributions from the open source community, it is our hope that the inventory of supported NCIP services can grow beyond those needed for Discovery Interfaces and start to fill other circulation related needs.

We have have created a [Test Bed](http://code.google.com/p/xcncip2toolkit/wiki/TestBed?ts=1318964768&updated=TestBed) where you can try out a set of NCIP serices.

An outstanding overview (and story) of what is involved in building a new connector can be found at http://journal.code4lib.org/articles/5608.  It details the work that Lehigh University did to write a connector for Symphony.