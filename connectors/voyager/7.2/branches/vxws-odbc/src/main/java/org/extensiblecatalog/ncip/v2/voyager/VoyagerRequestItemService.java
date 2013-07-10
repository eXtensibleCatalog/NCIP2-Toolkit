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
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * This class implements the Request item service for the Voyager back-end connector.
 *
 * @author SharmilaR
 */
public class VoyagerRequestItemService implements RequestItemService {

	static Logger log = Logger.getLogger(VoyagerRequestItemService.class);
	String patronId;
    String patronAgencyId;
    String patronUbId;
    String ubPrefix;

    BibliographicId bibliographicId;
    String bibId;
    String itemId;
    String itemAgencyId;
    String requestType;
    String requestScopeType;
	String itemUbId;
	UserId userId;
	VoyagerRemoteServiceManager voyagerSvcMgr;
    List<Problem> problems = new ArrayList<Problem>();

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory().getConfiguration();
            //voyagerConfig = (VoyagerConfiguration)ConnectorConfigurationFactory.getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

	/**
     * Handles a NCIP RequestItem service by returning data from voyager.
     *
     * @param initData       the RequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RequestItemResponseData
     */
    @Override
    public RequestItemResponseData performService (RequestItemInitiationData initData,
    											 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {

    	RequestItemResponseData requestItemResponseData = new RequestItemResponseData();
        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;

        bibliographicId = initData.getBibliographicId(0);
        bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        if (initData.getItemId(0) != null)
        	itemId = initData.getItemId(0).getItemIdentifierValue();
        else
        	itemId = null;
        itemAgencyId = initData.getItemId(0).getAgencyId().getValue();
        requestType = initData.getRequestType().getValue();
        requestScopeType = initData.getRequestScopeType().getValue();
    	itemUbId = (String) voyagerConfig.getProperty(itemAgencyId);

        boolean multipleTomcats = (Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_MULTIPLE_TOMCATS)));

    	if (initData.getUserId() != null){
        	log.info("User id is " + initData.getUserId().getUserIdentifierValue());
        	patronId = initData.getUserId().getUserIdentifierValue();
        	if (initData.getUserId().getAgencyId() != null) {
        		patronAgencyId = initData.getUserId().getAgencyId().getValue();
        	}
        	else {
        		log.debug("No To Agency ID found in the initiation header");
            	patronAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
        	}
        	// Retrieve the patron's database Id
        	patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);

        	if (multipleTomcats)
            	ubPrefix = "/" + patronAgencyId.toLowerCase() + "/vxws";
            else
            	ubPrefix = "/vxws";

