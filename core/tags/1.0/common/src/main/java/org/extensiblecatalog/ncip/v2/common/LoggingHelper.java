/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;

import java.io.*;

public class LoggingHelper {

    // Size of byte array used when copying stream
    protected final static int ARRAY_SIZE = 1024;

    public static InputStream copyAndLogStream(Logger log, InputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            int chunk = 0;
            byte[] data = new byte[ARRAY_SIZE];

            while (-1 != (chunk = inputStream.read(data))) {
                byteArrayOutputStream.write(data, 0, chunk);
            }
        } catch (IOException e) {
            log.warn("IOException copying the initiation message's InputStream for logging.", e);
        }

        InputStream loggingCopy = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        Writer writer = new StringWriter();
        char[] buffer = new char[ARRAY_SIZE];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(loggingCopy, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            log.warn("UnsupportedEncodingException writing the initiation message to the log.", e);
        } catch (IOException e) {
            log.warn("IOException writing the initiation message to the log.", e);
        } finally {
            try {
                loggingCopy.close();
            } catch (IOException e) {

                log.warn("IOException closing the copy of the initiation message made for logging.", e);

            }

        }

        log.info(writer.toString());

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

    }

}
