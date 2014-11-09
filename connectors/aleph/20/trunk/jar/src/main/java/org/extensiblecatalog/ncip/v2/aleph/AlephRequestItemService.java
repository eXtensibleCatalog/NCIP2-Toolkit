package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

public class AlephRequestItemService implements RequestItemService {

	@Override
	public RequestItemResponseData performService(RequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		RequestItemResponseData responseData = new RequestItemResponseData();

		String patronId = initData.getUserId().getUserIdentifierValue();

		if (patronId == "") {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is empty.");
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

		AlephRequestItem requestItem = null;
		try {
			requestItem = alephRemoteServiceManager.requestItem(initData);

			if (requestItem.getProblem() != null || requestItem == null) {
				List<Problem> problems = new ArrayList<Problem>();
				if (requestItem != null)
					problems.add(requestItem.getProblem());
				else
					throw new AlephException("Unknown request item returned by connector.");
				responseData.setProblems(problems);
				responseData.setRequiredFeeAmount(requestItem.getRequiredFeeAmount());
				responseData.setRequiredItemUseRestrictionTypes(requestItem.getItemUseRestrictionTypes());
			} else
				updateResponseData(responseData, initData, requestItem);

		} catch (IOException ie) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing IOException error."));
			p.setProblemDetail(ie.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (SAXException se) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing SAXException error."));
			p.setProblemDetail(se.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (AlephException ae) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing AlephException error."));
			p.setProblemDetail(ae.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (ParserConfigurationException pce) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing ParserConfigurationException error."));
			p.setProblemDetail(pce.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (Exception e) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Unknown processing exception error."));
			p.setProblemDetail(e.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		}

		return responseData;
	}

	private void updateResponseData(RequestItemResponseData responseData, RequestItemInitiationData initData, AlephRequestItem requestItem) {

		responseData.setUserId(initData.getUserId());
		if (initData.getItemIds().size() > 1) {
			// If there was more than one requested item, then merge all item identifier's values into one because responseData does not support
			// multiple ItemIds
			String joinedItemIds = "";
			int itemIdsSize = initData.getItemIds().size();
			for (int i = 0; i < itemIdsSize; i++) {
				if (i != itemIdsSize - 1) {
					joinedItemIds += initData.getItemId(i).getItemIdentifierValue() + AlephConstants.REQUEST_ID_DELIMITER;
				} else
					joinedItemIds += initData.getItemId(i).getItemIdentifierValue();
			}
			ItemId itemId = new ItemId();
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			itemId.setItemIdentifierValue(joinedItemIds);
			responseData.setItemId(itemId);
		} else
			responseData.setItemId(initData.getItemId(0));

		responseData.setRequestScopeType(requestItem.getRequestScopeType());
		responseData.setRequestType(requestItem.getRequestType());
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(requestItem.getFiscalTransactionInfo());

		// Not implemented services, most of them probably even not implementable in Aleph logic
		responseData.setDateAvailable(requestItem.getDateAvailable());
		responseData.setHoldPickupDate(requestItem.getHoldPickupDate());
		responseData.setHoldQueueLength(requestItem.getHoldQueueLength());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setShippingInformation(requestItem.getShippingInformation());
	}
}
