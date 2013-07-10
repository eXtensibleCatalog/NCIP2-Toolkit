/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.binding.BindingError;
import org.extensiblecatalog.ncip.v2.binding.BindingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.*;

/*
   Transit object or list of objects which is in SVC scope to JAXB scope
 */
public class ScopeConvertSVC2JAX {

    protected JAXBTranslator mainTranslator;

    protected final ObjectFactory objectFactory = new ObjectFactory();

    public ScopeConvertSVC2JAX(JAXBTranslator mainTranslator) {
        this.mainTranslator = mainTranslator;
    }

    /*
     UserFiscalAccount list
    */
    public List<UserFiscalAccount> createUserFiscalAccounts(
        List<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount> svcUserFiscalAccounts)
        throws BindingException {

        List<UserFiscalAccount> jaxbUserFiscalAccounts = new ArrayList<UserFiscalAccount>();

        if (svcUserFiscalAccounts != null && svcUserFiscalAccounts.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.UserFiscalAccount svcUserFiscalAccount : svcUserFiscalAccounts) {

                UserFiscalAccount jaxbUserFiscalAccount = createUserFiscalAccount(svcUserFiscalAccount);

                if (svcUserFiscalAccount != null) {
                    jaxbUserFiscalAccounts.add(jaxbUserFiscalAccount);
                }
            }
        }
        return jaxbUserFiscalAccounts;
    }

    /*
     Transforming from the SVC object to JAXB object, server-side to client-side
     The single transforming
    */
    public UserFiscalAccount createUserFiscalAccount(
        org.extensiblecatalog.ncip.v2.service.UserFiscalAccount userFiscalAccount) throws BindingException {

        UserFiscalAccount jaxbUserFiscalAccount = null;

        if ( userFiscalAccount != null ) {

            jaxbUserFiscalAccount = new UserFiscalAccount();

            jaxbUserFiscalAccount.setAccountBalance(createAccountBalance(userFiscalAccount.getAccountBalance()));

            if ( userFiscalAccount.getAccountDetails() != null ) {

                jaxbUserFiscalAccount.getAccountDetails().addAll(createAccountDetailsList(userFiscalAccount.getAccountDetails()));

            }

        }

        return jaxbUserFiscalAccount;

    }

    public AccountBalance createAccountBalance(org.extensiblecatalog.ncip.v2.service.AccountBalance accountBalance) {

        AccountBalance jaxbAccountBalance = null;

        if ( accountBalance != null ) {

            jaxbAccountBalance = new AccountBalance();

            jaxbAccountBalance.setCurrencyCode(mainTranslator.convertSVP(accountBalance.getCurrencyCode()));

            jaxbAccountBalance.setMonetaryValue(accountBalance.getMonetaryValue());

        }

        return jaxbAccountBalance;

    }

    /*
     AccountDetails list
    */
    public List<AccountDetails> createAccountDetailsList(
        List<org.extensiblecatalog.ncip.v2.service.AccountDetails> svcAccountDetailsList)
        throws BindingException {

        List<AccountDetails> jaxbAccountDetailsList = new ArrayList<AccountDetails>();

        if (svcAccountDetailsList != null && svcAccountDetailsList.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.AccountDetails svcAccountDetails : svcAccountDetailsList) {

                AccountDetails jaxbAccountDetails = createAccountDetails(svcAccountDetails);

                if (jaxbAccountDetails != null) {

                    jaxbAccountDetailsList.add(jaxbAccountDetails);
                }
            }
        }
        return jaxbAccountDetailsList;
    }


    public AccountDetails createAccountDetails(org.extensiblecatalog.ncip.v2.service.AccountDetails accountDetails)
        throws BindingException {

        AccountDetails jaxbAccountDetails = null;

        if ( accountDetails != null ) {

            jaxbAccountDetails = new AccountDetails();

            jaxbAccountDetails.setAccrualDate(JAXBTranslator.convertDate(accountDetails.getAccrualDate()));

            jaxbAccountDetails.setFiscalTransactionInformation(
                mainTranslator.createFiscalTransactionInformation(accountDetails.getFiscalTransactionInformation()));
        }

        return jaxbAccountDetails;

    }

    /*
     LoanedItemsCount list
    */
    public List<LoanedItemsCount> createLoanedItemsCounts(
        List<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount> svcLoanedItemsCounts)
        throws BindingException {

        List<LoanedItemsCount> jaxbLoanedItemsCounts = new ArrayList<LoanedItemsCount>();

        if (svcLoanedItemsCounts != null && svcLoanedItemsCounts.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.LoanedItemsCount svcLoanedItemsCount : svcLoanedItemsCounts) {

                LoanedItemsCount jaxbLoanedItemsCount = createLoanedItemsCount(svcLoanedItemsCount);

                if (svcLoanedItemsCount != null) {
                    jaxbLoanedItemsCounts.add(jaxbLoanedItemsCount);
                }
            }
        }
        return jaxbLoanedItemsCounts;
    }

    /*
     Transforming from the SVC object to JAXB object, server-side to client-side
     The single transforming
    */
    public LoanedItemsCount createLoanedItemsCount(
        org.extensiblecatalog.ncip.v2.service.LoanedItemsCount loanedItemsCount) throws BindingException {

        LoanedItemsCount jaxbLoanedItemsCount = null;

        if ( loanedItemsCount != null ) {

            jaxbLoanedItemsCount = new LoanedItemsCount();

            if ( loanedItemsCount.getCirculationStatus() != null ) {

                jaxbLoanedItemsCount.setCirculationStatus(
                    mainTranslator.convertSVP(loanedItemsCount.getCirculationStatus()));

            } else if ( loanedItemsCount.getItemUseRestrictionType() != null ) {

                jaxbLoanedItemsCount.setItemUseRestrictionType(
                    mainTranslator.convertSVP(loanedItemsCount.getItemUseRestrictionType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either CirculationStatus or ItemUseRestrictionType must be non-null and non-empty "
                        + "in LoanedItemsCount.");

            }

            jaxbLoanedItemsCount.setLoanedItemCountValue(loanedItemsCount.getLoanedItemCountValue());

        }
        return jaxbLoanedItemsCount;

    }


    /*
     LoanedItem list
    */
    public List<LoanedItem> createLoanedItems(List<org.extensiblecatalog.ncip.v2.service.LoanedItem> svcLoanedItems)
        throws BindingException {

        List<LoanedItem> jaxbLoanedItems = new ArrayList<LoanedItem>();

        if (svcLoanedItems != null && svcLoanedItems.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.LoanedItem svcLoanedItem : svcLoanedItems) {

                LoanedItem jaxbLoanedItem = createLoanedItem(svcLoanedItem);

                if (svcLoanedItem != null) {
                    jaxbLoanedItems.add(jaxbLoanedItem);
                }
            }
        }
        return jaxbLoanedItems;
    }

    /*
     Transforming from the SVC object to JAXB object, server-side to client-side
     The single transforming
    */
    public LoanedItem createLoanedItem(org.extensiblecatalog.ncip.v2.service.LoanedItem loanedItem)
        throws BindingException {

        LoanedItem jaxbLoanedItem = null;

        if ( loanedItem != null ) {

            jaxbLoanedItem = new LoanedItem();

            jaxbLoanedItem.setItemId(mainTranslator.createItemId(loanedItem.getItemId()));

            jaxbLoanedItem.setReminderLevel(loanedItem.getReminderLevel());

            if ( loanedItem.getDateDue() != null ) {

                jaxbLoanedItem.setDateDue(JAXBTranslator.convertDate(loanedItem.getDateDue()));

            } else if ( loanedItem.getIndeterminateLoanPeriodFlag() != null ) {

                jaxbLoanedItem.setIndeterminateLoanPeriodFlag(new IndeterminateLoanPeriodFlag());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either DateDue or IndeterminateLoanPeriodFlag must be non-null and non-empty "
                        + "in LoanedItem.");

            }

            jaxbLoanedItem.setAmount(mainTranslator.createAmount(loanedItem.getAmount()));

            jaxbLoanedItem.setTitle(loanedItem.getTitle());

            jaxbLoanedItem.setMediumType(mainTranslator.convertSVP(loanedItem.getMediumType()));

        }

        return jaxbLoanedItem;

    }


    /*
     RequestedItemsCount list
    */
    public List<RequestedItemsCount> createRequestedItemsCounts(
        List<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount> svcRequestedItemsCounts)
        throws BindingException {

        List<RequestedItemsCount> jaxbRequestedItemsCounts = new ArrayList<RequestedItemsCount>();

        if (svcRequestedItemsCounts != null && svcRequestedItemsCounts.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.RequestedItemsCount svcRequestedItemsCount
                : svcRequestedItemsCounts) {

                RequestedItemsCount jaxbRequestedItemsCount = createRequestedItemsCount(svcRequestedItemsCount);

                if (svcRequestedItemsCount != null) {
                    jaxbRequestedItemsCounts.add(jaxbRequestedItemsCount);
                }
            }
        }
        return jaxbRequestedItemsCounts;
    }

    /*
     Transforming from the SVC object to JAXB object, server-side to client-side
     The single transforming
    */
    public RequestedItemsCount createRequestedItemsCount(
        org.extensiblecatalog.ncip.v2.service.RequestedItemsCount requestedItemsCount) throws BindingException {

        RequestedItemsCount jaxbRequestedItemsCount = null;

        if ( requestedItemsCount != null ) {

            jaxbRequestedItemsCount = new RequestedItemsCount();

            if ( requestedItemsCount.getCirculationStatus() != null ) {

                jaxbRequestedItemsCount.setCirculationStatus(
                    mainTranslator.convertSVP(requestedItemsCount.getCirculationStatus()));

            } else if ( requestedItemsCount.getItemUseRestrictionType() != null ) {

                jaxbRequestedItemsCount.setItemUseRestrictionType(
                    mainTranslator.convertSVP(requestedItemsCount.getItemUseRestrictionType()));

            } else if ( requestedItemsCount.getRequestType() != null ) {

                jaxbRequestedItemsCount.setRequestType(
                    mainTranslator.convertSVP(requestedItemsCount.getRequestType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either CirculationStatus, ItemUseRestrictionType or RequestType must be non-null and non-empty "
                        + "in RequestedItemsCount.");

            }

            jaxbRequestedItemsCount.setRequestedItemCountValue(requestedItemsCount.getRequestedItemCountValue());

        }

        return jaxbRequestedItemsCount;

    }


    /*
     RequestedItem list
    */
    public List<RequestedItem> createRequestedItems(
        List<org.extensiblecatalog.ncip.v2.service.RequestedItem> svcRequestedItems)
        throws BindingException {

        List<RequestedItem> jaxbRequestedItems = new ArrayList<RequestedItem>();

        if (svcRequestedItems != null && svcRequestedItems.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.RequestedItem svcRequestedItem : svcRequestedItems) {

                RequestedItem jaxbRequestedItem = createRequestedItem(svcRequestedItem);

                if (svcRequestedItem != null) {
                    jaxbRequestedItems.add(jaxbRequestedItem);
                }
            }
        }
        return jaxbRequestedItems;
    }

    /*
    RequestedItem
    */
    public RequestedItem createRequestedItem(org.extensiblecatalog.ncip.v2.service.RequestedItem requestedItem)
        throws BindingException {

        RequestedItem jaxbRequestedItem = null;

        if ( requestedItem != null ) {

            jaxbRequestedItem = new RequestedItem();

            if ( requestedItem.getRequestId() != null ) {

                jaxbRequestedItem.getContent().add(mainTranslator.createRequestId(requestedItem.getRequestId()));

                ItemId jaxbItemId = mainTranslator.createItemId(requestedItem.getItemId());

                if ( jaxbItemId != null ) {

                    jaxbRequestedItem.getContent().add(jaxbItemId);

                }


            } else if ( requestedItem.getItemId() != null ) {

                jaxbRequestedItem.getContent().add(mainTranslator.createItemId(requestedItem.getItemId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either RequestId or ItemId must be non-null and non-empty "
                        + "in RequestedItem.");

            }

            if ( requestedItem.getRequestType() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createRequestType(mainTranslator.convertSVP(requestedItem.getRequestType())));

            }

            if ( requestedItem.getRequestStatusType() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createRequestStatusType(mainTranslator.convertSVP(requestedItem.getRequestStatusType())));

            }

            if ( requestedItem.getDatePlaced() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createDatePlaced(JAXBTranslator.convertDate(requestedItem.getDatePlaced())));

            }

            if ( requestedItem.getPickupDate() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createPickupDate(JAXBTranslator.convertDate(requestedItem.getPickupDate())));

            }

            if ( requestedItem.getPickupLocation() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createPickupLocation(mainTranslator.convertSVP(requestedItem.getPickupLocation())));

            }

            if ( requestedItem.getPickupExpiryDate() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createPickupExpiryDate(JAXBTranslator.convertDate(requestedItem.getPickupExpiryDate())));

            }

            if ( requestedItem.getReminderLevel() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createReminderLevel(requestedItem.getReminderLevel()));

            }

            if ( requestedItem.getHoldQueuePosition() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createHoldQueuePosition(requestedItem.getHoldQueuePosition()));

            }

            if ( requestedItem.getTitle() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createTitle(requestedItem.getTitle()));

            }

            if ( requestedItem.getMediumType() != null ) {

                jaxbRequestedItem.getContent().add(
                    objectFactory.createMediumType(mainTranslator.convertSVP(requestedItem.getMediumType())));

            }

        }

        return jaxbRequestedItem;

    }

}
