/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.*;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class AlephRenewItemService implements RenewItemService {

	@Override
	public RenewItemResponseData performService(RenewItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		RenewItemResponseData responseData = new RenewItemResponseData();

		String itemId = initData.getItemId().getItemIdentifierValue();
		String patronId = initData.getUserId().getUserIdentifierValue();

		if (patronId == null || itemId == null) {
			if (patronId == null && itemId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Item and User Id are undefined.");
			else if (itemId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Item Id is undefined.");
			else if (patronId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined.");
		}

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				responseHeader.setFromAgencyId(initiationHeader.getFromAgencyId());
				responseHeader.setToAgencyId(initiationHeader.getToAgencyId());
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
			responseData.setResponseHeader(responseHeader);
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		AlephRenewItem renewItem = null;
		try {
			renewItem = alephRemoteServiceManager.renewItem(initData);

			if (renewItem.getProblem() != null) {
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(renewItem.getProblem());
				responseData.setProblems(problems);
			} else
				updateResponseData(responseData, initData, renewItem);

		} catch (IOException ie) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing IOException error"));
			p.setProblemDetail(ie.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (SAXException se) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing SAXException error"));
			p.setProblemDetail(se.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (AlephException ae) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing AlephException error"));
			p.setProblemDetail(ae.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (ParserConfigurationException pce) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Procesing ParserConfigurationException error"));
			p.setProblemDetail(pce.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (Exception e) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Unknown procesing exception error"));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		}

		return responseData;
	}

	private void updateResponseData(RenewItemResponseData responseData, RenewItemInitiationData initData, AlephRenewItem renewItem) {
		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		responseData.setItemOptionalFields(renewItem.getItemOptionalFields());
		responseData.setUserOptionalFields(renewItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(renewItem.getFiscalTransactionInfo()); //TODO: Ask librarian when this service costs something & where to find those values
		responseData.setDateDue(renewItem.getDateDue());
		responseData.setDateForReturn(renewItem.getDateForReturn());
		responseData.setPending(renewItem.getPending());
		responseData.setRenewalCount(renewItem.getRenewalCount());
		responseData.setRequiredFeeAmount(renewItem.getRequiredFeeAmount());
		responseData.setRequiredItemUseRestrictionTypes(renewItem.getRequiredItemUseRestrictionTypes());
	}
}
