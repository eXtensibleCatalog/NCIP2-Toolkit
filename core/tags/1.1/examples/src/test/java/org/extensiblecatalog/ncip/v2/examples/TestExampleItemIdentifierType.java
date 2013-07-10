/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.examples;

import junit.framework.Assert;
import org.extensiblecatalog.ncip.v2.common.CoreConfiguration;
import org.extensiblecatalog.ncip.v2.common.CoreConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.common.TranslatorFactory;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class TestExampleItemIdentifierType {

    protected static String TEST_FILE = "src/test/data/LookupItemWithExampleItemIdentifierType.xml";

    @Test
    public void testExampleItemIdentifierType() throws FileNotFoundException, ToolkitException, ServiceException, ValidationException {

        Properties properties = new Properties();
        properties.put("CoreConfiguration.AddedSVPClasses",
            "org.extensiblecatalog.ncip.v2.examples.ExampleItemIdentifierType");

        CoreConfiguration coreConfig = CoreConfigurationFactory.getConfiguration(properties);

        Translator translator = TranslatorFactory.getTranslator(coreConfig.getTranslatorConfiguration());

        InputStream inStream = new FileInputStream(new File(TEST_FILE));

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        NCIPInitiationData initiationData = translator.createInitiationData(serviceContext, inStream);

        Assert.assertNotNull(initiationData);

    }

}
