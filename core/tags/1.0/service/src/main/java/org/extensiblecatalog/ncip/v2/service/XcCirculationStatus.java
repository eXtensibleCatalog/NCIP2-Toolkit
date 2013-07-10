/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class XcCirculationStatus extends CirculationStatus {

    private static final Logger LOG = Logger.getLogger(XcCirculationStatus.class);

    public static final String XC_CIRCULATION_STATUS
        = "http://www.extensiblecatalog.ncip.v2.org/schemes/circulationstatus/xccirculationstatus.scm";

    public static final XcCirculationStatus CHARGED
      = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Charged");

    public static final XcCirculationStatus NOT_CHARGED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Not Charged");

    public static final XcCirculationStatus RENEWED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Renewed");

    public static final XcCirculationStatus Overdue
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Overdue");

    public static final XcCirculationStatus RECALL_REQUEST
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Recall Request");

    public static final XcCirculationStatus HOLD_REQUEST
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Hold Request");

    public static final XcCirculationStatus ON_HOLD
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "On Hold");

    public static final XcCirculationStatus IN_TRANSIT
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "In Transit");

    public static final XcCirculationStatus IN_TRANSIT_DISCHARGED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "In Transit Discharged");

    public static final XcCirculationStatus IN_TRANSIT_ON_HOLD
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "In Transit On Hold");

    public static final XcCirculationStatus DISCHARGED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Discharged");

    public static final XcCirculationStatus MISSING
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Missing");

    public static final XcCirculationStatus LOST_LIBRARY_APPLIED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Lost--Library Applied");

    public static final XcCirculationStatus LOST_SYSTEM_APPLIED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Lost--System Applied");

    public static final XcCirculationStatus CLAIMS_RETURNED
    = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Claims Returned");

    public static final XcCirculationStatus DAMAGED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Damaged");

    public static final XcCirculationStatus WITHDRAWN
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Withdrawn");

    public static final XcCirculationStatus AT_BINDERY
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "At Bindery");

    public static final XcCirculationStatus CATALOGING_REVIEW
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Cataloging Review");

    public static final XcCirculationStatus CIRCULATION_REVIEW
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Circulation Review");

    public static final XcCirculationStatus SCHEDULED
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Scheduled");

    public static final XcCirculationStatus IN_PROCESS
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "In Process");

    public static final XcCirculationStatus CALL_SLIP_REQUEST
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Call Slip Request");

    public static final XcCirculationStatus SHORT_LOAN_REQUEST
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Short Loan Request");

    public static final XcCirculationStatus REMOTE_STORAGE_REQUEST
        = new XcCirculationStatus(XC_CIRCULATION_STATUS, "Remote Storage Request");

    public static void loadAll() {
        LOG.debug("Loading XCCirculationStatus.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public XcCirculationStatus(String scheme, String value) {
        super(scheme, value);
    }
}
