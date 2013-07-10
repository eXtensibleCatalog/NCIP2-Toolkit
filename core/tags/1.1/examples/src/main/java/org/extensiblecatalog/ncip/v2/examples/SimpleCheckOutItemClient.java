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

import java.text.DateFormat;

/**
 * This class provides a simple NCIP Initiator that sends an item barcode to
 * an NCIP Responder at a target URL and displays the results.
 */
public final class SimpleCheckOutItemClient extends SimpleClient {

    public SimpleCheckOutItemClient() throws ToolkitException {
    }

    @Override
    public void doService(String targetURL, String[] params) throws ServiceException, ValidationException,
        ToolkitException  {

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        serviceManager.setTargetAddress(targetURL);

        InitiatorServiceManager serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);

        CheckOutItemInitiationData checkOutItemData = new CheckOutItemInitiationData();

        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(params[0]);
        checkOutItemData.setItemId(itemId);

        UserId userId = new UserId();
        userId.setUserIdentifierValue(params[1]);
        checkOutItemData.setUserId(userId);

        NCIPResponseData responseData = service.performService(checkOutItemData, serviceContext, serviceManager);

        if (responseData instanceof CheckOutItemResponseData) {

            CheckOutItemResponseData checkOutItemResponseData = (CheckOutItemResponseData)responseData;

            if (checkOutItemResponseData.getProblems() != null
                && checkOutItemResponseData.getProblems().size() > 0) {

                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : checkOutItemResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }

                System.err.println("A problem was returned: " + problemBuffer.toString());

            } else {

                System.out.println("Check Out Item succeeded: " + checkOutItemResponseData);
                System.out.println("The due date is: " + DateFormat.getDateInstance().format(
                    checkOutItemResponseData.getDateDue().getTime()));

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
