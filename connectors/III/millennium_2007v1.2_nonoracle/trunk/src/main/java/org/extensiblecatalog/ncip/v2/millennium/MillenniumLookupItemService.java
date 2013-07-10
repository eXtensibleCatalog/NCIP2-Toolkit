/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

//import org.apache.http.HttpException;
import org.extensiblecatalog.ncip.v2.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Lookup Item service for the Dummy back-end
 * connector. Basically this just calls the MillenniumRemoteServiceManager to
 * get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService
 * classes, do not use this class as an example. See the NCIP toolkit Connector
 * developer's documentation for guidance.
 */
public class MillenniumLookupItemService implements LookupItemService {

	/**
	 * Construct a MillenniumRemoteServiceManager; this class is not
	 * configurable so there are no parameters.
	 */
	public MillenniumLookupItemService() {
	}

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
	public LookupItemResponseData performService(
			LookupItemInitiationData initData,
			RemoteServiceManager serviceManager) throws ServiceException {

		final LookupItemResponseData responseData = new LookupItemResponseData();

		String itemId = initData.getItemId().getItemIdentifierValue();

		MillenniumRemoteServiceManager millenniumSvcMgr = (MillenniumRemoteServiceManager) serviceManager;

		// Echo back the same item id that came in
		responseData.setItemId(initData.getItemId());

		// Put the bibliographic information into the response.
		BibliographicDescription bibDesc = new BibliographicDescription();

		// Set lookup page to the HTML
		String lookupPage = "";

		try {
			lookupPage = millenniumSvcMgr.getItemPage(itemId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// parse title from html
		String title = millenniumSvcMgr.getTitle(lookupPage);
		bibDesc.setTitle(title);

		String bibRecordId = millenniumSvcMgr.getBibRecordId(itemId);
		BibliographicRecordId bibliographicRecordId = new BibliographicRecordId();
		bibliographicRecordId.setBibliographicRecordIdentifier(bibRecordId);

		AgencyId agencyId = AgencyId.find(null,
				millenniumSvcMgr.getLibraryName());
		bibliographicRecordId.setAgencyId(agencyId);
    List<BibliographicRecordId> bibliographicRecordIdList = new ArrayList<BibliographicRecordId>();
    bibliographicRecordIdList.add(bibliographicRecordId);
		bibDesc.setBibliographicRecordIds(bibliographicRecordIdList);

    // Note: This could fail if the bib record has a language code that's not in this NCIP scheme. J Bodfish
		Language language = Language.find(Version1Language.VERSION_1_LANGUAGE,
				millenniumSvcMgr.getBibLanguage(bibRecordId));
		bibDesc.setLanguage(language);

		// set the author
		bibDesc.setAuthor(millenniumSvcMgr.getAuthor(lookupPage));

		// set the publisher
		millenniumSvcMgr.getBibDescription(lookupPage, "Imprint");
		bibDesc.setPublisher(millenniumSvcMgr.getImprint(lookupPage));

		// set the series
		bibDesc.setSeriesTitleNumber(millenniumSvcMgr.getBibDescription(
				lookupPage, "Series"));

		// Item information
		// Lookup the item's circulation status
		// CirculationStatus ilsCircStatus =
		// millenniumSvcMgr.getCirculationStatus(lookupPage);
		String ilsCircStatus = millenniumSvcMgr
				.getCirculationStatus(lookupPage);

		// Map from the Dummy ILS's circulation status values to the Scheme
		// Value Pair used in NCIP.
		CirculationStatus circStatus = null;
		/*switch (ilsCircStatus) {
		case ON_ORDER: {
			circStatus = CirculationStatus.IN_PROCESS;
			break;
		}
		case ON_SHELF: {
			circStatus = CirculationStatus.AVAILABLE_ON_SHELF;
			break;
		}
		case CHECKED_OUT: {
			circStatus = CirculationStatus.ON_LOAN;
			break;
		}
		case IN_TRANSIT: {
			circStatus = CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS;
			break;
		}
		case ON_THE_WEB: {
			// circStatus = CirculationStatus.
			break;
		}
		case NONCIRCULATING: {
			// circStatus = CirculationStatus.
			break;
		}
		default: {
			circStatus = CirculationStatus.CIRCULATION_STATUS_UNDEFINED;
			break;
		}
		}*/

		// Item Description
		String callNumber = millenniumSvcMgr.getCallNo(lookupPage);

		ItemDescription itemDescription = new ItemDescription();
		itemDescription.setCallNumber(callNumber);

		/*
		 * //Not doing anything with holdings at the moment String holdings =
		 * millenniumSvcMgr.getHoldings(itemId); HoldingsInformation
		 * holdingsInfo = new HoldingsInformation();
		 * holdingsInfo.setUnstructuredHoldingsData(holdings);
		 * itemDescription.setHoldingsInformation(holdingsInfo);
		 */

		// Assemble the ItemOptionalFields where most of the data is returned
		ItemOptionalFields itemOptionalFields = new ItemOptionalFields();

		//itemOptionalFields.setCirculationStatus(XcMillenniumCirculationStatus.find(XcMillenniumCirculationStatus.XC_CIRCULATION_STATUS, ilsCircStatus));
		//itemOptionalFields.setCirculationStatus(XcMillenniumCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, ilsCircStatus));
		//itemOptionalFields.setCirculationStatus(XcMillenniumCirculationStatus.AVAILABLE);
//		ArrayList values = new ArrayList(); 
//		values.add("AVAILABLE");		
		
//		XcMillenniumCirculationStatus.loadAll();

    // There doesn't appear to be a XcMillenniumCirculationStatus class, so I've changed this
    // to the XcCirculationStatus class, but this may fail if Millennium has different statuses than are in the
    // XcCirculationStatus list. J Bodfish
		itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, ilsCircStatus));
		//itemOptionalFields.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus, "AVAILABLE", values, svpClass));
		
		// Set the ISBN
		BibliographicItemId bibliographicItemId = new BibliographicItemId();
		bibliographicItemId.setBibliographicItemIdentifier(millenniumSvcMgr
				.getIsbn(lookupPage));

		// TODO lookupItem Set Location
		// Type: List
		//String strLocationName = "Test";

		itemOptionalFields.setBibliographicDescription(bibDesc);
		//itemOptionalFields.setCirculationStatus(circStatus);
		itemOptionalFields.setItemDescription(itemDescription);
		responseData.setItemOptionalFields(itemOptionalFields);

		return responseData;
	}

}
