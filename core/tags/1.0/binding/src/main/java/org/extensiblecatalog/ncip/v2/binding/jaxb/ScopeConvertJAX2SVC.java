/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.extensiblecatalog.ncip.v2.binding.BindingError;
import org.extensiblecatalog.ncip.v2.binding.BindingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.*;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/*
   Transit object or list of objects which is in JAXB scope to SVC scope
 */
public class ScopeConvertJAX2SVC {

    protected JAXBTranslator mainTranslator;

    protected final ObjectFactory objectFactory = new ObjectFactory();

    public ScopeConvertJAX2SVC(JAXBTranslator mainTranslator) {
        this.mainTranslator = mainTranslator;
    }


    /*
     UserFiscalAccount list
    */
    public List<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount>
    createUserFiscalAccounts(List<UserFiscalAccount> jaxbUserFiscalAccounts) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount> userFiscalAccounts
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount>();

        for (UserFiscalAccount jaxbUserFiscalAccount : jaxbUserFiscalAccounts) {
            userFiscalAccounts.add(createUserFiscalAccount(jaxbUserFiscalAccount));
        }
        return userFiscalAccounts;
    }

    /*
     UserFiscalAccount
    */
    public org.extensiblecatalog.ncip.v2.service.UserFiscalAccount createUserFiscalAccount(
        UserFiscalAccount jaxbUserFiscalAccount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserFiscalAccount userFiscalAccount = null;

        if ( jaxbUserFiscalAccount != null ) {

            userFiscalAccount = new org.extensiblecatalog.ncip.v2.service.UserFiscalAccount();

            userFiscalAccount.setAccountBalance(createAccountBalance(jaxbUserFiscalAccount.getAccountBalance()));

            if ( jaxbUserFiscalAccount.getAccountDetails() != null
                && jaxbUserFiscalAccount.getAccountDetails().size() > 0 ) {

                userFiscalAccount.setAccountDetails(createAccountDetailsList(jaxbUserFiscalAccount.getAccountDetails()));
            }

        }

        return userFiscalAccount;
    }

    public org.extensiblecatalog.ncip.v2.service.AccountBalance createAccountBalance(AccountBalance jaxbAccountBalance)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AccountBalance svcAccountBalance = null;

        if ( jaxbAccountBalance != null ) {

            svcAccountBalance = new org.extensiblecatalog.ncip.v2.service.AccountBalance();

            if ( jaxbAccountBalance.getCurrencyCode() != null ) {

                try {

                    svcAccountBalance.setCurrencyCode(org.extensiblecatalog.ncip.v2.service.CurrencyCode.find(
                        jaxbAccountBalance.getCurrencyCode().getScheme(),
                        jaxbAccountBalance.getCurrencyCode().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null and non-empty "
                        + "in AccountBalance.");

            }

            svcAccountBalance.setMonetaryValue(jaxbAccountBalance.getMonetaryValue());

        }

        return svcAccountBalance;

    }

    /*
     AccountDetails list
    */
    public List<org.extensiblecatalog.ncip.v2.service.AccountDetails> createAccountDetailsList(
        List<AccountDetails> jaxbAccountDetailsList)
        throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.AccountDetails> svcAccountDetailsList
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.AccountDetails>();

        if (jaxbAccountDetailsList != null && jaxbAccountDetailsList.size() > 0) {

            for (AccountDetails jaxbAccountDetails : jaxbAccountDetailsList) {

                org.extensiblecatalog.ncip.v2.service.AccountDetails svcAccountDetails
                    = createAccountDetails(jaxbAccountDetails);

                if (svcAccountDetails != null) {

                    svcAccountDetailsList.add(svcAccountDetails);
                }
            }
        }
        return svcAccountDetailsList;
    }


    public org.extensiblecatalog.ncip.v2.service.AccountDetails createAccountDetails(AccountDetails jaxbAccountDetails)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AccountDetails svcAccountDetails = null;

        if ( jaxbAccountDetails != null ) {

            svcAccountDetails = new org.extensiblecatalog.ncip.v2.service.AccountDetails();

            svcAccountDetails.setAccrualDate(mainTranslator.convertDate(jaxbAccountDetails.getAccrualDate()));

            svcAccountDetails.setFiscalTransactionInformation(
                mainTranslator.createFiscalTransactionInformation(jaxbAccountDetails.getFiscalTransactionInformation()));
        }

        return svcAccountDetails;

    }

    /*
     LoanedItemsCount list
    */
    public List<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount>
    createLoanedItemsCounts(List<LoanedItemsCount> jaxbLoanedItemsCounts) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount> loanedItemsCounts
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount>();

        for (LoanedItemsCount jaxbLoanedItemsCount : jaxbLoanedItemsCounts) {
            loanedItemsCounts.add(createLoanedItemsCount(jaxbLoanedItemsCount));
        }
        return loanedItemsCounts;
    }

    /*
     LoanedItemsCount
    */
    public org.extensiblecatalog.ncip.v2.service.LoanedItemsCount createLoanedItemsCount(
        LoanedItemsCount jaxbLoanedItemsCount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LoanedItemsCount loanedItemsCount = null;

        if ( jaxbLoanedItemsCount != null ) {

            loanedItemsCount = new org.extensiblecatalog.ncip.v2.service.LoanedItemsCount();

            if ( jaxbLoanedItemsCount.getCirculationStatus() != null ) {

                try {

                    loanedItemsCount.setCirculationStatus(org.extensiblecatalog.ncip.v2.service.CirculationStatus.find(
                        jaxbLoanedItemsCount.getCirculationStatus().getScheme(),
                        jaxbLoanedItemsCount.getCirculationStatus().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else if ( jaxbLoanedItemsCount.getItemUseRestrictionType() != null ) {

                try {

                    loanedItemsCount.setItemUseRestrictionType(org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType.find(
                        jaxbLoanedItemsCount.getItemUseRestrictionType().getScheme(),
                        jaxbLoanedItemsCount.getItemUseRestrictionType().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either CirculationStatus or ItemUseRestrictionType must be non-null and non-empty "
                        + "in LoanedItemsCount.");

            }

            loanedItemsCount.setLoanedItemCountValue(jaxbLoanedItemsCount.getLoanedItemCountValue());

        }


        return loanedItemsCount;
    }

    /*
     LoanedItem list
    */
    public List<org.extensiblecatalog.ncip.v2.service.LoanedItem>
    createLoanedItems(List<LoanedItem> jaxbLoanedItems) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.LoanedItem> loanedItems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.LoanedItem>();

        for (LoanedItem jaxbLoanedItem : jaxbLoanedItems) {
            loanedItems.add(createLoanedItem(jaxbLoanedItem));
        }
        return loanedItems;
    }

    /*
     LoanedItem
    */
    public org.extensiblecatalog.ncip.v2.service.LoanedItem createLoanedItem(LoanedItem jaxbLoanedItem)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LoanedItem loanedItem = null;

        if ( jaxbLoanedItem != null ) {

            loanedItem = new org.extensiblecatalog.ncip.v2.service.LoanedItem();

            loanedItem.setItemId(mainTranslator.createItemId(jaxbLoanedItem.getItemId()));

            loanedItem.setReminderLevel(jaxbLoanedItem.getReminderLevel());

            if ( jaxbLoanedItem.getDateDue() != null ) {

                loanedItem.setDateDue(mainTranslator.convertDate(jaxbLoanedItem.getDateDue()));

            } else if ( jaxbLoanedItem.getIndeterminateLoanPeriodFlag() != null ) {

                loanedItem.setIndeterminateLoanPeriodFlag(true);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either DateDue or IndeterminateLoanPeriodFlag must be non-null and non-empty "
                        + "in LoanedItem.");

            }

            loanedItem.setAmount(mainTranslator.createAmount(jaxbLoanedItem.getAmount()));

            loanedItem.setTitle(jaxbLoanedItem.getTitle());

            if ( jaxbLoanedItem.getMediumType() != null ) {

                try {

                    loanedItem.setMediumType(org.extensiblecatalog.ncip.v2.service.MediumType.find(
                        jaxbLoanedItem.getMediumType().getScheme(),
                        jaxbLoanedItem.getMediumType().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        return loanedItem;
    }

    /*
     RequestedItemsCount list
    */
    public List<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount>
    createRequestedItemsCounts(List<RequestedItemsCount> jaxbRequestedItemsCounts) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount> requestedItemsCounts
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount>();

        for (RequestedItemsCount jaxbRequestedItemsCount : jaxbRequestedItemsCounts) {
            requestedItemsCounts.add(createRequestedItemsCount(jaxbRequestedItemsCount));
        }
        return requestedItemsCounts;
    }

    /*
     RequestedItemsCount
    */
    public org.extensiblecatalog.ncip.v2.service.RequestedItemsCount createRequestedItemsCount(
        RequestedItemsCount jaxbRequestedItemsCount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestedItemsCount requestedItemsCount = null;

        if ( jaxbRequestedItemsCount != null ) {

            requestedItemsCount = new org.extensiblecatalog.ncip.v2.service.RequestedItemsCount();

            if ( jaxbRequestedItemsCount.getRequestType() != null ) {

                try {

                    requestedItemsCount.setRequestType(org.extensiblecatalog.ncip.v2.service.RequestType.find(
                        jaxbRequestedItemsCount.getRequestType().getScheme(),
                        jaxbRequestedItemsCount.getRequestType().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else if ( jaxbRequestedItemsCount.getCirculationStatus() != null ) {

                try {

                    requestedItemsCount.setCirculationStatus(org.extensiblecatalog.ncip.v2.service.CirculationStatus.find(
                        jaxbRequestedItemsCount.getCirculationStatus().getScheme(),
                        jaxbRequestedItemsCount.getCirculationStatus().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else if ( jaxbRequestedItemsCount.getItemUseRestrictionType() != null ) {

                try {

                    requestedItemsCount.setItemUseRestrictionType(org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType.find(
                        jaxbRequestedItemsCount.getItemUseRestrictionType().getScheme(),
                        jaxbRequestedItemsCount.getItemUseRestrictionType().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either RequestType, CirculationStatus or ItemUseRestrictionType must be non-null and non-empty "
                        + "in RequestedItemsCount.");

            }

            requestedItemsCount.setRequestedItemCountValue(jaxbRequestedItemsCount.getRequestedItemCountValue());

        }

        return requestedItemsCount;

    }

    /*
     RequestedItem list
    */
    public List<org.extensiblecatalog.ncip.v2.service.RequestedItem>
    createRequestedItems(List<RequestedItem> jaxbRequestedItems) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestedItem> requestedItems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestedItem>();

        for (RequestedItem jaxbRequestedItem : jaxbRequestedItems) {
            requestedItems.add(createRequestedItem(jaxbRequestedItem));
        }
        return requestedItems;
    }

    /*
     RequestedItem
    */
    public org.extensiblecatalog.ncip.v2.service.RequestedItem createRequestedItem(
        RequestedItem jaxbRequestedItem) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestedItem requestedItem = null;

        if ( jaxbRequestedItem != null ) {

            requestedItem = new org.extensiblecatalog.ncip.v2.service.RequestedItem();

            org.extensiblecatalog.ncip.v2.service.ItemId itemId = null;
            org.extensiblecatalog.ncip.v2.service.RequestId requestId = null;
            org.extensiblecatalog.ncip.v2.service.RequestType requestType = null;
            org.extensiblecatalog.ncip.v2.service.RequestStatusType requestStatusType = null;
            GregorianCalendar datePlaced = null;
            GregorianCalendar pickupDate = null;
            org.extensiblecatalog.ncip.v2.service.PickupLocation pickupLocation = null;
            GregorianCalendar pickupExpiryDate = null;
            BigDecimal reminderLevel = null;
            BigDecimal holdQueuePosition = null;
            String title = null;
            org.extensiblecatalog.ncip.v2.service.MediumType mediumType = null;


            for (Object object : jaxbRequestedItem.getContent()) {

                if (object instanceof ItemId) {

                    itemId = mainTranslator.createItemId((ItemId)object);

                } else if ( object instanceof RequestId) {

                    requestId = mainTranslator.createRequestId((RequestId)object);

                } else if (object instanceof JAXBElement) {

                    JAXBElement jaxbElement = (JAXBElement)object;

                    if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestType") == 0) {

                        SchemeValuePair svp = (SchemeValuePair)jaxbElement.getValue();
                        try {

                            requestType = org.extensiblecatalog.ncip.v2.service.RequestType.find(
                                svp.getScheme(), svp.getValue());

                        } catch (ServiceException e) {

                            throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                        }

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestStatusType") == 0) {

                        SchemeValuePair svp = (SchemeValuePair)jaxbElement.getValue();
                        try {

                            requestStatusType = org.extensiblecatalog.ncip.v2.service.RequestStatusType.find(
                                svp.getScheme(), svp.getValue());

                        } catch (ServiceException e) {

                            throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                        }

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DatePlaced") == 0) {

                        datePlaced = mainTranslator.convertDate((XMLGregorianCalendar)jaxbElement.getValue());

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupDate") == 0) {

                        pickupDate = mainTranslator.convertDate((XMLGregorianCalendar)jaxbElement.getValue());

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupLocation") == 0) {

                        SchemeValuePair svp = (SchemeValuePair)jaxbElement.getValue();
                        try {

                            pickupLocation = org.extensiblecatalog.ncip.v2.service.PickupLocation.find(
                                svp.getScheme(), svp.getValue());

                        } catch (ServiceException e) {

                            throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                        }

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupExpiryDate") == 0) {

                        pickupExpiryDate = mainTranslator.convertDate((XMLGregorianCalendar)jaxbElement.getValue());

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("ReminderLevel") == 0) {

                        reminderLevel = (BigDecimal)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("HoldQueuePosition") == 0) {

                        holdQueuePosition = (BigDecimal)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Title") == 0) {

                        title = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("MediumType") == 0) {

                        SchemeValuePair svp = (SchemeValuePair)jaxbElement.getValue();
                        try {

                            mediumType = org.extensiblecatalog.ncip.v2.service.MediumType.find(
                                svp.getScheme(), svp.getValue());

                        } catch (ServiceException e) {

                            throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                        }

                    } else {

                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                            + jaxbElement.getName().getLocalPart() + " in RequestedItem.");

                    }
                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object of class "
                        + object.getClass().getName() + " in RequestedItem.");

                }
            }

            if ( requestId != null ) {

                requestedItem.setRequestId(requestId);

                if ( itemId != null ) {

                    requestedItem.setItemId(itemId);

                }

            } else if ( itemId != null ) {

                requestedItem.setItemId(itemId);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either RequestId or ItemId must be non-null and non-empty "
                        + "in RequestedItem.");

            }

            requestedItem.setRequestType(requestType);

            requestedItem.setRequestStatusType(requestStatusType);

            requestedItem.setDatePlaced(datePlaced);

            requestedItem.setPickupDate(pickupDate);

            requestedItem.setPickupLocation(pickupLocation);

            requestedItem.setPickupExpiryDate(pickupExpiryDate);

            requestedItem.setReminderLevel(reminderLevel);

            requestedItem.setHoldQueuePosition(holdQueuePosition);

            requestedItem.setTitle(title);

            requestedItem.setMediumType(mediumType);

        }
        
        return requestedItem;
    }

}
