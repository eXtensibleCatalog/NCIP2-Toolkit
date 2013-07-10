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

import org.codehaus.jackson.annotate.JsonProperty;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.symphony.SymphonyConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 'bundle' of contact information for a user, e.g. home/work address, email
 * address.  Generally an address marked as "mailing" is the primary address that will be used to communicate
 * witht the user.
 * @author adam_constabaris@ncsu.edu
 */
public class Address implements Comparable<Address> {

    @Override
    public int compareTo(Address address) {
        return this.getType().compareTo(address.getType());
    }

    /**
     * Enumerated type outlining the computed 'type' an address has, and also
     * enumerating importance for sorting addresses.
     */
    public enum Type {
        MAILING,
        HOME,
        WORK,
        ELECTRONIC
    }

    private Type type = null;
	
	@JsonProperty("STREET")
	private String street;
	
	@JsonProperty("CITY/STATE")
	private String cityState;
	
	@JsonProperty("ZIP")
	private String zip;
	
	@JsonProperty("EMAIL")
	private String email;
	
	@JsonProperty("PHONE")
	private String phone;
	
	@JsonProperty("mailing")
	private boolean mailing = false;

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the cityState
	 */
	public String getCityState() {
		return cityState;
	}

	/**
	 * @param cityState the cityState to set
	 */
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the mailing
	 */
	public boolean isMailing() {
		return mailing;
	}

	/**
	 * @param mailing the mailing to set
	 */
	public void setMailing(boolean mailing) {
		this.mailing = mailing;
	}
	
	
	/**
	 * Since Sirsi is helpful enough to use a HOMEPHONE field
	 * instead of a PHONE field for home addresses we need to alias
	 * this bidness; sets <code>phone</code> property.
	 * @param homePhone
	 */
	@JsonProperty("HOMEPHONE")
	public void setHomePhone(String homePhone) {
        this.type = Type.HOME;
		this.phone = homePhone;
	}

    public Type getType() {
        if ( type == null ) {
            if ( mailing ) {
                return Type.MAILING;
            } else if ( getZip() == null && getEmail() != null ) {
                return Type.ELECTRONIC;
            } else {
                return Type.HOME;
            }
        }
        return type;
    }
	
	public String toString() {
		return street + "\n" + cityState + "\n" + zip + "\n" + phone + "\n" + email;
	}

    public List<UserAddressInformation> toAddressInformation() {
        List<UserAddressInformation> addresses = new ArrayList<UserAddressInformation>();


        if ( getZip() != null ) {
            String placeString = getCityState() == null ? "/" : getCityState();

            PhysicalAddress paddr = new PhysicalAddress();
            if ( isMailing() ) {
                paddr.setPhysicalAddressType(Version1PhysicalAddressType.POSTAL_ADDRESS);
            } else {
                paddr.setPhysicalAddressType(Version1PhysicalAddressType.STREET_ADDRESS);
            }

            StructuredAddress saddr = new StructuredAddress();
            saddr.setPostalCode(getZip());
            saddr.setStreet(getStreet());
            saddr.setCountry("USA");
            String [] placeParts = placeString.split("/");
            saddr.setLocality(placeParts[0]);
            if ( placeParts.length > 1 ) {
                saddr.setRegion(placeString.split("/")[1]);
            }
            paddr.setStructuredAddress(saddr);
            UserAddressInformation physicalAddr = new UserAddressInformation();
            if ( isMailing() ) {
                physicalAddr.setUserAddressRoleType(new UserAddressRoleType(SymphonyConstants.PRIMARY_ADDRESS_ROLE));
            } else {
                physicalAddr.setUserAddressRoleType(new UserAddressRoleType(SymphonyConstants.POSTAL_ADDRESS_TYPE));
            }

            physicalAddr.setPhysicalAddress(paddr);
            addresses.add(physicalAddr);
        }

        if ( getEmail() != null ) {
            ElectronicAddress email = new ElectronicAddress();
            email.setElectronicAddressType(SymphonyConstants.EMAIL_ADDRESS_TYPE);
            email.setElectronicAddressData(getEmail());
            UserAddressInformation emailInfo = new UserAddressInformation();
            emailInfo.setUserAddressRoleType(SymphonyConstants.EMAIL_ADDRESS_ROLE);
            emailInfo.setElectronicAddress(email);
            addresses.add(emailInfo);
        }

        if ( getPhone() != null ) {
            ElectronicAddress phone = new ElectronicAddress();
            phone.setElectronicAddressType(SymphonyConstants.TELEPHONE_ADDRESS_TYPE);
            phone.setElectronicAddressData(getPhone());
            UserAddressInformation phoneInfo = new UserAddressInformation();
            phoneInfo.setUserAddressRoleType(SymphonyConstants.TELEPHONE_ADDRESS_ROLE);
            phoneInfo.setElectronicAddress(phone);
            addresses.add(phoneInfo);
        }

        return addresses;
    }
}
