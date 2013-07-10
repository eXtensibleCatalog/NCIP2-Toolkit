/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.ItemStatus;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.StatusString;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.itemCirculation;
import org.extensiblecatalog.ncip.v2.service.*;
import org.apache.log4j.Logger; 

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Lookup Item Set service for the Dummy back-end
 * connector. Basically this just calls the MillenniumRemoteServiceManager to
 * get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService
 * classes, do not use this class as an example. See the NCIP toolkit Connector
 * developer's documentation for guidance.
 */
public class MillenniumLookupItemSetService implements LookupItemSetService {
	
	private static final Logger LOG = Logger.getLogger(MillenniumLookupItemSetService.class);
	/**
	 * Construct a MillenniumLookupItemSetService; this class is not
	 * configurable so there are no parameters.
	 */
	public MillenniumLookupItemSetService() {
	}

	/**
	 * Handles a NCIP LookupItemSet service by returning hard-coded data.
	 * 
	 * @param initData
	 *            the LookupItemSetInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupItemSetResponseData
	 * @throws ServiceException 
	 */
	@Override
	public LookupItemSetResponseData performService(
			LookupItemSetInitiationData initData, ServiceContext serviceContext,
			RemoteServiceManager serviceManager) throws ServiceException {

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
		List<Problem> problems = null;
		int maximumItemsCount = 20;
		int currentItem = 0;
		
		final LookupItemSetResponseData responseData = new LookupItemSetResponseData();
		String bibNo = "";
		List<BibInformation> bibInformations = new ArrayList<BibInformation>();

        if (initData.getBibliographicIds() != null && initData.getBibliographicIds().size() > 0) {
            /////////// Added for NextItemToken support ////////////////
            ////////////////////////////////////////////////////////////
        	//int currentItem = 0;
        	
            int startItem = 1;
            if ( initData.getNextItemToken() != null ) {
                try {
                    startItem = Integer.valueOf(initData.getNextItemToken());
                } catch (NumberFormatException e) {

                    BibInformation bibInformation = new BibInformation();
                    bibInformation.setProblems(ServiceHelper.generateProblems(
                        Version1GeneralProcessingError.UNAUTHORIZED_COMBINATION_OF_ELEMENT_VALUES_FOR_SYSTEM,
                        "//NextItemToken", initData.getNextItemToken(),
                        "Invalid token of '" + initData.getNextItemToken() + "'."));
                }
            }
            //int maximumItemsCount = 20;
            int processCount = 1;
            if ( initData.getMaximumItemsCount() != null ) {
                maximumItemsCount = initData.getMaximumItemsCount().intValue();
            }
            //LOG.debug("maximumItemsCount: " + maximumItemsCount);
            ////////////////////////////////////////////////////////////
            ///////////////// End of Added for NextItemToken support ///////////////////////
		
        	for (BibliographicId bibId : initData.getBibliographicIds()) {
        		if (currentItem <= maximumItemsCount) {
         			if (bibId.getBibliographicRecordId() != null) {
        				if (bibId.getBibliographicRecordId().getAgencyId() != null) {
        					bibNo = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        					LOG.debug("LookupItemSet - Process Record: " + processCount + " - BibNo: " + bibNo);
        					if (bibNo.length() > 0) {
          						BibliographicDescription bibDesc = new BibliographicDescription();

        						// Set lookup page to the HTML
        						StatusString lookupPage = millenniumSvcMgr.getItemPage(bibNo, "Bib Record");
        						if (lookupPage.recordStatus.returnStatus) {
        							LOG.debug("LookupItemSet - Success received lookup item page");
        							if (lookupPage.statusValue.contains("No Such Record") == false) {
        								itemCirculation itemCirculationStatus = millenniumSvcMgr.getItemStatusList(lookupPage.statusValue);
        								if (itemCirculationStatus.recordStatus.returnStatus) {
        									// Bach Nguyen - 10/12/2011 @ 4:00 PM - Added itemStatusList
            								//ArrayList<ItemStatus> itemStatusList = millenniumSvcMgr.getItemStatusList(lookupPage.statusValue);
                    						ArrayList<ItemStatus> itemStatusList = itemCirculationStatus.itemsList;
                     						//int x = 0;
                    						// LOG.debug("Millennium - Size of itemStatusList in LookupItemSet: " + itemStatusList.size());	
                    						// while (x < itemStatusList.size()) {
                    						// 		LOG.debug("Millennium - itemStatusList[" + x + "].itemLocStatus: " + itemStatusList.get(x).itemLocStatus);
                    						//   	LOG.debug("Millennium - itemStatusList[" + x + "].itemCallNoStatus: " + itemStatusList.get(x).itemCallNoStatus);
                    						//   	LOG.debug("Millennium - itemStatusList[" + x + "].itemCirStatus: " + itemStatusList.get(x).itemCirStatus);
                    						//	 	x++;
                    						// }
                   							
                    						BibliographicRecordId bibliographicRecordId = new BibliographicRecordId();
                    						bibliographicRecordId.setBibliographicRecordIdentifier(bibNo);

                    						AgencyId agencyId = AgencyId.find(null, bibId.getBibliographicRecordId().getAgencyId().getValue());
                    						bibliographicRecordId.setAgencyId(agencyId);
                    						List<BibliographicRecordId> bibliographicRecordIdList = new ArrayList<BibliographicRecordId>();
                    						bibliographicRecordIdList.add(bibliographicRecordId);
                    						bibDesc.setBibliographicRecordIds(bibliographicRecordIdList);

                    						// Note: This could fail if the bib record has a language code that's not in this NCIP scheme. J Bodfish
                    						Language language = Language.find(Version1Language.VERSION_1_LANGUAGE, millenniumSvcMgr.getBibLanguage(bibNo));
                    						bibDesc.setLanguage(language);

                    						// set the title
                    						//LOG.debug("Title");
                    						String getValue = "";
                    						getValue = millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Title");
                    						if (getValue != null && getValue.equals("Missing")) {
                    							getValue = null;
                    						}
                     						//bibDesc.setTitle(millenniumSvcMgr.getBibDescription(lookupPage, "Title"));
                    						bibDesc.setTitle(getValue);
                    							
                    						// set the author
                    						//LOG.debug("Author");
                    						getValue = "";
                    						getValue = millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Author");
                    						if (getValue != null && getValue.equals("Missing")) {
                     							getValue = null;
                    						} 
                    						//bibDesc.setAuthor(millenniumSvcMgr.getBibDescription(lookupPage, "Author"));
                    						bibDesc.setAuthor(getValue);

                    						// set the publisher
                    						//LOG.debug("Publisher");
                    						getValue = "";
                    						getValue = millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Publisher");
                    						if (getValue != null && getValue.equals("Missing")) {
                    							getValue = null;
                    						}
                    						//bibDesc.setPublisher(millenniumSvcMgr.getBibDescription(lookupPage, "Publisher"));
                    						bibDesc.setPublisher(getValue);

                    						// set the series
                    						//LOG.debug("Series");
                    						getValue = "";
                    						getValue = millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Series");
                    						if (getValue != null && getValue.equals("Missing")) {
                    							getValue = null;
                    						}
                    						//bibDesc.setSeriesTitleNumber(millenniumSvcMgr.getBibDescription(lookupPage, "Series"));
                    						bibDesc.setSeriesTitleNumber(getValue);

                    						// Item Description
                   							// Set the ISBN
                   							BibliographicItemId bibliographicItemId = new BibliographicItemId();
                   							bibliographicItemId.setBibliographicItemIdentifier(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "ISBN"));
                        					
                   							List<ItemInformation> itemInformationList = new ArrayList<ItemInformation>();
                         					
                   							for (ItemStatus itemStatus : itemStatusList){
                       						
                   								/////////// Added for NextItemToken support ////////////////
                   								////////////////////////////////////////////////////////////
                   								currentItem++;

                   								if (currentItem < startItem) {
                   									continue; // Skip this item because it was returned in a previous message (theoretically).
                   								}
                   								//LOG.debug("Value of startItem, currentItem, MaxItemcount: " + startItem + "; " + currentItem + "; " + maximumItemsCount);

                   								if (maximumItemsCount != 0 && currentItem >= (startItem + maximumItemsCount)) {
                   									LOG.debug("Next Token for bibNo: " + bibNo + " is (*, NextToken, *): (" + startItem + "; " + currentItem + "; " + maximumItemsCount +")");
                   									//responseData.setNextItemToken(Integer.toString(currentItem));
                   									break; // Stop returning items, we've reached the limit
                   								}
                   								////////////////////////////////////////////////////////////
                   								///////////////// End of Added for NextItemToken support ///////////////////////
                                                
                   								ItemInformation iInfo = new ItemInformation();
                   								// Assemble the ItemOptionalFields where most of the data is returned
                   								ItemOptionalFields itemOptionalFields = new ItemOptionalFields();			
                         						
                   								ItemDescription itemDescription = new ItemDescription();
                    								
                   								itemDescription.setCallNumber(itemStatus.itemCallNoStatus.trim());
                         						
                   								// There doesn't appear to be a XcMillenniumCirculationStatus class, so I've changed this
                   								// to the XcCirculationStatus class, but this may fail if Millennium has different statuses than are in the
                   								// XcCirculationStatus list. J Bodfish
                   								String finalitemCirStatus = null;
                   								if (itemStatus.itemCirStatus.trim().equals("Missing")){
                   									finalitemCirStatus = "";
                   								} else {
                   									finalitemCirStatus = itemStatus.itemCirStatus.trim();
                   								}
                    									
                   								//itemOptionalFields.setCirculationStatus(CirculationStatus.find(null,itemStatus.itemCirStatus.trim()));
                   								itemOptionalFields.setCirculationStatus(CirculationStatus.find(null,finalitemCirStatus));
                        							
                   								List<Location> locations = new ArrayList<Location>();
                   								Location location = new Location();
                   								LocationName locationName = new LocationName();
                   								List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
                   								LocationNameInstance locationNameInstance = new LocationNameInstance();
                   								locationNameInstance.setLocationNameValue(itemStatus.itemLocStatus.trim());
                   								locationNameInstance.setLocationNameLevel(new BigDecimal(1));
                   								locationNameInstances.add(locationNameInstance);
                   								locationName.setLocationNameInstances(locationNameInstances);
                   								location.setLocationName(locationName);
                   								location.setLocationType(Version1LocationType.CURRENT_LOCATION);
                   								locations.add(location);
                   								itemOptionalFields.setLocations(locations);
                   								//itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus, "AVAILABLE", values, svpClass));
                   								itemOptionalFields.setItemDescription(itemDescription);
                   								iInfo.setItemOptionalFields(itemOptionalFields);
                   								itemInformationList.add(iInfo); 
                   							}
                     						
                   							/////////// Added for NextItemToken support ////////////////
                   							////////////////////////////////////////////////////////////
                   							// Test whether we have added any ItemInformation objects; because if not there's no need to return the HoldingsSet & BibInformation.
                   							if ( itemInformationList.isEmpty() ) {
                   								LOG.debug("No more itemInformationList");
                   								break; // Don't bother returning the BibInformation as there's no ItemInformation to return.
                   							}
                   							////////////////////////////////////////////////////////////
                   							///////////////// End of added lines ///////////////////////
                        					
                   							//List<BibInformation> bibInformations = new ArrayList<BibInformation>(); // Bach Nguyen - Move outside of the loop
                   							BibInformation binfo = new BibInformation();
                   							BibliographicId bibliographicId = new BibliographicId();
                   							bibliographicId.setBibliographicRecordId(bibliographicRecordId);
                   							binfo.setBibliographicId(bibliographicId);
                   							binfo.setBibliographicDescription(bibDesc);
                        					
                   							List<HoldingsSet> holdingsSetList = new ArrayList<HoldingsSet>();
                         					
                   							HoldingsSet holdingsSet = new HoldingsSet();
                   							holdingsSet.setItemInformations(itemInformationList);
                   							holdingsSetList.add(holdingsSet);
                        					
                   							binfo.setHoldingsSets(holdingsSetList);
                   							bibInformations.add(binfo);
                   							
                   							//LOG.debug("Update bibInformations for: " + bibNo);
                   				        	//responseData.setBibInformations(bibInformations);
                   							
          								} else {
          									LOG.error("LookupItemSet - " + itemCirculationStatus.recordStatus.returnMsg + " for record: " + processCount);
        									 problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
        							                    null, itemCirculationStatus.recordStatus.returnMsg + " for record: " + processCount);
        									 break;
        								}
        							} else {
        								LOG.error("LookupItemSet - Request: " + bibNo + " not found for record: " + processCount); 
        				                problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
        					                    null, "LookupItem - Request: " + bibNo + " not found for record: " + processCount);
        				                break;
        							}
        						} else {
        							LOG.error("LookupItemSet - " + lookupPage.recordStatus.returnMsg + " for record: " + processCount);
        							problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "LookupItem",
        					                null, lookupPage.recordStatus.returnMsg + " for record: " + processCount);
        							 break;
        						}
         					} 
       					 	else {
       					 		LOG.error("LookupItemSet - ItemId is required for record: " + processCount);
       					 		//((LookupItemSetResponseData) bibInformations).setProblems(NCIPHelper.generateProblem(Version1LookupItemProcessingError.UNKNOWN_ITEM, "//BibliographicRecordId", bibNo, "Bib # '" + bibNo + "' empty."));
       					 		problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
       			                    null, "LookupItemSet - ItemId is required for record: " + processCount);
       					 	 break;
       					 	} // End if (bibNo != null) 
        				} else {
            				LOG.error("LookupItemSet - AgencyId is missing for record: " + processCount);
            				problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
       			                    null, "LookupItemSet - AgencyId is missing for record: " + processCount);
            				 break;
        				} // End if (getAgencyId() != null)
        			} 
        			else {
        				LOG.error("LookupItemSet - BibliographicRecordId is missing for record: " + processCount);
        				problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
   			                    null, "LookupItemSet - BibliographicRecordId is missing for record: " + processCount);
        				 break;
        			} // End if (bibId.getBibliographicRecordId() != null)
        		} // End if (currentItem < maximumItemsCount)
        		processCount++;
         	} // End For Loop
        } // End initData.getBibliographicIds() != null
        
        if ( problems != null ) {
        	responseData.setProblems(problems);
        	return responseData;
        }
        else {
			LOG.debug("LookupItemSet - Completed updating all bibNo");
			if (currentItem > maximumItemsCount) {
				responseData.setNextItemToken(Integer.toString(currentItem));	
			}
			responseData.setBibInformations(bibInformations);
        	return responseData;
        }
	} // End LookupItemSetResponseData performService
} // End class
