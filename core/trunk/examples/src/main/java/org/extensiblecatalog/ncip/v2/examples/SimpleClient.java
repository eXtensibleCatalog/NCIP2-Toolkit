/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;

import java.util.Arrays;

/**
 * This abstract class provides the base class for simple NCIP Initiators that perform a single NCIP service
 * on an NCIP Responder at a target URL and displays the results.
 */
public abstract class SimpleClient {

    private static final Logger LOG = Logger.getLogger(SimpleClient.class);

    protected static StatisticsBean statisticsBean;

    static {

        try {

            statisticsBean = StatisticsBeanFactory.buildStatisticsBean();

        } catch (ToolkitException e) {

            LOG.error(e);
            throw new ExceptionInInitializerError(e);

        }

    }

    /**
     * Main method for running this client from a command line.
     * Supply three or more command-line parameters: class-name targetURL parm1 parm2 ...
     * where 'class-name' is the name of a sub-class of SimpleClient that performs an NCIP service.
     *
     * @param args parameters from the command-line per above
     * @throws ServiceException if the service fails
     * @throws ValidationException if the data is not valid for the current protocol/version
     * @throws ToolkitException if the Toolkit is not properly configured
     * @throws ClassNotFoundException if a dependency is missing or a class name provided via Toolkit configuration is wrong
     * @throws IllegalAccessException if a class name provided via Toolkit configuration is wrong
     * @throws InstantiationException if a class name provided via Toolkit configuration is wrong
     */
    public static void main(String[] args) throws ServiceException, ValidationException, ToolkitException,
        ClassNotFoundException, IllegalAccessException, InstantiationException {

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
     * @throws ValidationException if the data is not valid for the current protocol/version
     * @throws ToolkitException if the Toolkit is not properly configured
     */
    public abstract void doService(String targetURL, String[] params) throws ServiceException, ValidationException,
        ToolkitException;


    protected String getUsagePrefix() {

        return "Usage: <targetURL>";
        
    }
}
