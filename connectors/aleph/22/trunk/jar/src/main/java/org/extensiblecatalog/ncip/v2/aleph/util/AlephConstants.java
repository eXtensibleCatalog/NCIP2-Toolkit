/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/. 
 */

package org.extensiblecatalog.ncip.v2.aleph.util;

import java.text.SimpleDateFormat;

/**
 * This class defines several constants as public static final variables which may be used throughout the NCIP Toolkit
 */
public class AlephConstants {
	// Aleph configuration

	/**
	 * X-Server address for making Aleph X-Service calls
	 */
	public static final String CONFIG_ALEPH_X_SERVER_NAME = "AlephXServerName";

	/**
	 * X-Server port for making Aleph X-Service calls
	 */
	public static final String CONFIG_ALEPH_X_SERVER_PORT = "AlephXServerPort";

	/**
	 * X-Server address for making Aleph RESTful APIs calls
	 */
	public static final String CONFIG_ALEPH_API_NAME = "AlephAPIName";

	/**
	 * X-Server port for making Aleph RESTful APIs calls
	 */
	public static final String CONFIG_ALEPH_API_PORT = "AlephAPIPort";

	/**
	 * Currency code used for fines for aleph
	 */
	public static final String CONFIG_ALEPH_CURRENCY_CODE = "AlephCurrencyCode";

	/**
	 * The number of agencies defined in the NCIPToolkit_config.xml file
	 */
	public static final String CONFIG_ALEPH_AGENCY_COUNT = "AlephAgencyCount";

	/**
	 * Define Agency, ADMLibrary, BIBLibrary, and Hold Library for all agencies, This is actually the base of indexed properties in the config file
	 */
	public static final String CONFIG_ALEPH_AGENCY = "AlephILSAgency";
	public static final String CONFIG_ALEPH_ADM_LIBRARY = "AlephAdmLibrary";
	public static final String CONFIG_ALEPH_BIB_LIBRARY = "AlephBibLibrary";
	public static final String CONFIG_ALEPH_HOLD_LIBRARY = "AlephHoldLibrary";

	public static final String CONFIG_ALEPH_CIRC_STATUS_AVAILABLE = "AlephCircStatusAvailable";
	public static final String CONFIG_ALEPH_CIRC_STATUS_POSSIBLY_AVAILABLE = "AlephCircStatusPossiblyAvailable";
	public static final String CONFIG_ALEPH_CIRC_STATUS_NOT_AVAILABLE = "AlephCircStatusNotAvailable";

	/**
	 * Location Type Enum, Defined in the Schema
	 */
	public static final String LOCATION_TYPE_PERMANENT = "Permanent";

	/**
	 * Location Type Enum, Defined in the Schema
	 */
	public static final String LOCATION_TYPE_TEMPORARY = "Temporary";

	/**
	 * Location Type Enum, Defined in the Schema not yet supported in database, item table only has perm and temp
	 */
	public static final String LOCATION_TYPE_CURRENT = "Current";

	public static final String DEFAULT_SCHEME = "http://www.niso.org/schemas/ncip/v2_02/ncip_v2_02.xsd";

	// Constants for Rest-Dlf & X-Services

	public static final boolean FIRST_SURNAME = true; // Sets whether aleph returns first surname at z304-address-2

	public static final SimpleDateFormat ALEPH_DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat ALEPH_HOUR_FORMATTER = new SimpleDateFormat("HHmm");

	// Both Aleph (RESTful APIs & X-Services) nodes:
	public static final String Z304_NODE = "z304";
	public static final String Z304_ZIP_NODE = "z304-zip";
	public static final String Z304_DATE_TO_NODE = "z304-date-to";
	public static final String Z304_EMAIL_ADDRESS_NODE = "z304-email-address";
	public static final String Z304_DATE_FROM_NODE = "z304-date-from";
	public static final String Z304_ADDRESS_1_NODE = "z304-address-1";
	public static final String Z304_ADDRESS_2_NODE = "z304-address-2";
	public static final String Z304_ADDRESS_3_NODE = "z304-address-3";
	public static final String Z304_ADDRESS_4_NODE = "z304-address-4";
	public static final String Z304_ADDRESS_5_NODE = "z304-address-5";
	public static final String Z304_SMS_NUMBER_NODE = "z304-sms-number";
	public static final String Z304_TELEPHONE_1_NODE = "z304-telephone-1";
	public static final String Z304_TELEPHONE_2_NODE = "z304-telephone-2";
	public static final String Z304_TELEPHONE_3_NODE = "z304-telephone-3";
	public static final String Z304_TELEPHONE_4_NODE = "z304-telephone-4";

