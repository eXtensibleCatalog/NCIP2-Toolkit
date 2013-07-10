/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.extensiblecatalog.ncip.v2.binding.BindingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.JAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LookupUserResponse;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserId;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean;
import org.extensiblecatalog.ncip.v2.service.Translator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;

public class TestMarshalling {

    private static final Logger LOG = Logger.getLogger(TestMarshalling.class);

    protected static final String LOG4J_CONFIG_FILENAME = "src/test/resources/log4j.properties";
    protected static final String APP_CONFIG_FILENAME = "src/test/resources/testconfig.xml";
    protected static final ApplicationContext appContext;
    /**
     * The {@link org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean} instance used to report performance data.
     */
    protected static final ToolkitStatisticsBean statisticsBean;

    static {

        PropertyConfigurator.configure(LOG4J_CONFIG_FILENAME);
        appContext = new FileSystemXmlApplicationContext(new String[] { APP_CONFIG_FILENAME });

        if ( appContext != null ) {

            LOG.debug("Loaded application context from '" + APP_CONFIG_FILENAME + "'.");
            statisticsBean = (ToolkitStatisticsBean)appContext.getBean("toolkitStatistics");

        } else {

            LOG.debug("No application context at '" + APP_CONFIG_FILENAME + "'; using defaults.");
            statisticsBean = new ToolkitStatisticsBean();

        }

    }

    protected static Schema schema = XMLHelper.loadSchema(NCIPConfiguration.getInstance().getSchemaURLs());

    @Test
    public void testMarshalling() throws BindingException, JAXBException {

        UserId jaxbUserId = new UserId();
        jaxbUserId.setUserIdentifierValue("123123");

        LookupUserResponse jaxbLookupUserResponse = new LookupUserResponse();
        jaxbLookupUserResponse.setUserId(jaxbUserId);

        marshall(jaxbLookupUserResponse);

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);
    }

    public static void marshall(Object obj) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance("org.extensiblecatalog.ncip.v2.binding.jaxb.elements",
            TestMarshalling.class.getClassLoader());

        Marshaller m;
        m = jc.createMarshaller();
        if (schema != null) {
            m.setSchema(schema);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

        m.marshal(obj, byteArrayOutputStream);

    }
}
