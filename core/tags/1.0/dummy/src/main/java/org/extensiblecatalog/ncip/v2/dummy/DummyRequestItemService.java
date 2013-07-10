/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Request Item service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. request id) and responds that the hold
 * was a success.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyRequestItemService implements RequestItemService {

    /**
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public DummyRequestItemService() {
    }

    /**
     * Handles a NCIP RequestItem service by returning hard-coded data.
     *
     * @param initData       the RequestItemInitiationData
     * @param serviceManager provides access to remote services
     * @return RequestItemResponseData
     */
    @Override
    public RequestItemResponseData performService(RequestItemInitiationData initData,
                                                  RemoteServiceManager serviceManager) {

        final RequestItemResponseData responseData = new RequestItemResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        String itemBarcode = null;

        if (initData.getBibliographicId() != null && initData.getBibliographicId().getBibliographicRecordId() != null) {

            List<DummyDatabase.BibInfo> bibInfos = null;

            BibliographicRecordId bibliographicRecordId = initData.getBibliographicId().getBibliographicRecordId();

            if ( bibliographicRecordId.getBibliographicRecordIdentifierCode() != null
              && bibliographicRecordId.getBibliographicRecordIdentifierCode()
                .equals(Version1BibliographicRecordIdentifierCode.OCLC)) {

                bibInfos = DummyDatabase.BibInfo.getBibsByOCLCNo(
                    bibliographicRecordId.getBibliographicRecordIdentifier());

            } else if ( bibliographicRecordId.getAgencyId() != null
                && bibliographicRecordId.getAgencyId().getScheme().compareToIgnoreCase(
                dummySvcMgr.getAgencyId().getScheme()) == 0
                && bibliographicRecordId.getAgencyId().getValue().compareToIgnoreCase(
                dummySvcMgr.getAgencyId().getValue()) == 0 ) {

                DummyDatabase.BibInfo bibInfo = DummyDatabase.BibInfo.getByBibNo(
                    bibliographicRecordId.getBibliographicRecordIdentifier());

                if ( bibInfo != null ) {

                    bibInfos = new ArrayList<DummyDatabase.BibInfo>();
                    bibInfos.add(bibInfo);

                }

            }

            if ( bibInfos != null ) {

                for ( DummyDatabase.BibInfo bibInfo : bibInfos ) {

                    for ( DummyDatabase.HoldingInfo holdingInfo : bibInfo.holdings) {

                        DummyDatabase.ItemInfo[] itemInfos = holdingInfo.items;
                        if ( itemInfos != null && itemInfos.length > 0 ) {

                            for ( DummyDatabase.ItemInfo itemInfo : itemInfos ) {

                                if ( itemInfo.circStatus.equals(DummyDatabase.CircStatus.ON_SHELF)) {

                                    itemBarcode = itemInfos[0].barcode;
                                    break;

                                }

                            }

                        }

                        if ( itemBarcode != null ) {

                            break;

                        }

                    }

                    if ( itemBarcode != null ) {

                        break;

                    }

                }

            }

        } else if (initData.getItemId() != null ) {

            DummyDatabase.ItemInfo itemInfo = DummyDatabase.ItemInfo.getByBarcode(
                initData.getItemId().getItemIdentifierValue());

            if ( itemInfo != null && itemInfo.circStatus.equals(DummyDatabase.CircStatus.ON_SHELF)) {

                itemBarcode = initData.getItemId().getItemIdentifierValue();

            }

        }

        if ( itemBarcode != null ) {

            responseData.setUserId(initData.getUserId());
            responseData.setRequestScopeType(Version1RequestScopeType.ITEM);
            responseData.setRequestType(initData.getRequestType());

            String requestIdString = dummySvcMgr.getNextRequestId();
            if (initData.getRequestId() != null) {
                // Echo back the same request id that came in
                requestIdString = initData.getRequestId().getRequestIdentifierValue();
            }
            RequestId requestId = new RequestId();
            requestId.setRequestIdentifierValue(requestIdString);
            responseData.setRequestId(requestId);

            ItemId itemId = new ItemId();
            itemId.setItemIdentifierValue(itemBarcode);
            responseData.setItemId(itemId);

        }
        else {

            // TODO: Return Problem - no match for Bibliographic Id

        }

        return responseData;
    }

}
