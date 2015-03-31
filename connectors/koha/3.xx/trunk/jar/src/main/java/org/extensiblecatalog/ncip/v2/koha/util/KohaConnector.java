package org.extensiblecatalog.ncip.v2.koha.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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
import org.extensiblecatalog.ncip.v2.koha.user.KohaUser;
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
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.UpdateUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.Version1OrganizationNameType;
import org.extensiblecatalog.ncip.v2.service.Version1PhysicalAddressType;
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

	private Random random = new Random();

	private static int loginAttempts;

	public KohaConnector() throws ServiceException {

		loginAttempts = 0;

		try {
			saxParser = SAXParserFactory.newInstance().newSAXParser();
			jsonParser = new JSONParser();
		} catch (Exception e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory.");
		}
		try {
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			KohaConfiguration kohaConfig = new KohaConfiguration(config);

			LocalConfig.setDefaultAgency(kohaConfig.getProperty(KohaConstants.CONF_DEFAULT_AGENCY));

			LocalConfig.setOpacServerName(kohaConfig.getProperty(KohaConstants.CONF_OPAC_SERVER));
			LocalConfig.setOpacServerPort(kohaConfig.getProperty(KohaConstants.CONF_OPAC_PORT));
			LocalConfig.setSvcServerPort(kohaConfig.getProperty(KohaConstants.CONF_SVC_PORT));

			LocalConfig.setAdminName(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_NAME));
			LocalConfig.setAdminPass(kohaConfig.getProperty(KohaConstants.CONF_ADMIN_PASS));

			LocalConfig.setIlsDiSuffix(kohaConfig.getProperty(KohaConstants.CONF_ILS_DI_SUFFIX));
			LocalConfig.setSvcSuffix(kohaConfig.getProperty(KohaConstants.CONF_SVC_SUFFIX));

			LocalConfig.setAgencyAddress(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_UNSTRUCTURED_ADDRESS));
			LocalConfig.setAgencyName(kohaConfig.getProperty(KohaConstants.CONF_AGENCY_TRANSLATED_NAME));

			LocalConfig.setUserRegistrationLink(kohaConfig.getProperty(KohaConstants.CONF_USER_REGISTRATION_LINK));
			LocalConfig.setAuthDataFormatType(kohaConfig.getProperty(KohaConstants.CONF_AUTH_DATA_FORMAT_TYPE));

			LocalConfig.setTokenExpirationTime(Integer.parseInt(kohaConfig.getProperty(KohaConstants.CONF_NEXT_ITEM_TOKEN_EXPIRATION_TIME)));

			LocalConfig.setEchoParticularProblemsToLUIS(Boolean.parseBoolean(kohaConfig.getProperty(KohaConstants.CONF_INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS)));

			try {
				LocalConfig.setMarcHoldingsItemTag(kohaConfig.getProperty(KohaConstants.CONF_HOLDINGS_ITEM_TAG));
			} catch (Exception e) {
				LocalConfig.setMarcHoldingsItemTag(KohaConstants.DATAFIELD_HOLDINGS_ITEM_DEFAULT_TAG);
			}

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
		}

	}

	/**
	 * @param patronId
	 * @param {@link LookupUserInitiationData} initData
	 * @return {@link KohaUser}
	 * @throws KohaException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	public JSONObject lookupUser(String patronId, LookupUserInitiationData initData) throws KohaException, SAXException, ParserConfigurationException, ParseException,
			URISyntaxException, IOException {

		URL url = getCommonSvcNcipURLBuilder(KohaConstants.ATTR_VAL_LOOKUP_USER).addRequest(KohaConstants.ATTR_VAL_USERID, patronId).toURL();

		String response = getPlainTextResponse(url);

		JSONObject obj = (JSONObject) jsonParser.parse(response);
		return obj;
	}

	public JSONObject cancelRequestItem(CancelRequestItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject lookupItem(LookupItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, ParseException,
			URISyntaxException {

		String itemId = initData.getItemId().getItemIdentifierValue();

		URL url = getCommonSvcNcipURLBuilder(KohaConstants.ATTR_VAL_LOOKUP_ITEM).addRequest(KohaConstants.ATTR_ITEM_ID, itemId).toURL();

		String response = getPlainTextResponse(url);

		JSONObject obj = (JSONObject) jsonParser.parse(response);

		return obj;
	}

	public List<JSONObject> lookupItems(String id, LookupItemSetInitiationData initData, KohaLookupItemSetService kohaLookupItemSetService) throws KohaException, IOException,
			SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject lookupItem(String id, LookupItemSetInitiationData initData) throws ParserConfigurationException, IOException, SAXException, KohaException, ParseException,
			URISyntaxException {
		LookupItemInitiationData LIinitData = new LookupItemInitiationData();
		ItemId itemId = new ItemId();
		itemId.setItemIdentifierValue(id);
		LIinitData.setItemId(itemId);
		LIinitData.setBibliographicDescriptionDesired(initData.getBibliographicDescriptionDesired());
		LIinitData.setCirculationStatusDesired(initData.getCirculationStatusDesired());
		LIinitData.setHoldQueueLengthDesired(initData.getHoldQueueLengthDesired());
		LIinitData.setItemDescriptionDesired(initData.getItemDescriptionDesired());
		LIinitData.setItemUseRestrictionTypeDesired(initData.getItemUseRestrictionTypeDesired());
		LIinitData.setLocationDesired(initData.getLocationDesired());
		return lookupItem(LIinitData);
	}

	public JSONObject lookupRequest(LookupRequestInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject renewItem(RenewItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject requestItem(RequestItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateUser(UpdateUserInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
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
		return getCommonSvcURLBuilder().appendPath(KohaConstants.SVC_NCIP).addRequest(KohaConstants.ATTR_SERVICE, service);
	}

	private String getPlainTextResponse(URL url) throws KohaException, IOException, SAXException, URISyntaxException {

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
			} else {
				throw KohaException.createCommonException(statusCode, responseEntity);
			}
		} else {
			renewSessionCookie();
			return getPlainTextResponse(url);
		}
	}

	/**
	 * Logins definer admin user (from settings in toolkit.properties) & saves cookies to be able to continue parsing requests as logged user.<br>
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
