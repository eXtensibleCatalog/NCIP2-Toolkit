package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.user.AlephUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.ILSException;
import org.extensiblecatalog.ncip.v2.aleph.util.ItemToken;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.BibInformation;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.service.CurrentBorrower;
import org.extensiblecatalog.ncip.v2.service.CurrentRequester;
import org.extensiblecatalog.ncip.v2.service.ElectronicResource;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemTransaction;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetService;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1ItemDescriptionLevel;
import org.extensiblecatalog.ncip.v2.service.Version1MediumType;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;
import org.xml.sax.SAXException;

public class AlephLookupItemSetService implements LookupItemSetService {
	
    static Logger log = Logger.getLogger(AlephLookupItemSetService.class);
	
	private static final int MAX_ITEMS_TO_RETURN = 10;  
	
	private static Random random = new Random();

	private static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();
	
    /**
     * Construct an AlephRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public AlephLookupItemSetService() {
    }

	@Override
	public LookupItemSetResponseData performService(
			LookupItemSetInitiationData initData,
			RemoteServiceManager serviceManager) throws ServiceException {
		Date sService = new Date();
        LookupItemSetResponseData responseData = new LookupItemSetResponseData();
        AlephRemoteServiceManager alephSvcMgr = (AlephRemoteServiceManager) serviceManager;
        boolean getBibDescription = initData.getBibliographicDescriptionDesired();
        boolean getCircStatus = initData.getCirculationStatusDesired();
        boolean getElectronicResource = initData.getElectronicResourceDesired();
        boolean getHoldQueueLength = initData.getHoldQueueLengthDesired();
        boolean getItemDescription = initData.getItemDescriptionDesired();
        boolean getLocation = initData.getLocationDesired();
        boolean getCurrentBorrowers = initData.getCurrentBorrowerDesired();
        boolean getCurrentRequesters = initData.getCurrentRequestersDesired();
        
        List<BibliographicId> bibIds = initData.getBibliographicIds();
    	List<HoldingsSet> holdingSets = new ArrayList<HoldingsSet>();
    	Map<String, HoldingsSet> holdingIdHoldingSets = new HashMap<String, HoldingsSet>();
        List<String> holdingIds = null;
        List<String> itemIds = null;
        
        int itemCount = 0;
        boolean reachedMaxItemCount = false;
        
        if (alephSvcMgr.getXServerName() == null || alephSvcMgr.getXServerPort() == null) {
		    throw new ServiceException(ServiceError.CONFIGURATION_ERROR,"Aleph X-Server name and/or port not set");
		}
	
		AlephItem alephItem = null;
                // Execute request if agency Id is blank or NRU
		if (initData.getInitiationHeader().getFromAgencyId() != null 
		     && !initData.getInitiationHeader().getFromAgencyId().getValue().equalsIgnoreCase("") 
		     && alephSvcMgr.getAlephAgency(initData.getInitiationHeader().getFromAgencyId().getValue()) == null) {
		    throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST,
					       "This request cannot be processed. Agency ID is invalid or not found.");
		}
        
        String token = initData.getNextItemToken();
        ItemToken nextItemToken = null;
        //remove any bib ids from bibIds list that may have already been processed
        if (token != null) {
        	nextItemToken = tokens.get(token);
        	if (nextItemToken != null) {
	        	int index = getBibIdIndex(bibIds, nextItemToken.getBibliographicId());
	        	if (index != -1) {
			    //remove the ones already processed
			    bibIds.subList(0, index).clear();
	        	}
	        	
	        	// Remove token from memory hashmap
	        	tokens.remove(token);
        	} else {
        		Problem problem = new Problem();
        		problem.setProblemDetail("Invalid nextItemToken");
        		problem.setProblemValue("nextItemToken =" + token);
        		List<Problem> problems = new ArrayList<Problem>();
				problems.add(problem);
        		responseData.setProblems(problems);
        		
        		return responseData;
        	}
        	log.debug("after removing already processed Bib ids ="+bibIds);
        	
        }
        List<BibInformation> bibInformations = new ArrayList<BibInformation>();
        
        // Loop through Bib Ids in request
        for (BibliographicId bibId : bibIds) {
        	log.debug("Processing Bib id = "+bibId.getBibliographicItemId().getBibliographicItemIdentifier());
	        try {
	        	String id = bibId.getBibliographicItemId().getBibliographicItemIdentifier();
	        	BibInformation bibInformation = new BibInformation();
	        	bibInformation.setBibliographicId(bibId);

			    AlephItem bibItem = alephSvcMgr.lookupItemByBibId(id, initData.getInitiationHeader().getFromAgencyId().getValue(), true, getHoldQueueLength, getCurrentBorrowers, getCurrentRequesters);
			    
			    bibInformation.setTitleHoldQueueLength(new BigDecimal(bibItem.getHoldQueueLength()));
			    if (getBibDescription){
			    	BibliographicDescription bDesc = AlephUtil.getBibliographicDescription(bibItem,initData.getInitiationHeader().getFromAgencyId());
	        		bibInformation.setBibliographicDescription(bDesc);
			    }
	        	List<AlephItem> holdingsItems = alephSvcMgr.lookupHoldingsItemsByBibId(id, initData.getInitiationHeader().getFromAgencyId().getValue(), 
	        			getBibDescription, getHoldQueueLength, getCurrentBorrowers, getCurrentRequesters);

	        	//first iterate through list of holdings items to get itemIds list so can move pointer if nextitemtoken set for itemids
	        	itemIds = new ArrayList<String>();
	        	for (AlephItem holdingsItem : holdingsItems){
	        		itemIds.add(holdingsItem.getItemId()); 
	        	}
	        	
	        	if (nextItemToken != null) {
	        		int index = itemIds.indexOf(nextItemToken.getItemId());
	        		if (index != -1) {
	        			//also modify holdingsItems list since that is what we use and will be in same sequential order as itemIds list
	        			if (index != holdingsItems.size() -1) {
	        				holdingsItems.subList(0, index + 1).clear();
	        				itemIds.subList(0, index + 1).clear();
	        			} else {
	        				holdingsItems.clear();
	        				itemIds.clear();
	        			}
				    
	        		} 

	        		log.debug("after removing already processed item ids ="+itemIds);
	        	
	        	}

	        	if (holdingsItems!=null){
	        		for (AlephItem holdingsItem : holdingsItems){
	        			if (holdingsItem.getHoldingsId()!=null&&holdingsItem.getHoldingsId().length()>0){
	        				HoldingsSet holdingsSet = holdingIdHoldingSets.get(holdingsItem.getHoldingsId());
	        				if (holdingsSet == null){
	        					holdingsSet = new HoldingsSet();
	        					// Set Bib Id and holdings set id
	        					holdingsSet.setHoldingsSetId(holdingsItem.getHoldingsId());
	        					holdingsSet.setCallNumber(holdingsItem.getCallNumber());
			        		
	        					// Set location
	        					Location location = null;
	        					if (getLocation) {
	        						location = AlephUtil.getLocation(alephItem);
	        						if (location != null) {
	        							holdingsSet.setLocation(location);
	        						}
	        					}
			        		
	        					int newItemCount = itemCount + holdingsItems.size();
			        		
	        					if (newItemCount > MAX_ITEMS_TO_RETURN) {
	        						holdingsItems = getHoldingsItemsSubset(holdingsItems, itemCount);
	        						itemIds = getItemIdSubset(itemIds, itemCount);
	        						log.debug("Subset holdingItems:"+holdingsItems);
	        					}
	        				}
	        			
	        				List<ItemInformation> itemInformations = holdingsSet.getItemInformations();
	        				if (itemInformations==null) itemInformations = new ArrayList<ItemInformation>();
	        				
	        				ItemInformation itemInformation = new ItemInformation();
	        				ItemId item = new ItemId();
	        				item.setItemIdentifierValue(alephItem.getItemId());
	        				item.setAgencyId(initData.getInitiationHeader().getFromAgencyId());
	    		        
	        				itemInformation.setItemId(item);
	        				itemInformation.setItemOptionalFields(AlephUtil.getItemOptionalFields(alephItem));
	        				if (alephItem.getDueDate()!=null){
	        					GregorianCalendar cal = new GregorianCalendar();
	        					cal.setTime(alephItem.getDueDate());
	        					itemInformation.setDateDue(cal);
	        				}
	        				itemInformations.add(itemInformation);
	        				holdingsSet.setItemInformations(itemInformations);
	        				holdingIdHoldingSets.put(holdingsItem.getHoldingsId(), holdingsSet);
	        				itemCount = itemCount + itemIds.size();
	        				log.debug("Item count: " + itemCount);
	        				if (itemCount == MAX_ITEMS_TO_RETURN) {
	        					// Set next item token
	        					ItemToken itemToken = new ItemToken();
	        					itemToken.setBibliographicId(id);
	        					itemToken.setHoldingsId(holdingsItem.getHoldingsId());
	        					itemToken.setItemId(itemIds.get(itemIds.size() - 1));
	        					int newToken = random.nextInt();
	        					itemToken.setNextToken(Integer.toString(newToken));
	        					tokens.put(Integer.toString(newToken), itemToken);
				    
	        					responseData.setNextItemToken(Integer.toString(newToken));
	    		        	
	        					reachedMaxItemCount = true;
	        					break;
	        				}
	        			}	
	        		}
	        	
	        		holdingSets.addAll(holdingIdHoldingSets.values());
	        		bibInformation.setHoldingsSets(holdingSets);
	        		bibInformations.add(bibInformation);
	        		if (reachedMaxItemCount) {
	        			break;
	        		}
	        	}
	        } catch (AlephException e) {
	        	Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Processing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        } catch (SAXException e) {
	        	Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Processing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        } catch (ParserConfigurationException e) {
	        	Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Processing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        } catch (IOException e) {
	        	Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Processing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        }
        }
        
        responseData.setBibInformations(bibInformations);
    	Date eService = new Date();
    	
    	log.debug("Service time log : " + (eService.getTime() - sService.getTime()) + "  " + ((eService.getTime() - sService.getTime())/1000) + " sec");
        log.debug(responseData);
        log.debug("Tokens in memory: " + tokens);
		return responseData;
	}
	
    private int getBibIdIndex(List<BibliographicId> bibIds, String bibId) {
    	
    	for (int i =0; i < bibIds.size() ; i++) {
    		
    		if (bibIds.get(i).getBibliographicItemId().getBibliographicItemIdentifier().equalsIgnoreCase(bibId)) {
    			return i;
    		}
    	}
    	
    	return -1;
    }
    
    private List<String> getItemIdSubset(List<String> itemIds, int itemCount) {
    	
    	int numOfitemIdsToProcess = MAX_ITEMS_TO_RETURN - itemCount;
    	
    	return itemIds.subList(0, numOfitemIdsToProcess);
    	
    }
    
    private List<AlephItem> getHoldingsItemsSubset(List<AlephItem> items, int itemCount) {
    	
    	int numOfitemIdsToProcess = MAX_ITEMS_TO_RETURN - itemCount;
    	
    	return items.subList(0, numOfitemIdsToProcess);
    	
    }
    
}
