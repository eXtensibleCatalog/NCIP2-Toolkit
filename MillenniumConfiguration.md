# Introduction #

The Millennium connector connects to the Millennium OPAC (HTML pages) and extracts, or scrapes, information from those pages for lookup services such as LookupItemSet, and emulates a user submitting forms for update services such as [RequestItem](requestItemNotes.md).

# Context Descriptor #
This sample shows all of the properties used by the Millennium connector which a library will probably need to override in their context descriptor file millennium.xml:
```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <Parameter name="MillenniumConfiguration.URL" value="jasmine.uncc.edu" override="true"/>
  <Parameter name="MillenniumConfiguration.Port" value="80" override="true"/>
  <Parameter name="MillenniumConfiguration.LibraryName" value="Atkins Library" override="true"/>
  <Parameter name="MillenniumConfiguration.DefaultAgency" value="circ, gen" override="true"/>
  <Parameter name="MillenniumConfiguration.CallNoLabel" value="CALL #" override="true"/>
  <Parameter name="MillenniumConfiguration.LocationLabel" value="Location" override="true"/>
  <Parameter name="MillenniumConfiguration.LibraryHasLabel" value="Lib. Has" override="true"/>
  <Parameter name="MillenniumConfiguration.SearchScope" value="S0" override="true"/>
  <Parameter name="MillenniumConfiguration.Functions" value="Cancel, Renew, Request" override="true"/>
  <Parameter name="MillenniumConfiguration.LDAPUserVariable" value="extpatid" override="true"/>
  <Parameter name="MillenniumConfiguration.LDAPPasswordVariable" value="extpatpw" override="true"/>
  <Parameter name="MillenniumConfiguration.PatronUserVariable" value="name" override="true"/>
  <Parameter name="MillenniumConfiguration.PatronPasswordVariable" value="code" override="true"/>
  <Parameter name="MillenniumConfiguration.AuthenticationType" value="both" override="true"/>

</Context>

```
# Connector Properties #
|Parameter|Default value|Notes|
|:--------|:------------|:----|
|MillenniumConfiguration.URL|your.library.url|This is the URL of the library's OPAC, e.g. jasmine.uncc.edu. Do not include the protocol or the port, e.g. `http:80//`.|
|MillenniumConfiguration.Port|80           |This is the port for the library's OPAC, typically `80`.|
|MillenniumConfiguration.LibraryName|Atkins Library|This is your library name|
|MillenniumConfiguration.DefaultAgency|circ, gen    |This is your Agency and also pickup location|
|MillenniumConfiguration.CallNoLabel|Call #       |This is the format of your library for call number|
|MillenniumConfiguration.LocationLabel|Location     |This is the format of your library for location|
|MillenniumConfiguration.LibraryHasLabel|Lib. Has     |This is the format of your library for Library Has|
|MillenniumConfiguration.SearchScope|S0           |(Zero not Capital O). This is the format of your library setup. Usually S0 or S1. If you login into your library website, you can see on the address bar: http://yourLibraryURL/patroninfo~S0/SomeNumber/top|
|MillenniumConfiguration.Functions|Cancel, Renew, Request|III supports Renew Item, Request Item, Cancel Request Item.  However, it depends on each library's setting; some libraries does not support renew item. You can login into your library and check what function they support.|
|MillenniumConfiguration.LDAPUserVariable|extpatid     |Your Variable of LDAP User Name|
|MillenniumConfiguration.LDAPPasswordVariable|extpatpw     |Your Variable of LDAP Password|
|MillenniumConfiguration.PatronUserVariable|name         |Your Variable of III Patron User Name|
|MillenniumConfiguration.PatronPasswordVariable|code         |Your Variable of III patron User Password|
|MillenniumConfiguration.AuthenticationType|both         |If your Library support both LDAP and Patron Login|