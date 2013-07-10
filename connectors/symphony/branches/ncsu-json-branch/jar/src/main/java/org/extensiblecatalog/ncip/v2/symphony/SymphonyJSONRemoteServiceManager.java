/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.symphony.json.CancelHoldResponse;
import org.extensiblecatalog.ncip.v2.symphony.json.DisplayUserResponse;
import org.extensiblecatalog.ncip.v2.symphony.json.RenewItemResponse;

/**
 * Service manager that brokers NCIP requests to the Sirsi JSON backend.
 * @author adam_constabaris@ncsu.edu
 *
 */
public class SymphonyJSONRemoteServiceManager implements RemoteServiceManager {

    private final static Logger logger = Logger.getLogger(SymphonyJSONRemoteServiceManager.class);

    private SymphonyHttpService httpService;

    public void setHttpService(SymphonyHttpService httpService) {
        this.httpService = httpService;
    }

    /**
     *
     * @param initData
     * @return
     * @throws ServiceException if an unanticipated problem occurs trying to handle responses from the
     * remote service.
     * @throws HttpException if there is a problem communicating with the remote service.
     */
    public LookupUserResponseData doLookupUser(LookupUserInitiationData initData) throws ServiceException {
        LookupUserResponseData data = new LookupUserResponseData();
        String userId = initData.getUserId().getUserIdentifierValue();
        boolean includeFiscalInfo = initData.getUserFiscalAccountDesired();
        boolean includeChargesInfo = initData.getLoanedItemsDesired();
        boolean includeRequestsInfo = initData.getRequestedItemsDesired();
        try {
            DisplayUserResponse response = httpService.doUserLookup(userId,includeFiscalInfo,includeChargesInfo,includeRequestsInfo);
            if ( response.isError() ) {
                data.setProblems( ConversionUtils.getProblems("Lookup failed", response.getErrorMessage() ) );
            } else {
                return response.getPatronInfo().getResponseData();
            }
        } catch( HttpException hx ) {
            throw hx;
        } catch( Exception x ) {
            logger.error("Unable to look up user " + userId, x);
            throw new ServiceException(ServiceError.RUNTIME_ERROR, x.getMessage(), x);
        }
        return data;
    }


    public CancelRequestItemResponseData doCancelRequest(CancelRequestItemInitiationData initData)
        throws ServiceException {
        CancelRequestItemResponseData resp = new CancelRequestItemResponseData();

        try {
            CancelHoldResponse response = httpService.doCancelHold(initData.getUserId().getUserIdentifierValue(),
                    initData.getItemId().getItemIdentifierValue());
            if ( response.isError() ) {
                 resp.setProblems(ConversionUtils.getProblems("RequestedItem", response.getErrorMessage() ) );
            } else {
                resp.setItemId(initData.getItemId());
                resp.setUserId(initData.getUserId());
            }
            return resp;
        } catch( HttpException hx ) {
            throw hx;
        } catch( Exception ex ) {
            throw new ServiceException(ServiceError.RUNTIME_ERROR, ex.getMessage(), ex);
        }
    }

    public RenewItemResponseData doRenewItem(RenewItemInitiationData initData)
        throws ServiceException {
        String itemId = initData.getItemId().getItemIdentifierValue();
        String userId = initData.getUserId().getUserIdentifierValue();
        RenewItemResponseData data = new RenewItemResponseData();
        try {
            RenewItemResponse response = httpService.doRenewItem(userId, itemId);
            if (response.isError()) {
                data.setProblems(ConversionUtils.getProblems("Backend", response.getErrorMessage()));
            } else {
                data.setUserId(initData.getUserId());
                data.setItemId(response.getRenewalInfo().getItem().getNCIPItemId());
                data.setDateDue(ConversionUtils.toGregorianCalendar(response.getRenewalInfo().getDueDate()));
            }
            return data;
        } catch( HttpException hx ) {
            throw hx;
        } catch (Exception x) {
            throw new ServiceException(ServiceError.RUNTIME_ERROR, x.getMessage(), x);

        }
    }
}