	// XML Aleph RESTful APIs Nodes:
	public static final String NOTE_NODE = "note";
	public static final String ERROR_NODE = "error";
	public static final String REPLY_NODE = "reply";
	public static final String TOTAL_NODE = "total";
	public static final String STATUS_NODE = "status";
	public static final String FINE_ITEM_NODE = "fine";
	public static final String LOAN_ITEM_NODE = "loan";
	public static final String BALANCE_NODE = "balance";
	public static final String LOCATION_NODE = "location";
	public static final String DUE_DATE_NODE = "due-date";
	public static final String DUE_HOUR_NODE = "due-hour";
	public static final String OPEN_SUM_NODE = "open-sum";
	public static final String REPLY_CODE_NODE = "reply-code";
	public static final String REPLY_TEXT_NODE = "reply-text";
	public static final String PAT_BLOCKS_NODE = "pat-blocks";
	public static final String CIRC_STATUS_NODE = "circ-status";
	public static final String SUB_LIBRARY_NODE = "sub-library";
	public static final String HOLD_REQUEST_NODE = "hold-request";
	public static final String NEW_DUE_DATE_NODE = "new-due-date";
	public static final String PATRON_BLOCK_NODE = "patron-block";
	public static final String GET_PAT_ADRS_NODE = "get-pat-adrs";
	public static final String PAT_REGISTER_NODE = "pat-register";
	public static final String GET_CIRC_ACTS_NODE = "get-circ-acts";
	public static final String PAT_CASH_LIST_NODE = "pat-cash-list";
	public static final String HOLD_REQUESTS_NODE = "hold-requests";
	public static final String CONSORTIAL_BLOCK_NODE = "consortial-block";
	public static final String ADDRESS_INFORMATION_NODE = "address-information";
	public static final String TRANSLATE_CHANGE_ACTIVE_LIBRARY_NODE = "translate-change-active-library";

	public static final String Z13_NODE = "z13";
	public static final String Z13_TITLE_NODE = "z13-title";
	public static final String Z13_AUTHOR_NODE = "z13-author";
	public static final String Z13_ISBN_NODE = "z13-isbn-issn";
	public static final String Z13_BIB_ID_NODE = "z13-doc-number";
	public static final String Z13_PUBLISHER_NODE = "z13-imprint";
	public static final String Z13_DOC_NUMBER_NODE = "z13-doc-number";

	public static final String Z30_NODE = "z30";
	public static final String Z30_NODE_ID = "Z30";
	public static final String Z30_BARCODE = "z30-barcode";
	public static final String Z30_COPY_ID_NODE = "z30-copy-id";
	public static final String Z30_MATERIAL_NODE = "z30-material";
	public static final String Z30_OPEN_DATE_NODE = "z30-open-date";
	public static final String Z30_CALL_NUMBER_NODE = "z30-call-no";
	public static final String Z30_COLLECTION_NODE = "z30-collection";
	public static final String Z30_DOC_NUMBER_NODE = "z30-doc-number";
	public static final String Z30_SUB_LIBRARY_NODE = "z30-sub-library";
	public static final String Z30_ITEM_STATUS_NODE = "z30-item-status";
	public static final String Z30_DESCRIPTION_NODE = "z30-description";
	public static final String Z30_CALL_NUMBER_2_NODE = "z30-call-no-2";
	public static final String Z30_ITEM_SEQUENCE_NODE = "z30-item-sequence";
	public static final String Z30_HOLD_DOC_NUMBER_NODE = "z30-hol-doc-number";
	public static final String Z30_CALL_NUMBER_2_TYPE_NODE = "z30-call-no-2-type";

	public static final String Z31_NODE = "z31";
	public static final String Z31_FINE_SUM_NODE = "z31-sum";
	public static final String Z31_DATE_NODE = "z31-date";
	public static final String Z31_NET_SUM_NODE = "z31-net-sum";
	public static final String Z31_FINE_STATUS_NODE = "z31-status";
	public static final String Z31_FINE_ACCRUAL_DATE_NODE = "z31-date";
	public static final String Z31_DESCRIPTION_NODE = "z31-description";
	public static final String Z31_CREDIT_DEBIT_NODE = "z31-credit-debit";

