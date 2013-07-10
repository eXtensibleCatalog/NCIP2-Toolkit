/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;


public class SymphonyCheckoutItemService extends SymphonyService implements CheckOutItemService {
			

	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
   
    public SymphonyCheckoutItemService() {
    }

  
    @Override
    public CheckOutItemResponseData performService(CheckOutItemInitiationData initData,
    											 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final CheckOutItemResponseData responseData = new CheckOutItemResponseData();
        UserId userId = this.retrieveUserId(initData);
        if (userId != null) userId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        ItemId itemId = initData.getItemId();     
        itemId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        
        CheckoutTransactionReults checkoutTrans = new CheckoutTransactionReults();
        try {
        	validateUserId(userId);
        	validateItemId(itemId);
        }
        catch(Exception exception) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.CHECK_OUT_PROBLEM),SymphonyConstants.CHECK_OUT_INPUT_PROBLEM,exception.getMessage(),exception.getMessage());
        	responseData.getProblems().add(p);
        	return responseData;
        }  
        
        try {
        	checkoutTrans = this.callSirsi(serviceManager,userId,itemId);
        	checkSirsiResponseForErrors(checkoutTrans);
        }
        catch(SymphonyConnectorException symphonyConnectorException) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.CHECK_OUT_PROBLEM),SymphonyConstants.UNKNOWN_DATA_ELEMENT,SymphonyConstants.CHECK_OUT_PROBLEM,symphonyConnectorException.getMessage() + "-" + SymphonyErrorCodes.getInstance().getCodes().get(symphonyConnectorException.getMessage()));
        	responseData.getProblems().add(p);
        	return responseData;
        }
        try {
        	GregorianCalendar calendar = new GregorianCalendar();
        	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        	Date d = sdf.parse(checkoutTrans.getDueDateString());
        	calendar.setTime(d);
            responseData.setUserId(userId);
            responseData.setItemId(itemId);
        	responseData.setDateDue(calendar);
        	responseData.setRenewalCount(checkoutTrans.getRenewalCount());
        	return responseData;
        }
        catch(Exception e) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.CHECK_OUT_PROBLEM),SymphonyConstants.UNKNOWN_DATA_ELEMENT,SymphonyConstants.CHECK_OUT_PROBLEM,"issue parsing sirsi return");
        	responseData.getProblems().add(p);
        	return responseData;
        }
    }
    
    private CheckoutTransactionReults callSirsi(RemoteServiceManager serviceManager,UserId userId,ItemId itemId) {
    	SymphonyRemoteServiceManager sirsiServiceManager = (SymphonyRemoteServiceManager)serviceManager;
        CheckoutTransactionReults checkoutTransaction = sirsiServiceManager.checkoutItem(userId, itemId);
        return checkoutTransaction;
    }
    
    private void checkSirsiResponseForErrors(CheckoutTransactionReults checkoutTransaction) throws SymphonyConnectorException {
        if (!checkoutTransaction.getStatusCode().equalsIgnoreCase(SymphonyConstants.CHARGED_OUT_SUCCESSFUL)) {
        	SymphonyConnectorException symphonyConnectorException = new SymphonyConnectorException(checkoutTransaction.getStatusCode());
        	throw symphonyConnectorException;
        }
    }
    
    private void validateUserId(UserId userId) throws SymphonyConnectorException {
    	this.validateUserIdIsPresent(userId);
    	this.validateUserIdIsValid(userId);
    }
    
    
    private void validateItemId(ItemId itemId) throws SymphonyConnectorException {
    	this.validateItemIdIsPresent(itemId);
    	this.validateItemIdIsValid(itemId);
    }
    
 
    private UserId retrieveUserId(CheckOutItemInitiationData initData) {

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
    

    
    private String retrieveAuthenticationInputTypeOf(String type,CheckOutItemInitiationData initData) {
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
