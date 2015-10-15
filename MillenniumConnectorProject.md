# Millennium Connector Project #
This document details the functionality implemented and status of project.

## SVN Projects ##
  * Millennium Project - [https://xcncip2toolkit.googlecode.com/svn/connectors/III/trunk](https://xcncip2toolkit.googlecode.com/svn/connectors/III/trunk)
  * NCIP Toolkit core -  [https://xcncip2toolkit.googlecode.com/svn/core/trunk](https://xcncip2toolkit.googlecode.com/svn/core/trunk)
## Setup/Installing ##
> Please follow the instruction in [How to setup your development environment to develop a connector](http://code.google.com/p/xcncip2toolkit/wiki/DevEnvironmentInstall)

## Details of Millennium Connector Project ##
> ### Lookup User Service ###
> > Implemented using screen capture the html return page

> ### Lookup Item Service ###
> > Implemented using screen capture the html return page

> ### Lookup Item Set Service ###
> > Implemented using screen capture the html return page

> ### Renew Item Service ###
> > Implemented using screen capture the html return page

> ### Request Item service ###
> > Implemented using screen capture the html return page

> ### Cancel Request Item Service ###
> > Implemented using screen capture the html return page
## Connector Properties ##

> There are two types of properties you can setup:

> ## Millennium Context Properties ##
> [Configuration/Millennium Connector Configuration Properties](http://code.google.com/p/xcncip2toolkit/wiki/MillenniumConfiguration)

> ## Millennium Toolkit Properties ##
> > toolkit.properties is the configuration file for connector.
```
ToolkitConfiguration.PropertiesFileTitle=MillenniumMain
ConnectorConfiguration.ConfigClass=org.extensiblecatalog.ncip.v2.millennium.MillenniumConfiguration
RemoteServiceManager.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager
CancelRequestItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumCancelRequestItemService
LookupItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumLookupItemService
LookupItemSetService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumLookupItemSetService
LookupUserService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumLookupUserService
RenewItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumRenewItemService
RequestItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumRequestItemService

# These services below are not supported by Millennium yet - 02/20/2012
#AcceptItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumAcceptItemService
#CheckInItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumCheckInItemService
#CheckOutItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumCheckOutItemService
#UpdateRequestItemService.Class=org.extensiblecatalog.ncip.v2.millennium.MillenniumUpdateRequestItemService
CoreConfiguration.IncludeStackTracesInProblemResponses=true

CoreConfiguration.AddedSVPClassesAllowAny=org.extensiblecatalog.ncip.v2.service.CirculationStatus,org.extensiblecatalog.ncip.v2.service.FiscalActionType,org.extensiblecatalog.ncip.v2.service.FiscalTransactionType,org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId,org.extensiblecatalog.ncip.v2.service.RequestType,org.extensiblecatalog.ncip.v2.service.RequestScopeType,org.extensiblecatalog.ncip.v2.service.UserIdentifierType


# These lines below need to be set for XC connector

MillenniumConfiguration.URL=jasmine.uncc.edu

MillenniumConfiguration.Port=80

MillenniumConfiguration.LibraryName=Atkins Library

MillenniumConfiguration.DefaultAgency=circ, gen

MillenniumConfiguration.CallNoLabel=CALL #

MillenniumConfiguration.LocationLabel=Location

MillenniumConfiguration.LibraryHasLabel=Lib. Has

MillenniumConfiguration.SearchScope=S0

MillenniumConfiguration.Functions=Cancel, Renew, Request

MillenniumConfiguration.LDAPUserVariable=extpatid

MillenniumConfiguration.LDAPPasswordVariable=extpatpw

MillenniumConfiguration.PatronUserVariable=name

MillenniumConfiguration.PatronPasswordVariable=code

MillenniumConfiguration.AuthenticationType=both
```