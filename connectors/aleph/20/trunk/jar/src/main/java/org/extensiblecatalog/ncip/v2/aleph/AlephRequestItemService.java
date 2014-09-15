package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

public class AlephRequestItemService implements RequestItemService {

	@Override
	public RequestItemResponseData performService(RequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		RequestItemResponseData responseData = new RequestItemResponseData();

		String itemId = initData.getItemId(0).getItemIdentifierValue();
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

	private void updateResponseData(RequestItemResponseData responseData, RequestItemInitiationData initData, AlephRequestItem requestItem) {

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId(0)); // TODO: Figure out why initiation data can have multiple itemId, but response don't
		responseData.setRequestScopeType(requestItem.getRequestScopeType());
		responseData.setRequestType(requestItem.getRequestType());
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(requestItem.getFiscalTransactionInfo()); //TODO: Ask librarian when this service costs something & where to find those values 

		// Not implemented services, most of them probably even not implementable
		responseData.setDateAvailable(requestItem.getDateAvailable());
		responseData.setHoldPickupDate(requestItem.getHoldPickupDate());
		responseData.setHoldQueueLength(requestItem.getHoldQueueLength());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setShippingInformation(requestItem.getShippingInformation());
	}
}
