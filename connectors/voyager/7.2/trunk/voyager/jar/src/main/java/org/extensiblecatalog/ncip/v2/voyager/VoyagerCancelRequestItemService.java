package org.extensiblecatalog.ncip.v2.voyager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RequestItemService;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1LookupUserProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1RequestItemProcessingError;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class VoyagerCancelRequestItemService implements CancelRequestItemService {

    static Logger log = Logger.getLogger(VoyagerRequestItemService.class);
    VoyagerRemoteServiceManager voyagerSvcMgr;

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
	
	@Override
	public CancelRequestItemResponseData performService(
			CancelRequestItemInitiationData initData,
			ServiceContext serviceContext, RemoteServiceManager serviceManager)
			//throws ServiceException {
			{	
		
		
		log.debug("Starting CancelRequestItem Service.");
	    List<Problem> problems = new ArrayList<Problem>();
        CancelRequestItemResponseData cancelRequestItemResponseData = new CancelRequestItemResponseData();
        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        String itemId;
        String patronId;
        String patronAgencyId;
        String patronUbId;
        String host;
        String pickupCode = null;
        String itemAgencyId = null;

        
        //String requestType = initData.getRequestType().getValue();
       
        // UserId is present
        if (initData.getUserId() != null){
            log.info("User id is " + initData.getUserId().getUserIdentifierValue());
            patronId = initData.getUserId().getUserIdentifierValue();
            if (initData.getUserId().getAgencyId() != null) {
                patronAgencyId = initData.getUserId().getAgencyId().getValue();
            } else {
                log.debug("No To Agency ID found in the initiation header");
                patronAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
            }
            // Retrieve the patron's database Id
            patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);

            boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
            if (consortialUse) {
                host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
            } else {
                host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
            }
            
            String url = host + "/vxws/patron/" + patronId + "?patron_homedb=" + patronUbId;
            Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(url);

            if (!patronInfoDoc.getRootElement().getChildText("reply-text").equalsIgnoreCase("ok")){
                problems.addAll(ServiceHelper.generateProblems(Version1LookupUserProcessingError.UNKNOWN_USER,
                        "UserId/UserIdentifierValue", initData.getUserId().getUserIdentifierValue(), "Unknown User"));
                cancelRequestItemResponseData.setProblems(problems);
                return cancelRequestItemResponseData;
            }
        } else {  // User ID not present, assuming Authentication Input

            if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getFromAgencyId() != null){
                patronAgencyId = initData.getInitiationHeader().getFromAgencyId().getAgencyId().getValue();
                patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
            } else {
                log.error("No From Agency ID found in the initiation header");
                patronAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
                patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
            }

            boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
            if (consortialUse) {
                host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
            } else {
                host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
            }

            String authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs(), host);

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
                problems.addAll(ServiceHelper.generateProblems(Version1LookupUserProcessingError.UNKNOWN_USER,
                        "UserId/UserIdentifierValue", username, "Unknown User"));
                cancelRequestItemResponseData.setProblems(problems);
                return cancelRequestItemResponseData;
            }
        }

        UserId userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));
		
		String requestId = initData.getRequestId().getRequestIdentifierValue();
		String requestType = initData.getRequestType().getValue();
		
        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = 
            	(String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }
		
        String truncatedUbId = patronUbId.substring(2);
        
        if (requestType.equalsIgnoreCase("Hold")) {

			String url = host + "/vxws/patron/" + patronId + "/circulationActions/requests/holds/" +
					truncatedUbId + "%7C" + requestId + "?patron_homedb=" + patronUbId;
			
			Document cancelRequestResponse = voyagerSvcMgr.deleteWebServicesDoc(url);
	
	        try {
	            XPath xpath = XPath.newInstance("response/reply-text");
	            Element message = (Element) xpath.selectSingleNode(cancelRequestResponse);
	            log.debug("The cancel hold request response is: " + message.getText());
	            if (message.getText().equalsIgnoreCase("ok")) {
	            	cancelRequestItemResponseData.setUserId(userId);
	            }
	            else {
	            	problems = new ArrayList<Problem>();
	                problems.addAll(ServiceHelper.generateProblems(
	                		Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
	                        null, null, "Unable to process cancel request"));
	                cancelRequestItemResponseData.setProblems(problems);
	                return cancelRequestItemResponseData;
	        	}
	        } catch (JDOMException e) {
	            log.error("XPath processing error in cancel hold request");
	        }
        } else if (requestType.equalsIgnoreCase("Stack Retrieval")) {
	        
			String url = host + "/vxws/patron/" + patronId + 
					"/circulationActions/requests/callslips/" + truncatedUbId + "%7C" +
					requestId + "?patron_homedb=" + patronUbId;
			
			Document cancelRequestResponse = voyagerSvcMgr.deleteWebServicesDoc(url);
	
	        try {
	            XPath xpath = XPath.newInstance("response/reply-text");
	            Element message = (Element) xpath.selectSingleNode(cancelRequestResponse);
	            log.debug("The cancel callslip request response is: " + message.getText());
	            if (message.getText().equalsIgnoreCase("ok")) {
	            	cancelRequestItemResponseData.setUserId(userId);
	            }
	            else {
	            	problems = new ArrayList<Problem>();
	                problems.addAll(ServiceHelper.generateProblems(
	                		Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
	                        null, null, "Unable to process cancel request"));
	                cancelRequestItemResponseData.setProblems(problems);
	                return cancelRequestItemResponseData;
	        	}
	        } catch (JDOMException e) {
	            log.error("XPath processing error in cancel callslip request");
	        }
        }
        
        return cancelRequestItemResponseData;
	}

}



