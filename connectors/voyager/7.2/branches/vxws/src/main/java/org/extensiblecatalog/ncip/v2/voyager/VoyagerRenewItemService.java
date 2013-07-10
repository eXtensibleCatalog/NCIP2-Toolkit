/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.voyager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * This class implements the Renew Item service for the Voyager connector. 
 */
public class VoyagerRenewItemService implements RenewItemService {
	
	static Logger log = Logger.getLogger(VoyagerRenewItemService.class);
    private HttpClient client = new HttpClient(
    	    new MultiThreadedHttpConnectionManager());

    Namespace serNs;
    Namespace myacNs;
    String itemId = null;
    String institutionId = null;
    String patronId = null;
    String patronAgencyId = null;
	String patronUbId = null;
    String patronSurname = null;
    String itemAgencyId = null;
    String itemUbId = null;
  
    /**
     * Construct a VoyagerRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public VoyagerRenewItemService() {
    }

    /**
     * Handles a NCIP LookupItem service by returning data from voyager.
     *
     * @param initData       the LookupItemInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupItemResponseData
     */
    @Override
    public RenewItemResponseData performService (RenewItemInitiationData initData,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {

        RenewItemResponseData renewItemResponseData = new RenewItemResponseData();
        VoyagerRemoteServiceManager voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        
        boolean multipleTomcats = new Boolean(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_MULTIPLE_TOMCATS)).booleanValue();
        itemId = initData.getItemId().getItemIdentifierValue();        
        String patronId;
        
        // Get the ubid of the patron
        if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
        	patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
        } else {
        	log.error("No To Agency ID found in the header");
        	patronAgencyId = NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY);
            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
        }
        
        // Get the ubid of the item
        if (initData.getItemId().getAgencyId() != null){
        	itemAgencyId = initData.getItemId().getAgencyId().getValue();
        	itemUbId = NCIPConfiguration.getProperty(itemAgencyId);
        }
        else {
        	log.error("No item Agency ID found in the header");
        	itemAgencyId = NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY);
            itemUbId = NCIPConfiguration.getProperty(itemAgencyId);
        }
        
        String ubPrefix = "";
        if (multipleTomcats)
        	ubPrefix = "/" + patronAgencyId.toLowerCase() + "/vxws";
        else
        	ubPrefix = "/vxws";
        
        try {
	        String authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs(), ubPrefix);
	        
	        log.info("The authenticated User id is " + authenticatedUserId);
	        
	        String username = "";
	        for (AuthenticationInput a : initData.getAuthenticationInputs()) {
				if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Username")) {
					username = a.getAuthenticationInputData();
				}  else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPUsername")) {
					username = a.getAuthenticationInputData();
				} 
			 }
	        
	        
	        patronId = authenticatedUserId;
	        
	        if (patronId == null) {
				Problem p = new Problem();
				p.setProblemElement("UserId");
				p.setProblemValue("Unknown User");
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
			    renewItemResponseData.setProblems(problems);
			    return renewItemResponseData;
			
	        }        
	    }
        catch (ILSException e) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing error"));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    renewItemResponseData.setProblems(problems);
		    return renewItemResponseData;
		}
   
        // Set up the response data
        UserId userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));
        

        ItemId id = new ItemId();
        id.setItemIdentifierValue(itemId);
        id.setAgencyId(new AgencyId(itemAgencyId));
        

        log.info("Using patron id: " + patronId + " in URL");
        
        // %7C is the escape sequence for | 
        String serviceUrl = ubPrefix + "/patron/" + patronId + "/circulationActions/loans/" +
        	itemUbId + "%7C" + itemId + "?patron_homedb=" + patronUbId;
        
        log.info("Using service Url: " + serviceUrl);
        
        Document doc = voyagerSvcMgr.postWebServicesDoc(serviceUrl);
        
        try {
        	String renewalStatus = 
        		doc.getRootElement().getChild("renewal").getChild("institution")
        		.getChild("loan").getChildText("renewalStatus");
        	String statusText = 
        		doc.getRootElement().getChild("renewal").getChild("institution")
        		.getChild("loan").getChildText("statusText");
        	String dueDate = 
        		doc.getRootElement().getChild("renewal").getChild("institution")
        		.getChild("loan").getChildText("dueDate");
        	
        	if (renewalStatus.equalsIgnoreCase("Success")) {
        		log.info("Renewal was a success");
        		renewItemResponseData.setUserId(userId);
        		renewItemResponseData.setItemId(id);
        		renewItemResponseData.setDateDue(voyagerSvcMgr.stringDateToGC(dueDate));
        	} else if (renewalStatus.equalsIgnoreCase("Not Found") && statusText.equalsIgnoreCase("Renewed")) {
        		Problem p = new Problem();
    			p.setProblemElement("Renewal");
    			p.setProblemValue("Item cannot be renewed again.");
    			List<Problem> problems = new ArrayList<Problem>();
    			problems.add(p);
    		    renewItemResponseData.setProblems(problems);
        	}
        
        } catch (NullPointerException npe) {
        	Problem p = new Problem();
			p.setProblemElement("Renewal");
			p.setProblemValue("Item not found or not charged to patron.");
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    renewItemResponseData.setProblems(problems);
        }
        
        return renewItemResponseData;
    }
}


    