	public static final String Z37_NODE = "z37";
	public static final String Z37_STATUS_NODE = "z37-status";
	public static final String Z37_PRIORITY_NODE = "z37-priority";
	public static final String Z37_HOLD_DATE_NODE = "z37-hold-date";
	public static final String Z37_OPEN_DATE_NODE = "z37-open-date";
	public static final String Z37_OPEN_HOUR_NODE = "z37-open-hour";
	public static final String Z37_DOC_NUMBER_NODE = "z37-doc-number";
	public static final String Z37_RECALL_TYPE_NODE = "z37-recall-type";
	public static final String Z37_REQUEST_DATE_NODE = "z37-request-date";
	public static final String Z37_END_HOLD_DATE_NODE = "z37-end-hold-date";
	public static final String Z37_ITEM_SEQUENCE_NODE = "z37-item-sequence";
	public static final String Z37_HOLD_SEQUENCE_NODE = "z37-hold-sequence";
	public static final String Z37_REQUEST_NUMBER_NODE = "z37-request-number";
	public static final String Z37_PICKUP_LOCATION_NODE = "z37-pickup-location";
	public static final String Z37_END_REQUEST_DATE_NODE = "z37-end-request-date";

	public static final String Z36_NODE = "z36";
	public static final String Z36_ID_NODE = "z36-id";
	public static final String Z36_STATUS_NODE = "z36-status";
	public static final String Z36_NUMBER_NODE = "z36-number";
	public static final String Z36_MATERIAL_NODE = "z36-material";
	public static final String Z36_DUE_DATE_NODE = "z36-due-date";
	public static final String Z36_LOAN_DATE_NODE = "z36-loan-date";
	public static final String Z36_DOC_NUMBER_NODE = "z36-doc-number";
	public static final String Z36_SUB_LIBRARY_NODE = "z36-sub-library";
	public static final String Z36_RECALL_DATE_NODE = "z36-recall-date";
	public static final String Z36_ITEM_SEQUENCE_NODE = "z36-item-sequence";
	public static final String Z36_RECALL_DUE_DATE_NODE = "z36-recall-due-date";

	public static final String Z36H_NUMBER_NODE = "z36h-number";
	public static final String Z36H_DUE_DATE_NODE = "z36h-due-date";
	public static final String Z36H_LOAN_DATE_NODE = "z36h-loan-date";
	public static final String Z36H_ITEM_SEQUENCE_NODE = "z36h-item-sequence";

	// XML Aleph X-Services nodes:
	public static final String SESSION_ID_NODE = "session-id";
	public static final String ITEM_DATA_NODE = "item-data";
	public static final String READ_ITEM_NODE = "read-item";
	public static final String ITEM_NODE = "item";
	public static final String GET_ITEM_LIST_NODE = "get-item-list";
	public static final String ITEMS_NODE = "items";
	public static final String SUBFIELD_NODE = "subfield";
	public static final String LOAN_STATUS_NODE = "loan-status";
	public static final String ITEM_STATUS_NODE = "item-status";
	public static final String REC_KEY_NODE = "rec-key";
	public static final String BARCODE_NODE = "barcode";
	public static final String HOLD_ITEM_NODE = "item-h";
	public static final String HOLD_REQ_ITEM_NODE = "hold-req";
	public static final String LIST_RECORDS_NODE = "ListRecords";
	public static final String HEADER_NODE = "header";
	public static final String METADATA_NODE = "metadata";
	public static final String IDENTIFIER_NODE = "identifier";
	public static final String DATAFIELD_NODE = "datafield";

