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
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.extensiblecatalog.ncip.v2.service.AccountBalance;
import org.extensiblecatalog.ncip.v2.service.AccountDetails;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.AgencyUserPrivilegeType;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.LookupUserResponseData;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.UserPrivilege;
import org.extensiblecatalog.ncip.v2.service.UserPrivilegeStatus;
import org.extensiblecatalog.ncip.v2.service.UserPrivilegeStatusType;
import org.extensiblecatalog.ncip.v2.symphony.ConversionUtils;
import org.extensiblecatalog.ncip.v2.symphony.SymphonyConstants;


/**
 * Represents a JSON response to a LookupUser request. Includes a default
 * conversion to toolkit responses.
 *
 * @author adam_constabaris@ncsu.edu, $LastChangedBy$
 * @since 1.2
 */
public class PatronInfo {
	
	@JsonProperty("user")
	private User user;
	
	
	@JsonProperty("charges")
	private List<Charge> charges = new ArrayList<Charge>();
	
	@JsonProperty("bills")
	private List<Bill> bills = new ArrayList<Bill>();
	
	
	@JsonProperty("holds")
	private List<Hold> holds = new ArrayList<Hold>();
	
	@JsonProperty("addresses")
	private List<Address> addresses = new ArrayList<Address>();
	
	/**
	 * @return the charges
	 */
	public List<Charge> getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	/**
	 * @return the bills
	 */
	public List<Bill> getBills() {
		return bills;
	}

	/**
	 * @param bills the bills to set
	 */
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString() {
			return user != null ? user.getName() : "[Unknown]";
	}

	/**
	 * @return the holds
	 */
	public List<Hold> getHolds() {
		return holds;
	}

	/**
	 * @param holds the holds to set
	 */
	public void setHolds(List<Hold> holds) {
		this.holds = holds;
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

    public LookupUserResponseData getResponseData() {
        return new NCIPTranslator().getResponseData(null);
    }

    public LookupUserResponseData getResponseData(LookupUserResponseData data) {
        return new NCIPTranslator().getResponseData(data);
    }

    private class NCIPTranslator {
        User user = PatronInfo.this.getUser();

        LookupUserResponseData getResponseData(LookupUserResponseData data) {
            if ( data == null ) {
                data = new LookupUserResponseData();
            }
            data.setUserId( getUserId() );
            data.setUserOptionalFields(new UserOptionalFields());
            data.getUserOptionalFields().setNameInformation(getName());
            data.getUserOptionalFields().setUserAddressInformations(getAddresses());
            data.getUserOptionalFields().setUserPrivileges(getPrivileges());
            data.setLoanedItems(getLoanedItems());
            data.setRequestedItems( getHolds() );
            data.setUserFiscalAccounts(Collections.singletonList(this.getFiscalAccount()));
            return data;
        }

        private UserId getUserId() {
            UserId uid = new UserId();
            uid.setUserIdentifierValue(user.getId());
            // there are no defined schemes/types for this information
            return uid;
        }

        private List<UserPrivilege> getPrivileges() {
            //List<UserPrivilege> privs = new ArrayList<UserPrivilege>();
            UserPrivilege affiliation = new UserPrivilege();
            AgencyUserPrivilegeType privType = new AgencyUserPrivilegeType(null, user.getPatronType() );
            affiliation.setAgencyUserPrivilegeType(privType);
            affiliation.setAgencyId( new AgencyId(SymphonyConstants.AGENCY_ID) );
            UserPrivilegeStatus status = new UserPrivilegeStatus();
            status.setUserPrivilegeStatusType(new UserPrivilegeStatusType(null, user.getStatus() ) );
            affiliation.setUserPrivilegeStatus(status);
            if ( user.getPrivilegeStartDate() != null ) {
                affiliation.setValidFromDate(ConversionUtils.toGregorianCalendar(user.getPrivilegeStartDate() ) );
            } else {
                affiliation.setValidFromDate(ConversionUtils.getFarPastDate());
            }
            if ( user.getPrivilegeExpiryDate() != null ) {
                affiliation.setValidToDate( ConversionUtils.toGregorianCalendar( user.getPrivilegeExpiryDate() ));
            } else {
                affiliation.setValidToDate(ConversionUtils.getAYearHence());
            }
            return Collections.singletonList(affiliation);
        }

        private NameInformation getName() {
            NameInformation name = new NameInformation();
            PersonalNameInformation pinfo = new PersonalNameInformation();
            // structured always takes precedence over unstructured
            StructuredPersonalUserName spn = new StructuredPersonalUserName();
            spn.setGivenName(user.getFirstName());
            spn.setSurname(user.getSurname());
            pinfo.setStructuredPersonalUserName(spn);
            name.setPersonalNameInformation(pinfo);
            return name;
        }

        private List<LoanedItem> getLoanedItems() {
            List<LoanedItem> items = new ArrayList<LoanedItem>();
            for( Charge charge : getCharges() )
            {
                items.add( charge.toLoanedItem() );
            }
            return items;
        }

        private List<UserAddressInformation> getAddresses() {
            List<UserAddressInformation> addrs = new ArrayList<UserAddressInformation>();
            for (Address addr : user.getAddresses() ) {
                   addrs.addAll( addr.toAddressInformation() );
            }
            return addrs;
        }

        private List<RequestedItem> getHolds() {
            List<RequestedItem> items = new ArrayList<RequestedItem>();
            for( Hold hold : holds ) {
                items.add( hold.toRequestedItem());
            }
            return items;
        }


        private UserFiscalAccount getFiscalAccount() {
            AccountBalance balance = new AccountBalance();
            org.extensiblecatalog.ncip.v2.service.Amount estFines = user.getEstimatedFines().toNCIPAmount();

            balance.setCurrencyCode( estFines.getCurrencyCode() );
            balance.setMonetaryValue( estFines.getMonetaryValue() );
            int billNumber = 1;
            List<AccountDetails> accountDetailses = new ArrayList<AccountDetails>();
            for( Bill bill : getBills() ) {
                accountDetailses.addAll(bill.getAccountDetails(billNumber++));
            }
            return new UserFiscalAccount(balance, accountDetailses);
        }

    }

}
