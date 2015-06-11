package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.extensiblecatalog.ncip.v2.koha.util.ItemToken;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.BibInformation;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.ILSDIvOneOneLookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.Version1GeneralProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

public class KohaLookupItemSetService implements org.extensiblecatalog.ncip.v2.ilsdiv1_1.ILSDIv1_1_LookupItemSetService {

	private static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();

	private Random random = new Random();

	private String newTokenKey = null;

	private List<BibInformation> bibInformations;

	private boolean isLast;

	private int maximumItemsCount;

	private int itemsForwarded;

	private ItemToken nextItemToken;

	@Override
	public LookupItemSetResponseData performService(ILSDIvOneOneLookupItemSetInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		maximumItemsCount = parseMaximumItemsCount(initData);

		KohaRemoteServiceManager kohaSvcMgr = (KohaRemoteServiceManager) serviceManager;

		final LookupItemSetResponseData responseData = new LookupItemSetResponseData();

		List<Problem> problems = new ArrayList<Problem>();
		try {
			nextItemToken = parseItemToken(initData, kohaSvcMgr);

			if (nextItemToken != null && nextItemToken.isExpired()) {

				Problem p = new Problem(new ProblemType("Usage of expired NextItemToken is not allowed."), null, null);
				problems.add(p);
			}
		} catch (ServiceException se) {
			if (se.getError().equals(ServiceError.UNSUPPORTED_REQUEST)) {
				Problem p = new Problem(new ProblemType("Unknown NextItemToken was used."), null, null, "It has probably been deleted.");
				problems.add(p);
			}
		}

		boolean itemIdsIsEmpty = initData.getItemIds() == null || initData.getItemIds().size() == 1 && initData.getItemId(0).getItemIdentifierValue().isEmpty();

		boolean bibRecIdIsEmpty = initData.getBibliographicIds() == null || initData.getBibliographicIds().size() == 1
				&& initData.getBibliographicId(0).getBibliographicItemId() == null
				&& initData.getBibliographicId(0).getBibliographicRecordId().getBibliographicRecordIdentifier().isEmpty();

		boolean bibItemIdIsEmpty = initData.getBibliographicIds() == null || initData.getBibliographicIds().size() == 1
				&& initData.getBibliographicId(0).getBibliographicRecordId() == null
				&& initData.getBibliographicId(0).getBibliographicItemId().getBibliographicItemIdentifier().isEmpty();

		if (itemIdsIsEmpty && bibRecIdIsEmpty && bibItemIdIsEmpty) {

			Problem p = new Problem(new ProblemType("Item id nor Bibliographic Id is undefined."), null, null);
			problems.add(p);

		}

		if (problems.size() == 0) {

			bibInformations = new ArrayList<BibInformation>();
			itemsForwarded = 0;

			if (initData.getBibliographicIds() != null && initData.getBibliographicIds().size() > 0) {

				parseBibIds(initData, responseData, kohaSvcMgr);

			} else if (initData.getItemIds() != null && initData.getItemIds().size() > 0) {

				parseItemIds(initData, responseData, kohaSvcMgr);

			} else {
				Problem p = new Problem(new ProblemType("LookupItemSet service has no implemented service to process HoldingsSetId."), null, null);
				responseData.setProblems(Arrays.asList(p));
			}

			if (responseData.getProblems() == null) {

				responseData.setBibInformations(bibInformations);

				ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

				if (responseHeader != null)
					responseData.setResponseHeader(responseHeader);
			}
		} else
			responseData.setProblems(problems);

		return responseData;
	}

