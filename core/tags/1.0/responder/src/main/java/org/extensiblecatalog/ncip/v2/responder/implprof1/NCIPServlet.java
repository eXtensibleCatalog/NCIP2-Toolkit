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
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean;
import org.extensiblecatalog.ncip.v2.service.MessageHandler;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.NCIPResponseData;
import org.extensiblecatalog.ncip.v2.service.NCIPService;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.Translator;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


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
     * Whether or not to include Java stacktraces in the NCIP Problem response elements. Doing so exposes
     * implementation details about the application code "behind" the responder, which an organization may not
     * want to do.
     */
    protected static boolean includeStackTracesInProblemResponses = true;

    /**
     * The {@link Translator} instance used to translate network octets to instances of {@link NCIPInitiationData}
     * or {@link NCIPResponseData} for passing to the {@link NCIPService}.
     */
    protected Translator translator;

    /**
     * The {@link MessageHandler} instance used to handle {@link NCIPInitiationData} objects representing incoming
     * NCIP initiation messages.
     */
    protected MessageHandler messageHandler;

    /**
     * The {@link org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean} instance used to report performance data.
     */
    protected ToolkitStatisticsBean statisticsBean;

    /**
     * Construct a new instance of this servlet with no {@link MessageHandler} or {@link Translator} set; these
     * must be set before any NCIP messages are received.
     */
    public NCIPServlet() {
        super();
    }

    /**
     * Construct a new instance of this servlet with the provided {@link MessageHandler} and {@link Translator}.
     *
     * @param messageHandler the message handler for this responder instance
     * @param translator     the translator for this responder instance
     */
    public NCIPServlet(MessageHandler messageHandler, Translator translator) {
        super();
        this.messageHandler = messageHandler;
        this.translator = translator;
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

    public static boolean includeStackTracesInProblemResponses() {
        return includeStackTracesInProblemResponses;
    }

    public static void includeStackTracesInProblemResponses(boolean includeStackTracesInProblemResponses) {
        NCIPServlet.includeStackTracesInProblemResponses = includeStackTracesInProblemResponses;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
            config.getServletContext());
        messageHandler = (MessageHandler)context.getBean("messageHandler");
        translator = (Translator)context.getBean("translator");
        statisticsBean = (ToolkitStatisticsBean)context.getBean("toolkitStatistics");
        try {
            includeStackTracesInProblemResponses = (Boolean)context.getBean("includeStackTracesInProblemResponses");
        } catch (NoSuchBeanDefinitionException e) {
            // Use default value
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("application/xml; charset=\"utf-8\"");

        ServletInputStream inputStream = request.getInputStream();

        NCIPInitiationData initiationData = null;

        try {

            initiationData = translator.createInitiationData(inputStream);

        } catch (ServiceException e) {

            returnException(response,
                "Exception creating the NCIPInitiationData object from the servlet's input stream.", e);

        }

        if (initiationData != null) {

            try {

                long initPerfSvcStartTime = System.currentTimeMillis();

                NCIPResponseData responseData = messageHandler.performService(initiationData);

                long initPerfSvcEndTime = System.currentTimeMillis();
                String serviceName = initiationData.getClass().getSimpleName().substring(
                    0, initiationData.getClass().getSimpleName().length() - "InitiationData".length());
                statisticsBean.record(initPerfSvcStartTime, initPerfSvcEndTime,
                    ToolkitStatisticsBean.RESPONDER_PERFORM_SERVICE_LABELS, serviceName);

                InputStream responseMsgInputStream = translator.createResponseMessageStream(responseData);

                try {

                    int bytesAvailable = responseMsgInputStream.available();
                    byte[] responseMsgBytes = new byte[bytesAvailable];

                    try {

                        int bytesRead = responseMsgInputStream.read(responseMsgBytes, 0, bytesAvailable);

                        if (bytesRead != bytesAvailable) {

                            returnProblem(response, "Bytes read from the response message's InputStream (" + bytesRead
                                + ") are not the same as the number available (" + bytesAvailable + ").");

                        }

                        response.setContentLength(responseMsgBytes.length);

                        ServletOutputStream outputStream = response.getOutputStream();

                        outputStream.write(responseMsgBytes);
                        outputStream.flush();

                    } catch (IOException e) {

                        returnException(response,
                            "Exception reading bytes from the response message's InputStream.", e);
                    }

                } catch (IOException e) {

                    returnException(response,
                        "Exception getting the count of available bytes from the response message's InputStream.", e);
                }

            } catch (Exception e) {
                LOG.error("Exception creating a response message from the NCIPResponseData object.", e);

                returnException(response, "Exception creating a response message from the NCIPResponseData object.", e);

            }
        }
    }

    protected void returnException(HttpServletResponse response, String msg, Throwable e) throws ServletException {
        if (includeStackTracesInProblemResponses) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.append("Stack trace from NCIP responder: " + System.getProperties().get("line.separator"));
            e.printStackTrace(pw);
            returnProblem(response, msg + System.getProperty("line.separator") + sw.getBuffer().toString());
        } else {
            returnProblem(response, msg);
        }
    }

    protected void returnProblem(HttpServletResponse response, String detail) throws ServletException {
        String problemMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<ns1:NCIPMessage ns1:version=\"http://www.niso.org/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd\""
            + " xmlns:ns1=\"http://www.niso.org/2008/ncip\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"http://www.niso.org/2008/ncip ncip_v2_0.xsd\">\n"
            + "  <ns1:Problem>\n"
            + "    <ns1:ProblemType ns1:Scheme=\"http://www.niso.org/ncip/v1_0/schemes/processingerrortype/generalprocessingerror.scm\">Temporary Processing Failure</ns1:ProblemType>\n"
            + "    <ns1:ProblemDetail>" + detail + "</ns1:ProblemDetail>\n"
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
