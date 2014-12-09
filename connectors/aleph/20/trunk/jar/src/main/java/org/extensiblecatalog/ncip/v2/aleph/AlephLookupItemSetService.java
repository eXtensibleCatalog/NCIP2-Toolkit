package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.ItemToken;
import org.extensiblecatalog.ncip.v2.service.BibInformation;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.Version1GeneralProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.xml.sax.SAXException;

public class AlephLookupItemSetService implements LookupItemSetService {

	private static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();

	private Random random = new Random();

	private String newTokenKey = null;

	private List<BibInformation> bibInformations;

	private boolean isLast;

	private int maximumItemsCount;

	private int itemsForwarded;

	private ItemToken nextItemToken;

	@Override
	public LookupItemSetResponseData performService(LookupItemSetInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		maximumItemsCount = parseMaximumItemsCount(initData);

		AlephRemoteServiceManager alephSvcMgr = (AlephRemoteServiceManager) serviceManager;

		final LookupItemSetResponseData responseData = new LookupItemSetResponseData();

		List<Problem> problems = new ArrayList<Problem>();
		try {
			nextItemToken = parseItemToken(initData, alephSvcMgr);

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

		if (problems.size() > 0)
			responseData.setProblems(problems);

		if (responseData.getProblems() == null) {

			bibInformations = new ArrayList<BibInformation>();
			itemsForwarded = 0;

			if (initData.getBibliographicIds() != null && initData.getBibliographicIds().size() > 0) {

				parseBibIds(initData, responseData, alephSvcMgr, nextItemToken);

			} else if (initData.getItemIds() != null && initData.getItemIds().size() > 0) {

				parseItemIds(initData, responseData, alephSvcMgr, nextItemToken);

			} else {
				Problem p = new Problem(new ProblemType("LookupItemSet service has no implemented service to process HoldingsSetId."), null, null);
				responseData.setProblems(Arrays.asList(p));
			}

			if (responseData.getProblems() == null) {

				responseData.setBibInformations(bibInformations);

				ResponseHeader responseHeader = AlephUtil.reverseInitiationHeader(initData);

				if (responseHeader != null)
					responseData.setResponseHeader(responseHeader);
			}
		}

		return responseData;
	}

	/**
	 * Sets {@link LookupItemSetResponseData} with desired values. *
	 * 
	 * @param initData
	 * @param responseData
	 * @param alephSvcMgr
	 * @param nextItemToken
	 */
	private void parseBibIds(LookupItemSetInitiationData initData, LookupItemSetResponseData responseData, AlephRemoteServiceManager alephSvcMgr, ItemToken nextItemToken) {

		boolean wantSeeAllProblems = alephSvcMgr.getLocalConfig().isEchoParticularProblemsToLUIS();

		List<BibliographicId> bibIds = initData.getBibliographicIds();

		BibInformation bibInformation;

		List<AlephItem> alephItems;
		AlephItem alephItem;

		List<HoldingsSet> holdingSets;

		String id;

		for (BibliographicId bibId : bibIds) {

			isLast = bibIds.get(bibIds.size() - 1).equals(bibId);

			try {
				if (bibId.getBibliographicRecordId() != null) {
					id = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();

					if (id.isEmpty()) {
						if (wantSeeAllProblems) {
							bibInformation = new BibInformation();
							Problem problem = new Problem(new ProblemType("Empty BibliographicRecordIdentifierValue."), null,
									"Here you have specified empty BibliographicRecordIdentifierValue.");
							bibInformation.setProblems(Arrays.asList(problem));
							bibInformation.setBibliographicId(bibId);
							bibInformations.add(bibInformation);
						}
						continue;
					}

					bibInformation = new BibInformation();

					alephItems = alephSvcMgr.lookupItems(id, initData, this);

					bibInformation.setBibliographicId(bibId);

					if (alephItems != null && alephItems.size() > 0) {

						holdingSets = parseHoldingsSets(alephItems, initData);

						bibInformation.setHoldingsSets(holdingSets);

						bibInformations.add(bibInformation);

						// Note that newTokenKey is always null until it is set from RestDlfConnector (lookupItems method)
						if (newTokenKey != null) {
							responseData.setNextItemToken(newTokenKey);
							newTokenKey = null;
							break;
						}

					} else if (wantSeeAllProblems) {
						Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
						++itemsForwarded;

						// Do not create NextItemToken if this is last item desired
						boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

						if (createNextItemToken) {
							// Set next item token
							ItemToken itemToken = new ItemToken();

							itemToken.setBibliographicId(id);

							int newToken = random.nextInt();
							itemToken.setNextToken(Integer.toString(newToken));

							this.addItemToken(Integer.toString(newToken), itemToken);

							responseData.setNextItemToken(Integer.toString(newToken));
							break;
						}
					}

				} else if (bibId.getBibliographicItemId() != null) {
					id = bibId.getBibliographicItemId().getBibliographicItemIdentifier();

					if (id.isEmpty()) {
						if (wantSeeAllProblems) {
							bibInformation = new BibInformation();
							Problem problem = new Problem(new ProblemType("Empty BibliographicItemIdentifierValue."), null,
									"Here you have specified empty BibliographicItemIdentifierValue.");
							bibInformation.setProblems(Arrays.asList(problem));
							bibInformations.add(bibInformation);
						}
						continue;
					}
					bibInformation = new BibInformation();

					bibInformation.setBibliographicId(bibId);

					alephItem = alephSvcMgr.lookupItem(id, initData);
					if (alephItem != null) {

						holdingSets = Arrays.asList(parseHoldingsSet(alephItem, initData));

						bibInformation.setHoldingsSets(holdingSets);

						bibInformations.add(bibInformation);
						++itemsForwarded;

						// Do not create NextItemToken if this is last item desired
						boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

						if (createNextItemToken) {

							ItemToken itemToken = new ItemToken();
							itemToken.setBibliographicId(id);
							itemToken.setItemId(alephItem.getItemId().getItemIdentifierValue());

							int newToken = random.nextInt();

							itemToken.setNextToken(Integer.toString(newToken));
							this.addItemToken(Integer.toString(newToken), itemToken);

							responseData.setNextItemToken(Integer.toString(newToken));
							break;
						}
					} else if (wantSeeAllProblems) {
						Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

						bibInformation.setProblems(Arrays.asList(p));

						// Note that Problem elements within <ns1:BibInformation> is also considered as one item forwarded
						bibInformations.add(bibInformation);
						++itemsForwarded;

						// Do not create NextItemToken if this is last item desired
						boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

						if (createNextItemToken) {

							ItemToken itemToken = new ItemToken();
							itemToken.setBibliographicId(id);

							int newToken = random.nextInt();

							itemToken.setNextToken(Integer.toString(newToken));
							this.addItemToken(Integer.toString(newToken), itemToken);

							responseData.setNextItemToken(Integer.toString(newToken));
							break;
						}
					}

				} else {
					bibInformation = new BibInformation();

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
			} catch (AlephException ae) {
				Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
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
	 * @param alephSvcMgr
	 * @param nextItemToken
	 */
	private void parseItemIds(LookupItemSetInitiationData initData, LookupItemSetResponseData responseData, AlephRemoteServiceManager alephSvcMgr, ItemToken nextItemToken) {

		boolean wantSeeAllProblems = alephSvcMgr.getLocalConfig().isEchoParticularProblemsToLUIS();

		List<ItemId> itemIds = initData.getItemIds();

		BibInformation bibInformation;

		AlephItem alephItem;

		List<HoldingsSet> holdingsSets;

		for (ItemId itemId : itemIds) {

			isLast = itemIds.get(itemIds.size() - 1).equals(itemId);

			String id = itemId.getItemIdentifierValue();

			if (id.isEmpty()) {
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
				alephItem = alephSvcMgr.lookupItem(id, initData);

				alephItem.setItemId(id);

				BibliographicId bibliographicId = new BibliographicId();
				BibliographicItemId bibliographicItemId = AlephUtil.createBibliographicItemIdAsLegalDepositNumber(id);
				bibliographicId.setBibliographicItemId(bibliographicItemId);

				bibInformation.setBibliographicId(bibliographicId);

				if (alephItem != null) {

					holdingsSets = Arrays.asList(parseHoldingsSet(alephItem, initData));

					bibInformation.setHoldingsSets(holdingsSets);

					bibInformations.add(bibInformation);
					++itemsForwarded;

					// Do not create NextItemToken if this is last item desired
					boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

					if (createNextItemToken) {

						ItemToken itemToken = new ItemToken();
						itemToken.setBibliographicId(id);

						int newToken = random.nextInt();

						itemToken.setNextToken(Integer.toString(newToken));
						this.addItemToken(Integer.toString(newToken), itemToken);

						responseData.setNextItemToken(Integer.toString(newToken));
						break;
					}
				} else if (wantSeeAllProblems) {
					Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

					bibInformation.setProblems(Arrays.asList(p));

					// Note that Problem elements within <ns1:BibInformation> is also considered as one item forwarded
					bibInformations.add(bibInformation);
					++itemsForwarded;

					// Do not create NextItemToken if this is last item desired
					boolean createNextItemToken = maximumItemsCount == itemsForwarded && !isLast;

					if (createNextItemToken) {

						ItemToken itemToken = new ItemToken();
						itemToken.setBibliographicId(id);

						int newToken = random.nextInt();

						itemToken.setNextToken(Integer.toString(newToken));
						this.addItemToken(Integer.toString(newToken), itemToken);

						responseData.setNextItemToken(Integer.toString(newToken));
						break;
					}
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
			} catch (AlephException ae) {
				Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
				break;
			}
		}

	}

	/**
	 * 
	 * Parses response from requested ItemId (we know for sure there is only one item to parse)
	 * 
	 * @param alephItem
	 * @param suppliedAgencyId
	 * @param getBibDescription
	 * @param getCircStatus
	 * @param getHoldQueueLength
	 * @param getItemDescription
	 * @return {@link org.extensiblecatalog.ncip.v2.service.HoldingsSet}
	 */
	private HoldingsSet parseHoldingsSet(AlephItem alephItem, LookupItemSetInitiationData initData) {
		HoldingsSet holdingsSet = new HoldingsSet();

		if (initData.getBibliographicDescriptionDesired()) {
			BibliographicDescription bDesc = AlephUtil.parseBibliographicDescription(alephItem);
			holdingsSet.setBibliographicDescription(bDesc);
		}

		ItemInformation info = new ItemInformation();

		info.setItemId(alephItem.getItemId());

		info.setItemOptionalFields(AlephUtil.parseItemOptionalFields(alephItem));

		holdingsSet.setItemInformations(Arrays.asList(info));

		return holdingsSet;
	}

	/**
	 * 
	 * Parses response from requested RecordId
	 * 
	 * @param alephItems
	 * @param suppliedAgencyId
	 * @param getBibDescription
	 * @param getCircStatus
	 * @param getHoldQueueLength
	 * @param getItemDescription
	 * @return List<{@link org.extensiblecatalog.ncip.v2.service.HoldingsSet}>
	 */
	private List<HoldingsSet> parseHoldingsSets(List<AlephItem> alephItems, LookupItemSetInitiationData initData) {

		HoldingsSet holdingsSet = new HoldingsSet();
		List<ItemInformation> itemInfoList = new ArrayList<ItemInformation>();

		for (AlephItem item : alephItems) {

			if (initData.getBibliographicDescriptionDesired()) {
				BibliographicDescription bDesc = AlephUtil.parseBibliographicDescription(item);
				holdingsSet.setBibliographicDescription(bDesc);
			}

			ItemInformation info = new ItemInformation();

			info.setItemId(item.getItemId());

			info.setItemOptionalFields(AlephUtil.parseItemOptionalFields(item));

			itemInfoList.add(info);
		}
		holdingsSet.setItemInformations(itemInfoList);

		return Arrays.asList(holdingsSet);
	}

	/**
	 * Returns ItemToken stored in static HashMap, associated with {@link String} tokenKey.<br />
	 * If passed tokenKey is empty or null, null will be returned.
	 * 
	 * @param tokenKey
	 * @param bibIds
	 * @param itemIds
	 * @return {@link org.extensiblecatalog.ncip.v2.aleph.util.ItemToken}
	 * @throws ServiceException
	 */
	private ItemToken parseItemToken(LookupItemSetInitiationData initData, AlephRemoteServiceManager alephSvcMgr) throws ServiceException {

		String tokenKey = initData.getNextItemToken();
		List<BibliographicId> bibIds;
		List<ItemId> itemIds;

		ItemToken nextItemToken = null;

		if (tokenKey != null && !tokenKey.isEmpty()) {

			AlephUtil.markExpiredTokens(tokens, alephSvcMgr.getLocalConfig().getTokenExpirationTime());

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
	private int parseMaximumItemsCount(LookupItemSetInitiationData initData) throws ServiceException {
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

	public int getItemsForwarded() {
		return itemsForwarded;
	}

	public void setItemsForwarded(int itemsForwarded) {
		this.itemsForwarded = itemsForwarded;
	}

	/**
	 * Returns private {@link Integer} of parsed maximumItemsCount which is set to 0 if was not specified on input.
	 * 
	 * @return {@link Integer} maximumItemsCount
	 */
	public int getMaximumItemsCount() {
		return maximumItemsCount;
	}

	public void addItemToken(String key, ItemToken token) {

		AlephUtil.purgeExpiredTokens(tokens);

		tokens.put(key, token);
	}

	public ItemToken getNextItemToken() {
		return nextItemToken;
	}

	public void setNewTokenKey(String newTokenKey) {
		this.newTokenKey = newTokenKey;
	}

	public boolean isLastDesiredId() {
		return isLast;
	}
}
