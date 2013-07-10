/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.MappingException;
import org.dozer.util.MappingUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class CalendarConverter extends DozerConverter<JAXBElement, GregorianCalendar> {

    public CalendarConverter() {
        super(JAXBElement.class, GregorianCalendar.class);
    }

    @Override
    public GregorianCalendar convertTo(JAXBElement srcObj, GregorianCalendar targetCalendar) {

        GregorianCalendar result = null;
        if ( srcObj != null ) {

            XMLGregorianCalendar calendar = (XMLGregorianCalendar)srcObj.getValue();
            result = calendar.toGregorianCalendar();

        } else {

            // Do nothing - input object is null

        }

        return result;

    }

    @Override
    public JAXBElement convertFrom(GregorianCalendar srcCalendar, JAXBElement targetObj) {

        throw new MappingException("CalendarConverter.convertFrom() method entered - this should never be called.");

    }


}
