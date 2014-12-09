package org.extensiblecatalog.ncip.v2.aleph.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.extensiblecatalog.ncip.v2.aleph.AlephLookupItemSetService;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephMediator;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephRenewItem;
import org.extensiblecatalog.ncip.v2.aleph.item.AlephRequestItem;
import org.extensiblecatalog.ncip.v2.aleph.user.AlephRestDlfUser;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephItemHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephLoanHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephRenewHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephRequestHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephRequestItemHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephURLsHandler;
import org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers.AlephUserHandler;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressInformation;
import org.extensiblecatalog.ncip.v2.service.AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;
import org.extensiblecatalog.ncip.v2.service.CancelRequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.Version1AgencyAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.Version1OrganizationNameType;
import org.extensiblecatalog.ncip.v2.service.Version1PhysicalAddressType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestScopeType;
import org.extensiblecatalog.ncip.v2.service.Version1UnstructuredAddressType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RestDlfConnector extends AlephMediator {

	private static final long serialVersionUID = -4425639616999642735L;

	private static SAXParser parser;

	private static LocalConfig localConfig;

	private Random random = new Random();

	public RestDlfConnector() throws ServiceException {

		try {
			parser = SAXParserFactory.newInstance().newSAXParser();
		} catch (ParserConfigurationException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory.");
		} catch (SAXException e1) {
			throw new ServiceException(ServiceError.RUNTIME_ERROR, "Failed to initialize SAX Parser from SAXParserFactory.");
		}
		try {
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			AlephConfiguration alephConfig = new AlephConfiguration(config);

			localConfig = new LocalConfig();

			localConfig.setServerName(alephConfig.getProperty(AlephConstants.REST_DLF_SERVER));
			localConfig.setServerPort(alephConfig.getProperty(AlephConstants.REST_DLF_PORT));
			localConfig.setServerSuffix(alephConfig.getProperty(AlephConstants.REST_DLF_SUFFIX));

			localConfig.setBibLibrary(alephConfig.getProperty(AlephConstants.BIBLIOGRAPHIC_LIBRARY));
			localConfig.setAdmLibrary(alephConfig.getProperty(AlephConstants.ALEPH_ADMINISTRATIVE_LIBRARY));

			localConfig.setDefaultAgency(alephConfig.getProperty(AlephConstants.DEFAULT_AGENCY));

			localConfig.setAgencyAddress(alephConfig.getProperty(AlephConstants.AGENCY_UNSTRUCTURED_ADDRESS));
			localConfig.setAgencyName(alephConfig.getProperty(AlephConstants.AGENCY_TRANSLATED_NAME));

			localConfig.setUserRegistrationLink(alephConfig.getProperty(AlephConstants.USER_REGISTRATION_LINK));
			localConfig.setAuthDataFormatType(alephConfig.getProperty(AlephConstants.AUTH_DATA_FORMAT_TYPE));

			localConfig.setMaxItemPreparationTimeDelay(Integer.parseInt(alephConfig.getProperty(AlephConstants.MAX_ITEM_PREPARATION_TIME_DELAY)));
			localConfig.setTokenExpirationTime(Integer.parseInt(alephConfig.getProperty(AlephConstants.NEXT_ITEM_TOKEN_EXPIRATION_TIME)));

			localConfig.setEchoParticularProblemsToLUIS(Boolean.parseBoolean(alephConfig.getProperty(AlephConstants.INCLUDE_PARTICULAR_PROBLEMS_TO_LUIS)));

		} catch (ToolkitException e) {
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR, "Toolkit configuration failed.");
		}

	}

	public LocalConfig getLocalConfig() {
		return localConfig;
	}

	public AlephItem lookupItem(String id, LookupItemSetInitiationData initData) throws ParserConfigurationException, IOException, SAXException, AlephException {
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

	public AlephItem lookupItem(LookupItemInitiationData initData) throws ParserConfigurationException, IOException, SAXException, AlephException {

		String alephItemId = initData.getItemId().getItemIdentifierValue();

		String recordId = AlephUtil.parseRecordIdFromAlephItemId(alephItemId);
		String itemId = AlephUtil.parseItemIdFromAlephItemId(alephItemId);

		boolean isCorrectRecordId = AlephUtil.isCorrectRecordId(recordId, localConfig.getBibLibraryLength());
		boolean isCorrectItemId = AlephUtil.isCorrectItemId(itemId, localConfig.getBibLibraryLength());

		if (!isCorrectRecordId || !isCorrectItemId) {
			throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01001276830-MZK50001311815000020");
		}

		String appProfileType = null;
		if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null)
			appProfileType = initData.getInitiationHeader().getApplicationProfileType().getValue();

		String lang = (appProfileType == null || appProfileType.isEmpty()) ? "en" : appProfileType;

		URL url = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
				.setPath(localConfig.getServerSuffix(), AlephConstants.ITEM_PATH_ELEMENT, recordId, AlephConstants.PARAM_ITEMS, itemId).addRequest("lang", lang).toURL();

		InputSource streamSource = new InputSource(url.openStream());

		AlephItemHandler itemHandler = new AlephItemHandler(initData);

		parser.parse(streamSource, itemHandler);

		return itemHandler.getCurrentAlephItem();
	}

	public List<AlephItem> lookupItems(String id, LookupItemSetInitiationData luisInitData, AlephLookupItemSetService service) throws ParserConfigurationException, IOException,
			SAXException, AlephException {
		LookupItemInitiationData lookupItemInitData = new LookupItemInitiationData();
		lookupItemInitData.setBibliographicDescriptionDesired(luisInitData.getBibliographicDescriptionDesired());
		lookupItemInitData.setCirculationStatusDesired(luisInitData.getCirculationStatusDesired());
		lookupItemInitData.setHoldQueueLengthDesired(luisInitData.getHoldQueueLengthDesired());
		lookupItemInitData.setItemDescriptionDesired(luisInitData.getItemDescriptionDesired());
		lookupItemInitData.setItemUseRestrictionTypeDesired(luisInitData.getItemUseRestrictionTypeDesired());
		lookupItemInitData.setLocationDesired(luisInitData.getLocationDesired());
		lookupItemInitData.setInitiationHeader(luisInitData.getInitiationHeader());
		return lookupItems(id, lookupItemInitData, service);
	}

	public List<AlephItem> lookupItems(String recordId, LookupItemInitiationData lookupItemInitData, AlephLookupItemSetService service) throws ParserConfigurationException,
			IOException, SAXException, AlephException {

		boolean isCorrectRecordId = AlephUtil.isCorrectRecordId(recordId, localConfig.getBibLibraryLength());

		if (!isCorrectRecordId) {
			throw new AlephException("Record Id is accepted only in strict format with strict length. e.g. MZK01000000421");
		}
		String appProfileType = null;
		if (lookupItemInitData.getInitiationHeader() != null && lookupItemInitData.getInitiationHeader().getApplicationProfileType() != null)
			appProfileType = lookupItemInitData.getInitiationHeader().getApplicationProfileType().getValue();

		String lang = (appProfileType == null || appProfileType.isEmpty()) ? "en" : appProfileType;

		URL url;
		InputSource streamSource;

		AlephItemHandler itemHandler = new AlephItemHandler(lookupItemInitData);

		// If there is maximumItemsCount set, then parse only URLs in range of maxItemsCount
		// Else parse all at once with "view=full" GET request

		if (service.getMaximumItemsCount() != 0) {

			ItemToken nextItemToken = service.getNextItemToken();

			int startFrom = (nextItemToken != null && nextItemToken.getBibliographicId().equalsIgnoreCase(recordId)) ? nextItemToken.getNoOfDoneItemIds() : 0;
			int maxLinks = service.getMaximumItemsCount() - service.getItemsForwarded();

			AlephURLsHandler urlsHandler = new AlephURLsHandler(startFrom, maxLinks);

			url = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.ITEM_PATH_ELEMENT, recordId, AlephConstants.PARAM_ITEMS).toURL();

			streamSource = new InputSource(url.openStream());

			parser.parse(streamSource, urlsHandler);

			if (urlsHandler.haveParsedMaxLinks()) {
				// Just create NextItemToken & continue

				// It is important to let token create even if the bibId is last of all desired bibIds in case of not all children items can be forwarded due to maximumItemsCount

				// Do not create NextItemToken if this is last item desired
				boolean createNewItemToken = !urlsHandler.haveParsedAll() || urlsHandler.haveParsedAll() && !service.isLastDesiredId();

				if (createNewItemToken) {
					// Set next item token
					ItemToken itemToken = new ItemToken();

					itemToken.setBibliographicId(recordId);

					itemToken.setIsRecordId(true);

					if (urlsHandler.haveParsedAll())
						itemToken.setDoneWithRecordId(true);
					else
						itemToken.setNoOfDoneItemIds(urlsHandler.getNextLinkIndex());

					String tokenKey = Integer.toString(random.nextInt());

					itemToken.setNextToken(tokenKey);

					service.addItemToken(tokenKey, itemToken);

					service.setNewTokenKey(tokenKey);

				}
			}

			service.setItemsForwarded(service.getItemsForwarded() + urlsHandler.getLinks().size());

			BigDecimal totalNumberOfPieces = new BigDecimal(urlsHandler.getTotalItemsCount());

			for (String link : urlsHandler.getLinks()) {

				url = new URLBuilder().parseLink(link).addRequest("lang", lang).toURL();

				streamSource = new InputSource(url.openStream());

				parser.parse(streamSource, itemHandler);

				itemHandler.getCurrentAlephItem().setNumberOfPieces(totalNumberOfPieces);

				// Now because handler doesn't know URL it's parsing, we need to set itemIds manually
				String[] linkParts = link.split("/");
				itemHandler.getCurrentAlephItem().setItemId(linkParts[5] + AlephConstants.UNIQUE_ITEM_ID_SEPARATOR + linkParts[7]);
			}

		} else {

			url = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.ITEM_PATH_ELEMENT, recordId, AlephConstants.PARAM_ITEMS).addRequest("view", "full")
					.addRequest("lang", lang).toURL();

			streamSource = new InputSource(url.openStream());

			parser.parse(streamSource, itemHandler);
		}
		return itemHandler.getListOfItems();

	}

	/**
	 * @param patronId
	 * @param {@link LookupUserInitiationData} initData
	 * @return {@link AlephRestDlfUser}
	 * @throws AlephException
	 * @throws IOException
	 * @throws SAXException
	 */
	public AlephRestDlfUser lookupUser(String patronId, LookupUserInitiationData initData) throws AlephException, IOException, SAXException {

		boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();
		boolean loanedItemsDesired = initData.getLoanedItemsDesired();
		boolean nameInformationDesired = initData.getNameInformationDesired();
		boolean requestedItemsDesired = initData.getRequestedItemsDesired();
		boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();
		boolean userFiscalAccountDesired = initData.getUserFiscalAccountDesired();
		boolean userIdDesired = initData.getUserIdDesired();
		boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();

		boolean atLeastOneDesired = false;

		// If there are only loanedItems desired, that means to include loans history to output
		// Better suggestions how to forward loaned items history, please send to kozlovsky@mzk.cz
		boolean loanedItemsDesiredOnly = loanedItemsDesired
				&& !(blockOrTrapDesired || nameInformationDesired || requestedItemsDesired || userAddressInformationDesired || userFiscalAccountDesired || userIdDesired || userPrivilegeDesired);

		String appProfileType = null;
		if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null)
			appProfileType = initData.getInitiationHeader().getApplicationProfileType().getValue();

		String lang = (appProfileType == null || appProfileType.isEmpty()) ? "en" : appProfileType;

		// Create URL request only if specified service was desired
		URL addressUrl = null;
		if (nameInformationDesired || userIdDesired || userAddressInformationDesired || userPrivilegeDesired) {
			atLeastOneDesired = true;
			addressUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_PATRON_INFO, AlephConstants.PARAM_ADDRESS).toURL();
		}

		URL circulationsUrl = null;
		URL cashUrl = null;
		if (userFiscalAccountDesired) {
			atLeastOneDesired = true;
			circulationsUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS).toURL();
			cashUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, "cash").addRequest("view", "full")
					.toURL();
		}

		URL loansUrl = null;
		URL loansHistoryUrl = null;
		if (loanedItemsDesired) {
			atLeastOneDesired = true;
			if (!loanedItemsDesiredOnly) {
				// If there is not history expected, parse regular loans
				loansUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
						.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_LOANS)
						.addRequest("view", "full").addRequest("lang", lang).toURL();

			} else {
				loansHistoryUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
						.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_LOANS)
						.addRequest("view", "full").addRequest("type", "history").addRequest("lang", lang).toURL();
			}
		}

		URL requestsUrl = null;
		if (requestedItemsDesired) {
			atLeastOneDesired = true;
			// We suppose desired requests are at http://aleph.mzk.cz:1892/rest-dlf/patron/930118BXGO/circulationActions/requests/holds?view=full
			requestsUrl = new URLBuilder()
					.setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
							AlephConstants.PARAM_HOLDS).addRequest("view", "full").addRequest("lang", lang).toURL();
		}

		URL blocksOrTrapsUrl = null;
		if (blockOrTrapDesired) {
			atLeastOneDesired = true;
			blocksOrTrapsUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_PATRON_STATUS, AlephConstants.PARAM_BLOCKS).toURL();
		}

		URL registrationUrl = null;
		if (userPrivilegeDesired) {
			atLeastOneDesired = true;
			registrationUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_PATRON_STATUS, AlephConstants.PARAM_REGISTRATION)
					.toURL();
		}

		if (atLeastOneDesired) {
			AlephRestDlfUser alephUser = new AlephRestDlfUser();

			AlephUserHandler userHandler = new AlephUserHandler(initData);

			InputSource streamSource;

			if (loansUrl != null || loansHistoryUrl != null) {

				AlephLoanHandler loanHandler = new AlephLoanHandler(localConfig);

				if (appProfileType != null && !appProfileType.isEmpty())
					loanHandler.setLocalizationDesired(true);

				if (loansUrl != null) {
					streamSource = new InputSource(loansUrl.openStream());
					parser.parse(streamSource, loanHandler);
					alephUser.setLoanedItems(loanHandler.getListOfLoanedItems());
				} else {
					streamSource = new InputSource(loansHistoryUrl.openStream());
					parser.parse(streamSource, loanHandler);
					alephUser.setLoanedItems(loanHandler.getListOfLoanedItems());
				}

			}
			if (requestsUrl != null) {
				AlephRequestItemHandler requestItemHandler = new AlephRequestItemHandler(localConfig.getBibLibrary());

				if (appProfileType != null && !appProfileType.isEmpty())
					requestItemHandler.setLocalizationDesired(true);

				streamSource = new InputSource(requestsUrl.openStream());
				parser.parse(streamSource, requestItemHandler);

				List<RequestedItem> requestedItems = requestItemHandler.getRequestedItems();

				if (localConfig.getMaxItemPreparationTimeDelay() != 0)
					for (RequestedItem requestedItem : requestedItems) {
						if (requestedItem.getDatePlaced() != null) {
							// Because Aleph does not support default delay between pickupDate and datePlaced, we will use custom configuration to set it
							GregorianCalendar pickupDate = (GregorianCalendar) requestedItem.getDatePlaced().clone();
							pickupDate.add(Calendar.DAY_OF_MONTH, localConfig.getMaxItemPreparationTimeDelay());
							requestedItem.setPickupDate(pickupDate);
						}
					}

				alephUser.setRequestedItems(requestedItems);
			}

			userHandler.setAlephUser(alephUser);

			if (addressUrl != null) {
				streamSource = new InputSource(addressUrl.openStream());

				parser.parse(streamSource, userHandler);
			}
			if (cashUrl != null) {
				streamSource = new InputSource(cashUrl.openStream());

				parser.parse(streamSource, userHandler);
			}
			if (circulationsUrl != null) {
				streamSource = new InputSource(circulationsUrl.openStream());

				parser.parse(streamSource, userHandler);
			}
			if (blocksOrTrapsUrl != null) {
				streamSource = new InputSource(blocksOrTrapsUrl.openStream());

				parser.parse(streamSource, userHandler);
			}
			if (registrationUrl != null) {
				streamSource = new InputSource(registrationUrl.openStream());

				parser.parse(streamSource, userHandler);
			}

			return userHandler.getAlephUser();
		} else
			return null;
	}

	public AlephRequestItem lookupRequest(LookupRequestInitiationData initData) throws AlephException, IOException, SAXException, ParserConfigurationException {

		String alephItemId = initData.getItemId().getItemIdentifierValue();

		String recordId = AlephUtil.parseRecordIdFromAlephItemId(alephItemId);
		String itemId = AlephUtil.parseItemIdFromAlephItemId(alephItemId);

		boolean isCorrectRecordId = AlephUtil.isCorrectRecordId(recordId, localConfig.getBibLibraryLength());

		if (!isCorrectRecordId) {
			throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01001276830-MZK50001311815000020");
		}

		AlephRequestItem requestItem = new AlephRequestItem();

		String patronId = initData.getUserId().getUserIdentifierValue();

		String appProfileType = null;
		if (initData.getInitiationHeader() != null && initData.getInitiationHeader().getApplicationProfileType() != null)
			appProfileType = initData.getInitiationHeader().getApplicationProfileType().getValue();

		String lang = (appProfileType == null || appProfileType.isEmpty()) ? "en" : appProfileType;

		URL holdsUrl = new URLBuilder()
				.setBase(localConfig.getServerName(), localConfig.getServerPort())
				.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
						AlephConstants.PARAM_HOLDS).addRequest("lang", lang).toURL();

		AlephRequestHandler requestHandler = new AlephRequestHandler(itemId, requestItem);
		InputSource streamSource = new InputSource(holdsUrl.openStream());
		parser.parse(streamSource, requestHandler);

		if (requestHandler.requestWasFound() && requestHandler.getRequestLink() != null) {

			if (localConfig.getMaxItemPreparationTimeDelay() != 0 && requestItem.getDatePlaced() != null) {
				GregorianCalendar pickupDate = (GregorianCalendar) requestItem.getDatePlaced().clone();
				pickupDate.add(Calendar.DAY_OF_MONTH, localConfig.getMaxItemPreparationTimeDelay());
				requestItem.setPickupDate(pickupDate);
			}

			requestItem.setRequestType(initData.getRequestType());
			requestItem.setRequestScopeType(Version1RequestScopeType.ITEM);

			URL requestLink = new URL(requestHandler.getRequestLink());

			streamSource = new InputSource(requestLink.openStream());

			requestHandler.setParsingRequests();
			parser.parse(streamSource, requestHandler);

			boolean nameInformationDesired = initData.getNameInformationDesired();
			boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();
			boolean userIdDesired = initData.getUserIdDesired();
			boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();
			boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();

			InitiationHeader initiationHeader = new InitiationHeader();
			ApplicationProfileType applicationProfileType = new ApplicationProfileType("", appProfileType);
			initiationHeader.setApplicationProfileType(applicationProfileType);

			if (nameInformationDesired || userAddressInformationDesired || userIdDesired || userPrivilegeDesired || blockOrTrapDesired) {
				LookupUserInitiationData userInitData = new LookupUserInitiationData();
				userInitData.setNameInformationDesired(nameInformationDesired);
				userInitData.setUserAddressInformationDesired(userAddressInformationDesired);
				userInitData.setUserIdDesired(userIdDesired);
				userInitData.setUserPrivilegeDesired(userPrivilegeDesired);
				userInitData.setBlockOrTrapDesired(blockOrTrapDesired);

				userInitData.setInitiationHeader(initiationHeader);

				AlephRestDlfUser user = lookupUser(patronId, userInitData);
				requestItem.setUserOptionalFields(user.getUserOptionalFields());
			}

			boolean getBibDescription = initData.getBibliographicDescriptionDesired();
			boolean getCircStatus = initData.getCirculationStatusDesired();
			boolean getHoldQueueLength = initData.getHoldQueueLengthDesired();
			boolean getItemDescription = initData.getItemDescriptionDesired();
			boolean getLocation = initData.getLocationDesired();

			if (getBibDescription || getCircStatus || getHoldQueueLength || getItemDescription || getLocation) {

				LookupItemInitiationData LIinitData = new LookupItemInitiationData();
				ItemId LIitemId = new ItemId();
				LIitemId.setItemIdentifierValue(alephItemId);

				LIinitData.setInitiationHeader(initiationHeader);

				LIinitData.setItemId(LIitemId);
				LIinitData.setBibliographicDescriptionDesired(getBibDescription);
				LIinitData.setCirculationStatusDesired(getCircStatus);
				LIinitData.setHoldQueueLengthDesired(getHoldQueueLength);
				LIinitData.setItemDescriptionDesired(getItemDescription);
				LIinitData.setLocationDesired(getLocation);

				AlephItem item = lookupItem(LIinitData);
				if (item == null)
					throw new AlephException("Unknown itemId passed.");

				requestItem.setItemOptionalFields(item.getItemOptionalFields());
			}
		} else {
			Problem problem = new Problem();
			problem.setProblemType(new ProblemType("Request does not exist."));
			requestItem.setProblem(problem);
		}

		return requestItem;
	}

	public AlephRequestItem requestItem(RequestItemInitiationData initData) throws AlephException, IOException, SAXException, ParserConfigurationException {

		List<ItemId> itemIds = initData.getItemIds();
		String patronId = initData.getUserId().getUserIdentifierValue();

		AlephRequestItem requestItem = new AlephRequestItem();
		String alephItemId;

		for (ItemId itemId : itemIds) {
			alephItemId = itemId.getItemIdentifierValue();

			if (!alephItemId.isEmpty()) {
				String recordId = AlephUtil.parseRecordIdFromAlephItemId(alephItemId);
				String itemIdVal = AlephUtil.parseItemIdFromAlephItemId(alephItemId);

				boolean isCorrectRecordId = AlephUtil.isCorrectRecordId(recordId, localConfig.getBibLibraryLength());
				boolean isCorrectItemId = AlephUtil.isCorrectItemId(itemIdVal, localConfig.getBibLibraryLength());

				if (!isCorrectRecordId || !isCorrectItemId) {
					throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01001276830-MZK50001311815000020");
				}

				String pickupLocation = null;
				if (initData.getPickupLocation() != null)
					pickupLocation = initData.getPickupLocation().getValue();

				String needBeforeDate = AlephUtil.convertToAlephDate(initData.getNeedBeforeDate());
				String earliestDateNeeded = AlephUtil.convertToAlephDate(initData.getEarliestDateNeeded());

				// Build e.g. this: MZK50001365071000010
				// String alephItemId = admLibrary + recordId.substring(AlephConstants.LIBRARY_PARAM_LENGTH) + sequenceNumber;

				// We need to put HTML PUT request to e.g. http://aleph.mzk.cz:1892/rest-dlf/patron/700/record/MZK01001330134/holds/MZK50001365071000010
				URL holdUrl = new URLBuilder()
						.setBase(localConfig.getServerName(), localConfig.getServerPort())
						.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_RECORD, recordId, AlephConstants.PARAM_HOLDS,
								itemIdVal).toURL();

				String firstNote = null;
				// Our own testing identifier system (Moravian Library's)
				if (patronId.equalsIgnoreCase("700") || true) {
					firstNote = "dg";
				}

				String XMLRequest = new XMLBuilder().setParent("hold-request-parameters").setPickupLocation(pickupLocation).setLastInterestDate(needBeforeDate)
						.setStartInterestDate(earliestDateNeeded).setFirstNote(firstNote).setRush("N").toString();

				InputSource streamSource;
				HttpURLConnection httpCon = (HttpURLConnection) holdUrl.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("PUT");

				OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
				outWriter.write(XMLRequest);
				outWriter.close();

				streamSource = new InputSource(httpCon.getInputStream());

				AlephRequestItemHandler requestItemHandler = new AlephRequestItemHandler(localConfig.getBibLibrary());
				parser.parse(streamSource, requestItemHandler);

				if (!requestItemHandler.returnedError()) {

					// Parse sequence number
					URL holdsUrl = new URLBuilder()
							.setBase(localConfig.getServerName(), localConfig.getServerPort())
							.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
									AlephConstants.PARAM_HOLDS).toURL();

					streamSource = new InputSource(holdsUrl.openStream());
					parser.parse(streamSource, requestItemHandler.parseSequenceNumber(itemIdVal));

					String seqNumber = requestItemHandler.getSequenceNumber();

					// Sample URL: http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds/MZK500013118150000200001
					URL requestsUrl = new URLBuilder()
							.setBase(localConfig.getServerName(), localConfig.getServerPort())
							.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
									AlephConstants.PARAM_HOLDS, itemIdVal + seqNumber).toURL();

					streamSource = new InputSource(requestsUrl.openStream());
					parser.parse(streamSource, requestItemHandler);

					requestItem.addRequestId(requestItemHandler.getRequestId());

				} else if (itemIds.size() > 1) {
					// If there is more than one item requested, than it is a good habit to let user know of which items could not been requested and why
					String noteValue = requestItemHandler.getNoteValue();

					if (noteValue == null)
						noteValue = "";
					else
						noteValue = " - " + requestItemHandler.getNoteValue();

					requestItem.addRequestId(requestItemHandler.getReplyText() + noteValue);
					// Output of this will usually be: Failed to create request - Patron has already requested this item.
				} else {
					Problem problem = new Problem();

					problem.setProblemValue(requestItemHandler.getReplyText());

					if (requestItemHandler.getNoteValue() != null)
						problem.setProblemDetail(requestItemHandler.getNoteValue() + " (" + alephItemId + ")");

					problem.setProblemType(new ProblemType("Aleph returned error while processing the request. See details below."));
					requestItem.setProblem(problem);
				}
			} else if (itemIds.size() > 1) {
				// If there is more than one item requested, than it is a good habit to let user know of which items could not been requested and why
				requestItem.addRequestId("Failed to create request - Empty ItemIdentifierValue was specified here.");
			} else {
				Problem problem = new Problem();
				problem.setProblemDetail("Cannot request unknown item.");
				problem.setProblemType(new ProblemType("You have sent empty ItemIdentifierValue."));
				requestItem.setProblem(problem);
			}
		}

		return requestItem;
	}

	public AlephRequestItem cancelRequestItem(CancelRequestItemInitiationData initData) throws AlephException, IOException, SAXException, ParserConfigurationException {

		String alephItemId = initData.getItemId().getItemIdentifierValue();

		String recordId = AlephUtil.parseRecordIdFromAlephItemId(alephItemId);
		String itemId = AlephUtil.parseItemIdFromAlephItemId(alephItemId);

		boolean isCorrectRecordId = AlephUtil.isCorrectRecordId(recordId, localConfig.getBibLibraryLength());
		boolean isCorrectItemId = AlephUtil.isCorrectItemId(itemId, localConfig.getBibLibraryLength());

		if (!isCorrectRecordId || !isCorrectItemId) {
			throw new AlephException("Item Id is accepted only in strict format with strict length. e.g. MZK01001276830-MZK50001311815000020");
		}

		AlephRequestItem requestItem = new AlephRequestItem();

		String patronId = initData.getUserId().getUserIdentifierValue();

		// Parse sequence number
		URL holdsUrl = new URLBuilder()
				.setBase(localConfig.getServerName(), localConfig.getServerPort())
				.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
						AlephConstants.PARAM_HOLDS).toURL();

		AlephRequestItemHandler requestItemHandler = new AlephRequestItemHandler(localConfig.getBibLibrary()).parseSequenceNumber(itemId);

		InputSource streamSource = new InputSource(holdsUrl.openStream());
		parser.parse(streamSource, requestItemHandler);

		if (requestItemHandler.requestWasFound() && requestItemHandler.isDeletable()) {

			String seqNumber = requestItemHandler.getSequenceNumber();

			// Sample URL: http://aleph.mzk.cz:1892/rest-dlf/patron/700/circulationActions/requests/holds/MZK500013118150000200001
			URL holdRequestUrl = new URLBuilder()
					.setBase(localConfig.getServerName(), localConfig.getServerPort())
					.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_REQUESTS,
							AlephConstants.PARAM_HOLDS, itemId + seqNumber).toURL();

			HttpURLConnection httpCon = (HttpURLConnection) holdRequestUrl.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("DELETE");

			streamSource = new InputSource(httpCon.getInputStream());

			parser.parse(streamSource, requestItemHandler);

			if (requestItemHandler.returnedError()) {
				Problem problem = new Problem();
				problem.setProblemValue(requestItemHandler.getReplyText());
				problem.setProblemType(new ProblemType("Aleph returned error while processing cancel request. See details below."));
				requestItem.setProblem(problem);
			}
		} else if (requestItemHandler.requestWasFound()) {
			Problem problem = new Problem();
			problem.setProblemValue("Delete value of specified hold request is set to \"N\"");
			problem.setProblemType(new ProblemType("Requested deletion is not allowed."));
			requestItem.setProblem(problem);
		} else {
			Problem problem = new Problem();
			problem.setProblemType(new ProblemType("Request does not exist."));
			requestItem.setProblem(problem);
		}
		return requestItem;
	}

	public AlephRenewItem renewItem(RenewItemInitiationData initData) throws AlephException, IOException, SAXException, ParserConfigurationException {

		String alephLoanId = initData.getItemId().getItemIdentifierValue();

		boolean isCorrectLoanId = AlephUtil.isCorrectLoanId(alephLoanId, localConfig.getBibLibraryLength());

		if (!isCorrectLoanId) {
			throw new AlephException("Loan Id is accepted only in strict format with strict length. e.g. MZK50004929137");
		}

		AlephRenewItem renewItem = new AlephRenewItem();

		String patronId = initData.getUserId().getUserIdentifierValue();

		URL loansUrl = new URLBuilder().setBase(localConfig.getServerName(), localConfig.getServerPort())
				.setPath(localConfig.getServerSuffix(), AlephConstants.USER_PATH_ELEMENT, patronId, AlephConstants.PARAM_CIRC_ACTIONS, AlephConstants.PARAM_LOANS).toURL();

		AlephRenewHandler renewHandler = new AlephRenewHandler(alephLoanId, renewItem);

		InputSource streamSource = new InputSource(loansUrl.openStream());

		// Here parser looks up our loan about to be renewed
		parser.parse(streamSource, renewHandler);

		if (renewHandler.loanWasFound() && renewHandler.isRenewable()) {

			URL loanLink = new URL(renewHandler.getLoanLink());

			String XMLRequest = AlephUtil.buildRenewPOSTXml(initData.getDesiredDateDue());

			HttpURLConnection httpCon = (HttpURLConnection) loanLink.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");

			OutputStreamWriter outWriter = new OutputStreamWriter(httpCon.getOutputStream());
			outWriter.write(XMLRequest);
			outWriter.close();

			streamSource = new InputSource(httpCon.getInputStream());

			// Here parser finds out whether was renew successful or not & why
			parser.parse(streamSource, renewHandler);

			if (renewHandler.actionSucceeded()) {

				boolean nameInformationDesired = initData.getNameInformationDesired();
				boolean userAddressInformationDesired = initData.getUserAddressInformationDesired();
				boolean userIdDesired = initData.getUserIdDesired();
				boolean userPrivilegeDesired = initData.getUserPrivilegeDesired();
				boolean blockOrTrapDesired = initData.getBlockOrTrapDesired();

				if (nameInformationDesired || userAddressInformationDesired || userIdDesired || userPrivilegeDesired || blockOrTrapDesired) {
					LookupUserInitiationData userInitData = new LookupUserInitiationData();
					userInitData.setNameInformationDesired(nameInformationDesired);
					userInitData.setUserAddressInformationDesired(userAddressInformationDesired);
					userInitData.setUserIdDesired(userIdDesired);
					userInitData.setUserPrivilegeDesired(userPrivilegeDesired);
					userInitData.setBlockOrTrapDesired(blockOrTrapDesired);

					userInitData.setInitiationHeader(initData.getInitiationHeader());

					AlephRestDlfUser user = lookupUser(patronId, userInitData);
					renewItem.setUserOptionalFields(user.getUserOptionalFields());
				}

				boolean getBibDescription = initData.getBibliographicDescriptionDesired();
				boolean getCircStatus = initData.getCirculationStatusDesired();
				boolean getHoldQueueLength = initData.getHoldQueueLengthDesired();
				boolean getItemDescription = initData.getItemDescriptionDesired();
				boolean getLocation = initData.getLocationDesired();

				if (getBibDescription || getCircStatus || getHoldQueueLength || getItemDescription || getLocation) {

					streamSource = new InputSource(loanLink.openStream());

					// Here the parser looks for itemDocNo, bibDocNo & itemSeqNo
					parser.parse(streamSource, renewHandler);

					if (renewHandler.isFullIdFound()) {
						String alephItemId = AlephUtil.buildAlephItemId(localConfig, renewHandler.getBibDocNumber(), renewHandler.getItemDocNumber(),
								renewHandler.getItemSequenceNumber());

						LookupItemInitiationData LIinitData = new LookupItemInitiationData();
						ItemId itemId = new ItemId();
						itemId.setItemIdentifierValue(alephItemId);

						LIinitData.setInitiationHeader(initData.getInitiationHeader());

						LIinitData.setItemId(itemId);
						LIinitData.setBibliographicDescriptionDesired(getBibDescription);
						LIinitData.setCirculationStatusDesired(getCircStatus);
						LIinitData.setHoldQueueLengthDesired(getHoldQueueLength);
						LIinitData.setItemDescriptionDesired(getItemDescription);
						LIinitData.setLocationDesired(getLocation);

						AlephItem item = lookupItem(LIinitData);

						renewItem.setItemOptionalFields(item.getItemOptionalFields());
					}
				}
			} else {
				Problem problem = new Problem();
				problem.setProblemDetail(renewHandler.getStatusText());
				problem.setProblemType(new ProblemType(renewHandler.getReplyText()));
				renewItem.setProblem(problem);
			}
		} else if (renewHandler.loanWasFound()) {
			Problem problem = new Problem();
			problem.setProblemType(new ProblemType("Loan is marked as not renewable."));
			renewItem.setProblem(problem);
		} else {
			Problem problem = new Problem();
			problem.setProblemType(new ProblemType("Loan does not exist."));
			renewItem.setProblem(problem);
		}
		return renewItem;
	}

	public AgencyAddressInformation getAgencyPhysicalAddressInformation() {
		AgencyAddressInformation agencyAddressInformation = new AgencyAddressInformation();

		PhysicalAddress physicalAddress = new PhysicalAddress();

		UnstructuredAddress unstructuredAddress = new UnstructuredAddress();

		unstructuredAddress.setUnstructuredAddressData(localConfig.getAgencyAddress());
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

		organizationNameInfo.setOrganizationName(localConfig.getAgencyName());
		organizationNameInfo.setOrganizationNameType(Version1OrganizationNameType.TRANSLATED_NAME);
		organizationNameInformations.add(organizationNameInfo);
		return organizationNameInformations;
	}
}
