/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.io.*;

public class LoggingHelper {

    // Size of byte array used when copying stream
    protected final static int ARRAY_SIZE = 1024;

    /**
     * If logging is enabled for level, copy the bytes from the inputStream and write them to the log, returning
     * a new InputStream made from the copied bytes; if logging is not enabled then simply return the inputStream.
     *
     * @param log
     * @param level
     * @param inputStream
     * @return
     */
    public static <S extends InputStream> S copyAndLogStream(Logger log, Level level, S inputStream) {

        boolean isEnabled = log.isEnabledFor(level);

        if ( isEnabled ) {

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

            // TODO: Add config parm for this
            if ( true ) {

                StringWriter strWriter = new StringWriter();
                try {

                    ToolkitHelper.prettyPrintXML(loggingCopy, strWriter);
                    log.log(level, strWriter.toString());

                } catch (ServiceException e) {

                    log.warn("ServiceException prettyPrinting the InputStream to a String for logging.", e);

                }

            } else {

                try {

                    log.log(level, ToolkitHelper.convertStreamToString(loggingCopy));

                } catch (ToolkitException e) {

                    log.warn("ToolkitException converting the copy of the InputStream to a String for logging.", e);

                }

            }

            return (S)(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        } else {

            return inputStream;

        }

    }
}
