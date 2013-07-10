/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.binding.BindingException;
import org.extensiblecatalog.ncip.v2.common.DefaultNCIP2TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LookupUserResponse;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserId;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;

public class TestMarshalling {

    private static final Logger LOG = Logger.getLogger(TestMarshalling.class);

    @Test
    public void testMarshalling() throws BindingException, JAXBException {

        UserId jaxbUserId = new UserId();
        jaxbUserId.setUserIdentifierValue("123123");

        LookupUserResponse jaxbLookupUserResponse = new LookupUserResponse();
        jaxbLookupUserResponse.setUserId(jaxbUserId);

        marshall(jaxbLookupUserResponse);

    }

    public static void marshall(Object obj) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance("org.extensiblecatalog.ncip.v2.binding.jaxb.elements",
            TestMarshalling.class.getClassLoader());

        Marshaller m;
        m = jc.createMarshaller();
        Schema schema = XMLHelper.loadSchema((new DefaultNCIP2TranslatorConfiguration()).getSchemaURLs());
        m.setSchema(schema);
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

        m.marshal(obj, byteArrayOutputStream);

    }
}
