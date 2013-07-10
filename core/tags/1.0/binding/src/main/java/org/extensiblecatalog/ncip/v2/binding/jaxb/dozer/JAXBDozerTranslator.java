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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.XMLHelper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.*;

import javax.xml.bind.*;
import javax.xml.validation.Schema;

import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.common.ReflectionHelper;
import org.extensiblecatalog.ncip.v2.common.ToolkitException;
import org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean;


public class JAXBDozerTranslator implements org.extensiblecatalog.ncip.v2.service.Translator {

    private static final Logger LOG = Logger.getLogger(JAXBDozerTranslator.class);

    protected static final String PACKAGE_NAME = "org.extensiblecatalog.ncip.v2.binding.jaxb.elements";

    protected static final String NCIP_VERSION_V2_0 = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd";

    static JAXBContext jaxbContext;
    static {
        try {

            jaxbContext = JAXBContext.newInstance(PACKAGE_NAME);

        } catch (JAXBException e) {

            LOG.error("Exception creating a new instance of JAXBContext:", e);
            throw new ExceptionInInitializerError(e);

        }
    }

    static final DozerBeanMapper mapper = new DozerBeanMapper();
    static {
        List<String> mappingFilesList = new ArrayList<String>();
        mappingFilesList.add("ncipv2_mappings.xml");
        mapper.setMappingFiles(mappingFilesList);
    }

    protected static Schema schema = XMLHelper.loadSchema(NCIPConfiguration.getInstance().getSchemaURLs());

    /**
     * The {@link org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean} instance used to report performance data.
     */
    protected ToolkitStatisticsBean statisticsBean;

    /**
     * Default constructor
     */
    public JAXBDozerTranslator() {

        statisticsBean = new ToolkitStatisticsBean();

    }

    /**
     * Constructor to use a shared ToolkitStatisticsBean
     * @param statisticsBean
     */
    public JAXBDozerTranslator(ToolkitStatisticsBean statisticsBean) {

        this.statisticsBean = statisticsBean;
        
    }

