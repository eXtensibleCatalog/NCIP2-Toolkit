_**Scenarios 2 and 3 can be used on the Testbed and copied as-is**_

# Scenario 1: Request with multiple bibliographic record IDs and two different Agencies (CARLI) (NCIP v2.02) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_02.xsd">
<ns1:LookupItemSet>
	<ns1:BibliographicId>
		<ns1:BibliographicRecordId>
			<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
			<ns1:AgencyId>UIU</ns1:AgencyId>
		</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>34940</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>5866</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>20071</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>45829</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>41258</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>

	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Circulation Status</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Electronic Resource</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Hold Queue Length</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Description</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Use Restriction Type</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Location</ns1:ItemElementType>
	
</ns1:LookupItemSet>
</ns1:NCIPMessage>
```

# Scenario 2: Simple request with 1 Bibliographic record returning 1 holding and 3 Items (Testbed) (pre 2.02) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
<ns1:Ext>
<ns1:LookupItemSet>
	<ns1:BibliographicId>
		<ns1:BibliographicRecordId>
			<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
			<ns1:AgencyId>XC</ns1:AgencyId>
		</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Circulation Status</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Electronic Resource</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Hold Queue Length</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Description</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Use Restriction Type</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Location</ns1:ItemElementType>

</ns1:LookupItemSet>
</ns1:Ext>
</ns1:NCIPMessage>
```
# Scenario 3: Request with multiple bibliographic record IDs returning nextItemToken (Testbed) (pre 2.02) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
<ns1:Ext>
<ns1:LookupItemSet>
	<ns1:BibliographicId>
		<ns1:BibliographicRecordId>
			<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
			<ns1:AgencyId>XC</ns1:AgencyId>
		</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>34940</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>XC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>5866</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>XC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>20071</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>XC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>45829</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>XC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>41258</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>XC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>

	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Circulation Status</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Electronic Resource</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Hold Queue Length</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Description</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Use Restriction Type</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Location</ns1:ItemElementType>
	
</ns1:LookupItemSet>
</ns1:Ext>
</ns1:NCIPMessage>
```

# Scenario 4: Simple request with 1 Bibliographic record returning 1 holding and 1 Item (CARLI) (pre 2.02) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
<ns1:Ext>
<ns1:LookupItemSet>
	<ns1:BibliographicId>
		<ns1:BibliographicRecordId>
			<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
			<ns1:AgencyId>UIU</ns1:AgencyId>
		</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Circulation Status</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Electronic Resource</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Hold Queue Length</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Description</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Use Restriction Type</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Location</ns1:ItemElementType>

</ns1:LookupItemSet>
</ns1:Ext>
</ns1:NCIPMessage>
```
# Scenario 5: Request with multiple bibliographic record IDs and two different Agencies (UIU&UIC) (CARLI) (pre 2.02) #
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns1:NCIPMessage xmlns:ns1="http://www.niso.org/2008/ncip" ns1:version="http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd">
<ns1:Ext>
<ns1:LookupItemSet>
	<ns1:BibliographicId>
		<ns1:BibliographicRecordId>
			<ns1:BibliographicRecordIdentifier>21236</ns1:BibliographicRecordIdentifier>
			<ns1:AgencyId>UIU</ns1:AgencyId>
		</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>34940</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>5866</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>20071</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>45829</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIU</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>
	<ns1:BibliographicId>
			<ns1:BibliographicRecordId>
				<ns1:BibliographicRecordIdentifier>41258</ns1:BibliographicRecordIdentifier>
				<ns1:AgencyId>UIC</ns1:AgencyId>
			</ns1:BibliographicRecordId>
	</ns1:BibliographicId>

	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Bibliographic Description</ns1:ItemElementType>
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Circulation Status</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Electronic Resource</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Hold Queue Length</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Description</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Item Use Restriction Type</ns1:ItemElementType>
	
	<ns1:ItemElementType ns1:Scheme="http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm">Location</ns1:ItemElementType>
	
</ns1:LookupItemSet>
</ns1:Ext>
</ns1:NCIPMessage>
```