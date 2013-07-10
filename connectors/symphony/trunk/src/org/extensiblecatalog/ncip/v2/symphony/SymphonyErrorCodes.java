/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import java.util.Hashtable;

public class SymphonyErrorCodes {
	
	private static SymphonyErrorCodes instance = null;
	private Hashtable<String,String> codes = new Hashtable<String,String>();


	public Hashtable<String, String> getCodes() {
		return codes;
	}
	public void setCodes(Hashtable<String, String> codes) {
		this.codes = codes;
	}
	private SymphonyErrorCodes() {
		
	}
	 public synchronized static SymphonyErrorCodes getInstance() {
	      if(instance == null) {
	         instance = new SymphonyErrorCodes();
	         instance.codes.put("792","Item Already Charged to this User.");
	         instance.codes.put("18","Item charged and may not be recirculated.");
	         instance.codes.put("252","Item has holds - Charge not successful.");
	         instance.codes.put("11","Item not charged.  The item you specified is not charged out.");
	         instance.codes.put("2","User not found. The user ID or name you provided did not match any user in the system.");
	         instance.codes.put("7", "Item not found in catalog. The key you supplied to find an item did not match any item in the system.");
	         instance.codes.put("16297", "Date hold expires may not be earlier than today.  Message for Create Hold B.");
	         instance.codes.put("722", "User already has a hold on this material.  User may not place a duplicate hold.");
	         instance.codes.put("753", "User already has this title charged out. An attempt was made to hold an already charged item.");
	      }
	      return instance;
	 }
	

}

