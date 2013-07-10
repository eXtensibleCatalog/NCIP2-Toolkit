/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.ItemStatus;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.StatusString;
import org.extensiblecatalog.ncip.v2.millennium.MillenniumRemoteServiceManager.itemCirculation;
import org.extensiblecatalog.ncip.v2.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Lookup Item service for the Dummy back-end
 * connector. Basically this just calls the MillenniumRemoteServiceManager to
 * get hard-coded data (e.g. title, call #, etc.).
 * 
 * Note: If you're looking for a model of how to code your own ILS's NCIPService
 * classes, do not use this class as an example. See the NCIP toolkit Connector
 * developer's documentation for guidance.
 */
public class MillenniumLookupItemService implements LookupItemService {

	private static final Logger LOG = Logger.getLogger(MillenniumLookupItemService.class);

	/**
	 * Handles a NCIP LookupItem service by returning hard-coded data.
	 * 
	 * @param initData
	 *            the LookupItemInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupItemResponseData
	 * @throws ServiceException 
	 */
	@Override
	public LookupItemResponseData performService(LookupItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

        MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;
        MillenniumConfiguration MillenniumConfig = millenniumSvcMgr.getConfiguration();

		String getAgencyId = MillenniumConfig.getDefaultAgency();
		String[] getAgencyIdList = getAgencyId.split(",");
		String defaultAgencyId = null;
		boolean existAgencyId = false; // Check AgencyId on Request HTML
		boolean foundAgencyId = false; // check AgencyId with value list in Config file
		String agencyId = null;

		final LookupItemResponseData responseData = new LookupItemResponseData();
		List<Problem> problems = null; 

		String itemId = initData.getItemId().getItemIdentifierValue();
		LOG.debug("LookupItem - itemId = " + itemId);
		if (initData.getItemId().getAgencyId() != null) {
			agencyId = initData.getItemId().getAgencyId().getValue(); 
			LOG.debug("LookupItem - agencyId = " + agencyId);
			existAgencyId = true;
		} else {
			LOG.debug("LookupItem - agencyId = " + existAgencyId);
		}

		for (int x=0; x < getAgencyIdList.length; x++) {
			//LOG.debug("LookupItem - AgencyIdList[" + x + "]: " + getAgencyIdList[x].trim());
			defaultAgencyId = getAgencyIdList[0].trim();
			if (agencyId != null && agencyId.equals(getAgencyIdList[x].trim())) {
				foundAgencyId = true;
			} 
		}
		
		if (existAgencyId == false || (agencyId != null && foundAgencyId)) {
			LOG.debug("LookupItem - Without AgencyId or AgencyId is validated");
			// Echo back the same item id that came in
			if (itemId.length() > 0) {
				StatusString lookupPage = millenniumSvcMgr.getItemPage(itemId, "Bib Record");
				if (lookupPage.recordStatus.returnStatus) {
					LOG.debug("LookupItem - Success received lookup item page");
					if (lookupPage.statusValue.contains("No Such Record") == false) {
						// Put the bibliographic information into the response.
						BibliographicDescription bibDesc = new BibliographicDescription();
						itemCirculation itemCirculationStatus = millenniumSvcMgr.getItemStatusList(lookupPage.statusValue);
						if (itemCirculationStatus.recordStatus.returnStatus) {
							ArrayList<ItemStatus> itemStatusList = itemCirculationStatus.itemsList;
							int itemIndex = 0;
							for (int x=0; x < itemStatusList.size(); x++) {
								if (itemStatusList.get(x).itemCirStatus.toLowerCase().contains("available") || 
										itemStatusList.get(x).itemCirStatus.toLowerCase().contains("due") || 
										itemStatusList.get(x).itemCirStatus.toLowerCase().contains("on the web") || 
										itemStatusList.get(x).itemCirStatus.toLowerCase().contains("hold") || 
										itemStatusList.get(x).itemCirStatus.toLowerCase().contains("noncirculating") ||
										itemStatusList.get(x).itemCirStatus.toLowerCase().contains("not chk'd out")) {
									itemIndex = x;
									break;
								}
							}
							
							responseData.setItemId(initData.getItemId());
							String bibRecordId = itemId;
							BibliographicRecordId bibliographicRecordId = new BibliographicRecordId();
							bibliographicRecordId.setBibliographicRecordIdentifier(bibRecordId);
							if (agencyId != null) {
								defaultAgencyId = agencyId;
							}
							AgencyId agencyName = AgencyId.find(null, defaultAgencyId);
							bibliographicRecordId.setAgencyId(agencyName);
							List<BibliographicRecordId> bibliographicRecordIdList = new ArrayList<BibliographicRecordId>();
							bibliographicRecordIdList.add(bibliographicRecordId);
							bibDesc.setBibliographicRecordIds(bibliographicRecordIdList);

							// Note: This could fail if the bib record has a language code that's not in this NCIP scheme. J Bodfish
							Language language = Language.find(Version1Language.VERSION_1_LANGUAGE, millenniumSvcMgr.getBibLanguage(bibRecordId));
							bibDesc.setLanguage(language);

							// set the title
							bibDesc.setTitle(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Title"));
							
							// set the author
							bibDesc.setAuthor(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Author"));

							// set the publisher
							bibDesc.setPublisher(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Publisher"));
			
							// set the series
							bibDesc.setSeriesTitleNumber(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Series"));

							// Item Description
							ItemDescription itemDescription = new ItemDescription();

							// Assemble the ItemOptionalFields where most of the data is returned
							ItemOptionalFields itemOptionalFields = new ItemOptionalFields();	
							
							// Item Call No Status - Get the first item value only
							itemDescription.setCallNumber(itemStatusList.get(itemIndex).itemCallNoStatus);

							// There doesn't appear to be a XcMillenniumCirculationStatus class, so I've changed this
							// to the XcCirculationStatus class, but this may fail if Millennium has different statuses than are in the
							// XcCirculationStatus list. J Bodfish
							//itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, ilsCircStatus));
							//itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus, "AVAILABLE", values, svpClass));
							
							// Item Circulation Status - Get the first item value only
							if (itemStatusList.get(itemIndex).itemCirStatus.equals("Missing")) {
								LOG.error("LookupItem - Request: " + itemId + "has status is null");
								itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, "Available"));
							} else {
								LOG.error("LookupItem - Request: " + itemId + " has circulation: " + itemStatusList.get(itemIndex).itemCirStatus);
								itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, itemStatusList.get(itemIndex).itemCirStatus));

							}
							//itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, itemStatusList.get(itemIndex).itemCirStatus));
							// Item Location - Get the first item value only
							// 10/26/2011 @ 12:00 PM - Bach Nguyen - Added Location section below
							List<Location> locations = new ArrayList<Location>();
							Location location = new Location();
							LocationName locationName = new LocationName();
							List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
							LocationNameInstance locationNameInstance = new LocationNameInstance();
							locationNameInstance.setLocationNameValue(itemStatusList.get(itemIndex).itemLocStatus.trim());
							locationNameInstance.setLocationNameLevel(new BigDecimal(1));
							locationNameInstances.add(locationNameInstance);
							locationName.setLocationNameInstances(locationNameInstances);
							location.setLocationName(locationName);
							location.setLocationType(Version1LocationType.CURRENT_LOCATION);
							locations.add(location);
							itemOptionalFields.setLocations(locations);
							// 10/26/2011 @ 12:00 PM - Bach Nguyen - End Added Location section above
						
							// Set the ISBN
							BibliographicItemId bibliographicItemId = new BibliographicItemId();
							bibliographicItemId.setBibliographicItemIdentifier(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "ISBN"));
							bibliographicItemId.setBibliographicItemIdentifier(millenniumSvcMgr.getBibDescription(lookupPage.statusValue, "Description"));
							itemOptionalFields.setBibliographicDescription(bibDesc);
							//itemOptionalFields.setCirculationStatus(circStatus);
							itemOptionalFields.setItemDescription(itemDescription);
							responseData.setItemOptionalFields(itemOptionalFields);
							
						} else {
			                problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
				                    null, itemCirculationStatus.recordStatus.returnMsg);
						}	
					} else {
						LOG.error("LookupItem - Request: " + itemId + " not found!"); 
		                problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
			                    null, "Request: " + itemId + " not found!"); //initData.getRequestId().getRequestIdentifierValue()
					}
				} else {
					problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "LookupItem",
			                null, lookupPage.recordStatus.returnMsg); 
				}
			} else {
	            problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "LookupItem",
	                null, "Either ItemId or RequestId is required."); 
			} //End if (initData.getItemId() != null)
			
		} else {
			LOG.error("LookupItem - AgencyId: " + agencyId + " not found!");
			problems = ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM, "LookupItem",
                    null, "AgencyId: " + agencyId + " not found!");
		} // if (foundAgentId == false || (agentId != null && agentId.equals(getAgentId)))

        if ( problems != null ) {
            responseData.setProblems(problems);
               return responseData;
        }
        else {
           	return responseData;
        }
  	}
}
