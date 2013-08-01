/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.binding.jaxb.MarshallerFactory;
import org.extensiblecatalog.ncip.v2.binding.jaxb.NamespaceFilter;
import org.extensiblecatalog.ncip.v2.common.LoggingHelper;
import org.extensiblecatalog.ncip.v2.common.NCIPServiceContext;
import org.extensiblecatalog.ncip.v2.common.StatisticsBean;
import org.extensiblecatalog.ncip.v2.common.StatisticsBeanFactory;
import org.extensiblecatalog.ncip.v2.common.Translator;
import org.extensiblecatalog.ncip.v2.common.TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.common.TranslatorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.MzkLookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.NCIPMessage;
import org.extensiblecatalog.ncip.v2.service.NCIPResponseData;
import org.extensiblecatalog.ncip.v2.service.ReflectionHelper;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.ValidationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

// TODO: Provide a  BaseTranslator that this extends, so that ctors illustrate what's expected (e.g. not initializing in default ctor).
public abstract class BaseJAXBDozerTranslator<M> implements Translator {

    private static final Logger LOG = Logger.getLogger(BaseJAXBDozerTranslator.class);

    protected DozerBeanMapper mapper;

    protected MarshallerFactory marshallerFactory;

    
    //FIXME delete
    static int i = 0;
    /**
     * The {@link StatisticsBean} instance used to report performance data.
     */
    protected StatisticsBean statisticsBean;

    // TODO: Logging messages should be handled as a list of command objects that take the message
    /** Whether or not to log initiation and response messages. */
    protected boolean logMessages = false;
    /** If logging the messages, use this logging level. */
    protected Level messagesLoggingLevel = Level.DEBUG;

    /**
     * Create an instance of the translator without initializing its properties; this is intended
     * for Spring or other dependency-injection usage.
     * @throws ToolkitException
     */
    public BaseJAXBDozerTranslator() throws ToolkitException {

        // Do nothing

    }

    /** Construct from provided {@link Properties} object.
     *
     */
    public BaseJAXBDozerTranslator(Properties properties) throws ToolkitException {

        this(TranslatorConfigurationFactory.buildConfiguration(properties));

    }

    /**
     * Construct an instance using the supplied configuration object.
     * Note: This gets its {@link StatisticsBean} by calling {@link StatisticsBeanFactory#getComponent()}.
     */
    public BaseJAXBDozerTranslator(TranslatorConfiguration config) throws ToolkitException {

        JAXBDozerNCIP2TranslatorConfiguration jaxbDozerNCIP2Config = (JAXBDozerNCIP2TranslatorConfiguration)config;

        this.statisticsBean = StatisticsBeanFactory.buildStatisticsBean();

        this.logMessages = config.getLogMessages();

        this.messagesLoggingLevel = config.getMessagesLoggingLevel();

        this.marshallerFactory = new MarshallerFactory(config);

        this.mapper = new DozerBeanMapper();

        this.mapper.setMappingFiles(jaxbDozerNCIP2Config.getMappingFiles());

    }

    public DozerBeanMapper getMapper() {
        return mapper;
    }

