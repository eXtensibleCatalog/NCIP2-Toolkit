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
public class ILSDIvOneOneLookupItemSetInitiationData extends LookupItemSetInitiationData {

	protected UserId userId;
	
	public UserId getUserId() {
		return this.userId;
	}
	
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

}
