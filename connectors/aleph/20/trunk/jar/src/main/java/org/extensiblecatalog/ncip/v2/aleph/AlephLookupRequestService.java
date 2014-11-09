package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

public class AlephLookupRequestService implements LookupRequestService {

	@Override
	public LookupRequestResponseData performService(LookupRequestInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {
		LookupRequestResponseData responseData = new LookupRequestResponseData();
		String itemId = initData.getItemId().getItemIdentifierValue();
		String patronId = initData.getUserId().getUserIdentifierValue();

		if (patronId == null || itemId == null) {
			if (patronId == null && itemId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Item nor User Id is undefined.");
			else if (itemId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST,
						"Item Id is undefined. Cannot cancel request for unknown item. Also Request Id is not supported by this service.");
			else if (patronId == null)
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined. Cannot lookup request with unknown user.");
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
			requestItem = alephRemoteServiceManager.lookupRequest(initData);

			if (requestItem.getProblem() != null) {
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(requestItem.getProblem());
				responseData.setProblems(problems);
			} else
				updateResponseData(responseData, initData, requestItem);

		} catch (IOException ie) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing IOException error"));
			p.setProblemDetail(ie.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (SAXException se) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing SAXException error"));
			p.setProblemDetail(se.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (AlephException ae) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing AlephException error"));
			p.setProblemDetail(ae.getMessage());
			List<Problem> problems = new ArrayList<Problem>();
			problems.add(p);
			responseData.setProblems(problems);
		} catch (ParserConfigurationException pce) {
			Problem p = new Problem();
			p.setProblemType(new ProblemType("Processing ParserConfigurationException error"));
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

	private void updateResponseData(LookupRequestResponseData responseData, LookupRequestInitiationData initData, AlephRequestItem requestItem) {
		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		responseData.setRequestScopeType(requestItem.getRequestScopeType());
		responseData.setRequestType(requestItem.getRequestType());
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields()); 

		// Not implemented services, most of them probably even not implementable
		responseData.setDateAvailable(requestItem.getDateAvailable());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setShippingInformation(requestItem.getShippingInformation());
		responseData.setAcknowledgedFeeAmount(requestItem.getAcknowledgedFeeAmout());
		responseData.setDateOfUserRequest(requestItem.getDateOfUserRequest());
		responseData.setEarliestDateNeeded(requestItem.getEarliestDateNeeded());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setNeedBeforeDate(requestItem.getNeedBeforeDate());
		responseData.setPaidFeeAmount(requestItem.getPaidFeeAmount());
		responseData.setPickupDate(requestItem.getHoldPickupDate());
		responseData.setPickupExpiryDate(requestItem.getPickupExpiryDate());
		responseData.setPickupLocation(requestItem.getPickupLocation());
		responseData.setRequestStatusType(requestItem.getRequestStatusType());
	}
}
