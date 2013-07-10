/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ServiceHelper {

    public static List<Problem> createUnsupportedServiceProblemList(String serviceName) {

        return generateProblems(Version1GeneralProcessingError.UNSUPPORTED_SERVICE, serviceName, null, null);

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
    public static List<Problem> generateProblems(ProblemType type,
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

    public static List<Problem> generateProblems(ProblemType type, String elementXPath, String value, String details,
                                                 Throwable exception) {

        String exceptionString = convertExceptionToString(exception);
        List<Problem> problems = generateProblems(type, elementXPath, value, details
            + System.getProperties().get("line.separator") + exceptionString);

        return problems;
    }

    public static String convertExceptionToString(Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
