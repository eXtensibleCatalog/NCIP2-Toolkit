/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatatypeConverter {

    private static final Logger LOG = Logger.getLogger(DatatypeConverter.class);

    // We are trusting that hasSameRules has no side-effects, i.e. is thread-safe.
    private final static TimeZone UTC_TIMEZONE = TimeZone.getTimeZone("Etc/UTC");

    private final static DatatypeFactory datatypeFactory;
    static {

        try {

            datatypeFactory = DatatypeFactory.newInstance();

        } catch (DatatypeConfigurationException e) {

            LOG.error("Exception creating a new instance of JAXBContext:", e);
            throw new ExceptionInInitializerError(e);

        }

    }

    // Pattern' Javadoc says it's thread-safe, but Matcher isn't.
    protected static Pattern timeHasExcessMillisecondsPattern 
        = Pattern.compile("(.*T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3})([0-9]+)((Z)?)$");

    public static XMLGregorianCalendar parseDateTime(String value) {

        // Note: To support greater precision than milliseconds we'd have to devise our own date/time class.
        // This simply drops the extra fractional digits so we can use Java's GregorianCalendar.
        if ( value != null ) {

            String originalValue = value;
            Matcher matcher = timeHasExcessMillisecondsPattern.matcher(value);
            if ( matcher.matches() ) {

                value = matcher.group(1) + matcher.group(3);
                if ( LOG.isDebugEnabled() && matcher.group(2).length() > 0 ) {

                    LOG.debug("Stripped sub-millsecond portion of time '" + originalValue
                        + "', leaving '" + value + "'.");

                }

            } // no need to fix this value

        }

        return datatypeFactory.newXMLGregorianCalendar(value);

    }

    public static String printDateTime(XMLGregorianCalendar calendar) {

        String result;
        TimeZone tz = calendar.getTimeZone(DatatypeConstants.FIELD_UNDEFINED);
        if ( tz.hasSameRules(UTC_TIMEZONE) ) {

            result = javax.xml.bind.DatatypeConverter.printDateTime(calendar.toGregorianCalendar());

        } else {

            // Convert to UTC and then print it
            Date utcDateTime = calendar.toGregorianCalendar().getTime();
            GregorianCalendar utcCalendar = (GregorianCalendar)GregorianCalendar.getInstance(TimeZone.getTimeZone("Etc/UTC"));
            utcCalendar.setTime(utcDateTime);

            result = javax.xml.bind.DatatypeConverter.printDateTime(utcCalendar);

        }

        return result;

    }
}
