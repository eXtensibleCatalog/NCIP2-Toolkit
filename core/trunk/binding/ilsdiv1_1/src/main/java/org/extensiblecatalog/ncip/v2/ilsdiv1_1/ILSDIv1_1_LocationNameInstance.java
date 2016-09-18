/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.ilsdiv1_1;

import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;

/**
 * Describes a LoanedItem result from an NCIP response
 */
public class ILSDIv1_1_LocationNameInstance extends org.extensiblecatalog.ncip.v2.service.LocationNameInstance {

	protected PhysicalAddress physicalAddress;
	
    public PhysicalAddress getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(PhysicalAddress physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

}
