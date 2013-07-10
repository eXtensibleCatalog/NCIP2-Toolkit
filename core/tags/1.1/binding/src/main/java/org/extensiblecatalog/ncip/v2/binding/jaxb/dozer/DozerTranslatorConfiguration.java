/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.extensiblecatalog.ncip.v2.common.TranslatorConfiguration;

import java.util.List;

public interface DozerTranslatorConfiguration extends TranslatorConfiguration {

    final String MAPPING_FILES_KEY = "DozerTranslatorConfiguration.MappingFiles";
    final String MAPPING_FILES_DEFAULT = "ncipv2_mappings.xml";
    List<String> getMappingFiles();
    void setMappingFiles(List<String> mappingFileNamesList);

}
