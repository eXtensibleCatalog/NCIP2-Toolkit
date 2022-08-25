/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class TestMonetaryValue {

    private BigDecimal moneyMoneyMoney = new BigDecimal(12345);

    /**
     * Test formatting of three example currencies. The currencies chosen are one example of every
     * different "minor unit" value (0, 2 and 3) that occurs in the NCIP {@link Version1CurrencyCode}.
     *
     */
    @Ignore
    @Test
    public void testFormatting() {

        CurrencyCode jpyCC = Version1CurrencyCode.JPY;
        String formattedJPY = ServiceHelper.formatMonetaryAmount(moneyMoneyMoney, jpyCC);
        Assert.assertEquals("12345", formattedJPY);

        NumberFormat jpyCurrencyNumberFormat = NumberFormat.getCurrencyInstance(Locale.JAPAN);
        String jpyFormattedWithSymbol = jpyCurrencyNumberFormat.format(ServiceHelper.decimalize(moneyMoneyMoney, jpyCC));
        // The \uFFE5 is the "full width Yen sign" in Unicode.
        Assert.assertEquals("\uFFE512,345", jpyFormattedWithSymbol);

        CurrencyCode usdCC = Version1CurrencyCode.USD;
        String formattedUSD = ServiceHelper.formatMonetaryAmount(moneyMoneyMoney, usdCC);
        Assert.assertEquals("123.45", formattedUSD);
        NumberFormat usdCurrencyNumberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String usdFormattedWithSymbol = usdCurrencyNumberFormat.format(ServiceHelper.decimalize(moneyMoneyMoney, usdCC));
        Assert.assertEquals("$123.45", usdFormattedWithSymbol);

        CurrencyCode bhdCC = Version1CurrencyCode.BHD;
        String formattedBHD = ServiceHelper.formatMonetaryAmount(moneyMoneyMoney, bhdCC);
        Assert.assertEquals("12.345", formattedBHD);

        NumberFormat bhdCurrencyNumberFormat = NumberFormat.getCurrencyInstance(new Locale("ar", "BH"));
        String bhdFormattedWithSymbol = bhdCurrencyNumberFormat.format(ServiceHelper.decimalize(moneyMoneyMoney, bhdCC));
        // The Unicode-escaped hex string is the format for a barhaini dinar as formatted for the Bahrain locale.
        Assert.assertEquals("\u062f\u002e\u0628\u002e\u200f 12.345", bhdFormattedWithSymbol);

        

    }

}
