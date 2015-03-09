/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0.jaxb.elements.ItemTransaction;
import org.extensiblecatalog.ncip.v2.koha.item.MarcItem;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
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
				MarcItem kohaItem = kohaRemoteServiceManager.lookupItem(initData);

				if (kohaItem != null) {
					updateResponseData(initData, responseData, kohaItem);
				} else {
					Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, "", "Item " + initData.getItemId().getItemIdentifierValue() + " was not found.");
					responseData.setProblems(Arrays.asList(p));
				}

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

	protected void updateResponseData(LookupItemInitiationData initData, LookupItemResponseData responseData, MarcItem marcItem) throws ServiceException, KohaException {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		responseData.setItemId(KohaUtil.parseItemId(marcItem));

		ItemOptionalFields iof = KohaUtil.parseItemOptionalFields(initData, marcItem);

		responseData.setItemOptionalFields(iof);
	}

}
