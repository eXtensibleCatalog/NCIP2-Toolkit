/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.JAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.initiator.InitiatorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Arrays;

/**
 * This abstract class provides the base class for simple NCIP Initiators that perform a single NCIP service
 * on an NCIP Responder at a target URL and displays the results.
 */
public abstract class SimpleClient {

    private static final Logger LOG = Logger.getLogger(SimpleClient.class);

    protected static final String LOG4J_CONFIG_FILENAME = "src\\main\\resources\\log4j.properties";
    protected static final String APP_CONFIG_FILENAME = "src\\main\\resources\\simpleclientconfig.xml";
    protected static final ApplicationContext appContext;
    protected static final Translator translator;
    /**
     * The {@link org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean} instance used to report performance data.
     */
    protected static final ToolkitStatisticsBean statisticsBean;

    static {

        PropertyConfigurator.configure(LOG4J_CONFIG_FILENAME);
        appContext = new FileSystemXmlApplicationContext(new String[] { APP_CONFIG_FILENAME });

        if ( appContext != null ) {

            LOG.debug("Loaded application context from '" + APP_CONFIG_FILENAME + "'.");
            translator = (Translator)appContext.getBean("translator");
            statisticsBean = (ToolkitStatisticsBean)appContext.getBean("toolkitStatistics");

        } else {

            LOG.debug("No application context at '" + APP_CONFIG_FILENAME + "'; using defaults.");
            statisticsBean = new ToolkitStatisticsBean();
            translator = new JAXBDozerTranslator(statisticsBean);

        }

    }

    protected NCIPService<NCIPInitiationData, NCIPResponseData> service;
    protected NCIPImplProf1ServiceManager serviceManager;

    public SimpleClient() {
        service = new InitiatorService(translator);
        serviceManager = new NCIPImplProf1ServiceManager();
    }

    /**
     * Main method for running this client from a command line.
     * Supply three or more command-line parameters: class-name targetURL parm1 parm2 ...
     * where 'class-name' is the name of a sub-class of SimpleClient that performs an NCIP service.
     *
     * @param args parameters from the command-line per above
     * @throws ServiceException if the service fails
     */
    public static void main(String[] args) throws ServiceException, ClassNotFoundException, IllegalAccessException,
        InstantiationException {

        String className = args[0];
        String targetURL = args[1];
        String[] params = Arrays.copyOfRange(args, 2, args.length);
        Class clazz = Class.forName(className);
        SimpleClient client = (SimpleClient)clazz.newInstance();
        client.doService(targetURL, params);

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);
        
    }

    /**
     * Perform the NCIP service for the supplied parameters on the NCIP responder at the target URL.
     *
     * @param targetURL the URL of the target NCIP responder, e.g. http://mylibrary.mycollege.edu:9020/ncip.
     * @param params    array of Strings
     * @throws ServiceException if the service fails
     */
    public abstract void doService(String targetURL, String[] params) throws ServiceException;


    protected String getUsagePrefix() {

        return "Usage: <targetURL>";
        
    }
}
