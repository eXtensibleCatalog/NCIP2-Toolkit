package org.extensiblecatalog.ncip.v2.voyager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;
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
    List<Problem> problems = new ArrayList<Problem>();

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
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
        String itemId;
        String patronId;
        String patronAgencyId;
        String patronUbId;
        String host;

        BibliographicId bibliographicId = initData.getBibliographicId(0);
        String bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        if (initData.getItemId(0) != null)
            itemId = initData.getItemId(0).getItemIdentifierValue();
        else
            itemId = null;
        //String itemAgencyId = initData.getItemId(0).getAgencyId().getValue();
        String itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
        String requestType = initData.getRequestType().getValue();
        String requestScopeType = initData.getRequestScopeType().getValue();
        String itemUbId = (String) voyagerConfig.getProperty(itemAgencyId);
        String pickupLocation = initData.getPickupLocation().getValue();

        // UserId is present
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
                requestItemResponseData.setProblems(problems);
                return requestItemResponseData;
            }
        } else {  // User ID not present, assuming Authentication Input

            if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
                patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
                patronUbId = (String) voyagerConfig.getProperty(patronAgencyId);
            } else {
                log.error("No To Agency ID found in the initiation header");
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
                requestItemResponseData.setProblems(problems);
                return requestItemResponseData;
            }
        }

        UserId userId = new UserId();
        userId.setUserIdentifierValue(patronId);
        userId.setUserIdentifierType(new UserIdentifierType("Username"));
        userId.setAgencyId(new AgencyId(patronAgencyId));

        boolean success = false;

        try {
            if (requestType.equalsIgnoreCase("Stack Retrieval"))
                success = processStackRetrieval(patronId, patronUbId, patronAgencyId, bibId, itemId, itemUbId);
            else if (requestType.equalsIgnoreCase("Hold"))
                success = processHold(patronId, patronUbId, patronAgencyId, bibId, itemId, itemUbId, pickupLocation);
            else if (requestType.equalsIgnoreCase("Recall"))
                success = processRecall(patronId, patronUbId, patronAgencyId, bibId, itemId, itemUbId, pickupLocation);
            else if (requestType.equalsIgnoreCase("UB"))
                success = processUBRequest(patronId, patronUbId, patronAgencyId, bibId, itemId, itemUbId, pickupLocation);
        } catch (ILSException e) {
            List<Problem> problems = new ArrayList<Problem>();
            problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
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
            List<Problem> problems = new ArrayList<Problem>();
            problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
                    null, null, "Unable to process request"));
            requestItemResponseData.setProblems(problems);
        }

        return requestItemResponseData;
    }

    private boolean processRecall(String patronId, String patronUbId, String patronAgencyId,
            String bibId, String itemId, String itemUbId, String pickupLocation) throws ILSException {
        String host;

        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("recall-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing recall");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        Element pickupLocationElement = new Element("pickup-location");
        pickupLocationElement.setText(pickupLocation);
        Element lastInterestDate = new Element("last-interest-date");
        lastInterestDate.setText("20121230");
        root.addContent(pickupLocationElement);
        root.addContent(lastInterestDate);

        root.addContent(dbkey);

        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" + itemId +
                    "/recall?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutPutter = new XMLOutputter();
        String xmlOutput = xmlOutPutter.outputString(doc);
        log.info("The input XML to the server: " + xmlOutput);
        log.info("The url using: " + url);
        Document recallResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);
        xmlOutput = xmlOutPutter.outputString(recallResponse);
        log.info("The request from the server: " + xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-recall/note");
            Element message = (Element) xpath.selectSingleNode(recallResponse);
            if (message != null) {
	            if (message.getText().equalsIgnoreCase("Your request was successful.") && verifyRecallWasSuccess(patronId, patronAgencyId, bibId)) {
	            	log.debug("Recall was successful");
	                return true;
	            } else {
	            	log.debug("Recall was not successful");
	                return false;
	            }
            } else {
            	log.debug("Message is null.  Recall was not successful.");
            	return false;
            }
        } catch (JDOMException e) {
            log.error("XPath processing error in processStackRetrieval");
            throw new ILSException(e);
        }
    }

    private boolean verifyRecallWasSuccess(String patronId, String patronAgencyId, String bibId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Connection conn = voyagerSvcMgr.openReadDbConnection(patronAgencyId);

        try {
            if (patronAgencyId.equalsIgnoreCase((String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY))) {
                pstmt = conn.prepareStatement("SELECT bib_id from hold_recall where hold_recall.patron_id = ?");
            }
            else {
                String dbuser = (String) voyagerConfig.getProperty(patronAgencyId + "dbuser");
                pstmt = conn.prepareStatement("SELECT bib_id from " + dbuser + ".hold_recall where hold_recall.patron_id = ?");
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

    private boolean processHold(String patronId, String patronUbId, String patronAgencyId,
            String bibId, String itemId, String itemUbId, String pickupLocation) throws ILSException {

        String host;

        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("hold-title-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing hold");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        Element pickupLocationElement = new Element("pickup-location");
        pickupLocationElement.setText(pickupLocation);
        Element lastInterestDate = new Element("last-interest-date");
        lastInterestDate.setText("20121230");
        root.addContent(comment);
        root.addContent(dbkey);
        root.addContent(pickupLocationElement);
        root.addContent(lastInterestDate);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" + itemId +
                    "/hold?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutPutter = new XMLOutputter();
        String xmlOutput = xmlOutPutter.outputString(doc);

        Document holdResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-hold/note");
            Element message = (Element) xpath.selectSingleNode(holdResponse);
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processStackRetrieval");
            throw new ILSException(e);
        }
    }

    private boolean processStackRetrieval(String patronId, String patronUbId, String patronAgencyId,
            String bibId, String itemId, String itemUbId) throws ILSException {

        String host;
        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));

        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        Document doc = new Document();
        Element root = new Element("call-slip-parameters");
        Element comment = new Element("comment");
        comment.setText("Testing callslip");
        Element dbkey = new Element("dbkey");
        dbkey.setText(itemUbId);
        root.addContent(comment);
        root.addContent(dbkey);
        doc.addContent(root);

        String url = host + "/vxws/record/" + bibId + "/items/" +
                    itemId + "/callslip?patron=" + patronId + "&patron_homedb=" + patronUbId;

        XMLOutputter xmlOutPutter = new XMLOutputter();
        String xmlOutput = xmlOutPutter.outputString(doc);

        Document callslipResponse = voyagerSvcMgr.putWebServicesDoc(url, xmlOutput);

        try {
            XPath xpath = XPath.newInstance("response/create-call-slip/note");
            Element message = (Element) xpath.selectSingleNode(callslipResponse);
            if (message.getText().equalsIgnoreCase("Your request was successful."))
                return true;
            else
                return false;
        } catch (JDOMException e) {
            log.error("XPath processing error in processStackRetrieval");
            throw new ILSException(e);
        }

//        XPath xpath;
//        try {
//            xpath = XPath.newInstance("response/create-call-slip/note");
//            Element message = (Element) xpath.selectSingleNode(callslipResponse);
//            log.debug("The message is: " + message.getText());
//            if (message.getText().equalsIgnoreCase("Your request was successful.")) {
//                ItemId id = new ItemId();
//                id.setAgencyId(new AgencyId(itemAgencyId));
//                id.setItemIdentifierValue(itemId);
//                requestItemResponseData.setItemId(id);
//                requestItemResponseData.setRequestType(new RequestType(null, requestType));
//                requestItemResponseData.setRequestScopeType(new RequestScopeType(null, requestScopeType));
//                requestItemResponseData.setUserId(userId);
//            } else if (message.getText().equalsIgnoreCase("Could not send request.")) {
//                problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.UNKNOWN_ITEM,
//                        null, itemId, "Unknown Item"));
//                requestItemResponseData.setProblems(problems);
//                return requestItemResponseData;
//            } else if (message.getText().equalsIgnoreCase("Patron blocked")) {
//                problems.addAll(ServiceHelper.generateProblems(Version1RequestItemProcessingError.USER_INELIGIBLE_TO_REQUEST_THIS_ITEM,
//                        null, itemId, "Unable to request this item."));
//                requestItemResponseData.setProblems(problems);
//                return requestItemResponseData;
//            }
//
//        } catch (JDOMException e) {
//            problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
//                    null, null, e.getMessage()));
//            requestItemResponseData.setProblems(problems);
//            return requestItemResponseData;
//        } catch (NullPointerException npe) {
//            problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
//                    null, null, npe.getMessage()));
//            requestItemResponseData.setProblems(problems);
//            return requestItemResponseData;
//        }
//
//        return requestItemResponseData;
//    }
    }
    // patronId, patronUbId, patronAgencyId, bibId, itemId, itemUbId, pickupLocation
    private boolean processUBRequest(String patronId, String patronUbId, String patronAgencyId,
            String bibId, String itemId, String itemUbId, String pickupLocation) throws ILSException {

        String host;
        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));

        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }


        return true;
    }
}
