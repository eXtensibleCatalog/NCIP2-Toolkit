_**Scenarios 1, 2 and 3 will work on the Testbed and can be copied as-is.**_

# Scenario 1: Authenticating against Voyager server, returning Charged Items, Requested Items and Fiscal Account info #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:LookupUser>

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
        
        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>
        
    </ns1:LookupUser>
</ns1:NCIPMessage>
```

# Scenario 2: Requesting User optional fields (e.g. address) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    <ns1:LookupUser>

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

        <ns1:UserElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/userelementtype/userelementtype.scm">User Address Information</ns1:UserElementType>
        <ns1:UserElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/userelementtype/userelementtype.scm">Name Information</ns1:UserElementType>
        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>

    </ns1:LookupUser>
</ns1:NCIPMessage>
```

# Scenario 3: Lookup User using a UserId (Voyager Patron Id) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:LookupUser>
        
        <ns1:UserId>
            <ns1:UserIdentifierValue>381</ns1:UserIdentifierValue>
        </ns1:UserId>
        
        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>

    </ns1:LookupUser>
</ns1:NCIPMessage>
```

# Scenario 4: Lookup User using a UserId (Voyager Patron Id) and AgencyId (CARLI) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:LookupUser>
        
        <ns1:UserId>
            <ns1:AgencyId>UIC</ns1:AgencyId>
            <ns1:UserIdentifierValue>381</ns1:UserIdentifierValue>
        </ns1:UserId>
        
        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>

    </ns1:LookupUser>
</ns1:NCIPMessage>
```

# Scenario 5: Authenticating against LDAP server #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    <ns1:LookupUser>

        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>yourUserName</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>LDAPUsername</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>yourPasswod</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>LDAPPassword</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>

        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>

    </ns1:LookupUser>
</ns1:NCIPMessage>
```

# Scenario 6: Simple Lookup User using ToAgencyId for UB lookups (CARLI) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
    
    <ns1:LookupUser>
        
        <ns1:InitiationHeader>
            <ns1:FromAgencyId>
                <ns1:AgencyId>UIU</ns1:AgencyId>
            </ns1:FromAgencyId>
            <ns1:ToAgencyId>
                <ns1:AgencyId>UIU</ns1:AgencyId>
            </ns1:ToAgencyId>
        </ns1:InitiationHeader>
        
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>Barcode</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Username</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>
        <ns1:AuthenticationInput>
            <ns1:AuthenticationInputData>Lastname</ns1:AuthenticationInputData>
            <ns1:AuthenticationDataFormatType>text</ns1:AuthenticationDataFormatType>
            <ns1:AuthenticationInputType>Password</ns1:AuthenticationInputType>
        </ns1:AuthenticationInput>

        <ns1:LoanedItemsDesired/>
        <ns1:RequestedItemsDesired/>
        <ns1:UserFiscalAccountDesired/>

    </ns1:LookupUser>
</ns1:NCIPMessage>
```