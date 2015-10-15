# Introduction #

The Dummy connector does not actually "connect" to an ILS. It is used as a demonstration of how to write a connector, and provides a no-configuration version of the Toolkit for users to download and test with.

# Context Descriptor #
This example shows how to override the library name in the Dummy connector's context descriptor file `dummy.xml`:
```
<?xml version='1.0' encoding='utf-8'?>
<Context>

  <Parameter name="DummyConfiguration.LibraryName" value="Dummy Town Library" override="true"/>

</Context>

```
See [below](DummyConfiguration#Connector_Properties.md) for the complete list of all Dummy connector properties and their use.

# Connector Properties #
|Parameter|Default value|Notes|
|:--------|:------------|:----|
|DummyConfiguration.LibraryName|Dummytown Library|This is the name of the library.|
|DummyConfiguration.AgencyScheme|local.edu    |This is the NCIP Scheme URI that _identifies_ a list of agencies (libraries, library branches, etc.). Note that this does _not_ have to point to an actual web page or document; it is used only within NCIP messages to uniquely identify the agencies.|
|DummyConfiguration.AgencyValue|Dummytown    |This is the Value of this library's identifier within the NCIP Scheme specified above.|