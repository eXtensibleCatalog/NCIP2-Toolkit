/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.MarshallerFactory;

import javax.xml.bind.*;
import javax.xml.transform.sax.SAXSource;

import org.extensiblecatalog.ncip.v2.binding.jaxb.NamespaceFilter;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.common.StatisticsBean;
import org.extensiblecatalog.ncip.v2.service.ValidationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class JAXBDozerTranslator implements Translator {

    private static final Logger LOG = Logger.getLogger(JAXBDozerTranslator.class);

    protected final DozerBeanMapper mapper = new DozerBeanMapper();

    protected final MarshallerFactory marshallerFactory;

    /**
     * The {@link StatisticsBean} instance used to report performance data.
     */
    protected StatisticsBean statisticsBean;

    /** Whether or not to log initiation and response messages. */
    protected boolean logMessages = false;
    /** If logging the messages, use this logging level. */
    protected Level messagesLoggingLevel = Level.DEBUG;

    /**
     * Default constructor, uses default configuration.
     * @throws ToolkitException if the translator can not be constructed
     */
    public JAXBDozerTranslator() throws ToolkitException {

        this(TranslatorConfigurationFactory.getConfiguration());

    }

    /**
     * Construct an instance using the supplied configuration object.
     * Note: This gets its {@link StatisticsBean} by calling {@link StatisticsBeanFactory#getSharedStatisticsBean()}.
     */
    public JAXBDozerTranslator(TranslatorConfiguration config) throws ToolkitException {

        DozerTranslatorConfiguration dozerConfig = (DozerTranslatorConfiguration)config;

        // TODO: Each non-Core configuration object should have a "getCoreConfiguration" method
        // So we don't have to do go "outside" of the config object to get shared objects.
        this.statisticsBean = StatisticsBeanFactory.getSharedStatisticsBean();

        this.logMessages = config.getLogMessages();

        this.messagesLoggingLevel = config.getMessagesLoggingLevel();

        this.marshallerFactory = new MarshallerFactory(config);

        this.mapper.setMappingFiles(dozerConfig.getMappingFiles());

    }

    /**
     * Constructor using the given MarshallerFactory and StatisticsBean.
     * @param statisticsBean
     */
    public JAXBDozerTranslator(MarshallerFactory marshallerFactory, StatisticsBean statisticsBean,
                               List<String> mappingFiles)
        throws ToolkitException {

        this.statisticsBean = statisticsBean;
        this.marshallerFactory = marshallerFactory;
        this.mapper.setMappingFiles(mappingFiles);

    }

    /**
     * Constructor using the default StatisticsBean but the given MarshallerFactory.
     */
    public JAXBDozerTranslator(MarshallerFactory marshallerFactory, List<String> mappingFiles) throws ToolkitException {

        this.statisticsBean = StatisticsBeanFactory.getSharedStatisticsBean();
        this.marshallerFactory = marshallerFactory;
        this.mapper.setMappingFiles(mappingFiles);

    }

    @Override
    public NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException {

        try {

            if (logMessages) {

                inputStream = LoggingHelper.copyAndLogStream(LOG, messagesLoggingLevel, inputStream);

            }

            long initUnmarshalStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage initiationMsg
                = createNCIPMessage(serviceContext, inputStream);

            String msgName = JAXBHelper.getMessageName(initiationMsg);

            long initUnmarshalEndTime = System.currentTimeMillis();

            statisticsBean.record(initUnmarshalStartTime, initUnmarshalEndTime,
                StatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);

            long initTranslateStartTime = System.currentTimeMillis();

            NCIPMessage svcMessage = mapper.map(initiationMsg, NCIPMessage.class);

            long initTranslateEndTime = System.currentTimeMillis();

            serviceContext.validateAfterUnmarshalling(svcMessage);

            NCIPInitiationData initiationData = ReflectionHelper.getInitiationData(svcMessage);

            statisticsBean.record(initTranslateStartTime, initTranslateEndTime,
                StatisticsBean.RESPONDER_CREATE_DATA_LABELS, msgName);

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

    @Override
    public NCIPResponseData createResponseData(ServiceContext serviceContext, InputStream responseMsgInputStream)
        throws ServiceException, ValidationException {

        try {

            if (logMessages) {

                responseMsgInputStream = LoggingHelper.copyAndLogStream(LOG, messagesLoggingLevel, responseMsgInputStream);

            }

            long respUnmarshalStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage responseMsg
                = createNCIPMessage(serviceContext, responseMsgInputStream);

            String msgName = JAXBHelper.getMessageName(responseMsg);

            long respUnmarshalEndTime = System.currentTimeMillis();

            statisticsBean.record(respUnmarshalStartTime, respUnmarshalEndTime,
                StatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);


            long respTranslateStartTime = System.currentTimeMillis();

            NCIPMessage svcMessage = mapper.map(responseMsg, NCIPMessage.class);

            long respTranslateEndTime = System.currentTimeMillis();

            serviceContext.validateAfterUnmarshalling(svcMessage);

            NCIPResponseData responseData = ReflectionHelper.getResponseData(svcMessage);

            statisticsBean.record(respTranslateStartTime, respTranslateEndTime,
                StatisticsBean.RESPONDER_CREATE_DATA_LABELS, msgName);

            return responseData;

        } catch (IllegalAccessException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the response message.", e);

        } catch (InvocationTargetException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the response message.", e);

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPResponseData object from the input stream.", e);

        }

    }

    @Override
    public ByteArrayInputStream createInitiationMessageStream(
        ServiceContext serviceContext, NCIPInitiationData initiationData)
        throws ServiceException, ValidationException {

        try {

            String msgName = JAXBHelper.getMessageName(initiationData);

            // Create a service.NCIPMessage object and put the initiationData object in it
            NCIPMessage svcNCIPMessage = new NCIPMessage();
            ReflectionHelper.setField(svcNCIPMessage, initiationData, msgName);

            serviceContext.validateBeforeMarshalling(svcNCIPMessage);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long initTranslateStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage ncipMsg = mapper.map(svcNCIPMessage,
                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage.class);

            long initTranslateEndTime = System.currentTimeMillis();
            statisticsBean.record(initTranslateStartTime, initTranslateEndTime,
                StatisticsBean.RESPONDER_CREATE_MESSAGE_LABELS, msgName);


            long initMarshalStartTime = System.currentTimeMillis();

            ByteArrayInputStream initMsgStream = createMsgStream(serviceContext, ncipMsg);

            long initMarshalEndTime = System.currentTimeMillis();
            statisticsBean.record(initMarshalStartTime, initMarshalEndTime,
                StatisticsBean.RESPONDER_MARSHAL_MESSAGE_LABELS, msgName);

            if (logMessages) {

                initMsgStream = LoggingHelper.copyAndLogStream(LOG, messagesLoggingLevel, initMsgStream);

            }

            return initMsgStream;

        } catch (InvocationTargetException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "InvocationTargetException creating the NCIPMessage from the NCIPInitiationData object.", e);

        } catch (IllegalAccessException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "IllegalAccessException creating the NCIPMessage from the NCIPInitiationData object.", e);

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPMessage from the NCIPInitiationData object.", e);

        }

    }

    @Override
    public ByteArrayInputStream createResponseMessageStream(
        ServiceContext serviceContext, NCIPResponseData responseData)
        throws ServiceException, ValidationException {

        try {

            String msgName = JAXBHelper.getMessageName(responseData);

            // Create a service.NCIPMessage object and put the responseData object in it
            NCIPMessage svcNCIPMessage = new NCIPMessage();
            ReflectionHelper.setField(svcNCIPMessage, responseData, msgName);

            serviceContext.validateBeforeMarshalling(svcNCIPMessage);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long respTranslateStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage ncipMsg = mapper.map(svcNCIPMessage,
                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage.class);

            long respTranslateEndTime = System.currentTimeMillis();
            statisticsBean.record(respTranslateStartTime, respTranslateEndTime,
                StatisticsBean.RESPONDER_CREATE_MESSAGE_LABELS, msgName);


            long respMarshalStartTime = System.currentTimeMillis();

            ByteArrayInputStream respMsgStream = createMsgStream(serviceContext, ncipMsg);

            long respMarshalEndTime = System.currentTimeMillis();
            statisticsBean.record(respMarshalStartTime, respMarshalEndTime,
                StatisticsBean.RESPONDER_MARSHAL_MESSAGE_LABELS, msgName);

            if (logMessages) {

                respMsgStream = LoggingHelper.copyAndLogStream(LOG, messagesLoggingLevel, respMsgStream);

            }

            return respMsgStream;

        } catch (InvocationTargetException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "InvocationTargetException creating the NCIPMessage from the NCIPResponseData object.", e);

        } catch (IllegalAccessException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "IllegalAccessException creating the NCIPMessage from the NCIPResponseData object.", e);

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPMessage from the NCIPResponseData object.", e);

        }

    }

    protected ByteArrayInputStream createMsgStream(ServiceContext serviceContext,
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage ncipMsg)
        throws ServiceException {

        try {

            Marshaller m = marshallerFactory.createMarshaller(serviceContext);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

            try {

                m.marshal(ncipMsg, byteArrayOutputStream);

            } catch (JAXBException e) {

                throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                    "JAXBException marshalling the message.", e);

            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream resultStream = new ByteArrayInputStream(bytes);

            return resultStream;

        } catch (JAXBException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                "JAXBException creating the Mashaller.", e);

        }

    }

    protected org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage createNCIPMessage(
        ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException {

        try {

            Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(serviceContext);

            try {

                NCIPServiceContext ncipServiceContext = (NCIPServiceContext)serviceContext;

                if ( ncipServiceContext.addDefaultNamespace() ) {

                    XMLReader reader = XMLReaderFactory.createXMLReader();

                    NamespaceFilter inFilter = new NamespaceFilter(
                            ((NCIPServiceContext)serviceContext).getDefaultNamespace(), true);

                    inFilter.setParent(reader);

                    SAXSource saxSource = new SAXSource(inFilter, new InputSource(inputStream));

                    return (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage)
                        unmarshaller.unmarshal(saxSource);

                } else {

                    return (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage)
                        unmarshaller.unmarshal(inputStream);

                }

            } catch (JAXBException e) {

                throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                        "JAXBException un-marshalling from InputStream.", e);

            } catch (SAXException e) {

                throw new ServiceException(ServiceError.RUNTIME_ERROR, "SAXException creating XMLReader.", e);

            }

        } catch (JAXBException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                "JAXBException creating the unmarshaller.", e);

        }

    }

}