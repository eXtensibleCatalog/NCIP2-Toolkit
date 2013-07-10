/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.SortField;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLv1_0AccountDetailsElementType extends SortField {

    private static final Logger LOG = Logger.getLogger(WCLv1_0AccountDetailsElementType.class);

    private static final List<WCLv1_0AccountDetailsElementType> VALUES_LIST = new CopyOnWriteArrayList<WCLv1_0AccountDetailsElementType>();

    public WCLv1_0AccountDetailsElementType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLv1_0AccountDetailsElementType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLv1_0AccountDetailsElementType that matches, or null if none is found to match.
     */
    public static WCLv1_0AccountDetailsElementType find(String scheme, String value) throws ServiceException {
        return (WCLv1_0AccountDetailsElementType)find(scheme, value, VALUES_LIST, WCLv1_0AccountDetailsElementType.class);
    }

    public static final String VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE
        = "http://worldcat.org/ncip/schemes/v2/extensions/accountdetailselementtype.scm";

    /** This identifies the sub-element named "Accrual Date" in the AccountDetails element. */
    public static final WCLv1_0AccountDetailsElementType ACCRUAL_DATE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Accrual Date");
    /** This identifies the sub-element named "Fiscal Action Type" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType FISCAL_ACTION_TYPE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Fiscal Action Type");
    /** This identifies the sub-element named "Fiscal Transaction Type" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType FISCAL_TRANSACTION_TYPE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Fiscal Transaction Type");
    /** This identifies the sub-element named "Valid From Date" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType VALID_FROM_DATE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Valid From Date");
    /** This identifies the sub-element named "Valid To Date" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType VALID_TO_DATE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Valid To Date");
    /** This identifies the sub-element named "Amount" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType AMOUNT = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Amount");
    /** This identifies the sub-element named "Payment Method Type" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType PAYMENT_METHOD_TYPE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Payment Method Type");
    /** This identifies the sub-element named "Fiscal Transaction Description" in the AccountDetails/FiscalTransactionInformation element. */
    public static final WCLv1_0AccountDetailsElementType FISCAL_TRANSACTION_DESCRIPTION = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Fiscal Transaction Description");
    /** This identifies the sub-element named "Title" in the AccountDetails/FiscalTransactionInformation/ItemDetails/BibliographicDescription element. */
    public static final WCLv1_0AccountDetailsElementType TITLE = new WCLv1_0AccountDetailsElementType(VERSION_1_WCL_ACCOUNT_DETAILS_ELEMENT_TYPE, "Title");

    public static void loadAll() {
        LOG.debug("Loading WCLv1_0AccountDetailsElementType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
