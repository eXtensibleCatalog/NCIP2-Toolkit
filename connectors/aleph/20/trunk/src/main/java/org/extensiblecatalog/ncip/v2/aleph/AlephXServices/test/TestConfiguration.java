/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  */

package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.test;

import java.io.IOException;
import java.util.Properties;

public class TestConfiguration {
	private static Properties props;
	
	
	public static String getProperty(String name) throws IOException{
		if (props==null){
			props = new Properties();
			props.load(ClassLoader.getSystemResourceAsStream("ilsInterface/aleph/AlephXServices/test/test.properties"));
		}
		return props.getProperty(name);
	}
	
	public static final String EXPECTED_LOAN_SIZE = "4";
}
