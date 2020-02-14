/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *                    
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;
import org.w3c.dom.Document;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseTestJAXBTranslator implements Runnable {

    private static final Logger LOG = Logger.getLogger(BaseTestJAXBTranslator.class);

    private static final String DEFAULT_FILENAMES_PATTERN = ".*\\.xml";

    private static final String DEFAULT_FILENAMES_TO_OMIT_PATTERN = null;

    private static final String DIFFXML_CLASS_NAME = "org.diffxml.diffxml.DiffFactory";

    protected static boolean performXMLDiff = Boolean.getBoolean(BaseTestJAXBTranslator.class.getName()
        + ".performXMLDiff");

    protected static boolean reportDiffAsXMLIndented = Boolean.getBoolean(BaseTestJAXBTranslator.class.getName()
        + ".reportDiffAsXMLIndented");

    protected static String diffCommand = System.getProperty(BaseTestJAXBTranslator.class.getName()
        + ".diffCommand", "diff -w") + " "; // Make sure there's a trailing space
                                            // to keep the file names distinct from the command.

    protected static Translator translator;
    /**
     * The {@link StatisticsBean} instance used to report performance data.
     */
    protected static StatisticsBean statisticsBean;

    protected static ServiceValidator serviceValidator;

    static {

        System.out.println("Working dir:" + System.getProperty("user.dir"));
        try {

            translator = TranslatorFactory.buildTranslator();

            statisticsBean = StatisticsBeanFactory.buildStatisticsBean();

            serviceValidator = ServiceValidatorFactory.buildServiceValidator();

        } catch (ToolkitException e) {

            LOG.error(e);
            throw new ExceptionInInitializerError(e);

        }

    }

    public void doTest() throws FileNotFoundException, ToolkitException {
                                                                                               
        String sampleFilesDirectory = System.getProperty(BaseTestJAXBTranslator.class.getName() + ".sampleFilesDir",
                "src/test/data/sampleNCIPMessages");
        if (sampleFilesDirectory == null) {
            Assert.fail("Test failed because system property " + BaseTestJAXBTranslator.class.getName() +
                    ".sampleFilesDir was not set. It must be set to a directory containing sample NCIP messages.");
        }

        String fileNamesPattern = System.getProperty(BaseTestJAXBTranslator.class.getName() + ".fileNamesPattern",
            DEFAULT_FILENAMES_PATTERN);

        String fileNamesToOmitPattern = System.getProperty(BaseTestJAXBTranslator.class.getName() + ".fileNamesToOmitPattern",
            DEFAULT_FILENAMES_TO_OMIT_PATTERN);


        int iterations = 1;

        String iterationsString = System.getProperty(BaseTestJAXBTranslator.class.getName() + ".iterations");
        if ( iterationsString != null ) {

            iterations = Integer.parseInt(iterationsString);

        }

        File dir = new File(sampleFilesDirectory);

        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        };

        StringBuilder failuresList = new StringBuilder();
        File[] files = dir.listFiles(fileFilter);
        long initTranslateStreamToSvcElapsedTime = 0;
        long respTranslateStreamToSvcElapsedTime = 0;
        long initTranslateSvcToStreamElapsedTime = 0;
        long respTranslateSvcToStreamElapsedTime = 0;
        long initMsgStreamToSvcCount = 0;
        long respMsgStreamToSvcCount = 0;
        long initMsgSvcToStreamCount = 0;
        long respMsgSvcToStreamCount = 0;

        if (files != null) {

            long startTime = 0;
            long endTime = 0;

            for ( int iteration = 0; iteration < iterations; ++iteration ) {

                for (File file : files) {

                    String fileName = file.getName();

                    if ( ( fileNamesPattern == null || fileNamesPattern.length() == 0
                        || fileName.matches(fileNamesPattern) )
                        && ( fileNamesToOmitPattern == null || fileNamesToOmitPattern.length() == 0
                        || ! fileName.matches(fileNamesToOmitPattern) ) ) {

                        InputStream inStream = new FileInputStream(file);

                        LOG.info("Testing " + fileName);
                        
                        ServiceContext serviceContext = serviceValidator.getInitialServiceContext();

                        if (fileName.contains("Response")) {

                            try {

                                startTime = System.currentTimeMillis();

                                NCIPResponseData responseData = translator.createResponseData(serviceContext,
                                    inStream);

                                endTime = System.currentTimeMillis();
                                respTranslateStreamToSvcElapsedTime += (endTime - startTime);
                                respMsgStreamToSvcCount++;

                                Assert.assertNotNull("createResponseData returned null for " + fileName + ".",
                                    responseData);

                                if (responseData != null) {

                                    try {

                                        startTime = System.currentTimeMillis();

                                        InputStream responseStream = translator.createResponseMessageStream(
                                            serviceContext, responseData);

                                        endTime = System.currentTimeMillis();
                                        respTranslateSvcToStreamElapsedTime += (endTime - startTime);
                                        respMsgSvcToStreamCount++;

                                        Assert.assertNotNull(responseStream);

                                        if (performXMLDiff) {

                                            performXMLDiff(responseStream, failuresList, file);

                                        }

                                    } catch (ServiceException e) {

                                        LOG.debug("Exception in createResponseMessageStream for '" + fileName + "'.", e);
                                        failuresList.append(collectException("createResponseMessageStream", fileName, e));

                                    } catch (ValidationException e) {

                                        LOG.debug("Exception in createResponseMessageStream for '" + fileName + "'.", e);
                                        failuresList.append(collectException("createResponseMessageStream", fileName, e));

                                    }

                                }

                            } catch (ServiceException e) {

                                LOG.debug("Exception in createResponseData for '" + fileName + "'.", e);
                                failuresList.append(collectException("createResponseData", fileName, e));

                            } catch (ValidationException e) {

                                LOG.debug("Exception in createResponseData for '" + fileName + "'.", e);
                                failuresList.append(collectException("createResponseData", fileName, e));

                            }

                        } else {

                            try {

                                startTime = System.currentTimeMillis();

                                NCIPInitiationData initiationData = translator.createInitiationData(
                                    serviceContext, inStream);

                                endTime = System.currentTimeMillis();
                                initTranslateStreamToSvcElapsedTime += (endTime - startTime);
                                initMsgStreamToSvcCount++;

                                Assert.assertNotNull("createInitiationData returned null for " + fileName + ".", initiationData);

                                if (initiationData != null) {

                                    try {

                                        startTime = System.currentTimeMillis();

                                        InputStream initiationStream = translator.createInitiationMessageStream(
                                            serviceContext, initiationData);

                                        Assert.assertNotNull(initiationStream);

                                        endTime = System.currentTimeMillis();
                                        initTranslateSvcToStreamElapsedTime += (endTime - startTime);
                                        initMsgSvcToStreamCount++;

                                        if (performXMLDiff) {

                                            performXMLDiff(initiationStream, failuresList, file);

                                        }

                                    } catch (ServiceException e) {

                                        LOG.debug("Exception in createInitiationMessageStream for '" + fileName + "'.", e);
                                        failuresList.append(collectException("createInitiationMessageStream", fileName, e));

                                    } catch (ValidationException e) {

                                        LOG.debug("Exception in createInitiationMessageStream for '" + fileName + "'.", e);
                                        failuresList.append(collectException("createInitiationMessageStream", fileName, e));

                                    }

                                }

                            } catch (ServiceException e) {

                                LOG.debug("Exception in createInitiationData for '" + fileName + "'.", e);
                                failuresList.append(collectException("createInitiationData", fileName, e));

                            } catch (ValidationException e) {

                                LOG.debug("Exception in createInitiationData for '" + fileName + "'.", e);
                                failuresList.append(collectException("createInitiationData", fileName, e));

                            }

                        }

                    }

                }

            }

            Assert.assertTrue("One or more messages failed:" + System.getProperty("line.separator")
                    + failuresList, failuresList.length() == 0);

        } else {
            Assert.fail(
                    "No files were found in " + sampleFilesDirectory + ". Note: do not include a trailing slash in the path."
                    + "Working directory: " + System.getProperty("user.dir"));
        }

        LOG.info("Elapsed time for translating stream-to-service for initiation messages: "
            + initTranslateStreamToSvcElapsedTime + " for " + initMsgStreamToSvcCount + " messages; average "
            + computeAverage( initTranslateStreamToSvcElapsedTime, initMsgStreamToSvcCount ));
        LOG.info("Elapsed time for translating stream-to-service for response messages: "
            + respTranslateStreamToSvcElapsedTime + " for " + respMsgStreamToSvcCount + " messages; average "
            + computeAverage( respTranslateStreamToSvcElapsedTime, respMsgStreamToSvcCount ));
        LOG.info("Elapsed time for translating service-to-stream for initiation messages: "
            + initTranslateSvcToStreamElapsedTime + " for " + initMsgSvcToStreamCount + " messages; average "
            + computeAverage( initTranslateSvcToStreamElapsedTime, initMsgSvcToStreamCount ));
        LOG.info("Elapsed time for translating service-to-stream for response messages: "
            + respTranslateSvcToStreamElapsedTime + " for " + respMsgSvcToStreamCount + " messages; average "
            + computeAverage( respTranslateSvcToStreamElapsedTime, respMsgSvcToStreamCount ));

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);

    }

    protected float computeAverage(long dividend, long divisor) {

        if ( divisor != 0 ) {

            return dividend / divisor;

        } else {

            return Float.MAX_VALUE;

        }

    }

    protected static StringBuilder collectException(String methodName, String fileName, Throwable e) {

        StringBuilder message = new StringBuilder(ServiceHelper.convertExceptionToString(e));
        message.append("' in '").append(methodName).append("' converting the sample file ")
            .append(fileName).append(".").append(System.getProperty("line.separator"));

        return message;

    }

    public static void main(String[] args) {

        Thread threads[];

        int numThreads = 1;
        String numThreadsString = System.getProperty(BaseTestJAXBTranslator.class.getName() + ".threads");
        if ( numThreadsString != null ) {

            numThreads = Integer.parseInt(numThreadsString);

        }

        threads = new Thread[numThreads];

        for ( int i = 0; i < threads.length; ++i) {

            threads[i] = new Thread(new BaseTestJAXBTranslator());

        }

        for (int i = 0; i < threads.length; ++i) {

            threads[i].start();

        }

        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();

            } catch (InterruptedException e)  {

                LOG.info("Thread " + i + " interrupted; will wait for remaining threads.", e);

            }
        }

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);
        
    }

    protected void performXMLDiff(InputStream msgStream, StringBuilder failuresList, File file) throws ServiceException {

        String fileName = file.getName();

        try {

            File prettyPrintedXMLFile = File.createTempFile("BaseTestJAXBTranslator",".xml");
            prettyPrintedXMLFile.deleteOnExit();

            Writer outWriter = null;
            try {

                outWriter = new FileWriter(prettyPrintedXMLFile);

            } catch (IOException e) {

                throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

            }

            ToolkitHelper.prettyPrintXML(msgStream, outWriter);

            // Compare the two XML files
            // Because the org.diffxml.diffxml uses a GNU License, not an MIT license,
            // it is not provided as part of the Toolkit, we use reflection to invoke
            // it here so we can avoid problems compiling this class in the absence of that
            // package. Note that, if the package is absent this "diff" will be silently
            // skipped.
            try {
                // The following 3 lines or code are equivalent to this:
                // Diff diffInstance = DiffFactory.createDiff();
                Class<?> diffFactoryClass = Class.forName(DIFFXML_CLASS_NAME);

                try {

                    Method createDiffMethod = diffFactoryClass.getMethod("createDiff");
                    Object diffInstance = createDiffMethod.invoke(null);

                    // The following 2 lines of code are equivalent to this:
                    // Document delta = diffInstance.diff(file, temp);
                    Method diffMethod = diffInstance.getClass().getMethod("diff", File.class, File.class);
                    Document deltaDocument = (Document)diffMethod.invoke(diffInstance, file, prettyPrintedXMLFile);

                    if (deltaDocument.getDocumentElement().hasChildNodes()) {

                        if ( reportDiffAsXMLIndented ) {

                            OutputStream byteStream = new ByteArrayOutputStream();
                            // DOMOps.outputXMLIndented(deltaDocument, byteStream);
                            Class<?> DOMOpsClass = Class.forName("org.diffxml.diffxml.DOMOps");
                            Method outputXMLIndentedMethod = DOMOpsClass.getMethod("outputXMLIndented", Document.class, OutputStream.class);
                            outputXMLIndentedMethod.invoke(null, deltaDocument, byteStream);

                            failuresList.append(new StringBuilder()
                                .append("performXMLDiff detected differences for ").append(fileName).append(":\n")
                                .append('\t').append("Result XML file does not match input:\n")
                                .append(byteStream.toString())).append('\n');

                        } else {

                            try {
                                String[] parms = { file.getAbsolutePath(), prettyPrintedXMLFile.getAbsolutePath() };
                                String diffOutput = runCmd(diffCommand, parms);
                                if ( diffOutput != null && diffOutput.length() > 0 ) {

                                    failuresList.append(new StringBuilder()
                                        .append("performXMLDiff detected differences for ").append(fileName).append(":\n")
                                        .append('\t').append("Input XML file (on the left) is not matched ")
                                        .append("by result XML file (on the right):\n").append(diffOutput));

                                }

                            } catch (ServiceException e) {

                                failuresList.append(collectException("performXMLDiff",
                                    fileName, e));

                            }

                        }

                    }

                } catch (ClassNotFoundException e) {

                    failuresList.append(collectException("performXMLDiff",
                        fileName, e));

                } catch (InvocationTargetException e) {

                    failuresList.append(collectException("performXMLDiff",
                        fileName, e));

                } catch (NoSuchMethodException e) {

                    failuresList.append(collectException("performXMLDiff",
                        fileName, e));

                } catch (IllegalAccessException e) {

                    failuresList.append(collectException("performXMLDiff",
                        fileName, e));

                } catch (Exception e) {

                        failuresList.append(collectException("performXMLDiff",
                            fileName, e));

                }
            } catch (ClassNotFoundException e) {

                LOG.warn("ClassNotFoundException trying to load " + DIFFXML_CLASS_NAME
                    + ". Skipping diffXML comparison. Exception was:", e);

            } finally {

                prettyPrintedXMLFile.delete();

            }


        } catch (IOException e) {

            failuresList.append(collectException("performXMLDiff",
                fileName, e));

        }

    }

    public String runCmd(String command, String[] parms) throws ServiceException
    {
        String result = "";
        try
        {
            String osName = System.getProperty("os.name" );
            LOG.debug("OS name: " + osName);
            String[] cmd;
            int nextParm;
            if( osName.equals( "Windows NT" ) || osName.equals("Windows XP") )
            {
                cmd = new String[3 + parms.length];
                cmd[0] = "cmd.exe" ;
                cmd[1] = "/C" ;
                nextParm = 2;
            }
            else if( osName.equals( "Windows 95" ) )
            {
                cmd = new String[3 + parms.length];
                cmd[0] = "command.com" ;
                cmd[1] = "/C" ;
                nextParm = 2;
            }
            else // Assuming that else is unix or unix-like in this regard of not needing an explicit shell program.
            {
                cmd = new String[1 + parms.length];
                nextParm = 0;
            }

            // Example values to use for 'command' here:
            //   If using cygwin on a Windows OS:
            //     "c:\\cygwin\\bin\\telnet.exe 192.168.1.101"
            //     "c:\\cygwin\\bin\\expect.exe /tmp/telnettojbdesktop.exp"
            //   If using a Unix (I'm guessing here):
            //     "telnet 192.168.1.101"
            //     "expect /tmp/telnettojbdesktop.exp"
            cmd[nextParm++] = command;

            for ( String p : parms )
            {
                cmd[nextParm++] = p;
            }

            if ( LOG.isDebugEnabled() )
            {
                StringBuffer buffer = new StringBuffer();
                for ( String a : cmd )
                {
                    buffer.append(a).append(" ");
                }
                LOG.debug("Executing " + buffer.toString());
            }

            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);

            StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR");

            StreamGobbler outputGobbler = new
                StreamGobbler(proc.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            // This is the stream used to pass INPUT to the process. (It's called the "output stream" because
            // it's an *output* stream from the perspective of this class.)
            OutputStream outputStream = proc.getOutputStream();

            int exitVal = proc.waitFor();
            if ( exitVal != 0 )
            {
              LOG.error("Exit value from " + cmd[2] + ": " + exitVal);
            }

            String stdErr = errorGobbler.getBuffer();
            if ( stdErr.length() > 0 )
            {
                result = stdErr;
            }

            String stdOut = outputGobbler.getBuffer();
            if ( stdOut.length() > 0 )
            {
                // It's intentional that stdOut replaces what was in stdErr if there *is* anything in stdOut.
                result = stdOut;
            }
        }
        catch (Throwable t)
        {
          throw new ServiceException(ServiceError.RUNTIME_ERROR, t);
        }
        return result;
    }

    @Override
    public void run() {

        try {

            doTest();

        } catch (ToolkitException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }
    }

    class StreamGobbler extends Thread
    {
        InputStream is;
        String type;
        StringBuffer stringBuffer = new StringBuffer();

        StreamGobbler(InputStream is, String type)
        {
            this.is = is;
            this.type = type;
        }

        public void run()
        {
            try
            {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line=null;
                while ( (line = br.readLine()) != null)
                {
                    stringBuffer.append(line).append(System.getProperty("line.separator"));
                }
            }
            catch (IOException ioe)
            {
              ioe.printStackTrace();
            }
        }

        public String getBuffer()
        {
            return stringBuffer.toString();
        }
    }

}
