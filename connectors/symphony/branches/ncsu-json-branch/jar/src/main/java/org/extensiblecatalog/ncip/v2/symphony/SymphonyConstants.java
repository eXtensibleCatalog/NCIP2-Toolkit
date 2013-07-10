/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony;

import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddressType;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.UserAddressRoleType;


public class SymphonyConstants {
	
	public static final String HOME_LIBRARY = NCIPConfiguration.getProperty("HOME_LIBRARY");
	public static final String AGENCY_ID = NCIPConfiguration.getProperty("AGENCY_ID");

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
	public static final UserAddressRoleType EMAIL_ADDRESS_ROLE = new UserAddressRoleType("mailto");

    public static final ElectronicAddressType TELEPHONE_ADDRESS_TYPE= new ElectronicAddressType("tel");
	
	//USED TO CONSTRUCT USER PRIVILEGE ELEMENTS IN RETURN XML 
	//UNDER LOOKUP USER SERVICE - USER OPTIONAL FIELDS
	public static final String PRIV_STAT_DESCRIPTION = NCIPConfiguration.getProperty("PRIV_STAT_DESCRIPTION");
	public static final String PRIV_TYPE_STATUS = NCIPConfiguration.getProperty("PRIV_TYPE_STATUS");
	
	public static final String PRIV_USER_PROFILE_DESCRIPTION = NCIPConfiguration.getProperty("PRIV_USER_PROFILE_DESCRIPTION");
	public static final String PRIV_TYPE_PROFILE = NCIPConfiguration.getProperty("PRIV_TYPE_PROFILE");
	
	public static final String PRIV_LIB_DESCRIPTION = NCIPConfiguration.getProperty("PRIV_LIB_DESCRIPTION");
	public static final String PRIV_TYPE_LIBRARY = NCIPConfiguration.getProperty("PRIV_TYPE_LIBRARY");
	
	public static final String PRIV_CAT_DESCRIPTION = NCIPConfiguration.getProperty("PRIV_CAT_DESCRIPTION");
	public static final String PRIV_TYPE_CAT = NCIPConfiguration.getProperty("PRIV_TYPE_CAT");
	

    public static final ElectronicAddressType EMAIL_ADDRESS_TYPE = new ElectronicAddressType("electronic mail address");
	//
	public static final String AUTH_BARCODE = NCIPConfiguration.getProperty("AUTH_BARCODE");
	public static final String AUTH_UID = NCIPConfiguration.getProperty("AUTH_UID");
	


    public static final ProblemType BACKEND_ERROR = new ProblemType("Internal Error");

	//ERRORS MESSAGES AS CONSTANTS
	public static final String UNKNOWN_USER_ERROR = "User is not known";
	public static final String USER_PROBLEM_ELEMENT = "AuthenticationInput or UserId";
	public static final String CHECK_OUT_PROBLEM = "Problem performing checkout";
	public static final String CHECK_OUT_INPUT_PROBLEM = "Problem occurred validating input (user id or item id)";
	public static final String UNKNOWN_DATA_ELEMENT = "Unknown Data Element";
	public static final String CHECK_IN_PROBLEM = "Problem performing check in";
	public static final String SIRSI_FAILED = "SIRSI CALL FAILED";
	
	//ACCEPT ID ERRORS:
	public static final String ACCEPT_ITEM_PROBLEM = "Problem performing AcceptItem";
	public static final String ACCEPT_ITEM_INPUT_PROBLEM = "Problem occurred validating input (user id or item id)";

    public static final UserAddressRoleType TELEPHONE_ADDRESS_ROLE = new UserAddressRoleType("tel");
}
