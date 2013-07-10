/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.voyager;

class VoyagerTester 
{
	private String driverName;
	public VoyagerTester(String driverName)
	{
		this.driverName = driverName;
	}
	public VoyagerTester()
	{
		this.driverName = "default constructor";
	}
	public String getName(){
		return this.driverName;
	}

	public String getDriverName()
	{
				return this.driverName;
	}
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}
     public void init() {
		 		this.driverName = "another test";

	 }

}
