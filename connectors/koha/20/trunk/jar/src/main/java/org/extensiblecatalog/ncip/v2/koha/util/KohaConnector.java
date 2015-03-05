package org.extensiblecatalog.ncip.v2.koha.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.koha.KohaLookupItemSetService;
import org.extensiblecatalog.ncip.v2.koha.item.KohaItem;
import org.extensiblecatalog.ncip.v2.koha.item.KohaRenewItem;
import org.extensiblecatalog.ncip.v2.koha.item.KohaRequestItem;
import org.extensiblecatalog.ncip.v2.koha.item.MarcItem;
import org.extensiblecatalog.ncip.v2.koha.user.KohaUser;
import org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers.KohaLoginHandler;
import org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers.KohaLookupItemHandler;
import org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers.KohaLookupUserHandler;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class KohaConnector {

	private static final long serialVersionUID = -4425639616999642735L;

	private static SAXParser parser;

	private static InputSource streamSource;

	private static URL authenticationUrl;

	private static String currentSessionIdCookie = "";

	private Random random = new Random();

	public KohaConnector() throws ServiceException {

		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
		} catch (ParserConfigurationException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory.");
		} catch (SAXException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory.");
		}
		try {
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			KohaConfiguration kohaConfig = new KohaConfiguration(config);
			
			LocalConfig.setDefaultAgency(kohaConfig.getProperty(KohaConstants.DEFAULT_AGENCY));

			LocalConfig.setOpacServerName(kohaConfig.getProperty(KohaConstants.OPAC_SERVER));
			LocalConfig.setOpacServerPort(kohaConfig.getProperty(KohaConstants.OPAC_PORT));
			LocalConfig.setSvcServerPort(kohaConfig.getProperty(KohaConstants.SVC_PORT));

			LocalConfig.setAdminName(kohaConfig.getProperty(KohaConstants.ADMIN_NAME));
			LocalConfig.setAdminPass(kohaConfig.getProperty(KohaConstants.ADMIN_PASS));

			LocalConfig.setIlsDiSuffix(kohaConfig.getProperty(KohaConstants.ILS_DI_SUFFIX));
			LocalConfig.setSvcSuffix(kohaConfig.getProperty(KohaConstants.SVC_SUFFIX));

			LocalConfig.setAgencyAddress(kohaConfig.getProperty(KohaConstants.AGENCY_UNSTRUCTURED_ADDRESS));
			LocalConfig.setAgencyName(kohaConfig.getProperty(KohaConstants.AGENCY_TRANSLATED_NAME));

			LocalConfig.setUserRegistrationLink(kohaConfig.getProperty(KohaConstants.USER_REGISTRATION_LINK));
			LocalConfig.setAuthDataFormatType(kohaConfig.getProperty(KohaConstants.AUTH_DATA_FORMAT_TYPE));

			LocalConfig.setTokenExpirationTime(Integer.parseInt(kohaConfig.getProperty(KohaConstants.NEXT_ITEM_TOKEN_EXPIRATION_TIME)));

			LocalConfig.setEchoParticularProblemsToLUIS(Boolean.parseBoolean(kohaConfig.getProperty(KohaConstants.INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS)));

			try {
				LocalConfig.setMaxItemPreparationTimeDelay(Integer.parseInt(kohaConfig.getProperty(KohaConstants.MAX_ITEM_PREPARATION_TIME_DELAY)));
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
	 * Logins definer admin user (from settings in toolkit.properties) & saves cookies to be able to continue parsing requests as logged user.<br>
	 * <br>
	 * Assings to {@link InputSource} streamSource opened {@link URL} url as {@link URL.openStream()}
	 * 
	 * @param streamSource
	 * @param url
	 * @throws IOException
	 * @throws SAXException
	 */
	private static void renewSessionCookie() throws IOException, SAXException, KohaException {

		HttpURLConnection httpCon = (HttpURLConnection) authenticationUrl.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("POST");

		String credentials = "userid=" + LocalConfig.getAdminName() + "&password=" + LocalConfig.getAdminPass();

		OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
		outWriter.write(credentials);
		outWriter.close();

		streamSource = new InputSource(httpCon.getInputStream());

		KohaLoginHandler loginHandler = new KohaLoginHandler();

		parser.parse(streamSource, loginHandler);

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
	}

	/**
	 * @param patronId
	 * @param {@link LookupUserInitiationData} initData
	 * @return {@link KohaUser}
	 * @throws KohaException
	 * @throws IOException
	 * @throws SAXException
	 */
	public KohaUser lookupUser(String patronId, LookupUserInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {

		boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();
		boolean loanedItemsDesired = initData.getLoanedItemsDesired();
		boolean requestedItemsDesired = initData.getRequestedItemsDesired();
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();

		boolean personalInfoDesired = initData.getNameInformationDesired() || initData.getUserIdDesired() || initData.getUserAddressInformationDesired();

		String appProfileType = null;
		if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null)
			appProfileType = initData.getInitiationHeader().getApplicationProfileType().getValue();

		String lang = (appProfileType == null || appProfileType.isEmpty()) ? "en" : appProfileType;

		// Create URL request only if specified service was desired
		URL addressUrl = new URLBuilder().setBase(LocalConfig.getServerName(), LocalConfig.getServerPort()).setPath(LocalConfig.getSvcSuffix()).toURL();

		KohaLookupUserHandler userHandler = new KohaLookupUserHandler(initData);

		/* Parse Requests
					KohaLookupRequestsHandler requestItemHandler = new KohaLookupRequestsHandler();
LocalConfig.setDefaultAgency(alephConfig.getProperty(AlephConstants.DEFAULT_AGENCY));
					if (appProfileType != null && !appProfileType.isEmpty())
						requestItemHandler.setLocalizationDesired(true);

					streamSource = new InputSource(requestsUrl.openStream());

					// Here parser parses all available info saveable into RequestItem class
					parser.parse(streamSource, requestItemHandler);

					List<RequestedItem> requestedItems = requestItemHandler.getRequestedItems();

					if (requestedItems.size() > 0) {

						// If there is set default item preparation delay, set it to all requested items
						if (LocalConfig.getMaxItemPreparationTimeDelay() != 0)
							for (RequestedItem requestedItem : requestedItems) {
								if (requestedItem.getDatePlaced() != null) {
									// Because Koha does not support default delay between pickupDate and datePlaced, we will use custom configuration to set it
									GregorianCalendar pickupDate = (GregorianCalendar) requestedItem.getDatePlaced().clone();
									pickupDate.add(Calendar.DAY_OF_MONTH, LocalConfig.getMaxItemPreparationTimeDelay());
									requestedItem.setPickupDate(pickupDate);
								}
							}

						userHandler.getKohaUser().setRequestedItems(requestedItems);
					}
		*/
		if (addressUrl != null) {
			streamSource = new InputSource(addressUrl.openStream());

			parser.parse(streamSource, userHandler);
		}

		return userHandler.getKohaUser();
	}

	public KohaRequestItem cancelRequestItem(CancelRequestItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public MarcItem lookupItem(LookupItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		KohaLookupItemHandler itemHandler = new KohaLookupItemHandler();

		String itemId = initData.getItemId().getItemIdentifierValue();

		URL url = getCommonSvcURLBuilder().appendPath(KohaConstants.SVC_BIB, itemId).toURL();

		try {
			streamSource = createInputSourceWithCookie(url);
		} catch (IOException e) {
			String responseCode = (String) e.getMessage().subSequence(36, 39);
			if (responseCode.equals("403")) {
				renewSessionCookie();
				streamSource = createInputSourceWithCookie(url);
			} else
				throw e;
		}

		// TODO: need to authenticate first
		parser.parse(streamSource, itemHandler);

		return itemHandler.getMarcItem();
	}

	public List<MarcItem> lookupItems(String id, LookupItemSetInitiationData initData, KohaLookupItemSetService kohaLookupItemSetService) throws KohaException, IOException,
			SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public MarcItem lookupItem(String id, LookupItemSetInitiationData initData) throws ParserConfigurationException, IOException, SAXException, KohaException {
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

	public KohaRequestItem lookupRequest(LookupRequestInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException, ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public KohaRenewItem renewItem(RenewItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public KohaRequestItem requestItem(RequestItemInitiationData initData) throws KohaException, IOException, SAXException, ParserConfigurationException {
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

	private static InputSource createInputSourceWithCookie(URL url) throws IOException {
		URLConnection urlConn = url.openConnection();
		urlConn.setRequestProperty("Cookie", currentSessionIdCookie);

		return new InputSource(urlConn.getInputStream());

	}
}
