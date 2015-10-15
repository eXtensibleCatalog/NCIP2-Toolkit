# Scenario 1: cancelRequestItem #
```
<ns1:NCIPMessage
    xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    <ns1:CancelRequestItem>
        <ns1:InitiationHeader>
            <ns1:FromAgencyId>
                <ns1:AgencyId ns1:Scheme="http://www.niso.org/ncip/v2_0/imp1/schemes/registryid/registryid.scm">cerc</ns1:AgencyId>
            </ns1:FromAgencyId>
            <ns1:ToAgencyId>
                <ns1:AgencyId>gen</ns1:AgencyId>
            </ns1:ToAgencyId>
        </ns1:InitiationHeader>
        <ns1:AuthenticationInput>
          <ns1:AuthenticationInputData>Your Username</ns1:AuthenticationInputData>
          <ns1:AuthenticationDataFormatType ns1:Scheme="http://www.iana.org/assignments/media-types">text/plain</ns1:AuthenticationDataFormatType>
          <ns1:AuthenticationInputType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/authenticationinputtype/authenticationinputtype.scm">LDAPUsername</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:AuthenticationInput>
          <ns1:AuthenticationInputData>Your Password</ns1:AuthenticationInputData>
          <ns1:AuthenticationDataFormatType ns1:Scheme="http://www.iana.org/assignments/media-types">text/plain</ns1:AuthenticationDataFormatType>
          <ns1:AuthenticationInputType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/authenticationinputtype/authenticationinputtype.scm">LDAPPassword</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:RequestId>
            <ns1:RequestIdentifierValue>b1067838</ns1:RequestIdentifierValue>
        </ns1:RequestId>
        <ns1:RequestType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm">Hold</ns1:RequestType>
    </ns1:CancelRequestItem>
</ns1:NCIPMessage>
```

# Scenario 2: CancelRequestItem (CARLI - non-consortial) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>  <ns1:NCIPMessage  xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
  <ns1:CancelRequestItem>

    <ns1:AuthenticationInput>  
      <ns1:AuthenticationInputData>12341234</ns1:AuthenticationInputData>   
        <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
        <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
    </ns1:AuthenticationInput>
    <ns1:AuthenticationInput>
        <ns1:AuthenticationInputData>Test</ns1:AuthenticationInputData>
        <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType> 
       <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
    </ns1:AuthenticationInput>

    <ns1:RequestId>
        <ns1:RequestIdentifierValue>b1067838</ns1:RequestIdentifierValue>
    </ns1:RequestId>
    <ns1:RequestType>Hold</ns1:RequestType>

  </ns1:CancelRequestItem> 
 </ns1:NCIPMessage> 
```