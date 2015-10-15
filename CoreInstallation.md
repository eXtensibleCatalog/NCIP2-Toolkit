# Installation #

## Prerequisites ##
  1. A supported ILS (Millennium, Symphony or Voyager). You may also install the "dummy" connector which does not actually connect to an ILS, instead it returns hard-coded values for the supported services.
  1. Tomcat installed and running. We have tested the Toolkit with tomcat version 6 and 7. (Toolkit connectors will also run under other web servers, such as Jetty, but we do not provide installation and configuration instructions for that yet.)

## Concepts ##
  1. Toolkit connectors are packaged as Web Application Archives, known as "war files". In these instructions the file is commonly called the "war file" and it's name is referred to as the "war name" or `<warname>`. These "war names" are simplified forms of the ILS that that particular connector is intended for use with:
|war name|ILS name|ILS vendor|
|:-------|:-------|:---------|
|millennium|Millennium|Innovative Information Inc.|
|symphony|Symphony|SirsiDynix|
|voyager |Voyager |Ex Libris |
|dummy   |(none)  |(none)    |

## Preparation ##
Toolkit connectors use Java properties to control how the connector interacts with your ILS. As each connector uses a different mechanism to interact with its target ILS, the properties used by each connector are different from those used by other connectors.<br />
You will need to customize the properties that the Toolkit connector uses when it interacts with your ILS. The simplest way to do this for tomcat is with a context descriptor. This is an XML file that sets the properties appropriately for your library. (There are other ways to customize the connector's properties; see [here](GeneralConfiguration.md) for a discussion of the various approaches.)
  1. Copy the example context descriptor for your connector (from here: [Voyager](VoyagerConfiguration#Context_Descriptor.md), [Millennium](MillenniumConfiguration#Context_Descriptor.md), [Symphony](SymphonyConfiguration#Context_Descriptor.md) (includes Jetty instructions) or the [dummy](DummyConfiguration#Context_Descriptor.md)) and save it to a file named `<warname>.xml`, where `<warname>` is the lower-cased name of the connector, e.g. if you're using the Millennium connector then the context descriptor file should be named "millennium.xml".
  1. Copy that context descriptor file to the `$CATALINA_BASE/conf/[enginename]/[hostname]/` directory. By default `[enginename]` is `Catalina` and `[hostname]` is `localhost`, so that would be `$CATALINA_BASE/conf/Catalina/localhost/`. (See the tomcat [configuration](http://tomcat.apache.org/tomcat-7.0-doc/config/) documentation for further information about the concepts of `engine` and `host`; this is only neccessary if you have customized the engine or host configuration of your tomcat server.)
## Installation ##
  1. Download the connector war file from [Google Code](http://code.google.com/p/xcncip2toolkit/downloads/list)
  1. Copy the connector war file into Tomcat's `$CATALINA_BASE/webapps/` folder, making sure it matches the name of the context descriptor file you created earlier. For example if you download version 1.1 of the millennium connector `millennium-web-1.1.war` you would rename it to `millennium.war`.
  1. Start (or re-start) the server. (You can skip this if your server is set to [auto-deploy](http://tomcat.apache.org/tomcat-7.0-doc/deployer-howto.html#Deploying_on_a_running_Tomcat_server) webapps.)

## Verification ##
  1. Open the URL `http://localhost:8080/<warname>` in your browser and enter the appropriate test NCIP message for your warname:
**> For the dummy.war:**
```
<?xml version="1.0" encoding="UTF-8"?>
<ns1:NCIPMessage 
  ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd" xmlns:ns1="http://www.niso.org/2008/ncip">
  <ns1:LookupItem>
	<ns1:ItemId>
		<ns1:AgencyId>String</ns1:AgencyId>
		<ns1:ItemIdentifierType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/visibleitemidentifiertype/visibleitemidentifiertype.scm">Accession Number</ns1:ItemIdentifierType>
<ns1:ItemIdentifierValue>25556192919132</ns1:ItemIdentifierValue>
	</ns1:ItemId>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
  </ns1:LookupItem>
</ns1:NCIPMessage>
```

**> For the millennium.war:**
```
Millennium example here.
```


**> For the symphony.war:**
```
<?xml version = '1.0' encoding='UTF-8'?>
<NCIPMessage version='http://www.niso.org/schemas/ncip/v2_0/ncip_v2_0.xsd'>xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
<LookupUser>
  <UserId>
    <AgencyId>NFSM</AgencyId>
    <UserIdentifierValue>1234567</UserIdentifierValue>
  </UserId>
  <UserElementType>Name Information</UserElementType>
  <UserElementType>User Address Information</UserElementType>
  <UserElementType>User Language</UserElementType>
  <UserElementType>User Privilege</UserElementType>
  <UserElementType>User Id</UserElementType>
</LookupUser>
</NCIPMessage>
```

> You should see a response message similar to this:
**> For the dummy.war:**
```
<?xml version="1.0" encoding="ISO-8859-1"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
   <ns1:LookupItemResponse>
      <ns1:ItemId>
         <ns1:ItemIdentifierValue>25556192919132</ns1:ItemIdentifierValue>
      </ns1:ItemId>
      <ns1:ItemOptionalFields>
         <ns1:BibliographicDescription>
            <ns1:BibliographicRecordId>
               <ns1:BibliographicRecordIdentifier>123</ns1:BibliographicRecordIdentifier>
               <ns1:AgencyId>Dummytown Library</ns1:AgencyId>
            </ns1:BibliographicRecordId>
            <ns1:Title>Of Mice and Men</ns1:Title>
            <ns1:Language ns1:Scheme="http://lcweb.loc.gov/standards/iso639-2/bibcodes.html">eng</ns1:Language>
         </ns1:BibliographicDescription>
         <ns1:CirculationStatus ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/circulationstatus/circulationstatus.scm">On Loan</ns1:CirculationStatus>
         <ns1:ItemDescription>
            <ns1:CallNumber>813.52 St34yV c.1</ns1:CallNumber>
            <ns1:HoldingsInformation>
               <ns1:UnstructuredHoldingsData>2 copies</ns1:UnstructuredHoldingsData>
            </ns1:HoldingsInformation>
            <ns1:NumberOfPieces>1</ns1:NumberOfPieces>
         </ns1:ItemDescription>
      </ns1:ItemOptionalFields>
   </ns1:LookupItemResponse>
</ns1:NCIPMessage>
```

**> For the millennium.war:**
```
Millennium example here.
```

**> For the symphony.war:**
```
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
   <ns1:LookupUserResponse>
      <ns1:UserId>
         <ns1:AgencyId>NFSM</ns1:AgencyId>
         <ns1:UserIdentifierValue>1234567</ns1:UserIdentifierValue>
      </ns1:UserId>
      <ns1:UserOptionalFields>
         <ns1:NameInformation>
            <ns1:PersonalNameInformation>
               <ns1:UnstructuredPersonalUserName>Doe, John</ns1:UnstructuredPersonalUserName>
            </ns1:PersonalNameInformation>
         </ns1:NameInformation>
         <ns1:UserAddressInformation>
            <ns1:UserAddressRoleType>Primary Address</ns1:UserAddressRoleType>
            <ns1:PhysicalAddress>
               <ns1:StructuredAddress>
                  <ns1:Line1>1 Main Street</ns1:Line1>
                  <ns1:Locality>New York</ns1:Locality>
                  <ns1:Region>NY</ns1:Region>
                  <ns1:PostalCode>10003</ns1:PostalCode>
               </ns1:StructuredAddress>
               <ns1:PhysicalAddressType>Postal Address</ns1:PhysicalAddressType>
            </ns1:PhysicalAddress>
         </ns1:UserAddressInformation>
         <ns1:UserAddressInformation>
            <ns1:UserAddressRoleType>Home</ns1:UserAddressRoleType>
            <ns1:ElectronicAddress>
               <ns1:ElectronicAddressType>mailto</ns1:ElectronicAddressType>
               <ns1:ElectronicAddressData>jdoe@university.edu</ns1:ElectronicAddressData>
            </ns1:ElectronicAddress>
         </ns1:UserAddressInformation>
         <ns1:UserLanguage>ENGLISH</ns1:UserLanguage>
         <ns1:UserPrivilege>
            <ns1:AgencyId>UNIV</ns1:AgencyId>
            <ns1:AgencyUserPrivilegeType>LIBRARY</ns1:AgencyUserPrivilegeType>
            <ns1:UserPrivilegeStatus>
               <ns1:UserPrivilegeStatusType>UNIVERSITY</ns1:UserPrivilegeStatusType>
            </ns1:UserPrivilegeStatus>
            <ns1:UserPrivilegeDescription>User Library</ns1:UserPrivilegeDescription>
         </ns1:UserPrivilege>
         <ns1:UserPrivilege>
            <ns1:AgencyId>UNIV</ns1:AgencyId>
            <ns1:AgencyUserPrivilegeType>PROFILE</ns1:AgencyUserPrivilegeType>
            <ns1:UserPrivilegeStatus>
               <ns1:UserPrivilegeStatusType>FACULTY</ns1:UserPrivilegeStatusType>
            </ns1:UserPrivilegeStatus>
            <ns1:UserPrivilegeDescription>User Profile</ns1:UserPrivilegeDescription>
         </ns1:UserPrivilege>
         <ns1:UserPrivilege>
            <ns1:AgencyId>UNIV</ns1:AgencyId>
            <ns1:AgencyUserPrivilegeType>STATUS</ns1:AgencyUserPrivilegeType>
            <ns1:UserPrivilegeStatus>
               <ns1:UserPrivilegeStatusType>OK</ns1:UserPrivilegeStatusType>
            </ns1:UserPrivilegeStatus>
            <ns1:UserPrivilegeDescription>User Status</ns1:UserPrivilegeDescription>
         </ns1:UserPrivilege>
         <ns1:UserPrivilege>
            <ns1:AgencyId>UNIV</ns1:AgencyId>
            <ns1:AgencyUserPrivilegeType>CAT1</ns1:AgencyUserPrivilegeType>
            <ns1:UserPrivilegeStatus>
               <ns1:UserPrivilegeStatusType>LIBRARY AN</ns1:UserPrivilegeStatusType>
            </ns1:UserPrivilegeStatus>
            <ns1:UserPrivilegeDescription>User Category One</ns1:UserPrivilegeDescription>
         </ns1:UserPrivilege>
      </ns1:UserOptionalFields>
   </ns1:LookupUserResponse>
</ns1:NCIPMessage>
```

> If you do not see this, please review the [troubleshooting guide](TroubleshootingFAQs.md).