    /** If you inject a mapper, you are responsible for configuring it, especially setting the mapping files
     * it's to use.
     * @param mapper the {@link org.dozer.DozerBeanMapper} to use
     */
    public void setMapper(DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    public MarshallerFactory getMarshallerFactory() {
        return marshallerFactory;
    }

    public void setMarshallerFactory(MarshallerFactory marshallerFactory) {
        this.marshallerFactory = marshallerFactory;
    }

    public StatisticsBean getStatisticsBean() {
        return statisticsBean;
    }

    public void setStatisticsBean(StatisticsBean statisticsBean) {
        this.statisticsBean = statisticsBean;
    }

    public boolean getLogMessages() {
        return logMessages;
    }

    public void setLogMessages(boolean logMessages) {
        this.logMessages = logMessages;
    }

    public Level getMessagesLoggingLevel() {
        return messagesLoggingLevel;
    }

    public void setMessagesLoggingLevel(Level messagesLoggingLevel) {
        this.messagesLoggingLevel = messagesLoggingLevel;
    }

    @Override
    public NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException, ValidationException {

        try {

        	inputStream.mark(9999);
            if (logMessages) {

                inputStream = LoggingHelper.copyAndLogStream(LOG, messagesLoggingLevel, inputStream);

            }

            long initUnmarshalStartTime = System.currentTimeMillis();

            M initiationMsg = createNCIPMessage(serviceContext, inputStream);

            String msgName = JAXBHelper.getMessageName(initiationMsg);

            long initUnmarshalEndTime = System.currentTimeMillis();

            statisticsBean.record(initUnmarshalStartTime, initUnmarshalEndTime,
                StatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);

            long initTranslateStartTime = System.currentTimeMillis();

            NCIPMessage svcMessage = mapper.map(initiationMsg, NCIPMessage.class);

            long initTranslateEndTime = System.currentTimeMillis();

            serviceContext.validateAfterUnmarshalling(svcMessage);

            NCIPInitiationData initiationData = svcMessage.getInitiationData();
            
 //FIXME experimental
            if ( initiationData instanceof LookupItemSetInitiationData) {
            	//check manually for unrecognized optional parameters 
	            String yearParam = null;
	            String volumeParam = null;
	            try {
					inputStream.reset();
					String requestContent = MzkLookupItemSetInitiationData.slurp(inputStream, 1024);
					requestContent = requestContent.replaceAll("\\s", "");
					
					//search for publication year
					if (requestContent != null) {
						int start = requestContent.toLowerCase().indexOf(MzkLookupItemSetInitiationData.MZK_TAG_START_PUBLICATION_YEAR);
						start = (start > -1 ?  start + MzkLookupItemSetInitiationData.MZK_TAG_START_PUBLICATION_YEAR.length() : start);
						int end = requestContent.toLowerCase().indexOf(MzkLookupItemSetInitiationData.MZK_TAG_END_PUBLICATION_YEAR);
					    if (start > -1 && end > start) {
					    	yearParam = requestContent.substring(start, end);
					    }
					}
					
					if ( yearParam == null) {
						//search for volume 
						int start = requestContent.toLowerCase().indexOf(MzkLookupItemSetInitiationData.MZK_TAG_START_VOLUME);
						start = (start > -1 ?  start + MzkLookupItemSetInitiationData.MZK_TAG_START_VOLUME.length() : start);
						int end = requestContent.toLowerCase().indexOf(MzkLookupItemSetInitiationData.MZK_TAG_END_VOLUME);
						 if (start > -1 && end > start) {
							 volumeParam = requestContent.substring(start, end);
						    }
					}
					
				} catch (IOException e) {
					// process normally if error occurs
				}
	            if ( ( yearParam != null || volumeParam != null)  && initiationData instanceof LookupItemSetInitiationData) {
	        		MzkLookupItemSetInitiationData mzkInitiationData = 
	        			new MzkLookupItemSetInitiationData( (LookupItemSetInitiationData) initiationData);
	        	    if ( yearParam != null) {
	        	    	mzkInitiationData.setPublicationYear(yearParam);
	        	    }
	        	    if ( volumeParam != null ) {
	        	    	mzkInitiationData.setVolume(volumeParam);
	        	    }
	        		initiationData = (LookupItemSetInitiationData) mzkInitiationData;
	            }
            }
//------
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

            M responseMsg = createNCIPMessage(serviceContext, responseMsgInputStream);

            String msgName = JAXBHelper.getMessageName(responseMsg);

            long respUnmarshalEndTime = System.currentTimeMillis();

            statisticsBean.record(respUnmarshalStartTime, respUnmarshalEndTime,
                StatisticsBean.RESPONDER_UNMARSHAL_MESSAGE_LABELS, msgName);


            long respTranslateStartTime = System.currentTimeMillis();

            NCIPMessage svcMessage = mapper.map(responseMsg, NCIPMessage.class);

            long respTranslateEndTime = System.currentTimeMillis();

            serviceContext.validateAfterUnmarshalling(svcMessage);

            NCIPResponseData responseData = svcMessage.getResponseData();

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

            String msgName = ServiceHelper.getMessageName(initiationData);

            // Create a service.NCIPMessage object and put the initiationData object in it
            NCIPMessage svcNCIPMessage = new NCIPMessage();
            ReflectionHelper.setField(svcNCIPMessage, initiationData, msgName);

            serviceContext.validateBeforeMarshalling(svcNCIPMessage);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long initTranslateStartTime = System.currentTimeMillis();

            M ncipMsg = mapMessage(svcNCIPMessage, mapper);

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

            String msgName = ServiceHelper.getMessageName(responseData);

            // Create a service.NCIPMessage object and put the responseData object in it
            NCIPMessage svcNCIPMessage = new NCIPMessage();
            ReflectionHelper.setField(svcNCIPMessage, responseData, msgName);

            serviceContext.validateBeforeMarshalling(svcNCIPMessage);

            // Map from the service.NCIPMessage object to the binding.jaxb.NCIPMessage object
            long respTranslateStartTime = System.currentTimeMillis();

            M ncipMsg = mapMessage(svcNCIPMessage, mapper);

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

    protected ByteArrayInputStream createMsgStream(ServiceContext serviceContext, M ncipMsg)
        throws ServiceException {

        NCIPServiceContext ncipServiceContext = (NCIPServiceContext)serviceContext;

        try {

            Marshaller marshaller = marshallerFactory.getMarshaller(serviceContext);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

            try {

                if ( ncipServiceContext.addDefaultNamespace() ) {

                    // TODO: Figure out how to add the default namespace; the following is ignoring that
                    marshaller.marshal(ncipMsg, byteArrayOutputStream);

                } else {

                    marshaller.marshal(ncipMsg, byteArrayOutputStream);

                }

            } catch (JAXBException e) {

                throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                    "JAXBException marshalling the message.", e);

            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            ByteArrayInputStream resultStream = new ByteArrayInputStream(bytes);

            return resultStream;

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                "Toolkit creating the Mashaller.", e);

        }

    }

    protected M createNCIPMessage(ServiceContext serviceContext, InputStream inputStream)
        throws ServiceException {

        try {

            Unmarshaller unmarshaller = marshallerFactory.getUnmarshaller(serviceContext);

            NCIPServiceContext ncipServiceContext = (NCIPServiceContext)serviceContext;

            try {

                // TODO: Reorganize this if/else so that addDefaultNamespace and parserFeatures aren't exclusive
                if ( ncipServiceContext.addDefaultNamespace() ) {

                    XMLReader reader = XMLReaderFactory.createXMLReader();

                    NamespaceFilter inFilter = new NamespaceFilter(
                            ((NCIPServiceContext)serviceContext).getDefaultNamespace(), true);

                    inFilter.setParent(reader);

                    SAXSource saxSource = new SAXSource(inFilter, new InputSource(inputStream));

                    return (M) unmarshaller.unmarshal(saxSource);

                } else {

                    // TODO: This may need to wrap the inputStream in an InputReader or InputSource to control encoding

                    Map<String, Boolean> parserFeatures = ncipServiceContext.getParserFeatures();
                    if ( parserFeatures != null && ! parserFeatures.isEmpty() ) {

                        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                        try {

                            for ( Map.Entry<String, Boolean> entry : parserFeatures.entrySet() ) {

                                // "http://apache.org/xml/features/nonvalidating/load-external-dtd"
                                LOG.debug("Setting feature " + entry.getKey() + " to " + entry.getValue());
                                parserFactory.setFeature(entry.getKey(), entry.getValue());

                            }

                            SAXParser saxParser = parserFactory.newSAXParser();
                            XMLReader xmlReader = saxParser.getXMLReader();
                            UnmarshallerHandler uh = unmarshaller.getUnmarshallerHandler();
                            xmlReader.setContentHandler(uh);
                            xmlReader.parse(new InputSource(inputStream));

                            return (M) uh.getResult();

                        } catch (IOException e) {

                            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                                "Exception creating NCIPMessage object from InputStream.", e);

                        } catch (ParserConfigurationException e) {

                            throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                                "Exception creating NCIPMessage object from InputStream.", e);

                        }

                      } else {

                            return (M) unmarshaller.unmarshal(inputStream);

                      }

                }

            } catch (JAXBException e) {

                throw new ServiceException(ServiceError.INVALID_MESSAGE_FORMAT,
                   "Exception creating NCIPMessage object from InputStream.", e);

            } catch (SAXException e) {

                throw new ServiceException(ServiceError.RUNTIME_ERROR,
                    "Exception creating NCIPMessage object from InputStream.", e);

            }

        } catch (ToolkitException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                "Exception creating NCIPMessage object from InputStream.", e);

        }

    }

    protected abstract M mapMessage(Object svcMsg, DozerBeanMapper mapper);

}
