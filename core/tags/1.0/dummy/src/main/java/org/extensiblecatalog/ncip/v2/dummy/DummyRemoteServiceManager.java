/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;


import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;


/**
 * ServiceManager is responsible for locating the correct back-end service; for
 * the Dummy back-end there are no services; this class always returns the same hard-coded values.
 * Note: If you're looking for a model of how to code your own ILS's RemoteServiceManager, do not
 * use this class's methods as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyRemoteServiceManager implements RemoteServiceManager {

    protected static AgencyId ourAgencyId = new AgencyId("local.edu", "US");
    protected static DummyDatabase database = new DummyDatabase();

    /**
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public DummyRemoteServiceManager() {
    }

    /**
     * Get the library's name.
     *
     * @return the library name
     */
    public String getLibraryName() {
        return "Dummytown Library";
    }

    public String getNextRequestId() {

        return database.getNextRequestId();

    }

    public AgencyId getAgencyId() {
        return ourAgencyId;
    }

    public static CirculationStatus translateCircStatus(DummyDatabase.CircStatus circStatus) {

        CirculationStatus ncipCircStatus;

        switch ( circStatus) {

            case ON_ORDER :
                ncipCircStatus = Version1CirculationStatus.ON_ORDER;
                break;

            case ON_SHELF :
                ncipCircStatus = Version1CirculationStatus.AVAILABLE_ON_SHELF;
                break;

            case CHECKED_OUT :
                ncipCircStatus = Version1CirculationStatus.ON_LOAN;
                break;

            case IN_TRANSIT :
                ncipCircStatus = Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS;
                break;

            default :
                ncipCircStatus = Version1CirculationStatus.CIRCULATION_STATUS_UNDEFINED;

        }

        return ncipCircStatus;

    }
}
