/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.aspectj.weaver.ArrayAnnotationValue;
import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0.jaxb.elements.ItemTransaction;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.Version1ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

/**
 * This class implements the Lookup Item service for the Koha back-end connector. Basically this just calls the KohaRemoteServiceManager to get hard-coded data (e.g. title, call
 * #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not use this class as an example. See the NCIP toolkit Connector developer's
 * documentation for guidance.
 */
public class KohaLookupItemService implements LookupItemService {

	/**
	 * Handles a NCIP LookupItem service by returning data from Koha.
	 *
	 * @param initData
	 *            the LookupItemInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupItemResponseData
	 */
	@Override
	public LookupItemResponseData performService(LookupItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final LookupItemResponseData responseData = new LookupItemResponseData();

		boolean itemIdIsEmpty = initData.getItemId().getItemIdentifierValue().isEmpty();

		if (itemIdIsEmpty) {

			Problem p = new Problem(new ProblemType("Item id is undefined."), null, null);
			responseData.setProblems(Arrays.asList(p));

		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				JSONObject kohaItem = kohaRemoteServiceManager.lookupItem(initData);
				updateResponseData(initData, responseData, kohaItem);

			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (KohaException ke) {
				Problem p = new Problem(new ProblemType(ke.getShortMessage()), null, ke.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}
		}
		return responseData;
	}

	protected void updateResponseData(LookupItemInitiationData initData, LookupItemResponseData responseData, JSONObject kohaItem) throws ServiceException, KohaException {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		ItemOptionalFields iof = new ItemOptionalFields();

		boolean itemInfoDesired = initData.getBibliographicDescriptionDesired() || initData.getItemDescriptionDesired() || initData.getLocationDesired();
		if (itemInfoDesired) {
			JSONObject itemInfo = (JSONObject) kohaItem.get("itemInfo");

			String itemId = (String) itemInfo.get("itemnumber");
			String agencyId = (String) itemInfo.get("holdingbranch");

			responseData.setItemId(KohaUtil.createItemId(itemId, agencyId));

			if (initData.getLocationDesired()) {
				String homeBranch = (String) itemInfo.get("homebranch");
				String locationVal = (String) itemInfo.get("location");
				if (homeBranch != null || locationVal != null) {
					iof.setLocations(KohaUtil.createLocations(homeBranch, locationVal));
				}
			}

			if (initData.getItemDescriptionDesired()) {
				ItemDescription itemDescription = new ItemDescription();

				String callNumber = (String) itemInfo.get("callnumber");
				String copyNumber = (String) itemInfo.get("copynumber");

				itemDescription.setCallNumber(callNumber);
				itemDescription.setCopyNumber(copyNumber);

				iof.setItemDescription(itemDescription);
			}

			if (initData.getBibliographicDescriptionDesired())
				iof.setBibliographicDescription(KohaUtil.parseBibliographicDescription(itemInfo));

		} else
			responseData.setItemId(initData.getItemId());

		if (initData.getCirculationStatusDesired()) {
			String circulationStatus = (String) kohaItem.get("circulationStatus");
			iof.setCirculationStatus(Version1CirculationStatus.find(Version1CirculationStatus.VERSION_1_CIRCULATION_STATUS, circulationStatus));
		}

		if (initData.getHoldQueueLengthDesired()) {
			String holdQueueLength = (String) kohaItem.get("holdQueueLength");

			if (holdQueueLength != null)
				iof.setHoldQueueLength(new BigDecimal(holdQueueLength));
		}

		if (initData.getItemUseRestrictionTypeDesired()) {
			JSONArray itemUseRestrictions = (JSONArray) kohaItem.get("itemUseRestrictions");
			if (itemUseRestrictions != null && itemUseRestrictions.size() != 0) {
				List<ItemUseRestrictionType> itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();
				for (Object itemUseRestriction : itemUseRestrictions) {
					String itemUseRestrictionValue = (String) itemUseRestriction;
					itemUseRestrictionTypes.add(Version1ItemUseRestrictionType.find(Version1ItemUseRestrictionType.VERSION_1_ITEM_USE_RESTRICTION_TYPE, itemUseRestrictionValue));
				}
				iof.setItemUseRestrictionTypes(itemUseRestrictionTypes);
			}
		}
		responseData.setItemOptionalFields(iof);
	}
}
