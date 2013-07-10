/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DefaultNCIP2TranslatorConfiguration extends DefaultTranslatorConfiguration implements NCIP2TranslatorConfiguration {

	private static final Logger LOG = Logger.getLogger(DefaultNCIP2TranslatorConfiguration.class);

    /** Default constructor */
	public DefaultNCIP2TranslatorConfiguration() {

        super();

	}

    public DefaultNCIP2TranslatorConfiguration(Properties properties) throws ToolkitException {

        super(properties);

        if ( properties != null ) {

            String schemaURLsString = this.properties.getProperty(NCIP2TranslatorConfiguration.SCHEMA_URLS_KEY,
                NCIP2TranslatorConfiguration.SCHEMA_URLS_DEFAULT);

            if ( schemaURLsString != null ) {

                String[] schemaURLsArray = schemaURLsString.split(",");
                schemaURLs = new ArrayList<String>(Arrays.asList(schemaURLsArray));

            } else {

                throw new ToolkitException("NCIP2TranslatorConfiguration.SCHEMA_URLS_DEFAULT is null," +
                    " no default schema URLs are available.");

            }

        }

    }

    protected List<String> schemaURLs;

    @Override
    public List<String> getSchemaURLs() {

        return schemaURLs;

    }

    @Override
    public void setSchemaURLs(List<String> urls) {

        this.schemaURLs = urls;

    }

}
