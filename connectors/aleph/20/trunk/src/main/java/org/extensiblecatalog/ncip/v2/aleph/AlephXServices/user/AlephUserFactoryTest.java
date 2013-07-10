package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.user;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.XMLParserUtil;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.agency.AlephAgencyFactory;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test.TestConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice.XService;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice.XServiceFactory;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import junit.framework.TestCase;

public class AlephUserFactoryTest extends TestCase {
	
	public void testCreateAlephUser() throws Exception{
		String admLibrary = TestConfiguration.getProperty("ADM_LIBRARY");
		String bibLibrary = TestConfiguration.getProperty("BIB_LIBRARY");
		String holdLibrary = TestConfiguration.getProperty("HOL_LIBRARY");
		String agencyId = TestConfiguration.getProperty("EXPECTED_AGENCY");
		String patron_id = TestConfiguration.getProperty("USERNAME");
		String password = TestConfiguration.getProperty("PASSWORD");
		String expectedUserId = TestConfiguration.getProperty("PATRON_ID");
		String expectedFullName = TestConfiguration.getProperty("FULLNAME");
		String expectedAddress = TestConfiguration.getProperty("ADDRESS");
		String cashDataType = AlephConstants.USER_CASH_ALL_DATA;
		String holdDataType = AlephConstants.USER_HOLDS_ALL_DATA;
		String loanDataType = AlephConstants.USER_LOANS_ALL_DATA;
		
		AlephUser user;
		AlephAgency agency = AlephAgencyFactory.createAlephAgency(agencyId, admLibrary, bibLibrary, holdLibrary);
		
		//do negative testing, test bad values
		XService xService = XServiceFactory.createBorAuthXService(admLibrary,null, null, null);
		Document doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		XMLParserUtil.outputNode(doc);
		
		boolean threwException = false;
		try {
			user = AlephUserFactory.createAlephUser(agency,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_BOR_ID_VER)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		//get good data
		xService = XServiceFactory.createBorAuthXService(admLibrary, null, patron_id, password);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		XMLParserUtil.outputNode(doc);
		
		//do negative testing, remove session id node
		NodeList nodes = doc.getElementsByTagName(AlephConstants.SESSION_ID_NODE);
		for (int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.getParentNode().removeChild(n);
		}
		
		threwException = false;
		try {
			user = AlephUserFactory.createAlephUser(agency,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_AUTHENTICATION_FAILED_SESSION_ID_MISSING)){
				fail("Unexpected Error Found");
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		//test missing z303 node
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		nodes = doc.getElementsByTagName(AlephConstants.Z303_ID_NODE);
		for (int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.getParentNode().removeChild(n);
		}
		
		threwException = false;
		try {
			user = AlephUserFactory.createAlephUser(agency,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_GLOBAL_PATRON_RECORD_MISSING)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		//test missing z304 node
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		nodes = doc.getElementsByTagName(AlephConstants.Z304_NODE);
		for (int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.getParentNode().removeChild(n);
		}
		
		threwException = false;
		try {
			user = AlephUserFactory.createAlephUser(agency,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_ADDRESS_INFORMATION_MISSING)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		//test missing z305 node
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		
		nodes = doc.getElementsByTagName(AlephConstants.Z305_NODE);
		for (int i=0; i<nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.getParentNode().removeChild(n);
		}
		
		threwException = false;
		try {
			user = AlephUserFactory.createAlephUser(agency,doc);
		} catch (Exception ex){
			if (!ex.getMessage().equals(AlephConstants.ERROR_LOCAL_PATRON_RECORD_MISSING)){
				fail("Unexpected Error Found: "+ex);
				throw ex;
			} else {
				threwException = true;
			}
		}
		if (!threwException){
			fail("Did not throw proper exception when session id missing");
		}
		
		xService = XServiceFactory.createBorInfoXService(admLibrary, patron_id, null, cashDataType, null, holdDataType, loanDataType);
		doc = xService.execute(TestConfiguration.getProperty("XSERVER_NAME"), TestConfiguration.getProperty("XSERVER_PORT"), false);
		user = AlephUserFactory.createAlephUser(agency,doc);
		XMLParserUtil.outputNode(doc);
		
		assertEquals("Aleph User name returned is incorrect, Expected: "+expectedUserId+" Actual: "+user.getUsername(),expectedUserId,user.getUsername());
		assertEquals("Aleph user full name returned is incorrect, Expected: "+expectedFullName+" Actual: "+user.getFullName(),expectedFullName,user.getFullName());
		assertEquals("Aleph user address returned is incorrect, Expected: "+expectedAddress+" Actual: "+user.getAddress(),expectedAddress,user.getAddress());
		assertTrue("User session id not Returned",user.getSessionId()!=null&&user.getSessionId().length()>0);
		
		//double expectedBalance = 37.12;
		//check fine balance
		//assertEquals("Total Fines does not match, Expected: "+expectedBalance+", Actual: "+user.getBalance(),expectedBalance,user.getBalance());
		
		//check blocks and notes
		System.out.println("Blocks: "+user.getBlocks());
		System.out.println("Notes: "+user.getNotes());
		
		//check fines
		//List<AlephItem> fines = user.getFineItems();
		//assertTrue("Expected fines to be greater than 0",fines.size()>0);
		//SimpleDateFormat dateFormatter = new SimpleDateFormat(AlephConstants.FINE_ACCRUAL_DATE_FORMAT);
		
		/*String creditDebit1 = AlephConstants.FINE_DEBIT;
		String status1 = Configuration.getProperty("FINE_STATUS_1");
		double fineAmount1 = Configuration.getProperty("FINE_AMOUNT_1");
		Date accrualDate1 = dateFormatter.parse(Configuration.getProperty("FINE_ACCRUAL_DATE_1"));
		String location1 = Configuration.getProperty("FINE_LOCATION_1");
		String fineHoldId1 = Configuration.getProperty("FINE_HOLD_ID_1");
		
		String creditDebit2 = AlephConstants.FINE_DEBIT;
		String status2 = Configuration.getProperty("FINE_STATUS_2");
		double fineAmount2 = Configuration.getProperty("FINE_AMOUNT_2");
		Date accrualDate2 = dateFormatter.parse(Configuration.getProperty("FINE_ACCRUAL_DATE_2"));
		String location2 = Configuration.getProperty("FINE_LOCATION_2");
		String fineHoldId2 = Configuration.getProperty("FINE_HOLD_ID_2");
		
		assertTrue("Unexpected number of fines: "+fines.size()+" Expected: 2",fines.size()==2);
		
		assertEquals("Credit Debit mismatch",creditDebit1,fines.get(0).getCreditDebit());
		assertEquals("Fine Status mismatch",status1,fines.get(0).getFineStatus());
		assertEquals("Fine Amount mismatch",fineAmount1,fines.get(0).getFineAmount().doubleValue());
		assertEquals("Fine Date mismatch",accrualDate1,fines.get(0).getFineAccrualDate());
		assertEquals("Fine Item location mismatch",location1,fines.get(0).getLocation());
		assertEquals("Fine Item hold id mismatch",fineHoldId1,fines.get(0).getHoldingsId());
		
		assertEquals("Credit Debit mismatch",creditDebit2,fines.get(1).getCreditDebit());
		assertEquals("Fine Status mismatch",status2,fines.get(1).getFineStatus());
		assertEquals("Fine Amount mismatch",fineAmount2,fines.get(1).getFineAmount().doubleValue());
		assertEquals("Fine Date mismatch",accrualDate2,fines.get(1).getFineAccrualDate());
		assertEquals("Fine Item location mismatch",location2,fines.get(1).getLocation());
		assertEquals("Fine Item hold id mismatch",fineHoldId2,fines.get(1).getHoldingsId());
		*/
		
		//check loans
		List<AlephItem> loans = user.getLoanItems();
		assertTrue("Expected loans to be greater than 0",loans.size()>0);
		
		String itemId1 = TestConfiguration.getProperty("LOAN_ADM_ID1");
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID1");
		String author1 = TestConfiguration.getProperty("LOAN_AUTHOR_1");
		String callNumber1 = TestConfiguration.getProperty("LOAN_CALLNUMBER_1");
		String circStatus1 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_1");
		//Date dueDate1 = dateFormatter.parse("09/30/2009");
		String isbn1 = TestConfiguration.getProperty("LOAN_ISBN_1");
		String loanLocation1 = TestConfiguration.getProperty("LOAN_LOCATION_1");
		String mediumType1= TestConfiguration.getProperty("LOAN_MEDIUMTYPE_1");
		String publisher1 = TestConfiguration.getProperty("LOAN_PUBLISHER_1");
		String title1 = TestConfiguration.getProperty("LOAN_TITLE_1");
		String barcode1 = TestConfiguration.getProperty("LOAN_BARCODE_1");
		String holdId1 = TestConfiguration.getProperty("LOAN_HOLD_ID_1");
				
		String itemId2 = TestConfiguration.getProperty("LOAN_ADM_ID2");
		String bibId2 = TestConfiguration.getProperty("LOAN_BIB_ID2");
		String author2 = TestConfiguration.getProperty("LOAN_AUTHOR_2");
		String callNumber2 = TestConfiguration.getProperty("LOAN_CALLNUMBER_2");
		String circStatus2 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_2");
		//Date dueDate2 = dateFormatter.parse("09/30/2009");
		String isbn2 = TestConfiguration.getProperty("LOAN_ISBN_2");
		String loanLocation2 = TestConfiguration.getProperty("LOAN_LOCATION_2");
		String mediumType2= TestConfiguration.getProperty("LOAN_MEDIUMTYPE_2");
		String publisher2 = TestConfiguration.getProperty("LOAN_PUBLISHER_2");
		String title2 = TestConfiguration.getProperty("LOAN_TITLE_2");
		String barcode2 = TestConfiguration.getProperty("LOAN_BARCODE_2");
		String holdId2 = TestConfiguration.getProperty("LOAN_HOLD_ID_2");
		
		String itemId3 = TestConfiguration.getProperty("LOAN_ADM_ID3");
		String bibId3 = TestConfiguration.getProperty("LOAN_BIB_ID3");
		String author3 = TestConfiguration.getProperty("LOAN_AUTHOR_3");
		String callNumber3 = TestConfiguration.getProperty("LOAN_CALLNUMBER_3");
		String circStatus3 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_3");
		//Date dueDate3 = dateFormatter.parse("09/30/2009");
		String isbn3 = TestConfiguration.getProperty("LOAN_ISBN_3");
		String loanLocation3 = TestConfiguration.getProperty("LOAN_LOCATION_3");
		String mediumType3= TestConfiguration.getProperty("LOAN_MEDIUMTYPE_3");
		String publisher3 = TestConfiguration.getProperty("LOAN_PUBLISHER_3");
		String title3 = TestConfiguration.getProperty("LOAN_TITLE_3");
		String barcode3 = TestConfiguration.getProperty("LOAN_BARCODE_3");
		String holdId3 = TestConfiguration.getProperty("LOAN_HOLD_ID_3");
			
		String itemId4 = TestConfiguration.getProperty("LOAN_ADM_ID4");
		String bibId4 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String author4 = TestConfiguration.getProperty("LOAN_AUTHOR_4");
		String callNumber4 = TestConfiguration.getProperty("LOAN_CALLNUMBER_4");
		String circStatus4 = TestConfiguration.getProperty("LOAN_CIRC_STATUS_4");
		//Date dueDate4 = dateFormatter.parse("09/30/2009");
		//String isbn4 = Configuration.getProperty("LOAN_ISBN_4");
		String loanLocation4 = TestConfiguration.getProperty("LOAN_LOCATION_4");
		String mediumType4= TestConfiguration.getProperty("LOAN_MEDIUMTYPE_4");
		String publisher4 = TestConfiguration.getProperty("LOAN_PUBLISHER_4");
		String title4 = TestConfiguration.getProperty("LOAN_TITLE_4");
		String barcode4 = TestConfiguration.getProperty("LOAN_BARCODE_4");
		String holdId4 = TestConfiguration.getProperty("LOAN_HOLD_ID4");
		
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		boolean found4 = false;
		for (AlephItem item : loans){
		
			if (item.getItemId().equals(itemId1)){
				assertEquals("Item Id Mismatch Expected: "+itemId1+" Actual: "+item.getItemId(),itemId1,item.getItemId());
				assertEquals("Bib Id Mismatch Expected: "+bibId1+" Actual: "+item.getBibId(),bibId1,item.getBibId());
				assertEquals("Holdings Id Mismatch Expected: "+holdId1+" Actual: "+item.getHoldingsId(),holdId1,item.getHoldingsId());
				assertEquals("Author Mismatch Expected: "+author1+" Actual: "+item.getAuthor(),author1,item.getAuthor());
				assertEquals("Call Number Mismatch Expected: "+callNumber1+" Actual: "+item.getCallNumber(),callNumber1,item.getCallNumber());
				assertEquals("Circ Status Mismatch Expected: "+circStatus1+" Actual: "+item.getCirculationStatus(),circStatus1,item.getCirculationStatus());
				assertEquals("Barcode Mismatch Expected: "+barcode1+" Actual: "+item.getBarcode(),barcode1,item.getBarcode());
				//assertEquals("Due Date Mismatch Expected: "+dueDate1+" Actual: "+item.getDueDate(),dueDate1,item.getDueDate());
				assertEquals("ISBN Mismatch Expected: "+isbn1+" Actual: "+item.getIsbn(),isbn1,item.getIsbn());
				assertEquals("Location Mismatch Expected: "+loanLocation1+" Actual: "+item.getLocation(),loanLocation1,item.getLocation());
				assertEquals("Medium Type Mismatch Expected: "+mediumType1+" Actual: "+item.getMediumType(),mediumType1.toLowerCase(),item.getMediumType().toLowerCase());
				assertEquals("Publisher Mismatch Expected: "+publisher1+" Actual: "+item.getPublisher(),publisher1,item.getPublisher());
				assertEquals("Title Mismatch Expected: "+title1+" Actual: "+item.getTitle(),title1,item.getTitle());
				found1 = true;
			} else if (item.getItemId().equals(itemId2)){
				assertEquals("Item Id Mismatch Expected: "+itemId2+" Actual: "+item.getItemId(),itemId2,item.getItemId());
				assertEquals("Bib Id Mismatch Expected: "+bibId2+" Actual: "+item.getBibId(),bibId2,item.getBibId());
				assertEquals("Holdings Id Mismatch Expected: "+holdId2+" Actual: "+item.getHoldingsId(),holdId2,item.getHoldingsId());
				assertEquals("Author Mismatch Expected: "+author2+" Actual: "+item.getAuthor(),author2,item.getAuthor());
				assertEquals("Call Number Mismatch Expected: "+callNumber2+" Actual: "+item.getCallNumber(),callNumber2,item.getCallNumber());
				assertEquals("Circ Status Mismatch Expected: "+circStatus2+" Actual: "+item.getCirculationStatus(),circStatus2,item.getCirculationStatus());
				assertEquals("Barcode Mismatch Expected: "+barcode2+" Actual: "+item.getBarcode(),barcode2,item.getBarcode());
				//assertEquals("Due Date Mismatch Expected: "+dueDate2+" Actual: "+item.getDueDate(),dueDate2,item.getDueDate());
				assertEquals("ISBN Mismatch Expected: "+isbn2+" Actual: "+item.getIsbn(),isbn2,item.getIsbn());
				assertEquals("Location Mismatch Expected: "+loanLocation2+" Actual: "+item.getLocation(),loanLocation2,item.getLocation());
				assertEquals("Medium Type Mismatch Expected: "+mediumType2+" Actual: "+item.getMediumType(),mediumType2.toLowerCase(),item.getMediumType().toLowerCase());
				assertEquals("Publisher Mismatch Expected: "+publisher2+" Actual: "+item.getPublisher(),publisher2,item.getPublisher());
				assertEquals("Title Mismatch Expected: "+title2+" Actual: "+item.getTitle(),title2,item.getTitle());
				found2 = true;
			} else if (item.getItemId().equals(itemId3)){
				assertEquals("Item Id Mismatch Expected: "+itemId3+" Actual: "+item.getItemId(),itemId3,item.getItemId());
				assertEquals("Bib Id Mismatch Expected: "+bibId3+" Actual: "+item.getBibId(),bibId3,item.getBibId());
				assertEquals("Holdings Id Mismatch Expected: "+holdId3+" Actual: "+item.getHoldingsId(),holdId3,item.getHoldingsId());
				assertEquals("Author Mismatch Expected: "+author3+" Actual: "+item.getAuthor(),author3,item.getAuthor());
				assertEquals("Call Number Mismatch Expected: "+callNumber3+" Actual: "+item.getCallNumber(),callNumber3,item.getCallNumber());
				assertEquals("Circ Status Mismatch Expected: "+circStatus3+" Actual: "+item.getCirculationStatus(),circStatus3,item.getCirculationStatus());
				assertEquals("Barcode Mismatch Expected: "+barcode3+" Actual: "+item.getBarcode(),barcode3,item.getBarcode());
				//assertEquals("Due Date Mismatch Expected: "+dueDate3+" Actual: "+item.getDueDate(),dueDate3,item.getDueDate());
				assertEquals("ISBN Mismatch Expected: "+isbn3+" Actual: "+item.getIsbn(),isbn3,item.getIsbn());
				assertEquals("Location Mismatch Expected: "+loanLocation3+" Actual: "+item.getLocation(),loanLocation3,item.getLocation());
				assertEquals("Medium Type Mismatch Expected: "+mediumType3+" Actual: "+item.getMediumType().toLowerCase(),mediumType3.toLowerCase(),item.getMediumType().toLowerCase());
				assertEquals("Publisher Mismatch Expected: "+publisher3+" Actual: "+item.getPublisher(),publisher3,item.getPublisher());
				found3 = true;
				assertEquals("Title Mismatch Expected: "+title3+" Actual: "+item.getTitle(),title3,item.getTitle());
			} else if (item.getItemId().equals(itemId4)){
				assertEquals("Item Id Mismatch Expected: "+itemId4+" Actual: "+item.getItemId(),itemId4,item.getItemId());
				assertEquals("Bib Id Mismatch Expected: "+bibId4+" Actual: "+item.getBibId(),bibId4,item.getBibId());
				assertEquals("Holdings Id Mismatch Expected: "+holdId4+" Actual: "+item.getHoldingsId(),holdId4,item.getHoldingsId());
				assertEquals("Author Mismatch Expected: "+author4+" Actual: "+item.getAuthor(),author4,item.getAuthor());
				assertEquals("Call Number Mismatch Expected: "+callNumber4+" Actual: "+item.getCallNumber(),callNumber4,item.getCallNumber());
				assertEquals("Circ Status Mismatch Expected: "+circStatus4+" Actual: "+item.getCirculationStatus(),circStatus4,item.getCirculationStatus());
				assertEquals("Barcode Mismatch Expected: "+barcode4+" Actual: "+item.getBarcode(),barcode4,item.getBarcode());
				//assertEquals("Due Date Mismatch Expected: "+dueDate4+" Actual: "+item.getDueDate(),dueDate4,item.getDueDate());
				//assertEquals("ISBN Mismatch Expected: "+isbn4+" Actual: "+item.getIsbn(),isbn4,item.getIsbn());
				assertEquals("Location Mismatch Expected: "+loanLocation4+" Actual: "+item.getLocation(),loanLocation4,item.getLocation());
				assertEquals("Medium Type Mismatch Expected: "+mediumType4+" Actual: "+item.getMediumType().toLowerCase(),mediumType4.toLowerCase(),item.getMediumType().toLowerCase());
				assertEquals("Publisher Mismatch Expected: "+publisher4+" Actual: "+item.getPublisher(),publisher4,item.getPublisher());
				assertEquals("Title Mismatch Expected: "+title4+" Actual: "+item.getTitle(),title4,item.getTitle());
				found4 = true;
			}
		}
		
		if (!found1){
			fail("Did not find loan item id: "+itemId1);
		}
		if (!found2){
			fail("Did not find loan item id: "+itemId2);
		}
		if (!found3){
			fail("Did not find loan item id: "+itemId3);
		}
		if (!found4){
			fail("Did not find loan item id: "+itemId4);
		}
	 
	}
}
