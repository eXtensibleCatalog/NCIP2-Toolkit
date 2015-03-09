package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test.TestConfiguration;

import java.net.URL;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import junit.framework.TestCase;

public class XServiceTest extends TestCase {
	
	public void testAddParameter(){
		String name = "user_name";
		String value1 = "rick";
		String value2 = "bob";
		String value3 = "ted!...";
		
		String name3 = "server";
		String value4 = "test_server";
		
		XService xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter(name, value1);
		xService.addParameter(name, value2);
		xService.addParameter(name, value3);
		//test not allow duplicates
		xService.addParameter(name, value3);
		//check parameters map, existing params include op=bor_auth
		assertTrue("Parameters Map is incorrect size, Expected: 2, Actual: "+xService.getParameters().size(),xService.getParameters().size()==2);
		//check param list
		assertTrue("Param "+name+" list is incorrect size, Expected: 3, Actual: "+xService.getParameters().get(name).size(),xService.getParameters().get(name).size()==3);
		//check param values
		assertTrue("Param "+name+" list does not contain expected value: "+value1,xService.getParameters().get(name).contains(value1));
		assertTrue("Param "+name+" list does not contain expected value: "+value2,xService.getParameters().get(name).contains(value2));
		assertTrue("Param "+name+" list does not contain expected value: "+value3,xService.getParameters().get(name).contains(value3));
		//check op param
		//check param list
		assertTrue("Param "+AlephConstants.PARAM_X_SERVICE_NAME+" list is incorrect size, Expected: 3, Actual: "+xService.getParameters().get(AlephConstants.PARAM_X_SERVICE_NAME).size(),xService.getParameters().get(AlephConstants.PARAM_X_SERVICE_NAME).size()==1);
		//check param values
		assertTrue("Param "+AlephConstants.PARAM_X_SERVICE_NAME+" list does not contain expected value: "+AlephConstants.XSERVICE_BOR_AUTH,xService.getParameters().get(AlephConstants.PARAM_X_SERVICE_NAME).contains(AlephConstants.XSERVICE_BOR_AUTH));
		//test allowing duplicates
		xService.addParameter(name, value3, true);
		//check parameters map
		assertTrue("Parameters Map is incorrect size, Expected: 2, Actual: "+xService.getParameters().size(),xService.getParameters().size()==2);
		//check param list
		assertTrue("Param "+name+" list is incorrect size, Expected: 4, Actual: "+xService.getParameters().get(name).size(),xService.getParameters().get(name).size()==4);
		//check param values, iterate through list to make sure it has correct number of each value
		int value1ExpCount = 1;
		int value2ExpCount = 1;
		int value3ExpCount = 2;
		int value1ActCount = 0;
		int value2ActCount = 0;
		int value3ActCount = 0;
		for (String value : xService.getParameters().get(name)){
			if (value.equals(value1)) value1ActCount++;
			if (value.equals(value2)) value2ActCount++;
			if (value.equals(value3)) value3ActCount++;
		}
		assertTrue("Param "+name+" list does not contain expected count for value: "+
				value1+" Expected: "+value1ExpCount+" Actual: "+value1ActCount,
				value1ExpCount==value1ActCount);
		assertTrue("Param "+name+" list does not contain expected count for value: "+
				value2+" Expected: "+value2ExpCount+" Actual: "+value2ActCount,
				value2ExpCount==value2ActCount);
		assertTrue("Param "+name+" list does not contain expected count for value: "+
				value3+" Expected: "+value3ExpCount+" Actual: "+value3ActCount,
				value3ExpCount==value3ActCount);
		
		xService.addParameter(name3,value4);
		//check parameters map
		assertTrue("Parameters Map is incorrect size, Expected: 3, Actual: "+xService.getParameters().size(),xService.getParameters().size()==3);
		//check param list
		assertTrue("Param "+name3+" list is incorrect size, Expected: 1, Actual: "+xService.getParameters().get(name3).size(),xService.getParameters().get(name3).size()==1);
		//check param values
		assertTrue("Param "+name3+" list does not contain expected value: "+value4,xService.getParameters().get(name3).contains(value4));
	}
	
