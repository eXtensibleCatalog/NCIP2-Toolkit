/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.dozer;

import org.dozer.DozerBeanMapper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.BaseJAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.common.LoggingHelper;
import org.extensiblecatalog.ncip.v2.common.StatisticsBean;
import org.extensiblecatalog.ncip.v2.common.TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.ilsdiv1_1.ILSDIv1_1_NCIPMessage;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ILSDIv1_1_JAXBDozerTranslator extends BaseJAXBDozerTranslator<org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.NCIPMessage> {

    /**
     * Default constructor, uses default configuration.
     */
    public ILSDIv1_1_JAXBDozerTranslator() throws ToolkitException {

        super();

    }

    /**
     * Constructor that uses provided {@link Properties} object for configuration values.
     */
    public ILSDIv1_1_JAXBDozerTranslator(Properties properties) throws ToolkitException {

        super(properties);

    }

    /**
     * Construct an instance using the supplied configuration object.
     * Note: This gets its {@link org.extensiblecatalog.ncip.v2.common.StatisticsBean} by calling {@link org.extensiblecatalog.ncip.v2.common.StatisticsBeanFactory#getSharedStatisticsBean()}.
     */
    public ILSDIv1_1_JAXBDozerTranslator(TranslatorConfiguration config) throws ToolkitException {

        super(config);
    }

    @Override
    protected org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.NCIPMessage mapMessage(Object svcMsg, DozerBeanMapper mapper) {
        return mapper.map(svcMsg, org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.NCIPMessage.class);
    }
    
    @Override
    public NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException {

        try {

        	org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.NCIPMessage initiationMsg = createNCIPMessage(serviceContext, inputStream);

            String msgName = JAXBHelper.getMessageName(initiationMsg);

            ILSDIv1_1_NCIPMessage svcMessage = mapper.map(initiationMsg, ILSDIv1_1_NCIPMessage.class);

            serviceContext.validateAfterUnmarshalling(svcMessage);

            NCIPInitiationData initiationData = svcMessage.getInitiationData();

            return initiationData;

        } catch (IllegalAccessException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the initiation message.", e);

        } catch (InvocationTargetException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the initiation message.", e);

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPInitiationData object from the input stream.", e);
        }

    }
    
}
