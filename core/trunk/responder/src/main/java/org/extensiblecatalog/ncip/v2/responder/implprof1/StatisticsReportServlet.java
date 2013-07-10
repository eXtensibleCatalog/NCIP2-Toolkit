/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.responder.implprof1;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.StatisticsBean;
import org.extensiblecatalog.ncip.v2.common.StatisticsBeanFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public class StatisticsReportServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(StatisticsReportServlet.class);

    /**
     * The {@link StatisticsBean} instance used to report performance data.
     */
    protected StatisticsBean statisticsBean;


    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        try {

            statisticsBean = StatisticsBeanFactory.buildStatisticsBean();

        } catch (ToolkitException e) {

            throw new ServletException("Exception getting shared statistics bean:", e);

        }

    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html; charset=\"utf-8\"");

        StringBuilder responseMsg = new StringBuilder();
        responseMsg.append("<html><body><img src=\"logo.jpg\"/><br/>Message parsing statistics:<br/>");

        DecimalFormat formatter = new DecimalFormat("0.00");
        responseMsg.append("<table border=\"2\"><tr><td align=\"center\" colspan=\"")
            .append(statisticsBean.getMaxLabels()).append("\">Type</td><td align=\"center\">Count</td>")
            .append("<td align=\"center\">Total ms.</td><td align=\"center\">Average ms.</td></tr>");


        for ( Map.Entry<String, StatisticsBean.StatsRecord> statRecord : statisticsBean.getStatsRecords().entrySet() ) {

            responseMsg.append("<tr>");

            buildKeyHTML(responseMsg, statRecord.getValue().getLabels());

            if ( statRecord.getValue().getLabelCount() < statisticsBean.getMaxLabels() ) {

                for ( long i = statRecord.getValue().getLabelCount(); i <= statisticsBean.getMaxLabels(); i++ ) {

                    responseMsg.append("<td>&nbsp;</td>");

                }

            }

            float floatAvg = ((float)statRecord.getValue().getTotalIntervals()) / ((float)statRecord.getValue().getCount());

            responseMsg.append("<td align=\"right\">").append(statRecord.getValue().getCount()).append("</td>")
                .append("<td align=\"right\">").append(statRecord.getValue().getTotalIntervals()).append("</td>")
                .append("<td align=\"right\">").append(formatter.format(floatAvg)).append("</td>")
                .append("</tr>");

        }
        
        responseMsg.append("</table>");
        responseMsg.append("<form name=\"clearForm\" action=\"Statistics\" method=\"POST\">")
            .append("<input type=hidden name=action value=\"clear\">")
            .append("<input type=\"submit\" value=\"clear\"></form>");
        responseMsg.append("</body></html>");

        response.setContentLength(responseMsg.length());

        try {

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(responseMsg.toString().getBytes("UTF-8"));
            outputStream.flush();

        } catch (IOException e) {

            throw new ServletException("Exception writing response.", e);

        }

    }

    /**
     * Append "<td>" + keyPart + "</td>" to the responseMsg; but if keyPart is an array
     * call this method recursively on each entry in the array.
     *
     * @param responseMsg
     * @param keyPart
     */
    private void buildKeyHTML(StringBuilder responseMsg, Object keyPart) {
        
        if ( keyPart != null && keyPart.getClass().isArray() ) {

            Object[] keyPartArray = (Object[])keyPart;
            for ( Object obj : keyPartArray ) {

                buildKeyHTML(responseMsg, obj);
                
            }

        } else {

            responseMsg.append("<td>").append(keyPart).append("</td>");

        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        String action = request.getParameter("action");
        if ( action != null && action.matches("(?i)^(clear|reset)$") ) {

            statisticsBean.clear();

        }

        response.sendRedirect("Statistics");

    }

}
