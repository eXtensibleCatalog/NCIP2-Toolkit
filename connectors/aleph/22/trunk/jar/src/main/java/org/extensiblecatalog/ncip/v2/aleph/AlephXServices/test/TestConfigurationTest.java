package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test;

import java.io.IOException;

import junit.framework.TestCase;

public class TestConfigurationTest extends TestCase {

	public void testGetProperty() throws IOException{
		assertEquals(TestConfiguration.getProperty("XSERVER_PORT"),"8991");
	}

}
