/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/. 
 */

package org.extensiblecatalog.ncip.v2.common.util;

/**
 * This class defines several constants as public static final variables which
 * may be used throughout the NCIP Toolkit V2
 * 
 * @author Hua Fan
 */
public class Constants {

    /**
     * default context/project name
     */
    public static final String DEFAULT_CONTEXT_NAME = "ncipv2";
    /**
     * Logger for the General log file
     */
    public static final String LOGGER_GENERAL = "general";

    /**
     * Parameter for looking up the location of the logger configuration file
     */
    public static final String CONFIG_LOGGER_CONFIG_FILE_LOCATION = "LoggerConfigFileLocation";

    /**
     * Parameter for looking up the default agency which the ILS is running on 
     */
    public static final String CONFIG_ILS_DEFAULT_AGENCY = "ILSDefaultAgency";

	/**
     * Parameter for looking up the default item identifier type which the ILS has for the item identifier.
     */
    public static final String CONFIG_ILS_DEFAULT_ITEM_IDENTIFIER_TYPE = "ILSDefaultItemIdentifierType";
	/**
     * Parameter for looking up the location of the NCIP Scheme being used
     */
    public static final String CONFIG_NCIP_SCHEME_FOLDER_URL = "NCIPSchemeFolderUrl";
    /**
     * ILSConfiguration path is the environment variable for the driver path
     */
    public static final String ILS_CONFIGURATIONDIR = "driver";
}