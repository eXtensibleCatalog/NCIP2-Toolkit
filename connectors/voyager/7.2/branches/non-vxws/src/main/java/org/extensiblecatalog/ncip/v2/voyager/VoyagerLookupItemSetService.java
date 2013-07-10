/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.voyager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.BibInformation;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.ItemToken;

/**
 * This class implements the Lookup Item Set service for the Voyager back-end connector. 
 * 
 * @author SharmilaR
 */
public class VoyagerLookupItemSetService implements LookupItemSetService {
	
	static Logger log = Logger.getLogger(VoyagerLookupItemSetService.class);
	
	private static final int MAX_ITEMS_TO_RETURN = 10;  
	
	private static Random random = new Random();

	private static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();
	
    /**
     * Construct a VoyagerRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public VoyagerLookupItemSetService() {
    }

    /**
     * Handles a NCIP LookupItem service by returning data from voyager.
     *
     * @param initData       the LookupItemInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupItemResponseData
     */
    @Override
    public LookupItemSetResponseData performService (LookupItemSetInitiationData initData,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {
 

    	Date sService = new Date();
        LookupItemSetResponseData responseData = new LookupItemSetResponseData();
        VoyagerRemoteServiceManager voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        
        List<BibliographicId> bibIds = initData.getBibliographicIds();
    	List<HoldingsSet> holdingSets = new ArrayList<HoldingsSet>();
        List<String> holdingIds = null;
        List<String> itemIds = null;
        
        int itemCount = 0;
        boolean reachedMaxItemCount = false;
        
        String token = initData.getNextItemToken();
        ItemToken nextItemToken = null;
        if (token != null) {
        	nextItemToken = tokens.get(token);
        	if (nextItemToken != null) {
	        	int index = getBibIdIndex(bibIds, nextItemToken.getBibliographicId());
	        	if (index != -1) {
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
	        	
	        	// Get holding Ids belonging to this bib
	        	holdingIds = voyagerSvcMgr.getHoldingIdsForBibId(id);
	        	
	        	if (nextItemToken != null) {
	             	int index = holdingIds.indexOf(nextItemToken.getHoldingsId());
	             	if (index != -1) {
	             		holdingIds.subList(0, index).clear();
	             	}
	             	log.debug("after removing already processed holding ids ="+holdingIds);
	             	
	             }
	        	
	        	// Set bib desc
	        	BibliographicDescription bDesc = null;
	        	if (initData.getBibliographicDescriptionDesired()) {
	        		bDesc = voyagerSvcMgr.getBibliographicDescriptionForBibId(id);
	        		bibInformation.setBibliographicDescription(bDesc);
	        	}
	        	
	        	// title hold queue length
	        	BigDecimal titleHoldQueue = voyagerSvcMgr.getTitleLevelHoldQueueLength(id);
	        	if (titleHoldQueue != null) {
	        		bibInformation.setTitleHoldQueueLength(titleHoldQueue);
	        	}
	        	
	        	holdingSets = new ArrayList<HoldingsSet>();
	        	
	        	// Build HoldingSet with items in it
	        	for (String holdingId : holdingIds) {
	        		log.debug("Processing Holding id = "+holdingId);
	        		itemIds = voyagerSvcMgr.getItemIdsForHoldingId(holdingId);
	        		log.debug("All itemIds:"+itemIds);
	        		if (nextItemToken != null) {
		             	int index = itemIds.indexOf(nextItemToken.getItemId());
		             	if (index != -1) {
		             		if (index != itemIds.size() -1) {
		             			itemIds.subList(0, index + 1).clear();
		             		} else {
		             			itemIds.clear();	
		             		}
		             	} 

		             	log.debug("after removing already processed item ids ="+itemIds);
		             }
	        		
	        		HoldingsSet holdingSet = new HoldingsSet();
	        		// Set Bib Id and holdings set id
	        		holdingSet.setHoldingsSetId(holdingId);	        		
	        		
	        		// Get Bib desc, holding set info only if items exist for that holdings
	        		if (itemIds != null && itemIds.size() > 0) {
		           		
	        			holdingSet.setCallNumber(voyagerSvcMgr.getCallNumberForHoldingId(holdingId));	
	        			
	        			// Set location
		        		Location location = null;
		        		if (initData.getLocationDesired()) {
		        			location = voyagerSvcMgr.getPermanentLocationForHoldingId(holdingId);
		        			if (location != null) {
		        				holdingSet.setLocation(location);
		        			}
		        		}
		        		
		        		int newItemCount = itemCount + itemIds.size();
		        		
		        		if (newItemCount > MAX_ITEMS_TO_RETURN) {
		        			itemIds = getItemIdSubset(itemIds, itemCount);
		        			log.debug("Subset itemIds:"+itemIds);
		        		}
	        			Map<String, ItemInformation> itemInformations = new HashMap<String, ItemInformation>();
	        			for (String itemId:itemIds) {
		        			ItemInformation itemInformation = new ItemInformation();
		        			ItemId item = new ItemId();
		        			item.setItemIdentifierValue(itemId);
		        			item.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY)));
		    		        
		        			itemInformation.setItemId(item);
		        			itemInformations.put(itemId, itemInformation);
	        			}
	
	    		        Map<String, String> statuses = null;
	    		        if (initData.getCirculationStatusDesired()) {
			        		statuses = voyagerSvcMgr.getCirculationStatusForItemIds(itemIds);
	    		        }
	    		        
	    		        Map<String, BigDecimal> lengths = null;
	    		        if (initData.getHoldQueueLengthDesired()) {
			        		lengths = voyagerSvcMgr.getHoldQueueLengthForItemIds(itemIds);
	    		        }
	
	    		        if (initData.getElectronicResourceDesired()) {
	    		        	holdingSet.setElectronicResource(voyagerSvcMgr.getElectronicResourceForHoldingId(holdingId));
	    		        }
	
	    		        Map<String, ItemDescription> itemDescriptions = null;
	    		        if (initData.getItemDescriptionDesired()) {
	    		        	itemDescriptions = voyagerSvcMgr.getItemDescriptionForItemIds(itemIds);
	    		        }
	
	    		        Map<String, List<Location>> locations = null;
	    		        if (initData.getLocationDesired()) {
	    		        	locations = voyagerSvcMgr.getTemporaryLocationForItemIds(itemIds);
	    		        }
	    		        
	    		        Map<String, GregorianCalendar> dueDates = null;
	    		        dueDates = voyagerSvcMgr.getDueDateForItemIds(itemIds);
	    		        
	    		        Iterator<String> itr = itemInformations.keySet().iterator();
	    		    	while (itr.hasNext()) {
			    			ItemOptionalFields iof = new ItemOptionalFields();
	    		        	String key = itr.next();
	    		        	if (statuses != null) {
	    		        		log.debug("Status for key " + statuses.get(key));
	    		        		if (statuses.get(key) != null) {
	    		        			iof.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, statuses.get(key)));
	    		        		}
	    		        	}
	    		       	
	    		        	if (lengths != null) {
	    		        		iof.setHoldQueueLength(lengths.get(key));
	    		        	}
	    		        	
	    		        	if (itemDescriptions != null) {
	    		        		iof.setItemDescription(itemDescriptions.get(key));
	    		        	}
	    		        	
	    		        	if (locations != null) {
	    		        		List<Location> tempLocations = locations.get(key);
	    		        		if (tempLocations != null) {
	    		        			LocationNameInstance lni = tempLocations.get(0).getLocationName().getLocationNameInstances().get(0);
	    		        			if (!lni.getLocationNameValue().equals(location.getLocationName().getLocationNameInstances().get(0).getLocationNameValue())) {
	    		        				iof.setLocations(tempLocations);
	    		        			}
	    		        			
	    		        		}
	    		        		
	    		        	}
	    		    
	    		        	ItemInformation itemInformation = itemInformations.get(key);
	    		        	itemInformation.setItemOptionalFields(iof);
	    		        	itemInformation.setDateDue(dueDates.get(key));
	    		        	itemInformations.put(key, itemInformation);
	    	        	}
	    		        
	    		        
	        			
	    		    	holdingSet.setItemInformations(new ArrayList<ItemInformation>(itemInformations.values()));
		        		
		        		
		        		itemCount = itemCount + itemIds.size();
		        		log.debug("Item count: " + itemCount);
		        		if (itemCount == MAX_ITEMS_TO_RETURN) {
		        			// Set next item token
	    		        	ItemToken itemToken = new ItemToken();
	    		        	itemToken.setBibliographicId(id);
	    		        	itemToken.setHoldingsId(holdingId);
	    		        	itemToken.setItemId(itemIds.get(itemIds.size() - 1));
	    		        	log.info("Setting token bib to : " + id + " and holding id to " + holdingId + " and item to " + itemIds.get(itemIds.size() - 1));
	    		        	int newToken = random.nextInt();
	    		        	itemToken.setNextToken(Integer.toString(newToken));
	    		        	tokens.put(Integer.toString(newToken), itemToken);
	    		        	
	    		        	responseData.setNextItemToken(Integer.toString(newToken));
	    		        	
	    		        	reachedMaxItemCount = true;
	    		        	break;
		        		}
	        		} else if (itemIds.size() == 0){
	        			
	        			itemCount = itemCount + 1;
		        		log.debug("Item count: " + itemCount);
		        		if (itemCount == MAX_ITEMS_TO_RETURN) {
		        			// Set next item token
	    		        	ItemToken itemToken = new ItemToken();
	    		        	itemToken.setBibliographicId(id);
	    		        	itemToken.setHoldingsId(holdingId);
	    		        	itemToken.setItemId("");
	    		        	log.info("Setting token bib to : " + id + " and holding id to " + holdingId + " and item to _blank_");
	    		        	int newToken = random.nextInt();
	    		        	itemToken.setNextToken(Integer.toString(newToken));
	    		        	tokens.put(Integer.toString(newToken), itemToken);
	    		        	
	    		        	responseData.setNextItemToken(Integer.toString(newToken));
	    		        	
	    		        	reachedMaxItemCount = true;
		        		}
		        		
		        		Map<String, ItemInformation> itemInformations = new HashMap<String, ItemInformation>();
		        		ItemInformation itemInformation = new ItemInformation();
	        			ItemId item = new ItemId();
	        			item.setItemIdentifierValue("");
	        			item.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY)));
	    		        
	        			itemInformation.setItemId(item);
	        			itemInformations.put("", itemInformation);
	        			holdingSet.setItemInformations(new ArrayList<ItemInformation>(itemInformations.values()));
	        		}
	        			
	        			
	        		holdingSets.add(holdingSet);
	        	}
	        	
	        	if (holdingIds.size() == 0){
		        	Problem p = new Problem();
					p.setProblemType(new SchemeValuePair("Unknown bib id"));
					List<Problem> problems = new ArrayList<Problem>();
					problems.add(p);
					bibInformation.setProblems(problems);
					bibInformations.add(bibInformation);
		        } else {
		        	bibInformation.setHoldingsSets(holdingSets);
		        	bibInformations.add(bibInformation);
		        }
		        		        
		        if (reachedMaxItemCount) {
	        		break;
	        	}
	  
	        } catch (ILSException e) {
	        	Problem p = new Problem();
				p.setProblemType(new SchemeValuePair("Processing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
	        }
        }
        
	    //responseData.setProblems(problems);    
    	responseData.setBibInformations(bibInformations);
    	Date eService = new Date();
    	
    	//log.debug("Service time log : " + (eService.getTime() - sService.getTime()) + "  " + ((eService.getTime() - sService.getTime())/1000) + " sec");
        //log.debug(responseData);
        //log.debug("Tokens in memory: " + tokens);
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
}
