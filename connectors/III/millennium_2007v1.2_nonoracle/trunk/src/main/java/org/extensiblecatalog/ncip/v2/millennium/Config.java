package org.extensiblecatalog.ncip.v2.millennium;

public class Config {

	
	public String millenniumUrl 	= "url, no http://";
	public String millenniumPort	= "80";
	public String libraryName 		= "Library Name";
	public String defaultAgency		= "Agency Name";
	
	// These are used temporarily until authentication issues are resolved
	private String tempUser 		= "test user";
	private String tempPass 		= "test pass";
	
	// Authentication type. Chocies: ldap, patron or both
	private String authType			= "both";
	
	public String getProperty(String value) {
		
		String strReturn = null;
		
		if (value.equals("millenniumUrl"))
			strReturn =  millenniumUrl;
				
		if (value.equals("libraryName"))
			strReturn =  libraryName;

		// These are used temporarily until authentication issues are resolved
		if (value.equals("tempUser"))
			strReturn =  tempUser;
		
		if (value.equals("tempPass"))
			strReturn =  tempPass;
		
		if (value.equals("defaultAgency"))
			strReturn = defaultAgency;
		
		if (value.equals("authType"))
			strReturn = authType;
				
		// Int Values
		if (value.equals("millenniumPort"))
			return millenniumPort;
		
		System.out.println("Returning: " + strReturn);
		
		return strReturn;
	}
	
}
