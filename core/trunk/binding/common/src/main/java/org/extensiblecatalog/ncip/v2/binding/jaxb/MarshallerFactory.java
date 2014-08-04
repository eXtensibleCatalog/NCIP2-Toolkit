/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.NCIP2TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class MarshallerFactory {

    private static final Logger LOG = Logger.getLogger(MarshallerFactory.class);

    protected final Map<String /* CanonicalSchemaURLsCSVList */, Schema> schemasByCanonicalURLsCSVList
        = new HashMap<String, Schema>();

    protected Map<String /* Known Alias */, String /* Canonical URL */> canonicalSchemaURLMap
        = new HashMap<String, String>();

    protected Map<String /* Canonical Schema URL */, String /* JAXB Package name */> schemaURLsToPackageMap
        = new HashMap<String, String>();

    public MarshallerFactory() throws ToolkitException {

    }

    public MarshallerFactory(TranslatorConfiguration config) {

        NCIP2TranslatorConfiguration ncipConfig = (NCIP2TranslatorConfiguration)config;

        Map<String, String> schemaURLsToPackageMap = ncipConfig.getSchemaURLsToPackageMap();
        if ( schemaURLsToPackageMap != null && ! schemaURLsToPackageMap.isEmpty() ) {

            this.schemaURLsToPackageMap = schemaURLsToPackageMap;

        }

        Map<String, String> canonicalSchemaURLMap = ncipConfig.getCanonicalSchemaURLMap();
        if ( canonicalSchemaURLMap != null && ! canonicalSchemaURLMap.isEmpty() ) {

            this.canonicalSchemaURLMap = canonicalSchemaURLMap;

        }
    }

    public void setSchemaURLsToPackageMap(Map<String, String> schemaURLsToPackageMap) {

        this.schemaURLsToPackageMap = schemaURLsToPackageMap;

    }

    public void setCanonicalSchemaURLMap(Map<String, String> canonicalSchemaURLMap) {

        this.canonicalSchemaURLMap = canonicalSchemaURLMap;

    }

    /**
     *
     * @param schemaURLsList {@link List} of XML schema URLs for the JAXBContext. This method calls
     * {@link #canonicalizeSchemaURL(String)} on supportedSchemaURLs and looks in its map of SchemaURLsToPackageNames
     * for the package names from the {@link TranslatorConfiguration} that are to be used to marshall/unmarshall
     * documents using those URLs, and then gets a {@link JAXBContext} for those packages.
     *
     * @return the JAXBContext
     */
    protected JAXBContext getJAXBContext(List<String> schemaURLsList) {

        JAXBContext jaxbContext;

        List<String> packageNameList = new ArrayList<String>();
        if ( schemaURLsList != null && ! schemaURLsList.isEmpty() ) {
            for ( String schemaURL : schemaURLsList ) {

                String packageName = getPackageNameForSchemaURL(schemaURL);
                if ( packageName != null && ! packageName.isEmpty() && ! packageNameList.contains(packageName) ) {

                    packageNameList.add(packageName);

                }

            }
        }

        String colonSeparatedPackageNames = ToolkitHelper.concatenateStrings(packageNameList, ":");
        try {

            jaxbContext = JAXBContextFactory.getJAXBContext(colonSeparatedPackageNames);

        } catch (ToolkitException e) {

            LOG.error("Exception getting JAXBContext:", e);
            throw new ExceptionInInitializerError(e);

        }

        return jaxbContext;

    }

    // TODO: Pool marshallers (they're not threadsafe so can't share them).
    public Marshaller getMarshaller(ServiceContext serviceContext) throws ToolkitException {

        Marshaller marshaller;
        if ( serviceContext instanceof NCIPServiceContext) {

            NCIPServiceContext ncipServiceContext = (NCIPServiceContext)serviceContext;
            try {

                List<String> schemaURLsList = ncipServiceContext.getSchemaURLs();
                marshaller = getJAXBContext(schemaURLsList).createMarshaller();

                if ( ncipServiceContext.validateMessagesAgainstSchema() ) {
                    Schema schema = getSchema(ncipServiceContext.getSchemaURLs());
                    if ( schema != null ) {

                        marshaller.setSchema(schema);

                    }
                }

                PreferredMapper mapper = new PreferredMapper(ncipServiceContext);
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);

            } catch (JAXBException e) {

                throw new ToolkitException("JAXBException creating Marshaller.", e);

            }

        } else {

            throw new ToolkitException("ServiceContext of '" + serviceContext.getClass().getName()
                + "' not supported; must be an instance of NCIPServiceContext.");

        }

        return marshaller;
    }

    // TODO: Pool unmarshallers (they're not threadsafe so can't share them).
    public Unmarshaller getUnmarshaller(ServiceContext serviceContext) throws ToolkitException {

        Unmarshaller unmarshaller;
        if ( serviceContext instanceof NCIPServiceContext) {

            try {

                NCIPServiceContext ncipServiceContext = (NCIPServiceContext)serviceContext;
                unmarshaller = getJAXBContext(ncipServiceContext.getSchemaURLs()).createUnmarshaller();

                if ( ncipServiceContext.validateMessagesAgainstSchema() ) {
                    Schema schema = getSchema(ncipServiceContext.getSchemaURLs());
                    if ( schema != null ) {

                        unmarshaller.setSchema(schema);

                    }
                }

            } catch (JAXBException e) {

                throw new ToolkitException("JAXBException creating Unmarshaller.", e);

            }

        } else {

            throw new ToolkitException("ServiceContext of '" + serviceContext.getClass().getName()
                + "' not supported; must be an instance of NCIPServiceContext.");

        }

        return unmarshaller;

    }

    protected Schema getSchema(List<String> schemaURLs) {

        Schema schema = null;

        if ( schemaURLs != null && ! schemaURLs.isEmpty() ) {

            List<String> canonicalSchemaURLsList = new ArrayList<String>(schemaURLs.size());
            for ( String schemaURL : schemaURLs ) {

                canonicalSchemaURLsList.add(canonicalizeSchemaURL(schemaURL));

            }
            Collections.sort(canonicalSchemaURLsList);

            String schemaURLsCSV = ToolkitHelper.concatenateStrings(canonicalSchemaURLsList, ",");
            if ( schemasByCanonicalURLsCSVList.containsKey(schemaURLsCSV) ) {

                schema = schemasByCanonicalURLsCSVList.get(schemaURLsCSV);

            } else {

                schema = loadSchema(schemaURLs);
                schemasByCanonicalURLsCSVList.put(schemaURLsCSV, schema);

            }

        }

        return schema;

    }

    protected String canonicalizeSchemaURL(String inSchemaURL) {

        String canonicalSchemaURL = inSchemaURL;
        if ( canonicalSchemaURLMap.containsKey(inSchemaURL) ) {

            canonicalSchemaURL = canonicalSchemaURLMap.get(inSchemaURL);

        }

        return canonicalSchemaURL;

    }


    protected String getPackageNameForSchemaURL(String schemaURL) {

        String canonicalSchemaURL = canonicalizeSchemaURL(schemaURL);
        return schemaURLsToPackageMap.get(canonicalSchemaURL);

    }


    protected static Schema loadSchema(List<String> schemaURLs) {

        Schema schema = null;

        if (schemaURLs != null && schemaURLs.size() > 0) {

            int schemaCount = schemaURLs.size();
            LOG.debug(schemaCount + " schema URLs were found for validating messages.");
            StreamSource[] schemaSources = new StreamSource[schemaCount];

            try {

                int schemaIndex = 0;
                for (String schemaURL : schemaURLs) {

                    StreamSource streamSource;
                    String systemId;
                    if ( schemaURL.startsWith("http") ) {

                        LOG.info("Loading schema '" + schemaURL + "' as a network resource.");
                        URL url = new URL(schemaURL);
                        streamSource = new StreamSource(url.openStream());
                        systemId = url.toURI().toString();

                    } else {

                        LOG.info("Loading schema '" + schemaURL + "' as a resource via ClassLoader or as a file.");
                        streamSource = new StreamSource(ToolkitHelper.getResourceOrFile(schemaURL));
                        systemId = schemaURL;

                    }

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

            LOG.warn("supportedSchemaURLs is null or the list is empty; messages can not be validated against the schema.");

        }

        return schema;

    }

    public class PreferredMapper extends com.sun.xml.bind.marshaller.NamespacePrefixMapper {

        protected String[] namespaceURIs;
        protected String defaultNamespace;

        public PreferredMapper(NCIPServiceContext serviceContext) {

            this.namespaceURIs = serviceContext.getNamespaceURIs();
            for ( String uri : this.namespaceURIs ) {
                uri.intern();
            }
            this.defaultNamespace = serviceContext.getDefaultNamespace();
            this.defaultNamespace.intern();
            
        }

        @Override
        public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

            // This approach per http://java.net/projects/jaxb/lists/users/archive/2009-08/message/31
            if((defaultNamespace != null) && (!requirePrefix) && (namespaceUri != null))
            {
                    if(defaultNamespace.equals(namespaceUri))
                            return ""; // this is our targetNamespace
            }

            /** Let JAXB pick a prefix. */
            return null;
        }

        @Override
        public String[] getPreDeclaredNamespaceUris() {
            return namespaceURIs;
        }
    }

}
