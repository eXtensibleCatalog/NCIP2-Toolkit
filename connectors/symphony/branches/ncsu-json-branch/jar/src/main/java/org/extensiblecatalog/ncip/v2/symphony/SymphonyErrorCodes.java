/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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

