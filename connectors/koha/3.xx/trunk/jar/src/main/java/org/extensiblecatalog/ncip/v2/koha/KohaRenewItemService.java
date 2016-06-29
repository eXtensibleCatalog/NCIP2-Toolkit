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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RenewItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RenewItemService;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.json.simple.JSONObject;
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
				JSONObject renewItem = kohaRemoteServiceManager.renewItem(initData);

				updateResponseData(responseData, initData, renewItem);

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
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, StringUtils.join(e.getStackTrace(), "\n"));
				responseData.setProblems(Arrays.asList(p));
			}
		}
		return responseData;
	}

	private void updateResponseData(RenewItemResponseData responseData, RenewItemInitiationData initData, JSONObject renewItem) throws ParseException {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		if (LocalConfig.useRestApiInsteadOfSvc()) {

			String userId = (String) renewItem.get("borrowernumber");
			String itemId = (String) renewItem.get("itemnumber");
			String branch = (String) renewItem.get("branchcode");
			String dateDue = (String) renewItem.get("date_due");
			String dateForReturn = (String) renewItem.get("returndate");
			
			BigDecimal renewalCount = new BigDecimal((String) renewItem.get("renewals"));
			
			responseData.setDateDue(KohaUtil.parseGregorianCalendarFromKohaLongDate(dateDue));
			responseData.setDateForReturn(KohaUtil.parseGregorianCalendarFromKohaLongDate(dateForReturn));
			responseData.setRenewalCount(renewalCount);
			
			responseData.setUserId(KohaUtil.createUserId(userId, branch));
			responseData.setItemId(KohaUtil.createItemId(itemId, branch));

		} else {
			String userId = (String) renewItem.get("userId");
			String itemId = (String) renewItem.get("itemId");
			String branch = (String) renewItem.get("branchcode");
			String dateDue = (String) renewItem.get("dateDue");

			responseData.setDateDue(KohaUtil.parseGregorianCalendarFromKohaDateWithBackslashes(dateDue));
			responseData.setUserId(KohaUtil.createUserId(userId, branch));
			responseData.setItemId(KohaUtil.createItemId(itemId, branch));
		}
		
		/*
		responseData.setItemOptionalFields(renewItem.getItemOptionalFields());
		responseData.setUserOptionalFields(renewItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(renewItem.getFiscalTransactionInfo());
		responseData.setPending(renewItem.getPending());
		responseData.setRenewalCount(renewItem.getRenewalCount());
		responseData.setRequiredFeeAmount(renewItem.getRequiredFeeAmount());
		responseData.setRequiredItemUseRestrictionTypes(renewItem.getRequiredItemUseRestrictionTypes());
		*/
	}
}