	public static final String Z303_ID_NODE = "z303-id";
	public static final String Z303_NAME_NODE = "z303-name";
	public static final String Z303_WANT_SMS_NODE = "z303-want-sms";
	public static final String Z303_FIELD_NOTE_1_NODE = "z303-field-1";
	public static final String Z303_FIELD_NOTE_2_NODE = "z303-field-2";
	public static final String Z303_FIELD_NOTE_3_NODE = "z303-field-3";
	public static final String Z303_PLAIN_HTML_NODE = "z303-plain-html";
	public static final String Z303_DELINQUENCY_1_NODE = "z303-delinq-1";
	public static final String Z303_DELINQUENCY_2_NODE = "z303-delinq-2";
	public static final String Z303_DELINQUENCY_3_NODE = "z303-delinq-3";
	public static final String Z303_ADDITIONAL_NOTE_1_NODE = "z303-note-1";
	public static final String Z303_ADDITIONAL_NOTE_2_NODE = "z303-note-2";
	public static final String Z303_DELINQUENCY_NOTE_1_NODE = "z303-delinq-n-1";
	public static final String Z303_DELINQUENCY_NOTE_2_NODE = "z303-delinq-n-2";
	public static final String Z303_DELINQUENCY_NOTE_3_NODE = "z303-delinq-n-3";
	public static final String Z303_DELINQUENCY_1_UPDATE_DATE_NODE = "z303-delinq-1-update-date";
	public static final String Z303_DELINQUENCY_2_UPDATE_DATE_NODE = "z303-delinq-2-update-date";
	public static final String Z303_DELINQUENCY_3_UPDATE_DATE_NODE = "z303-delinq-3-update-date";

	public static final String Z305_NODE = "z305";
	public static final String Z305_BOR_TYPE_NODE = "z305-bor-type";
	public static final String Z305_FIELD_NOTE_1_NODE = "z305-field-1";
	public static final String Z305_FIELD_NOTE_2_NODE = "z305-field-2";
	public static final String Z305_FIELD_NOTE_3_NODE = "z305-field-3";
	public static final String Z305_BOR_STATUS_NODE = "z305-bor-status";
	public static final String Z305_DELINQUENCY_1_NODE = "z305-delinq-1";
	public static final String Z305_DELINQUENCY_2_NODE = "z305-delinq-2";
	public static final String Z305_DELINQUENCY_3_NODE = "z305-delinq-3";
	public static final String Z305_EXPIRY_DATE_NODE = "z305-expiry-date";
	public static final String Z305_DELINQUENCY_NOTE_1_NODE = "z305-delinq-n-1";
	public static final String Z305_DELINQUENCY_NOTE_2_NODE = "z305-delinq-n-2";
	public static final String Z305_DELINQUENCY_NOTE_3_NODE = "z305-delinq-n-3";
	public static final String Z305_LOAN_PERMISSION_NODE = "z305-loan-permission";
	public static final String Z305_HOLD_PERMISSION_NODE = "z305-hold-permission";
	public static final String Z305_BOR_STATUS_CODE_NODE = "z305-bor-status-code";
	public static final String Z305_RENEW_PERMISSION_NODE = "z305-renew-permission";
	public static final String Z305_DELINQUENCY_1_UPDATE_DATE_NODE = "z305-delinq-1-update-date";
	public static final String Z305_DELINQUENCY_2_UPDATE_DATE_NODE = "z305-delinq-2-update-date";
	public static final String Z305_DELINQUENCY_3_UPDATE_DATE_NODE = "z305-delinq-3-update-date";

	// XML Attributes:
	public static final String ID_NODE_ATTRIBUTE = "id";
	public static final String TAG_NODE_ATTRIBUTE = "tag";
	public static final String HREF_NODE_ATTRIBUTE = "href";
	public static final String TYPE_NODE_ATTRIBUTE = "type";
	public static final String CODE_NODE_ATTRIBUTE = "code";
	public static final String LABEL_NODE_ATTRIBUTE = "label";
	public static final String RENEW_NODE_ATTRIBUTE = "renew";
	public static final String DELETE_NODE_ATTRIBUTE = "delete";
	public static final String MAX_LEN_NODE_ATTRIBUTE = "max_len";
	public static final String UPDATEABLE_NODE_ATTRIBUTE = "updateable";

	// XML Attribute values:
	public static final String BIB_ID_NODE_ATTR_VALUE = "b";
	public static final String MEDIUM_LABEL_ATTR_VALUE = "m";
	public static final String AVAILABLE_CODE_ATTR_VALUE = "e";
	public static final String LOCATION_LABEL_ATTR_VALUE = "A";
	public static final String AVAILABLE_TAG_ATTR_VALUE = "AVA";
	public static final String CALL_NUMBER_LABEL1_ATTR_VALUE = "h";
	public static final String CALL_NUMBER_LABEL2_ATTR_VALUE = "i";

