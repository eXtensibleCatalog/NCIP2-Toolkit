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



public class SymphonyAcceptItemService extends SymphonyService implements AcceptItemService {
	
	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
			
    public SymphonyAcceptItemService() {
    	
    }

  
    @Override
    public AcceptItemResponseData performService(AcceptItemInitiationData initData,
    											 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

        final AcceptItemResponseData responseData = new AcceptItemResponseData();
        UserId userId = this.retreiveUserId(initData);
        ItemId itemId = initData.getItemId();
        //ALTHOUGH THE PROTOCOL DOES NOT REQUIRE
        //USER ID & ITEM ID - I THINK SIRSI WILL NEED THIS TO ACCEPT ITEM
        try {
        	validateUserId(userId);
        	validateItemId(itemId);
        }
        catch(Exception exception) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.ACCEPT_ITEM_PROBLEM),SymphonyConstants.ACCEPT_ITEM_INPUT_PROBLEM,exception.getMessage(),exception.getMessage());
        	responseData.getProblems().add(p);
        	return responseData;
        }  
        itemId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        
        //Request ID is required by the protocol so we shouldn't have
        //to check for it at this level
        RequestId requestId = initData.getRequestId();
        requestId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        
        AcceptItemTransactionResults acceptItemTransactionResults = new AcceptItemTransactionResults();
        //4-8-2011 Addition - add name to bib. record
        String title = this.retreiveItemTitle(initData);
        
        try {
        	acceptItemTransactionResults = this.callSirsi(serviceManager,userId,itemId,requestId,title);
        	checkSirsiResponseForErrors(acceptItemTransactionResults);
        	responseData.setItemId(itemId);
        	responseData.setRequestId(requestId);
        	return responseData;
        }
        catch(SymphonyConnectorException symphonyConnectorException) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.ACCEPT_ITEM_PROBLEM),SymphonyConstants.UNKNOWN_DATA_ELEMENT,SymphonyConstants.ACCEPT_ITEM_PROBLEM,symphonyConnectorException.getMessage() + "-" + SymphonyErrorCodes.getInstance().getCodes().get(symphonyConnectorException.getMessage()));
        	responseData.getProblems().add(p);
        	return responseData;
        }
        
    }
    
    private AcceptItemTransactionResults callSirsi(RemoteServiceManager serviceManager,UserId userId,ItemId itemId,RequestId requestId, String title) {
    	SymphonyRemoteServiceManager sirsiServiceManager = (SymphonyRemoteServiceManager)serviceManager;
    	AcceptItemTransactionResults acceptItemTransactionResults = sirsiServiceManager.acceptItem(userId,itemId,requestId,title);
        return acceptItemTransactionResults;
    }
    
    private void checkSirsiResponseForErrors(AcceptItemTransactionResults acceptItemTransactionResults) throws SymphonyConnectorException {
    	
        //check for checked in (discharged) confirmation code which is 209
        if (!acceptItemTransactionResults.getStatusCode().equalsIgnoreCase(SymphonyConstants.ACCEPT_ITEM_SUCCESSFUL)) {
        	SymphonyConnectorException symphonyConnectorException = new SymphonyConnectorException(acceptItemTransactionResults.getStatusCode());
        	throw symphonyConnectorException;
        }
    }
    
    private void validateUserId(UserId userId) throws SymphonyConnectorException {
    	
    	this.validateUserIdIsPresent(userId);
    	this.validateUserIdIsValid(userId);
    }
    
  
    
    private void validateItemId(ItemId itemId) throws Exception {
    	
    	this.validateItemIdIsPresent(itemId);
    	this.validateItemIdIsValid(itemId);
    }
     
    private UserId retreiveUserId(AcceptItemInitiationData initData) {

    	UserId uid = null;
    	if (initData.getUserId() != null) {
    		uid = initData.getUserId();
    	}
    	return uid;
    }
    
    private String retreiveItemTitle(AcceptItemInitiationData initData) {
    	String title = "";
    	//put into a try catch - title is optional
    	try {
    		title = initData.getItemOptionalFields().getBibliographicDescription().getTitle();
    	}
    	catch (Exception e) {
    		//do nothing since title is optional.
    		//method will return an empty string
    	}

    	if (title==null) title = "";
    	return title;
    }
  
}
