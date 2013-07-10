/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1ItemUseRestrictionType extends ItemUseRestrictionType {

    private static final Logger LOG = Logger.getLogger(Version1ItemUseRestrictionType.class);

    public static final String VERSION_1_ITEM_USE_RESTRICTION_TYPE
            = "http://www.niso.org/ncip/v1_0/imp1/schemes/itemuserestrictiontype/itemuserestrictiontype.scm";

    public static final Version1ItemUseRestrictionType AVAILABLE_FOR_SUPPLY_WITHOUT_RETURN = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Available For Supply Without Return"); // User is not required to return the Item as supplied.
    public static final Version1ItemUseRestrictionType IN_LIBRARY_USE_ONLY = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "In Library Use Only"); // Item is available for use only within the library.
    public static final Version1ItemUseRestrictionType LIMITED_CIRCULATION_LONG_LOAN_PERIOD = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Limited Circulation, Long Loan Period"); // Long loan period, determined by Agency User Privilege Type.
    public static final Version1ItemUseRestrictionType LIMITED_CIRCULATION_NORMAL_LOAN_PERIOD = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Limited Circulation, Normal Loan Period"); // Normal loan period, determined by Agency User Privilege Type.
    public static final Version1ItemUseRestrictionType LIMITED_CIRCULATION_SHORT_LOAN_PERIOD = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Limited Circulation, Short Loan Period"); // Short loan period, determined by Agency User Privilege Type.
    public static final Version1ItemUseRestrictionType NO_REPRODUCTION = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "No Reproduction"); // Reproduction of the Item by any means is prohibited.
    public static final Version1ItemUseRestrictionType NOT_FOR_LOAN = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Not For Loan"); // Item is not for loan.
    public static final Version1ItemUseRestrictionType OVERNIGHT_ONLY = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Overnight Only"); // Item is available for loan, but must be returned by a specific time the next day.
    public static final Version1ItemUseRestrictionType RENEWALS_NOT_PERMITTED = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Renewals Not Permitted"); // Loan period for the Item cannot be extended beyond the current date due of the loan.
    public static final Version1ItemUseRestrictionType SUPERVISION_REQUIRED = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Supervision Required"); // The Item may be used only with direct supervision of Agency staff.
    public static final Version1ItemUseRestrictionType TERM_LOAN = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Term Loan"); // Loan period for the Item is for the extent of time of an academic term of the Agency. The duration of the loan period varies according to the structure of the academic year in place at the Agency (e.g., quarter, semester, etc.).
    public static final Version1ItemUseRestrictionType USE_ONLY_IN_CONTROLLED_ACCESS = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "Use Only In Controlled Access"); // Item may be used only within a controlled facility, such as a rare book room or a reading room to which access is limited.
    public static final Version1ItemUseRestrictionType USER_SIGNATURE_REQUIRED = new Version1ItemUseRestrictionType(VERSION_1_ITEM_USE_RESTRICTION_TYPE, "User Signature Required"); //  The signature of the User is required for use of the Item.

    public static void loadAll() {
        LOG.debug("Loading Version1ItemUseRestrictionType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ItemUseRestrictionType(String scheme, String value) {
        super(scheme, value);
    }
}
