/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * Data to initiate the LookupItemSet service.
 */
public class ILSDIvOneOneLookupUserInitiationData extends LookupUserInitiationData {

    /**
     * Maximum items count
     */
    protected ILSDIvOneOneHistoryDesired historyDesired;

	public ILSDIvOneOneHistoryDesired getHistoryDesired() {
		return historyDesired;
	}

	public void setHistoryDesired(ILSDIvOneOneHistoryDesired historyDesired) {
		this.historyDesired = historyDesired;
	}
    
    
}
