# Introduction #

The Voyager connector connects to the Voyager vxws and Oracle ports

# Context Descriptor #
This sample shows all of the properties used by the Voyager connector which a library will probably need to override in their context descriptor file voyager.xml:
```
<?xml version='1.0' encoding='utf-8'?>
<Context>
<Parameter name="VoyagerVxwsUrl" value="http://your.vxws.server.edu:19913" override="true"/>
<Parameter name="VoyagerDatabaseUrl" value="jdbc:oracle:thin:@your.oracle.server.edu:1521:VGER" override="true"/>
<Parameter name="VoyagerDatabaseReadOnlyUsername" value="username" override="true"/>
<Parameter name="VoyagerDatabaseReadOnlyPassword" value="password" override="true"/>
<Parameter name="ILSDefaultAgency" value="XC" override="true"/>
<Parameter name="XC" value="1@MAST20001DB20020910104124" override="true"/>
<Parameter name="UIU" value="1@UIUDB20020422223437" override="true"/>
<Parameter name="UICdb" value="jdbc:oracle:thin:@dbtest.ilcso.uiuc.edu:1521:UIC" override="true"/>
<Parameter name="UICusername" value="oracleusername" override="true"/>
<Parameter name="UICpassword" value="password" override="true"/>
<Parameter name="UICdbuser" value="databaseusername" override="true"/>
<Parameter name="MaxLuisItems" value="100" override="true"/>
<Parameter name="MultipleTomcats" value="false" override="true"/>
<Parameter name="ExternalLDAPLocation" value="ldap://dsee.cc.rochester.edu" override="true"/>
<Parameter name="ExternalLDAPPort" value="389" override="true"/>
<Parameter name="ExternalLDAPStart" value="ou=people, dc=rochester, dc=edu" override="true"/>
<Parameter name="ExternalLDAPUsernameAttribute" value="uid" override="true"/>
<Parameter name="ExternalLDAPUrId" value="urid" override="true"/>
<Parameter name="ExternalLDAPLastname" value="sn" override="true"/>
<Parameter name="CoreConfiguration.AddedSVPClasses" value="org.extensiblecatalog.ncip.v2.service.XcCirculationStatus" override="true"/>
<Parameter name="ConnectorConfiguration.ConfigClass" value="org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration" override="true"/>
</Context>
```
# Connector Properties #
|Parameter (Key)|Value notes|
|:--------------|:----------|
|VoyagerVxwsUrl |This is the URL of the library's vxws server including the port number, e.g. http://training.carli.illinois.edu:19913.|
|VoyagerDatabaseUrl|This is the URL of the library's Oracle server.  You will need to change the host and port, and provide the Oracle SID.|
|VoyagerDatabaseReadOnlyUsername|This is the Oracle username.|
|VoyagerDatabaseReadOnlyPassword|This is the Oracle password.|
|!ILSDefaultAgency|Configure a default Agency ID if the toolkit is to be used only for a single institution.  This allows the omission of Agency Id in every request.  If using the toolkit in a consortium this can safely be left out.|
|AgencyId e.g. like "XC" or "UIU"|Voyager Database ID. Configure an Agency ID/Voyager database ID mapping for each agency (member institution in a consortium).  Put an AgencyId with its corresponding Voyager database ID, one on each line.  For example `<Parameter name="UIU" value="1@MAST20001DB20020910104124" override="true"/> where 1@MAST`... is the database ID for the UIU AgencyId.  More information can be found here: http://support.exlibrisgroup.com/?kb=16384-22994.  If you're configuring the toolkit for a single institution you will only have one Agency ID mapping.  |
|AgencyId + "db"|(For consortia users) A previously configured AgencyId with the string "db" appended to it.  Put the value of the URL to your Oracle database + SID here.|
|AgencyId + "username"|(For consortia users) A previously configured AgencyId with the string "username" appended to it.  Put the value of the Oracle username here.|
|AgencyId + "password"|(For consortia users) A previously configured AgencyId with the string "password" appended to it.  Put the value of the Oracle username's password here.|
|AgencyId + "dbuser"|(For consortia users) A previously configured AgencyId with the string "dbuser" appended to it.  Put the value of the database username here.|
|MaxLuisItems   |Maximum number of items for LUIS to return in a single response before a next item token is used to page to the next set of results|
|MultipleTomcats|(For consortia users) Set to true if your library uses a separate Tomcat instance for each agency. Usually will be set to false.|
|ExternalLDAPLocation|URL of your LDAP server if using.|
|ExternalLDAPPort|Port number of your LDAP server.|
|ExternalLDAPStart|Location to start search.|
|ExternalLDAPUsernameAttribute|
|ExternalLDAPUrId|
|ExternalLDAPLastname|
|CoreConfiguration.AddedSVPClasses|Leave as default.|
|ConnectorConfiguration.ConfigClass|Leave as default.|