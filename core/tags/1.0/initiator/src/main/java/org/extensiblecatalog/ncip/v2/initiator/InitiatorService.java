/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator;

import org.extensiblecatalog.ncip.v2.common.LoggingHelper;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;

import java.io.*;
import org.apache.log4j.Logger;

/**
 * This class implements the NCIPService<NCIPInitiationData, NCIPResponseData> interface for an NCIP initiator.
 * As the functionality required does not vary by NCIPInitiationData or NCIPResponseData sub-class,
 * there does not need to be more than a single implementation of that interface for an initiator.
 * This class uses a Translator to convert the NCIPInitiationData object to an array of bytes, which it then
 * passes to the RemoteServiceManager to send to the NCIP Resonder and return an array of bytes representing the
 * NCIP response message.
 */
public class InitiatorService implements NCIPService<NCIPInitiationData, NCIPResponseData> {

    private static final Logger LOG = Logger.getLogger(InitiatorService.class);
    /**
     * Whether or not to log initiation and response messages.
     */
    protected static boolean logMessages = true;

    /**
     * For translating objects defined in the service package into XML binding-generated objects.
     */
    protected Translator translator;

    /**
     * Construct an un-configured InitiatorService; clients must call setTranslator(Translator)
     * to configure this object.
     */
    public InitiatorService() {
    }

    /**
     * Construct an InitiatorService using the {@link Translator} object provided.
     *
     * @param translator the Translator to use
     */
    public InitiatorService(Translator translator) {
        this.translator = translator;
    }

    /**
     * Set the {@link Translator} to be used by this InitiatorService. Note: If the Translator was not set during
     * construction ({@see #InitiatorService(Translator)} then this method must be used to configure the
     * InitiatorService.
     *
     * @param translator the Translator to use
     */
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    @Override
    public NCIPResponseData performService(NCIPInitiationData initiationData,
                                           RemoteServiceManager serviceManager) throws ServiceException {

        NCIPImplProf1ServiceManager ncipSvcMgr = (NCIPImplProf1ServiceManager)serviceManager;

        InputStream inputStream = translator.createInitiationMessageStream(initiationData);

        if (logMessages) {

            inputStream = LoggingHelper.copyAndLogStream(LOG, inputStream);

        }

        try {

            int bytesAvailable = inputStream.available();
            byte[] initiationBytes = new byte[bytesAvailable];

            try {

                int bytesRead = inputStream.read(initiationBytes, 0, bytesAvailable);
                if ( bytesRead < bytesAvailable ) {

                    throw new ServiceException(ServiceError.RUNTIME_ERROR,
                            "Read fewer bytes (" + bytesRead + ") from the inputStream than were available ("
                                + bytesAvailable + ").");

                }

                InputStream responseMsgInputStream = ncipSvcMgr.sendMessage(initiationBytes);

                if (logMessages) {

                    responseMsgInputStream = LoggingHelper.copyAndLogStream(LOG, responseMsgInputStream);

                }

                NCIPResponseData responseData = translator.createResponseData(responseMsgInputStream);

                return responseData;

            } catch (IOException e) {

                throw new ServiceException(ServiceError.RUNTIME_ERROR,
                        "IOException reading bytes from the initiation message's InputStream.", e);
            }
        } catch (IOException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                    "IOException getting the count of available bytes from the initiation message's InputStream.", e);
        }

    }

}

