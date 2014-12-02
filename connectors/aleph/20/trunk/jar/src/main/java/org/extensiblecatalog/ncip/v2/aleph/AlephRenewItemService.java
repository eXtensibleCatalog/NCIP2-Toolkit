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

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephRenewItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RenewItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RenewItemService;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.xml.sax.SAXException;

public class AlephRenewItemService implements RenewItemService {

	@Override
	public RenewItemResponseData performService(RenewItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		boolean itemIdIsEmpty = initData.getItemId() == null || initData.getItemId().getItemIdentifierValue().isEmpty();
		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();

		if (itemIdIsEmpty || userIdIsEmpty) {
			if (itemIdIsEmpty)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Item Id is undefined. Cannot renew unknown item.");
			else
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined. Cannot renew item loaned by unknown user.");
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final RenewItemResponseData responseData = new RenewItemResponseData();

		try {
			AlephRenewItem renewItem = alephRemoteServiceManager.renewItem(initData);

			if (renewItem.getProblem() == null) {
				updateResponseData(responseData, initData, renewItem);
			} else
				responseData.setProblems(Arrays.asList(renewItem.getProblem()));

		} catch (IOException ie) {
			Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
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

	private void updateResponseData(RenewItemResponseData responseData, RenewItemInitiationData initData, AlephRenewItem renewItem) {

		ResponseHeader responseHeader = AlephUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		responseData.setItemOptionalFields(renewItem.getItemOptionalFields());
		responseData.setUserOptionalFields(renewItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(renewItem.getFiscalTransactionInfo());
		responseData.setDateDue(renewItem.getDateDue());
		responseData.setDateForReturn(renewItem.getDateForReturn());
		responseData.setPending(renewItem.getPending());
		responseData.setRenewalCount(renewItem.getRenewalCount());
		responseData.setRequiredFeeAmount(renewItem.getRequiredFeeAmount());
		responseData.setRequiredItemUseRestrictionTypes(renewItem.getRequiredItemUseRestrictionTypes());
	}
}
