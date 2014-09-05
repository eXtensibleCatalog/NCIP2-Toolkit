package org.extensiblecatalog.ncip.v2.aleph;

import java.net.MalformedURLException;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.service.*;

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

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId(0)); // TODO: Find out why initiation data can have multiple itemId, but response don't

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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlephException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		updateResponseData(responseData, initData, requestItem);

		return responseData;
	}

	private void updateResponseData(RequestItemResponseData responseData, RequestItemInitiationData initData, AlephRequestItem requestItem) {
		
		//TODO: fix setting not set values or not wanted by input request XML
		responseData.setDateAvailable(requestItem.getDateAvailable());
		responseData.setFiscalTransactionInformation(requestItem.getFiscalTransactionInfo());
		responseData.setHoldPickupDate(requestItem.getHoldPickupDate());
		responseData.setHoldQueueLength(requestItem.getHoldQueueLength());
		responseData.setHoldQueuePosition(requestItem.getHoldQueuePosition());
		
		if(requestItem.getItemId() != null)
		responseData.setItemId(requestItem.getItemId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		
		if(requestItem.getUserId() != null)
		responseData.setUserId(requestItem.getUserId());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setRequestScopeType(requestItem.getRequestScopeType());
		responseData.setRequestType(requestItem.getRequestType());
		responseData.setRequiredFeeAmount(requestItem.getRequiredFeeAmount());
		responseData.setRequiredItemUseRestrictionTypes(requestItem.getItemUseRestrictionTypes());
		responseData.setShippingInformation(requestItem.getShippingInformation());
	}
}














