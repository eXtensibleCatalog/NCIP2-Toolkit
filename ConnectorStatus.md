# Voyager connector #
This document details the functionality implemented and status of project.

## SVN Projects ##
  * http://xcncip2toolkit.googlecode.com/svn/connectors/voyager/7.2/branches/carli
> > This project contains latest Lookup User, Lookup Item, Lookup Item Set and Renew service. Voyager connector v0.1.0 was from this branch. Version 379.


  * https://xcncip2toolkit.googlecode.com/svn/connectors/voyager/7.2/trunk
> > This project consists of  Lookup User, Lookup Item, Lookup Item Set. All these services and suthentication is done using database calls.

  * https://xcncip2toolkit.googlecode.com/svn/core/trunk
> > This is NCIP Toolkit core project.

## Server installs ##

  * http://128.151.244.132:8080/ncipv2_v_0_0_3/index.html
> > Latest version is installed here. This has NCIPToolkit core v 0.1 and Voyager connector V0.1.0.

  * http://128.151.244.132:8080/ncipv2/index.html
> > This is old verion installed by David Fan.

## Details of voyager connector project ##
### Lookup User service ###
Implemented using database calls. Authentication is performed using LDAP and webservice calls.

### Lookup Item service ###
Implemented using database calls.

### Lookup Item Set service ###
Implemented using database calls. Implemented all elements required by UR. It is upto date and compatible with latest schema version released in NCIP Toolkit v0.1

### Renew Item service ###
Implemented using webservice. There is conflict in meaning of status code when compared with Carli Voyager v 7.1. According to V7.1 status code 2 is renew success and 3 is renew failure. But for UR Voyager V 7.2status code 3 is renew success and 2 is renew failure.
So Voyager connector version 0.1.0 has status code coded that match voyager 7.2's output.(2=failue & 3=success)

### Request Item service ###
This service is not yet completely implemented. This service will use webservice for implementation.


### driver\_config.properties ###
driver\_config.properties is the configuration file for connector. UR's database, LDAP, webservice values are configured in file available in SVN. These values will not be there in release version in google code.
I have also copy pasted contents of driver\_config.properties file that is required for installing voyager connector.

# Replace property values with Connector Service class name. Do not change property(key) name.
RemoteServiceManager.class=org.extensiblecatalog.ncip.v2.voyager.VoyagerRemoteServiceManager
LookupItemService.class=org.extensiblecatalog.ncip.v2.voyager.VoyagerLookupItemService
LookupUserService.class=org.extensiblecatalog.ncip.v2.voyager.VoyagerLookupUserService
LookupItemSetService.class=org.extensiblecatalog.ncip.v2.voyager.VoyagerLookupItemSetService
CheckOutItemService.class=
CheckInItemService.class=
RequestItemService.class=
AcceptItemService.class=
RenewItemService.class=org.extensiblecatalog.ncip.v2.voyager.VoyagerRenewItemService

# Add relevant key value pair required by connector.
# Below are voyager related properties. These can be removed if not required and new key value pairs can be added
VoyagerDatabaseUrl=jdbc:oracle:thin:@128.151.244.143:1521:VGER
VoyagerDatabaseName=rochdb
VoyagerDatabaseReadOnlyUsername=ro\_rochdb
VoyagerDatabaseReadOnlyPassword=ro\_rochdb
VoyagerUrl=http://128.151.244.119/cgi-bin/Pwebrecon.cgi
VoyagerVxwsUrl=http://webvdev.lib.rochester.edu:7014/vxws
ILSDefaultAgency=NRU
NRU=1@ROCHDB20020802130352

ExternalLDAPLocation=ldap://dsee.cc.rochester.edu
ExternalLDAPPort=389
ExternalLDAPStart=ou=people, dc=rochester, dc=edu
ExternalLDAPUsernameAttribute=uid
ExternalLDAPUrId=urid
ExternalLDAPLastname=sn


Earlier Voyager connector V 0.1 used database calls and database update for authenticating a user. It also maintained sessions for logged in user. But in version 0.1.0, webservice's AuthenticatePatron is used to authenticate user and does not involve any writes to database. Old methods that were used to authenticate are commented. Once this new v 0.1.0 is stable then old commented code can be deleted.