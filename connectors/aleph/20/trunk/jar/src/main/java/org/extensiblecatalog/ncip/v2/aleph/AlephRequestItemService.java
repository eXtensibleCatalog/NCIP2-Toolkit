package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

public class AlephRequestItemService implements RequestItemService {

	@Override
	public RequestItemResponseData performService(RequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {
		
		RequestItemResponseData responseData = new RequestItemResponseData();
		responseData.setItemId(initData.getItemId(0)); // TODO: Find out why initiation data can have multiple itemId, but response don't
		
		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		return responseData;
	}

}
