package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.RequestItemService;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class KohaRequestItemService implements RequestItemService {

	@Override
	public RequestItemResponseData performService(RequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final RequestItemResponseData responseData = new RequestItemResponseData();

		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();
		boolean itemIdsisEmpty = initData.getItemIds() == null || initData.getItemIds().size() == 1 && initData.getItemId(0).getItemIdentifierValue().isEmpty();

		boolean bibIdIsEmpty = initData.getBibliographicIds() == null || initData.getBibliographicIds().size() == 1
				&& initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier().isEmpty();

		if (userIdIsEmpty || (itemIdsisEmpty && bibIdIsEmpty)) {
			List<Problem> problems = new ArrayList<Problem>();

			if (userIdIsEmpty) {

				Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Cannot request item with unknown user. Please specify UserId");
				problems.add(p);
			}

			if (itemIdsisEmpty && bibIdIsEmpty) {
				Problem p = new Problem(new ProblemType("ItemId & BilbiographicRecord are both undefined."), null, null,
						"Cannot request unknown item or record. Please specify BibliographicRecord or ItemId.");
				problems.add(p);
			}

			responseData.setProblems(problems);
		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				JSONObject requestItem = kohaRemoteServiceManager.requestItem(initData);
				updateResponseData(responseData, initData, requestItem);

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

	private void updateResponseData(RequestItemResponseData responseData, RequestItemInitiationData initData, JSONObject requestItem) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		String userId = (String) requestItem.get("userId");
		String itemId = (String) requestItem.get("itemId");
		String requestId = (String) requestItem.get("requestId");

		responseData.setUserId(KohaUtil.createUserId(userId));
		if (itemId != null)
			responseData.setItemId(KohaUtil.createItemId(itemId));
		responseData.setRequestId(KohaUtil.createRequestId(requestId));

		responseData.setRequestScopeType(initData.getRequestScopeType());
		responseData.setRequestType(initData.getRequestType());

	}
}
