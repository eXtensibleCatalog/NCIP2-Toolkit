/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.JAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.initiator.InitiatorService;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.InitiatorServiceManager;
import org.extensiblecatalog.ncip.v2.initiator.implprof1.NCIPImplProf1ServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;

import java.io.*;

public class FileInitiator implements Runnable {

    private static final Logger LOG = Logger.getLogger(FileInitiator.class);

    public static final String DEFAULT_FILENAME_PATTERN = ".*\\.xml";
    public static final String DEFAULT_FILE_DIRECTORY = ".";
    public static final String DEFAULT_TARGET_URL = "http://localhost:8080/ncipv2/NCIPResponder";
    protected static Translator translator;

    protected static StatisticsBean statisticsBean;

    protected static ServiceValidatorFactory serviceContextFactory;

    // TODO: Allow this to be set from command-line switches
    protected boolean logDataObjects = false;

    protected String[] args;

    static {

        try {

            translator = TranslatorFactory.getSharedTranslator();

            statisticsBean = StatisticsBeanFactory.getSharedStatisticsBean();

            serviceContextFactory = new ServiceValidatorFactory();

        } catch (ToolkitException e) {

            LOG.error(e);
            throw new ExceptionInInitializerError(e);

        }

    }


    public FileInitiator(String[] args) {

        this.args = args;

    }

    public static void main(String[] args) throws IOException, ServiceException {

        FileInitiator fileInitiator = new FileInitiator(args);

        fileInitiator.run();

    }

    public void run() {

        if ( args.length > 0 && args[0].matches("(?i)--?h(elp)?")) {

            System.out.println("Usage: java " + FileInitiator.class.getName() + " [fileNamePattern [fileDirectory [targetURL]]]");
            System.out.println("Where fileNamePattern defaults to '" + DEFAULT_FILENAME_PATTERN
                + "', fileDirectory defaults to '" + DEFAULT_FILE_DIRECTORY + "', and targetURL defaults to '"
                + DEFAULT_TARGET_URL + "'.");
            System.exit(-1);

        }

        String fileNamesPattern = DEFAULT_FILENAME_PATTERN;
        if ( args.length > 0 ) {

            fileNamesPattern = args[0];

        }

        String sampleFilesDirectory = DEFAULT_FILE_DIRECTORY;
        if ( args.length > 1 ) {

            sampleFilesDirectory = args[1];

        }

        String targetURL = DEFAULT_TARGET_URL;

        if ( args.length > 2 ) {

            targetURL = args[2];

        }

        File dir = new File(sampleFilesDirectory);

        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        };

        File[] files = dir.listFiles(fileFilter);

        long filesProcessed = 0;

        if (files != null) {

            for (File file : files) {

                String fileName = file.getName();

                if ( fileNamesPattern == null || fileNamesPattern.length() == 0
                    || fileName.matches(fileNamesPattern) ) {

                    System.out.println("Reading " + fileName);

                    InputStream inStream = null;
                    try {
                        inStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace(System.err);
                        continue;
                    }

                    filesProcessed++;

                    NCIPInitiationData initiationData = null;

                    ServiceContext serviceContext = null;
                    try {

                        serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

                    } catch (ToolkitException e) {

                        e.printStackTrace(System.err);
                        continue;

                    }

                    try {

                        initiationData = translator.createInitiationData(serviceContext, inStream);

                    } catch (ServiceException e) {

                        e.printStackTrace(System.err);
                        continue;

                    } catch (ValidationException e) {

                        e.printStackTrace(System.err);
                        continue;

                    }

                    if (initiationData != null) {

                        LOG.info("Successfully converted file to data object.");

                        if (logDataObjects) {

                            LOG.info(initiationData.toString());

                        }

                        System.out.println("Sending " + fileName);

                        InitiatorServiceManager serviceManager = new NCIPImplProf1ServiceManager();
                        serviceManager.setTargetAddress(targetURL);
                        NCIPService<NCIPInitiationData, NCIPResponseData> service = null;
                        try {

                            service = new InitiatorService(new JAXBDozerTranslator());
                            NCIPResponseData responseData = null;
                            try {

                                responseData = service.performService(initiationData, serviceContext, serviceManager);

                            } catch (ServiceException e) {

                                e.printStackTrace(System.err);
                                continue;

                            } catch (ValidationException e) {

                                e.printStackTrace(System.err);
                                continue;

                            }

                            if ( responseData != null ) {

                                LOG.info("Received response.");

                                if (logDataObjects) {

                                    LOG.info(responseData.toString());

                                }

                            } else {

                                LOG.error("Response data is null.");

                            }

                        } catch (ToolkitException e) {

                            LOG.error("Exception during construction of the InitiatorService:", e);
                            break;

                        }

                    } else {

                        LOG.error("Initiation data is null.");

                    }

                }

            }

        }

        if ( filesProcessed == 0 ) {
            System.err.println("No files matching the pattern '" + fileNamesPattern + "' were found in '"
                + sampleFilesDirectory + "'."
                + " Note: do not include a trailing slash in the path."
                + " Working directory: " + System.getProperty("user.dir"));
        } else {

            String statsReport = statisticsBean.createCSVReport();
            System.out.println(statsReport);
            
        }

    }

}
