/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1PaymentMethodType extends PaymentMethodType {

    private static final Logger LOG = Logger.getLogger(Version1PaymentMethodType.class);

    public static final String VERSION_1_PAYMENT_METHOD_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/paymentmethodtype/paymentmethodtype.scm";

    public static final Version1PaymentMethodType BANK_DRAFT
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Bank Draft");
    public static final Version1PaymentMethodType CASH
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Cash");
    public static final Version1PaymentMethodType CHECK
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Check");
    public static final Version1PaymentMethodType CREDIT_CARD
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Credit Card");
    public static final Version1PaymentMethodType DEBIT_CARD
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Debit Card");
    public static final Version1PaymentMethodType DEPOSIT_ACCOUNT
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Deposit Account");
    public static final Version1PaymentMethodType DIRECT_DEBIT
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Direct Debit");
    public static final Version1PaymentMethodType FUNDS_TRANSFER
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Funds Transfer");
    public static final Version1PaymentMethodType MONEY_ORDER
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Money Order");
    public static final Version1PaymentMethodType TRAVELERS_CHECK
        = new Version1PaymentMethodType(VERSION_1_PAYMENT_METHOD_TYPE, "Traveler's Check");

    public static void loadAll() {
        LOG.debug("Loading Version1PaymentMethodType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1PaymentMethodType(String scheme, String value) {
        super(scheme, value);
    }
}
