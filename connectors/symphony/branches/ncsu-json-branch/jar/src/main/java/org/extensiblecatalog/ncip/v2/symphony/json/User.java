/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	
	@JsonProperty("ID")
	private String id;


    @JsonProperty("name")
	private String name;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("altID")
    private String altId;



    @JsonProperty("statusType")
    private String status;

    /**
     * An patronType type supported by Sirsi Symphony
     */
    @JsonProperty("cat1")
    private String userCategory1;

    @JsonProperty("cat2")
    private String userCategory2;

	@JsonProperty("profileName")
	private String patronType;
	
	@JsonProperty("addresses")
	private List<Address> addresses = new ArrayList<Address>();

    @JsonProperty("datePrivsGranted")
    private Date privilegeStartDate;

    @JsonProperty("datePrivsExpire")
    private Date privilegeExpiryDate;


    @JsonProperty("estimatedFines")
    private Amount estimatedFines = new Amount();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the patronType
	 */
	public String getPatronType() {
		return patronType;
	}

	/**
	 * @param patronType the patronType to set
	 */
	public void setPatronType(String patronType) {
		this.patronType = patronType;
	}

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCategory1() {
        return userCategory1;
    }

    public void setUserCategory1(String userCategory1) {
        this.userCategory1 = userCategory1;
    }

    public String getUserCategory2() {
        return userCategory2;
    }

    public void setUserCategory2(String userCategory2) {
        this.userCategory2 = userCategory2;
    }

    public Date getPrivilegeStartDate() {
        return privilegeStartDate;
    }

    public void setPrivilegeStartDate(Date privilegeStartDate) {
        this.privilegeStartDate = privilegeStartDate;
    }

    public Date getPrivilegeExpiryDate() {
        return privilegeExpiryDate;
    }

    public void setPrivilegeExpiryDate(Date privilegeExpiryDate) {
        this.privilegeExpiryDate = privilegeExpiryDate;
    }

    public Amount getEstimatedFines() {
        return estimatedFines;
    }

    public void setEstimatedFines(Amount estimatedFines) {
        this.estimatedFines = estimatedFines;
    }
}
