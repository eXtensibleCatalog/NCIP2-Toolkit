package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemService;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Version1ItemElementType;

public class AlephCancelRequestItemService implements CancelRequestItemService {

	@Override
	public CancelRequestItemResponseData performService(CancelRequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		CancelRequestItemResponseData responseData = new CancelRequestItemResponseData();
		responseData.setItemId(initData.getItemId());
		
		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;
		
		return responseData;
	}

}
