/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/. 
 */

package org.extensiblecatalog.ncip.v2.koha.util;

import java.text.SimpleDateFormat;


/**
 * This class defines several constants as public static final variables which may be used throughout the NCIP Toolkit
 */
public class KohaConstants {
	// Koha configuration

	/**
	 * Currency code used for fines for koha
	 */
	public static final String CONFIG_KOHA_CURRENCY_CODE = "KohaCurrencyCode";

	/**
	 * Define Agency, ADMLibrary, BIBLibrary, and Hold Library for all agencies, This is actually the base of indexed properties in the config file
	 */
	public static final String CONFIG_KOHA_AGENCY = "KohaILSAgency";
	public static final String CONFIG_KOHA_ADM_LIBRARY = "KohaAdmLibrary";
	public static final String CONFIG_KOHA_BIB_LIBRARY = "KohaBibLibrary";
	public static final String CONFIG_KOHA_HOLD_LIBRARY = "KohaHoldLibrary";
	

	public static final String OPAC_PORT = "OPACPort";
	public static final String OPAC_SERVER = "OPACServer";
	public static final String ILS_DI_SUFFIX = "ILSDISuffix";
	public static final String AUTH_DATA_FORMAT_TYPE = "AuthDataFormatType";
	public static final String USER_REGISTRATION_LINK = "UserRegistrationLink";

	public static final String AGENCY_UNSTRUCTURED_ADDRESS = "AgencyUnstructuredAddress";
	public static final String AGENCY_TRANSLATED_NAME = "AgencyTranslatedName";
	public static final String NCIP_TOOLKIT_VERSION = "NCIPToolkitVersion";

	public static final String XSERVER_NAME = "AlephXServerName";
	public static final String XSERVER_PORT = "AlephXServerPort";
	public static final String XSERVER_SUFFIX = "AlephXServerSuffix";

	public static final String DEFAULT_AGENCY = "DefaultAgency";
	public static final String BIBLIOGRAPHIC_LIBRARY = "AlephBibLibrary";
	public static final String ALEPH_ADMINISTRATIVE_LIBRARY = "AlephAdmLibrary";

	public static final String MAX_ITEM_PREPARATION_TIME_DELAY = "MaxItemPreparationTimeDelay";
	public static final String NEXT_ITEM_TOKEN_EXPIRATION_TIME = "NextItemTokenExpirationTime";

	public static final String INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS = "IncludeParticularProblemsInLookupItemSet";

	public static final String DEFAULT_SCHEME = "http://www.niso.org/schemas/ncip/v2_02/ncip_v2_02.xsd";
	
	public static final String REQUEST_ID_DELIMITER = ";";
	
	public static final SimpleDateFormat KOHA_DATE_FORMATTER = new SimpleDateFormat("yyy-MM-dd");
	
	public static final String PARAM_SERVICE = "service";
	public static final String PARAM_ID = "id";
	
	public static final String ILS_DI_LOOKUP_USER = "GetPatronInfo";
	public static final String ILS_DI_LOOKUP_ITEM = "GetRecords";

	public static final String STATUS_OK = "ok";


}
