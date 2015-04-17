package org.extensiblecatalog.ncip.v2.koha.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.koha.KohaLookupItemSetService;
import org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers.KohaLoginHandler;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestElementType;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestType;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.Version1OrganizationNameType;
import org.extensiblecatalog.ncip.v2.service.Version1PhysicalAddressType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestElementType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.Version1UnstructuredAddressType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class KohaConnector {

	private static SAXParser saxParser;

	private static JSONParser jsonParser;

	private static InputSource streamSource;

	private static URL authenticationUrl;

	private static String currentSessionIdCookie = "";

	private static int loginAttempts;

	public KohaConnector() throws ServiceException {

		loginAttempts = 0;

		try {
			saxParser = SAXParserFactory.newInstance().newSAXParser();
			jsonParser = new JSONParser();
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			KohaConfiguration kohaConfig = new KohaConfiguration(config);

			LocalConfig.setTransferBranchesTime(kohaConfig.getProperty(KohaConstants.CONF_TRANSFER_BRANCH_TIME));

			LocalConfig.setDefaultAgency(kohaConfig.getProperty(KohaConstants.CONF_DEFAULT_AGENCY));

			LocalConfig.setCurrencyCode(kohaConfig.getProperty(KohaConstants.CONF_CURRENCY_CODE));

			LocalConfig.setOpacServerName(kohaConfig.getProperty(KohaConstants.CONF_OPAC_SERVER));

			LocalConfig.setOpacServerPort(kohaConfig.getProperty(KohaConstants.CONF_OPAC_PORT));

			LocalConfig.setSvcServerPort(kohaConfig.getProperty(KohaConstants.CONF_SVC_PORT));

			LocalConfig.setAdminName(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_NAME));
			LocalConfig.setAdminPass(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_PASS));

			LocalConfig.setSvcSuffix(kohaConfig.getProperty(KohaConstants.CONF_SVC_SUFFIX));

			LocalConfig.setAgencyAddress(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_UNSTRUCTURED_ADDRESS));
			LocalConfig.setAgencyName(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_TRANSLATED_NAME));

			LocalConfig.setUserRegistrationLink(kohaConfig.getProperty(KohaConstants.CONF_USER_REGISTRATION_LINK));
			LocalConfig.setAuthDataFormatType(kohaConfig.getProperty(KohaConstants.CONF_AUTH_DATA_FORMAT_TYPE));

			LocalConfig.setTokenExpirationTime(Integer.parseInt(kohaConfig.getProperty(KohaConstants.CONF_NEXT_ITEM_TOKEN_EXPIRATION_TIME)));

			LocalConfig.setEchoParticularProblemsToLUIS(Boolean.parseBoolean(kohaConfig.getProperty(KohaConstants.CONF_INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS)));

			try {
				LocalConfig.setMaxItemPreparationTimeDelay(Integer.parseInt(kohaConfig.getProperty(KohaConstants.CONF_MAX_ITEM_PREPARATION_TIME_DELAY)));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				authenticationUrl = getCommonSvcURLBuilder().appendPath(KohaConstants.SVC_AUTHENTICATION).toURL();
			} catch (MalformedURLException e) {
				throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to create svc authentication url from toolkit.properties");
			}

		} catch (ToolkitException e) {
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Toolkit configuration failed.");

		} catch (SAXException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory." + e1.getMessage());
		} catch (ParserConfigurationException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory." + e1.getMessage());
		}

	}

	/**
	 * @param patronId
	 * @param {@link LookupUserInitiationData} initData
	 * @return {@link KohaUser}
	 * @throws MalformedURLException
	 * @throws KohaException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	public JSONObject lookupUser(LookupUserInitiationData initData) throws MalformedURLException, KohaException, IOException, SAXException, URISyntaxException, ParseException {

		String patronId = initData.getUserId().getUserIdentifierValue();

		boolean loanedItemsDesired = initData.getLoanedItemsDesired();
		boolean requestedItemsDesired = initData.getRequestedItemsDesired();
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();

		boolean personalInfoDesired = initData.getNameInformationDesired() || initData.getUserIdDesired() || initData.getUserAddressInformationDesired()
				|| initData.getUserPrivilegeDesired();

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_USER).addRequest(KohaConstants.PARAM_USER_ID, patronId);

		if (loanedItemsDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_LOANED_ITEMS_DESIRED);

		if (requestedItemsDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_REQUESTED_ITEMS_DESIRED);

		if (userFiscalAccountDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_USER_FISCAL_ACCOUNT_DESIRED);

		if (!personalInfoDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_NOT_USER_INFO);

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject canBeRenewed(LookupRequestInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException,
			ParseException {
		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_CAN_BE_RENEWED);

		urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue()).addRequest(KohaConstants.PARAM_USER_ID,
				initData.getUserId().getUserIdentifierValue());

		// If there is NeedBeforeDate RequestElementType then include param maxDateDueDesired to url ..
		for (RequestElementType desiredService : initData.getRequestElementTypes()) {
			if (desiredService.equals(Version1RequestElementType.NEED_BEFORE_DATE)) {
				urlBuilder.addRequest(KohaConstants.PARAM_MAX_DATE_DUE_DESIRED);
				break;
			}
		}
		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject canBeRequested(LookupRequestInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException,
			ParseException {
		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_CAN_BE_REQUESTED);

		urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue()).addRequest(KohaConstants.PARAM_USER_ID,
				initData.getUserId().getUserIdentifierValue());

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject cancelRequestItem(CancelRequestItemInitiationData initData, boolean itemIdIsNotEmpty) throws KohaException, IOException, SAXException,
			ParserConfigurationException, URISyntaxException, ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_CANCEL_REQUEST_ITEM).addRequest(KohaConstants.PARAM_USER_ID,
				initData.getUserId().getUserIdentifierValue());

		if (itemIdIsNotEmpty)
			urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue());
		else
			urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_ID, initData.getRequestId().getRequestIdentifierValue());

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject lookupItem(LookupItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, ParseException,
			URISyntaxException {

		String itemIdVal = initData.getItemId().getItemIdentifierValue();
		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_ITEM).addRequest(KohaConstants.PARAM_ITEM_ID, itemIdVal);

		boolean itemRestrictionDesired = initData.getItemUseRestrictionTypeDesired();
		boolean holdQueueLengthDesired = initData.getHoldQueueLengthDesired();
		boolean circulationStatusDesired = initData.getCirculationStatusDesired();

		boolean itemInfoDesired = initData.getBibliographicDescriptionDesired() || initData.getItemDescriptionDesired() || initData.getLocationDesired();

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

	public JSONObject lookupItem(String id, LookupItemSetInitiationData initData) throws ParserConfigurationException, IOException, SAXException, KohaException, ParseException,
			URISyntaxException {
		return lookupItem(KohaUtil.luisInitDataToLookupItemInitData(initData, id));
	}

	public JSONObject lookupItemSet(String bibId, LookupItemSetInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException,
			URISyntaxException, ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_ITEM_SET).addRequest(KohaConstants.PARAM_BIB_ID, bibId);

		boolean itemRestrictionDesired = initData.getItemUseRestrictionTypeDesired();
		boolean holdQueueLengthDesired = initData.getHoldQueueLengthDesired();
		boolean circulationStatusDesired = initData.getCirculationStatusDesired();

		boolean bibInfoDesired = initData.getBibliographicDescriptionDesired();

		if (itemRestrictionDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_ITEM_USE_RESTRICTION_TYPE_DESIRED);

		if (holdQueueLengthDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_HOLD_QUEUE_LENGTH_DESIRED);

		if (circulationStatusDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_CIRCULATION_STATUS_DESIRED);

		if (!bibInfoDesired)
			urlBuilder.addRequest(KohaConstants.PARAM_NOT_BIB_INFO);

		String response = getPlainTextResponse(urlBuilder.toURL(), bibId);

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject lookupRequest(LookupRequestInitiationData initData, boolean requestIdIsNotEmpty) throws KohaException, IOException, SAXException,
			ParserConfigurationException, ServiceException, URISyntaxException, ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_LOOKUP_REQUEST);

		if (requestIdIsNotEmpty)
			urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_ID, initData.getRequestId().getRequestIdentifierValue());
		else
			urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue()).addRequest(KohaConstants.PARAM_USER_ID,
					initData.getUserId().getUserIdentifierValue());

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject renewItem(RenewItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException, ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_RENEW_ITEM).addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId().getItemIdentifierValue())
				.addRequest(KohaConstants.PARAM_USER_ID, initData.getUserId().getUserIdentifierValue());

		GregorianCalendar desiredDateDue = initData.getDesiredDateDue();

		if (desiredDateDue != null)
			urlBuilder.addRequest(KohaConstants.PARAM_DESIRED_DATE_DUE, KohaUtil.convertToKohaDate(desiredDateDue));

		String response = getPlainTextResponse(urlBuilder.toURL());

		return (JSONObject) jsonParser.parse(response);
	}

	public JSONObject requestItem(RequestItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, URISyntaxException,
			ParseException {

		URLBuilder urlBuilder = getCommonSvcNcipURLBuilder(KohaConstants.SERVICE_REQUEST_ITEM).addRequest(KohaConstants.PARAM_USER_ID,
				initData.getUserId().getUserIdentifierValue());

		if (initData.getItemIds() != null && initData.getItemId(0) != null)
			urlBuilder.addRequest(KohaConstants.PARAM_ITEM_ID, initData.getItemId(0).getItemIdentifierValue());
		else
			urlBuilder.addRequest(KohaConstants.PARAM_BIB_ID, initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier());

		RequestType requestType = initData.getRequestType();
		if (requestType != null && requestType.getValue().matches(Version1RequestType.HOLD.getValue() + "|" + Version1RequestType.LOAN.getValue()))
			urlBuilder.addRequest(KohaConstants.PARAM_REQUEST_TYPE, requestType.getValue());

		GregorianCalendar pickupExpiryDate = initData.getPickupExpiryDate();
		if (pickupExpiryDate != null)
			urlBuilder.addRequest(KohaConstants.PARAM_PICKUP_EXPIRY_DATE, KohaUtil.convertToKohaDate(pickupExpiryDate));

		GregorianCalendar earliestDateNeeded = initData.getEarliestDateNeeded();
		if (earliestDateNeeded != null)
			urlBuilder.addRequest(KohaConstants.PARAM_EARLIEST_DATE_NEEDED, KohaUtil.convertToKohaDate(earliestDateNeeded));

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

	private static URLBuilder getCommonSvcURLBuilder() {
		return new URLBuilder().setBase(LocalConfig.getServerName(), LocalConfig.getSvcServerPort()).setPath(LocalConfig.getSvcSuffix());
	}

	private static URLBuilder getCommonSvcNcipURLBuilder(String service) {
		return getCommonSvcURLBuilder().appendPath(KohaConstants.SVC_NCIP).addRequest(KohaConstants.PARAM_SERVICE, service);
	}

	private String getPlainTextResponse(URL url) throws KohaException, IOException, SAXException, URISyntaxException {
		return getPlainTextResponse(url, null);
	}

	private String getPlainTextResponse(URL url, String identifier) throws KohaException, IOException, SAXException, URISyntaxException {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(url.toURI());

		Builder builder = target.request(MediaType.TEXT_PLAIN_TYPE).header("Cookie", currentSessionIdCookie);
		Response response = builder.get();

		int statusCode = response.getStatus();
		if (statusCode != 403) {
			loginAttempts = 0;
			String responseEntity = response.readEntity(String.class);

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
			renewSessionCookie();
			return getPlainTextResponse(url, identifier);
		}
	}

	/**
	 * Logins definer admin user (from settings in toolkit.properties) & saves
	 * cookies to be able to continue parsing requests as logged user.<br>
	 * 
	 * @param streamSource
	 * @param url
	 * @throws IOException
	 * @throws SAXException
	 */
	private static void renewSessionCookie() throws IOException, SAXException, KohaException {

		if (++loginAttempts < 5) {
			HttpURLConnection httpCon = (HttpURLConnection) authenticationUrl.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");

			String credentials = "userid=" + LocalConfig.getAdminName() + "&password=" + LocalConfig.getAdminPass();

			OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
			outWriter.write(credentials);
			outWriter.close();

			streamSource = new InputSource(httpCon.getInputStream());

			KohaLoginHandler loginHandler = new KohaLoginHandler();

			saxParser.parse(streamSource, loginHandler);

			if (!loginHandler.isLogged()) {
				throw new KohaException("Invalid credentials were provided in toolkit.properties - cannot log in.");
			}

			String[] cookies = httpCon.getHeaderField("Set-Cookie").split(";");

			for (String cookie : cookies) {
				if (cookie.contains("CGISESSID=")) {
					currentSessionIdCookie = cookie;
					break;
				}
			}

		} else {
			throw KohaException.createTooManyLoginAttempts();
		}
	}
}
