/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1CirculationStatus extends CirculationStatus {

    private static final Logger LOG = Logger.getLogger(Version1CirculationStatus.class);

    public static final String VERSION_1_CIRCULATION_STATUS
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/circulationstatus/circulationstatus.scm";

    // Item is being held for pickup by a User.
    public static final Version1CirculationStatus AVAILABLE_FOR_PICKUP
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Available For Pickup");
    // Item can be found in the shelf location specified in Call Number and Location and is available for loan or supply.
    public static final Version1CirculationStatus AVAILABLE_ON_SHELF
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Available On Shelf");
    // Item's Circulation Status is undefined.
    public static final Version1CirculationStatus CIRCULATION_STATUS_UNDEFINED
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Circulation Status Undefined");
    // Agency has received a report that a User or Agency claims to have returned the Item or never borrowed it.
    public static final Version1CirculationStatus CLAIMED_RETURNED_OR_NEVER_BORROWED
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Claimed Returned Or Never Borrowed");
    // Item has been received by the Agency but has not yet been fully processed (e.g., accessioned or cataloged).
    public static final Version1CirculationStatus IN_PROCESS
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "In Process");
    // Item is being moved from one agency location to another.
    public static final Version1CirculationStatus IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "In Transit Between Library Locations");
    // Item has been reported lost. The concept of "Lost" carries the implication that there is little hope that the Item will be found.
    public static final Version1CirculationStatus LOST
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Lost");
    // Item has been reported missing and is being traced. The concept of "Missing" carries the implication that the Item may be found.
    public static final Version1CirculationStatus MISSING
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Missing");
    // Item is not available for loan or supply.
    public static final Version1CirculationStatus NOT_AVAILABLE
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Not Available");
    // Item is currently on loan.
    public static final Version1CirculationStatus ON_LOAN
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "On Loan");
    // Item is on order, but has not been received and processed by the Agency.
    public static final Version1CirculationStatus ON_ORDER
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "On Order");
    // Item is to be transferred to another location, but that transfer has not yet taken place.
    public static final Version1CirculationStatus PENDING_TRANSFER
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Pending Transfer");
    // Item is on loan and has been recalled.
    public static final Version1CirculationStatus RECALLED
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Recalled");
    // Item is waiting to be reshelved and may be available for loan or supply.
    public static final Version1CirculationStatus WAITING_TO_BE_RESHELVED
            = new Version1CirculationStatus(VERSION_1_CIRCULATION_STATUS, "Waiting To Be Reshelved");

    public static void loadAll() {
        LOG.debug("Loading Version1CirculationStatus.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1CirculationStatus(String scheme, String value) {
        super(scheme, value);
    }
}
