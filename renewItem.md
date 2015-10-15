_**Scenario 1 can be used on the Testbed**_

# Scenario 1: Renew Item non-UB (does not require Agency ID) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    <ns1:RenewItem>
        
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>4821</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>Callahan</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        
        <ns1:ItemId>
            <ns1:ItemIdentifierValue>62924</ns1:ItemIdentifierValue>
        </ns1:ItemId>
        
    </ns1:RenewItem>
</ns1:NCIPMessage>
```

# Scenario 2: Renew Item UB (requires Agency IDs) (CARLI) #
## Patron from UIC, item on loan from UIU ##
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns1:NCIPMessage xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.niso.org/2008/ncip ncip_v2_01.xsd"
 xmlns:ns1="http://www.niso.org/2008/ncip"
 ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    <ns1:RenewItem>
        
        <ns1:InitiationHeader>
            <ns1:FromAgencyId>
                <ns1:AgencyId>UIC</ns1:AgencyId>
            </ns1:FromAgencyId>
            <ns1:ToAgencyId>
                <ns1:AgencyId>UIC</ns1:AgencyId>
            </ns1:ToAgencyId>
        </ns1:InitiationHeader>
        
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>barcode</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>pass</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        
        <ns1:ItemId>
            <ns1:AgencyId>UIU</ns1:AgencyId>
            <ns1:ItemIdentifierValue>10226556</ns1:ItemIdentifierValue>
        </ns1:ItemId>
        
    </ns1:RenewItem>
</ns1:NCIPMessage>
```

# Scenario 2 Successful Renewal Response #
## Patron from UIC, item on loan from UIU ##
```
<?xml version="1.0" encoding="ISO-8859-1"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
   <ns1:RenewItemResponse>
      <ns1:ItemId>
         <ns1:AgencyId>UIU</ns1:AgencyId>
         <ns1:ItemIdentifierValue>10226556</ns1:ItemIdentifierValue>
      </ns1:ItemId>
      <ns1:UserId>
         <ns1:AgencyId>UIC</ns1:AgencyId>
         <ns1:UserIdentifierType>Username</ns1:UserIdentifierType>
         <ns1:UserIdentifierValue>127848</ns1:UserIdentifierValue>
      </ns1:UserId>
      <ns1:DateDue>2013-01-11T23:00:00Z</ns1:DateDue>
   </ns1:RenewItemResponse>
</ns1:NCIPMessage>
```