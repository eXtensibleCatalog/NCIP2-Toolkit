/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.InitiatorServiceManager;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class provides a simple NCIP Initiator that sends an user id to
 * an NCIP Responder at a target URL and displays the results.
 */
public final class SimpleLookupUserClient extends SimpleClient {

    public SimpleLookupUserClient() throws ToolkitException {
    }

    @Override
    public void doService(String targetURL, String[] params) throws ServiceException, ValidationException,
        ToolkitException {

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        serviceManager.setTargetAddress(targetURL);

        InitiatorServiceManager serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);

        LookupUserInitiationData lookupUserData = new LookupUserInitiationData();
        InitiationHeader initHeader = new InitiationHeader();
        lookupUserData.setInitiationHeader(initHeader);
        AgencyId agencyId;
        if ( params.length > 1 ) {
            agencyId = new AgencyId(params[1]);
        } else {
            agencyId = new AgencyId("1");
        }
        ToAgencyId toAgencyId = new ToAgencyId();
        toAgencyId.setAgencyId(agencyId);
        initHeader.setToAgencyId(toAgencyId);
        FromAgencyId fromAgencyId = new FromAgencyId();
        fromAgencyId.setAgencyId(agencyId);
        initHeader.setFromAgencyId(fromAgencyId);

        UserId userId = new UserId(); //the server side UserId class
        userId.setUserIdentifierValue(params[0]);
        userId.setAgencyId(agencyId);
        lookupUserData.setUserId(userId);

        lookupUserData.setLoanedItemsDesired(true);
        lookupUserData.setRequestedItemsDesired(true);
        lookupUserData.setUserFiscalAccountDesired(true);

        NCIPResponseData responseData = service.performService(lookupUserData, serviceContext, serviceManager);

        if (responseData instanceof LookupUserResponseData) {

            LookupUserResponseData lookupUserResponseData = (LookupUserResponseData)responseData;

            if (lookupUserResponseData.getProblems() != null
                && lookupUserResponseData.getProblems().size() > 0) {
                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : lookupUserResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }
                System.err.println("A problem was returned: " + problemBuffer.toString());
            } else {
                System.out.println("Lookup User succeeded: " + lookupUserResponseData);
                System.out.println("The User id is: " + lookupUserResponseData.getUserId().getUserIdentifierValue());
            }

        } else if (responseData instanceof ProblemResponseData) {

            ProblemResponseData problemResponseData = (ProblemResponseData)responseData;

            if (problemResponseData.getProblems() != null && problemResponseData.getProblems().size() > 0) {

                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : problemResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }
                System.err.println("A problem was returned: " + problemBuffer.toString());

            } else {

                System.err.println("A Problem response was returned, but it appears to be empty: "
                    + problemResponseData);

            }

        } else {

            System.err.println("Unexpected response type returned: " + responseData);

        }

    }
}
