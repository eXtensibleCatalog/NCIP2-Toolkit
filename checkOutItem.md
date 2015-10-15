# Scenario 1: Simple checkout from owning institution; authenticate against ILS #

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_01/ncip_v2_01.xsd">

<ns1:CheckOutItem>

<ns1:AuthenticationInput>
<ns1:AuthenticationInputData>1234567</ns1:AuthenticationInputData>
<ns1:AuthenticationDataFormatType>barcode</ns1:AuthenticationDataFormatType>
<ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
</ns1:AuthenticationInput>

<ns1:AuthenticationInput>
<ns1:AuthenticationInputData>foo</ns1:AuthenticationInputData>
<ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
<ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
</ns1:AuthenticationInput>

<ns1:ItemId>
<ns1:ItemIdentifierValue>S03000250</ns1:ItemIdentifierValue>
</ns1:ItemId>

</ns1:CheckOutItem>

</ns1:NCIPMessage>
```