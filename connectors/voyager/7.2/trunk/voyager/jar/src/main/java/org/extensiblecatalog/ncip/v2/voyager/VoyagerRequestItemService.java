package org.extensiblecatalog.ncip.v2.voyager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
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
import org.extensiblecatalog.ncip.v2.service.Version1GeneralProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1LookupUserProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1RequestItemProcessingError;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * This class implements the Request item service for the Voyager back-end connector.
 *
 * @author SharmilaR
 */
public class VoyagerRequestItemService implements RequestItemService {

    static Logger log = Logger.getLogger(VoyagerRequestItemService.class);
    VoyagerRemoteServiceManager voyagerSvcMgr;
    
    private String itemId;
    private String bibId;
    private String patronId;
    private String patronAgencyId;
    private String patronUbId;
    private String pickupCode = null;
    private String itemAgencyId = null;
    private BibliographicId bibliographicId;
    private Date lastInterestDate;

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(
            		new Properties()).getConfiguration();
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
        List<Problem> problems = new ArrayList<Problem>();
        RequestItemResponseData requestItemResponseData = new RequestItemResponseData();
        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        String host;
        
        bibliographicId = initData.getBibliographicId(0);
        bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        
        if (initData.getItemId(0) != null)
            itemId = initData.getItemId(0).getItemIdentifierValue();
        else
            itemId = null;

        String requestType = initData.getRequestType().getValue();
        String requestScopeType = initData.getRequestScopeType().getValue();

        if (initData.getPickupLocation() != null) {
        	pickupCode = initData.getPickupLocation().getValue();
        } else {
            problems = new ArrayList<Problem>();
            problems.addAll(ServiceHelper.generateProblems(
            		Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                    "PickupLocation", null, "Pickup Location missing in request"));
            requestItemResponseData.setProblems(problems);
            return requestItemResponseData;
        }
        
        if (initData.getPickupExpiryDate() != null) {
        	lastInterestDate = initData.getPickupExpiryDate().getTime();
        } else {
            problems.addAll(ServiceHelper.generateProblems(
            		Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                    "PickupExpiryDate", null, "Last Interest Date missing in request"));
            requestItemResponseData.setProblems(problems);
            return requestItemResponseData;
        }
        
        // UserId is present
        if (initData.getUserId() != null){
            log.info("User id is " + initData.getUserId().getUserIdentifierValue());
            patronId = initData.getUserId().getUserIdentifierValue();
            if (initData.getUserId().getAgencyId() != null) {
                patronAgencyId = initData.getUserId().getAgencyId().getValue();
            } else {
                log.debug("No To Agency ID found in the initiation header");
                patronAgencyId = (String) voyagerConfig.getProperty(
                		VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
            }
            // Retrieve the patron's database Id
            patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);

            boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(
            		VoyagerConstants.CONFIG_CONSORTIUM));
            if (consortialUse) {
                host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
            } else {
                host = (String) voyagerConfig.getProperty(
                		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
                itemAgencyId = patronAgencyId;
            }
            
            String url = host + "/vxws/patron/" + patronId + "?patron_homedb=" + patronUbId;
            Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(url);

            if (!patronInfoDoc.getRootElement().getChildText("reply-text").equalsIgnoreCase("ok")){
                problems.addAll(ServiceHelper.generateProblems(
                		Version1LookupUserProcessingError.UNKNOWN_USER,
                        "UserId/UserIdentifierValue", 
                        initData.getUserId().getUserIdentifierValue(), "Unknown User"));
                requestItemResponseData.setProblems(problems);
                return requestItemResponseData;
            }
        } else {  // User ID not present, assuming Authentication Input

            if (initData.getInitiationHeader() != null && 
            			initData.getInitiationHeader().getFromAgencyId() != null){
                patronAgencyId = 
                		initData.getInitiationHeader().getFromAgencyId().getAgencyId().getValue();
                patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
            } else {
                log.error("No From Agency ID found in the initiation header");
                patronAgencyId = (String) voyagerConfig.getProperty(
                		VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
                patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
            }

            boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(
            		VoyagerConstants.CONFIG_CONSORTIUM));
            if (consortialUse) {
                host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
            } else {
                host = (String) voyagerConfig.getProperty(
                		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
                itemAgencyId = patronAgencyId;
            }

            String authenticatedUserId = voyagerSvcMgr.authenticateUser(
            		initData.getAuthenticationInputs(), host);

            String username = "";
            for (AuthenticationInput a : initData.getAuthenticationInputs()) {
                if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Username")) {
                    username = a.getAuthenticationInputData();
                }  else if (
                		a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPUsername")) {
                    username = a.getAuthenticationInputData();
                }
             }

            patronId = authenticatedUserId;

            if (patronId == null) {
                problems.addAll(ServiceHelper.generateProblems(
                		Version1LookupUserProcessingError.UNKNOWN_USER,
                        "UserId/UserIdentifierValue", username, "Unknown User"));
                requestItemResponseData.setProblems(problems);
                return requestItemResponseData;
            }
        }

