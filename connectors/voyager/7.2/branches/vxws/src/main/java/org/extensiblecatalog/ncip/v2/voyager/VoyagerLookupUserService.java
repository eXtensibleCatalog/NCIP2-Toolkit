package org.extensiblecatalog.ncip.v2.voyager;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

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
	String ubPrefix;
	
    /**
     * Handles a NCIP LookupUser service by returning data from voyager.
     *
     * @param initData       the LookupUserInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupUserResponseData
     */
    @Override
    public LookupUserResponseData performService (LookupUserInitiationData initData,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {
        
        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
		LookupUserResponseData lookupUserResponseData = new LookupUserResponseData();
		
		boolean multipleTomcats = new Boolean(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_MULTIPLE_TOMCATS)).booleanValue();
				
        // Get the ubid of the patron
        String patronAgencyId = "";
        String patronUbId = "";
        String patronId;
        
        try { 
	    
        	if (initData.getUserId() != null){
	        	log.info("User id is " + initData.getUserId().getUserIdentifierValue());
	        	patronId = initData.getUserId().getUserIdentifierValue();
	        	if (initData.getUserId().getAgencyId() != null) {
	        		patronAgencyId = initData.getUserId().getAgencyId().getValue();
	        	}
	        	else {
	        		log.error("No To Agency ID found in the initiation header");
	            	patronAgencyId = NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY);
	        	}
	        	patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
	        	
	        	if (multipleTomcats)
	            	ubPrefix = "/" + patronAgencyId.toLowerCase() + "/vxws";
	            else
	            	ubPrefix = "/vxws";
	        	
	        	String serviceUrl = ubPrefix + "/patron/" + patronId + "?patron_homedb=" + patronUbId;
			    Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
			    
	        	if (!patronInfoDoc.getRootElement().getChildText("reply-text").equalsIgnoreCase("ok")){
	        		Problem p = new Problem();
					p.setProblemElement("UserId");
					p.setProblemValue("Unknown User");
					List<Problem> problems = new ArrayList<Problem>();
					problems.add(p);
				    lookupUserResponseData.setProblems(problems);
				    return lookupUserResponseData;
	        	}
	        		
	            UserId userId = new UserId();
		        userId.setUserIdentifierValue(patronId);
		        userId.setUserIdentifierType(new UserIdentifierType("Username"));
		        userId.setAgencyId(new AgencyId(patronAgencyId));
		        lookupUserResponseData.setUserId(userId);
	        	
	        } else {
	        
		        if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getToAgencyId() != null){
		        	patronAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId().getValue();
		            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
		        } else {
		        	log.error("No To Agency ID found in the initiation header");
		        	patronAgencyId = NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY);
		            patronUbId = NCIPConfiguration.getProperty(patronAgencyId);
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
					Problem p = new Problem();
					p.setProblemElement("UserId");
					p.setProblemValue("Unknown User");
					List<Problem> problems = new ArrayList<Problem>();
					problems.add(p);
				    lookupUserResponseData.setProblems(problems);
				    return lookupUserResponseData;
				}
		
		        UserId userId = new UserId();
		        userId.setUserIdentifierValue(username);
		        userId.setUserIdentifierType(new UserIdentifierType("Username"));
		        userId.setAgencyId(new AgencyId(patronAgencyId));
		        lookupUserResponseData.setUserId(userId);
	        }
        	
	        if (initData.getUserAddressInformationDesired() || initData.getNameInformationDesired()) {
				UserOptionalFields userOptionalFields = new UserOptionalFields();
 
		    	String serviceUrl = ubPrefix + "/patron/" + patronId + "/patronInformation/address?patron_homedb=" + patronUbId;
			    Document patronInfoDoc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
				
				if (initData.getUserAddressInformationDesired()) {
					List<UserAddressInformation> userAddressInformations = getAddress(patronInfoDoc);
					if (userAddressInformations != null) {
						log.info("Setting useraddressinformations in useroptionalfields");
						userOptionalFields.setUserAddressInformations(userAddressInformations);
					}
				}
					
				if (initData.getNameInformationDesired()) {
					userOptionalFields.setNameInformation(getName(patronInfoDoc));
				}
				
				if (userOptionalFields != null) {
					lookupUserResponseData.setUserOptionalFields(userOptionalFields);
				}
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
				callslipItems.addAll(getRequestedItemsForCallslipRequests(patronId, patronUbId, patronAgencyId));
				holdItems.addAll(getRequestedItemsForHoldRequests(patronId, patronUbId, patronAgencyId));
				recallItems.addAll(getRequestedItemsForRecallRequests(patronId, patronUbId, patronAgencyId));				
				
				requestedItems.addAll(callslipItems);
				requestedItems.addAll(holdItems);
				requestedItems.addAll(recallItems);
				lookupUserResponseData.setRequestedItems(requestedItems);
				
				// Populate RequestedItemsCount
				List<RequestedItemsCount> requestedItemsCounts = new ArrayList<RequestedItemsCount>();
				
				if (holdItems.size() > 0) {
					RequestedItemsCount itemsHoldCount = new RequestedItemsCount();
					itemsHoldCount.setRequestType(Version1RequestType.HOLD);
					itemsHoldCount.setRequestedItemCountValue(BigDecimal.valueOf(holdItems.size()));
					requestedItemsCounts.add(itemsHoldCount);
				}
				if (callslipItems.size() > 0) {
					RequestedItemsCount itemsCallslipCount = new RequestedItemsCount();
					itemsCallslipCount.setRequestType(XcRequestType.CALL_SLIP);
					itemsCallslipCount.setRequestedItemCountValue(BigDecimal.valueOf(callslipItems.size()));
					requestedItemsCounts.add(itemsCallslipCount);
				}
				if (recallItems.size() > 0) {
					RequestedItemsCount itemsRecallCount = new RequestedItemsCount();
					itemsRecallCount.setRequestType(XcRequestType.RECALL);
					itemsRecallCount.setRequestedItemCountValue(BigDecimal.valueOf(recallItems.size()));
					requestedItemsCounts.add(itemsRecallCount);
				}
				
				if (requestedItemsCounts.size() > 0) {
					lookupUserResponseData.setRequestedItemsCounts(requestedItemsCounts);
				}
				
			}
	
			// Get the loaned items information if necessary
			if (initData.getLoanedItemsDesired() ) {
				String serviceUrl = ubPrefix + "/patron/" + patronId + 
	        		"/circulationActions/loans?patron_homedb=" + patronUbId + "&view=brief";
	        
				Document loanedItemsDoc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
	    	
				List<LoanedItem> items = getLoanedItems(loanedItemsDoc, patronAgencyId);
				lookupUserResponseData.setLoanedItems(items);

				// Populate LoanedItemsCount
				if (items != null && items.size() > 0) {
					List<String> itemIds = new ArrayList<String>(); 
					for(LoanedItem item : items) {
						itemIds.add(item.getItemId().getItemIdentifierValue());
					}
					lookupUserResponseData.setLoanedItemsCounts(getLoanedItemsCount(loanedItemsDoc));
				}
			}
		} catch (ILSException e) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing error"));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    lookupUserResponseData.setProblems(problems);
		}
		
		log.debug("lookupUserResponseData ="+lookupUserResponseData);
		return lookupUserResponseData;

    }
    
    private Collection<? extends RequestedItem> getRequestedItemsForRecallRequests(
			String patronId, String patronUbId, String patronAgencyId) {
    	
    	List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
    	
    	String serviceUrl = ubPrefix + "/patron/" + patronId + 
    		"/circulationActions/requests/holds?patron_homedb=" + patronUbId + "&view=full";
    	
    	log.info ("Getting requested items for recall requests");
    	Document doc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
    	try {
        	if (doc.getRootElement().getChild("holds").getChildren("institution") != null){
        		List<Element> institutions = doc.getRootElement().getChild("holds").getChildren("institution");
        		for (Element institution : institutions) {
        			if (institution.getChildren("hold") != null){
        				List<Element> holds = institution.getChildren("hold");
        				for (Element hold : holds){
        					
        					Element requestItem = hold.getChild("requestItem");
        					
        					// if holdType != R  (aka Recall), skip
        					if (!requestItem.getChildText("holdType").equalsIgnoreCase("R"))
        						continue;
        					
        					RequestedItem requestedItem = new RequestedItem();
        					
        					ItemId itemId = new ItemId();
        					
        					String institutionAgencyId = institution.getAttributeValue("id");
	    					if (institutionAgencyId.equalsIgnoreCase("LOCAL"))
	    						institutionAgencyId = patronAgencyId;
	    					else
	    						institutionAgencyId = institutionAgencyId.substring(1);
	
        					itemId.setAgencyId(new AgencyId(institutionAgencyId));
        					itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
        					requestedItem.setItemId(itemId);
        					
        					String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
        					GregorianCalendar expireDate = new GregorianCalendar(
        							Integer.parseInt(dateComponents[0]),
        							Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0 
        							Integer.parseInt(dateComponents[2]));
        					
        					requestedItem.setPickupExpiryDate(expireDate);
        					
        					if (!requestItem.getChildText("queuePosition").equalsIgnoreCase(""))
        						requestedItem.setHoldQueuePosition(new BigDecimal(requestItem.getChildText("queuePosition")));
        					
        					log.info("Pickup location: " + requestItem.getChildText("pickupLocation"));
        					
        					if (!requestItem.getChildText("pickupLocation").equalsIgnoreCase(""))
        						requestedItem.setPickupLocation(new PickupLocation(requestItem.getChildText("pickupLocation")));
        					
							GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
							requestedItem.setDatePlaced(nullDate);        					
        					
        					requestedItem.setRequestType(XcRequestType.RECALL);
        					requestedItem.setRequestStatusType(new RequestStatusType(requestItem.getChildText("statusText")));
        					requestedItem.setTitle(requestItem.getChildText("itemTitle"));
        					requestedItems.add(requestedItem);
        				}
        			}
        		}
        	
        		return requestedItems;
        	}
    	} catch (NullPointerException npe) {
    		return requestedItems;
    	}
    		
		return requestedItems;
	}

	private List<LoanedItemsCount> getLoanedItemsCount(Document loanedItemsDoc) {
    	
    	List<LoanedItemsCount> loans = new ArrayList<LoanedItemsCount>();
    	
       	if (loanedItemsDoc.getRootElement().getChild("loans").getChildren("institution") != null){
    		List<Element> institutions = loanedItemsDoc.getRootElement().getChild("loans").getChildren("institution");
    		for (Element institution : institutions) {
    			if (institution.getChildren("loan") != null){
    				List<Element> loansList = institution.getChildren("loan");
    				for (Element loan : loansList) {			
	    				LoanedItemsCount loanedItemsCount = new LoanedItemsCount();
	    				try {
							loanedItemsCount.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, loan.getChildTextTrim("statusText")));
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

	private UserFiscalAccount getFiscalAccount(String patronId, String patronUbId) {
    	
    	UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
	    List<AccountDetails> accountDetails = new ArrayList<AccountDetails>();
    	String serviceUrl = ubPrefix + "/patron/" + patronId + 
			"/circulationActions/debt/fines?patron_homedb=" + patronUbId + "&view=full";
	    Document fiscalDoc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
	    
	    // Retrieve total fine amount
	    BigDecimal totalFines = new BigDecimal("0");
	    String currencyCode;
	    int minorUnit = 0;
	    
    	try {
			List<Element> institutions = fiscalDoc.getRootElement().getChild("fines").getChildren("institution");

			Element currencyCodeElement = fiscalDoc.getRootElement().getChild("fines").getChild("institution").getChild("fine").getChild("amount");
			currencyCode = currencyCodeElement.getText().split(" ")[0];
			
			for (Element institution : institutions) {
				List<Element> fines = institution.getChildren("fine");
				for (Element fine : fines) {
					String fineAmount = fine.getChildText("amount").split(" ")[1];
					log.info("fine amount: " + fineAmount);

					minorUnit = fineAmount.split("\\.")[1].length();
					
					fineAmount = fineAmount.split("\\.")[0];
					totalFines = totalFines.add(new BigDecimal(fineAmount).multiply(new BigDecimal(100)));
					
					AccountDetails details = new AccountDetails();
					
					String[] dateComponents = fine.getChildText("fineDate").split("-");
					GregorianCalendar fineDate = new GregorianCalendar(
							Integer.parseInt(dateComponents[0]),
							Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0 
							Integer.parseInt(dateComponents[2]));
					
					details.setAccrualDate(fineDate);
					
					FiscalTransactionInformation fiscalTransactionInformation = new FiscalTransactionInformation();
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
						ItemId itemId = new ItemId();
						itemId.setItemIdentifierValue("");
						itemDetails.setItemId(itemId);
						itemDetails.setBibliographicDescription(description);
						fiscalTransactionInformation.setItemDetails(itemDetails);	
					}
					
					if (!fine.getChildText("fineType").equalsIgnoreCase("")){
						// TODO: Dozer work
						fiscalTransactionInformation.setFiscalTransactionType(new FiscalTransactionType(fine.getChildText("fineType")));
					}
					
					details.setFiscalTransactionInformation(fiscalTransactionInformation);
					
					accountDetails.add(details);
				}
			}
			
    	} catch (NullPointerException npe) {
    		log.debug("Fines/Currency Code not present in Fiscal Doc");
    		return null;
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

	private List<RequestedItem> getRequestedItemsForHoldRequests(String patronId, String patronUbId, String patronAgencyId) {
        	
        	List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
        	
        	String serviceUrl = ubPrefix + "/patron/" + patronId + 
        		"/circulationActions/requests/holds?patron_homedb=" + patronUbId + "&view=full";
        	
        	log.info ("Getting requested items for hold requests");
        	Document doc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
        	try {
	        	if (doc.getRootElement().getChild("holds").getChildren("institution") != null){
	        		List<Element> institutions = doc.getRootElement().getChild("holds").getChildren("institution");
	        		for (Element institution : institutions) {
	        			if (institution.getChildren("hold") != null){
	        				List<Element> holds = institution.getChildren("hold");
	        				for (Element hold : holds){
	        					
	        					Element requestItem = hold.getChild("requestItem");
	        					
	        					// if holdType != H  (aka Hold), skip
	        					if (!requestItem.getChildText("holdType").equalsIgnoreCase("H"))
	        						continue;
	        					
	        					RequestedItem requestedItem = new RequestedItem();
	        					//requestedItem.setDatePlaced(VoyagerUtil.convertDateToGregorianCalendar(rs.getDate(1)));
	        					//requestedItem.setPickupDate(VoyagerUtil.convertDateToGregorianCalendar(rs.getDate(2)));
	        					
	        					ItemId itemId = new ItemId();
	        					
	        					String institutionAgencyId = institution.getAttributeValue("id");
		    					if (institutionAgencyId.equalsIgnoreCase("LOCAL"))
		    						institutionAgencyId = patronAgencyId;
		    					else
		    						institutionAgencyId = institutionAgencyId.substring(1);
		
	        					itemId.setAgencyId(new AgencyId(institutionAgencyId));
	        					itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
	        					requestedItem.setItemId(itemId);
	        					
	        					String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
	        					GregorianCalendar expireDate = new GregorianCalendar(
	        							Integer.parseInt(dateComponents[0]),
	        							Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0 
	        							Integer.parseInt(dateComponents[2]));
	        					
	        					requestedItem.setPickupExpiryDate(expireDate);
	        					
	        					if (!requestItem.getChildText("queuePosition").equalsIgnoreCase(""))
	        						requestedItem.setHoldQueuePosition(new BigDecimal(requestItem.getChildText("queuePosition")));
	        					
	        					log.info("Pickup location: " + requestItem.getChildText("pickupLocation"));
	        					
	        					if (!requestItem.getChildText("pickupLocation").equalsIgnoreCase(""))
	        						requestedItem.setPickupLocation(new PickupLocation(requestItem.getChildText("pickupLocation")));
	        					
								GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
								requestedItem.setDatePlaced(nullDate);
	        					
	        					requestedItem.setRequestType(XcRequestType.HOLD);
	        					requestedItem.setRequestStatusType(new RequestStatusType(requestItem.getChildText("statusText")));
	        					requestedItem.setTitle(requestItem.getChildText("itemTitle"));
	        					requestedItems.add(requestedItem);
	        				}
	        			}
	        		}
	        	
	        		return requestedItems;
	        	}
        	} catch (NullPointerException npe) {
        		return requestedItems;
        	}
        		
    		return requestedItems;
	}

	private List<RequestedItem> getRequestedItemsForCallslipRequests(
			String patronId, String patronUbId, String patronAgencyId) {
    	
    	List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
    	
    	String serviceUrl = ubPrefix + "/patron/" + patronId + 
    		"/circulationActions/requests/callslips?patron_homedb=" + patronUbId + "&view=full";    	
    	
    	Document doc = voyagerSvcMgr.getWebServicesDoc(serviceUrl);
    	
    	try {
			if (doc.getRootElement().getChild("callslips").getChildren("institution") != null){
				List<Element> institutions = doc.getRootElement().getChild("callslips").getChildren("institution");
				for (Element institution : institutions) {
					if (institution.getChildren("callslip") != null){
						List<Element> callslips = institution.getChildren("callslip");
						for (Element callslip : callslips){
							Element requestItem = callslip.getChild("requestItem");
							
							RequestedItem requestedItem = new RequestedItem();

							ItemId itemId = new ItemId();

							String institutionAgencyId = institution.getAttributeValue("id");
							if (institutionAgencyId.equalsIgnoreCase("LOCAL"))
								institutionAgencyId = patronAgencyId;
							else
								institutionAgencyId = institutionAgencyId.substring(1);
			
							itemId.setAgencyId(new AgencyId(institutionAgencyId));
							itemId.setItemIdentifierValue(requestItem.getChildText("itemId"));
							requestedItem.setItemId(itemId);

							String[] dateComponents = requestItem.getChildText("expiredDate").split("-");
							GregorianCalendar expireDate = new GregorianCalendar(
									Integer.parseInt(dateComponents[0]),
									Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0 
									Integer.parseInt(dateComponents[2]));
							
							requestedItem.setPickupExpiryDate(expireDate);
							
							if (!requestItem.getChildText("queuePosition").equalsIgnoreCase(""))
								requestedItem.setHoldQueuePosition(new BigDecimal(requestItem.getChildText("queuePosition")));
							
							log.info("Pickup location: " + requestItem.getChildText("pickupLocation"));
							
							if (!requestItem.getChildText("pickupLocation").equalsIgnoreCase(""))
								requestedItem.setPickupLocation(new PickupLocation(requestItem.getChildText("pickupLocation")));
							
							GregorianCalendar nullDate = new GregorianCalendar(0, 0, 0);
							requestedItem.setDatePlaced(nullDate);
							
							requestedItem.setRequestType(XcRequestType.CALL_SLIP);
							requestedItem.setRequestStatusType(new RequestStatusType(requestItem.getChildText("statusText")));
							requestedItem.setTitle(requestItem.getChildText("itemTitle"));
							requestedItems.add(requestedItem);
						}
					}
				}
				return requestedItems;
			}
		} catch (NullPointerException npe) {
			return requestedItems;
		}
		return requestedItems;
	}

	private List<LoanedItem> getLoanedItems(Document doc, String patronAgencyId) {

    	String dueDate = null;

    	List<LoanedItem> loanedItems = new ArrayList<LoanedItem>();
    	
    	if (doc.getRootElement().getChild("loans") != null){
    		if (doc.getRootElement().getChild("loans").getChildren("institution") != null){	
	    		List<Element> institutions = doc.getRootElement().getChild("loans").getChildren("institution");
	    		for (Element institution : institutions) {
	    			if (institution.getChildren("loan") != null){
	    				List<Element> loans = institution.getChildren("loan");
	    				for (Element loan : loans) {			
	    					LoanedItem loanedItem = new LoanedItem();
	    					
	    					dueDate = loan.getChildText("dueDate");
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
	    					
	    					String institutionAgencyId = institution.getAttributeValue("id");
	    					if (institutionAgencyId.equalsIgnoreCase("LOCAL"))
	    						institutionAgencyId = patronAgencyId;
	    					else
	    						institutionAgencyId = institutionAgencyId.substring(1);
	    					
	    					itemId.setAgencyId(new AgencyId(institutionAgencyId));
	    					itemId.setItemIdentifierValue(loan.getChildText("itemId"));
	    					loanedItem.setItemId(itemId);
	    					
	    					loanedItem.setTitle(loan.getChildText("title"));
	    					
	    					Amount amount = new Amount();
	    					amount.setCurrencyCode(new CurrencyCode("USD", 2));
	    					amount.setMonetaryValue(new BigDecimal(0));
	    					
	    					loanedItem.setAmount(amount);
	    					
	    					loanedItems.add(loanedItem);
	    				}
	    			}
	    		}
	    		return loanedItems;
    		}
    	}
		return null;
	}

	private NameInformation getName(Document doc) {
    	StructuredPersonalUserName structuredUserName = new StructuredPersonalUserName();
    	String firstName = "", middleName = "", lastName = "";
    	
    	if (doc.getRootElement().getChild("address").getChild("name") != null){
    		Element nameElement = doc.getRootElement().getChild("address").getChild("name");
    		if (nameElement.getChild("firstName") != null)
    			firstName = nameElement.getChild("firstName").getTextTrim();
    		if (nameElement.getChild("middleName") != null)
    			middleName = nameElement.getChild("middleName").getTextTrim();
    		if (nameElement.getChild("lastName") != null)
    			lastName = nameElement.getChild("lastName").getTextTrim();
    		
			StringBuffer name = new StringBuffer();
			name.append(firstName);
			structuredUserName.setGivenName(firstName);
			
			if (!middleName.equalsIgnoreCase("")){
				name.append(" " + middleName);
				structuredUserName.setInitials(middleName);
			}
			
			name.append(lastName);
			structuredUserName.setSurname(lastName);
				
			PersonalNameInformation p  = new PersonalNameInformation();
			p.setUnstructuredPersonalUserName(name.toString());
			p.setStructuredPersonalUserName(structuredUserName);
				
			NameInformation n = new NameInformation();
			n.setPersonalNameInformation(p);
			return n;	
    	}
    	return null;
	}

	private List<UserAddressInformation> getAddress(Document doc) {
		UserAddressInformation userAddressInformation = null;
		List<UserAddressInformation> userAddressInformations = new ArrayList<UserAddressInformation>();
		try {
			// Has permanent address
			if (doc.getRootElement().getChild("address").getChild("permanentAddress") != null){
				userAddressInformation = new UserAddressInformation();
				Element permanentAddress = doc.getRootElement().getChild("address").getChild("permanentAddress");
				StringBuilder address = new StringBuilder();
				if (permanentAddress.getChild("addressLine1") != null && 
						!permanentAddress.getChild("addressLine1").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("addressLine1").getTextTrim());
				if (permanentAddress.getChild("addressLine2") != null && 
						!permanentAddress.getChild("addressLine2").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("addressLine2").getTextTrim());
				if (permanentAddress.getChild("addressLine3") != null && 
						!permanentAddress.getChild("addressLine3").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("addressLine3").getTextTrim());
				if (permanentAddress.getChild("addressLine4") != null && 
						!permanentAddress.getChild("addressLine4").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("addressLine4").getTextTrim());
				if (permanentAddress.getChild("addressLine5") != null && 
						!permanentAddress.getChild("addressLine5").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("addressLine5").getTextTrim());
				if (permanentAddress.getChild("city") != null && 
						!permanentAddress.getChild("city").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("city").getTextTrim());
				if (permanentAddress.getChild("stateProvince") != null && 
						!permanentAddress.getChild("stateProvince").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("stateProvince").getTextTrim());
				if (permanentAddress.getChild("zipPostal") != null && 
						!permanentAddress.getChild("zipPostal").getTextTrim().equalsIgnoreCase(""))
					address.append("\n").append(permanentAddress.getChild("zipPostal").getTextTrim());

				address.append("\n");
				
				log.info("Address is : " + address.toString());
				PhysicalAddress physicalAddress = new PhysicalAddress();
				physicalAddress.setPhysicalAddressType(Version1PhysicalAddressType.POSTAL_ADDRESS);
				userAddressInformation.setUserAddressRoleType(new UserAddressRoleType(VoyagerConstants.LOCATION_TYPE_PERMANENT));
			    UnstructuredAddress unstructuredAddress = new UnstructuredAddress();
			    unstructuredAddress.setUnstructuredAddressData(address.toString());
			    unstructuredAddress.setUnstructuredAddressType(Version1UnstructuredAddressType.NEWLINE_DELIMITED_TEXT);
			    physicalAddress.setUnstructuredAddress(unstructuredAddress);
				userAddressInformation.setPhysicalAddress(physicalAddress);
				userAddressInformations.add(userAddressInformation);
			}
				
			if (doc.getRootElement().getChild("address").getChild("emailAddress") != null){
				Element emailAddressElement = doc.getRootElement().getChild("address").getChild("emailAddress");
				
				if (emailAddressElement.getChild("address") != null){
					userAddressInformation = new UserAddressInformation();
					String emailAddress = emailAddressElement.getChildText("address");
					ElectronicAddress electronicAddress = new ElectronicAddress();
					electronicAddress.setElectronicAddressData(emailAddress);
					electronicAddress.setElectronicAddressType(new ElectronicAddressType (VoyagerConstants.ADDRESS_EMAIL));
					userAddressInformation.setElectronicAddress(electronicAddress);
					userAddressInformation.setUserAddressRoleType(new UserAddressRoleType(VoyagerConstants.ADDRESS_EMAIL));
					userAddressInformations.add(userAddressInformation);
				}
			}
		} catch (NullPointerException e) {
			log.error("Did not find Address info in vxws response");
			return null;
		}

		return userAddressInformations;
	}
}
