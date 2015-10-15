# Introduction #
This document assumes you are testing the Toolkit's NCIP responder by means of the test page at `http://localhost:8080/<warname>`. If instead you are testing the Toolkit's NCIP responder with an NCIP version 2 compliant initiator, we recommend you obtain a copy of the initiation message being sent by that initiator and use that to recreate the problem in the test page.

Q1. The NCIP response message isn't what I expect - it begins with this:
```
Error: returned status code 500. <html><head><title>Apache Tomcat/7.0.25 - Error report</title>
```
This is followed by more HTML and then a long string of Java class names, numbers, etc. - a Java stack trace.

A1. Search through that output for the text "Service error:", which will be followed by the precise cause of the error, for example:
```
</pre></p><p><b>root cause</b> <pre>Service error: INVALID_SCHEME_VALUE. org.extensiblecatalog.ncip.v2.service.ServiceException: No match found for scheme 'null', value 'OCLC' in org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode; configuration option for defining values for this class is unset.
org.extensiblecatalog.ncip.v2.service.SchemeValuePair.find(SchemeValuePair.java:256)
```
Look below in this troubleshooting guide for what to do with that particular error. If you do not find any guidance, please open an [issue](http://code.google.com/p/xcncip2toolkit/issues/list), and include the text following the "Service error:" text. If you do not find the text "Service error:" please open an issue and include the entire content of the response.

Q2. The response indicates there was a "Service error: INVALID\_SCHEME\_VALUE".

A2. Immediately following that text should be further details which identify the scheme and value that were found in the NCIP message and the sub-class of SchemeValuePair which was given that scheme and value; for example:
```
</pre></p><p><b>root cause</b> <pre>Service error: INVALID_SCHEME_VALUE. org.extensiblecatalog.ncip.v2.service.ServiceException: No match found for scheme 'null', value 'OCLC' in org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode; configuration option for defining values for this class is unset.
	org.extensiblecatalog.ncip.v2.service.SchemeValuePair.find(SchemeValuePair.java:256)

```
In this case the error is in a BibliographicRecordIdentifierCode element, where the Scheme attribute is null (i.e., not present) and the value of the element (i.e. the text content of the BibliographicRecordIdentifierCode element) is "OCLC". The Toolkit also indicates that the "configuration option for defining values for this class is unset." One solution is for the initiation message to be changed to include the proper NCIP Scheme URI in the Scheme attribute of the element in question (in this case, BibliographicRecordIdentifierCode). If that is not possible, you can configure the Toolkit to allow the Scheme attribute to be omitted by setting the [CoreConfiguration.AddedSVPClassesAllowNullScheme](GeneralConfiguration.md) property to include the classes that handle those elements. For this example you would add the org.!extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode class to that property:
```
CoreConfiguration.AddedSVPClassesAllowNullScheme=org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode
```
**Note**: be careful not to set the CoreConfiguration.SVPClassesAllowNullScheme property (note the absence of "Added" in the property name), as this will undo this setting for the elements which do not require the Scheme attribute by default.