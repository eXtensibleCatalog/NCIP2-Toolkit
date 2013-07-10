/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ncipv2_01.jaxb.dozer;

import org.dozer.DozerBeanMapper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.BaseJAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.binding.ncipv2_01.jaxb.elements.NCIPMessage;
import org.extensiblecatalog.ncip.v2.common.TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;

public class NCIPv2_01JAXBDozerTranslator extends BaseJAXBDozerTranslator<NCIPMessage> {

    /**
     * Default constructor, uses default configuration.
     */
    public NCIPv2_01JAXBDozerTranslator() throws ToolkitException {

        super();

    }

    /**
     * Constructor that uses provided {@link Properties} object for configuration values.
     */
    public NCIPv2_01JAXBDozerTranslator(Properties properties) throws ToolkitException {

        super(properties);

    }

    /**
     * Construct an instance using the supplied configuration object.
     * Note: This gets its {@link org.extensiblecatalog.ncip.v2.common.StatisticsBean} by calling {@link org.extensiblecatalog.ncip.v2.common.StatisticsBeanFactory#getSharedStatisticsBean()}.
     */
    public NCIPv2_01JAXBDozerTranslator(TranslatorConfiguration config) throws ToolkitException {

        super(config);
    }

    @Override
    protected NCIPMessage mapMessage(Object svcMsg, DozerBeanMapper mapper) {
        return mapper.map(svcMsg, NCIPMessage.class);
    }
}
