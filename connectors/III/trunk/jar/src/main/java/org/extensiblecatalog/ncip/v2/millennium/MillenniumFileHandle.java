/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MillenniumFileHandle { 
    
	private static final Logger LOG = Logger.getLogger(MillenniumRemoteServiceManager.class); 
	
	public String ReadTextFile (String fileName) { 
     
		File file = new File(fileName); 
		StringBuffer contents = new StringBuffer(); 
		BufferedReader reader = null; 
		try {  
			reader = new BufferedReader(new FileReader(file)); 
			String text = null; 
			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {     
				contents.append(text).append(System.getProperty("line.separator"));
			} 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
        
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {     
			try {         
				if (reader != null) {         
					reader.close(); 
				}
			} catch (IOException e) {         
				e.printStackTrace();
			} 
		} 
	// show file contents here 
	//LOG.debug(contents.toString());
	return contents.toString();
	}
}

