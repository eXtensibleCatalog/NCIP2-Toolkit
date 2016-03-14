/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.voyager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RenewItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RenewItemService;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1LookupUserProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1RenewItemProcessingError;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * This class implements the Renew Item service for the Voyager connector.
 */
public class VoyagerRenewItemService implements RenewItemService {

    static Logger log = Logger.getLogger(VoyagerRenewItemService.class);
    RenewItemResponseData renewItemResponseData;
    VoyagerRemoteServiceManager voyagerSvcMgr;
    String patronId;
    String patronAgencyId;
    String patronUbId;
    String itemAgencyId;
    String itemUbId;
    String host;
    String itemId;
    
    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) 
            		new ConnectorConfigurationFactory(new Properties()).getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
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
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager)
        throws ServiceException {

        List<Problem> problems = new ArrayList<Problem>();
        renewItemResponseData = new RenewItemResponseData();
        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;

        itemId = initData.getItemId().getItemIdentifierValue();

        // Get the ubid of the patron
        if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
            patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
            patronUbId = voyagerConfig.getProperty(patronAgencyId);
        } else {
            log.debug("No To Agency ID found in the header");
            patronAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
            patronUbId = voyagerConfig.getProperty(patronAgencyId);
        }

        // Get the ubid of the item
        if (initData.getItemId().getAgencyId() != null){
            itemAgencyId = initData.getItemId().getAgencyId().getValue();
            itemUbId = voyagerConfig.getProperty(itemAgencyId);
        }
        else {
            log.debug("No item Agency ID found in the header");
            itemAgencyId = voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
            itemUbId = voyagerConfig.getProperty(itemAgencyId);
        }

        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs(), host);
        if (authenticatedUserId == null) {
            problems.addAll(ServiceHelper.generateProblems(Version1LookupUserProcessingError.UNKNOWN_USER,
                    "UserId/UserIdentifierValue", initData.getUserId().getUserIdentifierValue(), "Unknown User"));
            renewItemResponseData.setProblems(problems);
            return renewItemResponseData;
        }

        log.info("The authenticated User id is " + authenticatedUserId);

        patronId = authenticatedUserId;

        // Set up the response data
        UserId userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));

        ItemId id = new ItemId();
        id.setItemIdentifierValue(itemId);
        id.setAgencyId(new AgencyId(itemAgencyId));

        // Check if we can renew the item using the circulationActions service
        if (!canRenew()) {
        	problems.addAll(ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE,
                    "ItemId", itemId, "Item is not renewable."));
        	renewItemResponseData.setProblems(problems);
        	return renewItemResponseData;
        }
        
        log.debug("Using patron id: " + patronId + " in URL");

        // %7C is the escape sequence for | (pipe)
        String url = host + "/vxws/patron/" + patronId + "/circulationActions/loans/" +
                itemUbId + "%7C" + itemId + "?patron_homedb=" + patronUbId;

        Document doc = voyagerSvcMgr.postWebServicesDoc(url);

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
                log.debug("Renewal was a success");
                renewItemResponseData.setUserId(userId);
                renewItemResponseData.setItemId(id);
                renewItemResponseData.setDateDue(voyagerSvcMgr.stringDateToGC(dueDate));
            } else if (renewalStatus.equalsIgnoreCase("Not Found") && statusText.equalsIgnoreCase("Renewed")) {
                problems.addAll(ServiceHelper.generateProblems(Version1RenewItemProcessingError.MAXIMUM_RENEWALS_EXCEEDED,
                        "ItemId", itemId, "Item cannot be renewed again."));
            }

        } catch (NullPointerException npe) {  // TODO: Distinguish between not found and not charged to patron
            problems.addAll(ServiceHelper.generateProblems(Version1RenewItemProcessingError.ITEM_NOT_RENEWABLE,
                    "ItemId", itemId, "Item not found or not charged to patron."));
        }

        renewItemResponseData.setProblems(problems);
        return renewItemResponseData;
    }

	private boolean canRenew() {
		List<Element> loanElements;
		
		String url = host + "/vxws/patron/" + patronId + "/circulationActions/loans" +
                "?patron_homedb=" + patronUbId;

        Document doc = voyagerSvcMgr.getWebServicesDoc(url);
        
        try {
            XPath xpath = XPath.newInstance("//loan");
            loanElements = xpath.selectNodes(doc);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            return false;
        }
        
        for (Element loan : loanElements) {
        	String href = loan.getAttributeValue("href");
        	String canRenew = loan.getAttributeValue("canRenew");
        	if (href.contains("|" + itemId + "?patron_homedb")  && canRenew.equals("Y")) {
        		return true;
        	} else {
        		log.debug("Did not find match for itemId: " + itemId + " in " + href);
        		continue;
        	}
        }
        
		return false;
	}
}


