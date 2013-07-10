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
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupUserResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupUserService;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

/**
 */
public class SymphonyLookupUserService extends SymphonyService implements LookupUserService {

    private final static Logger logger = Logger.getLogger(SymphonyLookupUserService.class);

    @Override
    public LookupUserResponseData performService(LookupUserInitiationData initData, RemoteServiceManager serviceManager) throws ServiceException {
        try {
        	if ( logger.isDebugEnabled() ) {
        		logger.debug("User lookup on " + initData.getUserId() );
        	}
            return getServiceManager(serviceManager).doLookupUser(initData);
        } catch( HttpException hx ) {
            throw new ServiceException(ServiceError.RUNTIME_ERROR, hx.getMessage(), hx);
        }
    }
}
