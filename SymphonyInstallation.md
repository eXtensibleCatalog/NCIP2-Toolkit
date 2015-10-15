# Symphony Installation #
## Prework: ##

  1. Download, configure, install and test the Perl scripts that will communicate with the Symphony api:
> > #### Download ####
> > Download the XCNCIP2ToolkitScripts.zip from [www.sirsiapi.org](http://www.sirsiapi.org/).  This zip file contains one script for each supported service (acceptItem,checkinItem,checkoutItem,lookupUser).
> > The user id and password for the [www.sirsiapi.org](http://www.sirsiapi.org/) site are available from the [SirsiDynix API Wiki](http://clientcare.sirsidynix.com/index.php?goto=Knowledge&ApiWiki).
> > #### Configure ####
> > Each of the scripts will have to be modified to run on your server (e.g. Path to Unicorn) and to use your institutions data codes (e.g. FE - STATION LIBRARY,  FW - STATION USER'S USER ID).  Notes pertaining to this are included within the script files.
> > #### Install & Test ####
> > These scripts can be tested outside of the Toolkit by calling them directly through a web browser (for example):        `http://myserver.domain.edu/sirsiscripts/lookupUser.pl?id=123456789`

## Installing the Symphony connector: ##


> Please follow the steps in  [Installation/General Installation Instructions](http://code.google.com/p/xcncip2toolkit/wiki/CoreInstallation)


## Please Note: ##
At this time the Symphony connector does not implement the entire NCIP protocol for each service.  The functionality is limited to the input and output needed for our particular project.  Ideally, this connector can be built upon to include additional functionality.  The input/output supported by this connector is documented [here](http://code.google.com/p/xcncip2toolkit/wiki/SymphonyConnector).