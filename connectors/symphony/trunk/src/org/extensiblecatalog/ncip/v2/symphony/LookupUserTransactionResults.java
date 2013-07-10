/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;


public class LookupUserTransactionResults {
	
	private String emailAddress;
	private String privilegeHomeLibrary;
	private String privilegePatronType;
	private String privilegeStatus;
	private String privilegeDepartment;
	private String language;
	private String previousUid;
	private String userid;
	private String dateOfBirth;
	private String telephone;
	private String firstName;
	private String lastName;
	private String fullName; //unstructured name
	private String statusCode;
	private String physAddressStreet;
	private String physAddressCityState;
	private String physAddressZip;
	
	
	public String getPhysAddressStreet() {
		return physAddressStreet;
	}
	public void setPhysAddressStreet(String physAddressStreet) {
		this.physAddressStreet = physAddressStreet;
	}
	
	public String getPhysAddressCityState() {
		return physAddressCityState;
	}
	public void setPhysAddressCityState(String physAddressCityState) {
		this.physAddressCityState = physAddressCityState;
	}
	public String getPhysAddressZip() {
		return physAddressZip;
	}
	public void setPhysAddressZip(String physAddressZip) {
		this.physAddressZip = physAddressZip;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	public String getPrivilegeHomeLibrary() {
		return privilegeHomeLibrary;
	}
	public void setPrivilegeHomeLibrary(String privilegeHomeLibrary) {
		this.privilegeHomeLibrary = privilegeHomeLibrary;
	}
	public String getPrivilegePatronType() {
		return privilegePatronType;
	}
	public void setPrivilegePatronType(String privilegePatronType) {
		this.privilegePatronType = privilegePatronType;
	}
	public String getPrivilegeStatus() {
		return privilegeStatus;
	}
	public void setPrivilegeStatus(String privilegeStatus) {
		this.privilegeStatus = privilegeStatus;
	}
	public String getPrivilegeDepartment() {
		return privilegeDepartment;
	}
	public void setPrivilegeDepartment(String privilegeDepartment) {
		this.privilegeDepartment = privilegeDepartment;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPreviousUid() {
		return previousUid;
	}
	public void setPreviousUid(String previousUid) {
		this.previousUid = previousUid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	

}
