/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public final class XMLHelper {

    private static final Logger LOG = Logger.getLogger(XMLHelper.class);

    private XMLHelper() {

        // Private constructor to prevent instantiation.

    }
    
    public static Schema loadSchema(List<String> schemaURLs) {

        Schema schema = null;

        if (schemaURLs != null && schemaURLs.size() > 0) {

            int schemaCount = schemaURLs.size();
            LOG.debug(schemaCount + " schema URLs were found for validating messages.");
            StreamSource[] schemaSources = new StreamSource[schemaCount];

            try {

                int schemaIndex = 0;
                for (String schemaURL : schemaURLs) {

                    LOG.info("Loading schema '" + schemaURL + "'.");
                    URL url = Thread.currentThread().getContextClassLoader().getResource(schemaURL);
                    StreamSource streamSource = new StreamSource(url.openStream());
                    String systemId = url.toURI().toString();
                    LOG.info("Setting system id to '" + systemId + "'.");
                    streamSource.setSystemId(systemId);
                    schemaSources[schemaIndex++] = streamSource;
                    LOG.info("Loaded schema '" + schemaURL + "'.");

                }

                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

                LOG.info("Setting schema sources.");
                schema = schemaFactory.newSchema(schemaSources);

            } catch (SAXException e) {

                LOG.warn("SAXException creating the Schema object for marshaling.", e);

            } catch (MalformedURLException e) {

                LOG.warn("MalformedURLException creating the Schema object for marshaling.", e);

            } catch (URISyntaxException e) {

                LOG.warn("URISyntaxException creating the Schema object for marshaling.", e);

            } catch (IOException e) {

                LOG.warn("IOException creating the Schema object for marshaling.", e);

            }


            if ( schema == null ) {

                LOG.warn("Schema is null; messages will not be validated against the schema.");

            }

        } else {

            LOG.warn("schemaURLs is null or the list is empty; messages will not be validated against the schema.");

        }

        return schema;

    }
}
