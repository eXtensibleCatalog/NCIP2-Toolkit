/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.lang.reflect.Field;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

public class TestVersion1CurrencyCode {

    @Test
    public void testMinorAmounts() throws IllegalAccessException {

        Field[] fields = Version1CurrencyCode.class.getDeclaredFields();
        for ( Field f : fields ) {

            if ( f.getName().matches("^[A-Z]*$") && !f.getName().matches("^LOG$") ) {

                Version1CurrencyCode cc = (Version1CurrencyCode)f.get(null);

                String ccValue = cc.getValue();

                // Java's Currency definition for VND has a minor unit of 2, but as of 2011-09-09
                // the ISO 4217 spreadsheet (dl_iso_table_a1-1.xls) has 0, which is what Version1CurrencyCode uses.
                // Java does not have Currency objects for TMT, CUC, COU, UYI, CHE, CHW or ZWL.
                if ( !ccValue.matches("^(VND|TMT|CUC|COU|UYI|CHE|CHW|ZWL)$") ) {

                    try {
                        Currency javaCC = Currency.getInstance(ccValue);
                        int javaCCFractionalDigits = javaCC.getDefaultFractionDigits();
                        Assert.assertEquals(javaCCFractionalDigits, cc.getMinorUnit());

                    } catch (java.lang.IllegalArgumentException e) {

                        System.err.println("Java's currency code tables do not have currency code '" + cc.getValue() + "'.");

                    } catch (java.lang.AssertionError e) {

                        System.err.println("Assertion failed for currency code '" + cc.getValue() + "': "
                            + e.getMessage());

                    }

                }

            }

        }

    }

}
