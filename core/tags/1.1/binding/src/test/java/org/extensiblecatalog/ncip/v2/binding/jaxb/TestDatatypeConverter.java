/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import junit.framework.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TestDatatypeConverter {

    @Test
    public void noMilliseconds() throws DatatypeConfigurationException {

        GregorianCalendar wellKnownDate
            = (GregorianCalendar)GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        // This July 8, 1957 at 3:30 in the afternoon UTC (10:30 a.m. Eastern US time).
        wellKnownDate.set(1957, 6, 8, 15, 30, 0);
        wellKnownDate.set(Calendar.MILLISECOND, 0);

        final XMLGregorianCalendar calendar
            = DatatypeFactory.newInstance().newXMLGregorianCalendar(wellKnownDate);
        final String formattedDateString = DatatypeConverter.printDateTime(calendar);
        Assert.assertEquals("NCIP-formatted date without milliseconds", "1957-07-08T15:30:00Z", formattedDateString);

    }

    @Test
    public void withMilliseconds() throws DatatypeConfigurationException {

        GregorianCalendar wellKnownDate
            = (GregorianCalendar)GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        // This July 8, 1957 at 3:30 in the afternoon UTC (10:30 a.m. Eastern US time).
        wellKnownDate.set(1957, 6, 8, 15, 30, 0);
        wellKnownDate.set(Calendar.MILLISECOND, 17);

        final XMLGregorianCalendar calendar
            = DatatypeFactory.newInstance().newXMLGregorianCalendar(wellKnownDate);
        final String formattedDateString = DatatypeConverter.printDateTime(calendar);
        Assert.assertEquals("NCIP-formatted date with milliseconds", "1957-07-08T15:30:00.017Z", formattedDateString);
    }

    @Test
    public void truncationOfMilliseconds() throws DatatypeConfigurationException {

        final String inputDateTime = "1957-07-08T15:30:00.0178182Z";
        final XMLGregorianCalendar parsedDate = DatatypeConverter.parseDateTime(inputDateTime);
        final String formattedDateString = DatatypeConverter.printDateTime(parsedDate);
        // Ideally we'd test just the substring that is the sub-milliseconds; this may pass due to other differences.
        // But hopefully those other differences will show up in other unit-tests.
        assertThat("NCIP-formatted date with sub-milliseconds fraction should be truncated", formattedDateString, not(equalTo(inputDateTime)));

    }
}
