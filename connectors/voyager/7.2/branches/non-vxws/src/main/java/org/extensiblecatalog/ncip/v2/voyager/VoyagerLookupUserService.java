package org.extensiblecatalog.ncip.v2.voyager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.AccountBalance;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupUserService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.RequestedItemsCount;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.UserFiscalAccount;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.XcRequestType;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;

/**
 * Implements Lookup User service for voyager connector 
 * 
 * @author SharmilaR
 *
 */
public class VoyagerLookupUserService implements LookupUserService { 
	
	/** Logger */
    static Logger log = Logger.getLogger(VoyagerLookupUserService.class);

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
        
        VoyagerRemoteServiceManager voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
		LookupUserResponseData lookupUserResponseData = new LookupUserResponseData();

		try {
	        String authenticatedUserId = voyagerSvcMgr.authenticateUser(initData.getAuthenticationInputs());
	        UserId userId = new UserId();
	        userId.setUserIdentifierValue(authenticatedUserId);
	        userId.setUserIdentifierType(new SchemeValuePair("Username"));
	        userId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY)));
	        lookupUserResponseData.setUserId(userId);
	        
			String patronId = voyagerSvcMgr.getPatronIdFromInstitutionId(authenticatedUserId);
			if (patronId == null) {
				Problem p = new Problem();
				p.setProblemElement("UserId");
				p.setProblemValue("Unknown User");
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
			    lookupUserResponseData.setProblems(problems);
			    return lookupUserResponseData;
			}
	
			UserOptionalFields userOptionalFields = null;

			// Get the address information if necessary
			if (initData.getUserAddressInformationDesired()) {
				
				List<UserAddressInformation> userAddressInformations = voyagerSvcMgr.getAddress(patronId);
				
				if (userAddressInformations != null) {
					userOptionalFields = new UserOptionalFields();
					userOptionalFields.setUserAddressInformations(userAddressInformations);
				}
			}
			
			// Name information
			if (initData.getNameInformationDesired()) {
				if (userOptionalFields == null) {
					userOptionalFields = new UserOptionalFields();
	    		} 
				userOptionalFields.setNameInformation(voyagerSvcMgr.getNameInformation(patronId));
			}

			if (userOptionalFields != null) {
				lookupUserResponseData.setUserOptionalFields(userOptionalFields);
			}

			
			// Get the fines information if necessary
			if (initData.getUserFiscalAccountDesired()) {
			    
				List<UserFiscalAccount> accounts = new ArrayList<UserFiscalAccount>();
				// Get the user's total fines and the code for the currency they are
			    // in from the Voyager database
			    String currencyCode = voyagerSvcMgr.getCurrencyCode();
			    BigDecimal totalFines = voyagerSvcMgr.getTotalFines(patronId);
			    
			    UserFiscalAccount userFiscalAccount = new UserFiscalAccount();
			    
			    if (totalFines.compareTo(BigDecimal.ZERO) > 0) {
			    	AccountBalance accountBalance = new AccountBalance();
			    	accountBalance.setMonetaryValue(totalFines);
			    	accountBalance.setCurrencyCode(new SchemeValuePair(currencyCode));
			    	userFiscalAccount.setAccountBalance(accountBalance);
	
					if (log.isDebugEnabled())
					    log.debug("The user owes " + accountBalance.getMonetaryValue()
						    + " in fines with currency code "
						    + accountBalance.getCurrencyCode());
			    }
	
			    userFiscalAccount.setAccountDetails(voyagerSvcMgr.getAccountDetails(patronId));
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
				callslipItems.addAll(voyagerSvcMgr.getRequestedItemForCallslipRequests(patronId));
				holdItems.addAll(voyagerSvcMgr.getRequestedItemForHoldRequests(patronId));
				requestedItems.addAll(callslipItems);
				requestedItems.addAll(holdItems);
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
				
				if (requestedItemsCounts.size() > 0) {
					lookupUserResponseData.setRequestedItemsCounts(requestedItemsCounts);
				}
				
			}
	
			// Get the checked out items information if necessary
			if (initData.getLoanedItemsDesired() ) {
				List<LoanedItem> items = voyagerSvcMgr.getLoanedItems(patronId);
				lookupUserResponseData.setLoanedItems(items);
				
				// Populate LoanedItemsCount
				if (items != null && items.size() > 0) {
					List<String> itemIds = new ArrayList<String>(); 
					for(LoanedItem item : items) {
						itemIds.add(item.getItemId().getItemIdentifierValue());
					}
					lookupUserResponseData.setLoanedItemsCounts(voyagerSvcMgr.getLoanedItemsCount(itemIds));
				}
				
			}
		} catch (ILSException e) {
			Problem p = new Problem();
			p.setProblemType(new SchemeValuePair("Processing error"));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
		    lookupUserResponseData.setProblems(problems);
		}
		
		log.debug("lookupUserResponseData ="+lookupUserResponseData);
		return lookupUserResponseData;

    }
}