	public void testGetParameterValues() throws Exception{
		XService xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter("user", "rick johnson");
		xService.addParameter("password","testing...%1...%2...!3");
		xService.addParameter("color","red");
		xService.addParameter("color","green");
		
		List<String> ops = xService.getParameterValues(AlephConstants.PARAM_X_SERVICE_NAME);
		assertTrue("Ops size is incorrect, Expected: 1, Actual: "+ops.size(),ops.size()==1);
		assertEquals("Ops value incorrect, Expected: "+AlephConstants.XSERVICE_BOR_AUTH+" Actual: "+ops.get(0),AlephConstants.XSERVICE_BOR_AUTH,ops.get(0));
		
		List<String> colors = xService.getParameterValues("color");
		assertTrue("Colors list wrong size, Expected: 2 Actual: "+colors.size(),colors.size()==2);
		assertTrue("Colors does not contain red",colors.contains("red"));
		assertTrue("Colors does not contain green",colors.contains("green"));
		
		//check param that does not exist returns empty list
		List <String> bad = xService.getParameterValues("bogus");
		assertTrue("Bogus param value returned values: "+bad,bad.size()==0);
	}
	
	public void testEncodeParameters() throws Exception{
		XService xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter("user", "rick johnson");
		xService.addParameter("password","testing...%1...%2...!3");
		xService.addParameter("color","red");
		xService.addParameter("color","green");
		String encoded = xService.encodeParameters(xService.getParameters());
		//order of these could switch since just pulling keys from a map so look for that, but leave this way for now
		String expected = AlephConstants.PARAM_X_SERVICE_NAME+"="+AlephConstants.XSERVICE_BOR_AUTH+"&color=red&color=green&password=testing...%251...%252...%213&user=rick+johnson";
		assertEquals("Parameters not encoded correctly, Expected: "+expected+" Actual: "+encoded,expected,encoded);
		System.out.println("encoded is: "+encoded);
	}
	
	public void testPostHttpRequest() throws Exception {
		String urlString = "http://";
		urlString += TestConfiguration.getProperty("XSERVER_NAME")+":"+TestConfiguration.getProperty("XSERVER_PORT")+"/X";
		URL url = new URL(urlString);
		
		XService xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		Document doc = xService.postHttpRequest(url);
		assertTrue("No xml data returned",doc.hasChildNodes());
		NodeList nodes = doc.getElementsByTagName("bor-auth");
		assertTrue("bor-auth node was not returned",nodes.getLength()>0);
		
		System.out.println("\nTest Post HTTP Output:\n");
		outputNode(doc);
	}
	
	public void testExecute() throws Exception {
		String serverName = TestConfiguration.getProperty("XSERVER_NAME");
		String serverPort = TestConfiguration.getProperty("XSERVER_PORT");
		String library = TestConfiguration.getProperty("ADM_LIBRARY");
		String invalid_patron_id = "test";
		String patron_id = TestConfiguration.getProperty("USERNAME");
		String password = TestConfiguration.getProperty("PASSWORD");
		String sub_library = TestConfiguration.getProperty("EXPECTED_AGENCY");
		
		XService xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		
		Document doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Missing data output:\n");
		outputNode(doc.getDocumentElement());
		
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
		xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter(AlephConstants.PARAM_LIBRARY, library);
		xService.addParameter(AlephConstants.PARAM_PATRON_ID,invalid_patron_id);
		xService.addParameter(AlephConstants.PARAM_PASSWORD,password);
		//xService.addParameter(XService.SUB_LIBRARY_PARAM,sub_library);
		
	    doc = xService.execute(serverName, serverPort, false);
	    
	    System.out.println("\nTest Execute Invalid credentials output:\n");
	    outputNode(doc.getDocumentElement());
	    
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
		xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter(AlephConstants.PARAM_LIBRARY, library);
		xService.addParameter(AlephConstants.PARAM_PATRON_ID,patron_id);
		xService.addParameter(AlephConstants.PARAM_PASSWORD,password);
		xService.addParameter(AlephConstants.PARAM_SUB_LIBRARY,sub_library);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Valid credentials output:\n");
		outputNode(doc.getDocumentElement());
		
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
		
		xService = new XService(AlephConstants.XSERVICE_BOR_AUTH);
		xService.addParameter(AlephConstants.PARAM_PATRON_ID,patron_id);
		xService.addParameter(AlephConstants.PARAM_PASSWORD,password);
		
		doc = xService.execute(serverName, serverPort, false);
		
		System.out.println("\nTest Execute Valid credentials w/out library output:\n");
		outputNode(doc.getDocumentElement());
		
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
	
	private void outputNode(Node n){
		outputNode(n,0);
	}
	
	private void outputNode(Node n, int tabs){
		for (int i=0; i<tabs; i++){
			System.out.print("\t");
		}
		System.out.println(n);
		if (n.hasChildNodes()){
			NodeList nodes = n.getChildNodes();
			for (int i=0; i<nodes.getLength(); i++){
				outputNode(nodes.item(i),tabs+1);
			}
		}
	}
}
