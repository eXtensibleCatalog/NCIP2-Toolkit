# Scenario 1: RequestItem #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>  <ns1:NCIPMessage  xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
  <ns1:RequestItem>
    <ns1:InitiationHeader> 
       <ns1:FromAgencyId>
          <ns1:AgencyId ns1:Scheme="localhost/ncip/v1/schemes/agencies/1">aaaaa</ns1:AgencyId>
       </ns1:FromAgencyId>
     
       <ns1:ToAgencyId>
         <ns1:AgencyId ns1:Scheme="localhost/ncip/v1/schemes/agencies/1">bbbbb</ns1:AgencyId>
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

    <ns1:BibliographicId>
       <ns1:BibliographicRecordId>
          <ns1:BibliographicRecordIdentifier>b1111118</ns1:BibliographicRecordIdentifier>
          <ns1:BibliographicRecordIdentifierCode ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm">OCLC</ns1:BibliographicRecordIdentifierCode>
       </ns1:BibliographicRecordId>
    </ns1:BibliographicId>

    <ns1:RequestId>
       <ns1:AgencyId ns1:Scheme="localhost/ncip/v1/schemes/agencies/1">ENG</ns1:AgencyId>
       <ns1:RequestIdentifierValue>WCNav1234</ns1:RequestIdentifierValue>
    </ns1:RequestId>
    
    <ns1:RequestType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm">Loan</ns1:RequestType>
    <ns1:RequestScopeType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm">Bibliographic Item</ns1:RequestScopeType>

    <ns1:PickupLocation>bbbbb</ns1:PickupLocation>
   <ns1:PickupExpiryDate>2012-03-08T00:00:47.003Z</ns1:PickupExpiryDate>
  </ns1:RequestItem> 
 </ns1:NCIPMessage>  
```

# Scenario 2: RequestItem #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>  <ns1:NCIPMessage  xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
  <ns1:RequestItem>
    <ns1:InitiationHeader> 
       <ns1:FromAgencyId>
          <ns1:AgencyId ns1:Scheme="localhost/ncip/v1/schemes/agencies/1">XC</ns1:AgencyId>
       </ns1:FromAgencyId>
     
       <ns1:ToAgencyId>
          <ns1:AgencyId ns1:Scheme="localhost/ncip/v1/schemes/agencies/1">XC</ns1:AgencyId>
       </ns1:ToAgencyId>
    </ns1:InitiationHeader>

    <ns1:AuthenticationInput>  
      <ns1:AuthenticationInputData>98765432</ns1:AuthenticationInputData>   
        <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
        <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
    </ns1:AuthenticationInput>
    <ns1:AuthenticationInput>
        <ns1:AuthenticationInputData>Cook</ns1:AuthenticationInputData>
        <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType> 
       <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
    </ns1:AuthenticationInput>

    <ns1:BibliographicId>
       <ns1:BibliographicRecordId>
          <ns1:BibliographicRecordIdentifier>59781</ns1:BibliographicRecordIdentifier>
          <ns1:BibliographicRecordIdentifierCode ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm">OCLC</ns1:BibliographicRecordIdentifierCode>
       </ns1:BibliographicRecordId>
    </ns1:BibliographicId>

    <ns1:ItemId>
        <ns1:ItemIdentifierValue>62924</ns1:ItemIdentifierValue>
    </ns1:ItemId>
    
    <ns1:RequestType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm">Callslip</ns1:RequestType>
    <ns1:RequestScopeType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm">Item</ns1:RequestScopeType>

  </ns1:RequestItem> 
 </ns1:NCIPMessage>  
```

# Scenario 3: RequestItem #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:RequestItem>

        <ns1:InitiationHeader>
            <ns1:FromAgencyId>
                <ns1:AgencyId>UICdb</ns1:AgencyId>
            </ns1:FromAgencyId>
            <ns1:ToAgencyId>
                <ns1:AgencyId>UICdb</ns1:AgencyId>
            </ns1:ToAgencyId>
        </ns1:InitiationHeader>

        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>98765432</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>Cook</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>

        <ns1:BibliographicId>
                <ns1:BibliographicRecordId>
                        <ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
                        <ns1:AgencyId>UICdb</ns1:AgencyId>
                </ns1:BibliographicRecordId>
        </ns1:BibliographicId>
                
        <ns1:ItemId>
             <ns1:ItemIdentifierValue>111111</ns1:ItemIdentifierValue>
        </ns1:ItemId>

        <ns1:RequestType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm">Hold</ns1:RequestType>
        <ns1:RequestScopeType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm">Bibliographic Item</ns1:RequestScopeType>

    </ns1:RequestItem>
</ns1:NCIPMessage>
```

# Scenario 4: RequestItem (Recall) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:RequestItem>

        <ns1:InitiationHeader>
            <ns1:FromAgencyId>
                <ns1:AgencyId>UIUdb</ns1:AgencyId>
            </ns1:FromAgencyId>
            <ns1:ToAgencyId>
                <ns1:AgencyId>UIUdb</ns1:AgencyId>
            </ns1:ToAgencyId>
        </ns1:InitiationHeader>

        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>xxxxxx</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>xxxxxx</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>

        <ns1:BibliographicId>
                <ns1:BibliographicRecordId>
                        <ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
                        <ns1:AgencyId>UIUdb</ns1:AgencyId>
                </ns1:BibliographicRecordId>
        </ns1:BibliographicId>
                
        <ns1:ItemId>
             <ns1:ItemIdentifierValue>36193</ns1:ItemIdentifierValue>
        </ns1:ItemId>

        <ns1:RequestType>Recall</ns1:RequestType>
        <ns1:RequestScopeType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm">Bibliographic Item</ns1:RequestScopeType>
        <ns1:PickupLocation>376</ns1:PickupLocation>

    </ns1:RequestItem>
</ns1:NCIPMessage>
```


# Scenario 5: RequestItem (Callslip) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>  <ns1:NCIPMessage  xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
  <ns1:RequestItem>

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

    <ns1:BibliographicId>
       <ns1:BibliographicRecordId>
          <ns1:BibliographicRecordIdentifier>53539</ns1:BibliographicRecordIdentifier>
       </ns1:BibliographicRecordId>
    </ns1:BibliographicId>

    <ns1:ItemId>
        <ns1:ItemIdentifierValue>63287</ns1:ItemIdentifierValue>
    </ns1:ItemId>
    
    <ns1:RequestType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm">Stack Retrieval</ns1:RequestType>
    <ns1:RequestScopeType ns1:Scheme="http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm">Item</ns1:RequestScopeType>
    <ns1:PickupLocation>3</ns1:PickupLocation>


  </ns1:RequestItem> 
 </ns1:NCIPMessage> 
```