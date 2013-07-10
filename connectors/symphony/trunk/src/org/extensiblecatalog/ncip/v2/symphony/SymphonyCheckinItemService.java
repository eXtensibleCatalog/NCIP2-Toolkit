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


public class SymphonyCheckinItemService extends SymphonyService implements CheckInItemService {
			

	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
    
    public SymphonyCheckinItemService() {
    }

   
    @Override
    public CheckInItemResponseData performService(CheckInItemInitiationData initData,
    											 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) {

    	ItemId itemId = initData.getItemId();
        final CheckInItemResponseData responseData = new CheckInItemResponseData();
        itemId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        
        
        CheckInTransactionResults checkInTrans = new CheckInTransactionResults();
        try {
        	validateItemId(itemId);
        }
        catch(Exception checkInItemException) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.CHECK_IN_PROBLEM),SymphonyConstants.CHECK_IN_PROBLEM,"Item ID Invalid","item id validation failed");
        	responseData.getProblems().add(p);
        	return responseData;
        }  
        
        try {
        	checkInTrans = this.callSirsi(serviceManager,itemId);
        }
        catch(SymphonyConnectorException symphonyConnectorException) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.CHECK_IN_PROBLEM),SymphonyConstants.UNKNOWN_DATA_ELEMENT,SymphonyConstants.CHECK_IN_PROBLEM,symphonyConnectorException.getMessage() + "-" + SymphonyErrorCodes.getInstance().getCodes().get(symphonyConnectorException.getMessage()));
        	responseData.getProblems().add(p);
        	return responseData;
        }
        try {
        	UserId userId = new UserId();
        	userId.setAgencyId(new AgencyId((String) symphonyConfig.getProperty("AGENCY_ID")));
        	userId.setUserIdentifierValue(checkInTrans.getUserId());
        	responseData.setUserId(userId);
        	responseData.setItemId(itemId);
        	if (responseData.getItemOptionalFields()==null) responseData.setItemOptionalFields(new ItemOptionalFields());
        	if (initData.getBibliographicDescriptionDesired()) responseData.getItemOptionalFields().setBibliographicDescription(this.retrieveBiblioDescription(checkInTrans));
        	if (initData.getItemDescriptionDesired()) responseData.getItemOptionalFields().setItemDescription(this.retreiveItemDescription(checkInTrans));
        	
        	return responseData;
        }
        catch(Exception e) {
        	if (responseData.getProblems() == null) responseData.setProblems(new ArrayList<Problem>());
        	Problem p = new Problem(new ProblemType(SymphonyConstants.UNKNOWN_USER_ERROR),SymphonyConstants.USER_PROBLEM_ELEMENT,"UID Invalid","issue parsing sirsi return");
        	responseData.getProblems().add(p);
        	return responseData;
        }
    }
    
    
    private CheckInTransactionResults callSirsi(RemoteServiceManager serviceManager,ItemId itemId) throws SymphonyConnectorException{
    	SymphonyRemoteServiceManager sirsiServiceManager = (SymphonyRemoteServiceManager)serviceManager;
        CheckInTransactionResults checkInTransaction = sirsiServiceManager.checkInItem(itemId);
        //check for checked in (discharged) confirmation code which is 205
        if (!checkInTransaction.getStatusCode().equalsIgnoreCase(SymphonyConstants.CHECKED_IN_SUCCESSFUL)) {
        	SymphonyConnectorException symphonyConnectorException = new SymphonyConnectorException(checkInTransaction.getStatusCode());
        	throw symphonyConnectorException;
        }
        return checkInTransaction;
    }
    

    private void validateItemId(ItemId itemId) throws SymphonyConnectorException {
    	this.validateItemIdIsPresent(itemId);
    	this.validateItemIdIsValid(itemId);
    }
    
  
    private ItemDescription retreiveItemDescription(CheckInTransactionResults checkInTrans) {
    	ItemDescription itemDescription = new ItemDescription();
    	itemDescription.setCallNumber(checkInTrans.getCallNumber());
    	return itemDescription;
    }
    
    private BibliographicDescription retrieveBiblioDescription(CheckInTransactionResults checkInTrans){
    	BibliographicDescription biblioDescription = new BibliographicDescription();
    	biblioDescription.setAuthor(checkInTrans.getAuthor());
    	biblioDescription.setTitle(checkInTrans.getTitle());
    	return biblioDescription;
    }
       
}
