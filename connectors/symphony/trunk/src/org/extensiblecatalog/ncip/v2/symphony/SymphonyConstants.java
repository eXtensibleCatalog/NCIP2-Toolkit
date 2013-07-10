/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;




public class SymphonyConstants {
	
	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	

    //SIRSI CONSTANTS USED TO PARSE RETURN STRING:
	public static final String SIRSI_ENTRY_ID = "NH";
	public static final String SIRSI_LANGUAGE = "Ib";
	public static final String SIRSI_LAST_NAME = "uL";
	public static final String SIRSI_FIRST_NAME = "uF";
	public static final String SIRSI_FULL_NAME = "UA";
	public static final String SIRSI_USERID = "UO";
	public static final String SIRSI_PRIV_PATRON_TYPE = "PE";
	public static final String SIRSI_PRIV_STATUS = "UJ";
	public static final String SIRSI_PRIV_DEPT = "UN";
	public static final String SIRSI_MESSAGE_NUMBER = "MN";
	public static final String SIRSI_DUE_DATE = "CI";
	public static final String SIRSI_AUTHOR = "IA";
	public static final String SIRSI_TITLE = "IB";
	public static final String SIRSI_CALL_NUMBER = "IQ";
	
	//SIRSI SUCCESSFUL RETURN CODES:
	public static final String CHARGED_OUT_SUCCESSFUL = "250";
	public static final String CHECKED_IN_SUCCESSFUL = "205";
	public static final String ACCEPT_ITEM_SUCCESSFUL = "209";
	
	
	public static final String SIRSI_CITY_STATE = "CITY/STATE";
	public static final String SIRSI_STREET = "STREET";
	public static final String SIRSI_ZIP = "ZIP";
	public static final String SIRSI_EMAIL = "EMAIL";
	public static final String SIRSI_HOME_PHONE = "HOMEPHONE";
	public static final String PRIMARY_ADDRESS_ROLE = "Primary Address";
	public static final String POSTAL_ADDRESS_TYPE = "Postal Address";
	public static final String HOME_ADDRESS_ROLE = "Home";
	public static final String EMAIL_ADDRESS_TYPE = "mailto";
	public static final String TELEPHONE_ADDRESS_TYPE="tel";
	


	//ERRORS MESSAGES AS CONSTANTS
	public static final String UNKNOWN_USER_ERROR = "User is not known";
	public static final String USER_PROBLEM_ELEMENT = "AuthenticationInput or UserId";
	public static final String CHECK_OUT_PROBLEM = "Problem performing checkout";
	public static final String CHECK_OUT_INPUT_PROBLEM = "Problem occurred validating input (user id or item id)";
	public static final String UNKNOWN_DATA_ELEMENT = "Unknown Data Element";
	public static final String CHECK_IN_PROBLEM = "Problem performing check in";
	public static final String SIRSI_FAILED = "SIRSI CALL FAILED";
	
	//ACCEPT ITEM ERRORS:
	public static final String ACCEPT_ITEM_PROBLEM = "Problem performing AcceptItem";
	public static final String ACCEPT_ITEM_INPUT_PROBLEM = "Problem occurred validating input (user id or item id)";
	
}
