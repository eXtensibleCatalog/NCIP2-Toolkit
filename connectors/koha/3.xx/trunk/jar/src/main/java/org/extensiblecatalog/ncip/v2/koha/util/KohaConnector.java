package org.extensiblecatalog.ncip.v2.koha.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.FutureTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.ilsdiv1_1.ILSDIvOneOneLookupUserResponseData;
import org.extensiblecatalog.ncip.v2.ilsdiv1_1.LoanedItemsHistory;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.ILSDIvOneOneLookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.ILSDIvOneOneLookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestType;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.Version1OrganizationNameType;
import org.extensiblecatalog.ncip.v2.service.Version1PhysicalAddressType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.Version1UnstructuredAddressType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class KohaConnector {

	private static JSONParser jsonParser;

	private static String currentSessionIdCookie = "";

	private static int loginAttempts;

	private boolean haveBeenHere = false;

	public KohaConnector() throws ServiceException {

		loginAttempts = 0;

		try {
			jsonParser = new JSONParser();
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(
					new Properties()).getConfiguration();
			KohaConfiguration kohaConfig = new KohaConfiguration(config);

			LocalConfig.setBlockOrTrapStringFormatForExpired(
					kohaConfig.getProperty(KohaConstants.CONF_STRING_FORMAT_FOR_EXPIRED));
			LocalConfig.setBlockOrTrapStringFormatForTotalfines(
					kohaConfig.getProperty(KohaConstants.CONF_STRING_FORMAT_FOR_TOTALFINES));
			LocalConfig.setBlockOrTrapStringFormatForDebarred(
					kohaConfig.getProperty(KohaConstants.CONF_STRING_FORMAT_FOR_DEBARRED));

			String localeToSet = kohaConfig.getProperty("locale");
			if (localeToSet != null)
				for (Locale locale : Locale.getAvailableLocales()) {
					if (locale.toString().equals(localeToSet)) {
						Locale.setDefault(locale);
						break;
					}
				}

			LocalConfig.setTransferBranchesTime(kohaConfig.getProperty(KohaConstants.CONF_TRANSFER_BRANCH_TIME));

			LocalConfig.setDefaultAgency(kohaConfig.getProperty(KohaConstants.CONF_DEFAULT_AGENCY));

			LocalConfig.setCurrencyCode(kohaConfig.getProperty(KohaConstants.CONF_CURRENCY_CODE));

			LocalConfig.setOpacServerName(kohaConfig.getProperty(KohaConstants.CONF_INTRANET_SERVER));

			LocalConfig.setIntranetServerPort(kohaConfig.getProperty(KohaConstants.CONF_INTRANET_PORT));

			LocalConfig.setAdminName(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_NAME));
			LocalConfig.setAdminPass(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_PASS));

			LocalConfig.setSvcSuffix(kohaConfig.getProperty(KohaConstants.CONF_SVC_SUFFIX));
			LocalConfig.setUseRestApiInsteadOfSvc(
					Boolean.parseBoolean(kohaConfig.getProperty(KohaConstants.CONF_USE_REST_API_INSTEAD_OF_SVC)));

			LocalConfig.setAgencyAddress(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_UNSTRUCTURED_ADDRESS));
			LocalConfig.setAgencyName(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_TRANSLATED_NAME));

			LocalConfig.setUserRegistrationLink(kohaConfig.getProperty(KohaConstants.CONF_USER_REGISTRATION_LINK));
			LocalConfig.setAuthDataFormatType(kohaConfig.getProperty(KohaConstants.CONF_AUTH_DATA_FORMAT_TYPE));

			LocalConfig.setTokenExpirationTime(
					Integer.parseInt(kohaConfig.getProperty(KohaConstants.CONF_NEXT_ITEM_TOKEN_EXPIRATION_TIME)));

			LocalConfig.setEchoParticularProblemsToLUIS(Boolean
					.parseBoolean(kohaConfig.getProperty(KohaConstants.CONF_INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS)));

			try {
				LocalConfig.setMaxItemPreparationTimeDelay(
						Integer.parseInt(kohaConfig.getProperty(KohaConstants.CONF_MAX_ITEM_PREPARATION_TIME_DELAY)));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				// This try block checks for "TrustAllCertificates" in
				// configuration & if it is set to "true"

				if (Boolean.parseBoolean(kohaConfig.getProperty(KohaConstants.CONF_TRUST_ALL_CERTIFICATES))) {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
					SSLContext.setDefault(ctx);

					HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (ToolkitException e) {
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Toolkit configuration failed.");

		} catch (Exception e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR,
					"Failed to initialize SAX Parser from SAXParserFactory." + e1.getMessage());
		}
	}

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public JSONObject authenticateUser(String userId, String pw)
			throws MalformedURLException, KohaException, IOException, SAXException, URISyntaxException, ParseException {
		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_USER);

		urlBuilder.addRequest(KohaConstants.PARAM_USER_ID, userId);
		urlBuilder.addRequest(KohaConstants.PARAM_PW, pw);

		urlBuilder.addRequest(KohaConstants.PARAM_BOR_NO_DESIRED);

		String rawResponse = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(rawResponse);
	}

	/**
	 * @param patronId
	 * @param {@link
	 * 			LookupUserInitiationData} initData
	 * @return {@link KohaUser}
	 * @throws MalformedURLException
	 * @throws KohaException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParseException
	 * @throws URISyntaxException
	 * @throws java.text.ParseException
	 */
	public JSONObject lookupUser(ILSDIvOneOneLookupUserInitiationData initData) throws MalformedURLException,
			KohaException, IOException, SAXException, URISyntaxException, ParseException, java.text.ParseException {

		String patronId = initData.getUserId().getUserIdentifierValue();

		boolean loanedItemsDesired = initData.getLoanedItemsDesired();
		boolean requestedItemsDesired = initData.getRequestedItemsDesired();
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		boolean loanedItemsHistoryDesired = initData.getHistoryDesired() != null;

		if (LocalConfig.useRestApiInsteadOfSvc()) {
			URL patronRestUrl = new RestApiUrlBuilder().getPatron(patronId);

			JSONObject userInfo = (JSONObject) getJSONResponseFor(patronRestUrl, "GET");

			if (loanedItemsDesired) {
				URL checkoutsRestUrl = new RestApiUrlBuilder().getCheckouts(patronId);

				try {
					JSONArray checkouts = (JSONArray) getJSONResponseFor(checkoutsRestUrl, "GET");

					userInfo.put("checkouts", checkouts);
				} catch (ParseException e) {

				}
			}

			if (loanedItemsHistoryDesired) {

				URL checkoutsHistoryUrl = new RestApiUrlBuilder().getCheckoutsHistoryForPatron(patronId);

				JSONArray checkoutsHistory;
				try {
					checkoutsHistory = (JSONArray) getJSONResponseFor(checkoutsHistoryUrl, "GET");
					
					int page = initData.getHistoryDesired().getPage().intValue();

					JSONObject loanedItemsHistory = new JSONObject();

					loanedItemsHistory.put("size", checkoutsHistory.size());

					JSONArray checkoutsHistoryItems = new JSONArray();

					for (int i = --page * 10; i < checkoutsHistory.size(); ++i) {

						String checkoutId = (String) ((JSONObject) checkoutsHistory.get(i)).get("issue_id");

						checkoutsHistoryUrl = new RestApiUrlBuilder().getCheckoutsHistory(checkoutId);

						JSONObject loanedItemParsed = (JSONObject) getJSONResponseFor(checkoutsHistoryUrl, "GET");

						checkoutsHistoryItems.add(loanedItemParsed);
					}

					loanedItemsHistory.put("items", checkoutsHistoryItems);

					userInfo.put("loanedItemsHistory", loanedItemsHistory);
				} catch (ParseException e) {

				}
			}

			if (requestedItemsDesired) {

				URL holdsRestUrl = new RestApiUrlBuilder().getHoldsOfPatron(patronId);

				try {
					JSONArray holds = (JSONArray) getJSONResponseFor(holdsRestUrl, "GET");

					userInfo.put("holds", holds);
				} catch (ParseException e) {

				}
			}

			if (userFiscalAccountDesired) {

				URL accountLinesRestUrl = new RestApiUrlBuilder().getAccountLines(patronId);

				try {

					JSONArray accountLines = (JSONArray) getJSONResponseFor(accountLinesRestUrl, "GET");

					userInfo.put("accountLines", accountLines);
				} catch (ParseException e) {

				}
			}

			return userInfo;
		}

		boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();

		boolean personalInfoDesired = initData.getNameInformationDesired() || initData.getUserIdDesired()
				|| initData.getUserAddressInformationDesired() || initData.getUserPrivilegeDesired();

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_USER)
				.addRequest(KohaConstants.PARAM_USER_ID, patronId);

		if (loanedItemsDesired) {
			String appProfileType = KohaUtil.getAppProfileType(initData);

			if (appProfileType.contentEquals("LoanedItemsHistory")) {
				// Loaned items history desired :)
				urlBuilder.addRequest(KohaConstants.PARAM_LOANED_ITEMS_HISTORY_DESIRED);
			} else {
				// Regular loaned items desired
				urlBuilder.addRequest(KohaConstants.PARAM_LOANED_ITEMS_DESIRED)
						.addRequest(KohaConstants.PARAM_RENEWABILITY_DESIRED);
			}
		}

		if (requestedItemsDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_REQUESTED_ITEMS_DESIRED);

		if (userFiscalAccountDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_USER_FISCAL_ACCOUNT_DESIRED);

		if (blockOrTrapDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_BLOCK_OR_TRAP_DESIRED);

		if (!personalInfoDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_NOT_USER_INFO);

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public Object cancelRequestItem(CancelRequestItemInitiationData initData, boolean itemIdIsNotEmpty)
			throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException,
			ParseException {

		if (LocalConfig.useRestApiInsteadOfSvc()) {

			String patronId = initData.getUserId().getUserIdentifierValue();

			String reserveId = null;

			if (initData.getRequestId() != null)
				reserveId = initData.getRequestId().getRequestIdentifierValue();

			if (reserveId == null && itemIdIsNotEmpty) {

				String providedItemId = initData.getItemId().getItemIdentifierValue();

				String providedItemType = initData.getItemId().getItemIdentifierType().getValue();

				URL holdsRestUrl = new RestApiUrlBuilder().getHoldsOfPatron(patronId);

				JSONArray holds = (JSONArray) getJSONResponseFor(holdsRestUrl, "GET");

				for (Object object : holds) {
					JSONObject hold = (JSONObject) object;

					String itemId = (String) hold.get("itemnumber");

					if (itemId != null && itemId.equals(providedItemId)) {
						reserveId = (String) hold.get("reserve_id");
						break;
					}
				}

				if (reserveId == null)
					throw new KohaException("Request not found");
			}

			URL deleteHoldRestUrl = new RestApiUrlBuilder().deleteHold(reserveId);

			return getJSONResponseFor(deleteHoldRestUrl, "DELETE");
		} else {

			URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_CANCEL_REQUEST_ITEM)
					.addRequest(KohaConstants.PARAM_USER_ID, initData.getUserId().getUserIdentifierValue());

			if (itemIdIsNotEmpty)
				urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue());
			else
				urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_ID,
						initData.getRequestId().getRequestIdentifierValue());

			return getPlainTextResponse(urlBuilder.toURL());
		}
	}

	public JSONObject lookupItem(LookupItemInitiationData initData) throws KohaException, IOException, SAXException,
			ParserConfigurationException, ParseException, URISyntaxException {

		String itemIdVal = initData.getItemId().getItemIdentifierValue();

		boolean itemRestrictionDesired = initData.getItemUseRestrictionTypeDesired();
		boolean holdQueueLengthDesired = initData.getHoldQueueLengthDesired();
		boolean circulationStatusDesired = initData.getCirculationStatusDesired();

		boolean itemInfoDesired = initData.getBibliographicDescriptionDesired() || initData.getItemDescriptionDesired()
				|| initData.getLocationDesired();

		if (LocalConfig.useRestApiInsteadOfSvc()) {

			// Gets also itemDescription's itemcallnumber & copynumber
			URL itemsRestUrl = new RestApiUrlBuilder().getItems(itemIdVal);

			String itemsResponse = getPlainTextResponse(itemsRestUrl);

			JSONObject item = (JSONObject) jsonParser.parse(itemsResponse);

			if (circulationStatusDesired || holdQueueLengthDesired || initData.getLocationDesired()) {
				try {

					URL availabilityRestUrl = new RestApiUrlBuilder().getItemAvailability(itemIdVal);

					String itemAvailabilityResponse = getPlainTextResponse(availabilityRestUrl);

					JSONObject itemAvailability = (JSONObject) ((JSONArray) jsonParser.parse(itemAvailabilityResponse))
							.get(0);

					item.put("availability", itemAvailability);

				} catch (KohaException ke) {

					if (!ke.getShortMessage().equals(KohaException.NOT_FOUND_404))
						throw ke;

					// Current version of REST API does not support availability
				}
			}

			if (initData.getBibliographicDescriptionDesired()) {
				try {

					String bibIdVal = (String) item.get("biblionumber");

					URL bibliosRestUrl = new RestApiUrlBuilder().getBiblios(bibIdVal);

					String bibliosResponse = getPlainTextResponse(bibliosRestUrl);

					JSONObject biblio = (JSONObject) jsonParser.parse(bibliosResponse);

					item.put("biblio", biblio);

				} catch (KohaException ke) {

					if (!ke.getShortMessage().equals(KohaException.NOT_FOUND_404))
						throw ke;

					// Current version of REST API does not support biblios
				}
			}

			return item;
		} else {
			URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_ITEM)
					.addRequest(KohaConstants.PARAM_ITEM_ID, itemIdVal);

			if (itemRestrictionDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_ITEM_USE_RESTRICTION_TYPE_DESIRED);

			if (holdQueueLengthDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_HOLD_QUEUE_LENGTH_DESIRED);

			if (circulationStatusDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_CIRCULATION_STATUS_DESIRED);

			if (!itemInfoDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_NOT_ITEM_INFO);

			String response = getPlainTextResponse(urlBuilder.toURL(), itemIdVal);

			return (JSONObject) jsonParser.parse(response);
		}
	}

	public JSONObject lookupItem(String id, ILSDIvOneOneLookupItemSetInitiationData initData)
			throws ParserConfigurationException, IOException, SAXException, KohaException, ParseException,
			URISyntaxException {
		return lookupItem(KohaUtil.luisInitDataToLookupItemInitData(initData, id));
	}

	public JSONObject lookupItemSet(String bibId, ILSDIvOneOneLookupItemSetInitiationData initData)
			throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException,
			ParseException {

		boolean itemRestrictionDesired = initData.getItemUseRestrictionTypeDesired();
		boolean holdQueueLengthDesired = initData.getHoldQueueLengthDesired();
		boolean circulationStatusDesired = initData.getCirculationStatusDesired();

		boolean bibInfoDesired = initData.getBibliographicDescriptionDesired();

		if (LocalConfig.useRestApiInsteadOfSvc()) {

			URL bibliosRestUrl = new RestApiUrlBuilder().getBiblios(bibId);

			String bibliosResponse = getPlainTextResponse(bibliosRestUrl);

			JSONObject biblio = (JSONObject) jsonParser.parse(bibliosResponse);

			return biblio;

		} else {

			URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_ITEM_SET)
					.addRequest(KohaConstants.PARAM_BIB_ID, bibId);

			if (itemRestrictionDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_ITEM_USE_RESTRICTION_TYPE_DESIRED);

			if (holdQueueLengthDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_HOLD_QUEUE_LENGTH_DESIRED);

			if (circulationStatusDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_CIRCULATION_STATUS_DESIRED);

			if (!bibInfoDesired)
				urlBuilder.addRequest(KohaConstants.PARAM_NOT_BIB_INFO);

			boolean userIdProvided = initData.getUserId() != null
					&& !initData.getUserId().getUserIdentifierValue().trim().isEmpty();

			if (userIdProvided)
				urlBuilder.addRequest(KohaConstants.PARAM_CAN_BE_REQUESTED_BY_USERID,
						initData.getUserId().getUserIdentifierValue());

			String response = getPlainTextResponse(urlBuilder.toURL(), bibId);

			return (JSONObject) jsonParser.parse(response);
		}
	}

	public JSONObject lookupRequest(LookupRequestInitiationData initData, boolean requestIdIsNotEmpty)
			throws KohaException, IOException, SAXException, ParserConfigurationException, ServiceException,
			URISyntaxException, ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_REQUEST);

		if (requestIdIsNotEmpty)
			urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_ID, initData.getRequestId().getRequestIdentifierValue());
		else
			urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue())
					.addRequest(KohaConstants.PARAM_USER_ID, initData.getUserId().getUserIdentifierValue());

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject renewItem(RenewItemInitiationData initData) throws KohaException, IOException, SAXException,
			ParserConfigurationException, URISyntaxException, ParseException {

		if (LocalConfig.useRestApiInsteadOfSvc()) {

			String patronId = initData.getUserId().getUserIdentifierValue();

			URL patronRestUrl = new RestApiUrlBuilder().getCheckouts(patronId);

			String checkoutsResponse = getPlainTextResponse(patronRestUrl);

			JSONArray checkouts = (JSONArray) jsonParser.parse(checkoutsResponse);

			String lookingForItemId = initData.getItemId().getItemIdentifierValue();

			String checkoutId = null;

			for (Object checkoutObj : checkouts) {
				JSONObject checkout = (JSONObject) checkoutObj;

				String itemId = (String) checkout.get("itemnumber");

				if (itemId != null && lookingForItemId.equals(itemId)) {
					checkoutId = (String) checkout.get("issue_id");
					break;
				}
			}

			if (checkoutId == null)
				throw new KohaException("Hold not found");

			URL putCheckoutRestUrl = new RestApiUrlBuilder().putCheckouts(checkoutId);

			JSONObject renewalInfo = (JSONObject) getJSONResponseFor(putCheckoutRestUrl, "PUT");

			return renewalInfo;
		} else {
			URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_RENEW_ITEM)
					.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue())
					.addRequest(KohaConstants.PARAM_USER_ID, initData.getUserId().getUserIdentifierValue());

			GregorianCalendar desiredDateDue = initData.getDesiredDateDue();

			if (desiredDateDue != null)
				urlBuilder.addRequest(KohaConstants.PARAM_DESIRED_DATE_DUE, KohaUtil.convertToKohaDate(desiredDateDue));

			String response = getPlainTextResponse(urlBuilder.toURL());

			return (JSONObject) jsonParser.parse(response);
		}
	}

	public JSONObject requestItem(RequestItemInitiationData initData) throws KohaException, IOException, SAXException,
			ParserConfigurationException, URISyntaxException, ParseException, java.text.ParseException {

		if (LocalConfig.useRestApiInsteadOfSvc()) {
			URL postHoldsRestUrl = new RestApiUrlBuilder().postHolds();

			JSONObject data = new JSONObject();

			data.put("borrowernumber", Integer.parseInt(initData.getUserId().getUserIdentifierValue()));

			if (initData.getItemIds() != null && initData.getItemId(0) != null)
				data.put("itemnumber", Integer.parseInt(initData.getItemId(0).getItemIdentifierValue()));
			else
				data.put("biblionumber", Integer.parseInt(
						initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier()));

			data.put("branchcode", initData.getPickupLocation().getValue());

			String expirationDate = KohaUtil.createKohaDateFromGregorianCalendar(initData.getPickupExpiryDate());
			data.put("expirationdate", expirationDate);

			try {
				JSONObject holdCreated = (JSONObject) getJSONResponseFor(postHoldsRestUrl, "POST", data);

				return holdCreated;
			} catch (KohaException e) {

				if (e.getMessage().contains("See Koha logs for details")) // wtf??
					throw new KohaException(
							"You already have this item requested or you have not permissions to request this item.");

				throw e;
			}
		} else {
			URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_REQUEST_ITEM)
					.addRequest(KohaConstants.PARAM_USER_ID, initData.getUserId().getUserIdentifierValue());

			if (initData.getItemIds() != null && initData.getItemId(0) != null)
				urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId(0).getItemIdentifierValue());
			else
				urlBuilder.addRequest(KohaConstants.PARAM_BIB_ID,
						initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier());

			RequestType requestType = initData.getRequestType();
			if (requestType != null && requestType.getValue()
					.matches(Version1RequestType.HOLD.getValue() + "|" + Version1RequestType.LOAN.getValue()))
				urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_TYPE, requestType.getValue());

			GregorianCalendar pickupExpiryDate = initData.getPickupExpiryDate();
			if (pickupExpiryDate != null)
				urlBuilder.addRequest(KohaConstants.PARAM_PICKUP_EXPIRY_DATE,
						KohaUtil.convertToKohaDate(pickupExpiryDate));

			GregorianCalendar earliestDateNeeded = initData.getEarliestDateNeeded();
			if (earliestDateNeeded != null)
				urlBuilder.addRequest(KohaConstants.PARAM_EARLIEST_DATE_NEEDED,
						KohaUtil.convertToKohaDate(earliestDateNeeded));

			PickupLocation pickupLocation = initData.getPickupLocation();
			if (pickupLocation != null)
				urlBuilder.addRequest(KohaConstants.PARAM_PICKUP_LOCATION, pickupLocation.getValue());

			String userNote = initData.getUserNote();
			if (userNote != null)
				urlBuilder.addRequest(KohaConstants.PARAM_NOTES, userNote);

			URL url = urlBuilder.toURL();

			String response = getPlainTextResponse(url);

			return (JSONObject) jsonParser.parse(response);
		}
	}

	public AgencyAddressInformation getAgencyPhysicalAddressInformation() {
		AgencyAddressInformation agencyAddressInformation = new AgencyAddressInformation();

		PhysicalAddress physicalAddress = new PhysicalAddress();

		UnstructuredAddress unstructuredAddress = new UnstructuredAddress();

		unstructuredAddress.setUnstructuredAddressData(LocalConfig.getAgencyAddress());
		unstructuredAddress.setUnstructuredAddressType(Version1UnstructuredAddressType.NEWLINE_DELIMITED_TEXT);

		physicalAddress.setUnstructuredAddress(unstructuredAddress);

		physicalAddress.setPhysicalAddressType(Version1PhysicalAddressType.STREET_ADDRESS);
		agencyAddressInformation.setPhysicalAddress(physicalAddress);

		AgencyAddressRoleType agencyAddressRoleType = Version1AgencyAddressRoleType.OFFICIAL;
		agencyAddressInformation.setAgencyAddressRoleType(agencyAddressRoleType);

		return agencyAddressInformation;
	}

	public List<OrganizationNameInformation> getOrganizationNameInformations() {
		List<OrganizationNameInformation> organizationNameInformations = new ArrayList<OrganizationNameInformation>();
		OrganizationNameInformation organizationNameInfo = new OrganizationNameInformation();

		organizationNameInfo.setOrganizationName(LocalConfig.getAgencyName());
		organizationNameInfo.setOrganizationNameType(Version1OrganizationNameType.TRANSLATED_NAME);
		organizationNameInformations.add(organizationNameInfo);
		return organizationNameInformations;
	}

	private static URL getLoginPOSTurl() throws MalformedURLException {
		return new URLBuilder().setBase(LocalConfig.getServerName(), LocalConfig.getIntranetServerPort())
				.setPath("cgi-bin/koha/mainpage.pl").toURL();
	}

	private static URLBuilder getCommonSvcURLBuilder() {
		return new URLBuilder().setBase(LocalConfig.getServerName(), LocalConfig.getIntranetServerPort())
				.setPath(LocalConfig.getSvcSuffix());
	}

	private static URLBuilder getCommonSvcNcipURLBuilder(String service) {
		return getCommonSvcURLBuilder().appendPath(KohaConstants.SVC_NCIP).addRequest(KohaConstants.PARAM_SERVICE,
				service);
	}

	private String getPlainTextResponse(URL url) throws KohaException, IOException, SAXException, URISyntaxException {
		return getPlainTextResponse(url, null);
	}

	private String getPlainTextResponse(URL url, String identifier)
			throws KohaException, IOException, SAXException, URISyntaxException {

		boolean isHttps = url.getProtocol().equals("https");

		HttpURLConnection conn = null;
		if (isHttps)
			conn = (HttpsURLConnection) url.openConnection();
		else
			conn = (HttpURLConnection) url.openConnection();

		conn.addRequestProperty("Cookie", currentSessionIdCookie);
		int statusCode = conn.getResponseCode();
		if (statusCode != 403) {
			loginAttempts = 0;

			BufferedReader reader;
			if (statusCode == 200)
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			else
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

			StringBuilder stringBuilder = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			conn.disconnect();

			String responseEntity = stringBuilder.toString();

			if (statusCode == 200) {
				return responseEntity;
			} else if (statusCode == 400) {
				throw KohaException.create400BadRequestException(responseEntity);
			} else if (statusCode == 404) {
				throw KohaException.create404NotFoundException(responseEntity, identifier);
			} else {
				throw KohaException.createCommonException(statusCode, responseEntity);
			}
		} else {
			conn.disconnect();
			renewSessionCookie();
			return getPlainTextResponse(url, identifier);
		}
	}

	private Object getJSONResponseFor(URL url, String method)
			throws MalformedURLException, IOException, KohaException, ParseException, SAXException {

		return getJSONResponseFor(url, method, (String) null);
	}

	private Object getJSONResponseFor(URL url, String method, JSONObject json)
			throws MalformedURLException, IOException, KohaException, ParseException, SAXException {

		return getJSONResponseFor(url, method, json.toJSONString());
	}

	private Object getJSONResponseFor(URL url, String method, String data)
			throws MalformedURLException, IOException, KohaException, ParseException, SAXException {

		if (currentSessionIdCookie.isEmpty())
			renewSessionCookie();

		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.addRequestProperty("Cookie", currentSessionIdCookie);
		httpCon.setRequestMethod(method);

		if (data != null) {
			httpCon.setDoOutput(true);

			OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
			outWriter.write(data);
			outWriter.close();
		}

		int statusCode = httpCon.getResponseCode();

		if (statusCode > 399) {

			String content = KohaUtil.convertStreamToString(httpCon.getErrorStream());

			if (statusCode == 403 && content.contains("expired") && !haveBeenHere) {
				haveBeenHere = true;
				renewSessionCookie();
				return getJSONResponseFor(url, method);
			}

			haveBeenHere = false;

			httpCon.disconnect();

			JSONObject error = (JSONObject) jsonParser.parse(content);

			if (error == null)
				throw new KohaException(content);

			throw new KohaException((String) error.get("error"));
		}

		String rawResponse = KohaUtil.convertStreamToString(httpCon.getInputStream());

		Object response = jsonParser.parse(rawResponse);

		return response;
	}

	/**
	 * Logins definer admin user (from settings in toolkit.properties) & saves
	 * cookies to be able to continue parsing requests as logged user.
	 * 
	 * @param streamSource
	 * @param url
	 * @throws IOException
	 * @throws SAXException
	 */
	private static void renewSessionCookie() throws IOException, SAXException, KohaException {

		if (++loginAttempts < 5) {
			HttpURLConnection httpCon = (HttpURLConnection) getLoginPOSTurl().openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");

			String credentials = "userid=" + LocalConfig.getAdminName() + "&password=" + LocalConfig.getAdminPass();

			String additionalPostParams = "&koha_login_context=intranet&branch=";

			OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
			outWriter.write(credentials + additionalPostParams);
			outWriter.close();

			String response = KohaUtil.convertStreamToString(httpCon.getInputStream());

			if (response.contains("login_error")) {
				throw new KohaException("Invalid credentials were provided in toolkit.properties - cannot log in.");
			}

			String[] cookies = httpCon.getHeaderField("Set-Cookie").split(";");

			for (String cookie : cookies) {
				if (cookie.contains("CGISESSID=")) {
					currentSessionIdCookie = cookie;
					loginAttempts = 0;
					break;
				}
			}

		} else {
			asyncWait15minutesAndResetLoginAttempts();

			throw KohaException.createTooManyLoginAttempts();
		}
	}

	private static void asyncWait15minutesAndResetLoginAttempts() {
		new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(15 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loginAttempts = 0;

			}
		}.run();

	}

}
