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
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceHelper {

    private static final String INITIATION_DATA_SUFFIX = "InitiationData";
    private static final int LENGTH_OF_INITIATION_DATA_LITERAL = INITIATION_DATA_SUFFIX.length();
    private static final int LENGTH_OF_RESPONSE_DATA_LITERAL = "ResponseData".length();
    private static final int LENGTH_OF_DATA_LITERAL = "Data".length();
    protected static final Map<Class, Method> findMethodsByClass = new HashMap<Class, Method>();

    private ServiceHelper() {
        // Do not allow construction
    }

    public static List<Problem> createUnsupportedServiceProblems(NCIPData ncipData) {

        return generateProblems(Version1GeneralProcessingError.UNSUPPORTED_SERVICE, getServiceName(ncipData), null, null);

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

        List<Problem> problems = generateProblems(type, elementXPath, value, details
                + System.getProperties().get("line.separator") + convertExceptionToString(exception));

        return problems;
    }

    public static String convertExceptionToString(Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String getServiceName(NCIPData ncipData) {

        String serviceName;
        if ( ncipData != null ) {
            // Note that this calculation is *slightly* different than that in the getMessageName method -
            // this is stripping "InitiationData" or "ResponseData" to get the *service* name, which is the same for both the
            // initiation and response messages.
            int lengthOfSuffix
                    = ncipData instanceof NCIPInitiationData ? LENGTH_OF_INITIATION_DATA_LITERAL : LENGTH_OF_RESPONSE_DATA_LITERAL;
            String dataClassName = ncipData.getClass().getSimpleName();
            serviceName = dataClassName.substring(0, dataClassName.length() - lengthOfSuffix);
        } else {
            serviceName = "null";
        }

        return serviceName;

    }

    public static Class constructInitiationDataClass(String serviceName) throws ClassNotFoundException {

        String initDataClassName = NCIPService.class.getPackage().getName() + "." + serviceName + INITIATION_DATA_SUFFIX;
        return Class.forName(initDataClassName);

    }

    public static String getElementName(Class<?> svcClass) {

        String msgClassSimpleName = svcClass.getSimpleName();
        if (msgClassSimpleName.matches(".*InitiationData$")) {

            return msgClassSimpleName.substring(0, msgClassSimpleName.length() - LENGTH_OF_INITIATION_DATA_LITERAL);

        } else if (msgClassSimpleName.matches(".*ResponseData$")) {

            return msgClassSimpleName.substring(0, msgClassSimpleName.length() - LENGTH_OF_DATA_LITERAL);

        } else {

            return msgClassSimpleName;
        }

    }

    public static String getMessageName(NCIPData ncipData) {

        // Note that this calculation is *slightly* different than that in the getServiceName method -
        // this is stripping "InitiationData" or "Data" to get the *message* name, which is *not* the same for both the
        // initiation and response messages.
        int lengthOfSuffix
                = ncipData instanceof NCIPInitiationData ? LENGTH_OF_INITIATION_DATA_LITERAL : LENGTH_OF_DATA_LITERAL;
        String dataClassName = ncipData.getClass().getSimpleName();
        return dataClassName.substring(0, dataClassName.length() - lengthOfSuffix);

    }

    /**
     * Convert an integer currency amount (represented, sadly, as a BigDecimal object) to a 'decimalized' (really 'fixed point') form of a BigDecimal object.
     * @param amount the integer amount (e.g. $20.15 is represented as 2015; and the sum of 12.357 Bahraini Dinar is represented as 12357)
     * @param cc the {@link CurrencyCode} of the amount
     * @return
     */
    public static BigDecimal decimalize(BigDecimal amount, CurrencyCode cc) {

        return amount.movePointLeft(cc.getMinorUnit());

    }

    /**
     * Convert a fixed-point amount to an integer value (represented, sadly, as a BigDecimal object).
     * @param amount the fixed-point amount (e.g. $20.15 is represented as 20.15; and 12357 Bahraini Dinar is represented as 12.357)
     * @param cc the {@link CurrencyCode} of the amount
     * @return
     */
    public static BigDecimal undecimalize(BigDecimal amount, CurrencyCode cc) {
        return amount.movePointRight(cc.getMinorUnit());
    }

    public static String formatMonetaryAmount(BigDecimal amount, CurrencyCode cc) {

        return decimalize(amount, cc).toPlainString();

    }

    public static String formatSVP(SchemeValuePair svp) {

        return (svp == null ? "" : (svp.getScheme() == null ? "" : svp.getScheme() + ", ") + svp.getValue());

    }

    /**
     * Creates an {@link AgencyId} from the supplied string. If the string contains a '/' character
     * this will assume the characters following the last '/' are the {@link AgencyId#value} and
     * everything preceding the last '/' are the {@link AgencyId#scheme}. If there is no '/' it
     * will create an AgencyId with a null scheme and the value will be the entire supplied string.
     *
     * @param agencyIdString the string containing the value, and optionally the scheme, for the AgencyId.
     * @return
     */
    public static AgencyId createAgencyId(String agencyIdString) {

        AgencyId agencyId;
        int lastSlashOffset = agencyIdString.lastIndexOf('/');
        if (lastSlashOffset >= 0) {

            agencyId = new AgencyId(agencyIdString.substring(0, lastSlashOffset),
                    agencyIdString.substring(lastSlashOffset + 1));

        } else {

            agencyId = new AgencyId(agencyIdString);

        }

        return agencyId;

    }

    @SuppressWarnings(value={"unchecked"}) // Because Method.invoke returns Object.
    public static List<Problem> getProblems(NCIPResponseData responseData)
            throws InvocationTargetException, IllegalAccessException {

        List<Problem> problems = null;

        Method getProblemsMethod = ReflectionHelper.findMethod(responseData.getClass(), "getProblems");

        if (getProblemsMethod != null) {

            problems = (List<Problem>) getProblemsMethod.invoke(responseData);

        }

        return problems;


    }

    @SuppressWarnings(value={"unchecked"}) // Because Method.invoke returns Object.
    public static <SVCSVP extends SchemeValuePair> SVCSVP findSchemeValuePair(
            Class<? extends SchemeValuePair> svcSVPClass, String scheme, String value) throws ToolkitException {

        SVCSVP result;
        try {
            Method findMethod = getFindMethod(svcSVPClass);
            result = (SVCSVP) findMethod.invoke(null, scheme, value);
        } catch (IllegalAccessException e) {
            throw new ToolkitException(e);
        } catch (InvocationTargetException e) {
            throw new ToolkitException(e);
        }

        return result;

    }

    public static Method getFindMethod(Class<? extends SchemeValuePair> SVPClass) throws ToolkitException {
        Method findMethod = findMethodsByClass.get(SVPClass);
        if (findMethod == null) {
            try {
                findMethod = SVPClass.getMethod("find", String.class, String.class);
                findMethodsByClass.put(SVPClass, findMethod);
            } catch (NoSuchMethodException e) {
                throw new ToolkitException("Exception finding SchemeValuePair.find method:", e);
            }
        }
        return findMethod;
    }


}
