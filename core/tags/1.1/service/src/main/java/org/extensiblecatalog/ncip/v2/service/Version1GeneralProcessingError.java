/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1GeneralProcessingError extends ProblemType {

    private static final Logger LOG = Logger.getLogger(Version1GeneralProcessingError.class);

    public static final String VERSION_1_GENERAL_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/generalprocessingerror.scm";

    public static final Version1GeneralProcessingError AGENCY_AUTHENTICATION_FAILED
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Agency Authentication Failed"); // Attempt to authenticate the Agency was unsuccessful; the reason for the failure is unspecified.
    public static final Version1GeneralProcessingError INVALID_AMOUNT
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Invalid Amount"); // The requested action cannot proceed because the amount is not correct.
    public static final Version1GeneralProcessingError INVALID_DATE
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Invalid Date"); // Date/time is invalid because it is not a date, is an improperly formatted date/time, the date/time has passed, or is outside of the range of dates or times for which the service is provided.
    public static final Version1GeneralProcessingError NEEDED_DATA_MISSING
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Needed Data Missing"); // Action requested by the initiator cannot be carried out because one or more optional data elements that are needed for processing are missing.
    public static final Version1GeneralProcessingError SYSTEM_AUTHENTICATION_FAILED
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "System Authentication Failed"); // Attempt to authenticate the system was unsuccessful; the reason for the failure is unspecified.
    public static final Version1GeneralProcessingError TEMPORARY_PROCESSING_FAILURE
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Temporary Processing Failure"); // Temporary processing failure occurred.
    public static final Version1GeneralProcessingError UNAUTHORIZED_COMBINATION_OF_ELEMENT_VALUES_FOR_AGENCY
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unauthorized Combination Of Element Values For Agency"); // Agency is not authorized to use this combination of element values.
    public static final Version1GeneralProcessingError UNAUTHORIZED_COMBINATION_OF_ELEMENT_VALUES_FOR_SYSTEM
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unauthorized Combination Of Element Values For System"); // System is not authorized to use this combination of element values.
    public static final Version1GeneralProcessingError UNAUTHORIZED_SERVICE_FOR_AGENCY
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unauthorized Service For Agency"); // Agency is not authorized to use this Service.
    public static final Version1GeneralProcessingError UNAUTHORIZED_SERVICE_FOR_SYSTEM
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unauthorized Service For System"); // System is not authorized to use this Service.
    public static final Version1GeneralProcessingError UNKNOWN_AGENCY
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unknown Agency"); // Agency is not known.
    public static final Version1GeneralProcessingError UNKNOWN_SYSTEM
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unknown System"); // System is not known.
    public static final Version1GeneralProcessingError UNSUPPORTED_SERVICE
        = new Version1GeneralProcessingError(VERSION_1_GENERAL_PROCESSING_ERROR, "Unsupported Service"); // Service is not supported.


    public static void loadAll() {
        LOG.debug("Loading Version1GeneralProcessingError.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1GeneralProcessingError(String scheme, String value) {
        super(scheme, value);
    }
}
