/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.extensiblecatalog.ncip.v2.common.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class NCIPHelper {

    private NCIPHelper() {

        // Utility classes should not be constructed.

    }

    /**
     * Builds a Problem object for an error that indicates the problem type, the problem element, and some detailed
     * information about the problem.
     *
     * @param type         the SchemeValuePair that identifies the problem; must not be null
     * @param elementXPath an XPath expression pointing to the problem element in the initiation message; may be null
     * @param value        the content of the element that is a problem
     * @param details      textual information about the problem; may be null
     * @return Problem
     */
    public static List<Problem> generateProblem(ProblemType type,
                                         String elementXPath,
                                         String value,
                                         String details) {

        List<Problem> problems =
            new ArrayList<Problem>();
        Problem problem = new Problem();

        problem.setProblemType(type);

        if (elementXPath != null) {
            problem.setProblemElement(elementXPath);
        }

        if (details != null) {
            problem.setProblemDetail(details);
        }

        if (value != null) {
            problem.setProblemValue(value);
        }

        problems.add(problem);

        return problems;
    }


    public static List<Problem> getProblems(NCIPResponseData responseData)
        throws InvocationTargetException, IllegalAccessException {

        List<Problem> problems = null;

        Method getProblemsMethod = ReflectionHelper.findMethod(responseData.getClass(), "getProblems");

        if ( getProblemsMethod != null ) {

            problems = (List<Problem>)getProblemsMethod.invoke(responseData);

        }

        return problems;


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