	// XML Node IDs:
	public static final String ISBN_NODE_ID = "020";
	public static final String TITLE_NODE_ID = "245";
	public static final String AUTHOR_NODE_ID = "100";
	public static final String SERIES_NODE_ID = "490";
	public static final String BIB_ID_NODE_ID = "LKR";
	public static final String PUBLISHER_NODE_ID = "260";
	public static final String CALL_NUMBER_NODE_ID = "852";
	public static final String DESCRIPTION1_NODE_ID = "300";
	public static final String DESCRIPTION2_NODE_ID = "500";
	public static final String ELECTRONIC_RESOURCE_NODE_ID = "856";

	// get alephitem data for a fine
	public static final String FINE_DEBIT = "D";
	public static final String FINE_CREDIT = "C";
	public static final String FINE_STATUS_PAID = "Paid";
	public static final String FINE_STATUS_UNPAID = "Unpaid";

	// value constants
	public static final String STATUS_OK = "ok";
	public static final String BLOCK_NONE_CODE = "00";
	public static final String SUCCESS_REPLY_CODE = "0000";
	public static final String AVAILABLE_STATUS = "available";
	public static final String UNAVAILABLE_STATUS = "unavailable";

	public static final String NO = "N";
	public static final String YES = "Y";

	// Values lengths:
	public static final int BIB_ID_LENGTH = 9;
	public static final int SEQ_NUMBER_LENGTH = 4;
	public static final int REQUEST_ID_LENGTH = 9;
	public static final int DOC_NUMBER_LENGTH = 9;
	public static final int POSTAL_CODE_LENGTH = 6;
	public static final int HOLDINGS_ID_LENGTH = 9;
	public static final int LIBRARY_PARAM_LENGTH = 5;
	public static final int ITEM_SEQ_NUMBER_LENGTH = 6;
	public static final int ITEM_ID_UNIQUE_PART_LENGTH = 6;

	// Date formats:
	public static final String HOLD_DATE_FORMAT = "MM/dd/yyyy";
	public static final String STATUS_DATE_FORMAT = "dd/MMM/yyyy";
	public static final String RENEW_DUE_DATE_FORMAT = "yyyyMMdd";
	public static final String LOAN_DUE_DATE_FORMAT = "MM/dd/yyyy";
	public static final String FINE_ACCRUAL_DATE_FORMAT = "MM/dd/yyyy";
	public static final String CIRC_STATUS_DUE_DATE_FORMAT = "MM/dd/yy";

	// Aleph XServices http params & GET request params:
	public static final String ALEPHAPI_BOR_AUTH = "bor-auth";
	public static final String ALEPHAPI_BOR_INFO = "bor-info";
	public static final String ALEPHAPI_CIRC_STATUS = "circ-status";
	public static final String ALEPHAPI_ITEM_DATA = "item-data";
	public static final String ALEPHAPI_READ_ITEM = "read-item";
	public static final String ALEPHAPI_GET_ITEM_LIST = "get-item-list";
	public static final String ALEPHAPI_GET_HOLDING = "get-holding";
	public static final String ALEPHAPI_HOLD_REQUEST = "hold-req";
	public static final String ALEPHAPI_CANCEL_HOLD_REQUEST = "hold-req-cancel";
	public static final String ALEPHAPI_RENEW = "renew";
	public static final String ALEPHAPI_PUBLISH_AVAILABILITY = "publish_avail";
	public static final String PARAM_ALEPHAPI_NAME = "op";
	public static final String PARAM_LIBRARY = "library";
	public static final String PARAM_SUB_LIBRARY = "sub_library";
	public static final String PARAM_PATRON_ID = "bor_id";
	public static final String PARAM_PASSWORD = "verification";
	public static final String PARAM_LOANS = "loans";
	public static final String PARAM_CASH = "cash";
	public static final String PARAM_FILTER_CASH = "filter_cash";
	public static final String PARAM_HOLD = "hold";
	public static final String PARAM_HOLDS = "holds";
	public static final String PARAM_BLOCKS = "blocks";
	public static final String PARAM_SYSTEM_NUMBER = "sys_no";
	public static final String PARAM_BASE = "base";
	public static final String PARAM_DOC_NUMBER = "doc_number";
	public static final String PARAM_REC_KEY = "rec_key";
	public static final String PARAM_BARCODE = "item_barcode";
	public static final String PARAM_SEQ_NUMBER = "item_sequence";
	public static final String PARAM_CANCEL_SEQUENCE = "sequence";
	public static final String PARAM_DUE_DATE = "due_date";
	public static final String PARAM_DOC_NUM = "doc_num";
	public static final String PARAM_ITEMS = "items";
	public static final String PARAM_PATRON_INFO = "patronInformation";
	public static final String PARAM_ADDRESS = "address";
	public static final String PARAM_CIRC_ACTIONS = "circulationActions";
	public static final String PARAM_BOOKINGS = "bookings";
	public static final String PARAM_REQUESTS = "requests";
	public static final String PARAM_PATRON_STATUS = "patronStatus";
	public static final String PARAM_REGISTRATION = "registration";
	public static final String PARAM_RECORD = "record";

