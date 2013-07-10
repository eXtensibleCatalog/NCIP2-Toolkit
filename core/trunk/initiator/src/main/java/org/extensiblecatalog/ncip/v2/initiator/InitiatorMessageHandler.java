/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator;

import org.extensiblecatalog.ncip.v2.common.MessageHandler;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.InitiatorServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import java.util.List;

public class InitiatorMessageHandler implements MessageHandler {

    // TODO: Get this from config
    protected NCIPService<NCIPInitiationData, NCIPResponseData> service;

    public InitiatorMessageHandler() {

        try {

            service = new InitiatorService();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    protected InitiatorServiceManager serviceManager;

    @Override
    public NCIPResponseData performService(NCIPInitiationData initiationData, ServiceContext serviceContext) {

        NCIPResponseData responseData;

        try {

            responseData = service.performService(initiationData, serviceContext, serviceManager);

        } catch (ServiceException e) {

            List<Problem> problems = ServiceHelper.generateProblems(
                Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE, "NCIPMessage", null, "Exception:", e);
            ProblemResponseData problemResponseData = new ProblemResponseData();
            problemResponseData.setProblems(problems);
            responseData = problemResponseData;

        } catch (ValidationException e) {

            ProblemResponseData problemResponseData = new ProblemResponseData();
            problemResponseData.setProblems(e.getProblems());
            responseData = problemResponseData;

        }

        return responseData;

    }
}
