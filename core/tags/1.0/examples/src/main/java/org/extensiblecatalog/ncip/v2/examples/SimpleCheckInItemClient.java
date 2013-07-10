/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

/**
 * This class provides a simple NCIP Initiator that sends an item barcode to
 * an NCIP Responder at a target URL and displays the results.
 */
public final class SimpleCheckInItemClient extends SimpleClient {

    public SimpleCheckInItemClient() {
    }

    @Override
    public void doService(String targetURL, String[] params) throws ServiceException {

        serviceManager.setTargetAddress(targetURL);

        NCIPImplProf1ServiceManager serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);

        CheckInItemInitiationData checkInItemData = new CheckInItemInitiationData();

        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(params[0]);
        checkInItemData.setItemId(itemId);

        NCIPResponseData responseData = service.performService(checkInItemData, serviceManager);

        if (responseData instanceof CheckInItemResponseData) {

            CheckInItemResponseData checkInItemResponseData = (CheckInItemResponseData)responseData;

            if (checkInItemResponseData.getProblems() != null
                && checkInItemResponseData.getProblems().size() > 0) {

                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : checkInItemResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }

                System.err.println("A problem was returned: " + problemBuffer.toString());

            } else {

                System.out.println("Check In Item succeeded: " + checkInItemResponseData);

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
