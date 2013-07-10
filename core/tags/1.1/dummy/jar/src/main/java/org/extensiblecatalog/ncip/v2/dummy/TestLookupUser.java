/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class TestLookupUser {

    private static final Logger LOG = Logger.getLogger(TestLookupUser.class);

    protected MessageHandler messageHandler;

    {

        try {

            messageHandler = MessageHandlerFactory.getSharedMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }
    }

    @Test
    public void TestSimpleLookupUser() throws ServiceException, ToolkitException {

        NCIPService<LookupUserInitiationData, LookupUserResponseData> lookupUserService = new DummyLookupUserService();

        LookupUserInitiationData lookupUserData = new LookupUserInitiationData();

        UserId userId = new UserId();
        userId.setUserIdentifierValue("abc");
        lookupUserData.setUserId(userId);
        lookupUserData.setLoanedItemsDesired(true);
        lookupUserData.setRequestedItemsDesired(true);
        lookupUserData.setUserFiscalAccountDesired(true);

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(lookupUserData, serviceContext);

        System.out.println("Response: " + responseData);

        SimpleDateFormat dateFormatter = new SimpleDateFormat();
        
        if ( responseData instanceof LookupUserResponseData ) {

            LookupUserResponseData lookupUserResponse = (LookupUserResponseData)responseData;

            System.out.println("User barcode: " + lookupUserResponse.getUserId().getUserIdentifierValue());
            if ( lookupUserResponse.getLoanedItems() != null && lookupUserResponse.getLoanedItems().size() > 0 ) {

                for ( LoanedItem loanedItem : lookupUserResponse.getLoanedItems() ) {

                    System.out.println("Loaned item id: " + loanedItem.getItemId().getItemIdentifierValue());
                    System.out.println("Loaned item title: " + loanedItem.getTitle());
                    System.out.println("Loaned item date due: " + (loanedItem.getDateDue() == null ?
                        "" : dateFormatter.format(loanedItem.getDateDue().getTime())));
                    System.out.println("Loaned item amount: " + NCIPHelper.formatMonetaryAmount(
                        loanedItem.getAmount().getMonetaryValue(), loanedItem.getAmount().getCurrencyCode()));
                    System.out.println("Loaned item indeterminate loan period flag: " + loanedItem.getIndeterminateLoanPeriodFlag());
                    System.out.println("Loaned item medium type: " + NCIPHelper.formatSVP(loanedItem.getMediumType()));
                    System.out.println("Loaned item reminder level: " + loanedItem.getReminderLevel());

                }

            } else {

                System.out.println("Loaned items: none.");
            }

            if ( lookupUserResponse.getLoanedItemsCounts() != null && lookupUserResponse.getLoanedItemsCounts().size() > 0 ) {

                for ( LoanedItemsCount loanedItemsCount : lookupUserResponse.getLoanedItemsCounts() ) {

                    System.out.println("Loaned item count circ status: " + NCIPHelper.formatSVP(loanedItemsCount.getCirculationStatus()));
                    System.out.println("Loaned item count use restriction: " + NCIPHelper.formatSVP(loanedItemsCount.getItemUseRestrictionType()));
                    System.out.println("Loaned item count value: " + loanedItemsCount.getLoanedItemCountValue());

                }

            } else {

                System.out.println("Loaned item counts: none.");
            }

            if ( lookupUserResponse.getRequestedItems() != null && lookupUserResponse.getRequestedItems().size() > 0 ) {

                for ( RequestedItem requestedItem : lookupUserResponse.getRequestedItems() ) {

                    System.out.println("Request id: " + requestedItem.getRequestId().getRequestIdentifierValue());
                    System.out.println("Requested item id: " + (requestedItem.getItemId() == null ? "" : requestedItem.getItemId().getItemIdentifierValue()));
                    System.out.println("Request status type: " + NCIPHelper.formatSVP(requestedItem.getRequestStatusType()));
                    System.out.println("Request type: " + NCIPHelper.formatSVP(requestedItem.getRequestType()));
                    System.out.println("Requested item title: " + requestedItem.getTitle());
                    System.out.println("Requested item medium type: " + NCIPHelper.formatSVP(requestedItem.getMediumType()));
                    System.out.println("Date placed: " + (requestedItem.getDatePlaced() == null ?
                        "" : dateFormatter.format(requestedItem.getDatePlaced().getTime())));
                    System.out.println("Request queue position: " + requestedItem.getHoldQueuePosition());
                    System.out.println("Pickup date: " + (requestedItem.getPickupDate() == null ?
                        "" : dateFormatter.format(requestedItem.getPickupDate().getTime())));
                    System.out.println("Pickup location: " + NCIPHelper.formatSVP(requestedItem.getPickupLocation()));
                    System.out.println("Pickup expiry date: " + (requestedItem.getPickupExpiryDate() == null ?
                        "" : dateFormatter.format(requestedItem.getPickupExpiryDate().getTime())));
                    System.out.println("Reminder level: " + requestedItem.getReminderLevel());

                }

            } else {

                System.out.println("Requested items: none.");
            }

            if ( lookupUserResponse.getRequestedItemsCounts() != null && lookupUserResponse.getRequestedItemsCounts().size() > 0 ) {

                for ( RequestedItemsCount requestedItemsCount : lookupUserResponse.getRequestedItemsCounts() ) {

                    System.out.println("Requested item count circ status: " + NCIPHelper.formatSVP(requestedItemsCount.getCirculationStatus()));
                    System.out.println("Requested item count use restriction: " + NCIPHelper.formatSVP(requestedItemsCount.getItemUseRestrictionType()));
                    System.out.println("Requested item count request type: " + NCIPHelper.formatSVP(requestedItemsCount.getRequestType()));
                    System.out.println("Requested item count value: " + requestedItemsCount.getRequestedItemCountValue());

                }

            } else {

                System.out.println("Requested item counts: none.");
            }

            System.out.println("User fiscal accounts: " + lookupUserResponse.getUserFiscalAccounts());

            if ( lookupUserResponse.getUserOptionalFields() != null ) {

                UserOptionalFields userFields = lookupUserResponse.getUserOptionalFields();
                System.out.println("Name: " + userFields.getNameInformation());
                System.out.println("Block or traps: " + userFields.getBlockOrTraps());
                System.out.println("Date of birth: " + (userFields.getDateOfBirth() == null ?
                        "" : dateFormatter.format(userFields.getDateOfBirth().getTime())));
                System.out.println("Previous user ids: " + userFields.getPreviousUserIds());
                System.out.println("User Address: " + userFields.getUserAddressInformations());
                System.out.println("User Ids: " + userFields.getUserIds());
                System.out.println("Languages: " + userFields.getUserLanguages());
                System.out.println("Privileges: " + userFields.getUserPrivileges());

            } else {

                System.out.println("User optional fields: none.");
            }

            System.out.println("Problems: " + lookupUserResponse.getProblems());

        }

    }
}
