/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class implements the Lookup Item Set service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyLookupItemSetService implements LookupItemSetService {

    /**
     * Handles a NCIP LookupItemSet service by returning hard-coded data.
     *
     * @param initData       the LookupItemSetInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupItemSetResponseData
     */
    @Override
    public LookupItemSetResponseData performService(LookupItemSetInitiationData initData,
                                                    ServiceContext serviceContext,
                                                    RemoteServiceManager serviceManager) {

        final LookupItemSetResponseData responseData = new LookupItemSetResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        List<BibInformation> bibInformationsList = new ArrayList<BibInformation>();

        Map<String, List<DummyDatabase.ItemInfo>> itemInfosByBibNo
            = new HashMap<String, List<DummyDatabase.ItemInfo>>();

        if ( initData.getBibliographicIds() != null && initData.getBibliographicIds().size() > 0 ) {

            for (BibliographicId bibId : initData.getBibliographicIds()) {

                if (bibId.getBibliographicRecordId() != null ) {

                    if ( bibId.getBibliographicRecordId().getAgencyId() != null ) {

                        // TODO: Test whether this AgencyId is us

                        String bibNo = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();

                        if ( bibNo != null ) {

                            DummyDatabase.BibInfo bibInfo = DummyDatabase.BibInfo.getByBibNo(bibNo);
                            if ( bibInfo != null ) {

                                insertItemIdsForBib(itemInfosByBibNo, bibInfo);

                            } else {

                                BibInformation bibInformation = new BibInformation();
                                bibInformation.setProblems(ServiceHelper.generateProblems(
                                    Version1LookupItemProcessingError.UNKNOWN_ITEM, "//BibliographicRecordId",
                                    bibNo, "Bib # '" + bibNo + "' not found."));

                            }
                        
                        } else {

                            BibInformation bibInformation = new BibInformation();
                            bibInformation.setProblems(ServiceHelper.generateProblems(
                                Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                                "//BibliographicRecordId/BibliographicRecordIdentifier",
                                null, "BibliographicRecordIdentifier is empty."));

                        }

                    } else {

                        BibliographicRecordIdentifierCode code
                            = bibId.getBibliographicRecordId().getBibliographicRecordIdentifierCode();
                        if ( code.equals(Version1BibliographicRecordIdentifierCode.OCLC) ) {

                            String oclcNum = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();

                            List<DummyDatabase.BibInfo> bibInfos = DummyDatabase.BibInfo.getBibsByOCLCNo(oclcNum);

                            if ( bibInfos != null && bibInfos.size() > 0 ) {

                                for ( DummyDatabase.BibInfo bibInfo : bibInfos ) {

                                    insertItemIdsForBib(itemInfosByBibNo, bibInfo);

                                }

                            } else {

                                BibInformation bibInformation = new BibInformation();
                                bibInformation.setProblems(ServiceHelper.generateProblems(
                                    Version1LookupItemProcessingError.UNKNOWN_ITEM, "//BibliographicRecordId",
                                    oclcNum, "OCLC # '" + oclcNum + "' not found."));

                            }

                        } else {

                            BibInformation bibInformation = new BibInformation();
                            bibInformation.setProblems(ServiceHelper.generateProblems(
                                Version1GeneralProcessingError.UNAUTHORIZED_COMBINATION_OF_ELEMENT_VALUES_FOR_SYSTEM,
                                "//BibliographicRecordId/BibliographicRecordIdentifierCode",
                                code.getScheme() + ": " + code.getValue(), "Bib Id type '" + code.getScheme() + ": "
                                    + code.getValue() + "' not supported."));

                        }
                    }

                } else {

                    BibInformation bibInformation = new BibInformation();
                    bibInformation.setProblems(ServiceHelper.generateProblems(
                        Version1GeneralProcessingError.NEEDED_DATA_MISSING,
                        "//BibliographicRecordId", null,
                        "BibliographicRecordId is the only bib-level identifier supported by this responder."));

                }

            }

        } else if (initData.getItemIds() != null && initData.getItemIds().size() > 0 ) {

            for ( ItemId itemId : initData.getItemIds()) {

                DummyDatabase.ItemInfo itemInfo = DummyDatabase.ItemInfo.getByBarcode(itemId.getItemIdentifierValue());

                if ( itemInfo != null ) {

                    DummyDatabase.BibInfo bibInfo = itemInfo.holdingInfo.bibInfo;
                    List<DummyDatabase.ItemInfo> itemInfosForThisBib = itemInfosByBibNo.get(bibInfo.bibNo);

                    if ( itemInfosForThisBib == null ) {

                        itemInfosForThisBib = new ArrayList<DummyDatabase.ItemInfo>();
                        itemInfosByBibNo.put(bibInfo.bibNo, itemInfosForThisBib);

                    }

                    itemInfosForThisBib.add(itemInfo);

                } else {

                    BibInformation bibInformation = new BibInformation();
                    bibInformation.setProblems(ServiceHelper.generateProblems(
                        Version1LookupItemProcessingError.UNKNOWN_ITEM, "//BibliographicRecordId",
                        itemId.getItemIdentifierValue(), "Item # '" + itemId.getItemIdentifierValue() + "' not found."));

                }

            }

        } else {

            BibInformation bibInformation = new BibInformation();
            bibInformation.setProblems(ServiceHelper.generateProblems(
                Version1GeneralProcessingError.NEEDED_DATA_MISSING, "//BibliographicRecordId or //ItemId",
                null, "No BibIds or ItemIds supplied."));

        }

        if ( itemInfosByBibNo.size() > 0 ) {

            int currentItem = 0;

            int startItem = 1;
            if ( initData.getNextItemToken() != null ) {

                try {

                    startItem = Integer.valueOf(initData.getNextItemToken());

                } catch (NumberFormatException e) {

                    BibInformation bibInformation = new BibInformation();
                    bibInformation.setProblems(ServiceHelper.generateProblems(
                        Version1GeneralProcessingError.UNAUTHORIZED_COMBINATION_OF_ELEMENT_VALUES_FOR_SYSTEM,
                        "//NextItemToken", initData.getNextItemToken(),
                        "Invalid token of '" + initData.getNextItemToken() + "'."));

                }

            }

            int maximumItemsCount = 0;
            if ( initData.getMaximumItemsCount() != null ) {

                maximumItemsCount = initData.getMaximumItemsCount().intValue();

            }

            for ( String bibNo : itemInfosByBibNo.keySet() ) {

                BibInformation bibInformation = new BibInformation();

                BibliographicId bibId = new BibliographicId();
                BibliographicRecordId bibliographicRecordId = new BibliographicRecordId();
                bibliographicRecordId.setBibliographicRecordIdentifier(bibNo);
                bibliographicRecordId.setAgencyId(dummySvcMgr.getAgencyId());
                bibId.setBibliographicRecordId(bibliographicRecordId);
                bibInformation.setBibliographicId(bibId);

                BibliographicDescription bibDesc = dummySvcMgr.getBibliographicDescription(
                    DummyDatabase.BibInfo.getByBibNo(bibNo));
                bibInformation.setBibliographicDescription(bibDesc);

                List<HoldingsSet> holdingsSetList = new ArrayList<HoldingsSet>();

                if ( (currentItem + itemInfosByBibNo.get(bibNo).size()) >= startItem )
                {

                    HoldingsSet holdingsSet = new HoldingsSet();

                    // Item information
                    List<ItemInformation> itemInformationList = new ArrayList<ItemInformation>();
                    for ( DummyDatabase.ItemInfo itemInfo : itemInfosByBibNo.get(bibNo) ) {

                        currentItem++;

                        if (currentItem < startItem) {

                            continue; // Skip this item because it was returned in a previous message (theoretically).

                        }

                        if (maximumItemsCount != 0 && currentItem >= (startItem + maximumItemsCount)) {

                            responseData.setNextItemToken(Integer.toString(currentItem));
                            break;

                        }

                        ItemInformation itemInformation = getItemInformation(itemInfo);
                        itemInformationList.add(itemInformation);

                    }

                    if ( itemInformationList.size() > 0 ) {

                        holdingsSet.setItemInformations(itemInformationList);
                        holdingsSetList.add(holdingsSet);

                    }

                } else {

                    currentItem += itemInfosByBibNo.get(bibNo).size();

                }


                if (maximumItemsCount != 0 && currentItem >= (startItem + maximumItemsCount)) {

                    break;

                }

                bibInformation.setHoldingsSets(holdingsSetList);
                bibInformationsList.add(bibInformation);

            }

            responseData.setBibInformations(bibInformationsList);

        } else {

            if ( ! bibInformationsList.isEmpty() ) {

                // Presumably the list just has Problems about not-found bib ids or similar.
                responseData.setBibInformations(bibInformationsList);

            } else {

                // TODO: Return a Problem saying "no errors, no items, something's wrong."

            }
        }


        return responseData;
    }

    public static void insertItemIdsForBib(Map<String, List<DummyDatabase.ItemInfo>> itemInfosByBibNo,
                                           DummyDatabase.BibInfo bibInfo) {

        if ( !itemInfosByBibNo.containsKey(bibInfo.bibNo) ) {

            for ( DummyDatabase.HoldingInfo holdingInfo : bibInfo.holdings ) {

                DummyDatabase.ItemInfo[] itemInfos = holdingInfo.items;

                if ( itemInfos != null && itemInfos.length > 0 ) {

                    itemInfosByBibNo.put(bibInfo.bibNo, Arrays.asList(itemInfos));

                } else {

                    // TODO: Return a Problem for this bibNo: not found or has no items

                }
            }
        }

    }

    public ItemInformation getItemInformation(DummyDatabase.ItemInfo itemInfo) {

        // Lookup the item's circulation status
        DummyDatabase.CircStatus ilsCircStatus = itemInfo.circStatus;

        // Map from the Dummy ILS's circulation status values to the Scheme Value Pair used in NCIP.
        CirculationStatus circStatus;
        switch (ilsCircStatus) {
            case ON_ORDER: {
                circStatus = Version1CirculationStatus.IN_PROCESS;
                break;
            }
            case ON_SHELF: {
                circStatus = Version1CirculationStatus.AVAILABLE_ON_SHELF;
                break;
            }
            case CHECKED_OUT: {
                circStatus = Version1CirculationStatus.ON_LOAN;
                break;
            }
            case IN_TRANSIT: {
                circStatus = Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS;
                break;
            }
            default: {
                circStatus = Version1CirculationStatus.CIRCULATION_STATUS_UNDEFINED;
                break;
            }
        }

        // Item Description
        ItemDescription itemDescription = new ItemDescription();
        itemDescription.setCallNumber(itemInfo.callNo);

        HoldingsInformation holdingsInfo = new HoldingsInformation();
        holdingsInfo.setUnstructuredHoldingsData(itemInfo.holdingInfo.summaryHoldings);
        itemDescription.setHoldingsInformation(holdingsInfo);

        itemDescription.setNumberOfPieces(new BigDecimal(1));

        // Assemble the ItemOptionalFields where most of the data is returned
        ItemOptionalFields itemOptionalFields = new ItemOptionalFields();

        itemOptionalFields.setCirculationStatus(circStatus);
        itemOptionalFields.setItemDescription(itemDescription);

        ItemInformation itemInformation = new ItemInformation();

        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(itemInfo.barcode);
        itemInformation.setItemId(itemId);

        itemInformation.setItemOptionalFields(itemOptionalFields);

        return itemInformation;

    }

}
