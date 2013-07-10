/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/.
 */

package org.extensiblecatalog.ncip.v2.voyager.util;

//import org.extensiblecatalog.ncip.v2.common.Constants;

/**
 * This class defines several constants as public static final variables which
 * may be used throughout the NCIP Toolkit
 *
 */
public class VoyagerConstants {
    // Voyager configuration

    /**
     * Parameter for looking up the default agency which the ILS is running on
     */
    public static final String CONFIG_ILS_DEFAULT_AGENCY = "ILSDefaultAgency";

    /**
     * Parameter indicating if site is a consortium
     */
    public static final String CONFIG_CONSORTIUM = "ConsortialUse";

    /**
     * Parameter for looking up the Voyager database's SID
     */
    public static final String CONFIG_VOYAGER_DB_URL = "VoyagerDatabaseUrl";

    /**
     * Parameter for looking up the username for logging into the Voyager
     * database and writing authentication data
     */
    public static final String CONFIG_VOYAGER_DB_READ_ONLY_USERNAME = "VoyagerDatabaseReadOnlyUsername";

    /**
     * Parameter for looking up the password for logging into the Voyager
     * database and writing authentication data
     */
    public static final String CONFIG_VOYAGER_DB_READ_ONLY_PASSWORD = "VoyagerDatabaseReadOnlyPassword";


    /**
     * Parameter for looking up the message returned by Voyager to indicate a
     * successful request
     */
    public static final String CONFIG_VOYAGER_REQUEST_SUCCESS_MESSAGE = "VoyagerRequestSuccessMessage";

    /**
     * Parameter for looking up the valid circulation statuses for placing a
     * callslip on an item
     */
    public static final String CONFIG_VOYAGER_VALID_CIRC_STATUSES_CALLSLIP = "VoyagerValidCircStatusesCallslip";

    /**
     * Parameter for looking up the valid circulation statuses for placing a
     * hold on an item
     */
    public static final String CONFIG_VOYAGER_VALID_CIRC_STATUSES_HOLD = "VoyagerValidCircStatusesHold";

    /**
     * Parameter for looking up the valid circulation statuses for placing a
     * recall on an item
     */
    public static final String CONFIG_VOYAGER_VALID_CIRC_STATUSES_RECALL = "VoyagerValidCircStatusesRecall";

    /**
     * Parameter for the Voyager default currency if default currency is not
     * stored in the database.
     */
    public static final String CONFIG_VOYAGER_DEFAULT_CURRENCY = "VoyagerDefaultCurrency";

	/**
     * Location Type Enum, Defined in the Schema
     */
    public static final String LOCATION_TYPE_PERMANENT = "Permanent";

	/**
     * Location Type Enum, Defined in the Schema
     */
    public static final String LOCATION_TYPE_TEMPORARY = "Temporary";

	/**
     * Location Type Enum, Defined in the Schema
	 * not yet supported in database, item table only has perm and temp
     */
    public static final String LOCATION_TYPE_CURRENT = "Current";

	/**
	 * Flag signifying a PERMANENT address
	 */
	public static final int ADDRESS_TYPE_PERMANENT = 1;

	/**
	 * Flag signifying a temporary address
	 */
	public static final int ADDRESS_TYPE_TEMPORARY = 2;

	/**
	 * Flag signifying an email address
	 */
	public static final int ADDRESS_TYPE_EMAIL = 3;

	/**
	 * Flag signifying an email address
	 */
	public static final String ADDRESS_EMAIL = "E-mail";

	/**
     * Parameter for looking up the number of seconds a user may remain inactive
     * before they are timed out
     */
	public static final String CONFIG_ILS_AUTHENTICATION_TIMEOUT = "AuthenticationTimeout";

    /**
     * Parameter for looking up the URL of the LDAP server used for external
     * authentication
     */
    public static final String CONFIG_EXTERNAL_LDAP_LOCATION = "ExternalLDAPLocation";

    /**
     * Parameter for looking up the port on which the LDAP server used for
     * external authentication is running
     */
    public static final String CONFIG_EXTERNAL_LDAP_PORT = "ExternalLDAPPort";

    /**
     * Parameter for looking up the attribute on the LDAP server used for
     * external authentication which represents the username
     */
    public static final String CONFIG_EXTERNAL_LDAP_USERNAME_ATTRIBUTE = "ExternalLDAPUsernameAttribute";

    /**
     * Parameter for looking up the attribute on the LDAP server used for
     * external authentication which represents the user ID which is used for
     * authentication on the ILS
     */
    public static final String CONFIG_EXTERNAL_LDAP_UR_ID = "ExternalLDAPUrId";

    /**
     * Parameter for looking up the attribute on the LDAP server used for
     * external authentication which represents the last name which is used for
     * authentication on the ILS
     */
    public static final String CONFIG_EXTERNAL_LDAP_LAST_NAME = "ExternalLDAPLastname";

    /**
     * Parameter for looking up the start location for logging into the LDAP
     * server used for external authentication
     */
    public static final String CONFIG_EXTERNAL_LDAP_START = "ExternalLDAPStart";

    /**
     * Parameter for the bind user to access user IDs through LDAP
     */
    public static final String CONFIG_EXTERNAL_LDAP_BIND_USER = "ExternalLDAPBindUser";

    /**
     * Parameter for the bind password to access user IDs through LDAP
     */
    public static final String CONFIG_EXTERNAL_LDAP_BIND_PASSWORD = "ExternalLDAPBindPassword";

    /**
     * Parameter for looking up the Voyager web service URL
     */
    public static final String CONFIG_VOYAGER_WEB_SERVICE_URL = "VoyagerVxwsUrl";

    /**
     * Parameter for specifying the maximum number of items to return in LUIS response
     */
    public static final String CONFIG_VOYAGER_MAX_LUIS_ITEMS = "MaxLuisItems";

    /**
     * Parameter for specifying if multiple tomcat instances are used for vxws
     */
    public static final String CONFIG_VOYAGER_MULTIPLE_TOMCATS = "MultipleTomcats";
}