	/**
	 * Sets {@link LookupItemSetResponseData} with desired values. *
	 * 
	 * @param initData
	 * @param responseData
	 * @param kohaSvcMgr
	 * @param nextItemToken
	 */
	private void parseBibIds(ILSDIvOneOneLookupItemSetInitiationData initData, LookupItemSetResponseData responseData, KohaRemoteServiceManager kohaSvcMgr) {

		boolean wantSeeAllProblems = LocalConfig.isEchoParticularProblemsToLUIS();

		List<BibliographicId> bibIds = initData.getBibliographicIds();

		List<HoldingsSet> holdingSets;

		for (BibliographicId bibId : bibIds) {

			BibInformation bibInformation = new BibInformation();

			isLast = bibIds.get(bibIds.size() - 1).equals(bibId);

			try {
				if (bibId.getBibliographicRecordId() != null) {
					String bibRecordIdVal = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();

					if (bibRecordIdVal.isEmpty()) {
						if (wantSeeAllProblems) {
							Problem problem = new Problem(new ProblemType("Empty BibliographicRecordIdentifierValue."), null,
									"Here you have specified empty BibliographicRecordIdentifierValue.");
							bibInformation.setProblems(Arrays.asList(problem));
							bibInformation.setBibliographicId(bibId);
							bibInformations.add(bibInformation);
						}
						continue;
					}

					bibInformation.setBibliographicId(bibId);

					JSONObject kohaItem = kohaSvcMgr.lookupItem(bibRecordIdVal, initData);

					holdingSets = Arrays.asList(parseHoldingsSetFromLookupItem(kohaItem, initData, bibRecordIdVal));

					bibInformation.setHoldingsSets(holdingSets);

					bibInformations.add(bibInformation);
					++itemsForwarded;

					// Do not create NextItemToken if this is last item desired
					boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

					if (createNextItemToken) {

						ItemToken itemToken = new ItemToken();
						itemToken.setBibliographicId(bibRecordIdVal);

						int newToken = random.nextInt();

						itemToken.setNextToken(Integer.toString(newToken));
						this.addItemToken(Integer.toString(newToken), itemToken);

						responseData.setNextItemToken(Integer.toString(newToken));
						break;
					}

				} else if (bibId.getBibliographicItemId() != null) {
					String bibItemIdVal = bibId.getBibliographicItemId().getBibliographicItemIdentifier();

					if (bibItemIdVal.isEmpty()) {
						if (wantSeeAllProblems) {
							Problem problem = new Problem(new ProblemType("Empty BibliographicItemIdentifierValue."), null,
									"Here you have specified empty BibliographicItemIdentifierValue.");
							bibInformation.setProblems(Arrays.asList(problem));
							bibInformations.add(bibInformation);
						}
						continue;
					}

					JSONObject response = kohaSvcMgr.lookupItemSet(bibItemIdVal, initData);

					bibInformation.setBibliographicId(bibId);

					holdingSets = Arrays.asList(parseHoldingsSetFromLookupItemSet(response, initData, bibItemIdVal));

					bibInformation.setHoldingsSets(holdingSets);

					bibInformations.add(bibInformation);

					if (newTokenKey != null) {
						responseData.setNextItemToken(newTokenKey);
						newTokenKey = null;
						break;
					}

				} else {

					bibInformation.setProblems(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "BibliographicItemId/RecordId", null,
							"BibliographicItemId/RecordId was not properly set. Please think about turning on SchemaValidation in toolkit.properties."));
					bibInformations.add(bibInformation);
					++itemsForwarded;
				}
			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (KohaException ke) {
				if (ke.getShortMessage().equals(KohaException.NOT_FOUND_404)) {

					if (wantSeeAllProblems) {
						Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + ke.getNotFoundIdentifierValue()
								+ ", you are searching for, does not exist.");

						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
						++itemsForwarded;

						// Do not create NextItemToken if this is last item desired
						boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

						if (createNextItemToken) {
							// Set next item token
							ItemToken itemToken = new ItemToken();

							itemToken.setBibliographicId(ke.getNotFoundIdentifierValue());

							int newToken = random.nextInt();
							itemToken.setNextToken(Integer.toString(newToken));

							this.addItemToken(Integer.toString(newToken), itemToken);

							responseData.setNextItemToken(Integer.toString(newToken));
							break;
						}
					}

				} else {
					Problem p = new Problem(new ProblemType(ke.getShortMessage()), null, ke.getMessage());
					responseData.setProblems(Arrays.asList(p));
					break;
				}
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, StringUtils.join(e.getStackTrace(), "\n"));
				responseData.setProblems(Arrays.asList(p));
				break;
			}

		}

	}

	/**
	 * Sets {@link LookupItemSetResponseData} with desired values.
	 * 
	 * @param initData
	 * @param responseData
	 * @param kohaSvcMgr
	 * @param nextItemToken
	 */
	private void parseItemIds(ILSDIvOneOneLookupItemSetInitiationData initData, LookupItemSetResponseData responseData, KohaRemoteServiceManager kohaSvcMgr) {

		boolean wantSeeAllProblems = LocalConfig.isEchoParticularProblemsToLUIS();

		List<ItemId> itemIds = initData.getItemIds();

		BibInformation bibInformation;

		List<HoldingsSet> holdingsSets;

		for (ItemId itemId : itemIds) {

			isLast = itemIds.get(itemIds.size() - 1).equals(itemId);

			String itemIdVal = itemId.getItemIdentifierValue();

			if (itemIdVal.isEmpty()) {
				if (wantSeeAllProblems) {
					bibInformation = new BibInformation();
					Problem problem = new Problem(new ProblemType("Empty ItemIdIdentifierValue."), null, "Here you have specified empty ItemIdIdentifierValue.");
					bibInformation.setProblems(Arrays.asList(problem));
					bibInformations.add(bibInformation);
				} else
					continue;
			}

			bibInformation = new BibInformation();

			try {
				JSONObject kohaItem = kohaSvcMgr.lookupItem(itemIdVal, initData);

				BibliographicId bibliographicId = new BibliographicId();
				BibliographicRecordId bibliographicItemId = KohaUtil.createBibliographicRecordIdAsAccessionNumber(itemIdVal);
				bibliographicId.setBibliographicRecordId(bibliographicItemId);

				bibInformation.setBibliographicId(bibliographicId);

				holdingsSets = Arrays.asList(parseHoldingsSetFromLookupItem(kohaItem, initData, itemIdVal));

				bibInformation.setHoldingsSets(holdingsSets);

				bibInformations.add(bibInformation);
				++itemsForwarded;

				// Do not create NextItemToken if this is last item desired
				boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

				if (createNextItemToken) {

					ItemToken itemToken = new ItemToken();
					itemToken.setBibliographicId(itemIdVal);

					int newToken = random.nextInt();

					itemToken.setNextToken(Integer.toString(newToken));
					this.addItemToken(Integer.toString(newToken), itemToken);

					responseData.setNextItemToken(Integer.toString(newToken));
					break;
				}
			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (KohaException ke) {
				if (ke.getShortMessage().equals(KohaException.NOT_FOUND_404)) {
					if (wantSeeAllProblems) {
						Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + itemIdVal + ", you are searching for, does not exist.");

						bibInformation.setProblems(Arrays.asList(p));

						// Note that Problem elements within <ns1:BibInformation> is also considered as one item forwarded
						bibInformations.add(bibInformation);
						++itemsForwarded;

						// Do not create NextItemToken if this is last item desired
						boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

						if (createNextItemToken) {

							ItemToken itemToken = new ItemToken();
							itemToken.setBibliographicId(itemIdVal);

							int newToken = random.nextInt();

							itemToken.setNextToken(Integer.toString(newToken));
							this.addItemToken(Integer.toString(newToken), itemToken);

							responseData.setNextItemToken(Integer.toString(newToken));
							break;
						}
					}
				} else {
					Problem p = new Problem(new ProblemType(ke.getShortMessage()), null, ke.getMessage());
					responseData.setProblems(Arrays.asList(p));
					break;
				}
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, StringUtils.join(e.getStackTrace(), "\n"));
				responseData.setProblems(Arrays.asList(p));
				break;
			}
		}

	}

	private HoldingsSet parseHoldingsSetFromLookupItemSet(JSONObject response, ILSDIvOneOneLookupItemSetInitiationData initData, String bibIdVal) throws ServiceException {
		HoldingsSet holdingsSet = new HoldingsSet();

		int startFrom = 0, maxItemsToParse = 0;
		if (maximumItemsCount != 0) {
			if (nextItemToken != null && nextItemToken.getBibliographicId().equalsIgnoreCase(bibIdVal))
				startFrom = nextItemToken.getNoOfDoneItemIds();

			maxItemsToParse = maximumItemsCount - itemsForwarded;
		}

		if (initData.getBibliographicDescriptionDesired()) {
			JSONObject itemInfo = (JSONObject) response.get("bibInfo");
			BibliographicDescription bibliographicDescription = KohaUtil.parseBibliographicDescription(itemInfo);
			holdingsSet.setBibliographicDescription(bibliographicDescription);
		}

		Long numberOfPieces = (Long) response.get("itemsCount");

		List<ItemInformation> itemInformations = new ArrayList<ItemInformation>();

		JSONArray items = (JSONArray) response.get("items");

		int i;
		for (i = startFrom; i < items.size(); ++i) {
			boolean canContinue = maxItemsToParse == 0 || maxItemsToParse > i - startFrom;
			if (canContinue) {
				JSONObject itemInfo = (JSONObject) items.get(i);
				ItemInformation itemInformation = new ItemInformation();

				ItemOptionalFields iof = new ItemOptionalFields();

				String itemId = (String) itemInfo.get("itemnumber");
				String agencyId = (String) itemInfo.get("holdingbranch");

				itemInformation.setItemId(KohaUtil.createItemId(itemId, agencyId));

				if (initData.getLocationDesired()) {
					String homeBranch = (String) itemInfo.get("homebranch");
					String locationVal = (String) itemInfo.get("location");
					if (homeBranch != null || locationVal != null) {
						iof.setLocations(KohaUtil.createLocations(homeBranch, locationVal));
					}
				}

				if (initData.getItemDescriptionDesired()) {
					ItemDescription itemDescription = new ItemDescription();

					if (numberOfPieces != null)
						itemDescription.setNumberOfPieces(new BigDecimal(numberOfPieces));

					String callNumber = (String) itemInfo.get("itemcallnumber");
					String copyNumber = (String) itemInfo.get("copynumber");

					itemDescription.setCallNumber(callNumber);
					itemDescription.setCopyNumber(copyNumber);

					iof.setItemDescription(itemDescription);
				}

				if (initData.getCirculationStatusDesired()) {
					String circulationStatus = (String) itemInfo.get("circulationStatus");
					iof.setCirculationStatus(Version1CirculationStatus.find(Version1CirculationStatus.VERSION_1_CIRCULATION_STATUS, circulationStatus));
				}

				if (initData.getHoldQueueLengthDesired()) {
					String holdQueueLength = (String) itemInfo.get("holdQueueLength");

					if (holdQueueLength != null)
						iof.setHoldQueueLength(new BigDecimal(holdQueueLength));
				}

				if (initData.getItemUseRestrictionTypeDesired()) {
					JSONArray itemUseRestrictions = (JSONArray) itemInfo.get("itemUseRestrictions");
					if (itemUseRestrictions != null && itemUseRestrictions.size() != 0) {
						List<ItemUseRestrictionType> itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();
						for (Object itemUseRestriction : itemUseRestrictions) {
							String itemUseRestrictionValue = (String) itemUseRestriction;
							itemUseRestrictionTypes.add(Version1ItemUseRestrictionType.find(Version1ItemUseRestrictionType.VERSION_1_ITEM_USE_RESTRICTION_TYPE,
									itemUseRestrictionValue));
						}
						iof.setItemUseRestrictionTypes(itemUseRestrictionTypes);
					}
				}

				if (initData.getUserId() != null) {

					// Process "Not For Loan" as the output of inability of requesting an item with provided UserId
					List<ItemUseRestrictionType> itemUseRestrictionTypes;

					boolean createdRestrictionsAlready = iof.getItemUseRestrictionTypes() != null && iof.getItemUseRestrictionTypes().size() > 0;

					if (createdRestrictionsAlready) {
						itemUseRestrictionTypes = iof.getItemUseRestrictionTypes();
					} else
						itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();

					String canBeRequestedVal = (String) itemInfo.get("canBeRequested");

					// Do not create "Not For Loan" if it's null - maybe Koha ILS not updated?
					boolean cannotBeRequested = canBeRequestedVal != null && !canBeRequestedVal.equals("y");

					if (cannotBeRequested)
						itemUseRestrictionTypes.add(Version1ItemUseRestrictionType.NOT_FOR_LOAN);

					// Do not set empty list ..
					if (createdRestrictionsAlready || cannotBeRequested)
						iof.setItemUseRestrictionTypes(itemUseRestrictionTypes);
				}

				itemInformation.setItemOptionalFields(iof);
				itemInformations.add(itemInformation);
				++itemsForwarded;

			} else {

				ItemToken itemToken = new ItemToken();

				itemToken.setBibliographicId(bibIdVal);

				itemToken.setIsRecordId(true);

				itemToken.setNoOfDoneItemIds(i);

				String tokenKey = Integer.toString(random.nextInt());

				itemToken.setNextToken(tokenKey);

				addItemToken(tokenKey, itemToken);

				newTokenKey = tokenKey;
				break;

			}
		}

		if (maxItemsToParse != 0 && maxItemsToParse == i - startFrom && !isLast && newTokenKey == null) {
			ItemToken itemToken = new ItemToken();

			itemToken.setBibliographicId(bibIdVal);

			itemToken.setIsRecordId(true);

			itemToken.setDoneWithRecordId(true);

			String tokenKey = Integer.toString(random.nextInt());

			itemToken.setNextToken(tokenKey);

			addItemToken(tokenKey, itemToken);

			newTokenKey = tokenKey;
		}

		holdingsSet.setItemInformations(itemInformations);

		return holdingsSet;
	}

	private HoldingsSet parseHoldingsSetFromLookupItem(JSONObject kohaItem, ILSDIvOneOneLookupItemSetInitiationData initData, String itemIdVal) throws ServiceException {
		HoldingsSet holdingsSet = new HoldingsSet();
		ItemInformation itemInfo = new ItemInformation();

		itemInfo.setItemOptionalFields(KohaUtil.parseItemOptionalFields(kohaItem, initData, itemIdVal));

		holdingsSet.setItemInformations(Arrays.asList(itemInfo));
		return holdingsSet;
	}

	/**
	 * Returns ItemToken stored in static HashMap, associated with {@link String} tokenKey.<br />
	 * If passed tokenKey is empty or null, null will be returned.
	 * 
	 * @param tokenKey
	 * @param bibIds
	 * @param itemIds
	 * @return {@link org.extensiblecatalog.ncip.v2.koha.util.ItemToken}
	 * @throws ServiceException
	 */
	private ItemToken parseItemToken(ILSDIvOneOneLookupItemSetInitiationData initData, KohaRemoteServiceManager kohaSvcMgr) throws ServiceException {

		String tokenKey = initData.getNextItemToken();
		List<BibliographicId> bibIds;
		List<ItemId> itemIds;

		ItemToken nextItemToken = null;

		if (tokenKey != null && !tokenKey.isEmpty()) {

			KohaUtil.markExpiredTokens(tokens, LocalConfig.getTokenExpirationTime());

			nextItemToken = tokens.get(tokenKey);
			if (nextItemToken != null && !nextItemToken.isExpired()) {

				bibIds = initData.getBibliographicIds();
				itemIds = initData.getItemIds();

				int index;

				if (bibIds != null && bibIds.size() > 0) {
					index = getBibIdIndex(bibIds, nextItemToken.getBibliographicId());
					if (index > -1) {
						// We can't remove bibId, which is not completely processed due to MaximumItemsCount
						if (nextItemToken.isRecordId() && !nextItemToken.doneWithRecordId())
							bibIds.subList(0, index).clear();
						else
							bibIds.subList(0, ++index).clear();
					}
				} else if (itemIds != null && itemIds.size() > 0) {
					index = getItemIdIndex(itemIds, nextItemToken.getBibliographicId());
					if (index > -1) {
						itemIds.subList(0, ++index).clear();
					}
				}

				tokens.remove(tokenKey);
			} else if (nextItemToken == null) {
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, null, null);
			} else {
				// Do nothing .. token expiration will be announced to user with Problem element in responseData
			}
		}
		return nextItemToken;
	}

	/**
	 * Zero (0) means there was no MaximumItemsCount element set in initiation message.
	 * 
	 * @param initData
	 * @return {@link Integer}
	 * @throws ServiceException
	 */
	private int parseMaximumItemsCount(ILSDIvOneOneLookupItemSetInitiationData initData) throws ServiceException {
		if (initData.getMaximumItemsCount() != null)
			try {
				return initData.getMaximumItemsCount().intValueExact();
			} catch (Exception e) {
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "MaximumItemsCount element is not in correct format. " + e.getMessage());
			}
		else
			return 0;
	}

	/**
	 * Returns -1 if in passed list of BibliographicIds was not found specified bibId.
	 * 
	 * @param bibIds
	 * @param bibId
	 * @return {@link Integer}
	 */
	private int getBibIdIndex(List<BibliographicId> bibIds, String bibId) {

		for (int i = 0; i < bibIds.size(); i++) {
			BibliographicId bibIdFromList = bibIds.get(i);
			if (bibIdFromList.getBibliographicItemId() != null) {
				if (bibIds.get(i).getBibliographicItemId().getBibliographicItemIdentifier().equalsIgnoreCase(bibId)) {
					return i;
				}
			} else if (bibIdFromList.getBibliographicRecordId() != null) {
				if (bibIds.get(i).getBibliographicRecordId().getBibliographicRecordIdentifier().equalsIgnoreCase(bibId)) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Returns -1 if in passed list of ItemIds was not found specified itemId.
	 * 
	 * @param itemIds
	 * @param itemId
	 * @return {@link Integer}
	 */
	private int getItemIdIndex(List<ItemId> itemIds, String itemId) {
		for (int i = 0; i < itemIds.size(); i++) {
			if (itemIds.get(i).getItemIdentifierValue().equalsIgnoreCase(itemId)) {
				return i;
			}
		}
		return -1;
	}

	public void addItemToken(String key, ItemToken token) {

		KohaUtil.purgeExpiredTokens(tokens);

		tokens.put(key, token);
	}

}
