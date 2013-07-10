/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import java.util.ArrayList;


import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class implements the Lookup User service 
 */
public class SymphonyLookupUserService extends SymphonyService implements LookupUserService {

	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
    
    public SymphonyLookupUserService() {
    }

    /**
     * Handles a NCIP LookupUser service .
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService(LookupUserInitiationData initData,
    											 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final LookupUserResponseData responseData = new LookupUserResponseData();
        UserId userId = this.retrieveUserId(initData);
        
        LookupUserTransactionResults sirsiUser = new LookupUserTransactionResults();
        try {
        	validateUserId(userId);
        }
        catch(SymphonyConnectorException sirsiServiceException) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.UNKNOWN_USER_ERROR),SymphonyConstants.USER_PROBLEM_ELEMENT,"UID Invalid",sirsiServiceException.getMessage());
        	responseData.getProblems().add(p);
        	return responseData;
        }  
        
        try {
        	sirsiUser = this.callSirsi(serviceManager,userId);
        }
        catch(SymphonyConnectorException symphonyConnectorException) {
        	Problem problem = new Problem();
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	if (symphonyConnectorException.getMessage() != null && symphonyConnectorException.getMessage().equalsIgnoreCase(SymphonyConstants.SIRSI_FAILED)) {
        		problem = new Problem(new ProblemType(SymphonyConstants.SIRSI_FAILED),SymphonyConstants.SIRSI_FAILED,"SirsiDynix May be Unavailable",symphonyConnectorException.getMessage() + "-" + SymphonyErrorCodes.getInstance().getCodes().get(symphonyConnectorException.getMessage()));
        	}
        	else {
        		problem = new Problem(new ProblemType(SymphonyConstants.UNKNOWN_USER_ERROR),SymphonyConstants.USER_PROBLEM_ELEMENT,"UID Invalid",symphonyConnectorException.getMessage() + "-" + SymphonyErrorCodes.getInstance().getCodes().get(symphonyConnectorException.getMessage()));
        	}
        	responseData.getProblems().add(problem);
        	return responseData;
        }
        
        responseData.setUserId(userId);
        
        if (responseData.getUserOptionalFields()==null)
        	responseData.setUserOptionalFields(new UserOptionalFields());
        if (initData.getUserLanguageDesired())
        	responseData.getUserOptionalFields().setUserLanguages(this.retrieveLanguage(sirsiUser));
        if (initData.getUserAddressInformationDesired())
        	responseData.getUserOptionalFields().setUserAddressInformations(this.retrieveAddress(sirsiUser));
        if (initData.getUserPrivilegeDesired())
        	responseData.getUserOptionalFields().setUserPrivileges(this.retrievePrivileges(sirsiUser));
        if (initData.getNameInformationDesired())
        	responseData.getUserOptionalFields().setNameInformation(this.retrieveName(sirsiUser));
        return responseData;
    }
    
    private LookupUserTransactionResults callSirsi(RemoteServiceManager serviceManager,UserId userId) throws SymphonyConnectorException{
    	SymphonyRemoteServiceManager sirsiServiceManager = (SymphonyRemoteServiceManager)serviceManager;
        LookupUserTransactionResults sirsiUser = sirsiServiceManager.lookupSirsiUser(userId);
        //check for errors
        //if the sirsi call returns any message (mn field) from lookup user - there was a problem
        if (sirsiUser.getStatusCode() != null) {
        	SymphonyConnectorException symphonyConnectorException = new SymphonyConnectorException(sirsiUser.getStatusCode());
        	throw symphonyConnectorException;
        }
        return sirsiUser;
    }
    
    private void validateUserId(UserId userId) throws SymphonyConnectorException {
    	this.validateUserIdIsPresent(userId);
    	this.validateUserIdIsValid(userId);
    }
    
       
    private NameInformation retrieveName(LookupUserTransactionResults sirsiUser) {
    	NameInformation nameInformation = new NameInformation();
    	PersonalNameInformation personalNameInformation = new PersonalNameInformation();
    	StructuredPersonalUserName structuredPersonalUserName = new StructuredPersonalUserName();
    	structuredPersonalUserName.setGivenName(sirsiUser.getLastName());
    	structuredPersonalUserName.setSurname(sirsiUser.getFirstName());
    	personalNameInformation.setUnstructuredPersonalUserName(sirsiUser.getFullName());
    	nameInformation.setPersonalNameInformation(personalNameInformation);
    	return nameInformation;
    }
    
    private ArrayList<UserPrivilege> retrievePrivileges(LookupUserTransactionResults sirsiUser) {
    	ArrayList<UserPrivilege> list = new ArrayList<UserPrivilege>();
    	list.add(this.retrievePrivilegeFor((String) symphonyConfig.getProperty("HOME_LIBRARY"), (String)symphonyConfig.getProperty("PRIV_LIB_DESCRIPTION"), (String)symphonyConfig.getProperty("PRIV_TYPE_LIBRARY")));
    	list.add(this.retrievePrivilegeFor(sirsiUser.getPrivilegePatronType(), (String) symphonyConfig.getProperty("PRIV_USER_PROFILE_DESCRIPTION"), (String) symphonyConfig.getProperty("PRIV_TYPE_PROFILE")));
    	list.add(this.retrievePrivilegeFor(sirsiUser.getPrivilegeStatus(), (String) symphonyConfig.getProperty("PRIV_STAT_DESCRIPTION"), (String) symphonyConfig.getProperty("PRIV_TYPE_STATUS")));
    	list.add(this.retrievePrivilegeFor(sirsiUser.getPrivilegeDepartment(), (String) symphonyConfig.getProperty("PRIV_CAT_DESCRIPTION"), (String) symphonyConfig.getProperty("PRIV_TYPE_CAT")));
    	return list;
    }
    

    private UserPrivilege retrievePrivilegeFor(String userPrivilegeStatusTypeString, String descriptionString, String agencyUserPrivilegeTypeString) {
    	
    	UserPrivilege up = new UserPrivilege();
    	up.setUserPrivilegeDescription(descriptionString);
    	up.setAgencyId( new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
    	up.setAgencyUserPrivilegeType(new AgencyUserPrivilegeType(null,agencyUserPrivilegeTypeString));
    	
    	UserPrivilegeStatus ups = new UserPrivilegeStatus();
    	ups.setUserPrivilegeStatusType(new UserPrivilegeStatusType(null,userPrivilegeStatusTypeString));
    	
    	up.setUserPrivilegeStatus(ups);
    	return up;
    }
    
    private ArrayList<UserAddressInformation> retrieveAddress(LookupUserTransactionResults sirsiUser) {
    	ArrayList<UserAddressInformation> list = new ArrayList<UserAddressInformation>();
    	list.add(retrievePhysicalAddress(sirsiUser));
    	list.add(retrieveEmail(sirsiUser));
    	list.add(retrieveTelephoneNumber(sirsiUser));
    	return list;
    }
    
    private UserAddressInformation retrieveTelephoneNumber(LookupUserTransactionResults sirsiUser) {
    	if (sirsiUser.getTelephone() != null) {
    		ElectronicAddress phone = new ElectronicAddress();
    		phone.setElectronicAddressData(sirsiUser.getTelephone());
    		phone.setElectronicAddressType(new ElectronicAddressType(SymphonyConstants.TELEPHONE_ADDRESS_TYPE));
    		UserAddressInformation uai = new UserAddressInformation();
    		uai.setUserAddressRoleType(new UserAddressRoleType(SymphonyConstants.HOME_ADDRESS_ROLE));
    		uai.setElectronicAddress(phone);
    		return uai;
    	}
    	else return null;
    }
    
    private UserAddressInformation retrieveEmail(LookupUserTransactionResults sirsiUser) {
    	ElectronicAddress email = new ElectronicAddress();
    	email.setElectronicAddressData(sirsiUser.getEmailAddress());
    	email.setElectronicAddressType(new ElectronicAddressType(SymphonyConstants.EMAIL_ADDRESS_TYPE));
    	UserAddressInformation uai = new UserAddressInformation();
    	uai.setUserAddressRoleType(new UserAddressRoleType(SymphonyConstants.HOME_ADDRESS_ROLE));
    	uai.setElectronicAddress(email);
    	return uai;
    }
    
    private UserAddressInformation retrievePhysicalAddress(LookupUserTransactionResults sirsiUser) {
        UserAddressInformation uai = new UserAddressInformation();
        PhysicalAddress pa = new PhysicalAddress();
        StructuredAddress sa = new StructuredAddress();
        sa.setLine1((String)sirsiUser.getPhysAddressStreet());
        sa.setPostalCode((String)sirsiUser.getPhysAddressZip());
        String cityState = ((String)sirsiUser.getPhysAddressCityState());
        try {
        	String[] cityStateArray = cityState.split(",");
        	String city = (String)cityStateArray[0];
        	String state = (String)cityStateArray[1];
        	sa.setLocality(city);
            sa.setRegion(state);
        }
        catch(Exception e) {
        	sa.setLocality(cityState);
            sa.setRegion("");
        }
    	pa.setStructuredAddress(sa);
    	uai.setUserAddressRoleType(new UserAddressRoleType(SymphonyConstants.PRIMARY_ADDRESS_ROLE));
    	pa.setPhysicalAddressType(new PhysicalAddressType(null,SymphonyConstants.POSTAL_ADDRESS_TYPE));
    	uai.setPhysicalAddress(pa);
    	return uai;
    }
    
    private ArrayList<UserLanguage> retrieveLanguage(LookupUserTransactionResults sirsiUser) {
    	ArrayList<UserLanguage> list = new ArrayList<UserLanguage>();
    	list.add(new UserLanguage(null,sirsiUser.getLanguage()));
    	return list;
    }
    
    private UserId retrieveUserId(LookupUserInitiationData initData) {
    	UserId uid = null;
    	String uidString = null;
    	if (initData.getUserId() != null) {
    		uid = initData.getUserId();
    	}
    	else {
    		//TRY Barcode Id
    		uidString = this.retrieveAuthenticationInputTypeOf((String) symphonyConfig.getProperty("AUTH_BARCODE"),initData);
    		//TRY User Id
    		if (uidString == null) {
    			uidString = this.retrieveAuthenticationInputTypeOf((String) symphonyConfig.getProperty("AUTH_UID"), initData);
    		}
    	}
    	if (uidString != null) {
    		uid = new UserId();
    		uid.setUserIdentifierValue(uidString);
    	}
    	return uid;
    }
    

    
    private String retrieveAuthenticationInputTypeOf(String type,LookupUserInitiationData initData) {
    	if (initData.getAuthenticationInputs() == null) return null;
    	String authenticationID = null;
    	for (AuthenticationInput authenticationInput : initData.getAuthenticationInputs()) {
    		if (authenticationInput.getAuthenticationInputType().getValue().equalsIgnoreCase(type)) {
    			authenticationID = authenticationInput.getAuthenticationInputData();
    			break;
    		}
    	}
    	if (authenticationID != null && authenticationID.equalsIgnoreCase("")) authenticationID = null;
    	return authenticationID;
    }
}
