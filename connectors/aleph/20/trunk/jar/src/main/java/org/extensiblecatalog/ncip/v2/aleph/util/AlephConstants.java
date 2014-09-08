/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/. 
 */

package org.extensiblecatalog.ncip.v2.aleph.util;

/**
 * This class defines several constants as public static final variables which
 * may be used throughout the NCIP Toolkit
 * 
 */
public class AlephConstants extends org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants {
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
     * Define Agency, ADMLibrary, BIBLibrary, and Hold Library for all agencies,
     * This is actually the base of indexed properties in the config file
     */
    public static final String CONFIG_ALEPH_AGENCY = "AlephILSAgency";
    public static final String CONFIG_ALEPH_ADM_LIBRARY = "AlephAdmLibrary";
    public static final String CONFIG_ALEPH_BIB_LIBRARY = "AlephBibLibrary";
    public static final String CONFIG_ALEPH_HOLD_LIBRARY = "AlephHoldLibrary";

    public static final String CONFIG_ALEPH_CIRC_STATUS_AVAILABLE = "AlephCircStatusAvailable";
    public static final String CONFIG_ALEPH_CIRC_STATUS_POSSIBLY_AVAILABLE = "AlephCircStatusPossiblyAvailable";
    public static final String CONFIG_ALEPH_CIRC_STATUS_NOT_AVAILABLE = "AlephCircStatusNotAvailable";
    public static final String ON_SHELF = "On Shelf";
    
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
    
    public static final String CIRC_STATUS_CHECKED_OUT = "A";

	public static final String DEFAULT_SCHEME = "http://www.niso.org/schemas/ncip/v2_02/ncip_v2_02.xsd";

}
