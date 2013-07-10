/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1FiscalTransactionType extends FiscalTransactionType {

    private static final Logger LOG = Logger.getLogger(Version1FiscalTransactionType.class);

    public static final String VERSION_1_FISCAL_TRANSACTION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/fiscaltransactiontype/fiscaltransactiontype.scm";

    public static final Version1FiscalTransactionType BOOK_REPLACEMENT_CHARGE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Book Replacement Charge");
    public static final Version1FiscalTransactionType CARD_REPLACEMENT_CHARGE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Card Replacement Charge");
    public static final Version1FiscalTransactionType CATALOG_SEARCH
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Catalog Search");
    public static final Version1FiscalTransactionType DAY_PASS
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Day Pass");
    public static final Version1FiscalTransactionType FINE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Fine");
    public static final Version1FiscalTransactionType INTERLIBRARY_LOAN_FEE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Interlibrary Loan Fee");
    public static final Version1FiscalTransactionType PURCHASE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Purchase");
    public static final Version1FiscalTransactionType REMINDER_CHARGE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Reminder Charge");
    public static final Version1FiscalTransactionType RENEWAL_FEE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Renewal Fee");
    public static final Version1FiscalTransactionType RENTAL
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Rental");
    public static final Version1FiscalTransactionType RESERVATION_CHARGE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Reservation Charge");
    public static final Version1FiscalTransactionType SERVICE_CHARGE
        = new Version1FiscalTransactionType(VERSION_1_FISCAL_TRANSACTION_TYPE, "Service Charge");


    public static void loadAll() {
        LOG.debug("Loading Version1FiscalTransactionType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1FiscalTransactionType(String scheme, String value) {
        super(scheme, value);
    }
}
