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
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

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

                throw new ToolkitException("IOException:", e);

            }

        }

        return writer.toString();

    }


    public static String formatDate(GregorianCalendar date) {

        String formattedDate = "";
        if ( date != null ) {

            formattedDate = (new SimpleDateFormat()).format(date.getTime());

        }
        return formattedDate;
        
    }

    public static InputStream getResourceOrFile(String resourceOrFileName) {

        InputStream inputStream = ToolkitHelper.class.getClassLoader().getResourceAsStream(resourceOrFileName);

        if (inputStream == null) {

            try {

                LOG.debug("Resource '" + resourceOrFileName + "' not found; trying as file.");

                inputStream = new FileInputStream(resourceOrFileName);

            } catch (FileNotFoundException e) {

                LOG.debug("FileNotFoundException loading file '" + resourceOrFileName + "'; returning null.", e);

            }

        }

        return inputStream;
        
    }


    public static List<String> createStringList(String schemaURLsCSV) {

        List<String> result;
        if ( schemaURLsCSV != null && ! schemaURLsCSV.isEmpty() ) {

            String[] schemaURLs = schemaURLsCSV.split(",");
            result = Arrays.asList(schemaURLs);

        } else {

            result = new ArrayList<String>(0);

        }

        return result;

    }

    public static String concatenateStrings(List<String> strings) {

        String result = "";

        if ( strings != null && ! strings.isEmpty() ) {

            StringBuilder sb = new StringBuilder();
            for ( String s : strings ) {

                sb.append(s).append(",");

            }

            result = sb.toString();
            result = result.substring(0, result.length() - 1);

        }

        return result;
    }


    public static void dumpProperties(Logger log, String heading, Properties properties) {

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {

          log.debug(entry.getKey() + "=" + entry.getValue());

        }

    }
}
