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

	public static final String KOHA_TEMPLATE_LOOKUP_USER = "members/tables/ncip_lookup_user.tt";

	// Constants for parsing from toolkit.properties
	public static final String CONF_INTRANET_SERVER = "IntranetServer";
	public static final String CONF_INTRANET_PORT = "IntranetPort";
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
	public static final String CONF_TRANSFER_BRANCH_TIME = "TransferBranchTime";
	public static final String CONF_CURRENCY_CODE = "CurrencyCode";
	public static final String CONF_TRUST_ALL_CERTIFICATES = "TrustAllCertificates";
	public static final String CONF_USE_REST_API_INSTEAD_OF_SVC = "UseRestApiInsteadOfSvc";

	public static final String CONF_STRING_FORMAT_FOR_EXPIRED = "expired";
	public static final String CONF_STRING_FORMAT_FOR_TOTALFINES = "totalfines";
	public static final String CONF_STRING_FORMAT_FOR_DEBARRED = "debarred";

	public static final String REQUEST_ID_DELIMITER = ";";

	public static final SimpleDateFormat KOHA_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat KOHA_DATE_LONG_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat KOHA_DATE_FORMATTER_BACKSLASHES = new SimpleDateFormat("dd/MM/yyyy");

	public static final String ILS_DI_LOOKUP_USER = "GetPatronInfo";
	public static final String ILS_DI_LOOKUP_ITEM = "GetRecords";

	public static final String SVC_NCIP = "ncip";

	public static final String STATUS_OK = "ok";

	// HTML PARAMS
	public static final String PARAM_USER_ID = "userId";
	public static final String PARAM_ITEM_ID = "itemId";
	public static final String PARAM_BIB_ID = "bibId";
	public static final String PARAM_PW = "pw";
	public static final String PARAM_REQUEST_ID = "requestId";
	public static final String PARAM_REQUEST_TYPE = "requestType";
	public static final String PARAM_PICKUP_EXPIRY_DATE = "pickupExpiryDate";
	public static final String PARAM_EARLIEST_DATE_NEEDED = "earliestDateNeeded";
	public static final String PARAM_PICKUP_LOCATION = "pickupLocation";
	public static final String PARAM_NOTES = "notes";
	public static final String PARAM_BARCODE = "barcode";
	public static final String PARAM_DESIRED_DATE_DUE = "desiredDateDue";

	public static final String PARAM_SERVICE = "service";

	public static final String PARAM_BOR_NO_DESIRED = "borNoDesired";
	
	public static final String PARAM_LOANED_ITEMS_DESIRED = "loanedItemsDesired";
	public static final String PARAM_LOANED_ITEMS_HISTORY_DESIRED = "loanedItemsHistoryDesired";
	public static final String PARAM_REQUESTED_ITEMS_DESIRED = "requestedItemsDesired";
	public static final String PARAM_USER_FISCAL_ACCOUNT_DESIRED = "userFiscalAccountDesired";
	public static final String PARAM_BLOCK_OR_TRAP_DESIRED = "blockOrTrapDesired";
	
	public static final String PARAM_CIRCULATION_STATUS_DESIRED = "circulationStatusDesired";
	public static final String PARAM_ITEM_USE_RESTRICTION_TYPE_DESIRED = "itemUseRestrictionTypeDesired";
	public static final String PARAM_HOLD_QUEUE_LENGTH_DESIRED = "holdQueueLengthDesired";
	public static final String PARAM_MAX_DATE_DUE_DESIRED = "maxDateDueDesired";
	
	public static final String PARAM_RENEWABILITY_DESIRED = "renewabilityDesired";
	public static final String PARAM_CAN_BE_REQUESTED_BY_USERID = "canBeRequestedByUserId";

	public static final String PARAM_NOT_USER_INFO = "notUserInfo";
	public static final String PARAM_NOT_ITEM_INFO = "notItemInfo";
	public static final String PARAM_NOT_BIB_INFO = "notBibInfo";

	//
	// XML PARSING SECTION
	//

	public static final String XML_STATUS = "status";

	// Possible XML responses

	public static final String XML_VAL_STATUS_EXPIRED = "expired";
	public static final String XML_VAL_STATUS_OK = "ok";

	public static final String SERVICE_LOOKUP_ITEM = "lookup_item";
	public static final String SERVICE_LOOKUP_ITEM_SET = "lookup_item_set";
	public static final String SERVICE_LOOKUP_USER = "lookup_user";
	public static final String SERVICE_LOOKUP_REQUEST = "lookup_request";
	public static final String SERVICE_REQUEST_ITEM = "request_item";
	public static final String SERVICE_RENEW_ITEM = "renew_item";
	public static final String SERVICE_CANCEL_REQUEST_ITEM = "cancel_request_item";

	public static final String APP_PROFILE_TYPE_CAN_BE_REQUESTED = "Can Be Requested";
	public static final String APP_PROFILE_TYPE_CAN_BE_RENEWED = "Can Be Renewed";

	public static final String REGISTRATION_LINK_CONS_AGR_TYPE = "http://www.knihovny.cz/registration_link";
}
