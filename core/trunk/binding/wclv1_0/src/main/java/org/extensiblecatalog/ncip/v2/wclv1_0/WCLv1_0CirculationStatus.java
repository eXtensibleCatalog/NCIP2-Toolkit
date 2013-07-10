/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLv1_0CirculationStatus extends CirculationStatus {

    private static final Logger LOG = Logger.getLogger(WCLv1_0CirculationStatus.class);

    private static final List<WCLv1_0CirculationStatus> VALUES_LIST = new CopyOnWriteArrayList<WCLv1_0CirculationStatus>();

    public WCLv1_0CirculationStatus(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLv1_0CirculationStatus that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLv1_0CirculationStatus that matches, or null if none is found to match.
     */
    public static WCLv1_0CirculationStatus find(String scheme, String value) throws ServiceException {
        return (WCLv1_0CirculationStatus)find(scheme, value, VALUES_LIST, WCLv1_0CirculationStatus.class);
    }

    public static final String VERSION_1_WCL_CIRCULATION_STATUS
        = "http://worldcat.org/ncip/schemes/v2/extensions/circulationstatus.scm";

    /** The item is available. */
    public static final WCLv1_0CirculationStatus AVAILABLE = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Available");
    /** The item is temporarily unavailable. */
    public static final WCLv1_0CirculationStatus UNAVAILABLE = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Unavailable");
    /** The item is currently on order. */
    public static final WCLv1_0CirculationStatus ON_ORDER = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "On Order");
    /** The item is currently on loan. */
    public static final WCLv1_0CirculationStatus ON_LOAN = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "On Loan");
    /** The item is currently on loan - but has been recalled. */
    public static final WCLv1_0CirculationStatus RECALLED = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Recalled");
    /** The item is currently on the hold shelf. */
    public static final WCLv1_0CirculationStatus ON_HOLD = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "On Hold");
    /** The item is scheduled for transit between locations. */
    public static final WCLv1_0CirculationStatus TRANSIT = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Transit");
    /** The item has been dispatched for transit between locations. */
    public static final WCLv1_0CirculationStatus DISPATCHED	= new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Dispatched");
    /** The item is believed to have been lost. */
    public static final WCLv1_0CirculationStatus LOST = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Lost");
    /** The item is currently missing. */
    public static final WCLv1_0CirculationStatus MISSING = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Missing");
    /** The item is currently missing - the previous borrower claiming it was returned. */
    public static final WCLv1_0CirculationStatus CLAIMED_RETURNED	= new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Claimed Returned");
    /** The item is currently missing - the previous borrower claiming they never had it. */
    public static final WCLv1_0CirculationStatus CLAIMED_NEVER_HAD = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Claimed Never Had");
    /** The item has been withdrawn. */
    public static final WCLv1_0CirculationStatus WITHDRAWN = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Withdrawn");
    /** The item is overdue. */
    public static final WCLv1_0CirculationStatus OVERDUE = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Overdue");
    /** The item has been recently received. */
    public static final WCLv1_0CirculationStatus RECENTLY_RECEIVED = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Recently Received");
    /** The item has been recently returned. */
    public static final WCLv1_0CirculationStatus RECENTLY_RETURNED = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Recently Returned");
    /** The item has been cleared from the hold shelf. */
    public static final WCLv1_0CirculationStatus CLEARED = new WCLv1_0CirculationStatus(VERSION_1_WCL_CIRCULATION_STATUS, "Cleared");


    public static void loadAll() {
        LOG.debug("Loading WCLv1_0CirculationStatus.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
