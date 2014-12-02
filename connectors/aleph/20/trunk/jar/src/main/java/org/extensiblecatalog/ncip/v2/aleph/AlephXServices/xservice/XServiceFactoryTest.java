package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice;

import org.extensiblecatalog.ncip.v2.aleph.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.agency.AlephAgencyFactory;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.user.AlephXServicesUser;
import org.extensiblecatalog.ncip.v2.aleph.user.AlephXServicesUserFactory;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.XMLParserUtil;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test.TestConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;

public class XServiceFactoryTest extends TestCase {

	public void testCreateBorAuthXService() throws Exception{
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String sub_library = TestConfiguration.getProperty("EXPECTED_AGENCY");
		String patron_id = "test_user";
		String password = "test_password";
		XService xService = XServiceFactory.createBorAuthXService(library, sub_library, 
				patron_id, password);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_BOR_AUTH+" Actual: "+ops.get(0),AlephConstants.XSERVICE_BOR_AUTH,ops.get(0));
	}
	
	public void testCreateBorInfoXService() throws Exception{
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String patron_id = "test_user";
		String password = "test_password";
		XService xService = XServiceFactory.createBorInfoXService(library, patron_id, password, AlephConstants.USER_CASH_ALL_DATA, null, AlephConstants.USER_HOLDS_ALL_DATA, AlephConstants.USER_LOANS_ALL_DATA);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_BOR_INFO+" Actual: "+ops.get(0),AlephConstants.XSERVICE_BOR_INFO,ops.get(0));
	}
	
	public void testCreateCircStatusXService() throws Exception{
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String system_number = TestConfiguration.getProperty("LOAN_BIB_ID1");
		XService xService = XServiceFactory.createCircStatusXService(library, system_number);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_CIRC_STATUS+" Actual: "+ops.get(0),AlephConstants.XSERVICE_CIRC_STATUS,ops.get(0));
	}
	
	public void testCreateItemDataXService() throws Exception{
		String base = TestConfiguration.getProperty("ADM_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_BIB_ID1");
		XService xService = XServiceFactory.createItemDataXService(base, doc_number);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_ITEM_DATA+" Actual: "+ops.get(0),AlephConstants.XSERVICE_ITEM_DATA,ops.get(0));
	}
	
	public void testCreateReadItemXService() throws Exception{
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String barcode = TestConfiguration.getProperty("LOAN_BARCODE_1");
		XService xService = XServiceFactory.createReadItemXService(library, barcode);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_READ_ITEM+" Actual: "+ops.get(0),AlephConstants.XSERVICE_READ_ITEM,ops.get(0));
	}
	
	public void testExecuteReadItemXService() throws Exception{
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String barcode = TestConfiguration.getProperty("LOAN_BARCODE_1");
		String doc_number = TestConfiguration.getProperty("LOAN_DOC_NUMBER_1");
		String item_sequence = TestConfiguration.getProperty("LOAN_SEQ_NUMBER_1");
		XService xService = XServiceFactory.createReadItemXService(library, barcode);
		Document doc = xService.execute(serverName, serverPort, false);
		
		XMLParserUtil.outputNode(doc);
		
	    //test get holding id from right barcode
		assertTrue("No xml data returned",doc.hasChildNodes());
		NodeList nodes = doc.getElementsByTagName("read-item");
		assertTrue("read-item node was not returned",nodes.getLength()>0);
		assertTrue("More than one read-item node was returned",nodes.getLength()==1);
		Node n = nodes.item(0);
		assertTrue("read-item has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("error");
		assertFalse("Unexpected error found: "+n.getNodeValue(),nodes.getLength()>0);
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("z30");
		n = nodes.item(0);
		assertEquals("No z30 node found","z30",n.getNodeName());
		
		xService = XServiceFactory.createReadItemXService(library, doc_number, item_sequence);
		doc = xService.execute(serverName, serverPort, false);
		assertTrue("No xml data returned",doc.hasChildNodes());
		nodes = doc.getElementsByTagName("read-item");
		assertTrue("read-item node was not returned",nodes.getLength()>0);
		assertTrue("More than one read-item node was returned",nodes.getLength()==1);
		n = nodes.item(0);
		assertTrue("read-item has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("error");
		assertFalse("Unexpected error found: "+n.getNodeValue(),nodes.getLength()>0);
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("z30");
		n = nodes.item(0);
		assertEquals("No z30 node found","z30",n.getNodeName());
		XMLParserUtil.outputNode(doc);
	}
	
	public void testFindDocDataXService() throws Exception{
		String base = TestConfiguration.getProperty("ADM_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_BIB_ID1");
		XService xService = XServiceFactory.createFindDocXService(base, doc_number);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_FIND_DOC+" Actual: "+ops.get(0),AlephConstants.XSERVICE_FIND_DOC,ops.get(0));
	}
	
	public void testExecuteBorAuthXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String invalid_patron_id = "test";
		String patron_id = TestConfiguration.getProperty("USERNAME");
		String password = TestConfiguration.getProperty("PASSWORD");
		String sub_library = TestConfiguration.getProperty("EXPECTED_AGENCY");
		
		XService xService = XServiceFactory.createBorAuthXService(null,null,null,null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data output:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		assertTrue("No xml data returned",doc.hasChildNodes());
		NodeList nodes = doc.getElementsByTagName("bor-auth");
		assertTrue("bor-auth node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-auth node was returned",nodes.getLength()==1);
		Node n = nodes.item(0);
		assertTrue("bor-auth has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("error");
		n = nodes.item(0);
		assertEquals("No error node found", "error",n.getNodeName());
		nodes = n.getChildNodes();
		n = nodes.item(0);
		String expectedError = AlephConstants.ERROR_BOR_ID_VER; 
		assertEquals("Expected Error not found when no params supplied to bor-auth",expectedError.toLowerCase(),n.getNodeValue().toLowerCase());
		
		//test with invalid patron id
		
		xService = XServiceFactory.createBorAuthXService(library, sub_library, 
				invalid_patron_id, password);
		
	    doc = xService.execute(serverName, serverPort, false);
	    
	    System.out.println("\nTest Execute Invalid credentials output:\n");
	    XMLParserUtil.outputNode(doc.getDocumentElement());
	    
	    assertTrue("No xml data returned",doc.hasChildNodes());
		nodes = doc.getElementsByTagName("bor-auth");
		assertTrue("bor-auth node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-auth node was returned",nodes.getLength()==1);
		n = nodes.item(0);
		assertTrue("bor-auth has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("error");
		n = nodes.item(0);
		assertEquals("No error node found", "error",n.getNodeName());
		nodes = n.getChildNodes();
		n = nodes.item(0);
		expectedError = AlephConstants.ERROR_AUTH_VERIFICATION; 
		assertEquals("Expected Error not found when no params supplied to bor-auth",expectedError.toLowerCase(),n.getNodeValue().toLowerCase());
		
		//test proper credentials w/ library and w/ out
		xService = XServiceFactory.createBorAuthXService(library, sub_library, 
				patron_id, password);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Valid credentials output:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		assertTrue("No xml data returned",doc.hasChildNodes());
		nodes = doc.getElementsByTagName("bor-auth");
		assertTrue("bor-auth node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-auth node was returned",nodes.getLength()==1);
		n = nodes.item(0);
		assertTrue("bor-auth has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("z303");
		n = nodes.item(0);
		assertEquals("No z303 node found", "z303",n.getNodeName());
		assertTrue("z303 node has no children",n.hasChildNodes());
		nodes = doc.getElementsByTagName("z304");
		n = nodes.item(0);
		assertEquals("No z304 node found", "z304",n.getNodeName());
		assertTrue("z304 node has no children",n.hasChildNodes());
		nodes = doc.getElementsByTagName("z305");
		n = nodes.item(0);
		assertEquals("No z305 node found", "z305",n.getNodeName());
		assertTrue("z305 node has no children",n.hasChildNodes());
		
		xService = XServiceFactory.createBorAuthXService(null, null, 
				patron_id, password);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Valid credentials w/out library output:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		assertTrue("No xml data returned",doc.hasChildNodes());
		nodes = doc.getElementsByTagName("bor-auth");
		assertTrue("bor-auth node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-auth node was returned",nodes.getLength()==1);
		n = nodes.item(0);
		assertTrue("bor-auth has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("z303");
		n = nodes.item(0);
		assertEquals("No z303 node found", "z303",n.getNodeName());
		assertTrue("z303 node has no children",n.hasChildNodes());
		nodes = doc.getElementsByTagName("z304");
		n = nodes.item(0);
		assertEquals("No z304 node found", "z304",n.getNodeName());
		assertTrue("z304 node has no children",n.hasChildNodes());
		nodes = doc.getElementsByTagName("z305");
		n = nodes.item(0);
		assertEquals("No z305 node found", "z305",n.getNodeName());
		assertTrue("z305 node has no children",n.hasChildNodes());
	}
	
	public void testExecuteBorInfoXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String invalid_patron_id = "test";
		//rick
		String patron_id = TestConfiguration.getProperty("USERNAME");
		//pascal
		//String patron_id = Configuration.getProperty("PATRON_ID2");
		String cash = AlephConstants.USER_CASH_ALL_DATA;
		String holds = AlephConstants.USER_HOLDS_ALL_DATA;
		String loans = AlephConstants.USER_LOANS_ALL_DATA;
		
		XService xService = XServiceFactory.createBorInfoXService(null, null, null, null, null, null, null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data output:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		assertTrue("No xml data returned",doc.hasChildNodes());
		NodeList nodes = doc.getElementsByTagName("bor-info");
		assertTrue("bor-info node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-info node was returned",nodes.getLength()==1);
		
		Node n = nodes.item(0);
		assertTrue("bor-info has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("error");
		n = nodes.item(0);
		assertEquals("No error node found", "error",n.getNodeName());
		nodes = n.getChildNodes();
		n = nodes.item(0);
		String expectedError = AlephConstants.ERROR_BOR_ID_MISSING; 
		assertEquals("Expected Error not found when no params supplied to bor-info",expectedError.toLowerCase(),n.getNodeValue().toLowerCase());
		
		//test with invalid patron id
		xService = XServiceFactory.createBorInfoXService(library, invalid_patron_id, null, cash, null, holds, loans);
		
	    doc = xService.execute(serverName, serverPort, false);
	    
	    System.out.println("\nTest Execute Invalid credentials output:\n");
	    XMLParserUtil.outputNode(doc.getDocumentElement());
	    
	    assertTrue("No xml data returned",doc.hasChildNodes());
		nodes = doc.getElementsByTagName("bor-info");
		assertTrue("bor-info node was not returned",nodes.getLength()>0);
		assertTrue("More than one bor-info node was returned",nodes.getLength()==1);
		n = nodes.item(0);
		assertTrue("bor-info has no child nodes", n.hasChildNodes());
		nodes = doc.getElementsByTagName("session-id");
		n = nodes.item(0);
		assertEquals("No session-id node found","session-id",n.getNodeName());
		nodes = doc.getElementsByTagName("error");
		n = nodes.item(0);
		assertEquals("No error node found", "error",n.getNodeName());
		nodes = n.getChildNodes();
		n = nodes.item(0);
		String expectedError1 = AlephConstants.ERROR_RETRIEVE_PATRON_RECORD;
		String expectedError2 = AlephConstants.ERROR_PATRON_SYSTEM_KEY;
		assertTrue("Expected Error not found invalid patron_id passed to bor-info",expectedError1.equalsIgnoreCase(n.getNodeValue())||expectedError2.equalsIgnoreCase(n.getNodeValue()));
		
		//test with no loan, holds, or cash data
		xService = XServiceFactory.createBorInfoXService(library, patron_id, null, AlephConstants.USER_CASH_NO_DATA, "test", AlephConstants.USER_HOLDS_NO_DATA, AlephConstants.USER_LOANS_NO_DATA);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Valid data output no loans, holds, or cash:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		//test valid credentials
		xService = XServiceFactory.createBorInfoXService(library, patron_id, null, cash, null, holds, loans);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Bor Info Valid data output:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
	}
	
	public void testExecuteCircStatusXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("BIB_LIBRARY");
		String system_number = TestConfiguration.getProperty("LOAN_BIB_ID4");
		
		XService xService = XServiceFactory.createCircStatusXService(null, null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		xService = XServiceFactory.createCircStatusXService(library, system_number);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Circ Status Execute Correct Input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
	}
	
	public void testExecuteItemDataXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String base = TestConfiguration.getProperty("BIB_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_BIB_ID4");
		//String doc_number = "2249515";
		//String doc_number = "2249689";
		//String doc_number = Configuration.getProperty("LOAN_ADM_ID1;
		
		XService xService = XServiceFactory.createItemDataXService(null, null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		xService = XServiceFactory.createItemDataXService(base, doc_number);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Item Data Execute Correct Input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
	}
	
	public void testExecuteFindDocXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String base = TestConfiguration.getProperty("ADM_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_ADM_ID1");
		//String doc_number = "1738880";
		
		XService xService = XServiceFactory.createFindDocXService(null, null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		xService = XServiceFactory.createFindDocXService(base, doc_number);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Find Doc Execute Correct Input:\n");
		XMLParserUtil.outputNode(doc.getDocumentElement());
	}
	
	public void testExecuteGetHoldingXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String base = TestConfiguration.getProperty("ADM_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_BIB_ID1");
		XService xService = XServiceFactory.createGetHoldingXService(null, null);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Get Holding Missing data input:\n");
		
		XMLParserUtil.outputNode(doc.getDocumentElement());
		
		xService = XServiceFactory.createGetHoldingXService(base, doc_number);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Get Holding good data input:\n");
		
		XMLParserUtil.outputNode(doc.getDocumentElement());
	}
	
	public void testCreateHoldRequestXService() throws Exception {
		//String serverName = Configuration.getProperty("XSERVER_NAME");
		//String serverPort = Configuration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("BIB_LIBRARY");
		//String rec_key = Configuration.getProperty("LOAN_ADM_ID1;
		String barcode = "0012345";
		String bor_id = "testId";
		XService xService = XServiceFactory.createHoldRequestXService(library, barcode, bor_id);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_HOLD_REQUEST+" Actual: "+ops.get(0),AlephConstants.XSERVICE_HOLD_REQUEST,ops.get(0));
	}
	
	public void testExecuteHoldRequestXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		//String doc_number2 = Configuration.getProperty("LOAN_BIB_ID1;
		String doc_number = TestConfiguration.getProperty("LOAN_DOC_NUMBER_4");
		String seq_number = TestConfiguration.getProperty("LOAN_SEQ_NUMBER_4");
		String barcode2 = TestConfiguration.getProperty("AVAIL_BARCODE_1");
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		
		//test invalid data
		XService xService = XServiceFactory.createHoldRequestXService(library, null, null, bor_id);
		Document doc = xService.execute(serverName, serverPort, false);
		String reply = XMLParserUtil.getReply(doc);
		assertNull("Hold request executed with improper data. reply: "+reply,reply);
		String error = XMLParserUtil.getError(doc);
		assertNotNull("Error not returned when expected", error);
		XMLParserUtil.outputNode(doc);
		
		//test with doc number and item sequence
		xService = XServiceFactory.createHoldRequestXService(library, doc_number, seq_number, bor_id);
		doc = xService.execute(serverName, serverPort, false);
		reply = XMLParserUtil.getReply(doc);
		assertEquals("Hold request not executed successfully",AlephConstants.STATUS_OK,reply);
		error = XMLParserUtil.getError(doc);
		assertNull("Error returned when not expected: "+error, error);
		XMLParserUtil.outputNode(doc);
		
		//test with barcode
		xService = XServiceFactory.createHoldRequestXService(library, barcode2, bor_id);
		doc = xService.execute(serverName, serverPort, false);
		reply = XMLParserUtil.getReply(doc);
		assertEquals("Hold request not executed successfully",AlephConstants.STATUS_OK,reply);
		error = XMLParserUtil.getError(doc);
		assertNull("Error returned when not expected: "+error, error);
		XMLParserUtil.outputNode(doc);
	}
	
	public void testExecuteCancelHoldRequestXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String admLibrary = TestConfiguration.getProperty("ADM_LIBRARY");
		String agencyId = TestConfiguration.getProperty("EXPECTED_AGENCY");
		String bibLibrary = TestConfiguration.getProperty("BIB_LIBRARY");
		String holdLibrary = TestConfiguration.getProperty("HOL_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_DOC_NUMBER_4");
		String seq_number = TestConfiguration.getProperty("LOAN_SEQ_NUMBER_4");
		String item_id = doc_number+seq_number;
		String doc_number2 = TestConfiguration.getProperty("AVAIL_DOC_NUMBER_1");
		String item_id2 = doc_number2+seq_number;
		//String cancel_seq = "000150155";
		String bor_id = TestConfiguration.getProperty("PATRON_ID");
		
		String cash = AlephConstants.USER_CASH_ALL_DATA;
		
		String holds = AlephConstants.USER_HOLDS_ALL_DATA;
			
		String loans = AlephConstants.USER_LOANS_ALL_DATA;
		
		AlephAgency agency = AlephAgencyFactory.createAlephAgency(agencyId, admLibrary, bibLibrary, holdLibrary);
		
		XService xService = XServiceFactory.createBorInfoXService(admLibrary, bor_id, null, cash, null, holds, loans);
		Document doc = xService.execute(serverName,serverPort, false);
		
		//generate new aleph user
		AlephXServicesUser user = AlephXServicesUserFactory.createAlephUser(agency,doc);
		//then cancel all holds returned (maybe more than one)
		
		if (user!=null&&user.getRequestedItems()!=null&&user.getRequestedItems().size()>0){
			for (AlephItem eachItem : user.getRequestedItems()){
				System.out.println("Requested Item id: "+eachItem.getItemId()+"Holdings id: "+eachItem.getHoldingsId());
				if (eachItem!=null&&eachItem.getHoldRequestId()!=null&&
						(item_id.equals(eachItem.getItemId())||item_id2.equals(eachItem.getItemId()))){
					xService = XServiceFactory.createCancelHoldRequestXService(admLibrary, eachItem.getItemId(), eachItem.getHoldRequestId());
					doc = xService.execute(serverName, serverPort, false);
					XMLParserUtil.outputNode(doc);
				}
			}
		}
	}
	
	public void testCreateRenewXService() throws Exception {
		
		String library = TestConfiguration.getProperty("BIB_LIBRARY");
		String doc_number = TestConfiguration.getProperty("LOAN_ADM_ID1");
		String seq_number = "000010";
		String barcode = "0012345";
		String bor_id = "testId";
		XService xService = XServiceFactory.createRenewXService(library, barcode, bor_id);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_RENEW+" Actual: "+ops.get(0),AlephConstants.XSERVICE_RENEW,ops.get(0));
		
		xService = XServiceFactory.createRenewXService(library, doc_number, seq_number, bor_id);
		ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_RENEW+" Actual: "+ops.get(0),AlephConstants.XSERVICE_RENEW,ops.get(0));
	}
	
	public void testExecuteRenewXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		
		//galileo book
		String barcode2 = TestConfiguration.getProperty("LOAN_BARCODE_4");
		String bor_id_checkedout = TestConfiguration.getProperty("PATRON_ID");
		String bor_id_not_checkedout = TestConfiguration.getProperty("PATRON_ID_NOT_CHECKED_OUT");

		//test invalid data
		XService xService = XServiceFactory.createRenewXService(library, null, null, bor_id_checkedout);
		Document doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
		
		String reply = XMLParserUtil.getReply(doc);
		assertNull("Renew executed with improper data. reply: "+reply,reply);
		String error = XMLParserUtil.getError(doc);
		assertNotNull("Error not returned when expected", error);

		//test with bad id not checked out
		//test with doc number and item sequence
		/*xService = XServiceFactory.createRenewXService(library, doc_number, seq_number, bor_id_not_checkedout);
		doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
		
		reply = XMLParserUtil.getReply(doc);
		assertNull("Renew executed with improper data. reply: "+reply,reply);
		error = XMLParserUtil.getError(doc);
		assertNotNull("Error not returned when expected", error);
		
		//proper data
		xService = XServiceFactory.createRenewXService(library, doc_number, seq_number, bor_id_checkedout);
		doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
		
		reply = XMLParserUtil.getReply(doc);
		assertEquals("Renew not executed successfully",AlephConstants.STATUS_OK,reply);
		error = XMLParserUtil.getError(doc);
		assertNull("Error returned when not expected: "+error, error);
		*/
		
		//test with bad id not checked out
		//test with barcode
		xService = XServiceFactory.createRenewXService(library, barcode2, bor_id_not_checkedout);
		doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
		
		reply = XMLParserUtil.getReply(doc);
		assertNull("Renew executed with improper data. reply: "+reply,reply);
		error = XMLParserUtil.getError(doc);
		assertNotNull("Error not returned when expected", error);
		
		//proper data
		xService = XServiceFactory.createRenewXService(library, barcode2, bor_id_checkedout);
		doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
		
		reply = XMLParserUtil.getReply(doc);
		assertEquals("Renew not executed successfully",AlephConstants.STATUS_OK,reply);
		error = XMLParserUtil.getError(doc);
		assertNull("Error returned when not expected: "+error, error);
	}
	
	public void testCreatePublishAvailabilityXService() throws Exception {
		
		String library = TestConfiguration.getProperty("BIB_LIBRARY");
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID1");
		String bibId2 = "002249689";
		String bibId3 = "002249690";
		String bibId4 = "002249691";
		String bibId5 = "002249692";
		String bibId6 = "002249693";
		String bibId7 = "002249694";
		String bibId8 = "002249695";
		String bibId9 = "002249696";
		String bibId10 = "002249697";
		String bibId11 = "002249698";
		
		String expectedDocNums = bibId1;
		expectedDocNums += ","+bibId2;
		expectedDocNums += ","+bibId3;
		expectedDocNums += ","+bibId4;
		expectedDocNums += ","+bibId5;
		expectedDocNums += ","+bibId6;
		expectedDocNums += ","+bibId7;
		expectedDocNums += ","+bibId8;
		expectedDocNums += ","+bibId9;
		expectedDocNums += ","+bibId10;
		
		List<String> bibIds = new ArrayList<String>();
		bibIds.add(bibId1);
		bibIds.add(bibId2);
		bibIds.add(bibId3);
		bibIds.add(bibId4);
		bibIds.add(bibId5);
		bibIds.add(bibId6);
		bibIds.add(bibId7);
		bibIds.add(bibId8);
		bibIds.add(bibId9);
		bibIds.add(bibId10);
		
		XService xService = XServiceFactory.createPublishAvailabilityXService(library, bibIds);
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops invalid size, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Op value incorrect, Expected: "+AlephConstants.XSERVICE_PUBLISH_AVAILABILITY+" Actual: "+ops.get(0),AlephConstants.XSERVICE_PUBLISH_AVAILABILITY,ops.get(0));
		
		//check doc_num value
		List<String> docnums = xService.getParameterValues(AlephConstants.PARAM_DOC_NUM);
		assertTrue("Doc nums invalid size, Expected: 1, Actual: "+docnums.size(),docnums.size()==1);
		assertEquals("Doc num value incorrect, Expected: "+expectedDocNums+" Actual: "+docnums.get(0),expectedDocNums,docnums.get(0));
		
		//check throws exception for list too big
		bibIds.add(bibId11);
		boolean threwException = false;
		try {
			xService = XServiceFactory.createPublishAvailabilityXService(library, bibIds);
		} catch (Exception ex){
			System.out.println("Exception thrown correctly for list too large: "+ex);
			threwException = true;
		}
		
		assertTrue("Failed to throw exception for list greater than 10",threwException);
	}
	
	public void testExecutePublishAvailabilityXService() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("BIB_LIBRARY");
		String bibId1 = TestConfiguration.getProperty("LOAN_BIB_ID1");
		String bibId2 = TestConfiguration.getProperty("LOAN_BIB_ID4");
		String bibId3 = TestConfiguration.getProperty("LOAN_BIB_ID3");
		String bibId4 = TestConfiguration.getProperty("LOAN_BIB_ID2");
		
		List<String> bibIds = new ArrayList<String>();
		bibIds.add(bibId1);
		bibIds.add(bibId2);
		bibIds.add(bibId3);
		bibIds.add(bibId4);
		
		XService xService = XServiceFactory.createPublishAvailabilityXService(library, bibIds);
		Document doc = xService.execute(serverName, serverPort, false);
		XMLParserUtil.outputNode(doc);
	}
}