	// lookup user param values
	public static final String USER_CASH_BALANCE_ONLY = "B";
	public static final String USER_CASH_ALL_DATA = "Y";
	public static final String USER_CASH_NO_DATA = "N";
	public static final String USER_CASH_OPEN_TRANSACTIONS_ONLY = "O";
	public static final String USER_CASH_FILTER = "F";
	public static final String USER_LOANS_ALL_DATA = "Y";
	public static final String USER_LOANS_NO_DATA = "N";
	public static final String USER_LOANS_PARTIAL_DATA = "P";
	public static final String USER_HOLDS_ALL_DATA = "Y";
	public static final String USER_HOLDS_NO_DATA = "N";
	public static final String USER_HOLDS_PARTIAL_DATA = "P";
	public static final String QUEUE_NODE = "queue";

	// RESTful APIs http params:
	public static final String ITEM_PATH_ELEMENT = "record";
	public static final String USER_PATH_ELEMENT = "patron";

	// Toolkit.properties key values
	public static final String REST_DLF_PORT = "RestDlfPort";
	public static final String REST_DLF_SERVER = "RestDlfServer";
	public static final String REST_DLF_SUFFIX = "RestDlfSuffix";
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

	// Toolkit's Mapping Patron -> Address
	public static final String PATRON_ADDRESS_MAPPING_STREET = "UserStreetStoredIn";
	public static final String PATRON_ADDRESS_MAPPING_POSTAL_AND_CITY = "UserPostalCodeAndCityStoredIn";
	public static final String PATRON_ADDRESS_MAPPING_PHONE = "UserPhoneStoredIn";
	public static final String PATRON_ADDRESS_MAPPING_IDENTITY_CARD = "UserIdCardStoredIn";

	public static final String PATRON_ADDRESS_STREET_PARAM = "streetAddress";
	public static final String PATRON_ADDRESS_POSTAL_CODE_PARAM = "postalCode";
	public static final String PATRON_ADDRESS_PHONE_PARAM = "phone";
	public static final String PATRON_ADDRESS_ID_CARD_PARAM = "identityCard";

	public static final String PATRON_ADDRESS_DELIMITER = ", ";

	public static final int PATRON_ADDRESS_MAX_POSTAL_CODE_LENGTH = 6;

	public static final char REQUEST_ID_DELIMITER = ';';

	public static final String UNIQUE_ITEM_ID_SEPARATOR = "-";
	public static final String SEQUENCE_NUMBER_SEPARATOR = "\\."; // Be aware of this regex (dot is reserved character)
	public static final String UNSTRUCTURED_NAME_SEPARATOR = ", ";
	public static final String UNSTRUCTURED_ADDRESS_SEPARATOR = ", ";

	// Item statuses of Aleph ILS here:
	// Location/place status:
	public static final String ITEM_STATUS_STUDY_ROOM = "Study room";
	public static final String ITEM_STATUS_IN_HOUSE_LOAN = "In house loan";
	public static final String ITEM_STATUS_REFERENCE_ONLY = "Reference only";
	public static final String ITEM_STATUS_REFERENCE_SHELF = "Reference shelf";
	public static final String ITEM_STATUS_REFERENCE_ONLY_ST_4F = "Reference only (ST 4F)";
	public static final String ITEM_STATUS_REFERENCE_ONLY_SPN_2F = "Reference only (SPN 2F)";
	public static final String ITEM_STATUS_TO_THE_MUSIC_CORNER_ONLY_4F = "To The Music Corner only (4F)";

