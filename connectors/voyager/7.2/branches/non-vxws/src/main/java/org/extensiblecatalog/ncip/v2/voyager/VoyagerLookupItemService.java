/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.voyager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.Constants;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;

/**
 * This class implements the Lookup Item service for the Voyager back-end connector. Basically this just
 * calls the VoyagerRemoteServiceManager to get data (e.g. title, call #, etc.).
 * 
 * @author SharmilaR
 */
public class VoyagerLookupItemService implements LookupItemService {
	
	static Logger log = Logger.getLogger(VoyagerLookupItemService.class);

    /**
     * Construct a VoyagerRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public VoyagerLookupItemService() {
    }

    /**
     * Handles a NCIP LookupItem service by returning data from voyager.
     *
     * @param initData       the LookupItemInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupItemResponseData
     */
    @Override
    public LookupItemResponseData performService (LookupItemInitiationData initData,
                                                 RemoteServiceManager serviceManager)
		throws ServiceException {

        LookupItemResponseData responseData = new LookupItemResponseData();
        
        // Execute request if agency Id is blank or NRU
        if ( initData.getItemId().getAgencyId() != null 
        		&& !initData.getItemId().getAgencyId().getValue().equalsIgnoreCase("") 
        		&& !initData.getItemId().getAgencyId().getValue().equalsIgnoreCase(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY))) {
        	throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST,
        			"This request cannot be processed. Agency ID is invalid.Agency ID configured is " 
        				+ NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY));
        }
        	

        VoyagerRemoteServiceManager voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        String itemId = initData.getItemId().getItemIdentifierValue();
        
        try {
	        if (voyagerSvcMgr.getItemCount(itemId) <= 0) {
				
				Problem problem = new Problem();
				problem.setProblemValue("Unknown Item.");
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(problem);
				responseData.setProblems(problems);
				return responseData;
	        }
	
	        ItemId id = new ItemId();
	        id.setItemIdentifierValue(itemId);
	        id.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(Constants.CONFIG_ILS_DEFAULT_AGENCY)));
	        
	        responseData.setItemId(id);
	        
	        ItemOptionalFields iof = null;
	        if (initData.getBibliographicDescriptionDesired()) {
	        	BibliographicDescription bDesc = voyagerSvcMgr.getBibliographicDescription(itemId);
	        	
	        	if (bDesc != null) {
	        		if (iof == null) {
	        			iof = new ItemOptionalFields();
	        		}
	        		iof.setBibliographicDescription(bDesc);
	        	}
	        }
	
	        if (initData.getCirculationStatusDesired()) {
	        	String status = voyagerSvcMgr.getCirculationStatus(itemId);
	        	
	        	if (iof == null) {
	    			iof = new ItemOptionalFields();
	    		} 

	        	iof.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, status));
	        	
	        }
	
	        if (initData.getHoldQueueLengthDesired()) {
	        	if (iof == null) {
	    			iof = new ItemOptionalFields();
	    		} 
	        	
	        	iof.setHoldQueueLength(voyagerSvcMgr.getHoldQueueLength(itemId));
	        }
	        
	        if (initData.getElectronicResourceDesired()) {
	        	if (iof == null) {
	    			iof = new ItemOptionalFields();
	    		} 
	        	
	        	iof.setElectronicResource(voyagerSvcMgr.getElectronicResource(itemId));
	        }
	
	        if (initData.getLocationDesired()) {
	        	List<Location> locations = new ArrayList<Location>();
	        	if (iof == null) {
	    			iof = new ItemOptionalFields();
	    		} 
	        	locations.add(voyagerSvcMgr.getPermanentLocation(itemId));
	        	locations.add(voyagerSvcMgr.getTemporaryLocation(itemId));
	        	iof.setLocations(locations);
	        }
	        
	        if (initData.getItemDescriptionDesired()) {
	        	if (iof == null) {
	    			iof = new ItemOptionalFields();
	    		} 
	        	iof.setItemDescription(voyagerSvcMgr.getItemDescription(itemId));
	        }
	        
	        if (iof != null) {
				responseData.setItemOptionalFields(iof);
			}
        } catch (ILSException e) {
        	Problem p = new Problem();
			p.setProblemType(new SchemeValuePair("Processing error"));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
        }


		return responseData;
    }

}
