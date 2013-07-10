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
import org.apache.log4j.PropertyConfigurator;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.JAXBDozerTranslator;
import org.extensiblecatalog.ncip.v2.common.LoggingHelper;
import org.extensiblecatalog.ncip.v2.common.ToolkitStatisticsBean;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestJAXBTranslator implements Runnable {

    private static final Logger LOG = Logger.getLogger(TestJAXBTranslator.class);

    private static final String DEFAULT_FILENAMES_PATTERN = ".*\\.xml";

    private static final String DEFAULT_FILENAMES_TO_OMIT_PATTERN = null;

    protected static boolean performXMLDiff = Boolean.getBoolean(TestJAXBTranslator.class.getName()
        + ".performXMLDiff");

    protected static boolean reportDiffAsXMLIndented = Boolean.getBoolean(TestJAXBTranslator.class.getName() 
        + ".reportDiffAsXMLIndented");

    protected static String diffCommand = System.getProperty(TestJAXBTranslator.class.getName()
        + ".diffCommand", "diff -w") + " "; // Make sure there's a trailing space
                                            // to keep the filenames distinct from the command.

    protected boolean logMessages = false;

    protected static final String LOG4J_CONFIG_FILENAME = "src/test/resources/log4j.properties";
    protected static final String APP_CONFIG_FILENAME = "src/test/resources/testconfig.xml";
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

    @Test
    public void testSampleFiles() throws FileNotFoundException {

        doTest();

        String statsReport = statisticsBean.createCSVReport();
        System.out.println(statsReport);

    }

    public void doTest() throws FileNotFoundException {

        String sampleFilesDirectory = System.getProperty(TestJAXBTranslator.class.getName() + ".sampleFilesDir",
                "src/test/data/sampleNCIPMessages");
        if (sampleFilesDirectory == null) {
            Assert.fail("Test failed because system property " + TestJAXBTranslator.class.getName() +
                    ".sampleFilesDir was not set. It must be set to a directory containing sample NCIP messages.");
        }

        String fileNamesPattern = System.getProperty(TestJAXBTranslator.class.getName() + ".fileNamesPattern",
            DEFAULT_FILENAMES_PATTERN);

        String fileNamesToOmitPattern = System.getProperty(TestJAXBTranslator.class.getName() + ".fileNamesToOmitPattern",
            DEFAULT_FILENAMES_TO_OMIT_PATTERN);


        int iterations = 1;

        String iterationsString = System.getProperty(TestJAXBTranslator.class.getName() + ".iterations");
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

                    if ( fileNamesPattern == null || fileNamesPattern.length() == 0
                        || fileName.matches(fileNamesPattern)
                        && ( fileNamesToOmitPattern == null || ! fileName.matches(fileNamesToOmitPattern))) {

                        InputStream inStream = new FileInputStream(file);

                        LOG.info("Testing " + fileName);
                        
                        if (fileName.contains("Response")) {

                            try {

                                startTime = System.currentTimeMillis();

                                NCIPResponseData responseData = translator.createResponseData(inStream);

                                endTime = System.currentTimeMillis();
                                respTranslateStreamToSvcElapsedTime += (endTime - startTime);
                                respMsgStreamToSvcCount++;

                                Assert.assertNotNull("createResponseData returned null for " + fileName + ".",
                                    responseData);

                                if (responseData != null) {

                                    try {

                                        startTime = System.currentTimeMillis();

                                        InputStream responseStream = translator.createResponseMessageStream(responseData);

                                        if (logMessages) {

                                            LoggingHelper.copyAndLogStream(LOG, responseStream);

                                        }

                                        endTime = System.currentTimeMillis();
                                        respTranslateSvcToStreamElapsedTime += (endTime - startTime);
                                        respMsgSvcToStreamCount++;

                                        Assert.assertNotNull(responseStream);

                                        if (performXMLDiff) {

                                            performXMLDiff(responseStream, failuresList, file);

                                        }

                                    } catch (ServiceException e) {

                                        failuresList.append(collectException("createResponseMessageStream", fileName, e));

                                    }

                                }

                            } catch (ServiceException e) {

                                failuresList.append(collectException("createResponseData", fileName, e));

                            }

                        } else {

                            try {

                                startTime = System.currentTimeMillis();

                                NCIPInitiationData initiationData = translator.createInitiationData(inStream);

                                endTime = System.currentTimeMillis();
                                initTranslateStreamToSvcElapsedTime += (endTime - startTime);
                                initMsgStreamToSvcCount++;

                                Assert.assertNotNull("createInitiationData returned null for " + fileName + ".", initiationData);

                                if (initiationData != null) {

                                    try {

                                        startTime = System.currentTimeMillis();

                                        InputStream initiationStream = translator.createInitiationMessageStream(initiationData);

                                        if (logMessages) {

                                            LoggingHelper.copyAndLogStream(LOG, initiationStream);

                                        }

                                        Assert.assertNotNull(initiationStream);

                                        endTime = System.currentTimeMillis();
                                        initTranslateSvcToStreamElapsedTime += (endTime - startTime);
                                        initMsgSvcToStreamCount++;

                                        if (performXMLDiff) {

                                            performXMLDiff(initiationStream, failuresList, file);

                                        }

                                    } catch (ServiceException e) {

                                        failuresList.append(collectException("createInitiationMessageStream", fileName, e));

                                    }

                                }

                            } catch (ServiceException e) {

                                failuresList.append(collectException("createInitiationData", fileName, e));

                            }

                        }

                    }

                }

            }

            Assert.assertTrue("One or more messages failed:\n" + failuresList, failuresList.length() == 0);

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

    }

    protected float computeAverage(long dividend, long divisor) {

        if ( divisor != 0 ) {

            return dividend / divisor;

        } else {

            return Float.MAX_VALUE;

        }

    }

    protected static StringBuilder collectException(String methodName, String fileName, Throwable e) {

        StringBuilder message = new StringBuilder();
        message.append("Exception '").append(e.getClass().getName()).append("': '")
            .append(e.getLocalizedMessage()).append("' in '").append(methodName).append("' converting the sample file ")
            .append(fileName).append(":\n");

        Throwable cause = e.getCause();

        while (cause != null) {

            if (cause.getLocalizedMessage() == null) {

                message.append('\t').append(cause.getClass().getName()).append('\n');

            } else {

                message.append('\t').append(cause.getLocalizedMessage()).append('\n');

            }

            cause = cause.getCause();

        }

        return message;

    }

    public static void main(String[] args) {

        Thread threads[];

        int numThreads = 1;
        String numThreadsString = System.getProperty(TestJAXBTranslator.class.getName() + ".threads");
        if ( numThreadsString != null ) {

            numThreads = Integer.parseInt(numThreadsString);

        }

        threads = new Thread[numThreads];

        for ( int i = 0; i < threads.length; ++i) {

            threads[i] = new Thread(new TestJAXBTranslator());

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

    /**
     * Render the XML in the responseStream to a temp file in pretty-printed format.
     * Note: This closes the msgStream when it is finished writing to the output file.
     * @param msgStream
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    protected void prettyPrintXML(InputStream msgStream, File prettyPrintedXMLFile) throws ServiceException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        final Document document;

        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(msgStream);
            document = db.parse(is);

        } catch (ParserConfigurationException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        } catch (SAXException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        } catch (IOException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        }

        try {

            msgStream.close();

        } catch (IOException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        }

        Writer outWriter = null;
        try {

            outWriter = new FileWriter(prettyPrintedXMLFile);

        } catch (IOException e) {

            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

        }

        // This code from http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/ 10 May 2011.
        // Note that the "format-pretty-print" DOM configuration parameter can only be set in JDK 1.6+.
        DOMImplementation domImplementation = document.getImplementation();
        if (domImplementation.hasFeature("LS", "3.0") && domImplementation.hasFeature("Core", "2.0")) {
            DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementation.getFeature("LS", "3.0");
            LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
            DOMConfiguration domConfiguration = lsSerializer.getDomConfig();
            if (domConfiguration.canSetParameter("format-pretty-print", Boolean.TRUE)) {
                lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
                LSOutput lsOutput = domImplementationLS.createLSOutput();
                lsOutput.setEncoding("UTF-8");
                lsOutput.setCharacterStream(outWriter);
                lsSerializer.write(document, lsOutput);
            } else {
                throw new ServiceException(ServiceError.RUNTIME_ERROR, "DOMConfiguration 'format-pretty-print' parameter isn't settable.");
            }
        } else {
            throw new ServiceException(ServiceError.RUNTIME_ERROR, "DOM 3.0 LS and/or DOM 2.0 Core not supported.");
        }

        try {
            outWriter.close();
        } catch (IOException e) {
            LOG.warn("Error closing outWriter", e);
        }

    }

    protected void performXMLDiff(InputStream msgStream, StringBuilder failuresList, File file) throws ServiceException {

        String fileName = file.getName();

        try {

            File prettyPrintedXMLFile = File.createTempFile("TestJAXBTranslator",".xml");
            prettyPrintedXMLFile.deleteOnExit();
            prettyPrintXML(msgStream, prettyPrintedXMLFile);

            // Compare the two XML files
            // Because the org.diffxml.diffxml uses a GNU License, not an MIT license,
            // it is not provided as part of the Toolkit, we use reflection to invoke
            // it here so we can avoid problems compiling this class in the absence of that
            // package. Note that, if the package is absent this "diff" will be silently
            // skipped.
            try {

                // The following 3 lines or code are equivalent to this:
                // Diff diffInstance = DiffFactory.createDiff();
                Class diffFactoryClass = Class.forName("org.diffxml.diffxml.DiffFactory");
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
                        Class DOMOpsClass = Class.forName("org.diffxml.diffxml.DOMOps");
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
                LOG.error("The following was captured from stderr:");
                LOG.error(stdErr);
                result = stdErr;
            }

            String stdOut = outputGobbler.getBuffer();
            if ( stdOut.length() > 0 )
            {
                LOG.info("The following was captured from stdout:");
                LOG.info(stdOut);
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