    @Override
    public org.extensiblecatalog.ncip.v2.service.NCIPInitiationData createInitiationData(InputStream inputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            long initUnmarshalStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage initiationMsg
                = createNCIPMessage(inputStream);

            long initUnmarshalEndTime = System.currentTimeMillis();

            String msgName = JAXBHelper.getMessageName(initiationMsg);
            statisticsBean.record(initUnmarshalStartTime, initUnmarshalEndTime,
                ToolkitStatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);


            long initTranslateStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.service.NCIPMessage svcMessage = mapper.map(initiationMsg,
                org.extensiblecatalog.ncip.v2.service.NCIPMessage.class);

            long initTranslateEndTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData
                = (org.extensiblecatalog.ncip.v2.service.NCIPInitiationData)
                    ReflectionHelper.unwrapFirstNonNullFieldViaGetter(svcMessage);

            statisticsBean.record(initTranslateStartTime, initTranslateEndTime,
                ToolkitStatisticsBean.RESPONDER_CREATE_DATA_LABELS, JAXBHelper.getMessageName(initiationData));

            return initiationData;

        } catch (IllegalAccessException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the initiation message.", e);

        } catch (InvocationTargetException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the initiation message.", e);

        }

    }

    @Override
    public org.extensiblecatalog.ncip.v2.service.NCIPResponseData createResponseData(InputStream responseMsgInputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            long respUnmarshalStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage responseMsg
                = createNCIPMessage(responseMsgInputStream);

            long respUnmarshalEndTime = System.currentTimeMillis();

            String msgName = JAXBHelper.getMessageName(responseMsg);
            statisticsBean.record(respUnmarshalStartTime, respUnmarshalEndTime,
                ToolkitStatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);


            long respTranslateStartTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.service.NCIPMessage svcMessage = mapper.map(responseMsg,
                org.extensiblecatalog.ncip.v2.service.NCIPMessage.class);

            long respTranslateEndTime = System.currentTimeMillis();

            org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData
                = (org.extensiblecatalog.ncip.v2.service.NCIPResponseData)
                    ReflectionHelper.unwrapFirstNonNullFieldViaGetter(svcMessage);

            statisticsBean.record(respTranslateStartTime, respTranslateEndTime,
                ToolkitStatisticsBean.RESPONDER_CREATE_DATA_LABELS, JAXBHelper.getMessageName(responseData));

            return responseData;

        } catch (IllegalAccessException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the response message.", e);

        } catch (InvocationTargetException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "Exception unwrapping the response message.", e);

        }

    }

    @Override
    public ByteArrayInputStream createInitiationMessageStream(
        org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            // Create a service.NCIPMessage object and put the initiationData object in it
            org.extensiblecatalog.ncip.v2.service.NCIPMessage svcNCIPMessage
                = new org.extensiblecatalog.ncip.v2.service.NCIPMessage();
            String msgName = JAXBHelper.getMessageName(initiationData);
            ReflectionHelper.setField(svcNCIPMessage, initiationData, msgName);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long initTranslateStartTime = System.currentTimeMillis();

            NCIPMessage ncipMsg = mapper.map(svcNCIPMessage,
                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage.class);

            long initTranslateEndTime = System.currentTimeMillis();
            statisticsBean.record(initTranslateStartTime, initTranslateEndTime,
                ToolkitStatisticsBean.RESPONDER_CREATE_MESSAGE_LABELS, msgName);

            ncipMsg.setVersion(NCIP_VERSION_V2_0);

            long initMarshalStartTime = System.currentTimeMillis();

            ByteArrayInputStream initMsgStream = createMsgStream(ncipMsg);

            long initMarshalEndTime = System.currentTimeMillis();
            statisticsBean.record(initMarshalStartTime, initMarshalEndTime,
                ToolkitStatisticsBean.RESPONDER_MARSHAL_MESSAGE_LABELS, msgName);

            return initMsgStream;

        } catch (InvocationTargetException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "InvocationTargetException creating the NCIPMessage from the NCIPInitiationData object.", e);

        } catch (IllegalAccessException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "IllegalAccessException creating the NCIPMessage from the NCIPInitiationData object.", e);

        } catch (ToolkitException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPMessage from the NCIPInitiationData object.", e);

        }

    }

    @Override
    public ByteArrayInputStream createResponseMessageStream(
        org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            // Create a service.NCIPMessage object and put the responseData object in it
            org.extensiblecatalog.ncip.v2.service.NCIPMessage svcNCIPMessage
                = new org.extensiblecatalog.ncip.v2.service.NCIPMessage();
            String msgName = JAXBHelper.getMessageName(responseData);
            ReflectionHelper.setField(svcNCIPMessage, responseData, msgName);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long respTranslateStartTime = System.currentTimeMillis();

            NCIPMessage ncipMsg = mapper.map(svcNCIPMessage,
                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage.class);

            long respTranslateEndTime = System.currentTimeMillis();
            statisticsBean.record(respTranslateStartTime, respTranslateEndTime,
                ToolkitStatisticsBean.RESPONDER_CREATE_MESSAGE_LABELS, msgName);

            ncipMsg.setVersion(NCIP_VERSION_V2_0);

            long respMarshalStartTime = System.currentTimeMillis();

            ByteArrayInputStream respMsgStream = createMsgStream(ncipMsg);

            long respMarshalEndTime = System.currentTimeMillis();
            statisticsBean.record(respMarshalStartTime, respMarshalEndTime,
                ToolkitStatisticsBean.RESPONDER_MARSHAL_MESSAGE_LABELS, msgName);

            return respMsgStream;

        } catch (InvocationTargetException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "InvocationTargetException creating the NCIPMessage from the NCIPResponseData object.", e);

        } catch (IllegalAccessException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "IllegalAccessException creating the NCIPMessage from the NCIPResponseData object.", e);

        } catch (ToolkitException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "ToolkitException creating the NCIPMessage from the NCIPResponseData object.", e);

        }

    }

    protected ByteArrayInputStream createMsgStream(NCIPMessage ncipMsg)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            Marshaller m = jaxbContext.createMarshaller();
            if (schema != null) {

                m.setSchema(schema);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

            try {

                m.marshal(ncipMsg, byteArrayOutputStream);

            } catch (JAXBException e) {

                throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                    org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                    "JAXBException marshalling the message.", e);

            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream resultStream = new ByteArrayInputStream(bytes);

            return resultStream;

        } catch (JAXBException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.RUNTIME_ERROR,
                "JAXBException creating the Mashaller.", e);

        }

    }

    protected NCIPMessage createNCIPMessage(InputStream inputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            if (schema != null) {

                unmarshaller.setSchema(schema);

            }

            try {

                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage ncipMsg =
                    (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage)
                        unmarshaller.unmarshal(inputStream);

                return ncipMsg;

            } catch (JAXBException e) {

                throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                    org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                    "JAXBException un-marshalling from InputStream.", e);

            }

        } catch (JAXBException e) {

            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.RUNTIME_ERROR,
                "JAXBException creating the unmarshaller.", e);

        }

    }

}