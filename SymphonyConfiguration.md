# Introduction #

The Symphony connector connects to perl scripts which call the Symphony API to support the following four services: LookupUser, AcceptItem, CheckInItem and CheckOutItem.  (Not every possible element is supported in all of these services)

# Context Descriptor #
This sample shows all of the properties used by the Symphony connector which a library will probably need to override in their context descriptor file symphony.xml for Tomcat or override-web.xml file for Jetty:

## symphony.xml -- for use with Tomcat ##
```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <Parameter name="SirsiScriptURL" value="http://urlforyour.sirsiscripts.yourdomain.edu/sirsi/" override="true"/>
  <Parameter name="HOME_LIBRARY" value="LEHIGH" override="true"/>
  <Parameter name="AGENCY_ID" value="LEHI" override="true"/>
  <Parameter name="AUTH_BARCODE" value="Barcode Id" override="true"/>
  <Parameter name="AUTH_UID" value="Username" override="true"/>
  <Parameter name="PRIV_STAT_DESCRIPTION" value="User Status"/>
  <Parameter name="PRIV_TYPE_STATUS" value="STATUS" override="true"/>
  <Parameter name="PRIV_USER_PROFILE_DESCRIPTION" value="User Profile" override="true"/>
  <Parameter name="PRIV_TYPE_PROFILE" value="PROFILE" override="true"/>
  <Parameter name="PRIV_LIB_DESCRIPTION" value="User Library" override="true"/>
  <Parameter name="PRIV_TYPE_LIBRARY" value="LIBRARY" override="true"/>
  <Parameter name="PRIV_CAT_DESCRIPTION" value="User Category One" override="true"/>
  <Parameter name="PRIV_TYPE_CAT" value="CAT1" override="true"/>
  <Parameter name="daysUntilHoldExpires" value="7" override="true"/>

</Context>

```
## override-web.xml -- for use with Jetty ##
(Tested with Jetty 7.5.1 and 7.4.4)

Place within a ../contexts/symphony.d/folder
```
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app 
   xmlns="http://java.sun.com/xml/ns/javaee" 
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" 
   version="2.5"> 


<!-- This web.xml format file is an override file that is applied to the test webapp AFTER
     it has been configured by the default descriptor and the WEB-INF/web.xml descriptor -->

  <!-- Add or override context init parameter -->
  <context-param>
    <param-name>SirsiScriptURL</param-name>
    <param-value>http://urlforyour.sirsiscripts.yourdomain.edu/sirsi/</param-value>
  </context-param>
  <context-param>
    <param-name>HOME_LIBRARY</param-name>
    <param-value>LEHIGH</param-value>
  </context-param>
  <context-param>
    <param-name>AGENCY_ID</param-name>
    <param-value>LEHI</param-value>
  </context-param>
  <context-param>
    <param-name>AUTH_BARCODE</param-name>
    <param-value>Barcode Id</param-value>
  </context-param>
  <context-param>
    <param-name>AUTH_UID</param-name>
    <param-value>Username</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_STAT_DESCRIPTION</param-name>
    <param-value>User Status</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_STATUS</param-name>
    <param-value>STATUS</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_USER_PROFILE_DESCRIPTION</param-name>
    <param-value>User Profile</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_PROFILE</param-name>
    <param-value>PROFILE</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_LIB_DESCRIPTION</param-name>
    <param-value>User Library</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_LIBRARY</param-name>
    <param-value>LIBRARY</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_CAT_DESCRIPTION</param-name>
    <param-value>User Category One</param-value>
  </context-param>
  <context-param>
    <param-name>PRIV_TYPE_CAT</param-name>
    <param-value>CAT1</param-value>
  </context-param>
  <context-param>
    <param-name>daysUntilHoldExpires</param-name>
    <param-value>7</param-value>
  </context-param>

</web-app>

```

You will also need a symphony.xml file placed within the ../contexts/ folder.  (The symphony.xml file name matches the name of the war file -- symphony.war) The symphony.xml file should look like this:

```


<?xml version="1.0"  encoding="ISO-8859-1"?>
<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure.dtd">

<!-- ==================================================================
Configure and deploy the test web application in $(jetty.home)/webapps/test

Note. If this file did not exist or used a context path other that /test
then the default configuration of jetty.xml would discover the test
webapplication with a WebAppDeployer.  By specifying a context in this
directory, additional configuration may be specified and hot deployments 
detected.
===================================================================== -->

<Configure class="org.eclipse.jetty.webapp.WebAppContext">


  <!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
  <!-- Required minimal context configuration :                        -->
  <!--  + contextPath                                                  -->
  <!--  + war OR resourceBase                                          -->
  <!-- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -->
  <Set name="contextPath">/symphony</Set>
  <Set name="war"><SystemProperty name="jetty.home" default="."/>/webapps/symphony.war</Set>
  <Set name="overrideDescriptor"><SystemProperty name="jetty.home" default="."/>/contexts/symphony.d/override-web.xml</Set>


</Configure>


```


# Connector Properties #
|Parameter|Sample/Default value|Notes|
|:--------|:-------------------|:----|
|SirsiScriptURL|`http://urlforyour.sirsiscripts.yourdomain.edu/sirsi/`|This is the URL location of your Symphony scripts (the perl scripts that call the Symphony API)|
|AGENCY\_ID|LEHI                |used to construct the response XML to indicate the Agency Id |
|AUTH\_BARCODE|Barcode Id          |used to indicate the element the id will be found in when the request uses Authentication Input instead of UserId|
|AUTH\_UID|Username            |used to indicate the element the id will be found in when the request uses Authentication Input instead of UserId|
|PRIV\_STAT\_DESCRIPTION|User Status         |used to construct the response XML, UserPrivilege element to **describe** the User Status element|
|PRIV\_TYPE\_STATUS|STATUS              |used to construct the response XML UserPrivilege element to indicate the **type** of User Privilege in the element -- in this case TYPE = STATUS (Privilege Status -- e.g. OK)|
|PRIV\_USER\_PROFILE\_DESCRIPTION|User Profile        |used to construct the response XML, UserPrivilege element to **describe** the User Privilege Status element|
|PRIV\_TYPE\_PROFILE|PROFILE             |used to construct the response XML UserPrivilege element to indicate the **type** of User Privilege in the element -- in this case TYPE = PROFILE (Privilege Type -- e.g. Faculty)|
|PRIV\_LIB\_DESCRIPTION|User Library        |used to construct the response XML, UserPrivilege element to **describe** the User Privilege Library element|
|PRIV\_TYPE\_LIBRARY|LIBRARY             |used to construct the response XML UserPrivilege element to indicate the **type** of User Privilege in the element -- in this case TYPE = LIBRARY(Privilege Type -- e.g. LEHIGH)|
|HOME\_LIBRARY|LEHIGH              |used to construct the response XML, UserPrivilege element to indicate the **value** in the UserPrivilege type LIBRARY|
|PRIV\_CAT\_DESCRIPTION|User Category One   |used to construct the response XML, UserPrivilege element to **describe** the User Privilege -- User Category element|
|PRIV\_TYPE\_CAT|CAT1                |used to construct the response XML UserPrivilege element to indicate the **type** of User Privilege in the element -- in this case TYPE = CAT1|
|daysUntilHoldExpires|7                   |used for the AcceptItem service|