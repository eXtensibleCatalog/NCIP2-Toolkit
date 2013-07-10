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

import org.extensiblecatalog.ncip.v2.service.*;

/**
 * RenewItemService implementation for Sirsi Symphony.
 * @author adam_constabaris@ncsu.edu, $LastChangedBy$
 */
public class SymphonyRenewItemService extends SymphonyService implements RenewItemService {


    public SymphonyRenewItemService() {
        super();
    }


    @Override
    public RenewItemResponseData performService(RenewItemInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {
        try {
            return getServiceManager(serviceManager).doRenewItem(initData);
        } catch( HttpException hx ) {
            getLogger().error("Error communicating with remote service", hx);
            throw new ServiceException(ServiceError.RUNTIME_ERROR, hx.getMessage());
        } catch( ServiceException sx ) {
            // if we just let it be thrown it can be hard to find the logger output.  This
            // needs fixin'
            getLogger().error("Logging ServiceException for renewItem", sx);
            throw sx;
        }
    }
}
