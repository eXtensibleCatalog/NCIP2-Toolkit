/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.responder.implprof1;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;


/**
 * This servlet implements an NCIP responder for HTTP and HTTPS transport according to
 * the NCIP Implementation Profile 1.
 */
public class NCIPServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(NCIPServlet.class);

    /**
     * Serial Id
     */
    private static final long serialVersionUID = -8518989441219684952L;

    /**
     * Whether to include stack traces in problem responses.
     */
    protected boolean includeStackTracesInProblemResponse = false;

    /**
     * The {@link Translator} instance used to translate network octets to instances of {@link NCIPInitiationData}
     * or {@link NCIPResponseData} for passing to the {@link NCIPService}.
     */
    protected Translator translator;

    /**
     * The {@link ServiceValidator} instance used to create {@link ServiceContext}s.
     */
    protected ServiceValidator serviceValidator;

    /**
     * The {@link MessageHandler} instance used to handle {@link NCIPInitiationData} objects representing incoming
     * NCIP initiation messages.
     */
    protected MessageHandler messageHandler;

    /**
     * The {@link StatisticsBean} instance used to report performance data.
     */
    protected StatisticsBean statisticsBean;

    // TODO: Add constructors for variations
    /**
     * Construct a new instance of this servlet with no {@link MessageHandler} or {@link Translator} set; these
     * must be set before any NCIP messages are received.
     */
    public NCIPServlet() {
        super();
    }

    public NCIPServlet(Properties properties) throws ToolkitException {

        super();

        this.includeStackTracesInProblemResponse
            = CoreConfigurationFactory.buildConfiguration(properties).getIncludeStackTracesInProblemResponses();

        this.messageHandler = MessageHandlerFactory.buildMessageHandler(properties);

        this.translator = TranslatorFactory.buildTranslator(properties);

        this.serviceValidator = ServiceValidatorFactory.buildServiceValidator(properties);

        this.statisticsBean = StatisticsBeanFactory.buildStatisticsBean(properties);

    }

    public NCIPServlet(CoreConfiguration coreConfig) throws ToolkitException {

        super();
        this.includeStackTracesInProblemResponse = coreConfig.getIncludeStackTracesInProblemResponses();
        this.messageHandler = MessageHandlerFactory.buildMessageHandler(coreConfig.getMessageHandlerConfiguration());

        this.translator = TranslatorFactory.buildTranslator(coreConfig.getTranslatorConfiguration());

        this.serviceValidator = ServiceValidatorFactory.buildServiceValidator(coreConfig.getServiceValidatorConfiguration());

        this.statisticsBean = StatisticsBeanFactory.buildStatisticsBean(coreConfig.getStatisticsBeanConfiguration());

    }

    /**
     * Deprecated: Use {@link NCIPServlet(Properties)} or {@link NCIPServlet(CoreConfiguration)}.
     *
     * Construct a new instance of this servlet with the provided {@link MessageHandler}, {@link Translator},
     * {@link ServiceContext} and {@link StatisticsBean}.
     *
     * @param messageHandler the message handler for this responder instance
     * @param translator     the translator for this responder instance
     */
    @Deprecated
    public NCIPServlet(MessageHandler messageHandler, Translator translator, ServiceValidator serviceValidator,
                       StatisticsBean statisticsBean) throws ToolkitException {
        super();
        this.includeStackTracesInProblemResponse
            = ConfigurationHelper.getCoreConfiguration().getIncludeStackTracesInProblemResponses();
        this.messageHandler = messageHandler;
        this.translator = translator;
        this.serviceValidator = serviceValidator;
        this.statisticsBean = statisticsBean;
    }

    /**
     * Deprecated: Use {@link NCIPServlet(Properties)} or {@link NCIPServlet(CoreConfiguration)}.
     * Construct a new instance of this servlet with the provided {@link MessageHandler} and {@link Translator}.
     *
     * @param messageHandler the message handler for this responder instance
     * @param translator     the translator for this responder instance
     */
    @Deprecated
    public NCIPServlet(MessageHandler messageHandler, Translator translator,
                       ServiceValidator serviceValidator)
        throws ToolkitException {
        super();
        this.includeStackTracesInProblemResponse
            = ConfigurationHelper.getCoreConfiguration().getIncludeStackTracesInProblemResponses();
        this.messageHandler = messageHandler;
        this.translator = translator;
        this.serviceValidator = serviceValidator;
        this.statisticsBean = StatisticsBeanFactory.buildStatisticsBean();
    }

    /**
     * Set the {@link MessageHandler} for this responder instance
     *
     * @param messageHandler the message handler
     */
    public void setMessageHandler(MessageHandler messageHandler) {

        this.messageHandler = messageHandler;

    }

    /**
     * Set the {@link Translator} for this responder instance
     *
     * @param translator the translator
     */
    public void setTranslator(Translator translator) {

        this.translator = translator;

    }

    public void setServiceValidator(ServiceValidator serviceValidator) {

        this.serviceValidator = serviceValidator;

    }

    /**
     * Set the {@link StatisticsBean} for this responder instance
     *
     * @param statisticsBean the statistics bean
     */
    public void setStatisticsBean(StatisticsBean statisticsBean) {
        this.statisticsBean = statisticsBean;
    }

    /**
     * Set the includeStackTracesInProblemResponse flag.
     * @param includeStackTracesInProblemResponse
     */
    public void setIncludeStackTracesInProblemResponse(boolean includeStackTracesInProblemResponse) {

        this.includeStackTracesInProblemResponse = includeStackTracesInProblemResponse;

    }

    /**
     * Initialize the servlet
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        String appName = ConfigurationHelper.getAppName(config.getServletContext());

        try {

            if ( messageHandler == null ) {

                messageHandler = MessageHandlerFactory.buildMessageHandler(appName);

            }

            if ( translator == null ) {

                translator = TranslatorFactory.buildTranslator(appName);

            }

            if ( statisticsBean == null ) {

                statisticsBean = StatisticsBeanFactory.buildStatisticsBean(appName);

            }

            if ( serviceValidator == null ) {

                serviceValidator = ServiceValidatorFactory.buildServiceValidator(appName);

            }
            
            includeStackTracesInProblemResponse
                = ConfigurationHelper.getCoreConfiguration().getIncludeStackTracesInProblemResponses();

        } catch (ToolkitException e) {

            throw new ServletException("Exception during init method:", e);

        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {

        long respTotalStartTime = System.currentTimeMillis();

        response.setContentType("application/xml; charset=\"utf-8\"");

        // Note: Statements that might throw exceptions are wrapped in individual try/catch blocks, allowing us
        // to provide very specific error messages.

        ServletInputStream inputStream = null;
        try {

            inputStream = request.getInputStream();

        } catch (IOException e) {

            returnException(response, "Exception getting ServletInputStream from the HttpServletRequest.", e);

        }

        ServiceContext serviceContext = null;
        try {

            serviceContext = serviceValidator.getInitialServiceContext();

        } catch (ToolkitException e) {

            returnException(response, "Exception creating initial service context.", e);

        }

        NCIPInitiationData initiationData = null;

        try {

            initiationData = translator.createInitiationData(serviceContext, inputStream);

        } catch (ServiceException e) {

            returnException(response,
                "Exception creating the NCIPInitiationData object from the servlet's input stream.", e);

        } catch (ValidationException e) {

            returnValidationProblem(response, e);

        }

        long initPerfSvcStartTime = System.currentTimeMillis();

        NCIPResponseData responseData = messageHandler.performService(initiationData, serviceContext);

        long initPerfSvcEndTime = System.currentTimeMillis();

        String serviceName = ServiceHelper.getServiceName(initiationData);

        statisticsBean.record(initPerfSvcStartTime, initPerfSvcEndTime,
            StatisticsBean.RESPONDER_PERFORM_SERVICE_LABELS, serviceName);

        InputStream responseMsgInputStream = null;
        try {

            responseMsgInputStream = translator.createResponseMessageStream(serviceContext, responseData);

        } catch (ServiceException e) {

            returnException(response,
                "Exception creating the InputStream from the NCIPResponseData object.", e);

        } catch (ValidationException e) {

            returnException(response,
                "Exception creating the InputStream from the NCIPResponseData object.", e);

        }

        int bytesAvailable = 0;
        if ( responseMsgInputStream != null ) {

            try {

                bytesAvailable = responseMsgInputStream.available();

            } catch (IOException e) {

                returnException(response,
                    "Exception getting the count of available bytes from the response message's InputStream.", e);

            }

        }

        int bytesRead = 0;
        if ( bytesAvailable != 0 ) {

            byte[] responseMsgBytes = new byte[bytesAvailable];

            try {

                bytesRead = responseMsgInputStream.read(responseMsgBytes, 0, bytesAvailable);

            } catch (IOException e) {

                returnException(response,
                    "Exception reading bytes from the response message's InputStream.", e);

            }

            if (bytesRead != bytesAvailable) {

                returnProblem(response, "Bytes read from the response message's InputStream (" + bytesRead
                    + ") are not the same as the number available (" + bytesAvailable + ").");

            } else {

                response.setContentLength(responseMsgBytes.length);

                // TODO: What about a try/finally that closes the output stream - is that needed?

                ServletOutputStream outputStream = null;
                try {

                    outputStream = response.getOutputStream();

                } catch (IOException e) {

                    returnException(response, "Exception getting the HttpServletResponse's OutputStream.", e);

                }

                try {

                    outputStream.write(responseMsgBytes);

                } catch (IOException e) {

                    returnException(response,
                        "Exception writing the NCIP response message to the HttpServletResponse's OutputStream.", e);

                }

                try {

                    outputStream.flush();

                } catch (IOException e) {

                    returnException(response,
                        "Exception flushing the HttpServletResponse's OutputStream.", e);

                }

            }

        }


        long respTotalEndTime = System.currentTimeMillis();

        statisticsBean.record(respTotalStartTime, respTotalEndTime,
            StatisticsBean.RESPONDER_TOTAL_LABELS, serviceName);
    }

    protected void returnException(HttpServletResponse response, String msg, Throwable e) throws ServletException {
        if (includeStackTracesInProblemResponse) {
            returnProblem(response, msg + System.getProperty("line.separator")
                + "Stacktrace from NCIP responder:" + System.getProperty("line.separator")
                + ServiceHelper.convertExceptionToString(e));
        } else {
            returnProblem(response, msg);
        }
    }

    // TODO: Test this
    protected void returnValidationProblem(HttpServletResponse response,
                                           ValidationException validationException) throws ServletException {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            // TODO: The version, namespace, etc. ought to come from ServiceContext
            .append("<ns1:NCIPMessage ns1:version=\"http://www.niso.org/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd\"")
            .append(" xmlns:ns1=\"http://www.niso.org/2008/ncip\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
            .append(" xsi:schemaLocation=\"http://www.niso.org/2008/ncip ncip_v2_0.xsd\">\n");

        // This hand-marshaling may seem like a bad idea, but we may've entered this method because the marshaling is failing
        List<Problem> problemsList = validationException.getProblems();
        for ( Problem p : problemsList ) {

            sb.append("  <ns1:Problem>\n");
            ProblemType pt = p.getProblemType();
            if ( pt != null ) {

                sb.append("    <ns1:ProblemType");
                String scheme = p.getProblemType().getScheme();
                if ( scheme != null && !scheme.isEmpty() ) {
                    sb.append(" ns1:Scheme=\"").append(scheme).append("\"");
                }
                sb.append(">").append(p.getProblemType().getValue()).append("</ns1:ProblemType>\n");

            }

            String pd = p.getProblemDetail();
            if ( pd != null && !pd.isEmpty() ) {

                sb.append("    <ns1:ProblemDetail>").append(StringEscapeUtils.escapeXml(pd))
                    .append("</ns1:ProblemDetail>\\n");

            }

            sb.append("  </ns1:Problem>\n");

        }

        sb.append("</ns1:NCIPMessage>");

        byte[] problemMsgBytes = sb.toString().getBytes();

        response.setContentLength(problemMsgBytes.length);

        try {

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(problemMsgBytes);
            outputStream.flush();

        } catch (IOException e) {

            throw new ServletException("Exception writing Problem response.", e);

        }

    }

    /**
     * Create a valid version 2 NCIPMessage response indicating a "Temporary Processing Failure", without
     * relying on any facilities of the Toolkit. This method is to be used when handling exceptions that
     * indicate that Toolkit services such as
     * {@link ServiceHelper#generateProblems(org.extensiblecatalog.ncip.v2.service.ProblemType, String, String, String)} ()}
     * may fail.
     * @param response the HttpServletResponse object to use
     * @param detail the text message to include in the ProblemDetail element
     * @throws ServletException if there is an IOException writing to the response object's output stream
     */
    protected void returnProblem(HttpServletResponse response, String detail) throws ServletException {
        String problemMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            // TODO: The version, namespace, etc. ought to come from ServiceContext
            + "<ns1:NCIPMessage ns1:version=\"http://www.niso.org/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd\""
            + " xmlns:ns1=\"http://www.niso.org/2008/ncip\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"http://www.niso.org/2008/ncip ncip_v2_0.xsd\">\n"
            + "  <ns1:Problem>\n"
            + "    <ns1:ProblemType ns1:Scheme=\"http://www.niso.org/ncip/v1_0/schemes/processingerrortype/generalprocessingerror.scm\">Temporary Processing Failure</ns1:ProblemType>\n"
            + "    <ns1:ProblemDetail>" + StringEscapeUtils.escapeXml(detail) + "</ns1:ProblemDetail>\n"
            + "  </ns1:Problem>\n"
            + "</ns1:NCIPMessage>";

        byte[] problemMsgBytes = problemMsg.getBytes();

        response.setContentLength(problemMsgBytes.length);

        try {

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(problemMsgBytes);
            outputStream.flush();

        } catch (IOException e) {

            throw new ServletException("Exception writing Problem response.", e);

        }

    }
}
