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

import java.util.*;

public class DefaultNCIP2TranslatorConfiguration extends DefaultTranslatorConfiguration
    implements NCIP2TranslatorConfiguration {

	  private static final Logger LOG = Logger.getLogger(DefaultNCIP2TranslatorConfiguration.class);


    protected Map<String, String> schemaURLsToPackageMap;

    protected Map<String, String> canonicalSchemaURLMap;

    public DefaultNCIP2TranslatorConfiguration() throws ToolkitException {

        this(null, null);

	}

    public DefaultNCIP2TranslatorConfiguration(String appName) throws ToolkitException {

        this(appName, null);

	}

    public DefaultNCIP2TranslatorConfiguration(Properties properties) throws ToolkitException {

        this(null, properties);

	}

    public DefaultNCIP2TranslatorConfiguration(String appName, Properties properties) throws ToolkitException {

        super(appName, properties);

        if ( properties != null ) {

            String schemaURLsToPackageMapString = this.properties.getProperty(
                NCIP2TranslatorConfiguration.SCHEMA_URLS_TO_PACKAGE_MAP_KEY,
                NCIP2TranslatorConfiguration.SCHEMA_URLS_TO_PACKAGE_MAP_DEFAULT);

            if ( schemaURLsToPackageMapString != null ) {

                schemaURLsToPackageMap = new HashMap<String, String>();
                String[] schemaURLsToPackageArray = schemaURLsToPackageMapString.split(",");
                for ( String schemaURLtoPackageString : schemaURLsToPackageArray ) {

                    String[] schemaURLAndPackage = schemaURLtoPackageString.split("=");
                    if ( schemaURLAndPackage.length == 2 ) {

                        schemaURLsToPackageMap.put(schemaURLAndPackage[0], schemaURLAndPackage[1]);

                    } else {

                        throw new ToolkitException(NCIP2TranslatorConfiguration.SCHEMA_URLS_TO_PACKAGE_MAP_KEY
                            + " has invalid format: '" + schemaURLsToPackageMapString + "';"
                            + " format should be 'schemaURL=packagename'. E.g. '"
                            + NCIP2TranslatorConfiguration.SCHEMA_URLS_TO_PACKAGE_MAP_DEFAULT + "'.");

                    }

                }

            } else {

                throw new ToolkitException(NCIP2TranslatorConfiguration.SCHEMA_URLS_TO_PACKAGE_MAP_KEY
                    + " property is set to null.");

            }

            String canonicalSchemaURLMapString = this.properties.getProperty(
                NCIP2TranslatorConfiguration.CANONICAL_SCHEMA_URL_MAP_KEY,
                NCIP2TranslatorConfiguration.CANONICAL_SCHEMA_URL_MAP_DEFAULT);

            if ( canonicalSchemaURLMapString != null ) {

                canonicalSchemaURLMap = new HashMap<String, String>();
                String[] canonicalSchemaURLArray = canonicalSchemaURLMapString.split(",");
                for ( String aliasAndSchemaURLString : canonicalSchemaURLArray ) {

                    String[] aliasAndSchemaURL = aliasAndSchemaURLString.split("=");
                    if ( aliasAndSchemaURL.length == 2 ) {

                        canonicalSchemaURLMap.put(aliasAndSchemaURL[0], aliasAndSchemaURL[1]);

                    } else {

                        throw new ToolkitException(NCIP2TranslatorConfiguration.CANONICAL_SCHEMA_URL_MAP_KEY
                            + " has invalid format: '" + canonicalSchemaURLMapString + "';"
                            + " format should be 'aliasURL1=canonicalURL1,aliasURL2=canonicalURL1'. E.g. '"
                            + NCIP2TranslatorConfiguration.CANONICAL_SCHEMA_URL_MAP_DEFAULT + "'.");

                    }

                }

            }
        }

    }

    @Override
    public Map<String, String> getSchemaURLsToPackageMap() {

        return schemaURLsToPackageMap;

    }

    @Override
    public void setSchemaURLsToPackageMap(Map<String, String> urlsToPackageMap) {

        this.schemaURLsToPackageMap = urlsToPackageMap;

    }

    @Override
    public Map<String, String> getCanonicalSchemaURLMap() {

        return canonicalSchemaURLMap;

    }

    @Override
    public void setCanonicalSchemaURLMap(Map<String, String> aliasToCanonicalSchemaURLMap) {
        this.canonicalSchemaURLMap = aliasToCanonicalSchemaURLMap;
    }

}
