/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemTransaction;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.xml.sax.SAXException;

/**
 * This class implements the Lookup Item service for the Aleph back-end connector. Basically this just calls the AlephRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class AlephLookupItemService implements LookupItemService {

	/**
	 * Handles a NCIP LookupItem service by returning data from Aleph.
	 *
	 * @param initData
	 *            the LookupItemInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return LookupItemResponseData
	 */
	@Override
	public LookupItemResponseData performService(LookupItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		boolean itemIdIsEmpty = initData.getItemId().getItemIdentifierValue().isEmpty();

		if (itemIdIsEmpty) {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Item id is undefined.");
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final LookupItemResponseData responseData = new LookupItemResponseData();

		try {
			AlephItem alephItem = alephRemoteServiceManager.lookupItem(initData);

			// update NCIP response data with aleph item data
			if (alephItem != null) {
				updateResponseData(initData, responseData, alephItem);
			} else {
				Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, "", "Item " + initData.getItemId().getItemIdentifierValue() + " was not found.");
				responseData.setProblems(Arrays.asList(p));
			}

		} catch (IOException ie) {
			Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (SAXException se) {
			Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (AlephException ae) {
			Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (ParserConfigurationException pce) {
			Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (Exception e) {
			Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
			responseData.setProblems(Arrays.asList(p));
		}

		return responseData;
	}

	protected void updateResponseData(LookupItemInitiationData initData, LookupItemResponseData responseData, AlephItem alephItem) throws ServiceException {

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				// Reverse From/To AgencyId because of the request was processed (return to initiator)
				ToAgencyId toAgencyId = new ToAgencyId();
				toAgencyId.setAgencyIds(initiationHeader.getFromAgencyId().getAgencyIds());

				FromAgencyId fromAgencyId = new FromAgencyId();
				fromAgencyId.setAgencyIds(initiationHeader.getToAgencyId().getAgencyIds());

				responseHeader.setFromAgencyId(fromAgencyId);
				responseHeader.setToAgencyId(toAgencyId);
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
			responseData.setResponseHeader(responseHeader);
		}

		if (alephItem.getDateAvailablePickup() != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(alephItem.getDateAvailablePickup());
			if (AlephUtil.inDaylightTime())
				gc.add(Calendar.HOUR_OF_DAY, 2);
			responseData.setHoldPickupDate(gc);
		}

		responseData.setItemId(initData.getItemId());

		ItemOptionalFields iof = AlephUtil.getItemOptionalFields(alephItem);

		if (initData.getBibliographicDescriptionDesired()) {
			iof.setBibliographicDescription(AlephUtil.getBibliographicDescription(alephItem, initData.getItemId().getAgencyId()));
		}

		responseData.setItemOptionalFields(iof);

		ItemTransaction itemTransaction = AlephUtil.getItemTransaction(alephItem);
		responseData.setItemTransaction(itemTransaction);

	}

}
