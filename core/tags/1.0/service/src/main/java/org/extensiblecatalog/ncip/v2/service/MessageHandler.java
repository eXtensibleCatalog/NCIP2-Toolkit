/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Processes a message by determining the appropriate Service implementation and
 * calling the performService method on that.
 */
public class MessageHandler {

    /**
     * Map of supported Services
     */
    protected Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices;

    /**
     * Provides access to services
     */
    protected RemoteServiceManager serviceManager;

    /**
     * Construct a new MessageHandler
     *
     * @param supportedServices a map of supported services
     * @param serviceManager    provides access to remove services
     */
    public MessageHandler(Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices,
                          RemoteServiceManager serviceManager) {
        this.supportedServices = supportedServices;
        this.serviceManager = serviceManager;
    }

    public void setRemoteServiceManager(RemoteServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setSupportedServices(Map<String, NCIPService<NCIPInitiationData, NCIPResponseData>> supportedServices) {
        this.supportedServices = supportedServices;
    }

    public NCIPResponseData performService(NCIPInitiationData initiationData) {

        NCIPResponseData responseData;

        NCIPService<NCIPInitiationData, NCIPResponseData> service = null;

        if (supportedServices != null) {
            service = supportedServices.get(initiationData.getClass().getName());
        }

        // TODO: DummyService shouldn't require special handling; it should return the Unsupported Service error itself
        if (service != null && !service.getClass().getName().equalsIgnoreCase(
            "org.extensiblecatalog.ncip.v2.service.DummyService")) {

            try {

                responseData = service.performService(initiationData, serviceManager);

            } catch (ServiceException e) {

                List<Problem> problems = NCIPHelper.generateProblem(
                    Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE, "NCIPMessage", null, e.toString());
                ProblemResponseData problemResponseData = new ProblemResponseData();
                problemResponseData.setProblems(problems);
                responseData = problemResponseData;

            }

        } else {

            List<Problem> problems = NCIPHelper.generateProblem(
                Version1GeneralProcessingError.UNSUPPORTED_SERVICE, "NCIPMessage", null, null);
            ProblemResponseData problemResponseData = new ProblemResponseData();
            problemResponseData.setProblems(problems);
            responseData = problemResponseData;

        }
        return responseData;
    }

}