        UserId userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));

        String itemUbId = (String) voyagerConfig.getProperty(itemAgencyId);
        
        boolean success = false;
        boolean ubRequest = false;
        String toAgencyId = null;
        if (requestType.equalsIgnoreCase("Stack Retrieval")) {
        	if (initData.getInitiationHeader() != null && 
        			initData.getInitiationHeader().getToAgencyId() != null) {
        		toAgencyId = 
        				initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
        		if (itemAgencyId == null) {
        			ubRequest = false;
        		} else if (itemAgencyId.equalsIgnoreCase(toAgencyId)) {
        			ubRequest = false;
        		} else {
        			ubRequest = true;
        		} 			
        	}
        }

        log.debug("Request Type is " + requestType);
        try {
            if (requestType.equalsIgnoreCase("Stack Retrieval") && ubRequest == false) {
                success = processStackRetrieval(itemUbId);
            }
            else if (requestType.equalsIgnoreCase("Stack Retrieval") && ubRequest == true) {
                success = processUBRequest(toAgencyId);
            }
            else if (requestType.equalsIgnoreCase("Hold")) {
                success = processHold();
            }
            else if (requestType.equalsIgnoreCase("Recall")) {
                success = processRecall();
            }
        } catch (ILSException e) {
            problems = new ArrayList<Problem>();
            problems.addAll(ServiceHelper.generateProblems(
            		Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
                    null, null, "Unable to process request"));
            requestItemResponseData.setProblems(problems);
            return requestItemResponseData;
        }

        if (success) {
            requestItemResponseData.setItemId(initData.getItemId(0));
            requestItemResponseData.setUserId(userId);
            requestItemResponseData.setRequestType(initData.getRequestType());
            requestItemResponseData.setRequestScopeType(initData.getRequestScopeType());
        } else {
            problems.addAll(ServiceHelper.generateProblems(
            		Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
                    null, null, "Unable to process request"));
            requestItemResponseData.setProblems(problems);
        }

        return requestItemResponseData;
    }

    private boolean processHold() throws ILSException {

    	log.debug("Processing Hold Request");
        String host;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(
            		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("hold-request-parameters");
        Element pickupLocationElement = new Element("pickup-location");
        pickupLocationElement.setText(pickupCode);
        Element lastInterestDateElement = new Element("last-interest-date");
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        lastInterestDateElement.setText(df.format(lastInterestDate));
        Element comment = new Element("comment");
        comment.setText("Testing hold");
        Element dbkey = new Element("dbkey");
        dbkey.setText(patronUbId);

        root.addContent(pickupLocationElement);
        root.addContent(lastInterestDateElement);
        root.addContent(comment);
        root.addContent(dbkey);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" + itemId +
                    "/hold?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlOutput = xmlOutputter.outputString(doc);

        Document holdResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-hold/note");
            Element message = (Element) xpath.selectSingleNode(holdResponse);
            log.debug("The hold response is: " + message.getText());
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processHold");
            throw new ILSException(e);
        }
    }
    
    private boolean processStackRetrieval(String itemUbId) throws ILSException {

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));

        String host;

        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(
            		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("call-slip-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing stack retrieval");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        Element pickupLocation = new Element("pickup-location");
        pickupLocation.setText(pickupCode);
        root.addContent(comment);
        root.addContent(dbkey);
        root.addContent(pickupLocation);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" +
                    itemId + "/callslip?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlOutput = xmlOutputter.outputString(doc);
        log.debug("The callslip request is:\nurl=" + url + "\nXML=" + xmlOutput);

        Document callslipResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-call-slip/note");
            Element message = (Element) xpath.selectSingleNode(callslipResponse);
            log.debug("The callslip response is: " + message.getText());
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processStackRetrieval");
            throw new ILSException(e);
        }
    }
    
    private boolean processUBRequest(String toAgencyId) throws ILSException {

        String host = voyagerSvcMgr.getUrlFromAgencyId(itemAgencyId);
        String itemUbId = (String) voyagerConfig.getProperty(itemAgencyId);
        String pickupUbId = (String) voyagerConfig.getProperty(toAgencyId);

        Document doc = new Document();
        Element root = new Element("ub-request-parameters");
        
        Element pickupLibrary = new Element("pickup-library");
        pickupLibrary.setText(pickupUbId);
        
        Element pickupLocation = new Element("pickup-location");
        pickupLocation.setText(pickupCode);
        
        Element lastInterestDate = new Element("last-interest-date");
        lastInterestDate.setText("20140501");
        
        Element comment = new Element("comment");
        comment.setText("Testing UB Request in NCIP");
        
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);

        root.addContent(pickupLibrary);
        root.addContent(pickupLocation);
        root.addContent(lastInterestDate);
        root.addContent(comment);
        root.addContent(dbkey);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" +
                    itemId + "/ubrequest?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlOutput = xmlOutputter.outputString(doc);
        log.debug("The UB request is:\nurl=" + url + "\nXML=" + xmlOutput);

        Document ubResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-ubrequest/note");
            Element message = (Element) xpath.selectSingleNode(ubResponse);
            log.debug("The UB response is: " + message.getText());
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processStackRetrieval");
            throw new ILSException(e);
        }
    }
    
    private boolean processRecall() throws ILSException {

    	log.debug("Processing Recall Request");
    	
        String host;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(
            		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("recall-parameters");
        Element pickupLocationElement = new Element("pickup-location");
        pickupLocationElement.setText(pickupCode);
        Element lastInterestDateElement = new Element("last-interest-date");
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        lastInterestDateElement.setText(df.format(lastInterestDate));
        Element comment = new Element("comment");
        comment.setText("Testing recall request using NCIP");
        Element dbkey = new Element("dbkey");
        dbkey.setText(patronUbId);

        root.addContent(pickupLocationElement);
        root.addContent(lastInterestDateElement);
        root.addContent(comment);
        root.addContent(dbkey);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" + itemId +
                    "/recall?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlOutput = xmlOutputter.outputString(doc);

        Document holdResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);
        String holdResponseOutput = xmlOutputter.outputString(holdResponse);
        log.debug("The recall response is: " + holdResponseOutput);
        try {
            XPath xpath = XPath.newInstance("response/create-recall/note");
            Element message = (Element) xpath.selectSingleNode(holdResponse);
            log.debug("The recall response is: " + message.getText());
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processRecall");
            throw new ILSException(e);
        }
    }

    private boolean verifyRecallWasSuccess(String patronId, String patronAgencyId, String bibId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Connection conn = voyagerSvcMgr.openReadDbConnection(patronAgencyId);

        try {
            if (patronAgencyId.equalsIgnoreCase(
            		(String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY))) {
                pstmt = conn.prepareStatement("SELECT bib_id from hold_recall where hold_recall.patron_id = ?");
            }
            else {
                String dbuser = (String) voyagerConfig.getProperty(patronAgencyId + "dbuser");
                pstmt = conn.prepareStatement("SELECT bib_id from " + dbuser + 
                		".hold_recall where hold_recall.patron_id = ?");
            }
            pstmt.setString(1, bibId);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                return true;
            }
        } catch(SQLException e) {
            log.error("An error occurred while getting the bibliographic Id from the database.", e);
            return false;
        } catch(NullPointerException e) {
            log.error("An error connecting to the Oracle database");
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    log.error("An error occured closing the ResultSet.");
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch(SQLException e) {
                    log.error("An error occured closing the Statement.");
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    log.error("An error occured closing the Statement.");
                }
            }
        }
        return false;
    }
}
