/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator.implprof1;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.initiator.NCIPClient;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class implements the NCIP Implementation Profile 1's transport methods for an initiator (by delegating
 * to the {@link NCIPImplProf1Client} class).
 * Note: At the moment it only supports HTTP and HTTPS transport (i.e., it does not support "Raw Socket"
 * transport).
 */
public class NCIPImplProf1ServiceManager implements InitiatorServiceManager {

    /**
     * The network address, or URL, of the target NCIP responder, e.g. https://mylibrary.mycollege.edu:9090/ncip/.
     */
    protected String targetURL;

    public NCIPImplProf1ServiceManager() {
        // Do nothing - expect caller to set attributes.    
    }

    public NCIPImplProf1ServiceManager(Properties properties) {

        if ( properties.containsKey("targetURL") ) {

            targetURL = properties.getProperty("targetURL");

        }

    }

    @Override
    public void setTargetAddress(String targetAddress) {
        this.targetURL = targetAddress;
    }

    @Override
    public String getTargetAddress() {

        return targetURL;

    }

    /**
     * Send a message (as represented by the byte array) to the current NCIP responder (i.e. the target address),
     * returning an {@link InputStream} from which clients can read the bytes of the response message.
     *
     * @param initiationMsgBytes the bytes representing the initiation message
     * @return an array of bytes representing the response message
     * @throws ServiceException if the NCIP service fails without the responder returning an NCIPMessage
     */
    @Override
    public InputStream sendMessage(byte[] initiationMsgBytes) throws ServiceException {
        // TODO: Should keep the client object
        NCIPClient ncipClient = null;
        try {

            ncipClient = new NCIPImplProf1Client();

        } catch (ToolkitException e) {
            
            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        }
        ncipClient.setTargetAddress(targetURL);
        InputStream inputStream = ncipClient.sendMessage(initiationMsgBytes);
        return inputStream;
    }
}
