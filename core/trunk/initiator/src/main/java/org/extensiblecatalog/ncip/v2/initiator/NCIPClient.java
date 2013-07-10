/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator;

import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.io.InputStream;

/**
 * NCIPClient defines an interface for communicating with an NCIP target
 */
public interface NCIPClient {

    /**
     * Sets/Resets the NCIP target address
     *
     * @param url the target address
     */
    void setTargetAddress(String url);

    /**
     * Retrieves the NCIP target address
     *
     * @return url the target address
     */
    String getTargetAddress();

    /**
     * Sends/Receives an NCIP Request to the target and
     *
     * @param initiationMsgBytes the initiation message as an array of bytes (network octets)
     * @return InputStream from which the response message can be read as bytes (network octets)
     * @throws ServiceException when a failure occurs
     */
    InputStream sendMessage(byte[] initiationMsgBytes) throws ServiceException;

}
