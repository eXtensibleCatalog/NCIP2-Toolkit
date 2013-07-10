/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator.implprof1;

import org.extensiblecatalog.ncip.v2.initiator.NCIPClient;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.io.InputStream;

/**
 * This class implements the NCIP Implementation Profile 1's transport methods for an initiator (by delegating
 * to the {@link NCIPImplProf1Client} class).
 * Note: At the moment it only supports HTTP and HTTPS transport (i.e., it does not support "Raw Socket"
 * transport).
 */
public class NCIPImplProf1ServiceManager implements RemoteServiceManager {

    /**
     * The network address, or URL, of the target NCIP responder, e.g. https://mylibrary.mycollege.edu:9090/ncip/.
     */
    protected String targetURL;

    /**
     * Set the network address, or URL, of the target NCIP responder.
     *
     * @param targetAddress the network address, or URL
     */
    public void setTargetAddress(String targetAddress) {
        this.targetURL = targetAddress;
    }

    /**
     * Send a message (as represented by the byte array) to the current NCIP responder (i.e. the target address),
     * returning an {@link InputStream} from which clients can read the bytes of the response message.
     *
     * @param initiationMsgBytes the bytes representing the initiation message
     * @return an array of bytes representing the response message
     * @throws ServiceException if the NCIP service fails without the responder returning an NCIPMessage
     */
    public InputStream sendMessage(byte[] initiationMsgBytes) throws ServiceException {
        NCIPClient ncipClient = new NCIPImplProf1Client();
        ncipClient.setTargetAddress(targetURL);
        return ncipClient.sendMessage(initiationMsgBytes);
    }
}
