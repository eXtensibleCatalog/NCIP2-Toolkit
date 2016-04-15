package org.extensiblecatalog.ncip.v2.voyager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * Implements Lookup User service for voyager connector
 *
 * @author SharmilaR
 *
 */
public class VoyagerLookupUserService implements LookupUserService {

    /** Logger */
    static Logger log = Logger.getLogger(VoyagerLookupUserService.class);
    VoyagerRemoteServiceManager voyagerSvcMgr;
    String patronAgencyId;

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
            //voyagerConfig = (VoyagerConfiguration)ConnectorConfigurationFactory.getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Handles a NCIP LookupUser service by returning data from voyager.
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService (LookupUserInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager)
        throws ServiceException {

        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        LookupUserResponseData lookupUserResponseData = new LookupUserResponseData();

        String patronUbId = "";
        String patronId;
        String host;
        List<Problem> problems = new ArrayList<Problem>();

        log.info("Performing LookupUser service");
        long startTime = System.nanoTime();

        try {
            // UserId is present
            if (initData.getUserId() != null){
                log.info("User id is " + initData.getUserId().getUserIdentifierValue());
                patronId = initData.getUserId().getUserIdentifierValue();
                if (initData.getUserId().getAgencyId() != null) {
                    patronAgencyId = initData.getUserId().getAgencyId().getValue();
                }
                else {
                    log.debug("No To Agency ID found in the initiation header");
                    patronAgencyId = voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
                }

                if (patronAgencyId == null) {
                    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                            "AgencyId", null, "Default Agency Id not configured"));
                    lookupUserResponseData.setProblems(problems);
                    return lookupUserResponseData;
                }

                // Retrieve the patron's database Id
                patronUbId = voyagerConfig.getProperty(patronAgencyId);

                boolean consortialUse = Boolean.parseBoolean(voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
                if (consortialUse) {
                    host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                } else {
                    host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
                }

                String url = host + "/vxws/patron/" + patronId + "?patron_homedb=" + patronUbId;
                Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(url);

                if (!patronInfoDoc.getRootElement().getChildText("reply-text").equalsIgnoreCase("ok")){
                    problems.addAll(ServiceHelper.generateProblems(Version1LookupUserProcessingError.UNKNOWN_USER,
                            "UserId/UserIdentifierValue", initData.getUserId().getUserIdentifierValue(), "Unknown User"));
                    lookupUserResponseData.setProblems(problems);
                    return lookupUserResponseData;
                }
            } else {  // User ID not present, assuming Authentication Input

                if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
                    patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
                    patronUbId = voyagerConfig.getProperty(patronAgencyId);
                } else {
                    log.error("No From Agency ID found in the initiation header");
                    patronAgencyId =voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);
                    if (patronAgencyId == null) {
                        problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                                "AgencyId", null, "Default Agency Id not configured"));
                        lookupUserResponseData.setProblems(problems);
                        return lookupUserResponseData;
                    }
                    patronUbId = voyagerConfig.getProperty(patronAgencyId);
                }

                boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
                if (consortialUse) {
                    host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                } else {
                    host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
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
                    lookupUserResponseData.setProblems(problems);
                    return lookupUserResponseData;
                }
            }

            UserId userId = new UserId();
            userId.setUserIdentifierValue(patronId);
            userId.setUserIdentifierType(new UserIdentifierType("Username"));
            userId.setAgencyId(new AgencyId(patronAgencyId));
            lookupUserResponseData.setUserId(userId);

            if (initData.getUserAddressInformationDesired() || 
            		initData.getNameInformationDesired()) {
                UserOptionalFields userOptionalFields = new UserOptionalFields();

                boolean consortialUse = Boolean.parseBoolean(
                		voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
                if (consortialUse) {
                    host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                } else {
                    host = voyagerConfig.getProperty(
                    		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
                }
                // Retrieve data from the patronInformation/address service
                String url = host + "/vxws/patron/" + patronId + 
                		"/patronInformation/address?patron_homedb=" + patronUbId;
                Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(url);

                if (initData.getUserAddressInformationDesired()) {
                    List<UserAddressInformation> userAddressInformations = 
                    		getAddress(patronInfoDoc);
                    if (userAddressInformations != null) {
                        log.debug("Setting useraddressinformations in useroptionalfields");
                        userOptionalFields.setUserAddressInformations(userAddressInformations);
                    }
                }

                if (initData.getNameInformationDesired()) {
                    NameInformation nameInfo = getName(patronInfoDoc);
                    if (nameInfo != null)
                        userOptionalFields.setNameInformation(nameInfo);
                }


                lookupUserResponseData.setUserOptionalFields(userOptionalFields);
            }

            // Get the fines information if necessary
            if (initData.getUserFiscalAccountDesired()) {
                List<UserFiscalAccount> accounts = new ArrayList<UserFiscalAccount>();
                UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
                userFiscalAccount = getFiscalAccount(patronId, patronUbId);
                if (userFiscalAccount != null)
                    accounts.add(userFiscalAccount);
                if (accounts.size() > 0) {
                    lookupUserResponseData.setUserFiscalAccounts(accounts);
                }
            }

            // Get the requests information if necessary
            if (initData.getRequestedItemsDesired()) {

                List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
                List<RequestedItem> holdItems = new ArrayList<RequestedItem>();
                List<RequestedItem> callslipItems = new ArrayList<RequestedItem>();
                List<RequestedItem> recallItems = new ArrayList<RequestedItem>();

                callslipItems.addAll(
                		getRequestedItemsForCallslipRequests(patronId, patronUbId, patronAgencyId));
                holdItems.addAll(
                		getRequestedItemsForHoldRequests(patronId, patronUbId, patronAgencyId));
                recallItems.addAll(
                		getRequestedItemsForRecallRequests(patronId, patronUbId, patronAgencyId));

                requestedItems.addAll(callslipItems);
                requestedItems.addAll(holdItems);
                requestedItems.addAll(recallItems);
                lookupUserResponseData.setRequestedItems(requestedItems);

                // Populate RequestedItemsCount
                List<RequestedItemsCount> requestedItemsCounts = 
                		new ArrayList<RequestedItemsCount>();

                if (holdItems.size() > 0) {
                    RequestedItemsCount itemsHoldCount = new RequestedItemsCount();
                    itemsHoldCount.setRequestType(Version1RequestType.HOLD);
                    itemsHoldCount.setRequestedItemCountValue(BigDecimal.valueOf(holdItems.size()));
                    requestedItemsCounts.add(itemsHoldCount);
                }
                if (callslipItems.size() > 0) {
                    RequestedItemsCount itemsCallslipCount = new RequestedItemsCount();
                    itemsCallslipCount.setRequestType(XcRequestType.CALL_SLIP);
                    itemsCallslipCount.setRequestedItemCountValue(
                    		BigDecimal.valueOf(callslipItems.size()));
                    requestedItemsCounts.add(itemsCallslipCount);
                }
                if (recallItems.size() > 0) {
                    RequestedItemsCount itemsRecallCount = new RequestedItemsCount();
                    itemsRecallCount.setRequestType(XcRequestType.RECALL);
                    itemsRecallCount.setRequestedItemCountValue(
                    		BigDecimal.valueOf(recallItems.size()));
                    requestedItemsCounts.add(itemsRecallCount);
                }

                if (requestedItemsCounts.size() > 0) {
                    lookupUserResponseData.setRequestedItemsCounts(requestedItemsCounts);
                }
            }

            // Get the loaned items information if necessary
            if (initData.getLoanedItemsDesired() ) {
                boolean consortialUse = Boolean.parseBoolean(
                		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
                if (consortialUse) {
                    host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
                } else {
                    host = voyagerConfig.getProperty(
                    		VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
                }
                String url = host + "/vxws/patron/" + patronId + 
                		"/circulationActions/loans?patron_homedb=" + patronUbId + "&view=brief";
                Document loanedItemsDoc = voyagerSvcMgr.getWebServicesDoc(url);

                List<LoanedItem> items = getLoanedItems(loanedItemsDoc, patronAgencyId);
                lookupUserResponseData.setLoanedItems(items);

                // Populate LoanedItemsCount
                if (items != null && items.size() > 0) {
                    lookupUserResponseData.setLoanedItemsCounts(
                    		getLoanedItemsCount(loanedItemsDoc));
                }
            }
        } catch (ILSException e) {
            Problem p = new Problem();
            p.setProblemType(new ProblemType("Processing error"));
            p.setProblemDetail(e.getMessage());
            problems.add(p);
            lookupUserResponseData.setProblems(problems);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        log.info("Time to process LookupUser: " + duration / 1000000000 + " seconds.");
        return lookupUserResponseData;
    }

    private Collection<RequestedItem> getRequestedItemsForRecallRequests(
            String patronId, String patronUbId, String patronAgencyId) throws ILSException {

        String host;
        List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
        List<Element> institutionElements;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String url = host + "/vxws/patron/" + patronId +
            "/circulationActions/requests/holds?patron_homedb=" + patronUbId + "&view=full";

        Document doc = voyagerSvcMgr.getWebServicesDoc(url);

        try {
            XPath xpath = XPath.newInstance("/response/holds/institution");
            institutionElements = xpath.selectNodes(doc);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        for (Element institution : institutionElements) {
            List<Element> holds = institution.getChildren("hold");
            for (Element hold : holds) {
                Element requestItem = hold.getChild("requestItem");

                // if holdType != R  (aka Recall), skip
                if (!requestItem.getChildText("holdType").equalsIgnoreCase("R"))
                    continue;

                RequestedItem requestedItem = new RequestedItem();

                ItemId itemId = new ItemId();

                String institutionAgencyId = requestItem.getChildText("dbKey");
                institutionAgencyId = voyagerConfig.getUbidFromAgency(institutionAgencyId);

                itemId.setAgencyId(new AgencyId(institutionAgencyId));
                itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
                requestedItem.setItemId(itemId);

                List<BibliographicRecordId> bibRecordIdList = 
                		new ArrayList<BibliographicRecordId>();
                BibliographicRecordId bibRecordId = new BibliographicRecordId();
                BibliographicDescription bibDesc = new BibliographicDescription();
                String bibId = voyagerSvcMgr.getBibIdForItemId(
                		requestItem.getChildText("itemId"), institutionAgencyId);
                if (bibId == null) {
                    throw new ILSException("Could not retrieve Bib Id from Item Id");
                }
                bibRecordId.setAgencyId(new AgencyId(institutionAgencyId));
                bibRecordId.setBibliographicRecordIdentifier(bibId);
                bibRecordIdList.add(bibRecordId);
                bibDesc.setBibliographicRecordIds(bibRecordIdList);
                requestedItem.setBibliographicDescription(bibDesc);

                String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
                GregorianCalendar expireDate = new GregorianCalendar(
                        Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                        Integer.parseInt(dateComponents[2]));

                requestedItem.setPickupExpiryDate(expireDate);

                RequestId requestId = new RequestId();
                requestId.setRequestIdentifierValue(requestItem.getChildText("holdRecallId"));
                requestedItem.setRequestId(requestId);
                
                if (!requestItem.getChildText("queuePosition").equalsIgnoreCase("")) {
                    requestedItem.setHoldQueuePosition(
                    		new BigDecimal(requestItem.getChildText("queuePosition")));
                } else {
                	requestedItem.setHoldQueuePosition(new BigDecimal("0"));
                }

                log.info("Pickup location: " + requestItem.getChildText("pickupLocation"));

                if (!requestItem.getChildText("pickupLocation").equalsIgnoreCase("")) {
                    requestedItem.setPickupLocation(
                    		new PickupLocation(requestItem.getChildText("pickupLocation")));
                }

                GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
                requestedItem.setDatePlaced(nullDate);

                requestedItem.setRequestType(XcRequestType.RECALL);
                requestedItem.setRequestStatusType(
                		new RequestStatusType(requestItem.getChildText("statusText")));
                requestedItem.setTitle(requestItem.getChildText("itemTitle"));
                requestedItems.add(requestedItem);
            }
        }

        return requestedItems;
    }

    private List<LoanedItemsCount> getLoanedItemsCount(Document loanedItemsDoc) {

        List<LoanedItemsCount> loans = new ArrayList<LoanedItemsCount>();

           if (loanedItemsDoc.getRootElement().getChild("loans").getChildren("institution")!=null){
            List<Element> institutions = 
            		loanedItemsDoc.getRootElement().getChild("loans").getChildren("institution");
            for (Element institution : institutions) {
                if (institution.getChildren("loan") != null) {
                    List<Element> loansList = institution.getChildren("loan");
                    for (Element loan : loansList) {
                        LoanedItemsCount loanedItemsCount = new LoanedItemsCount();
                        try {
                            loanedItemsCount.setCirculationStatus(
                            		XcCirculationStatus.find(
                            				XcCirculationStatus.XC_CIRCULATION_STATUS, 
                            				loan.getChildTextTrim("statusText")));
                        } catch (ServiceException e) {
                            log.debug("Service exception getting circ status");
                            return null;
                        }
                        loanedItemsCount.setLoanedItemCountValue(new BigDecimal(1));
                        loans.add(loanedItemsCount);
                    }
                }
            }
        }
        return loans;
    }

    private UserFiscalAccount getFiscalAccount(String patronId, String patronUbId) 
    		throws ILSException {

        UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
        List<AccountDetails> accountDetails = new ArrayList<AccountDetails>();
        List<Element> institutionElements;
        String host;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String url = host + "/vxws/patron/" + patronId +
            "/circulationActions/debt/fines?patron_homedb=" + patronUbId + "&view=full";
        Document doc = voyagerSvcMgr.getWebServicesDoc(url);

        BigDecimal totalFines = new BigDecimal("0");
        String currencyCode = "";
        int minorUnit = 0;

        try {
            XPath xpath = XPath.newInstance("/response/fines/institution");
            institutionElements = xpath.selectNodes(doc);
            xpath = XPath.newInstance("//finesum");
            Element fineSum = (Element) xpath.selectSingleNode(doc);
            if (fineSum != null)
            	currencyCode = fineSum.getText().split(" ")[0];
            else
            	return null;
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        log.debug("Found currencyCode: " + currencyCode);

        for (Element institution : institutionElements) {
        	String totalFineAmount =  institution.getChild("balance").getChildText("finesum").split(" ")[1];
        	totalFineAmount = totalFineAmount.split("\\.")[0];
        	BigDecimal totalFinesForInstitution = new BigDecimal(totalFineAmount).multiply(new BigDecimal(100));
        	totalFines = totalFines.add(totalFinesForInstitution);
        	
            List<Element> fines = institution.getChildren("fine");
            for (Element fine : fines) {
                log.debug("Getting here in fines");
                String fineAmount = fine.getChildText("amount").split(" ")[1];

                minorUnit = fineAmount.split("\\.")[1].length();

                fineAmount = fineAmount.split("\\.")[0];

                AccountDetails details = new AccountDetails();

                String[] dateComponents = fine.getChildText("fineDate").split("-");
                GregorianCalendar fineDate = new GregorianCalendar(
                        Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                        Integer.parseInt(dateComponents[2]));

                details.setAccrualDate(fineDate);

                FiscalTransactionInformation fiscalTransactionInformation = 
                		new FiscalTransactionInformation();
                Amount amount = new Amount();
                amount.setCurrencyCode(new CurrencyCode(currencyCode, minorUnit));
                amount.setMonetaryValue(new BigDecimal(fineAmount).multiply(new BigDecimal(100)));
                fiscalTransactionInformation.setAmount(amount);
                // TODO - temporarily set to 'Payment'
                fiscalTransactionInformation.setFiscalActionType(Version1FiscalActionType.PAYMENT);

                if (!fine.getChildText("itemTitle").equalsIgnoreCase("")){
                    ItemDetails itemDetails = new ItemDetails();

                    BibliographicDescription description = new BibliographicDescription();
                    description.setTitle(fine.getChildText("itemTitle"));
                                        
                    // Set itemId to "".  vxws does not provide it yet toolkit expects it
                    ItemId itemId = new ItemId();
                    itemId.setItemIdentifierValue("");
                    
                    // It is still useful to know to which AgencyId this item belongs
                    String institutionAgencyId = fine.getChildText("dbKey");
                    institutionAgencyId = voyagerConfig.getUbidFromAgency(institutionAgencyId);
                    itemId.setAgencyId(new AgencyId(institutionAgencyId));

                    itemDetails.setItemId(itemId);

                    itemDetails.setBibliographicDescription(description);
                    fiscalTransactionInformation.setItemDetails(itemDetails);
                }

                if (!fine.getChildText("fineType").equalsIgnoreCase("")){
                    fiscalTransactionInformation.setFiscalTransactionType(
                    		new FiscalTransactionType(fine.getChildText("fineType")));
                }

                details.setFiscalTransactionInformation(fiscalTransactionInformation);

                accountDetails.add(details);
            }
        }
        if (totalFines.compareTo(BigDecimal.ZERO) > 0) {
            AccountBalance accountBalance = new AccountBalance();
            accountBalance.setMonetaryValue(totalFines);
            accountBalance.setCurrencyCode(new CurrencyCode(currencyCode, minorUnit));
            userFiscalAccount.setAccountBalance(accountBalance);

            if (log.isDebugEnabled())
                log.debug("The user owes " + accountBalance.getMonetaryValue()
                    + " in fines with currency code "
                    + accountBalance.getCurrencyCode());
        }

        userFiscalAccount.setAccountDetails(accountDetails);

        return userFiscalAccount;
    }

    private List<RequestedItem> getRequestedItemsForHoldRequests(String patronId, String patronUbId,
    		String patronAgencyId) throws ILSException {

        List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
        List<Element> institutionElements;
        String host;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String url = host + "/vxws/patron/" + patronId +
            "/circulationActions/requests/holds?patron_homedb=" + patronUbId + "&view=full";

        Document doc = voyagerSvcMgr.getWebServicesDoc(url);

        try {
            XPath xpath = XPath.newInstance("/response/holds/institution");
            institutionElements = xpath.selectNodes(doc);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        for (Element institution : institutionElements) {
            List<Element> holds = institution.getChildren("hold");
            for (Element hold : holds) {
                Element requestItem = hold.getChild("requestItem");
                // if holdType != H  (aka Hold), skip
                if (!requestItem.getChildText("holdType").equalsIgnoreCase("H"))
                    continue;

                RequestedItem requestedItem = new RequestedItem();
                ItemId itemId = new ItemId();

                String institutionAgencyId = requestItem.getChildText("dbKey");
                institutionAgencyId = voyagerConfig.getUbidFromAgency(institutionAgencyId);

                itemId.setAgencyId(new AgencyId(institutionAgencyId));
                itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
                requestedItem.setItemId(itemId);

                List<BibliographicRecordId> bibRecordIdList = 
                		new ArrayList<BibliographicRecordId>();
                BibliographicRecordId bibRecordId = new BibliographicRecordId();
                BibliographicDescription bibDesc = new BibliographicDescription();
                String bibId = voyagerSvcMgr.getBibIdForItemId(
                		requestItem.getChildText("itemId"), institutionAgencyId);
                if (bibId == null) {
                    throw new ILSException("Could not retrieve Bib Id from Item Id");
                }
                bibRecordId.setAgencyId(new AgencyId(institutionAgencyId));
                bibRecordId.setBibliographicRecordIdentifier(bibId);
                bibRecordIdList.add(bibRecordId);
                bibDesc.setBibliographicRecordIds(bibRecordIdList);
                requestedItem.setBibliographicDescription(bibDesc);

                String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
                GregorianCalendar expireDate = new GregorianCalendar(
                        Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                        Integer.parseInt(dateComponents[2]));

                requestedItem.setPickupExpiryDate(expireDate);

                RequestId requestId = new RequestId();
                requestId.setRequestIdentifierValue(requestItem.getChildText("holdRecallId"));
                requestedItem.setRequestId(requestId);
                
                if (!requestItem.getChildText("queuePosition").equalsIgnoreCase("")) {
                    requestedItem.setHoldQueuePosition(
                    		new BigDecimal(requestItem.getChildText("queuePosition")));
                } else {
                	requestedItem.setHoldQueuePosition(new BigDecimal("0"));
                }

                log.info("Pickup location: " + requestItem.getChildText("pickupLocation"));

                if (!requestItem.getChildText("pickupLocation").equalsIgnoreCase("")) {
                    requestedItem.setPickupLocation(
                    		new PickupLocation(requestItem.getChildText("pickupLocation")));
                }

                GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
                requestedItem.setDatePlaced(nullDate);

                requestedItem.setRequestType(XcRequestType.HOLD);
                requestedItem.setRequestStatusType(
                		new RequestStatusType(requestItem.getChildText("statusText")));
                requestedItem.setTitle(requestItem.getChildText("itemTitle"));
                requestedItems.add(requestedItem);
            }
        }

        return requestedItems;
    }

    private List<RequestedItem> getRequestedItemsForCallslipRequests(String patronId, 
    		String patronUbId, String patronAgencyId) throws ILSException {

        List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
        List<Element> institutionElements;
        String host;

        boolean consortialUse = Boolean.parseBoolean(
        		(String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(patronAgencyId);
        } else {
            host = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String url = host + "/vxws/patron/" + patronId +
            "/circulationActions/requests/callslips?patron_homedb=" + patronUbId + "&view=full";

        Document doc = voyagerSvcMgr.getWebServicesDoc(url);

        try {
            XPath xpath = XPath.newInstance("/response/callslips/institution");
            institutionElements = xpath.selectNodes(doc);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        for (Element institution : institutionElements) {
            List<Element> callslips = institution.getChildren("callslip");
            for (Element callslip : callslips) {
                Element requestItem = callslip.getChild("requestItem");

                RequestedItem requestedItem = new RequestedItem();

                ItemId itemId = new ItemId();

                String institutionAgencyId = requestItem.getChildText("dbKey");
                institutionAgencyId = voyagerConfig.getUbidFromAgency(institutionAgencyId);

                itemId.setAgencyId(new AgencyId(institutionAgencyId));
                itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
                requestedItem.setItemId(itemId);

                List<BibliographicRecordId> bibRecordIdList = 
                		new ArrayList<BibliographicRecordId>();
                BibliographicRecordId bibRecordId = new BibliographicRecordId();
                BibliographicDescription bibDesc = new BibliographicDescription();
                String bibId = voyagerSvcMgr.getBibIdForItemId(
                		requestItem.getChildText("itemId"), institutionAgencyId);
                if (bibId == null) {
                    throw new ILSException("Could not retrieve Bib Id from Item Id"); 
                }
                bibRecordId.setAgencyId(new AgencyId(institutionAgencyId));
                bibRecordId.setBibliographicRecordIdentifier(bibId);
                bibRecordIdList.add(bibRecordId);
                bibDesc.setBibliographicRecordIds(bibRecordIdList);
                requestedItem.setBibliographicDescription(bibDesc);

                String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
                GregorianCalendar expireDate = new GregorianCalendar(
                        Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                        Integer.parseInt(dateComponents[2]));

                requestedItem.setPickupExpiryDate(expireDate);

                RequestId requestId = new RequestId();
                requestId.setRequestIdentifierValue(requestItem.getChildText("holdRecallId"));
                requestedItem.setRequestId(requestId);
                
                if (!requestItem.getChildText("queuePosition").equalsIgnoreCase("")) {
                    requestedItem.setHoldQueuePosition(
                    		new BigDecimal(requestItem.getChildText("queuePosition")));
                } else {
                	requestedItem.setHoldQueuePosition(new BigDecimal("0"));
                }
                
                String pickupLocation = requestItem.getChildText("pickupLocation");
                log.info("Pickup location: " + pickupLocation);

                if (pickupLocation != null && !pickupLocation.equalsIgnoreCase("")) {
                    requestedItem.setPickupLocation(
                    		new PickupLocation(pickupLocation));
                }

                GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
                requestedItem.setDatePlaced(nullDate);

                requestedItem.setRequestType(XcRequestType.CALL_SLIP);
                requestedItem.setRequestStatusType(
                		new RequestStatusType(requestItem.getChildText("statusText")));
                requestedItem.setTitle(requestItem.getChildText("itemTitle"));
                requestedItems.add(requestedItem);
            }
        }

        return requestedItems;
    }

    private List<LoanedItem> getLoanedItems(Document doc, String patronAgencyId) 
    		throws ILSException {
        List<Element> institutionElements;
        List<LoanedItem> loanedItems = new ArrayList<LoanedItem>();

        try {
            XPath xpath = XPath.newInstance("/response/loans/institution");
            institutionElements = xpath.selectNodes(doc);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        for (Element institution : institutionElements) {
            List<Element> loans = institution.getChildren("loan");
            for (Element loan : loans) {
                LoanedItem loanedItem = new LoanedItem();

                String dueDate = loan.getChildText("dueDate");
                String[] dateComponents = dueDate.substring(0, 10).split("-");
                String[] timeComponents = dueDate.substring(11).split(":");
                GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                        Integer.parseInt(dateComponents[2]),
                        Integer.parseInt(timeComponents[0]),
                        Integer.parseInt(timeComponents[1]));
                loanedItem.setDateDue(gc);

                loanedItem.setReminderLevel(new BigDecimal(1));

                ItemId itemId = new ItemId();

                String institutionAgencyId = loan.getChildText("dbKey");
                institutionAgencyId = voyagerConfig.getUbidFromAgency(institutionAgencyId);

                itemId.setAgencyId(new AgencyId(institutionAgencyId));
                itemId.setItemIdentifierValue(loan.getChildText("itemId"));

                loanedItem.setItemId(itemId);

                loanedItem.setTitle(loan.getChildText("title"));

                String bibId = voyagerSvcMgr.getBibIdForItemId(
                		loan.getChildText("itemId"), patronAgencyId);
                if (bibId == null) {
                    throw new ILSException("Could not retrieve Bib Id from Item Id");
                }

                BibliographicDescription bibliographicDescription = new BibliographicDescription();

                List<BibliographicRecordId> bibliographicRecordIds = 
                		new ArrayList<BibliographicRecordId>();
                BibliographicRecordId bibRecordId = new BibliographicRecordId();
                bibRecordId.setBibliographicRecordIdentifier(bibId);
                bibRecordId.setAgencyId(new AgencyId(institutionAgencyId));
                bibliographicRecordIds.add(bibRecordId);
                bibliographicDescription.setBibliographicRecordIds(bibliographicRecordIds);
                loanedItem.setBibliographicDescription(bibliographicDescription);

                Amount amount = new Amount();
                amount.setCurrencyCode(Version1CurrencyCode.USD);
                amount.setMonetaryValue(new BigDecimal(000));
                loanedItem.setAmount(amount);

                loanedItems.add(loanedItem);
            }
        }
        return loanedItems;
    }

    private NameInformation getName(Document doc) throws ILSException {

        String firstName = "", middleName = "", lastName = "";
        try {
            XPath xpath = XPath.newInstance("/response/address/name");
            Element nameElement = (Element) xpath.selectSingleNode(doc);
            firstName = nameElement.getChildTextTrim("firstName");
            middleName = nameElement.getChildTextTrim("middleName");
            lastName = nameElement.getChildTextTrim("lastName");
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        StringBuffer name = new StringBuffer();
        StringBuffer initials = new StringBuffer();
        name.append(firstName);
        log.debug("In LUgetName First name is: " + firstName);
        initials.append(firstName.charAt(0));
        StructuredPersonalUserName structuredUserName = new StructuredPersonalUserName();
        structuredUserName.setGivenName(firstName);

        if (!middleName.equalsIgnoreCase("")){
            name.append(" " + middleName);
            initials.append(middleName.charAt(0));
        }

        name.append(" " + lastName);
        initials.append(lastName.charAt(0));
        structuredUserName.setSurname(lastName);
        structuredUserName.setInitials(initials.toString());

        PersonalNameInformation p  = new PersonalNameInformation();
        p.setUnstructuredPersonalUserName(name.toString());
        p.setStructuredPersonalUserName(structuredUserName);

        NameInformation n = new NameInformation();
        n.setPersonalNameInformation(p);
        return n;
    }

    private List<UserAddressInformation> getAddress(Document doc) throws ILSException {
        UserAddressInformation userAddressInformation = null;
        List<UserAddressInformation> userAddressInformations =
        		new ArrayList<UserAddressInformation>();
        try {
            XPath xpath = XPath.newInstance("/response/address/permanentAddress");
            Element addressElement = (Element) xpath.selectSingleNode(doc);
            if (addressElement != null) {
                // Has permanent address
                userAddressInformation = new UserAddressInformation();
                StringBuilder address = new StringBuilder();
                if(!addressElement.getChildTextTrim("addressLine1").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("addressLine1"));
                if(!addressElement.getChildTextTrim("addressLine2").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("addressLine2"));
                if(!addressElement.getChildTextTrim("addressLine3").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("addressLine3"));
                if(!addressElement.getChildTextTrim("addressLine4").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("addressLine4"));
                if(!addressElement.getChildTextTrim("addressLine5").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("addressLine5"));
                if(!addressElement.getChildTextTrim("city").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("city"));
                if(!addressElement.getChildTextTrim("stateProvince").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("stateProvince"));
                if(!addressElement.getChildTextTrim("zipPostal").equalsIgnoreCase(""))
                    address.append("\n").append(addressElement.getChildTextTrim("zipPostal"));
                address.append("\n");

                PhysicalAddress physicalAddress = new PhysicalAddress();
                physicalAddress.setPhysicalAddressType(Version1PhysicalAddressType.POSTAL_ADDRESS);
                userAddressInformation.setUserAddressRoleType(
                		new UserAddressRoleType(VoyagerConstants.LOCATION_TYPE_PERMANENT));
                UnstructuredAddress unstructuredAddress = new UnstructuredAddress();
                unstructuredAddress.setUnstructuredAddressData(address.toString());
                unstructuredAddress.setUnstructuredAddressType(
                		Version1UnstructuredAddressType.NEWLINE_DELIMITED_TEXT);
                physicalAddress.setUnstructuredAddress(unstructuredAddress);
                userAddressInformation.setPhysicalAddress(physicalAddress);
                userAddressInformations.add(userAddressInformation);
            }

            xpath = XPath.newInstance("/response/address/emailAddress/address");
            Element emailAddressElement = (Element) xpath.selectSingleNode(doc);
            if (emailAddressElement != null) {
                userAddressInformation = new UserAddressInformation();
                String emailAddress = emailAddressElement.getText();
                log.debug("Found email address: " + emailAddress);
                ElectronicAddress electronicAddress = new ElectronicAddress();
                electronicAddress.setElectronicAddressData(emailAddress);
                electronicAddress.setElectronicAddressType(
                		new ElectronicAddressType(VoyagerConstants.ADDRESS_EMAIL));
                userAddressInformation.setElectronicAddress(electronicAddress);
                userAddressInformation.setUserAddressRoleType(
                		new UserAddressRoleType(VoyagerConstants.ADDRESS_EMAIL));
                userAddressInformations.add(userAddressInformation);
            }
        } catch (NullPointerException e) {
            log.error("Did not find Address info in vxws response");
            throw new ILSException(e);
        } catch (JDOMException e) {
            log.error("XPath processing error");
            throw new ILSException(e);
        }

        return userAddressInformations;
    }
}
