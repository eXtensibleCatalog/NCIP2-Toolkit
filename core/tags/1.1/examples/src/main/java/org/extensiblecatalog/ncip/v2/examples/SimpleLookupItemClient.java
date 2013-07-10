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
 * This class provides a simple NCIP Initiator that sends an item barcode to
 * an NCIP Responder at a target URL and displays the results.
 */
public final class SimpleLookupItemClient extends SimpleClient {

    public SimpleLookupItemClient() throws ToolkitException {
    }

    @Override
    public void doService(String targetURL, String[] params) throws ServiceException, ValidationException,
        ToolkitException {

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        serviceManager.setTargetAddress(targetURL);

        InitiatorServiceManager serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);

        LookupItemInitiationData lookupItemData = new LookupItemInitiationData();

        if ( params.length >= 1 ) {

            if ( params[0].matches("(?i)^-?r.*") )  {

                RequestId requestId = new RequestId();

                if ( params[0].matches(".*=.*") ) {

                    requestId.setRequestIdentifierValue(params[0].substring(params[0].indexOf('=') + 1));

                } else if ( params.length > 1 ) {

                    requestId.setRequestIdentifierValue(params[1]);

                } else {

                    System.err.println(getUsagePrefix() + " (i|itemId) <itemid> | (r|requestId) <requestid>");
                    System.err.println("Optionally you can use syntax like \"itemId=<itemid>\"");
                    System.exit(-1);

                }
                
                lookupItemData.setRequestId(requestId);

            } else {

                ItemId itemId = new ItemId();

                if ( params.length > 1 ) {

                    itemId.setItemIdentifierValue(params[1]);

                } else if ( params[0].matches(".*=.*") ) {

                    itemId.setItemIdentifierValue(params[0].substring(params[0].indexOf('=') + 1));

                } else {

                    itemId.setItemIdentifierValue(params[0]);

                }

                lookupItemData.setItemId(itemId);

            }

        } else {

            System.err.println(getUsagePrefix() + " (i|itemId) <itemid> | (r|requestId) <requestid>");
            System.err.println("Optionally you can use syntax like \"itemId=<itemid>\"");
            System.exit(-1);

        }

        NCIPResponseData responseData = service.performService(lookupItemData, serviceContext, serviceManager);

        if (responseData instanceof LookupItemResponseData) {

            LookupItemResponseData lookupItemResponseData = (LookupItemResponseData)responseData;

            if (lookupItemResponseData.getProblems() != null
                && lookupItemResponseData.getProblems().size() > 0) {
                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : lookupItemResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }
                System.err.println("A problem was returned: " + problemBuffer.toString());
            } else {
                System.out.println("Lookup Item succeeded: " + lookupItemResponseData);
                System.out.println("The item id is: " + lookupItemResponseData.getItemId().getItemIdentifierValue());
                if (lookupItemResponseData.getItemOptionalFields() != null) {

                    if (lookupItemResponseData.getItemOptionalFields().getBibliographicDescription() != null
                        && lookupItemResponseData.getItemOptionalFields().getBibliographicDescription()
                        .getTitle() != null) {
                        System.out.println("The title is: " + lookupItemResponseData.getItemOptionalFields()
                            .getBibliographicDescription().getTitle());
                    }

                    if (lookupItemResponseData.getItemOptionalFields().getItemDescription() != null) {

                        if (lookupItemResponseData.getItemOptionalFields().getItemDescription().getCallNumber()
                            != null) {
                            System.out.println("The call number is: "
                                + lookupItemResponseData.getItemOptionalFields()
                                    .getItemDescription().getCallNumber());
                        }
                    }

                    if (lookupItemResponseData.getItemOptionalFields().getCirculationStatus() != null) {
                        System.out.println("The circulation status is: "
                            + (lookupItemResponseData.getItemOptionalFields().getCirculationStatus().getScheme()
                            != null ? "scheme \""
                            + lookupItemResponseData.getItemOptionalFields().getCirculationStatus()
                            .getScheme() + "\"" : "")
                            + ", value \"" + lookupItemResponseData.getItemOptionalFields()
                                .getCirculationStatus().getValue() + "\"");
                    }

                }

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
