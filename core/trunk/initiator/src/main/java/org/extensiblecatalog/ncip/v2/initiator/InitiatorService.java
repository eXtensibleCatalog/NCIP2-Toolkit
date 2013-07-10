/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator;

import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.InitiatorServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import java.io.*;
import java.util.Properties;

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
     * For translating objects defined in the service package into XML binding-generated objects.
     */
    protected Translator translator;

    /**
     * Construct an InitiatorService with default values.
     */
    public InitiatorService() throws ToolkitException {

        this.translator = TranslatorFactory.buildTranslator();

    }

    /**
     * Construct an InitiatorService with supplied properties.
     */
    public InitiatorService(Properties props) throws ToolkitException {

        this.translator = TranslatorFactory.buildTranslator(props);

    }

    /**
     * Construct an InitiatorService with values taken from the supplied configuration.
     * @param config
     * @throws ToolkitException
     */
    public InitiatorService(CoreConfiguration config) throws ToolkitException {

        this.translator = TranslatorFactory.buildTranslator(config.getTranslatorConfiguration());

    }

    /**
     * Construct an un-configured InitiatorService with the provided {@link ServiceValidatorFactory}.
     */
    public InitiatorService(ServiceValidatorFactory serviceContextFactory) throws ToolkitException {

        this.translator = TranslatorFactory.buildTranslator();

    }

    /**
     * Construct an InitiatorService using the {@link Translator} object provided.
     *
     * @param translator the Translator to use
     */
    public InitiatorService(Translator translator) throws ToolkitException {
        this.translator = translator;
    }

    /**
     * Construct an InitiatorService using the {@link ServiceValidatorFactory} and {@link Translator} object provided.
     *
     * @param translator the Translator to use
     */
    public InitiatorService(ServiceValidatorFactory serviceContextFactory, Translator translator) {

        this.translator = translator;

    }

    /**
     * Set the {@link Translator} to be used by this InitiatorService.
     *
     * @param translator the Translator to use
     */
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public NCIPResponseData performService(NCIPInitiationData initiationData,
                                           ServiceContext serviceContext,
                                           RemoteServiceManager serviceManager) throws ServiceException {

        InitiatorServiceManager ncipSvcMgr = (InitiatorServiceManager)serviceManager;

        InputStream inputStream = null;
        try {

            inputStream = translator.createInitiationMessageStream(serviceContext, initiationData);

        } catch (ValidationException e) {

            // TODO: Should return an NCIPResponseData object w/ Problem
            throw new ServiceException(ServiceError.RUNTIME_ERROR, "Exception validating the initiation message.", e);

        }

        try {

            int bytesAvailable = inputStream.available();
            byte[] initiationBytes = new byte[bytesAvailable];

            try {

                int bytesRead = inputStream.read(initiationBytes, 0, bytesAvailable);
                if ( bytesRead < bytesAvailable ) {

                    // TODO: Should return an NCIPResponseData object w/ Problem
                    throw new ServiceException(ServiceError.RUNTIME_ERROR,
                            "Read fewer bytes (" + bytesRead + ") from the inputStream than were available ("
                                + bytesAvailable + ").");

                }

                InputStream responseMsgInputStream = ncipSvcMgr.sendMessage(initiationBytes);

                NCIPResponseData responseData = translator.createResponseData(serviceContext, responseMsgInputStream);

                return responseData;

            } catch (IOException e) {

                // TODO: Should return an NCIPResponseData object w/ Problem
                throw new ServiceException(ServiceError.RUNTIME_ERROR,
                        "IOException reading bytes from the initiation message's InputStream.", e);

            } catch (ValidationException e) {

                ProblemResponseData responseData = new ProblemResponseData();
                responseData.setProblems(e.getProblems());

                return responseData;
                
            }

        } catch (IOException e) {

            // TODO: Should return an NCIPResponseData object w/ Problem
            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                    "Exception getting the count of available bytes from the initiation message's InputStream.", e);

        }

    }

}

