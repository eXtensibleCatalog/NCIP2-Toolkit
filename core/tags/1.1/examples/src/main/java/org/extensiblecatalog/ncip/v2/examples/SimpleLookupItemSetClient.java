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

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a simple NCIP Initiator that sends an item barcode to
 * an NCIP Responder at a target URL and displays the results.
 */
public final class SimpleLookupItemSetClient extends SimpleClient {

    public SimpleLookupItemSetClient() throws ToolkitException {
    }

    @Override
    public void doService(String targetURL, String[] params) throws ServiceException, ValidationException,
        ToolkitException {

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        serviceManager.setTargetAddress(targetURL);

        InitiatorServiceManager serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);

        LookupItemSetInitiationData lookupItemSetData = new LookupItemSetInitiationData();

        BibliographicId bibId = new BibliographicId();
        BibliographicRecordId bibRecordId = new BibliographicRecordId();
        bibRecordId.setBibliographicRecordIdentifier(params[0]);
        AgencyId agencyId = new AgencyId(null, params[1]);
        bibRecordId.setAgencyId(agencyId);
        bibId.setBibliographicRecordId(bibRecordId);
        List<BibliographicId> bibIds = new ArrayList<BibliographicId>();
        bibIds.add(bibId);
        lookupItemSetData.setBibliographicIds(bibIds);

        NCIPResponseData responseData = service.performService(lookupItemSetData, serviceContext, serviceManager);

        if (responseData instanceof LookupItemSetResponseData) {

            LookupItemSetResponseData lookupItemSetResponseData = (LookupItemSetResponseData)responseData;

            if (lookupItemSetResponseData.getProblems() != null
                && lookupItemSetResponseData.getProblems().size() > 0) {
                StringBuffer problemBuffer = new StringBuffer();
                for (Problem problem : lookupItemSetResponseData.getProblems()) {
                    problemBuffer.append(problem.toString()).append(System.getProperty("line.separator"));
                }
                System.err.println("A problem was returned: " + problemBuffer.toString());
            } else {
                System.out.println("Lookup Item Set succeeded: " + lookupItemSetResponseData);

                if (lookupItemSetResponseData.getBibInformations() != null
                    && lookupItemSetResponseData.getBibInformations().size() > 0) {

                    for (BibInformation bibInformation : lookupItemSetResponseData.getBibInformations()) {

                        if (bibInformation.getHoldingsSets() != null
                            && bibInformation.getHoldingsSets().size() > 0 ) {

                            if (bibInformation.getBibliographicId() != null) {

                                System.out.println("BibliographicId: " + bibInformation.getBibliographicId());

                            }

                            for (HoldingsSet holdingsSet : bibInformation.getHoldingsSets()) {

                                if (holdingsSet.getHoldingsSetId() != null) {

                                    System.out.println("HoldingsSetId: " + holdingsSet.getHoldingsSetId());

                                }

                                if (holdingsSet.getLocation() != null) {

                                    System.out.println("Location: " + holdingsSet.getLocation());

                                }

                                if (holdingsSet.getSummaryHoldingsInformation() != null) {

                                    System.out.println("HoldingsInformation: "
                                        + holdingsSet.getSummaryHoldingsInformation());

                                }

                                if ( holdingsSet.getItemInformations() != null
                                    && holdingsSet.getItemInformations().size() > 0) {

                                    for (ItemInformation itemInformation : holdingsSet.getItemInformations()) {

                                        System.out.println("ItemInformation: " + itemInformation);

                                    }

                                }
                            }

                        }

                    }

                }

                if ( lookupItemSetResponseData.getNextItemToken() != null) {

                    System.out.println("The next token is '" + lookupItemSetResponseData.getNextItemToken() + "'.");

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
