/**
 * Copyright (c) 2011 eXtensible Catalog Organization
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
import org.extensiblecatalog.ncip.v2.initiator.InitiatorService;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Use this class to perform load-tests on an NCIPResponder.
 * Its input is given on the command-line and consists of the following parameters:
 *     service file delimiter keyField threads messages
 * Example 'service' values are LookupItem, LookupItemSet, LookupUser, etc.
 * Note: Only 'LookupItemSet' is supported presently.
 * The 'file' parameter should be the full path and name of a text file containing the keys to be used in the Lookup messages.
 * The 'delimiter' is a character or string of characters that delimits fields in the text file.
 * The 'keyField' is the ordinal number of the key in the text file.
 * The 'threads' is the number of simultaneous threads to use to perform the tests.
 * The 'messages' is the number of messages
 * For example, if the text file is named "LoadTestRecords.csv" and is a comma-separated file with the key of a
 * bibliographic record id in the first column, using 4 threads each sending 20 messages, your parameters would be:
 *    LookupItemSet LoadTestRecords.csv "," 1 4 20
 * The messages sent will be randomly chosen from the list of keys in the input file.
 * Note: To speed the sending of messages once the test has started, this program creates the initiation messages
 * for every input record <i>before</i> sending any, so there may be a long start-up time, and it may require a large
 * amount of memory to run this program if the input file is large.
 *
 */
public class LoadTest implements Runnable {

    private static final Logger LOG = Logger.getLogger(LoadTest.class);
    protected static final String LOG4J_CONFIG_FILENAME = "src\\main\\resources\\log4j.properties";
    protected static final String APP_CONFIG_FILENAME = "src\\main\\resources\\simpleclientconfig.xml";
    protected static final String DEFAULT_TARGET_URL = "http://localhost:8080/ncipv2/NCIPResponder";
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

    protected String serviceName;
    protected NCIPInitiationData[] initiationData;
    protected int numberOfMessages;
    protected String targetURL;
    protected NCIPService<NCIPInitiationData, NCIPResponseData> service;
    protected NCIPImplProf1ServiceManager serviceManager;

    public static void main(String[] args)
        throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String serviceName = args[0];
        String fileName = args[1];
        String delimiter = args[2];
        int keyField = Integer.parseInt(args[3]);
        int numberOfThreads = 1;
        if ( args.length >= 5 && args[4] != null ) {

            numberOfThreads = Integer.parseInt(args[4]);

        }

        int numberOfMessages = 1;
        if ( args.length >= 6 && args[5] != null ) {

            numberOfMessages = Integer.parseInt(args[5]);

        }

        String targetURL = DEFAULT_TARGET_URL;

        if ( args.length >= 7 && args[6] != null ) {

            targetURL = args[6];

        }

        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;

        List<NCIPInitiationData> initiationDataList = new ArrayList <NCIPInitiationData>();

        // TODO: Get from command line
        AgencyId agencyId = new AgencyId("nowhere");

        LOG.info("Begining to create initiation messages.");
        int messageCount = 0;
        while ((strLine = br.readLine()) != null)   {

            String[] fields = strLine.split(delimiter);
            String key = fields[keyField - 1];
            if ( serviceName.compareToIgnoreCase("LookupItemSet") == 0 ) {

                LookupItemSetInitiationData initData = new LookupItemSetInitiationData();
                BibliographicRecordId bibRecId = new BibliographicRecordId();
                bibRecId.setBibliographicRecordIdentifier(key);
                bibRecId.setAgencyId(agencyId);
                BibliographicId bibId = new BibliographicId();
                bibId.setBibliographicRecordId(bibRecId);
                List<BibliographicId> bibIds = new ArrayList<BibliographicId>();
                bibIds.add(bibId);
                initData.setBibliographicIds(bibIds);
                initData.setBibliographicDescriptionDesired(true);
                initData.setCirculationStatusDesired(true);
                initData.setItemDescriptionDesired(true);
                initData.setLocationDesired(true);
                initiationDataList.add(initData);
                messageCount++;
                if ( messageCount % 10 == 0 ) {

                    LOG.info("Created " + messageCount + " messages.");

                }

            } else if ( serviceName.compareToIgnoreCase("LookupItem") == 0 ) {

                LookupItemInitiationData initData = new LookupItemInitiationData();
                ItemId itemId = new ItemId();
                itemId.setItemIdentifierValue(key);
                itemId.setAgencyId(agencyId);
                initData.setItemId(itemId);
                initData.setBibliographicDescriptionDesired(true);
                initData.setCirculationStatusDesired(true);
                initData.setItemDescriptionDesired(true);
                initData.setLocationDesired(true);
                initiationDataList.add(initData);
                messageCount++;
                if ( messageCount % 10 == 0 ) {

                    LOG.info("Created " + messageCount + " messages.");

                }

            } else {

                LOG.error("Unknown service name '" + serviceName + "'.");

            }

        }
        LOG.info("Created " + messageCount + " messages.");

        in.close();

        Thread[] threads = new Thread[numberOfThreads];

        for ( int i = 0; i < threads.length; ++i) {

            threads[i] = new Thread(new LoadTest(serviceName,
                initiationDataList.toArray(new NCIPInitiationData[initiationDataList.size()]),
                numberOfMessages, targetURL));

        }

        for (int i = 0; i < threads.length; ++i) {

            threads[i].start();

        }

        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();

            } catch (InterruptedException e)  {

                LOG.warn("Thread " + i + " interrupted; will wait for remaining threads.", e);

            }
        }

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);
        
    }

    public LoadTest(String serviceName, NCIPInitiationData[] initiationData, int numberOfMessages, String targetURL)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        this.serviceName = serviceName;
        this.initiationData = initiationData;
        this.numberOfMessages = numberOfMessages;
        this.targetURL = targetURL;
        serviceManager = new NCIPImplProf1ServiceManager();
        serviceManager.setTargetAddress(targetURL);
        service = new InitiatorService(translator);


    }

    @Override
    public void run() {

        Random randomGenerator = new Random();
        LOG.info("Beginning " + numberOfMessages + " message(s).");
        for ( int i = 0; i < numberOfMessages; ++i ) {

            int randomIndex = randomGenerator.nextInt(initiationData.length);
            NCIPInitiationData initiationMessage = initiationData[randomIndex];

            NCIPResponseData responseData = null;
            try {
                LOG.debug("Sending message.");
                responseData = service.performService(initiationMessage, serviceManager);
                if ( responseData != null ) {

                    LOG.debug("Received response.");
                    List<Problem> problems = NCIPHelper.getProblems(responseData);
                    if ( problems != null && problems.size() > 0 ) {

                        LOG.error("Problems found:");
                        LOG.error(responseData);

                    } else {

                        LOG.debug("Received successful response.");
                        LOG.debug(responseData);

                    }

                } else {

                    LOG.error("Response data is null.");

                }


            } catch (ServiceException e) {

                LOG.error("ServiceException on initiation message: " + initiationMessage, e);
                continue;

            } catch (IllegalAccessException e) {

                LOG.error("IllegalAccessException on initiation message: " + initiationMessage, e);
                break;

            } catch (InvocationTargetException e) {

                LOG.error("IllegalAccessException on initiation message: " + initiationMessage, e);
                break;

            }

        }

    }
}
