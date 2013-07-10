/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
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
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolkitHelper {

    private static final Logger LOG = Logger.getLogger(ToolkitHelper.class);

    public static String convertStreamToString(InputStream inStream) throws ToolkitException
    {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(inStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            throw new ToolkitException("UnsupportedEncodingException: ", e);
        } catch (IOException e) {
            throw new ToolkitException("IOException: .", e);
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {

                LOG.warn("IOException:", e);

            }

        }

        return writer.toString();

    }

    /**
     * Render the XML in the responseStream to a {@link Writer} in pretty-printed format.
     * Note: This closes the msgStream when it is finished reading from it and the outWriter when it is finished
     * writing to it.
     * @param msgStream the {@link InputStream} holding the XML document
     * @param outWriter the {@link Writer} to write the pretty-printed XML to
     * @throws ServiceException if an error occurs
     */
    public static void prettyPrintXML(InputStream msgStream, Writer outWriter) throws ServiceException {

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

    /**
     * A convenience method
     * @param resourceOrFileName
     * @return
     */
    public static InputStream getResourceOrFile(String resourceOrFileName) {

        return getResourceOrFile(resourceOrFileName, true);

    }

    /**
     * Load a resource (via {@link ClassLoader#getResourceAsStream(String)}) and if that fails as a file (via
     * {@link FileInputStream(String)}.
     * @param resourceOrFileName the name of the resource
     * @param logExceptionStackTrace  true if the stack trace of the exception should be logged.
     * @return
     */
    public static InputStream getResourceOrFile(String resourceOrFileName, boolean logExceptionStackTrace) {

        InputStream inputStream = ToolkitHelper.class.getClassLoader().getResourceAsStream(resourceOrFileName);

        if (inputStream == null) {

            try {

                LOG.debug("Resource '" + resourceOrFileName + "' not found; trying as file.");

                inputStream = new FileInputStream(resourceOrFileName);

            } catch (FileNotFoundException e) {

                if ( logExceptionStackTrace ) {

                    LOG.debug("FileNotFoundException loading file '" + resourceOrFileName + "'; returning null.", e);

                } else {

                    LOG.debug("FileNotFoundException loading file '" + resourceOrFileName + "'; returning null.");

                }

            }

        }

        return inputStream;
        
    }


    public static List<String> createStringList(String csvString) {

        List<String> result;
        if ( csvString != null && ! csvString.isEmpty() ) {

            String[] schemaURLs = csvString.split(", ?");
            result = Arrays.asList(schemaURLs);

        } else {

            result = new ArrayList<String>(0);

        }

        return result;

    }

    /**
     * Concatenate the Strings in the List, putting the separator between each but not after the last.
     * @param strings
     * @param separator
     * @return
     */
    public static String concatenateStrings(List<String> strings, String separator) {

        String result = "";

        if ( strings != null && ! strings.isEmpty() ) {

            StringBuilder sb = new StringBuilder();
            for ( String s : strings ) {

                sb.append(s).append(separator);

            }

            result = sb.toString();
            result = result.substring(0, result.length() - separator.length());

        }

        return result;
    }

    /**
     * Add all properties from a properties file named by propertiesFileName and read from the classpath or,
     * if not found on the classpath, from the filesystem. If the file is not found a message will be written
     * to the log at debug level; if there is an IOException loading the properties a message will be written
     * to the log at warn level.
     * @param properties the {@link Properties} into which the properties from the file will be loaded.
     * @param propertiesFileName
     */
    public static void setPropertiesFromClasspathOrFilesystem(Properties properties, String propertiesFileName) {

        LOG.debug("Trying to load " + propertiesFileName);

        InputStream inputStream = getResourceOrFile(propertiesFileName, false);

        if ( inputStream != null ) {

            try {

                LOG.debug("Adding properties from " + propertiesFileName);

                Properties newProperties = new Properties();
                newProperties.load(inputStream);

                if ( LOG.isDebugEnabled() ) {

                    LOG.debug("New properties from '" + propertiesFileName + "':");
                    dumpProperties(LOG, newProperties);

                    LOG.debug("Properties that will be replaced from '" + propertiesFileName + "':");
                    logReplacedProperties(LOG, properties, newProperties);

                }

                properties.putAll(newProperties);

            } catch (IOException e) {

                LOG.warn("IOException loading properties from file '" + propertiesFileName + "'.", e);

            }

        }

    }

    public static void dumpProperties(Logger log, Properties properties) {

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {

          log.debug(entry.getKey() + "=" + entry.getValue());

        }

    }

    public static void logReplacedProperties(Logger log, Properties existingProperties, Properties newProperties) {

        boolean replacementsFound = false;
        for (Map.Entry<Object, Object> entry : newProperties.entrySet()) {

            if ( existingProperties.contains(entry.getKey()) ) {

                replacementsFound = true;

                log.debug("For key '" + entry.getKey() + "', existing value of '"
                        + existingProperties.get(entry.getKey())
                        + "' will be replaced by '" + entry.getValue() + ".");

            }

        }

        if ( ! replacementsFound ) {

            log.debug("No keys in the new properties matched the keys of existing properties.");

        }

    }
}
