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
import java.math.BigDecimal;

public class BigDecimalConverter extends DozerConverter<JAXBElement, BigDecimal> {

    public BigDecimalConverter() {
        super(JAXBElement.class, BigDecimal.class);
    }

    @Override
    public BigDecimal convertTo(JAXBElement srcObj, BigDecimal targetBoolean) {

        BigDecimal result = null;
        if ( srcObj != null ) {

            result = (BigDecimal)srcObj.getValue();

        } else {

            // Do nothing - input object is null

        }

        return result;

    }

    @Override
    public JAXBElement convertFrom(BigDecimal srcBoolean, JAXBElement targetObj) {

        throw new MappingException("BigDecimalConverter.convertFrom() method entered - this should never be called.");

    }


}
