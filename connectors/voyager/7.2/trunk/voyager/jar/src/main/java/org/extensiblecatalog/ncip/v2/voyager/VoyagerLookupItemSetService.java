/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.voyager;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.ItemToken;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;



/**
 * This class implements the Lookup Item Set service for the Voyager back-end connector.
 *
 * @author SharmilaR
 */
public class VoyagerLookupItemSetService implements LookupItemSetService {

    static Logger log = Logger.getLogger(VoyagerLookupItemSetService.class);

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
            //voyagerConfig = (VoyagerConfiguration)ConnectorConfigurationFactory.getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    final int MAX_ITEMS_TO_RETURN = Integer.parseInt((String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_MAX_LUIS_ITEMS));

    static Random random = new Random();

    static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();

    VoyagerRemoteServiceManager voyagerSvcMgr;

    /**
     * Construct a VoyagerRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public VoyagerLookupItemSetService() {
    }

    /**
     * Handles a NCIP LookupItem service by returning data from voyager.
     *
     * @param initData       the LookupItemInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupItemResponseData
     */
    @Override
    public LookupItemSetResponseData performService (LookupItemSetInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager)
        throws ServiceException {

        voyagerSvcMgr = (VoyagerRemoteServiceManager) serviceManager;
        LookupItemSetResponseData luisResponseData = new LookupItemSetResponseData();
        List<Problem> problems = new ArrayList<Problem>();
        Date sService = new Date();

        log.info("Performing LUIS service.");

        List<BibliographicId> bibIds = initData.getBibliographicIds();
        if (bibIds == null) {
            problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                    null, null, "Missing Bib IDs"));
            luisResponseData.setProblems(problems);
            return luisResponseData;
        }

        List<HoldingsSet> holdingSets = new ArrayList<HoldingsSet>();
        List<String> holdingIds = null;
        List<String> itemIds = null;
        Document holdingsDocFromRestful = new Document();
        Document holdingsDocFromXml = new Document();

        int itemCount = 0;
        boolean reachedMaxItemCount = false;

        String token = initData.getNextItemToken();
        ItemToken nextItemToken = null;
        if (token != null) {
            nextItemToken = tokens.get(token);
            if (nextItemToken != null) {
                int index = getIndexOfBibId(bibIds, nextItemToken.getBibliographicId());
                if (index != -1) {
                    bibIds.subList(0, index).clear();
                }
                // Remove token from memory hashmap
                tokens.remove(token);
            } else {
                problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                        null, token, "Invalid nextItemToken"));
                luisResponseData.setProblems(problems);
                return luisResponseData;
            }
            log.debug("after removing already processed Bib ids = "+bibIds);
        }

        List<BibInformation> bibInformations = new ArrayList<BibInformation>();

        // Loop through Bib Ids in request
        for (BibliographicId bibId : bibIds) {

            String id = null;
            String itemAgencyId = null;

            id = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();
            itemAgencyId = bibId.getBibliographicRecordId().getAgencyId().getValue();

            try {
                BibInformation bibInformation = new BibInformation();
                bibInformation.setBibliographicId(bibId);

                if (!checkValidAgencyId(itemAgencyId)) {
                    log.error("Unrecognized Bibliographic Record Agency Id: " + itemAgencyId);
                    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                            null, null, "Unrecognized Bibliographic Record Agency Id"));
                    bibInformation.setProblems(problems);
                    bibInformations.add(bibInformation);
                    continue;
                }

                // Is the bib field empty?
                if (id.equals("") || itemAgencyId.equals("")) {
                    log.error("Missing Bib Id or Agency Id");
                    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                            null, null, "Missing Bib ID or item Agency Id"));
                    bibInformation.setProblems(problems);
                    bibInformations.add(bibInformation);
                    continue;
                }

                // Retrieve XML from vxws web services
                holdingsDocFromRestful = getHoldingRecordsFromRestful(bibId);
                holdingsDocFromXml = getHoldingRecordsFromXml(bibId);

                if (holdingsDocFromXml == null) {
                    problems.addAll(ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                            null, id, "Problem contacting the vxws service"));
                    luisResponseData.setProblems(problems);
                    return luisResponseData;
                }

                if (!doesRecordExist(holdingsDocFromXml)) {
                    log.error("Record does not exist");
                    problems.addAll(ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
                            null, id, "Record does not exist"));
                    bibInformation.setProblems(problems);
                    bibInformations.add(bibInformation);
                    continue;
                }

                // Get holding Ids belonging to this bib
                holdingIds = getHoldingIdsFromHoldingDoc(holdingsDocFromXml);

                if (nextItemToken != null) {
                     int index = holdingIds.indexOf(nextItemToken.getHoldingsId());
                     if (index != -1) {
                         holdingIds.subList(0, index).clear();
                     }
                }

                if (holdingIds == null) {
                    log.error("Bib does not have a holding record associated with it");
                    problems.addAll(ServiceHelper.generateProblems(Version1LookupItemProcessingError.UNKNOWN_ITEM,
                            null, id, "Record does not have a holding record associated with it"));
                    bibInformation.setProblems(problems);
                    bibInformations.add(bibInformation);
                    continue;  // Bib record exists but has no Holding records
                }

                // Set bib desc
                BibliographicDescription bDesc = null;
                if (initData.getBibliographicDescriptionDesired()) {
                    bDesc = getBibliographicDescriptionForBibId(holdingsDocFromXml);
                    bibInformation.setBibliographicDescription(bDesc);
                }

                // title hold queue length
                // Ignoring in vxws release
                /*BigDecimal titleHoldQueue = voyagerSvcMgr.getTitleLevelHoldQueueLength(id);
                if (titleHoldQueue != null) {
                    bibInformation.setTitleHoldQueueLength(titleHoldQueue);
                }*/

                holdingSets = new ArrayList<HoldingsSet>();

                // Build HoldingSet with items in it
                for (String holdingId : holdingIds) {
                    log.debug("Processing Holding id = " + holdingId);
                    itemIds = getItemIdsFromHoldingDoc(holdingId, holdingsDocFromRestful);
                    log.debug("All itemIds: " + itemIds);
                    if (nextItemToken != null) {
                         int index = itemIds.indexOf(nextItemToken.getItemId());
                         log.debug("Index of nextitem: " + index);
                         if (index != -1) {
                             itemIds.subList(0, index).clear();
                         }
                         log.debug("after removing already processed item ids = "+itemIds);
                    }

                    HoldingsSet holdingSet = new HoldingsSet();
                    // Set Bib Id and holdings set id
                    holdingSet.setHoldingsSetId(holdingId);

//                    if (initData.getElectronicResourceDesired()) {
                        ElectronicResource eResource = getElectronicResourceForHoldingId(holdingId, holdingsDocFromXml);
                        if (eResource != null)
                            holdingSet.setElectronicResource(eResource);
//                    }

                    // Get Bib desc, holding set info only if items exist for that holdings
                    if (itemIds != null && itemIds.size() > 0) {

                        holdingSet.setCallNumber(getCallNumberForHoldingDoc(holdingId, holdingsDocFromRestful));

                        // Set location
                        // Decision made to omit Location at the Holding Set level this release
                        /* Location location = null;
                        if (initData.getLocationDesired()) {
                            location = getPermanentLocationForHoldingDoc(holdingId, holdingsDocFromRestful);
                            if (location != null) {
                                holdingSet.setLocation(location);
                            }
                        } */

                        int newItemCount = itemCount + itemIds.size();

                        log.debug("Item count is " + itemCount + " and itemIds.size is " + itemIds.size());

                        if (newItemCount > MAX_ITEMS_TO_RETURN) {
                            itemIds = getItemIdSubset(itemIds, itemCount);
                            log.debug("Subset itemIds:"+itemIds);
                        }

                        Map<String, ItemInformation> itemInformations = new HashMap<String, ItemInformation>();

                        for (String itemId:itemIds) {
                            ItemInformation itemInformation = new ItemInformation();
                            ItemId item = new ItemId();
                            item.setItemIdentifierValue(itemId);
                            item.setAgencyId(new AgencyId(itemAgencyId));
                            itemInformation.setItemId(item);
                            itemInformations.put(itemId, itemInformation);
                        }

                        Map<String, String> statuses = null;
                        if (initData.getCirculationStatusDesired()) {
                            statuses = getCirculationStatusForItemIds(itemIds, holdingsDocFromRestful);
                        }

                        // TODO: Double check that this really isn't available through GetHoldings
                        /* Ignoring for vxws release
                        Map<String, BigDecimal> lengths = null;
                        if (initData.getHoldQueueLengthDesired()) {
                            lengths = voyagerSvcMgr.getHoldQueueLengthForItemIds(itemIds);
                        }
                        */

                        Map<String, ItemDescription> itemDescriptions = null;
                        if (initData.getItemDescriptionDesired()) {
                            itemDescriptions = getItemDescriptionForItemIds(itemIds, holdingsDocFromRestful);
                        }

                        // TODO: Refactor this because we no longer want to return a list of locations.
                        Map<String, Location> locations = null;
                        if (initData.getLocationDesired()) {
                            locations = getLocationForItemIds(itemIds, holdingsDocFromRestful);
                        }

                        Map<String, String> copyNumbers = new HashMap<String, String>();
                        Map<String, GregorianCalendar> dueDates = null;
                        if (itemDescriptions == null) {
                            itemDescriptions = getItemDescriptionForItemIds(itemIds, holdingsDocFromRestful);
                        }

                        Iterator<String> itrId = itemDescriptions.keySet().iterator();
                        while (itrId.hasNext()) {
                            String key = itrId.next();
                            copyNumbers.put(itemDescriptions.get(key).getCopyNumber(), key);
                        }

                        dueDates = getDueDateForItemIds(itemIds, holdingsDocFromXml, copyNumbers, holdingId);

                        Iterator<String> itr = itemInformations.keySet().iterator();
                        while (itr.hasNext()) {
                            ItemOptionalFields iof = new ItemOptionalFields();
                            String key = itr.next();
                            if (statuses != null) {
                                log.debug("Status for key " + statuses.get(key));
                                if (statuses.get(key) != null) {
                                    iof.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, statuses.get(key)));
                                }
                            }

                            /*  Ignoring for vxws release
                            if (lengths != null) {
                                iof.setHoldQueueLength(lengths.get(key));
                            } */

                            if (itemDescriptions != null) {
                                iof.setItemDescription(itemDescriptions.get(key));
                            }

                            if (locations != null) {

                                //List<Location> tempLocations = locations.get(key);
                                List<Location> tempLocations = new ArrayList<Location>();
                                tempLocations.add(locations.get(key));
                                if (tempLocations != null) {
                                    LocationNameInstance lni = tempLocations.get(0).getLocationName().getLocationNameInstances().get(0);
                                    iof.setLocations(tempLocations);
                                }
                            }

                            ItemInformation itemInformation = itemInformations.get(key);
                            itemInformation.setItemOptionalFields(iof);
                            if (dueDates != null)
                                itemInformation.setDateDue(dueDates.get(key));
                            itemInformations.put(key, itemInformation);
                        }

                        holdingSet.setItemInformations(new ArrayList<ItemInformation>(itemInformations.values()));

                        itemCount = itemCount + itemIds.size();

                        log.debug("Item count: " + itemCount);

                        if (itemCount == MAX_ITEMS_TO_RETURN) {
                            // Set next item token
                            ItemToken itemToken = new ItemToken();
                            itemToken.setBibliographicId(id);
                            itemToken.setHoldingsId(holdingId);
                            itemToken.setItemId(itemIds.get(itemIds.size() - 1));
                            int newToken = random.nextInt();
                            itemToken.setNextToken(Integer.toString(newToken));
                            tokens.put(Integer.toString(newToken), itemToken);

                            luisResponseData.setNextItemToken(Integer.toString(newToken));

                            reachedMaxItemCount = true;

                            holdingSets.add(holdingSet);

                            break;
                        }
                    } else if (itemIds.size() == 0){

                        itemCount = itemCount + 1;
                        log.debug("Item count: " + itemCount);
                        if (itemCount == MAX_ITEMS_TO_RETURN) {
                            // Set next item token
                            ItemToken itemToken = new ItemToken();
                            itemToken.setBibliographicId(id);
                            itemToken.setHoldingsId(holdingId);
                            itemToken.setItemId("");
                            int newToken = random.nextInt();
                            itemToken.setNextToken(Integer.toString(newToken));
                            tokens.put(Integer.toString(newToken), itemToken);

                            luisResponseData.setNextItemToken(Integer.toString(newToken));

                            reachedMaxItemCount = true;
                        }

                        Map<String, ItemInformation> itemInformations = new HashMap<String, ItemInformation>();
                        ItemInformation itemInformation = new ItemInformation();
                        ItemId item = new ItemId();
                        item.setItemIdentifierValue("");
                        item.setAgencyId(new AgencyId(itemAgencyId));

                        //itemInformation.setItemId(item);
                        itemInformations.put("", itemInformation);
                        holdingSet.setItemInformations(new ArrayList<ItemInformation>(itemInformations.values()));
                    }

                    log.info("Adding new holding set");
                    holdingSets.add(holdingSet);
                }

                if (holdingIds.size() != 0) {
                    bibInformation.setHoldingsSets(holdingSets);
                }

                bibInformations.add(bibInformation);

                if (reachedMaxItemCount) {
                    break;
                }

            } catch (ILSException e) {
                Problem p = new Problem();
                p.setProblemType(new ProblemType("Processing error"));
                p.setProblemDetail(e.getMessage());
                problems.add(p);
                luisResponseData.setProblems(problems);
            }
        }

        Date eService = new Date();
        log.debug("Service time log : " + (eService.getTime() - sService.getTime()) + "  " + ((eService.getTime() - sService.getTime())/1000) + " sec");

        luisResponseData.setBibInformations(bibInformations);

        return luisResponseData;
    }

    private boolean checkValidAgencyId(String itemAgencyId) {
        String ubid = (String) voyagerConfig.getProperty(itemAgencyId);
        if (ubid != null)
            return true;
        else
            return false;
    }

    private ElectronicResource getElectronicResource(Document doc) throws ILSException {
        Namespace holNs = Namespace.getNamespace("hol", "http://www.endinfosys.com/Voyager/holdings");
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Namespace slimNs = Namespace.getNamespace("slim", "http://www.loc.gov/MARC21/slim");
        XPath xpath;
        try {
            xpath = XPath.newInstance("//slim:datafield[@tag='856']/slim:subfield[@code='u']");
            xpath.addNamespace(serNs);
            xpath.addNamespace(holNs);
            xpath.addNamespace(slimNs);
            Element url = (Element) xpath.selectSingleNode(doc);
            if (url != null) {
                log.debug("Found url " + url.getTextTrim());
                ElectronicResource electronicResource = new ElectronicResource();
                electronicResource.setReferenceToResource(url.getTextTrim());
              return electronicResource;
            } else {
                log.debug("Did not find url");
            }
        } catch (JDOMException e) {
            throw new ILSException(e);
        }

        return null;
    }

    private boolean doesRecordExist(Document holdingsDocFromXml) throws ILSException {
        Namespace ns = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        XPath xpath;
        try {
            xpath = XPath.newInstance("/ser:voyagerServiceData/ser:messages/ser:message");
            xpath.addNamespace(ns);
            Element message = (Element) xpath.selectSingleNode(holdingsDocFromXml);
            if (message != null && message.getText().startsWith("Could not retrieve bib record")) {
                log.debug("Could not retrieve bib record");
                return false;
            } else
                return true;
        } catch (JDOMException e) {
            throw new ILSException(e);
        }
    }

    private int getIndexOfBibId(List<BibliographicId> bibIds, String bibId) {
        for (int i = 0; i < bibIds.size() ; i++) {
            if (bibIds.get(i).getBibliographicItemId().getBibliographicItemIdentifier().equalsIgnoreCase(bibId)) {
                return i;
            }
        }
        return -1;
    }

    private List<String> getItemIdSubset(List<String> itemIds, int itemCount) {

        int numOfitemIdsToProcess = MAX_ITEMS_TO_RETURN - itemCount;

        return itemIds.subList(0, numOfitemIdsToProcess);
    }

    private Document getHoldingRecordsFromRestful(BibliographicId bibliographicId) {
        String itemAgencyId;
        String host;
        String bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();

        if (bibliographicId.getBibliographicRecordId().getAgencyId() != null)
            itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
        else
            itemAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);

        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(itemAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String webServicesUrl =  host + "/vxws/record/" + bibId + "/holdings?view=items";
        Document doc = voyagerSvcMgr.getWebServicesDoc(webServicesUrl);

        return doc;
    }

    private List<String> getHoldingIdsFromHoldingDoc(Document doc) throws ILSException {
        List<String> holdingIds = new ArrayList<String>();

        Namespace holNs = Namespace.getNamespace("hol", "http://www.endinfosys.com/Voyager/holdings");
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Namespace mfhdNs = Namespace.getNamespace("mfhd", "http://www.endinfosys.com/Voyager/mfhd");
        try {
            XPath xpath = XPath.newInstance("//mfhd:mfhdRecord");
            xpath.addNamespace(holNs);
            xpath.addNamespace(serNs);
            xpath.addNamespace(mfhdNs);
            List<Element> mfhdElements  = xpath.selectNodes(doc);
            for (Element mfhdElement : mfhdElements) {
                holdingIds.add(mfhdElement.getAttributeValue("mfhdId"));
                log.debug("Found mfhd Id " + mfhdElement.getAttributeValue("mfhdId"));
            }
        } catch (JDOMException e) {
            throw new ILSException(e);
        }
        return holdingIds;
    }

    private Map<String, GregorianCalendar> getDueDateForItemIds(List<String> itemIds, Document doc, Map<String, String> copyNumbers, String mfhdId) {

        Namespace holNs = Namespace.getNamespace("hol", "http://www.endinfosys.com/Voyager/holdings");
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Namespace mfhdNs = Namespace.getNamespace("mfhd", "http://www.endinfosys.com/Voyager/mfhd");
        Namespace itemNs = Namespace.getNamespace("item", "http://www.endinfosys.com/Voyager/item");

        List<Element> mfhdElements;
        List<Element> itemRecords;
        List<Element> itemData;
        Element itemCollection;
        Element mfhdCollection;

        String copyNumber = null;
        String statusDate = null;

        Map<String, GregorianCalendar> dueDates = new HashMap<String, GregorianCalendar>();

        try {
            List<Element> list = doc.getRootElement().getChild("serviceData", serNs).getChild("holdingsRecord", holNs).getChildren();
            mfhdCollection = doc.getRootElement().getChild("serviceData", serNs).getChild("holdingsRecord", holNs).getChild("mfhdCollection", holNs);
            mfhdElements = mfhdCollection.getChildren("mfhdRecord", mfhdNs);

            for (Element mfhdRecord : mfhdElements) {
                  if (!mfhdRecord.getAttributeValue("mfhdId").equalsIgnoreCase(mfhdId))
                      continue;

                if (mfhdRecord.getChild("itemCollection", mfhdNs) != null) {
                    itemCollection = mfhdRecord.getChild("itemCollection", mfhdNs);
                    itemRecords = itemCollection.getChildren("itemRecord", itemNs);
                    for (Element itemRecord : itemRecords) {
                        itemData = itemRecord.getChildren("itemData", itemNs);
                        boolean foundCopy = false;
                        for (Element item : itemData) {
                            if (item.getAttributeValue("name").equalsIgnoreCase("copyNumber")){
                                copyNumber = item.getTextTrim();
                                if (copyNumbers.containsKey(copyNumber)){
                                    log.info("Found match between copy numbers");
                                    foundCopy = true;
                                }
                            }
                            if (item.getAttributeValue("name").equalsIgnoreCase("statusDate")){
                                statusDate = item.getTextTrim();
                            }
                        }
                        if (foundCopy) {
                            String[] dateComponents = statusDate.substring(0, 10).split("-");
                            String[] timeComponents = statusDate.substring(11).split(":");

                            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                                    Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                                    Integer.parseInt(dateComponents[2]),
                                    Integer.parseInt(timeComponents[0]),
                                    Integer.parseInt(timeComponents[1]),
                                    Integer.parseInt(timeComponents[2]));

                            dueDates.put(copyNumbers.get(copyNumber), gc);
                            foundCopy = false;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            log.debug("No due dates found");
            return null;
        }
        return dueDates;
    }

    private ElectronicResource getElectronicResourceForHoldingId(String mfhdId, Document doc) throws ILSException {

        Namespace holNs = Namespace.getNamespace("hol", "http://www.endinfosys.com/Voyager/holdings");
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Namespace slimNs = Namespace.getNamespace("slim", "http://www.loc.gov/MARC21/slim");
        ElectronicResource electronicResource = new ElectronicResource();

        try {
            XPath xpath = XPath.newInstance("//slim:datafield[@tag='856']/slim:subfield[@code='u']");
            xpath.addNamespace(holNs);
            xpath.addNamespace(serNs);
            xpath.addNamespace(slimNs);
            Element urlElement = (Element) xpath.selectSingleNode(doc);
            String href = urlElement.getTextTrim();
            log.debug("Found eResource url " + href);
            electronicResource.setReferenceToResource(href);
        } catch (JDOMException e) {
            log.error("Error processing document for eResource");
            throw new ILSException(e);
        } catch (NullPointerException e) {
            log.debug("eResource not found");
            return null;
        }

        return electronicResource;
    }

    private List<String> getItemIdsFromHoldingDoc(String holdingId, Document doc) throws ILSException {
        List<Element> items, holdings = null;
        List<String> itemIds = new ArrayList<String>();
        String itemId, holdingIdFound;
        String href;
        int index;
        try {
            XPath xpath = XPath.newInstance("/response/holdings/institution/holding");
            holdings = xpath.selectNodes(doc);
            for (Element holding : holdings) {
                href = holding.getAttributeValue("href");
                index = href.lastIndexOf("/");
                holdingIdFound = href.substring(index).substring(1);
                if (holdingIdFound.equalsIgnoreCase(holdingId)){
                    items = holding.getChildren("item");
                    for (Element item : items) {
                        href = item.getAttributeValue("href");
                        index = href.lastIndexOf("/");
                        itemId = href.substring(index).substring(1);
                        itemIds.add(itemId);
                    }
                }
            }
        } catch (JDOMException e) {
            log.error("Error processing document for eResource");
            throw new ILSException(e);
        }
        return itemIds;
    }

    private String getCallNumberForHoldingDoc(String holdingId, Document doc) throws ILSException {
        List<Element> holdings = null;
        Element itemData;
        String callNumber = null;
        String href, holdingIdFound;
        int index;
        try {
            XPath xpath = XPath.newInstance("/response/holdings/institution/holding");
            holdings = xpath.selectNodes(doc);
            for (Element holding : holdings) {
                href = holding.getAttributeValue("href");
                index = href.lastIndexOf("/");
                holdingIdFound = href.substring(index).substring(1);
                if (holdingIdFound.equalsIgnoreCase(holdingId)){
                    xpath = XPath.newInstance("/response/holdings/institution/holding[@href='" + href + "']/item/itemData[@name='callNumber']");
                    itemData = (Element) xpath.selectSingleNode(doc);
                    callNumber = itemData.getTextTrim();
                }
            }
        } catch (JDOMException e) {
            log.error("Error processing document in getCallNumberForHoldingDoc()");
            throw new ILSException(e);
        }
        return callNumber;
    }

    private Location getPermanentLocationForHoldingDoc(String holdingId, Document doc) {
        List<Element> items, holdings = null;
        String permLocation = null;
        String href, holdingIdFound;
        Location location = null;
        int index;

        if (doc.getRootElement().getChild("holdings").getChild("institution").getChildren() != null){
            holdings = doc.getRootElement().getChild("holdings").getChild("institution").getChildren("holding");
            for (Element holding : holdings) {
                href = holding.getAttributeValue("href");
                index = href.lastIndexOf("/");
                holdingIdFound = href.substring(index).substring(1);
                if (holdingIdFound.equalsIgnoreCase(holdingId)){
                    items = holding.getChild("item").getChildren("itemData");
                    for (Element itemData : items) {
                        if (itemData.getAttribute("name") != null){
                            if (itemData.getAttributeValue("name").equalsIgnoreCase("permLocation")){
                                permLocation = itemData.getTextTrim();
                                LocationNameInstance locationNameInstance = new LocationNameInstance();

                                locationNameInstance.setLocationNameValue(permLocation);
                                //TODO: more to come from requirement for level
                                locationNameInstance.setLocationNameLevel(new BigDecimal("1"));//temporarily set to 1.

                                List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
                                locationNameInstances.add(locationNameInstance);

                                LocationName locationName = new LocationName();
                                locationName.setLocationNameInstances(locationNameInstances);

                                location = new Location();
                                location.setLocationName(locationName);
                                location.setLocationType(Version1LocationType.PERMANENT_LOCATION);
                            }
                        }
                    }
                }
            }
        }
        return location;
    }

    private Map<String, Location> getLocationForItemIds(List<String> itemIds, Document doc) {

        List<Element> items, itemData, holdings = null;
        String itemId, href, tempLocation;
        Map<String, Location> tempLocations = new HashMap<String, Location>();

        int index;
        if (doc.getRootElement().getChild("holdings").getChild("institution").getChildren() != null){
            holdings = doc.getRootElement().getChild("holdings").getChild("institution").getChildren("holding");
            for (Element holding : holdings) {
                items = holding.getChildren("item");
                for (Element item : items) {

                    //List<Location> locations = new ArrayList<Location>();
                    //Location location = new Location();
                    Location location = null;
                    href = item.getAttributeValue("href");
                    index = href.lastIndexOf("/");
                    itemId = href.substring(index).substring(1);
                    if (itemIds.contains(itemId)){
                        itemData = item.getChildren("itemData");
                        for (Element id : itemData) {
                            if (id.getAttribute("name") != null){
                                if (id.getAttributeValue("name").equalsIgnoreCase("tempLocation")){
                                    tempLocation = id.getTextTrim();
                                    if (tempLocation.equalsIgnoreCase("")){
                                        log.info("Found empty temp location");
                                        continue;
                                    }
                                    LocationNameInstance locationNameInstance = new LocationNameInstance();

                                    locationNameInstance.setLocationNameValue(StringUtils.trim(tempLocation));
                                    locationNameInstance.setLocationNameLevel(new BigDecimal("1"));

                                    List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
                                    locationNameInstances.add(locationNameInstance);

                                    LocationName locationName = new LocationName();
                                    locationName.setLocationNameInstances(locationNameInstances);

                                    location = new Location();
                                    location.setLocationName(locationName);
                                    location.setLocationType(Version1LocationType.TEMPORARY_LOCATION);
                                }
                                if (id.getAttributeValue("name").equalsIgnoreCase("permLocation")){
                                    tempLocation = id.getTextTrim();
                                    if (tempLocation.equalsIgnoreCase("")){
                                        log.info("Found empty perm location");
                                        continue;
                                    }
                                    LocationNameInstance locationNameInstance = new LocationNameInstance();

                                    locationNameInstance.setLocationNameValue(StringUtils.trim(tempLocation));
                                    locationNameInstance.setLocationNameLevel(new BigDecimal("1"));

                                    List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
                                    locationNameInstances.add(locationNameInstance);

                                    LocationName locationName = new LocationName();
                                    locationName.setLocationNameInstances(locationNameInstances);
                                    if (location == null) {
                                        location = new Location();
                                        location.setLocationName(locationName);
                                        location.setLocationType(Version1LocationType.PERMANENT_LOCATION);
                                    }
                                }

                            }
                        }
                    }
                    //if (locations.size() > 0)
                    tempLocations.put(itemId, location);

                }
            }
            return tempLocations;
        } else {
            log.error("No children of institution found");
            return null;
        }
    }

    private Map<String, String> getCirculationStatusForItemIds(List<String> itemIds, Document doc){

        Map<String, String> statuses = new HashMap<String, String>();
        List<Element> items, itemData, holdings = null;
        String itemId, href, status;

        int index;
        if (doc.getRootElement().getChild("holdings").getChild("institution").getChildren() != null){
            holdings = doc.getRootElement().getChild("holdings").getChild("institution").getChildren("holding");
            for (Element holding : holdings) {
                items = holding.getChildren("item");
                for (Element item : items) {
                    href = item.getAttributeValue("href");
                    index = href.lastIndexOf("/");
                    itemId = href.substring(index).substring(1);
                    if (itemIds.contains(itemId)){
                        itemData = item.getChildren("itemData");
                        for (Element id : itemData) {
                            if (id.getAttribute("name") != null){
                                if (id.getAttributeValue("name").equalsIgnoreCase("itemStatus")){
                                    status = id.getTextTrim();
                                    log.debug("Found status: " + status);
                                    if (status.endsWith("+"))
                                        status = status.substring(0, status.length() - 1);
                                    if (status.contains("Not Charged"))
                                        status = "Not Charged";
                                    else if (status.contains("Charged"))
                                        status = "Charged";
                                    else if (status.contains("Missing"))
                                        status = "Missing";
                                    else if (status.contains("Lost"))
                                        status = "Missing";
                                    else {
                                        String arr[] = status.split(" ", 2);
                                        status = arr[0];
                                    }
                                    statuses.put(itemId, status);
                                }
                            }
                        }
                    }
                }
            }
            return statuses;
        } else {
            log.error("No children of institution found");
            return null;
        }
    }

    private Map<String, ItemDescription> getItemDescriptionForItemIds(List<String> itemIds, Document doc){

        Map<String, ItemDescription> itemDescriptions = new HashMap<String, ItemDescription>();

        List<Element> items, itemData, holdings = null;
        String itemId, href, status;

        int index;
        if (doc.getRootElement().getChild("holdings").getChild("institution").getChildren() != null){
            holdings = doc.getRootElement().getChild("holdings").getChild("institution").getChildren("holding");
            for (Element holding : holdings) {
                items = holding.getChildren("item");
                for (Element item : items) {
                    href = item.getAttributeValue("href");
                    index = href.lastIndexOf("/");
                    itemId = href.substring(index).substring(1);
                    if (itemIds.contains(itemId)){
                        itemData = item.getChildren("itemData");
                        ItemDescription itemDescription = new ItemDescription();
                        String infoString = "";
                        String callNumber = "";
                        String copy = "";

                        for (Element id : itemData) {
                            if (id.getAttribute("name") != null){
                                if (id.getAttributeValue("name").equalsIgnoreCase("enumeration")){
                                    infoString += id.getTextTrim();
                                } else if (id.getAttributeValue("name").equalsIgnoreCase("chron")){
                                    infoString += id.getTextTrim();
                                } else if (id.getAttributeValue("name").equalsIgnoreCase("year")){
                                    infoString += id.getTextTrim();
                                } else if (id.getAttributeValue("name").equalsIgnoreCase("callNumber")){
                                    callNumber = id.getTextTrim();
                                } else if (id.getAttributeValue("name").equalsIgnoreCase("copy")){
                                    copy = id.getTextTrim();
                                }
                            }
                        }

                        if(infoString != null && infoString.trim().length()>0) {
                            HoldingsInformation holdingsInformation = new HoldingsInformation();
                            holdingsInformation.setUnstructuredHoldingsData(infoString);
                            itemDescription.setHoldingsInformation(holdingsInformation);
                        }
                        if (callNumber.length() > 0) {
                            itemDescription.setCallNumber(callNumber);
                        }
                        if (copy.length() > 0) {
                            itemDescription.setCopyNumber(copy);
                        }
                        itemDescriptions.put(itemId, itemDescription);
                    }
                }
            }
            log.info("Returning item descriptions: " + itemDescriptions);
            return itemDescriptions;
        } else {
            log.error("No children of institution found");
            return null;
        }
    }

    private Document getHoldingRecordsFromXml(BibliographicId bibliographicId) {

        String bibId = bibliographicId.getBibliographicRecordId().getBibliographicRecordIdentifier();
        String itemAgencyId;
        String host;

        if (bibliographicId.getBibliographicRecordId().getAgencyId() != null)
            itemAgencyId = bibliographicId.getBibliographicRecordId().getAgencyId().getValue();
        else
            itemAgencyId = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY);

        boolean consortialUse = Boolean.parseBoolean((String)voyagerConfig.getProperty(VoyagerConstants.CONFIG_CONSORTIUM));
        if (consortialUse) {
            host = voyagerSvcMgr.getUrlFromAgencyId(itemAgencyId);
        } else {
            host = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_WEB_SERVICE_URL);
        }

        String url = host + "/vxws/GetHoldingsService?bibId=" + bibId;

        Document doc = voyagerSvcMgr.getWebServicesDoc(url);

        return doc;
    }

    private BibliographicDescription getBibliographicDescriptionForBibId(Document doc) {

        Namespace slimNs = Namespace.getNamespace("slim", "http://www.loc.gov/MARC21/slim");
        Namespace holNs = Namespace.getNamespace("hol", "http://www.endinfosys.com/Voyager/holdings");
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Element marcElement;
        List<Element> dataFieldElements = null;
        List<Element> subFieldElements = null;

        String isbn = null, issn = null, title = null, author = null;
        String titleA = "", titleB = "", titleC = "", titleF = "", titleG = "";
        String titleH = "", titleK = "", titleN = "", titleP = "", titleS = "";

        if (doc.getRootElement().getChild("serviceData", serNs).getChild("holdingsRecord", holNs).getChild("bibRecord", holNs).getChild("marcRecord", holNs) != null){
            marcElement = doc.getRootElement().getChild("serviceData", serNs).getChild("holdingsRecord", holNs).getChild("bibRecord", holNs).getChild("marcRecord", holNs);
            dataFieldElements = marcElement.getChildren("datafield", slimNs);
            for (Element eDf : dataFieldElements) {
                if (eDf.getAttributeValue("tag") != null && eDf.getAttributeValue("tag").equalsIgnoreCase("245")){
                    subFieldElements = eDf.getChildren("subfield", slimNs);
                    for (Element eSf : subFieldElements) {
                        if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("a"))
                            titleA = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("b"))
                            titleB = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("c"))
                            titleC = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("f"))
                            titleF = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("g"))
                            titleG = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("h"))
                            titleH = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("k"))
                            titleK = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("n"))
                            titleN = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("p"))
                            titleP = eSf.getTextTrim();
                        else if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("s"))
                            titleS = eSf.getTextTrim();
                    }
                    title = titleA + titleB + titleC + titleF + titleG + titleH + titleK + titleN + titleP + titleS;
                }
                if (eDf.getAttributeValue("tag") != null && eDf.getAttributeValue("tag").equalsIgnoreCase("020")){
                    subFieldElements = eDf.getChildren("subfield", slimNs);
                    for (Element eSf : subFieldElements) {
                        if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("a")){
                            isbn = eSf.getTextTrim();
                            log.info("Found ISBN: " + isbn);
                        } else {
                            log.error("020 code a not found");
                        }
                    }
                }
                if (eDf.getAttributeValue("tag") != null && eDf.getAttributeValue("tag").equalsIgnoreCase("022")){
                    subFieldElements = eDf.getChildren("subfield", slimNs);
                    for (Element eSf : subFieldElements) {
                        if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("a")){
                            issn = eSf.getTextTrim();
                            log.info("Found ISSN: " + issn);
                        } else {
                            log.error("022 code a not found");
                        }
                    }
                }
                if (eDf.getAttributeValue("tag") != null && eDf.getAttributeValue("tag").equalsIgnoreCase("100")){
                    subFieldElements = eDf.getChildren("subfield", slimNs);
                    for (Element eSf : subFieldElements) {
                        if (eSf.getAttributeValue("code") != null && eSf.getAttributeValue("code").equalsIgnoreCase("a")){
                            author = eSf.getTextTrim();
                            log.info("Found author: " + author);
                        } else {
                            log.error("100 code a not found");
                        }
                    }
                }
            }
        } else {
            log.error("marc element not found");
        }

        BibliographicDescription bibliographicDescription = new BibliographicDescription();
        boolean hasBibDesc = false;

        List<BibliographicItemId> bibliographicItemIds = new ArrayList<BibliographicItemId>();

        if(author != null && author.length()>0){
            bibliographicDescription.setAuthor(author);
            hasBibDesc = true;
        }

        // Set bib item id
        if(isbn != null && isbn.length() > 0 ){
            BibliographicItemId bibliographicItemId = new BibliographicItemId();
            bibliographicItemId.setBibliographicItemIdentifier(isbn);
            BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
                    Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
                    "ISBN");
            bibliographicItemId.setBibliographicItemIdentifierCode(code);
            bibliographicItemIds.add(bibliographicItemId);
            bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
            hasBibDesc = true;
        }
        else if(issn != null && issn.length() > 0){
            BibliographicItemId bibliographicItemId = new BibliographicItemId();
            bibliographicItemId.setBibliographicItemIdentifier(issn);
            BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
                    Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
                    "ISSN");
            bibliographicItemId.setBibliographicItemIdentifierCode(code);
            bibliographicItemIds.add(bibliographicItemId);
            bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
            hasBibDesc = true;
        }

        if(title != null && title.length()>0){
            bibliographicDescription.setTitle(title);
            hasBibDesc = true;
        }

        if(!hasBibDesc){
            return null;
        }

        return bibliographicDescription;
    }

}
