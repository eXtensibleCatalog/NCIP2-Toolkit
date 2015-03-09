/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.koha.item.MarcItem;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RenewItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RenewItemService;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.xml.sax.SAXException;

public class KohaRenewItemService implements RenewItemService {

	@Override
	public RenewItemResponseData performService(RenewItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final RenewItemResponseData responseData = new RenewItemResponseData();

		boolean itemIdIsEmpty = initData.getItemId() == null || initData.getItemId().getItemIdentifierValue().isEmpty();
		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();

		if (itemIdIsEmpty || userIdIsEmpty) {

			List<Problem> problems = new ArrayList<Problem>();

			if (itemIdIsEmpty) {

				Problem p = new Problem(new ProblemType("Item id is undefined."), null, null, "Cannot renew unknown item.");
				problems.add(p);

			}
			if (userIdIsEmpty) {

				Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Cannot renew item for unknown user.");
				problems.add(p);

			}
			responseData.setProblems(problems);

		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				MarcItem renewItem = kohaRemoteServiceManager.renewItem(initData);

				if (renewItem != null) {
					updateResponseData(responseData, initData, renewItem);
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

	private void updateResponseData(RenewItemResponseData responseData, RenewItemInitiationData initData, MarcItem renewItem) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		/*
		responseData.setItemOptionalFields(renewItem.getItemOptionalFields());
		responseData.setUserOptionalFields(renewItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(renewItem.getFiscalTransactionInfo());
		responseData.setDateDue(renewItem.getDateDue());
		responseData.setDateForReturn(renewItem.getDateForReturn());
		responseData.setPending(renewItem.getPending());
		responseData.setRenewalCount(renewItem.getRenewalCount());
		responseData.setRequiredFeeAmount(renewItem.getRequiredFeeAmount());
		responseData.setRequiredItemUseRestrictionTypes(renewItem.getRequiredItemUseRestrictionTypes());
		*/
	}
}
