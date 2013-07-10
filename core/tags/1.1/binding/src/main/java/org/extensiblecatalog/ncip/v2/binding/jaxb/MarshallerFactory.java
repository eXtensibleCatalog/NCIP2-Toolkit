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

import javax.xml.bind.*;
import javax.xml.validation.Schema;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarshallerFactory {

    private static final Logger LOG = Logger.getLogger(MarshallerFactory.class);

    protected final Map<String /* schemaURLsCSV */, Schema> schemasByURLsCSV = new HashMap<String, Schema>();

    public MarshallerFactory() throws ToolkitException {

    }

    public MarshallerFactory(TranslatorConfiguration config) {

        List<String> schemaURLs = ((NCIP2TranslatorConfiguration)config).getSchemaURLs();

        if ( schemaURLs != null && ! schemaURLs.isEmpty() ) {

            String schemaURLsCSV = ToolkitHelper.concatenateStrings(schemaURLs);
            if ( ! schemasByURLsCSV.containsKey(schemaURLsCSV) ) {

                Schema schema = XMLHelper.loadSchema(schemaURLs);
                schemasByURLsCSV.put(schemaURLsCSV, schema);

            }

        }

    }

    /**
     *
     * @param packageNames colon-separated list of package names for the JAXBContext
     * @return the JAXBContext
     */
    protected JAXBContext getJAXBContext(String packageNames) {

        JAXBContext jaxbContext;

        try {

            jaxbContext = JAXBContextFactory.getJAXBContext(packageNames);

        } catch (ToolkitException e) {

            LOG.error("Exception getting JAXBContext:", e);
            throw new ExceptionInInitializerError(e);

        }

        return jaxbContext;

    }

    // TODO: Pool marshallers (they're not threadsafe so can't share them).
    public Marshaller createMarshaller(ServiceContext serviceContext) throws JAXBException {

        Marshaller marshaller;
        if ( serviceContext instanceof NCIPv201ServiceContext) {

            NCIPv201ServiceContext ncipServiceContext = (NCIPv201ServiceContext)serviceContext;
            marshaller = getJAXBContext(ncipServiceContext.getPackageNames()).createMarshaller();

            Schema schema = getSchema(ncipServiceContext.getSchemaURLs());
            if ( schema != null ) {

                marshaller.setSchema(schema);

            }

            PreferredMapper mapper = new PreferredMapper(ncipServiceContext);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);

        } else {

            // Use defaults
            marshaller = getJAXBContext(NCIPServiceValidatorConfiguration.PACKAGE_NAMES_DEFAULT).createMarshaller();

        }

        return marshaller;
    }

    // TODO: Pool unmarshallers (they're not threadsafe so can't share them).
    public Unmarshaller createUnmarshaller(ServiceContext serviceContext) throws JAXBException {

        Unmarshaller unmarshaller;
        if ( serviceContext instanceof NCIPv201ServiceContext) {

            NCIPv201ServiceContext ncipServiceContext = (NCIPv201ServiceContext)serviceContext;
            unmarshaller = getJAXBContext(ncipServiceContext.getPackageNames()).createUnmarshaller();

            Schema schema = getSchema(ncipServiceContext.getSchemaURLs());
            if ( schema != null ) {

                unmarshaller.setSchema(schema);

            }

        } else {

            // Use defaults
            unmarshaller = getJAXBContext(NCIPServiceValidatorConfiguration.PACKAGE_NAMES_DEFAULT).createUnmarshaller();

        }

        return unmarshaller;

    }

    protected Schema getSchema(List<String> schemaURLs) {

        Schema schema = null;

        if ( schemaURLs != null && ! schemaURLs.isEmpty() ) {

            String schemaURLsCSV = ToolkitHelper.concatenateStrings(schemaURLs);
            if ( schemasByURLsCSV.containsKey(schemaURLsCSV) ) {

                schema = schemasByURLsCSV.get(schemaURLsCSV);

            } else {

                schema = XMLHelper.loadSchema(schemaURLs);
                schemasByURLsCSV.put(schemaURLsCSV, schema);

            }

        }

        return schema;

    }

    public class PreferredMapper extends com.sun.xml.bind.marshaller.NamespacePrefixMapper {

        protected String[] namespaceURIs;
        protected String defaultNamespace;

        public PreferredMapper(NCIPv201ServiceContext serviceContext) {

            this.namespaceURIs = serviceContext.getNamespaceURIs();
            this.defaultNamespace = serviceContext.getDefaultNamespace();
            
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
