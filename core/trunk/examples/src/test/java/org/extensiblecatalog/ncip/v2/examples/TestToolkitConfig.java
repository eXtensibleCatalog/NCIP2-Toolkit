/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import junit.framework.Assert;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class TestToolkitConfig {

    protected static String TEST_FILE = "src/test/data/LookupItemWithExampleItemIdentifierType.xml";

    @Test
    public void testToolkitConfigFile() throws FileNotFoundException, ToolkitException, ServiceException, ValidationException {

        Properties properties = new Properties();
        properties.put(CoreConfiguration.CORE_SCHEME_VALUE_PAIR_ADDED_CLASSES_LIST_KEY, ExampleItemIdentifierType.class.getName());
        Translator translator = TranslatorFactory.buildTranslator(properties);

        InputStream inStream = new FileInputStream(new File(TEST_FILE));

        NCIPInitiationData initiationData = null;

        ServiceContext serviceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();

        initiationData = translator.createInitiationData(serviceContext, inStream);

        Assert.assertNotNull(initiationData);

    }

}
