/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.extensiblecatalog.ncip.v2.common.DefaultNCIP2TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.common.NCIP2TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.common.ToolkitHelper;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JAXBDozerNCIP2TranslatorConfiguration extends DefaultNCIP2TranslatorConfiguration implements DozerTranslatorConfiguration {

    protected List<String> mappingFileNamesList;

    public JAXBDozerNCIP2TranslatorConfiguration() throws ToolkitException {

        super(System.getProperties());

    }

    public JAXBDozerNCIP2TranslatorConfiguration(Properties properties) throws ToolkitException {

        super(properties);

        if ( properties != null ) {

            String mappingFileNamesListString = this.properties.getProperty(
                DozerTranslatorConfiguration.MAPPING_FILES_KEY,
                DozerTranslatorConfiguration.MAPPING_FILES_DEFAULT);

            if ( mappingFileNamesListString != null ) {

                this.mappingFileNamesList = ToolkitHelper.createStringList(mappingFileNamesListString);

            } else {

                throw new ToolkitException("Property "
                    + DozerTranslatorConfiguration.MAPPING_FILES_KEY + " is null and "
                    + " DozerTranslatorConfiguration.MAPPING_FILES_DEFAULT is null.");

            }

        }

    }

    @Override
    public List<String> getMappingFiles() {

        return mappingFileNamesList;

    }

    @Override
    public void setMappingFiles(final List<String> mappingFileNamesList) {

        this.mappingFileNamesList = mappingFileNamesList;

    }
}
