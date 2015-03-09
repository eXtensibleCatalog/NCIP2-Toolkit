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
import org.extensiblecatalog.ncip.v2.koha.util.RequestDetails;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestElementType;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1RequestElementType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestScopeType;
import org.xml.sax.SAXException;

public class KohaLookupRequestService implements LookupRequestService {

	@Override
	public LookupRequestResponseData performService(LookupRequestInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		final LookupRequestResponseData responseData = new LookupRequestResponseData();

		boolean itemIdIsEmpty = initData.getItemId() == null || initData.getItemId().getItemIdentifierValue().isEmpty();
		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();

		if (itemIdIsEmpty || userIdIsEmpty) {

			List<Problem> problems = new ArrayList<Problem>();

			if (itemIdIsEmpty) {

				Problem p = new Problem(new ProblemType("Item id is undefined."), null, null, "Cannot lookup request of unknown item. ");
				problems.add(p);

			}
			if (userIdIsEmpty) {

				Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Cannot lookup request with unknown user.");
				problems.add(p);

			}
			responseData.setProblems(problems);

		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				MarcItem requestItem = kohaRemoteServiceManager.lookupRequest(initData);

				if (KohaUtil.parseProblems(requestItem) == null) {
					updateResponseData(responseData, initData, requestItem);
				} else
					responseData.setProblems(Arrays.asList(KohaUtil.parseProblems(requestItem)));

			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (KohaException ae) {
				Problem p = new Problem(new ProblemType("Processing KohaException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}
		}

		return responseData;
	}

	private void updateResponseData(LookupRequestResponseData responseData, LookupRequestInitiationData initData, MarcItem requestItem) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		RequestDetails requestDetails = KohaUtil.parseRequestDetails(requestItem);

		for (RequestElementType desiredService : initData.getRequestElementTypes()) {

			if (desiredService.equals(Version1RequestElementType.ACKNOWLEDGED_FEE_AMOUNT)) {
				responseData.setAcknowledgedFeeAmount(requestDetails.getAcknowledgedFeeAmout());
			} else if (desiredService.equals(Version1RequestElementType.DATE_AVAILABLE)) {
				// responseData.setDateAvailable(requestItem.getDateAvailable());
			} else if (desiredService.equals(Version1RequestElementType.DATE_OF_USER_REQUEST)) {
				responseData.setDateOfUserRequest(requestDetails.getDatePlaced());
			} else if (desiredService.equals(Version1RequestElementType.EARLIEST_DATE_NEEDED)) {
				responseData.setEarliestDateNeeded(requestDetails.getEarliestDateNeeded());
			} else if (desiredService.equals(Version1RequestElementType.HOLD_QUEUE_POSITION)) {
				// responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
			} else if (desiredService.equals(Version1RequestElementType.NEED_BEFORE_DATE)) {
				responseData.setNeedBeforeDate(requestDetails.getNeedBeforeDate());
			} else if (desiredService.equals(Version1RequestElementType.PAID_FEE_AMOUNT)) {
				responseData.setPaidFeeAmount(requestDetails.getPaidFeeAmount());
			} else if (desiredService.equals(Version1RequestElementType.PICKUP_DATE)) {
				responseData.setPickupDate(requestDetails.getPickupDate());
			} else if (desiredService.equals(Version1RequestElementType.PICKUP_EXPIRY_DATE)) {
				responseData.setPickupExpiryDate(requestDetails.getPickupExpiryDate());
			} else if (desiredService.equals(Version1RequestElementType.PICKUP_LOCATION)) {
				responseData.setPickupLocation(requestDetails.getPickupLocation());
			} else if (desiredService.equals(Version1RequestElementType.REQUEST_SCOPE_TYPE)) {
				responseData.setRequestScopeType(Version1RequestScopeType.ITEM);
			} else if (desiredService.equals(Version1RequestElementType.REQUEST_STATUS_TYPE)) {
				responseData.setRequestStatusType(requestDetails.getRequestStatusType());
			} else if (desiredService.equals(Version1RequestElementType.REQUEST_TYPE)) {
				responseData.setRequestType(initData.getRequestType());
			} else if (desiredService.equals(Version1RequestElementType.SHIPPING_INFORMATION)) {
				// responseData.setShippingInformation(requestItem.getShippingInformation());
			} else if (desiredService.equals(Version1RequestElementType.USER_ID)) {
				responseData.setUserId(initData.getUserId());
			}
		}

		responseData.setItemId(initData.getItemId());
		/*
		responseData.setRequestId(requestItem.getRequestId());

		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());

		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());
		*/
	}
}