	// Time statuses:
	public static final String ITEM_STATUS_WEEK = "Week";
	public static final String ITEM_STATUS_MONTH = "Month";
	public static final String ITEM_STATUS_7_DAYS = "7 days";
	public static final String ITEM_STATUS_2_HOURS = "2 hours";
	public static final String ITEM_STATUS_14_DAYS = "14 days";
	public static final String ITEM_STATUS_LONG_TERM_LOAN = "Long-term loan";
	public static final String ITEM_STATUS_OPEN_STOCK_MONTH = "Open Stock Month";

	// ILL statuses:
	public static final String ITEM_STATUS_IN_HOUSE_ILL = "In-house ILL";
	public static final String ITEM_STATUS_CHECK_OUT_ILL = "Check out ILL";

	// Other statuses:
	public static final String ITEM_STATUS_RETRO = "Retro";
	public static final String ITEM_STATUS_BINDING_1 = "Binding 1";
	public static final String ITEM_STATUS_BINDING_2 = "Binding 2";
	public static final String ITEM_STATUS_DIGITIZING = "Digitizing";
	public static final String ITEM_STATUS_NOT_FOUND_1 = "Not found 1";
	public static final String ITEM_STATUS_NOT_FOUND_2 = "Not found 2";
	public static final String ITEM_STATUS_NOT_FOUND_3 = "Not found 3";
	public static final String ITEM_STATUS_INVESTIGATED = "Investigated";
	public static final String ITEM_STATUS_CURRENT_YEAR = "Current Year";
	public static final String ITEM_STATUS_NOT_PUBLISHED = "Not published";
	public static final String ITEM_STATUS_CURRENT_YEAR_2 = "Current Year 2";
	public static final String ITEM_STATUS_ITEM_UNDER_CLAIN = "Item under claim";

	// Aleph circulation statuses:
	public static final String CIRC_STATUS_ON_SHELF = "On Shelf";
	public static final String CIRC_STATUS_REQUESTED = "Requested";
	public static final String CIRC_STATUS_PROCESSING = "Processing";

	// Item restrictions:
	public static final String ITEM_RESTRICTION_LOAN_PERIOD = "Loan Period";
	public static final String ITEM_RESTRICTION_IN_LIBRARY_USE_ONLY = "Library only";

	// Error messages for X-Services failures:
	public static final String ERROR_UNKNOWN_AGENCY = "Unknown agency";
	public static final String ERROR_BOR_ID_MISSING = "bor_id must be provided";
	public static final String ERROR_AUTH_VERIFICATION = "Error in Verification";
	public static final String ERROR_ITEM_NOT_CHECKED_OUT = "Item is not checked out.";
	public static final String ERROR_BIB_LIBRARY_NOT_SET = "Bib library not set for agency";
	public static final String ERROR_ADM_LIBRARY_NOT_SET = "Adm library not set for agency";
	public static final String ERROR_HOLD_LIBRARY_NOT_SET = "Hold library not set for agency";
	public static final String ERROR_PATRON_SYSTEM_KEY = "Error retrieving Patron System Key";
	public static final String ERROR_RETRIEVE_PATRON_RECORD = "Error retrieving Patron Record";
	public static final String ERROR_ITEM_MISSING = "No item exist with the specified criteria";
	public static final String ERROR_DOC_NUM_MISSING = "doc_num must be included in parameters";
	public static final String ERROR_BOR_ID_VER = "Both Bor_Id and Verification must be supplied";
	public static final String ERROR_RECORD_MISSING = "No item exists with the specified criteria";
	public static final String ERROR_LOCAL_PATRON_RECORD_MISSING = "Error retrieving Local Patron Record";
	public static final String ERROR_GLOBAL_PATRON_RECORD_MISSING = "Error retrieving Global Patron Record";
	public static final String ERROR_ADDRESS_INFORMATION_MISSING = "Error retrieving Patron Address record";
	public static final String ERROR_GET_ITEM_DATA_FAILED_UNKNOWN = "Get item data failed for unknown reason";
	public static final String ERROR_AUTHENTICATION_FAILED_UNKNOWN = "Authentication failed for unknown reason";
	public static final String ERROR_FIND_DOC_MISSING = "Find document or get item data failed for unknown reason";
	public static final String ERROR_FAILED_TO_FIND_REQUEST_FOR_CANCEL = "Failed to find the request to cancel it";
	public static final String ERROR_FIND_DOC_FAILED_SESSION_ID_MISSING = "Find document failed, no session id returned";
	public static final String ERROR_GET_ITEM_DATA_FAILED_SESSION_ID_MISSING = "Get item data failed, no session id returned";
	public static final String ERROR_AUTHENTICATION_FAILED_SESSION_ID_MISSING = "Authentication failed, no session id returned";
	public static final String ERROR_AVAILABILITY_LIST_TOO_LONG = "Too many ids requested for availability.  Maximum is 10.";
	public static final String ERROR_ITEM_DATA_MISSING = ERROR_GET_ITEM_DATA_FAILED_UNKNOWN;

