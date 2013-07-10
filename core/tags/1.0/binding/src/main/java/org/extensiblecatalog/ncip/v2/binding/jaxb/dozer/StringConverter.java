/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.util.MappingUtils;

import javax.xml.bind.JAXBElement;

public class StringConverter extends DozerConverter<JAXBElement, String> {

    public StringConverter() {
        super(JAXBElement.class, String.class);
    }

    @Override
    public String convertTo(JAXBElement srcObj, String targetBoolean) {

        String result = null;
        if ( srcObj != null ) {

            result = (String)srcObj.getValue();

        } else {

            // Do nothing - input object is null

        }

        return result;

    }

    @Override
    public JAXBElement convertFrom(String srcBoolean, JAXBElement targetObj) {

        JAXBElement result = null;

        MappingUtils.throwMappingException(
            "StringConverter.convertFrom() method entered - this should never be called.");


        return result;

    }


}
