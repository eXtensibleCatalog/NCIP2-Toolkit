/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator.implprof1;

import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.io.InputStream;

public interface InitiatorServiceManager extends RemoteServiceManager {

    // TODO: Change to setNCIPResponderAddress as this interface is now implemented by a concrete RemoteServiceManager and this method name is too vague
    /**
     * Set the network address, or URL, of the target NCIP responder.
     *
     * @param targetAddress the network address, or URL
     */
    void setTargetAddress(String targetAddress);

    /**
     * Get the network address, or URL, of the target NCIP responder.
     *
     * @return the network address, or URL
     */
    String getTargetAddress();

    InputStream sendMessage(byte[] initiationMsgBytes) throws ServiceException;
}
