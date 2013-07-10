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
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;

/**
 * This class implements the Request item service for the Voyager back-end connector. 
 * 
 * @author SharmilaR
 */
public class VoyagerRequestItemService implements RequestItemService {

	static Logger log = Logger.getLogger(VoyagerLookupItemSetService.class);
	
    private HttpClient client = new HttpClient(
    	    new MultiThreadedHttpConnectionManager());

	/**
     * Handles a NCIP RequestItem service by returning data from voyager.
     *
     * @param initData       the RequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RequestItemResponseData
     */
    @Override
    public RequestItemResponseData performService (RequestItemInitiationData initData,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {

    	RequestItemResponseData responseData = new RequestItemResponseData();
        VoyagerRemoteServiceManager voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        
        BibliographicId bibliographicId = initData.getBibliographicId();
        String bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        String itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
	
		boolean multipleTomcats = new Boolean(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_MULTIPLE_TOMCATS)).booleanValue();
		
        // Get the ubid of the patron
        String patronAgencyId;
        String patronUbId;
        String ubPrefix;
        
        if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
        	patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
        } else {
        	log.error("No To Agency ID found in the initiation header -- using configured default");
        	patronAgencyId = NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY);
            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
        }
        
        if (multipleTomcats)
        	ubPrefix = "/" + patronAgencyId.toLowerCase() + "/vxws";
        else
        	ubPrefix = "/vxws";	
        
        // Get patron Id and authenticate the user
        String authenticatedUserId;
        try {
        	authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs(), ubPrefix);	
        } catch (ILSException e) {
			Problem p = new Problem();
			p.setProblemElement("Username/Password");
			p.setProblemValue("The username or password was incorrect");
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    responseData.setProblems(problems);
		    return responseData;
        }
            
        String username = "";
        
        for (AuthenticationInput a : initData.getAuthenticationInputs()) {
			if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Username")) {
				username = a.getAuthenticationInputData();
			}  else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPUsername")) {
				username = a.getAuthenticationInputData();
			} 
		 }
        	        
        String patronId = authenticatedUserId;
        
        if (patronId == null) {
			Problem p = new Problem();
			p.setProblemElement("UserId");
			p.setProblemValue("Unknown User");
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    responseData.setProblems(problems);
		    return responseData;
		}

        UserId userId = new UserId();
        userId.setUserIdentifierValue(username);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));
        responseData.setUserId(userId);
    
        

        log.info("Patron ID: " + patronId);
        log.info("Patron Agency ID: " + patronAgencyId);
        log.info("Bibliographic Record ID: " + bibId);
        log.info("Bib Agency ID: " + itemAgencyId);
        log.info("Request Type: " + initData.getRequestType().getValue());
        log.info("Request Scope Type: " + initData.getRequestScopeType().getValue());
        
        
        /*
	    // Construct the xml to send to the web service
		Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Document requestItemXML = new Document();
        Element root = new Element("serviceParameters", serNs);
        Element parameters = new Element("parameters", serNs);
        Element bibId = new Element("parameter").setAttribute("key", "bibId");
        Element bibValue = new Element("value", serNs).addContent(bibliographicId);
        bibId.addContent(bibValue);
        
        Element eItemId = new Element("parameter").setAttribute("key", "itemId");
        Element itemIdValue = new Element("value", serNs).addContent(itemId);
        eItemId.addContent(itemIdValue);
        
        Element req = new Element("parameter").setAttribute("key", "requestCode");
        Element reqValue = new Element("value", serNs).addContent("HOLD");
        req.addContent(reqValue);
        
        Element dbCode = new Element("parameter").setAttribute("key", "bibDbCode");
        Element dbCodeValue = new Element("value", serNs).addContent("LOCAL");
        dbCode.addContent(dbCodeValue);
        
        parameters.addContent(bibId);
        parameters.addContent(itemId);
        parameters.addContent(req);
        
        // TODO : remove hard coded test user
        Element patron = new Element("patronIdentifier").setAttribute("lastName", "password")
        												 .setAttribute("patronHomeUbId", NCIPConfiguration.getProperty(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY)))
        												 .setAttribute("patronId" ,"98765432");
        Element authFactor = new Element("authFactor").setAttribute("type", "").addContent("I");
        patron.addContent(authFactor);
        
        root.addContent(parameters);
        root.addContent(patron);
        requestItemXML.addContent(root);
        
        Element parametersElement = new Element("parameters", serNs);
        root.addContent(parametersElement);
        
        XMLOutputter xmlOutP = new XMLOutputter();
        
        int statusCode = 0;
        PostMethod postRequestItem = null;
        // The URL of the web services server + service name
        String postUrl = NCIPConfiguration.getProperty("VoyagerVxwsUrl") + "/SendPatronRequestService";
        
        InputStream response = null;
        
    	try {
	        synchronized(client) {
	        	postRequestItem = new PostMethod(postUrl);
	        	postRequestItem.setRequestEntity(new StringRequestEntity(xmlOutP.outputString(requestItemXML)));
	        	statusCode = client.executeMethod(postRequestItem);
	        }
	        
	        if (statusCode == 200) {
	        	response = postRequestItem.getResponseBodyAsStream();
	        	responseData.setItemId(initData.getItemId());
	        
	        	UserId userId = new UserId();
		        userId.setUserIdentifierValue("98765432");
		        userId.setUserIdentifierType(new SchemeValuePair("Username"));
		        userId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY)));
	        	responseData.setUserId(userId);
	        	responseData.setRequestScopeType(initData.getRequestScopeType());
	        	responseData.setRequestType(initData.getRequestType());
	        } else {
				log.error("Cound not contact the vxws service.  Recieved HTTP status code: " + statusCode);
				Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Error contact the vxws web service."));
				p.setProblemDetail("HTTP status code " + statusCode);
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        }
    	}  catch (IOException e) {
			log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
			Problem p = new Problem();
			p.setProblemType(new SchemeValuePair("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit."));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		}
    	
    	log.info("Found response: " + response);
 */
    	return responseData;
    
    }
}
