# How to add a new service #

This section provides guidance on how to add a new service to the NCIP Toolkit core code.


# Details #

1) Create `[ServiceName]InitiationData` and `[ServiceName]ResponseData` classes (in the services package) that implement `NCIPInitationData` and `NCIPResponseData` interfaces respectively. The effort largely consists of adding all the elements of those messages, which often entails creating classes for the complex elements that are in those messages (if org.extensiblecatalog.ncip.v2.service package doesn't already have a class that implements them).
> a) If you need to create a class for an element that is of type `SchemeValuePair`, see `ItemElementType` for the template. Following that pattern allows us to use the defined `SchemeValuePair` values as an enumerated type, but still allows non-standard values to be handled.

2) Create `[ServiceName]Service` interface (in the services package) that extends `NCIPService<[ServiceName]InitiationData`, `[ServiceName]ResponseData>`. This is simple – just copy an existing one of these classes for a different service name and change the names of the class and the `InitiationData` and `ResponseData` objects.

3) Add `create[ServiceName]InitiationMessage`, `create[ServiceName]ResponseMessage`, `create[ServiceName]InitiationData`, and `create[ServiceName]InitiationData` methods in `JAXBTranslator`.
> a) I do this by copying an existing method for a different Message/Data object, strip out most of the middle section, and change the parameter names to match the service name I'm working on.
> b) Then I copy the section of the ncip\_v2\_0.xsd file that defines the message, e.g. this for `RequestItem`:
```
				<xs:element ref="InitiationHeader" minOccurs="0"/>
				<xs:element ref="MandatedAction" minOccurs="0"/>
				<xs:choice>
					<xs:element ref="UserId"/>
					<xs:element ref="AuthenticationInput" maxOccurs="unbounded"/>
				</xs:choice>
				<xs:choice>
					<xs:element ref="ItemId"/>
					<xs:element ref="BibliographicId"/>
				</xs:choice>
				<xs:element ref="RequestId" minOccurs="0"/>
				<xs:element ref="RequestType"/>
				<xs:element ref="RequestScopeType"/>
				<xs:element ref="ItemOptionalFields" minOccurs="0"/>
				<xs:element ref="ShippingInformation" minOccurs="0"/>
				<xs:element ref="EarliestDateNeeded" minOccurs="0"/>
				<xs:element ref="NeedBeforeDate" minOccurs="0"/>
				<xs:element ref="PickupLocation" minOccurs="0"/>
				<xs:element ref="PickupExpiryDate" minOccurs="0"/>
				<xs:element ref="AcknowledgedFeeAmount" minOccurs="0"/>
				<xs:element ref="PaidFeeAmount" minOccurs="0"/>
				<xs:element ref="AcknowledgedItemUseRestrictionType" minOccurs="0" maxOccurs="unbounded"/>
				<xs:element ref="ItemElementType" minOccurs="0" maxOccurs="unbounded"/>
				<xs:element ref="UserElementType" minOccurs="0" maxOccurs="unbounded"/>
				<xs:element ref="Ext" minOccurs="0"/>
```

Then I take each line in succession and write code to convert to/from that structure. I’m working on a set of templates for this, but at the moment the best guidance I can give is to look for a similar instance – e.g. for `PaidFeeAmount` in the above example I’d look for another create`[ServiceName`]… method that handles a complex type (i.e. an element that doesn’t have a defined XML Datatype like `xs:dateTime`, `xs:string`, `xs:postiveInteger`, etc., or `SchemeValuePair`), that is optional but not repeatable (that’s the meaning of `minOccurs=”0”` and no `maxOccurs` attribute).

> c) When writing a method to convert a complex object, if the input parameter is null then return null. This simplifies writing conversion methods for complex elements that contain these elements - they don't have to test whether the element is null or not before calling the `createXYZ` method. However, if the input parameter is a Collection, you must throw an exception (and the caller must test before calling) because it’s an error to add the list if it’s empty.

> d) Some complex elements in NCIP (e.g. `StructuredAddress`) have a structure that JAXB isn't able to generate getters and setters for each sub-element, so it generates a `getContent` method that returns a List to which you have to add each element, in the order it should appear in the XML document. Until we figure out a way to configure JAXB's generation of objects for the NCIP schema, see the handling of `StructuredAddress` in the `createStructuredAddress` method that converts from the Service API object to a JAXB object for an example of how to do this.




4) Add handling of the message to these methods in `JAXBTranslator`:
```
createInitiationMessage(org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData)
createResponseMessage(org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData)
createInitiationData(NCIPMessage initMsg);
createResponseData(NCIPMessage respMsg)
```

5) You should probably write a class that extends org.extensiblecatalog.ncip.v2.examples.SimpleClient to test the service, and a class in the org.extensiblecatalog.ncip.v2.dummy package that implements the dummy back-end for the service so you can test it.