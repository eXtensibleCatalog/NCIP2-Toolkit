/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class NCIPHelper {

    private NCIPHelper() {

        // Utility classes should not be constructed.

    }

    public static BigDecimal decimalize(BigDecimal amount, CurrencyCode cc) {

        return amount.movePointLeft(cc.getMinorUnit());

    }

    public static String formatMonetaryAmount(BigDecimal amount, CurrencyCode cc) {

        return decimalize(amount, cc).toPlainString();

    }

    public static String formatSVP(SchemeValuePair svp) {

        return (svp == null ? "" : (svp.getScheme() == null ? "" : svp.getScheme() + ", ") + svp.getValue());  

    }
}
