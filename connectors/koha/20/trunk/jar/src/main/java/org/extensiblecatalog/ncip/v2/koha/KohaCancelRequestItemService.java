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
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemResponseData;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.xml.sax.SAXException;

public class KohaCancelRequestItemService implements CancelRequestItemService {

	@Override
	public CancelRequestItemResponseData performService(CancelRequestItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		final CancelRequestItemResponseData responseData = new CancelRequestItemResponseData();

		boolean itemIdIsEmpty = initData.getItemId() == null || initData.getItemId().getItemIdentifierValue().isEmpty();
		boolean userIdIsEmpty = initData.getUserId() == null || initData.getUserId().getUserIdentifierValue().isEmpty();

		if (itemIdIsEmpty || userIdIsEmpty) {

			List<Problem> problems = new ArrayList<Problem>();

			if (itemIdIsEmpty) {

				Problem p = new Problem(new ProblemType("Item id is undefined."), null, null,
						"Cannot cancel request for unknown item. Also note that Request Id is not supported by this service.");
				problems.add(p);

			}
			if (userIdIsEmpty) {

				Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Cannot cancel request to unknown user.");
				problems.add(p);

			}
			responseData.setProblems(problems);

		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				MarcItem requestItem = kohaRemoteServiceManager.cancelRequestItem(initData);

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
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}
		}
		return responseData;
	}

	private void updateResponseData(CancelRequestItemResponseData responseData, CancelRequestItemInitiationData initData, MarcItem requestItem) {

		ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

		if (responseHeader != null)
			responseData.setResponseHeader(responseHeader);

		responseData.setUserId(initData.getUserId());
		responseData.setItemId(initData.getItemId());
		/*
		responseData.setRequestId(requestItem.getRequestId());
		responseData.setItemOptionalFields(requestItem.getItemOptionalFields());
		responseData.setUserOptionalFields(requestItem.getUserOptionalFields());
		responseData.setFiscalTransactionInformation(requestItem.getFiscalTransactionInfo());
		*/
	}
}