	// Error messages for RESTful APIs failures:
	public static final String ERROR_ISBN_NOT_FOUND = "ISBN is not set to this item.";
	public static final String ERROR_TITLE_NOT_FOUND = "Title is not set to this item.";
	public static final String ERROR_AUTHOR_NOT_FOUND = "Author is not set to this item.";
	public static final String ERROR_AGENCY_NOT_FOUND = "Agency is not set to this item.";
	public static final String ERROR_BARCODE_NOT_FOUND = "Barcode is not set to this item";
	public static final String ERROR_ITEM_ID_NOT_FOUND = "Item id is not set to this item.";
	public static final String ERROR_LOCATION_NOT_FOUND = "Location is not set to this item";
	public static final String ERROR_CALL_NO_NOT_FOUND = "Call Number is not set to this item";
	public static final String ERROR_COPY_NO_NOT_FOUND = "Copy Number is not set to this item";
	public static final String ERROR_OPENDATE_NOT_FOUND = "Open date is not set to this item.";
	public static final String ERROR_PUBLISHER_NOT_FOUND = "Publisher is not set to this item.";
	public static final String ERROR_COLLECTION_NOT_FOUND = "Collection is not set to this item";
	public static final String ERROR_HOLD_QUEUE_NOT_FOUND = "Hold queue is not set to this item.";
	public static final String ERROR_MATERIAL_NOT_FOUND = "Material (medium type) is not set to this item";
	public static final String ERROR_DOCUMENT_NUMBER_NOT_FOUND = "Document number is not set to this item";
	public static final String ERROR_SEQUENCE_NUMBER_NOT_FOUND = "Sequence number is not set to this item";
	public static final String ERROR_ITEM_DESCRIPTION_NOT_FOUND = "Item description is not set to this item.";
	public static final String ERROR_BIBLIOGRAPHIC_ID_NOT_FOUND = "Biblioghraphic id is not set to this item.";
	public static final String ERROR_CIRCULATION_STATUS_NOT_FOUND = "Circulation status is not set to this item.";

	public static enum MessageTypes {
		LOOKUP, UPDATE, NOTIFICATION
	}

	public static enum Availability {
		UNKNOWN, NOT_AVAILABLE, POSSIBLY_AVAILABLE, AVAILABLE, DOESNT_EXIST, DOESNT_EXIST_REMOVED_MANUALLY
	}

	// x-services
	public static final String XSERVICE_BOR_AUTH = "bor-auth";
	public static final String XSERVICE_BOR_INFO = "bor-info";
	public static final String XSERVICE_CIRC_STATUS = "circ-status";
	public static final String XSERVICE_ITEM_DATA = "item-data";
	public static final String XSERVICE_READ_ITEM = "read-item";
	public static final String XSERVICE_FIND_DOC = "find-doc";
	public static final String XSERVICE_GET_HOLDING = "get-holding";
	public static final String XSERVICE_HOLD_REQUEST = "hold-req";
	public static final String XSERVICE_CANCEL_HOLD_REQUEST = "hold-req-cancel";
	public static final String XSERVICE_RENEW = "renew";
	public static final String XSERVICE_PUBLISH_AVAILABILITY = "publish_avail";

	public static final String VARFIELD_NODE = "varfield";
	public static final String FIND_DOC_NODE = "find-doc";
	public static final String RECORD_NODE = "record";

	public static final String ALEPH_ILS_AGENCY = "AlephILSAgency";
	public static final String ALEPH_HOLD_LIBRARY = "AlephHoldLibrary";

	// x-service params
	public static final String PARAM_X_SERVICE_NAME = "op";
	public static final String CIRC_STATUS_CHECKED_OUT = "A";

}
