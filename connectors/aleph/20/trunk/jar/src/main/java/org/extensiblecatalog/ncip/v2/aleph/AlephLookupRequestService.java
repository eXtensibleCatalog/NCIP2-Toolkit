package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.xml.sax.SAXException;

public class AlephLookupRequestService implements LookupRequestService {

	@Override
	public LookupRequestResponseData performService(LookupRequestInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		boolean itemIdIsEmpty = initData.getItemId() == null || initData.getItemId().getItemIdentifierValue().isEmpty();
		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();

		if (itemIdIsEmpty || userIdIsEmpty) {
			if (itemIdIsEmpty)
				throw new ServiceException(
						ServiceError.UNSUPPORTED_REQUEST,
						"Item Id is undefined. Cannot lookup request for an unknown item. Note that Request Id is not supported by this service because Aleph cannot search for an request without the knowledge of UserId.");
			else
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "User Id is undefined. Cannot lookup request with unknown user.");
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final LookupRequestResponseData responseData = new LookupRequestResponseData();

		try {
			AlephRequestItem requestItem = alephRemoteServiceManager.lookupRequest(initData);

			if (requestItem.getProblem() == null) {
				updateResponseData(responseData, initData, requestItem);
			} else
				responseData.setProblems(Arrays.asList(requestItem.getProblem()));

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

	private void updateResponseData(LookupRequestResponseData responseData, LookupRequestInitiationData initData, AlephRequestItem requestItem) {

		ResponseHeader responseHeader = AlephUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		responseData.setRequestScopeType(requestItem.getRequestScopeType());
		responseData.setRequestType(requestItem.getRequestType());
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());

		responseData.setDateAvailable(requestItem.getDateAvailable());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setShippingInformation(requestItem.getShippingInformation());
		responseData.setAcknowledgedFeeAmount(requestItem.getAcknowledgedFeeAmout());
		responseData.setDateOfUserRequest(requestItem.getDatePlaced());
		responseData.setEarliestDateNeeded(requestItem.getEarliestDateNeeded());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		responseData.setNeedBeforeDate(requestItem.getNeedBeforeDate());
		responseData.setPaidFeeAmount(requestItem.getPaidFeeAmount());
		responseData.setPickupDate(requestItem.getPickupDate());
		responseData.setPickupExpiryDate(requestItem.getPickupExpiryDate());
		responseData.setPickupLocation(requestItem.getPickupLocation());
		responseData.setRequestStatusType(requestItem.getRequestStatusType());
	}
}
