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

	public static final String DEFAULT_SCHEME = "http://www.niso.org/schemas/ncip/v2_02/ncip_v2_02.xsd";

	private static final String COMMA_DELIMITER = ",";
	private static final String SEMICOLON_DELIMITER = ";";

	// Constants for parsing from toolkit.properties
	public static final String CONF_OPAC_PORT = "OPACPort";
	public static final String CONF_OPAC_SERVER = "OPACServer";
	public static final String CONF_SVC_PORT = "SVCPort";
	public static final String CONF_ILS_DI_SUFFIX = "ILSDISuffix";
	public static final String CONF_SVC_SUFFIX = "SVCSuffix";
	public static final String CONF_DEFAULT_AGENCY = "AgencyId";
	public static final String CONF_AUTH_DATA_FORMAT_TYPE = "AuthDataFormatType";
	public static final String CONF_USER_REGISTRATION_LINK = "UserRegistrationLink";
	public static final String CONF_AGENCY_UNSTRUCTURED_ADDRESS = "AgencyUnstructuredAddress";
	public static final String CONF_AGENCY_TRANSLATED_NAME = "AgencyTranslatedName";
	public static final String CONF_NCIP_TOOLKIT_VERSION = "NCIPToolkitVersion";
	public static final String CONF_MAX_ITEM_PREPARATION_TIME_DELAY = "MaxItemPreparationTimeDelay";
	public static final String CONF_NEXT_ITEM_TOKEN_EXPIRATION_TIME = "NextItemTokenExpirationTime";
	public static final String CONF_INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS = "IncludeParticularProblemsInLookupItemSet";
	public static final String CONF_KOHA_CURRENCY_CODE = "KohaCurrencyCode";
	public static final String CONF_HOLDINGS_ITEM_TAG = "MarcItemDescriptionField";
	public static final String CONF_ADMIN_NAME = "AdminName";
	public static final String CONF_ADMIN_PASS = "AdminPass";

	public static final String REQUEST_ID_DELIMITER = ";";

	public static final SimpleDateFormat KOHA_DATE_FORMATTER = new SimpleDateFormat("yyy-MM-dd");

	public static final String PARAM_SERVICE = "service";
	public static final String PARAM_ID = "id";

	public static final String ILS_DI_LOOKUP_USER = "GetPatronInfo";
	public static final String ILS_DI_LOOKUP_ITEM = "GetRecords";

	public static final String SVC_AUTHENTICATION = "authentication";
	public static final String SVC_BARCODE = "barcode";
	public static final String SVC_BIB = "bib";
	public static final String SVC_BIB_PROFILE = "bib_profile";
	public static final String SVC_CHECKING = "checkin";
	public static final String SVC_CHECKOUTS = "checkouts";
	public static final String SVC_HOLDS = "holds";
	public static final String SVC_RENEW = "renew";
	public static final String SVC_REPORT = "report";
	public static final String SVC_MEMBERS_SEARCH = "members/search";

	public static final String STATUS_OK = "ok";

	// HTML ATTRS

	public static final String ATTR_ITEMS = "items";

	//
	// XML PARSING SECTION
	//

	public static final String XML_AUTH_STATUS = "auth_status";
	public static final String XML_STATUS = "status";
	public static final String XML_CONTROLFIELD = "controlfield";
	public static final String XML_DATAFIELD = "datafield";
	public static final String XML_SUBFIELD = "subfield";
	public static final String XML_LEADER = "leader";

	// XML ATTRS

	public static final String XML_ATTR_TAG = "tag";
	public static final String XML_ATTR_CODE = "code";

	// Possible XML responses

	public static final String XML_VAL_STATUS_EXPIRED = "expired";
	public static final String XML_VAL_STATUS_OK = "ok";

	// controlfields
	public static final String CONTROLFIELD_ITEM_ID_TAG = "001";
	/**
	 * 008 - Fixed-Length Data Elements-General Information (NR)<br>
	 * http://www.loc.gov/marc/bibliographic/bd008.html
	 */
	public static final String CONTROLFIELD_FLDE_TAG = "008";

	// datafields
	public static final String DATAFIELD_AUTHOR_RELATED_TAG = "100";
	public static final String DATAFIELD_ISBN_RELATED_TAG = "020";
	public static final String DATAFIELD_TITLE_RELATED_TAG = "245";
	public static final String DATAFIELD_PUBLICATION_RELATED_TAG = "260";
	public static final String DATAFIELD_EDITION_TAG = "250";
	public static final String DATAFIELD_PHYSICAL_DESCRIPTION_TAG = "300";
	public static final String DATAFIELD_AUTHOR_OF_COMPONENT_RELATED_TAG = "700";
	public static final String DATAFIELD_SIGLA_PARENT_TAG = "910";
	public static final String DATAFIELD_HOLDINGS_ITEM_DEFAULT_TAG = "996";

	// subfield codes
	private static final String SUBFIELD_CODE_A = "a";
	public static final String SUBFIELD_AUTHOR_NAME_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_ISBN_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_TITLE_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_PUBLICATION_PLACE_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_EDITION_STATEMENT_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_PAGINATION_CODE = SUBFIELD_CODE_A;
	public static final String SUBFIELD_SIGLA_CODE = SUBFIELD_CODE_A;

	private static final String SUBFIELD_CODE_B = "b";
	public static final String SUBFIELD_TITLE_OF_COMPONENT_CODE = SUBFIELD_CODE_B;
	public static final String SUBFIELD_PUBLISHER_NAME_CODE = SUBFIELD_CODE_B;
	public static final String SUBFIELD_HOLDINGS_ITEM_BARCODE_CODE = SUBFIELD_CODE_B;

	private static final String SUBFIELD_CODE_C = "c";
	public static final String SUBFIELD_PUBLICATION_DATE_CODE = SUBFIELD_CODE_C;
	public static final String SUBFIELD_TITLE_OF_COMPONENT_SECOND_CODE = SUBFIELD_CODE_C;
	public static final String SUBFIELD_HOLDINGS_ITEM_STORAGE_SIGNATURE = SUBFIELD_CODE_C;

	private static final String SUBFIELD_CODE_D = "d";
	public static final String SUBFIELD_HOLDINGS_ITEM_VOLUME_DESCRIPTION_CODE = SUBFIELD_CODE_D;

	private static final String SUBFIELD_CODE_F = "f";
	public static final String SUBFIELD_HOLDINGS_ITEM_CURRENT_LOCATION_CODE = SUBFIELD_CODE_F;

	private static final String SUBFIELD_CODE_G = "g";
	public static final String SUBFIELD_HOLDINGS_ITEM_RECORD_SIGNATURE_CODE = SUBFIELD_CODE_G;

	private static final String SUBFIELD_CODE_H = "h";
	public static final String SUBFIELD_HOLDINGS_ITEM_CALL_NUMBER_CODE = SUBFIELD_CODE_H;

	private static final String SUBFIELD_CODE_I = "i";
	public static final String SUBFIELD_HOLDINGS_ITEM_NUMBER_CODE = SUBFIELD_CODE_I; // Periodics only

	private static final String SUBFIELD_CODE_K = "k";

	private static final String SUBFIELD_CODE_L = "l";
	public static final String SUBFIELD_HOLDINGS_ITEM_LOCATION_ON_AGENCY_LEVEL_CODE = SUBFIELD_CODE_L;

	private static final String SUBFIELD_CODE_R = "r";
	public static final String SUBFIELD_HOLDINGS_ITEM_EDITION_INFO_CODE = SUBFIELD_CODE_R;

	private static final String SUBFIELD_CODE_S = "s";
	public static final String SUBFIELD_HOLDINGS_ITEM_STATUS_CODE = SUBFIELD_CODE_S;

	private static final String SUBFIELD_CODE_V = "v";
	public static final String SUBFIELD_HOLDINGS_ITEM_EDITION_NUMBERS_CODE = SUBFIELD_CODE_V; // Periodics only

	private static final String SUBFIELD_CODE_W = "w";
	public static final String SUBFIELD_HOLDINGS_ITEM_PRICE_VALID_FROM_CODE = SUBFIELD_CODE_W;

	private static final String SUBFIELD_CODE_Y = "y";
	public static final String SUBFIELD_HOLDINGS_ITEM_YEARS_CODE = SUBFIELD_CODE_Y; // Periodics only

	private static final String SUBFIELD_CODE_Z = "z";
	public static final String SUBFIELD_HOLDINGS_ITEM_LOCATION_ON_BUILDING_LEVEL_CODE = SUBFIELD_CODE_Z;

	private static final String SUBFIELD_CODE_1 = "1";
	public static final String SUBFIELD_HOLDINGS_ITEM_BARCODE_SECOND_CODE = SUBFIELD_CODE_1;

	private static final String SUBFIELD_CODE_4 = "4";
	public static final String SUBFIELD_HOLDINGS_ITEM_PHYSICAL_CONDITION_CODE = SUBFIELD_CODE_4;

	private static final String SUBFIELD_CODE_5 = "5";
	public static final String SUBFIELD_HOLDINGS_ITEM_RESTRICTION_CODE = SUBFIELD_CODE_5;

	// Delimiters
	public static final String DELIMITER_HOLDINGS_ITEM_EDITION_NUMBERS = COMMA_DELIMITER;
	public static final String DELIMITER_HOLDINGS_ITEM_YEARS = COMMA_DELIMITER;

	public static final String CIRC_STATUS_PRESENT_ONLY = "P";
	public static final String CIRC_STATUS_ON_SHELF = "A";
	public static final String CIRC_STATUS_LOST = "Z";
	public static final String CIRC_STATUS_PROCESSING = "ZP";
	public static final String CIRC_STATUS_NOT_AVAILABLE = "V";
	public static final String CIRC_STATUS_DAMAGED = "PK";

}