        	String serviceUrl = ubPrefix + "/patron/" + patronId + "?patron_homedb=" + patronUbId;
		    Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);

        	if (!patronInfoDoc.getRootElement().getChildText("reply-text").equalsIgnoreCase("ok")){
			    problems.addAll(ServiceHelper.generateProblems(Version1LookupUserProcessingError.UNKNOWN_USER,
	                    "UserId/UserIdentifierValue", initData.getUserId().getUserIdentifierValue(), "Unknown User"));
		        requestItemResponseData.setProblems(problems);
		        return requestItemResponseData;
        	}
        } else {

	        if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
	        	patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
	            patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
	        } else {
	        	log.error("No To Agency ID found in the initiation header");
	        	patronAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
	            patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
	        }

	        if (multipleTomcats)
	        	ubPrefix = "/" + patronAgencyId.toLowerCase() + "/vxws";
	        else
	        	ubPrefix = "/vxws";

	        String authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs(), ubPrefix);

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
			    requestItemResponseData.setProblems(problems);
			    return requestItemResponseData;
			}
        }

        userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));

      /*  log.info("Patron ID: " + patronId);
        log.info("Patron Agency ID: " + patronAgencyId);
        log.info("Bibliographic Record ID: " + bibId);
        log.info("Item ID: " + itemId);
        log.info("Item Agency ID: " + itemAgencyId);
        log.info("Request Type: " + initData.getRequestType().getValue());
        log.info("Request Scope Type: " + initData.getRequestScopeType().getValue()); */

        if (requestType.equalsIgnoreCase("Stack Retrieval"))
        	requestItemResponseData = processStackRetrieval();
        else if (requestType.equalsIgnoreCase("Hold"))
        	requestItemResponseData = processHold();
        else if (requestType.equalsIgnoreCase("Recall"))
        	requestItemResponseData = processRecall();

    	return requestItemResponseData;
    }

	private RequestItemResponseData processRecall() {
		// TODO Auto-generated method stub
		return null;
	}

	private RequestItemResponseData processHold() {
        Document doc = new Document();
        Element root = new Element("hold-title-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing hold");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        Element pickupLocation = new Element("pickup-location");
        pickupLocation.setText("");
        Element lastInterestDate = new Element("last-interest-date");
        lastInterestDate.setText("");
        root.addContent(comment);
        root.addContent(dbkey);
        root.addContent(pickupLocation);
        root.addContent(lastInterestDate);
        doc.addContent(root);

        String serviceUrl;
        if (itemId != null) {
        	serviceUrl = ubPrefix + "/record/" + bibId + "/items/" +
            	itemId + "/hold?patron=" + patronId + "&patron_homedb=" + patronUbId;
        } else {
        	serviceUrl = ubPrefix + "/record/" + bibId + "/hold?patron=" +
        			patronId + "&patron_homedb=" + patronUbId;
        }


        log.debug("Using service Url: " + serviceUrl);

        XMLOutputter xmlOutPutter = new XMLOutputter();
        String xmlOutPut = xmlOutPutter.outputString(doc);
        Document callslipResponse = voyagerSvcMgr.putWebServicesDoc(serviceUrl, xmlOutPut);


        return null;
	}

	private RequestItemResponseData processStackRetrieval() {

		RequestItemResponseData requestItemResponseData = new RequestItemResponseData();
        Document doc = new Document();
        Element root = new Element("call-slip-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing callslip");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        root.addContent(comment);
        root.addContent(dbkey);
        doc.addContent(root);
        String serviceUrl = ubPrefix + "/record/" + bibId + "/items/" +
            	itemId + "/callslip?patron=" + patronId + "&patron_homedb=" + patronUbId;

        log.debug("Using service Url: " + serviceUrl);

        XMLOutputter xmlOutPutter = new XMLOutputter();
        String xmlOutPut = xmlOutPutter.outputString(doc);
        Document callslipResponse = voyagerSvcMgr.putWebServicesDoc(serviceUrl, xmlOutPut);

        log.debug("The output from request item is: " + xmlOutPutter.outputString(callslipResponse));

		XPath xpath;
		try {
			xpath = XPath.newInstance("response/create-call-slip/note");
			Element message = (Element) xpath.selectSingleNode(callslipResponse);
			log.debug("The message is: " + message.getText());
			if (message.getText().equalsIgnoreCase("Your request was successful.")) {
				ItemId id = new ItemId();
				id.setAgencyId(new AgencyId(itemAgencyId));
				id.setItemIdentifierValue(itemId);
				requestItemResponseData.setItemId(id);
				requestItemResponseData.setRequestType(new RequestType(null, requestType));
				requestItemResponseData.setRequestScopeType(new RequestScopeType(null, requestScopeType));
		        requestItemResponseData.setUserId(userId);
			} else if (message.getText().equalsIgnoreCase("Could not send request.")) {
			    problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM,
	                    null, itemId, "Unknown Item"));
		        requestItemResponseData.setProblems(problems);
		        return requestItemResponseData;
			} else if (message.getText().equalsIgnoreCase("Patron blocked")) {
			    problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
	                    null, itemId, "Unable to request this item."));
		        requestItemResponseData.setProblems(problems);
		        return requestItemResponseData;
			}

		} catch (JDOMException e) {
		    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                    null, null, e.getMessage()));
	        requestItemResponseData.setProblems(problems);
	        return requestItemResponseData;
		} catch (NullPointerException npe) {
		    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                    null, null, npe.getMessage()));
	        requestItemResponseData.setProblems(problems);
	        return requestItemResponseData;
		}

		return requestItemResponseData;
	}
}
